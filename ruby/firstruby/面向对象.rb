#!/usr/bin/ruby -w

# 定义类
class Box
    # 常量 (属于类)
    BOX_CONMPANY = "TATA INC"
    BOXWEIGHT = 10

    # 初始化类变量
    @@count = 0

    # 构造函数
    def initialize(w,h)
       @width, @height = w, h

       @@count += 1
    end
  
    # 访问器方法
    def printWidth
       @width
    end
  
    def printHeight
       @height
    end

    # 设置器方法
    def setWidth=(value)
        @width = value
    end
    def setHeight=(value)
        @height = value
    end

    def to_s
        "w:#@width,h:#@height" # 对象的字符串格式
    end

    def getWidth
        @width
    end

    def getHeight
        @height
    end

    # make them private
    private :getWidth, :getHeight

    def printArea
        @area = getWidth() * getHeight()
    end

    # 让实例方法protected的
    protected :printArea

    def getArea
        @area
    end

    #运算符重载
    def +(other) # 定义+来执行向量加法
        # puts other.width  ✖️
        # puts other.height ✖️
        Box.new(@width + other.printWidth(), @height + other.printHeight)
    end

    def -@
        Box.new(-@width, -@height)
    end

    def *(scalar)
        Box.new(@width*scalar, @height*scalar)
    end


    # 类方法
    def self.printCount() 
        puts "Box count is : #@@count"
    end

    # 输出类信息
    puts "Class of self = #{self.class}"
    puts "Name of self = #{self.name}"
end

# 创建对象， 初始化盒子的高度与宽度
box = Box.new(10, 20)
# 使用访问器方法
x = box.printWidth()
y = box.printHeight()

puts "盒子宽度：#{x}"
puts "盒子高度：#{y}"

box.setWidth = 100
box.setHeight = 200

x = box.printWidth()
y = box.printHeight()

puts "盒子宽度：#{x}"
puts "盒子高度：#{y}"

puts box
puts box.to_s

Box.printCount()

puts "访问控制"
puts "----Public"
puts "----Private"
puts "----Protected"
# puts box.printArea
# puts box.getWidth()
# puts box.getHeight()


puts "类的继承"
class BigBox < Box
    # 添加一个新的实例方法
    def printArea
        @area = @width * @height
        puts "Big box area is: #@area"
    end

    # 改变已有的getArea方法
    def getArea
        @area=@width*@height
        puts "Big box area is: #@area"
    end
end

# 创建对象
box = BigBox.new(10, 20)
# 输出面积
box.printArea
# 使用重载的方法输出面积
box.getArea

puts "调用重载操作符"
# 调用重载操作服
box = Box.new(10, 20) + Box.new(1, 2)
puts "打印结果"
puts box


puts Box::BOX_CONMPANY

puts "使用allocate创建对象"
box = Box.allocate 
a = box.getArea
puts "Area of the box is : #{a}"


puts "类 #{box.class}"
puts "类名 #{box.class.name}"

puts "end"