package qrbws

class ContaUsuario {

    String login
    String senha
    Pessoa pessoa
    Boolean ativo

    ContaUsuario() {
        this.ativo = true
    }

    static constraints = {
        login blank: false, unique: true, size: 5..20
        senha blank: false, size: 5..20
    }
}
