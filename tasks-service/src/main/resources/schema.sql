CREATE TABLE IF NOT EXISTS `tasks` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(500) NOT NULL,
	`completed` BOOLEAN NOT NULL,
	`created` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`start` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`deadline` TIMESTAMP NULL,
	`author` VARCHAR(500) NOT NULL,
	PRIMARY KEY (`ID`)
);