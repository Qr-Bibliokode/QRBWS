package qrbws

import grails.transaction.Transactional

@Transactional
class LendingService {

    def stockService

    Stock stockDecreases(Lending lending) {
        stockService.decreases(lending)
    }
}
