insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$IUHMgQqL0NKuzJlUTJjGxufS0AbRF4KS2LT1MBupclYT.XTnJ2PxS',
(select id from authorities where authority = 'ROLE_ADMIN'));