package qrbws

import grails.rest.Resource

@Resource(uri = '/api/lending', formats=['json'])
class Lending {

    UserAccount userAccount
    Book book
    Date dateOut
    Date dateIn
    boolean returned
    boolean avised

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
