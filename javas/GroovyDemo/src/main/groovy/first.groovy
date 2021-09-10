//#!/usr/bin/env groovy

static void main(String[] args) {
    println("hello")
    println "hello"
    //map()
    //stringtest()
    //stringWithJava()
    listtest()
}

// 1. 关键字
//  表1.关键字
//  as              assert           break               case
//  catch           class            const               continue
//  def             default          do                  else
//  enum            extends          false               finally
//  for             goto             if                  implements
//  import          in               instanceof          interface
//  new             null             package             return
//  super           switch           this                throw
//  throws          trait            true                try
//  while

// 2。 定义
static void map() {
    def map = [:]
    map."an identifier with a space and double quotes" = "ALLOWED"
    map.'with-dash-signs-and-single-quotes' = "ALLOWED"

    println map."an identifier with a space and double quotes" == "ALLOWED"
    println map.'with-dash-signs-and-single-quotes' == "ALLOWED"

    assert map."an identifier with a space and double quotes" == "ALLOWED"
    assert map.'with-dash-signs-and-single-quotes' == "ALLOWED"


    println map.'single quote'
    println map."double quote"
    println map.'''triple single quote'''
    println map."""triple double quote"""
    println map./slashy string/
    println map.$/dollar slashy string/$


    def firstname = "Homer"
    map."Simson-${firstname}" = "Homer Simson"

    assert map.'Simson-Homer' == "Homer Simson"

    println(map)
}

// 3. 字符串
static void stringtest() {
    // 字符串声明
    def s = 'a single quoted string'
    println(s)
    // 字符串的连接
    assert 'ab' == 'a' + 'b'
    // 三单引号字符串
    def s3 = '''a triple single quoted string'''
    println(s3)
    def aMultilineString = '''line one
        line two
        line three'''
    println(aMultilineString)
    def startingAndEndingWithANewline = '''
line one
line two
line three
'''
    println(startingAndEndingWithANewline)

    def strippedFirstNewline = '''\
line one
line two
line three
'''
    assert !strippedFirstNewline.startsWith('\n')
    println(strippedFirstNewline)

    println('转义字符')
    println('an escaped single quote: \' needs a backslash')
    println('an escaped escape character: \\ needs a double backslash')
    println('Unicode转义')
    println('The Euro currency symbol: \u20AC')

    // 转义序列    字符
    //'\t'        tabulation
    //'\b'        backspace
    //'\n'        newline
    //'\r'        carriage return
    //'\f'        formfeed
    //'\\'        backslash
    //'\''        single quote (for single quoted and triple single quoted strings)
    //'\"'        double quote (for double quoted and triple double quoted strings)


    println('双引号字符串')
    println("a double quoted string")

    println('字符串插值')
    def name = 'Guillaume' // a plain string
    def greeting = "Hello ${name}"

    assert greeting.toString() == 'Hello Guillaume'
    println(greeting)

    def person = [name: 'Guillaume', age: 36]
    assert "$person.name is $person.age years old" == 'Guillaume is 36 years old'
    println "${person.name} is ${person.age} years old"

    println('插入闭包表达式的特殊情况')
//    （1）这是一个不携带参数的无参闭包
    def sParameterLessClosure = "1 + 2 == ${-> 3}"
    assert sParameterLessClosure == '1 + 2 == 3'
    println(sParameterLessClosure)

//    （2）这里的闭包携带一个java.io.StringWrite参数，你能使用<<追加内容。在这两处，占位符被嵌入闭包。
    def sOneParamClosure = "1 + 2 == ${ w -> w << 3}"
    assert sOneParamClosure == '1 + 2 == 3'
    println(sOneParamClosure)


    def number = 1// （1）我们定义一个包含1的number变量，然后插入两个GString之中，在eagerGString中作为表达式，在lazyGString中作为闭包。
    def eagerGString = "value == ${number}"
    def lazyGString = "value == ${ -> number }"

    assert eagerGString == "value == 1"// （2）我们期望对于eagerGString得到的结果字符串是包含1的相同字符串
    assert lazyGString == "value == 1"// （3）lazyGString相似

    number = 2// （4）然后给变量赋一个新值
    assert eagerGString == "value == 1"// （5）使用纯插值表达式，这值在GString创建时结合
    assert lazyGString == "value == 2"// （6）使用闭包表达式，GString被强转为Sring时，闭包被调用，并产生包含新数值的更新字符串。


}

// 与java的互操作性
//（1）我们创建一个GSring变量
//（2）我们仔细检查GString实例
//（3）我们将GString传递个一个携带String参数的方法
//（4）takeString()明确说明它的唯一参数是一个String
//（5）我们也验证一个参数是String而不是GString
static void stringWithJava() {
    def message = "The message is ${'hello'}"//（1）
    assert message instanceof GString//（2）

    def result = takeString(message)//（3）
    assert result instanceof String
    assert result == 'The message is hello'

    println("GString和String的hashCode")
    assert "one: ${1}".hashCode() != "one: 1".hashCode()

    def key = "a"
    def m = ["${key}": "letter ${key}"]// （1）

    assert m["a"] == null// （2）
    //（1）map被一个初始化键值对创建，其键值是GString
    //（2）当我们尝试使用String键值获取值时，我们并没获取对应值，因为String和GString有不同的hashCode

    println('三引号字符串')
    def name = 'Groovy'
    def template = """
  Dear Mr ${name},

  You're the winner of the lottery!

  Yours sincerly,

  Dave
"""

    assert template.toString().contains('Groovy')

    println('斜杠字符串')
    def fooPattern = /.*foo.*/
    assert fooPattern == '.*foo.*'

    println('只有正斜杠需要反斜杠转义')
    def escapeSlash = /The character \/ is a forward slash/
    assert escapeSlash == 'The character / is a forward slash'

    println('斜杠字符串是多行的')
    def multilineSlashy = /one
two
three/
    assert multilineSlashy.contains('\n')
}

static String takeString(String message) {//（4）
    assert message instanceof String//（5）
    return message
}


static void listtest() {
    def numbers = [1, 2, 3]// （1）
    assert numbers instanceof List// （2）
    assert numbers.size() == 3// （3）
    //（1）我们定义用逗号分隔，并用方括号包围列表数字，并将列表赋值给一个变量
    //（2）list是java java.util.List接口的实例
    //（3）列表的大小可以使用size()方法查询，表明列表有三个元素

    def heterogeneous = [1, "a", true]// （1）
    //（1）我们的列表包含一个数字，一个字符串，一个布尔值

    ArrayList<Integer> arrayList = [1, 2, 3]
    assert arrayList instanceof java.util.ArrayList

    def linkedList = [2, 3, 4] as LinkedList// （1）
    assert linkedList instanceof java.util.LinkedList

    LinkedList otherLinked = [3, 4, 5]// （2）
    assert otherLinked instanceof java.util.LinkedList
    //（1）我们使用as操作符进行类型转换，显式请求一个java.util.LinkedList实现
    //（2）我们使用类型为java.util.LinkedList的变量保存列表字面量

    def letters = ['a', 'b', 'c', 'd']

    assert letters[0] == 'a'// （1）
    assert letters[1] == 'b'

    assert letters[-1] == 'd' //（2）
    assert letters[-2] == 'c'

    letters[2] = 'C'// （3）
    assert letters[2] == 'C'

    letters << 'e'// （4）
    assert letters[ 4] == 'e'
    assert letters[-1] == 'e'

    assert letters[1, 3] == ['b', 'd']// （5）
    assert letters[2..4] == ['C', 'd', 'e']// （6）
    println(letters)
//    （1）访问列表的第一个元素（索引从零开始计算）
//    （2）使用负索引访问列表的最后一个元素：-1是列表从尾部开始的第一个元素
//    （3）使用赋值操作为列表的第三个元素设置一个新值
//    （4）使用<<左移操作符在列表尾部追加一个元素
//    （5）一次访问两个元素，并返回一个包含这两个元素的新列表
//    （6）使用范围访问列表中这个范围内的元素，从start到end元素位置

    def multi = [[0, 1], [2, 3]]// （1）
    assert multi[1][0] == 2// （2）
    //（1）定义一个数字列表的列表
    //（2）访问顶级列表的第二个元素，内部列表的第一个元素
    println(multi)
}

// 数组
static void arraytest() {
    String[] arrStr = ['Ananas', 'Banana', 'Kiwi'] //（1）

    assert arrStr instanceof String[] //（2）
    assert !(arrStr instanceof List)

    def numArr = [1, 2, 3] as int[] //（3）

    assert numArr instanceof int[] //（4）
    assert numArr.size() == 3
//    （1）使用显式变量类型定义一个字符串数组
//    （2）断言说明我们创建了一个字符串数组
//    （3）使用as操作符创建以int数组
//    （4）断言表明我们创建了一个原始类型的int数组

    // 创建多维数组
    def matrix3 = new Integer[3][3] //（1）
    assert matrix3.size() == 3

    Integer[][] matrix2 //（2）
    matrix2 = [[1, 2], [3, 4]]
    assert matrix2 instanceof Integer[][]
    //（1）你能定义一个新数组的边界
    //（2）或不指定它的边界定义一个新数组


    String[] names = ['Cédric', 'Guillaume', 'Jochen', 'Paul']
    assert names[0] == 'Cédric'// （1）

    names[2] = 'Blackdrag'// （2）
    assert names[2] == 'Blackdrag'
//    （1）取得数组的第一个元素
//    （2）为数组的第三个元素设置一个新值
}

// map string
static void maptest() {
    def colors = [red: '#FF0000', green: '#00FF00', blue: '#0000FF']// （1）

    assert colors['red'] == '#FF0000'// （2）
    assert colors.green == '#00FF00'// （3）

    colors['pink'] = '#FF00FF'// （4）
    colors.yellow = '#FFFF00'// （5）

    assert colors.pink == '#FF00FF'
    assert colors['yellow'] == '#FFFF00'

    assert colors instanceof java.util.LinkedHashMap

    //（1）我们定义了一个字符串颜色名关联十六进制的html颜色的映射
    //（2）我们使用下标标记检查red键值关联的内容
    //（3）我们也能使用属性标记访问绿颜色十六进制表达式
    //（4）相似的，我们也能使用下标标记添加一个新的键值对
    //（5）或者使用属性标记添加yellow颜色

    def numbers = [1: 'one', 2: 'two']

    assert numbers[1] == 'one'

    def key = 'name'
    def person = [key: 'Guillaume']// （1）

    assert !person.containsKey('name')// （2）
    assert person.containsKey('key')// （3）
    //（1）key同’Guillaume’关联，名字将会变为”key”字符串，而不是其值
    //（2）这个映射不包括”name”键
    //（3）代替的是，映射包括一个”key”键

    person = [(key): 'Guillaume']// （1）

    assert person.containsKey('name')// （2）
    assert !person.containsKey('key')// （3）
//    （1）这次，我们使用圆括号包围key变量，指示解析器我们传递一个变量，而不是定义一个字符串键
//    （2）映射包含name键
//    （3）但映射不像之前包含key键
}