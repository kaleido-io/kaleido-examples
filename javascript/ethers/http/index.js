const ethers = require('ethers');

// Fill these in to test, ex. remove @RPC_ENDPOINT@
let USER = "@USER@";
let PASS = "@PASS@";
let RPC_ENDPOINT = "@RPC_ENDPOINT@"; // With https://

// HTTP Provider Example
let url = {url: RPC_ENDPOINT, user: USER, password: PASS};

let provider = new ethers.providers.JsonRpcProvider(url);

// Now you can call web3 functions, so we'll just test the connection by getting the latest block in the chain.
provider.getBlock().then((latestBlock) => {
    console.log("Latest Block Via JSON RPC Provider: ")
    console.log(latestBlock);
    // Stop the program once this has finished
    process.exit();
});
