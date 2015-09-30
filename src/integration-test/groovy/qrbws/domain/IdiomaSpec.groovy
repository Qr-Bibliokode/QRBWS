package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Idioma
import spock.lang.Specification

@TestFor(Idioma)
class IdiomaSpec extends Specification {

    Idioma idiom

    def setup() {
        idiom = new Idioma()
    }

    void "Test description can't be null"() {

        when: 'value is null'
        idiom.descricao = null

        then: 'validation should fail'
        !idiom.validate()
    }

    void "Test description can't be blank"() {

        when: 'value is blank'
        idiom.descricao = ' '

        then: 'validation should fail'
        !idiom.validate()
    }

    void "Test description max value is 45"() {

        when: 'value is 46'
        idiom.descricao = StringUtils.leftPad("*", 46, '*')

        then: 'validation should fail'
        !idiom.validate()

        when: 'value is 45'
        idiom.descricao = StringUtils.leftPad("*", 45, '*')

        then: 'validation should pass'
        idiom.validate()
    }

    void "Test description is unique"() {

        when: 'save two equals descriptions'
        new Idioma(descricao: 'Spanish').save(flush: true)
        idiom.descricao = 'Spanish'

        then: 'validation should fail'
        !idiom.validate()
    }
}
