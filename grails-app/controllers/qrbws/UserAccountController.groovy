package qrbws

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserAccountController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond UserAccount.list(params), model:[userAccountCount: UserAccount.count()]
    }

    def show(UserAccount userAccount) {
        respond userAccount
    }

    def create() {
        respond new UserAccount(params)
    }

    @Transactional
    def save(UserAccount userAccount) {
        if (userAccount == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (userAccount.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond userAccount.errors, view:'create'
            return
        }

        userAccount.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userAccount.label', default: 'UserAccount'), userAccount.id])
                redirect userAccount
            }
            '*' { respond userAccount, [status: CREATED] }
        }
    }

    def edit(UserAccount userAccount) {
        respond userAccount
    }

    @Transactional
    def update(UserAccount userAccount) {
        if (userAccount == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (userAccount.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond userAccount.errors, view:'edit'
            return
        }

        userAccount.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'userAccount.label', default: 'UserAccount'), userAccount.id])
                redirect userAccount
            }
            '*'{ respond userAccount, [status: OK] }
        }
    }

    @Transactional
    def delete(UserAccount userAccount) {

        if (userAccount == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        userAccount.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'userAccount.label', default: 'UserAccount'), userAccount.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userAccount.label', default: 'UserAccount'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
