package cn.irss.stock.repository.elasticsearch;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import cn.irss.stock.domain.elasticsearch.StockEntity;

@Repository
public interface ElasticsearchStockRepository extends ElasticsearchRepository<StockEntity, Integer> {
	public List<StockEntity> findByName(String name);
}
