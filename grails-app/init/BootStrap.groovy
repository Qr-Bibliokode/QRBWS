import qrbws.ContaUsuarioRole
import qrbws.Role
import qrbws.*

class BootStrap {

    def init = { servletContext ->
        new Categoria(descricao: 'Science fiction').save();
        new Idioma(descricao: 'Spanish').save();
        new Autor(nome: 'Aparecida', informacoesAdicionais: 'Beautiful pessoa').save();
        Estoque estoque = new Estoque(disponivel: 10, total: 10)
        Livro livro = new Livro(isbn: "123456", titulo: "Livro teste", estoque: estoque).save()
        Funcionario funcionario = new Funcionario(codigo: "1234", nome: "Ferran", email: "teste@teste.com").save()
        ContaUsuario usuario = new ContaUsuario(pessoa: funcionario, username: "felansu", password: "123456").save()

        Livro.marshaller()
        Comentario.marshaller()
        ContaUsuario.marshaller()
        Emprestimo.marshaller()
        Estoque.marshaller()
        Multa.marshaller()
        Reserva.marshaller()


        Role adminRole = new Role('ROLE_ADMIN').save()
        Role usuarioRol = new Role('ROLE_USER').save()


        ContaUsuarioRole.create usuario, adminRole, true

    }
    def destroy = {
    }
}
