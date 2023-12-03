package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.entity.CommentCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentCollectionMapper extends BaseMapper<CommentCollection> {

    @Select("select * from comment_collection where user_id = #{userId} and comment_id = #{commentId}")
    CommentCollection selectByUserIdAndCommentId(
            @Param("userId") Long userId,
            @Param("commentId") Long commentId
    );
}
