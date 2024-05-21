-- Creazione della tabella libri
CREATE TABLE `libri`
(
    `id`                int NOT NULL AUTO_INCREMENT,
    `titolo`            varchar(255) DEFAULT NULL,
    `autore`            varchar(255) DEFAULT NULL,
    `genere`            varchar(255) DEFAULT NULL,
    `anno`              int          DEFAULT NULL,
    `immagineCopertina` blob,
    `prezzo` double NOT NULL,
    `disponibilita`     enum('DISPONIBILE','NON DISPONIBILE','VENDUTO') DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Inserimento di dati nella tabella libri
INSERT INTO `libri` (`titolo`, `autore`, `genere`, `anno`, `prezzo`, `disponibilita`)
VALUES ('Il nome della rosa', 'Umberto Eco', 'Mistero', 1980, 10.99, 'DISPONIBILE'),
       ('1984', 'George Orwell', 'Fantascienza', 1949, 8.99, 'NON DISPONIBILE');

-- Creazione della tabella utenti
CREATE TABLE `utenti`
(
    `id`       int          NOT NULL AUTO_INCREMENT,
    `username` varchar(50)  NOT NULL,
    `email`    varchar(100) NOT NULL,
    `password` varchar(100) NOT NULL,
    `ruolo`    enum('UTENTE_ADMIN', 'UTENTE_REGISTRATO') NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Inserimento di dati nella tabella utenti
INSERT INTO `utenti` (`username`, `email`, `password`, `ruolo`)
VALUES ('utente1', 'utente1@example.com', 'password1', 'UTENTE_REGISTRATO'),
       ('utente2', 'utente2@example.com', 'password2', 'UTENTE_ADMIN');
