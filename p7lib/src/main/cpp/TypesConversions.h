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
  static void JByteArrayToVector(JNIEnv *env, const jbyteArray &Array, std::vector<unsigned char> *Vector);
  static void VectorToJByteArray(JNIEnv *env, const std::vector<unsigned char> &Vector, jbyteArray *Array);

  static void JStringToString(JNIEnv *env, const jstring &jStr,    std::string &Str);
  static void StringToJString(JNIEnv *env, const std::string &Str, jstring &jStr);

  static void DeleteLocalRef(JNIEnv *env, jobject *ApduAnswerJObj);

  static TP7ErrorType GetResultCode(JNIEnv *env, const jobject *ResultCodeJObj);

  static void CreateApduAnswerJObj(JNIEnv *env, jobject *ApduAnswerJObj);
  static void ConvertApduAnswerFromJObj(JNIEnv *env, const jobject *ApduAnswerJObj, TAPDUAnswer *APDUAnswer);

  static void CreateApduDataJObj(JNIEnv *env, jobject *ApduDataJObj);
  static void ConvertApduDataToJObj(JNIEnv *env, const TAPDUData &APDUData, jobject *ApduDataJObj);

};
//---------------------------------------------------------------------------------------------------------------



//---------------------------------------------------------------------------------------------------------------
#endif //POS_TYPES_CONVERSIONS_H
