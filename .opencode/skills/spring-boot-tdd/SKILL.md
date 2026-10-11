---
name: spring-boot-tdd
description: Use when writing or changing Java or Spring Boot code under backend/ (files like FinancesApplication.java, controllers, services, repositories, entities, Liquibase changelogs). Enforces the red-green-refactor cycle with Maven and JUnit 5.
---

# Spring Boot TDD (backend)

This repository follows a strict TDD cycle. Never change production code
without a test that describes the new behavior.

## Cycle

1. **Red** — write or update a JUnit 5 test that fails for the expected reason.
2. **Green** — write the minimal production code to pass it.
3. **Refactor** — clean up with tests still green.

## Commands

Run everything from `backend/`:

- Compile: `./mvnw -q compile`
- All tests: `./mvnw -q test`
- One class: `./mvnw -q -Dtest=FinancesApplicationTests test`
- One method: `./mvnw -q -Dtest=FinancesApplicationTests#contextLoads test`
- Full verification (what "done" means): `./mvnw -q verify`

## Conventions

- Package root is `com.chownrani.finances`; group code by feature.
- Use Lombok (`@RequiredArgsConstructor`, `@Getter`, `@Builder`, ...) instead
  of boilerplate.
- Persistence changes go through Liquibase changelogs, never manual DDL.
- Test classes live under `src/test/java/...` and mirror the production package.
- Prefer `@DataJpaTest` for repositories and `@WebMvcTest` for controllers.

## Definition of done

`./mvnw -q verify` passes, and no secrets, lockfiles or generated files were
edited by hand.
