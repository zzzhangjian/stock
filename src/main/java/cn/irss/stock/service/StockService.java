package cn.irss.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.irss.stock.domain.Stock;
import cn.irss.stock.repository.StockRepository;

@Service
public class StockService {
	@Autowired
	private StockRepository stockRepository;
	
	public Stock save(Stock stock) {
		return stockRepository.save(stock);
	}
	
	public List<Stock> save(List<Stock> stockList) {
		return stockRepository.saveAll(stockList);
	}
}
