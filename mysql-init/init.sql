CREATE DATABASE IF NOT EXISTS pocketshop_users;
CREATE DATABASE IF NOT EXISTS pocketshop_cards;
CREATE DATABASE IF NOT EXISTS pocketshop_buys;
CREATE DATABASE IF NOT EXISTS pocketshop_notification;
CREATE DATABASE IF NOT EXISTS pocketshop_chat;

GRANT ALL PRIVILEGES ON pocketshop_users.* TO 'pocketuser'@'%';
GRANT ALL PRIVILEGES ON pocketshop_cards.* TO 'pocketuser'@'%';
GRANT ALL PRIVILEGES ON pocketshop_buys.* TO 'pocketuser'@'%';
GRANT ALL PRIVILEGES ON pocketshop_notification.* TO 'pocketuser'@'%';
GRANT ALL PRIVILEGES ON pocketshop_chat.* TO 'pocketuser'@'%';