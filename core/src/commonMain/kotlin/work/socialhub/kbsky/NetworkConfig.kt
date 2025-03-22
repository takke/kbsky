package work.socialhub.kbsky

open class NetworkConfig {

    /**
     * a time period required to process an HTTP call: from sending a request to receiving a response.
     *
     * https://ktor.io/docs/client-timeout.html
     */
    var requestTimeout: Long? = null

    /**
     * a time period in which a client should establish a connection with a server.
     *
     * https://ktor.io/docs/client-timeout.html
     */
    var connectionTimeout: Long? = null

    /**
     * a maximum time of inactivity between two data packets when exchanging data with a server.
     *
     * https://ktor.io/docs/client-timeout.html
     */
    var socketTimeout: Long? = null
}