package ru.petroplus.pos.network.auth

import java.io.File

class AuthenticationParameters {
    lateinit var clientCertificate: File
    val clientCertificatePassword: String = "1234"
    var caCertificate: String = ""
}