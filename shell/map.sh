#!/bin/bash
# author: flannery

# Map定义及初始化
#declare -a myMap
#myMap["my03"]="03"

declare -a myMap=(["my01"]="01" ["my02"]="02")
myMap["my03"]="h03"
myMap["my04"]="h04"

# 输出所有的key、value、长度
# 1）输出所有的key
# 若未使用declare生命map，则此处讲输出0，与预期输出不符，此处输出语句格式比arry多了一个！
echo "输出所有的key"
echo ${!myMap[@]}
# 2) 输出所有value
# 与array输出格式相同
echo "输出所有value"
echo ${myMap[@]}
# 3) 输出map长度
# 与array输出格式相同
echo "输出map长度"
echo ${#myMap[@]}

echo "========================"
# Map遍历
echo "1)遍历，根据key找到对应的value"
for key in ${!myMap[*]};do
     echo $key
     echo ${myMap[$key]}
done
echo "2)遍历所有的key"
for key in ${!myMap[*]};do
        echo $key
        echo ${myMap[$key]}
done
echo "3)遍历所有的value"
for val in ${myMap[@]};do
        echo $val
done



