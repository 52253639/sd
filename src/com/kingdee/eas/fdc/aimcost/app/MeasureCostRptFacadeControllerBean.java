package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MeasureStageCollection;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.util.FDCSplitAcctHelper;
import com.kingdee.jdbc.rowset.IRowSet;

public class MeasureCostRptFacadeControllerBean extends AbstractMeasureCostRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.MeasureCostRptFacadeControllerBean");
    protected RetValue _getMeasureCosts(Context ctx, ParamValue param)throws BOSException, EASBizException
    {
    	RetValue retValue = new RetValue();
		String prjId = param.getString("prjId");
		if(prjId==null){
			throw new NullPointerException("bad prjId!");
		}
		//测算阶段
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("id",MeasureStageInfo.ORIENTE));
//		filter.getFilterItems().add(new FilterItemInfo("id",MeasureStageInfo.DESIGN));
//		filter.getFilterItems().add(new FilterItemInfo("id",MeasureStageInfo.CONSTRUCT));
//		filter.setMaskString("#0 or #1 or #2");
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add("name");
		view.getSelector().add("number");
		view.getSelector().add("id");
		SorterItemInfo sort = new SorterItemInfo("number");
		sort.setSortType(SortType.ASCEND);
		view.getSorter().add(sort);//默认升序
		MeasureStageCollection stages = MeasureStageFactory.getLocalInstance(ctx).getMeasureStageCollection(view);
		Set stageIds = new HashSet();
		for(Iterator iter=stages.iterator();iter.hasNext();){
			MeasureStageInfo stage = (MeasureStageInfo)iter.next();
			stageIds.add(stage.getId().toString());
		}
		retValue.put("stages", stages);
		
		//各阶段最大版本
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select head4.fid from t_aim_measurecost head4 \n");
		builder.appendSql("inner join ( \n");
		builder.appendSql("	   select fmeasurestageid,fmainverno,max(fsubverno) fsubverno from t_aim_measurecost where FState='4AUDITTED'  and fid in ( \n");
		builder.appendSql("	      select head2.fid from t_aim_measurecost head2 \n");
		builder.appendSql("	      inner join ( \n");
		builder.appendSql("	            select fmeasurestageid,max(fmainverno) fmainverno from t_aim_measurecost \n");
		builder.appendSql("             where  fprojectid=? \n");
		builder.appendSql("             group by fmeasurestageid \n");
		builder.appendSql("       ) head1 on head1.fmeasurestageid=head2.fmeasurestageid and head1.fmainverno=head2.fmainverno \n");
		builder.appendSql("       where head2.FState='4AUDITTED'  and head2.fprojectid=? \n");
		builder.appendSql("	   ) and fprojectid=?  \n");
		builder.appendSql("	   group by fmeasurestageid,fmainverno \n");
		builder.appendSql("	) head3 on head4.fmeasurestageid=head3.fmeasurestageid and head4.fmainverno=head3.fmainverno and head4.fsubverno=head3.fsubverno \n");
		builder.appendSql("	where  FState='4AUDITTED' and head4.fprojectid=? \n");
		builder.addParam(prjId);
		builder.addParam(prjId);
		builder.addParam(prjId);
		builder.addParam(prjId);
		Set headIds = new HashSet();
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				headIds.add(rs.getString("FID"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		try {
			retValue.put("settleSplits", FDCSplitAcctHelper.getSettleSplits(ctx, prjId));
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		if(headIds.size()==0){
			return retValue;
		}
		//可售面积
		builder.clear();
		builder.appendSql("select head.FHeadID headId,measure.FMeasureStageID stageId,sum(entry.FSellArea) sellArea from T_AIM_PlanIndexEntry entry ");
		builder.appendSql("inner join T_AIM_PlanIndex head on head.FID=entry.FParentID ");
		builder.appendSql("inner join T_AIM_MeasureCost measure on measure.FID=head.FHeadID ");
		builder.appendSql("where ");
		builder.appendParam("head.FHeadID", headIds.toArray());
		builder.appendSql("group by head.FHeadID,measure.FMeasureStageID ");
		rs = builder.executeQuery();
		try {
			while(rs.next()){
//				String headId = rs.getString("headId");
				String stageId = rs.getString("stageId");
				BigDecimal sellArea = rs.getBigDecimal("sellArea");
				retValue.setBigDecimal(stageId, sellArea);
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		//项目收入测算合价
		builder.clear();
		builder.appendSql("select entry.FIncomeAccountID acctId,head.FMeasureStageID stageId,entry.FChangeReason reason,sum(entry.FAmount) amount from T_AIM_MeasureIncomeEntry entry \n");
		builder.appendSql("inner join T_AIM_MeasureIncome head on head.FID=entry.FHeadID \n");
		builder.appendSql("where \n");
		builder.appendParam("head.FSrcMeasureCostID", headIds.toArray());
		builder.appendSql("group by entry.FIncomeAccountID,head.FMeasureStageID,entry.FChangeReason ");
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				String acctId = rs.getString("acctId");
				String stageId = rs.getString("stageId");
				String reason = rs.getString("reason");
				BigDecimal amount = rs.getBigDecimal("amount");
				//多产品
				retValue.setBigDecimal(acctId+stageId, FDCHelper.add(retValue.get(acctId+stageId), amount));
				if(retValue.containsKey(acctId+stageId+"reason")){
					retValue.setString(acctId+stageId+"reason", retValue.getString(acctId+stageId+"reason")+"；"+reason);
				}else{
					retValue.setString(acctId+stageId+"reason", reason);
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		//目标成本测算合价
		builder.clear();
		builder.appendSql("select entry.FCostAccountID acctId,head.FMeasureStageID stageId,entry.FChangeReason reason,sum(entry.FAmount) amount from T_AIM_MeasureEntry entry \n");
		builder.appendSql("inner join T_AIM_MeasureCost head on head.FID=entry.FHeadID \n");
		builder.appendSql("where \n");
		builder.appendParam("head.FID", headIds.toArray());
		builder.appendSql("group by entry.FCostAccountID,head.FMeasureStageID,entry.FChangeReason ");
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				String acctId = rs.getString("acctId");
				String stageId = rs.getString("stageId");
				String reason = rs.getString("reason");
				BigDecimal amount = rs.getBigDecimal("amount");
				retValue.setBigDecimal(acctId+stageId, FDCHelper.add(retValue.get(acctId+stageId), amount));
				if(reason!=null){
					if(retValue.containsKey(acctId+stageId+"reason")){
						retValue.setString(acctId+stageId+"reason", retValue.getString(acctId+stageId+"reason")+"；"+reason);
					}else{
						retValue.setString(acctId+stageId+"reason", reason);
					}
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		/**
		 * db2回归字段类型一致，字段表前辍 by hpw
		 */
		builder.clear();
		builder.appendSql("select entry.FCostAccountID acctId,entry.FDesc descStr,entry.FProgram program from T_AIM_CostEntry entry \n");
		builder.appendSql("inner join T_AIM_AimCost head on head.FID=entry.FHeadID \n");
		builder.appendSql("inner join (select max(to_number(FVersionNumber)) FVersionNumber,FMeasureStageID from T_AIM_AimCost where FMeasureStageID=? and FOrgOrProId=? group by FMeasureStageID) head2 on head2.FVersionNumber=TO_NUMBER(head.FVersionNumber) and head2.FMeasureStageID=head.FMeasureStageID \n");
		builder.appendSql("where head.FMeasureStageID=? and head.FOrgOrProId=? ");
		builder.addParam(MeasureStageInfo.CONSTRUCT);
		builder.addParam(prjId);
		builder.addParam(MeasureStageInfo.CONSTRUCT);
		builder.addParam(prjId);
		rs = builder.executeQuery();
		String descKey="desc";
		String programKey="program";
		try {
			while(rs.next()){
				String acctId = rs.getString("acctId");
				String desc = rs.getString("descStr");
				String reason = rs.getString("program");
				if(desc!=null){
					if(retValue.containsKey(acctId+descKey)){
						retValue.setString(acctId+descKey, retValue.getString(acctId+descKey)+"；"+desc);
					}else{
						retValue.setString(acctId+descKey, desc);
					}
				}
				if(reason!=null){
					if(retValue.containsKey(acctId+programKey)){
						retValue.setString(acctId+programKey, retValue.getString(acctId+programKey)+"；"+reason);
					}else{
						retValue.setString(acctId+programKey, reason);
					}
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		return retValue;
    }
}