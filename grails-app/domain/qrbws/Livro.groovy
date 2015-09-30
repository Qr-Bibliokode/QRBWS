package qrbws

import grails.rest.Resource

@Resource(uri = '/api/livro', formats = ['json'])
class Livro {

    String isbn
    String titulo
    String sinopse
    Integer paginas
    Idioma idioma
    Categoria categoria
    // TODO: Implement cover for livro

    static hasMany = [autores: Autor, comentarios: Comentario]

    static constraints = {
        isbn blank: false, nullable: false, maxSize: 17, unique: true, matches: '\\d+'
        titulo blank: false, maxSize: 254, unique: true
        sinopse nullable: true, blank: true, size: 5..5000
        paginas nullable: true, max: 9999, validator: {
            if (it < 0 && it != null) return ['livro.paginas.negative']
        }
        idioma nullable: true
        categoria nullable: true
        comentarios nullable: true
    }
}
