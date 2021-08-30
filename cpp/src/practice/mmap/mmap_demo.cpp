//

#include "mmap_demo.h"

// https://baike.baidu.com/item/mmap/1322217?fr=aladdin
// Created by 赵健 on 2021/8/30.
void mmap_demo::test1(int argc, char **argv) {
    // map a normal file as shared mem:
    argv[1] = "/Users/flannery/Desktop/AndroidHelper/cpp/src/practice/mmap/test.txt";
    int fd, i;
    people *p_map;
    char temp;
    fd = open(argv[1], O_CREAT | O_RDWR | O_TRUNC, 00777);
    lseek(fd, sizeof(people) * 5 - 1, SEEK_SET);
    write(fd, "", 1);
    p_map = (people *) mmap(NULL, sizeof(people) * 10, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (p_map == (void *) -1) {
        fprintf(stderr, "mmap: %s\n", strerror(errno));
        return;
    }
    close(fd);
    temp = 'a';
    for (i = 0; i < 10; ++i) {
        temp += 1;
        (*(p_map + i)).name[1] = '\0';
        memcpy((*(p_map + i)).name, &temp, 1);
        (*(p_map + i)).age = 20 + i;
    }
    printf("initializeover\n");
    sleep(10);
    munmap(p_map, sizeof(people) * 10);
    printf("umapok\n");
}

void mmap_demo::test2(int argc, char **argv) {
    int fd, i;
    people *p_map;
    argv[1] = "/Users/flannery/Desktop/AndroidHelper/cpp/src/practice/mmap/test.txt";
    fd = open(argv[1], O_CREAT | O_RDWR, 00777);
    p_map = (people *) mmap(NULL, sizeof(people) * 10, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (p_map == (void *) -1) {
        fprintf(stderr, "mmap: %s\n", strerror(errno));
        return;
    }
    for (i = 0; i < 10; ++i) {
        printf("name:%s age%d;\n", (*(p_map + i)).name, (*(p_map + i)).age);
    }
    munmap(p_map, sizeof(people) * 10);
}
