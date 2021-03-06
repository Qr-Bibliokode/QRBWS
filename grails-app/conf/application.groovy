environments {
    production {
        dataSource {
            dbCreate = "update"
            driverClassName = "org.postgresql.Driver"
            dialect = org.hibernate.dialect.PostgreSQLDialect
            uri = new URI(System.env.DATABASE_URL ?: "postgres://test:test@localhost/test")
            url = "jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path
            username = uri.userInfo.split(":")[0]
            password = uri.userInfo.split(":")[1]
        }
    }
}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'qrbws.ContaUsuario'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'qrbws.ContaUsuarioRole'
grails.plugin.springsecurity.authority.className = 'qrbws.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        '/'              : ['permitAll'],
        '/error'         : ['permitAll'],
        '/index'         : ['permitAll'],
        '/index.gsp'     : ['permitAll'],
        '/shutdown'      : ['permitAll'],
        '/assets/**'     : ['permitAll'],
        '/**/js/**'      : ['permitAll'],
        '/**/css/**'     : ['permitAll'],
        '/**/images/**'  : ['permitAll'],
        '/**/favicon.ico': ['permitAll'],
        '/swagger/**'    : ['permitAll'],
        '/api/**'    : ['permitAll']
]

