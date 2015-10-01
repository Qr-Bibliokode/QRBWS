package qrbws.domain

import grails.test.mixin.TestFor
import qrbws.Funcionario
import spock.lang.Specification

@TestFor(Funcionario)
class FuncionarioSpec extends Specification {

    Funcionario employee

    def setup() {
        employee = new Funcionario(nome: "Aparecida", email: "apare@cida.com")
    }

    void "Test code can't be null"() {

        when: 'value is null'
        employee.codigo = null

        then: 'validation should fail'
        !employee.validate()

        when: 'value is 12345'
        employee.codigo = "12345"

        then: 'validation should pass'
        employee.validate()
    }

    void "Test code must be unique"() {

        when: 'save a employe with 123 codigo'
        new Funcionario(nome: "Test", email: "test@test.com", codigo: 123).save(flush: true)
        employee.codigo = "123"

        then: 'validation should fail'
        !employee.validate()

        when: 'value is 1234'
        employee.codigo = "1234"

        then: 'validation should pass'
        employee.validate()
    }

    void "Test code can't be blank"() {

        when: 'codigo is blank'
        employee.codigo = " "

        then: 'validation should fail'
        !employee.validate()
    }

    void "Test code can not exceed 5 characters"() {

        when: 'codigo have 6 characters'
        employee.codigo = "123456"

        then: 'validation should fail'
        !employee.validate()
    }
}
