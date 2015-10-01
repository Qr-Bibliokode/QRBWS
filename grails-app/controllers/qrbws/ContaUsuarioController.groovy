package qrbws

import qrbws.sender.messages.MessageCreatorEmailRegister
import qrbws.sender.messages.MessageCreatorSMSRegister

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ContaUsuarioController {

    def contaUsuarioService

    static responseFormats = ['json']

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ContaUsuario.list(params), model:[contaUsuarioCount: ContaUsuario.count()]
    }

    def show(ContaUsuario contaUsuario) {
        respond contaUsuario
    }

    def create() {
        respond new ContaUsuario(params)
    }

    @Transactional
    def save(ContaUsuario contaUsuario) {
        if (contaUsuario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contaUsuario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaUsuario.errors, view:'create'
            return
        }

        contaUsuario.save flush:true

        if (contaUsuario) {
            contaUsuario.pessoa.celular != null ? contaUsuarioService.sendSMS(contaUsuario, new MessageCreatorSMSRegister()) : ''
            contaUsuarioService.sendEmail(contaUsuario, new MessageCreatorEmailRegister())
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contaUsuario.label', default: 'ContaUsuario'), contaUsuario.id])
                redirect contaUsuario
            }
            '*' { respond contaUsuario, [status: CREATED] }
        }
    }

    def edit(ContaUsuario contaUsuario) {
        respond contaUsuario
    }

    @Transactional
    def update(ContaUsuario contaUsuario) {
        if (contaUsuario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contaUsuario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaUsuario.errors, view:'edit'
            return
        }

        contaUsuario.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contaUsuario.label', default: 'ContaUsuario'), contaUsuario.id])
                redirect contaUsuario
            }
            '*'{ respond contaUsuario, [status: OK] }
        }
    }

    @Transactional
    def delete(ContaUsuario contaUsuario) {

        if (contaUsuario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contaUsuario.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contaUsuario.label', default: 'ContaUsuario'), contaUsuario.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contaUsuario.label', default: 'ContaUsuario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
