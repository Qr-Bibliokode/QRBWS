package qrbws

import grails.converters.JSON

class Multa {

    Double valor
    Emprestimo emprestimo
    Boolean paga
    Date dataPagamento

    static belongsTo = ContaUsuario

    static constraints = {
        valor min: 0.0D
        dataPagamento nullable: true
        paga nullable: true
    }

    static void marshaller() {
        JSON.registerObjectMarshaller(Multa) {
            [
                    'id'           : it.id,
                    'valor'        : it.valor,
                    'emprestimo'   : it.emprestimo,
                    'paga'         : it.paga,
                    'dataPagamento': it.dataPagamento
            ]
        }
    }
}
