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

        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TIME/_time.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TIME/_time.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TIME/StTime.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TIME/StTime.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TIME/Tpc_comp.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TIME/Tpc_comp.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TIME/Timer.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TIME/Timer.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TIME/TimeProc.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TIME/TimeProc.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TIME/tm_dt.h"

        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/FILES/FileOperationsHelper.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/FILES/fileemul.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/FILES/fileemul.cpp"

        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TLV/tlv_parser.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/TLV/tlv_parser.cpp"

        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/STRINGS/StringUtils.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/STRINGS/StringUtils.cpp"

        "${P7LIB_LIB_DIR}/P7LibSrc/convert.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/convert.cpp"



        )
source_group("P7LibSrc" FILES ${P7LibSrc})

set(Cripto
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/aescrypto.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/aescrypto.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/ocb.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/ocb.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/rijndael-alg-fst.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/rijndael-alg-fst.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/rijndael-api-fst.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/rijndael-api-fst.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/srng.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/srng.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/curve25519_i64.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/curve25519_i64.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/ecdh.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/ecdh.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/pp_uuid.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/pp_uuid.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/crc32.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/UTILS/CRIPTO/crc32.c"
        )
source_group("Cripto" FILES ${Cripto})

# PolarSSL 1.3.22
set(PolarSSL
        "${PolarSSL_SRC}/aes.c"
        "${PolarSSL_SRC}/aesni.c"
        "${PolarSSL_SRC}/arc4.c"
        "${PolarSSL_SRC}/asn1parse.c"
        "${PolarSSL_SRC}/asn1write.c"
        "${PolarSSL_SRC}/base64.c"
        "${PolarSSL_SRC}/bignum.c"
        "${PolarSSL_SRC}/blowfish.c"
        "${PolarSSL_SRC}/camellia.c"
        "${PolarSSL_SRC}/ccm.c"
        "${PolarSSL_SRC}/certs.c"
        "${PolarSSL_SRC}/cipher.c"
        "${PolarSSL_SRC}/cipher_wrap.c"
        "${PolarSSL_SRC}/ctr_drbg.c"
        "${PolarSSL_SRC}/debug.c"
        "${PolarSSL_SRC}/des.c"
        "${PolarSSL_SRC}/dhm.c"
        "${PolarSSL_SRC}/ecp.c"
        "${PolarSSL_SRC}/ecp_curves.c"
        "${PolarSSL_SRC}/ecdh.c"
        "${PolarSSL_SRC}/ecdsa.c"
        "${PolarSSL_SRC}/entropy.c"
        "${PolarSSL_SRC}/entropy_poll.c"
        "${PolarSSL_SRC}/error.c"
        "${PolarSSL_SRC}/gcm.c"
        "${PolarSSL_SRC}/havege.c"
        "${PolarSSL_SRC}/hmac_drbg.c"
        "${PolarSSL_SRC}/md.c"
        "${PolarSSL_SRC}/md_wrap.c"
        "${PolarSSL_SRC}/md2.c"
        "${PolarSSL_SRC}/md4.c"
        "${PolarSSL_SRC}/md5.c"
        "${PolarSSL_SRC}/memory_buffer_alloc.c"
        "${PolarSSL_SRC}/net.c"
        "${PolarSSL_SRC}/oid.c"
        "${PolarSSL_SRC}/padlock.c"
        "${PolarSSL_SRC}/pbkdf2.c"
        "${PolarSSL_SRC}/pem.c"
        "${PolarSSL_SRC}/pkcs5.c"
        "${PolarSSL_SRC}/pkcs11.c"
        "${PolarSSL_SRC}/pkcs12.c"
        "${PolarSSL_SRC}/pk.c"
        "${PolarSSL_SRC}/pk_wrap.c"
        "${PolarSSL_SRC}/pkparse.c"
        "${PolarSSL_SRC}/pkwrite.c"
        "${PolarSSL_SRC}/platform.c"
        "${PolarSSL_SRC}/ripemd160.c"
        "${PolarSSL_SRC}/rsa.c"
        "${PolarSSL_SRC}/sha1.c"
        "${PolarSSL_SRC}/sha256.c"
        "${PolarSSL_SRC}/sha512.c"
        "${PolarSSL_SRC}/ssl_cache.c"
        "${PolarSSL_SRC}/ssl_ciphersuites.c"
        "${PolarSSL_SRC}/ssl_cli.c"
        "${PolarSSL_SRC}/ssl_srv.c"
        "${PolarSSL_SRC}/ssl_tls.c"
        "${PolarSSL_SRC}/threading.c"
        "${PolarSSL_SRC}/timing.c"
        "${PolarSSL_SRC}/version.c"
        "${PolarSSL_SRC}/version_features.c"
        "${PolarSSL_SRC}/x509.c"
        "${PolarSSL_SRC}/x509_crt.c"
        "${PolarSSL_SRC}/x509_crl.c"
        "${PolarSSL_SRC}/x509_csr.c"
        "${PolarSSL_SRC}/x509_create.c"
        "${PolarSSL_SRC}/x509write_crt.c"
        "${PolarSSL_SRC}/x509write_csr.c"
        "${PolarSSL_SRC}/xtea.c"
        )
source_group("PolarSSL" FILES ${PolarSSL})

set(JavaCards
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/fleetcor_defines.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/fleetcor_public_keys.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/java_card_cache.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/jcc_errors.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/ppj_cryptogram.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/ppj_cryptogram.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/jcc_baseconn.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/jcc_baseconn.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/sam_conn.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/sam_conn.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/sam_wrapper.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/sam_wrapper.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/pp_java.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/pp_java.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/ppj_secsam.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/ppj_secsam.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/ppj_wrapper.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/JavaCards/ppj_wrapper.cpp"
        )
source_group("JavaCards" FILES ${JavaCards})

set(Online
        "${P7LIB_LIB_DIR}/P7LibSrc/Online/Act_Online_Cards.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/Online/Act_Online_Cards.cpp"


        )
source_group("Online" FILES ${Online})

set(Common
        "${P7LIB_LIB_DIR}/P7LibSrc/COMMON/Big_calc.c"
        "${P7LIB_LIB_DIR}/P7LibSrc/COMMON/Big_calc.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/COMMON/global.cpp"
        "${P7LIB_LIB_DIR}/P7LibSrc/COMMON/global.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/COMMON/servname.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/COMMON/pagefile.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/COMMON/pagefile.c"
        "${P7LIB_LIB_DIR}/P7LibSrc/COMMON/Tranzit_.h"
        "${P7LIB_LIB_DIR}/P7LibSrc/COMMON/Tranzit_.cpp"



        )
source_group("Common" FILES ${Common})



set(ALL_FILES
        ${Self}
        ${P7LibSrc}
        ${Cripto}
        ${PolarSSL}
        ${JavaCards}
        ${Online}
        ${Common}
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
        "${P7LIB_LIB_DIR}"
        "${P7LIB_LIB_DIR}/P7LibSrc"
        "${PolarSSL_INC}"

        #Временно для отладки (до переезда проекта LIB)
        "${P7LIB_DLL_DIR}"
        )

################################################################################
# Compile definitions
################################################################################
target_compile_definitions(${LIB_NAME} PRIVATE
        "EMULATE_SAM"
        "PPJC_REMOTE_CHANGE"
        "ENABLE_RETURN"
        )

################################################################################
# Compile and link options
################################################################################
if(MSVC)
    # Использование /Zp1 весьма нежелательно, но необхлдимо из-за его задания в P7Lib.dll.
    # Выделить классы, которые должны быть упаковыны в P7Lib с выравниванием на 1 байт
    # и обозначить их соответсвующей прагмой, на данный момент возможжным не представляется.

    target_compile_options(${LIB_NAME} PRIVATE
            /Od
            /Zp1;
            )
    string(CONCAT FILE_CL_OPTIONS
            "$<$<CONFIG:Release>:"
            "/Od"
            ">"
            )

    # ####### !!!
    # #    source_file_compile_options(JavaCards/ppj_secsam.cpp ${FILE_CL_OPTIONS})
    # ####### !!!

    target_link_options(${LIB_NAME} PRIVATE
            /SUBSYSTEM:WINDOWS
            )
endif()