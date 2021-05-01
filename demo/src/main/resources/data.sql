DROP TABLE IF EXISTS user_table;

CREATE TABLE user_table (
	id 		INT AUTO_INCREMENT PRIMARY KEY,
	login 	VARCHAR(128),
    password 	VARCHAR(254),
	email 	VARCHAR(254),
	name 	VARCHAR(128),
	surname VARCHAR(128),
	patronymic VARCHAR(128)
);

INSERT INTO user_table (id, login, password, email, name, surname, patronymic) VALUES
  (1, 'kant', 'kritika', 'kant@mail.ru', 'Иммануил', 'Кант', ''),
  (2, 'gegel', 'dialektica', 'gegel@mail.ru', 'Вильгельм', 'Гегель', 'Фридрих')
