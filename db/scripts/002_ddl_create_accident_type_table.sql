CREATE TABLE if not exists accident_type (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

comment on table accident_type is 'Типы нарушений';
comment on column accident_type.id is 'Идентификатор типа нарушения';
comment on column accident_type.name is 'Название типа нарушения';