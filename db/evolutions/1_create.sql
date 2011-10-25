# Orders schema
 
# --- !Ups

CREATE SEQUENCE order_seq;
CREATE SEQUENCE delivery_seq;
CREATE SEQUENCE address_seq;
CREATE SEQUENCE pricescheme_seq;


CREATE TABLE Order (
    id bigint(20) NOT NULL DEFAULT nextval('order_seq'),
    senderEmail varchar(100) NOT NULL,
    senderName varchar(50) NOT NULL,
    senderAddress bigint(20) references Address(id),
    recipientName varchar(50) NOT NULL,
    recipientPhone varchar(10) NOT NULL,
    created_at timestamp NOT NULL,
    PRIMARY KEY (id)
);
 
ALTER SEQUENCE order_seq OWNED BY Order.id;

CREATE TABLE Delivery (
    id bigint(20) NOT NULL DEFAULT nextval('delivery_seq'),
    order bigint(20) references Order(id),
    cardNote varchar(200),
    specialInstructions  varchar(200),
    deliveryDate date NOT NULL,
    priceScheme bigint(20) references PriceScheme(id),
    orderPlaced boolean NOT NULL,
    amountSpent money,
);

ALTER SEQUENCE delivery_seq OWNED BY Delivery.id;

CREATE TABLE Address (
    id bigint(20) NOT NULL DEFAULT nextval('address_seq'),
    address1 varchar(30) NOT NULL,
    address2 varchar(30),
    city varchar(30) NOT NULL,
    state varchar(2) NOT NULL,
    country varchar(2) NOT NULL,
    zip varchar(6) NOT NULL
);

ALTER SEQUENCE address_seq OWNED BY Address.id;
 
CREATE TABLE PriceScheme (
    id bigint(20) NOT NULL DEFAULT nextval('pricescheme_seq'),
    name varchar(255) NOT NULL,
    chargePrice money NOT NULL, 
    maxSpend money NOT NULL,
    minSpend money NOT NULL
); 

ALTER SEQUENCE pricescheme_seq OWNED BY PriceScheme.id;
 
# --- !Downs
 
DROP TABLE Order;
DROP TABLE Delivery;
DROP TABLE Address;
DROP TABLE PriceScheme;
