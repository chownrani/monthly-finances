# Monthly Finances — Agent Guide

Instructions for AI agents working in this repository. Read this file first.

## Project overview

**Planejador Financeiro Pessoal** (repo `monthly-finances`) is a single-user web
app for monthly budget planning. It computes how much the user can spend from
fixed priorities (investment, emergency reserve, fixed bills, card invoice) and
tracks spending pace through the month.

Usage philosophy: **read often, write rarely**. It is a monthly control panel,
not a daily ledger. Interface and copy are in **Brazilian Portuguese**; currency
is **BRL (R$)**; **desktop-first**; no complex authentication (single user).

## Tech stack

- **Backend:** Java 25, Spring Boot 4.1.1, Maven (wrapper `mvnw`)
- Spring Data JPA, PostgreSQL, Liquibase (migrations), Spring Security, WebMVC
- Lombok, JUnit 5, REST (JSON)
- **Frontend (planned):** Next.js + TypeScript + Shadcn UI + Tailwind CSS
- **Infra (planned):** Docker + Docker Compose (Postgres + backend + frontend)

> Frontend and infra do not exist yet. Ask before assuming a stack or scaffolding.

## Repository layout

```
backend/    Spring Boot API (Maven project root is backend/)
  src/main/java/com/chownrani/finances/
  src/main/resources/          (Liquibase changelogs)
  src/test/java/com/chownrani/finances/
frontend/   Next.js app (planned)
docker-compose.yml           (planned)
.opencode/  Agent config: agents, commands, skills, plugins
opencode.json
```

Backend code is grouped **by feature** under `com.chownrani.finances`:
`config`, `mes`, `gastofixo`, `cartao`, `categoria`, `gastovariavel`,
`reserva`, `historico`.

## Common commands

Run Maven from inside `backend/` (or pass `-f backend/pom.xml`):

| Task         | Command                              |
| ------------ | ------------------------------------ |
| Compile      | `./mvnw -q compile`                  |
| Run tests    | `./mvnw -q test`                     |
| Single test  | `./mvnw -q -Dtest=ClassName test`    |
| Full verify  | `./mvnw -q verify`                   |
| Run API      | `./mvnw spring-boot:run`             |

Always run the relevant tests before claiming a task is done.

## Domain essentials

The monthly calculation is deterministic and the core of the product. In short:

```
Renda do mês (R)
├── Investimento planejado   = R × %investimento
├── Reserva                  = R × %reserva          (0 se meta atingida)
├── Gastos fixos             = soma dos fixos do mês
├── Fatura do cartão         = valor informado no mês
└── Teto de gastos variáveis = R − Investimento − Reserva − Fixos − Fatura
Sobra do mês           = Teto − gastos variáveis lançados
Média diária permitida = (Teto − lançado) ÷ dias restantes no mês
```

Key rules: reserve stops receiving aporte once the meta is hit (and its
percentage drops to zero, with no automatic redirection); the variable-budget
leftover flows to **investment** at monthly closing; a missing variable income
inherits the previous month; variable expenses are entered as an **accumulated
value** (it replaces the previous one, never increments).

Full rules, data model and terminology: `.opencode/skills/finances-domain/SKILL.md`.

## Development conventions

- Follow the **TDD** cycle: failing test, minimal fix, then refactor.
- Keep the package root `com.chownrani.finances`; group by feature (see above).
- Use Lombok annotations instead of hand-written boilerplate where it fits.
- Database schema changes go through **Liquibase changelogs**, never manual DDL.
- Money is never `double`; use `BigDecimal`. Dates: `java.time`.
- REST endpoints and JSON fields follow the domain terms (Portuguese) used in
  the requirements; keep a consistent mapping to DB snake_case.
- Frontend: follow `.opencode/skills/nextjs-frontend/SKILL.md`.
- Do not modify generated/build output, dependency directories or lockfiles by hand.
- Never read, write or print secrets (credentials, tokens, `.env` files, keys).

## Guardrails (enforced)

Configured in `opencode.json` and `.opencode/plugin/guardrails.ts`.

- `git push` and `git commit` require explicit confirmation.
- Destructive commands are denied: `rm -rf`, `sudo`, `git reset --hard`,
  `git clean -f`, `chmod -R 777`, `--no-verify`, `git push --force`, piping
  downloads into a shell.
- Edits to lockfiles and secret files are blocked.
- Access to `~/.ssh`, `~/.aws`, `~/.gnupg` and `~/.config/gh` is denied.

## Agent waves

Larger tasks run as four sequential waves, one specialized subagent each:

1. **planner** — read-only reconnaissance and a written implementation plan.
2. **implementer** — writes the minimal source changes.
3. **tester** — writes/updates tests and drives the TDD cycle.
4. **reviewer** — read-only verification of correctness and conventions.

Run them with the `/wave <task>` command. Each wave must finish before the next
starts. See `.opencode/skills/agent-waves/SKILL.md` for details.

## Definition of done

- [ ] Tests added/updated and passing (`./mvnw -q verify`)
- [ ] No build, lint or type errors
- [ ] No secrets, lockfiles or generated files touched by hand
- [ ] Work committed in logical, task-sized commits (on a branch, not `main`)
