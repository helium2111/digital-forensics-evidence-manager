@echo off
if not exist out mkdir out
javac -d out src\model\*.java src\service\*.java src\util\*.java src\Main.java
if errorlevel 1 (
  echo Compilation failed.
  pause
  exit /b 1
)
java -cp out Main
pause
