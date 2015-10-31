package qrbws

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class StockController {

    static responseFormats = ['json']

    static allowedMethods = [
            save  : "POST",
            update: "PUT",
            delete: "DELETE"
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Stock.list(params), model: [stockCount: Stock.count()]
    }

    def show(Stock stock) {
        respond stock
    }

    def create() {
        respond new Stock(params)
    }

    @Transactional
    def save(Stock stock) {
        if (stock == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (stock.disponivel > stock.total) {
            stock.errors.reject('stock.disponivel.maior.total')
        }

        if (stock.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stock.errors, view: 'create'
            return
        }

        stock.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'stock.label', default: 'Stock'), stock.id])
                redirect stock
            }
            '*' { respond stock, [status: CREATED] }
        }
    }

    @Transactional
    def update(Stock stock) {
        if (stock == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (stock.disponivel > stock.total) {
            stock.errors.reject('stock.disponivel.maior.total')
        }

        if (stock.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stock.errors, view: 'edit'
            return
        }

        stock.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'stock.label', default: 'Stock'), stock.id])
                redirect stock
            }
            '*' { respond stock, [status: OK] }
        }
    }

    @Transactional
    def delete(Stock stock) {

        if (stock == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        stock.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'stock.label', default: 'Stock'), stock.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'stock.label', default: 'Stock'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
