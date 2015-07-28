package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Category
import spock.lang.Specification

@TestFor(Category)
class CategorySpec extends Specification {

	void "Test description can't be null"() {

		when: 'description be null'
		Category category = new Category(description: null)

		then: 'validation should fail'
		!category.validate()

		when: 'description be filled'
		category = new Category(description: 'Science')

		then: 'validation should pass'
		category.validate()
	}

	void "Test description can not exceed 254 characters"() {

		when: 'description be 255 characers'
		Category category = new Category(description: StringUtils.leftPad("", 255, '*'))

		then: 'validation should fail'
		!category.validate()

		when: 'description be 254 characers'
		category = new Category(description: StringUtils.leftPad("", 254, '*'))

		then: 'validation should pass'
		category.validate()
	}

}
