#!/bin/sh

SQS_NAME='SAMPLE-SQS'

awslocal sqs create-queue \
--queue-name $SQS_NAME

echo "Init Localstack"