import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class WeatherForecastAPI {
	
	private fullData today;
	private fullData tomorrow;

	private String[][] pattern = {{"<div class=\"days\">", "(<span"},
								  {"<span class=\"forecast-telop\">", "</span>"},
								  {"high-temp\">", "&#8451;"},
								  {"low-temp\">", "&#8451;"},
								  {"prob-precip\">", "%</div>"},
								 };
	private miniData[] allDay = new miniData[14];
	
	public WeatherForecastAPI(String URL) {
		read(URL);
	}
		
	public void read(String url){
		BufferedReader br = null;
		String html = "";
		try {
			URL u = URI.create(url).toURL();
			br = new BufferedReader(new InputStreamReader((u.openConnection()).getInputStream(), "UTF-8"));
			String line = null;
			while((line = br.readLine()) != null) {
				html += line;
			}
			br.close();
		}catch(IOException e){
			System.out.println(e);
		}
		
		setData(html);
		
	}
	
	private String getData(String data, String f_len, String l_len) {
		int num1 = data.indexOf(f_len) + f_len.length();
		int num2 = data.indexOf(l_len, num1 + 1);
		return data.substring(num1, num2);
	}
	
	private void setData(String html) {
		String tmp = "\"forecast10days-actab\">";
		int num1 = html.indexOf(tmp) + (tmp).length(), num2 = 0;
		String[] result;
		for(int i = 0; i < 14; i++) {
			result = new String[5];
			for(int j = 0; j < pattern.length; j++) {
				num1 = html.indexOf(pattern[j][0], num1) + pattern[j][0].length();
				num2 = html.indexOf(pattern[j][1], num1);
				result[j] = html.substring(num1, num2);

			}
			allDay[i] = new miniData(result);
			
			tmp = "\"forecast10days-actab\">";
			num1 = html.indexOf(tmp, num1) + (tmp).length();
			
		}
		
	
		String[][] otherDay = new String[2][8];
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < allDay[i].getArray().length; j++) {
				otherDay[i][j] = allDay[i].getArray()[j];
			}
		}
		
		otherDay[0][5] = getData(html, "today_max_temp_diff\":\"", "\",\"");
		otherDay[0][6] = getData(html, "today_min_temp_diff\":\"", "\",\"");
		otherDay[0][7] = getData(html, "today_wind\":\"", "\",\"");
		today = new fullData(otherDay[0]);
		
		otherDay[1][5] = getData(html, "tomorrow_max_temp_diff\":\"", "\",\"");
		otherDay[1][6] = getData(html, "tomorrow_min_temp_diff\":\"", "\",\"");
		otherDay[1][7] = getData(html, "tomorrow_wind\":\"", "\",\"");
		tomorrow = new fullData(otherDay[1]);
		
		
	}
	
	public fullData getToday() {
		return today;
	}
	
	public fullData getTomorrow() {
		return tomorrow;
	}
	
	public miniData[] getAllDay() {
		return allDay;
	}	
	
}

class fullData{

	private String[] forecast;
	
	public fullData(String[] forecast) {
		
		this.forecast = forecast;
	}
	
	/**
	 * 日付を取得
	*/
    public String getDay() {
		
		return forecast[0];
	}
	
	/**
	 * 天気を取得
	 */
	public String getWeather() {
    	return forecast[1];
    }
	
	/**
	 * 最高気温を取得
	 */
	public int getHighTemp() {
		return Integer.parseInt(forecast[2]);
	}
	
	/**
	 * 最低気温を取得
	 */
	public int getLowTemp() {
		return Integer.parseInt(forecast[3]);
	}
	
	/**
	 * 湿度を取得
	 */
	public String getPecip() {
		return forecast[4];
	}
	
	
	/**
	 * 昨日との最高気温の差を取得
	 */
	public int getHighTempDiff() {
		return Integer.parseInt(forecast[5]);
	}
	
	/**
	 * 昨日との最低気温の差を取得
	 */
	public int getLowTempDiff() {
		return Integer.parseInt(forecast[6]);
	}
	
	/**
	 * 風の向きを取得
	 */
	public String getWind() {
		return forecast[7];
	}
	
	
	/**
	 * 全体のデータをArrayで取得
	 */
	public String[] getArray() {
		return forecast;
	}
}

class miniData{
	
	private String[] forecast;
	
	public miniData(String[] forecast) {
		this.forecast = forecast;
	}
	
	public String getDay() {
		return forecast[0];
	}
	
	public String getWeather() {
    	return forecast[1];
    }
	
	public int getHighTemp() {
		return Integer.parseInt(forecast[2]);
	}
	
	public int getLowTemp() {
		return Integer.parseInt(forecast[3]);
	}
	
	public String getPecip() {
		return forecast[4];
	}
	
	public String[] getArray() {
		return forecast;
	}
}
