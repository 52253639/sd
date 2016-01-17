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
import com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerCollection;
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
		String msg=auditCustomer(info.getNumber());
        if(msg!=null){
        	throw new EASBizException(new NumericExceptionSubItem("100","msg"));
        }else{
        	super.audit(ctx, billId);
        }
	}
    private String auditCustomer(String number){
    	JSONObject jsonObject = new JSONObject();
		jsonObject.put("number", number);
         
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost("url");
        List formparams = new ArrayList();
        formparams.add(new BasicNameValuePair("phCenterDo", jsonObject.toString()));

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
	    		return "接口异常";
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			return "接口异常";
		} 
    }
    
}