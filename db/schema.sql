CREATE TABLE "user"
(
    id                int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_name         varchar(64) NOT NULL UNIQUE,
    password          varchar(64) NOT NULL,
    registration_time TIMESTAMP
);

CREATE TABLE vehicle
(
    vehicle_id  int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    manufacture varchar(64)    NOT NULL,
    engineType  varchar(64)    NOT NULL,
    model       varchar(64)    NOT NULL,
    price       decimal(10, 2) NOT NULL,
    age         int check ( age > 0 ),
    weight      int check (weight > 0 )
);

select * from "public"."user";



