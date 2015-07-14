package qrbws

class Book {

    String isbn
    String title
    String synopsis
    Integer pages
    Byte[] cover

    Language language
    Status status
    Category category

    static hasMany = [authors: Author, comments: Comment]


    static constraints = {
        isbn blank: false, size: 10..15, unique: true
        title blank: false, size: 5..450, unique: true
        synopsis nullable: true, size: 5..5000
        pages nullable: true, size: 1..5
        cover nullable: true
    }
}
