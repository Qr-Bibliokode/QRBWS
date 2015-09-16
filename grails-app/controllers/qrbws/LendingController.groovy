package qrbws

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class LendingController {

    def lendingService

    static responseFormats = ['json']

    static allowedMethods = [
            lend      : "POST",
            devolution: "PUT",
            update    : "PUT",
            delete    : "DELETE"
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Lending.list(params), model: [lendingCount: Lending.count()]
    }

    def show(Lending lending) {
        respond lending
    }

    def create() {
        respond new Lending(params)
    }

    @Transactional
    def lend(Lending lending) {
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

        lending.dateOut = new Date()

        lending.save flush: true

        if (lending) {
            lendingService.lend(lending)
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'lending.label', default: 'Lending'), lending.id])
                redirect lending
            }
            '*' { respond lending, [status: CREATED] }
        }
    }

    def edit(Lending lending) {
        respond lending
    }


    @Transactional
    def devolution(Lending lending) {
        if (lending == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }


        lendingService.devolution(lending)

        if (lending.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond lending.errors, view: 'edit'
            return
        }

        lending.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'lending.label', default: 'Lending'), lending.id])
                redirect lending
            }
            '*' { respond lending, [status: OK] }
        }
    }

    @Transactional
    def update(Lending lending) {
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
                flash.message = message(code: 'default.updated.message', args: [message(code: 'lending.label', default: 'Lending'), lending.id])
                redirect lending
            }
            '*' { respond lending, [status: OK] }
        }
    }

    @Transactional
    def delete(Lending lending) {

        if (lending == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        lending.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'lending.label', default: 'Lending'), lending.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'lending.label', default: 'Lending'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
