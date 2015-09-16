class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.${format})?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "500"(view: '/error')

        "/api/userAccount"(resources: "userAccount")
        "/api/lending/lend"(controller: 'lending', action: 'lend')
        "/api/lending"(resources: "lending")
    }
}