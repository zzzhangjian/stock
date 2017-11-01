package cn.irss.stock.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class StockDay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5207053595411765322L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private java.sql.Date date;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal close;
	private BigDecimal updown;
	private BigDecimal updownRate;
	private Long volume;
	private Long volAmount;
	private BigDecimal amplitude;
	private BigDecimal turnover;
	private String symbol;
	
	public StockDay() {}
	
	public StockDay(Long id, Date date, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close,
			BigDecimal updown, BigDecimal updownRate, Long volume, Long volAmount, BigDecimal amplitude,
			BigDecimal turnover, String symbol) {
		super();
		this.id = id;
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.updown = updown;
		this.updownRate = updownRate;
		this.volume = volume;
		this.volAmount = volAmount;
		this.amplitude = amplitude;
		this.turnover = turnover;
		this.symbol = symbol;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
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

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getUpdown() {
		return updown;
	}

	public void setUpdown(BigDecimal updown) {
		this.updown = updown;
	}

	public BigDecimal getUpdownRate() {
		return updownRate;
	}

	public void setUpdownRate(BigDecimal updownRate) {
		this.updownRate = updownRate;
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

	public BigDecimal getAmplitude() {
		return amplitude;
	}

	public void setAmplitude(BigDecimal amplitude) {
		this.amplitude = amplitude;
	}

	public BigDecimal getTurnover() {
		return turnover;
	}

	public void setTurnover(BigDecimal turnover) {
		this.turnover = turnover;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return "StockDay [id=" + id + ", date=" + date + ", open=" + open + ", high=" + high + ", low=" + low
				+ ", close=" + close + ", updown=" + updown + ", updownRate=" + updownRate + ", volume=" + volume
				+ ", volAmount=" + volAmount + ", amplitude=" + amplitude + ", turnover=" + turnover + ", symbol="
				+ symbol + "]";
	}

	
	
}
