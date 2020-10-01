package kaleido;

import java.net.URI;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.exceptions.ClientConnectionException;
import org.web3j.protocol.websocket.WebSocketClient;
import org.web3j.protocol.websocket.WebSocketService;


public final class App {
	private static final String BASIC_AUTH = "@APP_CREDS@"; // Replace with your kaleido application credentials
	private static final String WS_ENDPOINT = "@WS_ENDPOINT@"; // With wss://
	public static void main(String[] args) {
		Logger appLogger = Logger.getLogger(App.class.getName());

		try {
			Map<String, String> headers = new HashMap<>();
			headers.put("Authorization", "Basic "+ Base64.getEncoder().encodeToString(BASIC_AUTH.getBytes()));
			final WebSocketClient webSocketClient = new WebSocketClient(new URI(WS_ENDPOINT), headers);
			final boolean includeRawResponses = true;
			final WebSocketService webSocketService = new WebSocketService(webSocketClient, includeRawResponses);
			Web3j web3j = Web3j.build(webSocketService);
			webSocketService.connect();
			// Request to get a version of an Ethereum client
			final Request<?, Web3ClientVersion> request = new Request<>(
					// Name of an RPC method to call
					"web3_clientVersion",
					// Parameters for the method. "web3_clientVersion" does not expect any
					Collections.<String>emptyList(),
					// Service that is used to send a request
					webSocketService,
					// Type of an RPC call to get an Ethereum client version
					Web3ClientVersion.class);

			// Send an asynchronous request via WebSocket protocol
			final CompletableFuture<Web3ClientVersion> reply = webSocketService.sendAsync(
					request,
					Web3ClientVersion.class);

			// Get result of the reply
			final Web3ClientVersion clientVersion = reply.get();
			appLogger.info("Web3 client version: "+clientVersion.getWeb3ClientVersion());

			web3j.web3ClientVersion().flowable().subscribe(x -> {
				String version = x.getWeb3ClientVersion();
    			appLogger.info("Web3 client version using flowable: " + version);
			});
		} catch (ClientConnectionException ex) {
			// Catch any Connection errors and print them out
			appLogger.log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			appLogger.log(Level.SEVERE, null, ex);
		}
	}
}

