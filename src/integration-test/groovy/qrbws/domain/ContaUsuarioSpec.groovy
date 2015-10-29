package qrbws.domain

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Pessoa
import qrbws.ContaUsuario
import spock.lang.Specification

@TestFor(ContaUsuario)
@Mock([Pessoa])
class ContaUsuarioSpec extends Specification {

    ContaUsuario contaUsuario
    def person, status

    def setup() {
        person = new Pessoa(nome: "felansu", email: "gaferran@gmail.com").save()
        contaUsuario = new ContaUsuario(username: 'teste', password: 'teste', status: status, pessoa: person)
    }

    void "Test that username can't be null or blank"() {

        when: 'the isbn is null'
        contaUsuario.username = null

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the username is blank'
        contaUsuario.username = ' '

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the username is filled'
        contaUsuario.username = 'Teste'

        then: 'validation should pass'
        contaUsuario.validate()
    }

    void "Test username must be between 5 and 20 characters"() {

        when: 'the username have 4 characters'
        contaUsuario.username = 'Test'

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the username have 21 characters'
        contaUsuario.username = '123456789112345678912'

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the username have 8 characters'
        contaUsuario.username = '12345678'

        then: 'validation should pass'
        contaUsuario.validate()
    }

    void "Test that password can't be null or blank"() {

        when: 'the password is null'
        contaUsuario.password = null

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the password is blank'
        contaUsuario.password = ' '

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the password is filled'
        contaUsuario.password = 'password'

        then: 'validation should pass'
        contaUsuario.validate()
    }

    void "Test password must be between 5 and 20 characters"() {

        when: 'the password have 4 characters'
        contaUsuario.password = 'Test'

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the password have 21 characters'
        contaUsuario.password = '123456789112345678912'

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the password have 8 characters'
        contaUsuario.password = '12345678'

        then: 'validation should pass'
        contaUsuario.validate()
    }

    void "Test contaUsuario need a person for save"() {

        when: 'the pessoa is null'
        contaUsuario.pessoa = null

        then: 'validation should fail'
        !contaUsuario.validate()
    }
}
