def go_test(String go_dir = "app") {
    echo "Running Go tests in directory: ${go_dir}"

    dir(go_dir) {
        sh 'go mod tidy'
        sh 'go test -v'
    }
}
