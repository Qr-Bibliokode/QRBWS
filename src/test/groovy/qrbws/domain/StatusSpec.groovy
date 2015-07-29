package groovy.qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Status
import spock.lang.Specification

@TestFor(Status)
class StatusSpec extends Specification {

    Status status

    def setup() {
        status = new Status()
    }

    void "Test description can't be null"() {

        when: 'description be null'
        status.description = null

        then: 'validation should fail'
        !status.validate()

        when: 'description be filled'
        status.description = 'Science'

        then: 'validation should pass'
        status.validate()
    }

    void "Test description can't be blank"() {

        when: 'description be blank'
        status.description = ' '

        then: 'validation should fail'
        !status.validate()
    }

    void "Test description can not exceed 45 characters"() {

        when: 'description be 46 characers'
        status.description = StringUtils.leftPad("", 46, '*')

        then: 'validation should fail'
        !status.validate()

        when: 'description be 45 characers'
        status.description = StringUtils.leftPad("", 45, '*')

        then: 'validation should pass'
        status.validate()
    }

}
