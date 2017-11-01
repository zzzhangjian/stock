package cn.irss.stock.disruptor;

import com.lmax.disruptor.EventFactory;

public class StockEventFactory implements EventFactory<StockEvent> { 
    @Override 
    public StockEvent newInstance() { 
        return new StockEvent(); 
    } 
} 