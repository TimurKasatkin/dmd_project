package ru.innopolis.dmd.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.service.ConferenceService;

/**
 * @author Timur Kasatkin
 * @date 31.10.15.
 * @email aronwest001@gmail.com
 */
@Controller
@RequestMapping("/conferences")
public class ConferencesController {

    @Autowired
    private ConferenceService conferenceService;

    @RequestMapping(method = RequestMethod.GET)
    public String conferences(@RequestParam(required = false, defaultValue = "0") int offset,
                              @RequestParam(required = false, defaultValue = "true") boolean isAsc,
                              ModelMap map) {
        return "conferences/conferences";
    }

    @RequestMapping(value = "/{id:[1-9]+[0-9]*}", method = RequestMethod.GET)
    public String conference(@PathVariable Long id, ModelMap map) {
        Conference conference = conferenceService.findById(id);
        map.addAttribute("conference", conference);
        return "conferences/conference";
    }
}
