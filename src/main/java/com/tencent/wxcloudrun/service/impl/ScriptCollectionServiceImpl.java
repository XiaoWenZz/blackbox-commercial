package com.tencent.wxcloudrun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefScriptCollection;
import com.tencent.wxcloudrun.entity.Script;
import com.tencent.wxcloudrun.entity.ScriptCollection;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.mapper.ScriptCollectionMapper;
import com.tencent.wxcloudrun.mapper.ScriptMapper;
import com.tencent.wxcloudrun.mapper.UserMapper;
import com.tencent.wxcloudrun.service.ScriptCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScriptCollectionServiceImpl implements ScriptCollectionService {

    @Autowired
    private ScriptMapper scriptMapper;

    @Autowired
    private ScriptCollectionMapper scriptCollectionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void collect(String openId, Long scriptId) {
        Script script = scriptMapper.selectById(scriptId);
        if (script == null) throw new CommonException(CommonErrorCode.SCRIPT_NOT_EXIST);

        User user = userMapper.getUserByUnionId(openId);

        if (scriptCollectionMapper.getCollection(user.getId(), scriptId) != null) throw new CommonException(CommonErrorCode.COLLECTION_ALREADY_EXIST);

        ScriptCollection scriptCollection = ScriptCollection.builder()
                .userId(user.getId())
                .scriptId(scriptId)
                .picture(script.getFileId())
                .build();

        script.setCollectionCount(script.getCollectionCount() + 1);
        scriptMapper.updateById(script);

        scriptCollectionMapper.insert(scriptCollection);

    }

    @Override
    public void cancelCollect(String openId, Long collectionId) {

        User user = userMapper.getUserByUnionId(openId);
        if (user == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);

        ScriptCollection scriptCollection = scriptCollectionMapper.selectById(collectionId);
        if (scriptCollection == null) throw new CommonException(CommonErrorCode.COLLECTION_NOT_EXIST);

        Script script = scriptMapper.selectById(scriptCollection.getScriptId());
        script.setCollectionCount(script.getCollectionCount() - 1);
        scriptMapper.updateById(script);

        scriptCollectionMapper.deleteById(scriptCollection.getId());

    }

    @Override
    public Page<BriefScriptCollection> getCollectionList(String openId, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);

        User user = userMapper.getUserByUnionId(openId);
        if (user == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);

        List<ScriptCollection> lists = scriptCollectionMapper.getUserCollectionList(user.getId());

        List<BriefScriptCollection> briefScriptCollections = new ArrayList<>();

        for (ScriptCollection scriptCollection : lists) {
            Script script = scriptMapper.selectById(scriptCollection.getScriptId());

            BriefScriptCollection briefScriptCollection = BriefScriptCollection.builder()
                    .collectionId(scriptCollection.getId())
                    .scriptId(script.getId())
                    .collectionCount(script.getCollectionCount())
                    .fileId(script.getFileId())
                    .name(script.getName())
                    .saleForm(script.getSaleForm())
                    .type(script.getType())
                    .tag(script.getTag())
                    .build();

            briefScriptCollections.add(briefScriptCollection);
        }

        return new Page<>(new PageInfo<>(briefScriptCollections));
    }

    @Override
    public ScriptCollection getCollection(Long collectionId) {

        ScriptCollection scriptCollection = scriptCollectionMapper.selectById(collectionId);
        if (scriptCollection == null) throw new CommonException(CommonErrorCode.COLLECTION_NOT_EXIST);

        return scriptCollection;
    }
}
