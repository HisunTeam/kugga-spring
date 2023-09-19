package com.hisun.kugga.duke.user.cv.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.entity.Content;
import com.hisun.kugga.framework.common.exception.ServiceException;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: zhou_xiong
 */
public class EditorUtils {
    private EditorUtils() {
    }

    /**
     * 富文本编辑器原始纯文本转换
     *
     * @param context
     * @return
     */
    public static String parseContent(String context) {
        if (!JSONUtil.isJsonArray(context)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.CONTENT_NOT_EXISTS);
        }
        JSONArray jsonArray = JSONUtil.parseArray(context);
        if (jsonArray.size() == 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.CONTENT_NOT_EXISTS);
        }
        // 去除data中的html标签和&nbsp;空格
        return jsonArray.toList(Content.class).stream().map(Content::getData)
                .map(EditorUtils::parse)
                .map(HtmlUtil::cleanHtmlTag)
                .map(text -> StrUtil.removeAll(text, "&nbsp;"))
                .collect(Collectors.joining());
    }

    public static String parseContent(Content context) {
        Content.Data data = context.getData();
        // 去除data中的html标签和&nbsp;空格
        String text = parse(data);
        return StrUtil.removeAll(HtmlUtil.cleanHtmlTag(text), "&nbsp;");
    }

    private static String parse(Content.Data data) {
        StringBuilder text = new StringBuilder();
        //循环遍历纯文本，每次加上一个空格避免单词连接
        Optional.ofNullable(data.getText()).map(item -> text.append(item).append(" "));
        //循环遍历无序列表纯文本，每次加上一个空格避免单词连接
        Optional.ofNullable(data.getItems()).map(items -> items.stream()
                .map(item -> text.append(item).append(" "))
                .collect(Collectors.joining()));
        //循环遍历表格纯文本，每次加上一个空格避免单词连接
        Optional.ofNullable(data.getContent()).map(rows -> rows.stream()
                .map(row -> row.stream().map(item -> text.append(item).append(" "))
                        .collect(Collectors.joining())).collect(Collectors.joining()));
        return text.toString();
    }
}
