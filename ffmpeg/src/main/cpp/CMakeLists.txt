cmake_minimum_required(VERSION 3.10.2)
project("ffmpeg")
#添加头文件路径（相对于本文件路径）
include_directories(include)

#设置ffmpeg库所在路径的变量
set(FF ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/${ANDROID_ABI})
message(WARNING "${FF}")
add_library(avcodec SHARED IMPORTED)
set_target_properties(avcodec PROPERTIES IMPORTED_LOCATION ${FF}/libavcodec.so)
add_library(
        native-lib
        SHARED
        native-lib.cpp )
find_library(
        log-lib
        log )
target_link_libraries(
        native-lib
        avcodec
        ${log-lib} )