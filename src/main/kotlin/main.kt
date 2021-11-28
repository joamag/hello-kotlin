import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondOutputStream
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.netty.util.Version
import org.slf4j.Logger.ROOT_LOGGER_NAME
import org.slf4j.LoggerFactory
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

/**
 * The map of multiple logging levels to be used for the logging
 * of the current system.
 */
var levels = mapOf(
    "OFF" to Level.OFF,
    "TRACE" to Level.TRACE,
    "DEBUG" to Level.DEBUG,
    "INFO" to Level.INFO,
    "WARN" to Level.WARN,
    "ERROR" to Level.ERROR
)

fun main(args: Array<String>) {
    // retrieves the reference to the root logger and then updates
    // the verbosity level to the default info level as expected
    var level = System.getenv("LEVEL")
    if (level == null) level = "INFO"
    val root = LoggerFactory.getLogger(ROOT_LOGGER_NAME) as Logger
    root.level = levels.getOrDefault(level, Level.INFO)

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
    val nettyVersion = Version.identify()["netty-common"]?.artifactVersion()

    // prints a message about the initial operation of the server
    // and starts the blocking serving operation
    print("Starting server on $host:$port (kotlin/$kotlinVersion jvm/$javaVersion netty/$nettyVersion)\n")
    server.start(wait = true)
}
