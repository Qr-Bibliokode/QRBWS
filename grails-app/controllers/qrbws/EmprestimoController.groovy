package qrbws

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class EmprestimoController {

    EmprestimoService emprestimoService

    static responseFormats = ['json']

    static allowedMethods = [
            emprestar: "POST",
            devolver : "PUT",
            renovar  : "PUT",
            update   : "PUT",
            delete   : "DELETE"
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Emprestimo.list(params), model: [emprestimoCount: Emprestimo.count()]
    }

    def show(Emprestimo emprestimo) {
        respond emprestimo
    }

    def create() {
        respond new Emprestimo(params)
    }

    def edit(Emprestimo emprestimo) {
        respond emprestimo
    }

    @Transactional
    def update(Emprestimo emprestimo) {
        if (emprestimo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (emprestimo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond emprestimo.errors, view: 'edit'
            return
        }

        emprestimo.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'emprestimo.label', default: 'Emprestimo'), emprestimo.id])
                redirect emprestimo
            }
            '*' { respond emprestimo, [status: OK] }
        }
    }

    @Transactional
    def delete(Emprestimo emprestimo) {

        if (emprestimo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        emprestimo.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'emprestimo.label', default: 'Emprestimo'), emprestimo.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    @Transactional
    def emprestar(Emprestimo emprestimo) {
        if (emprestimo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (emprestimoService.emprestar(emprestimo).hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond emprestimo.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'emprestimo.label', default: 'Emprestimo'), emprestimo.id])
                redirect emprestimo
            }
            '*' { respond emprestimo, [status: CREATED] }
        }
    }

    //TODO: Implementar funcionalidade devolder
    @Transactional
    def devolver(Emprestimo emprestimo) {
        if (emprestimo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (emprestimoService.temMultasSemPagar(emprestimo.contaUsuario)) {
            transactionStatus.setRollbackOnly()
            renderErrorResponse('contausuario.multa.contem')
            return
        }

        emprestimoService.devolver(emprestimo)

        if (emprestimo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond emprestimo.errors, view: 'edit'
            return
        }

        emprestimo.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'emprestimo.label', default: 'Emprestimo'), emprestimo.id])
                redirect emprestimo
            }
            '*' { respond emprestimo, [status: OK] }
        }
    }

    @Transactional
    def renovar(Emprestimo emprestimo) {
        if (emprestimo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        emprestimo = emprestimoService.renovar(emprestimo)
        if (emprestimo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond emprestimo.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'emprestimo.label', default: 'Emprestimo'), emprestimo.id])
                redirect emprestimo
            }
            '*' { respond emprestimo, [status: CREATED] }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'emprestimo.label', default: 'Emprestimo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
