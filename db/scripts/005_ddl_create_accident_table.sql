CREATE TABLE if not exists accident (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  description_accident VARCHAR NOT NULL,
  car_number VARCHAR UNIQUE NOT NULL,
  type_id INT NOT NULL REFERENCES accident_type(id),
  address_accident VARCHAR NOT NULL,
  status_id INT NOT NULL REFERENCES accident_status(id),
  photo BYTEA,
  date_accident TIMESTAMP NOT NULL
);

comment on table accident is 'Нарушения';
comment on column accident.id is 'Идентификатор нарушения';
comment on column accident.name is 'Название нарушения';
comment on column accident.description_accident is 'Описание нарушения';
comment on column accident.car_number is 'Номер автомобиля';
comment on column accident.type_id is 'Тип нарушения';
comment on column accident.address_accident is 'Адрес нарушения';
comment on column accident.status_id is 'Статус нарушения';
comment on column accident.photo is 'Фото нарушения';
comment on column accident.date_accident is 'Время нарушения';