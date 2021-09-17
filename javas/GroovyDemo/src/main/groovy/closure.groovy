static void main(String[] args) {
    println("闭包有参数")
    def c4 = { println("Hello $it") }
    c4

    println("闭包是有返回值的")
    def c3 = {
        println("Hello world")
        return "I'm Callback"
    }
    println c3()

    println("传递参数的闭包")
    def c2 = { param -> println(param) }
    c2("Hello world!")
    c2.call("Hello call")
    c2 "Hello World!"

    println('闭包')
    def xxx = { params -> println("code") }
    def xxx2 = { println("code") }
    xxx("hello")
    xxx2()

    def str = "hello world"
    def c = { println(str) }
    c()

    println("无参函数")
    println getString()
    println getDef()
    printSomething()

    println("有参构造函数")
    printSomething01("Hello world")
    printSomething02(222)
    printSomething03("Hello world")
    printSomething_("hello xxx ", "world!")
    printSomething_(null, "world!")
}

def printSomething01(param) {
    println param
}

def printSomething02(int param) {
    println(param)
}

def printSomething03(def param) {
    println(param)
}

def printSomething_(param01, param02) {
    println param01 + param02
}


String getString() {
    return "hello world!"
}

def getDef() {
    return "hellow world!"
}

void printSomething() {
    println "hello world!"
}

