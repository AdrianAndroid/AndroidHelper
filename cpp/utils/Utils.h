//
// Created by 赵健 on 2021/8/15.
//

#ifndef ANDROIDHELPER_UTILS_H
#define ANDROIDHELPER_UTILS_H

#include <vector>

using namespace std;

class Utils {
public:
    // utils
    static int getLen(int * arr);
    static void printVector(vector<int> v);
    static void printVector2(vector<vector<int>> obj);
    static void printVectorWithAddr(vector<int> obj);

    // source
    static vector<int> vector001();
    static vector<int> vectorFrom(int arr[]);
};


#endif //ANDROIDHELPER_UTILS_H
