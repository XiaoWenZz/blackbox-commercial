package com.tencent.wxcloudrun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.wxcloudrun.dto.UserWithCodeCount;
import com.tencent.wxcloudrun.entity.SweepStake;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SweepStakeMapper extends BaseMapper<SweepStake> {

    @Select("select id from sweep_stake where code1=#{code} or code2=#{code} or code3=#{code}")
    Long selectIdByCode(
            @Param("code") String code
    );

    @Select("select * from sweep_stake where user_id=#{userId} and exhibition_id=#{exhibitionId}")
    List<SweepStake> getUserSweepStakeListByExhibitionId(
            @Param("userId") Long userId,
            @Param("exhibitionId") Long exhibitionId
    );

    @Select("select user_id from sweep_stake where code1=#{code} or code2=#{code} or code3=#{code}")
    Long getUserIdByCode(
            @Param("code") String code
    );

    @Select("select code1 from sweep_stake where exhibition_id=#{exhibitionId} and code1 is not null " +
            "union select code2 from sweep_stake where exhibition_id=#{exhibitionId} and code2 is not null " +
            "union select code3 from sweep_stake where exhibition_id=#{exhibitionId} and code3 is not null")
    List<String> getAllCodesByExhibitionId(
            @Param("exhibitionId") Long exhibitionId
    );

    @Select("select code1 from sweep_stake where user_id=#{userId} and exhibition_id=#{exhibitionId} and code1 is not null" +
            "union select code2 from sweep_stake where user_id=#{userId} and exhibition_id=#{exhibitionId} and code2 is not null " +
            "union select code3 from sweep_stake where user_id=#{userId} and exhibition_id=#{exhibitionId} and code3 is not null")
    List<String> getUserAllCodesByExhibitionId(
            @Param("userId") Long userId,
            @Param("exhibitionId") Long exhibitionId
    );

    @Select("select code1 from sweep_stake where user_id=#{userId} and merchant_id=#{merchantId} and exhibition_id=#{exhibitionId}")
    String getCodeByUserIdAndMerchantIdAndExhibitionId(
            @Param("userId") Long userId,
            @Param("merchantId") Long merchantId,
            @Param("exhibitionId") Long exhibitionId
    );

    @Select("select merchant_id from sweep_stake where user_id=#{userId} and exhibition_id=#{exhibitionId} and merchant_id is not null")
    List<Long> getCollectedMerchantIdByUserIdAndExhibitionId(
            @Param("userId") Long userId,
            @Param("exhibitionId") Long exhibitionId
    );

    @Select("select * from sweep_stake where merchant_id=#{merchantId} and exhibition_id=#{exhibitionId} and stake_id is null")
    List<SweepStake> getSweepStakeListByMerchantIdAndExhibitionId(
            @Param("merchantId") Long merchantId,
            @Param("exhibitionId") Long exhibitionId
    );

    @Select("select * from sweep_stake where stake_id=#{stakeId}")
    List<SweepStake> getSweepStakeListByStakeId(
            @Param("stakeId") Long stakeId
    );

//    @Select("select count(code1) from sweep_stake where user_id=#{userId} and code1 is not null")
//    int getUserCode1Count(
//            @Param("userId") Long userId
//    );
//
//    @Select("select count(code2) from sweep_stake where user_id=#{userId} and code2 is not null")
//    int getUserCode2Count(
//            @Param("userId") Long userId
//    );
//
//    @Select("select count(code3) from sweep_stake where user_id=#{userId} and code3 is not null")
//    int getUserCode3Count(
//            @Param("userId") Long userId
//    );

    @Select("SELECT SUM(code_count) AS total_count\n" +
            "FROM (\n" +
            "    SELECT COUNT(code1) AS code_count\n" +
            "    FROM sweep_stake\n" +
            "    WHERE user_id = #{userId} AND exhibition_id=#{exhibitionId} AND code1 IS NOT NULL\n" +
            "    UNION ALL\n" +
            "    SELECT COUNT(code2) AS code_count\n" +
            "    FROM sweep_stake\n" +
            "    WHERE user_id = #{userId} AND exhibition_id=#{exhibitionId} AND code2 IS NOT NULL\n" +
            "    UNION ALL\n" +
            "    SELECT COUNT(code3) AS code_count\n" +
            "    FROM sweep_stake\n" +
            "    WHERE user_id = #{userId} AND exhibition_id=#{exhibitionId} AND code3 IS NOT NULL\n" +
            ") AS subquery;\n")
    int getUserCodeCountByExhibitionId(
            @Param("userId") Long userId,
            @Param("exhibitionId") Long exhibitionId
    );

//    @Select("select count(code1) from sweep_stake where code1 is not null")
//    int getCode1Count();
//
//    @Select("select count(code2) from sweep_stake where code2 is not null")
//    int getCode2Count();
//
//    @Select("select count(code3) from sweep_stake where code3 is not null")
//    int getCode3Count();

    @Select("SELECT SUM(code_count) AS total_count\n" +
            "FROM (\n" +
            "    SELECT COUNT(code1) AS code_count\n" +
            "    FROM sweep_stake\n" +
            "    WHERE exhibition_id=#{exhibitionId} AND code1 IS NOT NULL\n" +
            "    UNION ALL\n" +
            "    SELECT COUNT(code2) AS code_count\n" +
            "    FROM sweep_stake\n" +
            "    WHERE exhibition_id=#{exhibitionId} AND code2 IS NOT NULL\n" +
            "    UNION ALL\n" +
            "    SELECT COUNT(code3) AS code_count\n" +
            "    FROM sweep_stake\n" +
            "    WHERE exhibition_id=#{exhibitionId} AND code3 IS NOT NULL\n" +
            ") AS subquery;\n")
    int getCodeCountByExhibitionId(
            @Param("exhibitionId") Long exhibitionId
    );

//    @Select("SELECT user_id, code_count, @rank := @rank + 1 AS rank\n" +
//            "FROM (\n" +
//            "    SELECT user_id, (COUNT(code1) + COUNT(code2) + COUNT(code3)) AS code_count\n" +
//            "    FROM sweep_stake\n" +
//            "    WHERE code1 IS NOT NULL OR code2 IS NOT NULL OR code3 IS NOT NULL\n" +
//            "    GROUP BY user_id\n" +
//            "    ORDER BY code_count DESC\n" +
//            ") AS subquery\n" +
//            "CROSS JOIN (SELECT @rank := 0) AS r")
//    @Results({
//            @Result(property = "userId", column = "user_id"),
//            @Result(property = "codeCount", column = "code_count"),
//            @Result(property = "rank", column = "rank")
//    })
//    List<UserWithCodeCount> getUserWithCodeCountList();

    @Select("select DISTINCT user_id from sweep_stake where exhibition_id=#{exhibitionId} and (code1 is not null or code2 is not null or code3 is not null)")
    List<Long> getUserIdsByExhibitionId(
            @Param("exhibitionId") Long exhibitionId
    );

    @Select("SELECT user_id, SUM(code_count) AS total_code_count\n" +
            "FROM (\n" +
            "    SELECT user_id, COUNT(code1) AS code_count\n" +
            "    FROM sweep_stake\n" +
            "    WHERE exhibition_id=#{exhibitionId} AND code1 IS NOT NULL\n" +
            "    GROUP BY user_id\n" +
            "    UNION ALL\n" +
            "    SELECT user_id, COUNT(code2) AS code_count\n" +
            "    FROM sweep_stake\n" +
            "    WHERE exhibition_id=#{exhibitionId} AND code2 IS NOT NULL\n" +
            "    GROUP BY user_id\n" +
            "    UNION ALL\n" +
            "    SELECT user_id, COUNT(code3) AS code_count\n" +
            "    FROM sweep_stake\n" +
            "    WHERE exhibition_id=#{exhibitionId} AND code3 IS NOT NULL\n" +
            "    GROUP BY user_id\n" +
            ") AS subquery\n" +
            "GROUP BY user_id\n" +
            "ORDER BY total_code_count DESC")
    List<UserWithCodeCount> getRankedUsersByCodeCount(@Param("exhibitionId") Long exhibitionId);






}
