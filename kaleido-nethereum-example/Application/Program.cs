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
        // Fill these in to test
        static string RPC_ENDPOINT = "";
        static string USER = "";
        static string PASS = "";
        static string ACCOUNT_ADDRESS = "";

        static decimal balance;

        static void Main(string[] args)
        {
            // Call an async function to set the balance and Wait on it to return
            getEthBalance().Wait();

            // Print the balance once the getEthBalance() function returns
            System.Diagnostics.Debug.Write("Account Balance: " + balance + " ether \n");
        }

        static async Task getEthBalance() 
        {
            // Encode App Credentials as <username>:<password>
            var byteArray = Encoding.ASCII.GetBytes(USER + ":" + PASS);
            AuthenticationHeaderValue auth = new AuthenticationHeaderValue("Basic", Convert.ToBase64String(byteArray));

            // Create the RPC Client to talk to the Kaleido RPC endpoint using web3
            IClient client = new RpcClient(new Uri(RPC_ENDPOINT), auth, null, null, null);
            var web3 = new Web3(client);

            //Now we can test the connection by calling some basic function
            // Get the account balance of my account
            var some = await web3.Eth.GetBalance.SendRequestAsync(ACCOUNT_ADDRESS);

            // Set variable for use in other places
            balance = Web3.Convert.FromWei(some.Value);
        }
    }
}
