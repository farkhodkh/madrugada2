# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.18.1)

# Declares and names the project.

project("p7lib")

message("${CMAKE_SYSTEM_NAME} is target system")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

set (COMMON_PRJCTS_DIR ${PROJECT_SOURCE_DIR}/../../../../..)
set (P7LIB_LIB_DIR ${COMMON_PRJCTS_DIR}/P7LIB_LIB)
set (P7LIB_DLL_DIR ${COMMON_PRJCTS_DIR}/P7LIB_DLL)

message("COMMON_PRJCTS_DIR is ${COMMON_PRJCTS_DIR}")
message("P7LIB_LIB_DIR     is ${P7LIB_LIB_DIR}")
message("P7LIB_DLL_DIR     is ${P7LIB_DLL_DIR}")


add_subdirectory(libP7Lib)


add_library( # Sets the name of the library.
        p7lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        p7lib.cpp
        TypesConversions.h
        TypesConversions.cpp
        CallbackController.h
        CallbackController.cpp
)

################################################################################
# Include directories
################################################################################



#set (P7LIB_LIB_DIR "${CMAKE_CURRENT_SOURCE_DIR}/../P7Lib;")

include_directories(p7lib
        "${P7LIB_LIB_DIR};"
)


# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.




target_link_libraries( # Specifies the target library.
        p7lib

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib}
        "P7Lib_lib")