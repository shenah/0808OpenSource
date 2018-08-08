
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParsing {

	public static void main(String[] args) {

		//다음의 책 검색 API 주소에서 author 값만 추출하기
		
		Thread th = new Thread() {
			public void run() {
				try {
					//다운로드 받을 URL 생성
					URL url = new URL("http://apis.daum.net/search/book?apikey=465b06fae32febacbc59502598dd7685&output=json&q=java\r\n");
					//URL 연결 객체 생성
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					//옵션설정
					//캐시 설정 여부 - 다운로드 받아놓고 다음에 다운로드 받은 데이터를 이용할 것인지 여부 설정
					con.setUseCaches(false);
					//얼마동안 접속을 시도해 볼것인지 설정
					con.setConnectTimeout(30000);
					//문자열을 읽기 위한 스트림 생성
					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					//줄 단위로 데이터를 읽어서 sb에 추가
					StringBuilder sb = new StringBuilder();
					while(true) {
						String line = br.readLine();
						if(line == null) break;
						sb.append(line);
					}
					//sb의 내용을 json에 대입
					String json = sb.toString();
					
					//메모리 스트림 정리
					sb = null;
					br.close();
					con.disconnect();

					//JSON 문자열 파싱 -JSONObject
					JSONObject jso = new JSONObject(json);
					
					//channel 키의 데이터를 가져오기
					JSONObject channel = jso.getJSONObject("channel");
					System.out.println(channel);
					
					//channel에서 item 키의 값을 배열로 가져오기
					JSONArray item = channel.getJSONArray("item");
					System.out.println(item);
					//배열을 순회 
					int i = 0;
					while(i<item.length()) {
						JSONObject book = item.getJSONObject(i);
						String author = book.getString("author");
						System.out.println(author);
						i = i + 1;
					}
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		};
		th.start();
	}

}
