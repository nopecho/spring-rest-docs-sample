#!/bin/bash

function is_elastic_server_up() {
    local server_url=$1
    curl --silent --fail "$server_url"/_cluster/health && return 0 || return 1
}

function wait_for_elastic() {
    local server_url=$1
    while true; do
        if is_elastic_server_up "$server_url"; then
            echo "ES is running"
            break
        fi
        sleep 5
    done
}

function is_aws_server_up() {
    local server_url=$1
    curl --silent --fail "$server_url"/_localstack/health && return 0 || return 1
}

function wait_for_aws() {
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

wait_for_elastic $LOCAL_HOST$ES_PORT
wait_for_aws $LOCAL_HOST$AWS_PORT

echo "Both servers are running. Exiting."
