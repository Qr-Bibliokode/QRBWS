package qrbws

class Author {

    String name
    String references

    static hasMany = [books: Book]

    static belongsTo = Book

    static constraints = {
        references nullable: true
    }
}
