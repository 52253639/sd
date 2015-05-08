package com.kingdee.eas.fdc.schedule.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelCollection;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryCollection;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryFactory;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryInfo;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelFactory;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;

public class ContractAndTaskRelControllerBean extends AbstractContractAndTaskRelControllerBean{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ContractAndTaskRelControllerBean");

	protected Map _getTaskData(Context ctx, String contractId)
			throws BOSException, EASBizException {
		Set wbsIds = new HashSet();
		Map map = new HashMap();
		Map conTaskmap = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		filter.getFilterItems().add(new FilterItemInfo("contract.id",contractId,CompareType.EQUALS));
		sic.add("entrys.id");
		sic.add("entrys.description");
		sic.add("name");
		sic.add("number");
		sic.add("state");
		sic.add("id");
		sic.add("isEnabled");
		sic.add("entrys.wbs.id");
		sic.add("entrys.wbs.name");
		sic.add("entrys.wbs.number");
		sic.add("entrys.wbs.longNumber");
		view.setFilter(filter);
		view.setSelector(sic);
		ContractAndTaskRelCollection infoCol =  
			ContractAndTaskRelFactory.getLocalInstance(ctx).getContractAndTaskRelCollection(view);
		if(infoCol != null && infoCol.size()>0){
			ContractAndTaskRelInfo info = infoCol.get(0);
			conTaskmap.put("conAndTaskRel", info);
			map.put("conTask", conTaskmap);
			Map taskOtherDataMap = new HashMap();
			ContractAndTaskRelEntryCollection infoEntrys = info.getEntrys();
			for(int i=0;i<infoEntrys.size();i++){
				wbsIds.add(infoEntrys.get(i).getWbs().getId().toString());
			}
			taskOtherDataMap = ContractAndTaskRelEntryFactory.getLocalInstance(ctx).getTaskOtherData(wbsIds);
			map.put("otherDataMap", taskOtherDataMap);
		}else{
			conTaskmap.put("conAndTaskRel", null);
		}
		return map;
	}


	protected void _updateIsEnabled(Context ctx, String conAndTaskId,boolean isEnabled) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_ContractAndTaskRel set FIsEnabled=? where FID=?");
		builder.addParam(Boolean.valueOf(isEnabled));
		builder.addParam(conAndTaskId);
		builder.executeUpdate();
	}
	
	
//	暂时处理，以后优化
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		ContractAndTaskRelInfo info = (ContractAndTaskRelInfo) model;
//		if(info.getId() != null && _exists(ctx, new ObjectUuidPK(info.getId()))){
//			SelectorItemCollection sic = new SelectorItemCollection();
//			sic.add("name");
//			sic.add("number");
//			_updatePartial(ctx, info, sic);
//			if(info.getEntrys().size() > 0){
//				ContractAndTaskRelEntryCollection col = info.getEntrys();
//				for(int i=0;i<col.size();i++){
//					ContractAndTaskRelEntryInfo entryInfo = col.get(i);
////					FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
////					if(entryInfo.getId() != null){
////						builder.appendSql("update T_SCH_Contractandtaskrelentry set Ftaskid=? ,FDescription=? where fid=? ");
////						builder.addParam(entryInfo.getTask().getId().toString());
////						builder.addParam(entryInfo.getDescription());
////						builder.addParam(entryInfo.getId().toString());
////						builder.executeUpdate();
////					}else{
////						String newId = BOSUuid.create(entryInfo.getBOSType()).toString();
////						builder.appendSql("insert into T_SCH_Contractandtaskrelentry set(FID,FTaskid,Fdescription,FparentID,FS) Ftaskid=? ,FDescription=? where fid=? ");
////					}
//					FilterInfo filter = new FilterInfo();
//					if(entryInfo.getId() != null){
//						filter.getFilterItems().add(new FilterItemInfo("id",entryInfo.getId().toString(),CompareType.EQUALS));
//					}
//					if(ContractAndTaskRelEntryFactory.getLocalInstance(ctx).exists(filter)){
//						SelectorItemCollection sic2 = new SelectorItemCollection();
//						sic.add("task");
//						sic.add("description");
//						ContractAndTaskRelEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo, sic2);
//					}else{
//						ContractAndTaskRelEntryFactory.getLocalInstance(ctx).addnew(entryInfo);
//					}
//				}
//			}
//			return new ObjectUuidPK(info.getId());
//		}else{
//		}
		return super._save(ctx, info);
	}
	protected void trimName(FDCBillInfo fDCBillInfo) {
		if(fDCBillInfo.getName() != null) {
			fDCBillInfo.setName(fDCBillInfo.getName().trim());
		}
	}
	public void checkNumberDup(Context ctx,IObjectValue model) throws BOSException, EASBizException {
		ContractAndTaskRelInfo info = (ContractAndTaskRelInfo) model;
		ContractBillInfo conInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(info.getContract().getId()));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contract.curProject.id",conInfo.getCurProject().getId().toString(),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("number",info.getNumber(),CompareType.EQUALS));
		if(info.getId() != null && info.getId().toString().length() >0){
			filter.getFilterItems().add(new FilterItemInfo("id",info.getId().toString(),CompareType.NOTEQUALS));
		}
		if(_exists(ctx, filter)){
			if (_exists(ctx, filter)) {
				throw new ContractException(ContractException.NUMBER_DUP);
			}
		}
	}
	
	protected void checkBill(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		checkNumberDup(ctx, model);
		checkNameDup(ctx, model);
	}
	
	protected void checkNameDup(Context ctx, IObjectValue billInfo)	throws BOSException, EASBizException {
		ContractAndTaskRelInfo info = (ContractAndTaskRelInfo) billInfo;
		ContractBillInfo conInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(info.getContract().getId()));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contract.curProject.id",conInfo.getCurProject().getId().toString(),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("name",info.getName(),CompareType.EQUALS));
		if(info.getId() != null && info.getId().toString().length() >0){
			filter.getFilterItems().add(new FilterItemInfo("id",info.getId().toString(),CompareType.NOTEQUALS));
		}
		if(_exists(ctx, filter)){
			throw new ContractException(ContractException.NAME_DUP);
		}
	}
}