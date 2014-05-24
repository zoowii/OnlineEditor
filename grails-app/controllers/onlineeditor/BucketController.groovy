package onlineeditor

import com.zoowii.online_editor.utils.Paginator
import grails.transaction.Transactional

class BucketController extends BaseController {
    def beforeInterceptor = [action: this.&auth, except: []]

    def static allowedMethods = [create: 'POST']

    def index() {
        def user = currentUser()
        def paginator = new Paginator(request)
        def query = "from Bucket as b where owner=? order by createdTime desc"
        def buckets = Bucket.findAll(query, [user], [max: paginator.pageSize, offset: paginator.getOffset()])
        paginator.setTotalCount(Bucket.countByOwner(user))
        [buckets: buckets, paginator: paginator]
    }

    def createPage() {
        render view: 'create'
    }

    @Transactional
    def create() {
        def user = currentUser()
        String name = params.name
        if (!name || name.length() < 1) {
            flash.error = "bucket name can't be empty"
            redirect(action: 'createPage')
            return
        }
        def bucket = Bucket.findByName(name)
        if (bucket != null) {
            flash.error = "bucket '${name}' exists!"
            redirect(action: 'createPage')
            return
        }
        bucket = new Bucket(owner: user, name: name)
        bucket.validate()
        if (bucket.hasErrors()) {
            flash.error = bucket.getErrors()
            redirect(action: 'createPage')
            return
        }
        bucket.save()
        flash.success = "Created bucket '${name}' successfully"
        redirect(action: 'index')
    }
}
