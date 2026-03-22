# フェーズ0: 依存関係棚卸し・脆弱性可視化 Runbook

この文書は `docs/eos-vulnerability-modernization-plan.md` の **フェーズ0（調査・可視化）** を実行するための運用手順です。

## 1. 目的

- Maven / npm の依存関係を自動出力する
- npm の脆弱性情報を JSON 形式で保存する
- 実行結果をタイムスタンプ付きで保管し、比較可能にする

## 2. 実行コマンド

```bash
./scripts/security/phase0-audit.sh
```

## 3. 出力先

- `docs/security/reports/<UTCタイムスタンプ>/`
- `docs/security/reports/latest` は最新結果へのシンボリックリンク

主な出力ファイル:

- `maven-dependency-tree.txt`
- `maven-dependency-list.txt`
- `npm-ls.txt`
- `npm-audit.json`

## 4. 失敗時の扱い

ツール未導入やネットワーク制約などで一部コマンドが失敗しても、可能な範囲で結果を保存します。
失敗の詳細は各出力ファイルに記録されるため、`latest` 配下を確認してください。

## 5. 次のサブフェーズ

- OWASP Dependency-Check / Trivy などの SCA ツールを CI に統合
- CycloneDX による SBOM 生成を追加
- Severity 閾値（Critical/High）を CI の fail 条件へ反映
