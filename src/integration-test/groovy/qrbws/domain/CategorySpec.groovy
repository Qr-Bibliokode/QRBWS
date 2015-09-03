package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Category
import spock.lang.Specification

@TestFor(Category)
class CategorySpec extends Specification {

    Category category

    def setup() {
        category = new Category()
    }

    void "Test description can't be null"() {

        when: 'description be null'
        category.description = null

        then: 'validation should fail'
        !category.validate()

        when: 'description be filled'
        category.description = 'Science'

        then: 'validation should pass'
        category.validate()
    }

    void "Test description can not exceed 50 characters"() {

        when: 'description be 51 characers'
        category.description = StringUtils.leftPad("", 51, '*')

        then: 'validation should fail'
        !category.validate()

        when: 'description be 50 characers'
        category.description = StringUtils.leftPad("", 50, '*')

        then: 'validation should pass'
        category.validate()
    }

}
