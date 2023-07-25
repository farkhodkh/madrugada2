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

//  TCallbackController::GetCallbacks(env, callbacks);


  TP7ErrorType ResultCode = TP7ErrorType::OK;
//  if (isOK) {
//    ResultCode = TP7Lib::Init(IniData, LastOpGUID,
//                             TCallbackController::GetCallbacks(env, callbacks),
//                              TempDir, DataDir);
//  }
//  else {
//    ResultCode = TP7ErrorType::UndefinedError;
//  }

  jobject ResultCodeJObj = nullptr;
  TP7LibTypes::ConvertResultCodeToJObj(env, ResultCode, &ResultCodeJObj);

  return ResultCodeJObj;
}
//-------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_deInit(JNIEnv *env, jobject thiz) {
  TP7ErrorType ResultCode;
  jobject ResultCodeJObj = nullptr;

  ResultCode = TP7Lib::Deinit();
  TP7LibTypes::ConvertResultCodeToJObj(env, ResultCode, &ResultCodeJObj);

  return ResultCodeJObj;
}
//-------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_detect(JNIEnv *env, jobject thiz,
                                                            jobject card_key, jobject card_data) {
  TP7ErrorType ResultCode = TP7ErrorType::OK;
  jobject      ResultCodeJObj = nullptr;

  TCardKey  CardKey;
  TCardInfo CardInfo;

  ResultCode = TP7Lib::Detect(CardKey, CardInfo);

  jobject CardKeyJObj = nullptr;
  jobject CardInfoJObj = nullptr;
  
  TP7LibTypes::ConvertCardKeyToJObj(env, &CardKey, &CardKeyJObj);
  TP7LibTypes::ConvertCardInfoToJObj(env, &CardInfo, &CardInfoJObj);

  TP7LibTypes::ConvertResultCodeToJObj(env, ResultCode, &ResultCodeJObj);

  return ResultCodeJObj;
}
//-------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_debit(JNIEnv *env, jobject thiz,
                                                           jobject params, jobject info,
                                                           jobject uuid) {
  TP7ErrorType ResultCode = TP7ErrorType::OK;;
  jobject      ResultCodeJObj = nullptr;
  bool isOK = true;

  TDebetParams     DebetParams;
  TTransactionInfo TransInfo;
  TTransactionUUID UUID;

  isOK = TP7LibTypes::ConvertDebetParamsFromJObj(env, &params, &DebetParams);
  if (isOK) {
    ResultCode = TP7Lib::Debet(DebetParams, TransInfo, UUID);  }

  if (isOK) {
    isOK = TP7LibTypes::ConvertTransactionInfoToJObj(env, &TransInfo, &info);  }
  if (isOK) {
    isOK = TP7LibTypes::ConvertTransactionUUIDToJObj(env, &UUID, &uuid);  }
  if (!isOK) {
    ResultCode = TP7ErrorType::UndefinedError;  }

  TP7LibTypes::ConvertResultCodeToJObj(env, ResultCode, &ResultCodeJObj);

  return ResultCodeJObj;
}
//-------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_refund(JNIEnv *env, jobject thiz,
                                                            jobject params, jobject info,
                                                            jobject uuid) {
  TP7ErrorType ResultCode;
  jobject      ResultCodeJObj = nullptr;
  bool isOK = true;

  TRefundParams    RefundParams;
  TTransactionInfo TransInfo;
  TTransactionUUID UUID;

  isOK = TP7LibTypes::ConvertRefundParamsFromJObj(env, &params, &RefundParams);
  if (isOK) {
    ResultCode = TP7Lib::Refund(RefundParams, TransInfo, UUID);  }

  if (isOK) {
    isOK = TP7LibTypes::ConvertTransactionInfoToJObj(env, &TransInfo, &info);  }
  if (isOK) {
    isOK = TP7LibTypes::ConvertTransactionUUIDToJObj(env, &UUID, &uuid);  }
  if (!isOK) {
    ResultCode = TP7ErrorType::UndefinedError;  }
  TP7LibTypes::ConvertResultCodeToJObj(env, ResultCode, &ResultCodeJObj);

  return ResultCodeJObj;
}
//-------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_getErrorInfo(JNIEnv *env, jobject thiz,
                                                                  jobject error_info) {
  TP7ErrorType ResultCode;
  jobject      ResultCodeJObj = nullptr;
  bool isOK = true;

  TErrorInfo ErrorInfo;

  ResultCode = TP7Lib::GetErrorInfo(ErrorInfo);

  isOK = TP7LibTypes::ConvertErrorInfoToJObj(env, &ErrorInfo, &error_info);
  if (!isOK) {
    ResultCode = TP7ErrorType::UndefinedError;  }
  TP7LibTypes::ConvertResultCodeToJObj(env, ResultCode, &ResultCodeJObj);

  return ResultCodeJObj;
}
//-------------------------------------------------------------------

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_getLibInfo(JNIEnv *env, jobject thiz,
                                                                jobject lib_info) {
  TP7ErrorType ResultCode;
  jobject      ResultCodeJObj = nullptr;
  bool isOK = true;

  TLibInfo LibInfo;

  ResultCode = TP7Lib::GetLibInfo(LibInfo);

  isOK = TP7LibTypes::ConvertLibInfoToJObj(env, &LibInfo, &lib_info);
  if (!isOK) {
    ResultCode = TP7ErrorType::UndefinedError;  }
  TP7LibTypes::ConvertResultCodeToJObj(env, ResultCode, &ResultCodeJObj);

  return ResultCodeJObj;
}
//-------------------------------------------------------------------
