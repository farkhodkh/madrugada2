package ru.petroplus.pos.network.ssl

import android.util.Base64
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext

/**
 *   Factory для создания SSL соединения со шлюзом (F5)
 */
class SSLContextFactory {

    private val sslContextProtocolType = "TLS"
    private val ketStoreType = "PKCS12"
    private val keyManagerAlgorithm = "X509"
    private val certificateFactoryType = "X.509"

    companion object {
        private var instance: SSLContextFactory? = null

        fun getFactoryInstance(): SSLContextFactory {
            if (instance == null) {
                instance = SSLContextFactory()
            }

            return instance as SSLContextFactory
        }
    }

    /**
     * Создание SSLContext с клиентскими и серверными сертификатами
     * @param clientCertFile Файл содержащий клиентский сертификат
     * @param clientCertPassword Пароль для клиентского сертификата
     * @param caCertString Строка содержащая серверный сертификат
     * @return Инициализированный SSLContext
     */
    fun makeContext(
        clientCertFile: File,
        clientCertPassword: String,
        caCertString: String,
        trustAllCerts: Array<TrustAllX509TrustManager>
    ): SSLContext {
        val keyStore = loadPKCS12KeyStore(clientCertFile, clientCertPassword)
        val kmf = KeyManagerFactory.getInstance(keyManagerAlgorithm)

        kmf.init(keyStore, clientCertPassword.toCharArray())
        val keyManagers = kmf.keyManagers

        /**
         * Для отладки пока нужен
         */
        val trustStore = loadPEMTrustStore(caCertString)

        val sslContext = SSLContext.getInstance(sslContextProtocolType)
        sslContext.init(keyManagers, trustAllCerts, null)

        return sslContext
    }

    private fun loadPKCS12KeyStore(certificateFile: File, clientCertPassword: String): KeyStore {
        val keyStore: KeyStore
        var fis: FileInputStream? = null

        try {
            keyStore = KeyStore.getInstance(ketStoreType)
            fis = FileInputStream(certificateFile)
            keyStore.load(fis, clientCertPassword.toCharArray())
        } catch (ex: Exception) {
            throw ex
        } finally {
            try {
                fis?.close()
            } catch (ex: IOException) {
                // Nothing to do
            }
        }

        return keyStore
    }

    /**
     * Produces a KeyStore from a String containing a PEM certificate (typically, the server's CA certificate)
     * @param certificateString A String containing the PEM-encoded certificate
     * @return a KeyStore (to be used as a trust store) that contains the certificate
     * @throws Exception
     */
    private fun loadPEMTrustStore(certificateString: String): KeyStore {
        val der = loadPemCertificate(ByteArrayInputStream(certificateString.toByteArray()))
        val derInputStream = ByteArrayInputStream(der)
        val certificateFactory = CertificateFactory.getInstance(certificateFactoryType)
        val cert = certificateFactory.generateCertificate(derInputStream) as X509Certificate
        val alias = cert.subjectX500Principal.name

        val trustStore = KeyStore.getInstance(KeyStore.getDefaultType())
        trustStore.load(null)
        trustStore.setCertificateEntry(alias, cert)

        return trustStore
    }

    private fun loadPemCertificate(certificateStream: InputStream): ByteArray {
        val resultArray: ByteArray
        var bufferedReader: BufferedReader? = null

        try {
            val stringBuilder = StringBuilder()
            bufferedReader = BufferedReader(InputStreamReader(certificateStream))
            var line = bufferedReader.readLine()
            while (line != null) {
                if (!line.startsWith("--")) {
                    stringBuilder.append(line)
                }
                line = bufferedReader.readLine()
            }

            resultArray = Base64.decode(stringBuilder.toString(), Base64.DEFAULT)
        } finally {
            bufferedReader?.close()
        }

        return resultArray
    }
}