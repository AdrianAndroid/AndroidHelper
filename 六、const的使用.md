1、前言：
const是一个C语言的关键字，它限定一个变量不允许被改变。使用const在一定程序上可以提高程序的健壮性，另外，在观看别人代码的时候，清晰理解const所起的作用，对理解别人的程序有所帮助。
2、const变量和常量
    1）const修饰的变量，其值存放在只读数据段中，其值不能被改变。称为只读变量。其形式为 const int a=5;此处可以用a代替5
    2）常量：其也存在只读数据段中，其数值也不能被改变。其形式为"abc" ,5
3、const 变量和const限定的内容，先看一个事例：
    typedef char* pStr;
    int main( )
    {
        char string[6] = “tiger”;
        const char *p1 = string;
        const pStr p2 = string;
        p1++;
        p2++;
        printf(“p1=%snp2=%sn”,p1,p2);
    }
    程序经过编译后，提示错误为
    error:increment of read-only variable ‘p2’
    
    1)const 使用的基本形式为：const char m;
    //限定m 不可变
    2)替换1式中的m,const char *pm;
    //限定*pm不可变，当然pm是可变的，因此p1++是对的
    3)替换1式中的char,const newType m;
    //限定m不可变，问题中的pStr是一种新类型，因此问题中p2不可变，p2++是错误的。
4、const 和指针

类型声明中const用来修饰一个常量，有如下两种写法：

1)const在前面
    const int nValue;//nValue是const
    const char *pContent;//*pContent是const,pConst可变
    const (char *)pContent;//pContent是const,*pContent可变
    char *const pContent;//pContent是const,*pContent可变
    const char * const pContent;//pContent和*pContent都是const
2)const 在后面与上面的声明对等
    int const nValue；// nValue是const
    char const *pContent;//*pContent是const, pContent可变
    (char *) constpContent;//pContent是const, *pContent可变
    char* const pContent;// pContent是const, *pContent可变
    char const* const pContent;//pContent和*pContent都是const

说明：const和指针一起使用是C语言中一个很常见的困惑之处,下面是两天规则：
    1）沿着*号划一条线，如果const位于*的左侧，则const就是用来修饰指针所指向的变量，即指针指向为常量；如果const位于*的右侧，const就是修饰指针本身，即指针本身是常量。你可以根据这个规则来看上面声明的实际意义，相信定会一目了然。
    2）对于const (char *) ; 因为char *是一个整体，相当于一个类型(如char)，因此，这是限定指针是const。