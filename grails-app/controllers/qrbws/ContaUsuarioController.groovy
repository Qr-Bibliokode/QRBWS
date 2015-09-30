package qrbws

import qrbws.sender.messages.MessageCreatorEmailRegister
import qrbws.sender.messages.MessageCreatorSMSRegister

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ContaUsuarioController {

    def userAccountService

    static responseFormats = ['json']

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ContaUsuario.list(params), model:[userAccountCount: ContaUsuario.count()]
    }

    def show(ContaUsuario userAccount) {
        respond userAccount
    }

    def create() {
        respond new ContaUsuario(params)
    }

    @Transactional
    def save(ContaUsuario userAccount) {
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

        if (userAccount) {
            userAccount.pessoa.celular != null ? userAccountService.sendSMS(userAccount, new MessageCreatorSMSRegister()) : ''
            userAccountService.sendEmail(userAccount, new MessageCreatorEmailRegister())
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userAccount.label', default: 'ContaUsuario'), userAccount.id])
                redirect userAccount
            }
            '*' { respond userAccount, [status: CREATED] }
        }
    }

    def edit(ContaUsuario userAccount) {
        respond userAccount
    }

    @Transactional
    def update(ContaUsuario userAccount) {
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
                flash.message = message(code: 'default.updated.message', args: [message(code: 'userAccount.label', default: 'ContaUsuario'), userAccount.id])
                redirect userAccount
            }
            '*'{ respond userAccount, [status: OK] }
        }
    }

    @Transactional
    def delete(ContaUsuario userAccount) {

        if (userAccount == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        userAccount.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'userAccount.label', default: 'ContaUsuario'), userAccount.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userAccount.label', default: 'ContaUsuario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
