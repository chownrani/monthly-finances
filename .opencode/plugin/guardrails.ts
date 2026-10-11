import type { Plugin } from "@opencode-ai/plugin"

class GuardrailError extends Error {}

const BLOCKED_BASH: Array<[RegExp, string]> = [
  [/\brm\s+(-[a-zA-Z]*r[a-zA-Z]*f|-[a-zA-Z]*f[a-zA-Z]*r)\b/, "recursive force delete"],
  [/(^|[\s;&|])sudo\b/, "privilege escalation"],
  [/git\s+reset\s+--hard\b/, "hard reset"],
  [/git\s+clean\s+-[a-zA-Z]*f/, "forced clean"],
  [/--no-verify\b/, "hook bypass"],
  [/git\s+push\b[^\n]*(--force\b|\s-f\b)/, "force push"],
  [/chmod\s+-R\s+777\b/, "world-writable permissions"],
  [
    /\b(curl|wget)\b[^\n]*\|\s*(sudo\s+)?(ba|z)?sh\b/,
    "piping a download into a shell",
  ],
]

const PROTECTED_PATHS: Array<[RegExp, string]> = [
  [/(^|[/\\])\.env(\.|$)/, "secret file"],
  [/(^|[/\\])\.git-credentials$/, "credential file"],
  [/(^|[/\\])\.netrc$/, "credential file"],
  [/(^|[/\\])id_(rsa|dsa|ecdsa|ed25519)(\.pub)?$/, "SSH key"],
  [
    /(^|\/)(package-lock\.json|bun\.lock|pnpm-lock\.yaml|yarn\.lock|poetry\.lock|Pipfile\.lock|Gemfile\.lock|Cargo\.lock|composer\.lock)$/,
    "lockfile",
  ],
  [/(^|\/)\.opencode\/plugin\//, "guardrail plugin"],
]

function checkBash(command: string) {
  for (const [pattern, what] of BLOCKED_BASH) {
    if (pattern.test(command)) {
      throw new GuardrailError(
        `Blocked by project guardrails (${what}); this command is not allowed. Command: ${command}`,
      )
    }
  }
}

function checkPath(filePath: unknown, verb: string) {
  if (typeof filePath !== "string" || filePath.length === 0) return
  for (const [pattern, what] of PROTECTED_PATHS) {
    if (pattern.test(filePath)) {
      throw new GuardrailError(
        `Blocked by project guardrails: refusing to ${verb} protected ${what} "${filePath}".`,
      )
    }
  }
}

export default (async () => {
  return {
    "tool.execute.before": async (input, output) => {
      try {
        const tool = (input as { tool?: string } | undefined)?.tool
        const args =
          ((output as { args?: Record<string, unknown> } | undefined)?.args ??
            {}) as Record<string, unknown>

        if (tool === "bash") {
          const command = args.command
          if (typeof command === "string") checkBash(command)
        } else if (tool === "edit" || tool === "write" || tool === "patch") {
          checkPath(args.filePath ?? args.path ?? args.file, tool)
        }
      } catch (error) {
        if (error instanceof GuardrailError) throw error
      }
    },
  }
}) satisfies Plugin
