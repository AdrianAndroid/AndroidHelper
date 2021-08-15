#include <iostream>
#include "practice/VectorTest.h"
#include "utils/Utils.h"
#include "LeetCode.h"

int main() {
    std::cout << "Hello, World!" << std::endl;

    LeetCode leetCode;
    vector<int> obj = Utils::vector001();
    vector<int> result = leetCode.twoSum(obj, 9);
    Utils::printVector(result);

//    VectorTest vt;
//    vt.test();
    return 0;
}
