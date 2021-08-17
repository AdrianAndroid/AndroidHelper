//
// Created by 赵健 on 2021/8/17.
//

#ifndef ANDROIDHELPER_REVIEW_H
#define ANDROIDHELPER_REVIEW_H


struct Review {
    std::string title;
    int rating;
};

bool FillReview(Review &rr) ;
void ShowReview(const Review &rr);
bool operator<(const Review & r1, const Review & r2);
bool worseThan(const Review & r1, const Review & r2);


#endif //ANDROIDHELPER_REVIEW_H
