package groovy.qrbws.controllers

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Employee
import qrbws.EmployeeController
import spock.lang.Specification

@TestFor(EmployeeController)
@Mock(Employee)
class EmployeeControllerSpec extends Specification {

    def employee

    def setup() {
        Employee.withNewSession() { session ->
            employee = new Employee(code: '12345', name: 'Employee Test', email: 'employee@test.com').save()
        }
    }

    String makeJson(def value) {
        """{"class":"qrbws.Employee","id":1,"code":"${value}","email":"employee@test.com","name":"Employee Test","phone":null}"""
    }

    String makeJsonList(def value) {
        "[" + makeJson(value) + "]"
    }

    String makeJsonCreate(def value) {
        """{"class":"qrbws.Employee","id":null,"code":"${value}","email":null,"name":null,"phone":null}"""
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include a employee"() {
        given:
        employee.save()

        when:
        response.format = 'json'
        controller.index(1)

        then:
        response.status == 200
        response.contentAsString == makeJsonList(employee.code)
    }

    void "test update is called after persist"() {
        when:
        employee.code = 83273
        employee.save()
        response.format = 'json'
        controller.show(employee)

        then:
        response.contentAsString == makeJson(employee.code)
    }

    void "test show() return a employee when is called"() {
        when:
        response.format = 'json'
        controller.show(employee)

        then:
        response.status == 200
        response.contentAsString == makeJson(employee.code)
    }

    void "test create() return a employee"() {
        when:
        response.format = 'json'
        params.code = 65432
        controller.create()

        then:
        response.status == 200
        response.contentAsString == makeJsonCreate(params.code)
    }

    void "test save() persist a employee"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(employee)

        then:
        response.status == 201
        response.contentAsString == makeJson(employee.code)
    }

    void "test edit() is called after persist"() {
        when:
        employee.code = "123123"
        response.format = 'json'
        controller.edit(employee)

        then:
        response.status == 200
        response.contentAsString == makeJson(employee.code)
    }

    void "test delete() is called after persist"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(employee)

        then:
        response.status == 204
        response.contentAsString == ''

        when:
        controller.show(employee)

        then:
        response.status == 204
    }


    void "test return 'not found' when try delete an inexistent employee"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try save an inexistent employee"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent employee"() {
        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(null)

        then:
        response.status == 404
    }
}