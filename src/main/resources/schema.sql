CREATE TABLE IF NOT EXISTS TACO_ORDER (
    id identity,
    order_name varchar(50) not null,
    delivery_Street varchar(50) not null,
    delivery_City varchar(50) not null,
    delivery_State varchar(2) not null,
    delivery_Zip varchar(10) not null,
    cc_number varchar(16) not null,
    cc_expiration varchar(16) not null,
    cc_cvv varchar(3) not null,
    placed_at timestamp not null
);

CREATE TABLE IF NOT EXISTS TACO (
    id identity,
    name varchar(50) not null,
    taco_order bigint not null,
    taco_order_key bigint not null,
    created_at timestamp not null
);

CREATE TABLE IF NOT EXISTS INGREDIENT_REF (
    ingredient varchar(4) not null,
    taco bigint not null,
    taco_key bigint not null
);

CREATE TABLE IF NOT EXISTS INGREDIENT (
    id varchar(4) PRIMARY KEY not null,
    name varchar(25) not null,
    type varchar(10) not null
);

ALTER TABLE TACO
    ADD FOREIGN KEY (taco_order) REFERENCES TACO_ORDER(id);

ALTER TABLE INGREDIENT_REF
    ADD FOREIGN KEY (ingredient) REFERENCES INGREDIENT(id);