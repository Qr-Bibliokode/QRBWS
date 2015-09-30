import grails.rest.render.json.JsonRenderer
import grails.rest.render.xml.XmlRenderer
import qrbws.Autor

beans = {
    cityXmlRenderer(XmlRenderer, Autor) {
        excludes = ['class']
    }
    cityJSONRenderer(JsonRenderer, Autor) {
        excludes = ['class']
    }
}
