package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Holiday
import spock.lang.Specification

@TestFor(Holiday)
class HolidaySpec extends Specification {

    Holiday holiday

    def setup() {
        holiday = new Holiday(startDate: new Date(), finalDate: new Date())
    }

    void "Test description can't be null"() {

        when: 'value is null'
        holiday.description = null

        then: 'validation should fail'
        !holiday.validate()
    }

    void "Test description can't be blank"() {

        when: 'value is blank'
        holiday.description = ' '

        then: 'validation should fail'
        !holiday.validate()
    }

    void "Test description max value is 45"() {

        when: 'value is 46'
        holiday.description = StringUtils.leftPad("*", 46, '*')

        then: 'validation should fail'
        !holiday.validate()

        when: 'value is 45'
        holiday.description = StringUtils.leftPad("*", 45, '*')

        then: 'validation should pass'
        holiday.validate()
    }

    void "Test description is unique"() {

        when: 'save two equals descriptions'
        new Holiday(description: 'Code Day', startDate: new Date(), finalDate: new Date()).save(flush: true)
        holiday.description = 'Code Day'

        then: 'validation should fail'
        !holiday.validate()
    }
}
