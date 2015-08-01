package groovy.qrbws.controllers

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Idiom
import qrbws.IdiomController
import spock.lang.Specification

@TestFor(IdiomController)
@Mock(Idiom)
class IdiomControllerSpec extends Specification {

    Idiom idiom

    def setup() {
        Idiom.withNewSession() { session ->
            idiom = new Idiom(description: 'Spanish').save()
        }
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include a idiom"() {
        given:
        idiom.save()

        when:
        response.format = 'json'
        controller.index(1)

        then:
        response.status == 200
        Idiom idiomResponse = JSON.parse(response.contentAsString)
        idiomResponse.description == idiom.description
    }

    void "test update is called after persist"() {
        when:
        idiom.description = 'Portuguese'
        idiom.save()
        response.format = 'json'
        controller.show(idiom)

        then:
        Idiom idiomResponse = JSON.parse(response.contentAsString)
        idiomResponse.description == idiom.description
    }

    void "test show() return a idiom when is called"() {
        when:
        response.format = 'json'
        controller.show(idiom)

        then:
        response.status == 200
        Idiom idiomResponse = JSON.parse(response.contentAsString)
        idiomResponse.description == idiom.description
    }

    void "test create() return a idiom"() {
        when:
        response.format = 'json'
        params.description = 'Korean'
        controller.create()

        then:
        response.status == 200
        Idiom idiomResponse = JSON.parse(response.contentAsString)
        idiomResponse.description == params.description
    }

    void "test save() persist a idiom"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(idiom)

        then:
        response.status == 201
        Idiom idiomResponse = JSON.parse(response.contentAsString)
        idiomResponse.description == idiom.description
    }

    void "test edit() is called after persist"() {
        when:
        idiom.description = "French"
        response.format = 'json'
        controller.edit(idiom)

        then:
        response.status == 200
        Idiom idiomResponse = JSON.parse(response.contentAsString)
        idiomResponse.description == idiom.description
    }

    void "test delete() is called after persist"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(idiom)

        then:
        response.status == 204
        response.contentAsString == ''

        when:
        controller.show(idiom)

        then:
        response.status == 204
    }


    void "test return 'not found' when try delete an inexistent idiom"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try save an inexistent idiom"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent idiom"() {
        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(null)

        then:
        response.status == 404
    }
}