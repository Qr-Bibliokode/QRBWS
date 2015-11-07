package qrbws

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class FeriadoController {

    def feriadoService

    static responseFormats = ['json']

    static allowedMethods = [
            save  : "POST",
            update: "PUT",
            delete: "DELETE"
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Feriado.list(params), model: [feriadoCount: Feriado.count()]
    }

    def show(Feriado feriado) {
        respond feriado
    }

    def create() {
        respond new Feriado(params)
    }

    @Transactional
    def save(Feriado feriado) {
        if (feriado == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        feriadoService.validaFeriado(feriado)

        if (feriado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond feriado.errors, view: 'create'
            return
        }

        feriado.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'feriado.label', default: 'feriado'), feriado.id])
                redirect feriado
            }
            '*' { respond feriado, [status: CREATED] }
        }
    }

    @Transactional
    def update(Feriado feriado) {
        if (feriado == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        feriadoService.validaFeriado(feriado)

        if (feriado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond feriado.errors, view: 'edit'
            return
        }

        feriado.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'feriado.label', default: 'Feriado'), feriado.id])
                redirect feriado
            }
            '*' { respond feriado, [status: OK] }
        }
    }

    @Transactional
    def delete(Feriado feriado) {

        if (feriado == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        feriado.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'feriado.label', default: 'Feriado'), feriado.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }


    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'feriado.label', default: 'Feriado'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
