package com.hisun.kugga.module.infra.dal.mysql.file;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.framework.file.core.client.db.DBFileContentFrameworkDAO;
import com.hisun.kugga.module.infra.dal.dataobject.file.FileContentDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class FileContentDAOImpl implements DBFileContentFrameworkDAO {

    @Resource
    private FileContentMapper fileContentMapper;

    @Override
    public void insert(Long configId, String path, byte[] content) {
        FileContentDO entity = new FileContentDO().setConfigId(configId)
                .setPath(path).setContent(content);
        fileContentMapper.insert(entity);
    }

    @Override
    public void delete(Long configId, String path) {
        fileContentMapper.delete(buildQuery(configId, path));
    }

    @Override
    public byte[] selectContent(Long configId, String path) {
        FileContentDO fileContentDO = fileContentMapper.selectOne(
                buildQuery(configId, path).select(FileContentDO::getContent));
        return fileContentDO != null ? fileContentDO.getContent() : null;
    }

    private LambdaQueryWrapper<FileContentDO> buildQuery(Long configId, String path) {
        return new LambdaQueryWrapper<FileContentDO>()
                .eq(FileContentDO::getConfigId, configId)
                .eq(FileContentDO::getPath, path);
    }

}
