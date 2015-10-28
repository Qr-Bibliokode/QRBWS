import com.testapp.Rol
import com.testapp.Usuario
import com.testapp.UsuarioRol
import qrbws.*

class BootStrap {

    def init = { servletContext ->
        Locale.default = new Locale('pt', 'BR')
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

        new Categoria(descricao: 'Science fiction').save();
        new Idioma(descricao: 'Spanish').save();
        new Autor(nome: 'Aparecida', informacoesAdicionais: 'Beautiful pessoa').save();
        Stock stock = new Stock(disponivel: 10, total: 10)
        Livro livro = new Livro(isbn: "123456", titulo: "Livro teste", stock: stock).save()
        Funcionario funcionario = new Funcionario(codigo: "1234", nome: "Ferran", email: "teste@teste.com").save()
        new ContaUsuario(pessoa: funcionario, login: "felansu", senha: "123456").save()

        Livro.marshaller()
        Comentario.marshaller()
        ContaUsuario.marshaller()
        Emprestimo.marshaller()
        Stock.marshaller()
        Multa.marshaller()
        Reserva.marshaller()


        def adminRole = new Rol('ROLE_ADMIN').save()
        def usuarioRol = new Rol('ROLE_USER').save()

        def testUser = new Usuario('me', 'password').save()

        UsuarioRol.create testUser, adminRole, true

        assert Usuario.count() == 1
        assert Rol.count() == 2
        assert UsuarioRol.count() == 1

    }
    def destroy = {
    }
}
