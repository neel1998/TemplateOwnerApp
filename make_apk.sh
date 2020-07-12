#!/bin/bash

p=/home/neel/AndroidStudioProjects/TemplateOwnerApp/app/src/main/assets/meta_data.txt
#echo $p
echo $1 > $p

appName='2s|.*|<string name="app_name">'$2'</string>|'

sed -i "$appName" /home/neel/AndroidStudioProjects/TemplateOwnerApp/app/src/main/res/values/strings.xml

id='7s/.*/        applicationId "com.abc.'$2'"/'
sed -i "$id" app/build.gradle

./gradlew assembleDebug

mv /home/neel/AndroidStudioProjects/TemplateOwnerApp/app/build/outputs/apk/debug/app-debug.apk /home/neel/AndroidStudioProjects/TemplateOwnerApp/app/build/outputs/apk/debug/$2.apk
