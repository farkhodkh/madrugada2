//
// Created by Farkhod Kh on 07.04.2023.
//

#ifndef NATIVELIBSAMPLE_ConvertMapJni_H
#define NATIVELIBSAMPLE_ConvertMapJni_H


#include <jni.h>
#include "Callbacks.h"
#include <map>

//void TestConversions(_JNIEnv *env, jobject pJobject, jobject methodsMap);

std::map<TBackclallTypes, jmethodID>
JavaHashMapToCppMap(_JNIEnv *env, jobject methodsMap, jobject pJobject);

#endif //NATIVELIBSAMPLE_ConvertMapJni_H
