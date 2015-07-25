package qrbws

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class StatusController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Status.list(params), model: [statusCount: Status.count()]
    }

    def show(Status status) {
        respond status
    }

    def create() {
        respond new Status(params)
    }

    @Transactional
    def save(Status status) {
        if (status == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (status.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond status.errors, view: 'create'
            return
        }

        status.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'status.label', default: 'Status'), status.id])
                redirect status
            }
            '*' { respond status, [status: CREATED] }
        }
    }

    def edit(Status status) {
        respond status
    }

    @Transactional
    def update(Status status) {
        if (status == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (status.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond status.errors, view: 'edit'
            return
        }

        status.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'status.label', default: 'Status'), status.id])
                redirect status
            }
            '*' { respond status, [status: OK] }
        }
    }

    @Transactional
    def delete(Status status) {

        if (status == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        status.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'status.label', default: 'Status'), status.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'status.label', default: 'Status'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
