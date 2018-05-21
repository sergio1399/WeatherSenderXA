package app.components.service;

import app.components.exception.NotExistCityException;
import app.components.util.ForecastConverter;
import app.components.view.ForecastCityView;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@SuppressWarnings("unchecked")
public class GetForecastService {

    private static final String YAHOOSTR = "https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")";

    public ForecastCityView getForecast(String city) throws ClassCastException, NullPointerException, ParseException, NotExistCityException {
        RestTemplate restTemplate = new RestTemplate();
        String strUri = String.format(YAHOOSTR, city);

        restTemplate.setMessageConverters(getMessageConverters());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<Object> response =
                restTemplate.exchange(strUri, HttpMethod.GET, entity, Object.class, "1");
        Object object = response.getBody();

        JSONObject json = new JSONObject((LinkedHashMap<String, String>) object);
        if(!json.getJSONObject("query").has("results")){
            throw new NotExistCityException("City " + city + " doesn't exist!");
        }
        return ForecastConverter.jsonToForecastCityView(json);
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters =
                new ArrayList<HttpMessageConverter<?>>();
        converters.add(new MappingJackson2HttpMessageConverter());
        return converters;
    }
}
