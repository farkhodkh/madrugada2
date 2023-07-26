set(LIB_NAME P7Lib_lib)

################################################################################
# Source groups
################################################################################

set(Self
    "${P7LIB_LIB_DIR}/P7Lib_lib.h"
    "${P7LIB_LIB_DIR}/P7Lib_lib.cpp"
    "${P7LIB_LIB_DIR}/P7Lib_types.h"
)
source_group("Self" FILES ${Self})

set(P7LibSrc
    "${P7LIB_LIB_DIR}/P7LibSrc/ERR/ERR.H"
    "${P7LIB_LIB_DIR}/P7LibSrc/ERR/p7_errors.cpp"
    "${P7LIB_LIB_DIR}/P7LibSrc/ERR/p7_errors.h"
    "${P7LIB_LIB_DIR}/P7LibSrc/ERR/p7_errors_res.cpp"
    "${P7LIB_LIB_DIR}/P7LibSrc/ERR/p7_errors_res.h"
)
source_group("P7LibSrc" FILES ${P7LibSrc})


set(ALL_FILES
    ${Self}
    ${P7LibSrc}
)

################################################################################
# Target
################################################################################

ADD_LIBRARY(${LIB_NAME} ${ALL_FILES})

# set_target_properties(${LIB_NAME} PROPERTIES
    # LIBRARY_OUTPUT_DIRECTORY_DEBUG "${PROJECT_SOURCE_DIR}/Debug"
    # RUNTIME_OUTPUT_DIRECTORY_DEBUG "${PROJECT_SOURCE_DIR}/Debug"
	# # ARCHIVE_OUTPUT_DIRECTORY_DEBUG "${PROJECT_SOURCE_DIR}/Debug"
    # LIBRARY_OUTPUT_DIRECTORY_RELEASE "${PROJECT_SOURCE_DIR}/Release"
    # RUNTIME_OUTPUT_DIRECTORY_RELEASE "${PROJECT_SOURCE_DIR}/Release"
	# # ARCHIVE_OUTPUT_DIRECTORY_RELEASE "${PROJECT_SOURCE_DIR}/Release"
# )

################################################################################
# Include directories
################################################################################
target_include_directories(${LIB_NAME} PUBLIC
    "${CMAKE_CURRENT_SOURCE_DIR}/P7LibSrc;"

    #Временно для отладки (до переезда проекта LIB)
    "${P7LIB_DLL_DIR}/P7Lib;"
)


# ################################################################################
# # Dependencies
# ################################################################################
# set(ADDITIONAL_LIBRARY_DEPENDENCIES
    # "kernel32;"
    # "user32;"
    # "gdi32;"
    # "winspool;"
    # "comdlg32;"
    # "advapi32;"
    # "shell32;"
    # "ole32;"
    # "oleaut32;"
    # "uuid;"
    # "odbc32;"
    # "odbccp32;"
    # "comctl32;"
    # "shlwapi"
# )
# target_link_libraries(${LIB_NAME} PRIVATE "${ADDITIONAL_LIBRARY_DEPENDENCIES}")

