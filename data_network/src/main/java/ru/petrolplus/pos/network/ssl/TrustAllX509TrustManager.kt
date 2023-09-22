package ru.petrolplus.pos.network.ssl

import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

/**
 * X509TrustManager принимающий/разрешающий всех издателей ключей
 */
class TrustAllX509TrustManager: X509TrustManager {

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit

    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
}