#!/bin/bash
# See: https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_getblockbynumber

PAYLOAD='{"jsonrpc":"2.0","method":"eth_getBlockByNumber","params":["latest", true],"id":1}'
URL='@RPC_ENDPOINT@'
USER='@USER@'
PASS='@PASS@'
curl -X POST -H 'Content-Type: application/json' "$URL" --user $USER:$PASS --data "$PAYLOAD"
