package onlineeditor

import com.zoowii.online_editor.utils.Paginator
import grails.transaction.Transactional

@Transactional
class BlogController extends BaseController {

    BlogService blogService

    def index(String authorName) {
        def author = Account.findByUserName(authorName)
        if (!author) {
            flash.error = "Can't find user ${authorName}"
        }
        def paginator = new Paginator(request)
        def articles = blogService.getRenderedArticles(paginator, author)
        def tags = blogService.getTags(author)
        [articles: articles, paginator: paginator, author: author, blogTitle: 'Blog Title', tags: tags]
    }
}
