package ru.petroplus.pos.util

import android.util.Patterns
import ru.petroplus.pos.util.ext.isNotConfigurationCommentedLine
import java.io.FileInputStream
import java.io.InputStream
import java.util.*

/**
 * Класс для проверки файла конвигурации терминала и для его чтения
 */
class ConfigurationFileReader() {

    var properties: Properties = Properties()
    private lateinit var inputStream: InputStream

    private val REQUIRED_FIELDS_LITS = listOf("AcquireID", "TerminalID", "Host1_ip", "Host1_port")

    @Throws(ConfigurationFileReaderException::class)
    fun readConfigurationFileContent(configurationFile: FileInputStream) {
        try {
            inputStream = configurationFile
            properties.load(inputStream)
        } catch (ex: Exception) {
            throw ConfigurationFileReaderException(true, ex.localizedMessage.orEmpty())
        }
    }

    private fun getConfigurationContent() = properties
        .filter {
            !(it.key as String).isNotConfigurationCommentedLine()
        }

    /**
     * Все указанные проперти обязательно должны присутствовать и иметь значение
     * ??? Значения должны быть в определенных диапазонах
     * Названия параметров не должны повторяться (т.е. не должно быть нескольких параметров с названием AcquireID и т.д.)
     * Необходимо игнорировать закомментированные параметры с символом ';', например ";AcquireID"
     * Необходимо будет подумать над другими проверками
     */
    @Throws(ConfigurationFileReaderException::class)
    fun checkConfigurationFileContent() {

        var errorDetails = ""
        val prop = getConfigurationContent()

        if (prop.isEmpty()) {
            errorDetails = "Properties empty!"
        }

        REQUIRED_FIELDS_LITS.forEach {
            if (!prop.containsKey(it)) {
                errorDetails = "$errorDetails Config file does not contain $it property\n"
            }
            val propValue = prop.getValue(it)
            if (propValue == null || propValue.equals("")) {
                errorDetails = "$errorDetails Config file does not contain value for property $it\n"
            }
        }

        //AcquireID check
        val acquireID = prop.getValue("AcquireID") as String
        try {
            acquireID.toInt()
        } catch (ex: Exception) {
            errorDetails = "$errorDetails AcquireID value error: ${ex.localizedMessage}"
        }

        //TerminalID check
        val terminalID = prop.getValue("TerminalID") as String
        try {
            terminalID.toInt()
        } catch (ex: Exception) {
            errorDetails = "$errorDetails TerminalID value error: ${ex.localizedMessage}"
        }

        //Host1_ip check
        val host1Ip = prop.getValue("Host1_ip") as String
        val itCheck = Patterns
                .IP_ADDRESS
                .matcher(host1Ip)
                .matches()

        if (!itCheck) {
            errorDetails = "$errorDetails Host1_ip value error: $host1Ip"
        }

        //Host1_port check 0 до 65535
        val host1Port = prop.getValue("Host1_port") as String

        var portInt: Int = -1
        try {
            portInt = host1Port.toInt()
        } catch (ex: Exception) {
            errorDetails = "$errorDetails Host1_port value error: ${ex.localizedMessage}"
        }

        if (portInt !in 0..65535) {
            errorDetails = "$errorDetails Host1_port value error: $host1Port"
        }
        throw ConfigurationFileReaderException(errorDetails.isNotEmpty(), errorDetails)
    }

    class ConfigurationFileReaderException(val hasError: Boolean, detailedMessage: String): Exception(detailedMessage)
}