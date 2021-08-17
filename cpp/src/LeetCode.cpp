//
// Created by 赵健 on 2021/8/14.
//

#include "LeetCode.h"
#include <map>

vector<int> LeetCode::twoSum2(vector<int> &nums, int target) {
    int i, j;
    for (i = 0; i < nums.size() - 1; i++) {
        for (j = i + 1; j < nums.size(); j++) {
            if (nums[i] + nums[j] == target) {
                return {i, j};
            }
        }
    }
    return {i, j};
}

/**
 * static_cast<new_type>      (expression)
 *      1。用于类层次结构中基类（父类）和派生类（子类）之间指针或引用的转换
 *      2。用于基本数据类型之间的转换， （int 转 enum）
 *      3。把空指针转换成目标类型的空指针
 *      4。把任何类型的表达式转换成void类型
 * dynamic_cast<new_type>     (expression)
 *      type必须是类类型
 *      主要是类层次转换
 *      1。指针类型
 *          if(Derived *dp = dynamic_cast<Derived *>(bp) {
 *              // 使用dp指向的Derived对象
 *          } else {
 *              // 使用bp指向的Base对象
 *          }
 *      2。引用类型
 *          void f(const Base &b) {
 *              try {
 *                  const Derived &d = dynamic_cast<const Base &>(b);
 *                  //使用b引用的Derived对象
 *              }
 *              catch(std::bad_cast){
 *                  //处理类型转换失败的情况
 *              }
 *          }
 * const_cast<new_type>       (expression)
 *      该运算符用来修改类型的const或volatile属性
 *      1. 常量指针被转换成非常量的指针，并且仍然指向原来的对象
 *      2. 常量引用被转换成非常量的引用，并且仍然指向原来的对象
 *      3. const_cast一般用于修改底指针，如const char *p形式
 * reinterpret_cast<new_type> (expression)
 *      把一个指针转换成一个整数，也可以把一个整数转换成一个指针
 */
vector<int> LeetCode::twoSum3(vector<int> &nums, int target) {
    map<int, int> a; // 建立hash
    vector<int> b(2, -1);
    for (int i = 0; i < nums.size(); ++i) {
        a.insert(map<int, int>::value_type(nums[i], i));
    }
    for (int i = 0; i < nums.size(); ++i) {
        if (a.count(target - nums[i]) > 0 && (a[target - nums[i]] != i)) {
            b[0] = i;
            b[1] = a[target - nums[i]];
            break;
        }
    }
    return b;
}

vector<int> LeetCode::twoSum(vector<int> &nums, int target) {
    map<int, int> a;
    vector<int> b(2, -1);
    for (int i = 0; i < nums.size(); ++i) {
        if(a.count(target - nums[i]) > 0){
            b[0] = a[target-nums[i]];
            b[1] = i;
            break;
        }
        a[nums[i]] = i;
    }
    return b;
}
