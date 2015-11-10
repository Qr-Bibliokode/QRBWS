package qrbws

import grails.converters.JSON
import grails.rest.Resource

@Resource(uri = '/api/livro', formats = ['json'])
class Livro {

    String isbn
    String titulo
    String sinopse
    Integer paginas
    Idioma idioma
    Categoria categoria
    // TODO: Implementar capa do livro

    static hasOne = [estoque: Estoque]

    static hasMany = [autores: Autor, comentarios: Comentario]

    static constraints = {
        isbn blank: false, nullable: false, maxSize: 17, unique: true, matches: '\\d+'
        titulo blank: false, maxSize: 254, unique: true
        sinopse nullable: true, blank: true, size: 5..5000
        paginas nullable: true, max: 9999, validator: {
            if (it < 0 && it != null) return ['livro.paginas.negativo']
        }
        idioma nullable: true
        categoria nullable: true
        comentarios nullable: true
    }

    static mapping = {
        autores cascade: 'none'
    }

    static void marshaller() {
        JSON.registerObjectMarshaller(Livro) {
            [
                    'id'         : it.id,
                    'idioma'     : it.idioma,
                    'isbn'       : it.isbn,
                    'titulo'     : it.titulo,
                    'sinopse'    : it.sinopse,
                    'paginas'    : it.paginas,
                    'categoria'  : it.categoria,
                    'autores'    : it.autores,
                    'comentarios': it.comentarios,
                    'estoque'      : it.estoque
            ]
        }
    }
}
