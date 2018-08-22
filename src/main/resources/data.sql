INSERT INTO author VALUES (1, 'Brian Wilson Kernighan');
INSERT INTO author VALUES (2, 'Dennis MacAlistair Ritchie');
INSERT INTO author VALUES (3, 'Rob Pike');
INSERT INTO author VALUES (4, 'Donald Knuth');
INSERT INTO author VALUES (5, 'Joshua J. Bloch');
INSERT INTO author VALUES (6, 'Neal Gafter');
INSERT INTO author VALUES (7, 'Brian Goetz');
INSERT INTO author VALUES (8, 'Tim Peierls');
INSERT INTO author VALUES (9, 'Joseph Bowbeer');
INSERT INTO author VALUES (10, 'David Holmes');
INSERT INTO author VALUES (11, 'Doug Lea');

INSERT INTO publisher VALUES (1, 'AT&T Bell Labs');
INSERT INTO publisher VALUES (2, 'Prentice Hall');
INSERT INTO publisher VALUES (3, 'Addison-Wesley');

INSERT INTO book VALUES (1, '0-13-110163-3', 'The C Programming Language', 1978, 1);
INSERT INTO book VALUES (2, '0-13-937681-X', 'The Unix Programming Environment', 1984, 2);
INSERT INTO book
VALUES (3, '0-201-03801-3', 'The Art of Computer Programming, Volume 1: Fundamental Algorithms', 1968, 3);
INSERT INTO book VALUES (4, '978-0134685991', 'Effective Java (3rd Edition)', 2017, 3);
INSERT INTO book VALUES (5, '0-321-33678-X', 'Java Puzzlers: Traps, Pitfalls, and Corner Cases', 2005, 3);
INSERT INTO book VALUES (6, '0-321-33678-X', 'Java Concurrency in Practice', 2006, 3);

INSERT INTO tag VALUES (1, 'Java');
INSERT INTO tag VALUES (2, 'Big Data');
INSERT INTO tag VALUES (3, 'Python');
INSERT INTO tag VALUES (4, 'C');
INSERT INTO tag VALUES (5, 'Classic');
INSERT INTO tag VALUES (6, 'Must read');
INSERT INTO tag VALUES (7, 'Unix');
INSERT INTO tag VALUES (8, 'Algorithm');
INSERT INTO tag VALUES (9, 'Concurrency');

INSERT INTO author_books VALUES (1, 1);
INSERT INTO author_books VALUES (1, 2);
INSERT INTO author_books VALUES (2, 1);
INSERT INTO author_books VALUES (2, 3);
INSERT INTO author_books VALUES (3, 4);
INSERT INTO author_books VALUES (4, 5);
INSERT INTO author_books VALUES (5, 5);
INSERT INTO author_books VALUES (5, 6);
INSERT INTO author_books VALUES (6, 5);
INSERT INTO author_books VALUES (6, 7);
INSERT INTO author_books VALUES (6, 8);
INSERT INTO author_books VALUES (6, 9);
INSERT INTO author_books VALUES (6, 10);
INSERT INTO author_books VALUES (6, 11);

INSERT INTO tag_books VALUES (1, 4);
INSERT INTO tag_books VALUES (1, 5);
INSERT INTO tag_books VALUES (1, 6);
INSERT INTO tag_books VALUES (2, 5);
INSERT INTO tag_books VALUES (2, 6);
INSERT INTO tag_books VALUES (2, 7);
INSERT INTO tag_books VALUES (3, 5);
INSERT INTO tag_books VALUES (3, 6);
INSERT INTO tag_books VALUES (3, 8);
INSERT INTO tag_books VALUES (4, 1);
INSERT INTO tag_books VALUES (4, 5);
INSERT INTO tag_books VALUES (4, 6);
INSERT INTO tag_books VALUES (5, 1);
INSERT INTO tag_books VALUES (6, 1);
INSERT INTO tag_books VALUES (6, 9);

ALTER SEQUENCE IF EXISTS HIBERNATE_SEQUENCE
RESTART WITH 100;