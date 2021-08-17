//
// Created by 赵健 on 2021/8/17.
//

#ifndef ANDROIDHELPER_FUNCTORSTEST_H
#define ANDROIDHELPER_FUNCTORSTEST_H

#include <iostream>

class FunctorsTest {
public:
    void test();
};

class Linear {
private:
    double slope;
    double y0;
public:
    Linear(double s1_ = 1, double y_ = 0) : slope(s1_), y0(y_) {
        std::cout << "Linear 构造函数" << std::endl;
    };

    double operator()(double x) {
        std::cout << "Linear ()(double x)" << std::endl;
        return y0 + slope * x;
    }
};

template<class T>
class TooBig {
private:
    T cutoff;
public:
    TooBig(const T &t) {}

    bool operator()(const T &v) { return v > cutoff; }
};


template<class T>
bool tooBig(const T &val, const T &lim) {
    return val > lim;
}

template<class T>
class TooBig2 {
private:
    T cutoff;
public:
    TooBig2(const T &t) : cutoff(t) {}
    bool operator()(const T & v) {return tooBig<T>(v, cutoff);}
};


#endif //ANDROIDHELPER_FUNCTORSTEST_H
