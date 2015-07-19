package qrbws

import grails.rest.Resource

@Resource(uri='/book')
class Book {

    String isbn
    String title
    String synopsis
    Integer pages
    // TODO Waiting for implement
    // Clob cover

    Language language
    Status status
    Category category

    static hasMany = [authors: Author, comments: Comment]

    static constraints = {
        isbn blank: false, maxSize: 17, unique: true
        title blank: false, maxSize: 254, unique: true
        synopsis nullable: true, size: 5..5000
        pages nullable: true, maxSize: 5, validator: {
            if (it < 0 && it != null) return ['book.pages.negative']
        }
        language nullable: true
        status nullable: true
        category nullable: true
        // cover nullable: true
    }
}
