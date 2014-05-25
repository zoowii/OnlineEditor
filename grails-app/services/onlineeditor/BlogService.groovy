package onlineeditor

import com.zoowii.online_editor.utils.Paginator
import grails.transaction.Transactional

import java.text.SimpleDateFormat

@Transactional
class BlogService {

    String bucketName = 'blog'
    // TODO: serve Blog service as an app in this platform, and use allocated bucket and table
    /**
     * store article meta data as ['file_id', 'title', 'summary', 'date', 'tags']
     */
    String articleTableName = 'blog_article'
    MarkDownService markDownService

    def getTags(Account user) {
        def table = BigTable.getOrCreate(user, articleTableName)
        def query = "from EntityColumn as col where col.entity.table=? and col.name=? order by createdTime desc"
        def ecs = EntityColumn.findAll(query, [table, 'tags'])
        def tags = []
        ecs.each { ec ->
            tags.addAll(ec.value?.split(','))
        }
        return tags.toSet()
    }

    def getArticleFiles(Paginator paginator, Account user) {
        def query = "from CloudFile f where f.bucket=? and f.author=? order by createdTime desc"
        def files = CloudFile.findAll(query, [Bucket.getOrCreate(user, bucketName), user], [max: paginator.pageSize, offset: paginator.getOffset()])
        return files
    }

    def getRenderedArticles(Paginator paginator, Account user) {
        return parseArticles(getArticleFiles(paginator, user))
    }

    /**
     * FIXME: use a BigTable DAO(a like-SQL wrapper to CRUD data)
     * @param file
     * @param user
     */
    def getInfoOfArticleFile(CloudFile file) {
        if (!file) {
            return []
        }
        def table = BigTable.getOrCreate(file.author, articleTableName)
        def query = "from EntityColumn as col where col.entity.table=? and col.name=? and col.value=? order by createdTime desc"
        def ec = EntityColumn.find(query, [table, 'file_id', file.id])
        if (!ec) {
            return []
        }
        def cols = ec?.entity?.columns
        def info = [tags: [], title: '', date: new Date(), summary: '']
        for (def col : cols) {
            if (col.name == 'tags') {
                info['tags'] = col.value?.split(',')
            } else if (col.name == 'title') {
                info['title'] = col.value
            } else if (col.name == 'date') {
                def sdf = new SimpleDateFormat('yyyy-MM-dd', Locale.CHINA)
                try {
                    info['date'] = sdf.format(col.value)
                } catch (Exception e) {

                }
            } else if (col.name == 'summary') {
                info['summary'] = col.value
            }
        }
        return info
    }

    /**
     * parse files to articles(with markdown rendered html, tags, comments, etc.)
     * @param files
     */
    def parseArticles(List<CloudFile> files) {
        def articles = []
        files.each { file ->
            def html = markDownService.renderMarkDown(file.content)
            def metaInfo = getInfoOfArticleFile(file)
            def article = [
                    html       : html, tags: metaInfo['tags'],
                    title      : metaInfo['title'], summary: metaInfo['summary'],
                    createdTime: file.createdTime, date: metaInfo['date'], author: file.author
            ]
            articles.add(article)
        }
        return articles
    }
}
