package qrbws

import grails.transaction.Transactional
import qrbws.sender.messages.MessageCreatorEmailRegister
import qrbws.sender.messages.MessageCreatorSMSRegister

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ContaUsuarioController {

    ContaUsuarioService contaUsuarioService

    static responseFormats = ['json']

    static allowedMethods = [
            save           : "POST",
            validaUsuario  : "GET",
            update         : "PUT",
            delete         : "DELETE",
            verificarMultas: "GET"
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ContaUsuario.list(params), model: [contaUsuarioCount: ContaUsuario.count()]
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

        verificacoes(contaUsuario)

        if (contaUsuario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaUsuario.errors, view: 'create'
            return
        }

        contaUsuario.save flush: true

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

        verificacoes(contaUsuario)

        if (contaUsuario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaUsuario.errors, view: 'edit'
            return
        }

        contaUsuario.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contaUsuario.label', default: 'ContaUsuario'), contaUsuario.id])
                redirect contaUsuario
            }
            '*' { respond contaUsuario, [status: OK] }
        }
    }

    @Transactional
    def delete(ContaUsuario contaUsuario) {

        if (contaUsuario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contaUsuario.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contaUsuario.label', default: 'ContaUsuario'), contaUsuario.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    ContaUsuario verificacoes(ContaUsuario contaUsuario) {
        int passwordLength = contaUsuario.password.length()
        if (passwordLength < 6 || passwordLength > 20) {
            contaUsuario.errors.reject('contausuario.senha.pequena')
        }
        contaUsuario
    }

    @Transactional
    def verificarMultas() {
        if (params.contaUsuarioId == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        ContaUsuario contaUsuario = ContaUsuario.get(params.contaUsuarioId)
        def multas = contaUsuarioService.verificarMultas(contaUsuario.getId())

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contaUsuario.label', default: 'ContaUsuario'), contaUsuario.id])
                redirect multa
            }
            '*' { respond multas ?: contaUsuario, [status: OK] }
        }
    }

    @Transactional
    def bloquearContaUsuario() {
        if (params.contaUsuarioId == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        ContaUsuario contaUsuario = ContaUsuario.get(params.contaUsuarioId)
        contaUsuario = contaUsuarioService.bloquearContaUsuario(contaUsuario)

        if (contaUsuario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaUsuario.errors, view: 'create'
            return
        }

        respond contaUsuario, formats: ['json']
    }

    @Transactional
    def habilitarContaUsuario() {
        if (params.contaUsuarioId == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        ContaUsuario contaUsuario = ContaUsuario.get(params.contaUsuarioId)
        contaUsuario = contaUsuarioService.habilitarContaUsuario(contaUsuario)

        if (contaUsuario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaUsuario.errors, view: 'create'
            return
        }

        respond contaUsuario, formats: ['json']
    }

    @Transactional
    def validaUsuario() {

        if (params.username == null || params.password == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        ContaUsuario contaParams = new ContaUsuario(
                username: params.username,
                password: params.password
        )

        ContaUsuario contaUsuario = ContaUsuario.findByUsernameAndPassword(contaParams.username, contaParams.password)

        if (!contaUsuario) {
            contaUsuario = new ContaUsuario()
            contaUsuario.errors.reject('contausuario.nao.existe')
        }

        if (contaUsuario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaUsuario.errors, view: 'create'
            return
        }

        respond contaUsuario, formats: ['json']
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contaUsuario.label', default: 'ContaUsuario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    // TODO: Acrescentar modulo recuperação / troca de senhas
}
