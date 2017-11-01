package cn.irss.stock.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.irss.stock.domain.JsonResult;
import cn.irss.stock.exception.ServiceProcessException;

public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected JsonResult restProcessor(ResultProcessor processor){
    	JsonResult result = new JsonResult();
        try{
        	result.setReult(processor.process());
        }
        catch (ServiceProcessException e1){
            logger.error("ServiceProcess Error Log :"+e1.getLocalizedMessage(),e1);
            result.setError(e1.getMessage());
        }
        catch (Exception e){
            logger.error("Error Log :"+e.getLocalizedMessage(),e);
            result.setError(e.getMessage());
        }

        return result;
    }
	
}
