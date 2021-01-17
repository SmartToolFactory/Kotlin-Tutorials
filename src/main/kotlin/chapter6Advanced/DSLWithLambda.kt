package chapter6Advanced


fun main() {
    testRouteHandler()
}

private fun testRouteHandler() {

    routeHandler("/index.html") {

        if (request.query != "") {
            // process
        }

        response {
            code = 404
            description = "Not found"
        }
    }
}

class RouteHandler(val request: Request, val response: Response) {
    var executeNext = false
    fun next() {
        executeNext = true
    }
}

fun routeHandler(path: String, f: RouteHandler.() -> Unit) {

    val request = Request("GET", "/v1/getProducts", "json")
    val response = Response("body", Status(200, ""))
    val routeHandler = RouteHandler(request, response)

    routeHandler.f()
}

class Status(var code: Int, var description: String)

class Request(val method: String, val query: String, val contentType: String)

class Response(var contents: String, var status: Status) {
    operator fun invoke(status: Status.() -> Unit) {
    }
}
