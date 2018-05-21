package app.components.service;

import app.components.view.ForecastCityView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@SuppressWarnings("unchecked")
public class SendForecastService {
    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendForecast(ForecastCityView forecastCityView){
        jmsTemplate.convertAndSend(forecastCityView);
    }

}
