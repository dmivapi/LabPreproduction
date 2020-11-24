-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema UserFinance
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema UserFinance
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `UserFinance` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `UserFinance` ;

-- -----------------------------------------------------
-- Table `UserFinance`.`user_balance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `UserFinance`.`user_balance` ;

CREATE TABLE IF NOT EXISTS `UserFinance`.`user_balance` (
  `email` VARCHAR(35) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `balance` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `UserFinance`.`user_balance`
-- -----------------------------------------------------
START TRANSACTION;
USE `UserFinance`;
INSERT INTO `UserFinance`.`user_balance` (`email`, `name`, `balance`) VALUES ('js@gmail.com', 'John Smith', 10);
INSERT INTO `UserFinance`.`user_balance` (`email`, `name`, `balance`) VALUES ('rr@gmail.com', 'Ronald Regan', 888784);
INSERT INTO `UserFinance`.`user_balance` (`email`, `name`, `balance`) VALUES ('yg@gmail.com', 'Yury Gagarin', 77421);
INSERT INTO `UserFinance`.`user_balance` (`email`, `name`, `balance`) VALUES ('dpua1611@gmail.com', 'Poor student', 9);
INSERT INTO `UserFinance`.`user_balance` (`email`, `name`, `balance`) VALUES ('kk@gmail.com', 'Kevin Kline', 842711);
INSERT INTO `UserFinance`.`user_balance` (`email`, `name`, `balance`) VALUES ('dmivapi@gmail.com', 'Jack Waldrip', 5);
INSERT INTO `UserFinance`.`user_balance` (`email`, `name`, `balance`) VALUES ('bo@gmail.com', 'Barak Obama', 27713);
INSERT INTO `UserFinance`.`user_balance` (`email`, `name`, `balance`) VALUES ('pischanetsky@gmail.com', 'Doug Fisher', 3);

COMMIT;

