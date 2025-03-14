package pt.tecnico.supplier;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

import com.google.type.Money;

import io.grpc.stub.StreamObserver;
import pt.tecnico.supplier.domain.Supplier;
import pt.tecnico.supplier.grpc.Product;
import pt.tecnico.supplier.grpc.ProductsRequest;
import pt.tecnico.supplier.grpc.ProductsResponse;
import pt.tecnico.supplier.grpc.SupplierGrpc;
import pt.tecnico.supplier.grpc.SignedResponse;
import pt.tecnico.supplier.grpc.Signature;

import javax.crypto.spec.SecretKeySpec;

import com.google.protobuf.ByteString;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

public class SupplierServiceImpl extends SupplierGrpc.SupplierImplBase {

	/**
	 * Set flag to true to print debug messages. The flag can be set using the
	 * -Ddebug command line option.
	 */
	private static final boolean DEBUG_FLAG = (System.getProperty("debug") != null);

	private static final String DIGEST_ALGO = "SHA-256";
	private static final String SYM_CIPHER = "RSA/ECB/PKCS1Padding";
	private static final String KEY_PATH = "secret.key";
	private static final String PRIV_KEY_PATH = "priv.key";

	/** Helper method to print debug messages. */
	private static void debug(String debugMessage) {
		if (DEBUG_FLAG)
			System.err.println(debugMessage);
	}

	/** Domain object. */
	final private Supplier supplier = Supplier.getInstance();

	/** Constructor */
	public SupplierServiceImpl() {
		debug("Loading demo data...");
		supplier.demoData();
	}

	/** Helper method to convert domain product to message product. */
	private Product buildProductFromProduct(pt.tecnico.supplier.domain.Product p) {
		Product.Builder productBuilder = Product.newBuilder();
		productBuilder.setIdentifier(p.getId());
		productBuilder.setDescription(p.getDescription());
		productBuilder.setQuantity(p.getQuantity());
		productBuilder.setDiscount(0.2f);

		Money.Builder moneyBuilder = Money.newBuilder();
		moneyBuilder.setCurrencyCode("EUR").setUnits(p.getPrice());
		productBuilder.setPrice(moneyBuilder.build());

		return productBuilder.build();
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

	public static PrivateKey readRSAKey(String resourcePathName) throws Exception {
		System.out.println("Reading key from resource " + resourcePathName + " ...");

		InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePathName);
		byte[] encoded = new byte[fis.available()];
		fis.read(encoded);
		fis.close();

		System.out.println("Key:");
		System.out.println(printHexBinary(encoded));
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encoded);

		// Use a KeyFactory to generate a PrivateKey from the key specification
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		return privateKey;
	}

	@Override
	public void listProducts(ProductsRequest request, StreamObserver<SignedResponse> responseObserver) {
		try{
			debug("listProducts called");

			debug("Received request:");
			debug(request.toString());
			debug("in binary hexadecimals:");
			byte[] requestBinary = request.toByteArray();
			debug(String.format("%d bytes%n", requestBinary.length));

			// build response
			ProductsResponse.Builder responseBuilder = ProductsResponse.newBuilder();
			responseBuilder.setSupplierIdentifier(supplier.getId());
			for (String pid : supplier.getProductsIDs()) {
				pt.tecnico.supplier.domain.Product p = supplier.getProduct(pid);
				Product product = buildProductFromProduct(p);
				responseBuilder.addProduct(product);
			}
			ProductsResponse response = responseBuilder.build();

			// start building signature
			Signature.Builder signatureBuilder = Signature.newBuilder();
			
			// digest data
			MessageDigest messageDigest = MessageDigest.getInstance(DIGEST_ALGO);
			byte[] responseBytes = response.toByteArray();
			messageDigest.update(responseBytes);
			byte[] digest = messageDigest.digest();	

			// encrypt digest
			Cipher cipher = Cipher.getInstance(SYM_CIPHER);
			cipher.init(Cipher.ENCRYPT_MODE, readRSAKey(PRIV_KEY_PATH));
			byte[] cipherBytes = cipher.doFinal(digest);

			// build signature
			signatureBuilder.setValue(ByteString.copyFrom(cipherBytes));
			signatureBuilder.setSignerId(supplier.getId());
			signatureBuilder.setTimestamp(System.currentTimeMillis());
			Signature signature = signatureBuilder.build();

			// build signed response
			SignedResponse.Builder signedResponseBuilder = SignedResponse.newBuilder();

			//------------------------- Validity test-------------------------
			// ProductsResponse.Builder modifiedProducts = response.toBuilder();
			// modifiedProducts.setSupplierIdentifier("modifiedID");
			//------------------------- Validity test-------------------------
			
			signedResponseBuilder.setResponse(response);
			signedResponseBuilder.setSignature(signature);

			SignedResponse signedResponse = signedResponseBuilder.build();

			debug("Response to send:");
			debug(response.toString());
			debug("in binary hexadecimals:");
			byte[] responseBinary = response.toByteArray();
			debug(printHexBinary(responseBinary));
			debug(String.format("%d bytes%n", responseBinary.length));

			// send single response back
			responseObserver.onNext(signedResponse);
			// complete call
			responseObserver.onCompleted();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
