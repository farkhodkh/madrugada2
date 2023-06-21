//
// Created by Yuriy.Poprygushin on 30.03.2023.
//

#ifndef NATIVELIBSAMPLE_CALLBACKS_H
#define NATIVELIBSAMPLE_CALLBACKS_H
//--------------------------------------------------------------------------------------------------

#include <string>
//--------------------------------------------------------------------------------------------------

class TSomeType {
public:
  TSomeType(void) {
    Reset();
  }

  int    IntVal;
  double DoubleVal;
  bool   BoolVal;
  char   CharVal;
  std::string StringVal;

  void Reset(void) {
    IntVal    = 0;
    DoubleVal = 0;
    BoolVal = false;
    CharVal = {};
    StringVal = "";
  }
};
//-------------------------------------------------------------------------------------------------------------------

enum TBackclallTypes { bctNon = 0, bctLOG = 1, bctVoidInt = 2 };
//--------------------------------------------------------------------------------------------------

struct TCallbacksSet {
  void (*pLog)(std::string Str);
  void (*pVoidIntFunc)(int);
  int  (*pChangeParam)(TSomeType &Value);

  TCallbacksSet(void) {
    pLog         = nullptr;
    pVoidIntFunc = nullptr;
    pChangeParam = nullptr;
  }
};
//--------------------------------------------------------------------------------------------------




//--------------------------------------------------------------------------------------------------
#endif //NATIVELIBSAMPLE_CALLBACKS_H
