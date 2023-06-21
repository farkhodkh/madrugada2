#ifndef DLLBlanck_H
#define DLLBlanck_H


#include <string>

#include "Callbacks.h"

//-------------------------------------------------------------------------------------------------------------------

//-------------------------------------------------------------------------------------------------------------------

typedef void (*TCallBackMsgReady)(int NewMsgfSize);
//-------------------------------------------------------------------------------------------------------------------


////-------------------------------------------------------------------------------------------------------------------
//#ifdef __cplusplus
//extern "C" {
//#endif
////-------------------------------------------------------------------------------------------------------------------

void GetDLLVersion(int *Major, int *Minor);

bool Init(TCallbacksSet Callbacks);

bool IsNewMsg(int *NewMsgSize);

int  GetNewMsg(char *Buff, int BuffSize);

bool SendMsg(const char *Buff, int BuffSize);

bool CreateFile(const std::string &FileName, const std::string &FileData);
bool ReadFile(const std::string &FileName, std::string &FileData);

//-------------------------------------------------------

TSomeType GetSomeTypeValue(void);
void      GetSomeTypeValueParam(TSomeType *Value);

////-------------------------------------------------------------------------------------------------------------------
//#ifdef __cplusplus
//}
//#endif
////-------------------------------------------------------------------------------------------------------------------

//-------------------------------------------------------------------------------------------------------------------
#endif // DLLBlanck_H