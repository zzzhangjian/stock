package cn.irss.stock.parse;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import cn.irss.stock.domain.Stock;

public class StockDataParseImpl implements StockDataParse<Stock,Integer> {
	private String pre;
	private String code;
	
	public StockDataParseImpl(String pre, String code) {
		this.pre = pre;
		this.code = code;
	}

	@Override
	public List<Stock> getStock(String code) {
		this.code = code;
		List<Stock> stockList = new ArrayList<>(1);
		Stock stock = getStock(this.pre,this.code);
		if(stock!=null && StringUtils.isNotBlank(stock.getSymbol())) {
			stockList.add(stock);
		}
		
		return stockList;
	}
	
	public Stock getStock(String pre, String code) {
		System.out.println("pre:"+pre+";code:"+code);
		Stock stock = new Stock();
		
		String targetUrl = "http://api.money.126.net/data/feed/"+code+",money.api?callback=_ntes_quote_callback";
		HttpGet httpGet = new HttpGet(targetUrl);
		
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpGet.setHeader("Cache-Control", "max-age=0");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("Host", "api.money.126.net");
		httpGet.setHeader("Upgrade-Insecure-Requests", "1");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3236.0 Safari/537.36");
		
		CloseableHttpClient  httpClient = HttpClients.createDefault();
		
		try {
			CloseableHttpResponse  res = httpClient.execute(httpGet);
			 if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				 
				 HttpEntity entity = res.getEntity();
				 if (entity != null) {
					 	String result = EntityUtils.toString(entity,"utf-8");
		                
		                int beginIndex = result.indexOf(":{");
		                int endIndex = result.indexOf("}");
		                if(beginIndex<0) {
		                	return null;
		                }
		                
	                	String substring = result.substring(beginIndex+1, endIndex+1);
	                	if(substring==null) {
	                		return null;
	                	}
	                	
	                	stock = JSONObject.parseObject(substring, Stock.class);
	                	BigDecimal amplitude = new BigDecimal(0);
	                	if(!(amplitude.compareTo(stock.getYestclose())==0)) {
	                		amplitude = stock.getHigh().subtract(stock.getLow()).divide(stock.getYestclose(),2,RoundingMode.HALF_UP);
	                	}
	                	stock.setAmplitude(amplitude);
	                	stock.setTime(stock.getTime().substring(0, stock.getTime().indexOf(" ")).replace("/", "-"));
	                	stock.setPercent(stock.getPercent().multiply(new BigDecimal(10000)));
	                	stock.setVolAmount(stock.getVolume()*100L*stock.getPrice().longValue());
	                	stock.setId(Integer.valueOf(stock.getSymbol()));
	                	System.out.println("获取["+code+"]"+stock);
		         }
			 }
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return stock;
	}

	
}
