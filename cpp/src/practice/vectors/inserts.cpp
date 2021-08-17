//
// Created by 赵健 on 2021/8/17.
//

#include "inserts.h"
#include "../../utils/Utils.h"
#include <iostream>
#include <iterator>
#include <algorithm>
#include <vector>


void inserts::test() {
    using namespace std;
    string s1[4] = {"fine", "fish", "fashion", "fate"};
    string s2[2] = {"busy", "bats"};
    string s3[2] = {"silly", "singers"};
    vector<string> words(4);
    copy(s1, s1 + 4, words.begin());
    for_each(words.begin(), words.end(), Utils::output);
    cout << endl;
    // construct anonymous back_insert_iterator object
    copy(s2, s2 + 2, back_insert_iterator<vector<string>>(words));
    for_each(words.begin(), words.end(), Utils::output);
    cout << endl;
    // consturct anoymous insert_iterator object
    copy(s3, s3 + 2, insert_iterator<vector<string>>(words, words.begin()));
    for_each(words.begin(), words.end(), Utils::output);
    cout << endl;
}
