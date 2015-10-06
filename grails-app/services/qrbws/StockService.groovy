package qrbws

import grails.transaction.Transactional

@Transactional
class StockService {

    Stock desconta(Livro livro) {
        Stock stock = Stock.findByLivro(livro)
        stock.disponivel = stock.disponivel - 1
        stock.save()
    }

    Stock incrementa(Livro livro) {
        Stock stock = Stock.findByLivro(livro)
        stock.disponivel = stock.disponivel + 1
        stock.save()
    }

    boolean temStock(Livro livro) {
        Stock.findByLivro(livro).disponivel
    }
}
