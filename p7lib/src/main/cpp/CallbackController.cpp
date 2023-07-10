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

    TP7LibTypes::ConvertApduDataToJObj(jniEnv, Request, &ApduDataJObj);
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


  }
  else {
    TCallbackController::Log("TCallbackController::TryASConnection: No instance.");
  }

  return Result;
}
//--------------------------------------------------

TOperationResult TCallbackController::DoASDataExchange(const vector<unsigned char> &Data) {
  TOperationResult Result;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && DoASDataExchangeCallbackID) {


  }
  else {
    TCallbackController::Log("TCallbackController::DoASDataExchange: No instance.");
  }

  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::FindLastTransDB(DWORD CardNumber, TTransDBRecord &DBRecord) {
  TP7ErrorType Result = TP7ErrorType::UndefinedError;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && FindLastTransDBCallbackID) {


  }
  else {
    TCallbackController::Log("TCallbackController::FindLastTransDB: No instance.");
  }

  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::CompleteTransDB(const TTransDBRecord &DBRecord) {
  TP7ErrorType Result = TP7ErrorType::UndefinedError;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && CompleteTransDBCallbackID) {


  }
  else {
    TCallbackController::Log("TCallbackController::CompleteTransDB: No instance.");
  }

  return Result;
}
//--------------------------------------------------



TP7ErrorType TCallbackController::PrintSimpleDoc(const TSimpleDoc &Doc) {
  TP7ErrorType Result = TP7ErrorType::UndefinedError;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && PrintSimpleDocCallbackID) {


  }
  else {
    TCallbackController::Log("TCallbackController::PrintSimpleDoc: No instance.");
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
    DoASDataExchangeCallbackID = jniEnv->GetMethodID(CallbackClass, "doASDataExchange", "([B)Lru/petroplus/pos/p7LibApi/responces/OperationResult;");
    if (DoASDataExchangeCallbackID) {
      CallbacksSet.DoASDataExchange = &DoASDataExchange;  }

    FindLastTransDBCallbackID = jniEnv->GetMethodID(CallbackClass, "findLastTransactionDB",
                                                    "(ILru/petroplus/pos/p7LibApi/dto/TransactionRecordDto;)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (FindLastTransDBCallbackID) {
      CallbacksSet.FindLastTransDB = &FindLastTransDB;  }


    jclass SomeClass = jniEnv->FindClass("ru/petroplus/pos/p7LibApi/dto/TransactionRecordDto");

    CompleteTransDBCallbackID = jniEnv->GetMethodID(CallbackClass, "completeTransactionDB",
                                                    "(Lru/petroplus/pos/p7LibApi/dto/TransactionRecordDto;)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (CompleteTransDBCallbackID) {
      CallbacksSet.CompleteTransDB = &CompleteTransDB;  }


    PrintSimpleDocCallbackID = jniEnv->GetMethodID(CallbackClass, "printSimpleDoc",
                                                   "(Lru/petroplus/pos/p7LibApi/dto/PrintDataDto;)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (PrintSimpleDocCallbackID) {
      CallbacksSet.PrintSimpleDoc = &PrintSimpleDoc;  }

  } // if JVM && CallbackObject

  return CallbacksSet;
}
//--------------------------------------------------













//---------------------------------------------------------------------------------------------------------------
#endif //POS_CALLBACKCONTROLLER_CPP