package com.kingdee.eas.fdc.aimcost.app;

import org.apache.log4j.Logger;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.query.util.CompareType;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.aimcost.CostIndexFactory;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.util.NumericExceptionSubItem;

public class CostIndexControllerBean extends AbstractCostIndexControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.CostIndexControllerBean");
    
    protected boolean isUseName() {
		return false;
	}
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("contract.id");
		sel.add("version");
		sel.add("inviteType");
		CostIndexInfo info =this.getCostIndexInfo(ctx, new ObjectUuidPK(billId), sel);
		String contractId = info.getContract().getId().toString();
		String inviteTypeId = info.getInviteType().getId().toString();
		int version = info.getVersion();
		version = version-1;
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_AIM_CostIndex set FIsLatest = 1 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update T_AIM_CostIndex set FIsLatest = 0 where fcontractId = '");
		sql.append(contractId).append("' ");
		sql.append("and FVersion = ").append(version);
		sql.append("and FInviteTypeid = '").append(inviteTypeId).append("' ");;
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update t_con_contractBill set FisHasCostIndex = 1 where fid = '");
		sql.append(contractId).append("' ");
		fdcSB.addBatch(sql.toString());
		
		fdcSB.executeBatch();
    }		
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("contract.id");
		sel.add("version");
		sel.add("isLatest");
		sel.add("inviteType");
		CostIndexInfo info =this.getCostIndexInfo(ctx, new ObjectUuidPK(billId), sel);
		if(!info.isIsLatest()){
			throw new EASBizException(new NumericExceptionSubItem("100","非最新版本不能反审批！"));
		}
		
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contract.id",info.getContract().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(CostIndexFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","单据已修订，不能进行反审批操作！"));
		}
		
		String contractId = info.getContract().getId().toString();
		String inviteTypeId = info.getInviteType().getId().toString();
		int version = info.getVersion();
		version = version-1;
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_AIM_CostIndex set FIsLatest = 0 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update T_AIM_CostIndex set FIsLatest = 1 where FContractId = '");
		sql.append(contractId).append("' ");
		sql.append("and FVersion = ").append(version);
		sql.append("and FInviteTypeid = '").append(inviteTypeId).append("' ");;
		fdcSB.addBatch(sql.toString());
		
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contract.id",info.getContract().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		if(!CostIndexFactory.getLocalInstance(ctx).exists(filter)){
			sql.setLength(0);
			sql.append("update t_con_contractBill set FisHasCostIndex = 0 where fid = '");
			sql.append(contractId).append("' ");
			fdcSB.addBatch(sql.toString());
		}
		fdcSB.executeBatch();
	}
    
}