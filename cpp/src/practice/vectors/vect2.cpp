//
// Created by 赵健 on 2021/8/17.
//

#include "vect2.h"
#include "vect3.h"


void vect2::test() {
    using std::cout;
    using std::vector;
    vector<Review> books;
    Review temp;
    while (FillReview(temp)) {
        books.push_back(temp);
    }
    int num = books.size();
    if (num > 0) {
        cout << "Thank you. You entered the following:\n"
             << "Rating\tBook\tn";
        for (int i = 0; i < num; ++i) {
            ShowReview(books[i]);
        }
        cout << "Reprising:\n"
             << "Rating\tBook\n";
        vector<Review>::iterator pr;
        for (pr = books.begin(); pr != books.end(); pr++) {
            ShowReview(*pr);
        }
        vector<Review> oldlist(books);
        if (num > 3) {
            // remove 2 items
            books.erase(books.begin() + 1, books.begin() + 3);
            cout << "After erasure:\n";
            for (pr = books.begin(); pr != books.end(); pr++) {
                ShowReview(*pr);
            }
            // insert 1 item
            books.insert(books.begin(), oldlist.begin() + 1, oldlist.begin() + 2);
            cout << "After insertion:\n";
            for (pr = books.begin(); pr != books.end(); pr++) {
                ShowReview(*pr);
            }
        }
        books.swap(oldlist);
        cout << "Swapping oldlist with books:\n";
        for (pr = books.begin(); pr != books.end(); pr++) {
            ShowReview(*pr);
        }
    } else {
        cout << "Nothing entered, nothing gained.\n";
    }
}
