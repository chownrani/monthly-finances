---
name: finances-domain
description: Use when implementing or reviewing any business rule of the monthly planner — the monthly calculation, teto de gastos variáveis, média diária, reserva de emergência, fechamento do mês, fatura do cartão, gastos fixos/variáveis, configuração de renda or histórico. Covers the calculation model, data model and Portuguese terminology.
---

# Finances domain

Single-user monthly budget planner. All rules are deterministic; money is
`BigDecimal`, dates are `java.time`. Interface and terms are in pt-BR.

## Monthly calculation

```
Renda do mês (R)
├── Investimento planejado   = R × percentual_investimento
├── Reserva                  = R × percentual_reserva     (0 se meta atingida)
├── Gastos fixos             = soma de gasto_fixo_mes.valor do mês
├── Fatura do cartão         = soma de fatura_mes.valor do mês
└── Teto de gastos variáveis = R − Investimento − Reserva − Fixos − Fatura

Sobra do mês            = Teto − Σ gasto_variavel_mes.valor_acumulado
Investimento acumulado  = Σ (Investimento planejado + Sobra de cada mês fechado)
Média diária permitida  = (Teto − Σ lançado) ÷ dias restantes no mês
```

Percentages are fractions of the month's income. Round to 2 decimals with
`BigDecimal`; never use `double`.

## Business rules (must be respected)

1. **Reserva stops at the meta.** Once `valor_atual >= meta_reserva` the reserve
   aporte becomes 0 and `percentual_reserva` drops to 0 — with **no automatic
   redirection** of that amount.
2. **Sobra → investimento.** The unused variable budget is redirected to
   investment **only at monthly closing**, recorded in `historico_investimento`.
3. **Variable income inherits.** If a month has no `renda` informed, it assumes
   the previous month's value (recursively back to the last informed value).
4. **Variable expenses are cumulative.** `gasto_variavel_mes.valor_acumulado`
   **replaces** the previous value for that category/month; it never increments.
5. **Month lifecycle.** A month is `aberto` or `fechado`. Closing computes the
   sobra, redirects to investment and freezes the month. No edits after closed.
6. **Order of derivation.** Teto depends on income, percentages, fixed bills and
   invoice; sobra depends on the teto and launched variable expenses.

## Data model (draft)

| Table                  | Key columns                                                        |
| ---------------------- | ------------------------------------------------------------------ |
| `configuracao`         | renda_fixa?, renda_variavel, percentual_investimento, meta_reserva, percentual_reserva, criado_em, atualizado_em |
| `mes`                  | ano, mes, renda?, status (aberto\|fechado), teto_variavel, sobra, criado_em, fechado_em |
| `gasto_fixo`           | nome, valor_padrao, dia_vencimento, ativo                           |
| `gasto_fixo_mes`       | mes_id, gasto_fixo_id, valor (editável no mês), pago               |
| `cartao`               | nome, dia_fechamento, dia_vencimento, ativo                        |
| `fatura_mes`           | mes_id, cartao_id, valor                                           |
| `categoria`            | nome, cor, ativa                                                   |
| `gasto_variavel_mes`   | mes_id, categoria_id, valor_acumulado                              |
| `historico_investimento` | mes_id, investimento_planejado, sobra_redirecionada              |

Schema changes go through **Liquibase changelogs** in
`backend/src/main/resources`, never manual DDL.

## Terminology and mapping

- Java/JSON use camelCase derived from the domain Portuguese:
  `mes`, `gastoFixo`, `cartao`, `categoria`, `gastoVariavel`, `fatura`,
  `reserva`, `percentualInvestimento`, `tetoVariavel`, `sobra`.
- DB columns stay snake_case (`valor_acumulado`, `dia_vencimento`, ...).
- Keep one canonical name per concept across entity, DTO and JSON.

## Feature packages

`config`, `mes`, `gastofixo`, `cartao`, `categoria`, `gastovariavel`,
`reserva`, `historico` — each holds its entity, repository, service and
controller.
