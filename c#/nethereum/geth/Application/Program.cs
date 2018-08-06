using Nethereum.JsonRpc.Client;
using Nethereum.Web3;
using System;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace Application
{
    class Program
    {
        // Fill these in to test, ex. remove @RPC_ENDPOINT@
        static string RPC_ENDPOINT = "@RPC_ENDPOINT@";
        static string USER = "@USER@";
        static string PASS = "@PASS@";

        static Nethereum.Hex.HexTypes.HexBigInteger latestBlockNumber;

        static void Main(string[] args)
        {
            // Call an async function to set the latest block number and Wait on it to return
            getLatestBlockNumber().Wait();

            // Print the latest block number once the getLatestBlockNumber() function returns
            System.Diagnostics.Debug.Write("Latest Block: #" + latestBlockNumber.Value + "\n");
        }

        static async Task getLatestBlockNumber()
        {
            // Encode App Credentials as <username>:<password>
            var byteArray = Encoding.ASCII.GetBytes(USER + ":" + PASS);
            AuthenticationHeaderValue auth = new AuthenticationHeaderValue("Basic", Convert.ToBase64String(byteArray));

            // Create the RPC Client to talk to the Kaleido RPC endpoint using web3
            IClient client = new RpcClient(new Uri(RPC_ENDPOINT), auth, null, null, null);
            var web3 = new Web3(client);

            //Now we can test the connection by calling some basic function
            // Get the latest block number in the chain
            latestBlockNumber = await web3.Eth.Blocks.GetBlockNumber.SendRequestAsync();

        }
    }
}
