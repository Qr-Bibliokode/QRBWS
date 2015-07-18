package qrbws

class Person {

    String name
    String email
    String phone

    static constraints = {
        name blank: false, maxSize: 254, matches: "[a-zA-Z]+"
        email blank: false, email: true, maxSize: 254
        phone nullable: true, maxSize: 254
    }
}
