package nosi.core.webapp.webservices.rest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import nosi.core.config.Config;
import nosi.core.webapp.Core;

/**
 * @author Isaias.Nunes
 *
 */
public class ConsumeJson {
	public String getObjectFromJson(String url, String json_data) throws IOException {

		byte[] postData = json_data.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		URL url_request = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) url_request.openConnection();
		
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
		conn.setUseCaches(false);

		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			wr.write(postData);
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

		StringBuffer response = new StringBuffer();
		in.lines().forEach(response::append);
		in.close();
		conn.disconnect();
		//System.out.println("response getObjectFromJson: " + response.toString());
		return response.toString();
	}

	public String getJsonFromUrl(String url, String authorization) throws IOException {
		try {
			URL url_request = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) url_request.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestProperty("Authorization", authorization);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "application/json");
			conn.setRequestProperty("charset", "utf-8");
			// conn.setUseCaches( false );
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuffer response = new StringBuffer();
			in.lines().forEach(response::append);
			in.close();
			conn.disconnect();
			return response.toString();
		} catch (Exception e) {
			String eString = e.toString();
			if(!new Config().getEnvironment().equals("dev"))
				System.out.println("Erro getJsonFromUrl: "+eString);
			else {				
				Core.setMessageError("Erro: " + eString);
			}
			return null;
		}
	}
}
