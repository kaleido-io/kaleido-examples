package kaleido;

import java.io.IOException;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.ClientConnectionException;
import org.web3j.protocol.http.HttpService;

import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

public final class App {
	// Fill these in to test, ex. remove @RPC_ENDPOINT@
	private static final String USER = "@USER@";
	private static final String PASS = "@PASS@";
	private static final String RPC_ENDPOINT = "@RPC_ENDPOINT@"; // With https://
	private static final BigInteger GAS_PRICE = BigInteger.valueOf(0); // connecting to zero gas price network
	private static final BigInteger GAS_LIMIT = BigInteger.valueOf(200000);

	public static void main(String[] args) {
		Logger appLogger = Logger.getLogger(App.class.getName());

		try {
			// Build an Authorization header using your app credentials
			OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
			clientBuilder.authenticator(new Authenticator() {
				@Override
				public Request authenticate(Route route, Response response) throws IOException {
					String credential = Credentials.basic(USER, PASS);
					return response.request().newBuilder().header("Authorization", credential).build();
				}
			});
			// Create a Service object for web3 to connect to
			HttpService service = new HttpService(RPC_ENDPOINT, clientBuilder.build(), false);
			Web3j web3 = Web3j.build(service);

			// To test the connection, get the latest block in the chain
			EthBlock.Block latestBlock = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();
			System.out.format("Connected to Node: %s, Latest Block: %s\n", RPC_ENDPOINT, latestBlock.getNumber().toString());

			// Get the default account on the node, we will use that as the `from` address for sending transactions
			EthAccounts accounts = web3.ethAccounts().send();
			if (accounts.getAccounts().size() < 1) {
				System.out.println("Could not find accounts on the target node, unable to deploy contract");
				System.exit(-1);
			}
			String fromAddress = accounts.getAccounts().get(0);
			System.out.format("Using Node Account %s for transactions\n", fromAddress);

			// Create a transaction manager which is supplied to the smart contract provider
			TransactionManager tm = new ClientTransactionManager(web3, fromAddress);

			// Deploy the simple storage contract, use gas price and limit values above
			System.out.format("Deploying Simple Storage contract..\n");
			StaticGasProvider gp = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
			SimpleStorage simpleStorage = SimpleStorage.deploy(web3, tm, gp).send();
			String ssContractAddress = simpleStorage.getContractAddress();
			System.out.format("Simple Storage contract address: %s\n", ssContractAddress);

			// Set a value in the contract
			BigInteger firstVal = new BigInteger("555");
			TransactionReceipt txReceipt = simpleStorage.set(firstVal).send();
			System.out.format("Set operation tx hash: %s\n", txReceipt.getTransactionHash());

			// Get the same value and make sure it's equal to what was set
			BigInteger getVal = simpleStorage.get().send();
			if (firstVal.compareTo(getVal) != 0) {
				System.out.format("Set value: [%s] does not equal value obtained from the contract: [%s]\n", firstVal.toString(), getVal.toString());
			} else {
				System.out.format("Verified that value from get: [%s] equals value set earlier\n", getVal.toString());
			}

		} catch (IOException | ClientConnectionException ex) {
			// Catch any Connection errors and print them out
			appLogger.log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			appLogger.log(Level.SEVERE, null, ex);
		}
	}
}

