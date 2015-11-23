import grails.rest.render.json.JsonRenderer
import grails.rest.render.xml.XmlRenderer
import qrbws.Autor
import spring.CorsFilter

beans = {
    cityXmlRenderer(XmlRenderer, Autor) {
        excludes = ['class']
    }
    cityJSONRenderer(JsonRenderer, Autor) {
        excludes = ['class']
    }

    corsFilter(CorsFilter)
}
