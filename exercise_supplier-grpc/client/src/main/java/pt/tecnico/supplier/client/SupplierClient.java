package pt.tecnico.supplier.client;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pt.tecnico.supplier.grpc.ProductsRequest;
import pt.tecnico.supplier.grpc.SupplierGrpc;
import pt.tecnico.supplier.grpc.SignedResponse;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;

import javax.crypto.spec.SecretKeySpec;

import java.io.InputStream;

public class SupplierClient {

	/**
	 * Set flag to true to print debug messages. The flag can be set using the
	 * -Ddebug command line option.
	 */
	private static final boolean DEBUG_FLAG = (System.getProperty("debug") != null);

	private static final String DIGEST_ALGO = "SHA-256";
	private static final String SYM_CIPHER = "RSA/ECB/PKCS1Padding";
	private static final String KEY_PATH = "secret.key";
	private static final String PUB_KEY_PATH = "pub.key";
	private static final Integer WAIT_TIME_SEC = 0;
	private static final Integer WAIT_TIME_TOL_MILLI = 500;

	/** Helper method to print debug messages. */
	private static void debug(String debugMessage) {
		if (DEBUG_FLAG)
			System.err.println(debugMessage);
	}

	public static SecretKeySpec readKey(String resourcePathName) throws Exception {
		System.out.println("Reading key from resource " + resourcePathName + " ...");
		
		InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePathName);
		byte[] encoded = new byte[fis.available()];
		fis.read(encoded);
		fis.close();
		
		System.out.println("Key:");
		System.out.println(printHexBinary(encoded));
		SecretKeySpec keySpec = new SecretKeySpec(encoded, "AES");
		
		return keySpec;
	}
	
	public static PublicKey readRSAKey(String resourcePathName) throws Exception {
		System.out.println("Reading key from resource " + resourcePathName + " ...");

		InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePathName);
		byte[] encoded = new byte[fis.available()];
		fis.read(encoded);
		fis.close();

		System.out.println("Key:");
		System.out.println(printHexBinary(encoded));
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encoded);

		// Use a KeyFactory to generate a PrivateKey from the key specification
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		return publicKey;
	}
	public static void main(String[] args) throws Exception {
		System.out.println(SupplierClient.class.getSimpleName() + " starting ...");

		// Receive and print arguments.
		System.out.printf("Received %d arguments%n", args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.printf("arg[%d] = %s%n", i, args[i]);
		}

		// Check arguments.
		if (args.length < 2) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s host port%n", SupplierClient.class.getName());
			return;
		}

		final String host = args[0];
		final int port = Integer.parseInt(args[1]);
		final String target = host + ":" + port;

		// Channel is the abstraction to connect to a service end-point.
		final ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();

		// Create a blocking stub for making synchronous remote calls.
		SupplierGrpc.SupplierBlockingStub stub = SupplierGrpc.newBlockingStub(channel);

		// Prepare request.
		ProductsRequest request = ProductsRequest.newBuilder().build();
		System.out.println("Request to send:");
		System.out.println(request.toString());
		debug("in binary hexadecimals:");
		byte[] requestBinary = request.toByteArray();
		debug(printHexBinary(requestBinary));
		debug(String.format("%d bytes%n", requestBinary.length));

		// Make the call using the stub.
		System.out.println("Remote call...");
		SignedResponse response = stub.listProducts(request);
		TimeUnit.SECONDS.sleep(WAIT_TIME_SEC);
		
		Cipher cipher = Cipher.getInstance(SYM_CIPHER);
		cipher.init(Cipher.DECRYPT_MODE, readRSAKey(PUB_KEY_PATH));
		byte[] newPlainBytes = cipher.doFinal(response.getSignature().getValue().toByteArray());

		// digest data
		MessageDigest messageDigest = MessageDigest.getInstance(DIGEST_ALGO);
		byte[] responseBytes = response.getResponse().toByteArray();
		messageDigest.update(responseBytes);
		byte[] digest = messageDigest.digest();


		// compare digests
		if (Arrays.equals(digest, newPlainBytes) && Math.abs(System.currentTimeMillis() - response.getSignature().getTimestamp()) < WAIT_TIME_TOL_MILLI)
			System.out.println("Signature is valid! Message accepted! :)");
		else
			System.out.println("Signature is invalid! Message rejected! :(");

		// Print response.
		System.out.println("Received response:");
		System.out.println(response.toString());
		debug("in binary hexadecimals:");
		byte[] responseBinary = response.toByteArray();
		debug(printHexBinary(responseBinary));
		debug(String.format("%d bytes%n", responseBinary.length));

		// A Channel should be shutdown before stopping the process.
		channel.shutdownNow();
	}

}
