package com.myself.mall.thirdparty.controller;


import com.myself.common.exception.BizCodeEnum;
import com.myself.common.utils.R;
import com.myself.mall.thirdparty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/sms")
public class SmsSendController {

	@Autowired
	private SmsComponent smsComponent;

	/**
	 * 提供给别的服务进行调用的
	 */
	@GetMapping("/sendcode")
	public R sendCode(HttpServletRequest request, HttpServletResponse response,@RequestParam("phone") String phone, @RequestParam("code") String code){
		if(!"fail".equals(smsComponent.sendSmsCode(phone, code).split("_")[0])){
			return R.ok();
		}
		return R.error(BizCodeEnum.SMS_SEND_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_SEND_CODE_EXCEPTION.getMsg());
	}
}
