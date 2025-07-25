# Connect Four :robot:

`Connect Four (also known as Four Up, Plot Four, Find Four, Captain's Mistress, Four in a Row, Drop Four, and Gravitrips in the Soviet Union) is a two-player connection board game, in which the players choose a color and then take turns dropping colored tokens into a seven-column, six-row vertically suspended grid. The pieces fall straight down, occupying the lowest available space within the column`[https://en.wikipedia.org/wiki/Connect_Four].

<img src="https://upload.wikimedia.org/wikipedia/commons/a/ad/Connect_Four.gif" width="400" height="250" />



## Kubernetes deployment

The `k8s` directory contains manifests to deploy the application on a Kubernetes cluster. Deploy the resources with:

```bash
kubectl apply -f k8s/
```

The manifests assume the container image is available in Amazon ECR. Update
`k8s/deployment.yaml` with your repository URI, for example
`123456789012.dkr.ecr.us-east-1.amazonaws.com/connect4-java:latest`. The
service is exposed on port `80`.

### Deploying to Amazon EKS

Once the ECR image is available, apply the manifests to your EKS cluster:

```bash
kubectl apply -f k8s/
```

A GitHub Actions workflow (`deploy-eks.yml`) is provided to automate this step.
