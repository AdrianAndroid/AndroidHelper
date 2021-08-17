//
// Created by 赵健 on 2021/8/16.
//

#include "vect1.h"
#include <iostream>
#include <string>
#include <vector>

void main_vector1() {
    using std::vector;
    using std::string;
    using std::cin;
    using std::cout;

    vector<int> rating(NUM);
    vector<string> titles(NUM);
    cout << "You will do exactly as told. You will enter\n"
         << NUM << " book titles and your ratings (0-10).\n";
    int i;
    for (i = 0; i < NUM; i++)
    {
        cout << "Enter title #" << i + 1 << ": ";
        getline(cin, titles[i]);
        cout << "Enter your rating (0-10)";
        cin >> rating[i];
        cin.get();
    }
    cout << "Thank you. You entered the following:\n"
        << "Rating\tBook\n";
    for (i = 0; i < NUM; i++)
    {
        cout << rating[i] << "\t" << titles[i] << std::endl;
    }
}
