//
// Created by 赵健 on 2021/8/17.
//

#include "ListTest.h"
#include "../../utils/Utils.h"
#include <iostream>
#include <list>
#include <iterator>
//#include <algorithm>

void ListTest::test() {
    using namespace std;
    list<int> one(5, 2); // ListTest of 5 2s
    int stuff[5] = {1,2,4,8,6};
    list<int> two;
    two.insert(two.begin(), stuff, stuff+5);
    int more[6] = {6,4,2,4,6,5};
    list<int> three(two);
    three.insert(three.end(), more, more+6);

    cout << "List one:";
    for_each(one.begin(), one.end(), Utils::outint);
    cout << endl << "List two:";
    for_each(two.begin(), two.end(), Utils::outint);
    cout << endl << "List Three:";
    for_each(three.begin(), three.end(), Utils::outint);

    three.remove(2);
    cout << endl << "List three minus 2s:";
    for_each(three.begin(), three.end(), Utils::outint);

    three.splice(three.begin(), one);
    cout << endl << "List three after splice:";
    Utils::printList(three);

    Utils::println("List one:");
    Utils::printList(one);

    three.unique();
    Utils::println("List three after unique: ");
    Utils::printList(three);

    three.sort();
    three.unique();
    Utils::println("List three after sort & unique :");
    Utils::printList(three);

    two.sort();
    Utils::println("Sorted two :");
    Utils::printList(two);

    three.merge(two);
    Utils::println("Sorted two merged into three:");
    Utils::printList(three);
    Utils::println("");
}
