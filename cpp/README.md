# 迭代器类型

* 输入迭代器
  ```asm
  template<class InputInterator, class T>
  InputIterator find(InputIterator first, InputIterator last, const T& value);
  ```
* 输出迭代器

* 正向迭代器
* 双向迭代器
* 随机访问迭代器
  ```asm
    template<class RandomAccessIterator>
    void sort(RandomAccessIterator first, RandomAccessIterator last);
  ```

# 概念、改进和模型

```c++
const int SIZE = 100;
double Receipts[SIZE];
sort(Receipts, Receipts + SIZE);

int casts[10] = { 6, 7, 2, 9, 4, 11, 8, 7, 10, 6 };
vector<int> dice[10];
copy(casts, casts + 10, dice.begin()); copy array to vector

#include
<iterator>
ostream_terator<int, char> out_iter(cout, " ");
*out_iter++ = 15; //works like cout << 15 << " ";
copy(dice.begin, dice.end(), out_iter);

copy(istream_iterator<int, char>(cin), istream_iterator<int, char>(), dice.begin());
```

# 容器种类

```c++
deque
ListTest
queue
priority_queue
stack
vector
map
MultiMaps
set
multiset
//c11
forward_list
unordered_map
unordered_multimap
unordered_set
unordered_multiset
```

| 表达式 | 返回值类型 | 说明 | 复杂度 |
|:----|:----|:----|:----|
| X::iterator   | 指向T的迭代器类型 | 满足正向迭代器要求的任何迭代器 | 编译时间 |
| X::value_type | T | T的类型 | 编译时间 |
| X u;          |  | 创建一个名为u的空容器 | 固定 |
| X();          |  | 创建一个匿名空容器 | 固定 |
| X u(a);       |  | 调用复制构造函数后 u==a | 固定 |
| X u = a;      |  | 作用同于 X u(a) | 固定 |
| r = a;        | X& | 调用复制运算符后r==a | 固定 |
| (&a)->~X()    | void | 对容器中每个元素应用析构函数 | 固定 |
| a.begin()     | 迭代器 | 返回指向容器第一个元素的迭代器 | 固定 |
| a.end()       | 迭代器 | 返回超尾值迭代器 | 固定 |
| a.size()      | 无符号整型 | 返回元素个数，等价于a.end()-a.begin() | 固定 |
| a.swap(b)     | void | 交换a和b的内容 | 固定 |
| a == b        | 可转换为bool | 如果a和b的长度相同，且a中每个元素都等于(==为真)b中相应的元素，则为真 | 线性 |
| a != b        | 可转换为bool | 返回!(a--b) | 线性 |

c11 新增的容器要求

| 表达式 | 返回值类型 | 说明 | 复杂度 |
|:----|:----|:----|:----|
| X u(rv)  |  | 调用移动构造函数后，u的值与rv的原始值相同 | 线性 |
| X u=rv   |  | 作用同于X u(rv) |  |
| a=rv     | X& | 调用移动赋值运算后，u的值与rv的原始值相同 | 线性 |
| a.cbegin | const_iterator | 返回指向容器第一个元素的const迭代器 | 固定 |
| a.cend   | const_iterator | 返回超尾值const迭代器 | 固定 |