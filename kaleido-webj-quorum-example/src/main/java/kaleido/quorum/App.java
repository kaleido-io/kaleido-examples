package kaleido.quorum;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.web3j.protocol.exceptions.ClientConnectionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public final class App {
	private static final String USER = "XXXX";
	private static final String PASS = "YYYYY";
	private static final String RPC_ENDPOINT = "https://RPC_HOSTNAME";
	public static void main(String[] args) {

		try {
			OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
			clientBuilder.authenticator(new Authenticator() {
			    @Override public Request authenticate(Route route, Response response) throws IOException {
			        String credential = Credentials.basic(USER, PASS);
			        return response.request().newBuilder().header("Authorization", credential).build();
			    }
			});
			HttpService service = new HttpService(RPC_ENDPOINT, clientBuilder.build(), false);
			Quorum quorum = Quorum.build(service);

			String clientVersion = quorum.web3ClientVersion().send().getWeb3ClientVersion();

			System.out.println(clientVersion);

		} catch (IOException | ClientConnectionException ex) {

			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);

		}
	}

}
