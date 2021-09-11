#!/usr/bin/ruby
 
$, =", "   # Array 值分隔符
range1 = (1..10).to_a
range2 = ('bar'..'bat').to_a
 
puts "#{range1}"
puts "#{range2}"

puts (1..5)        #==> 1, 2, 3, 4, 5
puts (1...5)       #==> 1, 2, 3, 4
puts ('a'..'d')    #==> 'a', 'b', 'c', 'd'

# 指定范围
digits = 0..9
 
puts digits.include?(5)
ret = digits.min
puts "最小值为 #{ret}"
 
ret = digits.max
puts "最大值为 #{ret}"
 
ret = digits.reject {|i| i < 5 }
puts "不符合条件的有 #{ret}"
 
digits.each do |digit|
   puts "在循环中 #{digit}"
end


puts "作为条件的范围"
# while gets
#     print if /start/../end/
# end

score = 70
 
result = case score
when 0..40
    "糟糕的分数"
when 41..60
    "快要及格"
when 61..70
    "及格分数"
when 71..100
       "良好分数"
else
    "错误的分数"
end
 
puts result


puts "作为间隔的范围"
if ((1..10) === 5)
    puts "5 在 (1..10)"
end
 
if (('a'..'j') === 'c')
  puts "c 在 ('a'..'j')"
end
 
if (('a'..'j') === 'z')
  puts "z 在 ('a'..'j')"
end



