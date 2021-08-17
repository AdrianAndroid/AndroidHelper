//
// Created by 赵健 on 2021/8/17.
//

#include "Node.h"


Node *find_all(Node *head, const double &val) {
    Node *start;
    for (start = head; start != 0; start = start->p_next) {
        if (start->item == val) return start;
    }
    return nullptr;
}

typedef double *iterator;

iterator find_ar(iterator ar, int n, const double &val) {
    for (int i = 0; i < n; i++, ar++) {
        if (*ar == val) return ar;
    }
    return nullptr;
}