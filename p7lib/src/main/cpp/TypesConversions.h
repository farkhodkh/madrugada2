//
// Created by Yuriy.Poprygushin on 05.07.2023.
//
#ifndef POS_TYPES_CONVERSIONS_H
#define POS_TYPES_CONVERSIONS_H
//---------------------------------------------------------------------------------------------------------------

#include "P7Lib_lib.h"

#include <jni.h>
#include <android/log.h>

#include <string>

using namespace std;
using namespace P7Lib;
//---------------------------------------------------------------------------------------------------------------

class TP7LibTypes {
public:
  static bool JByteArrayToVector(JNIEnv *env, const jbyteArray &Array, std::vector<unsigned char> *Vector);
  static bool VectorToJByteArray(JNIEnv *env, const std::vector<unsigned char> &Vector, jbyteArray *Array);

  static bool JStringToString(JNIEnv *env, const jstring &jStr,    std::string &Str);
  static bool StringToJString(JNIEnv *env, const std::string &Str, jstring &jStr);

  static bool DeleteLocalRef(JNIEnv *env, jobject *ApduAnswerJObj);

  static TP7ErrorType GetResultCode(JNIEnv *env, const jobject *ResultCodeJObj);
  static bool ConvertResultCodeToJObj(JNIEnv *env, TP7ErrorType ResultCode, jobject *ResultCodeJObj);

  static bool CreateApduAnswerJObj(JNIEnv *env, jobject *ApduAnswerJObj);
  static bool ConvertApduAnswerFromJObj(JNIEnv *env, const jobject *ApduAnswerJObj, TAPDUAnswer *APDUAnswer);

  static bool CreateApduDataJObj(JNIEnv *env, jobject *ApduDataJObj);
  static bool ConvertApduDataToJObj(JNIEnv *env, const TAPDUData &APDUData, jobject *ApduDataJObj);

  static bool ConvertIniDataFromJObj(JNIEnv *env, const jobject *ApduDataJObj, TIniData *IniData);

  static bool ConvertTransactionUUIDFromJObj(JNIEnv *env, const jobject *UUIDJObj, TTransactionUUID *UUID);



};
//---------------------------------------------------------------------------------------------------------------



//---------------------------------------------------------------------------------------------------------------
#endif //POS_TYPES_CONVERSIONS_H
