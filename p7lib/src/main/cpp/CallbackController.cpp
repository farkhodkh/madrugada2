//
// Created by Yuriy.Poprygushin on 10.07.2023.
//
#ifndef POS_CALLBACKCONTROLLER_CPP
#define POS_CALLBACKCONTROLLER_CPP
//---------------------------------------------------------------------------------------------------------------

#include "CallbackController.h"

//---------------------------------------------------------------------------------------------------------------

JavaVM   *TCallbackController::JVM = nullptr;
jobject   TCallbackController::CallbackObject = nullptr;

jmethodID TCallbackController::LogCallbackID = nullptr;
jmethodID TCallbackController::CardResetCallbackID = nullptr;
jmethodID TCallbackController::CardAPDUCallbackID  = nullptr;
jmethodID TCallbackController::SamResetCallbackID  = nullptr;
jmethodID TCallbackController::SamAPDUCallbackID   = nullptr;
jmethodID TCallbackController::TryASConnectionCallbackID  = nullptr;
jmethodID TCallbackController::DoASDataExchangeCallbackID = nullptr;
jmethodID TCallbackController::FindLastTransDBCallbackID = nullptr;
jmethodID TCallbackController::CompleteTransDBCallbackID = nullptr;
jmethodID TCallbackController::PrintSimpleDocCallbackID = nullptr;
jmethodID TCallbackController::TransferOOBToASCallbackID = nullptr;

//--------------------------------------------------

JNIEnv* TCallbackController::getJniEnv() {
  JavaVMAttachArgs attachArgs;
  attachArgs.version = JNI_VERSION_1_6;
  attachArgs.name = ">>>NativeThread__Any";
  attachArgs.group = nullptr;
  JNIEnv *env = nullptr;
  if (JVM && JVM->AttachCurrentThread(&env, &attachArgs) != JNI_OK) {
    env = nullptr;  }

  return env;
}
//--------------------------------------------------

void TCallbackController::Free(void) {
  if (CallbackObject) {
    JNIEnv *JniEnv = TCallbackController::getJniEnv();
    if (JniEnv) {
      JniEnv->DeleteGlobalRef(CallbackObject);
      CallbackObject = nullptr;  }
  } // if CallbackObject
}
//--------------------------------------------------

void TCallbackController::Log(const std::string &Msg) {
  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && LogCallbackID) {
    jstring JStr = nullptr;

    TP7LibTypes::StringToJString(jniEnv, Msg, JStr);

    jniEnv->CallVoidMethod(CallbackObject, LogCallbackID, JStr);
    jniEnv->DeleteLocalRef(JStr);
  } // if jniEnv && CallbackObject && LogCallbackID
}
//--------------------------------------------------

TP7ErrorType TCallbackController::ResetCard(TAPDUAnswer &Answer, jmethodID *MethodID) {
  TP7ErrorType Result = TP7ErrorType::UndefinedError;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && MethodID && *MethodID) {
    jobject ApduAnswerJObj = nullptr;
    TP7LibTypes::CreateApduAnswerJObj(jniEnv, &ApduAnswerJObj);

    jobject ResultCodeJObj = jniEnv->CallObjectMethod(CallbackObject, *MethodID,
                                                      ApduAnswerJObj);
    Result = TP7LibTypes::GetResultCode(jniEnv, &ResultCodeJObj);
    TP7LibTypes::ConvertApduAnswerFromJObj(jniEnv, &ApduAnswerJObj, &Answer);

    TP7LibTypes::DeleteLocalRef(jniEnv, &ApduAnswerJObj);
  } // if jniEnv && CallbackObject && LogCallbackID
  else {
    TCallbackController::Log("TCallbackController::ResetCard: No instance.");
  }

  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::APDUCard(const TAPDUData &Request, TAPDUAnswer &Answer, jmethodID *MethodID) {
  TP7ErrorType Result = TP7ErrorType::UndefinedError;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && MethodID && *MethodID) {
    jobject ApduDataJObj   = nullptr;
    jobject ApduAnswerJObj = nullptr;

    TP7LibTypes::ConvertApduDataToJObj(jniEnv, &Request, &ApduDataJObj);
    TP7LibTypes::CreateApduAnswerJObj(jniEnv, &ApduAnswerJObj);

    jobject ResultCodeJObj = jniEnv->CallObjectMethod(CallbackObject, *MethodID,
                                                      ApduDataJObj, ApduAnswerJObj);
    Result = TP7LibTypes::GetResultCode(jniEnv, &ResultCodeJObj);
    TP7LibTypes::ConvertApduAnswerFromJObj(jniEnv, &ApduAnswerJObj, &Answer);

    TP7LibTypes::DeleteLocalRef(jniEnv, &ApduDataJObj);
    TP7LibTypes::DeleteLocalRef(jniEnv, &ApduAnswerJObj);
  }
  else {
    TCallbackController::Log("TCallbackController::APDUCard: No instance.");
  }

  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::CardReset(TAPDUAnswer &Answer) {
  return ResetCard(Answer, &CardResetCallbackID);
}
//--------------------------------------------------

TP7ErrorType TCallbackController::CardAPDU(const TAPDUData &Request, TAPDUAnswer &Answer) {
  return APDUCard(Request, Answer, &CardAPDUCallbackID);
}
//--------------------------------------------------

TP7ErrorType TCallbackController::SamReset(TAPDUAnswer &Answer) {
  return ResetCard(Answer, &SamResetCallbackID);
}
//--------------------------------------------------

TP7ErrorType TCallbackController::SamAPDU(const TAPDUData &Request, TAPDUAnswer &Answer) {
  return APDUCard(Request, Answer, &SamAPDUCallbackID);
}
//--------------------------------------------------

bool TCallbackController::TryASConnection(int Timeout) {
  bool Result = false;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && TryASConnectionCallbackID) {
    Result = jniEnv->CallBooleanMethod(CallbackObject, TryASConnectionCallbackID, (long long)Timeout);
  }
  else {
    TCallbackController::Log("TCallbackController::TryASConnection: No instance.");
  }

  return Result;
}
//--------------------------------------------------

TOperationResult TCallbackController::DoASDataExchange(const vector<unsigned char> &Data) {
  bool isOK = true;
  TOperationResult Result;

  Result.Error = TP7ErrorType::UndefinedError;
  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && DoASDataExchangeCallbackID) {
    jbyteArray JArray = nullptr;
    isOK = TP7LibTypes::VectorToJByteArray(jniEnv, Data, &JArray);

    jobject OperationResultJObj = nullptr;
    if (isOK) {
      OperationResultJObj = jniEnv->CallObjectMethod(CallbackObject, DoASDataExchangeCallbackID, JArray);
      isOK = (OperationResultJObj != nullptr);  }
    if (isOK) {
      isOK = TP7LibTypes::ConvertOperationResultFromJObj(jniEnv, &OperationResultJObj, &Result);  }
    if (!isOK) {
      Result.Error = TP7ErrorType::UndefinedError;  }
  }
  else {
    TCallbackController::Log("TCallbackController::DoASDataExchange: No instance.");
  }

  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::FindLastTransDB(DWORD CardNumber, TTransDBRecord &DBRecord) {
  bool isOK = false;
  TP7ErrorType Result = TP7ErrorType::OK;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && FindLastTransDBCallbackID) {
    jobject DBRecordJObj = nullptr;

    TP7LibTypes::CreateTransDBRecordJObj(jniEnv, &DBRecordJObj);
    jobject ResultCodeJObj = jniEnv->CallObjectMethod(CallbackObject, FindLastTransDBCallbackID,
                                                      (long long)CardNumber, DBRecordJObj);
    isOK = (ResultCodeJObj != nullptr);
    if (isOK) {
      Result = TP7LibTypes::GetResultCode(jniEnv, &ResultCodeJObj);
      isOK = TP7LibTypes::ConvertTransDBRecordFromJObj(jniEnv, &DBRecordJObj, &DBRecord);  }
    if (!isOK) {
      Result = TP7ErrorType::UndefinedError;  }

    TP7LibTypes::DeleteLocalRef(jniEnv, &DBRecordJObj);
  } // if jniEnv && CallbackObject && FindLastTransDBCallbackID
  else {
    TCallbackController::Log("TCallbackController::FindLastTransDB: No instance.");
  }

  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::CompleteTransDB(const TTransDBRecord &DBRecord) {
  TP7ErrorType Result = TP7ErrorType::UndefinedError;
  bool isOK = false;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && CompleteTransDBCallbackID) {
    jobject DBRecordJObj = nullptr;

    TP7LibTypes::ConvertTransDBRecordToJObj(jniEnv, &DBRecord, &DBRecordJObj);
    jobject ResultCodeJObj = jniEnv->CallObjectMethod(CallbackObject, CompleteTransDBCallbackID, DBRecordJObj);
    isOK = (ResultCodeJObj != nullptr);

    if (isOK) {
      Result = TP7LibTypes::GetResultCode(jniEnv, &ResultCodeJObj);  }
    else {
      Result = TP7ErrorType::UndefinedError;  }
    TP7LibTypes::DeleteLocalRef(jniEnv, &DBRecordJObj);
  } // if jniEnv && CallbackObject && CompleteTransDBCallbackID
  else {
    TCallbackController::Log("TCallbackController::CompleteTransDB: No instance.");
  }

  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::PrintSimpleDoc(const TSimpleDoc &Doc) {
  TP7ErrorType Result = TP7ErrorType::UndefinedError;
  bool isOK = false;
  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && PrintSimpleDocCallbackID) {
    jobject SimpleDocJObj = nullptr;
    isOK = TP7LibTypes::ConvertSimpleDocToJObj(jniEnv, &Doc, &SimpleDocJObj);
    jobject ResultCodeJObj;
    if (isOK) {
      ResultCodeJObj = jniEnv->CallObjectMethod(CallbackObject, PrintSimpleDocCallbackID, SimpleDocJObj);
      isOK = (ResultCodeJObj != nullptr);  }
    if (isOK) {
      Result = TP7LibTypes::GetResultCode(jniEnv, &ResultCodeJObj);  }
    else {
      Result = TP7ErrorType::UndefinedError;
    }
    TP7LibTypes::DeleteLocalRef(jniEnv, &SimpleDocJObj);
  } // if jniEnv && CallbackObject && PrintSimpleDocCallbackID
  else {
    TCallbackController::Log("TCallbackController::PrintSimpleDoc: No instance.");
  }

  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::TransferOOBToAS(const vector<unsigned char> &OOB) {
  TP7ErrorType Result = TP7ErrorType::UndefinedError;
  bool isOK = false;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && TransferOOBToASCallbackID) {
    jbyteArray JBArr = nullptr;
    jobject ResultCodeJObj;

    isOK = TP7LibTypes::VectorToJByteArray(jniEnv, OOB, &JBArr);
    if (isOK) {
      ResultCodeJObj = jniEnv->CallObjectMethod(CallbackObject, TransferOOBToASCallbackID, JBArr);
      isOK = (ResultCodeJObj != nullptr);  }

    if (isOK) {
      Result = TP7LibTypes::GetResultCode(jniEnv, &ResultCodeJObj);  }
    else {
      Result = TP7ErrorType::UndefinedError;
    }
    if (JBArr) {
      jniEnv->DeleteLocalRef(JBArr);
      JBArr = nullptr;  }
  } // if jniEnv && CallbackObject && TransferOOBToASCallbackID
  else {
    TCallbackController::Log("TCallbackController::TransferOOBToAS: No instance.");
  }

  return Result;
}
//--------------------------------------------------

TCallbacksSet TCallbackController::GetCallbacks(JNIEnv *jniEnv, jobject &CallbackObjectJava) {
  TCallbacksSet CallbacksSet;

  if (CallbackObject) {
    jniEnv->DeleteGlobalRef(CallbackObject);
    CallbackObject = nullptr;  }
  CallbackObject = jniEnv->NewGlobalRef(CallbackObjectJava);

  LogCallbackID = nullptr;
  CardResetCallbackID = nullptr;
  CardAPDUCallbackID  = nullptr;
  SamResetCallbackID  = nullptr;
  SamAPDUCallbackID   = nullptr;
  TryASConnectionCallbackID  = nullptr;
  DoASDataExchangeCallbackID = nullptr;
  FindLastTransDBCallbackID = nullptr;
  CompleteTransDBCallbackID = nullptr;
  PrintSimpleDocCallbackID = nullptr;
  TransferOOBToASCallbackID = nullptr;

  JVM = nullptr;
  jniEnv->GetJavaVM(&JVM);
  if (JVM && CallbackObject) {
    jclass CallbackClass = jniEnv->GetObjectClass(CallbackObjectJava);

    LogCallbackID = jniEnv->GetMethodID(CallbackClass, "log", "(Ljava/lang/String;)V");
    if (LogCallbackID) {
      CallbacksSet.Log = &Log;  }

    CardResetCallbackID = jniEnv->GetMethodID(CallbackClass, "cardReset",
                                              "(Lru/petroplus/pos/p7LibApi/responces/ApduAnswer;)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (CardResetCallbackID) {
      CallbacksSet.CardReset = &CardReset;  }

    CardAPDUCallbackID = jniEnv->GetMethodID(CallbackClass, "sendDataToCard",
                                             "(Lru/petroplus/pos/p7LibApi/requests/ApduData;Lru/petroplus/pos/p7LibApi/responces/ApduAnswer;)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (CardAPDUCallbackID) {
      CallbacksSet.CardAPDU = &CardAPDU;  }

    SamResetCallbackID = jniEnv->GetMethodID(CallbackClass, "samReset",
                                             "(Lru/petroplus/pos/p7LibApi/responces/ApduAnswer;)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (SamResetCallbackID) {
      CallbacksSet.SamReset = &SamReset;  }

    SamAPDUCallbackID = jniEnv->GetMethodID(CallbackClass, "sendDataToSam",
                                            "(Lru/petroplus/pos/p7LibApi/requests/ApduData;Lru/petroplus/pos/p7LibApi/responces/ApduAnswer;)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (SamAPDUCallbackID) {
      CallbacksSet.SamAPDU = &SamAPDU;  }

    TryASConnectionCallbackID = jniEnv->GetMethodID(CallbackClass, "connectToAS", "(J)Z");   // (J)Z - bool(long)
    if (TryASConnectionCallbackID) {
      CallbacksSet.TryASConnection = &TryASConnection;  }

    DoASDataExchangeCallbackID = jniEnv->GetMethodID(CallbackClass, "doASDataExchange",
                                                     "([B)Lru/petroplus/pos/p7LibApi/responces/OperationResult;");
    if (DoASDataExchangeCallbackID) {
      CallbacksSet.DoASDataExchange = &DoASDataExchange;  }

    FindLastTransDBCallbackID = jniEnv->GetMethodID(CallbackClass, "findLastTransactionDB",
                                                    "(JLru/petroplus/pos/p7LibApi/dto/TransactionRecordDto;)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (FindLastTransDBCallbackID) {
      CallbacksSet.FindLastTransDB = &FindLastTransDB;  }

    CompleteTransDBCallbackID = jniEnv->GetMethodID(CallbackClass, "completeTransactionDB",
                                                    "(Lru/petroplus/pos/p7LibApi/dto/TransactionRecordDto;)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (CompleteTransDBCallbackID) {
      CallbacksSet.CompleteTransDB = &CompleteTransDB;  }

    PrintSimpleDocCallbackID = jniEnv->GetMethodID(CallbackClass, "printSimpleDoc",
                                                   "(Lru/petroplus/pos/p7LibApi/dto/SimpleDocDto;)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (PrintSimpleDocCallbackID) {
      CallbacksSet.PrintSimpleDoc = &PrintSimpleDoc;  }

    TransferOOBToASCallbackID = jniEnv->GetMethodID(CallbackClass, "transferOOBToAS",
                                                   "([B)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (TransferOOBToASCallbackID) {
      CallbacksSet.TransferOOBToAS = &TransferOOBToAS;  }
  } // if JVM && CallbackObject

  return CallbacksSet;
}
//--------------------------------------------------













//---------------------------------------------------------------------------------------------------------------
#endif //POS_CALLBACKCONTROLLER_CPP