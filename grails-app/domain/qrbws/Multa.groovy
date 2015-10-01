package qrbws

class Multa {

    Double valor
    Emprestimo emprestimo
    Boolean paga
    Date dataPagamento

    static belongsTo = ContaUsuario

    static constraints = {
        valor min: 0
        dataPagamento nullable: true
    }
}
