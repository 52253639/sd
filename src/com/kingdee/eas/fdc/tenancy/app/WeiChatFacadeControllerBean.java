package com.kingdee.eas.fdc.tenancy.app;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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
//		info.setPhone(phone);
//		info.setPassword(password);
//		if("1".equals(sex)){
//			info.setSex(SexEnum.Mankind);
//		}else if("2".equals(sex)){
//			info.setSex(SexEnum.Womenfolk);
//		}
//		info.setIdNum(idNum);
//		info.setWeChatNum(weChatNum);
//		info.setBank(bank);
//		info.setAccountName(accountNum);
//		info.setAccountNum(accountNum);
		
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
		Double infoAmount=obj.getDouble("infoAmount");
		Double amount=obj.getDouble("amount");
		String brokerNumber=obj.getString("brokerNumber");
		String bizDate=obj.getString("bizDate");
		
		JSONObject rs = new JSONObject();
		
		if(StringUtils.isEmpty(number)||StringUtils.isEmpty(name)||infoAmount==null||amount==null||StringUtils.isEmpty(brokerNumber)){
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
				SellProjectCollection projectCol=iproject.getSellProjectCollection("select * from where number='"+project+"'");
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
			
			iintentionCustomer.submit(info);
			
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
		
		if(StringUtils.isEmpty(number)||StringUtils.isEmpty(name)||StringUtils.isEmpty(project)||StringUtils.isEmpty(saleMan)||StringUtils.isEmpty(phone)){
			rs.put("state", "0");
			rs.put("msg", "接口必要字段不能为空！");
			return rs.toString();
		}
		
		IFDCCustomer ifdcCustomer=FDCCustomerFactory.getLocalInstance(ctx);
		ISellProject iproject=SellProjectFactory.getLocalInstance(ctx);
		ICity icity=CityFactory.getLocalInstance(ctx);
		IUser iuser=UserFactory.getLocalInstance(ctx);
		if(ifdcCustomer.exists("select id from where number='"+number+"'")){
			rs.put("state", "0");
			rs.put("msg", "编码重复！");
			return rs.toString();
		}else{
			FDCCustomerInfo info=new FDCCustomerInfo();
			info.setCustomerType(CustomerTypeEnum.EnterpriceCustomer);
			SellProjectCollection projectCol=iproject.getSellProjectCollection("select * from where number='"+project+"'");
			if(projectCol.size()==0){
				rs.put("state", "0");
				rs.put("msg", "意向楼盘/产业园区在EAS不存在！");
				return rs.toString();
			}
			info.setProject(projectCol.get(0));
			
			if(ifdcCustomer.exists("select id from where project.id='"+info.getProject().getId().toString()+"' and phone='"+phone+"'")){
				rs.put("state", "0");
				rs.put("msg", "公司电话重复！");
				return rs.toString();
			}
			
			UserCollection userCol=iuser.getUserCollection("select * from where number='"+saleMan+"'");
			if(userCol.size()==0){
				rs.put("state", "0");
				rs.put("msg", "业务顾问在EAS不存在！");
				return rs.toString();
			}
			info.setSalesman(userCol.get(0));
			
			if(!StringUtils.isEmpty(city)){
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("name",city));
				view.setFilter(filter);
				CityCollection cityCol=icity.getCityCollection(view);
				if(userCol.size()==0){
					rs.put("state", "0");
					rs.put("msg", "城市在EAS不存在！");
					return rs.toString();
				}
				info.setCity(cityCol.get(0));
			}
			info.setNumber(number);
			info.setName(name);
			info.setPhone(phone);
			
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
		
		if(StringUtils.isEmpty(number)||StringUtils.isEmpty(trackDate)||StringUtils.isEmpty(fdcCustomerNum)||StringUtils.isEmpty(receptionMode)){
			rs.put("state", "0");
			rs.put("msg", "接口必要字段不能为空！");
			return rs.toString();
		}
		
		ITrackRecord itrackRecord=TrackRecordFactory.getLocalInstance(ctx);
		IFDCCustomer ifdcCustomer=FDCCustomerFactory.getLocalInstance(ctx);
		IReceptionType ireceptionType=ReceptionTypeFactory.getLocalInstance(ctx);
		if(itrackRecord.exists("select id from where number='"+number+"'")){
			rs.put("state", "0");
			rs.put("msg", "编码重复！");
			return rs.toString();
		}else{
			TrackRecordInfo info=new TrackRecordInfo();
			FDCCustomerCollection customerCol=ifdcCustomer.getFDCCustomerCollection("select * from where number='"+fdcCustomerNum+"'");
			if(customerCol.size()==0){
				rs.put("state", "0");
				rs.put("msg", "客户台账编码在EAS不存在！");
				return rs.toString();
			}
			info.setHead(customerCol.get(0));
			info.setSaleMan(customerCol.get(0).getSalesman());
			info.setSellProject(customerCol.get(0).getProject());
			
			ReceptionTypeCollection receptionTypeCol=ireceptionType.getReceptionTypeCollection("select * from where name='"+receptionMode+"'");
			if(receptionTypeCol.size()==0){
				rs.put("state", "0");
				rs.put("msg", "接待方式在EAS不存在！");
				return rs.toString();
			}
			info.setReceptionType(receptionTypeCol.get(0));
			
			info.setNumber(number);
			info.setDescription(remark);
			info.setEventDate(FDCDateHelper.stringToDate(trackDate));
			
			itrackRecord.submit(info);
			rs.put("state", "1");
			rs.put("msg", "同步成功！");
		}
		return rs.toString();
	}
}