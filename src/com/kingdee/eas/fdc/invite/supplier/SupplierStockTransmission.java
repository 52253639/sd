package com.kingdee.eas.fdc.invite.supplier;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.app.ContextUtil;

public class SupplierStockTransmission extends AbstractDataTransmission{
	private static final Logger logger=CoreUIObject.getLogger(com.kingdee.eas.fdc.invite.supplier.SupplierStockTransmission.class);
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try
        {
            return SupplierStockFactory.getLocalInstance(ctx);
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
	     
	}
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		SupplierStockInfo info = (SupplierStockInfo)coreBaseInfo;
		info.setState(SupplierStateEnum.SAVE);
		try {
			info.setCreateTime(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		info.setHasCreatedSupplierBill(false);
		info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		info.setAdminCU(ContextUtil.getCurrentCtrlUnit(ctx));
		
		SupplierSplAreaEntryCollection delSplCol=new SupplierSplAreaEntryCollection();
		for(int i=0;i<info.getSupplierSplAreaEntry().size();i++){
			SupplierSplAreaEntryInfo entry=info.getSupplierSplAreaEntry().get(i);
			if(entry.getId()==null&&entry.getFdcSplArea()==null){
				delSplCol.add(entry);
				continue;
			}
		}
		for(int i=0;i<delSplCol.size();i++){
			info.getSupplierSplAreaEntry().remove(delSplCol.get(i));
		}
		SupplierLinkPersonCollection delLinkCol=new SupplierLinkPersonCollection();
		for(int i=0;i<info.getLinkPerson().size();i++){
			SupplierLinkPersonInfo entry=info.getLinkPerson().get(i);
			if(entry.getId()==null&&(entry.getPersonName()==null||"".equals(entry.getPersonName().trim()))&&(entry.getPhone()==null||"".equals(entry.getPhone().trim()))){
				delLinkCol.add(entry);
				continue;
			}
		}
		for(int i=0;i<delLinkCol.size();i++){
			info.getLinkPerson().remove(delLinkCol.get(i));
		}
		for(int i=0;i<info.getLinkPerson().size();i++){
			SupplierLinkPersonInfo entry=info.getLinkPerson().get(i);
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
		        SupplierPersonCollection col=SupplierPersonFactory.getLocalInstance(ctx).getSupplierPersonCollection(view);
		        for(int i=0;i<col.size();i++){
		        	SupplierPersonInfo sp=col.get(i);
		        	SupplierPersonEntryInfo entry=new SupplierPersonEntryInfo();
		        	entry.setNumber(sp.getNumber());
		        	entry.setName(sp.getName());
		        	info.getSupplierPersonEntry().add(entry);
		         }
		         SupplierAttachListCollection alCol= SupplierAttachListFactory.getLocalInstance(ctx).getSupplierAttachListCollection(view);
		         for(int i=0;i<alCol.size();i++){
		        	 SupplierAttachListInfo at=alCol.get(i);
		        	 SupplierAttachListEntryInfo entry=new SupplierAttachListEntryInfo();
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
