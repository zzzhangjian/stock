package cn.irss.stock.core;

import cn.irss.stock.domain.JsonResult;

@FunctionalInterface
public interface ResultProcessor {
	 JsonResult process();
}
