//
// Created by 赵健 on 2021/8/17.
//

#ifndef ANDROIDHELPER_STATICDEMO_H
#define ANDROIDHELPER_STATICDEMO_H

#include <iostream>

class StaticDemo {

};

class Point {
public:
    void output() {}

    static void init() {
        x = 0;
        y = 0;
    }

private:
    static int x;
    static int y;

    void test() {
        Point::init();
    }
};


#endif //ANDROIDHELPER_STATICDEMO_H
