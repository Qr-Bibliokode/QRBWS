package qrbws.domain

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Pessoa
import qrbws.ContaUsuario
import spock.lang.Specification

@TestFor(ContaUsuario)
@Mock([Pessoa])
class ContaUsuarioSpec extends Specification {

    ContaUsuario userAccount
    def person, status

    def setup() {
        person = new Pessoa(nome: "felansu", email: "gaferran@gmail.com").save()
        userAccount = new ContaUsuario(login: 'teste', senha: 'teste', status: status, pessoa: person)
    }

    void "Test that login can't be null or blank"() {

        when: 'the isbn is null'
        userAccount.login = null

        then: 'validation should fail'
        !userAccount.validate()

        when: 'the login is blank'
        userAccount.login = ' '

        then: 'validation should fail'
        !userAccount.validate()

        when: 'the login is filled'
        userAccount.login = 'Teste'

        then: 'validation should pass'
        userAccount.validate()
    }

    void "Test login must be between 5 and 20 characters"() {

        when: 'the login have 4 characters'
        userAccount.login = 'Test'

        then: 'validation should fail'
        !userAccount.validate()

        when: 'the login have 21 characters'
        userAccount.login = '123456789112345678912'

        then: 'validation should fail'
        !userAccount.validate()

        when: 'the login have 8 characters'
        userAccount.login = '12345678'

        then: 'validation should pass'
        userAccount.validate()
    }

    void "Test that password can't be null or blank"() {

        when: 'the senha is null'
        userAccount.senha = null

        then: 'validation should fail'
        !userAccount.validate()

        when: 'the senha is blank'
        userAccount.senha = ' '

        then: 'validation should fail'
        !userAccount.validate()

        when: 'the senha is filled'
        userAccount.senha = 'senha'

        then: 'validation should pass'
        userAccount.validate()
    }

    void "Test password must be between 5 and 20 characters"() {

        when: 'the senha have 4 characters'
        userAccount.senha = 'Test'

        then: 'validation should fail'
        !userAccount.validate()

        when: 'the senha have 21 characters'
        userAccount.senha = '123456789112345678912'

        then: 'validation should fail'
        !userAccount.validate()

        when: 'the senha have 8 characters'
        userAccount.senha = '12345678'

        then: 'validation should pass'
        userAccount.validate()
    }

    void "Test userAccount need a person for save"() {

        when: 'the pessoa is null'
        userAccount.pessoa = null

        then: 'validation should fail'
        !userAccount.validate()
    }
}
