# Workspace Copilot Instructions

These instructions are always-on for this repository.

## Session Startup
- At the start of every new chat session, read `.github/guardrails.md` before making any changes.
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
