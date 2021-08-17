#include <iostream>
#include "practice/vectors/VectorTest.h"
#include "utils/Utils.h"
#include "LeetCode.h"
#include "practice/vectors/vect1.h"
#include "practice/vectors/vect2.h"
#include "practice/vectors/vect3.h"


void leetcodeTest();

void otherTest();
void forTest();
void vectorTest();

int main() {
    std::cout << "Hello, World!" << std::endl;
    vectorTest();
    //forTest();
//    main_vector1();
//    leetcodeTest();
    return 0;
}


void forTest() {
    int i;
    for (i = 0; i < 10; ++i) {
        cout << i << "\t";
    }
}

void otherTest() {
    //    VectorTest vt;
    //    vt.test();
}

void leetcodeTest() {
    //    LeetCode leetCode;
//    vector<int> obj = Utils::vector001();
//    vector<int> result = leetCode.twoSum(obj, 9);
//    Utils::printVector(result);
}

void vectorTest() {
//    vect2 v2;
//    v2.test();

    vect3 v3;
    v3.test();
}
