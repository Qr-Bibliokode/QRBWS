package qrbws

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(AuthorController)
@Mock(Author)
class AuthorControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include an author"() {
        given:
        new Author(name: 'Author Good').save()

        when:
        request.method = 'GET'
        response.format = 'json'
        controller.index()

        then:
        response.status == 200
        response.contentAsString == '[{"class":"qrbws.Author","id":1,"name":"Author Good","notes":null}]'
    }

    void "test gorm hook is called in session Context"() {
        given:
        def author
        Author.withNewSession() { session ->
            author = new Author(name: 'Author Lylo', notes: 'Best author of Tests').save()
        }

        expect:
        assert author.name == 'Author Lylo'
        assert author.notes == 'Best author of Tests'
    }

    void "test update() is called after persist"() {
        given:
        def author
        Author.withNewSession() { session ->
            author = new Author(name: 'Author Puggy', notes: "The second best author of Tests").save()
        }

        when:
        author.name = "Fake author"
        author.save()

        then:
        assert author.name == 'Fake author'
        assert author.notes == 'The second best author of Tests'
    }

    void "test show() return an author when is called"() {
        given:
        def author
        Author.withNewSession() { session ->
            author = new Author(name: 'Author James').save()
        }

        when:
        request.method = 'GET'
        response.format = 'json'
        controller.show(author)

        then:
        response.status == 200
        response.contentAsString == '{"class":"qrbws.Author","id":1,"name":"Author James","notes":null}'
    }

    void "test create() return an author"() {
        when:
        request.method = 'GET'
        response.format = 'json'
        params.name = "Pepito"
        params.notes = "Azul"
        controller.create()

        then:
        response.status == 200
        response.contentAsString == '{"class":"qrbws.Author","id":null,"name":"Pepito","notes":"Azul"}'
    }

    void "test save() persist an author"() {
        given:
        def author
        Author.withNewSession() { session ->
            author = new Author(name: 'Author Trufa', notes: 'Tiroriro')
        }

        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(author)

        then:
        response.status == 201
        response.contentAsString == '{"class":"qrbws.Author","id":1,"name":"Author Trufa","notes":"Tiroriro"}'
    }

    void "test edit() is called after persist"() {
        given:
        def author
        Author.withNewSession() { session ->
            author = new Author(name: 'Author Santo Pai').save()
        }

        when:
        author.name = "Loli Pipokas"
        author.notes = "Nothing else matters"
        request.method = 'GET'
        response.format = 'json'
        controller.edit(author)

        then:
        response.status == 200
        response.contentAsString == '{"class":"qrbws.Author","id":1,"name":"Loli Pipokas","notes":"Nothing else matters"}'
    }

    void "test delete() is called after persist"() {
        given:
        def author
        Author.withNewSession() { session ->
            author = new Author(name: 'Author Santo Pai').save()
        }

        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(author)

        then:
        response.status == 204
        response.contentAsString == ''

        when:
        controller.show(author) == null

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