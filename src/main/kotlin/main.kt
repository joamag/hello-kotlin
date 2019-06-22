import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondOutputStream
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.nio.charset.Charset

/**
 * The default (bind) host to be used when none is defined,
 * notice that this values is permissive by default.
 */
var host = "0.0.0.0"

/**
 * The default port to be used when none is defined.
 */
var port = 8080

fun main(args: Array<String>) {
    // creates the server object with the provided port and host
    // values as defined by standard
    val server = embeddedServer(Netty, port = port, host = host) {
        routing {
            get(path = "/") {
                call.respondText("<strong>Hello, world!</strong>", ContentType.Text.Html)
            }
            get(path = "/message/{message}") {
                call.respondText(call.parameters["message"] as String, ContentType.Text.Plain)
            }
            get(path = "/stream/{message}") {
                call.respondOutputStream(ContentType.Text.Plain) { write((call.parameters["message"] as String).toByteArray(Charset.defaultCharset())) }
            }
        }
    }

    // tries to retrieve the value of the environment variables
    // that are going to control the execution
    host = if (System.getenv("HOST") == null) host else System.getenv("HOST")
    port = if (System.getenv("PORT") == null) port else System.getenv("PORT").toInt()

    // retrieves the current versions for both the JVM and the
    // kotlin runtime operations
    val javaVersion = System.getProperty("java.version")
    val kotlinVersion = KotlinVersion.CURRENT

    // prints a message about the initial operation of the server
    // ans starts the blocking serving operation
    print("Starting server on $host:$port (kotlin/$kotlinVersion jvm/$javaVersion)\n")
    server.start(wait = true)
}
