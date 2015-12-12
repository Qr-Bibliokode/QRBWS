package qrbws

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

class EstoqueController {

	def estoqueService

	static responseFormats = ['json']

	static allowedMethods = [
			save                   : "POST",
			update                 : "PUT",
			delete                 : "DELETE",
			consultaDisponibilidade: "GET"
	]

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond Estoque.list(params), model: [estoqueCount: Estoque.count()]
	}

	def show(Estoque estoque) {
		respond estoque
	}

	def create() {
		respond new Estoque(params)
	}

	@Transactional
	def save(Estoque estoque) {
		if (estoque == null) {
			transactionStatus.setRollbackOnly()
			notFound()
			return
		}

		if (estoque.disponivel > estoque.total) {
			estoque.errors.reject('estoque.disponivel.maior.total')
		}

		if (estoque.hasErrors()) {
			transactionStatus.setRollbackOnly()
			respond estoque.errors, view: 'create'
			return
		}

		estoque.save flush: true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [message(code: 'estoque.label', default: 'Estoque'), estoque.id])
				redirect estoque
			}
			'*' { respond estoque, [status: CREATED] }
		}
	}

	@Transactional
	def update(Estoque estoque) {
		if (estoque == null) {
			transactionStatus.setRollbackOnly()
			notFound()
			return
		}

		if (estoque.disponivel > estoque.total) {
			estoque.errors.reject('estoque.disponivel.maior.total')
		}

		if (estoque.hasErrors()) {
			transactionStatus.setRollbackOnly()
			respond estoque.errors, view: 'edit'
			return
		}

		estoque.save flush: true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'estoque.label', default: 'Estoque'), estoque.id])
				redirect estoque
			}
			'*' { respond estoque, [status: OK] }
		}
	}

	@Transactional
	def delete(Estoque estoque) {

		if (estoque == null) {
			transactionStatus.setRollbackOnly()
			notFound()
			return
		}

		estoque.delete flush: true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'estoque.label', default: 'Estoque'), estoque.id])
				redirect action: "index", method: "GET"
			}
			'*' { render status: NO_CONTENT }
		}
	}

	def consultaDisponibilidade() {
		if (!params.tituloLivro || params.tituloLivro.length() < 3) {
			Estoque estoque = new Estoque()
			estoque.errors.reject('estoque.consulta.carateres')
			respond estoque.errors
		}
		respond estoqueService.consultaDisponibilidade(params.tituloLivro)
	}

	protected void notFound() {
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'estoque.label', default: 'Estoque'), params.id])
				redirect action: "index", method: "GET"
			}
			'*' { render status: NOT_FOUND }
		}
	}
}
