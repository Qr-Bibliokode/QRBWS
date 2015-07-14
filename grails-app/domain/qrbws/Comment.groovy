package qrbws

class Comment {

    Integer avaliation
    String description
    Date dateCreated
    Boolean recommendation

    User user
    Book book

    static constraints = {

    }

    Comment() {
        recommendation = false
    }
}
