package qrbws

import grails.transaction.Transactional

@Transactional
class MultaService {

    Boolean temMultasSemPagar(ContaUsuario contaUsuario) {
        contaUsuario.multas ? contaUsuario.multas.find { if (!it.paga) true } : false
    }

    Multa pagar(Multa multa) {
        multa.dataPagamento = new Date()
        multa.paga = true
        multa.save flush: true
        multa
    }
}
