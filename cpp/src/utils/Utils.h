//
// Created by 赵健 on 2021/8/15.
//

#ifndef ANDROIDHELPER_UTILS_H
#define ANDROIDHELPER_UTILS_H

#include <vector>
#include <list>
#include <string>

using namespace std;

class Utils {
public:
    // utils
    static int getLen(int * arr);
    static void printVector(vector<int> v);
    static void printVector2(vector<vector<int>> obj);
    static void printVectorWithAddr(vector<int> obj);
    static void output(const std::string & s);

    // int
    static void outint(int num);
    static void ShowInt(int);

    // list
    static void printList(list<int> l);

    // string
    static void println(const std::string & s);
    static void display(const string & s);

    // double
    static void ShowDouble(double);

    // source
    static vector<int> vector001();
    static vector<int> vectorFrom(int arr[]);

    // char
};


#endif //ANDROIDHELPER_UTILS_H
