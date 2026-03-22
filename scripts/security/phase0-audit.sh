#!/usr/bin/env bash
set -euo pipefail

# This script generates dependency and vulnerability reports for Phase 0.
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
REPORT_DIR="${ROOT_DIR}/docs/security/reports"
TS="$(date -u +%Y%m%dT%H%M%SZ)"
RUN_DIR="${REPORT_DIR}/${TS}"
LATEST_LINK="${REPORT_DIR}/latest"

mkdir -p "${RUN_DIR}"

has_cmd() {
  command -v "$1" >/dev/null 2>&1
}

run_and_capture() {
  local output_file="$1"
  shift

  echo "[INFO] Running: $*"
  if "$@" >"${output_file}" 2>&1; then
    echo "[OK] $*"
  else
    echo "[WARN] Command failed: $*"
    echo "[WARN] See ${output_file}"
    return 1
  fi
}

status=0

if has_cmd mvn; then
  run_and_capture \
    "${RUN_DIR}/maven-dependency-tree.txt" \
    mvn -q org.apache.maven.plugins:maven-dependency-plugin:3.6.1:tree -Dscope=runtime || status=1
  run_and_capture \
    "${RUN_DIR}/maven-dependency-list.txt" \
    mvn -q org.apache.maven.plugins:maven-dependency-plugin:3.6.1:list -DincludeScope=runtime || status=1
else
  {
    echo "mvn command was not found."
    echo "Install Maven to generate Java dependency reports."
  } >"${RUN_DIR}/maven-dependency-tree.txt"
  cp "${RUN_DIR}/maven-dependency-tree.txt" "${RUN_DIR}/maven-dependency-list.txt"
  echo "[WARN] mvn command not found"
  status=1
fi

if has_cmd npm; then
  run_and_capture "${RUN_DIR}/npm-ls.txt" npm ls --all --depth=5 || status=1
  created_lockfile=0
  if [ ! -f "${ROOT_DIR}/package-lock.json" ]; then
    echo "[INFO] package-lock.json was not found. Generating a temporary lockfile for npm audit."
    if npm install --package-lock-only --ignore-scripts --no-audit >/dev/null 2>&1; then
      created_lockfile=1
    else
      echo "[WARN] Failed to generate temporary package-lock.json for npm audit."
      status=1
    fi
  fi
  run_and_capture "${RUN_DIR}/npm-audit.json" npm audit --json || status=1
  if [ "${created_lockfile}" -eq 1 ]; then
    rm -f "${ROOT_DIR}/package-lock.json"
    echo "[INFO] Removed temporary package-lock.json"
  fi
else
  {
    echo "npm command was not found."
    echo "Install Node.js and npm to generate Node.js dependency reports."
  } >"${RUN_DIR}/npm-ls.txt"
  cp "${RUN_DIR}/npm-ls.txt" "${RUN_DIR}/npm-audit.json"
  echo "[WARN] npm command not found"
  status=1
fi

cat >"${RUN_DIR}/README.md" <<SUMMARY
# Security Reports (${TS})

このディレクトリには、フェーズ0の依存関係棚卸しと脆弱性調査の出力結果を格納しています。

## Files
- maven-dependency-tree.txt
- maven-dependency-list.txt
- npm-ls.txt
- npm-audit.json

## Notes
- レポートは UTC タイムスタンプ形式で生成されています。
- 制限環境やオフライン環境では、一部コマンドが失敗する場合があります。
SUMMARY

rm -f "${LATEST_LINK}"
ln -s "${TS}" "${LATEST_LINK}"

echo "[INFO] Reports generated in ${RUN_DIR}"
echo "[INFO] Latest symlink updated: ${LATEST_LINK} -> ${TS}"

exit "${status}"
