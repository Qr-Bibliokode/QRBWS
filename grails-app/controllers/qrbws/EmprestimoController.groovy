package qrbws

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class EmprestimoController {

    EmprestimoService emprestimoService
    MultaService multaService

    static responseFormats = ['json']

    static allowedMethods = [
            emprestar: "POST",
            devolver : "GET",
            renovar  : "GET",
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

    @Transactional
    def devolver() {
        Emprestimo emprestimo = Emprestimo.get(params.emprestimoId)
        if (emprestimo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        emprestimoService.devolver(emprestimo)

        if (emprestimo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond emprestimo.errors, view: 'edit'
            return
        }

        emprestimo.save flush: true

        if (multaService.temMultasSemPagar(emprestimo.contaUsuario)) {
            emprestimo.errors.reject('contausuario.multa.contem')
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'emprestimo.label', default: 'Emprestimo'), emprestimo.id])
                redirect emprestimo
            }
            '*' {
                respond emprestimo.hasErrors() ? emprestimo.errors : emprestimo, [status: OK]
            }
        }
    }

    @Transactional
    def renovar() {
        Emprestimo emprestimo = Emprestimo.get(params.emprestimoId)
        if (emprestimo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        emprestimoService.renovar(emprestimo)

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

    @Transactional
    def liberar() {
        Emprestimo emprestimo = Emprestimo.get(params.emprestimoId)
        if (emprestimo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        emprestimoService.liberar(emprestimo)

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

    def obtenhaHistoricoEmprestimosPorLivro() {
        Livro livro = Livro.get(params.livroId)
        if (livro == null) {
            notFound()
            return
        }

        List<Emprestimo> emprestimos = emprestimoService.obtenhaHistoricoEmprestimosPorLivro(livro)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'emprestimos.label', default: 'Emprestimo'), emprestimos.size])
                redirect emprestimo
            }
            '*' { respond emprestimos, [status: CREATED] }
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
