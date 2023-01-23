CREATE TABLE if not exists accident_status (
  id SERIAL PRIMARY KEY,
  status VARCHAR NOT NULL
);

comment on table accident_status is 'Статусы нарушения';
comment on column accident_status.id is 'Идентификатор статуса';
comment on column accident_status.status is 'Название статьи';