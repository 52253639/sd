/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqCollection;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqFactory;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqInfo;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerCollection;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerFactory;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SchAdjustReqByGrantListUI extends AbstractSchAdjustReqByGrantListUI {
	private static final Logger logger = CoreUIObject.getLogger(SchAdjustReqByGrantListUI.class);

	private ScheduleVerManagerCollection schVerManagerColls = null;
	private Map adjustReqMap = new HashMap();

	private int selectedRowIndex = 0;
	/**
	 * output class constructor
	 */
	public SchAdjustReqByGrantListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	protected void tblSchAdjustReq_tableSelectChanged(KDTSelectEvent e) throws Exception {
		if(tblSchAdjustReq.getCell(tblSchAdjustReq.getSelectManager().getActiveRowIndex(), "id")!=null){
			Object o = tblSchAdjustReq.getCell(tblSchAdjustReq.getSelectManager().getActiveRowIndex(), "id").getValue();
			if(o!=null){
				String schVerManagerId = o.toString();
				Object o2 = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "id").getValue();
//				if(tblMain.getColumnIndex("workFlowID")<0){
//					IColumn col = tblMain.addColumn();
//					col.setKey("workFlowID");
//					col.setWidth(0);
//					col.getStyleAttributes().setHided(true);
//				}else{
//					tblMain.removeColumn(tblMain.getColumnIndex("workFlowID"));
//					IColumn col = tblMain.addColumn();
//					col.setKey("workFlowID");
//				col.setWidth(0);
//					col.getStyleAttributes().setHided(true);
//				}
				tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "workFlowID").setValue(o.toString());
			}
		}
	}
	protected void tblSchAdjustReq_tableClicked(KDTMouseEvent e) throws Exception {
		/*
		 * 表头排序
		 */
		if(e.getType()==KDTStyleConstants.HEAD_ROW){
			super.tblMain_tableClicked(e);
		}else 
		//双击查看总进度计划
		if (e.getClickCount() == 1) {
			return;
		}
		if (e.getClickCount() == 2) {
			actionView_actionPerformed(null);
		}
	}
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		//super.tblMain_tableClicked(e);
		/*
		 * 表头排序
		 */
		if(e.getType()==KDTStyleConstants.HEAD_ROW){
			super.tblMain_tableClicked(e);
		}else {
		//双击查看总进度计划
			if(e.getClickCount()==2){
				checkSelected(tblMain);
				this.setCursorOfWair();
		        UIContext uiContext = new UIContext(this);
		        String scheduleId = getSelectedKeyValue(tblMain);
		        if(scheduleId==null) return;
		        
				uiContext.put(UIContext.ID, scheduleId);
		        IUIWindow projectScheduleUIWindow = null;
		        String editUIName=null;
		        editUIName = com.kingdee.eas.fdc.schedule.client.ProjectScheduleUI.class.getName();
		        
		        if (SwingUtilities.getWindowAncestor(this) != null
		                && SwingUtilities.getWindowAncestor(this) instanceof JDialog) {
		        	projectScheduleUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
		                    editUIName, uiContext, null, "FINDVIEW");
	
		        } else {
		            // 创建UI对象并显示
		        	projectScheduleUIWindow = UIFactory.createUIFactory(getEditUIModal()).create(
		            		editUIName, uiContext, null, "FINDVIEW");
		        }
		        if(projectScheduleUIWindow!=null) {
	//	        	EditUI ui=(EditUI)contractUiWindow.getUIObject();
	//	        	ui.
		        	projectScheduleUIWindow.show();
		        }
				this.setCursorOfDefault();
			}
		}
	
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
		if (e.getSelectBlock() == null)
			return;
		KDTSelectBlock selectBlock = e.getSelectBlock();
		int top = selectBlock.getTop();
		this.tblSchAdjustReq.removeRows(false);
		
		fillSchAdjustReqTable(top);
	}

	private void fillSchAdjustReqTable(int top) throws BOSException {
		if(this.tblMain.getRow(top)==null)
			return;
		ScheduleVerManagerInfo schVerManager = (ScheduleVerManagerInfo)this.tblMain.getRow(top).getUserObject();
		if(schVerManager!=null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("adjustType"));
			selector.add(new SelectorItemInfo("adjustReason.id"));
			selector.add(new SelectorItemInfo("adjustReason.number"));
			selector.add(new SelectorItemInfo("adjustReason.name"));
			selector.add(new SelectorItemInfo("handler.id"));
			selector.add(new SelectorItemInfo("handler.number"));
			selector.add(new SelectorItemInfo("handler.name"));
			selector.add(new SelectorItemInfo("auditor.id"));
			selector.add(new SelectorItemInfo("auditor.number"));
			selector.add(new SelectorItemInfo("auditor.name"));
			
			view.setFilter(filter);
			view.setSelector(selector);
			filter.getFilterItems().add(new FilterItemInfo("curProject.id",schVerManager.getProject().getId().toString()));
			view.getSelector().add("*");
			ScheduleAdjustGattReqCollection adjustGanttReqs = ScheduleAdjustGattReqFactory.getRemoteInstance().getScheduleAdjustGattReqCollection(view);
			for(Iterator it = adjustGanttReqs.iterator();it.hasNext();){
				ScheduleAdjustGattReqInfo reqInfo = (ScheduleAdjustGattReqInfo)it.next();
				IRow row = tblSchAdjustReq.addRow();
				row.setUserObject(reqInfo);
				row.getCell("id").setValue(reqInfo.getId().toString());//ID
				row.getCell("project").setValue(schVerManager.getProject().getName());//工程项目
				row.getCell("number").setValue(reqInfo.getNumber());//编码
				row.getCell("name").setValue(reqInfo.getName());//名称
				row.getCell("state").setValue(reqInfo.getState());//状态
				row.getCell("adjustType").setValue(reqInfo.getAdjustType());//调整类别 
				row.getCell("adjustReason").setValue(reqInfo.getAdjustReason().getName());//调整原因
				row.getCell("affect").setValue(reqInfo.getAffect());//影响程度
				if(reqInfo.getHandler()!=null){
					row.getCell("reqPerson").setValue(reqInfo.getHandler().getName());//申请发起人
				}
				row.getCell("reqDate").setValue(reqInfo.getReqDate());//发起日期
				if(reqInfo.getAuditor()!=null){
					row.getCell("auditor").setValue(reqInfo.getAuditor().getName());//审批人
				}
				row.getCell("auditDate").setValue(reqInfo.getAuditTime());//审批日期
				
			}
			this.tblSchAdjustReq.getSelectManager().select(0,this.tblSchAdjustReq.getColumnCount());
		}
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	protected FilterInfo getDefaultFilterForQuery() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("state", ScheduleStateEnum.EXETING_VALUE));
		return filter;
	}

	protected void initTree() throws Exception {
		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}

		String rootid = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();

		TreeModel orgTreeModel = null;
		// 参数否，检查工程树根结点是否对应的控制单元组织，并且付款计划根据合同责任部门过滤
		rootid = cuInfo.getId().toString();
		orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY, "", rootid, null, FDCHelper.getActionPK(this.actionOnLoad));

		this.treeMain.setModel(orgTreeModel);
		this.treeMain.expandAllNodes(true, (TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		treeSelectChange();
	}

	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	}

	private void displayAdjustReqBillByTask() {
		// TODO Auto-generated method stub

	}

	/**
	 * 选择工程项目节点和合同类型节点后的选择事件
	 * 
	 * @return
	 * @throws Exception
	 */
	protected FilterInfo getFDCWBSFilter(Object projectNode) throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		if (projectNode != null && projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			if (projTreeNodeInfo instanceof CurProjectInfo && ((CurProjectInfo) projTreeNodeInfo).isIsLeaf()) {
				id = projTreeNodeInfo.getId();
				filterItems.add(new FilterItemInfo("curProject.id", id));
			}
		}
		filterItems.add(new FilterItemInfo("id", "select fwbsid from t_sch_fdcscheduletask", CompareType.INNER));
		return filter;
	}

	Set prjLeafset = null;

	private void beforeFillMainTable() throws EASBizException, BOSException {
		tblMain.removeRows();
		tblSchAdjustReq.removeRows();
		getPrjIds();
		getVerData();
	}

	private void fillMainTable() throws EASBizException, BOSException {
		if (schVerManagerColls == null || schVerManagerColls.size() == 0) {
			return;
		}
		for (Iterator iter = schVerManagerColls.iterator(); iter.hasNext();) {
			ScheduleVerManagerInfo schVerManager = (ScheduleVerManagerInfo) iter.next();
			IRow row = this.tblMain.addRow();
			row.setUserObject(schVerManager);
			row.getCell("id").setValue(schVerManager.getId());
			row.getCell("projectId").setValue(schVerManager.getProject().getId());
			row.getCell("curProject").setValue(schVerManager.getProject().getName());
			row.getCell("isLastestVerion").setValue(new Boolean(schVerManager.isIsLatestVer()));
			row.getCell("state").setValue(schVerManager.getState());
			row.getCell("version").setValue((new Float(schVerManager.getVersion())));
			row.getCell("versionReason").setValue(schVerManager.getCreateReason());
			row.getCell("beginDate").setValue(schVerManager.getStartDate());
			row.getCell("endDate").setValue(schVerManager.getEndDate());
			row.getCell("effectTimes").setValue(schVerManager.getEffectTimes());
			row.getCell("natureTimes").setValue(schVerManager.getNatureTimes());
		}
		//默认选中第一行
		this.tblMain.getSelectManager().select(0,this.tblMain.getRowCount());
	}

	private void getVerData() throws BOSException, EASBizException {
		if (schVerManagerColls != null && schVerManagerColls.size() > 0) {
			schVerManagerColls.clear();
		}
		if (prjLeafset != null && prjLeafset.size() > 0) {
			Map paramMap = new HashMap();
			paramMap.put("prjIds", prjLeafset);
			paramMap.put("isAdjust", Boolean.TRUE);
			schVerManagerColls = ScheduleVerManagerFactory.getRemoteInstance().getVerData(paramMap);
		}
	}

	private void getPrjIds() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null)
			return;
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		} else if (node != null) {
			prjLeafset = new HashSet();
			int count = node.getLeafCount();
			if (count > 0 && !node.isLeaf()) {// 非明细节点
				while (count > 0 && node != null && node.getUserObject() != null) {
					if (node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
						CurProjectInfo project = (CurProjectInfo) node.getUserObject();
						String orgId = project.getId().toString();
						prjLeafset.add(orgId);
						count--;
					}
					node = (DefaultKingdeeTreeNode) node.getNextNode();
				}
			} else {// 明细节点
				if (node.getUserObject() instanceof CurProjectInfo) {
					CurProjectInfo project = (CurProjectInfo) node.getUserObject();
					String orgId = project.getId().toString();
					prjLeafset.add(orgId);
				}
			}
		}
	}

	// 获取有权限的组织
	protected Set authorizedOrgs = null;

	private void buildProjectTree() throws Exception {

		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

		OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentFIUnit());
		projectTreeBuilder.build(this, treeMain, actionOnLoad);

		authorizedOrgs = (Set) ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if (authorizedOrgs == null) {
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);
			if (orgs != null) {
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while (it.hasNext()) {
					authorizedOrgs.add(it.next());
				}
			}
		}

	}

	protected void treeSelectChange() throws Exception {
		beforeFillMainTable();
		fillMainTable();
		//选择快如果为空
		if (tblMain.getSelectManager().getActiveRowIndex()==-1)
			return;
		KDTSelectBlock selectBlock = tblMain.getSelectManager().get();
		int top = selectBlock.getTop();
		this.tblSchAdjustReq.removeRows(false);
		fillSchAdjustReqTable(top);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
    	if(actRowIdx < 0)
    		return ;
    	if(actRowIdx>0){
    		selectedRowIndex = actRowIdx;
    	}
    	String schVerManagerId = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
    	String prjId = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "projectId").getValue().toString();
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select count(fid) jishu from T_SCH_ScheduleAdjustGattReq where fstate in (?,?) and FCurProjectID=?   ");
    	builder.addParam(FDCBillStateEnum.SAVED_VALUE);
    	builder.addParam(FDCBillStateEnum.SUBMITTED_VALUE);
    	builder.addParam(prjId);
    	
    	IRowSet rowSet = builder.executeQuery();
    	if(rowSet!=null&&rowSet.next()){
    		int count = rowSet.getInt("jishu");
    		if(count>0){
    			FDCMsgBox.showError("已经存在保存或是提交状态的单据，不允许新增！");
    			SysUtil.abort();
    		}
    	}
    	
    	ScheduleVerManagerInfo schVerManager = null;
    	CurProjectInfo project = null;
    	if(tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex())!=null&&
    			tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getUserObject()!=null){
    		schVerManager = (ScheduleVerManagerInfo) tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getUserObject();
    		project = schVerManager.getProject();
    	}
    	else{
    		return;
    	}
    	String editUIName = getEditUIName();
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("isAdjust", Boolean.TRUE);
		uiContext.put("adjustVerID", schVerManagerId);
		uiContext.put("CurProject", project);
		
		// 创建UI对象并显示
		IUIWindow windows = this.getUIWindow();
		String type = this.getEditUIModal();
		IUIWindow AdjustUiWindow = UIFactory.createUIFactory(type).create(editUIName, uiContext, null, "ADDNEW");
		if (AdjustUiWindow != null) {
			AdjustUiWindow.show();
		}
		this.treeSelectChange();
		toSelectPreSelected();
		selectedRowIndex = 0;
	}
	private String selectKeyValue = null;
	private int selectIndex = -1;
	/**
	 * 获取当前选择行的主键
	 * 
	 * @return 返回当前选择行的主键，若当前选择行为空，或者当前选中行的主键列为空，则返回null
	 */
	protected String getSelectedKeyValue(KDTable table) {
		//String value = super.getSelectedKeyValue();
		
        int[] selectRows = KDTableUtil.getSelectedRows(table);
        selectIndex=-1;
        if (selectRows.length > 0)
        {
        	selectIndex=selectRows[0];
        }
		
	    KDTSelectBlock selectBlock = table.getSelectManager().get();
	    selectKeyValue = null;
	
	    if (selectBlock != null) {
	        int rowIndex = selectBlock.getTop();
	        IRow row = table.getRow(rowIndex);
	        if (row == null) 
	        {
	            return null;
	        }
	        
	        ICell cell = row.getCell(getKeyFieldName());
	
	        if (cell == null) {
	            MsgBox.showError(EASResource
	                    .getString(FrameWorkClientUtils.strResource
	                            + "Error_KeyField_Fail"));
	            SysUtil.abort();
	        }
	
	        Object keyValue = cell.getValue();
	
	        if (keyValue != null) {
	        	selectKeyValue = keyValue.toString();
	        }
	    }   
	    	
	    return selectKeyValue;
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
    	if(actRowIdx>0){
    		selectedRowIndex = actRowIdx;
    	}
		if (tblSchAdjustReq.getRowCount() == 0 || tblSchAdjustReq.getSelectManager().size() == 0)
        {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
		String id = tblSchAdjustReq.getRow(tblSchAdjustReq.getSelectManager().getActiveRowIndex()).getCell("id").getValue().toString();
		FDCClientUtils.checkBillInWorkflow(this, id);
		ScheduleAdjustGattReqFactory.getRemoteInstance().audit(BOSUuid.read(id));
		treeSelectChange();
		
		toSelectPreSelected();
		selectedRowIndex = 0;
	}

	private void toSelectPreSelected() {
		KDTableUtil.setSelectedRow(tblMain,selectedRowIndex);
		tblMain.scrollToVisible(selectedRowIndex,0);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (tblSchAdjustReq.getRowCount() == 0 || tblSchAdjustReq.getSelectManager().size() == 0)
        {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
		String id = tblSchAdjustReq.getRow(tblSchAdjustReq.getSelectManager().getActiveRowIndex()).getCell("id").getValue().toString();
		FDCSQLBuilder builder =  new FDCSQLBuilder();
		builder.appendSql("select fstate from  T_SCH_ScheduleAdjustGattReq where fid = ? ");
		builder.addParam(id);
		IRowSet rowSet = builder.executeQuery();
		if(rowSet!=null&&rowSet.size()==1){
			rowSet.next();
			String state = rowSet.getString("fstate");
			if(!"已审批".equals(state)){
				MsgBox.showWarning("存在不符合审批条件的记录，请重新选择，保证所选的记录都是已审批状态的");
				SysUtil.abort();
			}
		}
		builder.clear();
		builder.appendSql("update T_SCH_ScheduleAdjustGattReq set FState = ? where fid = ? ");
		builder.addParam(FDCBillStateEnum.SUBMITTED_VALUE);
		builder.addParam(id);
		builder.executeUpdate();
	}
	protected String getSelectedKeyValue() {
		return getSelectedKeyValue(this.tblSchAdjustReq);
	}
	public String getKeyFieldNameForWF() {
		return "workFlowID";
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.refresh(e);
	}
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
    	if(actRowIdx < 0)
    		return ;
    	
    	if(actRowIdx>0){
    		selectedRowIndex = actRowIdx;
    	}
    	String schVerManagerId = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
    	
    	int actRowReqInx = this.tblSchAdjustReq.getSelectManager().getActiveRowIndex();
    	if(actRowReqInx < 0){
    		return ;
    	}
    	String adjustReqId = tblSchAdjustReq.getCell(this.tblSchAdjustReq.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
    	ScheduleAdjustGattReqInfo reqInfo = null;
    	if(tblSchAdjustReq.getRow(this.tblSchAdjustReq.getSelectManager().getActiveRowIndex())!=null&&
    			tblSchAdjustReq.getRow(this.tblSchAdjustReq.getSelectManager().getActiveRowIndex()).getUserObject()!=null){
    		reqInfo = (ScheduleAdjustGattReqInfo) tblSchAdjustReq.getRow(this.tblSchAdjustReq.getSelectManager().getActiveRowIndex()).getUserObject();
    		
    	}
    	
    	String editUIName = getEditUIName();
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, adjustReqId);
		uiContext.put("isAdjust", Boolean.TRUE);
		uiContext.put("adjustVerID", schVerManagerId);
		
		if(reqInfo!=null)
			uiContext.put("CurProject", reqInfo.getCurProject());
		
		
		
		// 创建UI对象并显示
		IUIWindow windows = this.getUIWindow();
		String type = getEditUIModal();
		IUIWindow AdjustUiWindow = UIFactory.createUIFactory(type).create(editUIName, uiContext, null, "VIEW");
		if (AdjustUiWindow != null) {
			AdjustUiWindow.show();
		}
		treeSelectChange();
		toSelectPreSelected();
		selectedRowIndex = 0;
	}
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
    	if(actRowIdx < 0)
    		return ;
    	
    	if(actRowIdx>0){
    		selectedRowIndex = actRowIdx;
    	}
		super.actionRefresh_actionPerformed(e);
		treeSelectChange();
		toSelectPreSelected();
		selectedRowIndex = 0;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
    	if(actRowIdx < 0)
    		return ;
    	
    	if(actRowIdx>0){
    		selectedRowIndex = actRowIdx;
    	}
    	String schVerManagerId = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
    	
    	int actRowReqInx = this.tblSchAdjustReq.getSelectManager().getActiveRowIndex();
    	if(actRowReqInx < 0){
    		return ;
    	}
    	String adjustReqId = tblSchAdjustReq.getCell(this.tblSchAdjustReq.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
    	ScheduleAdjustGattReqInfo reqInfo = null;
    	if(tblSchAdjustReq.getRow(this.tblSchAdjustReq.getSelectManager().getActiveRowIndex())!=null&&
    			tblSchAdjustReq.getRow(this.tblSchAdjustReq.getSelectManager().getActiveRowIndex()).getUserObject()!=null){
    		reqInfo = (ScheduleAdjustGattReqInfo) tblSchAdjustReq.getRow(this.tblSchAdjustReq.getSelectManager().getActiveRowIndex()).getUserObject();
    		if(FDCBillStateEnum.AUDITTED.equals(reqInfo.getState())){
    			FDCMsgBox.showInfo(this, "单据为已审批状态，不能执行此操作！");
    			return;
    		}
    	}
    	
    	
    	
    	String editUIName = getEditUIName();
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, adjustReqId);
		uiContext.put("isAdjust", Boolean.TRUE);
		uiContext.put("adjustVerID", schVerManagerId);
		
		if(reqInfo!=null)
			uiContext.put("CurProject", reqInfo.getCurProject());
		// 创建UI对象并显示
		IUIWindow windows = this.getUIWindow();
		String type = getEditUIModal();
		IUIWindow AdjustUiWindow = UIFactory.createUIFactory(type).create(editUIName, uiContext, null, "EDIT");
		if (AdjustUiWindow != null) {
			AdjustUiWindow.show();
		}
		treeSelectChange();
		toSelectPreSelected();
		selectedRowIndex = 0;
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
    	if(actRowIdx>0){
    		selectedRowIndex = actRowIdx;
    	}
    	
		if (tblSchAdjustReq.getRowCount() == 0 || tblSchAdjustReq.getSelectManager().size() == 0)
        {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
		if(tblSchAdjustReq.getRow(this.tblSchAdjustReq.getSelectManager().getActiveRowIndex())!=null&&
    			tblSchAdjustReq.getRow(this.tblSchAdjustReq.getSelectManager().getActiveRowIndex()).getUserObject()!=null){
    		ScheduleAdjustGattReqInfo reqInfo = (ScheduleAdjustGattReqInfo) tblSchAdjustReq.getRow(this.tblSchAdjustReq.getSelectManager().getActiveRowIndex()).getUserObject();
    		if(FDCBillStateEnum.AUDITTED.equals(reqInfo.getState())){
    			FDCMsgBox.showInfo(this, "单据为已审批状态，不能执行此操作！");
    			return;
    		}
    		FDCClientUtils.checkBillInWorkflow(this, reqInfo.getId().toString());
    		int answer = FDCMsgBox.showConfirm2("是否确认删除？");
    		if(answer == JOptionPane.CANCEL_OPTION){
    			return;
    		}
    		ScheduleAdjustGattReqFactory.getRemoteInstance().delete(new ObjectUuidPK(reqInfo.getId()));
    		FDCMsgBox.showInfo("操作成功");
    		treeSelectChange();
    		toSelectPreSelected();
    	}
	}
	public void checkSelected(KDTable paramTable)
    {
        if (paramTable.getRowCount() == 0 || paramTable.getSelectManager().size() == 0)
        {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
    }

	protected ICoreBase getBizInterface() throws Exception {
		return ScheduleAdjustGattReqFactory.getRemoteInstance();
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWWIN;
	}

	protected String getEditUIName() {
		return SchAdjustReqByGrantEditUI.class.getName();
	}
	/**
	 * 重写该方法，使得多表格的“表格设置”功能不至于混乱(点击A表格的“表格设置”显示B表格的相关信息) by cassiel 2010-06-24
	 */
	protected void setThelper() {
		// TODO Auto-generated method stub
		super.setThelper();
		this.setQueryPreference(true);
	}
	public void onLoad() throws Exception {
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		//启用甘特图的调整申请模式
		boolean adjustByGrant = FDCUtils.getDefaultFDCParamByKey(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString(), FDCConstants.FDC_PARAM_ADJUSTBYGRANT);
		if(!adjustByGrant){
			MsgBox.showWarning("已禁用甘特图的调整申请模式，此功能不可用！");
			this.abort();
		}
	
		super.onLoad();
		tblSchAdjustReq.checkParsed();
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		// 工程项目树的构建
		buildProjectTree();
		treeMain.setShowsRootHandles(true);
		treeSelectChange();
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblSchAdjustReq.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		treeMain.setSelectionRow(0);

		this.tblMain.getColumn("version").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("effectTimes").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("natureTimes").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		
		 //增加KDtreeView
		KDTreeView treeView=new KDTreeView();
		treeView.setTree(treeMain);
		treeView.setShowButton(true);
		treeView.setTitle("工程项目");
		pnlMain.add(treeView,"left");
		treeView.setShowControlPanel(true);
		this.ActionUnAudit.setVisible(false);
		this.ActionUnAudit.setEnabled(false);
		
		this.btnVersionCompare.setIcon(EASResource
				.getIcon("imgTbtn_combinemessage"));
	}

	public void actionVersionCompare_actionPerformed(ActionEvent e)
			throws Exception {
		ScheduleVerManagerInfo schVerManager = null;
    	CurProjectInfo project = null;
    	if(tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex())!=null&&
    			tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getUserObject()!=null){
    		schVerManager = (ScheduleVerManagerInfo) tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getUserObject();
    		project = schVerManager.getProject();
    	}
    	else{
    		FDCMsgBox.showInfo(this,"请先选择总计划");
    		return;
    	}
		UIContext uiContext = new UIContext();
		uiContext.put("curProject", project);
		
		String uiName = SelectVersionCompareUI.class.getName();
		IUIWindow windows = this.getUIWindow();
		String type = UIFactoryName.MODEL;
		IUIWindow AdjustUiWindow = UIFactory.createUIFactory(type).create(uiName, uiContext, null, "VIEW");
		if (AdjustUiWindow != null) {
			AdjustUiWindow.show();
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		this.btnTraceDown.setVisible(false);
		this.btnTraceUp.setVisible(false);
		this.btnCreateTo.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnSave.setVisible(false);
		this.btnAddRow.setVisible(false);
		this.btnDeleteRow.setVisible(false);
		this.btnEntryAttachment.setVisible(false);
		this.btnLocate.setVisible(false);
		this.btnQuery.setVisible(false);

		this.menuView.setVisible(false);
		this.menuView.setEnabled(false);
		this.menuBiz.setVisible(false);
		this.menuBiz.setEnabled(false);
		this.menuItemTraceDown.setVisible(false);
		this.menuItemTraceUp.setVisible(false);
		this.menuItemCreateTo.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.menuItemPrint.setVisible(false);
		this.menuItemPrintPreview.setVisible(false);
		this.menuItemSave.setVisible(false);
		this.menuItemEntryAttachment.setVisible(false);
		this.menuItemCopyTo.setVisible(false);
		this.menuItemAddRow.setVisible(false);
		this.menuItemDeleteRow.setVisible(false);
		
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
	}

	protected void initWorkButton() {
		super.initWorkButton();
	}

	public void initUIContentLayout() {

        this.setBounds(new Rectangle(10, 10, 1013, 629));
		this.setLayout(new KDLayout());
		this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
		pnlMain.setBounds(new Rectangle(10, 10, 993, 609));
		this.add(pnlMain, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT
				| KDLayout.Constraints.ANCHOR_RIGHT));
		// pnlMain
		pnlMain.add(pnlRight, "right");
		pnlMain.add(pnlLeftTree, "left");
		// pnlRight
		pnlRight.setLayout(new KDLayout());
		// TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
		pnlRight.putClientProperty("OriginalBounds", new Rectangle(0, 0, 733, 608));
		kDSplitPane1.setBounds(new Rectangle(0, 1, 733, 608));
		pnlRight.add(kDSplitPane1, new KDLayout.Constraints(0, 1, 733, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE
				| KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		// kDSplitPane1
		kDSplitPane1.add(ctnScheduleTask, "top");
		kDSplitPane1.add(ctnAdjustReq, "bottom");
		// ctnScheduleTask
		ctnScheduleTask.getContentPane().setLayout(new BorderLayout(0, 0));
		ctnScheduleTask.getContentPane().add(tblMain, BorderLayout.CENTER);
		// ctnAdjustReq
		ctnAdjustReq.getContentPane().setLayout(new BorderLayout(0, 0));
		ctnAdjustReq.getContentPane().add(tblSchAdjustReq, BorderLayout.CENTER);
		// pnlLeftTree
		pnlLeftTree.getViewport().add(treeMain, null);
	}
	
	public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		handleWorkFlow();
		super.actionViewDoProccess_actionPerformed(e);
	}
	//业务流程列表
	public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		if(!handleWorkFlow()){
			return;
		}
		super.actionWorkflowList_actionPerformed(e);
	}

	private boolean handleWorkFlow() {
		boolean isPass = true;
		ICell activeCell = tblMain.getCell(tblMain.getSelectManager().getActiveRowIndex(), "workFlowID");
		if(activeCell == null){
			isPass = false;
		}
		Object o = activeCell.getValue();
		if(o == null){
			isPass = false;
		}
		return isPass;
	}
	//流程图
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		if(!handleWorkFlow()){
			return;
		}
		super.actionWorkFlowG_actionPerformed(e);
	}
	//下一步处理人
	public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		if(!handleWorkFlow()){
			return;
		}
		super.actionNextPerson_actionPerformed(e);
	}
	//多级审批
	public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		if(!handleWorkFlow()){
			return;
		}
		super.actionMultiapprove_actionPerformed(e);
	}
	//审批结果查看
	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		if(!handleWorkFlow()){
			return;
		}
		super.actionAuditResult_actionPerformed(e);
	}
	//发送工作流短信
	public void actionSendSmsMessage_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		if(!handleWorkFlow()){
			return;
		}
		super.actionSendSmsMessage_actionPerformed(e);
	}
}