package cn.irss.stock.domain.elasticsearch;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6353742891610116679L;
	
	private Integer id;
    private String name;
    private String code;
    private String fullName;
    private String type; 
    private Integer status; 
    private BigDecimal open;    
    private BigDecimal price;   
    private BigDecimal high;  
    private BigDecimal low;   
    private Long volume;    
    private Long volAmount;    
    private BigDecimal turnover;  
    private BigDecimal amplitude;
    private String time;      
    private String symbol;
    private BigDecimal updown;
    private BigDecimal percent;
    private BigDecimal yestclose;

}
