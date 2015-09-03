package qrbws

import grails.rest.Resource

@Resource(uri = '/api/lending', formats = ['json'])
class Lending {

    Date dateOut
    Date dateIn
    boolean returned
    boolean avised

    UserAccount userAccount
    Book book

    static constraints = {
        userAccount nullable: false
        book nullable: false
    }

    def afterInsert() {
        Stock stock = Stock.findByBook(book)
        stock.availableBalance = stock.availableBalance--
        stock.save()
    }
}
