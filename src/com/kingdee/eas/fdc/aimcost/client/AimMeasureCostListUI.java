/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.log.LogUtil;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeCollection;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeFactory;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.basedata.client.ProjectIndexDataUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class AimMeasureCostListUI extends AbstractAimMeasureCostListUI {
	private static final Logger logger = CoreUIObject.getLogger(AimMeasureCostListUI.class);
	private KDDialog diag=null;
	protected Set authorizedOrgs = null;
	
	private static String orgID = null ;
	
	public void onLoad() throws Exception {
		tblMain.addKDTDataFillListener(new KDTDataFillListener(){
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_afterDataFile(e);
				
			}
		});
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.initTree();
		super.onLoad();
		this.treeMain.setSelectionRow(0);
		this.actionCreateTo.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuWorkFlow.setVisible(false);
		this.actionExportToAim.setEnabled(true);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		//TODO 强大的目标成本测算全部数据导入功能，很久没维护，暂时不能用，屏蔽
		// this.actionImportData.setVisible(false);
		String formatString = "yyyy-MM-dd";
		tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat(
				formatString);
		tblMain.getColumn("description").getStyleAttributes().setHided(true);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		tHelper = new TablePreferencesHelper(this);
	}

	protected void tblMain_afterDataFile(KDTDataRequestEvent e) {
		// 这里只能访问当前页的行，千万不要在这个事件里访问table的所有行
		int r1 = e.getFirstRow();
		int r2 = e.getLastRow();
		for (int i = r1; i <= r2; i++) {
			IRow row = tblMain.getRow(i);
			if (row.getCell("sourceBillId").getValue() != null) {
				// 修订版本
				row.getStyleAttributes().setBackground(new Color(0xFFEA67).brighter().brighter());
			}
			if (row.getCell("isLastVersion").getValue().equals(Boolean.TRUE)) {
				row.getStyleAttributes().setBackground(new Color(0xFFEA67));
			}
		}

		try {
			MeasureCostAttachmentUtil.tableDataFillHandle(tblMain, e.getFirstRow(), e.getLastRow(), "id", "attachment");
		} catch (Exception e1) {
			logger.error(e1.getCause(), e1);
		}
	}

	protected void initTree() throws Exception {
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
		
		authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if(authorizedOrgs==null){
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
			            OrgType.CostCenter, 
			            null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}		
		}
	}

	/**
	 * output class constructor
	 */
	public AimMeasureCostListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		this.mainQuery.setFilter(this.getMainFilter());
		this.mainQuery.put("selector", getSelectors());//.getSelector().
/*		mainQuery.getSorter().clear();
		mainQuery.getSorter().add(new SorterItemInfo("mainVerNo"));
		mainQuery.getSorter().add(new SorterItemInfo("subVerNo"));*/
		this.tblMain.removeRows();
		if (this.getSelectOrgId() == null) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			//是导入和新增功能进行同步
			this.actionImportData.setEnabled(false);
		} else {
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
            //是导入和新增功能进行同步
			this.actionImportData.setEnabled(true);
		}
		
		if(tblMain.getRowCount()>0){
			tblMain.getSelectManager().select(0, 0);
		}
	}

	protected FilterInfo getMainFilter() throws Exception {
		String projectId = this.getSelectProjectId();
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(projectId!=null){
			Set idSet = FDCClientUtils.genProjectIdSet(BOSUuid.read(projectId));
			filter.getFilterItems().add(new FilterItemInfo("project.id", idSet,CompareType.INCLUDE));
		}else if (node.getUserObject() instanceof OrgStructureInfo || node.getUserObject() instanceof FullOrgUnitInfo) {
			BOSUuid id = null;
			if (node.getUserObject() instanceof OrgStructureInfo) {
				id = ((OrgStructureInfo)node.getUserObject()).getUnit().getId();	
			}else{
				id = ((FullOrgUnitInfo)node.getUserObject()).getId();
			}				
			
			FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(id));
			String orgUnitLongNumber = orgUnitInfo.getLongNumber();
			
			FilterInfo f = new FilterInfo();
			f.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));
			f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
			f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
			
			f.setMaskString("#0 and #1 and #2");
			
			if(filter!=null){
				filter.mergeFilter(f,"and");
			}
		}
		filter.getFilterItems().add(
				new FilterItemInfo("isAimMeasure", Boolean.TRUE));
		
		//R110520-0084：集团下无法查看下级组织已编制的目标成本测算数据。CU过滤有问题
//		String culongnumber = getNodeCULongnumber(node);
//		
//		if (culongnumber != null) {
//			filter.getFilterItems().add(new FilterItemInfo("CU.longNumber", culongnumber));
//			filter.getFilterItems().add(new FilterItemInfo("CU.longNumber", culongnumber + "!%", CompareType.LIKE));
//			if (projectId == null && getSelectOrgId() == null) {
//				filter.setMaskString("#0 and (#1 or #2)");
//			} else {
//				filter.setMaskString("#0 and #1 and (#2 or #3)");
//			}
//		}
//		if(SysContext.getSysContext().getCurrentCtrlUnit()!=null){
//			//CU隔离 
//			String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
//			filter.getFilterItems().add(new FilterItemInfo("CU.id",cuId));
//		}
		return filter;
	}
	
	/**
	 * 获取节点的CU的longnumber， 如果及节点为空则返回null
	 * @param node
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private String getNodeCULongnumber(DefaultKingdeeTreeNode node) throws EASBizException, BOSException {
		if (node != null && node.getUserObject() != null) {
			
			FullOrgUnitInfo org = null;
			if (node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
				org = projectInfo.getCU().castToFullOrgUnitInfo();
				
			} else if (node.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo info = (OrgStructureInfo)node.getUserObject();
				org = info.getUnit();

			}
			if (org != null) {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("name");
				selector.add("number");
				selector.add("cu.id");
				selector.add("cu.name");
				selector.add("cu.number");
				selector.add("cu.longnumber");
				org = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(org.getId()), selector);
				return org.getCU().getLongNumber();
			}
		}
		return null;
	}

	protected String getSelectProjectId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null ||node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		}
		return null;
	}

	/**
	 * 所选择结点的实体财务组织
	 * @return
	 */
	protected String getSelectOrgId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null ||node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		FullOrgUnitInfo org=null;
		if (node.getUserObject() instanceof OrgStructureInfo){
			final OrgStructureInfo info = (OrgStructureInfo)node.getUserObject();
			org = info.getUnit();

		}
		if(node.getUserObject() instanceof CurProjectInfo){
			CurProjectInfo curPrj=(CurProjectInfo)node.getUserObject();
			EntityViewInfo view=new EntityViewInfo();
			String number=curPrj.getLongNumber();
			
			view.getSelector().add("costCenterOU.id");
			view.getSelector().add("costCenterOU.CU.id");
//			view.getSelector().add("costCenterOU.partFI.unit.id");
			view.getSelector().add("curProject.longNumber");
			ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection=null;
			try {
				projectWithCostCenterOUCollection = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
			} catch (BOSException e) {
				handUIException(e);
			}
			if(projectWithCostCenterOUCollection==null||projectWithCostCenterOUCollection.size()==0){
				return null;
			}
			ProjectWithCostCenterOUInfo pwcInfo=null;
			
			CurProjectInfo prj=null;
			CostCenterOrgUnitInfo costCenter=null;
			for(Iterator iter=projectWithCostCenterOUCollection.iterator();iter.hasNext();){
				pwcInfo=(ProjectWithCostCenterOUInfo)iter.next();
				final String longNumber = pwcInfo.getCurProject().getLongNumber();
				if(number.startsWith(longNumber)){
					if(prj==null){
						prj=pwcInfo.getCurProject();
						costCenter=pwcInfo.getCostCenterOU();
					}else{
						if(longNumber.startsWith(prj.getLongNumber())){
							prj=pwcInfo.getCurProject();
							costCenter=pwcInfo.getCostCenterOU();
						}
					}
				}
			}
			if(costCenter==null){
				MsgBox.showWarning(this, "当前工程项目没有做成本中心与工程项目的对应关系，请先设置！");
				SysUtil.abort();
			}
			org=costCenter.castToFullOrgUnitInfo();
		}
		
		
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("isCompanyOrgUnit");
		selector.add("partFI.isBizUnit");
		selector.add("parent.id");
		try{
			for(;org!=null&&org.getId()!=null;org=org.getParent()){
				org=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(org.getId()), selector);
				if(org.isIsCompanyOrgUnit()){
					if(org.getPartFI().isIsBizUnit()){
						return org.getId().toString();
					}
				}
			};
		}catch (Exception e){
			return null;
		}
		return null;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return MeasureCostFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return AimMeasureCostEditUI.class.getName();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		ItemAction action = getActionFromActionEvent(e);
        if (action.equals(actionAddNew)||action.equals(this.actionImportData))
        {
        	uiContext.put("projectId", this.getSelectProjectId());
    		uiContext.put("orgId", getSelectOrgId());
    		
        }else{
        	checkSelected();
        	int index = tblMain.getSelectManager().getActiveRowIndex();
        	Object prj = tblMain.getCell(index, "project.id").getValue();
        	if(prj!=null){
        		uiContext.put("projectId", prj.toString());
        	}
        	Object orgUnitId = tblMain.getCell(index, "orgUnit.id").getValue();
    		
        	if(orgUnitId!=null){
        		uiContext.put("orgId", orgUnitId.toString());
        	}
        }
        
        if (action.equals(actionVersion))
        {
        	uiContext.put("isEditVersion", Boolean.TRUE);
    		
        }
        uiContext.put("isAimMeasure", Boolean.TRUE);
		super.prepareUIContext(uiContext, e);
	}
	public void onShow() throws Exception {
		super.onShow();
		/**
		 * 中渝:目标成本修订需要支持工作流中多级审批，需要放开显示出一些框架的多级审核工作流的按钮  by Cassiel_peng 2009-8-18
		 */
		btnMultiapprove.setVisible(true);
		btnMultiapprove.setEnabled(true);
		btnNextPerson.setVisible(true);
		btnNextPerson.setEnabled(true);
		btnAuditResult.setVisible(true);
		btnAuditResult.setEnabled(true);
		btnWorkFlowG.setVisible(true);
		btnWorkFlowG.setEnabled(true);
		menuWorkFlow.setVisible(true);
		menuWorkFlow.setEnabled(true);
	}
	protected void initWorkButton() {
		super.initWorkButton();
		
		menuItemAudit.setAction(actionAudit);
		actionExportToAim.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_output"));
		actionAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_AUDIT);
		actionUnAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_UNAUDIT);
		
		menuItemAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		actionAudit.putValue(Action.MNEMONIC_KEY, new Integer('A'));
		menuItemAudit.setText(menuItemAudit.getText()+"(A)");
		actionUnAudit.putValue(Action.MNEMONIC_KEY, new Integer('U'));
		menuItemUnAudit.setText(menuItemUnAudit.getText()+"(U)");
		menuItemUnAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl shift U"));
		
		actionVersion.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_particular"));
		actionExportIndex.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_output"));
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);
		
		menuItemVersion.setMnemonic('V');
		menuItemVersion.setText(menuItemVersion.getText()+"(V)");
		menuItemVersion.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
		
		menuItemExportIndex.setMnemonic('E');
		menuItemExportIndex.setText(menuItemExportIndex.getText()+"(E)");
		menuItemExportIndex.setAccelerator(KeyStroke.getKeyStroke("ctrl shift E"));

		menuExportAim.setMnemonic('X');
		menuExportAim.setText(menuExportAim.getText()+"(X)");
		menuExportAim.setAccelerator(KeyStroke.getKeyStroke("ctrl shift X"));
		
		
	}

	public void actionExportToAim_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportToAim_actionPerformed(e);
		checkSelected();		
		
		String measureId = this.getSelectedKeyValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("measureStage.number");
		sels.add("measureStage.name");
		sels.add("costEntry.costAccount.longNumber");
		sels.add("costEntry.*");
		MeasureCostInfo measure = MeasureCostFactory.getRemoteInstance()
				.getMeasureCostInfo(new ObjectUuidPK(BOSUuid.read(measureId)),sels);
		if(measure.getState()!=null&&measure.getState()!=FDCBillStateEnum.AUDITTED){
			FDCMsgBox.showWarning(this, "测算未审批不能进行此操作");
			SysUtil.abort();
		}

		//收入测算不为审核状态的，不允许目标成本导出
		if(isIncomeJoinCost()){
			if(!isMeasureIncomeAudit(measureId)){
				FDCMsgBox.showWarning(this, "对应版本的收入测算未审批，不能进行此操作");
				SysUtil.abort();
			}
		}
		
		if(true){
			String msg=AimCostClientHelper.getRes("export");
			MeasureStageInfo lastStageInfo = AimCostClientHelper.getLastMeasureStage(measure.getProject(),measure.isIsAimMeasure());
			MeasureStageInfo stageInfo = measure.getMeasureStage();
			if(lastStageInfo!=null&&FDCHelper.subtract(lastStageInfo.getNumber(), stageInfo.getNumber()).compareTo(FDCHelper.ZERO)==1){
				FDCMsgBox
						.showWarning("已存在 [ " + lastStageInfo.getNumber() + lastStageInfo.getName() + " ] 最终版本的目标成本测算,不能" + msg + " [ " + stageInfo.getNumber() + stageInfo.getName() + " ] 目标成本测算.");
				SysUtil.abort();
			}
		}
		if(measure.isIsLastVersion()){
			int v = MsgBox.showConfirm2New(this, "该次测算已经进行了目标成本导出，是否再次导出？");
			if(v!=MsgBox.YES){
				return;
			}
		}
		CurProjectInfo oldPrj=measure.getProject();
//		if(measure.getProject()!=null){
//			int value = MsgBox.showConfirm2New(this, "本次测算已经存在工程项目,是否重新选择？");
//			if(value==MsgBox.YES){
//				measure.setProject(selectProject());
//			}
//		}else{
//			measure.setProject(selectProject());
//		}
		PlanIndexInfo planIndexInfo=getPlanIndex(measure.getId().toString());
		
		boolean isSysProd = checkProject(measure.getProject(),planIndexInfo);
		if (isSysProd) {
			Map param = new HashMap();
			param.put("isSysProd", Boolean.valueOf(isSysProd));
			param.put("measureId", measureId);
			param.put("projectId", measure.getProject().getId().toString());
			MeasureCostFactory.getRemoteInstance().sysProduct(param);
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("fid");
//		view.getSelector().add("costEntry.*");
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("orgOrProId", measure.getProject().getId()
						.toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("isLastVersion", Boolean.TRUE));
		AimCostCollection aims = AimCostFactory.getRemoteInstance()
				.getAimCostCollection(view);
		AimCostInfo aim = null;
		if (aims.size() > 0) {
			aim = aims.get(0);
			if (aim.getCostEntry().size() > 0) {
				if (MsgBox.showConfirm2New(this, "目标成本中已经有数据,是否覆盖?") == MsgBox.NO) {
					return;
				}
			}
		}
		
		MeasureCostFactory.getRemoteInstance().exportAimCost(measureId, measure.getProject().getId().toString());
		MsgBox.showInfo(this, "导出成功!");
		
		measure.setIsLastVersion(true);
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("update T_AIM_MeasureCost set fislastversion=0 where fisAimMeasure=1 and FProjectID=?");
			builder.addParam(measure.getProject().getId().toString());
			builder.execute();
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("isLastVersion");
			MeasureCostFactory.getRemoteInstance().updatePartial(measure, selector);
		} catch (Exception e1) {
			handUIExceptionAndAbort(e1);
		}
		
		if(oldPrj==null||!oldPrj.getId().toString().equals(measure.getProject().getId().toString())){
			reverseWriteProject(measure);
		}
		refreshList();
		storeTemplateData(measure.getId().toString());
	}
	
	/**
	 * 判断对应收入测算是否已审核 
	 */
	private boolean isMeasureIncomeAudit(String measureCostId) throws EASBizException, BOSException, UuidException{		
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("srcMeasureCostId", measureCostId);		
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");	
		MeasureIncomeInfo measureIncome = null;
		MeasureIncomeCollection measureIncomeColl = MeasureIncomeFactory.getRemoteInstance()
				.getMeasureIncomeCollection(view);		
		if(measureIncomeColl.size()>0){
			measureIncome = measureIncomeColl.get(0);
			if(measureIncome.getState()!=null&&measureIncome.getState()==FDCBillStateEnum.AUDITTED){
				return true;
			}else{
				return false;
			}
		}else{ //不存在该记录
			return false;
//			return true;//不存在说明是历史数据 by hpw
		}		
	}
	
	private HashMap params = null;
	private HashMap getParams() {
		if(params==null){
	        HashMap hmParamIn = new HashMap();	        
	        //成本测算与收入测算是否联用
	        hmParamIn.put(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST, null);
	        try{
	        	HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
	        	params=hmAllParam;
	        }catch(Exception e){
	        	handUIException(e);
	        }
		}
		if(params==null){
			params=new HashMap();
		}
        return params;
	}
	/**
	 * 成本测算与收入测算是否联用
	 * @return
	 */
	protected boolean isIncomeJoinCost() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}

	private PlanIndexInfo getPlanIndex(String headId) throws BOSException {
			if(headId==null) return null;
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			view.setFilter(filter);
			filter.appendFilterItem("headID", headId);

			SelectorItemCollection selector=view.getSelector();
			selector.add("*");
			selector.add("entrys.*");
			selector.add("entrys.product.*");
			
			view.getSorter().add(new SorterItemInfo("entrys.type"));
			view.getSorter().add(new SorterItemInfo("entrys.index"));
			PlanIndexCollection planIndexCollection = PlanIndexFactory.getRemoteInstance().getPlanIndexCollection(view);
			if(planIndexCollection.size()==1){
				return planIndexCollection.get(0);
			}else{
				return null;
			}
	}
	private CurProjectInfo selectProject(){
		
		if(diag==null){
			Frame f=(Frame)SwingUtilities.getWindowAncestor(this);
			
			diag=new KDDialog(f,"请选择要导入的工程项目",true);
			final KDTree mytree=new KDTree(treeMain.getModel(),false);
			mytree.expandAllNodes(true,
					(TreeNode) ((TreeModel) mytree.getModel()).getRoot());
			diag.getContentPane().setLayout(new java.awt.BorderLayout());
			KDTreeView treeView=new KDTreeView(mytree,"工程项目树",true);
			diag.getContentPane().add(treeView,BorderLayout.CENTER);
			KDButton btnOk=new KDButton("确定");
			btnOk.setSize(73, 50);
			btnOk.setPreferredSize(new Dimension(73,50));
			KDButton btnCancel=new KDButton("取消");
			btnCancel.setSize(73, 21);
			KDPanel p=new KDPanel();
			p.add(btnOk);
			p.add(btnCancel);
			diag.getContentPane().add(p,BorderLayout.SOUTH);
			btnOk.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					CurProjectInfo project=null;
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) mytree
					.getLastSelectedPathComponent();
					if (node == null ||node.getUserObject() == null
							|| OrgViewUtils.isTreeNodeDisable(node)) {
						project=null;
						MsgBox.showWarning(diag, "请选择工程项目");
						SysUtil.abort();
					}
					if (node.getUserObject() instanceof CurProjectInfo) {
						CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
						project=projectInfo;
						diag.setUserObject(project);
						diag.setVisible(false);
					}else{
						MsgBox.showWarning(diag, "请选择工程项目");
					}
					
					
				}
			});
			btnCancel.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					diag.setUserObject(null);
					diag.setVisible(false);
				}
			});
			diag.setSize(300,400);
			CtrlSwingUtilities.centerWindow(diag);
		
		}
		diag.setVisible(true);
		Object obj = diag.getUserObject();
		if(obj instanceof CurProjectInfo){
			return (CurProjectInfo)obj;
		}
		else{
			return null;
		}
	}
	
	private boolean checkProject(CurProjectInfo project,PlanIndexInfo info) throws EASBizException, BOSException{
		if(project==null||project.getId()==null){
			SysUtil.abort();
		}
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("curProjProductEntries.productType.id");
		selector.add("curProjProductEntries.productType.name");
		project=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(project.getId()), selector);
		CurProjProductEntriesCollection curProjProductEntries = project.getCurProjProductEntries();
		
		ProductTypeCollection planIndexProducts=new  ProductTypeCollection();
		for(int i=0;i<info.getEntrys().size();i++){
			PlanIndexEntryInfo entry = info.getEntrys().get(i);
			if(entry.isIsProduct()&&entry.getProduct()!=null){
				planIndexProducts.add(entry.getProduct());
			}
		}
		boolean isRight=false;
		String prjProd="";
		String planProd="";
		
		if(curProjProductEntries.size()==planIndexProducts.size()){
			isRight=true;
			HashSet set=new HashSet();
			for(int i=0;i<curProjProductEntries.size();i++){
				ProductTypeInfo productType = curProjProductEntries.get(i).getProductType();
				prjProd+=productType.getName()+" ";
				set.add(productType.getId().toString());
			}
			for(int k=0;k<planIndexProducts.size();k++){
				ProductTypeInfo productType = planIndexProducts.get(k);
				planProd+=productType.getName()+" ";
				if(!set.contains(productType.getId().toString())){
					isRight=false;
					break;
				}
			}
			
		}else{
			for(int i=0;i<curProjProductEntries.size();i++){
				ProductTypeInfo productType = curProjProductEntries.get(i).getProductType();
				prjProd+=productType.getName()+"、";
			}
			for(int k=0;k<planIndexProducts.size();k++){
				ProductTypeInfo productType = planIndexProducts.get(k);
				planProd+=productType.getName()+"、";
			}
		}
		if(!isRight){
			if(prjProd.length()>1){
				prjProd=prjProd.substring(0, prjProd.length()-1);
			}
			
			if(planProd.length()>1){
				planProd=planProd.substring(0, planProd.length()-1);
			}
			StringBuffer title = new StringBuffer();
			String content = "";
			content+="工程项目产品："+prjProd;
			content+="\n测算产品："+planProd;
			title.append("所选工程项目的产品与目标测算内的产品不一致。\n \n");
			title.append("是否用测算的产品覆盖工程项目产品，将工程项目产品与测算产品保持一致？\n");
			title.append("当选择是，则将工程项目产品分录修改为与测算产品一致，然后继续执行导出目标成本。\n");
			title.append("若选择否，请返回修改工程项目内的产品信息或重新选择后再执行导出操作！\n");
//			MsgBox.showDetailAndOK(this, title, content,0);
//			SysUtil.abort();
			int v = MsgBox.showConfirm3(this, title.toString(), content);
			if(v!=MsgBox.OK){
				this.abort();
			}else{
				//建发：改为提示为是同步工程项目产品
				return true;
			}
		}
		return false;
	}
	private void reverseWriteProject(MeasureCostInfo measure){
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("project.id");
		try {
			MeasureCostFactory.getRemoteInstance().reverseWriteProject(measure.getId().toString(), measure.getProject().getId().toString());
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}
	
	private void storeTemplateData(String measureId){
		if(measureId==null){
			return;
		}
		final String id=measureId;
		int v = MsgBox.showConfirm2New(this, "是否生成目标测算模板");
		final boolean isTemplate=MsgBox.isYes(v);
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
			public void run(){
				try{
					MeasureCostFactory.getRemoteInstance().storeToTemplate(id, isTemplate);
				}catch(Exception e){
					handUIExceptionAndAbort(e);
				}
			}
		});
		
			
	}
	
	
	protected String getEditUIModal() {
        String openModel = UIConfigUtility.getOpenModel();
        if(openModel != null)
        {
            return openModel;
        }
        else
        {
            return UIFactoryName.NEWTAB;
        }
	}
	
	public void actionExportIndex_actionPerformed(ActionEvent e)
			throws Exception {
		String id=getSelectedKeyValue();
		if(id==null) return;
		int value = MsgBox.showConfirm2(this, "确定要进行刷新面积指标？");
		if(value==MsgBox.YES){
			MeasureCostFactory.getRemoteInstance().exportIndex(BOSUuid.read(id));
//			MsgBox.showInfo(this, "指标导出成功，请到相应模块内查看");
//			final int v = MsgBox.showConfirm2New(this, "指标导出成功，是否进行指标刷新?");
//			if(v==MsgBox.YES){
				IRow row=KDTableUtil.getSelectedRow(getMainTable());
				String prj=(String)row.getCell("project.id").getValue();
				if(prj!=null){
					idxRefresh(prj);
				}
//			}
		}
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List list = getSelectIds();
		if (list == null)
			return;
		/**
		 * 之前"目标成本测算编辑界面"并没有提供"提交"功能,所以"保存"状态的单据可直接"审批".现提供"提交"功能，
		 * 就必须控制要"提交"之后才能审批   by Cassiel_peng 2009-8-18
		 */
		checkBillState(new String[] { /*FDCBillStateEnum.SAVED_VALUE,*/
				FDCBillStateEnum.SUBMITTED_VALUE }, "selectRightRowForAudit");
		// 加入数据互斥
		boolean hasMutex = false;
		try {

			FDCClientUtils.requestDataObjectLock(this, list, "Audit");

			MeasureCostFactory.getRemoteInstance().audit(list);
			FDCClientUtils.showOprtOK(this);
			refreshList();
		} catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		} finally {
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, list);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
		}
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List list=getSelectIds();
		if(list==null)	return;
		checkBillState(new String[]{FDCBillStateEnum.AUDITTED_VALUE}, "selectRightRowForUnAudit");
		checkVersion();
		MeasureCostFactory.getRemoteInstance().unAudit(list);
		FDCClientUtils.showOprtOK(this);
		refreshList();

	}
	
	/**
	 * 同一阶段存在修订版本不允许反审批之前版本
	 * @throws BOSException
	 * @throws Exception
	 */
	private void checkVersion() throws BOSException, Exception {
		String measureId = this.getSelectedKeyValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("createTime");
		sels.add("mainVerNo");
		sels.add("subVerNo");
		sels.add("measureStage.id");
		MeasureCostInfo measure = MeasureCostFactory.getRemoteInstance()
				.getMeasureCostInfo(new ObjectUuidPK(BOSUuid.read(measureId)),sels);
		
		List idList = ContractClientUtils.getSelectedIdValues(tblMain, "id");
		List id2List = ContractClientUtils.getSelectedIdValues(tblMain, "sourceBillId");
		idList.addAll(id2List);
		
		Set idSet = ContractClientUtils.listToSet(idList);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sourceBillId", idSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("measureStage.id",measure.getMeasureStage().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("mainVerNo",new Integer(measure.getMainVerNo()),CompareType.GREATER));
		filter.getFilterItems().add(new FilterItemInfo("mainVerNo",new Integer(measure.getMainVerNo())));
		filter.getFilterItems().add(new FilterItemInfo("subVerNo",new Integer(measure.getSubVerNo()),CompareType.GREATER));
		filter.getFilterItems().add(new FilterItemInfo("id",measureId,CompareType.NOTINCLUDE));
		filter.setMaskString("#0 and #1 and ( #2 or (#3 and #4)) and #5");
		boolean isEditVersion = getBizInterface().exists(filter);
		if(isEditVersion){
			FDCMsgBox.showWarning("该测算阶段存在修订版本，不允许反审批!");
			this.abort();
		}
	}

	private List getSelectIds() {
		List list =new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0;i<selectRows.length;i++){
			Object id = tblMain.getCell(selectRows[i],getKeyFieldName()).getValue();
			if(id==null) continue;
			list.add(id.toString());
		}
		return list;
	}
	
	public void actionVersion_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow selectRow=KDTableUtil.getSelectedRow(tblMain);
		
		Object value = selectRow.getCell("state").getValue();
		if(value instanceof BizEnumValueInfo&&!((BizEnumValueInfo)value).getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)){
			MsgBox.showError(this, "未审批单据不允许修订");
			return;
		}
		value = selectRow.getCell("isLastVersion").getValue();
		if(Boolean.FALSE.equals(value)){
			MsgBox.showError(this, "非最终版本不允许修订");
			return;
		}
		String measureId = this.getSelectedKeyValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("measureStage.number");
		sels.add("measureStage.name");
		sels.add("costEntry.costAccount.longNumber");
		sels.add("costEntry.*");
		MeasureCostInfo measure = MeasureCostFactory.getRemoteInstance()
				.getMeasureCostInfo(new ObjectUuidPK(BOSUuid.read(measureId)),sels);
				
		if(true){
			String msg=AimCostClientHelper.getRes("revise");
			MeasureStageInfo lastStageInfo = AimCostClientHelper.getLastMeasureStage(measure.getProject(),measure.isIsAimMeasure());
			MeasureStageInfo stageInfo = measure.getMeasureStage();
			if(lastStageInfo!=null&&FDCHelper.subtract(lastStageInfo.getNumber(), stageInfo.getNumber()).compareTo(FDCHelper.ZERO)==1){
				StringBuffer sb = new StringBuffer();
				sb.append("已存在 [ ").append(lastStageInfo.getNumber()).append(lastStageInfo.getName()).append(" ] 最终版本的目标成本测算,不能").append(msg).append(" [ ").append(stageInfo.getNumber())
						.append(stageInfo.getName()).append(" ] 目标成本测算。");
				FDCMsgBox.showWarning(sb.toString());
				SysUtil.abort();
			}
		}
		super.actionEdit_actionPerformed(e);
	}
	
    /**
	 * 
	 * 描述：检查单据状态
	 * 
	 * @param states
	 *            状态
	 * @param res
	 *            提示信息资源名称
	 * @throws BOSException
	 * @author:liupd 创建时间：2006-7-27
	 *               <p>
	 */
	protected void checkBillState(String[] states, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(tblMain, getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);
		Set stateSet = FDCHelper.getSetByArray(states);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		CoreBaseCollection coll = getBizInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			
//			检查单据是否在工作流中
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			
//			if (!element.getString(getBillStatePropertyName()).equals(states)) {
			if (!stateSet.contains(element.getString("state"))) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow selectRow=KDTableUtil.getSelectedRow(tblMain);
		final Object value = selectRow.getCell("state").getValue();
		if(value instanceof BizEnumValueInfo&&((BizEnumValueInfo)value).getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)){
			MsgBox.showError(this, "已审批单据不允许修改");
			return;
		}
		if(value instanceof BizEnumValueInfo&&((BizEnumValueInfo)value).getValue().equals(FDCBillStateEnum.AUDITTING_VALUE)){
			MsgBox.showWarning(this, "你所选择的单据的状态不适合修改操作");
			return;
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {	
		checkSelected();
		IRow selectRow=KDTableUtil.getSelectedRow(tblMain);
		final Object value = selectRow.getCell("state").getValue();
		if(value instanceof BizEnumValueInfo&&((BizEnumValueInfo)value).getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)){
			MsgBox.showError(this, "已审批单据不允许删除");
			return;
		}
		
		//收入测算不为审核状态的，不允许目标成本导出
		if(isIncomeJoinCost()){
			if(isMeasureIncomeAudit(this.getSelectedKeyValue())){
				FDCMsgBox.showWarning(this, "对应版本的收入测算已审批，不能进行此操作");
				SysUtil.abort();
			}else{
				int v = MsgBox.showConfirm2New(this, "对应版本的收入测算也将删除，是否确认进行删除？");
				if(v!=MsgBox.YES){
					return;
				}else{
					//super.actionRemove_actionPerformed(e);;
					//避免两次出现两次确认删除的对话框
					super.Remove();//TODO 以后会改di此方法调用服务器方法前先校验，导致不能删除（这里该校验的都校验了） by hpw
					super.refresh(e);
				}					
			}
		}else{
			super.actionRemove_actionPerformed(e);
		}		
//		super.actionRemove_actionPerformed(e);
	}
	
	private void idxRefresh(String prjId){
		final CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		if(currentFIUnit==null||currentFIUnit.getId()==null){
			MsgBox.showError(this, "当前财务组织为空,不能进行此操作,请切换到财务实体！");
			SysUtil.abort();
		}
		try{
			boolean isFinacial=FDCUtils.IsFinacial(null, currentFIUnit.getId().toString());
			if(isFinacial){
				FilterInfo filter=new FilterInfo();
				filter.appendFilterItem("isCostEnd", Boolean.TRUE);
				filter.appendFilterItem("isFinacialEnd", Boolean.FALSE);
				filter.appendFilterItem("project.id", prjId);
				boolean exits=ProjectPeriodStatusFactory.getRemoteInstance().exists(filter);
				if(exits){
					MsgBox.showError(this, "当前项目成本已经月结,财务成本还没有月结不能进行指标刷新!");
					SysUtil.abort();
				}
				
			}
		}catch(BOSException e){
			handUIException(e);
		}catch(EASBizException e){
			handUIException(e);
		}
		
		Map param=new HashMap();
		List list=new ArrayList();
		param.put("refreshSrcList", list);
		Map dataMap=new HashMap();
		dataMap.put("projId", prjId);
		Set changeApportions=new HashSet();
		changeApportions.add(ApportionTypeInfo.buildAreaType);
		changeApportions.add(ApportionTypeInfo.placeAreaType);
		changeApportions.add(ApportionTypeInfo.sellAreaType);
		dataMap.put("apportionsId", changeApportions);
		dataMap.put("includeAllProduct", Boolean.TRUE);
		list.add(dataMap);
		param.put("companyId", currentFIUnit.getId().toString());
		param.put("projId", prjId);
		ProjectIndexDataUtils.idxRefresh(this, param);
	}
	
	public void actionImportData_actionPerformed(final ActionEvent e) throws Exception {
		List verNumList= getVerNumber();
		DatataskParameter param = new DatataskParameter();
		ArrayList paramList = new ArrayList();
    	paramList.add(param);
    	
    	orgID = getSelectOrgId() ;
    	
    	param.getContextParam().put("orgUnitId", getSelectOrgId());
    	param.getContextParam().put("ListUI", this);
    	param.getContextParam().put("VerNumber", verNumList);
    	param.getContextParam().put("ActionEvent", e);
    	param.getContextParam().put("title", "目标测算导入");
    	paramList.add(param);
    	ImportDataDlg dlg=new ImportDataDlg(FDCClientHelper.getFrameAncestor(this),paramList,DatataskMode.ImpMode);
    	dlg.show();
	}
	/**
	 * 通过Sql语句获取当前组织下的所有版本号
	 */
	private List getVerNumber() throws EASBizException, BOSException
	{
		String selectOrgId = getSelectOrgId() ;
		List verNumber= new ArrayList();
		boolean isAimCost = true ;
		
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select REPLACE(FVersionNumber, '!', '.') as FVersionNumber from T_AIM_MeasureCost where FOrgUnitID = ? ");
		builder.addParam(selectOrgId);
		builder.appendSql(" and FIsAimMeasure = ? ");
		builder.addParam(Boolean.valueOf(isAimCost)) ;
		try
		{
			IRowSet rowSet=builder.executeQuery();
			while(rowSet.next())
			{
				verNumber.add(rowSet.getString("FVersionNumber"));
			}
		} catch (BOSException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return verNumber ;
	}
	public static String getOrgId()
	{
		return orgID ;
	}
	
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow selectRow=KDTableUtil.getSelectedRow(tblMain);
		final String id = selectRow.getCell("id").getValue().toString();
		MeasureCostInfo info = MeasureCostFactory.getRemoteInstance().getMeasureCostInfo(new ObjectStringPK(id));
		IObjectPK pk = LogUtil.beginLog(null, "1",info.getBOSType(), null, "false", "AimMeasureCost_Attachment" );
		super.actionAttachment_actionPerformed(e);
		selectRow.getCell("attachment").setValue(Boolean.valueOf(MeasureCostAttachmentUtil.isHasAttachement(id)));
		LogUtil.afterLog(null, pk );
	}
	//同一实体不同界面权限 问题 by hpw 2010.11.18
	protected void handlePermissionForItemAction(ItemAction action) {
//		super.handlePermissionForItemAction(action);
		try {
			PermissionFactory.getRemoteInstance().checkFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
					new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()), new MetaDataPK(AimMeasureCostListUI.class.getName()),
					new MetaDataPK("ActionOnLoad"));
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = getProjSelectedTreeNode();
		FDCClientUtils.checkSelectProj(this, node);
		FDCClientUtils.checkProjWithCostOrg(this, node);
		CurProjectInfo curProj = (CurProjectInfo) node
		.getUserObject();
		AimCostClientHelper.isCanAddNew(this,curProj);
		super.actionAddNew_actionPerformed(e);
	}
	
	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
	}
	
}