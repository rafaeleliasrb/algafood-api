package com.algaworks.algafoodapi.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.StreamUtils;

public class ResourceUtils {

	public static String getContentFromResource(String path) {
		try {
			InputStream resourceAsStream = ResourceUtils.class.getResourceAsStream(path);
			return StreamUtils.copyToString(resourceAsStream, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
