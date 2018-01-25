/* Customer Table */ 
CREATE TABLE customer 
  ( 
     id          SERIAL PRIMARY KEY, 
     firstname   VARCHAR (355) NOT NULL, 
     lastname    VARCHAR (50) NOT NULL, 
     email       VARCHAR (355) NOT NULL, 
     phonenumber INT NOT NULL, 
     password    VARCHAR (355) NOT NULL, 
     createdon   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
     lastupdated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP 
  ); 

/* Item Table */ 
CREATE TABLE item 
  ( 
     id          SERIAL PRIMARY KEY, 
     NAME        VARCHAR (355) NOT NULL, 
     description VARCHAR (355) NOT NULL, 
     unitprice   NUMERIC NOT NULL, 
     imageurl    VARCHAR (355) NOT NULL, 
     createdon   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
     lastupdated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP 
  ); 

/* Order_info Table */ 
CREATE TABLE order_info 
  ( 
     id              SERIAL PRIMARY KEY, 
     customer_id     INT NOT NULL REFERENCES customer (id), 
     order_time      TIMESTAMP NOT NULL, 
     collection_type VARCHAR (355) NOT NULL, 
     createdon       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
     payment_id      INT REFERENCES payment_details (id), 
  ); 

/* Order_item_info Table */ 
CREATE TABLE order_item_info 
  ( 
     order_id INT REFERENCES order_info (id) ON UPDATE CASCADE ON DELETE CASCADE 
     , 
     item_id  INT REFERENCES item (id) ON UPDATE CASCADE, 
     amount   NUMERIC NOT NULL DEFAULT 0, 
     CONSTRAINT item_order_pkey PRIMARY KEY (order_id, item_id) -- explicit pk 
  ); 

/* payment_details Table */ 
CREATE TABLE payment_details 
  ( 
     id              SERIAL PRIMARY KEY, 
     method          VARCHAR (355) NOT NULL, 
     order_id        INT NOT NULL REFERENCES order_info(id), 
     customer_id     INT NOT NULL REFERENCES customer (id), 
     amount          NUMERIC NOT NULL, 
     cardholder_name VARCHAR (355), 
     cardnumber      INT, 
     expiration      VARCHAR (6), 
     cvv_number      VARCHAR (6), 
     createdon       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
     payment_status  VARCHAR (10), 
  ); 