//
// Created by 赵健 on 2021/8/17.
//

#include "copyit.h"

void copyit::test() {
    using namespace std;
    int casts[10] = {6, 7, 2, 9, 4, 11, 8, 7, 10, 6};
    vector<int> dice(10);
    // copy from array to vector
    copy(casts, casts + 10, dice.begin());
    cout << "Let the dice be cast!\n";
    // create an ostream iterator
    ostream_iterator<int, char> out_iter(cout, "  ");
    // copy from vector to output
    copy(dice.begin(), dice.end(), out_iter);
    cout << endl;
    cout << "Implicit use of ereverse iterator.\n";
    copy(dice.rbegin(), dice.rend(), out_iter);
    cout << endl;
    cout << "Explicit use of reverse iterator.\n";
    vector<int>::reverse_iterator ri;
    for (ri = dice.rbegin(); ri != dice.rend(); ++ri) {
        cout << *ri << ' ';
    }
    cout << endl;
}
