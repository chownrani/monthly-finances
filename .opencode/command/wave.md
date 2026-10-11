---
description: Run the four agent waves (plan → implement → test → review) for a task, sharing the git diff between every wave
agent: build
---

You are the **wave orchestrator**. Run the waves below **sequentially** for this
task:

> $ARGUMENTS

Each wave is a fresh session that cannot see the others. The **git diff is the
shared source of truth**: you must capture it after every wave and hand the
accumulated diff to the next one, so no implementation diverges from what came
before.

## Shared context file

Maintain a single file at `.opencode/state/wave-context.md`:

- **Start** (before wave 1): overwrite it with the task description and an empty
  `## Diff log` section.
- **After each wave**: append a heading for that wave, the wave's summary, and
  the exact output of:

  ```
  git --no-pager diff
  git --no-pager status --short
  ```

- **Before each wave**: pass the **entire current content** of that file into the
  wave's prompt. Never start a wave without it.

## Hard rules

- One wave at a time with the `task` tool; **wait** for it to finish before the
  next. Never run waves in parallel.
- Do not skip or reorder waves. Do not do the work yourself; delegate it.
- After the implementer and tester waves, always capture the diff before moving on.
- If a wave returns a blocker, stop and report it instead of improvising.

## Sequence

1. Write `.opencode/state/wave-context.md` with the task and empty diff log.
2. **planner** — `task` `subagent_type: "planner"`. Give it the task. Read its
   plan. Append plan to the context file. Capture the diff (likely empty).
3. **implementer** — `task` `subagent_type: "implementer"`. Give it the full
   context file (task + plan + latest diff) and instruct it to build on the
   current diff, never revert it. Append its summary. Capture the new diff.
4. **tester** — `task` `subagent_type: "tester"`. Give it the context file,
   including the implementer's full diff as ground truth. Append its summary.
   Capture the new diff.
5. **reviewer** — `task` `subagent_type: "reviewer"`. Give it the context file
   and the final full diff; require a PASS/FAIL grounded in that diff. Append
   its verdict.
6. Print a short summary: files changed (`git --no-pager diff --stat`), the
   `./mvnw -q verify` result, and the reviewer's verdict.
