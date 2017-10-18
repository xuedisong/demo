#!/usr/bin/env bash

# description: 执行所有测试案例脚本，执行当前目录下所有的test_*.py文件，注意python版本为2.7

RED='\e[1;91m'
GREN='\e[1;92m'
WITE='\e[1;97m'
NC='\e[0m'

for case in $(ls test_*); do
    python $case $* 2>&1
    if [ "$?" != "0" ]; then
        echo $case failed ":("
        exit 1
    fi
done

echo All cases passed "^_^"