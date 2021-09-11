#!/usr/bin/ruby

ary = [1,2,3,4,5]
ary.each do |i|
    print i
end

puts ''

a = [1,2,3,4,5]
b = Array.new
b = a.collect { |x| x }
print b 
puts  ''

b = a.collect{|x| 10*x}
print b
puts

sum = 0
cutcome = {"block1" => 1000, "book2" => 1000, "book3" => 4000}
cutcome.each{|item, price| sum += price}
puts "sum = " + sum.to_s
sum = 0
cutcome.each { |pair| sum += pair[1] }
puts "sum = " + sum.to_s

