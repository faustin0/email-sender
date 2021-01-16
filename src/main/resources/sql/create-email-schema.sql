--CREATE TABLE IF NOT EXISTS EMAILS (
--    id INTEGER IDENTITY PRIMARY KEY,
--    sender varchar(255) NOT NULL,
--    subject varchar(255) NOT NULL,
--    to varchar(255) NOT NULL,
--    created date NOT NULL,
--    body text
--);

DROP TABLE IF EXISTS "emails";

CREATE TABLE "emails"
(
    id serial AUTO_INCREMENT NOT NULL,
    from_address character varying  NOT NULL,
    to_address character varying NOT NULL,
    subject character varying NOT NULL,
    body character varying,
    created datetime NOT NULL
);