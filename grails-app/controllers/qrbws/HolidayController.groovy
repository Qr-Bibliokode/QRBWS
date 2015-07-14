package qrbws

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class HolidayController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Holiday.list(params), model:[holidayCount: Holiday.count()]
    }

    def show(Holiday holiday) {
        respond holiday
    }

    def create() {
        respond new Holiday(params)
    }

    @Transactional
    def save(Holiday holiday) {
        if (holiday == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (holiday.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond holiday.errors, view:'create'
            return
        }

        holiday.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'holiday.label', default: 'Holiday'), holiday.id])
                redirect holiday
            }
            '*' { respond holiday, [status: CREATED] }
        }
    }

    def edit(Holiday holiday) {
        respond holiday
    }

    @Transactional
    def update(Holiday holiday) {
        if (holiday == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (holiday.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond holiday.errors, view:'edit'
            return
        }

        holiday.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'holiday.label', default: 'Holiday'), holiday.id])
                redirect holiday
            }
            '*'{ respond holiday, [status: OK] }
        }
    }

    @Transactional
    def delete(Holiday holiday) {

        if (holiday == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        holiday.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'holiday.label', default: 'Holiday'), holiday.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'holiday.label', default: 'Holiday'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
