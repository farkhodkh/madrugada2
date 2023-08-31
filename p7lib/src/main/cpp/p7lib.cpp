#include <jni.h>
#include <string>
#include <android/log.h>
#include <vector>

#include <iostream>
#include <regex>

#include <map>

const char *TAG = "P7Lib";

extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petrolplus_pos_p7Lib_impl_P7LibRepositoryImpl_init(JNIEnv *env, jobject thiz,
                                                          jobject init_data, jstring last_op_guid,
                                                          jobject callbacks, jstring temp_dir,
                                                          jstring data_dir) {
    jclass resultClass = env->FindClass("ru/petrolplus/pos/p7LibApi/dto/OK");
    jmethodID method = env->GetMethodID(resultClass, "<init>", "()V");
    return env->NewObject(resultClass, method);
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petrolplus_pos_p7Lib_impl_P7LibRepositoryImpl_deInit(JNIEnv *env, jobject thiz) {
    jstring dfg = 0;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petrolplus_pos_p7Lib_impl_P7LibRepositoryImpl_detect(JNIEnv *env, jobject thiz,
                                                            jobject card_key, jobject card_data) {
    jstring dfg = 0;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petrolplus_pos_p7Lib_impl_P7LibRepositoryImpl_debit(JNIEnv *env, jobject thiz,
                                                           jobject params, jobject info,
                                                           jstring uuid) {
    jstring dfg = 0;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petrolplus_pos_p7Lib_impl_P7LibRepositoryImpl_refund(JNIEnv *env, jobject thiz,
                                                            jobject params, jobject info,
                                                            jstring uuid) {
    jstring dfg = 0;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petrolplus_pos_p7Lib_impl_P7LibRepositoryImpl_getErrorInfo(JNIEnv *env, jobject thiz,
                                                                  jobject error_info) {
    jstring dfg = 0;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petrolplus_pos_p7Lib_impl_P7LibRepositoryImpl_getLibInfo(JNIEnv *env, jobject thiz,
                                                                jobject lib_info) {
    jstring dfg = 0;
}