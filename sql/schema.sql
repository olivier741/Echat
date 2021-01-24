#Create database
CREATE database echat;

use echat;
#Create user table
CREATE TABLE user(
id INT NOT NULL PRIMARY KEY auto_increment comment'user id',
name VARCHAR(100) comment'User Name',
password VARCHAR(100) comment'user password'
)ENGINE = innoDB CHARSET = utf8;

#Login logout information form
CREATE TABLE login_info (
   `id` INT NOT NULL AUTO_INCREMENT,
   `user_id` INT NULL,
   `user_name` VARCHAR(45) NULL,
   `status` TINYINT NULL COMMENT' 1. Go online, 2. Go offline',
   `create_time` DATETIME NULL,
   PRIMARY KEY (`id`)) default charset = utf8;

#Chat content table
CREATE TABLE message_record (
   `id` INT NOT NULL AUTO_INCREMENT,
   `user_id` INT NULL,
   `user_name` VARCHAR(45) NULL,
   `message_type` TINYINT NULL COMMENT' 1. Text, 2. Picture',
   `content` VARCHAR(256) NULL,
   `create_time` VARCHAR(45) NULL,
   PRIMARY KEY (`id`))default charset = utf8;