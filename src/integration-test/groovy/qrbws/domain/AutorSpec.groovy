package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Autor
import spock.lang.Specification

@TestFor(Autor)
public class AutorSpec extends Specification {

    Autor author

    def setup() {
        author = new Autor()
    }


    void "Test notes can be null"() {

        when: 'informacoesAdicionais be null'
        author.nome = 'ferran'
        author.informacoesAdicionais = null

        then: 'validation should pass'
        author.validate()
    }

    void "Test name can't be null"() {

        when: 'nome be null'
        author.nome = null

        then: 'validation should fail'
        !author.validate()
    }

    void "Test name can't be blank"() {

        when: 'nome be blank'
        author.nome = ''

        then: 'validation should fail'
        !author.validate()
    }

    void "Test name can not exceed 50 characters"() {

        when: 'nome be 51 characers'
        author.nome = StringUtils.leftPad("A", 51, 'A')

        then: 'validation should fail'
        !author.validate()

        when: 'nome be 40 characers'
        author.nome = StringUtils.leftPad("B", 40, 'B')

        then: 'validation should pass'
        author.validate()
    }

    void "Test name can not allow numbers"() {

        when: 'nome be a number'
        author.nome = 'Messi10'

        then: 'validation should fail'
        !author.validate()
    }
}
