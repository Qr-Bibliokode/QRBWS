package groovy.qrbws

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.*
import spock.lang.Specification

@TestFor(CommentController)
@Mock([Comment, Book, UserAccount, Person, Status])
class CommentControllerSpec extends Specification {

    def comment, book, user, person, status
    def json, jsonCreate

    def setup() {
        Comment.withNewSession() { session ->
            book = new Book(title: 'Book Test', isbn: '123').save()
            person = new Person(name: 'Person Name', email: 'person@email.com').save()
            status = new Status(description: 'Ativo').save()
            user = new UserAccount(login: 'User Login', password: '12345', person: person, status: status).save()
            comment = new Comment(description: 'Comment Test', book: book, userAccount: user, avaliation: 2).save()
        }
    }

    void makeJson(def value) {
        json = """{"class":"qrbws.Comment","id":1,"avaliation":2,"book":{"class":"qrbws.Book","id":1},"dateCreated":null,"description":"${value}","recommendation":false,"userAccount":{"class":"qrbws.UserAccount","id":1}}"""
    }

    void makeJsonList(def value) {
        makeJson(value)
        json = "[" + json + "]"
    }

    void makeJsonCreate(def value) {
        jsonCreate = """{"class":"qrbws.Comment","id":null,"avaliation":null,"book":null,"dateCreated":null,"description":"${value}","recommendation":false,"userAccount":null}"""
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include a comment"() {
        given:
        comment.save()

        when:
        response.format = 'json'
        controller.index(1)

        then:
        response.status == 200
        makeJsonList(comment.description)
        response.contentAsString == json
    }

    void "test update is called after persist"() {
        when:
        comment.description = "Comment Edited"
        comment.save()
        response.format = 'json'
        controller.show(comment)

        then:
        makeJson(comment.description)
        response.contentAsString == json
    }

    void "test show() return a comment when is called"() {
        when:
        response.format = 'json'
        controller.show(comment)

        then:
        response.status == 200
        makeJson(comment.description)
        response.contentAsString == json
    }

    void "test create() return a comment"() {
        when:
        response.format = 'json'
        params.description = "Comment Created"
        controller.create()

        then:
        response.status == 200
        makeJsonCreate(params.description)
        response.contentAsString == jsonCreate
    }

    void "test save() persist a comment"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(comment)

        then:
        response.status == 201
        makeJson(comment.description)
        response.contentAsString == json
    }

    void "test edit() is called after persist"() {
        when:
        comment.description = "Other Comment"
        response.format = 'json'
        controller.edit(comment)

        then:
        response.status == 200
        makeJson(comment.description)
        response.contentAsString == json
    }

    void "test delete() is called after persist"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(comment)

        then:
        response.status == 204
        response.contentAsString == ''

        when:
        controller.show(comment)

        then:
        response.status == 204
    }


    void "test return 'not found' when try delete an inexistent comment"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try save an inexistent comment"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent comment"() {
        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(null)

        then:
        response.status == 404
    }
}