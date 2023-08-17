package com.example.myprojectbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myprojectbackend.entity.dto.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * ClassName:AccountMapper
 * Package:com.example.myprojectbackend.mapper
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/15 17:57
 * @Version 1.0
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}
