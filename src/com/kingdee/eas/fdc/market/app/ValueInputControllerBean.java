package com.kingdee.eas.fdc.market.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.fdc.market.DecorateEnum;
import com.kingdee.eas.fdc.market.ValueInputCollection;
import com.kingdee.eas.fdc.market.ValueInputDYEntryInfo;
import com.kingdee.eas.fdc.market.ValueInputFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.market.ValueInputInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectFactory;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ValueInputControllerBean extends AbstractValueInputControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.ValueInputControllerBean");
    protected boolean isUseName() {
		return false;
	}
	protected boolean isUseNumber() {
		return false;
	}
	protected void setDYEntry(Context ctx,ValueInputInfo info) throws BOSException, SQLException{
		info.getDyEntry().clear();
		FDCSQLBuilder _builder=new FDCSQLBuilder(ctx);
		_builder.appendSql(" select pb.fname project,entry.fproject operationPhases,entry.fbatch batch,entry.fproductType productType,");
		_builder.appendSql(" isnull(sum(saccount),0)+isnull(sum(daccount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end),0) account,round(isnull(sum(sarea),0)+isnull(sum(darea),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end),0),2) area,round((case fcalculateType when '10' then (case when isnull(sum(sarea),0)+isnull(sum(darea),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end),0)=0 then 0 else (isnull(sum(samount),0)+isnull(sum(damount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else entry.famount end),0))/(isnull(sum(sarea),0)+isnull(sum(darea),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end),0)) end) else (case when (isnull(sum(saccount),0)+isnull(sum(daccount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end),0))=0 then 0 else (isnull(sum(samount),0)+isnull(sum(damount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else entry.famount end),0))/(isnull(sum(saccount),0)+isnull(sum(daccount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end),0)) end) end ),0) price,round((isnull(sum(samount),0)+isnull(sum(damount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else entry.famount end),0))/10000,0) amount,");
		_builder.appendSql(" sum(saccount) saccount,round(sum(sarea),2) sarea,round((case when sum(sarea)=0 then 0 else sum(samount)/sum(sarea) end),0) sprice,round(sum(samount)/10000,0) samount,");
		_builder.appendSql(" sum(daccount) daccount,round(sum(darea),2) darea,round((case when sum(darea)=0 then 0 else sum(damount)/sum(darea) end),0) dprice,round(sum(damount)/10000,0) damount,");
		_builder.appendSql(" sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end) paccount,round(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end),2) parea,round((case fcalculateType when '10' then (case when sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end)=0 then 0 else sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else entry.famount end)/sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end) end) else (case when sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end)=0 then 0 else sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else entry.famount end)/sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end) end) end ),0) pprice,round(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else entry.famount end)/10000,0) pamount");
		_builder.appendSql(" from T_MAR_ValueInputEntry entry left join T_FDC_ProductType pt on pt.fid=entry.fproductTypeId left join T_BD_OperationPhases op on op.fid=entry.fprojectid left join T_MAR_ValueInput vi on vi.fid=entry.fheadId left join T_FDC_ProjectBase pb on pb.fid=vi.FProjectId");
		_builder.appendSql(" left join (select banEntry.FBanBasedataEntryId buildId,room.fproductTypeId productTypeId,count(*) saccount,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) sarea,sum(sign.fcontractTotalAmount) samount");
		_builder.appendSql(" from t_she_signManage sign left join t_she_room room on room.fid=sign.froomId left join T_SHE_BanBasedataEntryList banEntry on banEntry.fheadId=room.fbuildingId where sign.fbizState in('SignApple','SignAudit') and banEntry.FBanBasedataEntryId is not null group by banEntry.FBanBasedataEntryId,room.fproductTypeId) st on st.buildId=entry.fbuildId and st.productTypeId=entry.fproductTypeId");
		_builder.appendSql(" left join (select banEntry.FBanBasedataEntryId buildId,room.fproductTypeId productTypeId,count(*) daccount,sum(case room.FSellAreaType when 'planning' then room.fPlanBuildingArea when 'presell' then room.FBuildingArea else room.fActualBuildingArea end) darea,sum(room.fbaseStandardPrice) damount");
		_builder.appendSql(" from t_she_room room left join T_SHE_BanBasedataEntryList banEntry on banEntry.fheadid=room.fbuildingid where room.FSellState!='Sign' and banEntry.FBanBasedataEntryId is not null group by banEntry.FBanBasedataEntryId,room.fproductTypeId) dt on dt.buildId=entry.fbuildId and dt.productTypeId=entry.fproductTypeId");
		_builder.appendSql(" where entry.fheadid='"+info.getId().toString()+"' group by pb.fname,entry.fproject,entry.fbatch,entry.fproductType,pb.fnumber,op.flongnumber,fcalculateType,pt.fnumber order by pb.fnumber,op.flongnumber,entry.fbatch,pt.fnumber");
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			ValueInputDYEntryInfo entry=new ValueInputDYEntryInfo();
			entry.setProject(rowSet.getString("project"));
			entry.setOperationPhases(rowSet.getString("operationPhases"));
			entry.setBatch(rowSet.getString("batch"));
			entry.setProductType(rowSet.getString("productType"));
//			entry.setDecorate(DecorateEnum.getEnum(rowSet.getString("decorate")));
			entry.setAccount(rowSet.getInt("account"));
			entry.setArea(rowSet.getBigDecimal("area"));
			entry.setPrice(rowSet.getBigDecimal("price"));
			entry.setAmount(rowSet.getBigDecimal("amount"));
			
			entry.setSaccount(rowSet.getInt("saccount"));
			entry.setSarea(rowSet.getBigDecimal("sarea"));
			entry.setSprice(rowSet.getBigDecimal("sprice"));
			entry.setSamount(rowSet.getBigDecimal("samount"));
			
			entry.setDaccount(rowSet.getInt("daccount"));
			entry.setDarea(rowSet.getBigDecimal("darea"));
			entry.setDprice(rowSet.getBigDecimal("dprice"));
			entry.setDamount(rowSet.getBigDecimal("damount"));
			
			entry.setPaccount(rowSet.getInt("paccount"));
			entry.setParea(rowSet.getBigDecimal("parea"));
			entry.setPprice(rowSet.getBigDecimal("pprice"));
			entry.setPamount(rowSet.getBigDecimal("pamount"));
			
			info.getDyEntry().add(entry);
		}
	}
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
		super._submit(ctx, pk, model);
		try {
			setDYEntry(ctx,(ValueInputInfo) model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		super._submit(ctx, pk, model);
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		super._submit(ctx, model);
		try {
			setDYEntry(ctx,(ValueInputInfo) model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return super._submit(ctx, model);
	}
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._save(ctx, pk, model);
		try {
			setDYEntry(ctx,(ValueInputInfo) model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		super._save(ctx, pk, model);
	}
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		super._save(ctx, model);
		try {
			setDYEntry(ctx,(ValueInputInfo) model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return super._save(ctx, model);
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._unAudit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("project.id");
		sel.add("version");
		sel.add("isLatest");
		sel.add("indexVersion");
		ValueInputInfo info =this.getValueInputInfo(ctx, new ObjectUuidPK(billId), sel);
		if(!info.isIsLatest()){
			throw new EASBizException(new NumericExceptionSubItem("100","非最新版本不能反审批！"));
		}
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",info.getProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("indexVersion",info.getIndexVersion().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(ValueInputFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","单据已修订，不能进行反审批操作！"));
		}

		String proId = info.getProject().getId().toString();
		int version = info.getVersion();
		version = version-1;
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_MAR_ValueInput set FIsLatest = 0 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update T_MAR_ValueInput set FIsLatest = 1 where fProjectid = '");
		sql.append(proId).append("'");
		sql.append("and FVersion = ").append(version);
		sql.append("and findexVersionId = '").append(info.getIndexVersion().getId().toString()).append("'");;
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("project.id");
		sel.add("version");
		sel.add("indexVersion");
		ValueInputInfo info =this.getValueInputInfo(ctx, new ObjectUuidPK(billId), sel);
		String proId = info.getProject().getId().toString();
		int version = info.getVersion();
		version = version-1;
		
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",info.getProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("indexVersion",info.getIndexVersion().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(ValueInputFactory.getLocalInstance(ctx).exists(filter)){
			return;
		}
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_MAR_ValueInput set FIsLatest = 1 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update T_MAR_ValueInput set FIsLatest = 0 where fProjectid = '");
		sql.append(proId).append("' ");	
		sql.append("and FVersion = ").append(version);
		sql.append("and findexVersionId = '").append(info.getIndexVersion().getId().toString()).append("'");
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
	}
}