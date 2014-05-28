package onlineeditor

import com.zoowii.online_editor.orm.Expr
import com.zoowii.online_editor.orm.Query
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
    String blogMetaInfoTableName = 'blog_meta'
    MarkDownService markDownService
    TableService tableService

    def getBlogMetaInfo(Account user) {
        def table = BigTable.getOrCreate(user, blogMetaInfoTableName)
        if (table.entities.size() > 0) { // FIXME: this is slow
            return table.entities.last()
        }
        def entity = new Entity(table: table)
        table.addEntity(entity)
        entity.save()
        table.save()
        return entity
    }

    /**
     * 获取博客主标题
     */
    def getBlogSiteTitle(Account user) {
        def metaInfoEntity = getBlogMetaInfo(user)
        def title = tableService.getEntityColumnValue(metaInfoEntity, 'site_title')
        return title
    }

    def setBlogSiteTitle(Account user, String title) {
        def metaInfoEntity = getBlogMetaInfo(user)
        def col = tableService.getEntityColumn(metaInfoEntity, 'site_title')
        if (col) {
            col.value = title
            col.save()
        } else {
            tableService.insertColumn(metaInfoEntity, 'site_title', title)
        }
    }

    def getTags(Account user) {
        def table = BigTable.getOrCreate(user, articleTableName)
        def query = new Query(EntityColumn)
        // TODO: use TableService
        query = query.eq('entity.table', table).eq('name', 'tags').desc('createdTime')
        def ecs = query.all()
        def tags = []
        ecs.each { ec ->
            tags.addAll(ec.value?.split(','))
        }
        return tags.toSet()
    }

    def getArticleFiles(Paginator paginator, Account user) {
        def query = new Query(CloudFile)
        query = query.eq('bucket', Bucket.getOrCreate(user, bucketName)).eq('author', user).desc('createdTime')
        query.offset(paginator.getOffset())
        query.limit(paginator.pageSize)
        def files = query.all()
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
        def entity = tableService.find(table, Expr.createEQ('file_id', file.id))
        def cols = entity?.columns
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
        println(info)
        return info
    }

    def parseArticle(CloudFile file) {
        def html = markDownService.renderMarkDown(file.content)
        def metaInfo = getInfoOfArticleFile(file)
        return [
                id: file.id,
                html       : html, tags: metaInfo['tags'],
                title      : metaInfo['title'], summary: metaInfo['summary'],
                createdTime: file.createdTime, date: metaInfo['date'], author: file.author
        ]
    }

    /**
     * parse files to articles(with markdown rendered html, tags, comments, etc.)
     * @param files
     */
    def parseArticles(List<CloudFile> files) {
        return files.collect { file ->
            return parseArticle(file)
        }
    }
}
