const Web3 = require('web3');

// Fill these in to test, ex. remove @WSS_ENDPOINT@
let USER = "@USER@";
let PASS = "@PASS@";
let WSS_ENDPOINT = "@WSS_ENDPOINT_NO_PRE@"; // Remove the leading wss://


// Web Socket Example
// NOTE: Basic Auth support for websockets was added in 1.0.0-beta.35, prior versions do not support basic auth

let nodeUrl = "wss://" + USER + ":" + PASS + "@" + WSS_ENDPOINT;

let provider = new Web3.providers.WebsocketProvider(nodeUrl);
let web3 = new Web3(provider);

// Now you can call web3 functions, so we'll just test the connection by getting the latest block in the chain.

web3.eth.getBlock("latest").then((latestBlock) => {
    console.log("Latest Block Via Websocket Provider: ");
    console.log(latestBlock);
    // Stop the program once this has finished
    process.exit();
});