package qrbws

import grails.transaction.Transactional

class MultaController {

    MultaService multaService

    @Transactional
    def pagar() {
        if (params.multaId == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        Multa multa = Multa.get(params.multaId)
        multa = multaService.pagar(multa)

        if (multa.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond multa.errors, view: 'create'
            return
        }

        respond multa, formats: ['json']
    }
}
