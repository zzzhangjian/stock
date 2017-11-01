package cn.irss.stock.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import cn.irss.stock.core.BaseController;
import cn.irss.stock.disruptor.StockEvent;
import cn.irss.stock.disruptor.StockEventHandler;
import cn.irss.stock.domain.JsonResult;
import cn.irss.stock.domain.Stock;
import cn.irss.stock.parse.StockDataParseByDayImpl;
import cn.irss.stock.parse.StockDataParseImpl;
import cn.irss.stock.repository.StockDayRepository;
import cn.irss.stock.repository.StockRepository;
import cn.irss.stock.thread.StockDayThread;
import cn.irss.stock.thread.StockThread;

@RestController
@RequestMapping("/stock")
public class StockController extends BaseController {
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private StockDayRepository stockDayRepository;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public JsonResult list(@RequestParam(defaultValue="0",name="page")String page,@RequestParam(defaultValue="10",name="size")String size){
		
			
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(Integer.valueOf(page), Integer.valueOf(size));
		Page<Stock> all = stockRepository.findAll(pageable);
		
		return JsonResult.setOk(all, "invoke success");
		
	}
	@RequestMapping(value = "/save",method=RequestMethod.POST)
	public JsonResult save(@RequestParam("stock")Stock stock) {
		stockRepository.save(stock);
		return JsonResult.setOk("save success", "invoke success");
	}
	@RequestMapping(value = "/history",method=RequestMethod.POST)
	public JsonResult history(@RequestParam("symbol")String symbol,@RequestParam(name="startYear",defaultValue="1990")String startYear,@RequestParam(name="endYear",defaultValue="2017")String endYear,@RequestParam(name="season",defaultValue="1,2,3,4")String[] season) {
		//stockRepository.save(stock);
		ExecutorService exec = Executors.newFixedThreadPool(50);
		if(StringUtils.isBlank(symbol)) {
			List<Stock> allStock = stockRepository.findAll();
			for (Stock stock : allStock) {
				for (int year = Integer.valueOf(startYear);year <= Integer.valueOf(endYear);year++) {
					for (String s:season) {
						exec.execute(new StockDayThread(stock.getSymbol(), new StockDataParseByDayImpl(""+year,s,stock.getSymbol()), stockDayRepository));
					}
				}
			}
		}else {
			for (int year = Integer.valueOf(startYear);year <= Integer.valueOf(endYear);year++) {
				for (String s:season) {
					exec.execute(new StockDayThread(symbol, new StockDataParseByDayImpl(""+year,s,symbol), stockDayRepository));
				}
			}
		}
		return JsonResult.setOk("save success", "invoke success");
	}
	
	private void batchImportStockDay(int thread) {
		List<Stock> allStock = stockRepository.findAll();
		ExecutorService exec = Executors.newFixedThreadPool(thread);
		for (Stock stock : allStock) {
			for (int y = 1990;y <= 2017;y++) {
				for (int s = 1;s <= 4;s++) {
					exec.execute(new StockDayThread(stock.getSymbol(), new StockDataParseByDayImpl(""+y,""+s,stock.getSymbol()), stockDayRepository));
				}
			}
		}
	}
	
	
	@RequestMapping(value = "/import",method=RequestMethod.POST)
	public JsonResult importStock(@Param("pre")String pre,@Param("code")String code ) {
		//异步处理
		/*for(int i=0;i<9999;i++) {
			String[] preCode = new String[] {"00","30","60"};
			for (String pc : preCode) {
				String[] preSymbol = new String[] {"0","1"};
				for (String ps : preSymbol) {
					String symbol = pc+String.format("%04d", i);
					handler.getStock(ps, symbol);
				}
			}
		}*/
		
		//线程池
		/*final List<Stock> stockList = new ArrayList<>(1000);
		for(int i=0;i<9999;i++) {
			String[] preCode = new String[] {"00","30","60"};
			for (String pc : preCode) {
				Stock stock = new Stock();
				stock.setSymbol(pc+String.format("%04d", i));
				stockList.add(stock);
			}
		}
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(200); 
		final Iterator<Stock> iterator = stockList.iterator();
		final List<Stock> list = new ArrayList<>(200);
		while(iterator.hasNext()) {
			final Stock tmp = iterator.next();
			cachedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					String[] preSymbol = new String[] {"0","1"};
					for (String ps : preSymbol) {
						synchronized (this.getClass()) {
							Stock stock = new StockEventHandler().getStock(ps, tmp.getSymbol());
							if(stock!=null &&stock.getCode()!=null) {
								list.add(stock);
								System.out.println(tmp.getSymbol());
							}
							if(list.size()>100) {
								stockRepository.saveAll(list);
								list.clear();
							}
						}
					}
				}
			});
		}*/
		
		//第二种
		/*List<Stock> stockList = new ArrayList<>(1000);
		for(int i=0;i<9999;i++) {
			String[] str = new String[] {"00","30","60"};
			for (String symbol : str) {
				
				Stock szStock = new StockEventHandler().getStock("1", symbol+String.format("%04d", i));
				if(szStock!=null &&szStock.getCode()!=null) {
					stockList.add(szStock);
				}
				Stock shStock = new StockEventHandler().getStock("0", symbol+String.format("%04d", i));
				if(shStock!=null &&shStock.getCode()!=null) {
					stockList.add(shStock);
				}
				if(stockList.size()>100) {
					stockRepository.saveAll(stockList);
					stockList.clear();
				}
			}
		}*/
		//第三种
		/*for(int i=609999;i>0;i--) {
			String symbol = String.format("%06d", i);
			System.out.println("第 【"+symbol+"】个:");
			Stock stock = getStock("1",symbol);
			if(stock!=null&&stock.getCode()!=null) {
				System.out.println(stock);
				stockRepository.save(stock);
			}
			Stock stock2 = getStock("0",symbol);
			if(stock2!=null&&stock2.getSymbol()!=null) {
				System.out.println(stock2);
				stockRepository.save(stock2);
			}
		}*/
		
		//disruptor
		//run();
		
		//线程池
		//读取文件
		BufferedReader bufferedReader = null;
		try {
			/*bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("d:/stock.txt")),1024*1024*10);
			List<String> list = new ArrayList<>(1000);
			String line;
			while((line =bufferedReader.readLine())!=null) {
				list.add(line);
			}*/
			List<String> list = new ArrayList<>(1000);
			for(int i=0;i<9999;i++) {
				String[] preCode = new String[] {"00","30","60"};
				for (String pc : preCode) {
					String[] preSymbol = new String[] {"0","1"};
					for (String ps : preSymbol) {
						String symbol = ps + pc + String.format("%04d", i);
						list.add(symbol);
					}
				}
			}
			System.out.println("list : 完成"+list.size());
			
			ExecutorService exec = Executors.newFixedThreadPool(100);
			for (String tmp : list) {
				exec.execute(new StockThread(tmp, new StockDataParseImpl(tmp.substring(0, 1),tmp.substring(1)), stockRepository));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(bufferedReader!=null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			bufferedReader=null;
		}
		
		
		return JsonResult.setOk("import success", "invoke success");
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void run() {
		ExecutorService executor = Executors.newWorkStealingPool(500);
		
		Disruptor<StockEvent> disruptor = new Disruptor<>(StockEvent.stockEventFactory, 1024, executor);
		
		final EventHandler<StockEvent> handler = new StockEventHandler();
		
		disruptor.handleEventsWith(handler);
		RingBuffer<StockEvent> ringBuffer = disruptor.start();
			
		for(int i=0;i<9999;i++) {
			String[] preCode = new String[] {"00","30","60"};
			for (String pc : preCode) {
				String[] preSymbol = new String[] {"0","1"};
				for (String ps : preSymbol) {
					
					long seq = ringBuffer.next();
					
					StockEvent stockEvent = ringBuffer.get(seq);
					stockEvent.setPre(ps);
					stockEvent.setCode(pc+String.format("%04d", i));
					
					ringBuffer.publish(seq);
				}
			}
		}
		
		disruptor.shutdown();
		executor.shutdown();
	}

}
