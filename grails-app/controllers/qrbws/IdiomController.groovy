package qrbws

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class IdiomController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Idiom.list(params), model: [idiomCount: Idiom.count()]
    }

    def show(Idiom idiom) {
        respond idiom
    }

    def create() {
        respond new Idiom(params)
    }

    @Transactional
    def save(Idiom idiom) {
        if (idiom == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (idiom.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond idiom.errors, view: 'create'
            return
        }

        idiom.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'idiom.label', default: 'Idiom'), idiom.id])
                redirect idiom
            }
            '*' { respond idiom, [status: CREATED] }
        }
    }

    def edit(Idiom idiom) {
        respond idiom
    }

    @Transactional
    def update(Idiom idiom) {
        if (idiom == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (idiom.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond idiom.errors, view: 'edit'
            return
        }

        idiom.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'idiom.label', default: 'Idiom'), idiom.id])
                redirect idiom
            }
            '*' { respond idiom, [status: OK] }
        }
    }

    @Transactional
    def delete(Idiom idiom) {

        if (idiom == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        idiom.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'idiom.label', default: 'Idiom'), idiom.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'idiom.label', default: 'Idiom'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
