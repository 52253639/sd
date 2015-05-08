package com.kingdee.eas.fdc.schedule.app;

import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.util.FilterUtility;
import com.kingdee.eas.util.app.ContextUtil;

public class RESchTemplateControllerBean extends
		AbstractRESchTemplateControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.schedule.app.RESchTemplateControllerBean");

	protected void _importTasks(Context ctx, String templateID,
			IObjectCollection tasks) throws BOSException, EASBizException {

	}

	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		checkNumberDup(ctx, model);
		checkNameDup(ctx, model);
	}
	
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RESchTemplateInfo info = (RESchTemplateInfo) model;
		
		info.setState(FDCBillStateEnum.SAVED);
		return super._save(ctx, info);
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RESchTemplateInfo info = (RESchTemplateInfo) model;
		info.setState(FDCBillStateEnum.SUBMITTED);
		return super._submit(ctx, model);
	}
	
	protected IObjectValue _getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException {
		SorterItemCollection sorters = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("entry.seq");
		si.setSortType(SortType.ASCEND);
		sorters.add(si);
		return super._getValue(ctx, pk, selector, sorters);
	}
	
	protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("isLeaf");
		sic.add("longNumber");
		sic.add("state");
		sic.add("templateType");
		sic.add("entry.id");
		sic.add("entry.name");
		sic.add("entry.number");
		sic.add("entry.longNumber");
		sic.add("entry.referenceday");
		sic.add("entry.isLeaf");
		sic.add("entry.level");
		sic.add("entry.srcGroupNode");
		sic.add("entry.parent.id");
		sic.add("entry.parent.name");
		sic.add("entry.parent.number");
		sic.add("entry.parent.level");
		sic.add("entry.parent.isLeaf");
		sic.add("entry.standardTask.id");
		sic.add("entry.standardTask.name");
		sic.add("entry.standardTask.number");

		sic.add("entry.achievementType.id");
		sic.add("entry.achievementType.name");
		sic.add("entry.achievementType.number");

		sic.add("entry.businessType.id");
		sic.add("entry.businessType.bizType.id");
		sic.add("entry.businessType.bizType.number");
		sic.add("entry.businessType.bizType.name");

		sic.add("entry.predecessors.id");
		sic.add("entry.predecessors.predecessorTask.id");
		sic.add("entry.predecessors.predecessorTask.name");
		sic.add("entry.predecessors.predecessorTask.number");
		sic.add("entry.predecessors.differenceDay");
		sic.add("entry.predecessors.predecessorType");

		SorterItemCollection sorters = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("entry.seq");
		si.setSortType(SortType.ASCEND);
		sorters.add(si);

		return super._getValue(ctx, pk, sic, sorters);
	}
	
	/**
	 * 编码重复
	 * 
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		TreeBaseInfo treeModel = (TreeBaseInfo) model;

		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = null;
		// 父节点为空时检查根对象编码是否重复。
		if (treeModel.innerGetParent() == null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number,
					treeModel.getNumber(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			filter.getFilterItems().add(
					new FilterItemInfo(IFWEntityStruct.tree_Parent, null,
							CompareType.EQUALS));
			filter.setMaskString("#0 and #1");
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
						treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				// filter.getFilterItems().add(new FilterItemInfo("level",new
				// Integer(treeModel.getLevel()),CompareType.EQUALS));
				// 修改，应当使用parentID，因为levle是计算生成的，不应由客户端传递。 Jacky at 2004-11-4
				filter.setMaskString("#0 and #1 and #2");
			}
		} else {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number,
					treeModel.getNumber(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			if (treeModel.innerGetParent().getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.tree_Parent,
						treeModel.innerGetParent().getId().toString(),
						CompareType.EQUALS);
				filter.getFilterItems().add(filterItem);
				filter.setMaskString("#0 and #1");
			}
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
						treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				if (treeModel.innerGetParent().getId() != null) {
					filter.setMaskString("#0 and #1 and #2");
				} else {
					filter.setMaskString("#0 and #1");
				}
			}
		}

		EntityViewInfo view = new EntityViewInfo();
		// CU隔离
		FilterInfo filterCU = getFilterForDefaultCU(ctx, treeModel);
		if (FilterUtility.hasFilterItem(filterCU)) {
			if (FilterUtility.hasFilterItem(filter)) {
				filter.mergeFilter(filterCU, "AND");
			} else {
				filter = filterCU;
			}
		}

		view.setFilter(filter);
		TreeBaseCollection results = this.getTreeBaseCollection(ctx, view);

		if (results != null && results.size() > 0) {
			throw new EASBizException(EASBizException.CHECKNUMDUP,
					new Object[] { treeModel.getNumber() });
		}
	}

	/**
	 * 名称重复
	 * 
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		TreeBaseInfo treeModel = (TreeBaseInfo) model;

		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = null;
		// 父节点为空时检查根对象编码是否重复。
		if (treeModel.innerGetParent() == null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name,
					treeModel.getName(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			filter.getFilterItems().add(
					new FilterItemInfo(IFWEntityStruct.tree_Parent, null,
							CompareType.EQUALS));
			filter.setMaskString("#0 and #1");
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
						treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				// filter.getFilterItems().add(new FilterItemInfo("level",new
				// Integer(treeModel.getLevel()),CompareType.EQUALS));
				// 修改，应当使用parentID，因为levle是计算生成的，不应由客户端传递。 Jacky at 2004-11-4
				filter.setMaskString("#0 and #1 and #2");
			}
		} else {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name,
					treeModel.getName(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			if (treeModel.innerGetParent().getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.tree_Parent,
						treeModel.innerGetParent().getId().toString(),
						CompareType.EQUALS);
				filter.getFilterItems().add(filterItem);
				filter.setMaskString("#0 and #1");
			}
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
						treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				if (treeModel.innerGetParent().getId() != null) {
					filter.setMaskString("#0 and #1 and #2");
				} else {
					filter.setMaskString("#0 and #1");
				}
			}
		}

		EntityViewInfo view = new EntityViewInfo();
		// CU隔离
		FilterInfo filterCU = getFilterForDefaultCU(ctx, treeModel);
		if (FilterUtility.hasFilterItem(filterCU)) {
			if (FilterUtility.hasFilterItem(filter)) {
				filter.mergeFilter(filterCU, "AND");
			} else {
				filter = filterCU;
			}
		}

		view.setFilter(filter);
		TreeBaseCollection results = this.getTreeBaseCollection(ctx, view);

		if (results != null && results.size() > 0) {
			throw new EASBizException(EASBizException.CHECKNAMEDUP,
					new Object[] { treeModel.getName() });
		}
	}

	protected void _audit(Context ctx, BOSUuid billID) throws BOSException,
			EASBizException {
		RESchTemplateInfo info = (RESchTemplateInfo) _getValue(ctx, new ObjectUuidPK(billID));
		if (info.getState().equals(FDCBillStateEnum.SAVED) || info.getState().equals(FDCBillStateEnum.AUDITTED)) {
           throw new ScheduleException(ScheduleException.WITHMSG, new Object[] { "当前状态为" + info.getState().getAlias() + "状态，不能进行审批操作!" });  
		}
		info.setState(FDCBillStateEnum.AUDITTED);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		_updatePartial(ctx, info, selector);
		
	}
	

	protected void _setAudittingStatus(Context ctx, BOSUuid billID)
			throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_sch_reschtemplate set fstate = ? where fid = ?");
		builder.addParam(FDCBillStateEnum.AUDITTING.getValue());
		builder.addParam(billID.toString());
		builder.execute();
		
	}

	protected void _setSubmitStatus(Context ctx, BOSUuid billID)
			throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_sch_reschtemplate set fstate = ? where fid = ?");
		builder.addParam(FDCBillStateEnum.SUBMITTED.getValue());
		builder.addParam(billID.toString());
		builder.execute();
		
	}

	protected void _unAudit(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		RESchTemplateInfo info = (RESchTemplateInfo) _getValue(ctx, new ObjectUuidPK(billID));
		if (!info.getState().equals(FDCBillStateEnum.AUDITTED)) {
			throw new ScheduleException(ScheduleException.WITHMSG, new Object[] { "当前状态为" + info.getState().getAlias() + "状态，不能进行反审批操作!" });
		}

		info.setState(FDCBillStateEnum.SUBMITTED);
		info.setAuditor(null);
		info.setAuditTime(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		_updatePartial(ctx, info, selector);

	}
    /**
     * 批量删除
     */
    protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		for(int i = 0; i<arrayPK.length;i++){
			_delete( ctx, arrayPK[i]); 
		}
	}
}