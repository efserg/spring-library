package space.efremov.otusspringlibrary.controller;

import space.efremov.otusspringlibrary.domain.Author;

import java.util.List;

public interface AuthorController {

    Author add(String name);

    void remove(Long id);

    Author get(Long id);

    List<Author> list();
}
