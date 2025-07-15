# Connect Four :robot:

`Connect Four (also known as Four Up, Plot Four, Find Four, Captain's Mistress, Four in a Row, Drop Four, and Gravitrips in the Soviet Union) is a two-player connection board game, in which the players choose a color and then take turns dropping colored tokens into a seven-column, six-row vertically suspended grid. The pieces fall straight down, occupying the lowest available space within the column`[https://en.wikipedia.org/wiki/Connect_Four].

<img src="https://upload.wikimedia.org/wikipedia/commons/a/ad/Connect_Four.gif" width="400" height="250" />

## Deploying to AWS App Runner

The project is built into a Docker image via the `Dockerfile` and the
`publish-docker.yml` workflow. Once the image is available in a container
registry (Docker Hub or Amazon ECR) you can run it on AWS App Runner.

1. Open the AWS console and navigate to **App Runner**.
2. Choose **Create service** and select **Container registry** as the source.
3. Enter the image URL, for example `docker.io/your-user/connect4-java:latest`,
   and keep the default port `8080`.
4. Create the service. App Runner will pull the container and expose a public
   URL where the application can be accessed.

The health check endpoint `/isHealthy` can be used to verify the service after
deployment. The provided workflow automatically checks this endpoint once the
container has been deployed.

### Using GitHub Actions

Instead of creating the service manually, you can trigger the
`deploy-app-runner.yml` workflow from the **Actions** tab. The workflow
authenticates with AWS using the secrets defined in your repository and deploys
the `latest` Docker image from Docker&nbsp;Hub. Configure `AWS_ACCESS_KEY_ID`,
`AWS_SECRET_ACCESS_KEY`, `AWS_REGION`, `APP_RUNNER_ROLE_ARN`, and
`APP_RUNNER_SERVICE_ROLE_ARN` as repository secrets. Triggering the workflow
will redeploy the service whenever a new image is published.

