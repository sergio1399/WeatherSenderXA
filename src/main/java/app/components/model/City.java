package app.components.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "City")
public class City {

    @Id
    private Integer id;

    private String name;

    private String region;

    private String country;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Forecast> forecasts;

    @Version
    private Integer version;

    public void addForecast(Forecast forecast)
    {
        getForecasts().add(forecast);
        forecast.setCity(this);
    }

    public void removeForecast(Forecast forecast)
    {
        getForecasts().remove(forecast);
        forecast.setCity(null);
    }

    public Set<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(Set<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public City() {
    }

    public City(Integer id, String name, String region, String country) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.country = country;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}