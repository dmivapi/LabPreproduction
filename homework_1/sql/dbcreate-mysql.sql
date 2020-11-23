-- MySQL Workbench Forward Engineering

-- begin attached script 'script'
SET GLOBAL time_zone = '+3:00';
-- end attached script 'script'
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Library
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Library
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Library` DEFAULT CHARACTER SET utf8 ;
USE `Library` ;

-- -----------------------------------------------------
-- Table `Library`.`book`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`book` ;

CREATE TABLE IF NOT EXISTS `Library`.`book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`author`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`author` ;

CREATE TABLE IF NOT EXISTS `Library`.`author` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `last_name` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `last_name_UNIQUE` (`last_name` ASC) VISIBLE,
  UNIQUE INDEX `first_name_UNIQUE` (`first_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`book_has_author`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`book_has_author` ;

CREATE TABLE IF NOT EXISTS `Library`.`book_has_author` (
  `author_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  PRIMARY KEY (`author_id`, `book_id`),
  INDEX `fk_book_has_author_author_idx` (`author_id` ASC) INVISIBLE,
  INDEX `fk_book_has_author_book_idx` (`book_id` ASC) INVISIBLE,
  CONSTRAINT `fk_book_has_author_author`
    FOREIGN KEY (`author_id`)
    REFERENCES `Library`.`author` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_book_has_author_book`
    FOREIGN KEY (`book_id`)
    REFERENCES `Library`.`book` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`publisher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`publisher` ;

CREATE TABLE IF NOT EXISTS `Library`.`publisher` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`publication`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`publication` ;

CREATE TABLE IF NOT EXISTS `Library`.`publication` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `book_id` INT NOT NULL,
  `publisher_id` INT NOT NULL,
  `year` INT UNSIGNED NOT NULL,
  `price` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_publication_publisher_idx` (`publisher_id` ASC) VISIBLE,
  CONSTRAINT `fk_publication_book`
    FOREIGN KEY (`book_id`)
    REFERENCES `Library`.`book` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_publication_publisher`
    FOREIGN KEY (`publisher_id`)
    REFERENCES `Library`.`publisher` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`book_copy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`book_copy` ;

CREATE TABLE IF NOT EXISTS `Library`.`book_copy` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `lib_code` VARCHAR(45) NOT NULL,
  `publication_id` INT NOT NULL,
  PRIMARY KEY (`id`, `publication_id`),
  UNIQUE INDEX `lib_code_UNIQUE` (`lib_code` ASC) VISIBLE,
  INDEX `fk_catalog_publication_idx` (`publication_id` ASC) VISIBLE,
  CONSTRAINT `fk_book_copy_publication`
    FOREIGN KEY (`publication_id`)
    REFERENCES `Library`.`publication` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`user` ;

CREATE TABLE IF NOT EXISTS `Library`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `locale_name` VARCHAR(15) NULL,
  `user_role` ENUM('admin', 'librarian', 'reader') NOT NULL,
  `blocked` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `user_type` (`user_role` ASC) VISIBLE,
  INDEX `blocked` (`blocked` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `Library`.`book_loan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`book_loan` ;

CREATE TABLE IF NOT EXISTS `Library`.`book_loan` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `book_copy_id` INT NOT NULL,
  `date_out` DATE NULL,
  `due_date` DATE NULL,
  `date_in` DATE NULL,
  `reading_room` TINYINT NULL,
  INDEX `fk_request_user_idx` (`user_id` ASC) INVISIBLE,
  INDEX `fk_catalog_idx` (`book_copy_id` ASC) INVISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_book_loan_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `Library`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_book_loan_catalog`
    FOREIGN KEY (`book_copy_id`)
    REFERENCES `Library`.`book_copy` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`languages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`languages` ;

CREATE TABLE IF NOT EXISTS `Library`.`languages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(5) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`genre_translation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`genre_translation` ;

CREATE TABLE IF NOT EXISTS `Library`.`genre_translation` (
  `language_id` INT NOT NULL,
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`language_id`, `id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_genre_translation_languages_idx` (`language_id` ASC) INVISIBLE,
  CONSTRAINT `fk_genre_translation_languages`
    FOREIGN KEY (`language_id`)
    REFERENCES `Library`.`languages` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`book_has_genre_translation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`book_has_genre_translation` ;

CREATE TABLE IF NOT EXISTS `Library`.`book_has_genre_translation` (
  `book_id` INT NOT NULL,
  `language_id` INT NOT NULL,
  `genre_id` INT NOT NULL,
  PRIMARY KEY (`book_id`, `language_id`, `genre_id`),
  INDEX `fk_book_has_genre_book_idx` (`book_id` ASC) INVISIBLE,
  INDEX `fk_book_has_genre_translation_genre_translation_idx` (`language_id` ASC, `genre_id` ASC) VISIBLE,
  CONSTRAINT `fk_book_has_genre_translation_book`
    FOREIGN KEY (`book_id`)
    REFERENCES `Library`.`book` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_book_has_genre_translation_genre_translation`
    FOREIGN KEY (`language_id` , `genre_id`)
    REFERENCES `Library`.`genre_translation` (`language_id` , `id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `Library` ;

-- -----------------------------------------------------
-- Placeholder table for view `Library`.`view_book_authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Library`.`view_book_authors` (`id` INT, `title` INT, `authors` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Library`.`view_published_books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Library`.`view_published_books` (`id` INT, `book_id` INT, `title` INT, `authors` INT, `publisher` INT, `year` INT, `price` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Library`.`view_book_authors_genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Library`.`view_book_authors_genre` (`id` INT, `title` INT, `authors` INT, `genre` INT, `publisher` INT, `year` INT, `price` INT, `language_id` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Library`.`view_book_loans`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Library`.`view_book_loans` (`id` INT, `user_id` INT, `book_copy_id` INT, `date_out` INT, `due_date` INT, `date_in` INT, `reading_room` INT, `lib_code` INT, `title` INT, `authors` INT, `genre` INT, `language_id` INT, `publisher` INT, `year` INT, `price` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Library`.`view_available_book_copies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Library`.`view_available_book_copies` (`id` INT, `lib_code` INT, `publication_id` INT);

-- -----------------------------------------------------
-- Placeholder table for view `Library`.`view_book_user_loans`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Library`.`view_book_user_loans` (`id` INT, `user_id` INT, `book_copy_id` INT, `date_out` INT, `due_date` INT, `date_in` INT, `reading_room` INT, `lib_code` INT, `title` INT, `authors` INT, `genre` INT, `language_id` INT, `publisher` INT, `year` INT, `price` INT, `email` INT, `first_name` INT, `last_name` INT, `blocked` INT);

-- -----------------------------------------------------
-- View `Library`.`view_book_authors`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`view_book_authors`;
DROP VIEW IF EXISTS `Library`.`view_book_authors` ;
USE `Library`;
CREATE  OR REPLACE VIEW `view_book_authors` AS
    SELECT 
        id, title, authors
    FROM
        book
	JOIN
        (SELECT 
            book_id,
            GROUP_CONCAT(first_name, ' ', last_name SEPARATOR ', ') AS authors
         FROM
            book_has_author
         JOIN author ON author_id = id
         GROUP BY book_id
        ) AS b_authors
	ON id = book_id;

-- -----------------------------------------------------
-- View `Library`.`view_published_books`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`view_published_books`;
DROP VIEW IF EXISTS `Library`.`view_published_books` ;
USE `Library`;
CREATE  OR REPLACE VIEW `view_published_books` AS
SELECT 
    publications.id, book_id, title, authors, publisher, year, price
FROM
    view_book_authors AS vba
JOIN
	(SELECT 
		publication.id, book_id, year, price, name as publisher
	FROM
		publication
	JOIN publisher AS p ON publisher_id = p.id
	) AS publications
ON vba.id = book_id;

-- -----------------------------------------------------
-- View `Library`.`view_book_authors_genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`view_book_authors_genre`;
DROP VIEW IF EXISTS `Library`.`view_book_authors_genre` ;
USE `Library`;
CREATE  OR REPLACE VIEW `view_book_authors_genre` AS
SELECT 
    id, title, authors, genre, publisher, year, price, language_id
FROM
    view_published_books
JOIN
	(SELECT 
		book_id, name as genre, gt.language_id
	FROM
		genre_translation AS gt
	JOIN book_has_genre_translation AS bgt ON bgt.genre_id = gt.id
											AND bgt.language_id = gt.language_id
	) AS book_genre
ON view_published_books.book_id = book_genre.book_id;

-- -----------------------------------------------------
-- View `Library`.`view_book_loans`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`view_book_loans`;
DROP VIEW IF EXISTS `Library`.`view_book_loans` ;
USE `Library`;
CREATE  OR REPLACE VIEW `view_book_loans` AS
         SELECT lc.id, user_id, book_copy_id, date_out, due_date, date_in, reading_room,
                lib_code,
				title, authors,
                genre, language_id,
                publisher, year, price
         FROM
           (SELECT book_loan.id, user_id, book_copy_id, date_out, due_date, date_in, reading_room,
                   lib_code,
                   publication_id 
            FROM book_loan
			JOIN book_copy
            ON book_copy_id=book_copy.id
			) as lc
		 JOIN view_book_authors_genre vbag
         ON lc.publication_id = vbag.id;

-- -----------------------------------------------------
-- View `Library`.`view_available_book_copies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`view_available_book_copies`;
DROP VIEW IF EXISTS `Library`.`view_available_book_copies` ;
USE `Library`;
CREATE  OR REPLACE VIEW `view_available_book_copies` AS
    SELECT * FROM book_copy
    WHERE id NOT IN (
            SELECT book_copy_id FROM book_loan WHERE date_in IS NULL
                    );

-- -----------------------------------------------------
-- View `Library`.`view_book_user_loans`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`view_book_user_loans`;
DROP VIEW IF EXISTS `Library`.`view_book_user_loans` ;
USE `Library`;
CREATE  OR REPLACE VIEW `view_book_user_loans` AS
SELECT vbl.id, user_id, book_copy_id, date_out, due_date, date_in, reading_room,
       lib_code,
	   title, authors,
       genre, language_id,
       publisher, year, price,
       email, first_name, last_name, blocked
FROM view_book_loans vbl
JOIN user
ON user_id=user.id;

-- -----------------------------------------------------
-- Data for table `Library`.`book`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`book` (`id`, `title`) VALUES (1, '12 стульев');
INSERT INTO `Library`.`book` (`id`, `title`) VALUES (2, 'Золотой теленок');
INSERT INTO `Library`.`book` (`id`, `title`) VALUES (3, 'Ключи');
INSERT INTO `Library`.`book` (`id`, `title`) VALUES (4, 'Капитал');
INSERT INTO `Library`.`book` (`id`, `title`) VALUES (5, 'On Java 8');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`author`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`author` (`id`, `last_name`, `first_name`) VALUES (1, 'Санников', 'Сергей Викторович');
INSERT INTO `Library`.`author` (`id`, `last_name`, `first_name`) VALUES (2, 'Маркс', 'Карл');
INSERT INTO `Library`.`author` (`id`, `last_name`, `first_name`) VALUES (3, 'Хант', 'Джун');
INSERT INTO `Library`.`author` (`id`, `last_name`, `first_name`) VALUES (4, 'Лукадо', 'Макс');
INSERT INTO `Library`.`author` (`id`, `last_name`, `first_name`) VALUES (5, 'Ильф', 'Илья');
INSERT INTO `Library`.`author` (`id`, `last_name`, `first_name`) VALUES (6, 'Петров', 'Евгений');
INSERT INTO `Library`.`author` (`id`, `last_name`, `first_name`) VALUES (7, 'Брюс', 'Эккель');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`book_has_author`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`book_has_author` (`author_id`, `book_id`) VALUES (5, 1);
INSERT INTO `Library`.`book_has_author` (`author_id`, `book_id`) VALUES (6, 1);
INSERT INTO `Library`.`book_has_author` (`author_id`, `book_id`) VALUES (5, 2);
INSERT INTO `Library`.`book_has_author` (`author_id`, `book_id`) VALUES (6, 2);
INSERT INTO `Library`.`book_has_author` (`author_id`, `book_id`) VALUES (3, 3);
INSERT INTO `Library`.`book_has_author` (`author_id`, `book_id`) VALUES (2, 4);
INSERT INTO `Library`.`book_has_author` (`author_id`, `book_id`) VALUES (7, 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`publisher`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`publisher` (`id`, `name`) VALUES (1, 'Издательство \"Заря\"');
INSERT INTO `Library`.`publisher` (`id`, `name`) VALUES (2, 'Издательство \"Ранок\"');
INSERT INTO `Library`.`publisher` (`id`, `name`) VALUES (3, 'Издательство \"Барвинок\"');
INSERT INTO `Library`.`publisher` (`id`, `name`) VALUES (4, 'books.google.com');
INSERT INTO `Library`.`publisher` (`id`, `name`) VALUES (5, 'Вагриус');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`publication`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`publication` (`id`, `book_id`, `publisher_id`, `year`, `price`) VALUES (1, 1, 2, 1977, 250);
INSERT INTO `Library`.`publication` (`id`, `book_id`, `publisher_id`, `year`, `price`) VALUES (2, 1, 5, 1997, 100);
INSERT INTO `Library`.`publication` (`id`, `book_id`, `publisher_id`, `year`, `price`) VALUES (3, 3, 1, 2005, 150);
INSERT INTO `Library`.`publication` (`id`, `book_id`, `publisher_id`, `year`, `price`) VALUES (4, 2, 3, 2012, 480);
INSERT INTO `Library`.`publication` (`id`, `book_id`, `publisher_id`, `year`, `price`) VALUES (5, 4, 2, 2018, 320);
INSERT INTO `Library`.`publication` (`id`, `book_id`, `publisher_id`, `year`, `price`) VALUES (6, 5, 4, 2017, 120);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`book_copy`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (1, '777.1', 1);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (2, '777.2', 1);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (3, '777.3', 1);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (4, '778.1', 1);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (5, '778.2', 1);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (6, '800.1', 3);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (7, '800.2', 3);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (8, '800.3', 3);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (9, '500.1', 2);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (10, '500.2', 2);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (11, '500.3', 2);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (12, '500.4', 2);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (13, '500.5', 2);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (14, '673.1', 4);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (15, '673.2', 4);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (16, '673.3', 4);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (17, '674.4', 4);
INSERT INTO `Library`.`book_copy` (`id`, `lib_code`, `publication_id`) VALUES (18, '900.1', 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `locale_name`, `user_role`, `blocked`) VALUES (1, '1@gmail.com', '1', 'Василий', 'Караченцов', NULL, 'librarian', 0);
INSERT INTO `Library`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `locale_name`, `user_role`, `blocked`) VALUES (2, '2@gmail.com', '2', 'Джон', 'Петрушевский', 'ru', 'reader', 0);
INSERT INTO `Library`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `locale_name`, `user_role`, `blocked`) VALUES (3, '3@gmail.com', '3', 'Валентин', 'Смирнов', 'en', 'admin', 0);
INSERT INTO `Library`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `locale_name`, `user_role`, `blocked`) VALUES (4, '4@gmail.com', '4', 'Иосиф', 'Петренко', NULL, 'reader', 0);
INSERT INTO `Library`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `locale_name`, `user_role`, `blocked`) VALUES (5, '5@gmail.com', '5', 'Иван', 'Забывайкин', NULL, 'reader', 1);
INSERT INTO `Library`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `locale_name`, `user_role`, `blocked`) VALUES (6, '6@gmail.com', '6', 'Артем', 'Злобин', NULL, 'librarian', 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`book_loan`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`book_loan` (`id`, `user_id`, `book_copy_id`, `date_out`, `due_date`, `date_in`, `reading_room`) VALUES (DEFAULT, 2, 1, NULL, NULL, NULL, NULL);
INSERT INTO `Library`.`book_loan` (`id`, `user_id`, `book_copy_id`, `date_out`, `due_date`, `date_in`, `reading_room`) VALUES (DEFAULT, 2, 9, '2020-09-26', '2020-09-28', '2020-10-01', NULL);
INSERT INTO `Library`.`book_loan` (`id`, `user_id`, `book_copy_id`, `date_out`, `due_date`, `date_in`, `reading_room`) VALUES (DEFAULT, 4, 18, '2019-08-07', '2019-08-21', NULL, NULL);
INSERT INTO `Library`.`book_loan` (`id`, `user_id`, `book_copy_id`, `date_out`, `due_date`, `date_in`, `reading_room`) VALUES (DEFAULT, 2, 6, '2020-09-28', '2020-10-29', NULL, NULL);
INSERT INTO `Library`.`book_loan` (`id`, `user_id`, `book_copy_id`, `date_out`, `due_date`, `date_in`, `reading_room`) VALUES (DEFAULT, 4, 15, NULL, NULL, NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`languages`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`languages` (`id`, `code`, `name`) VALUES (1, 'ru', 'Русский');
INSERT INTO `Library`.`languages` (`id`, `code`, `name`) VALUES (2, 'en', 'English');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`genre_translation`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`genre_translation` (`language_id`, `id`, `name`) VALUES (1, 1, 'Научная литература');
INSERT INTO `Library`.`genre_translation` (`language_id`, `id`, `name`) VALUES (2, 1, 'Science');
INSERT INTO `Library`.`genre_translation` (`language_id`, `id`, `name`) VALUES (1, 2, 'Детская литература');
INSERT INTO `Library`.`genre_translation` (`language_id`, `id`, `name`) VALUES (2, 2, 'For children');
INSERT INTO `Library`.`genre_translation` (`language_id`, `id`, `name`) VALUES (1, 3, 'Роман');
INSERT INTO `Library`.`genre_translation` (`language_id`, `id`, `name`) VALUES (2, 3, 'Novel');
INSERT INTO `Library`.`genre_translation` (`language_id`, `id`, `name`) VALUES (1, 4, 'Программирование');
INSERT INTO `Library`.`genre_translation` (`language_id`, `id`, `name`) VALUES (2, 4, 'Programming');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`book_has_genre_translation`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`book_has_genre_translation` (`book_id`, `language_id`, `genre_id`) VALUES (1, 1, 3);
INSERT INTO `Library`.`book_has_genre_translation` (`book_id`, `language_id`, `genre_id`) VALUES (1, 2, 3);
INSERT INTO `Library`.`book_has_genre_translation` (`book_id`, `language_id`, `genre_id`) VALUES (2, 1, 3);
INSERT INTO `Library`.`book_has_genre_translation` (`book_id`, `language_id`, `genre_id`) VALUES (2, 2, 3);
INSERT INTO `Library`.`book_has_genre_translation` (`book_id`, `language_id`, `genre_id`) VALUES (3, 1, 2);
INSERT INTO `Library`.`book_has_genre_translation` (`book_id`, `language_id`, `genre_id`) VALUES (3, 2, 2);
INSERT INTO `Library`.`book_has_genre_translation` (`book_id`, `language_id`, `genre_id`) VALUES (4, 1, 1);
INSERT INTO `Library`.`book_has_genre_translation` (`book_id`, `language_id`, `genre_id`) VALUES (4, 2, 1);
INSERT INTO `Library`.`book_has_genre_translation` (`book_id`, `language_id`, `genre_id`) VALUES (5, 1, 4);
INSERT INTO `Library`.`book_has_genre_translation` (`book_id`, `language_id`, `genre_id`) VALUES (5, 2, 4);

COMMIT;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
