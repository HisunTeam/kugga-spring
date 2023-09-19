package com.hisun.kugga.duke.forum.vo;

import com.hisun.kugga.duke.entity.Content;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zuocheng
 */
@ApiModel("回复贴子 request VO")
@Data
public class ReplyPostsReqVO {
    @ApiModelProperty(value = "贴子ID", required = true, example = "1")
    @NotNull(message = "postsId field cannot be empty")
    private Long postsId;

    @ApiModelProperty(value = "回复内容", required = true, example = "和他相遇的瞬间，我的人生就改变了...")
    @NotEmpty(message = "content field cannot be empty")
    private List<Content> content;
}
