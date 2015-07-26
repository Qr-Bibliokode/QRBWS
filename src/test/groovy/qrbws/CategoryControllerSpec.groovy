package groovy.qrbws

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Category
import qrbws.CategoryController
import spock.lang.Specification

@TestFor(CategoryController)
@Mock(Category)
class CategoryControllerSpec extends Specification {

    def category

    def setup() {
        Category.withNewSession() { session ->
            category = new Category(description: 'Category Test').save()
        }
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include a category"() {
        given:
        category.save()

        when:
        response.format = 'json'
        controller.index()

        then:
        response.status == 200
        response.contentAsString == '[{"class":"qrbws.Category","id":1,"description":"Category Test"}]'
    }

    void "test update is called after persist"() {
        when:
        category.description = "Category Edited"
        category.save()
        response.format = 'json'
        controller.show(category)

        then:
        response.contentAsString == '{"class":"qrbws.Category","id":1,"description":"Category Edited"}'
    }

    void "test show() return a category when is called"() {
        when:
        response.format = 'json'
        controller.show(category)

        then:
        response.status == 200
        response.contentAsString == '{"class":"qrbws.Category","id":1,"description":"Category Test"}'
    }

    void "test create() return a category"() {
        when:
        response.format = 'json'
        params.description = "Category Created"
        controller.create()

        then:
        response.status == 200
        response.contentAsString == '{"class":"qrbws.Category","id":null,"description":"Category Created"}'
    }

    void "test save() persist a category"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(category)

        then:
        response.status == 201
        response.contentAsString == '{"class":"qrbws.Category","id":1,"description":"Category Test"}'
    }

    void "test edit() is called after persist"() {
        when:
        category.description = "Other Category"
        response.format = 'json'
        controller.edit(category)

        then:
        response.status == 200
        response.contentAsString == '{"class":"qrbws.Category","id":1,"description":"Other Category"}'
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
        controller.delete()

        then:
        response.status == 404
    }

    void "test return 'not found' when try save an inexistent category"() {
        given:
        def category

        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(category)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent category"() {
        given:
        def category

        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(category)

        then:
        response.status == 404
    }
}