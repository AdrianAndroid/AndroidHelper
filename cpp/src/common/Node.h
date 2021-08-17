//
// Created by 赵健 on 2021/8/17.
//

#ifndef ANDROIDHELPER_NODE_H
#define ANDROIDHELPER_NODE_H


struct Node {
    double item;
    Node *p_next;
};

Node *find_all(Node *head, const double &val);

#endif //ANDROIDHELPER_NODE_H
