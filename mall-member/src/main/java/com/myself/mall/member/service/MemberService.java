package com.myself.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.utils.PageUtils;
import com.myself.mall.member.entity.MemberEntity;
import com.myself.mall.member.exception.PhoneExistException;
import com.myself.mall.member.exception.UserNameExistException;
import com.myself.mall.member.vo.MemberLoginVo;
import com.myself.mall.member.vo.UserRegisterVo;

import java.util.Map;

/**
 * 会员
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-21 22:04:30
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(UserRegisterVo userRegisterVo);

    void checkPhone(String phone) throws PhoneExistException;

    void checkUserName(String username) throws UserNameExistException;

    MemberEntity login(MemberLoginVo vo);
}

