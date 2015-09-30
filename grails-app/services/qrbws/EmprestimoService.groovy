package qrbws

import grails.transaction.Transactional

@Transactional
class EmprestimoService {

    def stockService

    Stock emprestar(Emprestimo lending) {
        // TODO: Verify if have stock
        // TODO: Verify if the user exceeds the max of lending books
        // TODO: Verify if the user have a book exceed the limit date
        stockService.decreases(lending)
    }

    Emprestimo devolution(Emprestimo lending) {
        lending.dateDevolucao = new Date()
        lending.returned = true
        stockService.increases(lending)
    }

}
