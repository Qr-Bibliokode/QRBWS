package qrbws

class Pessoa {

    String nome
    String email
    String celular

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
        nome blank: false, maxSize: 254, matches: '[^0-9\\-]+'
        email blank: false, email: true, maxSize: 254
        celular nullable: true, maxSize: 15
    }
}