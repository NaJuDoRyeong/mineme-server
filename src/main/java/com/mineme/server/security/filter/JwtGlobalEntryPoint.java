package com.mineme.server.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.mineme.server.common.enums.ErrorCode;

@Component("jwtGlobalEntryPoint")
public class JwtGlobalEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
		Integer exception = (Integer)request.getAttribute("exception");

		if (exception == null) {
			setResponse(response, ErrorCode.INVALID_REQUEST);
		} else if (exception.equals(ErrorCode.EMPTY_TOKEN_CLAIMS.getCode())) {
			setResponse(response, ErrorCode.EMPTY_TOKEN_CLAIMS);
		} else if (exception.equals(ErrorCode.INVALID_TOKEN_SIGNATURE.getCode())) {
			setResponse(response, ErrorCode.INVALID_TOKEN_SIGNATURE);
		} else if (exception.equals(ErrorCode.INVALID_TOKEN.getCode())) {
			setResponse(response, ErrorCode.INVALID_TOKEN);
		} else if (exception.equals(ErrorCode.TOKEN_EXPIRED.getCode())) {
			setResponse(response, ErrorCode.TOKEN_EXPIRED);
		} else if (exception.equals(ErrorCode.UNSUPPORTED_TOKEN.getCode())) {
			setResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
		} else if (exception.equals(ErrorCode.INVALID_TOKEN_FORMAT.getCode())) {
			setResponse(response, ErrorCode.INVALID_TOKEN_FORMAT);
		} else {
			setResponse(response, ErrorCode.INVALID_TOKEN);
		}
	}

	private void setResponse(HttpServletResponse response, ErrorCode code) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");

		JSONObject responseJson = new JSONObject();
		JSONObject errorJson = new JSONObject();
		errorJson.put("code", code.getCode());
		errorJson.put("message", code.getMessage());

		responseJson.put("success", false);
		responseJson.put("data", null);
		responseJson.put("error", errorJson);

		response.getWriter().print(responseJson);
	}
}