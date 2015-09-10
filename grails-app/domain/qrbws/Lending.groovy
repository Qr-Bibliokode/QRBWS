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

    Lending() {
        this.dateIn = new Date()
    }
    static constraints = {
        userAccount nullable: false
        book nullable: false
        dateOut nullable: true
    }

    def afterInsert() {
        Stock stock = Stock.findByBook(book)
        stock.availableBalance = stock.availableBalance--
        stock.save()
    }
}
