@echo off
echo Student Result Management System - Compile and Run Script
echo.

REM Check if MySQL JDBC driver exists
if not exist "mysql-connector-java-8.0.33.jar" (
    echo ERROR: MySQL JDBC driver not found!
    echo Please download mysql-connector-java-8.0.33.jar and place it in this directory
    echo Download from: https://dev.mysql.com/downloads/connector/j/
    pause
    exit /b 1
)

echo Compiling Java application...
javac -cp ".;mysql-connector-java-8.0.33.jar" StudentResultManagement.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Starting Student Result Management System...
echo Make sure MySQL server is running and database is set up!
echo.

java -cp ".;mysql-connector-java-8.0.33.jar" StudentResultManagement

pause