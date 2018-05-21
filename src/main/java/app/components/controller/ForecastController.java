package app.components.controller;

import app.components.service.GetForecastService;
import app.components.service.SendForecastService;
import app.components.view.ForecastCityView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForecastController {

    private GetForecastService getForecastService;

    @Autowired
    public void setGetForecastService(GetForecastService getForecastService) {
        this.getForecastService = getForecastService;
    }

    private SendForecastService sendForecastService;

    @Autowired
    public void setSendForecastService(SendForecastService sendForecastService) {
        this.sendForecastService = sendForecastService;
    }

    @RequestMapping(path={"/"},method=RequestMethod.GET)
    public String init(Model model) {
        return "index";
    }

    @RequestMapping(path={"/forecast"},method=RequestMethod.GET)
    public String forecast(@RequestParam(value="city", required=true) String city, Model model) {
        ForecastCityView forecastCityView = null;
        try {
            forecastCityView = getForecastService.getForecast(city);
            sendForecastService.sendForecast(forecastCityView);
        }
        catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }

        model.addAttribute("city", city);
        model.addAttribute("forecast",forecastCityView.toString());
        return "forecast";
    }



}