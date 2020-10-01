# WebJ websocket Application
An example application on Kaleido using the web3 Java library. This demonstrates how to establish websocket connection to a Kaleido node using basic auth.

## Requirements

1. Java 1.8+
2. Maven

## Steps
1. In src/main/java/kaleido/App.java:
    + Replace the `@WS_ENDPOINT@` placeholder with the RPC endpoint for your node.
    + Replace the `@APP_CREDS@` placeholder with the basic auth credentials `username:passward`.
2. Navigate to the java/webj/ws/ subdirectory and run `mvn package` to install the dependencies and build the Java archive file.  This will output a JAR file - `kaleido-webj-ws-example-0.0.1-SNAPSHOT-jar-with-dependencies.jar` - into a newly created /target directory.
3. Remain in java/webj/http/ and run `java -jar target/kaleido-webj-ws-example-0.0.1-SNAPSHOT-jar-with-dependencies.jar` to kick off the program.
