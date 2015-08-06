package cn.ichano.common.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;

import com.amazonaws.DefaultRequest;
import com.amazonaws.HttpMethod;
import com.amazonaws.Request;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.internal.BucketNameUtils;
import com.amazonaws.services.s3.internal.Constants;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import cn.ichano.common.config.Env;

public class AwsUtil {

	private static final Map<String, String> bucketRogin = new ConcurrentHashMap<String, String>();

	public static String getRoginByBucketString(String server, String bucket) {
		String key = server + "," + bucket;
		String region = bucketRogin.get(key);
		if (StringUtils.isEmpty(region)) {
			// 中国s3 region域名与美国不同
			if (server != null && server.indexOf(".cn") != -1) {
				region = "cn-north-1";
				bucketRogin.put(key, region);
				return region;
			}
			AmazonS3Client s3 = getS3Client();
			com.amazonaws.services.s3.model.Region regi0n = s3.getRegion();
			region = regi0n.name();
			bucketRogin.put(bucket, region);
		}
		return region;
	}

	public static Region getRogionByBucket(String bucket) {
		String server = Env.getConfig().getValue("aws.defautlServer");
		String region = getRoginByBucketString(server, bucket);
		return RegionUtils.getRegion(region);
	}

	/**
	 * 查询头部
	 * 
	 * @param bucket
	 * @param key
	 * @return
	 */
	public static Map<String, String> getHeaderSign(String bucket, String key) {
		Map<String, String> attchSingParams = new HashMap<String, String>();
		attchSingParams
				.put("x-amz-content-sha256",
						"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");

		return getHeaderSign(bucket, key, attchSingParams);

	}

	/**
	 * 查询s3 url中参数请求的url
	 * 
	 * @param bucket
	 * @param key
	 * @return
	 */
	public static String getUrlSign(String bucket, String key) {

		Date date = new Date();
		date = org.apache.commons.lang3.time.DateUtils.addSeconds(date, Env
				.getConfig().getInteger("aws.expire", 86400));
		return getS3Client().generatePresignedUrl(bucket, key, date,
				HttpMethod.PUT).toString();
		// Region region = getRogionByBucket(bucket);
		//
		// GetObjectRequest getObjectRequest = new GetObjectRequest(bucket,
		// key);
		// Request<GetObjectRequest> request = createRequest(
		// getObjectRequest.getBucketName(), getObjectRequest.getKey(),
		// getObjectRequest, HttpMethodName.GET);
		//
		// AWS4Signer s4 = new AWS4Signer();
		// s4.setRegionName(region.getName());
		// s4.setServiceName("s3");
		//
		// String awsKey = Env.getConfig().getValue("aws.key");
		// String awsSecret = Env.getConfig().getValue("aws.secret");
		// s4.presignRequest(request, new BasicAWSCredentials(awsKey,
		// awsSecret),
		// TimeUtil.getDateEnd(new Date()));
		//
		// return request.getParameters();

	}

	/**
	 * 查询头部
	 * 
	 * @param bucket
	 * @param key
	 * @return
	 */
	public static Map<String, String> getHeaderSign(String bucket, String key,
			Map<String, String> attchSingParams) {

		Region region = getRogionByBucket(bucket);

		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
		Request<GetObjectRequest> request = createRequest(
				getObjectRequest.getBucketName(), getObjectRequest.getKey(),
				getObjectRequest, HttpMethodName.GET);
		// request.addHeader("Content-Type","text/plain");
		// request.addHeader("User-Agent",
		// "aws-sdk-java/1.9.11 Windows_7/6.1 Java_HotSpot(TM)_64-Bit_Server_VM/24.71-b01/1.7.0_71");

		// request.addHeader("x-amz-content-sha256",
		// "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
		if (attchSingParams != null) {
			Set<Entry<String, String>> sets = attchSingParams.entrySet();
			for (Entry<String, String> entry : sets) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}
		AWS4Signer s4 = new AWS4Signer();
		s4.setRegionName(region.getName());
		s4.setServiceName("s3");
		s4.sign(request,
				new BasicAWSCredentials(Env.getConfig().getValue("aws.key"),
						Env.getConfig().getValue("aws.secret")));

		return request.getHeaders();
	}

	private static void configRequest(Request<?> request, String bucketName,
			String key) throws URISyntaxException {
		Region region = getRogionByBucket(bucketName);
		String url = region.getServiceEndpoint("s3");
		System.out.println("url:" + url);
		if (!new S3ClientOptions().isPathStyleAccess()
				&& BucketNameUtils.isDNSBucketName(bucketName) && !validIP(url)) {
			request.setEndpoint(new URI("https://" + bucketName + "." + url));
			/*
			 * If the key name starts with a slash character, in order to
			 * prevent it being treated as a path delimiter, we need to add
			 * another slash before the key name. {@see
			 * com.amazonaws.http.HttpRequestFactory#createHttpRequest}
			 */
			if (key != null && key.startsWith("/")) {
				// key = "/" + key;
			}
			request.setResourcePath(key);
		} else {
			request.setEndpoint(new URI("https://" + url));

			if (bucketName != null) {
				request.setResourcePath(bucketName + "/"
						+ (key != null ? key : ""));
			}
		}
	}

	private static boolean validIP(String IP) {
		if (IP == null) {
			return false;
		}
		String[] tokens = IP.split("\\.");
		if (tokens.length != 4) {
			return false;
		}
		for (String token : tokens) {
			int tokenInt;
			try {
				tokenInt = Integer.parseInt(token);
			} catch (NumberFormatException ase) {
				return false;
			}
			if (tokenInt < 0 || tokenInt > 255) {
				return false;
			}

		}
		return true;
	}

	public static AmazonS3Client getS3Client() {
		AWSCredentials credentials;
		credentials = new BasicAWSCredentials(Env.getConfig().getValue(
				"aws.key"), Env.getConfig().getValue("aws.secret"));

		AmazonS3Client s3 = new AmazonS3Client(credentials);
		s3.setEndpoint(Env.getConfig().getValue("aws.defautlServer"));
		return s3;
	}

	private static Request<GetObjectRequest> createRequest(String bucketName,
			String key, GetObjectRequest originalRequest,
			HttpMethodName httpMethod) {
		Request<GetObjectRequest> request = new DefaultRequest<GetObjectRequest>(
				originalRequest, Constants.S3_SERVICE_NAME);
		request.setHttpMethod(httpMethod);
		try {
			configRequest(request, bucketName, key);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return request;
	}

	/**
	 * 计算签名
	 * 
	 * @param httpVerb
	 * @param contentMD5
	 * @param contentType
	 * @param date
	 * @param resource
	 * @param metas
	 * @return
	 */
	public static String sign(String httpVerb, String contentMD5,
			String contentType, String date, String resource,
			Map<String, String> metas) {

		String stringToSign = httpVerb + "\n"
				+ StringUtils.trimToEmpty(contentMD5) + "\n"
				+ StringUtils.trimToEmpty(contentType) + "\n" + date + "\n";
		if (metas != null) {
			for (Map.Entry<String, String> entity : metas.entrySet()) {
				stringToSign += StringUtils.trimToEmpty(entity.getKey()) + ":"
						+ StringUtils.trimToEmpty(entity.getValue()) + "\n";
			}
		}
		stringToSign += resource;
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			String accessKey = Env.getConfig().getValue("aws.key");
			byte[] keyBytes = accessKey.getBytes("UTF8");
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
			mac.init(signingKey);
			byte[] signBytes = mac.doFinal(stringToSign.getBytes("UTF8"));
			String signature = encodeBase64(signBytes);
			return "AWS" + " " + accessKey + ":" + signature;
		} catch (Exception e) {
			throw new RuntimeException("MAC CALC FAILED.");
		}
	}

	private static String encodeBase64(byte[] data) {
		String base64 = new String(Base64.encodeBase64(data));
		if (base64.endsWith("\r\n"))
			base64 = base64.substring(0, base64.length() - 2);
		if (base64.endsWith("\n"))
			base64 = base64.substring(0, base64.length() - 1);

		return base64;
	}

	/**
	 * 删除aws指定日期前的数据
	 * 
	 * @param date
	 */
	public static void awsRemoveDate(String date) {

		String path = Env.getConfig().getValue("aws.video.prefix");

		AmazonS3Client s3client = AwsUtil.getS3Client();

		ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
				.withBucketName(Env.getConfig().getValue("aws.video.bucket"))
				.withPrefix(path.substring(1)).withDelimiter("/");
		ObjectListing objectListing;
		do {
			objectListing = s3client.listObjects(listObjectsRequest);
			delete(s3client, objectListing);
			listObjectsRequest.setMarker(objectListing.getNextMarker());
		} while (objectListing.isTruncated());
	}

	private static void delete(AmazonS3Client s3, ObjectListing preview) {
		DeleteObjectsRequest request = new DeleteObjectsRequest(Env.getConfig()
				.getValue("aws.video.bucket"));
		List<KeyVersion> keys = new ArrayList<KeyVersion>();

		List<S3ObjectSummary> s3ObjectList = preview.getObjectSummaries();
		for (S3ObjectSummary summary : s3ObjectList) {
			keys.add(new KeyVersion(summary.getKey()));
		}
		request.setKeys(keys);
		s3.deleteObjects(request);
	}

	/**
	 * 批量s3中 key删除
	 * 
	 * @param keyList
	 */
	public static void deleteObjects(List<String> keyList) {
		if (keyList != null && keyList.size() > 0) {
			AmazonS3Client s3 = getS3Client();
			DeleteObjectsRequest request = new DeleteObjectsRequest(Env
					.getConfig().getValue("aws.video.bucket"));
			List<KeyVersion> keys = new ArrayList<KeyVersion>();
			for (String key : keyList) {
				keys.add(new KeyVersion(key));
			}
			request.setKeys(keys);
			s3.deleteObjects(request);
		}
	}

	public static void createAndUpload() throws IOException {
		// AwsUtil.awsRemoveDate("20150116");
		String bucket = Env.getConfig().getValue("aws.video.bucket");
		java.util.Date expiration = new java.util.Date();
		long msec = expiration.getTime();
		msec += 1000 * 60 * 60; // Add 1 hour.
		expiration.setTime(msec);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				bucket, "free/abc.txt");
		generatePresignedUrlRequest.setMethod(HttpMethod.PUT);
		generatePresignedUrlRequest.setExpiration(expiration);

		AmazonS3Client s3client = AwsUtil.getS3Client();
		URL s = s3client.generatePresignedUrl(generatePresignedUrlRequest);

		System.out.println(s.toString());

		uploadUrl(s);

		generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
		generatePresignedUrlRequest.setExpiration(expiration);

		s = s3client.generatePresignedUrl(generatePresignedUrlRequest);

		System.out.println("get url:" + s.toString());
	}

	private static void uploadUrl(URL url) throws IOException {

		// URL url2 = new URL(url.toString());
		// HttpURLConnection connection = (HttpURLConnection)
		// url.openConnection();
		// connection.setDoOutput(true);
		// connection.setRequestMethod("PUT");
		// OutputStreamWriter out = new OutputStreamWriter(
		// connection.getOutputStream());
		// out.write("This text uploaded as object11111111111111111222222222222222222222222222222.");
		// out.flush();
		//
		// System.out.println("toString:" + connection.toString());
		// out.write("\nthis is chunked data.");
		// out.close();
		// int responseCode = connection.getResponseCode();
		// System.out.println("Service returned response code " + responseCode);

		HttpPut method = new HttpPut(url.toString());
		// method.addHeader("Transfer-Encoding", "chunked");
		// method.addHeader("chunk", "1024");
		byte[] bytes = new byte[204800];
		for (int i = 0; i < 204800; i++) {
			bytes[i] = 'a';
		}
		ByteArrayEntity entity = new ByteArrayEntity(bytes, ContentType.create(
				"application/text", Charset.forName("utf-8")));
		entity.setChunked(true);

		System.out.println("is chunked:" + entity.isChunked());
		// entity.
		method.setEntity(entity);

		HttpUtil.execute(method);
	}
}
