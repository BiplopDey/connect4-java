# Connect4 Java (Spring Boot)

A small Spring Boot service that applies Connect 4 rules to a given board, places a token for a player, and returns the updated game state (PLAYING, DRAW, or WINNER) with winning cells when applicable.

- Language: Java 17
- Framework: Spring Boot 2.7
- Build: Maven
- Container: Docker
- Orchestration: Kubernetes (manifests in `k8s/`)
- CI/CD: GitHub Actions (Docker Hub & AWS ECR publishing; EKS/App Runner deploy; SonarQube; local kind smoke test)

## Quick Start

- Prerequisites: Java 17, Maven (or use the bundled wrapper), Docker (optional), kubectl (optional)
- Build and test: `./mvnw verify` (Windows: `mvnw.cmd verify`)
- Run locally (jar): `java -jar target/*.jar`
- Health: `GET http://localhost:8080/public/actuator/health`

## API

Base URL: `http://localhost:8080`

- POST `/connect4/placeToken`
  - Places a token for a player in a specific column and returns the new state.
  - Request body fields:
    - `table`: array of columns; each column is bottom-to-top token values
      - token values: `1` for PLAYER_1, `2` for PLAYER_2
    - `player`: `1` or `2`
    - `column`: 1-based column index (leftmost is `1`)
    - `row_size`: maximum number of rows for the board (height)
  - Responses:
    - `200 OK` with updated table and `status` (`PLAYING`, `DRAW`, or `WINNER`)
    - `400 Bad Request` on invalid input with an error message

Example: place PLAYER_1 in column 2 and continue playing

```
POST /connect4/placeToken
Content-Type: application/json

{
  "table": [[1], [2]],
  "player": 1,
  "column": 2,
  "row_size": 2
}
```

Response:
```
{
  "table": [[1], [2, 1]],
  "status": "PLAYING"
}
```

Example: result is a draw
```
POST /connect4/placeToken
{
  "table": [[1], [2, 1]],
  "player": 2,
  "column": 1,
  "row_size": 2
}
```
Response:
```
{
  "table": [[1, 2], [2, 1]],
  "status": "DRAW"
}
```

Example: result is a winner (vertical connect-4)
```
POST /connect4/placeToken
{
  "table": [[1, 1, 1], [2, 1]],
  "player": 1,
  "column": 1,
  "row_size": 4
}
```
Response:
```
{
  "table": [[1, 1, 1, 1], [2, 1]],
  "status": "WINNER",
  "winner": "PLAYER_1",
  "cells_of_connect4": [
    [[0, 0], [3, 0]]
  ]
}
```

Notes
- `cells_of_connect4` is a list of 2-point segments marking each connect-4 found. Points are `[row, column]`, 0-based; row `0` is the bottom row, column `0` is the leftmost column.
- The service enforces rules such as valid players, valid cell values, column capacity, and board height.

Errors (400 Bad Request)
- `"Invalid player"`
- `"Invalid cell value"`
- `"All columns length should be less than row size specified"`
- `"Column X is full"` (0-based column index in the message)

## Actuator Endpoints

Base path: `/public/actuator`
- Health: `GET /public/actuator/health` (returns `{"status":"UP"}` when healthy)
- Info: `GET /public/actuator/info` (includes project name/description/version from `pom.xml`)

## Build, Test, and Quality Gates

- Run unit tests: `./mvnw test`
- Full verify (tests + PMD + JaCoCo report): `./mvnw verify`
  - PMD runs at `verify` and fails on violations
  - Coverage report: `target/site/jacoco/index.html`
- Local run without packaging: `./mvnw spring-boot:run`

## Docker

Build image and run locally:
```
docker build -t connect4-java:local .
docker run --rm -p 8080:8080 connect4-java:local
```

Smoke check:
```
curl -fs http://localhost:8080/public/actuator/health
curl -fs http://localhost:8080/public/actuator/info
```

## Kubernetes

Manifests are in `k8s/`:
- Deployment: `k8s/deployment.yml` (container port 8080; readiness/liveness use actuator)
- Service: `k8s/service.yml` (`LoadBalancer` on port 8080)

Apply manifests and port-forward for local testing:
```
kubectl apply -f k8s/
kubectl wait --for=condition=available --timeout=90s deployment/connect4-java
kubectl port-forward service/connect4-java 8080:8080
```

By default, the deployment references `docker.io/biplopdey/connect4-java:latest`. You can point it to another image tag at runtime:
```
kubectl set image deployment/connect4-java connect4-java=docker.io/biplopdey/connect4-java:<TAG>
```

## CI/CD Workflows (GitHub Actions)

Reusable actions
- Maven setup: `.github/actions/maven-setup` (Java 17 + Maven cache)
- Smoke tests: `.github/actions/smoke-test` (health + version), `smoke-test-lite` (health only)

Pipelines
- Tests and PMD: `.github/workflows/test-ci.yml` (on changes to `src/**` or `pom.xml`)
- SonarQube: `.github/workflows/sonarqube.yml` (push to `master`, PRs)
  - Requires `SONAR_TOKEN` secret and project key configured in the workflow
- Publish to Docker Hub: `.github/workflows/publish-docker.yml` (manual)
  - Tags: `<pom.version>` and `latest`
  - Requires secrets: `DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN`
- Publish to AWS ECR: `.github/workflows/publish-ecr.yml` (manual)
  - Uses OIDC to assume role; pushes `<pom.version>` and `latest`
  - Requires vars: `AWS_REGION`, `ECR_REPOSITORY`, `AWS_ECR_ROLE_ARN`
- Deploy to EKS: `.github/workflows/deploy-eks.yml` (manual)
  - Applies `k8s/` and updates image to `docker.io/biplopdey/connect4-java:<version>`
  - Requires vars: `AWS_REGION`, `EKS_CLUSTER`; secrets: `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`
- Deploy to App Runner: `.github/workflows/deploy-app-runner.yml` (manual)
  - Deploys ECR image to App Runner
  - Requires vars: `AWS_REGION`, `ECR_REPOSITORY`; secrets: `ROLE_ARN`
- Local k8s smoke test: `.github/workflows/local-k8s-test.yml` (on every push, or manual with `version`)
  - Creates a kind cluster, applies `k8s/`, port-forwards, and runs `smoke-test-lite`

Versioning
- The pipelines extract the application version from `pom.xml` (the project `<version>`), and use it as the image tag.

## Project Structure

```
.
├─ src/main/java/com/minimax/minimax/...     # Spring Boot app and Connect4 domain
├─ src/main/resources/application.yml         # Actuator base-path and info
├─ k8s/deployment.yml, k8s/service.yml       # K8s manifests
├─ Dockerfile                                # Multi-stage build (Maven -> JRE)
├─ .github/actions/*                         # Composite actions
└─ .github/workflows/*                       # CI/CD pipelines
```

## Development Notes

- CORS: the Connect4 controller allows cross-origin calls for the API route.
- Columns are dynamic-length arrays up to `row_size`. The service rejects any column longer than `row_size`.
- The server listens on port `8080` by default.

## Example cURL

- Health:
```
curl -fs http://localhost:8080/public/actuator/health
```

- Place a token:
```
curl -sS -X POST http://localhost:8080/connect4/placeToken \
  -H 'Content-Type: application/json' \
  -d '{
        "table": [[1],[2],[],[]],
        "player": 1,
        "column": 3,
        "row_size": 6
      }' | jq .
```

## Troubleshooting

- Build failures due to PMD: run `./mvnw -DskipTests pmd:check` and inspect `target/pmd.xml`.
- Service not ready in Kubernetes: check readiness/liveness at `/public/actuator/health/readiness` and `/public/actuator/health/liveness`, describe pods, and view logs.
- Local k8s testing via GitHub Actions: use the `local-k8s-test` workflow and optionally supply an image `version`.
