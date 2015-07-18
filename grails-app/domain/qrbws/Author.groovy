package qrbws

class Author {

    String name
    String references

    static constraints = {
        references nullable: true
    }
}
