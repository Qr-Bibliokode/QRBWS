package qrbws

import grails.transaction.Transactional

@Transactional
class StockService {

    Stock desconta(Emprestimo emprestimo) {
        Stock stock = Stock.findByLivro(emprestimo.livro)
        stock.disponivel = stock.disponivel - 1
        stock.save()
    }

    Stock incrementa(Emprestimo emprestimo) {
        Stock stock = Stock.findByLivro(emprestimo.livro)
        stock.disponivel = stock.disponivel + 1
        stock.save()
    }
}
