package com.kevinAri.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class ResponseHelper {
	@Value("#{${message-code-wsdl}}")
	Map<String, String> messageCodeWsdl;

	@Autowired
    ObjectMapper objectMapper;

	public void setResponseMessage (String messageCode, String errorMessage, Object responseObject) {
		try {
			String[] responseCodeMsg = messageCodeWsdl.get(messageCode).split("\\|");

			Map<String, String> errorSchema = new HashMap<>();
			errorSchema.put("errorCode", responseCodeMsg[0]);
			if (errorMessage!=null) errorSchema.put("errorMessage", errorMessage);
			else errorSchema.put("errorMessage", responseCodeMsg[1]);

			Map<String, Object> responseSchema = new HashMap<>();
			responseSchema.put("errorSchema", errorSchema);

			objectMapper.updateValue(responseObject, responseSchema);
		} catch (Exception ignored) {
		}
	}
}
