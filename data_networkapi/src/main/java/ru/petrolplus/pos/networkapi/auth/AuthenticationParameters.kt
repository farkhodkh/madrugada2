package ru.petrolplus.pos.networkapi.auth

import java.io.File

/* Класс с параметрами для идентификации по SSL
* @param clientCertificate файл с клиентским сертификатом
* @param clientCertificatePassword пароль от клиентского сертификата
* @param caCertificate Строка содержащая серверный сертификат
*/
class AuthenticationParameters(
    val clientCertificate: File,
    val clientCertificatePassword: String,
    val caCertificate: String
)