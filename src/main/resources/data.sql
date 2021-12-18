-- MEMBER_ROLE

INSERT INTO BAND_ROLE (title)
values ('LEADER');
INSERT INTO BAND_ROLE (title)
values ('MEMBER');
--
-- -- USER
INSERT INTO USER (CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, EMAIL, LOGIN_TYPE,
                  HASH_PASSWORD)
VALUES ('system', '2021-12-14 14:54:20.000000', 'system', '2021-12-14 14:54:27.000000', 'test@naver.com', 'D',
        'asdfadsfdsafdsaf');
