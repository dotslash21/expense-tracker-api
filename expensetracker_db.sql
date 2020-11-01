-- clear existing
drop database expensetrackerdb;
drop user expensetracker;

-- create new user
create user expensetracker with password 'password';

-- create new database
create database expensetrackerdb with template=template0 owner=expensetracker;

-- connect to database
\connect expensetrackerdb;

-- grant required privileges
alter default privileges grant all on tables to expensetracker;
alter default privileges grant all on sequences to expensetracker;

-- create users table
create table et_users(
  user_id integer primary key not null,
  first_name varchar(20) not null,
  last_name varchar(20) not null,
  email varchar(30) not null,
  password text not null
);

-- create categories table
create table et_categories(
  category_id integer primary key not null,
  user_id integer not null,
  title varchar(20) not null,
  description varchar(50) not null
);

-- set foreign key constraint on user_id in et_categories.
alter table et_categories add constraint cat_users_fk
foreign key (user_id) references et_users(user_id);

-- create transactions table
create table et_transactions(
  transaction_id integer primary key not null,
  category_id integer not null,
  user_id integer not null,
  amount numeric(10, 2) not null,
  note varchar(50) not null,
  transaction_date bigint not null
);

-- set foreign key constraint on category_id in et_transactions.
alter table et_transactions add constraint trans_cat_fk
foreign key (category_id) references et_categories(category_id);

-- set foreign key constraint on user_id in et_transactions.
alter table et_transactions add constraint trans_user_fk
foreign key (user_id) references et_users(user_id);

-- create sequences
create sequence et_users_seq increment 1 start 1;
create sequence et_categories_seq increment 1 start 1;
create sequence et_transactions_seq increment 1 start 1000;
