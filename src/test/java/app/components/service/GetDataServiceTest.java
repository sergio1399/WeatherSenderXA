package app.components.service;

import app.components.view.ForecastCityView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GetDataServiceTest {
    @Mock
    private GetDataService getDataService;

    @Test
    public void getData() throws ParseException {

        ForecastCityView view = new ForecastCityView.Builder("Boston").cityRegion(" MA").cityCountry("United States").
                temperature("48").wind("chill: 48, direction:0, speed:4").text("Partly Cloudy").pressure(1021.0).
                visibility(12.5).cityId(2367105).build();
        org.mockito.Mockito.when(getDataService.getView("Boston")).thenReturn(view);
        assertEquals(getDataService.getView("Boston"), view);
    }

}