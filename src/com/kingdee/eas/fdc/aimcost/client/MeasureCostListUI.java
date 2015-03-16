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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostException;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCProgressDialog;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCProgressMonitor;
import com.kingdee.eas.fdc.basedata.client.IFDCRunnableWithProgress;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fm.common.client.FMClientHelper;
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

/**
 * output class name
 */
public class MeasureCostListUI extends AbstractMeasureCostListUI {
	
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.aimcost.client.MeasureCostListUI");
	private KDDialog diag=null;
	private static String orgID = null ;
	//当进入可研序事簿下则获取当前的当前目录下的版本号和公司的名称
	public void onLoad() throws Exception {
		tblMain.addKDTDataFillListener(new KDTDataFillListener(){
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_afterDataFile(e);
				
			}
		});
/*		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("alter table T_ORG_Structure add fsortcode varchar(80)");
		builder.execute();*/
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.initTree();
		super.onLoad();
		this.treeMain.setSelectionRow(0);
		FMClientHelper.addSqlMenu(this, this.menuEdit);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.menuBiz.setVisible(true);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		String formatString = "yyyy-MM-dd";
		tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat(
				formatString);
		tblMain.getColumn("description").getStyleAttributes().setHided(true);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
//		tHelper = new TablePreferencesHelper(this);
//		initUserConfig();
		tHelper = new TablePreferencesHelper(this);
		
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

	protected void initTree() throws Exception {
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
//		treeBuilder.setDevPrjFilter(!isContainDevProject());
		if(isContainDevProject()){
			treeBuilder.setDevPrjFilter(false);
		}else{
			treeBuilder.setDevPrjFilter(true,!isContainDevProject());
		}
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	/**
	 * output class constructor
	 */
	public MeasureCostListUI() throws Exception {
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
		mainQuery.getSorter().clear();
		mainQuery.getSorter().add(new SorterItemInfo("mainVerNo"));
		mainQuery.getSorter().add(new SorterItemInfo("subVerNo"));
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
		Object obj=getSelectObj();
		if(!isSelectLeafPrj(obj)){
			//仅允许明细工程项目新增
			actionAddNew.setEnabled(false);
		}
		if(isSelectParentPrj(obj)){
			actionMeasureCollect.setEnabled(true);
		}else{
			actionMeasureCollect.setEnabled(false);
		}	
	}

	protected FilterInfo getMainFilter() throws Exception {
		String projectId = this.getSelectProjectId();
		FilterInfo filter = new FilterInfo();
		if(projectId!=null){
			FDCSQLBuilder builder=new FDCSQLBuilder();
			if(isContainDevProject()){
				builder.appendSql("select b.fid as fid from T_FDC_CurProject a ,T_FDC_CurProject b where a.fid=? and charindex(a.flongnumber||'!',b.flongnumber)=1");
			}else{
				builder.appendSql("select b.fid as fid from T_FDC_CurProject a ,T_FDC_CurProject b where a.fid=? and charindex(a.flongnumber||'!',b.flongnumber)=1 and b.fisdevprj = 0");
			}
			
			builder.addParam(projectId);
			IRowSet rowSet=builder.executeQuery();
			Set set=new HashSet();
			while(rowSet.next()){
				set.add(rowSet.getString("fid"));
			}
			set.add(projectId);
			filter.getFilterItems()
				.add(new FilterItemInfo("project.id", set,CompareType.INCLUDE));
		}else{
			String orgId=getSelectOrgId();
			if(orgId!=null){
				filter.appendFilterItem("orgUnit.id", orgId);
			}else{
//				filter.appendFilterItem("orgUnit", orgId);
			}
		}
		filter.getFilterItems().add(
				new FilterItemInfo("isAimMeasure", Boolean.FALSE));
		if(!isContainDevProject()){
			filter.getFilterItems().add(
					new FilterItemInfo("project.isDevPrj", Boolean.FALSE));
		}
		if(SysContext.getSysContext().getCurrentCtrlUnit()!=null){
			//CU隔离 
			String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("CU.id",cuId));
		}
		return filter;
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
			//PBG031628 by sxhong,hpw 2009-08-28
/*			EntityViewInfo view=new EntityViewInfo();
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
		}*/
			if(curPrj.getFullOrgUnit()==null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", curPrj.getId().toString()));
				view.getSelector().add("fullOrgUnit.id");
				try {
					return CurProjectFactory.getRemoteInstance().getCurProjectCollection(view).get(0).getFullOrgUnit().getId().toString();
				} catch (BOSException e) {
					this.handUIException(e);
				}
				
			}else{
				return curPrj.getFullOrgUnit().getId().toString();
			}
		}
		if(org!=null){
			return org.getId().toString();
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
        if (action.equals(actionAddNew)||action.equals(actionImportData))
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
        uiContext.put("isAimMeasure", Boolean.FALSE);
		super.prepareUIContext(uiContext, e);
	}

	protected void initWorkButton() {
		super.initWorkButton();
		actionLastVersion.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_output"));
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
		actionLastVersion.setEnabled(true);
		
		menuItemVersion.setMnemonic('V');
		menuItemVersion.setText(menuItemVersion.getText()+"(V)");
		menuItemVersion.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
		
		menuItemExportIndex.setMnemonic('E');
		menuItemExportIndex.setText(menuItemExportIndex.getText()+"(E)");
		menuItemExportIndex.setAccelerator(KeyStroke.getKeyStroke("ctrl shift E"));
		
		menuLastVersion.setMnemonic('L');
		menuLastVersion.setText(menuLastVersion.getText()+"(L)");
		menuLastVersion.setAccelerator(KeyStroke.getKeyStroke("ctrl shift L"));
		menuItemImportData.setEnabled(true);
		menuItemImportData.setVisible(true);
		
		menuItemMeasureCollect.setMnemonic('M');
		menuItemMeasureCollect.setText(menuItemMeasureCollect.getText()+"(M)");
		menuItemMeasureCollect.setAccelerator(KeyStroke.getKeyStroke("ctrl shift M"));
		actionMeasureCollect.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_view"));
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
			
			//set some properties of the OK_button
			KDWorkButton btnOk=new KDWorkButton("确定");
			Dimension dim =  new Dimension(70, 26);
			btnOk.setPreferredSize(dim);
			btnOk.setAlignmentY((float) 0.5);
			btnOk.setFactType(KDWorkButton.BLUE_FACE);
		    btnOk.setBorderPainted(true);
		    btnOk.setFocusable(true);
		    btnOk.setDefaultCapable(true);
		    btnOk.setOpaque(true);
		 	
            //set some properties of the cancel_button 
			KDWorkButton btnCancel=new KDWorkButton("取消");
			btnCancel.setPreferredSize(dim);
			btnCancel.setFactType(KDWorkButton.BLUE_FACE);
			btnCancel.setAlignmentY((float) 0.5);
			btnCancel.setBorderPainted(true);
			btnCancel.setFocusable(true);
			btnCancel.setDefaultCapable(true);
			btnCancel.setOpaque(true);
			
			KDPanel p=new KDPanel();
			p.setBackground(new Color(236,236,232));
			p.setForeground(new Color(0,0,0));
			p.add(btnOk, BorderLayout.EAST);
			p.add(btnCancel,BorderLayout.WEST);
			p.setOpaque(true);
			
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
		String id = getSelectedKeyValue();
		if (id == null)
			return;
		int value = MsgBox.showConfirm2(this,
				"确定要将本测算的规划指标导出？");
		if (value == MsgBox.YES) {
			MeasureCostFactory.getRemoteInstance()
					.exportIndex(BOSUuid.read(id));
			MsgBox.showInfo(this, "指标导出成功，请到相应模块内查看");
		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List list = getSelectIds();
		if (list == null)
			return;
		/**
		 * 之前"可研成本测算编辑界面"并没有提供"提交"功能,所以"保存"状态的单据可直接"审批".现提供"提交"功能，
		 * 就必须控制要"提交"之后才能审批   by Cassiel_peng 2009-8-18
		 */
		checkBillState(new String[] {/* FDCBillStateEnum.SAVED_VALUE,*/
				FDCBillStateEnum.SUBMITTED_VALUE }, "selectRightRowForAudit");
		
		boolean hasMutex = false;
		try{
			
			FDCClientUtils.requestDataObjectLock(this, list, "Audit");
			MeasureCostFactory.getRemoteInstance().audit(list);
			FDCClientUtils.showOprtOK(this);
			refreshList();
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
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
		List list = getSelectIds();
		if (list == null)
			return;
		checkBillState(new String[] { FDCBillStateEnum.AUDITTED_VALUE },
				"selectRightRowForUnAudit");
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
		List list = new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for (int i = 0; i < selectRows.length; i++) {
			Object id = tblMain.getCell(selectRows[i], getKeyFieldName()).getValue();
			if (id == null)
				continue;
			list.add(id.toString());
		}
		return list;
	}

	public void actionVersion_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow selectRow=KDTableUtil.getSelectedRow(tblMain);
		
		final Object value = selectRow.getCell("state").getValue();
		if(value instanceof BizEnumValueInfo&&!((BizEnumValueInfo)value).getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)){
			MsgBox.showError(this, "未审批单据不允许修订");
			return;
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
		List idList = ContractClientUtils.getSelectedIdValues(tblMain,
				getKeyFieldName());

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

			// 检查单据是否在工作流中
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());

			// if
			// (!element.getString(getBillStatePropertyName()).equals(states)) {
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

	public void actionLastVersion_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		String measureId = this.getSelectedKeyValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("costEntry.costAccount.longNumber");
		sels.add("costEntry.*");
		MeasureCostInfo measure = MeasureCostFactory.getRemoteInstance()
				.getMeasureCostInfo(new ObjectUuidPK(BOSUuid.read(measureId)),sels);
		//此处对单据状态的判断难道不会有问题吗？？？   为什么不把"审批中"状态算进来？by Cassiel_peng
		if(measure.getState()!=null&&measure.getState()!=FDCBillStateEnum.AUDITTED){
			FDCMsgBox.showWarning(this, "测算未审批不能进行此操作");
			SysUtil.abort();
		}
		if(measure.isIsLastVersion()){
			int v = MsgBox.showConfirm2New(this, "该次测算已经是最终版本，是否重新选择工程项目？");
			if(v!=MsgBox.YES){
				return;
			}
		}
		CurProjectInfo oldPrj=measure.getProject();
		if(measure.getProject()!=null){
			int value = MsgBox.showConfirm2New(this, "本次测算已经存在工程项目,是否重新选择？");
			if(value==MsgBox.YES){
				measure.setProject(selectProject());
			}
		}else{
			measure.setProject(selectProject());
		}
		PlanIndexInfo planIndexInfo=getPlanIndex(measure.getId().toString());
		checkProject(measure.getProject(),planIndexInfo);
		if(measure.getProject()==null||measure.getProject().getId()==null){
			SysUtil.abort();
		}
		measure.setIsLastVersion(true);
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("update T_AIM_MeasureCost set fislastversion=0 where fisAimMeasure=0 and FProjectID=?");
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
	
	private void reverseWriteProject(MeasureCostInfo measure){
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
		int v = MsgBox.showConfirm2New(this, "是否生成可研测算模板");
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
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow selectRow=KDTableUtil.getSelectedRow(tblMain);
		final Object value = selectRow.getCell("state").getValue();
		if(value instanceof BizEnumValueInfo&&((BizEnumValueInfo)value).getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)){
			MsgBox.showError(this, "已审批单据不允许删除");
			return;
		}
		super.actionRemove_actionPerformed(e);
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
	
	private void checkProject(CurProjectInfo project,PlanIndexInfo info) throws EASBizException, BOSException{
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
			String title = "工程项目产品类型数据不一致";
			String content = "所选工程项目的产品与目标测算内的产品不一致\n";
			content+="工程项目产品:"+prjProd;
			content+="\n测算产品:"+planProd;
			content+="\n\n请修改工程项目内的产品信息或重新选择";
			
			MsgBox.showDetailAndOK(this, title, content,0);
			SysUtil.abort();
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
	public void actionImportData_actionPerformed(final ActionEvent e) throws Exception {
		List verNumList= getVerNumber();
		DatataskParameter param = new DatataskParameter();
		
		orgID = getSelectOrgId() ;
		
		ArrayList paramList = new ArrayList();
    	paramList.add(param);
    	param.getContextParam().put("orgUnitId", getSelectOrgId());
    	param.getContextParam().put("ListUI", this);
    	param.getContextParam().put("VerNumber", verNumList);
    	param.getContextParam().put("ActionEvent", e);
    	param.getContextParam().put("title", "可研测算导入");
    	paramList.add(param);
    	ImportDataDlg dlg=new ImportDataDlg(FDCClientHelper.getFrameAncestor(this),paramList,DatataskMode.ImpMode);
    	dlg.show();
		if(true){
			return;
		}
		KDFileChooser chooser=new KDFileChooser();
		chooser.setFileFilter(new  SimpleFileFilter(new String[] { "xls" }));
		int v=FDCMsgBox.showConfirm2New(this, "是否导出导入模板？选择是导出导入模板，选择否继续导入");
		if(v==FDCMsgBox.YES){
			//导出模板
			v=chooser.showSaveDialog(this);
				if(v==KDFileChooser.APPROVE_OPTION){
				File file=chooser.getSelectedFile();
				String fileName=file.getAbsolutePath();
				if(!fileName.endsWith(".xls")&&!fileName.endsWith(".XLS")){
					fileName=fileName+".xls";
				}
//				InputStream is=Class.forName("com.kingdee.eas.fdc.aimcost.client.MeasureCostListUI").getResourceAsStream("measureImporterTemplate.xls");
//				try{
//					FileOutputStream os=new FileOutputStream(fileName);
//					byte data[]=new byte[1024];
//					while(is!=null&&is.available()>0){
//						is.read(data);
//						os.write(data);
//					}
//					os.close();
//					is.close();
//					FDCMsgBox.showInfo(this,"保存成功！");
//				}catch(Exception e1){
//					FDCMsgBox.showDetailAndOK(this,"所选择的文件无法打开,请确认文件是否存在或者被使用",e1.getMessage(),0);
//				}
				
				/**
				 * 因分包问题，从客房端无法读取模板文件,改为从服务器端读取
				 * modify by pengwei_hou 2008-10-31 18:00
				 */
				
				logger.info("hpwURL: " + "client object!");
				Object object = MeasureCostFactory.getRemoteInstance().getTemplateDataStream();
				logger.info("hpwURL: MeasureCostFactory.getRemoteInstance().toString():" + MeasureCostFactory.getRemoteInstance().toString());
				
				logger.info("hpwURL: object==" + (object == null));
				logger.info("hpwURL: " + "client app write data before!");
				if(object instanceof byte[]){
					FileOutputStream os = new FileOutputStream(fileName);
					BufferedOutputStream bw = new BufferedOutputStream(os);
					byte[] data = (byte[])object;
					logger.info("hpwURL: " + "client app write data ready!");
					try{
						bw.write(data, 0, data.length);
						logger.info("hpwURL: " + "client app write data complete!");
					} catch (Exception e2){
						FDCMsgBox.showDetailAndOK(this, "所选择文件无法打开,请确认文件是否存在或被使用!", e2.getMessage(), 0);
						throw new AimCostException(AimCostException.FILEREADENRROR);
					} finally{
						bw.close();
						os.close();
						logger.info("hpwURL: " + "client app close stream!");
					}
				}
				
			}
			return;
		}
		v = chooser.showOpenDialog(this);
		if(v==KDFileChooser.APPROVE_OPTION){
			final File file=chooser.getSelectedFile();
			FDCProgressDialog dialog = FDCProgressDialog.createProgressDialog(this, true);
			dialog.run(false, true,new IFDCRunnableWithProgress(){
				public void run(IFDCProgressMonitor monitor) {
					try{
						monitor.beginTask("测算导入", -1);
						monitor.setTaskName("正在进行测算导入，请稍候 ...");
						importExcel(file, e);
						
					}finally{
						monitor.done();
					}
				}
			});
		}
	}
	private  void importExcel(File file,ActionEvent e){
		MeasureImporter importer=new MeasureImporter(file,getSelectOrgId());
		try {
			MeasureCostInfo measure = importer.transToMeasure(getVerNumber());
	        UIContext uiContext = new UIContext(this);
	        uiContext.put("MeasureEditData",measure);
	        prepareUIContext(uiContext, e);
			IUIWindow  uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,
                    OprtState.ADDNEW);
			uiWindow.show();
		} catch (Exception e1) {
			handUIException(e1);
		}
	}
	/**
	 * 通过Sql语句获取当前组织下的所有版本号
	 */
	private List getVerNumber() throws EASBizException, BOSException
	{
		String selectOrgId = getSelectOrgId() ;
		List verNumber= new ArrayList();
		boolean isAimCost = false ;
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
	/**
	 * 
	 */
	public static String getOrgID()
	{
		return orgID ;
	}
	
	/**
	 * 得到当前选择的对象工程项目,组织ID,或Null
	 * @return 当前选择的对象工程项目,组织ID,或Null
	 */
	protected Object getSelectObj() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {
				return null;
			}
			FullOrgUnitInfo info = oui.getUnit();
			return info;
		}
		return null;
	}
	
	protected CurProjectInfo getSelectProject() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		return null;
	}
	
//	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
//			EntityViewInfo viewInfo) {
//		// TODO Auto-generated method stub
//	    if(!isContainDevProject()){
//	    	FilterInfo filter = null;
//			try {
//				filter = this.getMainFilter();
//				FilterInfo fi = new FilterInfo();
//				fi.getFilterItems().add((new FilterItemInfo("project.isDevPrj",Boolean.FALSE)));
//				filter.mergeFilter(fi, "and");
//				viewInfo.setFilter(filter);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    	
//	    }
//		return super.getQueryExecutor(queryPK, viewInfo);
//	}
	/**
	 * measureCostCotainDevProject
	 * @return
	 */
	public boolean isContainDevProject(){
		boolean isContainDevPrj = true;
		if(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo() != null){
			HashMap param = null;
			try {
				 param = FDCUtils.getDefaultFDCParam(null, null);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//FDC_PARAM_MEASURECOSTISCONTAINDEVPROJECT
			if(param.get(FDCConstants.FDC_PARAM_MEASURECOSTISCONTAINDEVPROJECT) != null){
				 isContainDevPrj = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_MEASURECOSTISCONTAINDEVPROJECT).toString()).booleanValue();
			}
		}
		
		return isContainDevPrj;
	}
	/**
	 * 
	 * @return
	 */
	protected boolean isSelectLeafPrj(Object obj){
		if(obj!=null && obj instanceof CurProjectInfo &&!((CurProjectInfo)obj).isIsLeaf()){
			return false;
		}
		return true;
	}
	/**
	 * 与需求确定，处理工程项目所有下级,及下级的下级
	 * @author hpw date:2009-9-4
	 * @param obj
	 * @return
	 * @throws BOSException
	 */
	protected boolean isSelectParentPrj(Object obj) throws BOSException{
		if(obj!=null && obj instanceof CurProjectInfo){
			Map childInfos = ProjectHelper.getCurProjChildInfos(null, ((CurProjectInfo)obj).getId().toString());
			if(childInfos.size()>0){
				return true;
			}
		}
		return false;
	}
	public void actionMeasureCollect_actionPerformed(ActionEvent e)
			throws Exception {
		Object obj = getSelectObj();
		if (obj != null && obj instanceof CurProjectInfo
				&& !((CurProjectInfo) obj).isIsLeaf()) {
			CurProjectInfo curProjectInfo = (CurProjectInfo) obj;
			if (curProjectInfo.getParent() != null) {
				return;
			}
			UIContext context=new UIContext(ui);
			// 必须为顶级工程项目O
			context.put("curProjectInfo", curProjectInfo);
			context.put(UIContext.ID, curProjectInfo.getId().toString());
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
					.create(FDCMeasureRptUI.class.getName(), context, null,
							"VIEW");
			uiWindow.show();
		}
	}
	
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow selectRow=KDTableUtil.getSelectedRow(tblMain);
		final String id = selectRow.getCell("id").getValue().toString();
		super.actionAttachment_actionPerformed(e);
		selectRow.getCell("attachment").setValue(Boolean.valueOf(MeasureCostAttachmentUtil.isHasAttachement(id)));
	}
	//同一实体不同界面权限 问题 by hpw 2010.11.18
	protected void handlePermissionForItemAction(ItemAction action) {
//		super.handlePermissionForItemAction(action);
		try {
			PermissionFactory.getRemoteInstance().checkFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
					new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()), new MetaDataPK(MeasureCostListUI.class.getName()),
					new MetaDataPK("ActionOnLoad"));
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

	}	
}