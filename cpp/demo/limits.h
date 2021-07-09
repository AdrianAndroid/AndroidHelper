//
// Created by 赵健 on 2021/7/9.
//

#ifndef DEMO_LIMITS_H
#define DEMO_LIMITS_H

#include <iostream>
#include <climits>
#include <string>

using namespace std;

class limits {
private:
    string s;
public:
    limits() : s(){
        cout << "constructor s="<< s << endl;
    }
    ~limits(){
        cout << "un constructor" << endl;
    }

    void test() {
        using namespace std;
        int n_int = INT_MAX; // initiallize n_int to max int value
        short n_short = SHRT_MAX;
        long n_long = LONG_MAX;
        long long n_llong = LLONG_MAX;

        // sizeof operator yields size of type or of variable
        cout << "int is " << sizeof(int) << " bytes." << endl;
        cout << "short is " << sizeof n_short << " bytes." << endl;
        cout << "long is " << sizeof n_long << " bytes." << endl;
        cout << "long long is " << sizeof n_llong << " bytes." << endl;
        cout << endl;


    }
};


#endif //DEMO_LIMITS_H
