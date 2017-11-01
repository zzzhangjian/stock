package cn.irss.stock.parse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import cn.irss.stock.domain.StockDay;

public class StockDataParseByDayImpl implements StockDataParse<StockDay,Long> {
	
	private String code;
	private String year;
	private String season;
	
	public StockDataParseByDayImpl(String year,String season,String code) {
		this.code = code;
		this.year = year;
		this.season = season;
	}
	
	@Override
	public List<StockDay> getStock(String code) {
		this.code = code;
		return getStockDay(this.code,this.year,this.season);
	}
	
	private List<StockDay> getStockDay(String code,String year,String season){
		String targetUrl = "http://quotes.money.163.com/trade/lsjysj_"+code+".html?year="+this.year+"&season="+this.season;
		List<StockDay> stockDayList = new ArrayList<>(100);  
	 	try {
			Document document = Jsoup.connect(targetUrl).header("Accept-Encoding", "gzip, deflate").get();
			//Document document = Jsoup.connect(targetUrl).get();
			//System.out.println(document.html());
			
			Elements elements = document.select("table.table_bg001.border_box.limit_sale tr");
			elements.remove(0);
			for (Element trElement:elements) {
			    Elements tdElements = trElement.children();
				String date = tdElements.get(0).text();
				String open = tdElements.get(1).text();
				String high = tdElements.get(2).text();
				String low = tdElements.get(3).text();
				String close = tdElements.get(4).text();
				String updown = tdElements.get(5).text();
				String updownrate = tdElements.get(6).text();
				String volume = tdElements.get(7).text().replace(",", "");
				String vol_amount = tdElements.get(8).text().replace(",", "");
				String amplitude = tdElements.get(9).text();
				String turnover = tdElements.get(10).text();
				
				if(!StringUtils.isBlank(date)) {
					
					StockDay stockDay = new StockDay(null, Date.valueOf(date)
														, new BigDecimal(open)
														, new BigDecimal(high)
														,new BigDecimal(low)
														, new BigDecimal(close)
														, new BigDecimal(updown)
														,new BigDecimal(updownrate)
														, Long.valueOf(volume)
														, Long.valueOf(vol_amount)
														, new BigDecimal(amplitude)
														, new BigDecimal(turnover),code);
					
					stockDayList.add(stockDay);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	 	
		return stockDayList;
	}
	
	
	
}
