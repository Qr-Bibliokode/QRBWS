package qrbws

import grails.transaction.Transactional

@Transactional
class EmprestimoService {

    def stockService

    Stock emprestar(Emprestimo emprestimo) {
        // TODO: Verificar se o usuário tem algum livro estorando o limite de devolução
        // TODO: Verificar se o usuário excede o limite de livros emprestados
        // TODO: Verificar se tem stock
        stockService.desconta(emprestimo)
    }

    Emprestimo devolution(Emprestimo emprestimo) {
        emprestimo.dateDevolucao = new Date()
        emprestimo.devolvido = true
        stockService.incrementa(emprestimo)
    }

}
