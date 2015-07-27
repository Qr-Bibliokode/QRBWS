package groovy.qrbws.controllers

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Author
import qrbws.AuthorController
import spock.lang.Specification

@TestFor(AuthorController)
@Mock(Author)
class AuthorControllerSpec extends Specification {

    def author

    def setup() {
        Author.withNewSession() { session ->
            author = new Author(name: 'Author Test', notes: 'Tiroriro').save()
        }
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include an author"() {
        given:
        author.save()

        when:
        response.format = 'json'
        controller.index()

        then:
        response.status == 200
        response.contentAsString == '[{"class":"qrbws.Author","id":1,"name":"Author Test","notes":"Tiroriro"}]'
    }

    void "test update is called after persist"() {
        when:
        author.name = "Author Edited"
        author.save()
        response.format = 'json'
        controller.show(author)

        then:
        response.contentAsString == '{"class":"qrbws.Author","id":1,"name":"Author Edited","notes":"Tiroriro"}'
    }

    void "test show() return an author when is called"() {
        when:
        response.format = 'json'
        controller.show(author)

        then:
        response.status == 200
        response.contentAsString == '{"class":"qrbws.Author","id":1,"name":"Author Test","notes":"Tiroriro"}'
    }

    void "test create() return an author"() {
        when:
        response.format = 'json'
        params.name = "Pepito"
        params.notes = "Azul"
        controller.create()

        then:
        response.status == 200
        response.contentAsString == '{"class":"qrbws.Author","id":null,"name":"Pepito","notes":"Azul"}'
    }

    void "test save() persist an author"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(author)

        then:
        response.status == 201
        response.contentAsString == '{"class":"qrbws.Author","id":1,"name":"Author Test","notes":"Tiroriro"}'
    }

    void "test edit() is called after persist"() {
        when:
        author.name = "Loli Pipokas"
        author.notes = "Nothing else matters"
        response.format = 'json'
        controller.edit(author)

        then:
        response.status == 200
        response.contentAsString == '{"class":"qrbws.Author","id":1,"name":"Loli Pipokas","notes":"Nothing else matters"}'
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
        given:
        def author

        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(author)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent author"() {
        given:
        def author

        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(author)

        then:
        response.status == 404
    }
}