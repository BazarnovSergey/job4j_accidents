CREATE TABLE if not exists accident_rule (
   accident_id INT REFERENCES accident(id),
   rule_id INT REFERENCES rule(id),
   PRIMARY KEY(accident_id,rule_id)
);

comment on table accident_rule is 'Связывающая таблица образующая связь 'многие ко многим'
между таблиами accident и rule';
comment on column accident_rule.accident_id is 'Идентификатор инцидента';
comment on column accident_rule.rule_id is 'Идентификатор статьи нарушения';