package cn.irss.stock.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stock implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8748716591563555931L;
    private Integer id;
    private String name;
    @Id
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public Long getVolume() {
		return volume;
	}
	public void setVolume(Long volume) {
		this.volume = volume;
	}
	public Long getVolAmount() {
		return volAmount;
	}
	public void setVolAmount(Long volAmount) {
		this.volAmount = volAmount;
	}
	public BigDecimal getTurnover() {
		return turnover;
	}
	public void setTurnover(BigDecimal turnover) {
		this.turnover = turnover;
	}
	public BigDecimal getAmplitude() {
		return amplitude;
	}
	public void setAmplitude(BigDecimal amplitude) {
		this.amplitude = amplitude;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public BigDecimal getUpdown() {
		return updown;
	}
	public void setUpdown(BigDecimal updown) {
		this.updown = updown;
	}
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	public BigDecimal getYestclose() {
		return yestclose;
	}
	public void setYestclose(BigDecimal yestclose) {
		this.yestclose = yestclose;
	}
	@Override
	public String toString() {
		return "Stock [id=" + id + ", name=" + name + ", code=" + code + ", fullName=" + fullName + ", type=" + type
				+ ", status=" + status + ", open=" + open + ", price=" + price + ", high=" + high + ", low=" + low
				+ ", volume=" + volume + ", volAmount=" + volAmount + ", turnover=" + turnover + ", amplitude="
				+ amplitude + ", time=" + time + ", symbol=" + symbol + ", updown=" + updown + ", percent=" + percent
				+ ", yestclose=" + yestclose + "]";
	}
}