import qrbws.Author
import qrbws.Category
import qrbws.Idiom
import qrbws.Status

class BootStrap {

    def init = { servletContext ->
        Locale.default = new Locale('pt', 'BR')
//        new Category(description: 'Science fiction').save();
//        new Idiom(description: 'Spanish').save();
//        new Status(description: 'Active').save();
//        new Status(description: 'Desactivate').save();
//        new Author(name: 'Aparecida', notes: 'Beautiful person').save();
    }
    def destroy = {
    }
}
