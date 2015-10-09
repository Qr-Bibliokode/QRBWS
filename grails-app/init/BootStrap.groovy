import qrbws.*

class BootStrap {

    def init = { servletContext ->
        Locale.default = new Locale('pt', 'BR')
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

        new Categoria(descricao: 'Science fiction').save();
        new Idioma(descricao: 'Spanish').save();
        new Autor(nome: 'Aparecida', informacoesAdicionais: 'Beautiful pessoa').save();
        Livro livro = new Livro(isbn: "123456", titulo: "Livro teste").save()
        new Stock(livro: livro, disponivel: 10, total: 10).save()
        Funcionario funcionario = new Funcionario(codigo: "1234", nome: "Ferran", email: "teste@teste.com").save()
        new ContaUsuario(pessoa: funcionario, login: "felansu", senha: "123456").save()

        Livro.marshaller()
        Comentario.marshaller()
        ContaUsuario.marshaller()

    }
    def destroy = {
    }
}
