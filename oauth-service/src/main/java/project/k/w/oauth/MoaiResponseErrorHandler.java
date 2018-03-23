package project.k.w.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MoaiResponseErrorHandler extends DefaultResponseErrorHandler {

	public static Logger logger =  LoggerFactory.getLogger(MoaiResponseErrorHandler.class);
	
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	protected byte[] getResponseBody(ClientHttpResponse response) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody()));) {

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			Error error = mapper.readValue(sb.toString(), Error.class);
			return error.toString().getBytes(StandardCharsets.UTF_8);
		} catch (IOException ex) {
			logger.warn("Error happened in ResponseErrorHandler", ex);
			// ignore
		}
		return new byte[0];
	}

}

class Error {
	String errorCode;
	String message;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		return this.errorCode + " " + this.message;
	}

}
