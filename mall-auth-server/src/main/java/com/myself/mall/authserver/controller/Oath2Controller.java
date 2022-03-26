package com.myself.mall.authserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.myself.common.constants.AuthServerConstant;
import com.myself.common.utils.HttpUtils;
import com.myself.common.utils.R;
import com.myself.common.vo.MemberRsepVo;
import com.myself.mall.authserver.feign.MemberFeignService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: Oath2Controller</p>
 * Description：
 * date：2020/6/26 14:14
 */
@Slf4j
@Controller
@RequestMapping("/oauth2.0")
public class Oath2Controller {

	@Autowired
	private MemberFeignService memberFeignService;


	private String appid ;
	private String secret ;

	private String giteeClientId ;
	private String giteeSerret ;

	@GetMapping("/logout")
	public String login(HttpSession session){
		if(session.getAttribute(AuthServerConstant.LOGIN_USER) != null){
			log.info("\n[" + ((MemberRsepVo)session.getAttribute(AuthServerConstant.LOGIN_USER)).getUsername() + "] 已下线");
		}
		session.invalidate();
		return "redirect:http://auth.mall.com/login.html";
	}

	/**
	 * 登录成功回调
	 * {
	 *     "access_token": "2.00b5w4HGbwxc6B0e3d62c666DlN1DD",
	 *     "remind_in": "157679999",
	 *     "expires_in": 157679999,
	 *     "uid": "5605937365",
	 *     "isRealName": "true"
	 * }
	 * 	汀西氟的我是你	---		2.00b5w4HGbwxc6B0e3d62c666DlN1DD
	 */
//	@GetMapping("/weibo/success")
//	public String weiBo(@RequestParam("code") String code, HttpSession session) throws Exception {
//
//		// 根据code换取 Access Token
//		Map<String,String> map = new HashMap<>();
//		map.put("client_id", "1294828100");
//		map.put("client_secret", "a8e8900e15fba6077591cdfa3105af44");
//		map.put("grant_type", "authorization_code");
//		map.put("redirect_uri", "http://auth.glmall.com/oauth2.0/weibo/success");
//		map.put("code", code);
//		Map<String, String> headers = new HashMap<>();
//		HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", headers, null, map);
//		if(response.getStatusLine().getStatusCode() == 200){
//			// 获取到了 Access Token
//			String json = EntityUtils.toString(response.getEntity());
//			SocialUser socialUser = JSON.parseObject(json, SocialUser.class);
//
//			// 相当于我们知道了当前是那个用户
//			// 1.如果用户是第一次进来 自动注册进来(为当前社交用户生成一个会员信息 以后这个账户就会关联这个账号)
//			R login = memberFeignService.login(socialUser);
//			if(login.getCode() == 0){
//				MemberRsepVo rsepVo = login.getData("data" ,new TypeReference<MemberRsepVo>() {});
//
//				log.info("\n欢迎 [" + rsepVo.getUsername() + "] 使用社交账号登录");
//				// 第一次使用session 命令浏览器保存这个用户信息 JESSIONSEID 每次只要访问这个网站就会带上这个cookie
//				// 在发卡的时候扩大session作用域 (指定域名为父域名)
//				// TODO 1.默认发的当前域的session (需要解决子域session共享问题)
//				// TODO 2.使用JSON的方式序列化到redis
////				new Cookie("JSESSIONID","").setDomain("glmall.com");
//				session.setAttribute(AuthServerConstant.LOGIN_USER, rsepVo);
//				// 登录成功 跳回首页
//				return "redirect:http://glmall.com";
//			}else{
//				return "redirect:http://auth.glmall.com/login.html";
//			}
//		}else{
//			return "redirect:http://auth.glmall.com/login.html";
//		}
//	}

	/**
	 * 微信小程序登陆成功回调
	 * @param code
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/wechat/success")
	public String loginByWX(@RequestParam("code") String code, HttpSession session, HttpServletRequest request) throws Exception {

		Map<String,String> param = new HashMap<>();
		param.put("appid",appid);
		param.put("secret",secret);
		param.put("code",code);
		param.put("grant_type","authorization_code");
		//根据code获取token
		HttpResponse httpResponse = HttpUtils.doGet("https://api.weixin.qq.com", "/sns/oauth2/access_token", "", new HashMap<>(), param);

		String result = HttpUtils.parseResponse(httpResponse);
		JSONObject json = JSON.parseObject(result);
		String accessToken = (String)json.get("access_token");
		System.out.println(result);

		Map<String,String> tokenParam = new HashMap<>();
		tokenParam.put("access_token",accessToken);
		tokenParam.put("openid",appid);
		tokenParam.put("lang","zh_CN");

		//根据获取的token获取用户信息
		HttpResponse userResponse = HttpUtils.doGet("https://api.weixin.qq.com", "/sns/userinfo", "", new HashMap<>(), tokenParam);
		String user = HttpUtils.parseResponse(userResponse);
		JSONObject userJson = JSON.parseObject(user);
		System.out.println(user);
		return "redirect:http://glmall.com";
	}

	/**
	 * gitee登陆成功回调
	 * @param code
	 * @param session
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/gitee/success")
	public String loginByGitee(@RequestParam("code") String code, HttpSession session, HttpServletRequest request) throws Exception {

		Map<String,String> param = new HashMap<>();
		param.put("code",code);
		param.put("grant_type","authorization_code");
		param.put("client_id",giteeClientId);
		param.put("client_secret",giteeSerret);
		param.put("redirect_uri","http://auth.mall.com/oauth2.0/gitee/success");
		HttpResponse httpResponse = HttpUtils.doPost("https://gitee.com", "/oauth/token", "", new HashMap<>(),  param, new HashMap<>());
		String result = HttpUtils.parseResponse(httpResponse);
		JSONObject json = JSON.parseObject(result);
		System.out.println(result);

		Map<String,String> userParam = new HashMap<>();
		userParam.put("access_token",(String)json.get("access_token"));
		HttpResponse userResponse = HttpUtils.doGet("https://gitee.com", "/api/v5/user", "", new HashMap<>(),  userParam);
		String user = HttpUtils.parseResponse(userResponse);
		System.out.println(user);
		return "redirect:http://glmall.com";
	}
}
