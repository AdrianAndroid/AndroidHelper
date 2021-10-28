cmake_minimum_required(VERSION 3.17)
project(AndroidHelper)

set(CMAKE_CXX_STANDARD 14)

add_executable(AndroidHelper src/main.cpp
        src/LeetCode.cpp src/LeetCode.h
        src/practice/vectors/VectorTest.cpp src/practice/vectors/VectorTest.h
        src/utils/Utils.cpp src/utils/Utils.h
        src/practice/vectors/vect1.h src/practice/vectors/vect1.cpp
        src/practice/vectors/vect2.cpp src/practice/vectors/vect2.h
        src/practice/vectors/vect3.cpp src/practice/vectors/vect3.h
        src/practice/statics/StaticDemo.cpp src/practice/statics/StaticDemo.h
        src/common/Review.h src/common/Review.cpp
        src/common/Node.cpp src/common/Node.h
        src/common/iterator.cpp src/common/iterator.h
        src/practice/vectors/copyit.cpp src/practice/vectors/copyit.h
        src/practice/vectors/inserts.cpp src/practice/vectors/inserts.h
        src/practice/lists/ListTest.cpp src/practice/lists/ListTest.h
        src/practice/sets/setops.cpp src/practice/sets/setops.h
        src/practice/map/MultiMaps.cpp src/practice/map/MultiMaps.h
        src/practice/fuctors/FunctorsTest.cpp src/practice/fuctors/FunctorsTest.h
        src/practice/fuctors/funadap.cpp src/practice/fuctors/funadap.h
        src/practice/strings/strgst1.cpp src/practice/strings/strgst1.h
        src/practice/lists/listrmv.cpp src/practice/lists/listrmv.h src/practice/alogs/usealgo.cpp src/practice/alogs/usealgo.h src/practice/arrays/valvect.cpp src/practice/arrays/valvect.h src/practice/lists/ilist.cpp src/practice/lists/ilist.h src/practice/mmap/mmap_demo.cpp src/practice/mmap/mmap_demo.h src/datastruct/Graph.cpp src/datastruct/Graph.h)