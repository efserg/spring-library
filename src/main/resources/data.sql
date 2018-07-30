INSERT INTO publisher VALUES (1, 'O''Reilly Media');

INSERT INTO book_tag VALUES (1, 'Java');
INSERT INTO book_tag VALUES (2, 'Big Data');
INSERT INTO book_tag VALUES (3, 'Python');

INSERT INTO author VALUES (1, 'Aurélien Géron');
INSERT INTO book VALUES (1, 'Hands-On Machine Learning with Scikit-Learn and TensorFlow', '978-1-491-96229-9', 2017, 1);

INSERT INTO book_has_book_tag VALUES (1, 1);
INSERT INTO book_has_book_tag VALUES (1, 2);
INSERT INTO book_has_book_tag VALUES (1, 3);

INSERT INTO book_has_author VALUES (1, 1);





