package cn.irss.stock.parse;

import java.util.List;

public interface StockDataParse<T,ID> {
	List<T> getStock(String code);
}
