//
// Created by 赵健 on 2021/8/17.
//

#ifndef ANDROIDHELPER_ITERATOR_H
#define ANDROIDHELPER_ITERATOR_H


#include "Node.h"

class iterator {
    Node *pt;
public:
    iterator() : pt(nullptr) {}

    iterator(Node *pn) : pt(pn) {}

    double operator*() { return pt->item; }

    iterator &operator++() { // for ++it
        pt = pt->p_next;
        return *this;
    }

    iterator operator++(int) { // for it++
        iterator tmp = *this;
        pt = pt->p_next;
        return tmp;
    }

};


#endif //ANDROIDHELPER_ITERATOR_H
