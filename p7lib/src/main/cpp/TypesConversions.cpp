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
    Vector->resize(DataLen);
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
    Result = (*Array != nullptr);
    if (Result) {
      env->SetByteArrayRegion(*Array, 0, Len, reinterpret_cast<const jbyte *>(Vector.data()));  }
  } // if env && Array
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ArrayToJByteArray(JNIEnv *env, const unsigned char *Data, int DataSize, jbyteArray *Array) {
  bool Result = false;
  if (env && Data && DataSize && Array) {
    if (*Array) {
      env->DeleteLocalRef(*Array);
      *Array = nullptr;  }
    *Array = env->NewByteArray(DataSize);
    Result = (*Array != nullptr);
    if (Result) {
      env->SetByteArrayRegion(*Array, 0, DataSize, reinterpret_cast<const jbyte *>(Data));  }
  } // if env && Data && DataSize && Array
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
    jclass   ResultCodeClass   = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/ResultCode");
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
      *ResultCodeJObj = nullptr;  }

    jclass ResultClass = nullptr;
    switch (ResultCode) {
      case OK:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/OK");
      break;
      case AlreadyInitialized:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/AlreadyInitialized");
      break;
      case NonInitializedError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/NonInitializedError");
      break;
      case CardResetInitError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/CardResetInitError");
      break;
      case CardIoInitError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/CardIoInitError");
      break;
      case CardSelectError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/CardSelectError");
      break;
      case CardAuthError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/CardAuthError");
      break;
      case LoadIniError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/LoadIniError");
      break;
      case LibFatalError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/LibFatalError");
      break;
      case SequenceError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/SequenceError");
      break;
      case NotPetrol7Card:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/NotPetrol7Card");
      break;
      case CardReadError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/CardReadError");
      break;
      case SamGetError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/SamGetError");
      break;
      case PinDataError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/PinDataError");
      break;
      case PinCheckError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/PinCheckError");
      break;
      case DebitError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/DebitError");
      break;
      case ArgAmountPriceSumError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/ArgAmountPriceSumError");
      break;
      case ArgServiceError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/ArgServiceError");
      break;
      case ArgPinblockError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/ArgPinblockError");
      break;
      case ArgCardTypeJError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/ArgCardTypeJError");
      break;
      case RefundError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/RefundError");
      break;
      case SamResetInitError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/SamResetInitError");
      break;
      case SamIoInitError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/SamIoInitError");
      break;
      case SamSelectError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/SamSelectError");
      break;
      case SamAuthInitError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/SamAuthInitError");
      break;
      case SamLicenseError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/SamLicenseError");
      break;
      case NetworkModuleError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/NetworkModuleError");
      break;
      case UndefinedError:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/UndefinedError");
      break;
      default:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/UndefinedError");
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

    jclass ApduAnswerClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/ApduAnswer");
    jmethodID ApduAnswerConstructorID = env->GetMethodID(ApduAnswerClass, "<init>", "()V");
    *ApduAnswerJObj = env->NewObject(ApduAnswerClass, ApduAnswerConstructorID);
    Result = true;
  } // if env && ApduAnswerJObj
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertApduAnswerFromJObj(JNIEnv *env, const jobject *ApduAnswerJObj, TAPDUAnswer *APDUAnswer) {
  bool Result = false;
  if (env && ApduAnswerJObj && APDUAnswer) {
    jclass ApduAnswerClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/ApduAnswer");

    jfieldID SW1_ID  = env->GetFieldID(ApduAnswerClass, "sw1", "I");
    jfieldID SW2_ID  = env->GetFieldID(ApduAnswerClass, "sw2", "I");
    jfieldID Data_ID = env->GetFieldID(ApduAnswerClass, "data", "[B");

    jint jSW1 = env->GetIntField(*ApduAnswerJObj, SW1_ID);
    jint jSW2 = env->GetIntField(*ApduAnswerJObj, SW2_ID);
    jbyteArray jData = (jbyteArray)(env->GetObjectField(*ApduAnswerJObj, Data_ID));

    APDUAnswer->SW1 = jSW1;
    APDUAnswer->SW2 = jSW2;
    Result = JByteArrayToVector(env, jData, &APDUAnswer->Data);
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

    jclass ApduDataClass = env->FindClass("ru/petrolplus/pos/p7LibApi/requests/ApduData");
    jmethodID ApduDataConstructorID = env->GetMethodID(ApduDataClass, "<init>", "()V");
    *ApduDataJObj = env->NewObject(ApduDataClass, ApduDataConstructorID);
    Result = true;
  } // if env && ApduDataJObj
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertApduDataToJObj(JNIEnv *env, const TAPDUData *APDUData, jobject *ApduDataJObj) {
  bool Result = false;
  if (env && APDUData && ApduDataJObj) {
    if (*ApduDataJObj) {
      DeleteLocalRef(env, ApduDataJObj);
      ApduDataJObj = nullptr;  }
    CreateApduDataJObj(env, ApduDataJObj);

    jclass ApduDataClass = env->FindClass("ru/petrolplus/pos/p7LibApi/requests/ApduData");

    jfieldID GLA_ID  = env->GetFieldID(ApduDataClass, "GLA", "B");
    jfieldID INS_ID  = env->GetFieldID(ApduDataClass, "INS", "B");
    jfieldID P1_ID  = env->GetFieldID(ApduDataClass, "P1", "B");
    jfieldID P2_ID  = env->GetFieldID(ApduDataClass, "P2", "B");
    jfieldID LC_ID  = env->GetFieldID(ApduDataClass, "LC", "B");
    jfieldID Data_ID = env->GetFieldID(ApduDataClass, "Data", "[B");
    jfieldID LE_ID  = env->GetFieldID(ApduDataClass, "LE", "B");

    env->SetByteField(*ApduDataJObj, GLA_ID, APDUData->CLA);
    env->SetByteField(*ApduDataJObj, INS_ID, APDUData->INS);
    env->SetByteField(*ApduDataJObj, P1_ID, APDUData->P1);
    env->SetByteField(*ApduDataJObj, P2_ID, APDUData->P2);
    env->SetByteField(*ApduDataJObj, GLA_ID, APDUData->LC);
    env->SetByteField(*ApduDataJObj, LE_ID, APDUData->LE);

    jbyteArray Array = nullptr;
    Result = VectorToJByteArray(env, APDUData->Data, &Array);
    env->SetObjectField(*ApduDataJObj, Data_ID, Array);
    env->DeleteLocalRef(Array);
    Array = nullptr;
  } // if env && ApduDataJObj

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertIniDataFromJObj(JNIEnv *env, const jobject *IniDataJObj, TIniData *IniData) {
  bool Result = false;
  if (env && IniDataJObj && IniData) {
    jclass IniDataClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/InitDataDto");

    jfieldID acquirerId_ID = env->GetFieldID(IniDataClass, "acquirerId", "I");
    jfieldID terminalId_ID = env->GetFieldID(IniDataClass, "terminalId", "I");
    jfieldID hostIp_ID     = env->GetFieldID(IniDataClass, "hostIp", "Ljava/lang/String;");
    jfieldID hostPort_ID   = env->GetFieldID(IniDataClass, "hostPort", "I");

    IniData->AcquireID  = env->GetIntField(*IniDataJObj, acquirerId_ID);
    IniData->TerminalID = env->GetIntField(*IniDataJObj, terminalId_ID);
    IniData->Host_port  = env->GetIntField(*IniDataJObj, hostPort_ID);
    Result = JStringToString(env, (jstring)(env->GetObjectField(*IniDataJObj, hostIp_ID)), IniData->Host_ip);
  } // if env && IniDataJObj && IniData

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::CreateTransactionUUIDJObj(JNIEnv *env, jobject *UUIDJObj) {
  bool Result = false;
  if (env && UUIDJObj) {
    if (*UUIDJObj) {
      DeleteLocalRef(env, UUIDJObj);
      *UUIDJObj = nullptr;  }

    jclass UUIDClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/TransactionUUIDDto");
    jmethodID UUIDConstructorID = env->GetMethodID(UUIDClass, "<init>", "()V");
    *UUIDJObj = env->NewObject(UUIDClass, UUIDConstructorID);
    Result = true;
  } // if env && UUIDJObj
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertTransactionUUIDFromJObj(JNIEnv *env, const jobject *UUIDJObj, TTransactionUUID *UUID) {
  bool Result = false;
  if (env && UUIDJObj && UUID) {
    jclass UUIDDClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/TransactionUUIDDto");

    jfieldID onlineTransNumber_ID = env->GetFieldID(UUIDDClass, "onlineTransNumber", "I");
    jfieldID lastGenTime_ID       = env->GetFieldID(UUIDDClass, "lastGenTime", "I");  // "J"
    jfieldID clockSequence_ID     = env->GetFieldID(UUIDDClass, "clockSequence", "I");
    jfieldID hasNodeId_ID         = env->GetFieldID(UUIDDClass, "hasNodeId", "Z");
    jfieldID nodeId_ID            = env->GetFieldID(UUIDDClass, "nodeId", "[B");
    Result = (onlineTransNumber_ID && lastGenTime_ID && clockSequence_ID && hasNodeId_ID && nodeId_ID);

    if (Result) {
      UUID->OnlineTransNumber = env->GetIntField(*UUIDJObj, onlineTransNumber_ID);
      UUID->LastGenTime = env->GetIntField(*UUIDJObj, lastGenTime_ID);
      UUID->ClockSequence = env->GetIntField(*UUIDJObj, clockSequence_ID);
      UUID->hasNodeID = env->GetBooleanField(*UUIDJObj, hasNodeId_ID);

      std::vector<unsigned char> NodeID;
      Result = JByteArrayToVector(env, (jbyteArray) (env->GetObjectField(*UUIDJObj, nodeId_ID)),
                                  &NodeID);
      if (Result) {
        memset(UUID->NodeID, 0x00, sizeof(UUID->NodeID));
        memcpy(UUID->NodeID, NodeID.data(),
               ((sizeof(UUID->NodeID) < NodeID.size()) ? sizeof(UUID->NodeID) : NodeID.size()));
      } // if Result
    } // if Result
  } // if env && IniDataJObj && IniData

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertTransactionUUIDToJObj(JNIEnv *env, const TTransactionUUID *UUID, jobject *UUIDJObj) {
  bool Result = false;
  if (env && UUIDJObj && UUID) {
    Result = CreateTransactionUUIDJObj(env, UUIDJObj);
    jclass UUIDDClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/TransactionUUIDDto");
    Result = (Result && UUIDDClass != nullptr);
    if (!Result) {
      return false;  }

    jfieldID onlineTransNumber_ID = env->GetFieldID(UUIDDClass, "onlineTransNumber", "I");
    jfieldID lastGenTime_ID       = env->GetFieldID(UUIDDClass, "lastGenTime", "I"); // "J"
    jfieldID clockSequence_ID     = env->GetFieldID(UUIDDClass, "clockSequence", "I");
    jfieldID hasNodeId_ID         = env->GetFieldID(UUIDDClass, "hasNodeId", "Z");
    jfieldID nodeId_ID            = env->GetFieldID(UUIDDClass, "nodeId", "[B");
    Result = (onlineTransNumber_ID && lastGenTime_ID && clockSequence_ID && hasNodeId_ID && nodeId_ID);

    if (Result) {
      env->SetIntField(*UUIDJObj, onlineTransNumber_ID, (int)UUID->OnlineTransNumber);
      env->SetIntField(*UUIDJObj, lastGenTime_ID, (int)UUID->LastGenTime);
      env->SetIntField(*UUIDJObj, clockSequence_ID, UUID->ClockSequence);
      env->SetBooleanField(*UUIDJObj, hasNodeId_ID, UUID->hasNodeID);

      jbyteArray JBArr = nullptr;
      Result = ArrayToJByteArray(env, UUID->NodeID, sizeof(UUID->NodeID), &JBArr);
      if (Result) {
        env->SetObjectField(*UUIDJObj, nodeId_ID, JBArr);
        env->DeleteLocalRef(JBArr);
      }
    } // if Result
  } // if env && IniDataJObj && IniData

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertOperationResultFromJObj(JNIEnv *env, const jobject *OperationResultObj, TOperationResult *OperationResult) {
  bool Result = false;
  if (env && OperationResultObj && OperationResult) {
    jclass OperationResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/responces/OperationResult");

    jfieldID ResultCode_ID = env->GetFieldID(OperationResultClass, "resultCode", "Lru/petrolplus/pos/p7LibApi/responces/ResultCode;");
    jfieldID Data_ID      = env->GetFieldID(OperationResultClass, "data", "[B");

    jobject    ResultCode = env->GetObjectField(*OperationResultObj, ResultCode_ID);
    jbyteArray Data = (jbyteArray)env->GetObjectField(*OperationResultObj, Data_ID);

    OperationResult->Error = GetResultCode(env, &ResultCode);
    Result = JByteArrayToVector(env, Data, &OperationResult->Data);
  } // if env && IniDataJObj && IniData

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::CreateTransDBRecordJObj(JNIEnv *env, jobject *DBRecordJObj) {
  bool Result = false;
  if (env && DBRecordJObj) {
    if (*DBRecordJObj) {
      DeleteLocalRef(env, DBRecordJObj);  }

    jclass DBRecordJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/TransactionRecordDto");
    jmethodID ApduAnswerConstructorID = env->GetMethodID(DBRecordJObjClass, "<init>", "()V");
    *DBRecordJObj = env->NewObject(DBRecordJObjClass, ApduAnswerConstructorID);
    Result = (*DBRecordJObj != nullptr);
  } // if env && ApduAnswerJObj
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertTransDBRecordFromJObj(JNIEnv *env, const jobject *DBRecordJObj, TTransDBRecord *DBRecord) {
  bool Result = false;

  std::tm    *ptm = nullptr;
  std::time_t Time;

  if (env && DBRecordJObj && DBRecord) {
    std::vector<unsigned char> StdByteArr;
    jobject timeStampJObj = nullptr;

    jclass DBRecordClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/TransactionRecordDto");
    Result = (DBRecordClass != nullptr);
    if (!Result) {
      return false;  }

    jfieldID cardNumber_ID = env->GetFieldID(DBRecordClass, "cardNumber", "J");
    jfieldID shiftNumber_ID = env->GetFieldID(DBRecordClass, "shiftNumber", "J");
    jfieldID timeStamp_ID = env->GetFieldID(DBRecordClass, "timeStamp",
                                            "Lru/petrolplus/pos/p7LibApi/dto/ClockDto;");
    jfieldID serviceIdOrigEmit_ID = env->GetFieldID(DBRecordClass, "serviceIdOrigEmit", "B");
    jfieldID serviceIdCurrEmit_ID = env->GetFieldID(DBRecordClass, "serviceIdCurrEmit", "B");
    jfieldID totalVolume_ID = env->GetFieldID(DBRecordClass, "totalVolume", "J");
    jfieldID price_ID = env->GetFieldID(DBRecordClass, "price", "J");
    jfieldID totalSum_ID = env->GetFieldID(DBRecordClass, "totalSum", "J");
    jfieldID cardTrzCounter_ID = env->GetFieldID(DBRecordClass, "cardTrzCounter", "I");
    jfieldID hasReturn_ID = env->GetFieldID(DBRecordClass, "hasReturn", "Z");
    jfieldID rollbackCode_ID = env->GetFieldID(DBRecordClass, "rollbackCode", "[B");
    jfieldID debitToken_ID = env->GetFieldID(DBRecordClass, "debitToken", "[B");
    jfieldID terminalNumber_ID = env->GetFieldID(DBRecordClass, "terminalNumber", "I");
    jfieldID crc32_ID = env->GetFieldID(DBRecordClass, "crc32", "[B");
    jfieldID operationType_ID = env->GetFieldID(DBRecordClass, "operationType", "B");
    jfieldID cardType_ID = env->GetFieldID(DBRecordClass, "cardType", "B");
    jfieldID clientSum_ID = env->GetFieldID(DBRecordClass, "clientSum", "J");
    jfieldID deltaBonus_ID = env->GetFieldID(DBRecordClass, "deltaBonus", "J");
    jfieldID returnTimeStamp_ID = env->GetFieldID(DBRecordClass, "returnTimeStamp",
                                                  "Lru/petrolplus/pos/p7LibApi/dto/ClockDto;");


    DBRecord->CardNumber = (DWORD)env->GetLongField(*DBRecordJObj, cardNumber_ID);
    DBRecord->SiftNumber = (DWORD)env->GetLongField(*DBRecordJObj, shiftNumber_ID);
    timeStampJObj = env->GetObjectField(*DBRecordJObj, timeStamp_ID);
    Result = ConvertStTimeFromJObj(env, &timeStampJObj, &DBRecord->TimeStamp);
    timeStampJObj = nullptr;
    StdByteArr.clear();
    if (Result) {
      DBRecord->ServiceIdOrigEmit = env->GetByteField(*DBRecordJObj, serviceIdOrigEmit_ID);
      DBRecord->ServiceIdCurrEmit = env->GetByteField(*DBRecordJObj, serviceIdCurrEmit_ID);
      DBRecord->TotalVolume = (DWORD)env->GetLongField(*DBRecordJObj, totalVolume_ID);
      DBRecord->Price = (DWORD)env->GetLongField(*DBRecordJObj, price_ID);
      DBRecord->TotalSum = (DWORD)env->GetLongField(*DBRecordJObj, totalSum_ID);
      DBRecord->CardTrzCounter = (WORD)env->GetIntField(*DBRecordJObj, cardTrzCounter_ID);
      DBRecord->HasReturn = env->GetBooleanField(*DBRecordJObj, hasReturn_ID);
      Result = JByteArrayToVector(env, (jbyteArray)(env->GetObjectField(*DBRecordJObj, rollbackCode_ID)),
                                  &StdByteArr);
      Result = (Result && (StdByteArr.size() == sizeof(DBRecord->RollbackCode)));  }
    if (Result) {
      memcpy(DBRecord->RollbackCode, StdByteArr.data(), sizeof(DBRecord->RollbackCode));
      Result = JByteArrayToVector(env, (jbyteArray)(env->GetObjectField(*DBRecordJObj, debitToken_ID)),
                                  &StdByteArr);
      Result = (Result && (StdByteArr.size() == sizeof(DBRecord->DebitToken)));  }
    if (Result) {
      memcpy(DBRecord->DebitToken, StdByteArr.data(), sizeof(DBRecord->DebitToken));
      DBRecord->TerminalNumber = (WORD)env->GetIntField(*DBRecordJObj, terminalNumber_ID);
      Result = JByteArrayToVector(env, (jbyteArray)(env->GetObjectField(*DBRecordJObj, crc32_ID)),
                                  &StdByteArr);
      Result = (Result && (StdByteArr.size() == sizeof(DBRecord->Crc32)));  }
    if (Result) {
      memcpy(DBRecord->Crc32, StdByteArr.data(), sizeof(DBRecord->Crc32));
      DBRecord->OperationType = (TrzBaseOperType)env->GetByteField(*DBRecordJObj, operationType_ID);
      DBRecord->CardType = env->GetByteField(*DBRecordJObj, cardType_ID);
      DBRecord->ClientSum = (DWORD)env->GetLongField(*DBRecordJObj, clientSum_ID);
      DBRecord->DeltaBonus = (DWORD)env->GetLongField(*DBRecordJObj, deltaBonus_ID);
      timeStampJObj = env->GetObjectField(*DBRecordJObj, returnTimeStamp_ID);
      Result = ConvertStTimeFromJObj(env, &timeStampJObj, &DBRecord->ReturnTimeStamp);
      timeStampJObj = nullptr;  }
  } // env && DBRecordJObj && DBRecord

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertTransDBRecordToJObj(JNIEnv *env, const TTransDBRecord *DBRecord, jobject *DBRecordJObj) {
  bool Result = false;

  std::tm     tm {0};
  jbyteArray  JObjByteArr = nullptr;
  std::vector<unsigned char> StdByteArr;
  jobject timeStampJObj = nullptr;

  if (env && DBRecord && DBRecordJObj) {
    Result = CreateTransDBRecordJObj(env, DBRecordJObj);
    jclass DBRecordClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/TransactionRecordDto");

    Result = (Result && *DBRecordJObj && DBRecordClass);
    if (!Result) {
      return false;  }

    jfieldID cardNumber_ID = env->GetFieldID(DBRecordClass, "cardNumber", "J");
    jfieldID shiftNumber_ID = env->GetFieldID(DBRecordClass, "shiftNumber", "J");
    jfieldID timeStamp_ID = env->GetFieldID(DBRecordClass, "timeStamp", "Lru/petrolplus/pos/p7LibApi/dto/ClockDto;");
    jfieldID serviceIdOrigEmit_ID = env->GetFieldID(DBRecordClass, "serviceIdOrigEmit", "B");
    jfieldID serviceIdCurrEmit_ID = env->GetFieldID(DBRecordClass, "serviceIdCurrEmit", "B");
    jfieldID totalVolume_ID = env->GetFieldID(DBRecordClass, "totalVolume", "J");
    jfieldID price_ID = env->GetFieldID(DBRecordClass, "price", "J");
    jfieldID totalSum_ID = env->GetFieldID(DBRecordClass, "totalSum", "J");
    jfieldID cardTrzCounter_ID = env->GetFieldID(DBRecordClass, "cardTrzCounter", "I");
    jfieldID hasReturn_ID = env->GetFieldID(DBRecordClass, "hasReturn", "Z");
    jfieldID rollbackCode_ID = env->GetFieldID(DBRecordClass, "rollbackCode", "[B");
    jfieldID debitToken_ID = env->GetFieldID(DBRecordClass, "debitToken", "[B");
    jfieldID terminalNumber_ID = env->GetFieldID(DBRecordClass, "terminalNumber", "I");
    jfieldID crc32_ID = env->GetFieldID(DBRecordClass, "crc32", "[B");
    jfieldID operationType_ID = env->GetFieldID(DBRecordClass, "operationType", "B");
    jfieldID cardType_ID = env->GetFieldID(DBRecordClass, "cardType", "B");
    jfieldID clientSum_ID = env->GetFieldID(DBRecordClass, "clientSum", "J");
    jfieldID deltaBonus_ID = env->GetFieldID(DBRecordClass, "deltaBonus", "J");
    jfieldID returnTimeStamp_ID = env->GetFieldID(DBRecordClass, "returnTimeStamp", "Lru/petrolplus/pos/p7LibApi/dto/ClockDto;");


    env->SetLongField(*DBRecordJObj, cardNumber_ID, (long long)DBRecord->CardNumber);
    env->SetLongField(*DBRecordJObj, shiftNumber_ID, (long long)DBRecord->SiftNumber);
    Result = ConvertStTimeToJObj(env, &DBRecord->TimeStamp, &timeStampJObj);
    env->SetObjectField(*DBRecordJObj, timeStamp_ID, timeStampJObj);
    DeleteLocalRef(env, &timeStampJObj);
    env->SetByteField(*DBRecordJObj, serviceIdOrigEmit_ID, DBRecord->ServiceIdOrigEmit);
    env->SetByteField(*DBRecordJObj, serviceIdCurrEmit_ID, DBRecord->ServiceIdCurrEmit);
    env->SetLongField(*DBRecordJObj, totalVolume_ID, (long long)DBRecord->TotalVolume);
    env->SetLongField(*DBRecordJObj, price_ID, (long long)DBRecord->Price);
    env->SetLongField(*DBRecordJObj, totalSum_ID, (long long)DBRecord->TotalSum);
    env->SetIntField(*DBRecordJObj, cardTrzCounter_ID, DBRecord->CardTrzCounter);
    env->SetBooleanField(*DBRecordJObj, hasReturn_ID, DBRecord->HasReturn);
    Result = ArrayToJByteArray(env, DBRecord->RollbackCode, sizeof(DBRecord->RollbackCode), &JObjByteArr);
    env->SetObjectField(*DBRecordJObj, rollbackCode_ID, JObjByteArr);
    env->DeleteLocalRef(JObjByteArr);
    JObjByteArr = nullptr;
    Result = ArrayToJByteArray(env, DBRecord->DebitToken, sizeof(DBRecord->DebitToken), &JObjByteArr);
    env->SetObjectField(*DBRecordJObj, debitToken_ID, JObjByteArr);
    env->DeleteLocalRef(JObjByteArr);
    JObjByteArr = nullptr;
    env->SetIntField(*DBRecordJObj, terminalNumber_ID, DBRecord->TerminalNumber);
    Result = ArrayToJByteArray(env, DBRecord->Crc32, sizeof(DBRecord->Crc32), &JObjByteArr);
    env->SetObjectField(*DBRecordJObj, crc32_ID, JObjByteArr);
    env->DeleteLocalRef(JObjByteArr);
    JObjByteArr = nullptr;
    env->SetByteField(*DBRecordJObj, operationType_ID, (BYTE)DBRecord->OperationType);
    env->SetByteField(*DBRecordJObj, cardType_ID, DBRecord->CardType);
    env->SetLongField(*DBRecordJObj, clientSum_ID, (long long)DBRecord->ClientSum);
    env->SetLongField(*DBRecordJObj, deltaBonus_ID, (long long)DBRecord->DeltaBonus);
    Result = ConvertStTimeToJObj(env, &DBRecord->ReturnTimeStamp, &timeStampJObj);
    env->SetObjectField(*DBRecordJObj, returnTimeStamp_ID, timeStampJObj);
    DeleteLocalRef(env, &timeStampJObj);
  } // if env && DBRecord && DBRecordJObj

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::CreateSimpleDocJObj(JNIEnv *env, jobject *SimpleDocJObj) {
  bool Result = false;
  if (env &&  SimpleDocJObj) {
    if (*SimpleDocJObj) {
      DeleteLocalRef(env, SimpleDocJObj);  }

    jclass PrintDataJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/PrintableDataDto");
    jmethodID SimpleDocConstructorID = env->GetMethodID(PrintDataJObjClass, "<init>",
                                                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    *SimpleDocJObj = env->NewObject(PrintDataJObjClass, SimpleDocConstructorID,
                                    env->NewStringUTF(""), env->NewStringUTF(""),
                                    env->NewStringUTF(""), env->NewStringUTF(""));
    Result = (*SimpleDocJObj != nullptr);
  } // if env && ApduAnswerJObj
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertSimpleDocToJObj(JNIEnv *env, const TSimpleDoc *SimpleDoc, jobject *SimpleDocJObj) {
  bool Result = false;

  if (env && SimpleDoc && SimpleDocJObj) {
    jstring JStr = nullptr;
    Result = CreateSimpleDocJObj(env, SimpleDocJObj);
    jclass SimpleDocJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/PrintableDataDto");
    Result = (Result && SimpleDocJObjClass != nullptr);
    if (!Result) {
      return false;  }

    jfieldID header_ID = env->GetFieldID(SimpleDocJObjClass, "header", "Ljava/lang/String;");
    jfieldID title_ID = env->GetFieldID(SimpleDocJObjClass, "title", "Ljava/lang/String;");
    jfieldID body_ID = env->GetFieldID(SimpleDocJObjClass, "body", "Ljava/lang/String;");
    jfieldID footer_ID = env->GetFieldID(SimpleDocJObjClass, "footer", "Ljava/lang/String;");
    Result = (header_ID && title_ID && body_ID && footer_ID);

    if (Result) {
      Result = StringToJString(env, SimpleDoc->Header, JStr);
      env->SetObjectField(*SimpleDocJObj, header_ID, JStr);
      env->DeleteLocalRef(JStr);
      JStr = nullptr;  }
    if (Result) {
      Result = StringToJString(env, SimpleDoc->Title, JStr);
      env->SetObjectField(*SimpleDocJObj, title_ID, JStr);
      env->DeleteLocalRef(JStr);
      JStr = nullptr;  }
    if (Result) {
      Result = StringToJString(env, SimpleDoc->Body, JStr);
      env->SetObjectField(*SimpleDocJObj, body_ID, JStr);
      env->DeleteLocalRef(JStr);
      JStr = nullptr;  }
    if (Result) {
      Result = StringToJString(env, SimpleDoc->Footer, JStr);
      env->SetObjectField(*SimpleDocJObj, footer_ID, JStr);
      env->DeleteLocalRef(JStr);
      JStr = nullptr;  }
  } // if env && ApduAnswerJObj

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::CreateCardKeyJObj(JNIEnv *env, jobject *CardKeyJObj) {
  bool Result = false;
  if (env &&  CardKeyJObj) {
    if (*CardKeyJObj) {
      DeleteLocalRef(env, CardKeyJObj);  }

    jclass CardKeyJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/CardKeyDto");
    jmethodID CardKeyConstructorID = env->GetMethodID(CardKeyJObjClass, "<init>", "()V");

    *CardKeyJObj = env->NewObject(CardKeyJObjClass, CardKeyConstructorID);
    Result = (*CardKeyJObj != nullptr);
  } // if env && ApduAnswerJObj
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertCardKeyToJObj(JNIEnv *env, const TCardKey *CardKey, jobject *CardKeyJObj) {
  bool Result = false;

  if (env && CardKey && CardKeyJObj) {
    jbyteArray JBArray = nullptr;

    Result = CreateCardKeyJObj(env, CardKeyJObj);
    jclass CardKeyJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/CardKeyDto");
    Result = (Result && CardKeyJObjClass != nullptr);
    if (!Result) {
      return false;  }

    jfieldID publicKey_ID = env->GetFieldID(CardKeyJObjClass, "publicKey", "[B");
    jfieldID publicExt_ID = env->GetFieldID(CardKeyJObjClass, "publicExp", "[B");
    jfieldID nonce_ID = env->GetFieldID(CardKeyJObjClass, "nonce", "[B");
    Result = (publicKey_ID && publicExt_ID && nonce_ID);

    if (Result) {
      Result = VectorToJByteArray(env, CardKey->PublicKey, &JBArray);
      env->SetObjectField(*CardKeyJObj, publicKey_ID, JBArray);
      env->DeleteLocalRef(JBArray);
      JBArray = nullptr;  }
    if (Result) {
      Result = VectorToJByteArray(env, CardKey->PublicExp, &JBArray);
      env->SetObjectField(*CardKeyJObj, publicExt_ID, JBArray);
      env->DeleteLocalRef(JBArray);
      JBArray = nullptr;  }
    if (Result) {
      Result = VectorToJByteArray(env, CardKey->Nonce, &JBArray);
      env->SetObjectField(*CardKeyJObj, nonce_ID, JBArray);
      env->DeleteLocalRef(JBArray);
      JBArray = nullptr;  }
  } // if env && CardKey && CardKeyJObj

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertCardKeyFromJObj(JNIEnv *env, const jobject *CardKeyJObj, TCardKey *CardKey) {
  bool Result = false;

  if (env && CardKey && CardKeyJObj) {
    jclass CardKeyJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/CardKeyDto");
    Result = (CardKeyJObjClass != nullptr);
    if (!Result) {
      return false;  }

    jfieldID publicKey_ID = env->GetFieldID(CardKeyJObjClass, "publicKey", "[B");
    jfieldID publicExp_ID = env->GetFieldID(CardKeyJObjClass, "publicExp", "[B");
    jfieldID nonce_ID     = env->GetFieldID(CardKeyJObjClass, "nonce",     "[B");
    Result = (publicKey_ID && publicExp_ID && nonce_ID);

    if (Result) {
      Result = JByteArrayToVector(env, (jbyteArray)(env->GetObjectField(*CardKeyJObj, publicKey_ID)), &CardKey->PublicKey);  }
    if (Result) {
      Result = JByteArrayToVector(env, (jbyteArray)(env->GetObjectField(*CardKeyJObj, publicExp_ID)), &CardKey->PublicExp);  }
    if (Result) {
      Result = JByteArrayToVector(env, (jbyteArray)(env->GetObjectField(*CardKeyJObj, nonce_ID)), &CardKey->Nonce);  }
  } // if env && CardKey && CardKeyJObj

  return Result;
}
//--------------------------------------------------

TCardType TP7LibTypes::GetCardType(JNIEnv *env, const jobject *CardTypeJObj) {
  TCardType Result = TCardType::UnknownCardType;

  if (env && CardTypeJObj) {
    jclass   CardTypeClass   = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/CardType");
    jfieldID CardTypeValueID = env->GetFieldID(CardTypeClass, "code", "I");
    jint     intVal           = env->GetIntField(*CardTypeJObj, CardTypeValueID);
    Result = static_cast<TCardType>(intVal);  }

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertCardTypeToJObj(JNIEnv *env, TCardType CardType, jobject *CardTypeJObj) {
  bool Result = false;
  if (env && CardTypeJObj) {
    if (*CardTypeJObj) {
      env->DeleteLocalRef(*CardTypeJObj);
      *CardTypeJObj = nullptr;  }

    jclass ResultClass = nullptr;
    switch (CardType) {
      case UnknownCardType:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/UnknownCardType");
      break;
      case P7_A:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/P7_A");
      break;
      case P7_B:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/P7_B");
      break;
      case P7_C:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/P7_C");
      break;
      case P7_D:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/P7_D");
      break;
      case P7_E:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/P7_E");
      break;
      case P7_F:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/P7_F");
      break;
      case P7_G:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/P7_G");
      break;
      case P7_H:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/P7_H");
      break;
      case P7_I:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/P7_I");
      break;
      case P7_J:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/P7_J");
      break;
      case P5:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/P5");
      break;
      case PPay:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/PPay");
      break;
      case PLNR:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/PLNR");
      break;
      case ExtLikard:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/ExtLikard");
      break;
      case ExtRN:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/ExtRN");
      break;
      case ExtGPN:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/ExtGPN");
      break;
      default:
        ResultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/UnknownCardType");
      break;
    } // switch CardType

    if (ResultClass) {
      jmethodID CardTypeConstructor = env->GetMethodID(ResultClass, "<init>", "()V");
      *CardTypeJObj = env->NewObject(ResultClass, CardTypeConstructor);
    } // if ResultClass

    Result = true;
  } // if env && ResultCodeJObj

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::CreateCardInfoJObj(JNIEnv *env, jobject *CardInfoJObj) {
  bool Result = false;
  if (env &&  CardInfoJObj) {
    if (*CardInfoJObj) {
      DeleteLocalRef(env, CardInfoJObj);  }

    jclass CardInfoJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/CardInfo");
    jmethodID CardInfoConstructorID = env->GetMethodID(CardInfoJObjClass, "<init>", "()V");
    *CardInfoJObj = env->NewObject(CardInfoJObjClass, CardInfoConstructorID);

    Result = (*CardInfoJObj != nullptr);
  } // if env && CardInfoJObj
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertCardInfoToJObj(JNIEnv *env, const TCardInfo *CardInfo, jobject *CardInfoJObj) {
  bool Result = false;

  if (env && CardInfo && CardInfoJObj) {
    jbyteArray JBArray = nullptr;

    Result = CreateCardInfoJObj(env, CardInfoJObj);
    jclass CardInfoJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/card/CardInfo");
    Result = (Result && CardInfoJObjClass != nullptr);
    if (!Result) {
      return false;  }

    jfieldID isRecalcCard_ID = env->GetFieldID(CardInfoJObjClass, "isRecalcCard", "Z");
    jfieldID PTC_ID = env->GetFieldID(CardInfoJObjClass, "PTC", "B");
    jfieldID cardNumber_ID = env->GetFieldID(CardInfoJObjClass, "cardNumber", "J");
    jfieldID issuerID_ID = env->GetFieldID(CardInfoJObjClass, "issuerID", "J");
    jfieldID cardType_ID = env->GetFieldID(CardInfoJObjClass, "cardType",
                                           "Lru/petrolplus/pos/p7LibApi/dto/card/CardType;");
    Result = (isRecalcCard_ID && PTC_ID && cardNumber_ID && issuerID_ID && cardType_ID);

    if (Result) {
      long long LongVal = 0;
      env->SetBooleanField(*CardInfoJObj, isRecalcCard_ID, CardInfo->isRecalcCard);
      env->SetByteField(*CardInfoJObj, PTC_ID, CardInfo->PTC);
      LongVal = CardInfo->CardNumber;
      env->SetLongField(*CardInfoJObj, cardNumber_ID, LongVal);
      LongVal = CardInfo->IssuerID;
      env->SetLongField(*CardInfoJObj, issuerID_ID, LongVal);
      jobject CardTypeJObj = nullptr;
      Result = ConvertCardTypeToJObj(env, CardInfo->CardType, &CardTypeJObj);
      if (Result) {
        env->SetObjectField(*CardInfoJObj, cardType_ID, CardTypeJObj);  }
      env->DeleteLocalRef(CardTypeJObj);
      CardTypeJObj = nullptr;
    } // if Result
  } // if env && CardKey && CardInfoJObj

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::CreateTransactionInfoJObj(JNIEnv *env, jobject *TransactionInfoJObj) {
  bool Result = false;
  if (env &&  TransactionInfoJObj) {
    if (*TransactionInfoJObj) {
      DeleteLocalRef(env, TransactionInfoJObj);  }

    jclass TransInfoJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/TransactionInfoDto");
    jmethodID TransInfoConstructorID = env->GetMethodID(TransInfoJObjClass, "<init>", "()V");
    *TransactionInfoJObj = env->NewObject(TransInfoJObjClass, TransInfoConstructorID);

    Result = (*TransactionInfoJObj != nullptr);
  } // if env && TransactionInfoJObj
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertTransactionInfoToJObj(JNIEnv *env, const TTransactionInfo *TransInfo, jobject *TransInfoJObj) {
  bool Result = false;

  if (env && TransInfo && TransInfoJObj) {
    jbyteArray JBArray = nullptr;

    Result = CreateTransactionInfoJObj(env, TransInfoJObj);
    jclass TransInfoJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/TransactionInfoDto");
    Result = (Result && TransInfoJObjClass != nullptr);
    if (!Result) {
      return false;  }

    jfieldID cardNumber_ID = env->GetFieldID(TransInfoJObjClass, "cardNumber", "J");
    jfieldID transNumber_ID = env->GetFieldID(TransInfoJObjClass, "transactionNumber", "J");
    jfieldID terminalNumber_ID = env->GetFieldID(TransInfoJObjClass, "terminalNumber", "J");
    jfieldID terminalId_ID = env->GetFieldID(TransInfoJObjClass, "terminalId", "J");
    jfieldID issuerId_ID = env->GetFieldID(TransInfoJObjClass, "issuerId", "J");
    Result = (transNumber_ID && cardNumber_ID && terminalNumber_ID && terminalId_ID && issuerId_ID);

    if (Result) {
      long long LongVal = 0;
      LongVal = TransInfo->CardNumber;
      env->SetLongField(*TransInfoJObj, cardNumber_ID, LongVal);
      LongVal = TransInfo->TransNumber;
      env->SetLongField(*TransInfoJObj, transNumber_ID, LongVal);
      LongVal = TransInfo->TerminalNumber;
      env->SetLongField(*TransInfoJObj, terminalNumber_ID, LongVal);
      LongVal = TransInfo->TerminalID;
      env->SetLongField(*TransInfoJObj, terminalId_ID, LongVal);
      LongVal = TransInfo->IssuerID;
      env->SetLongField(*TransInfoJObj, issuerId_ID, LongVal);
    } // if Result
  } // if env && CardKey && CardInfoJObj

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertDebetParamsFromJObj(JNIEnv *env, const jobject *DebetParamsJObj, TDebetParams *DebetParams) {
  bool Result = false;

  if (env && DebetParamsJObj && DebetParams) {
    jclass DebitJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/DebitParamsDto");
    Result = (DebitJObjClass != nullptr);
    if (!Result) {
      return false;  }

    jfieldID serviceWhat_ID = env->GetFieldID(DebitJObjClass, "serviceWhat", "I");
    jfieldID serviceFrom_ID = env->GetFieldID(DebitJObjClass, "serviceFrom", "I");
    jfieldID amount_ID = env->GetFieldID(DebitJObjClass, "amount", "J");
    jfieldID price_ID = env->GetFieldID(DebitJObjClass, "price", "J");
    jfieldID sum_ID = env->GetFieldID(DebitJObjClass, "sum", "J");
    jfieldID pinBlock_ID = env->GetFieldID(DebitJObjClass, "pinBlock", "[B");
    Result = (serviceWhat_ID && serviceFrom_ID && amount_ID && price_ID && sum_ID && pinBlock_ID);

    if (Result) {
      DebetParams->service_what = env->GetIntField(*DebetParamsJObj, serviceWhat_ID);
      DebetParams->service_from = env->GetIntField(*DebetParamsJObj, serviceFrom_ID);
      DebetParams->amount       = (DWORD)env->GetLongField(*DebetParamsJObj, amount_ID);
      DebetParams->price        = (DWORD)env->GetLongField(*DebetParamsJObj, price_ID);
      DebetParams->sum          = (DWORD)env->GetLongField(*DebetParamsJObj, sum_ID);
      jbyteArray JBArr = nullptr;
      JBArr = (jbyteArray)(env->GetObjectField(*DebetParamsJObj, pinBlock_ID));
      Result = JByteArrayToVector(env, JBArr, &DebetParams->pinblock);
    } // if Result
  } // if env && DebetParamsJObj && DebetParams

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertRefundParamsFromJObj(JNIEnv *env, const jobject *RefundParamsJObj, TRefundParams *RefundParams) {
  bool Result = false;

  if (env && RefundParamsJObj && RefundParams) {
    jclass RefundJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/RefundParamsDto");
    Result = (RefundJObjClass != nullptr);
    if (!Result) {
      return false;  }

    jfieldID serviceWhat_ID = env->GetFieldID(RefundJObjClass, "serviceWhat", "I");
    jfieldID amount_ID = env->GetFieldID(RefundJObjClass, "amount", "J");
    jfieldID price_ID = env->GetFieldID(RefundJObjClass, "price", "J");
    jfieldID sum_ID = env->GetFieldID(RefundJObjClass, "sum", "J");
    Result = (serviceWhat_ID && amount_ID && price_ID && sum_ID);

    if (Result) {
      RefundParams->service_what = env->GetIntField(*RefundParamsJObj, serviceWhat_ID);
      RefundParams->amount       = (DWORD)env->GetLongField(*RefundParamsJObj, amount_ID);
      RefundParams->price        = (DWORD)env->GetLongField(*RefundParamsJObj, price_ID);
      RefundParams->sum          = (DWORD)env->GetLongField(*RefundParamsJObj, sum_ID);
    } // if Result
  } // if env && RefundParamsJObj && RefundParams

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::CreateErrorInfoJObj(JNIEnv *env, jobject *ErrorInfoJObj) {
  bool Result = false;
  if (env &&  ErrorInfoJObj) {
    if (*ErrorInfoJObj) {
      DeleteLocalRef(env, ErrorInfoJObj);  }

    jclass TransInfoJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/ErrorInfoDto");
    jmethodID TransInfoConstructorID = env->GetMethodID(TransInfoJObjClass, "<init>", "()V");
    *ErrorInfoJObj = env->NewObject(TransInfoJObjClass, TransInfoConstructorID);

    Result = (*ErrorInfoJObj != nullptr);
  } // if env && ErrorInfoJObj
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertErrorInfoToJObj(JNIEnv *env, const TErrorInfo *ErrorInfo, jobject *ErrorInfoJObj) {
  bool Result = false;

  if (env && ErrorInfo && ErrorInfoJObj) {
    Result = CreateErrorInfoJObj(env, ErrorInfoJObj);
    jclass ErrorInfoJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/ErrorInfoDto");
    Result = (Result && ErrorInfoJObjClass != nullptr);
    if (!Result) {
      return false;  }

    jfieldID errorCode_ID = env->GetFieldID(ErrorInfoJObjClass, "errorCode", "Lru/petrolplus/pos/p7LibApi/responces/ResultCode;");
    jfieldID errorStack_ID = env->GetFieldID(ErrorInfoJObjClass, "errorStack", "[Ljava/lang/Byte;");
    jfieldID errorMessage_ID = env->GetFieldID(ErrorInfoJObjClass, "errorMessage", "Ljava/lang/String;");
    Result = (errorCode_ID && errorStack_ID && errorMessage_ID);

    if (Result) {
      jobject ResultCodeJObj = nullptr;
      Result = ConvertResultCodeToJObj(env, ErrorInfo->ErrorCode, &ResultCodeJObj);
      if (Result) {
        env->SetObjectField(*ErrorInfoJObj, errorCode_ID, ResultCodeJObj);  }
      env->DeleteLocalRef(ResultCodeJObj);
      ResultCodeJObj = nullptr;
    } // if Result

    //todo: структура стека ошибок согласовывается

    if (Result) {
      jstring JStr = nullptr;
      Result = StringToJString(env, ErrorInfo->ErrorMsg, JStr);
      if (Result) {
        env->SetObjectField(*ErrorInfoJObj, errorMessage_ID, JStr);  }
      env->DeleteLocalRef(JStr);
      JStr = nullptr;
    } // if Result
  } // if env && ErrorInfo && ErrorInfoJObj

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::CreateLibInfoJObj(JNIEnv *env, jobject *LibInfoJObj) {
  bool Result = false;
  if (env &&  LibInfoJObj) {
    if (*LibInfoJObj) {
      DeleteLocalRef(env, LibInfoJObj);  }

    jclass TransInfoJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/LibInfoDto");
    jmethodID TransInfoConstructorID = env->GetMethodID(TransInfoJObjClass, "<init>", "()V");
    *LibInfoJObj = env->NewObject(TransInfoJObjClass, TransInfoConstructorID);

    Result = (*LibInfoJObj != nullptr);
  } // if env && ErrorInfoJObj
  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertLibInfoToJObj(JNIEnv *env, const TLibInfo *LibInfo, jobject *LibInfoJObj) {
  bool Result = false;

  if (env && LibInfo && LibInfoJObj) {
    jbyteArray JBArray = nullptr;

    Result = CreateLibInfoJObj(env, LibInfoJObj);
    jclass TransInfoJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/LibInfoDto");
    Result = (Result && TransInfoJObjClass != nullptr);
    if (!Result) {
      return false;  }

    jfieldID acquirerId_ID = env->GetFieldID(TransInfoJObjClass, "acquirerId", "J");
    jfieldID terminalNum_ID = env->GetFieldID(TransInfoJObjClass, "terminalNum", "J");
    jfieldID majorVersion_ID = env->GetFieldID(TransInfoJObjClass, "majorVersion", "J");
    jfieldID minerVersion_ID = env->GetFieldID(TransInfoJObjClass, "minerVersion", "J");
    Result = (acquirerId_ID && terminalNum_ID && majorVersion_ID && minerVersion_ID);

    if (Result) {
      env->SetLongField(*LibInfoJObj, acquirerId_ID, (long long)LibInfo->AquireID);
      env->SetLongField(*LibInfoJObj, terminalNum_ID, (long long)LibInfo->TerminalNum);
      env->SetLongField(*LibInfoJObj, majorVersion_ID, (long long)LibInfo->MajorVersion);
      env->SetLongField(*LibInfoJObj, minerVersion_ID, (long long)LibInfo->MinorVersion);
    } // if Result
  } // if env && LibInfo && LibInfoJObj

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::CreateStTimeJObj(JNIEnv *env, jobject *StTimeJObj) {
  bool Result = false;
  if (env &&  StTimeJObj) {
    if (*StTimeJObj) {
      DeleteLocalRef(env, StTimeJObj);  }

    jclass StTimeJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/ClockDto");
    jmethodID TransInfoConstructorID = env->GetMethodID(StTimeJObjClass, "<init>", "()V");
    *StTimeJObj = env->NewObject(StTimeJObjClass, TransInfoConstructorID);

    Result = (*StTimeJObj != nullptr);
  } // if env && ErrorInfoJObj
  return Result;}
//--------------------------------------------------

bool TP7LibTypes::ConvertStTimeToJObj(JNIEnv *env, const TSTTime *StTime, jobject *StTimeJObj) {
  bool Result = false;

  if (env && StTime && StTimeJObj) {
    jbyteArray JBArray = nullptr;

    Result = CreateStTimeJObj(env, StTimeJObj);
    jclass StTimeJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/ClockDto");
    Result = (Result && StTimeJObjClass != nullptr);
    if (!Result) {
      if (*StTimeJObj) {
        env->DeleteLocalRef(*StTimeJObj);
        *StTimeJObj = nullptr;  }
      return false;  }

    jfieldID year_ID = env->GetFieldID(StTimeJObjClass, "year", "S");
    jfieldID month_ID = env->GetFieldID(StTimeJObjClass, "month", "S");
    jfieldID day_ID = env->GetFieldID(StTimeJObjClass, "day", "S");
    jfieldID hour_ID = env->GetFieldID(StTimeJObjClass, "hour", "S");
    jfieldID minute_ID = env->GetFieldID(StTimeJObjClass, "minute", "S");
    jfieldID second_ID = env->GetFieldID(StTimeJObjClass, "second", "S");
    jfieldID weekDay_ID = env->GetFieldID(StTimeJObjClass, "weekDay", "S");
    Result = (year_ID && month_ID && day_ID && hour_ID && minute_ID && second_ID && weekDay_ID);

    if (Result) {
      env->SetShortField(*StTimeJObj, year_ID, (short)StTime->year);
      env->SetShortField(*StTimeJObj, month_ID, (short)StTime->month);
      env->SetShortField(*StTimeJObj, day_ID, (short)StTime->day);
      env->SetShortField(*StTimeJObj, hour_ID, (short)StTime->hour);
      env->SetShortField(*StTimeJObj, minute_ID, (short)StTime->minute);
      env->SetShortField(*StTimeJObj, second_ID, (short)StTime->second);
      env->SetShortField(*StTimeJObj,weekDay_ID, (short)StTime->weekDay);
    } // if Result
  } // if env && StTime && StTimeJObj

  return Result;
}
//--------------------------------------------------

bool TP7LibTypes::ConvertStTimeFromJObj(JNIEnv *env, const jobject *StTimeJObj, TSTTime *StTime) {
  bool Result = false;

  if (env && StTime && StTimeJObj) {
    jclass StTimeJObjClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/ClockDto");
    Result = (StTimeJObjClass != nullptr);
    if (!Result) {
      return false;  }

    jfieldID year_ID = env->GetFieldID(StTimeJObjClass, "year", "S");
    jfieldID month_ID = env->GetFieldID(StTimeJObjClass, "month", "S");
    jfieldID day_ID = env->GetFieldID(StTimeJObjClass, "day", "S");
    jfieldID hour_ID = env->GetFieldID(StTimeJObjClass, "hour", "S");
    jfieldID minute_ID = env->GetFieldID(StTimeJObjClass, "minute", "S");
    jfieldID second_ID = env->GetFieldID(StTimeJObjClass, "second", "S");
    jfieldID weekDay_ID = env->GetFieldID(StTimeJObjClass, "weekDay", "S");
    Result = (year_ID && month_ID && day_ID && hour_ID && minute_ID && second_ID && weekDay_ID);

    if (Result) {
      StTime->year = (BYTE)env->GetShortField(*StTimeJObj,  year_ID);
      StTime->month = (BYTE)env->GetShortField(*StTimeJObj, month_ID);
      StTime->day = (BYTE)env->GetShortField(*StTimeJObj, day_ID);
      StTime->hour = (BYTE)env->GetShortField(*StTimeJObj, hour_ID);
      StTime->minute = (BYTE)env->GetShortField(*StTimeJObj, minute_ID);
      StTime->second = (BYTE)env->GetShortField(*StTimeJObj, second_ID);
      StTime->weekDay = (BYTE)env->GetShortField(*StTimeJObj, weekDay_ID);
    } // if Result
  } // if env && StTime && StTimeJObj

  return true;
}
//--------------------------------------------------









//---------------------------------------------------------------------------------------------------------------
#endif //POS_TYPES_CONVERSIONS_CPP