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
  static bool JByteArrayToVector(JNIEnv *env, const jbyteArray &Array, std::vector<unsigned char> *Vector);
  static bool VectorToJByteArray(JNIEnv *env, const std::vector<unsigned char> &Vector, jbyteArray *Array);
  static bool ArrayToJByteArray(JNIEnv *env, const unsigned char *Data, int DSataSize, jbyteArray *Array);


  static bool JStringToString(JNIEnv *env, const jstring &jStr,    std::string &Str);
  static bool StringToJString(JNIEnv *env, const std::string &Str, jstring &jStr);

  static bool DeleteLocalRef(JNIEnv *env, jobject *ApduAnswerJObj);

  static TP7ErrorType GetResultCode(JNIEnv *env, const jobject *ResultCodeJObj);
  static bool ConvertResultCodeToJObj(JNIEnv *env, TP7ErrorType ResultCode, jobject *ResultCodeJObj);

  static bool CreateApduAnswerJObj(JNIEnv *env, jobject *ApduAnswerJObj);
  static bool ConvertApduAnswerFromJObj(JNIEnv *env, const jobject *ApduAnswerJObj, TAPDUAnswer *APDUAnswer);

  static bool CreateApduDataJObj(JNIEnv *env, jobject *ApduDataJObj);
  static bool ConvertApduDataToJObj(JNIEnv *env, const TAPDUData *APDUData, jobject *ApduDataJObj);

  static bool ConvertIniDataFromJObj(JNIEnv *env, const jobject *ApduDataJObj, TIniData *IniData);

  static bool CreateTransactionUUIDJObj(JNIEnv *env, jobject *UUIDJObj);
  static bool ConvertTransactionUUIDFromJObj(JNIEnv *env, const jobject *UUIDJObj, TTransactionUUID *UUID);
  static bool ConvertTransactionUUIDToJObj(JNIEnv *env, const TTransactionUUID *UUID, jobject *UUIDJObj);

  static bool ConvertOperationResultFromJObj(JNIEnv *env, const jobject *OperationResultJObj, TOperationResult *OperationResult);

  static bool CreateTransDBRecordJObj(JNIEnv *env, jobject *DBRecordJObj);
  static bool ConvertTransDBRecordFromJObj(JNIEnv *env, const jobject *DBRecordJObj, TTransDBRecord *DBRecord);
  static bool ConvertTransDBRecordToJObj(JNIEnv *env, const TTransDBRecord *DBRecord, jobject *DBRecordJObj);

  static bool CreateSimpleDocJObj(JNIEnv *env, jobject *SimpleDocJObj);
  static bool ConvertSimpleDocToJObj(JNIEnv *env, const TSimpleDoc *SimpleDoc, jobject *SimpleDocJObj);

  static bool CreateCardKeyJObj(JNIEnv *env, jobject *CardKeyJObj);
  static bool ConvertCardKeyToJObj(JNIEnv *env, const TCardKey *CardKey, jobject *CardKeyJObj);
  static bool ConvertCardKeyFromJObj(JNIEnv *env, const jobject *CardKeyJObj, TCardKey *CardKey);

  static TCardType GetCardType(JNIEnv *env, const jobject *CardTypeJObj);
  static bool ConvertCardTypeToJObj(JNIEnv *env,  TCardType CardType,jobject *CardTypeJObj);

  static bool CreateCardInfoJObj(JNIEnv *env, jobject *CardKeyJObj);
  static bool ConvertCardInfoToJObj(JNIEnv *env, const TCardInfo *CardInfo, jobject *CardInfoJObj);

  static bool CreateTransactionInfoJObj(JNIEnv *env, jobject *TransactionInfoJObj);
  static bool ConvertTransactionInfoToJObj(JNIEnv *env, const TTransactionInfo *TransInfo, jobject *TransInfoJObj);

  static bool ConvertDebetParamsFromJObj(JNIEnv *env, const jobject *DebetParamsJObj, TDebetParams *DebetParams);

  static bool ConvertRefundParamsFromJObj(JNIEnv *env, const jobject *RefundParamsJObj, TRefundParams *RefundParams);

  static bool CreateErrorInfoJObj(JNIEnv *env, jobject *ErrorInfoJObj);
  static bool ConvertErrorInfoToJObj(JNIEnv *env, const TErrorInfo *ErrorInfo, jobject *ErrorInfoJObj);

  static bool CreateLibInfoJObj(JNIEnv *env, jobject *LibInfoJObj);
  static bool ConvertLibInfoToJObj(JNIEnv *env, const TLibInfo *LibInfo, jobject *LibInfoJObj);

  static bool CreateStTimeJObj(JNIEnv *env, jobject *StTimeJObj);
  static bool ConvertStTimeToJObj(JNIEnv *env, const TSTTime *StTime, jobject *StTimeJObj);
  static bool ConvertStTimeFromJObj(JNIEnv *env, const jobject *StTimeJObj, TSTTime *StTime);




};
//---------------------------------------------------------------------------------------------------------------



//---------------------------------------------------------------------------------------------------------------
#endif //POS_TYPES_CONVERSIONS_H
