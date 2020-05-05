create database crcommunication;
CREATE USER if not exists 'cr_user_migration'@'localhost' IDENTIFIED BY 'qwerty#123456';
CREATE USER if not exists 'cr_user'@'localhost' IDENTIFIED BY 'qwerty#123456';
GRANT DELETE,CREATE, DROP, ALTER, INDEX, SELECT,INSERT,UPDATE ON crcommunication.* to 'cr_user_migration'@'localhost';
GRANT SELECT,INSERT,UPDATE,DELETE ON crcommunication.* to 'cr_user'@'localhost';
