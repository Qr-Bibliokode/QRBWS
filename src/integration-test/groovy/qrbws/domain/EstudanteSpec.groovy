package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Estudante
import spock.lang.Specification

@TestFor(Estudante)
class EstudanteSpec extends Specification {

    Estudante student

    def setup() {
        student = new Estudante(nome: 'Ronaldinho', email: 'ronaldinho@gmail.com')
    }

    void "Test enrollment can't be null"() {

        when: 'matricula be null'
        student.matricula = null

        then: 'validation should fail'
        !student.validate()

        when: 'matricula be filled'
        student.matricula = '15487'

        then: 'validation should pass'
        student.validate()
    }

    void "Test enrollment can't be blank"() {

        when: 'matricula be blank'
        student.matricula = ' '

        then: 'validation should fail'
        !student.validate()
    }

    void "Test enrollment can not exceed 5 characters"() {

        when: 'matricula be 6 characers'
        student.matricula = StringUtils.leftPad("", 6, '*')

        then: 'validation should fail'
        !student.validate()

        when: 'matricula be 5 characers'
        student.matricula = StringUtils.leftPad("", 5, '*')

        then: 'validation should pass'
        student.validate()
    }

    void "Test enrollment must be unique"() {

        when: 'matricula is duplicated'
        new Estudante(nome: 'test', email: 'test@gmail.com', matricula: '12312').save(flush: true)
        student.matricula = '12312'

        then: 'validation should fail'
        !student.validate()
    }

}
