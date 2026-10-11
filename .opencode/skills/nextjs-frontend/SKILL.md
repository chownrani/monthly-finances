---
name: nextjs-frontend
description: Use when building or changing the web UI under frontend/ — Next.js pages, React components, Shadcn UI, Tailwind styling, forms, charts or API calls for the finance panel. Not yet scaffolded; ask before creating the app.
---

# Frontend (Next.js + Shadcn + Tailwind)

The `frontend/` app does not exist yet. Do not scaffold it without confirming
the approach with the user.

## Intended stack

- **Next.js** (App Router) + **TypeScript**
- **Shadcn UI** components + **Tailwind CSS**
- REST calls to the Spring Boot API (JSON), no auth (single user)
- Charts for the histórico screen (e.g. Recharts)

## Product constraints

- **Desktop-first**, dense and informative (a control panel, not mobile).
- **pt-BR** copy and labels; never English strings in the UI.
- **BRL currency**: format with `Intl.NumberFormat("pt-BR", { style: "currency",
  currency: "BRL" })`; never hardcode `R$` formatting by hand.
- **Read-heavy UI**: most interactions are viewing; writes are rare.
- Money from the API arrives as numbers/strings; keep it exact (no float math in
  the UI — do not sum floats client-side, prefer server-computed totals).

## Planned layout

```
frontend/
  src/
    app/                 (routes / pages)
    components/ui/       (Shadcn)
    components/domain/   (panel, cards, tables, modals)
    lib/                 (formatters, api client)
    services/            (API bindings)
```

## Conventions

- Group domain components in `components/domain/`; keep Shadcn primitives
  untouched in `components/ui/`.
- One API client module; type the responses with interfaces mirroring the
  backend DTOs (Portuguese domain terms).
- Match the screens in the requirements doc: Painel principal (7.1),
  Configuração (7.2), Modal lançar gasto variável (7.3), Histórico (7.4).
- Follow the domain rules in `.opencode/skills/finances-domain/SKILL.md`; never
  re-implement the monthly calculation in the UI.
