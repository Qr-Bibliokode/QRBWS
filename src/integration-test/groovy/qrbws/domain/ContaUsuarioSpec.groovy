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
        contaUsuario = new ContaUsuario(login: 'teste', senha: 'teste', status: status, pessoa: person)
    }

    void "Test that login can't be null or blank"() {

        when: 'the isbn is null'
        contaUsuario.login = null

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the login is blank'
        contaUsuario.login = ' '

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the login is filled'
        contaUsuario.login = 'Teste'

        then: 'validation should pass'
        contaUsuario.validate()
    }

    void "Test login must be between 5 and 20 characters"() {

        when: 'the login have 4 characters'
        contaUsuario.login = 'Test'

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the login have 21 characters'
        contaUsuario.login = '123456789112345678912'

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the login have 8 characters'
        contaUsuario.login = '12345678'

        then: 'validation should pass'
        contaUsuario.validate()
    }

    void "Test that password can't be null or blank"() {

        when: 'the senha is null'
        contaUsuario.senha = null

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the senha is blank'
        contaUsuario.senha = ' '

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the senha is filled'
        contaUsuario.senha = 'senha'

        then: 'validation should pass'
        contaUsuario.validate()
    }

    void "Test password must be between 5 and 20 characters"() {

        when: 'the senha have 4 characters'
        contaUsuario.senha = 'Test'

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the senha have 21 characters'
        contaUsuario.senha = '123456789112345678912'

        then: 'validation should fail'
        !contaUsuario.validate()

        when: 'the senha have 8 characters'
        contaUsuario.senha = '12345678'

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
