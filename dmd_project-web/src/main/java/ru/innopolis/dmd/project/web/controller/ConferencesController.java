package ru.innopolis.dmd.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
