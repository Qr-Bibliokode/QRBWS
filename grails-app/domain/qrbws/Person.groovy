package qrbws

class Person {

    String name
    String email
    String phone

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
        name blank: false, maxSize: 254, matches: '[^0-9\\-]+'
        email blank: false, email: true, maxSize: 254
        phone nullable: true, maxSize: 15
    }
}