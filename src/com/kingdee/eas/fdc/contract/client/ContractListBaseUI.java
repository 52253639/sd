/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.client.UIActionPostman;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IIDList;
import com.kingdee.eas.framework.client.RealModeIDList;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * 
 * 描述:合同列表界面基类
 * 
 * @author liupd date:2006-8-1
 *         <p>
 * @version EAS5.1.3
 */
public abstract class ContractListBaseUI extends AbstractContractListBaseUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractListBaseUI.class);
	
	//获取有权限的组织
	protected Set authorizedOrgs = null;

	private CommonQueryDialog commonQueryDialog = null;
	private ContractBillFilterUI filterUI = null;
	/**
	 * output class constructor
	 */
	public ContractListBaseUI() throws Exception {
		super();
	}

	
	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			KDTSelectEvent e)
			throws Exception {
		if(!isHasBillTable()) {
			super.tblMain_tableSelectChanged(e);
			return;
		}
		if(e.getSelectBlock()==null) return;
		getBillListTable().removeRows(false);
		
		EntityViewInfo view = genBillQueryView(e);
		if(!displayBillByContract(e, view) ){
			displayBillByContract(view);
		}
		if(getBillListTable()!=null&&getBillListTable().getRowCount()>0){
			getBillListTable().getSelectManager().select(0,0);
		}
		super.tblMain_tableSelectChanged(e);
	}

	/**
	 * 为双分录的序时薄的下面分录增加鼠标事件，在onLoad()中调用
	 * @author sxhong  		Date 2006-10-20
	 * @param e
	 * @throws Exception
	 */
	protected void tblBill_tableClicked(KDTMouseEvent e) throws Exception
	{
    	/*
    	 * 表头暂不排序，不然会引起对tblMain的排序，与逻辑不符合
    	 */
		if(e.getType()==KDTStyleConstants.HEAD_ROW) {
			return;
		}
		//TODO 以后修改tblMain_tableClicked 方法的调用 by sxhong
		if(tblMain.getColumn(e.getColIndex())==null){
			return;
		}
		super.tblMain_tableClicked(e);
	}

	/**
	 * 有上下分录序时薄在点击上面合同时打开合同的查看界面
	 * @author sxhong  		Date 2006-10-19
	 * @param e
	 * @throws Exception
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent)
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
		if(isHasBillTable()){
			/*
			 * 表头排序
			 */
			if(e.getType()==KDTStyleConstants.HEAD_ROW){
				super.tblMain_tableClicked(e);
			}else 
			//双击查看合同/无文本合同
			if(e.getClickCount()==2){
				checkSelected(getMainTable());
				this.setCursorOfWair();
		        UIContext uiContext = new UIContext(this);
		        String contractId = getSelectedKeyValue(getMainTable());
		        if(contractId==null) return;
		        
				uiContext.put(UIContext.ID, contractId);
				uiContext.put("source", "listBase");	//用于与工作流过来的区分
		        IUIWindow contractUiWindow = null;
		        String editUIName=null;
		        BOSObjectType contractType = BOSUuid.read(contractId).getType();
		        if(contractType.equals(new ContractBillInfo().getBOSType())){
		        	editUIName=com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class.getName();
		        }else if(contractType.equals(new ContractWithoutTextInfo().getBOSType())){
		        	editUIName=com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class.getName();
		        }else{
		        	return;
		        }
		        
		        if (SwingUtilities.getWindowAncestor(this) != null
		                && SwingUtilities.getWindowAncestor(this) instanceof JDialog) {
		        	contractUiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
		                    editUIName, uiContext, null, "FINDVIEW");

		        } else {
		            // 创建UI对象并显示
		        	contractUiWindow = UIFactory.createUIFactory(getEditUIModal()).create(
		            		editUIName, uiContext, null, "FINDVIEW");
		        }
		        if(contractUiWindow!=null) {
//		        	EditUI ui=(EditUI)contractUiWindow.getUIObject();
//		        	ui.
		        	contractUiWindow.show();
		        }
				this.setCursorOfDefault();
			}
		}else{
			super.tblMain_tableClicked(e);
		}
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
		checkBeforeEdit();
		
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		checkBeforeRemove();
		
		super.actionRemove_actionPerformed(e);
	}


	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if(isHasBillTable()){
			preparePrintPage(getBillListTable());
	        getBillListTable().getPrintManager().print();
		}else{
			super.actionPrint_actionPerformed(e);
		}
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		if(isHasBillTable()){
			preparePrintPage(getBillListTable());
	        getBillListTable().getPrintManager().printPreview();
		}else{
			super.actionPrintPreview_actionPerformed(e);
		}
	}


	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionView_actionPerformed(e);
	}
	
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
    	if (this.actionQuery.isVisible()) {
			getFilterUI();
			super.actionQuery_actionPerformed(e);
		}
    }
    
	/**
	 * output getEditUIName method
	 */
	protected abstract String getEditUIName();

	/**
	 * output getBizInterface method
	 */
	protected abstract com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception;

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.ContractBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillInfo();
		return objectValue;
	}

	
	/**
	 * private方法修改为public方法，子类可以修改工程项目树的构建方式
	 * 原因：进度管理中工程项目的构建取当前组织上级财务组织下的所有工程项目，默认取的当前组织下的工程项目
	 * @throws Exception
	 */
	public void buildProjectTree() throws Exception {

		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

		projectTreeBuilder.build(this, treeProject, actionOnLoad);
		
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

	private TreeSelectionListener treeSelectionListener;

	private ITreeBuilder treeBuilder;

	/**
	 * 构造合同类型树
	 */
	protected void buildContractTypeTree() throws Exception {
		KDTree treeMain = getContractTypeTree();
		TreeSelectionListener[] listeners = treeMain
				.getTreeSelectionListeners();
		if (listeners.length > 0) {
			treeSelectionListener = listeners[0];
			treeMain.removeTreeSelectionListener(treeSelectionListener);
		}

		treeBuilder = TreeBuilderFactory.createTreeBuilder(getLNTreeNodeCtrl(),
				getTreeInitialLevel(), getTreeExpandLevel(), this
						.getDefaultFilterForTree());

		if (getRootName() != null) {
			KDTreeNode rootNode = new KDTreeNode(getRootObject());
			((DefaultTreeModel) treeMain.getModel()).setRoot(rootNode);
			
		} else {
			((DefaultTreeModel) treeMain.getModel()).setRoot(null);
		}
		
		treeBuilder.buildTree(treeMain);
		if(containConWithoutTxt()){
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeMain.getModel().getRoot();
			KDTreeNode node = new KDTreeNode("allContract");
			node.setUserObject("allContract");
			node.setText(getRootName());
			root.setText("合同");
			node.add(root);
			((DefaultTreeModel) treeMain.getModel()).setRoot(node);
			
		}
		
		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);

	}
	
	/**
	 * 合同类型树是否有无文本合同
	 * @return
	 */
	protected boolean containConWithoutTxt(){
		return false;
	}
	/**
	 * 初始化ILNTreeNodeCtrl，一般不用重载， 在根据表内数据构建特定树时，（例如菜单树需要根据权限进行过滤）
	 * 可能需要编写特定的ILNTreeNodeCtrl的实现类（实现类的编写可以参照DefaultLNTreeNodeCtrl)，
	 * 这时候就需要重载（重载为示例化这个特定类型）。
	 */
	protected ILNTreeNodeCtrl getLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getTreeInterface());
	}

	private ITreeBase getTreeInterface() {

		ITreeBase treeBase = null;
		try {
			treeBase = ContractTypeFactory.getRemoteInstance();
		} catch (BOSException e) {
			abort(e);
		}

		return treeBase;
	}

	private KDTree getContractTypeTree() {
		return treeContractType;
	}

	protected int getTreeInitialLevel() {
		return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}

	protected int getTreeExpandLevel() {
		return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
	}

	// 树形的CU过滤处理。
	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		return filter;
	}

	/**
	 * 定义根节点显示名称，默认返回null，即没有根结点（所有结点来自于数据） 继承类可以重载，添加显示结点
	 */
	protected String getRootName() {
		return ContractClientUtils.getRes("allContractType");
	}

	protected Object getRootObject() {
		return getRootName();
	}

	
	public void onLoad() throws Exception {
		
		super.onLoad();
		//工程项目树的构建
		buildProjectTree();
		//合同类型书的构建
		buildContractTypeTree();	
		
		
	   //增加KDtreeView
		KDTreeView treeView=new KDTreeView();
		treeView.setTree(treeProject);
		treeView.setShowButton(false);
		treeView.setTitle("工程项目");
        kDSplitPane1.add(treeView, "left");
		treeView.setShowControlPanel(true);
		
		
		treeProject.setShowsRootHandles(true);
		updateButtonStatus();
		
		// 去掉查询功能
		actionQuery.setEnabled(false);
		actionQuery.setVisible(false);
		// 去掉多级审批
//		actionMultiapprove.setEnabled(false);
//		actionMultiapprove.setVisible(false);
		// 去掉下一步处理人
//		actionNextPerson.setEnabled(false);
//		actionNextPerson.setVisible(false);
		
		actionAuditResult.setEnabled(true);
//		actionAuditResult.setVisible(false);

//		treeProject.expandAllNodes(true, (TreeNode) treeProject.getModel().getRoot());
		
		TreeSelectionListener projTreeSelectionListener = null; 
		TreeSelectionListener[] listeners = treeProject
		.getTreeSelectionListeners();
		if (listeners.length > 0) {
			projTreeSelectionListener = listeners[0];
			treeProject.removeTreeSelectionListener(projTreeSelectionListener);
		}
		
		TreeSelectionListener conTypeTreeSelectionListener = null;
			TreeSelectionListener[] listeners2 = treeContractType
			.getTreeSelectionListeners();
			if (listeners2.length > 0) {
				conTypeTreeSelectionListener = listeners2[0];
				treeContractType.removeTreeSelectionListener(conTypeTreeSelectionListener);
		}
		
		/*
		 * 设置选中根结点
		 */
		treeProject.setSelectionRow(0);
		treeProject.expandRow(0);
		if(containConWithoutTxt()){
			treeContractType.setSelectionRow(1);
		}else{
			treeContractType.setSelectionRow(0);
		}
		treeContractType.expandRow(1);
		treeProject.addTreeSelectionListener(projTreeSelectionListener);
		treeContractType.addTreeSelectionListener(conTypeTreeSelectionListener);
		
		treeSelectChange();
		
		/*
		 * 为下面的分录注册点击事件
		 */
		if(isHasBillTable()){
			getBillListTable().addKDTMouseListener(
					new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
	            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
	                try {
	                    tblBill_tableClicked(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                }
	            }
	        });
			getBillListTable().setColumnMoveable(true);
		}
		
		String key = "F8";
		JComponent c = this.pnlSplit;
		InputMap inputMap = c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		if(inputMap.get(KeyStroke.getKeyStroke(key))!=null){
			if(c.getActionMap().getParent()!=null)
				c.getActionMap().getParent().remove(KeyStroke.getKeyStroke(key));
			c.getActionMap().remove(inputMap.get(KeyStroke.getKeyStroke(key)));
			inputMap.remove(KeyStroke.getKeyStroke(key));
			if(inputMap.getParent()!=null)
				inputMap.getParent().remove(KeyStroke.getKeyStroke(key));
		}
		//设置可以保存样式
		tHelper = new TablePreferencesHelper(this);
//		btnSetRespite.setIcon();
	}
	/**
	 * 
	 * 描述：初始化表格
	 * @author:liupd
	 * 创建时间：2006-8-3 <p>
	 */
	protected void initTable() {
		
		freezeMainTableColumn();
		if(getBillListTable()!=null){
			freezeBillTableColumn();
			getBillListTable().getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
			if(getBillListTable().getColumn("createTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createTime");
			}
			if(getBillListTable().getColumn("auditTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "auditTime");
			}
			if(getBillListTable().getColumn("createDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createDate");
			}
			
			//revDate
			if(getBillListTable().getColumn("revDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "revDate");
			}
			
			if(getBillListTable().getColumn("amount")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "amount");
			}
			
			if(getBillListTable().getColumn("originalAmount")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "originalAmount");
			}
			
			
			if(getBillListTable().getColumn("totalSettlePrice")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "totalSettlePrice");
			}
			
			
			if(getBillListTable().getColumn("totalOriginalAmount")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "totalOriginalAmount");
			}
			
			if(getBillListTable().getColumn("actPaidAmount")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "actPaidAmount");
			}		
			
			if(getBillListTable().getColumn("oldOriginalAmount")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "oldOriginalAmount");
			}
			
			if(getBillListTable().getColumn("oldAmount")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "oldAmount");
			}
		}
		getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		if(getMainTable().getColumn("amount")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "amount");
		}
		if(getMainTable().getColumn("originalAmount")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "originalAmount");
		}
		
		if(getMainTable().getColumn("signDate")!=null){
			FDCHelper.formatTableDate(getMainTable(), "signDate");
		}
		
		super.initTable();
	}
	
	
	protected void refresh(ActionEvent e) throws Exception {
		if(e!=null&&e.getActionCommand()!=null&&
				e.getActionCommand().equals("com.kingdee.eas.framework.client.AbstractListUI$ActionView")){
			//从查看状态返回不刷新界面
			return;
		}
		super.refresh(e);
//		initTable();
//		FDCHelper.formatTableNumber(getMainTable(), "amount");
	}
	
	/**
	 * 
	 * 描述：冻结合同表列
	 * 
	 * @author:liupd 创建时间：2006-7-25
	 *               <p>
	 */
	protected void freezeMainTableColumn() {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				/*
				 * 冻结最后一列即可，列的序号从1开始
				 */
				// 合同编码
				int number_col_index = getMainTable().getColumn("number")
						.getColumnIndex();
				getMainTable().getViewManager().setFreezeView(-1, number_col_index+1);
			}});
		
	}

	/**
	 * 
	 * 描述：冻结单据表列
	 * 
	 * @author:liupd 创建时间：2006-7-25
	 *               <p>
	 */
	protected void freezeBillTableColumn() {
		getBillListTable().checkParsed();
		getBillListTable().getStyleAttributes().setLocked(true);
		
	}
	
	
	protected void updateButtonStatus() {

		super.updateButtonStatus();

		// 如果是虚体成本中心，则不能增、删、改
//		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
//			actionAddNew.setEnabled(false);
//			actionEdit.setEnabled(false);
//			actionRemove.setEnabled(false);
//			actionAddNew.setVisible(false);
//			actionEdit.setVisible(false);
//			actionRemove.setVisible(false);
//			actionAttachment.setEnabled(false);
//			actionAttachment.setVisible(false);
//			menuEdit.setVisible(false);
//		}
		
		actionRemove.setEnabled(true);
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);
		actionRespite.setVisible(false);
		actionCancelRespite.setVisible(false);
	}

	/**
	 * 
	 * 描述：按状态排序（按暂存、已提交、审批中，已审批的顺序排序）
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#beforeExcutQuery(com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	protected void beforeExcutQuery(EntityViewInfo ev) {

		ev.setTopCount(2000);
		ev.getSorter().remove(new SorterItemInfo("id"));
		ev.getSorter().add(new SorterItemInfo("state"));
		ev.getSorter().add(new SorterItemInfo("number"));

		super.beforeExcutQuery(ev);
	}

	protected void treeProject_valueChanged(TreeSelectionEvent e)
			throws Exception {

		super.treeProject_valueChanged(e);
		treeSelectChange();
	}

	protected void treeContractType_valueChanged(TreeSelectionEvent e)
			throws Exception {

		super.treeContractType_valueChanged(e);
		treeSelectChange();
	}

	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeProject
				.getLastSelectedPathComponent();
	}

	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeContractType
				.getLastSelectedPathComponent();
	}

	/**
	 * 
	 * 描述：左边树选择改变，重新构造条件执行查询
	 * 
	 * @author:liupd 创建时间：2006-7-25
	 *               <p>
	 */
	protected void treeSelectChange() throws Exception {

		DefaultKingdeeTreeNode projectNode  = getProjSelectedTreeNode();
		DefaultKingdeeTreeNode  typeNode  =	getTypeSelectedTreeNode() ;
		
		Object project  = null;
		if(projectNode!=null){
			project = projectNode.getUserObject();
		}
		Object type  = null;
		if(typeNode!=null){
			type = typeNode.getUserObject();
		}
		
		mainQuery.setFilter(getTreeSelectFilter(project,type,containConWithoutTxt()));

		execQuery();

		if(isHasBillTable()) {
			getBillListTable().removeRows(false);
		}	
		
		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
			btnAddNew.setEnabled(true);
		}else if(isHasBillTable()){
			/*
			 * 没有合同时不能新增下游单据 sxhong
			 */
			btnAddNew.setEnabled(false);
		}
	}
	
	/**
	 * 选择工程项目节点和合同类型节点后的选择事件
	 * @return
	 * @throws Exception
	 */
	protected FilterInfo getTreeSelectFilter(Object projectNode,Object  typeNode,boolean containConWithoutTxt) throws Exception {
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		
		/*
		 * 工程项目树
		 */
		if (projectNode != null 	&& projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();	
				}else{
					id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
				}				
				
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(
						new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));

				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
				
				f.setMaskString("#0 and #1 and #2");
				
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			}

		}

		FilterInfo typefilter =  new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();	
		/*
		 * 合同类型树
		 */
		if (typeNode != null&& typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo)typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractType.id", idSet,CompareType.INCLUDE));
		}else if(containConWithoutTxt && typeNode != null &&typeNode.equals("allContract")){
			//如果包含无文本合同，查询所有时，让它查不到合同
			typefilterItems.add(new FilterItemInfo("contractType.id", "allContract"));
		}
		
		//三方合同
		//R110412-116：合同管理-合同订立-合同修订，修订审批后，被修订的合同在“合同列表”里消失
		if(!(this instanceof ContractBillListUI) && !(this instanceof ContractBillReviseListUI)){
			typefilter.appendFilterItem("isAmtWithoutCost", String.valueOf(0));
		}
		
		if(filter!=null && typefilter!=null){
			filter.mergeFilter(typefilter,"and");
		}
		
		return filter;
	}

	/**
	 * 
	 * 描述：当左边的树选择变化时的缺省条件（提供默认实现，合同状态为审核，子类可以覆盖，如果没有条件，也要返回一个new FilterInfo()，不能直接返回null）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-9-5 <p>
	 */
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		Set set = getContractBillStateSet();
//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.STORED_VALUE));
		filterItems.add(new FilterItemInfo("state",set,CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		
/*		String maskString="(#0 or #1) and #2 and #3";
		filter.setMaskString(maskString);*/
		return filter;
	}


	/**
	 * 要显示的合同的状态集合,用于过滤合同
	 * @return
	 */
	protected Set getContractBillStateSet() {
		Set set=new HashSet();
		set.add(FDCBillStateEnum.AUDITTED_VALUE);
		return set;
	}
	
	/**
	 * 
	 * 描述：审批通过
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractBillListUI#actionAudit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();

		checkBillState(new String[]{getStateForAudit()}, "selectRightRowForAudit");

		audit(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));

		super.actionAudit_actionPerformed(e);
		
		showOprtOKMsgAndRefresh();
	}
	
	//查看BillListTable 的审批结果
	public void actionAuditResult_actionPerformed(ActionEvent e)throws Exception
	{
    	checkSelected();    	
        String id = getSelectedKeyValue(getBillListTable());
        if (!StringUtils.isEmpty(id)) {
            MultiApproveUtil.showApproveHis(
            		BOSUuid.read(id) ,UIModelDialogFactory.class.getName() , this);
        }
	}


	
	/**
	 * 
	 * 描述：反审批
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractBillListUI#actionUnAudit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {

		checkSelected();

		checkBillState(new String[]{getStateForUnAudit()}, "selectRightRowForUnAudit");

		List selectedIdValues = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		
		//检查引用
		for (Iterator iter = selectedIdValues.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			checkRef(id);
		}
		
		unAudit(selectedIdValues);
		
		super.actionUnAudit_actionPerformed(e);
		
		showOprtOKMsgAndRefresh();
	}

	

    
    /**
	 * 
	 * 描述：返回远程接口（子类必须实现）
	 * @return
	 * @throws BOSException
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected abstract ICoreBillBase getRemoteInterface() throws BOSException;

	/**
	 * 
	 * 描述：审核通过（子类必须实现，调用服务器端打审核标志的方法即可）
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected abstract void audit(List ids) throws Exception;

	/**
	 * 
	 * 描述：反审核（子类必须实现，调用服务器端打反审核标志的方法即可）
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected abstract void unAudit(List ids) throws Exception;
	
	/**
	 * 
	 * 描述：审核操作的单据前置状态
	 * @return
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}
	
	/**
	 * 
	 * 描述：反审核操作的单据前置状态
	 * @return
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}
	
	/**
	 * 
	 * 描述：为当前单据的新增、编辑、查看准备Context
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.CoreBillListUI#prepareUIContext(com.kingdee.eas.common.client.UIContext, java.awt.event.ActionEvent)
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {
			
			//如果没有BillTable，则要传工程项目树叶子节点的ID集合，用于编辑界面工程项目树的F7选择过滤
			if(!isHasBillTable()) {
				Set leafNodesIdSet = new HashSet();
				TreeNode projRoot = (TreeNode) treeProject.getModel().getRoot();
				FDCClientUtils.genProjLeafNodesIdSet(projRoot, leafNodesIdSet);
				uiContext.put("projLeafNodesIdSet", leafNodesIdSet);
			}
		}
		else if(act.equals(actionEdit) || act.equals(actionView)) {
			uiContext.put(UIContext.ID, getSelectedKeyValue());
		}
		
		uiContext.put("contractBillId", getSelectedKeyValue(getMainTable()));
		uiContext.put("contractBillNumber", getSelectedContractNumber());
		
		Object userObject2 = getProjSelectedTreeNode().getUserObject();
		if(userObject2 instanceof CurProjectInfo){
			BOSUuid projId = ((CurProjectInfo) userObject2).getId();
			uiContext.put("projectId", projId);
		}
	}

	protected String getSelectedContractNumber() {
		KDTSelectBlock selectBlock = getMainTable().getSelectManager().get();
		
	    if (selectBlock != null) {
	        int rowIndex = selectBlock.getTop();
	        IRow row = getMainTable().getRow(rowIndex);
	        if (row == null) 
	        {
	            return null;
	        }
	        
	        ICell cell = row.getCell("number");
	
	        if (cell == null) {
	            MsgBox.showError(EASResource
	                    .getString(FrameWorkClientUtils.strResource
	                            + "Error_KeyField_Fail"));
	            SysUtil.abort();
	        }
	
	        Object keyValue = cell.getValue();
	
	        if (keyValue != null) {
	            return keyValue.toString();
	        }
	    }
	
	    return null;
	}
	
	/**
	 * 获取当前选择行的主键
	 * 
	 * @return 返回当前选择行的主键，若当前选择行为空，或者当前选中行的主键列为空，则返回null
	 */
	protected String getSelectedKeyValue(KDTable table) {
		//String value = super.getSelectedKeyValue();
		
        int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
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
	
	protected String getSelectedKeyValue() {
		return getSelectedKeyValue(getBillListTable());
	}

	/**
	 * 
	 * 描述：获取当前单据的Table（子类必须实现）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-8-2 <p>
	 */
	protected abstract KDTable getBillListTable();

	/**
	 * 
	 * 描述：检查当前单据的Table是否选中行
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#checkSelected()
	 */
	public void checkSelected() {
		checkSelected(getBillListTable());
	}

	/**
	 * 
	 * 描述：检查当前单据的Table是否选中行
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#checkSelected()
	 */
	public void checkSelected(KDTable table) {
		if (table.getRowCount()==0 || table.getSelectManager().size() == 0) {
	        MsgBox.showWarning(this, EASResource
	                .getString(FrameWorkClientUtils.strResource
	                        + "Msg_MustSelected"));
	        SysUtil.abort();
	    }
	}
	
	/**
	 * 批量删除
	 * @throws Exception
	 * @throws EASBizException
	 */
	protected void removeBill() throws Exception {
		int [] selectRows = KDTableUtil.getSelectedRows(getBillListTable());
		IObjectPK[] arrayPK = new ObjectUuidPK[selectRows.length];
		
		boolean canRemove = true ;
		//网络控制
        try
        {
			for (int i = 0; i < selectRows.length; i++) {
				String id = (String)getBillListTable().getCell(selectRows[i], getKeyFieldName()).getValue();
				checkRef(id);
				arrayPK[i] = new ObjectUuidPK(id);
			
                this.setOprtState("REMOVE");
                this.pubFireVOChangeListener(arrayPK[i].toString());
            }
            
		}catch (Throwable ex)
        {
        	this.handUIException(ex);
        	canRemove = false;
            SysUtil.abort();
        }
		
		if(canRemove){
			getRemoteInterface().delete(arrayPK);		
			showOprtOKMsgAndRefresh();
		}
	}
	
	/**
     * 
     * 描述：根据选择的合同显示单据列表
     * @param e
     * @throws BOSException
     * @author:liupd
     * 创建时间：2006-8-2 <p>
     */
	protected void displayBillByContract(EntityViewInfo view) throws BOSException {
		if(view==null){
			return ;
		}
	}
	
	protected boolean  displayBillByContract(KDTSelectEvent e,EntityViewInfo view) throws BOSException {
		return false;
	}
	
	//获取该行的币别精度
	protected int getPre(KDTSelectEvent e){
		KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	int pre = 2;
    	
    	if(getMainTable().getCell(top,"currency.precision")!=null){
    		pre = ((Integer)getMainTable().getCell(top, "currency.precision").getValue()).intValue();
    	}
    	
    	return pre;
	}
	
	/**
	 * 
	 * 描述：生成查询单据的EntityViewInfo
	 * @param e
	 * @return
	 * @author:liupd
	 * 创建时间：2006-8-2 <p>
	 */
	protected EntityViewInfo genBillQueryView(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	if(getMainTable().getCell(top, getKeyFieldName())==null){
    		return null;
    	}
    	
    	String contractId = (String)getMainTable().getCell(top, getKeyFieldName()).getValue();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
    	view.setFilter(filter);
    	view.getSorter().add(new SorterItemInfo(getBillStatePropertyName()));
    	SelectorItemCollection selectors = genBillQuerySelector();
    	if(selectors != null && selectors.size() > 0) {
    		for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);
				
			}
    	}
		return view;
	}
	
	/**
	 * 
	 * 描述：生成获取单据的Selector
	 * @return
	 * @author:liupd
	 * 创建时间：2006-8-3 <p>
	 */
	protected abstract SelectorItemCollection genBillQuerySelector();
	
	/**
     * 单据编辑界面默认是新开窗口方式打开
     * @return
     */
    protected String getEditUIModal()
    {
        return UIFactoryName.NEWTAB;
    }
    
//  数据对象变化时，刷新界面状态
    protected void setActionState(){
    	super.setActionState();
    	updateButtonStatus();
    }
    
    /**
     * 
     * 描述：是否使用单据Table，即合同Table下是否还有Table
     * @return
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     */
    protected boolean isHasBillTable() {
    	return true;
    }
    
    /**
     * 
     * 描述：检查是否有关联对象(删除前调用)
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     */
    protected void checkRef(String id) throws Exception {
    	
    }
    
    /**
     * 删除操作。业务可重载。
     * @throws Exception
     * @throws EASBizException
     */
    protected void Remove() throws Exception
    {
    	removeBill();
    }
    
    /**
     * 
     * 描述：修改前检查
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     * @throws Exception 
     */
    protected void checkBeforeEdit() throws Exception {
	    checkSelected();
		
		CoreBillBaseInfo billInfo = getRemoteInterface().getCoreBillBaseInfo(new ObjectUuidPK(getSelectedKeyValue(getBillListTable())));
		String billState = billInfo.getString(getBillStatePropertyName());
		String[] states = getBillStateForEditOrRemove();
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if(billState.equals(states[i])) {
				pass = true;
			}
		}
		if(!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
    }
    
    /**
     * 
     * 描述：（批量）删除前检查
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     * @throws Exception 
     */
    protected boolean checkBeforeRemove() throws Exception {
    	checkSelected();
    	
    	List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());

    	if(idList==null || idList.size()==0){
    		return false;
    	}
    	
		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBillBaseCollection coll = getRemoteInterface().getCoreBillBaseCollection(view);

		
		String[] states = getBillStateForEditOrRemove();
		
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) {
				if(billState.equals(states[i])) {
					pass = true;
				}
			}
			if(!pass) {
				MsgBox.showWarning(this, ContractClientUtils.getRes("cantRemove"));
				SysUtil.abort();
			}
			
			ContractClientUtils.checkContractBillRefForRemove(this, element.getId().toString());
		}
		
		return true;
    }
    
    
  


	/** 
	 * 描述：设置采用KDLayout布局的容器的"OriginalBounds"客户属性。KDLayout设计思想采用了绝对布局方式，没有考虑Java中存在相对布局的情况。
	 * 			必须在UI的非抽象类中重载方法public void initUIContentLayout()
     * @return
     * @author:jelon
     * 创建时间：2006-8-28 <p>
	 */
	private void setContainerLayout() {
		//pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(pnlSplit.getX(), pnlSplit.getY(), pnlSplit.getDividerLocation(), pnlSplit.HEIGHT));
		pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(10, 10, 250, 609));
		pnlRight.putClientProperty("OriginalBounds", new Rectangle(270, 10, 733, 609));
	}
	
	/* setContainerLayout一定要在子类调用的语义不明确(不调用设计会出现界面显示时的死循环,导致outofMem 错误而使程序中断),
	 * 将其移入基类方法,子类就不用实现了
	 * @see com.kingdee.bos.ui.face.CoreUIObject#initLayout()
	 */
	public void initLayout() {
		super.initLayout();
		setContainerLayout();
	}
	/**
	 * 打印的时候会调用以加载打印信息
	 * @author sxhong  		Date 2006-9-14
	 * @return
	 * @see com.kingdee.eas.framework.client.ListUI#getTableForPrintSetting()
	 */
	protected KDTable getTableForPrintSetting()
	{
		if(isHasBillTable()) return getBillListTable();
		else return super.getTableForPrintSetting();
	}

	/**
	 * 
	 * 描述：为实现编辑界面的上一条、下一条功能，需要将ListUI的allIdList设置为Bill的IdList
	 * 注意：没有考虑联合主键的情况，比如单据头id和单据体id，一般叙事簿界面只显示单据头的信息
	 * 如果要支持联合主键的情况，请参考ListUI的processAllIdList方法修改
	 * 在getBillIDList()中使用
	 * @return
	 * @author:liupd
	 * 创建时间：2006-9-29 <p>
	 */
	protected List getAllIdListForBill() {
		List idList = new ArrayList();
		int count = getBillListTable().getRowCount();

        if (count == 0)
        {
            return null;
        }
        String id = null;
        Object[]keyValue = null;
		for (int i = 0; i < count; i++) {
			id = (String)getBillListTable().getCell(i, getKeyFieldName()).getValue();
			keyValue = new Object[1];
			keyValue[0] = id;
			idList.add(keyValue);
		}
		
		return idList;
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		menuItemUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));
		menuItemAudit.setText(menuItemAudit.getText().replaceAll("\\(A\\)", "")+"(A)");
		menuItemAudit.setMnemonic('A');
		
		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setText(menuItemUnAudit.getText().replaceAll("\\(U\\)", "")+"(U)");
		menuItemUnAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setMnemonic('U');
		actionRespite.setVisible(false);
		actionRespite.setEnabled(false);
		actionCancelRespite.setVisible(false);
		actionCancelRespite.setEnabled(false);
//		menuItemTraceUp.setAccelerator(KeyStroke.getKeyStroke("F5"));
	}
	
    /**
     * 
     * 描述：单据可修改、删除的状态
     * @return
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     */
    protected String[] getBillStateForEditOrRemove() {
    	return new String[]{FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE};
    }
      
    /**
     * 获取当前选择所有行的主键集合，已处理单据的id重复情况，它被getSelectedKeyValues()调用
     * 注：对于房地产系统来存在双分录的情况，而框架把得到主键集合的写死了，
     * 只能从tblMain得到，造成很多不明确的问题
     * @author sxhong  		Date 2006-10-28
     * @return	IIDList 返回当前选择行的主键，若当前选择行为空，或者当前选中行的主键列为空，则返回null
     * @see com.kingdee.eas.framework.client.ListUI#getSelectedKeyValuesForHasQueryPK()
     */
    protected IIDList getSelectedKeyValuesForHasQueryPK()
    {
    	if(isHasBillTable()){
    		return getBillIDList();
    	}else{
    		return super.getSelectedKeyValuesForHasQueryPK();
    	}
    }
    
    private IIDList getBillIDList()
    {
    	if(getBillListTable()==null){
    		return RealModeIDList.getEmptyIDList();//返回一个空集
    	}
    	KDTable billTable=getBillListTable();
        // 多选情况下，获取所有的选择块信息
        int [] selectRows = KDTableUtil.getSelectedRows(billTable);
        
//        selectList = new ArrayList();
        int size = selectRows.length; // 获取选择块的总个数
        int maxReturnRowCount = 10000;//来自基类
        if (size == 0)
        {
            return null;
        }

        if ((size == 1)
                && (billTable.getSelectManager().get().getTop() == billTable
                        .getSelectManager().get().getBottom()))
        {
            // 仅选择一行时，选择数据范围在100以内。
            //因为考虑到所有数据都获取并不好，新地虚模式也不能支持。用户很少这么使用的。 by psu_s 2005-8-24
            RealModeIDList idList = new RealModeIDList();
            int rowNum = billTable.getSelectManager().get().getTop();
            ICell cell = billTable.getRow(rowNum).getCell(getKeyFieldName());
             
            if (cell == null)
            {
                MsgBox.showError(EASResource
                        .getString(FrameWorkClientUtils.strResource
                                + "Error_KeyField_Fail"));
                SysUtil.abort();
            }

            String curId = cell.getValue().toString();
            //allIdList
            Object[] currentIds = null;
            //TODO 要改进
            List allIdList = getAllIdListForBill();
            if(rowNum<allIdList.size())
            {
                currentIds = (Object[]) allIdList.get(rowNum);
            }
            else
            {
                for(int i=0;i<allIdList.size();i++)
                {
                    Object [] objs = (Object[]) allIdList.get(i);
                    if(curId!=null && curId.equals(objs[0].toString()))
                    {
                        rowNum = i;
                        currentIds = (Object[]) allIdList.get(rowNum);
                        break;
                    }
                }
                 
            }
            String currentId = null;
            if(currentIds == null)
            {
            	//取当前选择行的主键返回
                RealModeIDList idList2 = new RealModeIDList();
                if(cell != null && cell.getValue() != null)
                {
                    idList2.add(cell.getValue().toString());
                }
                return idList2;
            }
            currentId = currentIds[0].toString();
            
            Object[] ids = null;

            if (currentIds.length > 1) // 多主键，bill
            {
                String theId = null;
                int j = 0;

                for (int i = 0; i < allIdList.size(); i++)
                {
                    ids = (Object[]) allIdList.get(i);

                    if (theId == null || !theId.equals(ids[0].toString()))
                    {
                        j++;
                        theId = ids[0].toString();

                        if (currentId.equals(theId))
                        {
                            rowNum = j - 1;
                        }

                        idList.add(theId);
                        // selectList.add(new Integer(i));
                    }
                }
            }
            else
            // 单主键 
            {
                for (int i = 0; i < allIdList.size(); i++)
                {//在这里将所有的id放入到idList中，然后再指定当前的Id就确定了所选择的id
                    ids = (Object[]) allIdList.get(i);
                    idList.add(ids[0].toString());
                    // selectList.add(new Integer(i));
                }
            }

            idList.setCurrentIndex(rowNum);
//            idList.setQuery(this.mainQueryPK, this.mainQuery);
//             idList.setCount(allIdList.size()); //可能有问题
            idList.setMaxRowCount(10000);
//            getSelectid
//            selectList.add(new Integer(rowNum));

            return idList;
        }
        else
        {
            RealModeIDList idList = new RealModeIDList();

            for(int i=0;i<size;i++)
            {
                if(selectRows[i]<0)
                {
                    return idList;
                }
                ICell cell = billTable.getRow(selectRows[i]).getCell(getKeyFieldName());

                if (cell == null)
                {
                    MsgBox.showError(EASResource
                            .getString(FrameWorkClientUtils.strResource
                                    + "Error_KeyField_Fail"));
                    SysUtil.abort();
                }

                if(cell.getValue() == null)
                {
                    return idList;
                }
                String id = cell.getValue().toString();
                idList.add(id);
//                selectList.add(new Integer(selectRows[i]));

            }
            idList.setCurrentIndex(0);
//            idList.setQuery(this.mainQueryPK, this.mainQuery);
            //idList.setCount(this.rowCount);
//            idList.setMaxRowCount(this.maxReturnRowCount);
            idList.setMaxRowCount(maxReturnRowCount);
            return idList;
        }
    }
    
    /**
     * 
     * 没有特别的意义，只是重载超类的方法，指明如果是双分录的时候返回的是子分录的ID集合
     * @author sxhong
     * 获取当前选择所有行的主键集合
     * 
     * @return 返回当前选择行的主键，若当前选择行为空，或者当前选中行的主键列为空，则返回null
     */
    protected IIDList getSelectedKeyValues() {
    	return super.getSelectedKeyValues();
    }
    
    /**
     * 关联生成，上查，下查等都会用到调用这个方法从tblMain中取值，重载方法使得从子分录获得实体
     * @author sxhong  		Date 2006-10-30
     * @return
     * @throws Exception
     * @see com.kingdee.eas.framework.client.CoreBillListUI#getBillList()
     */
    protected CoreBillBaseCollection getBillList() throws Exception
    {
    	CoreBillBaseCollection billList;
		if(isHasBillTable()){
			KDTable bak = getMainTable();
			tblMain = getBillListTable();
			try{
				billList=super.getBillList();
			}finally{
				tblMain = bak;
			}
		}else{
			billList=super.getBillList();
		}
    	return billList;
    }
    
    public void onGetRowSet(IRowSet rowSet) {
    	
    	super.onGetRowSet(rowSet);
/*	已经对query进行了处理,不在需要动态查询了 by sxhong 2009-05-07 15:26:29
		
		 * 选择不计成本的金额为否,金额录在分录上,显示的时候要从分录上取
		 
		try {
			rowSet.beforeFirst();
			
			Set contractIds = new HashSet() ;
			Map idMap  = new HashMap();
			while (rowSet.next()) {
				String id  = ContractClientUtils.getUpdateAmtByAmtWithoutCost(rowSet);
				if(id!=null){
					idMap.put(id,id);					
				}
				contractIds.add(rowSet.getString("id"));
			}		
//			RPC改造有问题,暂时不使用,以后将不放成本的金额放到单据头上去
			IUIActionPostman actionPost = prepareDataAfterOnRowSet();
			((RequestContext)actionPost.getRequestContext()).put("ContractListBaseUIHandler.contractIds",contractIds);
			((RequestContext)actionPost.getRequestContext()).put("ContractListBaseUIHandler.idMap",idMap);
			actionPost.callHandler();
			
			Map amountMap  = (Map) ActionCache.get("ContractListBaseUIHandler.amountMap");
			if(amountMap==null){
				amountMap = ((IContractBill)ContractBillFactory.getRemoteInstance()).getAmtByAmtWithoutCost(idMap);
			}
//			Map	amountMap = ((IContractBill)ContractBillFactory.getRemoteInstance()).getAmtByAmtWithoutCost(idMap);
			rowSet.beforeFirst();
			while (rowSet.next()) {
				ContractClientUtils.updateAmtByAmtWithoutCost( rowSet, amountMap);
			}			
			
			rowSet.beforeFirst();
		} catch (Exception e) {
			handUIException(e);
		}*/

    }
    
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    	KDTable table=getBillListTable();
    	if(!isHasBillTable()||table==null){
    		table=getMainTable();
    	}
    	
    	boolean isEdit=false;
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = this.getSelectedKeyValue();
        checkSelected(table);
        if (boID == null)
        {
            return;
        }
        if(getBillStatePropertyName()!=null){
        	int rowIdx=table.getSelectManager().getActiveRowIndex();
        	ICell cell =table.getCell(rowIdx, getBillStatePropertyName());
        	Object obj=cell.getValue();
        	if(obj!=null&&
        			(obj.toString().equals(FDCBillStateEnum.SAVED.toString())
        			||obj.toString().equals(FDCBillStateEnum.SUBMITTED.toString())
        			||obj.toString().equals(FDCBillStateEnum.AUDITTING.toString())
        			||obj.toString().equals(BillStatusEnum.SAVE.toString())
        			||obj.toString().equals(BillStatusEnum.SUBMIT.toString())
        			||obj.toString().equals(BillStatusEnum.AUDITING.toString()))){
        		isEdit=true;
        	}else{
        		isEdit=false;
        	}
			
        }
        
        
        //对合同与付款申请单做特殊处理
        if(isEdit){
        	//合同
	        String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
	        String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
			if(this.getClass().getName().equals("com.kingdee.eas.fdc.contract.client.ContractBillListUI")){
				String uiName = "com.kingdee.eas.fdc.contract.client.ContractBillEditUI";
				boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
	    				new ObjectUuidPK(userId),
	    				new ObjectUuidPK(orgId),
	    				new MetaDataPK(uiName),
	    				new MetaDataPK("ActionAttamentCtrl") );
				//如果未启用参数则有权限的用户才能进行附件维护,如果已经启用了参数则制单人等于当前用户且有权限才能进行 维护
	        	if(hasFunctionPermission){
	        		boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
	        		if(creatorCtrl){
	        			//制单人要等于当前用户才行
	        			FDCSQLBuilder builder=new FDCSQLBuilder();
	        			builder.appendSql("select 1 from T_Con_ContractBill where fid=? and fcreatorId=?");
	        			builder.addParam(boID);
	        			builder.addParam(userId);
	        			if(!builder.isExist()){
	        				isEdit=false;
	        			}
	        		}
	        	}else{
	        		isEdit=false;
	        	}
	        }
	        //付款申请单
	        if(this.getClass().getName().equals("com.kingdee.eas.fdc.contract.client.PayRequestBillListUI")){
	        		String uiName = "com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI";
					boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
		    				new ObjectUuidPK(userId),
		    				new ObjectUuidPK(orgId),
		    				new MetaDataPK(uiName),
		    				new MetaDataPK("ActionAttamentCtrl") );
					//如果未启用参数则有权限的用户才能进行附件维护,如果已经启用了参数则制单人等于当前用户且有权限才能进行 维护
		        	if(hasFunctionPermission){
		        		boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
		        		if(creatorCtrl){
		        			//制单人要等于当前用户才行
		        			FDCSQLBuilder builder=new FDCSQLBuilder();
		        			builder.appendSql("select 1 from T_Con_PayRequestBill where fid=? and fcreatorId=?");
		        			builder.addParam(boID);
		        			builder.addParam(userId);
		        			if(!builder.isExist()){
		        				isEdit=false;
		        			}
		        		}
		        	}else{
		        		isEdit=false;
		        	}
		        }
	    }
        acm.showAttachmentListUIByBoID(boID, this,isEdit); // boID 是 与附件关联的 业务对象的 ID
    }
    
	protected abstract boolean isFootVisible();
	
	
    private String selectKeyValue = null;
    private int selectIndex = -1;
    protected void setPreSelecteRow()
    {
        if (isDoRefreshLocate())
        {
        	selectKeyValue = selectPreRow(tblMain,this.getAllIdList(),selectKeyValue,selectIndex,getKeyFieldName());
        }
    }
    
    
    /**
     * 刷新listui后,定位到原来选定行
     */
    public String selectPreRow(KDTable table,List allIdList,String selectKeyValue,int selectIndex,String keyFieldName)
    {
    	int index = selectIndex;
        if (table.getRowCount() <= 0)
            return null;
        if (index >= 0 && selectKeyValue != null)
        {
            if (index>=table.getRowCount())
                index=table.getRowCount()-1;
            //优先选择index行
            String selectValue=getTableCellValue(table,index,keyFieldName);
            if (selectValue!=null&&selectKeyValue.equals(selectValue))
            {
            	table.getSelectManager().select(index, 0);
                return selectValue;
            }

            //如果没有找到，遍历取出的所有数据
            int size=allIdList==null?0:allIdList.size();
            for (int r=0;r<size;r++)
            {
                selectValue=getTableCellValue(table,r,keyFieldName);
                if (selectValue!=null&&selectKeyValue.equals(selectValue))
                {
                	table.getSelectManager().select(index, 0);
                    return selectValue;
                }
            }
            if (table.getRowCount() > 0)
            {
            	table.getSelectManager().select(0, 0);
            }
        }
        return null;
    }
    
    public String getTableCellValue(KDTable table,int rowIndex,String  FieldName){
        String selectValue="";
        if (rowIndex<0)
            return selectValue;
        IRow row = table.getRow(rowIndex);
        if (row != null)
        {
            ICell cell = row.getCell(FieldName);
            if (cell!=null&&cell.getValue()!=null)
                selectValue=cell.getValue().toString();
        }
        return selectValue;
    }
    
	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();

		return clientHanlder;
    }
	
	/**
	 * OnRowSet
	 * @return
	 */
	public IUIActionPostman prepareDataAfterOnRowSet() {
		IUIActionPostman clientHanlder = UIActionPostman.getInstance(this);
		RequestContext request=new RequestContext();
		if (clientHanlder != null) {
			
			clientHanlder.setAvailabe(true);
//			request.setItemActionHandler(getUIHandlerClassName());
//			request.setItemAction("afterOnRowSet");
			request.setClassName(getUIHandlerClassName());
			request.setMethodName("afterOnRowSet");
//	    	request.setMetaDataPK(this.getMetaDataPK());
//	    	
//	    	request.setUIName(this.getClass().getName());
//	    	request.setOrgExt(((Boolean)getUIContext().get("ORGEXT"))==null?
//	    			           true:((Boolean)getUIContext().get("ORGEXT")).booleanValue());
//	    	request.setPerMenuItem(this.getOnloadPermItemName());
//	    	request.setActionPK(new MetaDataPK(getActionName(actionOnLoad)));
//	    	request.setConfigPK(this.buildPK());
//	        request.setMainOrgType(this.getMainBizOrgType());
//	        request.setMainOrgContext(this.getMainOrgContext());
//	        
//	        request.put("License.UserInfo",
//	        		(LicenseUserInfo) SysContext.getSysContext().getProperty("License.UserInfo"));
//	        request.put("License.Ckeck", 
//	        		(getUIContext().get(UIContext.CHECK_LICENSE)==null ? 
//	        				"":getUIContext().get(UIContext.CHECK_LICENSE).toString()));
//	        
	        clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	
    //如下处理基类对上下查关联生成等的变化进行的重载
	/**
     *只获取单据id和分录id
     */
    protected CoreBillBaseCollection getNewBillList() throws Exception
    {
        checkSelected();

        ArrayList idList = new ArrayList();
        idList.add(getSelectedKeyValue());

        Object[] filterObj=new Object[idList.size()];
        FilterInfo filterInfo = new FilterInfo();
        Iterator idIter = idList.iterator();
        int index = 0;
        StringBuffer sbMaskString = new StringBuffer();

        while (idIter.hasNext())
        {
              String id = idIter.next().toString();
              filterObj[index]=id;
              index++;
         }

         String strIdLists =StringUtils.arrayToString(idList.toArray(),",");
         filterInfo.getFilterItems().add(new FilterItemInfo("id", strIdLists, com.kingdee.bos.metadata.query.util.CompareType.INCLUDE));
         //如果是生成凭证，根据VOUCHERFLAG判断，其他如关联生成，上查等不根据这个判断
         if(isCanVoucher())
         {
            filterInfo.getFilterItems().add(new FilterItemInfo(VOUCHERFLAG,Boolean.FALSE,CompareType.EQUALS));
            filterInfo.getFilterItems().add(new FilterItemInfo(VOUCHERFLAG,null,CompareType.EQUALS));
            sbMaskString.append("#0 and (#1 or #2)");
         }else
         {
            sbMaskString.append("#0");
         }
         filterInfo.setMaskString(sbMaskString.toString());

         EntityViewInfo entityViewInfo = new EntityViewInfo();
         entityViewInfo.getSelector().add(new SelectorItemInfo("id"));
         entityViewInfo.setFilter(filterInfo);

         CoreBillBaseCollection sourceBillCollection = ( (ICoreBillBase)getCoreBaseInterface())
                .getCoreBillBaseCollection(entityViewInfo);

         return sourceBillCollection;
    }
    
    
    private static final String VOUCHERFLAG = "fiVouchered";
    //是否是生成凭证的动作
	private boolean canVoucher=false;
    private boolean isCanVoucher()
    {
    	return this.canVoucher;
    }
    
    public void beforeActionPerformed(ActionEvent e) {
    	ItemAction act = getActionFromActionEvent(e);
    	if(act!=null&&act.equals(actionVoucher)){
    		this.canVoucher=true;
    	}else{
    		this.canVoucher=false;
    	}
    	super.beforeActionPerformed(e);
    }
    
	protected ArrayList getSelectedIdValues() {
		// TODO 自动生成方法存根
		ArrayList idList;
		if(isHasBillTable()){
			KDTable bak = getMainTable();
			tblMain = getBillListTable();
			try{
				idList=super.getSelectedIdValues();
			}finally{
				tblMain = bak;
			}
		}else{
			idList=super.getSelectedIdValues();
		}
    	return idList;
	}
	
	public void actionRespite_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedUnAuditedId(getBillListTable(), getKeyFieldName(),false);
			if(idList.size()>0 && idList.get(0) != null){
				((IFDCBill)getRemoteInterface()).setRespite(idList, true);
				MsgBox.showWarning("操作成功！已审批状态的单据不会启用暂缓");
				refreshList();
			}
	}
	
	public void actionCancelRespite_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedUnAuditedId(getBillListTable(), getKeyFieldName(),true);
		if(idList.size()>0 && idList.get(0) != null){
			((IFDCBill)getRemoteInterface()).setRespite(idList, false);
			showOprtOKMsgAndRefresh();
		}
	}
	
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog == null) {
			commonQueryDialog = super.initCommonQueryDialog();
			commonQueryDialog.setWidth(400);
			commonQueryDialog.setTitle("合同查询");
		}
		if(this.getClass().getName().equals(ContractBillListUI.class.getName())){
			commonQueryDialog.addUserPanel(this.getFilterUI());
		}
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ContractBillFilterUI(this, this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		filterUI.setCompany(null);
		filterUI.setProject(null);
		filterUI.setContractType(containConWithoutTxt(), null);
		filterUI.setAuthorizedOrgs(this.authorizedOrgs);
		if (isOnlyQueryAudited()) {
			filterUI.setAuditedState();
		}
		filterUI.setOtherFilter(getOtherFilter4Query());
		DefaultKingdeeTreeNode projectNode = this.getProjSelectedTreeNode();
		if (projectNode != null && projectNode.getUserObject() != null && projectNode.getUserObject() instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode.getUserObject();
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				FullOrgUnitInfo company = null;
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					company = ((OrgStructureInfo) projTreeNodeInfo).getUnit();
				} else {
					company = (FullOrgUnitInfo) projTreeNodeInfo;
				}
				filterUI.setCompany(company);
			} else if (projTreeNodeInfo instanceof CurProjectInfo) {
				filterUI.setProject((CurProjectInfo) projTreeNodeInfo);
			}
		}
		DefaultKingdeeTreeNode contractTypeNode = this.getTypeSelectedTreeNode();
		if (contractTypeNode != null && contractTypeNode.getUserObject() != null) {
			Object typeNode = contractTypeNode.getUserObject();
			if (typeNode instanceof ContractTypeInfo) {
				filterUI.setContractType(containConWithoutTxt(), (ContractTypeInfo) typeNode);
			}
		}

		return this.filterUI;
	}
	
	protected FilterInfo getOtherFilter4Query() {
		return null;
	}
	protected boolean isOnlyQueryAudited() {
		return false;
	}

    /**
     * 是否忽略CU过滤，如果是集团登录，要忽略CU，否则点“查询”按钮得到的结果集会比刚进入的结果集要少。
     * @author owen_wen 2010-12-13
     */
    protected boolean isIgnoreCUFilter() {
    	if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()))
    		return true;
    	else 
    		return false;
    }
}