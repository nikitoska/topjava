DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

  INSERT INTO meals (user_id,date_time, description, calories) VALUES
  (100000,'2016-06-28 10:00:00','завтрак','500'),
  (100000,'2016-06-28 12:00:00','обед','500'),
  (100000,'2016-06-28 17:00:00','ужин','500'),
  (100000,'2016-06-29 10:00:00','завтрак','500'),
  (100000,'2016-06-29 12:00:00','обед','500'),
  (100001,'2016-06-28 10:00:00','завтрак','500'),
  (100001,'2016-06-28 12:00:00','обед','500');

