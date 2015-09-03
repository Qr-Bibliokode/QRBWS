package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Student
import spock.lang.Specification

@TestFor(Student)
class StudentSpec extends Specification {

    Student student

    def setup() {
        student = new Student(name: 'Ronaldinho', email: 'ronaldinho@gmail.com')
    }

    void "Test enrollment can't be null"() {

        when: 'enrollment be null'
        student.enrollment = null

        then: 'validation should fail'
        !student.validate()

        when: 'enrollment be filled'
        student.enrollment = '15487'

        then: 'validation should pass'
        student.validate()
    }

    void "Test enrollment can't be blank"() {

        when: 'enrollment be blank'
        student.enrollment = ' '

        then: 'validation should fail'
        !student.validate()
    }

    void "Test enrollment can not exceed 5 characters"() {

        when: 'enrollment be 6 characers'
        student.enrollment = StringUtils.leftPad("", 6, '*')

        then: 'validation should fail'
        !student.validate()

        when: 'enrollment be 5 characers'
        student.enrollment = StringUtils.leftPad("", 5, '*')

        then: 'validation should pass'
        student.validate()
    }

    void "Test enrollment must be unique"() {

        when: 'enrollment is duplicated'
        new Student(name: 'test', email: 'test@gmail.com', enrollment: '12312').save(flush: true)
        student.enrollment = '12312'

        then: 'validation should fail'
        !student.validate()
    }

}
