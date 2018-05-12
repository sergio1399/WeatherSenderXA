package app.components.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Forecast")
public class Forecast {

    @Id
    @GeneratedValue
    private  Integer id;

    private String temperature;

    private String wind;

    private String text;

    private Double pressure;

    private Double visibility;

    @Temporal(TemporalType.DATE)
    @Column(name = "forecast_date")
    private Date forecastDate;

    public Date getForecastDate() {
        return forecastDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Version
    private Integer version;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setForecastDate(Date forecastDate) {
        this.forecastDate = forecastDate;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getVisibility() {
        return visibility;
    }

    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

    /* Constructors */

    public Forecast() {
    }

    public Forecast(String temperature, String wind, String text) {
        this.temperature = temperature;
        this.wind = wind;
        this.text = text;
    }

    public Forecast(String temperature, String wind, String text, Double pressure, Double visibility, Date forecastDate) {
        this.temperature = temperature;
        this.wind = wind;
        this.text = text;
        this.pressure = pressure;
        this.visibility = visibility;
        this.forecastDate = forecastDate;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "id=" + id +
                ", temperature='" + temperature + '\'' +
                ", wind='" + wind + '\'' +
                ", text='" + text + '\'' +
                ", pressure=" + pressure +
                ", visibility=" + visibility +
                ", forecastDate=" + forecastDate +
                ", city=" + city +
                ", version=" + version +
                '}';
    }
}