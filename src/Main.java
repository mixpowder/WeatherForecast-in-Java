public class Main {
	public static void main(String[] args) {
		WeatherForecastAPI api = new WeatherForecastAPI("https://tenki.jp/forecast/6/30/6200/27100/10days.html");
		
		System.out.println("大阪市気温:");
		System.out.println(api.getToday().getWind());
		System.out.println(api.getToday().getDay());
		

		
	}

}
