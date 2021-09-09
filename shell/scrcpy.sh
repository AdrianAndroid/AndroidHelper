#!/bin/bash

#    _Command('scrcpy-x21', 'scrcpy --window-x 0 --window-y 0 -s 98badcb9'),
#    _Command('scrcpy-google', 'scrcpy --window-x 0 --window-y 0 -s 8AWX10UKL'),
# 弄一个数组吧
array=("x21" "scrcpy --window-x 0 --window-y 0 -s 98badcb9" "google" "scrcpy --window-x 0 --window-y 0 -s 8AWX10UKL")
# 遍历数组
echo ${#array[@]}
echo "-----"
echo ${#array[*]}

# 遍历输出0到10
# #!/bin/bash
# for i in {0..10}; do
#   echo $i;
# done;

# https://blog.csdn.net/weixin_31321851/article/details/116079747?utm_term=for%E6%89%93%E5%8D%B0%E5%8F%98%E9%87%8Fshell&utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~all~sobaiduweb~default-0-116079747&spm=3001.4430
#java把for循环的变量输出,shell for循环、循环变量值付给其他shell脚本
#shell for 循环：
# #!第一种写法 类似C、Java
# for ((i=1; i<=100; i ++))
# do
#     echo $i
# done
# #!第二种写法 in应用
# for i in {1..100}
# do
#     echo $i 
# done
# #!第三种写法 seq 使用
# for i in `seq 1 100`
# do
#     echo $i
# done

# shell的取模操作
# echo $((2%4))
# echo "2 % 4" | bc


# $#	传递到脚本的参数个数
# $*	以一个单字符串显示所有向脚本传递的参数。如"$*"用「"」括起来的情况、以"$1 $2 … $n"的形式输出所有参数。
# $$	脚本运行的当前进程ID号
# $!	后台运行的最后一个进程的ID号
# $@	与$*相同，但是使用时加引号，并在引号中返回每个参数。 如"$@"用「"」括起来的情况、以"$1" "$2" … "$n" 的形式输出所有参数。
# $-	显示Shell使用的当前选项，与set命令功能相同。
# $?	显示最后命令的退出状态。0表示没有错误，其他任何值表明有错误。


num=${#array[@]} # 个数


# linux shell 等待输入_shell中获得用户的输入
# https://blog.csdn.net/weixin_39751076/article/details/111206100
# read -p "do you want install [Y/N]:" choice
# case $choice in
# Y | y) echo 'installing';;
# N | n) echo 'bye';;
# esac
# echo "this is the end of script!"


# 跳出循环
#!/bin/bash
# while :
# do
#     echo -n "输入 1 到 5 之间的数字:"
#     read aNum
#     case $aNum in
#         1|2|3|4|5) echo "你输入的数字为 $aNum!"
#         ;;
#         *) echo "你输入的数字不是 1 到 5 之间的! 游戏结束"
#             break
#         ;;
#     esac
# done


# continue
#!/bin/bash
# while :
# do
#     echo -n "输入 1 到 5 之间的数字: "
#     read aNum
#     case $aNum in
#         1|2|3|4|5) echo "你输入的数字为 $aNum!"
#         ;;
#         *) echo "你输入的数字不是 1 到 5 之间的!"
#             continue
#             echo "游戏结束"
#         ;;
#     esac
# done


# case操作
# echo '输入 1 到 4 之间的数字:'
# echo '你输入的数字为:'
# read aNum
# case $aNum in
#     1)  echo '你选择了 1';;
#     2)  echo '你选择了 2';;
#     3)  echo '你选择了 3';;
#     4)  echo '你选择了 4';;
#     *)  echo '你没有输入 1 到 4 之间的数字';;
# esac

read -p "intput scrcpy devices:" devices
${array[devices+1]}

# isFind=false
# 循环找到值
# for((i=0; i<num; i++))
# do
#     # 获取名称打印出来
#     echo ${array[i]}
#     # if [ $((i%2)) == 0 ] # 第一个
#     # then
#     #     echo "请输入: ${array[i]}"
#     # fi
# done