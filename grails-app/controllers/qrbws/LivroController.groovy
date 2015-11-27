package qrbws

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

class LivroController {

    static responseFormats = ['json']

    static allowedMethods = [
            save  : "POST",
            update: "PUT",
            delete: "DELETE"
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Livro.list(params), model: [livroCount: Livro.count()]
    }

    def show(Livro livro) {
        respond livro
    }

    def create() {
        respond new Livro(params)
    }

    @Transactional
    def save(Livro livro) {
        if (livro == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (livro.estoque.disponivel > livro.estoque.total) {
            livro.errors.reject('livro.disponivel.maior.total')
        }

        if (livro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond livro.errors, view: 'create'
            return
        }

        livro.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'livro.label', default: 'Livro'), livro.id])
                redirect livro
            }
            '*' { respond livro, [status: CREATED] }
        }
    }

    @Transactional
    def update(Livro livro) {
        if (livro == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (livro.estoque.disponivel > livro.estoque.total) {
            livro.errors.reject('livro.disponivel.maior.total')
        }

        if (livro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond livro.errors, view: 'edit'
            return
        }

        livro.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'livro.label', default: 'Livro'), livro.id])
                redirect livro
            }
            '*' { respond livro, [status: OK] }
        }
    }

    @Transactional
    def delete(Livro livro) {

        if (livro == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        livro.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'livro.label', default: 'Livro'), livro.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'livro.label', default: 'Livro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
