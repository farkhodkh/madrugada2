package ru.petroplus.pos.network.ssl

import java.io.InputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.Socket
import java.net.SocketAddress
import java.nio.channels.SocketChannel
import javax.net.ssl.HandshakeCompletedListener
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

class NoSSLv3SocketFactory(socketFactory: SSLSocketFactory) : SSLSocketFactory() {
    private var delegate: SSLSocketFactory = HttpsURLConnection.getDefaultSSLSocketFactory()

    override fun createSocket(s: Socket?, host: String?, port: Int, autoClose: Boolean): Socket =
        makeSocketSafe(delegate.createSocket(s, host, port, autoClose))

    override fun createSocket(host: String?, port: Int): Socket =
        makeSocketSafe(delegate.createSocket(host, port))

    override fun createSocket(
        host: String?,
        port: Int,
        localHost: InetAddress?,
        localPort: Int
    ): Socket = makeSocketSafe(delegate.createSocket(host, port, localHost, localPort))

    override fun createSocket(host: InetAddress?, port: Int): Socket =
        makeSocketSafe(delegate.createSocket(host, port))

    override fun createSocket(
        address: InetAddress?,
        port: Int,
        localAddress: InetAddress?,
        localPort: Int
    ): Socket = makeSocketSafe(delegate.createSocket(address, port, localAddress, localPort))

    override fun getDefaultCipherSuites(): Array<String> = delegate.defaultCipherSuites

    override fun getSupportedCipherSuites(): Array<String> = delegate.supportedCipherSuites

    fun makeSocketSafe(safeSocket: Socket): Socket {
        val socket = if (safeSocket is SSLSocket) {
            NoSSLv3SSLSocket(safeSocket)
        } else {
            safeSocket
        }
        return socket
    }

    internal class NoSSLv3SSLSocket(delegate: SSLSocket) : DelegateSSLSocket(delegate) {
        //        private NoSSLv3SSLSocket(SSLSocket delegate) {
        //            super(delegate);
        //        }
        //
        //        @Override
        //        public void setEnabledProtocols(String[] protocols) {
        ////            if (protocols != null && protocols.length == 1 && "SSLv3".equals(protocols[0])) {
        ////
        ////                List<String> enabledProtocols = new ArrayList<String>(Arrays.asList(delegate.getEnabledProtocols()));
        ////                if (enabledProtocols.size() > 1) {
        ////                    enabledProtocols.remove("SSLv3");
        ////                    System.out.println("Removed SSLv3 from enabled protocols");
        ////                } else {
        ////                    System.out.println("SSL stuck with protocol available for " + String.valueOf(enabledProtocols));
        ////                }
        ////                protocols = enabledProtocols.toArray(new String[enabledProtocols.size()]);
        ////            }
        //
        ////            super.setEnabledProtocols(protocols);
        //
        //            String[] prots = new String[1];
        //            prots[0] = "TLSv1.2";
        //            super.setEnabledProtocols(prots);
        //        }
    }

    internal open class DelegateSSLSocket(private val delegate: SSLSocket) : SSLSocket() {

        override fun bind(bindpoint: SocketAddress?) {
            delegate.bind(bindpoint)
        }

        override fun close() {
            delegate.close()
        }

        override fun connect(endpoint: SocketAddress?) {
            delegate.connect(endpoint)
        }

        override fun connect(endpoint: SocketAddress?, timeout: Int) {
            delegate.connect(endpoint, timeout)
        }

        override fun getChannel(): SocketChannel = delegate.channel

        override fun getInetAddress(): InetAddress = delegate.inetAddress

        override fun getSupportedCipherSuites(): Array<String> = delegate.supportedCipherSuites

        override fun getEnabledCipherSuites(): Array<String> = delegate.enabledCipherSuites

        override fun setEnabledCipherSuites(suites: Array<out String>?) {
            delegate.enabledCipherSuites = suites
        }

        override fun getSupportedProtocols(): Array<String> = delegate.supportedProtocols

        override fun getEnabledProtocols(): Array<String> = delegate.enabledProtocols

        override fun setEnabledProtocols(protocols: Array<out String>?) {
            delegate.enabledProtocols = protocols
        }

        override fun getSession(): SSLSession = delegate.session

        override fun addHandshakeCompletedListener(listener: HandshakeCompletedListener?) {
            delegate.addHandshakeCompletedListener(listener)
        }

        override fun removeHandshakeCompletedListener(listener: HandshakeCompletedListener?) {
            delegate.removeHandshakeCompletedListener(listener)
        }

        override fun startHandshake() {
            delegate.startHandshake()
        }

        override fun setUseClientMode(mode: Boolean) {
            delegate.useClientMode = mode
        }

        override fun getUseClientMode(): Boolean = delegate.useClientMode

        override fun setNeedClientAuth(need: Boolean) {
            delegate.needClientAuth = need
        }

        override fun getNeedClientAuth(): Boolean = delegate.needClientAuth

        override fun setWantClientAuth(want: Boolean) {
            delegate.wantClientAuth = want
        }

        override fun getWantClientAuth(): Boolean = delegate.wantClientAuth

        override fun setEnableSessionCreation(flag: Boolean) {
            delegate.enableSessionCreation = flag
        }

        override fun getEnableSessionCreation(): Boolean = delegate.enableSessionCreation

        override fun getInputStream(): InputStream = delegate.inputStream

        override fun getKeepAlive(): Boolean = delegate.keepAlive

        override fun getLocalAddress(): InetAddress = delegate.localAddress

        override fun getLocalPort(): Int = delegate.localPort

        override fun getLocalSocketAddress(): SocketAddress = delegate.localSocketAddress

        override fun getOOBInline(): Boolean = delegate.oobInline

        override fun getOutputStream(): OutputStream = delegate.outputStream

        override fun getPort(): Int = delegate.localPort

        override fun getReceiveBufferSize(): Int = delegate.receiveBufferSize

        override fun getRemoteSocketAddress(): SocketAddress = delegate.remoteSocketAddress

        override fun getReuseAddress(): Boolean = delegate.reuseAddress

        override fun getSendBufferSize(): Int = delegate.sendBufferSize

        override fun getSoLinger(): Int = delegate.soLinger

        override fun getSoTimeout(): Int = delegate.soTimeout

        override fun getTcpNoDelay(): Boolean = delegate.tcpNoDelay

        override fun getTrafficClass(): Int = delegate.trafficClass

        override fun isBound(): Boolean = delegate.isBound

        override fun isClosed(): Boolean = delegate.isClosed

        override fun isConnected(): Boolean = delegate.isConnected

        override fun isInputShutdown(): Boolean = delegate.isInputShutdown

        override fun isOutputShutdown(): Boolean = delegate.isOutputShutdown

        override fun sendUrgentData(data: Int) = delegate.sendUrgentData(data)

        override fun setKeepAlive(on: Boolean) {
            delegate.keepAlive = on
        }

        override fun setOOBInline(on: Boolean) {
            delegate.oobInline = on
        }

        override fun setPerformancePreferences(connectionTime: Int, latency: Int, bandwidth: Int) {
            delegate.setPerformancePreferences(connectionTime, latency, bandwidth)
        }

        override fun setReceiveBufferSize(size: Int) {
            delegate.receiveBufferSize = size
        }

        override fun setReuseAddress(on: Boolean) {
            delegate.reuseAddress = on
        }

        override fun setSendBufferSize(size: Int) {
            delegate.receiveBufferSize = size
        }

        override fun setSoLinger(on: Boolean, linger: Int) {
            delegate.setSoLinger(on, linger)
        }

        override fun setSoTimeout(timeout: Int) {
            delegate.soTimeout = timeout
        }

        override fun setTcpNoDelay(on: Boolean) {
            delegate.tcpNoDelay = on
        }

        override fun setTrafficClass(tc: Int) {
            delegate.trafficClass = tc
        }

        override fun shutdownInput() {
            delegate.shutdownInput()
        }

        override fun shutdownOutput() {
            delegate.shutdownOutput()
        }

        override fun toString(): String = delegate.toString()

        override fun equals(other: Any?): Boolean = delegate == other
    }
}