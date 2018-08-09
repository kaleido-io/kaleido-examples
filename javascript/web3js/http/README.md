# Web3.js - HTTP Provider Example

An example script on Kaleido using [Web3.js](https://github.com/ethereum/web3.js "Web3.js Github"). This demonstrates how to connect to a Kaleido node using basic auth via HTTP and retrieve the latest block on the chain.

## Requirements
1. [Node & NPM](https://nodejs.org/en/)

## Steps
1. In web3js/http/index.js:
    + Replace the `@RPC_ENDPOINT_NO_PRE@` placeholder with the RPC endpoint for your node.  Note that this string must preclude the prefacing `https://`.
    + Replace the `@USER@` placeholder with the basic auth credentials username.
    + Replace the `@PASS@` placeholder with the basic auth credentials password.
2. Save the file and from the ethers/http subdirectory run `npm install` to install the node modules for the project.
3. From the same directory, run `node index.js` to drive the program.
