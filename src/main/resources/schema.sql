--CREATE TABLE weather (id NUMBER PRIMARY KEY, date TIMESTAMP , temperature NUMBER );
CREATE TABLE weather (
                         id INT NOT NULL AUTO_INCREMENT,
                         millis BIGINT NOT NULL,
                         temperature NUMBER NOT NULL,
                         fecha timestamp not null,
                         PRIMARY KEY (id)
);
--INSERT INTO weather(fecha, temperatura) VALUES ('2019-09-05 18:10',30);

/*
INSERT INTO weather(fecha, temperatura) VALUES (sysdate ,20);
INSERT INTO weather(fecha, temperatura) VALUES (sysdate ,40);

INSERT INTO weather(fecha, temperatura) VALUES (TO_TIMESTAMP('2019-09-05 18:10','YYYY-MM-DD HH:MI'),30);
INSERT INTO weather(fecha, temperatura) VALUES (TO_TIMESTAMP('2019-09-05 18:29','YYYY-MM-DD HH:MI'),45.5);
INSERT INTO weather(fecha, temperatura) VALUES (TO_TIMESTAMP('2019-09-05 18:59','YYYY-MM-DD HH:MI'),30.5);
INSERT INTO weather(fecha, temperatura) VALUES (TO_TIMESTAMP('2019-09-05 19:00','YYYY-MM-DD HH:MI'),11);
INSERT INTO weather(fecha, temperatura) VALUES (TO_TIMESTAMP('2019-09-05 19:20','YYYY-MM-DD HH:MI'),22.5);
*/

