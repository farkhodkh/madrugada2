package ru.petroplus.pos.mainscreen.ui.debit

import android.os.Bundle
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import ru.petroplus.pos.mainscreen.ui.debit.http.SSLConnectionExample
import ru.petroplus.pos.networkapi.GatewayServerApi
import ru.petroplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petroplus.pos.sdkapi.CardReaderRepository
import ru.petroplus.pos.ui.BuildConfig

class DebitViewModel(
    private val cardReaderRepository: CardReaderRepository,
    private val gatewayServer: GatewayServerRepositoryApi,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = mutableStateOf<DebitViewState>(DebitViewState.StartingState)
    val viewState: State<DebitViewState> = _viewState

    init {
        viewModelScope.launch {
            cardReaderRepository
                .sdkRepository
                .eventBus
                .events
                .collectIndexed { index, value ->
                    _viewState.value = DebitViewState
                        .CommandExecutionState(value)
                }
        }

        if (BuildConfig.DEBUG) {
            _viewState.value = DebitViewState.DebugState
        }
    }

    fun ping() {
        viewModelScope.launch(Dispatchers.IO) {
            gatewayServer.doPing()
        }
    }

    fun sendCommand(command: String) {
        //APDU для выбора апплета
        //
        //00A4040008A000000003000000
        //
        //cla 00
        //ins a4
        //p1 04
        //p2 00
        //Lc 08
        //Data A000000003000000

        //5342520101


        //Пин без карты

//        m_App.m_PublicKey
//        0x96 0x25 0xb4 0x00 0x44 0xaf 0x2c 0xd8 0x05 0x4c 0x34 0x08 0x3c 0xd5 0x9c 0x5b 0x51 0xf3 0xcc 0x4e 0x4d 0xa1 0xe7 0xfe 0x01 0x21 0xd6 0x64 0xbc 0x1d 0xf3 0xe5 0xb7 0x0c 0xe2 0x65 0x93 0x76 0x70 0x10 0xe2 0x65 0x15 0xf3 0xb5 0xb3 0xec 0xc5 0x85 0x2e 0x0e 0xc1 0xf9 0x7a 0x56 0x34 0xfc 0x1b 0x58 0x92 0xe2 0x02 0x10 0x76 0xb0 0x1f 0xfb 0x8d 0x7a 0x3b 0x0e 0xb8 0x73 0x2b 0x10 0xb3 0x76 0x71 0x4f 0xc6 0xc5 0xea 0xbe 0x39 0xf3 0xcb 0x37 0xcd 0xec 0x1b 0xcc 0x91 0x2d 0x71 0x36 0xaf 0xf4 0x55 0xd2 0x0e 0xf1 0x3c 0xd0 0x07 0x64 0x11 0xa6 0x48 0xe0 0x2d 0xb8 0x65 0xe0 0x5b 0xa8 0x36 0xbf 0x6e 0xaf 0x3b 0xb2 0x66 0x6e 0x67 0xc7 0x71 0x63 0xab
//
//        m_App.m_PublicKeySize
//        128
//
//        m_App.m_PublicExp
//        0x01 0x00 0x01
//
//        m_App.m_PublicExpSize
//        3
//
//        m_App.m_Nonce
//        0xf9 0x59 0x7e 0x10 0xf2 0x5d 0xae 0x43
//
//        m_App.m_NonceSize
//        8

        //0x06	    N1	PinMode: 1:OnLine, 2:OfflineEnc
        //0x5A	    P19	Full PAN (BCD, выровненный до 19 байт символами FF)
        // 5449669465469641
        // 7013330040009998207F
        // 0x70 0x13 0x33 0x00 0x40 0x00 0x99 0x98 0x20 0x7F ????
        //0x9F02	N12	Amount
        //0x9F03	N12	Amount 2
        //0x9F2D	BIN(0…256)	RSA public key (modulus) (Присутствует для Offline Encrypted PIN)
        //0x9F2E	BIN3	RSA Exponent (Присутствует для Offline Encrypted PIN)
        //0x1A	BIN8	RSA Random nonce (Присутствует для Offline Encrypted PIN)

        //250601025A0854496694654696419f02060000000011221A0811223344556677889F2E030100019F2D8180BEDB6B21E12D2B6EB590EC129FCC847EA4C00BE41CA530A5FA2CCDDE3B7DB3A0F50E6D3E348CD9258D7076973DD01FDC5B7E00F1F714F4E55C650DF88AAA293ED9376D2B0905F589108FB5C2835EA025D036F369891E5F5F3BA4F5E96CF25D164C1B26215B8D9627CDB95C22F00EBF50DF821A984E01309C1677B5D013E2BEEF000000000006
        //
//        25 - код команды
//        06 - тег Pinmode
//        01 - размер Pinmode
//        02 - offlineencripted
//        5A - тег PAN
//        08 - длина PAN
//        5449669465469641 - PAN
//        9f02 - тег сумма транзакции
//        06 - длина суммы
//        000000001122 - сумма
//        1A - тег RSA Random
//        08 - длина данных тега RSA Random
//        1122334455667788 - Random
//        9F2E - тег экспоненты
//        03 - длина экспоненты
//        010001 - экспонента
//        9F2D - RSA public
//        8180 - длина RSA public 128 байт
//        BEDB6B21E12D2B6EB590EC129FCC847EA4C00BE41CA530A5FA2CCDDE3B7DB3A0F50E6D3E348CD9258D7076973DD01FDC5B7E00F1F714F4E55C650DF88AAA293ED9376D2B0905F589108FB5C2835EA025D036F369891E5F5F3BA4F5E96CF25D164C1B26215B8D9627CDB95C22F00EBF50DF821A984E01309C1677B5D013E2BEEF
//
//        000000000006 - выравнивание

        //9625b40044af2cd8054c34083cd59c5b51f3cc4e4da1e7fe0121d664bc1df3e5b70ce26593767010e26515f3b5b3ecc5852e0ec1f97a5634fc1b5892e2021076b01ffb8d7a3b0eb8732b10b376714fc6c5eabe39f3cb37cdec1bcc912d7136aff455d20ef13cd0076411a648e02db865e05ba836bf6eaf3bb2666e67c77163ab

        //SBR
        //534252

        val command1 =
            "250601025A0854496694654696419f02060000000011221A0811223344556677889F2E030100019F2D8180BEDB6B21E12D2B6EB590EC129FCC847EA4C00BE41CA530A5FA2CCDDE3B7DB3A0F50E6D3E348CD9258D7076973DD01FDC5B7E00F1F714F4E55C650DF88AAA293ED9376D2B0905F589108FB5C2835EA025D036F369891E5F5F3BA4F5E96CF25D164C1B26215B8D9627CDB95C22F00EBF50DF821A984E01309C1677B5D013E2BEEF000000000006"
//        val command1 = "00"
        cardReaderRepository.sdkRepository.sendCommand(command)
    }

    companion object {
        fun provideFactory(
            cardReaderRepository: CardReaderRepository,
            gatewayServer: GatewayServerRepositoryApi,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return DebitViewModel(cardReaderRepository, gatewayServer, handle) as T
                }
            }
    }
}