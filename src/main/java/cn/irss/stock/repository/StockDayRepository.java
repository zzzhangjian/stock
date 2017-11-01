package cn.irss.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.irss.stock.domain.StockDay;
@Repository
public interface StockDayRepository extends JpaRepository<StockDay, Long> {

}
