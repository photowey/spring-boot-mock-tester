@echo off

echo --- Prepare to refresh the spring-boot-mock-tester project version ---

mvn versions:set -DprocessAllModules=true -DgenerateBackupPoms=false -DnewVersion=%1

mvn versions:update-child-modules -DgenerateBackupPoms=false
mvn versions:commit