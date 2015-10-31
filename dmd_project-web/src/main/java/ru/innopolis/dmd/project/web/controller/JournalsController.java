package ru.innopolis.dmd.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.dmd.project.model.Journal;
import ru.innopolis.dmd.project.service.ArticleService;
import ru.innopolis.dmd.project.service.JournalService;

import java.util.List;

import static ru.innopolis.dmd.project.web.util.Constants.JOURNALS_PAGE_LIMIT;

/**
 * @author Timur Kasatkin
 * @date 31.10.15.
 * @email aronwest001@gmail.com
 */
@Controller
@RequestMapping("/journals")
public class JournalsController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private ArticleService articleService;


    @RequestMapping(method = RequestMethod.GET)
    public String journals(@RequestParam(required = false, defaultValue = "0") int offset,
                           @RequestParam(required = false, defaultValue = "true") boolean isAsc,
                           ModelMap map) {
        int limit = JOURNALS_PAGE_LIMIT;
        List<Journal> journals = journalService.findAllSortedBy("name", isAsc, offset, limit);
        map.addAttribute("journals", journals);
        long count = journalService.count();
        if (offset - limit >= 0)
            map.addAttribute("prevOffset", offset - limit);
        if (offset + limit < count)
            map.addAttribute("nextOffset", offset + limit);
        return "journals/journals";
    }

    @RequestMapping(value = "/{id:[1-9]+[0-9]*}", method = RequestMethod.GET)
    public String journal(@PathVariable Long id, ModelMap map) {
        Journal journal = journalService.findById(id);
        map.addAttribute("journal", journal);
        map.addAttribute("articles", articleService.findByJournal(journal));
        return "journals/journal";
    }
}
