package space.efremov.otusspringlibrary.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.domain.Book;
import space.efremov.otusspringlibrary.domain.Publisher;
import space.efremov.otusspringlibrary.domain.Tag;
import space.efremov.otusspringlibrary.exception.EntityNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BookDao extends AbstractJdbcDao<Book> {

    private final PublishDao publishDao;
    private final TagDao tagDao;
    private AuthorDao authorDao;

    private final RowMapper<BookDPO> rowMapper = (RowMapper<BookDPO>) (resultSet, i) -> {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        String isbn = resultSet.getString("isbn");
        Integer year = resultSet.getInt("year");
        Long publishId = resultSet.getLong("publisher_id");
        return new BookDPO(id, title, isbn, year, publishId);
    };

    public BookDao(NamedParameterJdbcOperations jdbc, EntityToMapConverter converter, PublishDao publishDao, TagDao tagDao, AuthorDao authorDao) {
        super(jdbc, converter);
        this.publishDao = publishDao;
        this.tagDao = tagDao;
        this.authorDao = authorDao;
    }

    @Override
    public Long count() {
        return jdbc.queryForObject("select count(*) from book", Collections.emptyMap(), Long.class);
    }

    @Override
    public Book insert(Book entity) {
        final Map<String, Object> params = converter.convert(entity);
        params.put("publishId", entity.getPublisher().getId());
        jdbc.update("insert into book (id, title, isbn, year, publish_id) values (:id, :title, :isbn, :year, :publishId )", params);
        return getById(entity.getId());
    }

    @Override
    public void delete(Long id) {
        getById(id);
        final Map<String, Object> idParam = converter.getIdParam(id);
        jdbc.update("delete from book where id = :id", idParam);
    }

    @Override
    public Book getById(Long id) {
        try {
            final BookDPO dpo = jdbc.queryForObject("select * from book where id = :id", converter.getIdParam(id), rowMapper);
            final Publisher publisher = publishDao.getById(dpo.publishId);
            final List<Tag> tags = jdbc.queryForList("select * from book_has_book_tag where book_id = :id", converter.getIdParam(id)).stream().map(row -> tagDao.getById((Long) row.get("book_tag_id"))).collect(Collectors.toList());
            final List<Author> authors = jdbc.queryForList("select * from book_has_author where book_id = :id", converter.getIdParam(id)).stream().map(row -> authorDao.getById((Long) row.get("author_id"))).collect(Collectors.toList());
            return new Book(dpo.id, dpo.title, dpo.isbn, dpo.year, publisher, tags, authors, Collections.emptyList());
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<Book> getAll() {
        final List<Map> rows = jdbc.queryForList("select * from book", Collections.EMPTY_MAP);
        return rows.stream().map(m -> new BookDPO((Long) m.get("id"), (String) m.get("title"), (String) m.get("isbn"), (Integer) m.get("year"), (Long) m.get("publisher_id"))).map(dpo -> {
            final Publisher publisher = publishDao.getById(dpo.publishId);
            final List<Tag> tags = jdbc.queryForList("select * from book_has_book_tag where book_id = :id", converter.getIdParam(dpo.id)).stream().map(row -> tagDao.getById((Long) row.get("book_tag_id"))).collect(Collectors.toList());
            final List<Author> authors = jdbc.queryForList("select * from book_has_author where book_id = :id", converter.getIdParam(dpo.id)).stream().map(row -> authorDao.getById((Long) row.get("author_id"))).collect(Collectors.toList());
            return new Book(dpo.id, dpo.title, dpo.isbn, dpo.year, publisher, tags, authors, Collections.emptyList());
        }).collect(Collectors.toList());
    }

    private class BookDPO {
        private final Long id;
        private final String title;
        private final String isbn;
        private final int year;
        private final Long publishId;

        BookDPO(Long id, String title, String isbn, int year, Long publishId) {
            this.id = id;
            this.title = title;
            this.isbn = isbn;
            this.year = year;
            this.publishId = publishId;
        }
    }

}

