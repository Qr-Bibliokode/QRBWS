package qrbws

import grails.converters.JSON
import grails.rest.Resource

@Resource(uri = '/api/comentario', formats = ['json'])
class Comentario {

    Integer avaliacao
    String descricao
    Date dateCreated
    Boolean recomendacao

    ContaUsuario contaUsuario

    static hasOne = [livro: Livro]

    static constraints = {
        avaliacao nullable: false, max: 5, validator: { if (it < 0) return ['comentario.avaliacao.negativa'] }
        descricao nullable: true, maxSize: 500
        contaUsuario nullable: false
    }

    Comentario() {
        recomendacao = false
        avaliacao = 0
    }

    static void marshaller() {
        JSON.registerObjectMarshaller(Comentario) {
            [
                    'id'          : it.id,
                    'avaliacao'   : it.avaliacao,
                    'descricao'   : it.descricao,
                    'dateCreated' : it.dateCreated,
                    'recomendacao': it.recomendacao,
                    'contaUsuario': it.contaUsuario,
                    'livro'       : it.livro
            ]
        }
    }
}
