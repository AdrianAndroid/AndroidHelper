//
// Created by 赵健 on 2021/7/9.
//

#ifndef DEMO_HEXOCT2_H
#define DEMO_HEXOCT2_H


#include <iostream>

class hexoct2 {
public:
    void test() {
        using namespace std;
        int chest = 42;
        int waist = 42;
        int insseam = 42;

        cout << "Monsieur cuts a striking figure!" << endl;
        cout << "chest = " << chest << " (decimal for 42)" << endl;
        cout << hex;
        cout << "waist = " << waist << " (hexadecimal for 42)" << endl;
        cout << oct;
        cout << "inseam = " << insseam << " (octal for 42)" << endl;
    }

    void test5() {
        using namespace std;
        const int Size = 15;
        char name1[Size];
        char name2[Size] = "C++owboy";
        cout << "Howdy! I'm " << name2;
        cin >> name1;
        cout << "Well, " << name1 << ", your name has ";
        cout << strlen(name1) << " letters and is stored\n";
        cout << "in an array of" << sizeof(name1) << " bytes.\n";
        cout << "Your initial is " << name1[0] << ".\n";
        name2[3] = '\0';
        cout << "Here are the first 3 characters of my name: ";
        cout << name2 << endl;
    }

    void test4() {
        int num = 0;
        double earnings[4]{1.2e4, 1.2e5, 1.2e4, 1.2e5};
        num = sizeof earnings / sizeof(double);
        for (int i = 0; i < num; i++) std::cout << "result = " << earnings[i] << std::endl;
        std::cout << "=======================" << std::endl;

        unsigned int counts[10] = {}; // all elements set to 0
        num = sizeof counts / sizeof(unsigned int);
        for (int i = 0; i < num; i++) std::cout << "result = " << counts[i] << std::endl;
        std::cout << "=======================" << std::endl;

        float balances[100]{};
        num = sizeof balances / sizeof(float);
        for (int i = 0; i < num; i++) std::cout << "result = " << balances[i] << std::endl;
        std::cout << "=======================" << std::endl;
    }

    void test3() {
        short things[] = {1, 5, 3, 8};
        int num_elements = sizeof things / sizeof(short);
        std::cout << "num_elements" << num_elements << std::endl;
    }

    void test2() {
        using namespace std;
        cout.setf(ios_base::fixed, ios_base::floatfield);
        float tub = 10.0 / 3.0;
        double mint = 10.0 / 3.0;
        const float million = 1.0e6;

        cout << "tub = " << tub;
        cout << ", a million tubs = " << million * tub;
        cout << ",]nand ten million tubs = ";
        cout << 10 * million * tub << endl;

        cout << "mint = " << " and a million mints = ";
        cout << million * mint << endl;
//        wchar_t bob = L'P';
//        std::cout << bob << std::endl;
//        std::wcout << L"tall" << std::endl;
    }
};


#endif //DEMO_HEXOCT2_H
