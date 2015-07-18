package qrbws

class Language {

    String description

    static constraints = {
        description blank: false, maxSize: 45
    }
}
