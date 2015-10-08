package qrbws

class Emprestimo {

    Date dataEmprestimo
    Date dataDevolucao
    Date dataLimiteDevolucao
    boolean devolvido
    boolean avisado
    Integer renovacoes

    ContaUsuario contaUsuario
    Livro livro

    static constraints = {
        dataEmprestimo nullable: true
        dataDevolucao nullable: true
        dataLimiteDevolucao nullable: true
        renovacoes nullable: true, min: 0
    }

    public Emprestimo(){
        renovacoes = 0
    }
}
