CREATE DATABASE IF NOT EXISTS ${db.name};

ALTER DATABASE ${db.name} CHARACTER SET utf8;

ALTER DATABASE ${db.name} COLLATE utf8_bin;

GRANT ALL ON ${db.name}.* TO '${db.user}'@'%' IDENTIFIED BY '${db.password}';

GRANT ALL ON ${db.name}.* TO '${db.user}'@'localhost' IDENTIFIED BY '${db.password}';

FLUSH PRIVILEGES;