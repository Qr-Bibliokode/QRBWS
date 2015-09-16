package qrbws

import grails.transaction.Transactional

@Transactional
class LendingService {

    def stockService

    Stock lend(Lending lending) {
        // TODO: Verify if the user exceeds the max of lending books
        // TODO: Verify if the user have a book exceed the limit date
        stockService.decreases(lending)
    }

    Lending devolution(Lending lending) {
        lending.dateIn = new Date()
        lending.returned = true
        stockService.increases(lending)
    }

}
