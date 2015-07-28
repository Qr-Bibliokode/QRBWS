package groovy.qrbws.controllers

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Student
import qrbws.StudentController
import spock.lang.Specification

@TestFor(StudentController)
@Mock(Student)
class StudentControllerSpec extends Specification {

    Student student

    def setup() {
        Student.withNewSession() { session ->
            student = new Student(enrollment: '12345', name: 'Student Test', email: 'student@test.com').save()
        }
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include a student"() {
        given:
        student.save()

        when:
        response.format = 'json'
        controller.index(1)

        then:
        response.status == 200
        Student studentResponse = JSON.parse(response.contentAsString)
        studentResponse.enrollment == student.enrollment
        studentResponse.name == student.name
        studentResponse.email == student.email
    }

    void "test update is called after persist"() {
        when:
        student.enrollment = "83273"
        student.save()
        response.format = 'json'
        controller.show(student)

        then:
        Student studentResponse = JSON.parse(response.contentAsString)
        studentResponse.enrollment == student.enrollment
        studentResponse.name == student.name
        studentResponse.email == student.email
    }

    void "test show() return a student when is called"() {
        when:
        response.format = 'json'
        controller.show(student)

        then:
        response.status == 200
        Student studentResponse = JSON.parse(response.contentAsString)
        studentResponse.enrollment == student.enrollment
        studentResponse.name == student.name
        studentResponse.email == student.email
    }

    void "test create() return a student"() {
        when:
        response.format = 'json'
        params.enrollment = "65432"
        controller.create()

        then:
        response.status == 200
        Student studentResponse = JSON.parse(response.contentAsString)
        studentResponse.enrollment == params.enrollment
        studentResponse.name == params.name
        studentResponse.email == params.email
    }

    void "test save() persist a student"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(student)

        then:
        response.status == 201
        Student studentResponse = JSON.parse(response.contentAsString)
        studentResponse.enrollment == student.enrollment
        studentResponse.name == student.name
        studentResponse.email == student.email
    }

    void "test edit() is called after persist"() {
        when:
        student.enrollment = "12312"
        response.format = 'json'
        controller.edit(student)

        then:
        response.status == 200
        Student studentResponse = JSON.parse(response.contentAsString)
        studentResponse.enrollment == student.enrollment
        studentResponse.name == student.name
        studentResponse.email == student.email
    }

    void "test delete() is called after persist"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(student)

        then:
        response.status == 204
        response.contentAsString == ''

        when:
        controller.show(student)

        then:
        response.status == 204
    }


    void "test return 'not found' when try delete an inexistent student"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try save an inexistent student"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent student"() {
        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(null)

        then:
        response.status == 404
    }
}