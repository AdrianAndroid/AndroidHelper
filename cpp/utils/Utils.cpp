//
// Created by 赵健 on 2021/8/15.
//

#include <iostream>
#include "Utils.h"

void Utils::printVector(vector<int> obj) {
    vector<int>::iterator it;
    for (it = obj.begin(); it != obj.end(); it++) {
        cout << *it << " ";
    }
}

void Utils::printVector2(vector<vector<int>> obj) {
    vector<vector<int>>::iterator it;
    for (it = obj.begin(); it != obj.end(); it++) {
        printVector(*it);
    }
}

vector<int> Utils::vector001() {
    vector<int> obj;
    int nums[] = {2, 7, 11, 15};

    obj.reserve(getLen(nums));
    for (int i = 0; i < getLen(nums); ++i) {
        obj.push_back(nums[i]);
    }
    return obj;
}

int Utils::getLen(int * ar) {
    int cnt = sizeof(*ar) / sizeof(ar[0]);
    return cnt;
}

vector<int> Utils::vectorFrom(int *arr) {
    int len = getLen(arr);
    vector<int> obj(len);
    for (int i = 0; i < len; ++i) {
        obj.push_back(arr[i]);
    }
    return obj;
}

void Utils::printVectorWithAddr(vector<int> obj) {
//    vector<int>::iterator it;
//    for (it = obj.begin(); it != obj.end(); it++) {
//
//    }
}
