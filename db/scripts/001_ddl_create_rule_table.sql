CREATE TABLE if not exists rule (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

comment on table rule is 'Статьи нарушения';
comment on column rule.id is 'Идентификатор статьи нарушения';
comment on column rule.name is 'Название статьи нарушения';