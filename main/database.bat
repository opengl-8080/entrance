@echo off

call gradle bootJar

java -jar build\libs\entrance.jar --database --ENTRANCE_HOME=./entrance_home
pause
