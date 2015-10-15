package qrbws

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED

class MultaController {

    MultaService multaService

    @Transactional
    def pagar(Multa multa) {
        if (multa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        multa = multaService.pagar(multa)

        if (multa.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond multa.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'emprestimo.label', default: 'Emprestimo'), multa.id])
                redirect multa
            }
            '*' { respond multa, [status: CREATED] }
        }
    }
}
