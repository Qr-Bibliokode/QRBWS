package qrbws

import grails.converters.JSON

class ContaUsuario {

    String login
    String senha
    Pessoa pessoa
    Boolean ativo

    static hasMany = [multas: Multa]

    ContaUsuario() {
        this.ativo = true
    }

    static constraints = {
        login blank: false, unique: true, size: 5..20
        senha blank: false, size: 5..20
    }

    static void marshaller() {
        JSON.registerObjectMarshaller(ContaUsuario) {
            [
                    'id'    : it.id,
                    'login' : it.login,
                    'senha' : it.senha,
                    'pessoa': it.pessoa,
                    'ativo' : it.ativo,
                    'multas': it.multas
            ]
        }
    }
}
