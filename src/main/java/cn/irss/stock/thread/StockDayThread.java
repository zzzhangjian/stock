package cn.irss.stock.thread;

import java.util.List;

import cn.irss.stock.domain.StockDay;
import cn.irss.stock.parse.StockDataParseByDayImpl;
import cn.irss.stock.repository.StockDayRepository;

public class StockDayThread extends Thread{
    private String code;
    private StockDayRepository stockDayRepository;
    private StockDataParseByDayImpl stockDataParseByDayImpl;
    
    public  StockDayThread (String code,StockDataParseByDayImpl stockDataParseByDayImpl,StockDayRepository stockDayRepository){
        this.code = code;
        this.stockDayRepository = stockDayRepository;
        this.stockDataParseByDayImpl = stockDataParseByDayImpl;
    }
    
    public void run(){
    	List<StockDay> list = stockDataParseByDayImpl.getStock(code);
    	save(list);
    }
	
	public List<StockDay> save(List<StockDay> stockDayList) {
		if(stockDayList!=null && stockDayList.size()>0) {
			stockDayRepository.saveAll(stockDayList);
			System.out.println("处理【"+stockDayList.size()+"】条数据");
		}
		return stockDayList;
	}
}