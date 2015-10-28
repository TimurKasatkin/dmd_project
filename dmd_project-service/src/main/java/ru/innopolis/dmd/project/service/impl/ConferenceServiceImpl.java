package ru.innopolis.dmd.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.dmd.project.dao.ConferenceDao;
import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.service.ConferenceService;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Service
public class ConferenceServiceImpl extends AbstractServiceImpl<Conference, Long> implements ConferenceService {

    @Autowired
    public ConferenceServiceImpl(ConferenceDao conferenceDao) {
        super(conferenceDao);
    }
}
