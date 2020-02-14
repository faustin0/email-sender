--CREATE TABLE IF NOT EXISTS EMAILS (
--    id INTEGER IDENTITY PRIMARY KEY,
--    sender varchar(255) NOT NULL,
--    subject varchar(255) NOT NULL,
--    to varchar(255) NOT NULL,
--    created date NOT NULL,
--    body text
--);

CREATE TABLE IF NOT EXISTS EMAILS
(
    id serial AUTO_INCREMENT NOT NULL,
    sender character varying  NOT NULL,
    to character varying NOT NULL,
    subject character varying NOT NULL,
    body character varying,
    created datetime NOT NULL
);