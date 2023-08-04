package ru.petroplus.pos.network.ssl

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
    private lateinit var delegate: SSLSocketFactory

    init {
        delegate = HttpsURLConnection.getDefaultSSLSocketFactory()
    }

    override fun createSocket(s: Socket?, host: String?, port: Int, autoClose: Boolean): Socket {
        TODO("Not yet implemented")
    }

    override fun createSocket(host: String?, port: Int): Socket {
        TODO("Not yet implemented")
    }

    override fun createSocket(
        host: String?,
        port: Int,
        localHost: InetAddress?,
        localPort: Int
    ): Socket {
        TODO("Not yet implemented")
    }

    override fun createSocket(host: InetAddress?, port: Int): Socket {
        TODO("Not yet implemented")
    }

    override fun createSocket(
        address: InetAddress?,
        port: Int,
        localAddress: InetAddress?,
        localPort: Int
    ): Socket {
        TODO("Not yet implemented")
    }

    override fun getDefaultCipherSuites(): Array<String> = delegate.defaultCipherSuites

    override fun getSupportedCipherSuites(): Array<String> = delegate.supportedCipherSuites

//    fun makeSocketSafe(safeSocket: Socket) {
//        if (safeSocket is SSLSocket) {
//            socket = NoSSLv3SSLSocket(safeSocket as SSLSocket)
//        }
//        return socket;
//    }


        internal class NoSSLv3SSLSocket: DelegateSSLSocket() {
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

    internal open class DelegateSSLSocket: SSLSocket() {
        private lateinit var delegate: SSLSocket

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

    }
}