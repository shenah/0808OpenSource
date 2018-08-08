import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParsing {

	public static void main(String[] args) {
		// 스레드 만들기
		Thread th = new Thread() {
			public void run() {
				try {
					URL url = new URL("https://www.daum.net");
					HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
					con.setUseCaches(false);
					con.setConnectTimeout(30000);
					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

					StringBuilder sb = new StringBuilder();
					while (true) {
						String line = br.readLine();
						if (line == null)
							break;
						sb.append(line + "\n");
					}
					String html = sb.toString();
					sb = null;
					br.close();
					con.disconnect();

					// HTML 루트를 찾아줍니다.
					Document doc = Jsoup.parse(html);
					// h2 태그 전체를 가져옵니다.
					Elements ele = doc.getElementsByTag("h2");

					int i = 0;
					while (i < ele.size()) {
						Element element = ele.get(i);
						System.out.println(element.text());
						i = i + 1;
					}
					//id는 중복 없음
					Element e = doc.getElementById("daumIndex");
					System.out.println(e.text());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}

			}
		};
		th.start();

	}

}
