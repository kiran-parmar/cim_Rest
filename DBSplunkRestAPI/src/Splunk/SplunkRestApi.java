package Splunk;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

import com.splunk.HttpService;
import com.splunk.Index;
import com.splunk.SSLSecurityProtocol;
import com.splunk.Service;
import com.splunk.ServiceArgs;

public class SplunkRestApi {
	public static void main(String[] args) throws IOException {
		HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
		ServiceArgs loginArgs = new ServiceArgs();
		loginArgs.setUsername("admin");
		loginArgs.setPassword("admin");
		loginArgs.setHost("192.168.202.155");
		loginArgs.setPort(8088);

		Service service = Service.connect(loginArgs);
		Index index = service.getIndexes().get("deloittelaptop");
		Socket socket;
		try {
			socket = index.attach();
			OutputStream ostream = socket.getOutputStream();
			Writer writerOut = new OutputStreamWriter(ostream, "UTF8");

			// stream 10 events to Splunk
			for (int i = 0; i < 10; i++) {
				writerOut.write("Some data I want to stream to Splunk");
			}
			writerOut.flush();
			writerOut.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void publishToSplunk(String data) {
		HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
		ServiceArgs loginArgs = new ServiceArgs();
		loginArgs.setUsername("admin");
		loginArgs.setPassword("admin");
		loginArgs.setHost("192.168.202.155");
		loginArgs.setPort(8089);

		Service service = Service.connect(loginArgs);
		Index index = service.getIndexes().get("deloittelaptop");
		Socket socket;
		try {
			socket = index.attach();
			OutputStream ostream = socket.getOutputStream();
			Writer writerOut = new OutputStreamWriter(ostream, "UTF8");

			writerOut.write(data);
			
			writerOut.flush();
			writerOut.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
}
