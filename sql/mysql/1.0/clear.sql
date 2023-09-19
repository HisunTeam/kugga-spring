show VARIABLES like 'autocommit';
set autocommit = off;
ROLLBACK;
commit;

select *
from system_menu
where deleted = TRUE;
select *
from system_menu
where deleted = FALSE;
select *
from system_menu;
select *
from system_role_menu
where menu_id not in (select id from system_menu where deleted = FALSE);
select *
from system_role_menu
where role_id not in (select id from system_role where deleted = FALSE);

select *
from system_user_role;
select *
from system_users
where deleted = TRUE;
select *
from system_users
where deleted = FALSE;
select *
from system_user_role
WHERE user_id not in (select id from system_users where deleted = FALSE);
select *
from system_user_role
WHERE role_id not in (select id from system_role where deleted = FALSE);

select *
from system_role;
select *
from system_role
where deleted = TRUE;
select *
from system_role
where deleted = FALSE;


select *
from system_dict_type
where deleted = TRUE;
select *
from system_dict_type
where type like 'pay%';

select *
from system_dict_data
WHERE deleted = TRUE;
select *
from system_dict_data
where dict_type like 'pay%';

select *
from system_menu
where deleted = FALSE;
select *
from system_menu;

select *
from system_operate_log;
select *
from system_login_log;
select *
from system_sms_code;
select *
from system_sms_log;
TRUNCATE TABLE system_operate_log;
truncate TABLE system_login_log;
truncate TABLE system_sms_code;
truncate TABLE system_sms_log;
