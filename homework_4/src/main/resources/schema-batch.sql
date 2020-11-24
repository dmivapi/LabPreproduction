-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

--CREATE SCHEMA IF NOT EXISTS `UserFinance` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `UserFinance` ;


-- -----------------------------------------------------
-- Table `UserFinance`.`user_balance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `UserFinance`.`user_balance` ;

CREATE TABLE IF NOT EXISTS `UserFinance`.`user_balance` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `balance` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`))
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
INSERT INTO `UserFinance`.`user_balance` (`id`, `name`, `balance`) VALUES (1, 'John Smith', 10);
INSERT INTO `UserFinance`.`user_balance` (`id`, `name`, `balance`) VALUES (2, 'Ronald Regan', 888784);
INSERT INTO `UserFinance`.`user_balance` (`id`, `name`, `balance`) VALUES (3, 'Yury Gagarin', 77421);
INSERT INTO `UserFinance`.`user_balance` (`id`, `name`, `balance`) VALUES (4, 'Poor student', 9);
INSERT INTO `UserFinance`.`user_balance` (`id`, `name`, `balance`) VALUES (5, 'Kevin Kline', 842711);
INSERT INTO `UserFinance`.`user_balance` (`id`, `name`, `balance`) VALUES (6, 'Jack Waldrip', 5);
INSERT INTO `UserFinance`.`user_balance` (`id`, `name`, `balance`) VALUES (7, 'Barak Obama', 27713);
INSERT INTO `UserFinance`.`user_balance` (`id`, `name`, `balance`) VALUES (8, 'Doug Fisher', 3);

COMMIT;

