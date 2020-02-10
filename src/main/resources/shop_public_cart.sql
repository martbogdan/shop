create table cart(
    id bigint not null constraint cart_pkey primary key,
    quantity   integer,
    product_id bigint constraint fkpu4bcbluhsxagirmbdn7dilm5 references products,
    user_id    bigint constraint fkg5uhi8vpsuy0lgloxk2h4w5o6 references users);

alter table cart owner to postgres;

create table categories(
    id bigint not null constraint categories_pkey primary key,
    category_description varchar(255),
    category_name        varchar(255));

alter table categories owner to postgres;

create table comments(
    id  bigint not null
        constraint comments_pkey  primary key,
    date_of_creation timestamp,
    text   varchar(255),
    comment_id   bigint
        constraint fke2dbs56lhmp8fucafi3xvhjyd  references comments,
    product_id  bigint
        constraint fk6uv0qku8gsu6x1r2jkrtqwjtn references products,
    user_user_id  bigint
        constraint fkcc96r34gkial9e3fxphcr3abt  references users);

alter table comments owner to postgres;


create table order_delivery_company(
    id   bigint not null
        constraint order_delivery_company_pkey primary key,
    company_name varchar(255));

alter table order_delivery_company owner to postgres;

create table order_items(
    id  bigint not null
        constraint order_items_pkey primary key,
    quantity    integer,
    order_id    bigint
        constraint fkbioxgbv59vetrxe0ejfubep1w references orders,
    product_id  bigint
        constraint fkocimc7dtr037rh4ls4l95nlfi  references products,
    order_price double precision);

alter table order_items owner to postgres;

create table orders(
    id                        bigint not null
        constraint orders_pkey
            primary key,
    comment                   varchar(255),
    date_of_creation          timestamp,
    user_user_id              bigint
        constraint fk38709695otpk064vi3y92u08s
            references users,
    order_status              integer,
    delivery_address          varchar(255),
    order_delivery_company_id bigint
        constraint fk22b6ro1g79hjg17f74s6pu8qq
            references order_delivery_company
);

alter table orders
    owner to postgres;

create table products
(
    id          bigint not null
        constraint products_pkey
            primary key,
    description varchar(255000),
    name        varchar(255),
    price       double precision,
    quantity    integer,
    category_id bigint
        constraint fkog2rp4qthbtt2lfyhfo32lsw9
            references categories,
    photo       varchar(255)
);

alter table products
    owner to postgres;


create table user_cart
(
    id           bigint not null
        constraint user_cart_pkey
            primary key,
    quantity     integer,
    product_id   bigint
        constraint fkkrrosoq9uogpv7il806ebp69s
            references products,
    user_user_id bigint
        constraint fk9eb74oe4o35bexgjgy5injgh1
            references users
);

alter table user_cart
    owner to postgres;


create table user_role
(
    user_id bigint not null
        constraint fkj345gk1bovqvfame88rcx7yyx
            references users,
    role    varchar(255)
);

alter table user_role
    owner to postgres;


create table users
(
    user_id      bigint  not null
        constraint users_pkey
            primary key,
    active       boolean not null,
    email        varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    password     varchar(255),
    username     varchar(255),
    photo        varchar(255),
    dob          timestamp,
    phone_number varchar(255)
);

alter table users
    owner to postgres;

INSERT INTO public.users (user_id, active, email, first_name, last_name, password, username, photo, dob, phone_number) VALUES (1, true, 'q@q', 'q', 'q', '$2a$10$wv4WZBpFwFywvUDT59GKT.e3rEqLzgdmgZTsza8H1aqYQbGzbZLCq', 'q', null, null, null);
INSERT INTO public.users (user_id, active, email, first_name, last_name, password, username, photo, dob, phone_number) VALUES (2, true, 'a@a', 'a', 'a', '$2a$10$T9yogpNk/k4y6B.e2DhImuQmx2SiqayeW9ZYBy3Bi/mUW8kI/RC1S', 'a', null, null, null);

INSERT INTO public.user_role (user_id, role) VALUES (1, 'USER');
INSERT INTO public.user_role (user_id, role) VALUES (2, 'ADMIN');

INSERT INTO public.categories (id, category_description, category_name) VALUES (1, 'Here you can find only fresh fruits', 'Fruits');
INSERT INTO public.categories (id, category_description, category_name) VALUES (2, 'Here you can find only fresh vegetables', 'Vagetables');
INSERT INTO public.order_delivery_company (id, company_name) VALUES (1, 'Nova Poshta');
INSERT INTO public.order_delivery_company (id, company_name) VALUES (2, 'Ukr Post');
INSERT INTO public.products (id, description, name, price, quantity, category_id, photo) VALUES (1, 'Gold apple', 'Apple', 15.99, 150, 1, null);
INSERT INTO public.products (id, description, name, price, quantity, category_id, photo) VALUES (2, 'Yellow banana', 'Banana', 30, 100, 1, null);
INSERT INTO public.products (id, description, name, price, quantity, category_id, photo) VALUES (3, 'Big fresh ananas', 'Ananas', 100, 120, 1, null);
INSERT INTO public.products (id, description, name, price, quantity, category_id, photo) VALUES (4, 'Red apple', 'Apple Red', 15.99, 138, 1, null);
INSERT INTO public.products (id, description, name, price, quantity, category_id, photo) VALUES (5, 'The grapefruit (Citrus × paradisi) is a subtropical citrus tree known for its relatively large sour to semi-sweet, somewhat bitter fruit. Grapefruit is a citrus hybrid originating in Barbados as an accidental cross between two introduced species – sweet orange (C. sinensis), and pomelo (or shaddock) (C. maxima) – both of which were introduced from Asia in the seventeenth century.[1] When found, it was nicknamed the "forbidden fruit".[2] Frequently, it is misidentified as the very similar parent species, pomelo.[3]  The grape part of the name alludes to clusters of fruit on the tree that often appear similar to grape clusters.[4] The interior flesh is segmented and varies in color from white to yellow to red to pink.', 'Grapefruit', 34.99, 122, 1, null);
INSERT INTO public.products (id, description, name, price, quantity, category_id, photo) VALUES (6, 'Green banana', 'Banana Green', 30, 90, 1, null);
INSERT INTO public.products (id, description, name, price, quantity, category_id, photo) VALUES (7, 'fresh swit orange', 'Mandarin', 26.99, 87, 1, null);
INSERT INTO public.products (id, description, name, price, quantity, category_id, photo) VALUES (8, 'Fresh orange carrot', 'Carrot', 7.99, 100, 2, null);