package com.hisun.kugga.duke.user.controller.vo.favorite;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("收藏删除VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FavoriteDeleteReqVO extends FavoriteBaseVO {

}
