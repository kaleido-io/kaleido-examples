#!/bin/bash
# See: https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_getblockbynumber

# Fill these in to test, ex. remove @RPC_ENDPOINT@
URL='@RPC_ENDPOINT@'
USER='@USER@'
PASS='@PASS@'

# Get the latest block in the chain
PAYLOAD='{"jsonrpc":"2.0","method":"eth_getBlockByNumber","params":["latest", true],"id":1}'

# Curl the endpoint with the data payload and the user credentials
curl -X POST -H 'Content-Type: application/json' "$URL" --user $USER:$PASS --data "$PAYLOAD"
