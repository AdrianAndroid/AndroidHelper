#!/usr/bin/ruby


require 'rubygems'
require 'json'
require 'pp'

puts "Hello World!"
json = File.read('input.json')
obj = JSON.parse(json)

pp obj