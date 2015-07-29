package groovy.qrbws.controllers

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Category
import qrbws.CategoryController
import spock.lang.Specification

@TestFor(CategoryController)
@Mock(Category)
class CategoryControllerSpec extends Specification {

    Category category

    def setup() {
        category = new Category(description: 'Category Test').save()
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include a category"() {

        when:
        response.format = 'json'
        controller.index(1)

        then:
        response.status == 200
        Category categoryResponse = JSON.parse(response.contentAsString)
        categoryResponse.description == category.description
    }

    void "test update is called after persist"() {

        when:
        category.description = "Category Edited"
        category.save()
        response.format = 'json'
        controller.show(category)

        then:
        Category categoryResponse = JSON.parse(response.contentAsString)
        categoryResponse.description == category.description
    }

    void "test show() return a category when is called"() {

        when:
        response.format = 'json'
        controller.show(category)

        then:
        response.status == 200
        Category categoryResponse = JSON.parse(response.contentAsString)
        categoryResponse.description == category.description
    }

    void "test create() return a category"() {

        when:
        response.format = 'json'
        params.description = "Category Created"
        controller.create()

        then:
        response.status == 200
        Category categoryResponse = JSON.parse(response.contentAsString)
        categoryResponse.description == params.description
    }

    void "test save() persist a category"() {

        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(category)

        then:
        response.status == 201
        Category categoryResponse = JSON.parse(response.contentAsString)
        categoryResponse.description == category.description
    }

    void "test edit() is called after persist"() {

        when:
        category.description = "Other Category"
        response.format = 'json'
        controller.edit(category)

        then:
        response.status == 200
        Category categoryResponse = JSON.parse(response.contentAsString)
        assert categoryResponse.description == category.description
    }

    void "test delete() is called after persist"() {

        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(category)

        then:
        response.status == 204
        response.contentAsString == ''

        when:
        controller.show(category)

        then:
        response.status == 204
    }


    void "test return 'not found' when try delete an inexistent category"() {

        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try save an inexistent category"() {

        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent category"() {

        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(null)

        then:
        response.status == 404
    }
}