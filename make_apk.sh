#!/bin/bash
mainPath=/home/neel/AndroidStudioProjects/TemplateOwnerApp

p=$mainPath/app/src/main/assets/meta_data.txt
#echo $p
echo $1 > $p

appName='2s|.*|<string name="app_name">'$2'</string>|'

sed -i "$appName" $mainPath/app/src/main/res/values/strings.xml

id='7s/.*/        applicationId "com.abc.'$2'"/'
sed -i "$id" $mainPath/app/build.gradle

cd $mainPath
./gradlew assembleDebug
cd -

mv $mainPath/app/build/outputs/apk/debug/app-debug.apk $mainPath/app/build/outputs/apk/debug/$2.apk
