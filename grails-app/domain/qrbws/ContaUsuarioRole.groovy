package qrbws

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class ContaUsuarioRole implements Serializable {

	private static final long serialVersionUID = 1

	ContaUsuario usuario
	Role rol

	ContaUsuarioRole(ContaUsuario u, Role r) {
		usuario = u
		rol = r
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof ContaUsuarioRole)) {
			return false
		}

		other.usuario?.id == usuario?.id && other.rol?.id == rol?.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (usuario) builder.append(usuario.id)
		if (rol) builder.append(rol.id)
		builder.toHashCode()
	}

	static ContaUsuarioRole get(long usuarioId, long rolId) {
		criteriaFor(usuarioId, rolId).get()
	}

	static boolean exists(long usuarioId, long rolId) {
		criteriaFor(usuarioId, rolId).count()
	}

	private static DetachedCriteria criteriaFor(long usuarioId, long rolId) {
		ContaUsuarioRole.where {
			usuario == ContaUsuario.load(usuarioId) &&
			rol == Role.load(rolId)
		}
	}

	static ContaUsuarioRole create(ContaUsuario usuario, Role rol, boolean flush = false) {
		def instance = new ContaUsuarioRole(usuario, rol)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(ContaUsuario u, Role r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = ContaUsuarioRole.where { usuario == u && rol == r }.deleteAll()

		if (flush) { ContaUsuarioRole.withSession { it.flush() } }

		rowCount
	}

	static void removeAll(ContaUsuario u, boolean flush = false) {
		if (u == null) return

		ContaUsuarioRole.where { usuario == u }.deleteAll()

		if (flush) { ContaUsuarioRole.withSession { it.flush() } }
	}

	static void removeAll(Role r, boolean flush = false) {
		if (r == null) return

		ContaUsuarioRole.where { rol == r }.deleteAll()

		if (flush) { ContaUsuarioRole.withSession { it.flush() } }
	}

	static constraints = {
		rol validator: { Role r, ContaUsuarioRole ur ->
			if (ur.usuario == null || ur.usuario.id == null) return
			boolean existing = false
			ContaUsuarioRole.withNewSession {
				existing = ContaUsuarioRole.exists(ur.usuario.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['usuario', 'rol']
		version false
	}
}
