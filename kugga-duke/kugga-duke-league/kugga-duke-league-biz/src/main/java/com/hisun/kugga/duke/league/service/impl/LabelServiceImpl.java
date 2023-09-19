package com.hisun.kugga.duke.league.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.hisun.kugga.duke.enums.NumberEnum;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueLabelDO;
import com.hisun.kugga.duke.league.dal.mysql.LeagueLabelMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueMapper;
import com.hisun.kugga.duke.league.service.LabelService;
import com.hisun.kugga.duke.league.vo.*;
import com.hisun.kugga.duke.system.api.params.SystemParamsApi;
import com.hisun.kugga.duke.system.api.params.dto.SystemParamsRespDTO;
import com.hisun.kugga.framework.common.pojo.PageParam;
import com.hisun.kugga.framework.common.pojo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zuocheng
 */
@Service
@Slf4j
public class LabelServiceImpl implements LabelService {

    @Resource
    private LeagueLabelMapper leagueLabelMapper;

    @Resource
    private LeagueMapper leagueMapper;

    @Resource
    private SystemParamsApi systemParamsApi;

    @Override
    public List<SimpleLabelsVO> simpleLabels() {
        List<LeagueLabelDO> iLabels = leagueLabelMapper.simpleLabels();

        List<SimpleLabelsVO> labels = new ArrayList<>(iLabels.size());
        iLabels.stream().forEach(item -> {
            //去除精选板块(精选公会,临时方案)
            if (!NumberEnum.FIVE.getValue().equals(item.getLabelValue())) {
                labels.add(
                        new SimpleLabelsVO()
                                .setLabelValue(item.getLabelValue())
                                .setLabelName(item.getLabelName())
                );
            }
        });
        return labels;
    }

    @Override
    public List<LabelBestLeaguesVO> labelBestLeagues() {
        //读取指定语言所有显示的标签列表
        List<LeagueLabelDO> labels = leagueLabelMapper.selectListByLanguage();

        List<LabelBestLeaguesVO> rsp = new ArrayList<>(labels.size());
        labels.stream().forEach(item -> {
            //设置标签对象
            LabelBestLeaguesVO labelLeagues = BeanUtil.copyProperties(item, LabelBestLeaguesVO.class);
            labelLeagues.setLabelId(item.getId());
            if (NumberEnum.FIVE.getValue().equals(item.getLabelValue())) {
                labelLeagues.setHiddenBoo(true);
                //获取精选公会列表(精选公会,临时方案),哎本来想用这个框架的字典表的,引用模块直接挂了不想研究了,反正是临时方案
                SystemParamsRespDTO param = systemParamsApi.getSystemParams("league", "selected_league");
                labelLeagues.setLeagues(setSelectedLeagues(param.getValue()));
            } else {
                labelLeagues.setHiddenBoo(false);
                //设置标签公会列表
                labelLeagues.setLeagues(setLeagues(item.getLabelValue()));
            }
            rsp.add(labelLeagues);
        });

        return rsp;
    }

    @Override
    public LeagueLabelVO singleLabel(String labelValue) {
        LeagueLabelDO label = leagueLabelMapper.selectOneByLabelValue(labelValue);
        return BeanUtil.copyProperties(label, LeagueLabelVO.class).setLabelId(label.getId());
    }

    @Override
    public PageResult<LeagueVO> labelLeagues(PageLabelLeaguesReqVO reqVO) {
        PageResult<LeagueDO> iPage = leagueMapper.pageByLabel(reqVO, reqVO.getLabelValue());
        //将查询到的公会列表 copy 给业务对象
        return new PageResult<>(leagueDOToVO(iPage.getList()), iPage.getTotal());
    }

    /**
     * 设置每个标签的公会列表
     *
     * @param labelValue
     * @return
     */
    private List<LeagueVO> setLeagues(String labelValue) {
        //根据标签值查询公会
        PageParam pageParam = new PageParam().setPageSize(6);
        PageResult<LeagueDO> iPage = leagueMapper.pageByLabel(pageParam, labelValue);
        //将查询到的公会列表 copy 给业务对象
        return leagueDOToVO(iPage.getList());
    }

    /**
     * 设置精选公会
     *
     * @param leaguesIds
     * @return
     */
    private List<LeagueVO> setSelectedLeagues(String leaguesIds) {
        String[] idArr = leaguesIds.split(",");
        //公会表ID为Long类型,将String数组转成Long集合,方便使用in查询
        List<Long> list = new ArrayList<>(idArr.length);
        for (String idStr : idArr) {
            list.add(Long.valueOf(idStr));
        }
        List<LeagueDO> leagues = leagueMapper.selectInId(list);
        return leagueDOToVO(leagues);
    }

    /**
     * 公会list DO转VO
     *
     * @param list
     * @return
     */
    private List<LeagueVO> leagueDOToVO(List<LeagueDO> list) {
        //将查询到的公会列表 copy 给业务对象
        List<LeagueVO> leagues = new ArrayList<>(list.size());
        list.stream().forEach(item -> {
            LeagueVO league = BeanUtil.copyProperties(item, LeagueVO.class);
            league.setLeagueId(item.getId());
            leagues.add(league);
        });

        return leagues;
    }
}
