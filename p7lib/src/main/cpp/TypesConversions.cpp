//
// Created by Yuriy.Poprygushin on 05.07.2023.
//
#ifndef POS_TYPES_CONVERSIONS_CPP
#define POS_TYPES_CONVERSIONS_CPP
//---------------------------------------------------------------------------------------------------------------

#include "TypesConversions.h"

//---------------------------------------------------------------------------------------------------------------

void TP7LibTypeConvertor::JStringToString(JNIEnv *env, const jstring &jStr, std::string &Str) {
  jclass     stringClass = env->GetObjectClass(jStr);
  jmethodID  getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
  jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes, env->NewStringUTF("UTF-8"));

  auto length = (size_t) env->GetArrayLength(stringJbytes);
  jbyte* pBytes = env->GetByteArrayElements(stringJbytes, nullptr);

  Str = std::string((char *)pBytes, length);

  env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);
  env->DeleteLocalRef(stringJbytes);
  env->DeleteLocalRef(stringClass);
}
//--------------------------------------------------

void TP7LibTypeConvertor::StringToJString(JNIEnv *env, const std::string &Str, jstring &jStr) {
  if (jStr) {
    env->DeleteLocalRef(jStr);  }
  jStr = env->NewStringUTF(Str.c_str());
}
//--------------------------------------------------





//---------------------------------------------------------------------------------------------------------------
#endif //POS_TYPES_CONVERSIONS_CPP