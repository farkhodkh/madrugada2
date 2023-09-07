package ru.petrolplus.pos.util

import android.content.Context
import android.util.Patterns
import java.io.FileNotFoundException
import java.util.*

/**
 * Класс для проверки файла конвигурации терминала и для его чтения
 */
class ConfigurationFileReader(private val context: Context) {

    companion object {
        const val ACQUIRER_ID = "AcquireID"
        const val TERMINAL_ID = "AcquireID"
        const val HOST_PORT = "Host1_port"
        const val HOST_IP = "Host1_ip"
    }

    @Throws(NoConfigurationFileException::class)
    fun readConfiguration(configurationFile: String): Properties {

        val inputStream = try {
            context.openFileInput(configurationFile)
        } catch (e: FileNotFoundException) {
            throw NoConfigurationFileException("Не найден файл конфигурации по текущему пути")
        }

        val properties = try {
            Properties().apply { load(inputStream) }
        } catch (ex: Exception) {
            throw FileParseException(ex.localizedMessage.orEmpty())
        } finally {
            inputStream.close()
        }

        validateConfiguration(properties)

        return properties
    }

    /**
     * Все указанные проперти обязательно должны присутствовать и иметь значение
     * ??? Значения должны быть в определенных диапазонах
     * Названия параметров не должны повторяться (т.е. не должно быть нескольких параметров с названием AcquireID и т.д.)
     * Необходимо игнорировать закомментированные параметры с символом ';', например ";AcquireID"
     * Необходимо будет подумать над другими проверками
     */
    @Throws(NoConfigurationFileException::class)
    private fun validateConfiguration(prop: Properties) {

        var errorDetails = ""

        if (prop.isEmpty) errorDetails = "Properties empty!"

        fun checkIsPropExists(propName: String) {
            if (!prop.containsKey(propName)) {
                errorDetails = "$errorDetails Config file does not contain $propName property\n"
            }
            val propValue = prop.getValue(propName)
            if (propValue == null || propValue.equals("")) {
                errorDetails = "$errorDetails Config file does not contain value for property $propName\n"
            }
        }

        //AcquireID check
        checkIsPropExists(ACQUIRER_ID)
        val acquireID = prop.getValue(ACQUIRER_ID) as String
        try {
            acquireID.toInt()
        } catch (ex: Exception) {
            errorDetails = "$errorDetails $ACQUIRER_ID value error: ${ex.localizedMessage}"
        }

        //TerminalID check
        checkIsPropExists(TERMINAL_ID)
        val terminalID = prop.getValue("TerminalID") as String
        try {
            terminalID.toInt()
        } catch (ex: Exception) {
            errorDetails = "$errorDetails TerminalID value error: ${ex.localizedMessage}"
        }

        //Host1_ip check
        checkIsPropExists(HOST_IP)
        val host1Ip = prop.getValue("Host1_ip") as String
        val itCheck = Patterns
                .IP_ADDRESS
                .matcher(host1Ip)
                .matches()

        if (!itCheck) {
            errorDetails = "$errorDetails Host1_ip value error: $host1Ip"
        }

        //Host1_port check 0 до 65535
        checkIsPropExists(HOST_PORT)
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

        if (errorDetails.isNotEmpty()) throw FileParseException(errorDetails)
    }

    class FileParseException(detailedMessage: String): Exception(detailedMessage)
    class NoConfigurationFileException(detailedMessage: String): Exception(detailedMessage)
}