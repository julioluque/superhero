DROP TABLE IF EXISTS HERO;

CREATE TABLE HERO (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  nombre VARCHAR(150) UNIQUE NOT NULL,
  saga VARCHAR(20) NOT NULL,
  estado BOOLEAN 
);

INSERT INTO HERO (nombre, saga, estado) VALUES
  ('Spiderman', 'Marvel', true),
  ('Batman', 'DC', true),
  ('Thor', 'Marvel', false),
  ('Superman', 'DC', false);