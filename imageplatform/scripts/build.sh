#!/usr/bin/env bash

# author: yc
# description: 构建imageplatform脚本

PACK_JRE_DIR=$1

# 获取工程当前目录路径
TMP=$(pwd)
DIR=${TMP%/scripts}

# 使用gradle打jar包
cd $DIR
gradle build
gradle task copyJars

# 清除打包目录
rm -rf "$DIR/dist"
mkdir -p "$DIR/dist"

# 整理发包信息
REV=$(svn info . | grep 'Revision:' | awk '{print $2}')
PKG_NAME=imageplatform-rev$REV.$(date +%y%m%d%H%M).$(hostname)
PKG_PATH="$DIR/dist/$PKG_NAME"
mkdir -p $PKG_PATH

# 创建工程路径
mkdir -p "$PKG_PATH/lib"
cp -r "$DIR/build/libs/" "$PKG_PATH/lib"

# 将test脚本目录放到打包文件里
mkdir -p "$PKG_PATH/test"
cp -r "$DIR/test" "$PKG_PATH/test"

# 拷贝启动文件和脚本
cp "$DIR/scripts/run.sh" "$PKG_PATH/"
mkdir -p "$PKG_PATH/config"
cp "$DIR/src/main/resources/application.properties" "$PKG_PATH/config/"
cp "$DIR/src/main/resources/jdbc.properties" "$PKG_PATH/config/"

# 压缩成tar包
cd "$DIR/dist"
tar -zcf $PKG_NAME.tar.gz $PKG_NAME
