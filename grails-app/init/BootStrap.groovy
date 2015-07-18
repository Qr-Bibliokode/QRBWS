import qrbws.Author
import qrbws.Category
import qrbws.Language
import qrbws.Status

class BootStrap {

    def init = { servletContext ->
        new Category(description: 'Science fiction').save();
        new Language(description: 'Spanish').save();
        new Status(description: 'Active').save();
        new Status(description: 'Desactivate').save();
        new Author(name: 'Aparecida', references: 'Beautiful person').save();
    }
    def destroy = {
    }
}
