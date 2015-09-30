package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Categoria
import spock.lang.Specification

@TestFor(Categoria)
class CategoriaSpec extends Specification {

    Categoria category

    def setup() {
        category = new Categoria()
    }

    void "Test description can't be null"() {

        when: 'descricao be null'
        category.descricao = null

        then: 'validation should fail'
        !category.validate()

        when: 'descricao be filled'
        category.descricao = 'Science'

        then: 'validation should pass'
        category.validate()
    }

    void "Test description can not exceed 50 characters"() {

        when: 'descricao be 51 characers'
        category.descricao = StringUtils.leftPad("", 51, '*')

        then: 'validation should fail'
        !category.validate()

        when: 'descricao be 50 characers'
        category.descricao = StringUtils.leftPad("", 50, '*')

        then: 'validation should pass'
        category.validate()
    }

}
