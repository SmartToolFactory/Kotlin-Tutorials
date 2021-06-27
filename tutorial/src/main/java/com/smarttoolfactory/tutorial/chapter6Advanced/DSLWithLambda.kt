package com.smarttoolfactory.tutorial.chapter6Advanced

import java.util.*


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


// HTML

class HTML {

    fun init() {

    }
}

interface Element

class Head : Element
class Body : Element

val children = ArrayList<Element>()

fun head(init: Head.() -> Unit): Head {
    val head = Head()
    head.init()
    children.add(head)
    return head
}

fun body(init: Body.() -> Unit): Body {
    val body = Body()
    body.init()
    children.add(body)
    return body
}