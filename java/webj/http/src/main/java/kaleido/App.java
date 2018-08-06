package kaleido;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.exceptions.ClientConnectionException;
import org.web3j.protocol.http.HttpService;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public final class App {
	// Fill these in to test
	private static final String USER = "";
	private static final String PASS = "";
	private static final String RPC_ENDPOINT = ""; // With https://

	public static void main(String[] args) {
		try {
			// Build an Authorixation header using your app credentials
			OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
			clientBuilder.authenticator(new Authenticator() {
			    @Override public Request authenticate(Route route, Response response) throws IOException {
			        String credential = Credentials.basic(USER, PASS);
			        return response.request().newBuilder().header("Authorization", credential).build();
			    }
			});
			// Create a Service object for web3 to connect to
			HttpService service = new HttpService(RPC_ENDPOINT, clientBuilder.build(), false);
			Web3j web3 = Web3j.build(service);

			// Get the latest block in the chain
			EthBlock.Block latestBlock = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();

			// Print out the number of the latest block
			System.out.println("Latest Block: #" + latestBlock.getNumber());

		} catch (IOException | ClientConnectionException ex) {
			// Catch any Connection errors and print them out
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
