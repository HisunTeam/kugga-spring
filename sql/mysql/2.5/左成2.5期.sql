-- 添加邀请加入公会模板
UPDATE duke_message_template
SET subject  = '邀请加入公会',
    template = 'You''re invited to join [{}] guild.'
WHERE message_code = '0003';

update duke_league_label
set label_name='Popular',
    label_desc='Find new dates, new jobs, various rewards,<br>with your real verified profile.'
where label_name = 'KuggaDuke';
