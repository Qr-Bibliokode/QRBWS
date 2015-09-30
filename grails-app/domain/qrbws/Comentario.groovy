package qrbws

import grails.rest.Resource

@Resource(uri = '/api/comentario', formats = ['json'])
class Comentario {

    Integer avaliacao
    String descricao
    Date dateCreated
    Boolean recomendacao

    ContaUsuario contaUsuario

    static constraints = {
        avaliacao nullable: false, max: 5, validator: { if (it < 0) return ['comentario.avaliacao.negativa'] }
        descricao nullable: true, maxSize: 500
        contaUsuario nullable: false
    }

    Comentario() {
        recomendacao = false
        avaliacao = 0
    }
}
