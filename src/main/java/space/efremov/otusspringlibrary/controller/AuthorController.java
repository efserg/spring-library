package space.efremov.otusspringlibrary.controller;

import space.efremov.otusspringlibrary.domain.Author;

public interface AuthorController {

    Author add(String name);

    void remove(int id);

    String get(Integer id);

}
