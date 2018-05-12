package app.components.util;

import app.components.model.City;
import app.components.model.Forecast;
import app.components.view.ForecastCityView;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ForecastConverter {

    private static SimpleDateFormat RFC822DATEFORMAT
            = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm aaa Z", Locale.US);

    public static Forecast jsonToForecast(JSONObject json) throws ClassCastException, NullPointerException, ParseException{
        Forecast forecast = new Forecast();

        JSONObject channel = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel");
        if (channel.has("wind")) {
            JSONObject wind = channel.getJSONObject("wind");
            forecast.setWind("chill: " + String.valueOf(wind.getInt("chill")) + ", direction:" + String.valueOf(wind.getInt("direction")) + ", speed:" + String.valueOf(wind.getInt("speed")));
        }
        if (channel.has("atmosphere")) {
            JSONObject atmo = channel.getJSONObject("atmosphere");
            forecast.setPressure(atmo.getDouble("pressure"));
            forecast.setVisibility(atmo.getDouble("visibility"));
        }
        if (channel.has("item")) {
            JSONObject condition = channel.getJSONObject("item").getJSONObject("condition");

            forecast.setForecastDate(RFC822DATEFORMAT.parse(condition.getString("date")));

            forecast.setTemperature(condition.getString("temp"));
            forecast.setText(condition.getString("text"));
        }
        return forecast;
    }

    public static ForecastCityView jsonToForecastCityView(JSONObject json) throws ClassCastException, NullPointerException, ParseException{
        JSONObject location = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("location");
        String cityName = location.getString("city");
        String cityCountry = location.getString("country");
        String cityRegion = location.getString("region");
        String link = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getString("link");
        String cityId = link.split("-")[1].split("/")[0];
        Forecast forecast = jsonToForecast(json);
        return toView(new City(Integer.valueOf(cityId), cityName, cityRegion, cityCountry), forecast );
    }

    public static Forecast viewToForecast(ForecastCityView view) throws ClassCastException, NullPointerException{
        Forecast forecast = new Forecast(view.temperature, view.wind, view.text, view.pressure, view.visibility, view.forecastDate);
        forecast.setCity(viewToCity(view));
        return forecast;
    }

    public static City viewToCity(ForecastCityView view) throws ClassCastException, NullPointerException{
        return new City(view.cityId, view.cityName, view.cityRegion, view.cityCountry);
    }

    public static ForecastCityView toView(City city, Forecast forecast) throws ClassCastException, NullPointerException{
        return new ForecastCityView.Builder(city.getName()).cityRegion(city.getRegion()).cityCountry(city.getCountry()).
                temperature(forecast.getTemperature()).wind(forecast.getWind()).text(forecast.getText()).pressure(forecast.getPressure()).
                visibility(forecast.getVisibility()).cityId(city.getId()).forecastDate(forecast.getForecastDate()).build();
    }

}
