-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema library_rest_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema library_rest_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `library_rest_db` DEFAULT CHARACTER SET utf8 ;
USE `library_rest_db` ;

-- -----------------------------------------------------
-- Table `library_rest_db`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_rest_db`.`client` (
                                                          `client_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                          `name` VARCHAR(45) NOT NULL,
                                                          `surname` VARCHAR(45) NOT NULL,
                                                          `birthday` DATE NULL,
                                                          PRIMARY KEY (`client_id`),
                                                          UNIQUE INDEX `id_UNIQUE` (`client_id` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_rest_db`.`library_card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_rest_db`.`library_card` (
                                                                `card_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                                `client_id` BIGINT NOT NULL,
                                                                `start_date` DATE NOT NULL,
                                                                PRIMARY KEY (`card_id`),
                                                                INDEX `fk_library_card_client_idx` (`client_id` ASC) VISIBLE,
                                                                UNIQUE INDEX `id_UNIQUE` (`card_id` ASC) VISIBLE,
                                                                CONSTRAINT `fk_library_card_client`
                                                                    FOREIGN KEY (`client_id`)
                                                                        REFERENCES `library_rest_db`.`client` (`client_id`)
                                                                        ON DELETE NO ACTION
                                                                        ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_rest_db`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_rest_db`.`book` (
                                                        `book_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                        `name` VARCHAR(45) NOT NULL,
                                                        `release_date` DATE NULL,
                                                        `genre` VARCHAR(10) NOT NULL,
                                                        PRIMARY KEY (`book_id`),
                                                        UNIQUE INDEX `id_UNIQUE` (`book_id` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_rest_db`.`record`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_rest_db`.`record` (
                                                          `record_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                          `start_date` DATE NOT NULL,
                                                          `end_date` DATE NULL,
                                                          `book_id` BIGINT NOT NULL,
                                                          `library_card_id` BIGINT NOT NULL,
                                                          PRIMARY KEY (`record_id`),
                                                          UNIQUE INDEX `id_UNIQUE` (`record_id` ASC) VISIBLE,
                                                          INDEX `fk_record_Book1_idx` (`book_id` ASC) VISIBLE,
                                                          INDEX `fk_record_library_card1_idx` (`library_card_id` ASC) VISIBLE,
                                                          CONSTRAINT `fk_record_Book1`
                                                              FOREIGN KEY (`book_id`)
                                                                  REFERENCES `library_rest_db`.`book` (`book_id`)
                                                                  ON DELETE NO ACTION
                                                                  ON UPDATE NO ACTION,
                                                          CONSTRAINT `fk_record_library_card1`
                                                              FOREIGN KEY (`library_card_id`)
                                                                  REFERENCES `library_rest_db`.`library_card` (`card_id`)
                                                                  ON DELETE NO ACTION
                                                                  ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_rest_db`.`author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_rest_db`.`author` (
                                                          `author_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                          `name` VARCHAR(45) NOT NULL,
                                                          `surname` VARCHAR(45) NOT NULL,
                                                          `birthday` DATE NULL,
                                                          PRIMARY KEY (`author_id`),
                                                          UNIQUE INDEX `UNIQUE` (`name` ASC, `surname` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_rest_db`.`books_authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_rest_db`.`books_authors` (
                                                                 `book_book_id` BIGINT NOT NULL,
                                                                 `author_author_id` BIGINT NOT NULL,
                                                                 INDEX `fk_book_has_author_author1_idx` (`author_author_id` ASC) VISIBLE,
                                                                 INDEX `fk_book_has_author_book1_idx` (`book_book_id` ASC) VISIBLE,
                                                                 CONSTRAINT `fk_book_has_author_book1`
                                                                     FOREIGN KEY (`book_book_id`)
                                                                         REFERENCES `library_rest_db`.`book` (`book_id`)
                                                                         ON DELETE NO ACTION
                                                                         ON UPDATE NO ACTION,
                                                                 CONSTRAINT `fk_book_has_author_author1`
                                                                     FOREIGN KEY (`author_author_id`)
                                                                         REFERENCES `library_rest_db`.`author` (`author_id`)
                                                                         ON DELETE NO ACTION
                                                                         ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_rest_db`.`book_accounting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_rest_db`.`book_accounting` (
                                                                   `book_book_id` BIGINT NOT NULL,
                                                                   `total` BIGINT NOT NULL,
                                                                   `available` BIGINT NOT NULL,
                                                                   INDEX `fk_book_accounting_book1_idx` (`book_book_id` ASC) VISIBLE,
                                                                   UNIQUE INDEX `book_book_id_UNIQUE` (`book_book_id` ASC) VISIBLE,
                                                                   PRIMARY KEY (`book_book_id`),
                                                                   CONSTRAINT `fk_book_accounting_book1`
                                                                       FOREIGN KEY (`book_book_id`)
                                                                           REFERENCES `library_rest_db`.`book` (`book_id`)
                                                                           ON DELETE NO ACTION
                                                                           ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;