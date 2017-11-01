package cn.irss.stock.disruptor;

import com.lmax.disruptor.EventFactory;

public class StockEvent { 
    private String code;
    private String pre;
    
    public String getPre() {
		return pre;
	}

	public void setPre(String pre) {
		this.pre = pre;
	}

	public String getCode() { 
        return code; 
    } 
 
    public void setCode(String code) { 
        this.code = code; 
    } 
    
    public static final EventFactory<StockEvent> stockEventFactory = new EventFactory<StockEvent>() {

		@Override
		public StockEvent newInstance() {
			
			return new StockEvent();
		}
		
	};
} 