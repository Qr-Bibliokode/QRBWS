package qrbws

class Person {

    String name
    String email
    String phone

    static constraints = {
        name size: 5..50, blank: false
        email email: true, blank: false
        phone nullable: true
    }
}
