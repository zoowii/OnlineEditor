class UrlMappings {

    static mappings = {
        "/u/$authorName"(controller: 'blog', action: 'index', params: [authorName: authorName])

        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "blog", action: 'index', params: [authorName: 'zoowii'])
        "500"(view: '/error')
    }
}
