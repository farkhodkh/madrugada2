//
// Created by Yuriy.Poprygushin on 05.07.2023.
//
#ifndef POS_TYPES_CONVERSIONS_CPP
#define POS_TYPES_CONVERSIONS_CPP
//---------------------------------------------------------------------------------------------------------------

#include "TypesConversions.h"

//---------------------------------------------------------------------------------------------------------------

bool TP7LibTypes::JByteArrayToVector(JNIEnv *env, const jbyteArray &Array, std::vector<unsigned char> *Vector) {
  bool Result = false;
  if (env && Vector) {
    Vector->clear();
    const size_t DataLen = env->GetArrayLength(Array);
    if (DataLen) {
      env->GetByteArrayRegion(Array, 0, DataLen, reinterpret_cast<jbyte *>(Vector->data()));  }
    Result = true;
  } // if env && Vector
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::VectorToJByteArray(JNIEnv *env, const std::vector<unsigned char> &Vector, jbyteArray *Array) {
  bool Result = false;
  if (env && Array) {
    if (*Array) {
      env->DeleteLocalRef(*Array);
      *Array = nullptr;  }
    const jsize Len = static_cast<jsize>(Vector.size());
    *Array = env->NewByteArray(Len);
    env->SetByteArrayRegion(*Array, 0, Len, reinterpret_cast<const jbyte *>(Vector.data()));
    Result = true;
  } // if env && Array
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::JStringToString(JNIEnv *env, const jstring &jStr, std::string &Str) {
  bool Result = false;
  if (env) {
    jclass stringClass = env->GetObjectClass(jStr);
    jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes,
                                                                 env->NewStringUTF("UTF-8"));

    auto length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte *pBytes = env->GetByteArrayElements(stringJbytes, nullptr);

    Str = std::string((char *) pBytes, length);

    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);
    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    Result = true;
  }
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::StringToJString(JNIEnv *env, const std::string &Str, jstring &jStr) {
  bool Result = false;
  if (jStr) {
    env->DeleteLocalRef(jStr);
    jStr = nullptr;  }
  jStr = env->NewStringUTF(Str.c_str());
  Result = true;
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::DeleteLocalRef(JNIEnv *env, jobject *ApduAnswerJObj) {
  bool Result = false;
  if (env && ApduAnswerJObj) {
    if (*ApduAnswerJObj) {
      env->DeleteLocalRef(*ApduAnswerJObj);
      *ApduAnswerJObj = nullptr;  }
    Result = true;
  }
  return Result;
}
//--------------------------------------------------

TP7ErrorType TP7LibTypes::GetResultCode(JNIEnv *env, const jobject *ResultCodeJObj) {
  TP7ErrorType Result = TP7ErrorType::UndefinedError;

  if (env && ResultCodeJObj) {
    jclass   ResultCodeClass   = env->FindClass("ru/petroplus/pos/p7LibApi/dto/ResultCode");
    jfieldID ResultCodeValueID = env->GetFieldID(ResultCodeClass, "code", "I");
    jint     intVal            = env->GetIntField(*ResultCodeJObj, ResultCodeValueID);

    Result = static_cast<TP7ErrorType>(intVal);  }

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertResultCodeToJObj(JNIEnv *env,TP7ErrorType ResultCode, jobject *ResultCodeJObj) {
  bool Result = false;
  if (env && ResultCodeJObj) {
    if (*ResultCodeJObj) {
      env->DeleteLocalRef(*ResultCodeJObj);
      *ResultCodeJObj = NULL;  }

    jclass ResultClass = nullptr;
    switch (ResultCode) {
      case OK:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/OK");
      break;
      case AlreadyInitialized:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/AlreadyInitialized");
      break;
      case NonInitializedError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/NonInitializedError");
      break;
      case CardResetInitError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/CardResetInitError");
      break;
      case CardIoInitError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/CardIoInitError");
      break;
      case CardSelectError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/CardSelectError");
      break;
      case CardAuthError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/CardAuthError");
      break;
      case LoadIniError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/LoadIniError");
      break;
      case LibFatalError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/LibFatalError");
      break;
      case SequenceError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/SequenceError");
      break;
      case NotPetrol7Card:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/NotPetrol7Card");
      break;
      case CardReadError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/CardReadError");
      break;
      case SamGetError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/SamGetError");
      break;
      case PinDataError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/PinDataError");
      break;
      case PinCheckError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/PinCheckError");
      break;
      case DebitError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/DebitError");
      break;
      case ArgAmountPriceSumError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/ArgAmountPriceSumError");
      break;
      case ArgServiceError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/ArgServiceError");
      break;
      case ArgPinblockError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/ArgPinblockError");
      break;
      case ArgCardTypeJError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/ArgCardTypeJError");
      break;
      case RefundError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/RefundError");
      break;
      case SamResetInitError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/SamResetInitError");
      break;
      case SamIoInitError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/SamIoInitError");
      break;
      case SamSelectError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/SamSelectError");
      break;
      case SamAuthInitError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/SamAuthInitError");
      break;
      case SamLicenseError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/SamLicenseError");
      break;
      case NetworkModuleError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/NetworkModuleError");
      break;
      case UndefinedError:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/UndefinedError");
      break;
      default:
        ResultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/UndefinedError");
      break;
    }  // switch ResultCode

    if (ResultClass) {
      jmethodID ResultClassConstructor = env->GetMethodID(ResultClass, "<init>", "()V");
      *ResultCodeJObj = env->NewObject(ResultClass, ResultClassConstructor);
    } // if ResultClass

    Result = true;
  } // if env && ResultCodeJObj

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::CreateApduAnswerJObj(JNIEnv *env, jobject *ApduAnswerJObj) {
  bool Result = false;
  if (env && ApduAnswerJObj) {
    if (*ApduAnswerJObj) {
      DeleteLocalRef(env, ApduAnswerJObj);  }

    jclass ApduAnswerClass = env->FindClass("ru/petroplus/pos/p7LibApi/responces/ApduAnswer");
    jmethodID ApduAnswerConstructorID = env->GetMethodID(ApduAnswerClass, "<init>", "(II[B)V");
    *ApduAnswerJObj = env->NewObject(ApduAnswerClass, ApduAnswerConstructorID,
                                     0, 0, jbyteArray());
    Result = true;
  } // if env && ApduAnswerJObj
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertApduAnswerFromJObj(JNIEnv *env, const jobject *ApduAnswerJObj, TAPDUAnswer *APDUAnswer) {
  bool Result = false;
  if (env && ApduAnswerJObj && APDUAnswer) {
    jclass ApduAnswerClass = env->FindClass("ru/petroplus/pos/p7LibApi/responces/ApduAnswer");

    jfieldID SW1_ID  = env->GetFieldID(ApduAnswerClass, "sw1", "I");
    jfieldID SW2_ID  = env->GetFieldID(ApduAnswerClass, "sw2", "I");
    jfieldID Data_ID = env->GetFieldID(ApduAnswerClass, "data", "[B");

    jint jSW1 = env->GetIntField(*ApduAnswerJObj, SW1_ID);
    jint jSW2 = env->GetIntField(*ApduAnswerJObj, SW2_ID);
    jbyteArray jData = (jbyteArray)(env->GetObjectField(*ApduAnswerJObj, Data_ID));

    APDUAnswer->SW1 = jSW1;
    APDUAnswer->SW2 = jSW2;
    TP7LibTypes::JByteArrayToVector(env, jData, &APDUAnswer->Data);
    Result = true;
  } // if env && ApduAnswerJObj && APDUAnswer
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::CreateApduDataJObj(JNIEnv *env, jobject *ApduDataJObj) {
  bool Result = false;
  if (env && ApduDataJObj) {
    if (*ApduDataJObj) {
      DeleteLocalRef(env, ApduDataJObj);
      ApduDataJObj = nullptr;  }

    jclass ApduDataClass = env->FindClass("ru/petroplus/pos/p7LibApi/requests/ApduData");
    jmethodID ApduDataConstructorID = env->GetMethodID(ApduDataClass, "<init>", "(BBBBB[BB)V");
    *ApduDataJObj = env->NewObject(ApduDataClass, ApduDataConstructorID,
                                     0, 0,  0, 0, 0, jbyteArray(), 0);
    Result = true;
  } // if env && ApduDataJObj
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertApduDataToJObj(JNIEnv *env, const TAPDUData &APDUData, jobject *ApduDataJObj) {
  bool Result = false;
  if (env && ApduDataJObj) {
    if (*ApduDataJObj) {
      DeleteLocalRef(env, ApduDataJObj);
      ApduDataJObj = nullptr;  }

    jclass ApduDataClass = env->FindClass("ru/petroplus/pos/p7LibApi/requests/ApduData");
    CreateApduDataJObj(env, ApduDataJObj);

    jfieldID GLA_ID  = env->GetFieldID(ApduDataClass, "GLA", "B");
    jfieldID INS_ID  = env->GetFieldID(ApduDataClass, "INS", "B");
    jfieldID P1_ID  = env->GetFieldID(ApduDataClass, "P1", "B");
    jfieldID P2_ID  = env->GetFieldID(ApduDataClass, "P2", "B");
    jfieldID LC_ID  = env->GetFieldID(ApduDataClass, "LC", "B");
    jfieldID Data_ID = env->GetFieldID(ApduDataClass, "Data", "[B");
    jfieldID LE_ID  = env->GetFieldID(ApduDataClass, "LE", "B");

    env->SetByteField(*ApduDataJObj, GLA_ID, APDUData.CLA);
    env->SetByteField(*ApduDataJObj, INS_ID, APDUData.INS);
    env->SetByteField(*ApduDataJObj, P1_ID, APDUData.P1);
    env->SetByteField(*ApduDataJObj, P2_ID, APDUData.P2);
    env->SetByteField(*ApduDataJObj, GLA_ID, APDUData.LC);
    env->SetByteField(*ApduDataJObj, LE_ID, APDUData.LE);

    jbyteArray Array = nullptr;
    VectorToJByteArray(env, APDUData.Data, &Array);
    env->SetObjectField(*ApduDataJObj, Data_ID, Array);
    env->DeleteLocalRef(Array);
    Array = nullptr;

    Result = true;
  } // if env && ApduDataJObj

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertIniDataFromJObj(JNIEnv *env, const jobject *IniDataJObj, TIniData *IniData) {
  bool Result = false;
  if (env && IniDataJObj && IniData) {
    jclass IniDataClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/InitDataDto");

    jfieldID acquirerId_ID = env->GetFieldID(IniDataClass, "acquirerId", "I");
    jfieldID terminalId_ID = env->GetFieldID(IniDataClass, "terminalId", "I");
    jfieldID hostIp_ID     = env->GetFieldID(IniDataClass, "hostIp", "Ljava/lang/String;");
    jfieldID hostPort_ID   = env->GetFieldID(IniDataClass, "hostPort", "I");

    IniData->AcquireID  = env->GetIntField(*IniDataJObj, acquirerId_ID);
    IniData->TerminalID = env->GetIntField(*IniDataJObj, terminalId_ID);
    JStringToString(env, (jstring)(env->GetObjectField(*IniDataJObj, hostIp_ID)), IniData->Host_ip);
    IniData->Host_port  = env->GetIntField(*IniDataJObj, hostPort_ID);

    Result = true;
  } // if env && IniDataJObj && IniData

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertTransactionUUIDFromJObj(JNIEnv *env, const jobject *UUIDJObj, TTransactionUUID *UUID) {
  bool Result = false;
  if (env && UUIDJObj && UUID) {
    jclass UUIDDClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/TransactionUUIDDto");

    jfieldID onlineTransNumber_ID = env->GetFieldID(UUIDDClass, "onlineTransNumber", "I");
    jfieldID lastGenTime_ID       = env->GetFieldID(UUIDDClass, "lastGenTime", "J");
    jfieldID clockSequence_ID     = env->GetFieldID(UUIDDClass, "clockSequence", "I");
    jfieldID hasNodeId_ID         = env->GetFieldID(UUIDDClass, "hasNodeId", "Z");
    jfieldID nodeId_ID            = env->GetFieldID(UUIDDClass, "nodeId", "Ljava/lang/String;");

    UUID->OnlineTransNumber = env->GetIntField(*UUIDJObj, onlineTransNumber_ID);
    UUID->LastGenTime       = env->GetLongField(*UUIDJObj, lastGenTime_ID);
    UUID->ClockSequence     = env->GetIntField(*UUIDJObj, clockSequence_ID);
    UUID->hasNodeID         = env->GetBooleanField(*UUIDJObj, hasNodeId_ID);
    JStringToString(env, (jstring)(env->GetObjectField(*UUIDJObj, nodeId_ID)), UUID->NodeID);

    Result = true;
  } // if env && IniDataJObj && IniData

  return Result;
}
//--------------------------------------------------





//---------------------------------------------------------------------------------------------------------------
#endif //POS_TYPES_CONVERSIONS_CPP