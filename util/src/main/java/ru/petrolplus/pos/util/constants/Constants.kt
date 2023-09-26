package ru.petrolplus.pos.util.constants

object Constants {
    const val GATEWAY_SERVER_ADDRESS_AND_PORT = "https://91.240.172.195:6567"
    const val CONFIG_FILE_NAME = "config.ini"
    const val DEMO_CONFIGURATION = "AcquireID = 4000\n" +
            "TerminalID = 12\n" +
            "\n" +
            "[Authorization]\n" +
            ";Host1_ip = 91.240.172.69\n" +
            ";Host1_port = 6567\n" +
            ";Host1_ip = 127.0.0.1\n" +
            ";Host1_ip = 91.240.172.192\n" +
            "Host1_ip = 91.240.172.195\n" +
            "Host1_port = 6567\n" +
            "\n" +
            "[AuthData]\n" +
            "AuthPin = \"000000000000\"\n" +
            "LastOnlineTrz=157\n" +
            "\n" +
            "[Signature]\n" +
            "Data = \"\"\n" +
            "\n" +
            "[UUID]\n" +
            "LastGenTime=1685106271\n" +
            "ClockSequence=2331\n" +
            "HasNodeID=1\n" +
            "NodeID=01B652966F64"
}