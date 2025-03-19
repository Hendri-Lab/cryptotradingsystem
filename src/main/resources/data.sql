CREATE TABLE users (
                       user_id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wallets (
                         wallet_id INT PRIMARY KEY AUTO_INCREMENT,
                         user_id INT NOT NULL,
                         username VARCHAR(255) NOT NULL,
                         currency VARCHAR(10),
                         balance DECIMAL(18, 8),
                         FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE transactions (
                              transaction_id INT PRIMARY KEY AUTO_INCREMENT,
                              user_id INT,
                              transaction_type ENUM('buy', 'sell'),
                              crypto_pair VARCHAR(10),
                              amount DECIMAL(18, 8), -- Amount of crypto
                              price DECIMAL(18, 8), -- Price at the time of transaction
                              total DECIMAL(18, 8), -- Total value of transaction (amount * price)
                              timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE crypto_prices (
                               crypto_pair VARCHAR(10) PRIMARY KEY,  -- 'ETHUSDT', 'BTCUSDT'
                               price DECIMAL(18, 8) NOT NULL,
                               timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (user_id, username, email, password)
VALUES (1, 'test', 'test@example.com', 'test123');

INSERT INTO wallets (user_id, username, currency, balance)
VALUES
    (1, 'test', 'USDT', 50000),
    (1, 'test', 'ETH', 0),
    (1, 'test', 'BTC', 0);