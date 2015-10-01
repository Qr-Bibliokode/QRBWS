package qrbws

class Emprestimo {

    Date dateEmprestimo
    Date dateDevolucao
    Date dateLimiteDevolucao
    boolean devolvido
    boolean alertado

    ContaUsuario contaUsuario
    Livro livro

    static constraints = {
        dateEmprestimo nullable: false
        dateDevolucao nullable: false
        dateLimiteDevolucao nullable: true
        contaUsuario nullable: false
        livro nullable: false
    }
}
