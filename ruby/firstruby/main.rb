#!/usr/bin/ruby -w -d
# -*- coding: UTF-8 -*- 
# 中文编码

puts "Hello, Ruby!";



puts "start Here Document";
print <<EOF
    这是第一种方式创建here document
    多行字符串
EOF

print <<"EOF"    # 与上面相同
    这是第二种方式创建here document
    多行字符串
EOF

print <<`EOC` # 执行命令
    echo hi there
    echo lo there
EOC

print <<"foo", <<"bar" #您可以把它们进行堆叠
    I said foo.
foo
    I Said bar.
bar
puts "end Here Document";

END {
    puts "Ruby END语句， 在程序运行之后执行"
}

puts "Ruby BEGIN语句"
BEGIN {
    puts "初始化 Rub 程序， 在程序运行之前执行"
}

puts "Ruby注释"
# 我是注释
=begin
这是注释
这是注释
这是注释
=end

# 数据类型
puts "整型"
# 123                  # Fixnum 十进制
# 1_234                # Fixnum 带有下划线的十进制
# -500                 # 负的 Fixnum
# 0377                 # 八进制
# 0xff                 # 十六进制
# 0b1011               # 二进制
# "a".ord              # "a" 的字符编码
# ?\n                  # 换行符（0x0a）的编码
# 12345678901234567890 # 大数

#整型 Integer 以下是一些整型字面量 
#字面量（literal）：代码中能见到的值，数值，bool值，字符串等都叫字面量 
#如以下的0,1_000_000,0xa等 
a1=0 
 
#带千分符的整型 
a2=1_000_000 
 
#其它进制的表示 
a3=0xa 
puts a1,a2 
puts a3 
 
#puts print 都是向控制台打印字符，其中puts带回车换行符 
=begin 
这是注释，称作：嵌入式文档注释 
类似C#中的/**/ 
=end


puts "浮点型"
# 123.4                # 浮点值
# 1.0e6                # 科学记数法
# 4E20                 # 不是必需的
# 4e+20                # 指数前的符号
 
#浮点型 
f1=0.0 
f2=2.1 
f3=1000000.1 
puts f3

puts "算术操作 加减乘除操作符：+-*/；指数操作符为**"
# 指数算术
puts 2**(1/4)#1与4的商为0，然后2的0次方为1 
puts 16**(1/4.0)#1与4.0的商为0.25（四分之一），然后开四次方根

puts "字符串类型"
puts 'escape using "\\"';
puts 'That\'s right';

puts "\#\{ expr \} 替换任意 Ruby 表达式的值为一个字符串"
puts "相乘 : #{24*60*60}"
name = "Ruby"
puts name
puts "#{name+",ok"}"


puts "转义字符"
# 符号	表示的字符
# \n	换行符 (0x0a)
# \r	回车符 (0x0d)
# \f	换页符 (0x0c)
# \b	退格键 (0x08)
# \a	报警符 Bell (0x07)
# \e	转义符 (0x1b)
# \s	空格符 (0x20)
# \nnn	八进制表示法 (n 是 0-7)
# \xnn	十六进制表示法 (n 是 0-9、a-f 或 A-F)
# \cx, \C-x	Control-x
# \M-x	Meta-x (c | 0x80)
# \M-\C-x	Meta-Control-x
# \x	字符 x


puts "\n\n数组"
# （1）数组通过[]索引访问
# （2）通过赋值操作插入、删除、替换元素
# （3）通过+，－号进行合并和删除元素，且集合做为新集合出现
# （4）通过<<号向原数据追加元素
# （5）通过*号重复数组元素
# （6）通过｜和&符号做并集和交集操作（注意顺序）
ary = [ "fred", 10, 3.14, "This is a string", "last element", ]
ary.each do |i|
    print i, ' '
end

puts "\n哈希类型"
hsh = colors = { "red" => 0xf00, "green" => 0x0f0, "blue" => 0x00f }
hsh.each do |key, value|
    print key, " is ", value, "\n"
end

puts "\n 范围类型"
(10..15).each do |n|
    print n, ' + '
end


puts "\n类和对象"

puts '局部变量：局部变量是在方法中定义的变量。局部变量在方法外是不可用的。在后续的章节中，您将看到有关方法的更多细节。局部变量以小写字母或 _ 开始。'
puts '实例变量：实例变量可以跨任何特定的实例或对象中的方法使用。这意味着，实例变量可以从对象到对象的改变。实例变量在变量名之前放置符号（@）。'
puts '类变量：类变量可以跨不同的对象使用。类变量属于类，且是类的一个属性。类变量在变量名之前放置符号（@@）。'
puts '全局变量：类变量不能跨类使用。如果您想要有一个可以跨类使用的变量，您需要定义全局变量。全局变量总是以美元符号（$）开始。'

class Sample
    def hello
        puts "Hello Ruby!"
    end

end
s=Sample.new()
s.hello()

class Customer
    @@no_of_customers=0     # 类变量 类似static
    def initialize(id, name, addr)
        @cust_id=id      # 实例变量 相当于累类的局部变量
        @cust_name=name
        @cust_addr=addr
    end
    def display_details()
        puts "Customer id      #@cust_id"
        puts "Customer name    #@cust_name"
        puts "Customer address #@cust_addr"
    end
    def total_no_of_customers()
        @@no_of_customers += 1
        puts "Toatal number of customers: #@@no_of_customers"
    end
end

# 创建对象
cust1=Customer.new("1", "John", "Wisdom Apartments, Ludhiya")
cust2=Customer.new("2", "poul", "New Empire road, Khandala")
# 调用方法
cust1.display_details()
cust1.total_no_of_customers()
cust2.display_details()
cust2.total_no_of_customers()

puts "\n Ruby变量"
puts 'Ruby 支持五种类型的变量。'
puts '  一般小写字母、下划线开头：变量（Variable）。'
puts '  $开头：全局变量（Global variable）。'
puts '  @开头：实例变量（Instance variable）。'
puts '  @@开头：类变量（Class variable）类变量被共享在整个继承链中'
puts '  大写字母开头：常数（Constant）。'

$global_variable = 10 # 全局变量
class Class1
    def print_global
        puts "全局变量在 Class1 中输出为 #$global_variable"
    end
end
class Class2
    def print_global
        puts "全局变量在 Class2 中输出为 #$global_variable"
    end
end
class1obj = Class1.new
class1obj.print_global
class2obj = Class2.new
class2obj.print_global 

puts "\n 实例变量"
class Customer3 
    def initialize(id, name, addr)
        @cust_id=id
        @cust_name=name
        @cust_addr=addr
    end
    def display_details()
        puts "Customer id #@cust_id"
        puts "Customer name #@cust_name"
        puts "Customer address #@cust_addr"
    end
end
# 创建对象
# 创建对象
cust3_1=Customer3.new("1", "John", "Wisdom Apartments, Ludhiya")
cust3_2=Customer3.new("2", "Poul", "New Empire road, Khandala")
 
# 调用方法
cust3_1.display_details()
cust3_2.display_details()

puts "\n 类变量"
class Customer4
    @@no_of_customers=0
    def initialize(id, name, addr)
        @cust_id=id
        @cust_name=name
        @cust_addr=addr
    end
    def display_details()
        puts "Customer id #@cust_id"
        puts "Customer name #@cust_name"
        puts "Customer address #@cust_addr"
    end
    def total_no_of_customers()
        @@no_of_customers += 1
        puts "Total number of customers: #@@no_of_customers"
    end
end

# 创建对象
cust1=Customer4.new("1", "John", "Wisdom Apartments, Ludhiya")
cust2=Customer4.new("2", "Poul", "New Empire road, Khandala")
 
# 调用方法
cust1.total_no_of_customers()
cust2.total_no_of_customers()

puts "\n 局部变量"
puts "_开头"

puts "\n 常量"
class Example
    VAR1 = 100
    VAR2 = 200
    def show
        puts "第一个常量的值为 #{VAR1}"
        puts "第二个常量的值为 #{VAR2}"
    end
end
# 创建对象
object=Example.new()
object.show

puts "\n 伪常量"
puts "self: 当前方法的接收器对象。"
puts "true: 代表 true 的值。"
puts "false: 代表 false 的值。"
puts "nil: 代表 undefined 的值。"
puts "__FILE__: 当前源文件的名称。"
puts "__LINE__: 当前行在源文件中的编号。"


puts "\n Ruby运算符"
puts "运算符	描述	实例"
puts "+	加法 - 把运算符两边的操作数相加	a + b 将得到 30"
puts "-	减法 - 把左操作数减去右操作数	a - b 将得到 -10"
puts "*	乘法 - 把运算符两边的操作数相乘	a * b 将得到 200"
puts "/	除法 - 把左操作数除以右操作数	b / a 将得到 2"
puts "%	求模 - 把左操作数除以右操作数，返回余数	b % a 将得到 0"
puts "**	指数 - 执行指数计算	a**b 将得到 10 的 20 次方"

puts "\n 比较运算符"
puts "运算符	描述	实例"
puts "==	检查两个操作数的值是否相等，如果相等则条件为真。	(a == b) 不为真。"
puts "!=	检查两个操作数的值是否相等，如果不相等则条件为真。	(a != b) 为真。"
puts ">	检查左操作数的值是否大于右操作数的值，如果是则条件为真。	(a > b) 不为真。"
puts "<	检查左操作数的值是否小于右操作数的值，如果是则条件为真。	(a < b) 为真。"
puts ">=	检查左操作数的值是否大于或等于右操作数的值，如果是则条件为真。	(a >= b) 不为真。"
puts "<=	检查左操作数的值是否小于或等于右操作数的值，如果是则条件为真。	(a <= b) 为真。"
puts "<=>	联合比较运算符。如果第一个操作数等于第二个操作数则返回 0，如果第一个操作数大于第二个操作数则返回 1，如果第一个操作数小于第二个操作数则返回 -1。	(a <=> b) 返回 -1。"
puts "===	用于测试 case 语句的 when 子句内的相等。	(1...10) === 5 返回 true。"
puts ".eql?	如果接收器和参数具有相同的类型和相等的值，则返回 true。	1 == 1.0 返回 true，但是 1.eql?(1.0) 返回 false。"
puts "equal?	如果接收器和参数具有相同的对象 id，则返回 true。	如果 aObj 是 bObj 的副本，那么 aObj == bObj 返回 true，a.equal?bObj 返回 false，但是 a.equal?aObj 返回 true。"

puts "\n 赋值运算符"
puts "运算符	描述	实例"
puts "=	简单的赋值运算符，把右操作数的值赋给左操作数	c = a + b 将把 a + b 的值赋给 c"
puts "+=	加且赋值运算符，把右操作数加上左操作数的结果赋值给左操作数	c += a 相当于 c = c + a"
puts "-=	减且赋值运算符，把左操作数减去右操作数的结果赋值给左操作数	c -= a 相当于 c = c - a"
puts "*=	乘且赋值运算符，把右操作数乘以左操作数的结果赋值给左操作数	c *= a 相当于 c = c * a"
puts "/=	除且赋值运算符，把左操作数除以右操作数的结果赋值给左操作数	c /= a 相当于 c = c / a"
puts "%=	求模且赋值运算符，求两个操作数的模赋值给左操作数	c %= a 相当于 c = c % a"
puts "**=	指数且赋值运算符，执行指数计算，并赋值给左操作数	c **= a 相当于 c = c ** a"

puts "\n 并行赋值"
a, b, c = 10, 20, 30
print a,' ',b,' ', c


puts "\n Ruby位运算符"
puts "运算符	描述	实例"
puts "&	如果同时存在于两个操作数中，二进制 AND 运算符复制一位到结果中。	(a & b) 将得到 12，即为 0000 1100"
puts "|	如果存在于任一操作数中，二进制 OR 运算符复制一位到结果中。	(a | b) 将得到 61，即为 0011 1101"
puts "^	如果存在于其中一个操作数中但不同时存在于两个操作数中，二进制异或运算符复制一位到结果中。	(a ^ b) 将得到 49，即为 0011 0001"
puts "~	二进制补码运算符是一元运算符，具有\'翻转\'位效果，即0变成1，1变成0。	(~a ) 将得到 -61，即为 1100 0011，一个有符号二进制数的补码形式。"
puts "<<	二进制左移运算符。左操作数的值向左移动右操作数指定的位数。	a << 2 将得到 240，即为 1111 0000"
puts ">>	二进制右移运算符。左操作数的值向右移动右操作数指定的位数。	a >> 2 将得到 15，即为 0000 1111puts \""



puts "逻辑运算符"
puts "运算符	描述	实例"
puts "and	称为逻辑与运算符。如果两个操作数都为真，则条件为真。	(a and b) 为真。"
puts "or	称为逻辑或运算符。如果两个操作数中有任意一个非零，则条件为真。	(a or b) 为真。"
puts "&&	称为逻辑与运算符。如果两个操作数都非零，则条件为真。	(a && b) 为真。"
puts "||	称为逻辑或运算符。如果两个操作数中有任意一个非零，则条件为真。	(a || b) 为真。"
puts "!	称为逻辑非运算符。用来逆转操作数的逻辑状态。如果条件为真则逻辑非运算符将使其为假。	!(a && b) 为假。"
puts "not	称为逻辑非运算符。用来逆转操作数的逻辑状态。如果条件为真则逻辑非运算符将使其为假。	not(a && b) 为假。"


puts "\n Ruby 三元运算符"
puts "运算符	描述	实例"
puts "? :	条件表达式	如果条件为真 ? 则值为 X : 否则值为 Y"

puts "\n Ruby 范围运算符"
puts "运算符	描述	实例"
puts "..	创建一个从开始点到结束点的范围（包含结束点）	1..10 创建从 1 到 10 的范围"
puts "...	创建一个从开始点到结束点的范围（不包含结束点）	1...10 创建从 1 到 9 的范围"


puts "\nRuby defined? 运算符"
puts "defined? 是一个特殊的运算符，以方法调用的形式来判断传递的表达式是否已定义。它返回表达式的描述字符串，如果表达式未定义则返回 nil。"
puts "下面是 defined? 运算符的各种用法："
puts "用法 1"
puts "defined? variable # 如果 variable 已经初始化，则为 True"
puts "例如："
puts ""
puts "foo = 42"
puts "defined? foo    # => \"local-variable\""
puts "defined? $_     # => \"global-variable\""
puts "defined? bar    # => nil（未定义）"
puts foo = 42
puts defined? foo
puts defined? $_
puts defined? bar



puts "用法 2"
puts "defined? method_call # 如果方法已经定义，则为 True"
puts "例如："
puts ""
puts "defined? puts        # => \"method\""
puts "defined? puts(bar)   # => nil（在这里 bar 未定义）"
puts "defined? unpack      # => nil（在这里未定义）"
puts "用法 3"
puts "# 如果存在可被 super 用户调用的方法，则为 True"
puts "defined? super"
puts "例如："
puts ""
puts "defined? super     # => \"super\"（如果可被调用）"
puts "defined? super     # => nil（如果不可被调用）"
puts "用法 4"
puts "defined? yield   # 如果已传递代码块，则为 True"
puts "例如："
puts ""
puts "defined? yield    # => \"yield\"（如果已传递块）"
puts "defined? yield    # => nil（如果未传递块）"


puts "Ruby点运算符. 和 双冒号运算法 ':'"
puts "你可以通过在方法名称前加上类或模块名称和 . 来调用类或模块中的方法。你可以使用类或模块名称和两个冒号 :: 来引用类或模块中的常量。"
puts ":: 是一元运算符，允许在类或模块内定义常量、实例方法和类方法，可以从类或模块外的任何地方进行访问。"
puts "请记住：在 Ruby 中，类和方法也可以被当作常量。"
puts "你只需要在表达式的常量名前加上 :: 前缀，即可返回适当的类或模块对象。"
puts "如果 :: 前的表达式为类或模块名称，则返回该类或模块内对应的常量值；如果 :: 前未没有前缀表达式，则返回主Object类中对应的常量值。"

MR_COUNT = 0 # 定义在主Object类上的常量
module Foo
    MR_COUNT = 0
    ::MR_COUNT = 1 # 设置全局计数为1
    MR_COUNT = 2   # 设置局部计数为2
end
puts MR_COUNT      # 这是全局变量
puts Foo::MR_COUNT # 这是“Foo”的局部变量

CONST = ' out there'
class Inside_one
    CONST = proc {'in there'}
    def where_is_my_CONST
        ::CONST + ' inside one'
    end
end
class Inside_two
    CONST = ' inside two'
    def where_is_my_CONST
        CONST
    end
end
puts Inside_one.new.where_is_my_CONST
puts Inside_two.new.where_is_my_CONST
puts Object::CONST + Inside_two::CONST
puts Inside_one::CONST
puts Inside_one::CONST.call + Inside_two::CONST

puts "\nRuby 优先级"
puts "      方法	运算符	描述"
puts "      是	::	常量解析运算符"
puts "      是	[ ] [ ]=	元素引用、元素集合"
puts "      是	**	指数"
puts "      是	! ~ + -	非、补、一元加、一元减（最后两个的方法名为 +@ 和 -@）"
puts "      是	* / %	乘法、除法、求模"
puts "      是	+ -	加法和减法"
puts "      是	>> <<	位右移、位左移"
puts "      是	&	位与"
puts "      是	^ |	位异或、位或"
puts "      是	<= < > >=	比较运算符"
puts "      是	<=> == === != =~ !~	相等和模式匹配运算符（!= 和 !~ 不能被定义为方法）"
puts "      &&	逻辑与"
puts "      ||	逻辑或"
puts "      .. ...	范围（包含、不包含）"
puts "      ? :	三元 if-then-else"
puts "      = %= { /= -= += |= &= >>= <<= *= &&= ||= **=	赋值"
puts "      defined?	检查指定符号是否已定义"
puts "      not	逻辑否定"
puts "      or and	逻辑组成"

puts "\nRuby if..else语句"
x=1
if x > 2
   puts "x 大于 2"
elsif x <= 2 and x!=0
   puts "x 是 1"
else
   puts "无法得知 x 的值"
end


puts "\nif修饰符 if修饰词组表示当 if 右边之条件成立时才执行 if 左边的式子。即如果 conditional 为真，则执行 code。"
$debug=1
print "debug\n" if $debug


puts "\nRuby unless 语句"
x=1
unless x>2
   puts "x 小于 2"
else
  puts "x 大于 2"
end

puts "\nRuby unless 修饰符"
$var =  1
print "1 -- 这一行输出\n" if $var
print "2 -- 这一行不输出\n" unless $var
 
$var = false
print "3 -- 这一行输出\n" unless $var

puts "\nRuby case 语句"
# case expr0
# when expr1, expr2
#    stmt1
# when expr3, expr4
#    stmt2
# else
#    stmt3
# end

# _tmp = expr0
# if expr1 === _tmp || expr2 === _tmp
#    stmt1
# elsif expr3 === _tmp || expr4 === _tmp
#    stmt2
# else
#    stmt3
# end
$age =  5
case $age
when 0 .. 2
    puts "婴儿"
when 3 .. 6
    puts "小孩"
when 7 .. 12
    puts "child"
when 13 .. 18
    puts "少年"
else
    puts "其他年龄段的"
end

foo = false
bar = true
quu = false
 
case
when foo then puts 'foo is true'
when bar then puts 'bar is true'
when quu then puts 'quu is true'
end

puts "\nRuby while 语句"
$i = 0
$num = 5
while $i < $num  do
   puts("在循环语句中 i = #$i" )
   $i +=1
end

puts "\nRuby while 修饰符"
$i = 0
$num = 5
begin
   puts("在循环语句中 i = #$i" )
   $i +=1
end while $i < $num

puts "\nRuby until 语句"
$i = 0
$num = 5
until $i > $num  do
   puts("在循环语句中 i = #$i" )
   $i +=1;
end


puts "\nRuby until 修饰符"
$i = 0
$num = 5
begin
   puts("在循环语句中 i = #$i" )
   $i +=1;
end until $i > $num

puts "\nRuby for 语句"
for i in 0..5
    puts "局部变量的值为 #{i}"
end

(0..5).each do |i|
    puts "局部变量的值为 #{i}"
end

puts "\nRuby break 语句"
for i in 0..5
    if i > 2 then
       break
    end
    puts "局部变量的值为 #{i}"
end

puts "\nRuby next 语句"
for i in 0..5
    if i < 2 then
       next
    end
    puts "局部变量的值为 #{i}"
end

puts "\nRuby redo 语句 重新开始最内部循环的该次迭代，不检查循环条件。如果在块内调用，则重新开始 yield 或 call。"
# for i in 0..5
#     if i > 3 then
#        puts "局部变量的值为 #{i}"
#        redo
#     end
# end

puts "\nRuby retry 语句"
#for i in 1..5
#     retry if  i > 2
#     puts "局部变量的值为 #{i}"
#end

puts "语法"
puts "def method_name [( [arg [= default]]...[, * arg [, &expr ]])]"
puts "   expr.."
puts "end"
puts "所以，您可以定义一个简单的方法，如下所示："
puts ""
puts "def method_name "
puts "   expr.."
puts "end"
puts "您可以定义一个接受参数的方法，如下所示："
puts ""
puts "def method_name (var1, var2)"
puts "   expr.."
puts "end"
puts "您可以为参数设置默认值，如果方法调用时未传递必需的参数则使用默认值："
puts ""
puts "def method_name (var1=value1, var2=value2)"
puts "   expr.."
puts "end"

def test(a1="Ruby", a2="Perl")
    puts "编程语言为 #{a1}"
    puts "编程语言为 #{a2}"
end
 test "C", "C++"
 test

 puts "\n从方法返回值"
 def test
    i = 100
    j = 10
    k = 'x'
 end
 puts test

 puts "\nRuby return 语句"
 def test
    i = 100
    j = 200
    k = 300
 return i, j, k
 end
 var = test
 puts var
 print var[0], ' --- ', var[1] , ' --- ', var[2]
 puts

puts "\n可变数量的参数"
def sample (*test)
    puts "参数个数为 #{test.length}"
    for i in 0...test.length
       puts "参数值为 #{test[i]}"
    end
end
sample "Zara", "6", "F"
sample "Mac", "36", "M", "MCA"

puts "\n类方法"
class Accounts
    def reading_charge
    end
    def Accounts.return_date
    end
end


puts "\nRuby alias 语句"
# alias foo bar
# alias $MATCH $&

puts "\nRuby undef 语句"
# undef bar

puts "\nRuby块"
puts "您已经知道 Ruby 如何定义方法以及您如何调用方法。类似地，Ruby 有一个块的概念。"
puts "    块由大量的代码组成。"
puts "    您需要给块取个名称。"
puts "    块中的代码总是包含在大括号 {} 内。"
puts "    块总是从与其具有相同名称的函数调用。这意味着如果您的块名称为 test，那么您要使用函数 test 来调用这个块。"
puts "    您可以使用 yield 语句来调用块。"

puts "\nyield语句 使用一个简单的 yield 语句来调用块"
def test
    puts "在 test 方法内"
    yield
    puts "你又回到了 test 方法内"
    yield
end
test {puts "你在块内"}

def test
    yield 5
    puts "在 test 方法内"
    yield 100
end
test {|i| puts "你在块 #{i} 内"}


puts "\n块和方法"
def test
    yield
end
test{ puts "Hello world"}


def test(&block)
    block.call
end
test { puts "Hello World!"}


puts "\nRuby 模块（Module）"
puts "模块（Module）是一种把方法、类和常量组合在一起的方式。模块（Module）为您提供了两大好处。"
puts "        模块提供了一个命名空间和避免名字冲突。"
puts "        模块实现了 mixin 装置。"
puts "模块（Module）定义了一个命名空间，相当于一个沙盒，在里边您的方法和常量不会与其他地方的方法常量冲突。"
puts "模块类似与类，但有以下不同："
puts "        模块不能实例化"
puts "        模块没有子类"
puts "        模块只能被另一个模块定义"

module Trig
    PI = 3.1415
    def Trig.sin(x)
        # ..
    end
    def Trig.cos(x)
        #..
    end
end

module Moral
    VERY_BAD = 0
    BAD = 1
    def Moral.sin(badness)
        #..
    end
end

puts "Ruby require语句"
# #LOAD_PATH << "."
# require 'trig.rb'
# require 'moral'
# y = Trig.sin(Trig::PI/4)
# wrongdoing = Moral.sin(Moral::VERY_BAD)

puts "Ruby include 语句"
$LOAD_PATH << '.'
require "support"
class Decade
include Week
    no_of_yrs=10
    def no_of_months
        puts Week::FIRST_DAY
        number=10*12
        puts number
    end
end
d1=Decade.new
puts Week::FIRST_DAY
Week.weeks_in_month
Week.weeks_in_year
d1.no_of_months


puts "\nRuby中的Minxins"
module AA
    def a1
    end
    def a2
    end
end

module BB
    def b1
    end
    def b2
    end
end

class Sample2
include AA
include BB
    def s1
    end
end

samp = Sample2.new
samp.a1
samp.a2
samp.b1
samp.b2
samp.s1

puts "\n字符串的公共方法"
puts " 序号	方法 & 描述"
puts " 1	str \% arg          使用格式规范格式化字符串。如果 arg 包含一个以上的替代，那么 arg 必须是一个数组。如需了解更多格式规范的信息，请查看\"内核模块\"下的 sprintf。"
puts " 2	str * integer	   返回一个包含 integer 个 str 的新的字符串。换句话说，str 被重复了 integer 次。"
puts " 3	str + other_str	   连接 other_str 到 str。"
puts " 4	str << obj         连接一个对象到字符串。如果对象是范围为 0.255 之间的固定数字 Fixnum，则它会被转换为一个字符。把它与 concat 进行比较。"
puts " 5	str <=> other_str  把 str 与 other_str 进行比较，返回 -1（小于）、0（等于）或 1（大于）。比较是区分大小写的。"
puts " 6	str == obj         检查 str 和 obj 的相等性。如果 obj 不是字符串，则返回 false，如果 str <=> obj，则返回 true，返回 0。"
puts " 7	str =~ obj         根据正则表达式模式 obj 匹配 str。返回匹配开始的位置，否则返回 false。"
puts " 8	str[position] # 注意返回的是ASCII码而不是字符 str[start, length]  str[start..end]  str[start...end]  使用索引截取子串"
puts " 9	str.capitalize     把字符串转换为大写字母显示。"
puts " 10	str.capitalize!    与 capitalize 相同，但是 str 会发生变化并返回。"
puts " 11	str.casecmp        不区分大小写的字符串比较。"
puts " 12	str.center         居中字符串。"
puts " 13	str.chomp          从字符串末尾移除记录分隔符（$/），通常是 \n。如果没有记录分隔符，则不进行任何操作。"
puts " 14	str.chomp!         与 chomp 相同，但是 str 会发生变化并返回。"
puts " 15	str.chop           移除 str 中的最后一个字符。"
puts " 16	str.chop!          与 chop 相同，但是 str 会发生变化并返回。"
puts " 17	str.concat(other_str)  连接 other_str 到 str。"
puts " 18	str.count(str, ...)    给一个或多个字符集计数。如果有多个字符集，则给这些集合的交集计数。"
puts " 19	str.crypt(other_str)   对 str 应用单向加密哈希。参数是两个字符长的字符串，每个字符的范围为 a.z、 A.Z、 0.9、 . 或 /。"
puts " 20	str.delete(other_str, ...)   返回 str 的副本，参数交集中的所有字符会被删除。"
puts " 21	str.delete!(other_str, ...)  与 delete 相同，但是 str 会发生变化并返回。"
puts " 22	str.downcase       返回 str 的副本，所有的大写字母会被替换为小写字母。"
puts " 23	str.downcase!      与 downcase 相同，但是 str 会发生变化并返回。"
puts " 24	str.dump           返回 str 的版本，所有的非打印字符被替换为 \nnn 符号，所有的特殊字符被转义。"
puts " 25	str.each(separator=$/) { |substr| block }    使用参数作为记录分隔符（默认是 $/）分隔 str，传递每个子字符串给被提供的块。"
puts " 26	str.each_byte { |fixnum| block }    传递 str 的每个字节给 block，以字节的十进制表示法返回每个字节。"
puts " 27	str.each_line(separator=$/) { |substr| block }   使用参数作为记录分隔符（默认是 $/）分隔 str，传递每个子字符串给被提供的 block。"
puts " 28	str.empty?     如果 str 为空（即长度为 0），则返回 true。"
puts " 29	str.eql?(other)    如果两个字符串有相同的长度和内容，则这两个字符串相等。"
puts " 30	str.gsub(pattern, replacement) [or] str.gsub(pattern) { |match| block }  返回 str 的副本，pattern 的所有出现都替换为 replacement 或 block 的值。pattern 通常是一个正则表达式 Regexp；如果是一个字符串 String，则没有正则表达式元字符被解释（即，/\d/ 将匹配一个数字，但 '\d' 将匹配一个反斜杠后跟一个 'd'）。"
puts " 31	str[fixnum] [or] str[fixnum,fixnum] [or] str[range] [or] str[regexp] [or] str[regexp, fixnum] [or] str[other_str]  使用下列的参数引用 str：参数为一个 Fixnum，则返回 fixnum 的字符编码；参数为两个 Fixnum，则返回一个从偏移（第一个 fixnum）开始截至到长度（第二个 fixnum）为止的子字符串；参数为 range，则返回该范围内的一个子字符串；参数为 regexp，则返回匹配字符串的部分；参数为带有 fixnum 的 regexp，则返回 fixnum 位置的匹配数据；参数为 other_str，则返回匹配 other_str 的子字符串。一个负数的 Fixnum 从字符串的末尾 -1 开始。"
puts " 32	str[fixnum] = fixnum [or] str[fixnum] = new_str [or] str[fixnum, fixnum] = new_str [or] str[range] = aString [or] str[regexp] =new_str [or] str[regexp, fixnum] =new_str [or] str[other_str] = new_str ] 替换整个字符串或部分字符串。与 slice! 同义。"
puts " 33	str.gsub!(pattern, replacement) [or] str.gsub!(pattern) { |match| block }  执行 String#gsub 的替换，返回 str，如果没有替换被执行则返回 nil。"
puts " 34	str.hash  返回一个基于字符串长度和内容的哈希。"
puts " 35	str.hex  把 str 的前导字符当作十六进制数字的字符串（一个可选的符号和一个可选的 0x），并返回相对应的数字。如果错误则返回零。"
puts " 36	str.include? other_str [or] str.include? fixnum  如果 str 包含给定的字符串或字符，则返回 true。"
puts " 37	str.index(substring [, offset]) [or] str.index(fixnum [, offset]) [or] str.index(regexp [, offset]) 返回给定子字符串、字符（fixnum）或模式（regexp）在 str 中第一次出现的索引。如果未找到则返回 nil。如果提供了第二个参数，则指定在字符串中开始搜索的位置。"
puts " 38	str.insert(index, other_str) 在给定索引的字符前插入 other_str，修改 str。负值索引从字符串的末尾开始计数，并在给定字符后插入。其意图是在给定的索引处开始插入一个字符串。"
puts " 39	str.inspect 返回 str 的可打印版本，带有转义的特殊字符。"
puts " 40	str.intern [or] str.to_sym 返回与 str 相对应的符号，如果之前不存在，则创建符号。"
puts " 41	str.length 返回 str 的长度。把它与 size 进行比较。"
puts " 42	str.ljust(integer, padstr=' ')  如果 integer 大于 str 的长度，则返回长度为 integer 的新字符串，新字符串以 str 左对齐，并以 padstr 作为填充。否则，返回 str。"
puts " 43	str.lstrip  返回 str 的副本，移除了前导的空格。"
puts " 44	str.lstrip!  从 str 中移除前导的空格，如果没有变化则返回 nil。"
puts " 45	str.match(pattern)  如果 pattern 不是正则表达式，则把 pattern 转换为正则表达式 Regexp，然后在 str 上调用它的匹配方法。"
puts " 46	str.oct  把 str 的前导字符当作十进制数字的字符串（一个可选的符号），并返回相对应的数字。如果转换失败，则返回 0。"
puts " 47	str.replace(other_str)  把 str 中的内容替换为 other_str 中的相对应的值。"
puts " 48	str.reverse  返回一个新字符串，新字符串是 str 的倒序。"
puts " 49	str.reverse!  逆转 str，str 会发生变化并返回。"
puts " 50	str.rindex(substring [, fixnum]) [or] str.rindex(fixnum [, fixnum]) [or]  str.rindex(regexp [, fixnum])  返回给定子字符串、字符（fixnum）或模式（regexp）在 str 中最后一次出现的索引。如果未找到则返回 nil。如果提供了第二个参数，则指定在字符串中结束搜索的位置。超出该点的字符将不被考虑。"
puts " 51	str.rjust(integer, padstr=' ')  如果 integer 大于 str 的长度，则返回长度为 integer 的新字符串，新字符串以 str 右对齐，并以 padstr 作为填充。否则，返回 str。"
puts " 52	str.rstrip  返回 str 的副本，移除了尾随的空格。"
puts " 53	str.rstrip!  从 str 中移除尾随的空格，如果没有变化则返回 nil。"
puts " 54	str.scan(pattern) [or] str.scan(pattern) { |match, ...| block } 两种形式匹配 pattern（可以是一个正则表达式 Regexp 或一个字符串 String）遍历 str。针对每个匹配，会生成一个结果，结果会添加到结果数组中或传递给 block。如果 pattern 不包含分组，则每个独立的结果由匹配的字符串、$& 组成。如果 pattern 包含分组，每个独立的结果是一个包含每个分组入口的数组。"
puts " 55	str.slice(fixnum) [or] str.slice(fixnum, fixnum) [or] str.slice(range) [or] str.slice(regexp) [or] str.slice(regexp, fixnum) [or] str.slice(other_str) See str[fixnum], etc. str.slice!(fixnum) [or] str.slice!(fixnum, fixnum) [or] str.slice!(range) [or] str.slice!(regexp) [or] str.slice!(other_str) 从 str 中删除指定的部分，并返回删除的部分。如果值超出范围，参数带有 Fixnum 的形式，将生成一个 IndexError。参数为 range 的形式，将生成一个 RangeError，参数为 Regexp 和 String 的形式，将忽略执行动作。"
puts " 56	str.split(pattern=$;, [limit]) 基于分隔符，把 str 分成子字符串，并返回这些子字符串的数组。"

puts " 如果 pattern 是一个字符串 String，那么在分割 str 时，它将作为分隔符使用。如果 pattern 是一个单一的空格，那么 str 是基于空格进行分割，会忽略前导空格和连续空格字符。"
puts " 如果 pattern 是一个正则表达式 Regexp，则 str 在 pattern 匹配的地方被分割。当 pattern 匹配一个零长度的字符串时，str 被分割成单个字符。"
puts " 如果省略了 pattern 参数，则使用 $; 的值。如果 $; 为 nil（默认的），str 基于空格进行分割，就像是指定了 ` ` 作为分隔符一样。"
puts " 如果省略了 limit 参数，会抑制尾随的 null 字段。如果 limit 是一个正数，则最多返回该数量的字段（如果 limit 为 1，则返回整个字符串作为数组中的唯一入口）。如果 limit 是一个负数，则返回的字段数量不限制，且不抑制尾随的 null 字段。"
puts " 57	str.squeeze([other_str]*) 使用为 String#count 描述的程序从 other_str 参数建立一系列字符。返回一个新的字符串，其中集合中出现的相同的字符会被替换为单个字符。如果没有给出参数，则所有相同的字符都被替换为单个字符。"
puts " 58	str.squeeze!([other_str]*) 与 squeeze 相同，但是 str 会发生变化并返回，如果没有变化则返回 nil。"
puts " 59	str.strip 返回 str 的副本，移除了前导的空格和尾随的空格。"
puts " 60	str.strip!  从 str 中移除前导的空格和尾随的空格，如果没有变化则返回 nil。"
puts " 61	str.sub(pattern, replacement) [or] str.sub(pattern) { |match| block } 返回 str 的副本，pattern 的第一次出现会被替换为 replacement 或 block 的值。pattern 通常是一个正则表达式 Regexp；如果是一个字符串 String，则没有正则表达式元字符被解释。"
puts " 62	str.sub!(pattern, replacement) [or] str.sub!(pattern) { |match| block } 执行 String#sub 替换，并返回 str，如果没有替换执行，则返回 nil。"
puts " 63	str.succ [or] str.next 返回 str 的继承。"
puts " 64	str.succ! [or] str.next! 相当于 String#succ，但是 str 会发生变化并返回。"
puts " 65	str.sum(n=16) 返回 str 中字符的 n-bit 校验和，其中 n 是可选的 Fixnum 参数，默认为 16。结果是简单地把 str 中每个字符的二进制值的总和，以 2n - 1 为模。这不是一个特别好的校验和。"
puts " 66	str.swapcase 返回 str 的副本，所有的大写字母转换为小写字母，所有的小写字母转换为大写字母。"
puts " 67	str.swapcase! 相当于 String#swapcase，但是 str 会发生变化并返回，如果没有变化则返回 nil。"
puts " 68	str.to_f 返回把 str 中的前导字符解释为浮点数的结果。超出有效数字的末尾的多余字符会被忽略。如果在 str 的开头没有有效数字，则返回 0.0。该方法不会生成异常。"
puts " 69	str.to_i(base=10) 返回把 str 中的前导字符解释为整数基数（基数为 2、 8、 10 或 16）的结果。超出有效数字的末尾的多余字符会被忽略。如果在 str 的开头没有有效数字，则返回 0。该方法不会生成异常。"
puts " 70	str.to_s [or] str.to_str 返回接收的值。"
puts " 71	str.tr(from_str, to_str) 返回 str 的副本，把 from_str 中的字符替换为 to_str 中相对应的字符。如果 to_str 比 from_str 短，那么它会以最后一个字符进行填充。两个字符串都可以使用 c1.c2 符号表示字符的范围。如果 from_str 以 ^ 开头，则表示除了所列出的字符以外的所有字符。"
puts " 72	str.tr!(from_str, to_str)  相当于 String#tr，但是 str 会发生变化并返回，如果没有变化则返回 nil。"
puts " 73	str.tr_s(from_str, to_str)  把 str 按照 String#tr 描述的规则进行处理，然后移除会影响翻译的重复字符。"
puts " 74	str.tr_s!(from_str, to_str)  相当于 String#tr_s，但是 str 会发生变化并返回，如果没有变化则返回 nil。"
puts " 75	str.unpack(format)  根据 format 字符串解码 str（可能包含二进制数据），返回被提取的每个值的数组。format 字符由一系列单字符指令组成。每个指令后可以跟着一个数字，表示重复该指令的次数。星号（*）将使用所有剩余的元素。指令 sSiIlL 每个后可能都跟着一个下划线（_），为指定类型使用底层平台的本地尺寸大小，否则使用独立于平台的一致的尺寸大小。format 字符串中的空格会被忽略。"
puts " 76	str.upcase  返回 str 的副本，所有的小写字母会被替换为大写字母。操作是环境不敏感的，只有字符 a 到 z 会受影响。"
puts " 77	str.upcase! 改变 str 的内容为大写，如果没有变化则返回 nil。"
puts " 78	str.upto(other_str) { |s| block }  遍历连续值，以 str 开始，以 other_str 结束（包含），轮流传递每个值给 block。String#succ 方法用于生成每个值。"

