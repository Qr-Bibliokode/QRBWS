package groovy.qrbws.controllers

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Status
import qrbws.StatusController
import spock.lang.Specification

@TestFor(StatusController)
@Mock(Status)
class StatusControllerSpec extends Specification {

    def status

    def setup() {
        Status.withNewSession() { session ->
            status = new Status(description: 'Active').save()
        }
    }

    String makeJson(def value) {
        """{"class":"qrbws.Status","id":1,"description":"${value}"}"""
    }

    String makeJsonList(def value) {
        "[" + makeJson(value) + "]"
    }

    String makeJsonCreate(def value) {
        """{"class":"qrbws.Status","id":null,"description":"${value}"}"""
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include a status"() {
        given:
        status.save()

        when:
        response.format = 'json'
        controller.index(1)

        then:
        response.status == 200
        response.contentAsString == makeJsonList(status.description)
    }

    void "test update is called after persist"() {
        when:
        status.description = 'Updating...'
        status.save()
        response.format = 'json'
        controller.show(status)

        then:
        response.contentAsString == makeJson(status.description)
    }

    void "test show() return a status when is called"() {
        when:
        response.format = 'json'
        controller.show(status)

        then:
        response.status == 200
        response.contentAsString == makeJson(status.description)
    }

    void "test create() return a status"() {
        when:
        response.format = 'json'
        params.description = 'Status Created'
        controller.create()

        then:
        response.status == 200
        response.contentAsString == makeJsonCreate(params.description)
    }

    void "test save() persist a status"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(status)

        then:
        response.status == 201
        response.contentAsString == makeJson(status.description)
    }

    void "test edit() is called after persist"() {
        when:
        status.description = "Editing..."
        response.format = 'json'
        controller.edit(status)

        then:
        response.status == 200
        response.contentAsString == makeJson(status.description)
    }

    void "test delete() is called after persist"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(status)

        then:
        response.status == 204
        response.contentAsString == ''

        when:
        controller.show(status)

        then:
        response.status == 204
    }


    void "test return 'not found' when try delete an inexistent status"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try save an inexistent status"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent status"() {
        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(null)

        then:
        response.status == 404
    }
}