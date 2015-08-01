package groovy.qrbws.controllers

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Employee
import qrbws.EmployeeController
import spock.lang.Specification

@TestFor(EmployeeController)
@Mock(Employee)
class EmployeeControllerSpec extends Specification {

    Employee employee

    def setup() {
        Employee.withNewSession() { session ->
            employee = new Employee(code: '12345', name: 'Employee Test', email: 'employee@test.com').save()
        }
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
        Employee employeeResponse = JSON.parse(response.contentAsString)
        employeeResponse.code == employee.code
        employeeResponse.name == employee.name
        employeeResponse.email == employee.email
    }

    void "test update is called after persist"() {
        when:
        employee.code = 83273
        employee.save()
        response.format = 'json'
        controller.show(employee)

        then:
        Employee employeeResponse = JSON.parse(response.contentAsString)
        employeeResponse.code == employee.code
        employeeResponse.name == employee.name
        employeeResponse.email == employee.email
    }

    void "test show() return a employee when is called"() {
        when:
        response.format = 'json'
        controller.show(employee)

        then:
        response.status == 200
        Employee employeeResponse = JSON.parse(response.contentAsString)
        employeeResponse.code == employee.code
        employeeResponse.name == employee.name
        employeeResponse.email == employee.email
    }

    void "test create() return a employee"() {
        when:
        response.format = 'json'
        params.code = "65432"
        controller.create()

        then:
        response.status == 200
        Employee employeeResponse = JSON.parse(response.contentAsString)
        employeeResponse.code == params.code
        employeeResponse.name == params.name
        employeeResponse.email == params.email
    }

    void "test save() persist a employee"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(employee)

        then:
        response.status == 201
        Employee employeeResponse = JSON.parse(response.contentAsString)
        employeeResponse.code == employee.code
        employeeResponse.name == employee.name
        employeeResponse.email == employee.email
    }

    void "test edit() is called after persist"() {
        when:
        employee.code = "123123"
        response.format = 'json'
        controller.edit(employee)

        then:
        response.status == 200
        Employee employeeResponse = JSON.parse(response.contentAsString)
        employeeResponse.code == employee.code
        employeeResponse.name == employee.name
        employeeResponse.email == employee.email
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