CREATE TABLE IF NOT EXISTS `accounts` (
	`username` VARCHAR(500) NOT NULL UNIQUE,
	`password` VARCHAR(500) NOT NULL,
	PRIMARY KEY (`username`)
) ENGINE=InnoDB	DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `user_roles` (
	`user_name` VARCHAR(500) NOT NULL,
	`user_role` VARCHAR(500) NOT NULL,
	FOREIGN KEY(`user_name`) REFERENCES accounts(username)
) ENGINE=InnoDB	DEFAULT CHARSET=utf8mb4;