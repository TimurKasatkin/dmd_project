package ru.innopolis.dmd.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.dmd.project.dao.AuthorDao;
import ru.innopolis.dmd.project.model.Author;
import ru.innopolis.dmd.project.service.AuthorService;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Service
public class AuthorServiceImpl extends AbstractServiceImpl<Author, Long> implements AuthorService {

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        super(authorDao);
    }
}
