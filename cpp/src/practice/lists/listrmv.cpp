//
// Created by 赵健 on 2021/8/17.
//

#include "listrmv.h"
#include "../../utils/Utils.h"
#include <iostream>
#include <algorithm>
#include <list>

const int LIM = 10;

void listrmv::test() {
    using namespace std;
    int ar[LIM] = {4, 5, 4, 2, 2, 3, 4, 8, 1, 4};
    list<int> la(ar, ar + LIM);
    list<int> lb(la);
    cout << "Original list contents:\n\t";
    for_each(la.begin(), la.end(), Utils::ShowInt);
    cout << endl;

    la.remove(4);
    cout << "After using them remove() method:\n";
    cout << "la:\t";
    for_each(la.begin(), la.end(), Utils::ShowInt);
    cout << endl;

    list<int>::iterator last;
    last = remove(lb.begin(), lb.end(), 4);
    cout << "After using the remove() function:\n";
    cout << "lb:\t";
    for_each(lb.begin(), lb.end(), Utils::ShowInt);
    cout << endl;

    lb.erase(last, lb.end());
    cout << "After using the erase() method:\n";
    cout << "lb:\t";
    for_each(lb.begin(), lb.end(), Utils::ShowInt);
    cout << endl;
}
