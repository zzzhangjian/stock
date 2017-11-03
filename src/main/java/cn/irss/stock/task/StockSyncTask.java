package cn.irss.stock.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.irss.stock.domain.Stock;
import cn.irss.stock.domain.elasticsearch.StockEntity;
import cn.irss.stock.repository.StockRepository;
import cn.irss.stock.repository.elasticsearch.ElasticsearchStockRepository;

@Component
@ComponentScan
public class StockSyncTask {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ElasticsearchStockRepository elasticsearchStockRepository;
	
	@Autowired
	private StockRepository stockRepository;
	//全量同步
	@SuppressWarnings("deprecation")
//	@Scheduled(cron="0 0/5 * * * ?")
	@Scheduled(cron="${stock-allsync-cron}")
	public void allSync() {
		logger.info("开始同步数据。。。");
		Long count = stockRepository.count();
		logger.info("同步数据：【"+count+"】");
		Integer step = 100;
		Integer page = (int)((count / step) );
		
		for(int i=0;i<page;i++) {
			Page<Stock> pageOfStock = stockRepository.findAll(new PageRequest(i, step));
			List<Stock> stockList = pageOfStock.getContent();
			List<StockEntity> stockEntityList = new ArrayList<>(100);
			for (Stock stock : stockList) {
				
				StockEntity stockEntity = new StockEntity();
				BeanUtils.copyProperties(stock, stockEntity);
				stockEntityList.add(stockEntity);
			}
			Iterable<StockEntity> all = elasticsearchStockRepository.saveAll(stockEntityList);
			System.out.println(all);
		}
		
		logger.info("同步数据完成。。。");
	}
}
