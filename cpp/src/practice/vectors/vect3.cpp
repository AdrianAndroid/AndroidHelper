//
// Created by 赵健 on 2021/8/17.
//

#include "vect3.h"

void vect3::test() {
    using namespace std;
    vector<Review> books;
    Review temp;
    while(FillReview(temp)) books.push_back(temp);
    if(!books.empty()) {
        cout << "Thank you. You entered the following "
            << books.size() << " rating:\n"
            << "Rating\tBook\n";
        for_each(books.begin(), books.end(), ShowReview);

        sort(books.begin(), books.end());
        cout << "Sorted by title:\nRating\tBook\n";
        for_each(books.begin(), books.end(), ShowReview);

        sort(books.begin(), books.end(), worseThan);
        cout << "Sorted by rating:\nRating\tBook\n";
        for_each(books.begin(), books.end(), ShowReview);

        random_shuffle(books.begin(), books.end());
        cout << "After shuffling:\nRating\tBook\n";
        for_each(books.begin(), books.end(), ShowReview);
    }
}


double * find_ar(double  * ar, int n, const double & val){
    for (int i = 0; i < n; i++) {
        if(ar[i] == val) return &ar[i];
    }
    return 0;
}

