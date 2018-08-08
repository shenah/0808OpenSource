
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParsing1 {

	public static void main(String[] args) {
		//위도와 경도를 대입해서 관광명소를 알려주는 다음 API 사용하기
		try {
			URL url = new URL(
					"https://apis.daum.net/local/v1/search/category.json?apikey=465b06fae32febacbc59502598dd7685&code=AT4&location=37.514322572335935,127.06283102249932&radius=20000");
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
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
			String json = sb.toString();
			//메모리, 스트림 정리 
			sb = null;
			br.close();
			con.disconnect();
			
			JSONObject jo = new JSONObject(json);
			JSONObject channel = jo.getJSONObject("channel");
			// System.out.println(channel);

			JSONArray item = channel.getJSONArray("item");
			// System.out.println(item);
			int i = 0;
			while (i < item.length()) {
				JSONObject temp = item.getJSONObject(i);
				String title = temp.getString("title");
				String address = temp.getString("address");
				System.out.println("명소:" + title + "\t" + "주소:" + address);
				i = i + 1;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
