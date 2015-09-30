package qrbws

import grails.transaction.Transactional

@Transactional
class StockService {

    Stock decreases(Emprestimo lending) {
        Stock stock = Stock.findByBook(lending.book)
        stock.disponivel = stock.disponivel - 1
        stock.save()
    }

    Stock increases(Emprestimo lending) {
        Stock stock = Stock.findByBook(lending.book)
        stock.disponivel = stock.disponivel + 1
        stock.save()
    }
}
