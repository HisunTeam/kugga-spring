package com.hisun.kugga.duke.league.service.impl;

import com.hisun.kugga.duke.league.dal.mysql.TaskLeagueAuthMapper;
import com.hisun.kugga.duke.league.service.TaskLeagueAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-09-13 10:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class TaskLeagueAuthServiceImpl implements TaskLeagueAuthService {

    TaskLeagueAuthMapper taskLeagueAuthMapper;

}
