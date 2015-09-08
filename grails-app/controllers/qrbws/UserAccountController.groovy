package qrbws

import grails.rest.RestfulController
import grails.web.http.HttpHeaders
import qrbws.sender.messages.MessageCreatorEmailRegister
import qrbws.sender.messages.MessageCreatorSMSRegister

import static org.springframework.http.HttpStatus.CREATED

class UserAccountController extends RestfulController {

    static responseFormats = ['json']

    def userAccountService


    UserAccountController() {
        super(UserAccount)
    }

    def index() {}

    @Override
    def save() {
        if (handleReadOnly()) {
            return
        }
        def instance = createResource()

        instance.validate()
        if (instance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond instance.errors, view: 'create' // STATUS CODE 422
            return
        }

        instance.save flush: true

        if (instance) {
            instance.person.phone != null ? userAccountService.sendSMS(instance, new MessageCreatorSMSRegister()) : ''
            userAccountService.sendEmail(instance, new MessageCreatorEmailRegister())
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: "${resourceName}.label".toString(), default: resourceClassName), instance.id])
                redirect instance
            }
            '*' {
                response.addHeader(HttpHeaders.LOCATION,
                        grailsLinkGenerator.link(resource: this.controllerName, action: 'show', id: instance.id, absolute: true,
                                namespace: hasProperty('namespace') ? this.namespace : null))
                respond instance, [status: CREATED]
            }
        }

    }
}
