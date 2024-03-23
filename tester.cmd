@echo off
Setlocal enabledelayedexpansion

echo command path: !cd!
cd !cd!

SET command=%1

@REM mvn OR mvnd

IF "%command%"=="clean" (
    call mvn clean
) ELSE IF "%command%"=="compile" (
    call mvn clean compile
) ELSE IF "%command%"=="install" (
    call mvn clean install
) ELSE IF "%command%"=="package" (
    call mvn clean package
) ELSE IF "%command%"=="deploy" (
    call mvn clean -DskipTests source:jar deploy
) ELSE IF "%command%"=="prepare" (
    call mvn release:prepare
) ELSE IF "%command%"=="perform" (
    call mvn release:perform
) ELSE IF "%command%"=="rollback" (
    call mvn release:rollback
) ELSE (
    echo "Invalid command. Usage: tester [clean|compile|install|package|deploy|prepare|perform|rollback]"
)
