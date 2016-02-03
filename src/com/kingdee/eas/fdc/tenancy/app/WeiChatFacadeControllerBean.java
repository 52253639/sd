package com.kingdee.eas.fdc.tenancy.app;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
import java.util.HashMap;
import java.util.List;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.IUser;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.basedata.assistant.CityCollection;
import com.kingdee.eas.basedata.assistant.CityFactory;
import com.kingdee.eas.basedata.assistant.ICity;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.merch.common.EntityViewHelper.EntityViewer;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.IFDCCustomer;
import com.kingdee.eas.fdc.sellhouse.IReceptionType;
import com.kingdee.eas.fdc.sellhouse.ISellProject;
import com.kingdee.eas.fdc.sellhouse.ITrackRecord;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeCollection;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SellProject;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.tenancy.Broker;
import com.kingdee.eas.fdc.tenancy.BrokerCollection;
import com.kingdee.eas.fdc.tenancy.BrokerFactory;
import com.kingdee.eas.fdc.tenancy.BrokerInfo;
import com.kingdee.eas.fdc.tenancy.IBroker;
import com.kingdee.eas.fdc.tenancy.IIntentionCustomer;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerFactory;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

public class WeiChatFacadeControllerBean extends AbstractWeiChatFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.WeiChatFacadeControllerBean");

	protected String _synBroker(Context ctx, String str) throws BOSException,EASBizException {
		JSONObject obj = JSONObject.fromObject(str);
		String number=obj.getString("number");
		String name=obj.getString("name");
		String phone=obj.getString("phone");
		String password=obj.getString("password");
		String sex=obj.getString("sex");
		String idNum=obj.getString("idNum");
		String weChatNum=obj.getString("weChatNum");
		String bank=obj.getString("bank");
		String accountName=obj.getString("accountName");
		String accountNum=obj.getString("accountNum");
		String idCardPictureURL=obj.getString("idCardPictureURL");
		
		JSONObject rs = new JSONObject();
		
		if(StringUtils.isEmpty(number)||StringUtils.isEmpty(name)){
			rs.put("state", "0");
			rs.put("msg", "接口必要字段不能为空！");
			return rs.toString();
		}
		
		IBroker ibroker=BrokerFactory.getLocalInstance(ctx);
		BOSUuid id=null;
		BrokerCollection col=ibroker.getBrokerCollection("select id from where number='"+number+"'");
		if(col.size()>0){
			id=col.get(0).getId();
		}
		BrokerInfo info=new BrokerInfo();
		info.setId(id);
		info.setNumber(number);
		info.setName(name);
		info.setPhone(phone);
		info.setPassword(password);
		if("1".equals(sex)){
			info.setSex(SexEnum.Mankind);
		}else if("2".equals(sex)){
			info.setSex(SexEnum.Womenfolk);
		}
		info.setIdNum(idNum);
		info.setWeChatNum(weChatNum);
		info.setBank(bank);
		info.setAccountName(accountName);
		info.setAccountNum(accountNum);
		info.setIdCardPictureURL(idCardPictureURL);
		
		ibroker.submit(info);
		
		rs.put("state", "1");
		rs.put("msg", "同步成功！");
		return rs.toString();
	}
	protected String _synCustomer(Context ctx, String str) throws BOSException,EASBizException {
		JSONObject obj = JSONObject.fromObject(str);
		
		String number=obj.getString("number");
		String name=obj.getString("name");
		String phone=obj.getString("phone");
		String sex=obj.getString("sex");
		String city=obj.getString("city");
		
		String project=obj.getString("project");
		String brokerNumber=obj.getString("brokerNumber");
		String bizDate=obj.getString("bizDate");
		
//		String number="12312";
//		String name="123123";
//		String phone="123123123";
//		String sex="1";
//		String city="12312";
//		
//		String project="B-571DC14.01.03";
//		String brokerNumber="123";
//		String bizDate="2016-01-25";
		
		JSONObject rs = new JSONObject();
		
		if(StringUtils.isEmpty(number)||StringUtils.isEmpty(name)||StringUtils.isEmpty(brokerNumber)){
			rs.put("state", "0");
			rs.put("msg", "接口必要字段不能为空！");
			return rs.toString();
		}
		
		IIntentionCustomer iintentionCustomer=IntentionCustomerFactory.getLocalInstance(ctx);
		ISellProject iproject=SellProjectFactory.getLocalInstance(ctx);
		IBroker ibroker=BrokerFactory.getLocalInstance(ctx);
		if(iintentionCustomer.exists("select id from where number='"+number+"'")){
			rs.put("state", "0");
			rs.put("msg", "编码重复！");
			return rs.toString();
		}else{
			IntentionCustomerInfo info=new IntentionCustomerInfo();
			if(StringUtils.isEmpty(project)){
				rs.put("state", "0");
				rs.put("msg", "意向楼盘/产业园区不能为空！");
				return rs.toString();
			}else{
				SellProjectCollection projectCol=iproject.getSellProjectCollection("select *,orgUnit.*,CU.* from where number='"+project+"'");
				if(projectCol.size()==0){
					rs.put("state", "0");
					rs.put("msg", "意向楼盘/产业园区在EAS不存在！");
					return rs.toString();
				}
				info.setProject(projectCol.get(0));
			}
			if(StringUtils.isEmpty(brokerNumber)){
				rs.put("state", "0");
				rs.put("msg", "经纪人不能为空！");
				return rs.toString();
			}else{
				BrokerCollection brokerCol=ibroker.getBrokerCollection("select * from where number='"+brokerNumber+"'");
				if(brokerCol.size()==0){
					if(brokerCol.size()==0){
						rs.put("state", "0");
						rs.put("msg", "经纪人在EAS不存在！");
						return rs.toString();
					}
				}
				info.setBroker(brokerCol.get(0));
			}
			info.setNumber(number);
			info.setName(name);
			info.setPhone(phone);
			info.setCity(city);
			if("1".equals(sex)){
				info.setSex(SexEnum.Mankind);
			}else if("2".equals(sex)){
				info.setSex(SexEnum.Womenfolk);
			}
			info.setBizDate(FDCDateHelper.stringToDate(bizDate));
			
			HashMap hmParamIn = new HashMap();
			hmParamIn.put("WEICHATINFOAMOUNT", null);
		
			HashMap hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);
			
			if(hmAllParam.get("WEICHATINFOAMOUNT")!=null){
				info.setInfoAmount(new BigDecimal(hmAllParam.get("WEICHATINFOAMOUNT").toString()));
			}
			info.setCU(info.getProject().getCU());
			info.setOrgUnit(info.getProject().getOrgUnit());
			iintentionCustomer.submit(info);
			
			rs.put("state", "1");
			rs.put("msg", "同步成功！");
		}
		return rs.toString();
	}
	protected void _synFDCCustomer(Context ctx)throws BOSException, EASBizException {
		try {
			StringBuffer sql = new StringBuffer();
		    sql.append("select furl,fparam from T_WC_URL where fnumber='WS002'");
		    ISQLExecutor isql = SQLExecutorFactory.getLocalInstance(ctx,sql.toString());
		    IRowSet rs = isql.executeSQL();
		    String url=null;
		    String param=null;
		    if(rs.size()==0){
		    	logger.error("客户台账微信接口URL未配置");
		    }else{
				while (rs.next()){
					url=rs.getString("furl");
					param=rs.getString("fparam");
				}
			}
		    sql = new StringBuffer();
		    sql.append("select a.fid id,a.fnumber number,a.fname_l2 name,b.fname_l2 city,a.fphone phone,d.fnumber saleMan,c.fnumber project from T_SHE_FDCCustomer a left join T_BD_City b on b.fid=a.FCityID left join T_SHE_SellProject c on c.fid=a.FProjectID left join T_PM_User d on d.fid=a.FSalesmanID where fisSyn=0");
		    isql = SQLExecutorFactory.getLocalInstance(ctx,sql.toString());
		    rs = isql.executeSQL();
	    	while (rs.next()){
	    		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
	            CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
	
	            HttpPost httpPost = new HttpPost(url);
	            List formparams = new ArrayList();
	            formparams.add(new BasicNameValuePair("method", param));
	            formparams.add(new BasicNameValuePair("number", rs.getString("number")));
	            formparams.add(new BasicNameValuePair("name", rs.getString("name")));
	            formparams.add(new BasicNameValuePair("city", rs.getString("city")==null?"":rs.getString("city")));
	            formparams.add(new BasicNameValuePair("phone", rs.getString("phone")==null?"":rs.getString("phone")));
	            formparams.add(new BasicNameValuePair("saleMan", rs.getString("saleMan")));
	            formparams.add(new BasicNameValuePair("project", rs.getString("project")));
	    		try {
	    			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
	    			httpPost.setEntity(entity);
	    	         
	    	        HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
	    	        HttpEntity httpEntity = httpResponse.getEntity();
	    	        if (httpEntity != null) {
	    	        	String resData = EntityUtils.toString(httpEntity, "UTF-8");
	    	        	closeableHttpClient.close();
	    	        	JSONObject jsonRSObject = JSONObject.fromObject(resData);
	    	        	if (jsonRSObject.getString("state").equals("1")) {
	    	        		logger.info("客户台账微信接口:"+rs.getString("number")+"同步成功");
	    	        		
	    	        		StringBuffer upsql = new StringBuffer();
	    	        		upsql.append("update T_SHE_FDCCustomer set fisSyn=1 where fid='"+rs.getString("id")+"'");
	    	        		DbUtil.execute(ctx,upsql.toString());
	    	        	} else {
	    	        		logger.error("客户台账微信接口:"+jsonRSObject.getString("msg"));
	    	        	}
	    	    	} else {
	    	    		logger.error("客户台账微信接口异常");
	    	    	}
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    			logger.error("客户台账微信接口异常:"+e.getMessage());
	    		} 
	    	}	
    	} catch (SQLException e) {
			e.printStackTrace();
		} 	
	}
	protected void _sysTrackRecord(Context ctx)throws BOSException, EASBizException {
		try {
			StringBuffer sql = new StringBuffer();
		    sql.append("select furl,fparam from T_WC_URL where fnumber='WS003'");
		    ISQLExecutor isql = SQLExecutorFactory.getLocalInstance(ctx,sql.toString());
		    IRowSet rs = isql.executeSQL();
		    String url=null;
		    String param=null;
		    if(rs.size()==0){
		    	logger.error("客户跟进微信接口URL未配置");
		    }else{
				while (rs.next()){
					url=rs.getString("furl");
					param=rs.getString("fparam");
				}
			}
		    sql = new StringBuffer();
		    sql.append("select a.fid id,a.fnumber number,b.fnumber cusNumber,to_char(a.FEventDate,'yyyy-MM-dd') trackDate,a.fdescription remark from T_SHE_TrackRecord a left join T_SHE_FDCCustomer b on b.fid=a.FHeadID where a.fisSyn=0");
		    isql = SQLExecutorFactory.getLocalInstance(ctx,sql.toString());
		    rs = isql.executeSQL();
	    	while (rs.next()){
	    		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
	            CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
	
	            HttpPost httpPost = new HttpPost(url);
	            List formparams = new ArrayList();
	            formparams.add(new BasicNameValuePair("method", param));
	            formparams.add(new BasicNameValuePair("number", rs.getString("number")));
	            formparams.add(new BasicNameValuePair("cusNumber", rs.getString("cusNumber")));
	            formparams.add(new BasicNameValuePair("trackDate", rs.getString("trackDate")));
	            formparams.add(new BasicNameValuePair("remark", rs.getString("remark")==null?"":rs.getString("remark")));
	    		try {
	    			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
	    			httpPost.setEntity(entity);
	    	         
	    	        HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
	    	        HttpEntity httpEntity = httpResponse.getEntity();
	    	        if (httpEntity != null) {
	    	        	String resData = EntityUtils.toString(httpEntity, "UTF-8");
	    	        	closeableHttpClient.close();
	    	        	JSONObject jsonRSObject = JSONObject.fromObject(resData);
	    	        	if (jsonRSObject.getString("state").equals("1")) {
	    	        		logger.info("客户跟进微信接口:"+rs.getString("number")+"同步成功");
	    	        		
	    	        		StringBuffer upsql = new StringBuffer();
	    	        		upsql.append("update T_SHE_TrackRecord set fisSyn=1 where fid='"+rs.getString("id")+"'");
	    	        		DbUtil.execute(ctx,upsql.toString());
	    	        	} else {
	    	        		logger.error("客户跟进微信接口:"+jsonRSObject.getString("msg"));
	    	        	}
	    	    	} else {
	    	    		logger.error("客户跟进微信接口异常");
	    	    	}
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    			logger.error("客户跟进微信接口异常:"+e.getMessage());
	    		} 
	    	}	
    	} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}