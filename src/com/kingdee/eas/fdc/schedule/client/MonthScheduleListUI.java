/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.schedule.MonthScheduleCollection;
import com.kingdee.eas.fdc.schedule.MonthScheduleFactory;
import com.kingdee.eas.fdc.schedule.MonthScheduleInfo;
import com.kingdee.eas.fdc.schedule.ProjectImageCollection;
import com.kingdee.eas.fdc.schedule.ProjectImageFactory;
import com.kingdee.eas.fdc.schedule.ProjectImageInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MonthScheduleListUI extends AbstractMonthScheduleListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MonthScheduleListUI.class);
    
    /**
     * output class constructor
     */
    public MonthScheduleListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	FDCClientHelper.setActionEnable(new ItemAction[]{
    			this.actionCancel,
    			this.actionCancelCancel,
    			this.actionClose,
    			this.actionUnClose
    	}, false);
    	
    	btnTraceUp.setVisible(false);
		btnTraceDown.setVisible(false);
		
		menuItemCancel.setVisible(false);
		menuItemCancelCancel.setVisible(false);
		
		btnCreateTo.setVisible(false);
		btnTraceDown.setVisible(false);
		btnTraceUp.setVisible(false);
		
		btnClose.setVisible(false);
		btnUnClose.setVisible(false);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeMain_valueChanged(e);
    }
    

	 public void actionEdit_actionPerformed(ActionEvent e) throws Exception{
		 super.actionEdit_actionPerformed(e);
	 }
	
	 public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
			checkSelected();
			checkBillState(ScheduleStateEnum.SUBMITTED, "selectRightRowForAudit");
			
			audit(ContractClientUtils.getSelectedIdValues(getMainTable(),
					getKeyFieldName()));
			showOprtOKMsgAndRefresh();
	}
	    
	 public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		 checkSelected();
		 checkBillState(ScheduleStateEnum.AUDITTED, "selectRightRowForUnAudit");
		 List selectedIdValues = ContractClientUtils.getSelectedIdValues(
					getMainTable(), getKeyFieldName());
			// 检查引用
		 for (Iterator iter = selectedIdValues.iterator(); iter.hasNext();) {
			 String id = (String) iter.next();
			 checkRef(id);
		 }

		 unAudit(selectedIdValues);
		 showOprtOKMsgAndRefresh();
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
	    	
		checkSelected();
		checkBillStateForRemove(ScheduleStateEnum.AUDITTED, "selectRightRowForAudit");
		super.actionRemove_actionPerformed(e);
	}
    
    protected ICoreBase getBizInterface() throws Exception {
		return MonthScheduleFactory.getRemoteInstance();
	}
    
    protected String getEditUIName() {
		return MonthScheduleEditUI.class.getName();
	}
    
    protected FilterInfo getMainFilter() throws Exception {
    	//做工程项目隔离及部门隔离
    	String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	Set prjIdSet=getProjectLeafsOfNode(node);
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("project.id",prjIdSet,CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("adminDept.id",currentOrgId));
		return filter;
    }
    
    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    protected boolean isTableTreeRow() {
    	return false;
    }
    
    protected void checkBillState(ScheduleStateEnum state, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		MonthScheduleCollection coll = MonthScheduleFactory.getRemoteInstance().getMonthScheduleCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			MonthScheduleInfo element = (MonthScheduleInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());

			if (!element.getState().equals(state)) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}
    
    protected void audit(List ids) throws Exception {
    	MonthScheduleFactory.getRemoteInstance().audit(new HashSet(ids),SysContext.getSysContext().getCurrentUserInfo());
	}

	protected void unAudit(List ids) throws Exception {
		MonthScheduleFactory.getRemoteInstance().unAudit(new HashSet(ids),SysContext.getSysContext().getCurrentUserInfo());
	}
	
	protected void execQuery()
    {
        super.execQuery();
    }
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
    	viewInfo=(EntityViewInfo)viewInfo.clone();
    	try{
    		viewInfo.getFilter().mergeFilter(getMainFilter(), "and");
//    		SorterItemInfo info = new SorterItemInfo("version");
//    		info.setSortType(SortType.DESCEND);
//			viewInfo.getSorter().addObject(0, info);
			viewInfo.getSorter().addObject(0,new SorterItemInfo("project.id"));
    	}catch(Exception e){
    		this.handUIException(e);
    	}
    	return super.getQueryExecutor(queryPK, viewInfo);
    }
	
	protected void checkBillStateForRemove(ScheduleStateEnum state, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		MonthScheduleCollection coll = MonthScheduleFactory.getRemoteInstance().getMonthScheduleCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			MonthScheduleInfo element = (MonthScheduleInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());

			if (element.getState().equals(state)) {
				MsgBox.showWarning(this, "单据处于审核状态，不能删除！");
				abort();
			}

		}
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
}