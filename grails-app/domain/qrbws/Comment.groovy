package qrbws

import grails.rest.Resource

@Resource(uri = '/comment')
class Comment {

    Integer avaliation
    String description
    Date dateCreated
    Boolean recommendation

    UserAccount userAccount
    Book book

    static constraints = {
        avaliation maxSize: 5, validator: { if (it < 0) return ['comment.avaliation.negative'] }
        description maxSize: 500
        avaliation nullable: true
    }

    Comment() {
        recommendation = false
    }
}
