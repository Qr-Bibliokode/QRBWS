package qrbws.domain

import grails.test.mixin.TestFor
import qrbws.Employee
import spock.lang.Specification

@TestFor(Employee)
class EmployeeSpec extends Specification {

    Employee employee

    def setup() {
        employee = new Employee(name: "Aparecida", email: "apare@cida.com")
    }

    void "Test code can't be null"() {

        when: 'value is null'
        employee.code = null

        then: 'validation should fail'
        !employee.validate()

        when: 'value is 12345'
        employee.code = "12345"

        then: 'validation should pass'
        employee.validate()
    }

    void "Test code must be unique"() {

        when: 'save a employe with 123 code'
        new Employee(name: "Test", email: "test@test.com", code: 123).save(flush: true)
        employee.code = "123"

        then: 'validation should fail'
        !employee.validate()

        when: 'value is 1234'
        employee.code = "1234"

        then: 'validation should pass'
        employee.validate()
    }

    void "Test code can't be blank"() {

        when: 'code is blank'
        employee.code = " "

        then: 'validation should fail'
        !employee.validate()
    }

    void "Test code can not exceed 5 characters"() {

        when: 'code have 6 characters'
        employee.code = "123456"

        then: 'validation should fail'
        !employee.validate()
    }
}
