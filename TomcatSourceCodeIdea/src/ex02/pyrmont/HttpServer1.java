package ex02.pyrmont;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer1 {

	/**
	 * WEB_ROOT is the directory where our HTML and other files reside. For this
	 * package, WEB_ROOT is the "webroot" directory under the working directory. The
	 * working directory is the location in the file system from where the java
	 * command was invoked.
	 */
//	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

	// shutdown command
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	// the shutdown command received
	private boolean shutdown = false;

	public static void main(String[] args) {
		HttpServer1 httpServer = new HttpServer1();
		httpServer.await();
	}

	public void await() {
		ServerSocket serverSocket = null;
		int port = 10001;

		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

		while (!shutdown) {
			Socket socket = null;
			InputStream inputStream = null;
			OutputStream outputStream = null;

			try {
				socket = serverSocket.accept();
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();

				// create Request Object
				Request request = new Request(inputStream);
				request.parse();

				// create Response Object
				Response response = new Response(outputStream);
				response.setRequest(request);

				// check if is a static request
				if (request.getUri().startsWith("/servlet/")) {
					ServletProcessor1 processor = new ServletProcessor1();
					processor.process(request, response);
				} else {
					StaticResourceProcessor processor = new StaticResourceProcessor();
					processor.process(request, response);
				}
				socket.close();

				// Shutdown server
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
	}
}
