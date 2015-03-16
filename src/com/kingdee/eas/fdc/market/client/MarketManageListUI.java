/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.insider.client.IntegralApplicationEditUI;
import com.kingdee.eas.fdc.market.IMarketManage;
import com.kingdee.eas.fdc.market.MarketManageCustomerInfo;
import com.kingdee.eas.fdc.market.MarketManageFactory;
import com.kingdee.eas.fdc.market.MarketManageInfo;
import com.kingdee.eas.fdc.market.MarketTypeInfo;
import com.kingdee.eas.fdc.market.PlanFactory;
import com.kingdee.eas.fdc.market.PlanInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.TrackRecordEditUI;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketManageListUI extends AbstractMarketManageListUI {
	private static final Logger logger = CoreUIObject.getLogger(MarketManageListUI.class);
	private String marketId = null; // 当前选中的活动类型id
	private String projectId = null; // 当前选中的销售项目id
	private String allMarketIds = null;
	private String allProjectIds = null;
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public MarketManageListUI() throws Exception {
		super();
	}

	protected OrgType getMainBizOrgType() {
		return OrgType.Sale;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 
	 * 描述：新增判断是否选中具体销售项目，如果没选中则给出提示
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.framework.client.AbstractListUI#actionAddNew_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		TreePath projectPath = sellProjectTree.getSelectionPath();
		if (projectPath == null) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "请选择具体项目！");
			com.kingdee.eas.util.SysUtil.abort();
		}
		DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode) projectPath.getLastPathComponent();
		if (!(projectNode.getUserObject() instanceof SellProjectInfo)) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "请选择具体项目！");
			com.kingdee.eas.util.SysUtil.abort();
		}
		TreePath marketPath = marketTypeTree.getSelectionPath();
		if (marketPath == null) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "请选择具体活动类型！");
			com.kingdee.eas.util.SysUtil.abort();
		}
		DefaultKingdeeTreeNode marketNode = (DefaultKingdeeTreeNode) marketPath.getLastPathComponent();

		if (marketNode.isRoot()) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "请先录入活动类型！");
			com.kingdee.eas.util.SysUtil.abort();
		}
		if (!marketNode.isLeaf()) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "请选择具体活动类型！");
			com.kingdee.eas.util.SysUtil.abort();
		}

		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output getKeyFieldName method
	 */
	protected String getKeyFieldName() {
		return "id";
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.MarketManageInfo objectValue = new com.kingdee.eas.fdc.market.MarketManageInfo();
		return objectValue;
	}

	/**
	 * 
	 * 描述：<方法描述>使新增页面以页签的形式打开
	 * 
	 * @author:ZhangFeng 创建时间：2009-3-13
	 *                   <p>
	 */
	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return UIFactoryName.NEWWIN;
		} else {
			return UIFactoryName.NEWTAB;
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.btnTraceUp.setVisible(false);// 上查
		this.btnTraceDown.setVisible(false);// 下查
		this.menuWorkFlow.setVisible(false);// 工作流
		this.menuBiz.setVisible(false);// 业务
		this.actionCreateTo.setVisible(false);// 编辑--关联生成
		this.actionCopyTo.setVisible(false);// 编辑--复制生成
		this.menuBiz.setVisible(false);// 业务
		this.menuTool.setVisible(false);// 工具
		this.btnWorkFlowG.setVisible(false);// 流程图
		this.btnAuditResult.setVisible(false);// 多级审批结果
		tblMain.removeRows();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.btnWorkFlowG.setVisible(false);
			this.btnAuditResult.setVisible(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}
		// 初始化活动类型树
		MarketClientHelper.getMarketTypeTree(marketTypeTree);
		// 初始化销售项目树
		this.sellProjectTree.setModel(FDCTreeHelper.getSellProjectTree(actionOnLoad, null)); // SHEHelper
		this.sellProjectTree.expandAllNodes(true, (TreeNode) this.sellProjectTree.getModel().getRoot());
		allProjectIds = null;
		allMarketIds = null;
		getAllProjectIds((TreeNode) this.sellProjectTree.getModel().getRoot());
		getAllMarketIds((TreeNode) this.marketTypeTree.getModel().getRoot());
		if (sellProjectTree.getRowCount() > 0) {
			sellProjectTree.setSelectionRow(0);
		}
		if (marketTypeTree.getRowCount() > 0) {
			marketTypeTree.setSelectionRow(0);
		}

		this.actionIntegral.setVisible(false);
		this.actionIntegral.setEnabled(false);
	}

	public void initUIContentLayout() {
		super.initUIContentLayout();
		kDSplitPane1.setDividerLocation(350);

	}

	/*
	 * 描述：<方法描述>根据选中的活动类型树拼接查询条件
	 * 
	 * @param <参数描述>活动类型树节点
	 * 
	 * @author:ZhangFeng 创建时间：2009-4-30 <p>
	 * 
	 * @see <相关的类>
	 */
	private void getAllMarketIds(TreeNode treeNode) {		
		if (treeNode.isLeaf()) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof MarketTypeInfo) {
				MarketTypeInfo market = (MarketTypeInfo) thisNode.getUserObject();
				if (allMarketIds != null) {
					allMarketIds += "," + market.getId().toString();
				} else {
					allMarketIds = market.getId().toString();
				}
			}
		} else {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllMarketIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	/**
	 * 
	 * 描述：<方法描述>根据选中的营销项目树拼接查询条件
	 * 
	 * @param <参数描述>营销项目树节点
	 * 
	 * @author:ZhangFeng 创建时间：2009-4-30
	 *                   <p>
	 * 
	 * @see <相关的类>
	 */
	private void getAllProjectIds(TreeNode treeNode) {		
		if (treeNode.isLeaf()) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo project = (SellProjectInfo) thisNode.getUserObject();
				if (allProjectIds != null) {
					allProjectIds += "," + project.getId().toString();
				} else {
					allProjectIds = project.getId().toString();
				}
			}
		} else {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllProjectIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	/**
	 * 
	 /**
	 * 
	 * 描述：营销项目树节点变化事件，并且把选中的营销项目存入UI，是单据新增页面自动存值
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.fdc.market.client.AbstractMarketManageListUI#sellProjectTree_valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	protected void sellProjectTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		TreePath path = sellProjectTree.getSelectionPath();
		if (path == null) {
			return;
		}
		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path.getLastPathComponent();
		if (treenode.getUserObject() != null && treenode.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) treenode.getUserObject();
			if (sellProject != null) {
				getUIContext().put("project", sellProject);
				projectId = sellProject.getId().toString();
			} else {
				projectId = null;
			}
		} else {
			projectId = null;
		}
		refreshTableData();
	}

	/**
	 * 
	 * 描述：活动类型树节点变化事件
	 * 
	 * @author:ZhangFeng
	 */
	protected void marketTypeTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		TreePath path = marketTypeTree.getSelectionPath();
		if (path == null) {
			return;
		}
		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path.getLastPathComponent();
		if (treenode.getUserObject() != null && treenode.getUserObject() instanceof MarketTypeInfo) {
			MarketTypeInfo marketInfo = (MarketTypeInfo) treenode.getUserObject();
			if (marketInfo != null) {
				marketId = marketInfo.getId().toString();
				getUIContext().put("marketType", marketInfo);
			} else {
				marketId = null;
			}
		} else {
			marketId = null;
		}
		refreshTableData();
	}

	/**
	 * 
	 * 描述：<方法描述>刷新表格数据
	 * 
	 * @author:ZhangFeng 创建时间：2009-4-30
	 *                   <p>
	 * 
	 * @see <相关的类>
	 */
	protected void refreshTableData() throws Exception {
		this.mainQuery = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (allMarketIds != null) {
			filter.getFilterItems().add(new FilterItemInfo("marketType.id", allMarketIds, CompareType.INCLUDE));
		}
		if (allProjectIds != null) {
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", allProjectIds, CompareType.INCLUDE));
		}
		if (marketId != null) {
			filter.getFilterItems().add(new FilterItemInfo("marketType.id", marketId));
		}
		if (projectId != null) {
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", projectId));
		}
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
	}

	/**
	 * 
	 * 描述：给自定义按钮增加图片
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.framework.client.CoreUI#initWorkButton()
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		btnActionIntegral.setEnabled(true);
		btnActionEvaluate.setEnabled(true);
		btnActionEvaluate.setIcon(EASResource.getIcon("imgTbtn_showlist"));
		btnActionIntegral.setIcon(EASResource.getIcon("imgTbtn_submit"));
	}

	public void actionEvaluate_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int[] rowID = KDTableUtil.getSelectedRows(tblMain);

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("actionid", tblMain.getRow(rowID[0]).getCell("id").getValue().toString().trim());
		uiContext.put("number", tblMain.getRow(rowID[0]).getCell("number").getValue() == null ? "" : tblMain.getRow(rowID[0]).getCell("number").getValue().toString().trim());
		uiContext.put("theme", tblMain.getRow(rowID[0]).getCell("movemntTheme").getValue() == null ? "" : tblMain.getRow(rowID[0]).getCell("movemntTheme").getValue().toString().trim());
		uiContext.put("MarketType", tblMain.getRow(rowID[0]).getCell("marketType.id").getValue() == null ? "" : tblMain.getRow(rowID[0]).getCell("marketType.id").getValue().toString().trim());

		IUIFactory uiFactory = null;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			/** 修改为显示效果评估 */
			IUIWindow curDialog = uiFactory.create(ActionEvaluateListUI.class.getName(), uiContext);
			curDialog.show();
		} catch (UIException ex1) {
			handleException(ex1);
		}
	}

	public void actionIntegral_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int[] rowID = KDTableUtil.getSelectedRows(tblMain);
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		String sourceId = null;// sourceId赋值
		sourceId = tblMain.getRow(rowID[0]).getCell("id").getValue().toString().trim();
		FDCCustomerCollection fdcCustomerCollection = new FDCCustomerCollection();// fdcCustomerCollection赋值
		if (sourceId == null) {
			return;
		}
		ObjectUuidPK pk = new ObjectUuidPK(sourceId);
		// MarketManageInfo info = im.getMarketManageInfo(pk);
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("Customer.*");
		sel.add("Customer.fdcCustomer.*");
		MarketManageInfo info = MarketManageFactory.getRemoteInstance().getMarketManageInfo(pk, sel);
		for (int i = 0; i < info.getCustomer().size(); i++) {
			MarketManageCustomerInfo mmCustomerInfo = info.getCustomer().get(i);
			if (mmCustomerInfo != null) {
				FDCCustomerInfo fdcCustomerInfo = mmCustomerInfo.getFdcCustomer();
				if (fdcCustomerInfo != null) {
					FDCCustomerInfo fdcInfo = new FDCCustomerInfo();
					fdcInfo.setId(fdcCustomerInfo.getId());
					fdcCustomerCollection.add(fdcInfo);
				}
			}
		}
		uiContext.put("sourceId", sourceId);
		uiContext.put("fdcCustomerCollection", fdcCustomerCollection);

		IUIFactory uiFactory = null;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			this.setOprtState("ADDNEW");
			IUIWindow curDialog = uiFactory.create(IntegralApplicationEditUI.class.getName(), uiContext, null, this.getOprtState());
			curDialog.show();
		} catch (UIException ex1) {
			handleException(ex1);
		}
	}

	/**
	 * output actionClueCustomer_actionPerformed method
	 */
	public void actionClueCustomer_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		UIContext uiContext = new UIContext(this);
		uiContext.put("ActionCommand", "Clew");
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(CustomerTotalAddUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	public SelectorItemCollection getInsiderSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("sellProject.*");
		return sic;
	}

	/**
	 * output actionLatencyCustomer_actionPerformed method
	 */
	public void actionLatencyCustomer_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		UIContext uiContext = new UIContext(this);
		uiContext.put("ActionCommand", "Latency");
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(CustomerTotalAddUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * output actionIntentCustomer_actionPerformed method
	 */
	public void actionIntentCustomer_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		UIContext uiContext = new UIContext(this);
		uiContext.put("ActionCommand", "Intency");
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(CustomerTotalAddUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * output actionOldCustomer_actionPerformed method
	 */
	public void actionOldCustomer_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int[] rowID = KDTableUtil.getSelectedRows(tblMain);

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);

		String sourceId = null;// sourceId赋值
		sourceId = tblMain.getRow(rowID[0]).getCell("id").getValue().toString().trim();

		ObjectUuidPK pk = new ObjectUuidPK(sourceId);
		MarketManageInfo info = MarketManageFactory.getRemoteInstance().getMarketManageInfo(pk, getInsiderSelectors());

		uiContext.put("MarketManage", info);
		uiContext.put("SellProject", info.getSellProject());
		// uiContext.put("TrackRecordTypeEnum", TrackRecordTypeEnum.Clew);

		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TrackRecordEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}


	
}