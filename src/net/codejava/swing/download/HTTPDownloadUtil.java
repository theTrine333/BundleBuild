package net.codejava.swing.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.cod3r.Tools;

/**
 * A utility that downloads a file from a URL.
 * 
 * @author www.codejava.net
 * 
 */
public class HTTPDownloadUtil {

	private HttpURLConnection httpConn;

	/**
	 * hold input stream of HttpURLConnection
	 */
	private InputStream inputStream;

	private String fileName;
	private int contentLength;

	/**
	 * Downloads a file from a URL
	 * 
	 * @param fileURL
	 *            HTTP URL of the file to be downloaded
	 * @throws IOException
	 */
	public void downloadFile(String fileURL) throws IOException {
		URL url = new URL(fileURL);
		httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.lastIndexOf('=')+1;
				if (index > 0) {
					fileName = disposition.substring(index,
							disposition.length());
				}
			} else {
				// extracts file name from URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/"),
						fileURL.length());
			}

			// output for debugging purpose only
			
			Tools.setMessage("Content-Type = " + contentType);
			Tools.setMessage("Content-Disposition = " + disposition);
			Tools.setMessage("Content-Length = " + contentLength);
			Tools.setMessage("fileName = " + fileName);

			// opens input stream from the HTTP connection
			inputStream = httpConn.getInputStream();

		} else {
			throw new IOException(
					"No file to download. Server replied HTTP code: "
							+ responseCode);
		}
	}

	public void disconnect() throws IOException {
		inputStream.close();
		httpConn.disconnect();
	}

	public String getFileName() {
		return this.fileName;
	}

	public int getContentLength() {
		return this.contentLength;
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}
}