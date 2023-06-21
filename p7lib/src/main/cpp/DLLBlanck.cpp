#ifndef DLLBlanck_CPP
#define DLLBlanck_CPP

#define DLLBlanck_BUILD

#include "DLLBlanck.h"

#include <iostream>
#include <stdio.h>


//-------------------------------------------------------------------------------------------------------------------



using namespace std;
//-------------------------------------------------------------------------------------------------------------------

class TBuffedMsg {
 public:
  TBuffedMsg(void) {
    Msg = NULL;
    MsgSize = 0;
  }
  ~TBuffedMsg(void) {
    Free();
  }
  void Free(void) {
    if (Msg) {
      delete[] Msg;
      Msg = NULL;  }
    MsgSize = 0;  
  }

  unsigned char *Msg;  
  int            MsgSize;
};



//-------------------------------------------------------------------------------------------------------------------



static const int MajorVersion = 0;
static const int MinerVersion = 1;

static TCallbacksSet CallbacksSet;

static const std::string ClientDLLName = "ClientDLL.dll";

static const std::string ClientDLLPath = "";


TBuffedMsg CurrentMsg;

//-------------------------------------------------------------------------------------------------------------------

void GetDLLVersion(int *Major, int *Minor) {
  if (CallbacksSet.pLog) {
    CallbacksSet.pLog("C++: Call GetDLLVersion");  }

  *Major = MajorVersion;
  *Minor = MinerVersion;
}
//------------------------------------------------------------------------
  
bool Init(TCallbacksSet Callbacks) {
  if (CallbacksSet.pLog) {
    CallbacksSet.pLog("C++: Call Init");  }

  CallbacksSet = Callbacks;
  return true;
}
//------------------------------------------------------------------------

bool IsNewMsg(int *NewMsgBuffSize) {
  if (CallbacksSet.pLog) {
    CallbacksSet.pLog("C++: Call IsNewMsg");  }

  bool Result = false;

  if (CurrentMsg.MsgSize) {
    Result = true;
    *NewMsgBuffSize = CurrentMsg.MsgSize;  }

  return Result;
}
//------------------------------------------------------------------------

int GetNewMsg(char *Buff, int BuffSize) {
  if (CallbacksSet.pLog) {
    CallbacksSet.pLog("C++: Call GetNewMsg");  }

  int MsgSize = 0;

  if (CurrentMsg.MsgSize) {
    MsgSize = (CurrentMsg.MsgSize < BuffSize) ? CurrentMsg.MsgSize : BuffSize;
    memcpy(Buff, CurrentMsg.Msg, MsgSize);
    CurrentMsg.Free();
  }

  return MsgSize;
}
//------------------------------------------------------------------------

bool SendMsg(const char *Buff, int BuffSize) {
  if (CallbacksSet.pLog) {
    CallbacksSet.pLog("C++: Call SendMsg");  }

  bool Result = false;

  if (Buff && BuffSize) {
    if (!CurrentMsg.MsgSize) {
      CurrentMsg.Free();
      CurrentMsg.MsgSize = BuffSize;
      CurrentMsg.Msg     = new unsigned char [BuffSize];
      memcpy(CurrentMsg.Msg, Buff, BuffSize);

      Result = true;

      if (CallbacksSet.pVoidIntFunc) {
        CallbacksSet.pVoidIntFunc(BuffSize);  }

      if (CallbacksSet.pChangeParam) {
        TSomeType Value;
        Value.IntVal    = 8;
        Value.DoubleVal = 1.2578;
        Value.BoolVal   = true;
        Value.CharVal   = 'G';
        Value.StringVal = "Арбат";

        int Ret = CallbacksSet.pChangeParam(Value);
        Ret = 0;
      }
    }
  }

  return Result;
}
//------------------------------------------------------------------------

bool CreateFile(const std::string &FileName, const std::string &FileData) {
  if (CallbacksSet.pLog) {
    CallbacksSet.pLog(std::string("C++: Call CreateFile.\nFilePath: ") + FileName);  }

  bool Result    = true;
  int  ErrNumber = 0;
  std::string ErrStr;
  FILE *pFile = nullptr;

  pFile = fopen(FileName.c_str(), "w");
  if (pFile) {
    if (fputs (FileData.c_str(),pFile) < 0) {
      ErrStr = "Writing Error.";
      Result = false;
      ErrNumber = ferror(pFile);  }
  } // if pFile
  else {
    ErrStr = "Opening Error.";
    ErrNumber = errno;
    Result    = false;  }

  if (pFile) {
    fclose(pFile);
    pFile = nullptr;  }

  if (!Result) {
    ErrStr += " Error number is " + std::to_string(ErrNumber);
    if (CallbacksSet.pLog) {
      CallbacksSet.pLog(std::string("C++: CreateFile. ") + ErrStr);  }
  } // if !Result

  return Result;
}
//------------------------------------------------------------------------

bool ReadFile(const std::string &FileName, std::string &FileData) {
  if (CallbacksSet.pLog) {
    CallbacksSet.pLog(std::string("C++: Call ReadFile.\nFilePath: ") + FileName);  }

  bool Result    = true;
  int  ErrNumber = 0;
  std::string ErrStr;
  FILE *pFile = nullptr;

  pFile = fopen(FileName.c_str(), "r");
  if (pFile) {
    const int BuffSize = 100;
    char Buff[BuffSize + 1];
    FileData = "";
    while (!feof(pFile)) {
      memset(Buff, 0x00, BuffSize + 1);
      // в мультибайт кодировке на стыке м.б. рроблемы
      int Ret = fread(Buff, 1, BuffSize, pFile);
      if (Ret > 0) {
        FileData = FileData + std::string(Buff);  }
      else {
        if (!feof(pFile)) {
          ErrStr = "Reading Error.";
          Result = false;
          ErrNumber = ferror(pFile);  }
        break;
      }
    } // while feof()
  } // if pFile
  else {
    ErrStr = "Opening Error.";
    ErrNumber = errno;
    Result    = false;  }

  if (pFile) {
    fclose(pFile);
    pFile = nullptr;  }

  if (!Result) {
    ErrStr += " Error number is " + std::to_string(ErrNumber);
    if (CallbacksSet.pLog) {
      CallbacksSet.pLog(std::string("C++: CreateFile. ") + ErrStr);  }
  } // if !Result

  return Result;
}
//------------------------------------------------------------------------

TSomeType GetSomeTypeValue(void) {
  TSomeType SomeVal; 	
  SomeVal.IntVal = 2;	
  SomeVal.DoubleVal = 3.45;
  SomeVal.BoolVal = true;
  SomeVal.CharVal = {5};
  SomeVal.StringVal = "SomeStringFromC++";
  return SomeVal;
}

//------------------------------------------------------------------------

void GetSomeTypeValueParam(TSomeType *Value) {
  if (!Value) {
    return;  }	
  //TSomeType SomeVal;

  Value->IntVal = 2;
  Value->DoubleVal = 3.45;
  Value->BoolVal = true;
  Value->CharVal = {5};
  Value->StringVal = "SomeStringFromC++";

//  SomeVal.IntVal = 2;
//  SomeVal.DoubleVal = 3.45;
//  SomeVal.BoolVal = true;
//  SomeVal.CharVal = {5};
//  SomeVal.StringVal = "SomeStringFromC++";

  //*Value = SomeVal;
}
//------------------------------------------------------------------------



//-------------------------------------------------------------------------------------------------------------------
#endif // DLLBlanck_CPP