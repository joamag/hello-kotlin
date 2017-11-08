import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

/**
 * The default (bind) host to be used when none is defined,
 * notice that this values is permissive by default.
 */
val host = "0.0.0.0"

/**
 * The default port to be used when none is defined.
 */
val port = 8080

fun main(args: Array<String>) {
    // creates the server object with the provided port and host
    // values as defined by standard
    val server = embeddedServer(Netty, port = port, host = host) {
        routing {
            get(path = "/") {
                call.respondText("Hello, world!", ContentType.Text.Html)
            }
        }
    }

    // prints a message about the initial operation of the server
    // ans starts the blocking serving operation
    print("Starting server on $host:$port\n")
    server.start(wait = true)
}
