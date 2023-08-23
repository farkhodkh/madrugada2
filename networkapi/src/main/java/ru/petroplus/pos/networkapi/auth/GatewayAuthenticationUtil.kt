package ru.petroplus.pos.networkapi.auth

import ru.petroplus.pos.util.ResourceHelper
import java.io.File
import java.io.IOException

/**
 * Класс для инициализации параметров для аутентификации на шлюзе (F5)
 */
object GatewayAuthenticationUtil {
    private val clientCertificatePassword = "1234"
    private val caCertificateName = "master-cacert.pem"
    private val clientCertificateName = "client_cert.pfx"
    private val pingFileName = "PingP7.bin"

    fun gateGatewayAuthenticationParams(): AuthenticationParameters = AuthenticationParameters(
        getClientCertificateFile(),
        clientCertificatePassword,
        getCaCert()
    )

    private fun getClientCertificateFile(): File =
        ResourceHelper.getAssetFile(clientCertificateName)
            ?: throw IOException("Не удалось обнаружить клиентский сертификат")

    private fun getCaCert(): String = ResourceHelper.readAssetContent(caCertificateName)
        ?: throw IOException("Не удалось обнаружить серверный сертификат")

    fun getPingBinFile(): File = ResourceHelper.getAssetFile(pingFileName) ?: throw IOException("Не удалось обнаружить файл данных для Ping")
}