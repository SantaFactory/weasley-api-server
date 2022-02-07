-- MEMBER_ROLE
INSERT INTO BAND_ROLE (title)
values ('LEADER');
INSERT INTO BAND_ROLE (title)
values ('MEMBER');


-- AUTH
INSERT INTO AUTH (TITLE) VALUES ('ROLE_ADMIN');
INSERT INTO AUTH (TITLE) VALUES ('ROLE_GUEST');
INSERT INTO AUTH (TITLE) VALUES ('ROLE_USER');

-- USER
INSERT INTO USER (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, EMAIL, LOGIN_TYPE, NAME, USER_KEY)
VALUES ('system', '2021-12-14 14:54:20.000000', 'system', '2021-12-14 14:54:27.000000', 'admin', 'D', 'admin', '11111');

INSERT INTO USER_AUTH values (1, 'ROLE_ADMIN')