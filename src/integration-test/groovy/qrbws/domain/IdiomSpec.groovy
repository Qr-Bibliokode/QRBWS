package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Idiom
import spock.lang.Specification

@TestFor(Idiom)
class IdiomSpec extends Specification {

    Idiom idiom

    def setup() {
        idiom = new Idiom()
    }

    void "Test description can't be null"() {

        when: 'value is null'
        idiom.description = null

        then: 'validation should fail'
        !idiom.validate()
    }

    void "Test description can't be blank"() {

        when: 'value is blank'
        idiom.description = ' '

        then: 'validation should fail'
        !idiom.validate()
    }

    void "Test description max value is 45"() {

        when: 'value is 46'
        idiom.description = StringUtils.leftPad("*", 46, '*')

        then: 'validation should fail'
        !idiom.validate()

        when: 'value is 45'
        idiom.description = StringUtils.leftPad("*", 45, '*')

        then: 'validation should pass'
        idiom.validate()
    }

    void "Test description is unique"() {

        when: 'save two equals descriptions'
        new Idiom(description: 'Spanish').save(flush: true)
        idiom.description = 'Spanish'

        then: 'validation should fail'
        !idiom.validate()
    }
}
