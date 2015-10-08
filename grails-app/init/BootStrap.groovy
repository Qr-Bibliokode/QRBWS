import grails.converters.JSON
import qrbws.Autor
import qrbws.Categoria
import qrbws.ContaUsuario
import qrbws.Funcionario
import qrbws.Idioma
import qrbws.Livro
import qrbws.Stock

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

        JSON.registerObjectMarshaller(Livro) {
            def map= [:]
            map['id'] = it.id
            map['idioma'] = it.idioma
            map['isbn'] = it.isbn
            map['titulo'] = it.titulo
            map['sinopse'] = it.sinopse
            map['paginas'] = it.paginas
            map['categoria'] = it.categoria
            map['autores'] = it.autores
            map['comentarios'] = it.comentarios
            return map
        }

    }
    def destroy = {
    }
}
