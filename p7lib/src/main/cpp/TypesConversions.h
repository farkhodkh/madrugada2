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

class TP7LibTypeConvertor {
public:
  static std::string JStringToString(JNIEnv *env, jstring jStr);


};
//---------------------------------------------------------------------------------------------------------------



//---------------------------------------------------------------------------------------------------------------
#endif //POS_TYPES_CONVERSIONS_H
