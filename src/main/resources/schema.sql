CREATE TABLE author (
    id int  NOT NULL ,
    name varchar  NOT NULL ,
    CONSTRAINT pk_author PRIMARY KEY (
        id
    )
);

CREATE TABLE book_tag (
    id int  NOT NULL ,
    name varchar  NOT NULL ,
    CONSTRAINT pk_book_tag PRIMARY KEY (
        id
    )
);

CREATE TABLE publisher (
    id int  NOT NULL ,
    name varchar  NOT NULL ,
    CONSTRAINT pk_publisher PRIMARY KEY (
        id
    )
);

CREATE TABLE book (
    id int  NOT NULL ,
    title varchar  NOT NULL ,
    isbn varchar  ,
    year int  ,
    publisher_id int  NOT NULL ,
    CONSTRAINT pk_book PRIMARY KEY (
        id
    )
);

CREATE TABLE book_has_author (
    book_id int  NOT NULL ,
    author_id int  NOT NULL
);

CREATE TABLE book_has_book_tag (
    book_id int  NOT NULL ,
    book_tag_id int  NOT NULL
);

ALTER TABLE book ADD CONSTRAINT fk_book_publisher_id FOREIGN KEY(publisher_id)
REFERENCES publisher (id);

ALTER TABLE book_has_author ADD CONSTRAINT fk_book_has_author_book_id FOREIGN KEY(book_id)
REFERENCES book (id);

ALTER TABLE book_has_author ADD CONSTRAINT fk_book_has_author_author_id FOREIGN KEY(author_id)
REFERENCES author (id);

ALTER TABLE book_has_book_tag ADD CONSTRAINT fk_book_has_book_tag_book_id FOREIGN KEY(book_id)
REFERENCES book (id);

ALTER TABLE book_has_book_tag ADD CONSTRAINT fk_book_has_book_tag_book_tag_id FOREIGN KEY(book_tag_id)
REFERENCES book_tag (id);

CREATE INDEX idx_author_name
ON author (name);

CREATE INDEX idx_book_tag_name
ON book_tag (name);

CREATE INDEX idx_publisher_name
ON publisher (name);

