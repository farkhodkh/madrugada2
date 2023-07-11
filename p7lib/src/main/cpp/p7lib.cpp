#include <jni.h>
#include <android/log.h>

#include <iostream>

#include <vector>
#include <string>
#include <map>
#include <regex>

#include "P7Lib_lib.h"
#include "TypesConversions.h"
#include "CallbackController.h"

using namespace P7Lib;
//---------------------------------------------------------------------------------------------------------------

const char *TAG = "P7Lib";

//---------------------------------------------------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_init(JNIEnv *env, jobject thiz,
                                                          jobject init_data, jobject last_op_guid,
                                                          jobject callbacks, jstring temp_dir,
                                                          jstring data_dir) {
  bool isOK = true;

  TIniData         IniData;
  TTransactionUUID LastOpGUID;
  std::string      TempDir;
  std::string      DataDir;

  if (isOK)  { isOK = TP7LibTypes::ConvertIniDataFromJObj(env, &init_data, &IniData);  }
  if (isOK)  { isOK = TP7LibTypes::ConvertTransactionUUIDFromJObj(env, &last_op_guid, &LastOpGUID);  }
  if (isOK)  { isOK = TP7LibTypes::JStringToString(env, temp_dir, TempDir);  }
  if (isOK)  { isOK = TP7LibTypes::JStringToString(env, data_dir, DataDir);  }

  TP7ErrorType ResultCode;
  if (isOK) {
    ResultCode = TP7Lib::Init(IniData, LastOpGUID,
                             TCallbackController::GetCallbacks(env, callbacks),
                              TempDir, DataDir);
  }
  else {
    ResultCode = TP7ErrorType::UndefinedError;
  }

  jobject ResultCodeJObj = nullptr;
  TP7LibTypes::ConvertResultCodeToJObj(env, ResultCode, &ResultCodeJObj);

  return ResultCodeJObj;
}
//-------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_deInit(JNIEnv *env, jobject thiz) {
    jstring dfg = 0;
}
//-------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_detect(JNIEnv *env, jobject thiz,
                                                            jobject card_key, jobject card_data) {
    jstring dfg = 0;
}
//-------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_debit(JNIEnv *env, jobject thiz,
                                                           jobject params, jobject info,
                                                           jobject uuid) {
    jstring dfg = 0;
}
//-------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_refund(JNIEnv *env, jobject thiz,
                                                            jobject params, jobject info,
                                                            jobject uuid) {
    jstring dfg = 0;
}
//-------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_getErrorInfo(JNIEnv *env, jobject thiz,
                                                                  jobject error_info) {
    jstring dfg = 0;
}
//-------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_getLibInfo(JNIEnv *env, jobject thiz,
                                                                jobject lib_info) {
    jstring dfg = 0;
}
//-------------------------------------------------------------------
