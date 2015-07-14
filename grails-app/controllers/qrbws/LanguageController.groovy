package qrbws

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LanguageController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Language.list(params), model:[languageCount: Language.count()]
    }

    def show(Language language) {
        respond language
    }

    def create() {
        respond new Language(params)
    }

    @Transactional
    def save(Language language) {
        if (language == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (language.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond language.errors, view:'create'
            return
        }

        language.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'language.label', default: 'Language'), language.id])
                redirect language
            }
            '*' { respond language, [status: CREATED] }
        }
    }

    def edit(Language language) {
        respond language
    }

    @Transactional
    def update(Language language) {
        if (language == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (language.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond language.errors, view:'edit'
            return
        }

        language.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'language.label', default: 'Language'), language.id])
                redirect language
            }
            '*'{ respond language, [status: OK] }
        }
    }

    @Transactional
    def delete(Language language) {

        if (language == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        language.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'language.label', default: 'Language'), language.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'language.label', default: 'Language'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
