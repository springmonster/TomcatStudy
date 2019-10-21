package ex01.pyrmont;

import java.io.IOException;
import java.io.InputStream;

public class Request {
	private InputStream inputStream;
	private String uri;

	public Request(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void parse() {
		StringBuffer sb = new StringBuffer();
		int i;
		byte[] buffer = new byte[2048];
		try {
			i = inputStream.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			i = -1;
		}

		for (int j = 0; j < i; j++) {
			sb.append((char) buffer[j]);
		}
		System.out.println(sb.toString());
		uri = parseUri(sb.toString());
	}

	private String parseUri(String requestString) {
		int index1, index2;
		index1 = requestString.indexOf(' ');
		if (index1 != -1) {
			index2 = requestString.indexOf(' ', index1 + 1);
			if (index2 > index1)
				return requestString.substring(index1 + 1, index2);
		}
		return null;
	}

	public String getUri() {
		return uri;
	}
}
