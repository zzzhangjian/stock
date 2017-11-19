package cn.irss.stock.controller;

import java.util.List;

import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.irss.stock.core.BaseController;
import cn.irss.stock.domain.elasticsearch.StockEntity;
import cn.irss.stock.repository.elasticsearch.ElasticsearchStockRepository;
@RestController
@RequestMapping("/esstock")
public class ESController extends BaseController {
	
	@Autowired
	private ElasticsearchStockRepository elasticsearchStockRepository;
	
	@RequestMapping("/all")
	public List<StockEntity> all(){
		long count = elasticsearchStockRepository.count();
		System.out.println("索引库共:{"+count+"}条数据！");
		List<StockEntity> findByName = elasticsearchStockRepository.findByName("上证指数");
		return findByName;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/suggest")
	public List<StockEntity> suggest(String prefix){
		
		String field = "name";
		PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery("name", prefix);
		Page<StockEntity> searchResult = elasticsearchStockRepository.search(prefixQueryBuilder, new PageRequest(0, 10));
		return searchResult.getContent();
	}
	
	
	
	
}
