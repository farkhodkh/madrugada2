#include <jni.h>
#include <string>
#include <android/log.h>
#include <vector>

#include <iostream>
#include <regex>

#include <map>

#include "P7Lib_lib.h"
#include "TypesConversions.h"

using namespace P7Lib;
//---------------------------------------------------------------------------------------------------------------

const char *TAG = "P7Lib";

//---------------------------------------------------------------------------------------------------------------

class TCallbackController {
 private:
  static JNIEnv *getJniEnv();

  static JavaVM *JVM;
  static jobject CallbackObject;

  static jmethodID    LogCallbackID;
  static void         Log(const std::string &Msg);

  static jmethodID    CardResetCallbackID;
  static TP7ErrorType CardReset(TAPDUAnswer &Answer);

  static jmethodID    CardAPDUCallbackID;
  static TP7ErrorType CardAPDU(const TAPDUData &Request, TAPDUAnswer &Answer);
  static jmethodID    SamResetCallbackID;
  static TP7ErrorType SamReset(TAPDUAnswer &Answer);
  static jmethodID    SamAPDUCallbackID;
  static TP7ErrorType SamAPDU(const TAPDUData &Request, TAPDUAnswer &Answer);

  static jmethodID        TryASConnectionCallbackID;
  static bool             TryASConnection(int Timeout);
  static jmethodID        DoASDataExchangeCallbackID;
  static TOperationResult DoASDataExchange(const vector<unsigned char> &Data);

  static jmethodID    FindLastTransDBCallbackID;
  static TP7ErrorType FindLastTransDB(DWORD CardNumber, TTransDBRecord &DBRecord);
  static jmethodID    CompleteTransDBCallbackID;
  static TP7ErrorType CompleteTransDB(const TTransDBRecord &DBRecord);

  static jmethodID    PrintSimpleDocCallbackID;
  static TP7ErrorType PrintSimpleDoc(const TSimpleDoc &Doc);

 public:
  static TCallbacksSet GetCallbacks(JNIEnv *jniEnv, jobject &CallbackObjectJava);
  static void Free(void);
};
//--------------------------------------------------

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

        TP7LibTypeConvertor::StringToJString(jniEnv, Msg, JStr);

        jniEnv->CallVoidMethod(CallbackObject, LogCallbackID, JStr);
        jniEnv->DeleteLocalRef(JStr);
    } // if jniEnv && CallbackObject && LogCallbackID
}
//--------------------------------------------------

TP7ErrorType TCallbackController::CardReset(TAPDUAnswer &Answer) {
  TP7ErrorType Result = TP7ErrorType::OK;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && CardResetCallbackID) {

    jclass OperationResultClass = jniEnv->FindClass("ru/petroplus/pos/p7LibApi/responces/OperationResult");

    jmethodID ConstructorMethID = jniEnv->GetMethodID(OperationResultClass, "<init>",
                                           "(Lru/petroplus/pos/p7LibApi/dto/ResultCode;[B)V");



    jobject OperRes = jniEnv->CallObjectMethod(CallbackObject, CardResetCallbackID);

    jfieldID ResultCodeID = jniEnv->GetFieldID(OperationResultClass, "resultCode",
                                                "Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    jfieldID DataID = jniEnv->GetFieldID(OperationResultClass, "data", "[B");


    Answer.SW1;
    Answer.SW2;
    Answer.Data;


    jniEnv->DeleteLocalRef(OperRes);
  } // if jniEnv && CallbackObject && LogCallbackID
  else {
    TCallbackController::Log("TCallbackController::CardReset: No instance.");
  }


  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::CardAPDU(const TAPDUData &Request, TAPDUAnswer &Answer) {
  TP7ErrorType Result = TP7ErrorType::OK;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && CardAPDUCallbackID) {


  }

  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::SamReset(TAPDUAnswer &Answer) {
  TP7ErrorType Result = TP7ErrorType::OK;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && SamResetCallbackID) {


  }

  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::SamAPDU(const TAPDUData &Request, TAPDUAnswer &Answer) {
  TP7ErrorType Result = TP7ErrorType::OK;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && SamAPDUCallbackID) {


  }

  return Result;
}
//--------------------------------------------------

bool TCallbackController::TryASConnection(int Timeout) {
  bool Result = TP7ErrorType::OK;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && TryASConnectionCallbackID) {


  }

  return Result;
}
//--------------------------------------------------

TOperationResult TCallbackController::DoASDataExchange(const vector<unsigned char> &Data) {
  TOperationResult Result;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && DoASDataExchangeCallbackID) {


  }

  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::FindLastTransDB(DWORD CardNumber, TTransDBRecord &DBRecord) {
  TP7ErrorType Result = TP7ErrorType::OK;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && FindLastTransDBCallbackID) {


  }

  return Result;
}
//--------------------------------------------------

TP7ErrorType TCallbackController::CompleteTransDB(const TTransDBRecord &DBRecord) {
  TP7ErrorType Result = TP7ErrorType::OK;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && CompleteTransDBCallbackID) {


  }

  return Result;
}
//--------------------------------------------------



TP7ErrorType TCallbackController::PrintSimpleDoc(const TSimpleDoc &Doc) {
  TP7ErrorType Result = TP7ErrorType::OK;

  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && PrintSimpleDocCallbackID) {


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

//todo: проверить сингнатуру
//    CardResetCallbackID = jniEnv->GetMethodID(CallbackClass, "cardReset", "()Lru/petroplus/pos/p7LibApi/responces/OperationResult;");
//    if (CardResetCallbackID) {
//      CallbacksSet.CardReset = &CardReset;  }
//    CardAPDUCallbackID = jniEnv->GetMethodID(CallbackClass, "sendDataToCard", "()V;");
//    if (CardAPDUCallbackID) {
//      CallbacksSet.CardAPDU = &CardAPDU;  }
//    SamResetCallbackID = jniEnv->GetMethodID(CallbackClass, "samReset", "()V;");
//    if (SamResetCallbackID) {
//      CallbacksSet.SamReset = &SamReset;  }
//    SamAPDUCallbackID = jniEnv->GetMethodID(CallbackClass, "sendToSamCard", "()V;");
//    if (SamAPDUCallbackID) {
//      CallbacksSet.SamAPDU = &SamAPDU;  }

//todo: проверить сингнатуру
    TryASConnectionCallbackID = jniEnv->GetMethodID(CallbackClass, "connectToAS", "(J)Z;");   // (J)Z - bool(long)
    if (TryASConnectionCallbackID) {
      CallbacksSet.TryASConnection = &TryASConnection;  }
    DoASDataExchangeCallbackID = jniEnv->GetMethodID(CallbackClass, "sendToAS", "([B)Lru/petroplus/pos/p7LibApi/responces/OperationResult;");
    if (DoASDataExchangeCallbackID) {
      CallbacksSet.DoASDataExchange = &DoASDataExchange;  }

    FindLastTransDBCallbackID = jniEnv->GetMethodID(CallbackClass, "findLastTransaction",
                                                    "(I;Lru/petroplus/pos/p7LibApi/dto/TransactionInfoDto)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (FindLastTransDBCallbackID) {
      CallbacksSet.FindLastTransDB = &FindLastTransDB;  }

//todo: проверить сингнатуру
    CompleteTransDBCallbackID = jniEnv->GetMethodID(CallbackClass, "updateTransaction",
                                                    "(Lru/petroplus/pos/p7LibApi/dto/TransactionInfoDto)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (CompleteTransDBCallbackID) {
      CallbacksSet.CompleteTransDB = &CompleteTransDB;  }


    PrintSimpleDocCallbackID = jniEnv->GetMethodID(CallbackClass, "printSimpleDoc",
                                                    "(Lru/petroplus/pos/p7LibApi/dto/TransactionInfoDto)Lru/petroplus/pos/p7LibApi/dto/ResultCode;");
    if (PrintSimpleDocCallbackID) {
      CallbacksSet.PrintSimpleDoc = &PrintSimpleDoc;  }


  } // if JVM && CallbackObject

  return CallbacksSet;
}
//--------------------------------------------------

//---------------------------------------------------------------------------------------------------------------





extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_init(JNIEnv *env, jobject thiz,
                                                          jobject init_data, jobject last_op_guid,
                                                          jobject callbacks, jstring temp_dir,
                                                          jstring data_dir) {

    TIniData         IniData;
    TTransactionUUID LastOpGUID;
    std::string      TempDir;
    std::string      DataDir;

    TP7LibTypeConvertor::JStringToString(env, temp_dir, TempDir);
    TP7LibTypeConvertor::JStringToString(env, data_dir, DataDir);

    TP7ErrorType Err = TP7Lib::Init(IniData, LastOpGUID,
                                    TCallbackController::GetCallbacks(env, callbacks),
                                    TempDir, DataDir);



    jclass resultClass = env->FindClass("ru/petroplus/pos/p7LibApi/dto/OK");
    jmethodID method = env->GetMethodID(resultClass, "<init>", "()V");
    return env->NewObject(resultClass, method);
}

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_deInit(JNIEnv *env, jobject thiz) {
    jstring dfg = 0;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_detect(JNIEnv *env, jobject thiz,
                                                            jobject card_key, jobject card_data) {
    jstring dfg = 0;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_debit(JNIEnv *env, jobject thiz,
                                                           jobject params, jobject info,
                                                           jobject uuid) {
    jstring dfg = 0;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_refund(JNIEnv *env, jobject thiz,
                                                            jobject params, jobject info,
                                                            jobject uuid) {
    jstring dfg = 0;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_getErrorInfo(JNIEnv *env, jobject thiz,
                                                                  jobject error_info) {
    jstring dfg = 0;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_getLibInfo(JNIEnv *env, jobject thiz,
                                                                jobject lib_info) {
    jstring dfg = 0;
}