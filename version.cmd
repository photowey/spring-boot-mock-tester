@echo off
setlocal enabledelayedexpansion

echo ==========================================
echo   Spring Boot Mock Tester - Version Refresh
echo ==========================================

if "%~1"=="" (
    echo Usage: %~nx0 ^<version^> [--yes]
    exit /b 1
)

set NEW_VERSION=%~1
set AUTO_CONFIRM=%~2

git rev-parse --is-inside-work-tree >nul 2>nul
if errorlevel 1 (
    echo [ERROR] Not inside a git repository.
    exit /b 1
)

for /f %%i in ('git status --porcelain') do (
    echo [ERROR] Working tree is not clean.
    echo Please commit or stash changes first.
    exit /b 1
)

for /f "delims=" %%i in ('mvn help:evaluate -Dexpression^=project.version -q -DforceStdout') do (
    set CURRENT_VERSION=%%i
)

echo.
echo Current Version : !CURRENT_VERSION!
echo Target Version  : %NEW_VERSION%
echo.

if /I not "%AUTO_CONFIRM%"=="--yes" (
    set /p CONFIRM=Confirm update !CURRENT_VERSION! ^> %NEW_VERSION% ? (y/N):

    if /I not "!CONFIRM!"=="Y" (
        echo Cancelled.
        exit /b 1
    )
)

echo.
echo Updating version...
echo.

call mvn versions:set ^
    -DprocessAllModules=true ^
    -DgenerateBackupPoms=false ^
    -DnewVersion=%NEW_VERSION%

if errorlevel 1 exit /b 1

call mvn versions:update-child-modules -DgenerateBackupPoms=false
if errorlevel 1 exit /b 1

call mvn versions:commit
if errorlevel 1 exit /b 1

echo.
echo ==========================================
echo   Version updated successfully
echo   !CURRENT_VERSION! ^> %NEW_VERSION%
echo ==========================================
echo.

endlocal