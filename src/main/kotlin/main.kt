import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.routing

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
    print("Starting server on $host:$port")
    server.start(wait = true)
}
