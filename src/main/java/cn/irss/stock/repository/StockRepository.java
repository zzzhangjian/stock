package cn.irss.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.irss.stock.domain.Stock;
@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

}
