package cn.irss.stock.thread;

import java.util.List;

import cn.irss.stock.domain.Stock;
import cn.irss.stock.parse.StockDataParseImpl;
import cn.irss.stock.repository.StockRepository;

public class StockThread extends Thread{
    private String code;
    private StockRepository stockRepository;
    private StockDataParseImpl stockDataParse;
    
    public  StockThread (String code,StockDataParseImpl stockDataParse,StockRepository stockRepository){
        this.code = code;
        this.stockRepository = stockRepository;
        this.stockDataParse = stockDataParse;
    }
    public void run(){
    	List<Stock> list = stockDataParse.getStock(code);
    	for (Stock stock : list) {
			save(stock);
		}
    }
	
	public Stock save(Stock stock) {
		if(stock!=null && stock.getCode()!=null) {
    		stockRepository.save(stock);
    		System.out.println(stock.getCode());
    	}
		return null;
	}
}