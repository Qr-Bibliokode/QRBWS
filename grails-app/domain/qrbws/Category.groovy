package qrbws

class Category {

    String description

    static hasMany = [books: Book]

    static constraints = {
        books nullable: true
    }
}
