package com.kingdee.eas.fdc.tenancy.app;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
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
import java.math.BigDecimal;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.CityCollection;
import com.kingdee.eas.basedata.assistant.CityFactory;
import com.kingdee.eas.basedata.assistant.ICity;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.IFDCCustomer;
import com.kingdee.eas.fdc.sellhouse.ISellProject;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum;
import com.kingdee.eas.fdc.tenancy.CommissionApplyInfo;
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
import com.kingdee.eas.fdc.tenancy.IntentionCustomerCollection;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

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
			msg = auditCustomer(ctx,info.getNumber(),info.getInfoAmount(),info.getPayedInfoAmount());
		} catch (SQLException e) {
			e.printStackTrace();
			msg=e.getMessage();
		}
        if(msg!=null){
        	throw new EASBizException(new NumericExceptionSubItem("100",msg));
        }else{
        	
        	super._audit(ctx, billId);
        	
        	IFDCCustomer ifdcCustomer=FDCCustomerFactory.getLocalInstance(ctx);
        	ICity icity=CityFactory.getLocalInstance(ctx);
        	FDCCustomerInfo cus=new FDCCustomerInfo();
        	
        	cus.setCU(SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(info.getProject().getId())).getCU());
        	cus.setProject(info.getProject());
        	cus.setName(info.getName());
        	cus.setSalesman(info.getSaleMan());
        	cus.setQQ(info.getNumber());
        	cus.setCustomerType(CustomerTypeEnum.EnterpriceCustomer);
        	cus.setIsForTen(true);
        	cus.setIsEnabled(true);
        	cus.setIsImportantTrack(false);
        	cus.setTrackPhase(TrackPhaseEnum.PotentialCustomer);
        	cus.setCreator(info.getSaleMan());
        	
        	ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
        	cus.setNumber(iCodingRuleManager.getNumber(cus, cus.getCU().getId().toString()));
        	
			if(!StringUtils.isEmpty(info.getCity())){
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("name",info.getCity()));
				view.setFilter(filter);
				CityCollection cityCol=icity.getCityCollection(view);
				if(cityCol.size()>0){
					cus.setCity(cityCol.get(0));
				}
			}
			
			ifdcCustomer.submit(cus);
        }
	}
    private String auditCustomer(Context ctx,String number,BigDecimal infoAmount,BigDecimal payedInfoAmount) throws BOSException, SQLException{
    	StringBuffer sql = new StringBuffer();
	    sql.append("select furl,fparam from T_WC_URL where fnumber='WS001'");
	    ISQLExecutor isql = SQLExecutorFactory.getLocalInstance(ctx,sql.toString());
	    IRowSet rs = isql.executeSQL();
	    String url=null;
	    String param=null;
	    if(rs.size()==0){
	    	return "意向客户微信接口URL未配置";
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
        formparams.add(new BasicNameValuePair("amount", "0"));
        formparams.add(new BasicNameValuePair("payedAmount", "0"));
        
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
		    		return "意向客户微信接口异常";
		    	}
			} catch (Exception e) {
				if(i>3){
					e.printStackTrace();
					return "意向客户微信接口异常:"+e.getMessage();
				}else{
					i=i+1;
				}
			}
		}
    }
    protected void _pay(Context ctx, BOSUuid id) throws BOSException,EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		IntentionCustomerInfo info=this.getIntentionCustomerInfo(ctx, new ObjectUuidPK(id),sic);
		if(!info.getState().equals(FDCBillStateEnum.AUDITTED)){
			throw new EASBizException(new NumericExceptionSubItem("100","单据未审批不能进行支付！"));
		}
		if(info.isIsPayed()){
			throw new EASBizException(new NumericExceptionSubItem("100","信息费已支付！"));
		}
		String msg="";
		try {
			msg = auditCustomer(ctx,info.getNumber(),info.getInfoAmount(),info.getInfoAmount());
		} catch (SQLException e) {
			e.printStackTrace();
			msg=e.getMessage();
		}
        if(msg!=null){
        	throw new EASBizException(new NumericExceptionSubItem("100",msg));
        }else{
        	IntentionCustomerInfo billInfo = new IntentionCustomerInfo();
    		billInfo.setId(id);
    		billInfo.setIsPayed(true);
    		billInfo.setPayedInfoAmount(info.getInfoAmount());
    		SelectorItemCollection selector = new SelectorItemCollection();
    		selector.add("isPayed");
    		selector.add("payedInfoAmount");
    		_updatePartial(ctx, billInfo, selector);
        }
	}
}