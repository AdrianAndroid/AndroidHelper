cmake_minimum_required(VERSION 3.17)
project(AndroidHelper)

set(CMAKE_CXX_STANDARD 14)

add_executable(AndroidHelper main.cpp
        LeetCode.cpp LeetCode.h
        practice/VectorTest.cpp practice/VectorTest.h
        utils/Utils.cpp utils/Utils.h)