//
// Created by Farkhod Kh on 07.04.2023.

//
#include "ConvertMapJni.h"
#include "Callbacks.h"

#include <map>

std::map<TBackclallTypes, jmethodID>
JavaHashMapToCppMap(_JNIEnv *env, jobject callbacks, jobject methodsMap) {

    std::map<TBackclallTypes, jmethodID> mapOut;
    jclass callbacksClass = env->GetObjectClass(callbacks);

    jclass mapClass = env->FindClass("java/util/Map");
    if (mapClass == NULL) {
        return mapOut;
    }
    jmethodID entrySet =
            env->GetMethodID(mapClass, "entrySet", "()Ljava/util/Set;");
    if (entrySet == NULL) {
        return mapOut;
    }
    jobject set = env->CallObjectMethod(methodsMap, entrySet);
    if (set == NULL) {
        return mapOut;
    }
    jclass setClass = env->FindClass("java/util/Set");
    if (setClass == NULL) {
        return mapOut;
    }
    jmethodID iterator =
            env->GetMethodID(setClass, "iterator", "()Ljava/util/Iterator;");
    if (iterator == NULL) {
        return mapOut;
    }
    jobject iter = env->CallObjectMethod(set, iterator);
    if (iter == NULL) {
        return mapOut;
    }
    jclass iteratorClass = env->FindClass("java/util/Iterator");
    if (iteratorClass == NULL) {
        return mapOut;
    }
    jmethodID hasNext = env->GetMethodID(iteratorClass, "hasNext", "()Z");
    if (hasNext == NULL) {
        return mapOut;
    }
    jmethodID next =
            env->GetMethodID(iteratorClass, "next", "()Ljava/lang/Object;");
    if (next == NULL) {
        return mapOut;
    }
    jclass entryClass = env->FindClass("java/util/Map$Entry");
    if (entryClass == NULL) {
        return mapOut;
    }
    jmethodID getKey =
            env->GetMethodID(entryClass, "getKey", "()Ljava/lang/Object;");
    if (getKey == NULL) {
        return mapOut;
    }
    jmethodID getValue =
            env->GetMethodID(entryClass, "getValue", "()Ljava/lang/Object;");
    if (getValue == NULL) {
        return mapOut;
    }
    while (env->CallBooleanMethod(iter, hasNext)) {
        jobject entry = env->CallObjectMethod(iter, next);
        jstring key = (jstring) env->CallObjectMethod(entry, getKey);
        jstring value = (jstring) env->CallObjectMethod(entry, getValue);
        const char* keyStr = env->GetStringUTFChars(key, NULL);
        if (!keyStr) {
            continue;
        }
        const char* valueStr = env->GetStringUTFChars(value, NULL);
        if (!valueStr) {
            env->ReleaseStringUTFChars(key, keyStr);
            continue;
        }

        jmethodID getMethodsId = env->GetMethodID(callbacksClass, keyStr,valueStr);
        if(!getMethodsId) {
            continue;
        }

        //TODO - Написать переобразователь из строки с названием enum из JAVA в значение enum из C++
        if (std::string(keyStr).find("log")) {
            mapOut.insert(std::make_pair(TBackclallTypes::bctNon, getMethodsId));
        } else {
            mapOut.insert(std::make_pair(TBackclallTypes::bctVoidInt, getMethodsId));
        }

        env->DeleteLocalRef(entry);
        env->ReleaseStringUTFChars(key, keyStr);
        env->DeleteLocalRef(key);
        env->ReleaseStringUTFChars(value, valueStr);
        env->DeleteLocalRef(value);
    }

    return mapOut;
}
