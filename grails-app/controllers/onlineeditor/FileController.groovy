package onlineeditor

import com.zoowii.online_editor.utils.Paginator
import grails.transaction.Transactional
import org.markdown4j.Markdown4jProcessor

/**
 * TODO:
 * * add markdown preview
 */
class FileController extends BaseController {

    def beforeInterceptor = [action: this.&auth, except: ['get', 'getMarkDown']]

    def static allowedMethods = [createOrUpdate: 'POST', doCreate: 'POST', changeAcl: 'POST']

    MarkDownService markDownService

    def list() {
        def user = currentUser()
        def paginator = new Paginator(request)
        def query = "from CloudFile as f where author = ? order by createdTime desc"
        def files = CloudFile.findAll(query, [user], [max: paginator.pageSize, offset: paginator.getOffset()])
        paginator.setTotalCount(CloudFile.countByAuthor(user))
        [files: files, paginator: paginator]
    }

    def create() {
        [file: new CloudFile(), user: currentUser()]
    }

    @Transactional
    def get(String id) {
        def user = currentUser()
        def file = CloudFile.findById(id)
        if (file == null) {
            flash.error = "Can't find this file"
            redirect(controller: 'site', action: 'index')
            return
        }
        if (file.isPrivate && file.author != user) {
            flash.error = 'You have no permission to view file'
            redirect(controller: 'site', action: 'index')
            return
        }
        file.visitCount += 1
        file.save()
        response.setContentType(file.mimeType)
        render text: file.content, encoding: file.encoding
    }

    @Transactional
    def getMarkDown(String id) {
        def user = currentUser()
        def file = CloudFile.findById(id)
        if (file == null) {
            flash.error = "Can't find this file"
            redirect(controller: 'site', action: 'index')
            return
        }
        if (file.isPrivate && file.author != user) {
            flash.error = 'You have no permission to view file'
            redirect(controller: 'site', action: 'index')
            return
        }
        file.visitCount += 1
        file.save()
        response.setContentType(file.mimeType)
        def html = markDownService.renderMarkDown(file.content)
        render text: html, encoding: file.encoding
    }

    @Transactional
    def doCreate() {
        def user = currentUser()
        if (user == null) {
            flash.error = 'login required'
            redirectToLogin()
            return
        }
        String name = params.name
        def description = params.description
        def isPrivate = params.is_private == 'on'
        String bucketId = params.bucket
        def bucket = Bucket.findById(bucketId)
        if (bucket == null || bucket.owner != user) {
            flash.error = "You don't have this bucket"
            redirect(action: 'create')
            return
        }
        if (CloudFile.findByNameAndAuthor(name, user)) {
            flash.error = "file with name ${name} exists"
            redirect(action: 'create')
            return
        }
        def file = new CloudFile(author: user, name: name, bucket: bucket, description: description, isPrivate: isPrivate, content: '')
        file.content = 'Hello World!'
        if (!file.description || file.description.length() < 1) {
            file.description = file.name
        }
        println file
        file.validate()
        if (file.hasErrors()) {
            flash.error = file.getErrors().toString()
            redirect(action: 'create')
            return
        }
        file.save()
        file.addChangeLog()
        flash.success = 'Created file successfully'
        redirect(action: 'edit', params: [id: file.id])
    }

    @Transactional
    def edit(String id) {
        def user = currentUser()
        def file = CloudFile.findById(id)
        def isNew = file == null
        if (isNew) {
            file = new CloudFile()
        } else {
            if (file.author != user) {
                flash.error = "Your have no permission to edit this file"
                redirectToLogin()
                return
            }
            file.visitCount += 1
            file.save()
        }
        render view: 'edit', model: [file: file, isNew: isNew]
    }

    @Transactional
    def createOrUpdate() {
        String id = params.id
        def user = currentUser()
        if (user == null) {
            redirectToLogin()
            return
        }
        def version = Integer.valueOf(params.version)
        def content = params.content
        def mimeType = params.mime_type
        def file = CloudFile.findById(id)
        def isNew = file == null
        if (isNew) {
            flash.error = "Can't find this file"
            redirect(action: 'create')
            return
        }
        if (file.author != user) {
            ajaxFail('You are not author of this file')
            return
        }
        if (file.fileVersion > version) {
            ajaxFail('Your file version is too old, please flush it')
            return
        }
        if (!file.content?.equals(content) || !file.mimeType?.equals(mimeType)) {
            file.mimeType = mimeType
            file.content = content
            file.fileVersion += 1
            file.lastUpdatedTime = new Date()
            // TODO: add file change log
        }
        file.validate()
        if (file.hasErrors()) {
            ajaxFail(file.getErrors().toString())
            return
        }
        file.save()
        file.addChangeLog()
        ajaxSuccess([id: file.id, version: file.fileVersion, name: file.name])
    }

    @Transactional
    def changeAcl(String id) {
        def user = currentUser()
        def version = Integer.valueOf(params.version)
        def file = CloudFile.findById(id)
        if (file == null) {
            ajaxFail("Can't find file ${id}")
            return
        }
        if (file.author != user) {
            ajaxFail('You are not the owner of this file')
            return
        }
        if (file.fileVersion > version) {
            ajaxFail('you version of this file is expired')
            return
        }
        file.isPrivate = !file.isPrivate
        file.lastUpdatedTime = new Date()
        file.fileVersion += 1
        file.save()
        file.addChangeLog()
        ajaxSuccess([id: file.id, version: file.fileVersion, isPrivate: file.isPrivate])
    }

}
