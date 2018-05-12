package app.components.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ForecastControllerTest {
    @Mock
    private JmsTemplate jmsTemplate;


    @Test
    public void getForecast(){


    }

}