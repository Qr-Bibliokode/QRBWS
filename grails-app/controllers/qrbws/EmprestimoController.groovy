package qrbws

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class EmprestimoController {

    def lendingService

    static responseFormats = ['json']

    static allowedMethods = [
            emprestar: "POST",
            devolver: "PUT",
            update    : "PUT",
            delete    : "DELETE"
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Emprestimo.list(params), model: [lendingCount: Emprestimo.count()]
    }

    def show(Emprestimo lending) {
        respond lending
    }

    def create() {
        respond new Emprestimo(params)
    }

    def edit(Emprestimo lending) {
        respond lending
    }

    @Transactional
    def update(Emprestimo lending) {
        if (lending == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (lending.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond lending.errors, view: 'edit'
            return
        }

        lending.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'lending.label', default: 'Emprestimo'), lending.id])
                redirect lending
            }
            '*' { respond lending, [status: OK] }
        }
    }

    @Transactional
    def delete(Emprestimo lending) {

        if (lending == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        lending.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'lending.label', default: 'Emprestimo'), lending.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    @Transactional
    def emprestar(Emprestimo lending) {
        if (lending == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (lending.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond lending.errors, view: 'create'
            return
        }

        lending.dateEmprestimo = new Date()

        lending.save flush: true

        if (lending) {
            lendingService.emprestar(lending)
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'lending.label', default: 'Emprestimo'), lending.id])
                redirect lending
            }
            '*' { respond lending, [status: CREATED] }
        }
    }

    @Transactional
    def devolver(Emprestimo lending) {
        if (lending == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }


        lendingService.devolver(lending)

        if (lending.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond lending.errors, view: 'edit'
            return
        }

        lending.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'lending.label', default: 'Emprestimo'), lending.id])
                redirect lending
            }
            '*' { respond lending, [status: OK] }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'lending.label', default: 'Emprestimo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
