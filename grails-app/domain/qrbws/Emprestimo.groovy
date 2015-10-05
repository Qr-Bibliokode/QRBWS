package qrbws

class Emprestimo {

    Date dataEmprestimo
    Date dataDevolucao
    Date dataLimiteDevolucao
    boolean devolvido
    boolean avisado

    ContaUsuario contaUsuario
    Livro livro

    static constraints = {
        dataEmprestimo nullable: true
        dataDevolucao nullable: true
        dataLimiteDevolucao nullable: true
    }
}
