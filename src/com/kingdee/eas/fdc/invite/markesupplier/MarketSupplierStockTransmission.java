package com.kingdee.eas.fdc.invite.markesupplier;


import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAreaCollection;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAreaFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAttachListCollection;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAttachListFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAttachListInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierPersonCollection;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierPersonFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierPersonInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.app.ContextUtil;

public class MarketSupplierStockTransmission extends AbstractDataTransmission{
	private static final Logger logger=CoreUIObject.getLogger(MarketSupplierStockTransmission.class);
	
	private String arce = null;
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try
        {
            return MarketSupplierStockFactory.getLocalInstance(ctx);
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
	     if(hsData.get("FNumber") == null || hsData.get("FNumber") != null && hsData.get("FNumber").toString().trim().length() == 0)
	         throw new TaskExternalException("供应商编码不能为空！");
	     if(hsData.get("FName_l2") == null || hsData.get("FName_l2") != null && hsData.get("FName_l2").toString().trim().length() == 0)
	         throw new TaskExternalException("供应商名称不能为空！");
	     if(hsData.get("FInviteType_name_l2") == null || hsData.get("FInviteType_name_l2") != null && hsData.get("FInviteType_name_l2").toString().trim().length() == 0)
	         throw new TaskExternalException("采购类别不能为空！");
	     if(hsData.get("FSupplierSplAreaEntry$fdcSplArea_name_l2") == null || hsData.get("FSupplierSplAreaEntry$fdcSplArea_name_l2") != null && hsData.get("FSupplierSplAreaEntry$fdcSplArea_name_l2").toString().trim().length() == 0)
	    	 throw new TaskExternalException("服务区域不能为空！");
	     else{
	    	 arce = hsData.get("FSupplierSplAreaEntry$fdcSplArea_name_l2").toString().trim();
	    	 hsData.remove("FSupplierSplAreaEntry$fdcSplArea_name_l2");
	    	 String splarce[] = arce.split(";");
	    	 for (int i = 0; i < splarce.length; i++) {
	    		 if(splarce[i].length()==0){continue;}
	    		 MarketSplAreaCollection coll =  MarketSplAreaFactory.getLocalInstance(ctx).getMarketSplAreaCollection("select id where name='"+splarce[i]+"'");
	    		 if(coll.size()<1){
	    			 throw new TaskExternalException("服务区域数据错误可能是:\n 1、如果需要同时录入多条则用分号隔开 例子：华北区域;华南区域。" +
	    			 "\n2、数据库中不存在字段fdcSplArea_name_l2,值为 "+splarce[i]+" 对应的数据对象。      ！");
	    		 }
			 }
	     }
	}
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		MarketSupplierStockInfo info = (MarketSupplierStockInfo)coreBaseInfo;
		info.setState(SupplierStateEnum.SAVE);
		try {
			info.setCreateTime(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		info.setHasCreatedSupplierBill(false);
		info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		info.setAdminCU(ContextUtil.getCurrentCtrlUnit(ctx));
		String splarce[] = arce.split(";");
		for (int i = 0; i < splarce.length; i++) {
			if(splarce[i].length()==0){continue;}
			MarketSupplierStockSupplierSplAreaEntryInfo Areainfo = new MarketSupplierStockSupplierSplAreaEntryInfo();
			try {
				Areainfo.setFdcSplArea(MarketSplAreaFactory.getLocalInstance(ctx).getMarketSplAreaInfo("select id where name='"+splarce[i]+"'"));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			info.getSupplierSplAreaEntry().add(Areainfo);
		}
		info.setSplArea(arce);
		MarketSupplierStockLinkPersonCollection delLinkCol=new MarketSupplierStockLinkPersonCollection();
		for(int i=0;i<info.getLinkPerson().size();i++){
			MarketSupplierStockLinkPersonInfo entry=info.getLinkPerson().get(i);
			if(entry.getId()==null&&(entry.getPersonName()==null||"".equals(entry.getPersonName().trim()))&&(entry.getPhone()==null||"".equals(entry.getPhone().trim()))){
				delLinkCol.add(entry);
				continue;
			}
		}
		for(int i=0;i<delLinkCol.size();i++){
			info.getLinkPerson().remove(delLinkCol.get(i));
		}
		for(int i=0;i<info.getLinkPerson().size();i++){
			MarketSupplierStockLinkPersonInfo entry=info.getLinkPerson().get(i);
			if(i==0){
				entry.setIsDefault(true);
			}else{
				entry.setIsDefault(false);
			}
		}
		try {
			if(info.getSupplierAttachListEntry().size()==0&&info.getSupplierFileType()!=null){
				SorterItemCollection sort=new SorterItemCollection();
				sort.add(new SorterItemInfo("number"));
				EntityViewInfo view=new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		        filter.getFilterItems().add(new FilterItemInfo("supplierFileType.id" , info.getSupplierFileType().getId().toString()));
		        filter.getFilterItems().add(new FilterItemInfo("isEnabled" , Boolean.TRUE));
		        view.setFilter(filter);
		        view.setSorter(sort);
		        MarketSupplierPersonCollection col=MarketSupplierPersonFactory.getLocalInstance(ctx).getMarketSupplierPersonCollection(view);
		        for(int i=0;i<col.size();i++){
		        	MarketSupplierPersonInfo sp=col.get(i);
		        	MarketSupplierStockSupplierPersonEntryInfo entry=new MarketSupplierStockSupplierPersonEntryInfo();
		        	entry.setNumber(sp.getNumber());
		        	entry.setName(sp.getName());
		        	info.getSupplierPersonEntry().add(entry);
		         }
		        MarketSupplierAttachListCollection alCol= MarketSupplierAttachListFactory.getLocalInstance(ctx).getMarketSupplierAttachListCollection(view);
		         for(int i=0;i<alCol.size();i++){
		        	 MarketSupplierAttachListInfo at=alCol.get(i);
		        	 MarketSupplierStockSupplierAttachListEntryInfo entry=new MarketSupplierStockSupplierAttachListEntryInfo();
		        	 entry.setNumber(at.getNumber());
		        	 entry.setName(at.getName());
		        	 info.getSupplierAttachListEntry().add(entry);
		         }
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		super.submit(info, ctx);
	}
}
