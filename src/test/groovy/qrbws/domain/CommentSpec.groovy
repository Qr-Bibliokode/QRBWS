package qrbws.domain

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.*
import spock.lang.Specification

@TestFor(Comment)
@Mock([Book, UserAccount, Person, Status])
class CommentSpec extends Specification {

	def comment, book, user, person, status

	def setup() {
		Comment.withNewSession() { session ->
			book = new Book(title: 'Book Test', isbn: '123').save()
			person = new Person(name: 'Person Name', email: 'person@email.com').save()
			status = new Status(description: 'Ativo').save()
			user = new UserAccount(login: 'User Login', password: '12345', person: person, status: status).save()
			comment = new Comment(description: 'Comment Test', book: book, userAccount: user, avaliation: 2)
		}
	}

	void "Test avaliation max value is 5"() {

		when: 'value is 6'
		comment.avaliation = 6

		then: 'validation should fail'
		!comment.validate()

		when: 'value is negative'
		comment.avaliation = -1

		then: 'validation should fail'
		!comment.validate()

		when: 'value is 3'
		comment.avaliation = 3

		then: 'validation should fail'
		comment.validate()
	}
}
