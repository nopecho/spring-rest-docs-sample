#!/bin/sh

is_elastic_server_up() {
    # shellcheck disable=SC2039
    local server_url=$1
    if curl --silent --fail "$server_url"/_cluster/health; then
        return 0
    else
        return 1
    fi
}

wait_for_elastic() {
    # shellcheck disable=SC2039
    local server_url=$1
    while true; do
        if is_elastic_server_up "$server_url"; then
            echo "ES is running"
            break
        fi
        sleep 5
    done
}

is_aws_server_up() {
    local server_url=$1
    if curl --silent --fail "$server_url"/_localstack/health; then
        return 0
    else
        return 1
    fi
}

wait_for_aws() {
    local server_url=$1
    while true; do
        if is_aws_server_up "$server_url"; then
            echo "AWS is running"
            break
        fi
        sleep 5
    done
}

LOCAL_HOST="http://localhost:"
ES_PORT="9222"
AWS_PORT="4569"

wait_for_elastic "$LOCAL_HOST$ES_PORT"
wait_for_aws "$LOCAL_HOST$AWS_PORT"

echo "Both servers are running. Exiting."
