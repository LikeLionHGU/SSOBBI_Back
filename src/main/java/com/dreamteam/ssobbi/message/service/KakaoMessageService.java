package com.dreamteam.ssobbi.message.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoMessageService {

	@Value("${kakao.api.key.client}")
	private String apiKey;

	public void sendKakaoMessage(String recipient, String message) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Bearer " + apiKey);

		Map<String, Object> templateObject = new HashMap<>();
		templateObject.put("object_type", "text");
		templateObject.put("text", message);
		templateObject.put("link", Map.of("web_url", "http://localhost:3000"));

		Map<String, Object> body = new HashMap<>();
		body.put("template_object", templateObject);

		restTemplate.postForObject(url, body, String.class, headers);
	}

}
