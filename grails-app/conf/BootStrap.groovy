import onlineeditor.Account

class BootStrap {

    def init = { servletContext ->
        Account.initAccounts()
    }
    def destroy = {
    }
}
