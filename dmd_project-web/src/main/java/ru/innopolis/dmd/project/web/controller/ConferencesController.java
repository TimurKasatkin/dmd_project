package ru.innopolis.dmd.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.service.ArticleService;
import ru.innopolis.dmd.project.service.ConferenceService;

import java.util.List;

import static ru.innopolis.dmd.project.web.util.Constants.CONFERENCES_PAGE_LIMIT;

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

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public String conferences(@RequestParam(required = false, defaultValue = "0") int offset,
                              @RequestParam(required = false, defaultValue = "true") boolean isAsc,
                              ModelMap map) {
        int limit = CONFERENCES_PAGE_LIMIT;
        List<Conference> conferences = conferenceService.findAllSortedBy("name", isAsc, offset, limit);
        map.addAttribute("conferences", conferences);
        long count = conferenceService.count();
        if (offset - limit >= 0)
            map.addAttribute("prevOffset", offset - limit);
        if (offset + limit < count)
            map.addAttribute("nextOffset", offset + limit);
        return "conferences/conferences";
    }

    @RequestMapping(value = "/{id:[1-9]+[0-9]*}", method = RequestMethod.GET)
    public String conference(@PathVariable Long id, ModelMap map) {
        Conference conference = conferenceService.findById(id);
        map.addAttribute("conference", conference);
        map.addAttribute("articles", articleService.findByConference(conference));
        return "conferences/conference";
    }
}
