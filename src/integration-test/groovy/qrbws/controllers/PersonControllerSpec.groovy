package groovy.qrbws.controllers

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Person
import qrbws.PersonController
import spock.lang.Specification

@TestFor(PersonController)
@Mock(Person)
class PersonControllerSpec extends Specification {

    def person

    def setup() {
        Person.withNewSession() { session ->
            person = new Person(name: 'Person Test', email: 'person@test.com').save()
        }
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include a person"() {
        given:
        person.save()

        when:
        response.format = 'json'
        controller.index(1)

        then:
        response.status == 200
        Person personResponse = JSON.parse(response.contentAsString)
        personResponse.name == person.name
        personResponse.email == person.email
    }

    void "test update is called after persist"() {
        when:
        person.name = 'Person Updated'
        person.save()
        response.format = 'json'
        controller.show(person)

        then:
        Person personResponse = JSON.parse(response.contentAsString)
        personResponse.name == person.name
        personResponse.email == person.email
    }

    void "test show() return a person when is called"() {
        when:
        response.format = 'json'
        controller.show(person)

        then:
        response.status == 200
        Person personResponse = JSON.parse(response.contentAsString)
        personResponse.name == person.name
        personResponse.email == person.email
    }

    void "test create() return a person"() {
        when:
        response.format = 'json'
        params.name = 'Person Created'
        controller.create()

        then:
        response.status == 200
        Person personResponse = JSON.parse(response.contentAsString)
        personResponse.name == params.name
        personResponse.email == null
    }

    void "test save() persist a person"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(person)

        then:
        response.status == 201
        Person personResponse = JSON.parse(response.contentAsString)
        personResponse.name == person.name
        personResponse.email == person.email
    }

    void "test edit() is called after persist"() {
        when:
        person.name = "Person Edited"
        response.format = 'json'
        controller.edit(person)

        then:
        response.status == 200
        Person personResponse = JSON.parse(response.contentAsString)
        personResponse.name == person.name
        personResponse.email == person.email
    }

    void "test delete() is called after persist"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(person)

        then:
        response.status == 204
        response.contentAsString == ''

        when:
        controller.show(person)

        then:
        response.status == 204
    }


    void "test return 'not found' when try delete an inexistent person"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try save an inexistent person"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent person"() {
        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(null)

        then:
        response.status == 404
    }
}