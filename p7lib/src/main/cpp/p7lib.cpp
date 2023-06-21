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
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_init(JNIEnv *env, jobject thiz,
                                                          jobject init_data, jstring last_op_guid,
                                                          jobject callbacks, jstring temp_dir,
                                                          jstring data_dir) {
    // TODO: implement init()
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_deInit(JNIEnv *env, jobject thiz) {
    // TODO: implement deInit()
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_detect(JNIEnv *env, jobject thiz,
                                                            jobject card_key, jobject card_data) {
    // TODO: implement detect()
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_debit(JNIEnv *env, jobject thiz,
                                                           jobject params, jobject info,
                                                           jstring uuid) {
    // TODO: implement debit()
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_refund(JNIEnv *env, jobject thiz,
                                                            jobject params, jobject info,
                                                            jstring uuid) {
    // TODO: implement refund()
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_getErrorInfo(JNIEnv *env, jobject thiz,
                                                                  jobject error_info) {
    // TODO: implement getErrorInfo()
}
extern "C"
JNIEXPORT jobject JNICALL
Java_ru_petroplus_pos_p7Lib_impl_P7LibRepositoryImpl_getLibInfo(JNIEnv *env, jobject thiz,
                                                                jobject lib_info) {
    // TODO: implement getLibInfo()
}