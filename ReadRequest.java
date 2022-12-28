import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class ReadRequest {


	private final static int LISTENING_PORT = 8080;


	public static void main(String[] args) {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(LISTENING_PORT);
		}
		catch (Exception e) {
			System.out.println("Failed to create listening socket.");
			return;
		}
		System.out.println("Listening on port " + LISTENING_PORT);
		try {
			while (true) {
				Socket connection = serverSocket.accept();
				System.out.println("\nConnection from " 
						+ connection.getRemoteSocketAddress());
				ConnectionThread thread = new ConnectionThread(connection);
				thread.start();
			}
		}
		catch (Exception e) {
			System.out.println("Server socket shut down unexpectedly!");
			System.out.println("Error: " + e);
			System.out.println("Exiting.");
		}
	}


	private static void handleConnection(Socket connection) throws IOException {
		try {
			Scanner in = new Scanner(connection.getInputStream());
			PrintWriter out = new PrintWriter (connection.getOutputStream());
			String rootDirectory = "D:\\ServerDirectory\\";
			String line = in.nextLine();
			
			if (line.toLowerCase().startsWith("get")) {
				String fileName = line.substring(5, line.lastIndexOf(" "));
				File file = new File(rootDirectory + fileName);
				if (!file.exists()) throw new IOException();
				out.print("HTTP/1.1 200 OK\r\n");
				out.print("Connection: close\r\n");
				out.print("Content-Type: " + getMimeType(file.getName()) + "\r\n");
				out.print("Content-Length: " + file.length() + "\r\n");
				out.print("\r\n");
				out.flush();
				sendFile(file, connection.getOutputStream());
			}
			else {
				throw new Exception();
			}
		}
		catch (IOException e){
			sendErrorResponse(404, connection.getOutputStream());
		}
		catch (Exception e) {
			sendErrorResponse(501, connection.getOutputStream());
		}
		finally {  // make SURE connection is closed before returning!
			try {
				connection.close();
			}
			catch (Exception e) {
			}
			System.out.println("Connection closed.");
		}
	}

	private static String getMimeType(String fileName) {
		int pos = fileName.lastIndexOf('.');
		if (pos < 0)  // no file extension in name
			return "x-application/x-unknown";
		String ext = fileName.substring(pos+1).toLowerCase();
		if (ext.equals("txt")) return "text/plain";
		else if (ext.equals("html")) return "text/html";
		else if (ext.equals("htm")) return "text/html";
		else if (ext.equals("css")) return "text/css";
		else if (ext.equals("js")) return "text/javascript";
		else if (ext.equals("java")) return "text/x-java";
		else if (ext.equals("jpeg")) return "image/jpeg";
		else if (ext.equals("jpg")) return "image/jpeg";
		else if (ext.equals("png")) return "image/png";
		else if (ext.equals("gif")) return "image/gif";
		else if (ext.equals("ico")) return "image/x-icon";
		else if (ext.equals("class")) return "application/java-vm";
		else if (ext.equals("jar")) return "application/java-archive";
		else if (ext.equals("zip")) return "application/zip";
		else if (ext.equals("xml")) return "application/xml";
		else if (ext.equals("xhtml")) return"application/xhtml+xml";
		else return "x-application/x-unknown";
		// Note:  x-application/x-unknown  is something made up;
		// it will probably make the browser offer to save the file.
	}

	private static void sendFile(File file, OutputStream socketOut) throws
	IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(file));
		OutputStream out = new BufferedOutputStream(socketOut);
		while (true) {
			int x = in.read(); // read one byte from file
			if (x < 0)
				break; // end of file reached
			out.write(x);  // write the byte to the socket
		}
		out.flush();
	}

	static void sendErrorResponse(int errorCode, OutputStream socketOut) {
		PrintWriter out = new PrintWriter (socketOut);
		if (errorCode == 404) {
			out.print("HTTP/1.1 404 Not Found\r\n");
			out.print("Connection: close\r\n");
			out.print("Content-Type: text/html\r\n");
			out.print("\r\n");
			out.print("<html><head><title>Error</title></head><body>\r\n"
					+ "<h2>Error: 404 Not Found</h2>\r\n"
					+ "<p>The resource that you requested does not exist on this server.</p>\r\n"
					+ "</body></html>\r\n");
			out.flush();
		}
		else if (errorCode == 501){
			out.print("HTTP/1.1 501 Not Implemented\r\n");
			out.print("Connection: close\r\n");
			out.print("Content-Type: text/html\r\n");
			out.print("\r\n");
			out.print("<html><head><title>Error</title></head><body>\r\n"
					+ "<h2>Error: 501 Not Implemented</h2>\r\n"
					+ "<p>The server is not able to implement this request</p>\r\n"
					+ "</body></html>\r\n");
			out.flush();
		}
	}

	private static class ConnectionThread extends Thread {
		Socket connection;
		ConnectionThread(Socket connection) {
			this.connection = connection;
		}
		public void run() {
			try {
				handleConnection(connection);
			} catch (IOException e) {
				return;
			}
		}
	}
}
