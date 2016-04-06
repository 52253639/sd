package com.kingdee.eas.fdc.tenancy.app;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.assistant.CityCollection;
import com.kingdee.eas.basedata.assistant.CityFactory;
import com.kingdee.eas.basedata.assistant.ICity;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.IFDCCustomer;
import com.kingdee.eas.fdc.tenancy.CommissionApplyCollection;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerFactory;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.tenancy.CommissionApplyInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

public class CommissionApplyControllerBean extends AbstractCommissionApplyControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.CommissionApplyControllerBean");
    
    protected boolean isUseNumber() {
		return false;
	}
    protected boolean isUseName() {
		return false;
	}
    protected void _audit(Context ctx, BOSUuid id) throws BOSException,EASBizException {
    	SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("intentionCustomer.*");
		CommissionApplyInfo info=this.getCommissionApplyInfo(ctx, new ObjectUuidPK(id),sic);
		if(!this.exists(ctx, "select * from where intentionCustomer.id='"+info.getIntentionCustomer().getId().toString()+"' and id!='"+id+"'")){
			String msg="";
			try {
				msg = payCustomer(ctx,info.getIntentionCustomer().getNumber(),info.getIntentionCustomer().getInfoAmount(),info.getIntentionCustomer().getPayedInfoAmount(),info.getCommissionAmount(),null);
			} catch (SQLException e) {
				e.printStackTrace();
				msg=e.getMessage();
			}
	        if(msg!=null){
	        	throw new EASBizException(new NumericExceptionSubItem("100",msg));
	        }else{
	        	SelectorItemCollection upsic = new SelectorItemCollection();
	        	upsic.add("amount");
	    		
	    		IntentionCustomerInfo cus=new IntentionCustomerInfo();
	    		cus.setId(info.getIntentionCustomer().getId());
	    		cus.setAmount(info.getCommissionAmount());
	    		IntentionCustomerFactory.getLocalInstance(ctx).updatePartial(cus, upsic);
	    		
	        	super._audit(ctx, id);
	        }
		}else{
			super._audit(ctx, id);
		}
	}
	protected void _pay(Context ctx, BOSUuid id) throws BOSException,EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("intentionCustomer.*");
		CommissionApplyInfo info=this.getCommissionApplyInfo(ctx, new ObjectUuidPK(id),sic);
		if(!info.getState().equals(FDCBillStateEnum.AUDITTED)){
			throw new EASBizException(new NumericExceptionSubItem("100","单据未审批不能进行支付！"));
		}
		if(info.isIsPayed()){
			throw new EASBizException(new NumericExceptionSubItem("100","佣金已支付！"));
		}
		String msg="";
		try {
			msg = payCustomer(ctx,info.getIntentionCustomer().getNumber(),info.getIntentionCustomer().getInfoAmount(),info.getIntentionCustomer().getPayedInfoAmount(),info.getCommissionAmount(),FDCHelper.add(info.getAmount(), info.getIntentionCustomer().getPayedAmount()));
		} catch (SQLException e) {
			e.printStackTrace();
			msg=e.getMessage();
		}
        if(msg!=null){
        	throw new EASBizException(new NumericExceptionSubItem("100",msg));
        }else{
        	CommissionApplyInfo billInfo = new CommissionApplyInfo();
    		billInfo.setId(id);
    		billInfo.setIsPayed(true);
    		
    		SelectorItemCollection selector = new SelectorItemCollection();
    		selector.add("isPayed");

    		_updatePartial(ctx, billInfo, selector);
    		
    		SelectorItemCollection upsic = new SelectorItemCollection();
    		upsic.add("payedAmount");
    		upsic.add("amount");
    		
    		IntentionCustomerInfo cus=new IntentionCustomerInfo();
    		cus.setId(info.getIntentionCustomer().getId());
    		cus.setPayedAmount(FDCHelper.add(info.getAmount(), info.getIntentionCustomer().getPayedAmount()));
    		cus.setAmount(info.getCommissionAmount());
    		IntentionCustomerFactory.getLocalInstance(ctx).updatePartial(cus, upsic);
        }
	}
	private String payCustomer(Context ctx,String number,BigDecimal infoAmount,BigDecimal payedInfoAmount,BigDecimal amount,BigDecimal payedAmount) throws BOSException, SQLException{
    	StringBuffer sql = new StringBuffer();
	    sql.append("select furl,fparam from T_WC_URL where fnumber='WS001'");
	    ISQLExecutor isql = SQLExecutorFactory.getLocalInstance(ctx,sql.toString());
	    IRowSet rs = isql.executeSQL();
	    String url=null;
	    String param=null;
	    if(rs.size()==0){
	    	return "佣金支付微信接口URL未配置";
	    }else{
	    	while (rs.next()){
	    		url=rs.getString("furl");
	    		param=rs.getString("fparam");
	    	}
		}
		
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost(url);
        List formparams = new ArrayList();
        formparams.add(new BasicNameValuePair("method", param));
        formparams.add(new BasicNameValuePair("number", number));
        if(infoAmount!=null){
        	formparams.add(new BasicNameValuePair("infoAmount", infoAmount.toString()));
        }else{
        	formparams.add(new BasicNameValuePair("infoAmount", "0"));
        }
        if(payedInfoAmount!=null){
        	formparams.add(new BasicNameValuePair("payedInfoAmount", payedInfoAmount.toString()));
        }else{
        	formparams.add(new BasicNameValuePair("payedInfoAmount", "0"));
        }
        if(amount!=null){
        	formparams.add(new BasicNameValuePair("amount", amount.toString()));
        }else{
        	formparams.add(new BasicNameValuePair("amount", "0"));
        }
        if(payedAmount!=null){
        	formparams.add(new BasicNameValuePair("payedAmount", payedAmount.toString()));
        }else{
        	formparams.add(new BasicNameValuePair("payedAmount", "0"));
        }
        int i=0;
		while(true){
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
				httpPost.setEntity(entity);
		         
				CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);
		        HttpEntity httpEntity = httpResponse.getEntity();
		        if (httpEntity != null) {
		        	String resData = EntityUtils.toString(httpEntity, "UTF-8");
		        	closeableHttpClient.close();
		        	httpResponse.close();
		        	
		        	JSONObject jsonRSObject = JSONObject.fromObject(resData);
		        	if (jsonRSObject.getString("state").equals("1")) {
		        		return null;
		        	} else {
		        		return jsonRSObject.getString("msg");
		        	}
		    	} else {
		    		return "佣金支付微信接口异常";
		    	}
			} catch (Exception e) {
				if(i>3){
					e.printStackTrace();
					return "佣金支付微信接口异常:"+e.getMessage();
				}else{
					i=i+1;
				}
			}
		}
    }
    
}