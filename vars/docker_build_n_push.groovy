def call(String dockerfile_dir = "app", String docker_token = "", String docker_user = "", String build_number = "") {
    echo "Building docker image with tag ${build_number} for user ${docker_user}"
    dir(dockerfile_dir) {
        sh 'docker version'
        sh 'echo "${docker_token}" | docker login -u "${docker_user}" --password-stdin '
        sh 'docker build -t $DOCKER_USER/go-server:${build_number} ./server/ '
        sh 'docker push $DOCKER_USER/go-server:${build_number}'
    }
}