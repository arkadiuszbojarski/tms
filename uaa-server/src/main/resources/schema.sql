CREATE TABLE IF NOT EXISTS `accounts` (
	`username` VARCHAR(500) NOT NULL,
	`password` VARCHAR(500) NOT NULL,
	PRIMARY KEY (`username`)
);

CREATE TABLE IF NOT EXISTS `user_roles` (
	`user_name` VARCHAR(500) NOT NULL,
	`user_role` VARCHAR(500) NOT NULL,
	PRIMARY KEY(`user_name`),
	FOREIGN KEY(`user_name`) REFERENCES accounts(username)
);