package ru.innopolis.dmd.project.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Controller
public class MainController {

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String fullSearch(@RequestParam String keyWord, ModelMap map) {
        map.addAttribute("authors", null);
        map.addAttribute("journals", null);
        map.addAttribute("conferences", null);
        map.addAttribute("articles", null);
        map.addAttribute("keywords", null);
        return "";
    }


}
