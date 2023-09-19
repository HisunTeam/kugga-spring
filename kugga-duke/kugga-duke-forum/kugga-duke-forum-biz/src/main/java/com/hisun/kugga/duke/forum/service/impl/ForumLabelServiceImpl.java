package com.hisun.kugga.duke.forum.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.forum.dal.dataobject.ForumLabelDO;
import com.hisun.kugga.duke.forum.dal.mysql.ForumLabelMapper;
import com.hisun.kugga.duke.forum.service.ForumLabelService;
import com.hisun.kugga.duke.forum.vo.HotLabelsRespVO;
import com.hisun.kugga.duke.forum.vo.LabelVO;
import com.hisun.kugga.duke.forum.vo.VagueLabelsReqVO;
import com.hisun.kugga.duke.system.api.params.SystemParamsApi;
import com.hisun.kugga.duke.system.api.params.dto.SystemParamsRespDTO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zuocheng
 */
@Service
@Slf4j
public class ForumLabelServiceImpl implements ForumLabelService {

    //系统参数所属类型 “form” 论坛
    private final static String TYPE = "forum";

    //热度标签列表展示数量查询key
    private final static String HOT_LABEL_LIST_NUM = "hot_label_list_num";

    //热度标签列表统计维度查询key
    private final static String HOT_LABEL_DIMENSION = "hot_label_dimension";

    @Resource
    private ForumLabelMapper forumLabelMapper;

    @Resource
    private SystemParamsApi systemParamsApi;

    @Override
    public HotLabelsRespVO hotLabels() {
        //获取热度标签列表数量配置
        SystemParamsRespDTO numParam = systemParamsApi.getSystemParams(TYPE, HOT_LABEL_LIST_NUM);
        int num = Integer.valueOf(numParam.getValue());

        //获取热度标签维度多少天内
        SystemParamsRespDTO dimensionParam = systemParamsApi.getSystemParams(TYPE, HOT_LABEL_DIMENSION);

        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(Integer.valueOf(dimensionParam.getValue()));
        //查询热度标签列表
        List<ForumLabelDO> data = forumLabelMapper.selectRangeHotDesc(startTime, endTime, num);

        List<LabelVO> labels = new ArrayList<>(num);
        data.stream().forEach(item->{
            //根据ID获取到标签的名称
            ForumLabelDO labelData = forumLabelMapper.selectById(item.getId());
            if(ObjectUtil.isNull(labelData)){
                return;
            }
            LabelVO label = new LabelVO()
                    .setLabelId(item.getId())
                    .setLabelName(labelData.getLabelName())
                    .setHotNum(item.getHotNum());

            labels.add(label);
        });

        return new HotLabelsRespVO().setLabels(labels);
    }

    @Override
    public PageResult<LabelVO> vagueLabels(VagueLabelsReqVO reqVO) {
        if(StrUtil.isBlank(reqVO.getSearchStr())){
            return new PageResult(new ArrayList(),0L);
        }
        //模糊查询标签列表
        PageResult<ForumLabelDO> data = forumLabelMapper.selectVague(reqVO ,reqVO.getSearchStr());

        List<LabelVO> labels = new ArrayList<>(data.getTotal().intValue());
        data.getList().stream().forEach(item->
                labels.add(new LabelVO().setLabelId(item.getId()).setLabelName(item.getLabelName()))
        );

        return new PageResult(labels,data.getTotal());
    }
}
