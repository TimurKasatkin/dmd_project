package ru.innopolis.dmd.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.dmd.project.model.Keyword;
import ru.innopolis.dmd.project.service.ArticleService;
import ru.innopolis.dmd.project.service.KeywordService;

import java.util.List;

import static ru.innopolis.dmd.project.web.util.Constants.KEYWORDS_PAGE_LIMIT;

/**
 * @author Timur Kasatkin
 * @date 31.10.15.
 * @email aronwest001@gmail.com
 */
@Controller
@RequestMapping("/keywords")
public class KeywordsController {

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public String keywords(@RequestParam(required = false, defaultValue = "0") int offset,
                           @RequestParam(required = false, defaultValue = "true") boolean isAsc,
                           ModelMap map) {
        int limit = KEYWORDS_PAGE_LIMIT;
        List<Keyword> keywords = keywordService.findAllSortedBy("word", isAsc, offset, limit);
        map.addAttribute("keywords", keywords);
        long count = keywordService.count();
        if (offset - limit >= 0)
            map.addAttribute("prevOffset", offset - limit);
        if (offset + limit < count)
            map.addAttribute("nextOffset", offset + limit);
        return "keywords/keywords";
    }

    @RequestMapping(value = "/{id:[1-9]+[0-9]*}", method = RequestMethod.GET)
    public String keyword(@PathVariable Long id, ModelMap map) {
        Keyword keyword = keywordService.findById(id);
        map.addAttribute("keyword", keyword);
        map.addAttribute("articles", articleService.findByKeyword(keyword));
        return "keywords/keyword";
    }
}
