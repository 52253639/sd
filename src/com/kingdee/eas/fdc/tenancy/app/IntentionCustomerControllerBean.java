package com.kingdee.eas.fdc.tenancy.app;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerCollection;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class IntentionCustomerControllerBean extends AbstractIntentionCustomerControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.IntentionCustomerControllerBean");

	protected boolean isUseNumber() {
		return false;
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		IntentionCustomerInfo info=this.getIntentionCustomerInfo(ctx, new ObjectUuidPK(billId));
		String msg="";
		try {
			msg = auditCustomer(ctx,info.getNumber());
		} catch (SQLException e) {
			e.printStackTrace();
			msg=e.getMessage();
		}
        if(msg!=null){
        	throw new EASBizException(new NumericExceptionSubItem("100",msg));
        }else{
        	super.audit(ctx, billId);
        }
	}
    private String auditCustomer(Context ctx,String number) throws BOSException, SQLException{
    	StringBuffer sql = new StringBuffer();
	    sql.append("select furl,fparam from T_WC_URL where fnumber=''");
	    ISQLExecutor isql = SQLExecutorFactory.getLocalInstance(ctx,sql.toString());
	    IRowSet rs = isql.executeSQL();
	    String url=null;
	    String param=null;
	    if(rs.size()==0){
	    	return "微信接口URL未配置";
	    }else{
	    	while (rs.next()){
	    		url=rs.getString("url");
	    		param=rs.getString("param");
	    	}
		}
    	JSONObject jsonObject = new JSONObject();
		jsonObject.put("number", number);
         
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost(url);
        List formparams = new ArrayList();
        formparams.add(new BasicNameValuePair(param, jsonObject.toString()));

		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httpPost.setEntity(entity);
	         
	        HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        if (httpEntity != null) {
	        	String resData = EntityUtils.toString(httpEntity, "UTF-8");
	        	closeableHttpClient.close();
	        	JSONObject jsonRSObject = JSONObject.fromObject(resData);
	        	if (jsonRSObject.getString("status").equals("1")) {
	        		return null;
	        	} else {
	        		return jsonRSObject.getString("msg");
	        	}
	    	} else {
	    		return "微信接口异常";
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			return "微信接口异常:"+e.getMessage();
		} 
    }
    
}