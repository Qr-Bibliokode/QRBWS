package groovy.qrbws.controllers

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Author
import qrbws.AuthorController
import spock.lang.Specification

@TestFor(AuthorController)
@Mock(Author)
class AuthorControllerSpec extends Specification {

    Author author

    def setup() {
        author = new Author(name: 'Author Test', notes: 'Tiroriro').save()
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include an author"() {

        when:
        response.format = 'json'
        controller.index()

        then:
        response.status == 200
        Author authorResponse = JSON.parse(response.contentAsString)

        authorResponse.name == author.name
        authorResponse.notes == author.notes
    }

    void "test update is called after persist"() {

        when:
        author.name = "Author Edited"
        author.save()
        response.format = 'json'
        controller.show(author)

        then:
        Author authorResponse = JSON.parse(response.contentAsString)

        authorResponse.name == author.name
        authorResponse.notes == author.notes
    }

    void "test show() return an author when is called"() {

        when:
        response.format = 'json'
        controller.show(author)

        then:
        response.status == 200
        Author authorResponse = JSON.parse(response.contentAsString)

        authorResponse.name == author.name
        authorResponse.notes == author.notes
    }

    void "test create() return an author"() {

        when:
        response.format = 'json'
        params.name = "Pepito"
        params.notes = "Azul"
        controller.create()

        then:
        response.status == 200
        Author authorResponse = JSON.parse(response.contentAsString)

        authorResponse.name == params.name
        authorResponse.notes == params.notes
    }

    void "test save() persist an author"() {

        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(author)

        then:
        response.status == 201
        Author authorResponse = JSON.parse(response.contentAsString)

        authorResponse.name == author.name
        authorResponse.notes == author.notes
    }

    void "test edit() is called after persist"() {

        when:
        author.name = "Loli Pipokas"
        author.notes = "Nothing else matters"
        response.format = 'json'
        controller.edit(author)

        then:
        response.status == 200
        Author authorResponse = JSON.parse(response.contentAsString)

        authorResponse.name == author.name
        authorResponse.notes == author.notes
    }

    void "test delete() is called after persist"() {

        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(author)

        then:
        response.status == 204
        response.contentAsString == ''

        when:
        controller.show(author)

        then:
        response.status == 204
    }


    void "test return 'not found' when try delete an inexistent author"() {

        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete()

        then:
        response.status == 404
    }

    void "test return 'not found' when try save an inexistent author"() {

        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent author"() {

        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(null)

        then:
        response.status == 404
    }
}