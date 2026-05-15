# Agent Instructions

These instructions are always-on for this repository.

## Session Startup
- At the start of every new chat session, read `.github/guardrails.md` first — unconditionally, before any other file, question, or action. Verify it is in sync with the source of truth at `../.github/guardrails.md`. If out of sync, stop and flag the discrepancy before proceeding.
- After guardrails are confirmed, read `.github/project-context.md`.
- The full guardrails apply to every action, response, and decision in the session — not only to changes.
- Treat every user sentence as an instruction-bearing requirement until explicitly resolved.
- Before first edit, create an internal checklist that maps each sentence and constraint to a concrete action.
- If the user reports a regression, stop forward edits, roll back the breaking change first, verify the rollback, then apply a minimal fix.

## Requirements Handling
- Before final response, confirm every sentence-level requirement is satisfied.
- If any sentence-level requirement is unsatisfied, continue work instead of finalizing.

## Communication Quality
- Use direct, specific language.
- Avoid AI-isms, filler phrasing, and AI-slop patterns.
- No AI written text unless the user explicitly requests it.

## Verification Discipline
- Verify each change with a targeted status/check command before proceeding.
