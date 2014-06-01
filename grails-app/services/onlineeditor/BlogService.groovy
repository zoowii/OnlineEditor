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
     * store article meta data as ['file_id', 'title', 'summary', 'date', 'tags', 'visit_count']
     */
    String articleTableName = 'blog_article'
    String blogMetaInfoTableName = 'blog_meta'
    String blogOutLinksTableName = 'blog_outlinks'
    MarkDownService markDownService
    TableService tableService

    def Entity getBlogMetaInfoEntity(Account user) {
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

    def getBlogOutLinksTable(Account user) {
        return BigTable.getOrCreate(user, blogOutLinksTableName)
    }

    def getBlogOutLinks(Account user) {
        def table = getBlogOutLinksTable(user)
        def outLinks = table.entities.collect { entity ->
            def url = tableService.getEntityColumnValue(entity, 'url')
            def name = tableService.getEntityColumnValue(entity, 'name')
            def title = tableService.getEntityColumnValue(entity, 'title')
            if (!url) {
                return null
            } else {
                return [url: url, name: name, title: title]
            }
        }
        outLinks = outLinks.findAll { it != null }
        return outLinks
    }

    def getBlogMetaInfo(Account user) {
        def metaInfoEntity = getBlogMetaInfoEntity(user)
        def metaInfo = [tableId: metaInfoEntity.table.id, id: metaInfoEntity.id, site_title: null, blog_host: null, duoshuo_username: user.userName, sub_title: null]
        metaInfoEntity.columns.each { col ->
            if (col.name == 'site_title') {
                metaInfo['site_title'] = col.value
            } else if (col.name == 'blog_host') {
                metaInfo['blog_host'] = col.value
            } else if (col.name == 'duoshuo_username') {
                metaInfo['duoshuo_username'] = col.value
            } else if (col.name == 'sub_title') {
                metaInfo['sub_title'] = col.value
            }
        }
        return metaInfo
    }

    /**
     * 获取博客主标题
     */
    def getBlogSiteTitle(Account user) {
        def metaInfoEntity = getBlogMetaInfoEntity(user)
        def title = tableService.getEntityColumnValue(metaInfoEntity, 'site_title')
        return title
    }

    def setBlogSiteTitle(Account user, String title) {
        def metaInfoEntity = getBlogMetaInfoEntity(user)
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
            tags.addAll(ec.value?.split(',').collect { s -> s.trim() })
        }
        def set = new HashSet()
        set.addAll(tags)
        return set
    }

    def getArticleFiles(Paginator paginator, Account user) {
        def query = new Query(CloudFile)
        def bucket = Bucket.getOrCreate(user, bucketName)
        query = query.eq('bucket', bucket).eq('author', user).desc('createdTime')
        paginator.setTotalCount(CloudFile.countByAuthorAndBucket(user, bucket))
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
        def info = [tags: [], title: '', date: new Date(), summary: '', visitCount: 0]
        for (def col : cols) {
            if (col.name == 'tags') {
                info['tags'] = col.value?.split(',')
            } else if (col.name == 'title') {
                info['title'] = col.value
            } else if (col.name == 'date') {
                println(col.value)
                def sdf = new SimpleDateFormat('yyyy-MM-dd', Locale.CHINA)
                try {
                    info['date'] = sdf.parse(col.value)
                    println(info['date'])
                } catch (Exception e) {
                }
            } else if (col.name == 'summary') {
                info['summary'] = col.value
            } else if (col.name == 'visitCount') {
                info['visitCount'] = Integer.valueOf(col.value)
            }
        }
        println(info)
        return info
    }

    def parseArticle(CloudFile file) {
        def html = markDownService.renderMarkDown(file.content)
        def metaInfo = getInfoOfArticleFile(file)
        return [
                id         : file.id,
                html       : html,
                tags       : metaInfo['tags'],
                title      : metaInfo['title'],
                summary    : metaInfo['summary'],
                createdTime: file.createdTime,
                date       : metaInfo['date'],
                author     : file.author
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

    def getOrCreateBlogBucket(Account user) {
        return Bucket.getOrCreate(user, bucketName)
    }

    def getOrCreateBlogArticleTable(Account user) {
        return BigTable.getOrCreate(user, articleTableName)
    }

}
