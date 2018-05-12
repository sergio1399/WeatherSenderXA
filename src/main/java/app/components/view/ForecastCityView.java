package app.components.view;

import app.components.model.City;
import app.components.model.Forecast;

import java.util.Date;

public class ForecastCityView {
    public String cityName;

    public String cityRegion;

    public String cityCountry;

    public String temperature;

    public String wind;

    public String text;

    public Double pressure;

    public Double visibility;

    public Integer cityId;

    public Date forecastDate;

    public ForecastCityView() {
    }

    @Override
    public String toString() {
        return "ForecastCityView{" +
                "cityName='" + cityName + '\'' +
                ", cityRegion='" + cityRegion + '\'' +
                ", cityCountry='" + cityCountry + '\'' +
                ", temperature='" + temperature + '\'' +
                ", wind='" + wind + '\'' +
                ", text='" + text + '\'' +
                ", pressure=" + pressure +
                ", visibility=" + visibility +
                ", cityId=" + cityId +
                ", forecastDate=" + forecastDate +
                '}';
    }

    public static class Builder{

        private String cityName;

        private String cityRegion;

        private String cityCountry;

        private String temperature;

        private String wind;

        private String text;

        private Double pressure;

        private Double visibility;

        private Integer cityId;

        private Date forecastDate;

        public Builder(String cityName) {
            this.cityName = cityName;
        }

        public Builder cityRegion(String cityRegion){
            this.cityRegion = cityRegion;
            return this;
        }

        public Builder cityCountry(String cityCountry){
            this.cityCountry = cityCountry;
            return this;
        }

        public Builder temperature(String temperature){
            this.temperature = temperature;
            return this;
        }

        public Builder wind(String wind){
            this.wind = wind;
            return this;
        }

        public Builder text(String text){
            this.text = text;
            return this;
        }

        public Builder pressure(Double pressure){
            this.pressure = pressure;
            return this;
        }

        public Builder visibility(Double visibility){
            this.visibility = visibility;
            return this;
        }

        public Builder cityId(Integer cityId){
            this.cityId = cityId;
            return this;
        }

        public Builder forecastDate(Date forecastDate){
            this.forecastDate = forecastDate;
            return this;
        }

        public ForecastCityView build(){
            return new ForecastCityView(this);
        }

    }

    private ForecastCityView(Builder builder){
        cityName = builder.cityName;
        cityRegion = builder.cityRegion;
        cityCountry = builder.cityCountry;
        temperature = builder.temperature;
        wind = builder.wind;
        text = builder.text;
        pressure = builder.pressure;
        visibility = builder.visibility;
        cityId = builder.cityId;
        forecastDate = builder.forecastDate;
    }

}
