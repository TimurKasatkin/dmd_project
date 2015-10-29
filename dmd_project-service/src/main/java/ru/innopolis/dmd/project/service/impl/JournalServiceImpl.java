package ru.innopolis.dmd.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.dmd.project.dao.JournalDao;
import ru.innopolis.dmd.project.model.Journal;
import ru.innopolis.dmd.project.service.JournalService;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Service
public class JournalServiceImpl extends AbstractServiceImpl<Journal, Long> implements JournalService {

    @Autowired
    public JournalServiceImpl(JournalDao journalDao) {
        super(journalDao);
    }
}
