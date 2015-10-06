package qrbws

import grails.transaction.Transactional

@Transactional
class ReservaService {

    int existemReservasAtivasSuperiorADisponivel(Livro livro) {
        Reserva.findAllByLivroAndAtiva(livro, true).size()
    }
}
