#!/usr/bin/env bash

set -euo pipefail

echo "=========================================="
echo "  Spring Boot Mock Tester - Version Refresh"
echo "=========================================="

if [ $# -lt 1 ]; then
  echo "Usage: $0 <version> [--yes]"
  exit 1
fi

NEW_VERSION="$1"
AUTO_CONFIRM="${2:-}"

if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
  echo "[ERROR] Not inside a git repository."
  exit 1
fi

if [[ -n "$(git status --porcelain)" ]]; then
  echo "[ERROR] Working tree is not clean. Please commit or stash changes first."
  exit 1
fi

CURRENT_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

echo
echo "Current Version : ${CURRENT_VERSION}"
echo "Target Version  : ${NEW_VERSION}"
echo

if [[ "$AUTO_CONFIRM" != "--yes" ]]; then
  read -r -p "Confirm update ${CURRENT_VERSION} -> ${NEW_VERSION}? (y/N): " CONFIRM
  if [[ ! "$CONFIRM" =~ ^[Yy]$ ]]; then
    echo "Cancelled."
    exit 1
  fi
fi

echo
echo "Updating version..."
echo

mvn versions:set \
  -DprocessAllModules=true \
  -DgenerateBackupPoms=false \
  -DnewVersion="${NEW_VERSION}"

mvn versions:update-child-modules -DgenerateBackupPoms=false
mvn versions:commit

echo
echo "=========================================="
echo "  Version updated successfully"
echo "  ${CURRENT_VERSION} -> ${NEW_VERSION}"
echo "=========================================="
echo