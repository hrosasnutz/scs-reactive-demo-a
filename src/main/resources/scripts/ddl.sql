CREATE TABLE IF NOT EXISTS transactions (
    id uuid DEFAULT gen_random_uuid(),
    status varchar(255) NULL,
    description text NULL,
    amount decimal(12, 4) NULL,
    timestamp timestamp(6) NULL,
    "version" int8 NULL,
    CONSTRAINT transactions_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS customers (
    id uuid DEFAULT gen_random_uuid(),
    full_name text NULL,
    timestamp timestamp(6) NULL,
    "version" int8 NULL,
    CONSTRAINT customers_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS accounts (
    id uuid DEFAULT gen_random_uuid(),
    account_number text NULL,
    timestamp timestamp(6) NULL,
    "version" int8 NULL,
    CONSTRAINT accounts_pkey PRIMARY KEY (id)
);