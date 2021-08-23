//
// Created by 赵健 on 2021/8/17.
//

#ifndef ANDROIDHELPER_REVIEW_H
#define ANDROIDHELPER_REVIEW_H

#include "Review.h"
#include <iostream>

struct Review {
    std::string title;
    int rating;
};

bool FillReview(Review &rr) {
    std::cout << "Enter book title (quit to quit):";
    std::getline(std::cin, rr.title);
    if (rr.title == "quit")
        return false;
    std::cout << "Enter book rating:";
    std::cin >> rr.rating;
    if (!std::cin)
        return false;
    // get rid of rest of input line
    while (std::cin.get() != '\n') continue;
    return true;
}

void ShowReview(const Review &rr) {
    std::cout << rr.rating << "\t" << rr.title << std::endl;
}

bool operator<(const Review &r1, const Review &r2) {
    if (r1.title < r2.title) {
        return true;
    } else if (r1.title == r2.title && r1.rating < r2.rating) {
        return true;
    } else {
        return false;
    }
}

bool worseThan(const Review &r1, const Review &r2) {
    if (r1.rating < r2.rating) {
        return true;
    } else {
        return false;
    }
}


#endif //ANDROIDHELPER_REVIEW_H
