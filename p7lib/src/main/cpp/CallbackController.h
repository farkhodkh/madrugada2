#ifndef POS_CALLBACKCONTROLLER_H
#define POS_CALLBACKCONTROLLER_H
//---------------------------------------------------------------------------------------------------------------

#include <jni.h>
#include <android/log.h>

#include <vector>
#include <string>

#include "P7Lib_lib.h"
#include "TypesConversions.h"

using namespace P7Lib;
using namespace std;
//---------------------------------------------------------------------------------------------------------------

class TCallbackController {
private:
  static JNIEnv *getJniEnv();

  static JavaVM *JVM;
  static jobject CallbackObject;

  static jmethodID    LogCallbackID;

  static jmethodID    CardResetCallbackID;
  static TP7ErrorType CardReset(TAPDUAnswer &Answer);
  static jmethodID    CardAPDUCallbackID;
  static TP7ErrorType CardAPDU(const TAPDUData &Request, TAPDUAnswer &Answer);
  static jmethodID    SamResetCallbackID;
  static TP7ErrorType SamReset(TAPDUAnswer &Answer);
  static jmethodID    SamAPDUCallbackID;
  static TP7ErrorType SamAPDU(const TAPDUData &Request, TAPDUAnswer &Answer);

  static TP7ErrorType ResetCard(TAPDUAnswer &Answer, jmethodID *MethodID);
  static TP7ErrorType APDUCard(const TAPDUData &Request, TAPDUAnswer &Answer, jmethodID *MethodID);

  static jmethodID        TryASConnectionCallbackID;
  static bool             TryASConnection(int Timeout);
  static jmethodID        DoASDataExchangeCallbackID;
  static TOperationResult DoASDataExchange(const vector<unsigned char> &Data);

  static jmethodID    FindLastTransDBCallbackID;
  static TP7ErrorType FindLastTransDB(DWORD CardNumber, TTransDBRecord &DBRecord);
  static jmethodID    CompleteTransDBCallbackID;
  static TP7ErrorType CompleteTransDB(const TTransDBRecord &DBRecord);

  static jmethodID    PrintSimpleDocCallbackID;
  static TP7ErrorType PrintSimpleDoc(const TSimpleDoc &Doc);

  static jmethodID    TransferOOBToASCallbackID;
  static TP7ErrorType TransferOOBToAS(const vector<unsigned char> &OOB);

public:
  static TCallbacksSet GetCallbacks(JNIEnv *jniEnv, jobject &CallbackObjectJava);
  static void Free(void);
  static void Log(const std::string& msg);
};
//---------------------------------------------------------------------------------------------------------------



//---------------------------------------------------------------------------------------------------------------
#endif //POS_CALLBACKCONTROLLER_H
