#!/usr/bin/env bash

case "$1" in
    clean)
        mvn clean
        ;;
    compile)
        mvn clean compile
        ;;
    install)
        mvn clean install
        ;;
    package)
        mvn clean package
        ;;
    deploy)
        mvn clean -DskipTests source:jar deploy
        ;;
    prepare)
        mvn release:prepare
        ;;
    perform)
        mvn release:perform
        ;;
    rollback)
        mvn release:rollback
        ;;
    *)
        echo "Invalid command. Usage: tester [clean|compile|install|package|deploy|prepare|perform|rollback]"
        ;;
esac
