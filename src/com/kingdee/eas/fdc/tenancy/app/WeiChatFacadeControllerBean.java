package com.kingdee.eas.fdc.tenancy.app;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
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
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.eas.base.permission.IUser;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.basedata.assistant.CityCollection;
import com.kingdee.eas.basedata.assistant.CityFactory;
import com.kingdee.eas.basedata.assistant.ICity;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.merch.common.EntityViewHelper.EntityViewer;
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
		
		JSONObject rs = new JSONObject();
		
		IBroker ibroker=BrokerFactory.getLocalInstance(ctx);
		if(ibroker.exists("select id from where number='"+number+"'")){
			rs.put("state", "0");
			rs.put("msg", "编码重复！");
		}else{
			BrokerInfo info=new BrokerInfo();
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
			info.setAccountName(accountNum);
			info.setAccountNum(accountNum);
			
			ibroker.save(info);
			
			rs.put("state", "1");
			rs.put("msg", "同步成功！");
		}
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
		Double infoAmount=obj.getDouble("infoAmount");
		Double amount=obj.getDouble("amount");
		String brokerNumber=obj.getString("brokerNumber");
		String bizDate=obj.getString("bizDate");
		
		JSONObject rs = new JSONObject();
		
		IIntentionCustomer iintentionCustomer=IntentionCustomerFactory.getLocalInstance(ctx);
		ISellProject iproject=SellProjectFactory.getLocalInstance(ctx);
		IBroker ibroker=BrokerFactory.getLocalInstance(ctx);
		if(iintentionCustomer.exists("select id from where number='"+number+"'")){
			rs.put("state", "0");
			rs.put("msg", "编码重复！");
		}else{
			IntentionCustomerInfo info=new IntentionCustomerInfo();
			if(StringUtils.isEmpty(project)){
				rs.put("state", "0");
				rs.put("msg", "意向楼盘/产业园区不能为空！");
			}else{
				SellProjectCollection projectCol=iproject.getSellProjectCollection("select * from where number='"+project+"'");
				if(projectCol.size()==0){
					rs.put("state", "0");
					rs.put("msg", "意向楼盘/产业园区在EAS不存在！");
				}
				info.setProject(projectCol.get(0));
			}
			if(StringUtils.isEmpty(brokerNumber)){
				rs.put("state", "0");
				rs.put("msg", "经纪人不能为空！");
			}else{
				BrokerCollection brokerCol=ibroker.getBrokerCollection("select * from where number='"+brokerNumber+"'");
				if(brokerCol.size()==0){
					if(brokerCol.size()==0){
						rs.put("state", "0");
						rs.put("msg", "经纪人在EAS不存在！");
					}
				}
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
			info.setInfoAmount(BigDecimal.valueOf(infoAmount));
			info.setAmount(BigDecimal.valueOf(amount));
			info.setBizDate(FDCDateHelper.stringToDate(bizDate));
			
			iintentionCustomer.save(info);
			
			rs.put("state", "1");
			rs.put("msg", "同步成功！");
		}
		return rs.toString();
	}
	protected String _sysFDCCsutomer(Context ctx, String str)throws BOSException, EASBizException {
		JSONObject obj = JSONObject.fromObject(str);
		
		String number=obj.getString("number");
		String name=obj.getString("name");
		String phone=obj.getString("phone");
		String city=obj.getString("city");
		
		String project=obj.getString("project");
		String saleMan=obj.getString("saleMan");
		
		JSONObject rs = new JSONObject();
		
		IFDCCustomer ifdcCustomer=FDCCustomerFactory.getLocalInstance(ctx);
		ISellProject iproject=SellProjectFactory.getLocalInstance(ctx);
		ICity icity=CityFactory.getLocalInstance(ctx);
		IUser iuser=UserFactory.getLocalInstance(ctx);
		if(ifdcCustomer.exists("select id from where number='"+number+"'")){
			rs.put("state", "0");
			rs.put("msg", "编码重复！");
		}else{
			FDCCustomerInfo info=new FDCCustomerInfo();
			if(StringUtils.isEmpty(project)){
				rs.put("state", "0");
				rs.put("msg", "意向楼盘/产业园区不能为空！");
			}else{
				SellProjectCollection projectCol=iproject.getSellProjectCollection("select * from where number='"+project+"'");
				if(projectCol.size()==0){
					rs.put("state", "0");
					rs.put("msg", "意向楼盘/产业园区在EAS不存在！");
				}
				info.setProject(projectCol.get(0));
			}
			if(StringUtils.isEmpty(saleMan)){
				rs.put("state", "0");
				rs.put("msg", "业务顾问不能为空！");
			}else{
				UserCollection userCol=iuser.getUserCollection("select * from where number='"+saleMan+"'");
				if(userCol.size()==0){
					rs.put("state", "0");
					rs.put("msg", "业务顾问在EAS不存在！");
				}
				info.setSalesman(userCol.get(0));
			}
			ifdcCustomer.submit(info);
			rs.put("state", "1");
			rs.put("msg", "同步成功！");
		}
		return rs.toString();
	}
	protected String _sysTrackRecord(Context ctx, String str)throws BOSException, EASBizException {
		JSONObject obj = JSONObject.fromObject(str);
		
		String number=obj.getString("number");
		String trackDate=obj.getString("trackDate");
		
		String remark=obj.getString("remark");
		String fdcCustomerNum=obj.getString("fdcCustomerNum");
		String receptionMode=obj.getString("receptionMode");

		JSONObject rs = new JSONObject();
		
		ITrackRecord itrackRecord=TrackRecordFactory.getLocalInstance(ctx);
		IFDCCustomer ifdcCustomer=FDCCustomerFactory.getLocalInstance(ctx);
		IReceptionType ireceptionType=ReceptionTypeFactory.getLocalInstance(ctx);
		if(itrackRecord.exists("select id from where number='"+number+"'")){
			rs.put("state", "0");
			rs.put("msg", "编码重复！");
		}else{
			TrackRecordInfo info=new TrackRecordInfo();
			if(StringUtils.isEmpty(fdcCustomerNum)){
				rs.put("state", "0");
				rs.put("msg", "客户台账编码不能为空！");
			}else{
				FDCCustomerCollection customerCol=ifdcCustomer.getFDCCustomerCollection("select * from where number='"+ifdcCustomer+"'");
				if(customerCol.size()==0){
					rs.put("state", "0");
					rs.put("msg", "客户台账编码在EAS不存在！");
				}
				info.setHead(customerCol.get(0));
				info.setSaleMan(customerCol.get(0).getSalesman());
				info.setSellProject(customerCol.get(0).getProject());
			}
			if(StringUtils.isEmpty(receptionMode)){
				rs.put("state", "0");
				rs.put("msg", "接待方式不能为空！");
			}else{
				ReceptionTypeCollection receptionTypeCol=ireceptionType.getReceptionTypeCollection("select * from where number='"+receptionMode+"'");
				if(receptionTypeCol.size()==0){
					rs.put("state", "0");
					rs.put("msg", "接待方式在EAS不存在！");
				}
				info.setReceptionType(receptionTypeCol.get(0));
			}
			info.setDescription(remark);
			info.setEventDate(FDCDateHelper.stringToDate(trackDate));
			
			itrackRecord.submit(info);
			rs.put("state", "1");
			rs.put("msg", "同步成功！");
		}
		return rs.toString();
	}
    
    
}