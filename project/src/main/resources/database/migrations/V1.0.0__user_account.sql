CREATE TABLE IF NOT EXISTS `user_account`(
    `id` BIGINT UNSIGNED AUTO_INCREMENT,
    `uuid` VARCHAR(36),
    `username` VARCHAR(255),
    `password` VARCHAR(255),
    `role` VARCHAR(255),
    `created_at` TIMESTAMP NULL,
    `updated_at` TIMESTAMP NULL,
    `deleted_at` TIMESTAMP NULL,
    PRIMARY KEY(`id`)
);
