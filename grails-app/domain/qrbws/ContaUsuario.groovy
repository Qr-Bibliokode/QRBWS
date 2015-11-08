package qrbws

import grails.converters.JSON

class ContaUsuario implements Serializable {

    private static final long serialVersionUID = 1

    transient springSecurityService

    String username
    String password
    Pessoa pessoa
    boolean enabled = true
    boolean accountExpired = false
    boolean accountLocked = false
    boolean passwordExpired = false

    static hasMany = [multas: Multa]

    static transients = ['springSecurityService']

    static constraints = {
        username blank: false, unique: true, size: 5..20
        password blank: false
    }

    static mapping = {
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        ContaUsuarioRole.findAllByUsuario(this)*.rol
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
//        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }

    @Override
    int hashCode() {
        username?.hashCode() ?: 0
    }

    @Override
    boolean equals(other) {
        is(other) || (other instanceof ContaUsuario && other.username == username)
    }

    @Override
    String toString() {
        username
    }

    static void marshaller() {
        JSON.registerObjectMarshaller(ContaUsuario) {
            [
                    'id'      : it.id,
                    'username': it.username,
                    'password': it.password,
                    'pessoa'  : it.pessoa,
                    'enabled' : it.enabled,
                    'multas'  : it.multas
            ]
        }
    }
}
