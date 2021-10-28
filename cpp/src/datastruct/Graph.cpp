//
// Created by 赵健 on 2021/10/20.
//

#include "Graph.h"
#include <iostream>

using namespace std;

Status Graph::CreateUDN(AMGraph &G) {
    // 采用邻接矩阵表示法，创建无向网G
    cin>>G.vexnum>>G.arcnum;              //输入总顶点数，总边数
    int i,j,k,v1,v2,w;
    for(i=0; i<G.vexnum; ++i){            //依次输入点的信息
        cin>>G.vexs[i];
    }
    // 初始化邻接矩阵，边的权值均置为极大值MaxInt
    for(i=0; G.vexnum;++i) {
        for(j=0; G.vexnum;++j) {
            G.arcs[i][j] = MaxInt;
        }
    }
    //构造邻接矩阵
    for(k=0; k<G.arcnum;++k) {
        cin>>v1>>v2>>w;
    }
    return OK;
}
