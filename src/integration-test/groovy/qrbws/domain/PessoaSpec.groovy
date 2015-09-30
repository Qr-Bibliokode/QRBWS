package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Pessoa
import spock.lang.Specification

@TestFor(Pessoa)
class PessoaSpec extends Specification {

    Pessoa person

    def setup() {
        person = new Pessoa(nome: "felansu", email: "gaferran@gmail.com")
    }

    void "Test that name must begin with an upper case letter"() {

        when: 'the nome begins with a lower letter'
        person.nome = 'ferran'

        then: 'validation should pass'
        person.validate()

        when: 'the nome begins with an upper case letter'
        person.nome = 'Ferran'

        then: 'validation should pass'
        person.validate()
    }

    void "Test that name don't can have numbers"() {

        when: 'the nome have a number'
        person.nome = 'ferran1989'

        then: 'validation should fail'
        !person.validate()
    }

    void "Test name can not exceed 254 characters"() {

        when: 'nome be 255 characers'
        person.nome = StringUtils.leftPad("", 255, '*')

        then: 'validation should fail'
        !person.validate()

        when: 'nome be 254 characers'
        person.nome = StringUtils.leftPad("", 254, '*')

        then: 'validation should pass'
        person.validate()
    }

    void "Test name can not be blank"() {

        when: 'nome is blank'
        person.nome = ' '

        then: 'validation should fail'
        !person.validate()
    }

    void "Test email can not be blank"() {

        when: 'email is blank'
        person.email = ' '

        then: 'validation should fail'
        !person.validate()
    }

    void "Test email is an email"() {

        when: 'email is not a email'
        person.email = 'reallyImNotAnEmail??'

        then: 'validation should fail'
        !person.validate()
    }


    void "Test email can not exceed 254 characters"() {

        when: 'email be 255 characers'
        person.email = StringUtils.leftPad("", 255, '*')

        then: 'validation should fail'
        !person.validate()

        when: 'email be 254 characers'
        person.email = StringUtils.leftPad("email@email.com", 254, '*')

        then: 'validation should pass'
        person.validate()
    }

    void "Test phone can not exceed 15 characters"() {

        when: 'celular be 16 characers'
        person.celular = StringUtils.leftPad("", 16, '*')

        then: 'validation should fail'
        !person.validate()

        when: 'celular be 15 characers'
        person.celular = StringUtils.leftPad("", 15, '*')

        then: 'validation should pass'
        person.validate()
    }
}
