//
// Created by Yuriy.Poprygushin on 05.07.2023.
//
#ifndef POS_TYPES_CONVERSIONS_CPP
#define POS_TYPES_CONVERSIONS_CPP
//---------------------------------------------------------------------------------------------------------------

#include "TypesConversions.h"

//---------------------------------------------------------------------------------------------------------------

void TP7LibTypes::JByteArrayToVector(JNIEnv *env, const jbyteArray &Array, std::vector<unsigned char> *Vector) {
  if (env && Vector) {
    Vector->clear();
    const size_t DataLen = env->GetArrayLength(Array);
    if (DataLen) {
      env->GetByteArrayRegion(Array, 0, DataLen, reinterpret_cast<jbyte *>(Vector->data()));  }
  } // if env && Vector
}
//--------------------------------------------------

void TP7LibTypes::VectorToJByteArray(JNIEnv *env, const std::vector<unsigned char> &Vector, jbyteArray *Array) {
  if (env && Array) {
    if (*Array) {
      env->DeleteLocalRef(*Array);
      *Array = nullptr;  }
    const jsize Len = static_cast<jsize>(Vector.size());
    *Array = env->NewByteArray(Len);
    env->SetByteArrayRegion(*Array, 0, Len, reinterpret_cast<const jbyte *>(Vector.data()));
  } // if env && Array
}
//--------------------------------------------------

void TP7LibTypes::JStringToString(JNIEnv *env, const jstring &jStr, std::string &Str) {
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

void TP7LibTypes::StringToJString(JNIEnv *env, const std::string &Str, jstring &jStr) {
  if (jStr) {
    env->DeleteLocalRef(jStr);  }
  jStr = env->NewStringUTF(Str.c_str());
}
//--------------------------------------------------

void TP7LibTypes::DeleteLocalRef(JNIEnv *env, jobject *ApduAnswerJObj) {
  if (env && ApduAnswerJObj) {
    if (*ApduAnswerJObj) {
      env->DeleteLocalRef(*ApduAnswerJObj);
      *ApduAnswerJObj = nullptr;  }
  }
}
//--------------------------------------------------

TP7ErrorType TP7LibTypes::GetResultCode(JNIEnv *env, const jobject *ResultCodeJObj) {
  TP7ErrorType Result = TP7ErrorType::UndefinedError;

  if (env && ResultCodeJObj) {
    jclass   ResultCodeClass   = env->FindClass("ru/petroplus/pos/p7LibApi/dto/ResultCode");
    jfieldID ResultCodeValueID = env->GetFieldID(ResultCodeClass, "code", "I");
    jint     intVal            = env->GetIntField(*ResultCodeJObj, ResultCodeValueID);

    Result = static_cast<TP7ErrorType>(intVal);  }

  return Result;
}
//--------------------------------------------------

void TP7LibTypes::CreateApduAnswerJObj(JNIEnv *env, jobject *ApduAnswerJObj) {
  if (env && ApduAnswerJObj) {
    if (*ApduAnswerJObj) {
      DeleteLocalRef(env, ApduAnswerJObj);  }

    jclass ApduAnswerClass = env->FindClass("ru/petroplus/pos/p7LibApi/responces/ApduAnswer");
    jmethodID ApduAnswerConstructorID = env->GetMethodID(ApduAnswerClass, "<init>", "(II[B)V");
    *ApduAnswerJObj = env->NewObject(ApduAnswerClass, ApduAnswerConstructorID,
                                     0, 0, jbyteArray());
  } // if env && ApduAnswerJObj
}
//--------------------------------------------------

void TP7LibTypes::ConvertApduAnswerFromJObj(JNIEnv *env, const jobject *ApduAnswerJObj, TAPDUAnswer *APDUAnswer) {
  if (env && ApduAnswerJObj && APDUAnswer) {
    jclass ApduAnswerClass = env->FindClass("ru/petroplus/pos/p7LibApi/responces/ApduAnswer");

    jfieldID SW1_ID  = env->GetFieldID(ApduAnswerClass, "sw1", "I");
    jfieldID SW2_ID  = env->GetFieldID(ApduAnswerClass, "sw2", "I");
    jfieldID Data_ID = env->GetFieldID(ApduAnswerClass, "data", "[B");

    jint jSW1 = env->GetIntField(*ApduAnswerJObj, SW1_ID);
    jint jSW2 = env->GetIntField(*ApduAnswerJObj, SW2_ID);
    jbyteArray jData = (jbyteArray)(env->GetObjectField(*ApduAnswerJObj, Data_ID));

    APDUAnswer->SW1 = jSW1;
    APDUAnswer->SW2 = jSW2;
    TP7LibTypes::JByteArrayToVector(env, jData, &APDUAnswer->Data);
  } // if env && ApduAnswerJObj && APDUAnswer
}
//--------------------------------------------------

void TP7LibTypes::CreateApduDataJObj(JNIEnv *env, jobject *ApduDataJObj) {
  if (env && ApduDataJObj) {
    if (*ApduDataJObj) {
      DeleteLocalRef(env, ApduDataJObj);
      ApduDataJObj = nullptr;  }

    jclass ApduDataClass = env->FindClass("ru/petroplus/pos/p7LibApi/requests/ApduData");
    jmethodID ApduDataConstructorID = env->GetMethodID(ApduDataClass, "<init>", "(BBBBB[BB)V");
    *ApduDataJObj = env->NewObject(ApduDataClass, ApduDataConstructorID,
                                     0, 0,  0, 0, 0, jbyteArray(), 0);
  } // if env && ApduDataJObj
}
//--------------------------------------------------

void TP7LibTypes::ConvertApduDataToJObj(JNIEnv *env, const TAPDUData &APDUData, jobject *ApduDataJObj) {
  if (env && ApduDataJObj) {
    if (*ApduDataJObj) {
      DeleteLocalRef(env, ApduDataJObj);
      ApduDataJObj = nullptr;  }

    jclass ApduDataClass = env->FindClass("ru/petroplus/pos/p7LibApi/requests/ApduData");
    CreateApduDataJObj(env, ApduDataJObj);

    jfieldID GLA_ID  = env->GetFieldID(ApduDataClass, "GLA", "B");
    jfieldID INS_ID  = env->GetFieldID(ApduDataClass, "INS", "B");
    jfieldID P1_ID  = env->GetFieldID(ApduDataClass, "P1", "B");
    jfieldID P2_ID  = env->GetFieldID(ApduDataClass, "P2", "B");
    jfieldID LC_ID  = env->GetFieldID(ApduDataClass, "LC", "B");
    jfieldID Data_ID = env->GetFieldID(ApduDataClass, "Data", "[B");
    jfieldID LE_ID  = env->GetFieldID(ApduDataClass, "LE", "B");

    env->SetByteField(*ApduDataJObj, GLA_ID, APDUData.CLA);
    env->SetByteField(*ApduDataJObj, INS_ID, APDUData.INS);
    env->SetByteField(*ApduDataJObj, P1_ID, APDUData.P1);
    env->SetByteField(*ApduDataJObj, P2_ID, APDUData.P2);
    env->SetByteField(*ApduDataJObj, GLA_ID, APDUData.LC);
    env->SetByteField(*ApduDataJObj, LE_ID, APDUData.LE);

    jbyteArray Array = nullptr;
    VectorToJByteArray(env, APDUData.Data, &Array);
    env->SetObjectField(*ApduDataJObj, Data_ID, Array);
    env->DeleteLocalRef(Array);
    Array = nullptr;
  } // if env && ApduDataJObj
}
//--------------------------------------------------





//---------------------------------------------------------------------------------------------------------------
#endif //POS_TYPES_CONVERSIONS_CPP