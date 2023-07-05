//
// Created by Yuriy.Poprygushin on 05.07.2023.
//
#ifndef POS_TYPES_CONVERSIONS_CPP
#define POS_TYPES_CONVERSIONS_CPP
//---------------------------------------------------------------------------------------------------------------

#include "TypesConversions.h"

//---------------------------------------------------------------------------------------------------------------

std::string TP7LibTypeConvertor::JStringToString(JNIEnv *env, jstring jStr) {
  if (!jStr)
    return "";

  jclass     stringClass = env->GetObjectClass(jStr);
  jmethodID  getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
  jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes, env->NewStringUTF("UTF-8"));

  auto length = (size_t) env->GetArrayLength(stringJbytes);
  jbyte* pBytes = env->GetByteArrayElements(stringJbytes, nullptr);

  std::string ret = std::string((char *)pBytes, length);
  env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

  env->DeleteLocalRef(stringJbytes);
  env->DeleteLocalRef(stringClass);

  return ret;
}
//--------------------------------------------------



//---------------------------------------------------------------------------------------------------------------
#endif //POS_TYPES_CONVERSIONS_CPP