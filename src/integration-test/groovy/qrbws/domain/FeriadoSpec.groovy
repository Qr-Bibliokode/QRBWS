package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Feriado
import spock.lang.Specification

@TestFor(Feriado)
class FeriadoSpec extends Specification {

    Feriado holiday

    def setup() {
        holiday = new Feriado(dataInicio: new Date(), dataFim: new Date())
    }

    void "Test description can't be null"() {

        when: 'value is null'
        holiday.descricao = null

        then: 'validation should fail'
        !holiday.validate()
    }

    void "Test description can't be blank"() {

        when: 'value is blank'
        holiday.descricao = ' '

        then: 'validation should fail'
        !holiday.validate()
    }

    void "Test description max value is 45"() {

        when: 'value is 46'
        holiday.descricao = StringUtils.leftPad("*", 46, '*')

        then: 'validation should fail'
        !holiday.validate()

        when: 'value is 45'
        holiday.descricao = StringUtils.leftPad("*", 45, '*')

        then: 'validation should pass'
        holiday.validate()
    }

    void "Test description is unique"() {

        when: 'save two equals descriptions'
        new Feriado(descricao: 'Code Day', dataInicio: new Date(), dataFim: new Date()).save(flush: true)
        holiday.descricao = 'Code Day'

        then: 'validation should fail'
        !holiday.validate()
    }
}
