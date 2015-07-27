package groovy.qrbws.controllers

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Person
import qrbws.Status
import qrbws.UserAccount
import qrbws.UserAccountController
import spock.lang.Specification

@TestFor(UserAccountController)
@Mock([UserAccount, Person, Status])
class UserAccountControllerSpec extends Specification {

    def userAccount, person, status

    def setup() {
        person = new Person(name: 'Person Test', email: 'person@test.com').save()
        status = new Status(description: 'Active').save()
        UserAccount.withNewSession() { session ->
            userAccount = new UserAccount(login: 'login', password: '12345', person: person, status: status).save()
        }
    }

    String makeJson(def value) {
        """{"class":"qrbws.UserAccount","id":1,"login":"${value}","password":"12345","person":{"class":"qrbws.Person","id":1},"status":{"class":"qrbws.Status","id":1}}"""
    }

    String makeJsonList(def value) {
        "[" + makeJson(value) + "]"
    }

    String makeJsonCreate(def value) {
        """{"class":"qrbws.UserAccount","id":null,"login":"${value}","password":null,"person":null,"status":null}"""
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include a userAccount"() {
        given:
        userAccount.save()

        when:
        response.format = 'json'
        controller.index(1)

        then:
        response.status == 200
        response.contentAsString == makeJsonList(userAccount.login)
    }

    void "test update is called after persist"() {
        when:
        userAccount.login = "admin"
        userAccount.save()
        response.format = 'json'
        controller.show(userAccount)

        then:
        response.contentAsString == makeJson(userAccount.login)
    }

    void "test show() return a userAccount when is called"() {
        when:
        response.format = 'json'
        controller.show(userAccount)

        then:
        response.status == 200
        response.contentAsString == makeJson(userAccount.login)
    }

    void "test create() return a userAccount"() {
        when:
        response.format = 'json'
        params.login = "quest"
        controller.create()

        then:
        response.status == 200
        response.contentAsString == makeJsonCreate(params.login)
    }

    void "test save() persist a userAccount"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(userAccount)

        then:
        response.status == 201
        response.contentAsString == makeJson(userAccount.login)
    }

    void "test edit() is called after persist"() {
        when:
        userAccount.login = "foreign"
        response.format = 'json'
        controller.edit(userAccount)

        then:
        response.status == 200
        response.contentAsString == makeJson(userAccount.login)
    }

    void "test delete() is called after persist"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(userAccount)

        then:
        response.status == 204
        response.contentAsString == ''

        when:
        controller.show(userAccount)

        then:
        response.status == 204
    }


    void "test return 'not found' when try delete an inexistent userAccount"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try save an inexistent userAccount"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent userAccount"() {
        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(null)

        then:
        response.status == 404
    }
}