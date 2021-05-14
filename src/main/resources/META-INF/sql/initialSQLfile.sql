CREATE TABLE produse (idProdus INT NOT NULL AUTO_INCREMENT, numeProdus VARCHAR(50) NOT NULL, stoc INT NOT NULL, PRIMARY KEY (idProdus))

CREATE TABLE comenzi (idComanda INT NOT NULL AUTO_INCREMENT, produse INT NOT NULL, numeClient VARCHAR(50) NOT NULL, statusComanda VARCHAR(50) NOT NULL, PRIMARY KEY (idComanda), FOREIGN KEY (produse) REFERENCES produse(idProdus))

INSERT INTO Produse (numeProdus, stoc) VALUES ('Cana', 5);
INSERT INTO Produse (numeProdus, stoc) VALUES ('Pahar', 15);
INSERT INTO Produse (numeProdus, stoc) VALUES ('Ciocolata', 50);
INSERT INTO Produse (numeProdus, stoc) VALUES ('Apa', 25);