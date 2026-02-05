# Start from official Go image
FROM golang:1.25.6

# Install Docker CLI + DinD prerequisites
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        ca-certificates \
        curl \
        git \
        lsb-release \
        gnupg \
        sudo \
        iptables \
        conntrack \
        unzip \
        bash \
        jq \
    && rm -rf /var/lib/apt/lists/*

# Install Docker CLI
RUN curl -fsSL https://download.docker.com/linux/static/stable/x86_64/docker-24.0.5.tgz | tar -xzC /usr/local/bin --strip-components=1 docker/docker

# Install Helm
RUN curl -fsSL https://get.helm.sh/helm-v3.12.3-linux-amd64.tar.gz | tar -xzC /tmp && \
    mv /tmp/linux-amd64/helm /usr/local/bin/helm && \
    chmod +x /usr/local/bin/helm

# Set up DinD (needed if you want to run dockerd inside container)
ENV DOCKER_HOST=tcp://0.0.0.0:2375
ENV DOCKER_TLS_CERTDIR=""

# Expose Docker socket if needed
EXPOSE 2375

# Set working directory
WORKDIR /workspace

# Default entrypoint to bash
ENTRYPOINT ["bash"]
