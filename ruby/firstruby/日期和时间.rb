#!/usr/bin/ruby -w
# -*- coding: UTF-8 -*-

time1 = Time.new
puts "当前时间："+time1.inspect

# Time.noew 功能相同
time2 = Time.now
puts "当前时间："+time2.inspect

puts "获取 Date & Time 组件"
time = Time.new
 
# Time 的组件
puts "当前时间 : " + time.inspect
puts time.year    # => 日期的年份
puts time.month   # => 日期的月份（1 到 12）
puts time.day     # => 一个月中的第几天（1 到 31）
puts time.wday    # => 一周中的星期几（0 是星期日）
puts time.yday    # => 365：一年中的第几天
puts time.hour    # => 23：24 小时制
puts time.min     # => 59
puts time.sec     # => 59
puts time.usec    # => 999999：微秒
puts time.zone    # => "UTC"：时区名称


puts "Time.utc、Time.gm 和 Time.local 函数"
# July 8, 2008
puts Time.local(2008, 7, 8).inspect
# July 8, 2008, 09:10am，本地时间
puts Time.local(2008, 7, 8, 9, 10).inspect
# July 8, 2008, 09:10 UTC
puts Time.utc(2008, 7, 8, 9, 10)  .inspect
# July 8, 2008, 09:10:11 GMT （与 UTC 相同）
puts Time.gm(2008, 7, 8, 9, 10, 11).inspect


time = Time.new
values = time.to_a
p values
puts Time.utc(*values)

puts "返回从纪元以来的秒数"
puts "time = Time.now.to_i"  
puts "把秒数转换为 Time 对象"
puts "Time.at(time)"
puts "返回从纪元以来的秒数，包含微妙"
puts "time = Time.now.to_f"


puts "时区和夏令时"
time = Time.new
 
# 这里是解释
puts time.zone       # => "UTC"：返回时区
puts time.utc_offset # => 0：UTC 是相对于 UTC 的 0 秒偏移
puts time.zone       # => "PST"（或其他时区）
puts time.isdst      # => false：如果 UTC 没有 DST（夏令时）
puts time.utc?       # => true：如果在 UTC 时区
puts time.localtime  # 转换为本地时区
puts time.gmtime     # 转换回 UTC
puts time.getlocal   # 返回本地区中的一个新的 Time 对象
puts time.getutc     # 返回 UTC 中的一个新的 Time 对象

puts "格式化时间和日期"
time = Time.new 
puts time.to_s
puts time.ctime
puts time.localtime
puts time.strftime("%Y-%m-%d %H:%M:%S")

puts "时间格式化指令"
puts "指令	描述"
puts "%a	星期几名称的缩写（比如 Sun）。"
puts "%A	星期几名称的全称（比如 Sunday）。"
puts "%b	月份名称的缩写（比如 Jan）。"
puts "%B	月份名称的全称（比如 January）。"
puts "%c	优选的本地日期和时间表示法。"
puts "%d	一个月中的第几天（01 到 31）。"
puts "%H	一天中的第几小时，24 小时制（00 到 23）。"
puts "%I	一天中的第几小时，12 小时制（01 到 12）。"
puts "%j	一年中的第几天（001 到 366）。"
puts "%m	一年中的第几月（01 到 12）。"
puts "%M	小时中的第几分钟（00 到 59）。"
puts "%p	子午线指示（AM 或 PM）。"
puts "%S	分钟中的第几秒（00 或 60）。"
puts "%U	当前年中的周数，从第一个星期日（作为第一周的第一天）开始（00 到 53）。"
puts "%W	当前年中的周数，从第一个星期一（作为第一周的第一天）开始（00 到 53）。"
puts "%w	一星期中的第几天（Sunday 是 0，0 到 6）。"
puts "%x	只有日期没有时间的优先表示法。"
puts "%X	只有时间没有日期的优先表示法。"
puts "%y	不带世纪的年份表示（00 到 99）。"
puts "%Y	带有世纪的年份。"
puts "%Z	时区名称。"
puts "%%	% 字符。"

puts "时间算法"
now = Time.now           # 当前时间
puts now
 
past = now - 10          # 10 秒之前。Time - number => Time
puts past
 
future = now + 10        # 从现在开始 10 秒之后。Time + number => Time
puts future
 
diff = future - now      # => 10  Time - Time => 秒数
puts diff

