-- -------简历经历认证-------
alter table duke_resume_experience
    add cert_flag varchar(2) null comment '经历认证 0-待审核 1-审核通过 2-审核拒绝';
alter table duke_resume_experience
    add certification varchar(4096) null comment '经历认证信息';
alter table duke_resume_experience
    add remark varchar(255) null comment '备注';
alter table duke_resume_experience
    add suggestion varchar(255) null comment '审核意见';
-- -------------------------

-- -----菜单配置-------------
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2041, '经历审核', '', 1, 5, 0, '/experience', '#', null, 0, true, true, '1', '2022-10-20 09:17:03', '1',
        '2022-10-20 09:17:03', false);
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2042, '教育经历审核', '', 2, 1, 2041, 'school', '#', 'operation/resumeExperience/index.vue', 0, true, true, '1',
        '2022-10-20 09:20:33', '1', '2022-10-20 09:21:22', false);
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible,
                         keep_alive, creator, create_time, updater, update_time, deleted)
VALUES (2043, '工作经历审核', '', 2, 2, 2041, 'workExperience', '#', 'operation/resumeExperience/workExperience/index.vue', 0,
        true, true, '1', '2022-10-24 09:54:55', '1', '2022-10-24 09:54:55', false);

INSERT INTO system_role_menu ( role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id) VALUES ( 114, 2041, '1', '2022-10-24 10:51:05', '1', '2022-10-24 10:51:05', false, 0);
INSERT INTO system_role_menu ( role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id) VALUES ( 114, 2042, '1', '2022-10-24 10:51:05', '1', '2022-10-24 10:51:05', false, 0);
INSERT INTO system_role_menu ( role_id, menu_id, creator, create_time, updater, update_time, deleted, tenant_id) VALUES ( 114, 2043, '1', '2022-10-24 10:51:05', '1', '2022-10-24 10:51:05', false, 0);
-- -------------------------
