package qrbws

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class EstoqueController {

    static responseFormats = ['json']

    static allowedMethods = [
            save  : "POST",
            update: "PUT",
            delete: "DELETE"
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Estoque.list(params), model: [estoqueCount: Estoque.count()]
    }

    def show(Estoque estoque) {
        respond estoque
    }

    def create() {
        respond new Estoque(params)
    }

    @Transactional
    def save(Estoque estoque) {
        if (estoque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (estoque.disponivel > estoque.total) {
            estoque.errors.reject('estoque.disponivel.maior.total')
        }

        if (estoque.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond estoque.errors, view: 'create'
            return
        }

        estoque.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'estoque.label', default: 'Estoque'), estoque.id])
                redirect estoque
            }
            '*' { respond estoque, [status: CREATED] }
        }
    }

    @Transactional
    def update(Estoque estoque) {
        if (estoque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (estoque.disponivel > estoque.total) {
            estoque.errors.reject('estoque.disponivel.maior.total')
        }

        if (estoque.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond estoque.errors, view: 'edit'
            return
        }

        estoque.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'estoque.label', default: 'Estoque'), estoque.id])
                redirect estoque
            }
            '*' { respond estoque, [status: OK] }
        }
    }

    @Transactional
    def delete(Estoque estoque) {

        if (estoque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        estoque.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'estoque.label', default: 'Estoque'), estoque.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'estoque.label', default: 'Estoque'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
