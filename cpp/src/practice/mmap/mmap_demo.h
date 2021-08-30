//
// Created by 赵健 on 2021/8/30.
//

#ifndef ANDROIDHELPER_MMAP_DEMO_H
#define ANDROIDHELPER_MMAP_DEMO_H

#include <sys/mman.h>
#include <sys/types.h>
#include <fcntl.h>
#include <string>
#include <unistd.h>
#include <errno.h>


typedef struct {
    char name[4];
    int age;
} people;

class mmap_demo {
public:
    void test1(int argc, char **argv);
    void test2(int argc, char **argv);
};


#endif //ANDROIDHELPER_MMAP_DEMO_H
