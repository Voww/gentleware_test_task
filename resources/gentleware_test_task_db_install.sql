DROP SCHEMA IF EXISTS gentleware_test_task;
CREATE SCHEMA IF NOT EXISTS gentleware_test_task DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_general_ci';
USE gentleware_test_task;

-- DROP TABLE client;
CREATE TABLE IF NOT EXISTS client (
  id SERIAL,
  email VARCHAR(64) NOT NULL UNIQUE,
  first_name VARCHAR(64),
  last_name VARCHAR(64),
  PRIMARY KEY (id)
);

-- DROP TABLE account;
CREATE TABLE IF NOT EXISTS account (
  id SERIAL,
  code VARCHAR(64) NOT NULL UNIQUE,
  alias VARCHAR(64),
  amount DECIMAL(10,2) NOT NULL,
  description VARCHAR(256),
  PRIMARY KEY (id)
);

DROP TABLE payment;
CREATE TABLE IF NOT EXISTS payment (
  id SERIAL,
  date DATETIME DEFAULT NOW(),
  payment_amount DECIMAL(10,2) NOT NULL,
  from_account_amount DECIMAL(10,2) NOT NULL,
  to_account_amount DECIMAL(10,2) NOT NULL,
  from_account_id BIGINT UNSIGNED NOT NULL,
  to_account_id BIGINT UNSIGNED NOT NULL,
  comment VARCHAR(256),
  PRIMARY KEY (id),
  FOREIGN KEY (from_account_id)
    REFERENCES account(id) ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (to_account_id)
  REFERENCES account(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- DROP TABLE client_account;
CREATE TABLE IF NOT EXISTS client_account (
  client_id BIGINT UNSIGNED NOT NULL,
  account_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (client_id, account_id),
  FOREIGN KEY (client_id)
    REFERENCES client(id) ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (account_id)
    REFERENCES account(id) ON DELETE RESTRICT ON UPDATE CASCADE
);