import qrbws.ContaUsuarioRole
import qrbws.Role
import qrbws.*

class BootStrap {

    def init = { servletContext ->
        new Categoria(descricao: 'Science fiction').save();
        new Idioma(descricao: 'Spanish').save();
        new Autor(nome: 'Aparecida', informacoesAdicionais: 'Beautiful pessoa').save();
        Estoque estoque = new Estoque(disponivel: 10, total: 10)
        new Livro(isbn: "123456", titulo: "Livro teste", estoque: estoque).save()
        Pessoa funcionario = new Funcionario(codigo: "1234", nome: "Ferran", email: "felansu@teste.com").save()
        ContaUsuario usuarioAdmin = new ContaUsuario(pessoa: funcionario, username: "felansu", password: "felansu").save()

        Pessoa estudante = new Estudante(matricula: "4321", nome: "Aparecida", email: "aparecida@teste.com").save()
        ContaUsuario usuarioEstudante = new ContaUsuario(pessoa: estudante, username: "aparecida", password: "aparecida").save()

        Livro.marshaller()
        Comentario.marshaller()
        ContaUsuario.marshaller()
        Emprestimo.marshaller()
        Estoque.marshaller()
        Multa.marshaller()
        Reserva.marshaller()


        Role adminRole = new Role('ROLE_ADMIN').save()
        Role usuarioRol = new Role('ROLE_USER').save()


        ContaUsuarioRole.create usuarioAdmin, adminRole, true
        ContaUsuarioRole.create usuarioEstudante, usuarioRol, true

    }
    def destroy = {
    }
}
