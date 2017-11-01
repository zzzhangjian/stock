package cn.irss.stock.disruptor;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.lmax.disruptor.EventHandler;

import cn.irss.stock.domain.Stock;
@Component
public class StockEventHandler implements EventHandler<StockEvent> {
//	@Autowired
//	private StockRepository stockRepository;
	@Override
	public void onEvent(StockEvent event, long sequence, boolean endOfBatch) throws Exception {
		
		Stock stock = getStock(event.getPre(),event.getCode());
		if(stock!=null && stock.getCode()!=null) {
//			stockRepository.save(stock);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("d:/stock.txt", true)), 1024*10*10);
			bw.write(stock.getCode());
			bw.newLine();
			bw.close();
		}
	} 
	
	public Stock getStock(String pre, String code) {
		Stock stock = new Stock();
		String targetUrl = "http://api.money.126.net/data/feed/"+pre+code+",money.api?callback=_ntes_quote_callback";
		HttpGet get = new HttpGet(targetUrl);
		
		CloseableHttpClient  httpClient = HttpClients.createDefault();
		
		try {
			CloseableHttpResponse  res = httpClient.execute(get);
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