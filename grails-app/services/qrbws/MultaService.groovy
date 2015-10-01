package qrbws

import grails.transaction.Transactional

@Transactional
class MultaService {

    Boolean verificarTemMultasSemPagar(ContaUsuario contaUsuario) {
        contaUsuario.multas.each { multa ->
            if (!multa.paga) {
                return true
            }
        }
    }
}
