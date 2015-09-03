package qrbws

import grails.rest.Resource

@Resource(uri = '/api/author', formats=['json'])
class Author {

    String name
    String notes

    static belongsTo = Book

    static hasMany = [books:Book]

    static constraints = {
        name blank: false, maxSize: 50, matches: '[^0-9\\-]+'
        notes nullable: true
    }
}
