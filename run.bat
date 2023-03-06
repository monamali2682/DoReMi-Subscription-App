@echo off

gradle clean build -x test --no-daemon
java -jar build\libs\geektrust.jar input1.txt

