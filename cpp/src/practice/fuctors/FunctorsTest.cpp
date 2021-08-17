//
// Created by 赵健 on 2021/8/17.
//

#include "FunctorsTest.h"
#include "../../utils/Utils.h"
#include <list>


void FunctorsTest::test() {
    Linear f1;
    Linear f2(2.5, 10.0);
    double y1 = f1(12.5); // right-hand side is f1.operator()(12.5)
    double y2 = f2(0.4);

    using std::list;
    using std::cout;
    using std::endl;

    TooBig<int> f100(100); // limit = 100
    int vals[10] = {50, 100, 90, 180, 60, 210, 415, 88, 188, 201};
    list<int> yadayada(vals, vals + 10); // range constructor
    list<int> etcetera(vals, vals + 10);

    // C++11 can use the following instead.
    // list<int> yadayada = {50, 100, 90, 180, 60, 210, 415, 88, 188, 201};
    // list<int> etcetera = {50, 100, 90, 180, 60, 210, 415, 88, 188, 201};
    cout << "Original lists:\n";
    for_each(yadayada.begin(), yadayada.end(), Utils::outint);
    cout << endl;
    for_each(etcetera.begin(), etcetera.end(), Utils::outint);
    cout << endl;

    yadayada.remove_if(f100);                     // use a named function object
    etcetera.remove_if(TooBig<int>(200)); // consturct a function object
    cout << "Trimmed lists:\n";
    for_each(yadayada.begin(), yadayada.end(), Utils::outint);
    cout << endl;
    for_each(etcetera.begin(), etcetera.end(), Utils::outint);
    cout << endl;
}
