package com.shopme.admin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpServletResponse;

public class AbstractExporter {

	public void setResponseHeader(HttpServletResponse response , String contentType,
			String extension, String prefix) throws IOException{
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd_hh-mm-ss");
		String timeStamp = dateFormatter.format(new Date());
		String fileName = prefix + timeStamp + extension;
		
		response.setContentType(contentType);
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		response.setHeader(headerKey, headerValue);
		
	}
}
