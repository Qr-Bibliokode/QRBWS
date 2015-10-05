package qrbws

class Emprestimo {

    Date dateEmprestimo
    Date dateDevolucao
    Date dateLimiteDevolucao
    boolean devolvido
    boolean avisado

    ContaUsuario contaUsuario
    Livro livro

    static constraints = {
        dateEmprestimo nullable: true
        dateDevolucao nullable: true
        dateLimiteDevolucao nullable: true
        contaUsuario nullable: false
        livro nullable: false
    }
}
