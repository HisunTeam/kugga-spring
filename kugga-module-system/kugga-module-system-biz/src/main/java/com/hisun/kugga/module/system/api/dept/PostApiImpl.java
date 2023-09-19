package com.hisun.kugga.module.system.api.dept;

import com.hisun.kugga.module.system.service.dept.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 岗位 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class PostApiImpl implements PostApi {

    @Resource
    private PostService postService;

    @Override
    public void validPosts(Collection<Long> ids) {
        postService.validPosts(ids);
    }
}
