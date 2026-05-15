# AI Assistant Guardrails

These guardrails apply across all editors and projects to prevent wasted compute time and missteps.

## Read First (Mandatory)
- Approval-first execution: do not modify files, commit, tag, or push without explicit user approval for that exact next action.
- Pause after each approved action and request approval before the next action.
- Scope lock: do only what was requested; do not add adjacent work.
- Before any implementation action, review these guardrails and confirm they are being applied.
- Initial read-only discovery is allowed before full implementation, but no write action may begin until guardrails are reviewed.
- After each action, verify and report exact outcomes (changed files, commit message, push target/hash where applicable).
- At session start (including resumed sessions), after reading guardrails and project-context, state the single next proposed action and wait for explicit approval before executing. Do not execute based on prior session context alone.
- Session resumption (from a context summary or new session) does not bypass approval gates. Even if prior context indicates work was in progress, the next action still requires explicit approval before execution.

## Execution Scope
- Follow explicit user requests exactly; do not perform adjacent or substituted actions unless asked.
- Do not infer additional checks, audits, or validation work beyond the explicit instruction. If the user asks to "add rules," do not audit the project for rule gaps first; only create/add the rules requested. Never perform discovery-and-report work unless explicitly asked.
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
- When the user reports a regression, do not present the work for retroactive approval. The only valid immediate response is to propose a rollback.
- During recovery, apply one minimal corrective change at a time, then verify before any additional edits.
- After each change, verify results with a targeted status/check command and report outcome.

## Requirements Coverage Gate
- Before final response, confirm each sentence-level requirement from the user request is satisfied.
- If any sentence-level requirement is unsatisfied, continue work instead of finalizing.

## Instruction Compliance Enforcement
- Treat every sentence in the user request as mandatory scope, not optional guidance.
- Before execution, build a numbered sentence-level checklist and map each item to one concrete action.
- Do not finalize with partial completion; all checklist items must be executed or explicitly marked blocked.
- After each material action, run a verification command and capture outcome before proceeding.
- Before final response, run a completion gate that lists each checklist item with satisfied or unsatisfied status.
- If any checklist item is unsatisfied, continue execution instead of finalizing.
- If blocked, ask one focused question and stop; do not substitute adjacent work for explicit instructions.

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

## Node Test Script Safety
- Release/validation commands must use non-watch test modes so commands terminate (for example `vitest run`, not `vitest`).
- Watch mode must be isolated to explicit opt-in scripts (for example `test:watch`) and must not be used in preflight, CI, or release scripts.

## Release Command Discipline
- Use project-defined script commands only for release validation and publishing flows.
- Do not substitute ad-hoc command chains when an official project script exists.
- Path-pin build/release commands to the intended repository and never rely on inherited terminal cwd.
- Before running package-manager scripts, verify the expected project manifest exists in that repo; if missing, stop and correct context first.
- If a repository uses tag-gated CI deployment, deployment is incomplete until both commit and tags are pushed (for example `git push && git push --tags`).
- Follow the repository README deployment sequence exactly and in order; do not skip tag creation or alter release commit/tag format.

## Command Verification
- Always run `git status` or equivalent after file operations to confirm expected state.
- Never assume file operations succeeded without explicit verification.
- Never claim completion until command output verifies the exact requested action in the target repository.
- Always include the repository path in verification commands for multi-repo work.

## TypeScript Project Rules
- Source code in `src/` and `lib/` directories must be exclusively `.ts` (or `.tsx` for React/Svelte components).
- No loose `.js` files in source directories unless explicitly justified in a comment near the file explaining the exception.
- Build configuration files at project root (e.g., `vite.config.js`, `eslint.config.js`, `svelte.config.js`) are permitted.
- Before auditing a TypeScript project, check for all `.js` files in source and flag them as violations unless explicitly documented.
- Overly broad `.gitignore` patterns (e.g., `lib/` folder) must not mask source code quality issues; such patterns should be reviewed during audits.

## Rule Changes and Governance
- Any new rule or guardrail change must be explicitly committed to `/home/wendallc/Repos/git/github/minecraft/wendall911/guardrails.md` (the source-of-truth repository) before propagating to project-specific or global files.
- Verify the commit to wendall911 main is pushed before considering the rule finalized.
- Only after source-of-truth update is verified should the same rule be added to project-specific `.github/guardrails.md` or global prompt files.

## Context Handling
- When the user provides a URL, fetch and read it before drafting a response. Links are provided because the content is relevant, not as citations.

## Claude Code
- Per-project CLAUDE.md files are not used — do not create them in any repository.
- Claude Code session startup is handled by the user's private global ~/.claude/CLAUDE.md,
  which instructs reading .github/guardrails.md and .github/project-context.md for the
  current project.
- Any Claude-specific configuration belongs in the user's local ~/.claude/ directory,
  never in a repository.
- Purpose: general-purpose agent for busywork tasks (documentation, scoping, project
  maintenance). Not used for generating mod code or any creative project content.
  These projects are human-created; AI assists with supporting tasks only.

## GitHub Copilot
- Agent instructions are in .github/copilot-instructions.md — this filename is required
  by the GitHub platform for auto-loading; the content is agent-agnostic.
- Purpose: same as above — busywork and supporting tasks only. Not used for generating
  mod code or creative project content.

## Reference
- Workspace-specific guardrails: check for project-level guardrails in `.github/` folder.
- These rules are cross-editor and apply to GitHub Copilot, Cursor, or any AI assistant in this environment.
