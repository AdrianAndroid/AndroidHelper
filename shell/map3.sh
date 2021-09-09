#!/bin/bash
echo "Hello World!"

declare myMap
myMap["01"]="01"
myMap["02"]="02"
myMap["03"]="03"
myMap["04"]="04"
myMap["05"]="05"

echo ${!myMap[@]}
