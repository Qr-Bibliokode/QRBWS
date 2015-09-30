package qrbws

class Emprestimo {

    // TODO: Change the nome of dateEmprestimo and dateDevolucao
    Date dateEmprestimo
    Date dateDevolucao
    Date dateLimiteDevolucao
    boolean devolvido
    boolean alertado

    ContaUsuario contaUsuario
    Livro livro

    static constraints = {
        dateEmprestimo nullable: true
        dateDevolucao nullable: true
        dateLimiteDevolucao nullable: true
        contaUsuario nullable: false
        livro nullable: false
        //TODO: Change this, this date should be calculated and nullable false
    }
}
