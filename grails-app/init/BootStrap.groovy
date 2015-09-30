import qrbws.Autor
import qrbws.Categoria
import qrbws.Idioma

class BootStrap {

    def init = { servletContext ->
        Locale.default = new Locale('pt', 'BR')
        new Categoria(descricao: 'Science fiction').save();
        new Idioma(descricao: 'Spanish').save();
        new Autor(nome: 'Aparecida', informacoesAdicionais: 'Beautiful pessoa').save();
    }
    def destroy = {
    }
}
