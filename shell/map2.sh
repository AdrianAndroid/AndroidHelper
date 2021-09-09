#!/bin/sh


# +/- 　“-“   可用来指定变量的属性，”+”则是取消变量所设的属性。
# -a    数组类型
# -i    整数类型
# -p    可以查看变量类型
# -x    与export相同，将变量定义为环境变量
# -r    定义为只读类型
# -f    显示此脚本前面定义过的所有函数名及其内容。
# -F    仅打印函数名字。



myMap=(["my00"]="00" ["my01"]="01")
myMap["my02"]="02"
myMap["my03"]="03"
myMap["my04"]="04"
myMap["my05"]="05"
myMap["my06"]="06"
myMap["my07"]="07"


"二、输出所有的key:"
echo ${!myMap[@]}
 
echo "三、输出所有value:"
echo ${myMap[@]}
 
echo "四、输出map的长度:"
echo ${#myMap[@]}
 
echo "五、遍历，根据key找到对应的value:"
for key in ${!myMap[*]};do
 echo "key:"$key
 echo "value:"${myMap[$key]}
done
 
echo "六、遍历所有的key:"
for key in ${!myMap[@]};do
 echo "key:"$key
 echo "value:"${myMap[$key]}
done
 
echo "七、遍历所有value:"
for val in ${myMap[@]};do
 echo "value:"$val
done
