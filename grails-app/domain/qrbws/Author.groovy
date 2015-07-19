package qrbws

import grails.rest.Resource

@Resource(uri='/author')
class Author {

    String name
    String references

    static constraints = {
        references nullable: true
    }
}
