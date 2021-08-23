//
// Created by 赵健 on 2021/8/17.
//

#include "strgst1.h"
#include <iostream>
#include <string>
#include <algorithm>

void strgst1::test() {
    using namespace std;
    string letters;
    cout << "Enter the letter grouping(quit to quit):" << endl;
    while (cin >> letters && letters != "quit") {
        cout << "Permutations of " << letters << endl;
        sort(letters.begin(), letters.end());
        cout << letters << endl;
        while (next_permutation(letters.begin(), letters.end())) {
            cout << letters << endl;
        }
        cout << "Enter next sequence (quit to quit):";
    }
    cout << "Done. \n";
}
