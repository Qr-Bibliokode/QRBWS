package qrbws

import grails.transaction.Transactional

@Transactional
class MultaService {

    Boolean verificarTemMultasSemPagar(ContaUsuario contaUsuario) {
        contaUsuario.multas ? contaUsuario.multas.find { if (!it.paga) true } : false
    }
}
