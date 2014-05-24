package onlineeditor

import com.zoowii.online_editor.utils.Paginator
import grails.transaction.Transactional

class TableController extends BaseController {

    def beforeInterceptor = [action: this.&auth, except: []]

    def list() {
        def user = currentUser()
        if (user == null) {
            redirectToLogin()
            return
        }
        def paginator = new Paginator(request)
        def query = "from BigTable as tbl where owner=? order by createdTime desc"
        def tables = Bucket.findAll(query, [user], [max: paginator.pageSize, offset: paginator.getOffset()])
        paginator.setTotalCount(BigTable.countByOwner(user))
        [tables: tables, paginator: paginator]
    }

    def createPage() {
        editPage(null)
    }

    def editPage(String id) {
        def table = BigTable.findById(id)
        def isNew = table == null
        if (isNew) {
            table = new BigTable()
        }
        render view: 'edit', model: [table: table, isNew: isNew]
    }

    @Transactional
    def createOrUpdate(String id) {
        def user = currentUser()
        String name = params.name
        if (!name || name.length() < 1) {
            flash.error = "table name can't be empty"
            redirect(action: 'editPage', params: [id: id])
            return
        }
        def table = BigTable.findById(id)
        def isNew = table == null
        if (BigTable.findByNameAndOwner(name, user) != null) {
            flash.error = "table '${name}' exists!"
            redirect(action: 'editPage', params: [id: id])
            return
        }
        if (isNew) {
            table = new BigTable(owner: user, name: name)
        } else {
            if (table.owner != user) {
                flash.error = "You are not the owner of this table"
                redirect(action: 'editPage', params: [id: id])
                return
            }
            table.name = name
        }
        table.validate()
        if (table.hasErrors()) {
            flash.error = table.getErrors()
            redirect(action: 'editPage', params: [id: id])
            return
        }
        table.save()
        flash.success = "Created/Edit table '${name}' successfully"
        redirect(action: 'list')
    }

}
