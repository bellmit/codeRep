drop table if exists photo;
CREATE TABLE photo (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) COMMENT '����',
    photo blob COMMENT '��Ƭ'
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;