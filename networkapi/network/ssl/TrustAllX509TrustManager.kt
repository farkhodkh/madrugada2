package ru.petroplus.pos.network.ssl

import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class TrustAllX509TrustManager: X509TrustManager {

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        //Not required, trust all
    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        //Not required, trust all
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
}