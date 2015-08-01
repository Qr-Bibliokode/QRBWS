package groovy.qrbws.domain

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.*
import spock.lang.Specification

@TestFor(Comment)
@Mock([Book, UserAccount, Person, Status])
class CommentSpec extends Specification {

    Comment comment
    def book, user, person, status

    def setup() {
        Comment.withNewSession() { session ->
            book = new Book(title: 'Book Test', isbn: '123').save()
            person = new Person(name: 'Person Name', email: 'person@email.com').save()
            status = new Status(description: 'Ativo').save()
            user = new UserAccount(login: 'User Login', password: '12345', person: person, status: status).save()
            comment = new Comment(description: 'Comment Test', book: book, userAccount: user, avaliation: 2)
        }
    }

    void "Test avaliation max value is 5"() {

        when: 'value is 6'
        comment.avaliation = 6

        then: 'validation should fail'
        !comment.validate()

        when: 'value is negative'
        comment.avaliation = -1

        then: 'validation should fail'
        !comment.validate()

        when: 'value is 3'
        comment.avaliation = 3

        then: 'validation should pass'
        comment.validate()
    }

    void "Test avaliation can be 0"() {

        when: 'value is null'
        comment.avaliation = 0

        then: 'validation should pass'
        comment.validate()
    }

    void "Test description can't be null"() {

        when: 'value is null'
        comment.description = null

        then: 'validation should fail'
        !comment.validate()
    }

    void "Test description max value is 500"() {

        when: 'value is 501'
        comment.description = StringUtils.leftPad("*", 501, '*')

        then: 'validation should fail'
        !comment.validate()

        when: 'value is 500'
        comment.description = StringUtils.leftPad("*", 500, '*')

        then: 'validation should pass'
        comment.validate()
    }

    void "Test recommendation is false by default"() {

        when: 'save an comment without recommendation'
        comment.save()

        then: 'validation should pass'
        !Comment.find(comment).recommendation
    }

    void "Test comment need an UserAccount for save"() {

        when: 'save an comment without userAccount'
        comment.userAccount = null

        then: 'validation should fail'
        !comment.validate()

        when: 'save an comment whit userAccount'
        comment.userAccount = user

        then: 'validation should pass'
        comment.validate()
    }

    void "Test comment need a book for save"() {

        when: 'save an comment without book'
        comment.book = null

        then: 'validation should fail'
        !comment.validate()

        when: 'save an comment whit book'
        comment.book = book

        then: 'validation should pass'
        comment.validate()
    }
}
