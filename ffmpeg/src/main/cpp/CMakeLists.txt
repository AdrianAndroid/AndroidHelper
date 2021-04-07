cmake_minimum_required(VERSION 3.10.2)
project("ffmpeg")
#添加头文件路径（相对于本文件路径）
include_directories(include)

#设置ffmpeg库所在路径的变量
set(FF ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/${ANDROID_ABI})
message(WARNING "${FF}")

add_library(avcodec SHARED IMPORTED)
set_target_properties(avcodec PROPERTIES IMPORTED_LOCATION ${FF}/libavcodec.so)

add_library(avformat SHARED IMPORTED)
set_target_properties(avformat PROPERTIES IMPORTED_LOCATION ${FF}/libavformat.so)

add_library(avutil SHARED IMPORTED)
set_target_properties(avutil PROPERTIES IMPORTED_LOCATION ${FF}/libavutil.so)

add_library(swscale SHARED IMPORTED)
set_target_properties(swscale PROPERTIES IMPORTED_LOCATION ${FF}/libswscale.so)

add_library(swresample SHARED IMPORTED)
set_target_properties(swresample PROPERTIES IMPORTED_LOCATION ${FF}/libswresample.so)


add_library(
        native-lib
        SHARED
        native-lib.cpp )
find_library(
        log-lib
        log )
target_link_libraries(
        native-lib
        avcodec avformat avutil swscale swresample
        ${log-lib} )