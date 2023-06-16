#include <jni.h>
#include <string>
#include <android/log.h>
#include <vector>

#include <iostream>
#include <regex>

#include "Callbacks.h"
#include "DLLBlanck.h"
#include "ConvertMapJni.h"

#include <map>

const char *TAG = "NativeLib";

//--------------------------------------------------------------------------------------------------

class TCallbackController {
public: //private:
  static JNIEnv *getJniEnv();

  static JavaVM *JVM;

  static jobject CallbackObject;

  static jmethodID LogCallbackID;
  static void      LogCallback(std::string Msg);

  static jmethodID VoidIntCallbackID;
  static void      VoidIntCallback(int intValue);

  static jmethodID ChangeParamCallbackID;
  static int       ChangeParamCallback(TSomeType &Value);

public:
  static std::string JStringToString(JNIEnv *env, jstring jStr);

  static TCallbacksSet GetCallbacks(JNIEnv *jniEnv, jobject &CallbackObjectJava);
  static void Free(void);
};
//--------------------------------------------------

JavaVM *TCallbackController::JVM = nullptr;
jobject TCallbackController::CallbackObject = nullptr;

jmethodID TCallbackController::LogCallbackID         = nullptr;
jmethodID TCallbackController::VoidIntCallbackID     = nullptr;
jmethodID TCallbackController::ChangeParamCallbackID = nullptr;
//--------------------------------------------------

std::string TCallbackController::JStringToString(JNIEnv *env, jstring jStr) {
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

JNIEnv* TCallbackController::getJniEnv() {
  JavaVMAttachArgs attachArgs;
  attachArgs.version = JNI_VERSION_1_6;
  attachArgs.name = ">>>NativeThread__Any";
  attachArgs.group = nullptr;
  JNIEnv *env = nullptr;
  if (JVM && JVM->AttachCurrentThread(&env, &attachArgs) != JNI_OK) {
    env = nullptr;  }

  return env;
}
//--------------------------------------------------

void TCallbackController::Free(void) {
  if (CallbackObject) {
    JNIEnv *JniEnv = TCallbackController::getJniEnv();
    if (JniEnv) {
      JniEnv->DeleteGlobalRef(CallbackObject);
      CallbackObject = nullptr;  }
  } // if CallbackObject
}
//--------------------------------------------------

void TCallbackController::LogCallback(std::string Msg) {
  JNIEnv* jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && VoidIntCallbackID) {
    jstring JStr = jniEnv->NewStringUTF(Msg.c_str());
    jniEnv->CallVoidMethod(CallbackObject, LogCallbackID, JStr);
    jniEnv->DeleteLocalRef(JStr);  }
}
//--------------------------------------------------

void TCallbackController::VoidIntCallback(int intValue) {
  JNIEnv *jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && VoidIntCallbackID) {
    jniEnv->CallVoidMethod(CallbackObject, VoidIntCallbackID, intValue);  }
}
//--------------------------------------------------

int TCallbackController::ChangeParamCallback(TSomeType &Value) {
  int Result = 0;                                                     ;

  JNIEnv *jniEnv = getJniEnv();
  if (jniEnv && CallbackObject && ChangeParamCallbackID) {

    jclass SomeTypeJavaClass = jniEnv->FindClass("ru/farkhodkhaknazarov/nativelib/TSomeTypeJava");
    jmethodID method = jniEnv->GetMethodID(SomeTypeJavaClass, "<init>", "(IDZCLjava/lang/String;)V");

    // В метод
    jstring JStr = jniEnv->NewStringUTF(Value.StringVal.c_str());
    jobject JObjSomeType = jniEnv->NewObject(
      SomeTypeJavaClass,
      method,
      Value.IntVal,
      Value.DoubleVal,
      Value.BoolVal,
      Value.CharVal,
      JStr
    );
    jniEnv->DeleteLocalRef(JStr);

    Result = jniEnv->CallIntMethod(CallbackObject, ChangeParamCallbackID, JObjSomeType);

    jfieldID intValueId    = jniEnv->GetFieldID(SomeTypeJavaClass, "intValue", "I");
    jfieldID doubleValueId = jniEnv->GetFieldID(SomeTypeJavaClass, "doubleValue", "D");
    jfieldID boolValueId   = jniEnv->GetFieldID(SomeTypeJavaClass, "boolValue", "Z");
    jfieldID charValueId   = jniEnv->GetFieldID(SomeTypeJavaClass, "charValue", "C");
    jfieldID stringValueId = jniEnv->GetFieldID(SomeTypeJavaClass, "strValue", "Ljava/lang/String;");

    jint     intVal      = jniEnv->GetIntField(JObjSomeType, intValueId);
    jdouble  doubleValue = jniEnv->GetDoubleField(JObjSomeType, doubleValueId);
    jboolean boolValue   = jniEnv->GetBooleanField(JObjSomeType, boolValueId);
    jchar    charValue   = jniEnv->GetCharField(JObjSomeType, charValueId);
    jstring  stringValue = (jstring)(jniEnv->GetObjectField(JObjSomeType, stringValueId));

    Value.IntVal    = intVal;
    Value.DoubleVal = doubleValue;
    Value.CharVal   = charValue;
    Value.BoolVal   = boolValue;
    Value.StringVal = TCallbackController::JStringToString(jniEnv, stringValue);

  }


    return Result;
}
//--------------------------------------------------

TCallbacksSet TCallbackController::GetCallbacks(JNIEnv *jniEnv, jobject &CallbackObjectJava) {
  TCallbacksSet CallbacksSet;

  if (CallbackObject) {
    jniEnv->DeleteGlobalRef(CallbackObject);
    CallbackObject = nullptr;  }
  CallbackObject = jniEnv->NewGlobalRef(CallbackObjectJava);

  VoidIntCallbackID     = nullptr;
  LogCallbackID         = nullptr;
  ChangeParamCallbackID = nullptr;

  JVM = nullptr;
  jniEnv->GetJavaVM(&JVM);
  if (JVM && CallbackObject) {
    jclass CallbackClass = jniEnv->GetObjectClass(CallbackObjectJava);

    VoidIntCallbackID = jniEnv->GetMethodID(CallbackClass, "onNativeVoidCall", "(I)V");
    if (VoidIntCallbackID) {
      CallbacksSet.pVoidIntFunc = &VoidIntCallback;  }

    LogCallbackID = jniEnv->GetMethodID(CallbackClass, "log", "(Ljava/lang/String;)V");
    if (LogCallbackID) {
      CallbacksSet.pLog = &LogCallback;  }

    ChangeParamCallbackID = jniEnv->GetMethodID(CallbackClass, "onNativeCallReturn",
                                                "(Lru/farkhodkhaknazarov/nativelib/TSomeTypeJava;)I");
    if (ChangeParamCallbackID) {
      CallbacksSet.pChangeParam = &ChangeParamCallback;  }


  } // if JVM && CallbackObject

  return CallbacksSet;
}
//--------------------------------------------------

//--------------------------------------------------------------------------------------------------



extern "C" JNIEXPORT jstring JNICALL
Java_ru_farkhodkhaknazarov_nativelib_NativeLib_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

//*****
//#include <vector>
//...
//Первый вызов нам не нужен, у нас уже есть переданный
// JByteArray нам его получать не нужно. (далее наш JByteArray используется как jba)
////jbyteArray jba = (jbyteArray) env->CallStaticObjectMethod(clazz, methodId, dataPath);
//int len = env->GetArrayLength (jba);
//std::vector<char> buff(len + 1, 0);
//env->GetByteArrayRegion (jba, 0, len, reinterpret_cast<jbyte*>(buff.data()));
//Log_d(LOG_TAG, "getPkgData: %s", buff.data());



//extern "C" JNIEXPORT jbyteArray JNICALL
//Java_ru_farkhodkhaknazarov_nativelib_NativeLib_getNewMsg(
//        JNIEnv *env,
//        jobject /* this */) {
//
//    int NewMsgSize = 0;
//    bool isCopy = false;
//    jbyteArray retVal;
//
//    bool resultBool = false;
//    jbyteArray result;
//
//    if (IsNewMsg(&NewMsgSize)) {
//        if (NewMsgSize) {
//            retVal = env->NewByteArray(NewMsgSize);
//            jbyte *buf = env->GetByteArrayElements(retVal, reinterpret_cast<jboolean *>(&isCopy));
//
//            resultBool = GetNewMsg(reinterpret_cast<char *>(buf), NewMsgSize);
//            env->ReleaseByteArrayElements(retVal, buf, 0);
//
////            jcharArray jca2 = jniEnv->NewCharArray(strlen(text));
////            jniEnv->SetCharArrayRegion(jca2, 0, strlen(text), jc2);
//        }
//    }
//
//    return retVal;
//}

extern "C" JNIEXPORT jstring JNICALL
Java_ru_farkhodkhaknazarov_nativelib_NativeLib_getDLLVersion(
        JNIEnv *env,
        jobject /* this */) {

    int a = 0;
    int b = 0;

    GetDLLVersion(&a, &b);

    std::string version = "Version " + std::to_string(a) + "." + std::to_string(b);

    return env->NewStringUTF(version.c_str());  //todo: возможна ли здесь утечка ресурсов?
}

extern "C" JNIEXPORT jboolean JNICALL
Java_ru_farkhodkhaknazarov_nativelib_NativeLib_sendMsg(
        JNIEnv *env,
        jobject /* this */,
        jbyteArray byte_array) {

    bool result;

    int len = env->GetArrayLength(byte_array);
    std::vector<char> buff(len + 1, 0);
    env->GetByteArrayRegion(byte_array, 0, len, reinterpret_cast<jbyte *>(buff.data()));
    result = SendMsg(buff.data(), len);

    return result;
}

extern "C" JNIEXPORT int JNICALL
Java_ru_farkhodkhaknazarov_nativelib_NativeLib_IsNewMsg(
        JNIEnv *env,
        jobject /* this */) {
    int NewMsgSize = 0;
    IsNewMsg(&NewMsgSize);

    return NewMsgSize;
}

extern "C" JNIEXPORT jbyteArray JNICALL
Java_ru_farkhodkhaknazarov_nativelib_NativeLib_getNewMsg(
        JNIEnv *env,
        jobject /* this */) {

    jbyteArray retVal;      //todo: Нужно выполнять инициализацию?
    int NewMsgSize = 0;

    bool resultBool = false;

    if (IsNewMsg(&NewMsgSize)) {
        if (NewMsgSize) {
            char *buf = new char[NewMsgSize];
            retVal = env->NewByteArray(NewMsgSize);
            resultBool = GetNewMsg(reinterpret_cast<char *>(buf), NewMsgSize);
            env->SetByteArrayRegion(retVal, 0, NewMsgSize, reinterpret_cast<const jbyte *>(buf));
            delete[] buf;
            buf = nullptr;
        } // if NewMsgSize
    } // if IsNewMsg()

    return retVal;
}
//----------------------------------------------------------------------------------

extern "C" JNIEXPORT jboolean JNICALL
Java_ru_farkhodkhaknazarov_nativelib_NativeLib_createFile(
  JNIEnv *env,
  jobject thiz,
  jstring FileName,
  jstring Data
) {
  return CreateFile(TCallbackController::JStringToString(env, FileName),
                    TCallbackController::JStringToString(env, Data));
}
//----------------------------------------------------------------------------------

extern "C" JNIEXPORT jboolean JNICALL
Java_ru_farkhodkhaknazarov_nativelib_NativeLib_readFile(
  JNIEnv *env,
  jobject thiz,
  jstring FileName,
  jobject Data
) {
  bool Result = false;

  std::string FileData;
  Result = ReadFile(TCallbackController::JStringToString(env, FileName), FileData);

  if (Result) {
    jclass DataClass = env->GetObjectClass(Data);
    jfieldID StringValueId = env->GetFieldID(DataClass, "msg", "Ljava/lang/String;");
    env->SetObjectField(Data, StringValueId, env->NewStringUTF(FileData.c_str()));
  } // if Result

  return Result;
}
//----------------------------------------------------------------------------------






extern "C" JNIEXPORT void JNICALL
Java_ru_farkhodkhaknazarov_nativelib_NativeLib_sendJniString(  //todo: Этот метод не нужен, его следует удальть
        JNIEnv *env,
        jobject thiz,
        jstring value
) {

}

extern "C" JNIEXPORT jobject JNICALL
Java_ru_farkhodkhaknazarov_nativelib_NativeLib_getSomeTypeValue(
        JNIEnv *env,
        jobject /* this */) {

    //(C++)
    jclass someClass = env->FindClass("ru/farkhodkhaknazarov/nativelib/TSomeTypeJava");
    jmethodID method = env->GetMethodID(someClass, "<init>", "(IDZCLjava/lang/String;)V");

    TSomeType someType = GetSomeTypeValue();

    return env->NewObject(
            someClass,
            method,
            someType.IntVal,
            someType.DoubleVal,
            someType.BoolVal,
            someType.CharVal,
            env->NewStringUTF(someType.StringVal.c_str())     //todo: здесь утечька ресурсов?
    );
}

extern "C" JNIEXPORT void JNICALL
Java_ru_farkhodkhaknazarov_nativelib_NativeLib_sendSomeTypeValue(
        JNIEnv *env,
        jobject obj,
        jobject some_type) {

    jclass clazz = env->GetObjectClass(some_type);
    jfieldID intValueId = env->GetFieldID(clazz, "intValue", "I");
    jfieldID doubleValueId = env->GetFieldID(clazz, "doubleValue", "D");
    jfieldID boolValueId = env->GetFieldID(clazz, "boolValue", "Z");
    jfieldID charValueId = env->GetFieldID(clazz, "charValue", "C");
    jfieldID stringValueId = env->GetFieldID(clazz, "strValue", "Ljava/lang/String;");

    jint intVal = env->GetIntField(some_type, intValueId);
    jdouble doubleValue = env->GetDoubleField(some_type, doubleValueId);
    jboolean boolValue = env->GetBooleanField(some_type, boolValueId);
    jchar charValue = env->GetCharField(some_type, charValueId);

    //todo: Нужно закончить проработку метода в части получения строки
    //Пример:
    //TCallbackController::JStringToString(JNIEnv *env, jstring jStr)

    //jstring stringValue = env->NewStringUTF(env->GetObjectField(some_type, stringValueId));

    //auto value = reinterpret_cast<TSomeType *>(&some_type);
    auto *someTypeCpp = new TSomeType();

    //someTypeCpp->StringVal = value->StringVal;
    someTypeCpp->IntVal = intVal;
    someTypeCpp->DoubleVal = doubleValue;
    someTypeCpp->CharVal = charValue;
    someTypeCpp->BoolVal = boolValue;

    GetSomeTypeValueParam(NULL);

}

//--------------------------------------------------------------------------------------------------

extern "C" JNIEXPORT void JNICALL
Java_ru_farkhodkhaknazarov_nativelib_NativeLib_init(
        JNIEnv *env,
        jobject obj,
        jobject callbackObj) {

    Init(TCallbackController::GetCallbacks(env, callbackObj));
}

extern "C"
JNIEXPORT void JNICALL
Java_ru_farkhodkhaknazarov_nativelib_NativeLib_sendIniProperties(
        JNIEnv *env,
        jobject thiz,
        jobject properties
        ) {
    jclass clazz = env->GetObjectClass(properties);
    //TODO - Реализовать С++ код для конвертации  Может и мой вариант подойдет
    //JavaHashMapToCppMap()
}