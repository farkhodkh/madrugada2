package ru.evotor.pinpaddriver.external.api;

import ru.evotor.pinpaddriver.external.api.ExternalLowLevelApiCallbackInterface;


/**
 * Reserved for future use
 */
interface ExternalLowLevelApiInterface {
    oneway void sendCommand(in byte[] bytes, in ExternalLowLevelApiCallbackInterface callback);
}
