package com.kingdee.eas.fdc.sellhouse.app;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.market.ChannelTypeCollection;
import com.kingdee.eas.fdc.market.ChannelTypeFactory;
import com.kingdee.eas.fdc.market.ChannelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.app.ContextUtil;

public class CommerceChanceTransmission extends AbstractDataTransmission{
	private static final Logger logger=CoreUIObject.getLogger(com.kingdee.eas.fdc.sellhouse.app.CommerceChanceTransmission.class);
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try
        {
            return CommerceChanceFactory.getLocalInstance(ctx);
        }
        catch(BOSException e)
        {
            logger.debug(e.getMessage());
            throw new TaskExternalException(e.getMessage(), e);
        }
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		try
        {
            check(hsData, ctx);
        }
        catch(EASBizException e)
        {
            logger.debug(e.getMessage());
            throw new TaskExternalException(e.getMessage(), e);
        }
        catch(BOSException e)
        {
            logger.debug(e.getMessage());
            throw new TaskExternalException(e.getMessage(), e);
        }
        return null;
	}
	private void check(Hashtable hsData, Context ctx) throws TaskExternalException, EASBizException, BOSException{
	     if(hsData.get("FSellProject_name_l2") == null || hsData.get("FSellProject_name_l2") != null && hsData.get("FSellProject_name_l2").toString().trim().length() == 0)
	         throw new TaskExternalException("销售项目不能为空！");
	     ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	     if(!iCodingRuleManager.isExist(new CommerceChanceInfo(), ContextUtil.getCurrentOrgUnit(ctx).getId().toString())){
	    	 if(hsData.get("FNumber") == null || hsData.get("FNumber") != null && hsData.get("FNumber").toString().trim().length() == 0)
		         throw new TaskExternalException("单据编号不能为空！");
	     }
	     if(hsData.get("FCustomer_name_l2") == null || hsData.get("FCustomer_name_l2") != null && hsData.get("FCustomer_name_l2").toString().trim().length() == 0)
	         throw new TaskExternalException("客户名称不能为空！");
	}
	protected  EntityViewInfo  getPermitCustomerView(Context ctx,SellProjectInfo sellPorjct,UserInfo userInfo) throws EASBizException, BOSException{
		SaleOrgUnitInfo orgUnitInfo = ContextUtil.getCurrentSaleUnit(ctx);
		
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();		
		if(sellPorjct==null) {
			String permitSaleIdStr = MarketingUnitFactory.getLocalInstance(ctx).getPermitSaleManIdSql(userInfo,sellPorjct);
			String permitProIdStr = MarketingUnitFactory.getLocalInstance(ctx).getPermitSellProjectIdSql(userInfo);
			filter.getFilterItems().add(new FilterItemInfo("createUnit.id",orgUnitInfo.getId().toString()));
			
			filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id",permitSaleIdStr,CompareType.INNER));			
			//共享给营销顾问的客户
			String saleSaleIdStr = "select FCustomerID from T_SHE_ShareProperty where FUserID in ("+permitSaleIdStr+")";
			filter.getFilterItems().add(new FilterItemInfo("id",saleSaleIdStr,CompareType.INNER));			
			//共享给项目的客户
			String sellProIdStr = "select FCustomerID from T_SHE_ShareSellProject where FSellProjectID in ("+permitProIdStr+") ";
			filter.getFilterItems().add(new FilterItemInfo("id",sellProIdStr,CompareType.INNER));
			
			filter.setMaskString("#0 and (#1 or #2 or #3)");
		}else{
			sellPorjct=SHEManageHelper.getParentSellProject(ctx,sellPorjct);
			
			String permitSaleIdStr = MarketingUnitFactory.getLocalInstance(ctx).getPermitSaleManIdSql(userInfo,sellPorjct);
			//permitSaleIdStr += " and FSellProjectID = '"+sellPorjct.getId()+"' ";
			
			filter.getFilterItems().add(new FilterItemInfo("createUnit.id",orgUnitInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellPorjct.getId().toString()));
			
			filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id",permitSaleIdStr,CompareType.INNER));			
			//共享给营销顾问的客户
			String saleSaleIdStr = "select FCustomerID from T_SHE_ShareProperty where FUserID in ("+permitSaleIdStr+")";
			filter.getFilterItems().add(new FilterItemInfo("id",saleSaleIdStr,CompareType.INNER));			
			//共享给项目的客户
			String sellProIdStr = "select FCustomerID from T_SHE_ShareSellProject where FSellProjectID = '"+sellPorjct.getId()+"' ";
			filter.getFilterItems().add(new FilterItemInfo("id",sellProIdStr,CompareType.INNER));
			
			filter.setMaskString("#0 and ((#1 and (#2 or #3)) or #4)  ");			
		}
		viewInfo.setFilter(filter);
		return viewInfo;
	}
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		CommerceChanceInfo info=(CommerceChanceInfo)coreBaseInfo;
		info.setSysType(MoneySysTypeEnum.SalehouseSys);
		info.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
		info.setOrgUnit(ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo());
		
		try {
			SHECustomerInfo sheCI = info.getCustomer();
			if(sheCI!=null){
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("phone");
				sels.add("name");
				sheCI=SHECustomerFactory.getLocalInstance(ctx).getSHECustomerInfo(new ObjectUuidPK(sheCI.getId()),sels);
				EntityViewInfo view =getPermitCustomerView(ctx,info.getSellProject(),ContextUtil.getCurrentUserInfo(ctx));
				EntityViewInfo newView=(EntityViewInfo)view.clone();
				FilterInfo filter = new FilterInfo();		
				filter.getFilterItems().add(new FilterItemInfo("name",sheCI.getName()));
				filter.getFilterItems().add(new FilterItemInfo("phone",sheCI.getPhone()));
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",info.getSellProject().getId()));
				newView.getFilter().mergeFilter(filter, "and");
				SHECustomerCollection col = SHECustomerFactory.getLocalInstance(ctx).getSHECustomerCollection(newView);
				
				if(col.size()>1||col.size()==0){
					info.setCustomer(null);
				}else{
					info.setCustomer(col.get(0));
				}
			}
			ChannelTypeInfo classify=info.getClassify();
			if(classify!=null){
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("name");
				classify=ChannelTypeFactory.getLocalInstance(ctx).getChannelTypeInfo(new ObjectUuidPK(classify.getId()),sels);
				EntityViewInfo view =new EntityViewInfo();
				FilterInfo filter = new FilterInfo();		
				filter.getFilterItems().add(new FilterItemInfo("name",classify.getName()));
				filter.getFilterItems().add(new FilterItemInfo("CU.id",ContextUtil.getCurrentCtrlUnit(ctx).getId().toString()));
				view.setFilter(filter);
				ChannelTypeCollection col = ChannelTypeFactory.getLocalInstance(ctx).getChannelTypeCollection(view);
				
				if(col.size()>1||col.size()==0){
					info.setClassify(null);
				}else{
					info.setClassify(col.get(0));
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
			if(iCodingRuleManager.isExist(info, ContextUtil.getCurrentOrgUnit(ctx).getId().toString())){
				String retNumber = iCodingRuleManager.getNumber(info, ContextUtil.getCurrentOrgUnit(ctx).getId().toString());
				info.setNumber(retNumber);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		super.submit(info,ctx);
	}
}
