package ru.innopolis.dmd.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.dmd.project.service.*;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Controller
public class MainController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private JournalService journalService;
    @Autowired
    private KeywordService keywordService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String fullSearch(@RequestParam String keyWord, ModelMap map) {
        if (!keyWord.trim().isEmpty()) {
            map.addAttribute("authors", authorService.findBySomeFieldLike(keyWord));
            map.addAttribute("journals", journalService.findBySomeFieldLike(keyWord));
            map.addAttribute("conferences", conferenceService.findBySomeFieldLike(keyWord));
            map.addAttribute("articles", articleService.findBySomeFieldLike(keyWord));
            map.addAttribute("keywords", keywordService.findBySomeFieldLike(keyWord));
        }
        return "fullsearch";
    }


}
