
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WeatherXML {

	public static void main(String[] args) {
		try {
			URL url = new URL("http://www.hani.co.kr/rss/international/" );
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setConnectTimeout(30000);
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder sb = new StringBuilder();
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				sb.append(line);
			}
			String xml = sb.toString();
			sb = null;
			//xml 문자열을 파싱할 수 있는 객체 생성
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			//파싱할 문자열을 스트림으로 변환
			InputStream is = new ByteArrayInputStream(xml.getBytes());
			//문자열 파싱 수행
			Document doc = builder.parse(is);
			//푸트 찾기
			Element root = doc.getDocumentElement();
			//System.out.println(root);
			NodeList list = root.getElementsByTagName("title");
			int i = 0;
			
			while(i<list.getLength()) {
				Node item = list.item(i);
				Node child = item.getFirstChild();
				String tmx = child.getNodeValue();
				
				System.out.println(tmx);
				Thread.sleep(1000);
				i = i + 1;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		

	}

}
