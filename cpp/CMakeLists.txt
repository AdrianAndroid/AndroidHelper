cmake_minimum_required(VERSION 3.17)
project(AndroidHelper)

set(CMAKE_CXX_STANDARD 14)

add_executable(AndroidHelper main.cpp
        LeetCode.cpp LeetCode.h
        practice/vectors/VectorTest.cpp practice/vectors/VectorTest.h
        utils/Utils.cpp utils/Utils.h
        practice/vectors/vect1.h practice/vectors/vect1.cpp
        practice/vectors/vect2.cpp practice/vectors/vect2.h
        practice/vectors/vect3.cpp practice/vectors/vect3.h
        practice/statics/StaticDemo.cpp practice/statics/StaticDemo.h
        common/Review.h common/Review.cpp common/Node.cpp common/Node.h common/iterator.cpp common/iterator.h)