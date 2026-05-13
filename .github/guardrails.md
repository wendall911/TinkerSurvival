# AI Assistant Guardrails

These guardrails apply across all editors and projects to prevent wasted compute time and missteps.

## Execution Scope
- Follow explicit user requests exactly; do not perform adjacent or substituted actions unless asked.
- If intent is unclear, infer the most useful likely action and proceed instead of guessing.
- Treat every user sentence as an instruction-bearing requirement until explicitly resolved.
- Before first edit, create an internal checklist that maps each sentence/constraint to a concrete action.

## Cleanup Operations
- Before cleanup operations, run a dry-run preview first and show exact targets.
- Do not use force-remove style cleanup commands (`rm -f`, etc).
- Prefer git-scoped cleanup and restoration commands over raw file deletion.

## Destructive Actions
- Require explicit confirmation before broad or destructive operations.
- Show planned command and expected impact before running.

## Error Recovery
- Keep responses remediation-focused when prior AI actions caused regressions.
- If the user reports an error introduced by recent AI changes, roll back the breaking change first, verify the rollback, then re-apply a minimal fix.
- During recovery, apply one minimal corrective change at a time, then verify before any additional edits.
- After each change, verify results with a targeted status/check command and report outcome.

## Requirements Coverage Gate
- Before final response, confirm each sentence-level requirement from the user request is satisfied.
- If any sentence-level requirement is unsatisfied, continue work instead of finalizing.

## Content Rules
- No AI written text.
- Use direct, specific language and avoid AI-isms/AI-slop patterns.

## Commit Messages
- Commit messages must be concise, human-readable summaries of what changed.
- Do not include AI-isms, filler, or excessive technical detail that duplicates the diff.

## Branch Safety
- Before creating commits, verify the local branch matches the remote default branch (typically `main`) and tracks the correct upstream.
- If branch names diverge (for example `master` local vs `main` remote), align branches first and confirm commits are on the push target branch before proceeding.
- Never create or use `master` when the repository default branch is `main`.

## Command Verification
- Always run `git status` or equivalent after file operations to confirm expected state.
- Never assume file operations succeeded without explicit verification.

## Reference
- Workspace-specific guardrails: check for project-level guardrails in `.github/` folder.
- These rules are cross-editor and apply to GitHub Copilot, Cursor, or any AI assistant in this environment.
