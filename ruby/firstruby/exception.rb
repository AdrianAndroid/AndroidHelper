#!/usr/bin/ruby

begin
    file = open("/unexistant_file")
    if file
       puts "File opened successfully"
    end
 rescue
       file = STDIN
 end
 puts
 puts file, "==", STDIN, "\n"


puts "使用retry语句"


begin
    file = open("/unexistant_file")
    if file
       puts "File opened successfully"
    end
 rescue
    fname = "existant_file"
    puts fname
   #  retry
end



# raise 
# 或
# raise "Error Message" 
# 或
# raise ExceptionType, "Error Message"
# 或
# raise ExceptionType, "Error Message" condition
begin  
   puts 'I am before the raise.'  
   raise 'An error has occurred.'  
   puts 'I am after the raise.'  
rescue  
   puts 'I am rescued.'  
end  
puts 'I am after the begin block.'

begin  
   raise 'A test exception.'  
 rescue Exception => e  
   puts e.message  
   puts e.backtrace.inspect  
 end


 使用 ensure 语句
 begin
   raise 'A test exception.'
 rescue Exception => e
   puts e.message
   puts e.backtrace.inspect
 ensure
   puts "Ensuring execution"
 end

 使用 else 语句
begin
   # 抛出 'A test exception.'
   puts "I'm not raising exception"
rescue Exception => e
  puts e.message
  puts e.backtrace.inspect
else
   puts "Congratulations-- no errors!"
ensure
  puts "Ensuring execution"
end


Catch 和 Throw
raise 和 rescue 的异常机制能在发生错误时放弃执行，有时候需要在正常处理时跳出一些深层嵌套的结构。此时 catch 和 throw 就派上用场了。
catch 定义了一个使用给定的名称（可以是 Symbol 或 String）作为标签的块。块会正常执行直到遇到一个 throw。

def promptAndGet(prompt)
   print prompt
   res = readline.chomp
   throw :quitRequested if res == "!"
   return res
end
 
catch :quitRequested do
   name = promptAndGet("Name: ")
   age = promptAndGet("Age: ")
   sex = promptAndGet("Sex: ")
   # ..
   # 处理信息
end
promptAndGet("Name:")


类 Exception
Interrupt
NoMemoryError
SignalException
ScriptError
StandardError
SystemExit

class FileSaveError < StandardError
   attr_reader :reason
   def initialize(reason)
      @reason = reason
   end
end

File.open(path, "w") do |file|
begin
    # 写出数据 ...
rescue
    # 发生错误
    raise FileSaveError.new($!)
end
end