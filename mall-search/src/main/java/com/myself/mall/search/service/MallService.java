package com.myself.mall.search.service;


import com.myself.mall.search.vo.SearchParam;
import com.myself.mall.search.vo.SearchResult;

/**
 * <p>Title: MasllService</p>
 * Description：
 * date：2020/6/12 23:05
 */
public interface MallService {

	/**
	 * 检索所有参数
	 */
	SearchResult search(SearchParam Param);
}
