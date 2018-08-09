# Web3.js - Websocket Provider Example

An example script on Kaleido using [Web3.js](https://github.com/ethereum/web3.js "Web3.js Github"). This demonstrates how to connect to a Kaleido node using basic auth via Websocket and retrieve the latest block on the chain.

## Requirements
1. [Node & NPM](https://nodejs.org/en/)

## Steps
1. In web3js/wss/index.js:
    + Replace the `@WSS_ENDPOINT_NO_PRE@` placeholder with the WSS endpoint for your node.  Note that this string must preclude the prefacing `wss://`.
    + Replace the `@USER@` placeholder with the basic auth credentials username.
    + Replace the `@PASS@` placeholder with the basic auth credentials password.
2. Save the file and from the ethers/wss subdirectory run `npm install` to install the node modules for the project.
3. From the same directory, run `node index.js` to drive the program.

## Walkthrough GIF

![Web3.js Example Gif](../Web3jsExample.gif "Web3.js Example GIF")
<a href="https://raw.githubusercontent.com/kaleido-io/kaleido-examples/master/javascript/web3js/Web3jsExample.gif">Open GIF</a>