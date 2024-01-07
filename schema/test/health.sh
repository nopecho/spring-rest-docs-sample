#!/bin/sh

is_server_up() {
    # shellcheck disable=SC2039
    local server_url=$1
    if curl --silent --fail "$server_url"; then
        return 0
    else
        echo "Server is not running. please wait"
        return 1
    fi
}

health_check() {
    # shellcheck disable=SC2039
    local server_url=$1
    while true; do
        if is_server_up "$server_url"; then
            echo "Server is up"
            break
        fi
        sleep 3
    done
}

LOCAL_HOST="http://localhost:"
ES_PORT="9222"
AWS_PORT="4569"

ES_HOST=$LOCAL_HOST$ES_PORT
health_check "$ES_HOST/_cluster/health"

AWS_HOST=$LOCAL_HOST$AWS_PORT
health_check "$AWS_HOST/_localstack/health"

echo "servers health check ok."