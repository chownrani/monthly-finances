---
name: finances-domain
description: Use when implementing or reviewing any business rule of the monthly planner — the monthly calculation, variable spending cap, daily average, emergency reserve, month closing, card invoice, fixed/variable expenses, income setup or history. Covers the calculation model, data model and English terminology.
---

# Finances domain

Single-user monthly budget planner. All rules are deterministic; money is
`BigDecimal`, dates are `java.time`. Source identifiers and JSON are in
**English**; UI copy stays pt-BR and currency is BRL (R$).

## Monthly calculation

```
Monthly income (R)
├── Planned investment     = R × income percentage
├── Emergency reserve      = R × reserve percentage  (0 once the goal is reached)
├── Fixed expenses         = Σ subscription_competence.amount of the month
├── Card invoice           = Σ invoice.amount of the month
└── Variable spending cap  = R − Investment − Reserve − Fixed − Invoice

Month surplus            = Cap − Σ variable_expense_competence.accumulated_amount
Accumulated investment   = Σ (Planned investment + Surplus of each closed month)
Allowed daily average    = (Cap − Σ launched) ÷ remaining days in the month
```

Percentages are fractions of the month's income. Round to 2 decimals with
`BigDecimal`; never use `double`.

## Business rules (must be respected)

1. **Reserve stops at the goal.** Once `total_saved >= goal` the reserve
   contribution becomes 0 and the reserve percentage drops to 0 — with **no
   automatic redirection** of that amount.
2. **Surplus → investment.** The unused variable budget is redirected to
   investment **only at monthly closing**, recorded in `investment_history`.
3. **Variable income inherits.** If a month has no `income` informed, it assumes
   the previous month's value (recursively back to the last informed value).
4. **Variable expenses are cumulative.** `variable_expense_competence.accumulated_amount`
   **replaces** the previous value for that category/month; it never increments.
5. **Month lifecycle.** A month is `OPEN` or `CLOSED`. Closing computes the
   surplus, redirects to investment and freezes the month. No edits after closed.
6. **Order of derivation.** Cap depends on income, percentages, fixed expenses
   and invoice; surplus depends on the cap and launched variable expenses.

## Data model

All entities extend `AbstractEntity` (`@MappedSuperclass`): `id`, `created_at`
(`@CreatedDate`), `updated_at` (`@LastModifiedDate`) and soft delete via
`@SoftDelete(columnName = "deleted_at", strategy = TIMESTAMP)`.

| Table                       | Key columns                                                                                 |
| --------------------------- | ------------------------------------------------------------------------------------------- |
| `users`                     | name, email, base_income                                                                    |
| `account`                   | user_id, current_balance                                                                    |
| `investment`                | user_id, income_percentage, total_saved                                                     |
| `emergency`                 | user_id, goal, income_percentage, total_saved                                              |
| `competence`                | user_id, year, month, income?, status (OPEN\|CLOSED), variable_limit, surplus, closed_at    |
| `subscription`              | fixed_expense_category_id, name, default_value, due_day, active                             |
| `subscription_competence`   | competence_id, subscription_id, amount (editable in the month), paid                        |
| `card` / `credit` / `debit` | user_id, name, active; credit adds closing_day, due_day                                     |
| `invoice`                   | competence_id, credit_id, amount                                                            |
| `fixed_expense_category`    | user_id, name, active                                                                       |
| `variable_expense_category` | user_id, name, color, active                                                                |
| `variable_expense_competence` | competence_id, category_id, accumulated_amount                                            |
| `investment_history`        | **(not implemented)** competence_id, planned_investment, redirected_surplus                 |

Every table also carries the audit/soft-delete columns `created_at`, `updated_at`
and `deleted_at`. Schema changes go through **Liquibase changelogs** in
`backend/src/main/resources`, never manual DDL.

## Terminology and mapping

- Java entities use English names: `User`, `Account`, `Investment`, `Emergency`,
  `Competence`, `Subscription`, `SubscriptionCompetence`, `Card`/`Credit`/`Debit`,
  `Invoice`, `FixedExpenseCategory`, `VariableExpenseCategory`,
  `VariableExpenseCompetence`, `CompetenceStatus` (`OPEN`/`CLOSED`).
- DB tables and columns are snake_case (`subscription_competence`,
  `accumulated_amount`, `due_day`, ...) and map 1:1 to the entity fields.
- Keep one canonical name per concept across entity, DTO and JSON.
- UI copy and currency remain pt-BR / BRL.

## Feature packages

`config`, `competence`, `subscription`, `card`, `category`, `variableexpense`,
`emergency`, `investment` — each holds its entity, repository, service and
controller.
