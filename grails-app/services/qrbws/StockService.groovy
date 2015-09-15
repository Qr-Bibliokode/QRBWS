package qrbws

import grails.transaction.Transactional

@Transactional
class StockService {

    Stock decreases(Lending lending) {
        Stock stock = Stock.findByBook(lending.book)
        stock.availableBalance = stock.availableBalance - 1
        stock.save()
    }
}
