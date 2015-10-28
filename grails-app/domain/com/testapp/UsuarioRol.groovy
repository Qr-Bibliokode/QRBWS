package com.testapp

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class UsuarioRol implements Serializable {

	private static final long serialVersionUID = 1

	Usuario usuario
	Rol rol

	UsuarioRol(Usuario u, Rol r) {
		this()
		usuario = u
		rol = r
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof UsuarioRol)) {
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

	static UsuarioRol get(long usuarioId, long rolId) {
		criteriaFor(usuarioId, rolId).get()
	}

	static boolean exists(long usuarioId, long rolId) {
		criteriaFor(usuarioId, rolId).count()
	}

	private static DetachedCriteria criteriaFor(long usuarioId, long rolId) {
		UsuarioRol.where {
			usuario == Usuario.load(usuarioId) &&
			rol == Rol.load(rolId)
		}
	}

	static UsuarioRol create(Usuario usuario, Rol rol, boolean flush = false) {
		def instance = new UsuarioRol(usuario, rol)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(Usuario u, Rol r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = UsuarioRol.where { usuario == u && rol == r }.deleteAll()

		if (flush) { UsuarioRol.withSession { it.flush() } }

		rowCount
	}

	static void removeAll(Usuario u, boolean flush = false) {
		if (u == null) return

		UsuarioRol.where { usuario == u }.deleteAll()

		if (flush) { UsuarioRol.withSession { it.flush() } }
	}

	static void removeAll(Rol r, boolean flush = false) {
		if (r == null) return

		UsuarioRol.where { rol == r }.deleteAll()

		if (flush) { UsuarioRol.withSession { it.flush() } }
	}

	static constraints = {
		rol validator: { Rol r, UsuarioRol ur ->
			if (ur.usuario == null || ur.usuario.id == null) return
			boolean existing = false
			UsuarioRol.withNewSession {
				existing = UsuarioRol.exists(ur.usuario.id, r.id)
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
