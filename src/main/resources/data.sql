USE light;

-- ==============================
-- 用户
-- ==============================
DELETE
FROM
	t_user;

ALTER TABLE t_user AUTO_INCREMENT = 1;

-- light: light_up
INSERT INTO `t_user`
VALUES
	(
		'1',
		'light',
		'$2a$10$iseIarnUOLvG0rYRdTW8DegosXXflrNJQh694tH92q51Qd2DtC3OS',
		'程序猿',
		'1',
		'2017-08-01 12:00:00',
		'1'
	);

INSERT INTO `t_user`
VALUES
	(
		'2',
		'test',
		'$2a$10$7EVUgpBDnxWcAX09Z/g9/.uFrUziIO.o7lxWi0XfTAmlbtSERkfYy',
		'普通测试用户',
		'1',
		'2017-08-01 12:00:00',
		'4'
	);

-- ==============================
-- 角色
-- ==============================
DELETE
FROM
	t_role;

ALTER TABLE t_role AUTO_INCREMENT = 1;

INSERT INTO t_role
VALUES
	(1, 'DEV', '开发者');

INSERT INTO t_role
VALUES
	(
		2,
		'ADMIN',
		'系统管理员'
	);

INSERT INTO t_role
VALUES
	(
		3,
		'TESTDEV',
		'测试开发工程师'
	);

INSERT INTO t_role
VALUES
	(4, 'USER', '普通用户');

-- ==============================
-- 资源
-- ==============================
DELETE
FROM
	t_resource;

ALTER TABLE t_resource AUTO_INCREMENT = 1;

INSERT INTO `t_resource`
VALUES
	('1', 'GET', '/test/admin');

INSERT INTO `t_resource`
VALUES
	('2', 'POST', '/test/user');

INSERT INTO `t_resource`
VALUES
	('3', 'GET', '/test/user');

-- ==============================
-- 角色资源关联
-- ==============================
DELETE
FROM
	t_role_resource;

INSERT INTO `t_role_resource`
VALUES
	('2', '1');

INSERT INTO `t_role_resource`
VALUES
	('2', '2');

INSERT INTO `t_role_resource`
VALUES
	('2', '3');

INSERT INTO `t_role_resource`
VALUES
	('4', '3');