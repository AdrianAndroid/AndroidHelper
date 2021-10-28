//
// Created by 赵健 on 2021/10/20.
//

#ifndef ANDROIDHELPER_GRAPH_H
#define ANDROIDHELPER_GRAPH_H

#define MaxInt 32767     // 表示极大值，即∞
#define MVNum 100        // 最大顶点数
#define OK    1

typedef char VerTexType; // 假设顶点的数据类型为字符型
typedef int ArcType;     // 假设边的权值类型为整形
typedef int Status;      //返回的状态

typedef struct {
    VerTexType vexs[MVNum];     // 顶点表
    ArcType arcs[MVNum][MVNum];// 邻接矩阵
    int vexnum, arcnum;        // 图的当前点数和边数
} AMGraph;

class Graph {
public:
    Status CreateUDN(AMGraph &);// 创建邻接矩阵
};


#endif //ANDROIDHELPER_GRAPH_H
