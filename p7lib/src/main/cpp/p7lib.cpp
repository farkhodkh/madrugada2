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

//  static jmethodID LogCallbackID;
//  static void      LogCallback(std::string Msg);
//
//  static jmethodID VoidIntCallbackID;
//  static void      VoidIntCallback(int intValue);
//
//  static jmethodID ChangeParamCallbackID;
//  static int       ChangeParamCallback(TSomeType &Value);

 public:
  static TCallbacksSet GetCallbacks(JNIEnv *jniEnv, jobject &CallbackObjectJava);
  static void Free(void);
};
//--------------------------------------------------

JavaVM *TCallbackController::JVM = nullptr;
jobject TCallbackController::CallbackObject = nullptr;
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

TCallbacksSet TCallbackController::GetCallbacks(JNIEnv *jniEnv, jobject &CallbackObjectJava) {
    TCallbacksSet CallbacksSet;

    if (CallbackObject) {
        jniEnv->DeleteGlobalRef(CallbackObject);
        CallbackObject = nullptr;  }
    CallbackObject = jniEnv->NewGlobalRef(CallbackObjectJava);

//    VoidIntCallbackID     = nullptr;
//    LogCallbackID         = nullptr;
//    ChangeParamCallbackID = nullptr;

    JVM = nullptr;
    jniEnv->GetJavaVM(&JVM);
    if (JVM && CallbackObject) {
        jclass CallbackClass = jniEnv->GetObjectClass(CallbackObjectJava);

//        VoidIntCallbackID = jniEnv->GetMethodID(CallbackClass, "onNativeVoidCall", "(I)V");
//        if (VoidIntCallbackID) {
//            CallbacksSet.pVoidIntFunc = &VoidIntCallback;  }
//
//        LogCallbackID = jniEnv->GetMethodID(CallbackClass, "log", "(Ljava/lang/String;)V");
//        if (LogCallbackID) {
//            CallbacksSet.pLog = &LogCallback;  }
//
//        ChangeParamCallbackID = jniEnv->GetMethodID(CallbackClass, "onNativeCallReturn",
//                                                    "(Lru/farkhodkhaknazarov/nativelib/TSomeTypeJava;)I");
//        if (ChangeParamCallbackID) {
//            CallbacksSet.pChangeParam = &ChangeParamCallback;  }


    } // if JVM && CallbackObject

    return CallbacksSet;
}
//--------------------------------------------------

//---------------------------------------------------------------------------------------------------------------





extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_init(JNIEnv *env, jobject thiz,
                                                          jobject init_data, jstring last_op_guid,
                                                          jobject callbacks, jstring temp_dir,
                                                          jstring data_dir) {

    TIniData         IniData;
    TTransactionUUID LastOpGUID;
    std::string      TempDir;
    std::string      DataDir;

     TempDir = TP7LibTypeConvertor::JStringToString(env, temp_dir);
     DataDir = TP7LibTypeConvertor::JStringToString(env, data_dir);

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
                                                           jstring uuid) {
    jstring dfg = 0;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_refund(JNIEnv *env, jobject thiz,
                                                            jobject params, jobject info,
                                                            jstring uuid) {
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