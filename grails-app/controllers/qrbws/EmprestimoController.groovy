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

        if (emprestimo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond emprestimo.errors, view: 'create'
            return
        }

        if (emprestimoService.temMultasSemPagar(emprestimo.contaUsuario)) {
            transactionStatus.setRollbackOnly()
            renderErrorResponse('contausuario.multa.contem', emprestimo)
        }

        if (!emprestimoService.temStock(emprestimo.livro)) {
            transactionStatus.setRollbackOnly()
            renderErrorResponse('stock.livro.indisponivel', emprestimo)
        }

        if (emprestimoService.temEmprestimoForaDeData(emprestimo.contaUsuario)) {
            transactionStatus.setRollbackOnly()
            renderErrorResponse('emprestimo.invalido.passou.data.devolucao', emprestimo)
        }

        if (emprestimoService.excedeLimiteEmprestimos(emprestimo.contaUsuario)) {
            transactionStatus.setRollbackOnly()
            renderErrorResponse('emprestimo.invalido.passou.limite.emprestimos', emprestimo)
        }

        if (emprestimoService.existemReservasAtivasSuperiorADisponivel(emprestimo.livro)) {
            transactionStatus.setRollbackOnly()
            renderErrorResponse('emprestimo.invalido.existem.reservas', emprestimo)
        }

        emprestimoService.emprestar(emprestimo);

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'emprestimo.label', default: 'Emprestimo'), emprestimo.id])
                redirect emprestimo
            }
            '*' { respond emprestimo, [status: CREATED] }
        }
    }

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

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'emprestimo.label', default: 'Emprestimo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    protected void renderErrorResponse(String mensagem, Emprestimo emprestimo) {
        emprestimo.errors.reject(mensagem, ['emprestimo', 'class Emprestimo'] as Object[], null)

        respond emprestimo.errors, view: 'edit'
    }
}
