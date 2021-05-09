#! /bin/bash
## 测试
#ROOT_PATH="/Users/mj/Android/People28"
echo '==============打包开始=============='
echo $1
# java -jar packer-ng-2.0.1.jar generate --channels=@channels.txt --output=build/archives app/build/outputs/apk/release/AndResGuard_app-release/app-release_7zip_aligned_signed.apk
# echo java -jar $ROOT_PATH/packer-ng-2.0.0.jar generate --channels=@channels.txt --output=$ROOT_PATH/build/archives $1
#java -jar $ROOT_PATH/packer-ng-2.0.0.jar generate --channels=@$ROOT_PATH/markets.txt --output=$ROOT_PATH/build/archives $1
java -jar ../packer/packer-ng-2.0.1.jar generate --channels=@../packer/channels.txt --output=../build/archives $1
echo '==============打包完成=============='