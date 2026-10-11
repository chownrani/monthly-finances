---
name: agent-waves
description: Use when the user asks to build a feature, fix a bug, or complete a non-trivial task, or explicitly says "wave"/"waves". Runs the four specialized subagents (planner, implementer, tester, reviewer) sequentially so each phase finishes before the next starts.
---

# Agent waves

Large tasks are split into four **sequential waves**, each handled by a
dedicated subagent. Waves never overlap: a wave starts only after the previous
one has reported back.

| Wave | Agent         | Role                                   | May edit? |
| ---- | ------------- | -------------------------------------- | --------- |
| 1    | `planner`     | read-only recon + implementation plan  | no        |
| 2    | `implementer` | minimal source changes                 | yes       |
| 3    | `tester`      | tests + TDD evidence                   | yes       |
| 4    | `reviewer`    | read-only verification, PASS/FAIL      | no        |

## How to run

- Preferred: `/wave <task description>` — the command orchestrates all waves.
- Manually: invoke each with the `task` tool (`subagent_type: "planner"`, then
  `"implementer"`, then `"tester"`, then `"reviewer"`), passing prior output
  forward as context.

## Diff handoff (no divergence between waves)

Waves are fresh sessions, so the **git diff is the contract** between them.

- The orchestrator keeps `.opencode/state/wave-context.md` (git-ignored) holding
  the task, the plan and, after every wave, that wave's
  `git --no-pager diff` + `git --no-pager status --short`.
- Every wave receives the full current context plus the latest diff before it
  starts. No wave runs without it.
- The implementer and tester must run `git --no-pager diff` themselves before
  editing, build on the existing changes and never revert them.
- The tester treats the incoming diff as ground truth and only edits tests; the
  reviewer bases its verdict on the final full diff.

## Rules

- Pass each wave the previous wave's output; do not lose the plan.
- Stop on a blocker instead of improvising around a failed wave.
- The reviewer is read-only and must run `./mvnw -q verify` before judging.
- Keep the four-wave flow for non-trivial work; small one-line edits may skip it.
