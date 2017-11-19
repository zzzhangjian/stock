package cn.irss.stock.controller;

import static org.assertj.core.api.Assertions.setMaxLengthForSingleLineDescription;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.irss.stock.core.BaseController;
@RestController
@RequestMapping("/download")
public class DownloadController extends BaseController {
	
	private HashMap<String,Long> fileMap = new HashMap<String,Long>(50);
	//@Value("${path}")
	private String path = "D:/";
	@RequestMapping("/demo.do")
	public void demo(String id,final HttpServletResponse response){
		System.out.println("id:["+id+"]");
	    download(id,response);  
	}
	
	@RequestMapping("/checkInfo.do")
	@ResponseBody
	public String checkInfo(String id,final HttpServletResponse response){
		
		if(fileMap.get(id) == 0) { 
			//正在处理
			return "";
		}
		
		File file = new File(this.path+"/tmp/"+id+".txt");
		//创建文件
		if(fileMap.get(id)>0 && file.exists()) {
			return "success";
		}
	
		fileMap.put(id, 0L); //开始处理
		
		try {
			Thread.sleep(10*1000L);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		process(file);
		//处理完成
		fileMap.put(id, file.length()); 
		
		return "success"; 
		
	}

	private void process(File file) {
		//读取数据库创建文件
		try {
			file.createNewFile();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));  
			String fileName = "POI批量下载测试。。。。。。。";
			bos.write(fileName.getBytes(),0,fileName.length());
			bos.close();
			fileMap.put(fileName, file.length());//处理结束
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void download(String id,final HttpServletResponse response) {
		OutputStream bos = null;
		InputStream bis = null;
		try {
			System.out.println(this.path+"/tmp/"+id);
			
			File file = new File(this.path+"/tmp/"+id+".txt");
			String fileName = "下载文件.txt";  
			fileName = URLEncoder.encode(fileName, "UTF-8");  
			response.reset();  
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
			response.addHeader("Content-Length", "" + file.length());  
			response.setContentType("application/octet-stream;charset=UTF-8");  
			bos = new BufferedOutputStream(response.getOutputStream());  
			
			bis = new BufferedInputStream(new FileInputStream(file));
			int len;
			byte[] data = new byte[1024*1024*100];
			while((len=bis.read(data)) != -1) {
				bos.write(data,0,len);
			}
			bos.flush();  
			bis.close();
			bos.close();
			file.delete(); //删除文件
			fileMap.remove(id);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(bis != null) {
				  bis.close();
				}
			} catch (IOException e) {
				bis = null;
			}
			try {
				if(bos != null) {
				  bos.close();
				}
			} catch (IOException e) {
				bos = null;
			}
		}
	}
	
}
