create table providers (
	id bigserial primary key,
	name varchar not null,
	address varchar not null
);

create table expenditures (
	id bigserial primary key,
	name varchar not null,
	unit varchar not null,
	quantity decimal
);

create table orders (
	id bigserial primary key,
	expenditure_id bigint not null,
	provider_id bigint not null,
	quantity decimal not null,
	cost decimal not null,
	transaction_timestamp timestamp not null,
	foreign key (expenditure_id) references expenditures(id),
	foreign key (provider_id) references providers(id)
);

create table tags (
	id bigserial primary key,
    name varchar not null
);

create table expenditures_to_tags (
	expenditure_id bigint,
	tag_id bigint,
    foreign key (expenditure_id) references expenditures(id),
    foreign key (tag_id) references tags(id),
    PRIMARY KEY (expenditure_id, tag_id)
)

-- useful queries
-- ALTER SEQUENCE providers_id_seq RESTART WITH 1;
-- ALTER SEQUENCE expenditures_id_seq RESTART WITH 1;
-- ALTER SEQUENCE orders_id_seq RESTART WITH 1;
-- ALTER SEQUENCE tags_id_seq RESTART WITH 1;

-- drop
-- drop table providers;
-- drop table expenditures_to_tags;
-- drop table tags;
-- drop table orders;
-- drop table expenditures;
