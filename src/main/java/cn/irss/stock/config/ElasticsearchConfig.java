package cn.irss.stock.config;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
@ComponentScan
@Configuration
@EnableAutoConfiguration
public class ElasticsearchConfig {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${cluster-nodes-ip}")
	private String ip;
	@Value("${cluster-nodes-port}")
	private String port;
	
	@SuppressWarnings("resource")
	@Bean
    public Client client() {
       TransportClient client = null;
       try {
    	   client = new PreBuiltTransportClient(Settings.EMPTY)
    		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), Integer.valueOf(port)));
       } catch (Exception e) {
    	   logger.error(e.toString());
       }
       return client;
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client) throws Exception {
       return new ElasticsearchTemplate(client);
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
