package com.hisun.kugga.duke.user.convert.favorite;

import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteCreateReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteRespVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteUpdateReqVO;
import com.hisun.kugga.duke.user.dal.dataobject.FavoriteDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 收藏 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface FavoriteConvert {

    FavoriteConvert INSTANCE = Mappers.getMapper(FavoriteConvert.class);

    FavoriteDO convert(FavoriteCreateReqVO bean);

    FavoriteDO convert(FavoriteUpdateReqVO bean);

    FavoriteRespVO convert(FavoriteDO bean);

    List<FavoriteRespVO> convertList(List<FavoriteDO> list);

    PageResult<FavoriteRespVO> convertPage(PageResult<FavoriteDO> page);

}
