/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.JMenuItem;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.market.client.QuestionPaperAnswerEditUI;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.IFDCCustomer;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CustomerListUI extends AbstractCustomerListUI {
	private static final Logger logger = CoreUIObject.getLogger(CustomerListUI.class);
	// SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	SaleOrgUnitInfo saleOrg = SysContext.getSysContext().getCurrentSaleUnit();
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	MarketDisplaySetting setting = new MarketDisplaySetting();
	private boolean showAll = false;

	private CustomerQueryPanel filterUI;
	protected CommonQueryDialog commonQueryDialog;
	
	
	private TreeNode marketNode = null;
	/**
	 * 存储是否认购的id list
	 */
	private List idList  = new ArrayList();

	private String saleUserIds = "null";

	public void actionClueQueryShow_actionPerformed(ActionEvent e) throws Exception {

		CommerceHelper.openTheUIWindow(this, CustomerShareObjectUI.class.getName(), null);
		this.refresh(e);
	}

	protected void execQuery() {
		super.execQuery();
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new FDCCustomerFullFilterUI(this, this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	protected boolean initDefaultFilter() {
		return true;
	}

	/**
	 * output class constructor
	 */
	public CustomerListUI() throws Exception {
		super();
	}

	protected void refresh(ActionEvent e) throws Exception {
		CacheServiceFactory.getInstance().discardType(new LinkmanEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new TrackRecordInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		
//		resetTradeTimes();
		
		super.refresh(e);
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		CacheServiceFactory.getInstance().discardType(new LinkmanEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new TrackRecordInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());

		// initCommonQueryDialog();
		initTree();
		this.treeMain.setSelectionRow(0);
		super.onLoad();
		this.MenuItemAttachment.setVisible(true);
		this.btnAttachment.setVisible(true);
		this.chkShowAll.setVisible(false);
		this.btnQuestionPrint.setEnabled(true);
		this.actionQuestionPrint.setEnabled(true);
		this.menuItemCancel.setText("作废");
		this.menuItemCancelCancel.setText("捡回");

		this.btnCancel.setText("作废");
		this.btnCancel.setToolTipText("作废");
		this.btnCancelCancel.setText("捡回");
		this.btnCancelCancel.setToolTipText("捡回");

		this.btnClueQueryShow.setVisible(true);// 线索查询
		this.btnClueQueryShow.setEnabled(true);// 线索查询
		this.menuItemClueQueryShow.setVisible(true);
		this.menuItemClueQueryShow.setEnabled(true);

		this.menuEdit.remove(menuItemRemove);
		// this.btnRemove.setVisible(false);
		this.btnModifyCustomerName.setVisible(false);
		this.btnModifyCustomerPhone.setVisible(false);
		this.menuItemSwitchTo.setVisible(false);

		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.btnAddTrackRecord.setEnabled(true);
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);

			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionToSysCustomer.setEnabled(false);
			this.actionImportantTrack.setEnabled(false);
			this.actionCancelImportantTrack.setEnabled(false);
			this.actionCustomerAdapter.setEnabled(false);
			this.actionCustomerShare.setEnabled(false);
			this.actionCustomerCancelShare.setEnabled(false);
			this.btnAddTrackRecord.setEnabled(false);

		}
		
		/**
		 * 禁止使用ctrl+c功能
		 */
		ActionMap actionMap = tblMain.getActionMap();
		actionMap.remove(KDTAction.COPY);
		actionMap.remove(KDTAction.CUT);
		
		disableExport(this, tblMain);
		
		this.actionImportantTrack.setVisible(false);
		this.actionCancelImportantTrack.setVisible(false);
		this.actionToSysCustomer.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCancel.setVisible(false);
}

	
	/**
	 * 禁用右键导出功能
	 * 父类中的不能满足要求
	 */
	public static void disableExport(CoreUI ui, KDTable table) {
		String menuItemExportExcel = "menuItemExportExcel";
		String menuItemExportSelected = "menuItemExportSelected";
		String menuMail = "menuMail";
		/**
		 * 原来方法中没有此功能
		 */
		String menuCopy = "menuItemCopy";
		String menuPublishReport = "menuPublishReport";
		if (table == null || ui == null) {
			return;
		}
		KDTMenuManager tm = ui.getMenuManager(table);
		if (tm != null) {
			Component[] menus = tm.getMenu().getComponents();
			for (int i = 0; i < menus.length; i++) {
				// 隐藏处理。
				if (menus[i] instanceof JMenuItem) {
					JMenuItem menu = (JMenuItem) menus[i];
					if (menu != null && menu.getName() != null && 
							(menu.getName().equalsIgnoreCase(menuItemExportExcel) 
									|| menu.getName().equalsIgnoreCase(menuItemExportSelected)
									||menu.getName().equalsIgnoreCase(menuMail)
									||menu.getName().equalsIgnoreCase(menuCopy)
									||menu.getName().equalsIgnoreCase(menuPublishReport))) {
						menu.setVisible(false);
					} else if (menu!=null) {
//						System.out.println("菜单:" + "\t" + menu.getName() + "\t" + menu.getText());
					}

				}
			}
		}
	}
	public void loadFields() {
		super.loadFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
//		this.tblMain.removeRows();
		this.execQuery();
		
		/**
		 * 初始化是否认购状态
		 * by renliang
		 */
//		idList = super.getQueryPkList();
//		fetchInitData(idList);
	}

	/* 客户忽略CU隔离 zhicheng_jin 090226 */
	protected boolean isIgnoreCUFilter() {
		return true;
	}

	// 过滤条件
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			// 过滤面板上的条件加上销售顾问的过滤
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeMain.getLastSelectedPathComponent();
			MarketingUnitInfo marketingInfo = null;
			Set marketSet = new HashSet();
			Set orgSet = new HashSet();
			// 当点击的节点为营销顾问时只显示营销顾问所属的客户和共享给改顾问的客户
			// 当点击的节点为营销单元时显示该营销单元能看到的所有客户以及共享给他下级营销单元的客户
			// 当点击的节点为销售组织时显示该组织能显示的所有客户以及共享给该组织的客户
			// 注释的代码是因为以前客户显示，点击节点为营销顾问时显示上面所说的以及共享给他所在的营销单元的
			// 和上级营销单元的以及共享给组织的全都显示。点击节点为营销单元时显示上面所说的以及共享给他上级单元的客户

			if (node != null && node.getUserObject() instanceof MarketingUnitInfo) {
				marketingInfo = (MarketingUnitInfo) node.getUserObject();
				marketSet.add(marketingInfo.getId().toString());
				// marketSet = SHEHelper.getMarketingUnit(node, marketSet);
				marketSet = SHEHelper.getChildMarketUnit(node, marketSet);
			} else if (node != null && node.getUserObject() instanceof OrgStructureInfo) {
				orgSet.add(saleOrg.getId().toString());
			}
			FilterInfo thisFilter = new FilterInfo();
			saleUserIds = "null";
			if (node != null)
				getAllSaleIds(node);
			thisFilter.getFilterItems().add(new FilterItemInfo("salesman.id", saleUserIds, CompareType.INCLUDE));
			FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
			if (para.getBoolean("chkShare")) {
				thisFilter.getFilterItems().add(new FilterItemInfo("shareSellerList.seller.id", saleUserIds, CompareType.INCLUDE));
				if (orgSet.size() > 0) {
					thisFilter.getFilterItems().add(new FilterItemInfo("shareSellerList.orgUnit.id", orgSet, CompareType.INCLUDE));
					thisFilter.setMaskString("#0 or #1 or #2");
				} else if (marketSet.size() > 0) {
					thisFilter.getFilterItems().add(new FilterItemInfo("shareSellerList.marketingUnit.id", marketSet,CompareType.INCLUDE));
					thisFilter.setMaskString("#0 or #1 or #2");
				} else {
					thisFilter.setMaskString("#0 or #1");
				}
			}

			//需要增加项目隔离
			FilterInfo addSpFilter = addSeparateBySellProject(node);
			thisFilter.mergeFilter(addSpFilter, "and");			
			
			if (viewInfo.getFilter() != null) {
				thisFilter.mergeFilter(viewInfo.getFilter(), "AND");
			}
			viewInfo.setFilter(thisFilter);
			
		
		} catch (Exception e) {
			this.handleException(e);
		}

		// 根据过滤tia
		CustomerTypeEnum customerType = ((FDCCustomerFullFilterUI) this.getFilterUI()).getCustomerType();
		boolean isEnterCus = CustomerTypeEnum.EnterpriceCustomer.equals(customerType);

		this.tblMain.getColumn("sex").getStyleAttributes().setHided(isEnterCus);
		this.tblMain.getColumn("famillyEarning.name").getStyleAttributes().setHided(isEnterCus);
		this.tblMain.getColumn("employment").getStyleAttributes().setHided(isEnterCus);
		this.tblMain.getColumn("workCompany").getStyleAttributes().setHided(isEnterCus);

//		this.tblMain.getColumn("enterpriceProperty").getStyleAttributes().setHided(!isEnterCus);
//		this.tblMain.getColumn("enterpriceSize").getStyleAttributes().setHided(!isEnterCus);
//		this.tblMain.getColumn("enterpriceHomepage").getStyleAttributes().setHided(!isEnterCus);
//		this.tblMain.getColumn("industry.name").getStyleAttributes().setHided(!isEnterCus);
		//和李方波，金志诚确认，由于性能问题将是否认购字段注释
		this.tblMain.getColumn("isSub").getStyleAttributes().setHided(true);
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	
	//若选择的节点非当前用户，则必须增加项目隔离
	private FilterInfo addSeparateBySellProject(DefaultMutableTreeNode thisNode) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();	
		String spFilterString = "";
			if(thisNode==null || thisNode.getUserObject() instanceof OrgStructureInfo){
				spFilterString = CommerceHelper.getPermitProjectIdSql(userInfo);
			}else if(thisNode.getUserObject() instanceof MarketingUnitInfo){//选择单元或人节点的情况
				MarketingUnitInfo marketingInfo = (MarketingUnitInfo)thisNode.getUserObject();
				spFilterString = "select FSellProjectID from T_TEN_MarketingUnitSellProject where fHeadId ='"+marketingInfo.getId().toString()+"'";				
			}else if(thisNode.getUserObject() instanceof UserInfo){
				UserInfo nodeUser = (UserInfo)thisNode.getUserObject();
				if(!nodeUser.getId().toString().equals(userInfo.getId().toString())) {
					MarketingUnitInfo marketingUnit = (MarketingUnitInfo)((DefaultKingdeeTreeNode)thisNode.getParent()).getUserObject();
					spFilterString = "select FSellProjectID from T_TEN_MarketingUnitSellProject where fHeadId ='"+marketingUnit.getId().toString()+"'";
				}
			}
			
			if(!spFilterString.equals(""))
				filter.getFilterItems().add(new FilterItemInfo("project.id",spFilterString,CompareType.INNER));
			return filter;
	}
	
	

	/**
	 * 查询节点下所有的营销人员
	 * 
	 * @param treeNode
	 */
	private void getAllSaleIds(DefaultMutableTreeNode treeNode) {
		if (treeNode.isLeaf()) { // DefaultMutableTreeNode
			// DefaultKingdeeTreeNode
			DefaultMutableTreeNode thisNode = (DefaultMutableTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof UserInfo) {
				UserInfo user = (UserInfo) thisNode.getUserObject();
				saleUserIds += "," + user.getId().toString();
			}
		} else {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllSaleIds((DefaultMutableTreeNode) treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getMarketTree(this.actionOnLoad));
		// this.treeMain.setModel(SHEHelper.getMarketTree(this.actionOnLoad));

		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (confirmDialog("是否确认作废选中数据？")) {
			FDCCustomerFactory.getRemoteInstance().blankOut(getSelectedIdValues());
			CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
			refresh(e);
		}
	}

	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		FDCCustomerFactory.getRemoteInstance().pickUp(getSelectedIdValues());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		refresh(e);
	}

	protected String getQueryUiName() {
		return super.getQueryUiName();
	}

	// protected void checkIsOUSealUp() throws Exception {
	// }

	protected String getEditUIName() {
		return CustomerEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCCustomerFactory.getRemoteInstance();
	}

	/**
	 * 是否显示全部，针对是否已废弃
	 * 
	 * @deprecated 全部由过滤界面过滤
	 */
	protected void chkShowAll_actionPerformed(ActionEvent e) throws Exception {
		super.chkShowAll_actionPerformed(e);
		// if (this.chkShowAll.isSelected()) {
		// // MsgBox.showInfo("全部");
		// showAll = true;
		// this.mainQuery = getEntityViewInfo(mainQuery);
		// refreshList();
		// } else {
		// // MsgBox.showInfo("非全部");
		// showAll = false;
		// this.mainQuery = getEntityViewInfo(mainQuery);
		// refreshList();
		// }
	}

	/**
	 * 标识为重点跟进
	 */
	public void actionImportantTrack_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportantTrack_actionPerformed(e);
		this.checkSelected();
		FDCCustomerFactory.getRemoteInstance().signImportantTrack(getSelectedIdValues());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		refresh(e);
	}

	/**
	 * 反标识重点跟进
	 */
	public void actionCancelImportantTrack_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancelImportantTrack_actionPerformed(e);
		this.checkSelected();
		FDCCustomerFactory.getRemoteInstance().cancelImportantTrack(getSelectedIdValues());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		refresh(e);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
	}

	/**
	 * 修改名字和电话号码
	 */
	public void actionModifyCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.actionModifyCustomer_actionPerformed(e);

		checkSelected();

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		IRow row = this.tblMain.getRow(selectRows[0]);
		String id = (String) row.getCell("id").getValue();

		if (id == null)
			return;

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("customer.id", id));
		// 如果在认购单客户信息存在关联该客户记录,则姓名不可修改
		boolean exist = PurchaseCustomerInfoFactory.getRemoteInstance().exists(filter);
		if (exist) {
			MsgBox.showInfo("认购单中存在该客户记录，姓名不可修改！");
			return;
		}

		UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("ListUI", this);// 为了在EditUi完成操作后对ListUI进行刷新，将ListUI对象传入
		uiContext.put("ActionCommand", "modifyName");

		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ModifyCustomerNameUI.class.getName(), uiContext, null, "EDIT");
		uiWindow.show();
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
	}

	public void actionModifyPhone_actionPerformed(ActionEvent e) throws Exception {
		super.actionModifyPhone_actionPerformed(e);

		String idStr = this.getSelectedKeyValue();
		if (idStr == null)
			return;

		UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("ListUI", this);// 为了在EditUi完成操作后对ListUI进行刷新，将ListUI对象传入
		uiContext.put("ActionCommand", "modifyPhone");

		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ModifyCustomerNameUI.class.getName(), uiContext, null, "EDIT");
		uiWindow.show();
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
	}

	/**
	 * 导入菜单
	 * */
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportData_actionPerformed(e);
		UIContext uiContext = new UIContext(ui);

		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ImportExcelFDCCustomerUI.class.getName(), uiContext, null);
		uiWindow.show();
	}

	public void actionToSysCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.actionToSysCustomer_actionPerformed(e);
		this.checkSelected();
		// String cusId = this.getSelectedKeyValue();
		// 支持再同步
		// FDCCustomerInfo customer = FDCCustomerFactory.getRemoteInstance()
		// .getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(cusId)));
		// if (customer.getSysCustomer() != null) {
		// MsgBox.showInfo("已经关联了系统客户!");
		// return;
		// }

		// 简单实现批量同步功能,循环调用远程方法．．
		Map sortStandardMap = setting.getSortStandardMap();
		Iterator it = sortStandardMap.values().iterator();
		List sortStandardList = new ArrayList();
		while (it.hasNext()) {
			CSSPGroupInfo cssInfo = (CSSPGroupInfo) it.next();
			if (cssInfo != null) {
				// 分类集合
				sortStandardList.add(cssInfo.getId().toString());
			}
		}
		ArrayList selectIds = getSelectedIdValues();
		for (int i = 0; i < selectIds.size(); i++) {
			String cusId = (String) selectIds.get(i);
			FDCCustomerFactory.getRemoteInstance().addToSysCustomer(cusId, sortStandardList);
		}
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		MsgBox.showInfo("同步成功!");
	}

	public void actionSwitchTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionSwitchTo_actionPerformed(e);
		this.checkSelected();

		UIContext uiContext = new UIContext(ui);
		uiContext.put("ListUI", this);
		uiContext.put("SelectedIdValues", this.getSelectedIdValues());

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerSwitchToEditUI.class.getName(), uiContext, null);
		uiWindow.show();
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
	}

	/**
	 * 转接
	 * 
	 * @throws Exception
	 * */
	public void actionCustomerAdapter_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		
		int selectedRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0;i<selectedRows.length;i++) {
			IRow row = this.tblMain.getRow(selectedRows[i]);
			String custIdStr = (String)row.getCell("id").getValue();
			if(!CustomerAdapterUI.hasAdpaterPermission(custIdStr))  {
				MsgBox.showInfo("您对第"+(row.getRowIndex()+1) + "行客户没有转接权限，不能转接!");
				return;
			}
		}
		
		List idList = this.getSelectedIdValues();
		CustomerAdapterUI.showUI(this, idList);

		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * 共享
	 * 
	 * @throws Exception
	 * */
	public void actionCustomerShare_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		
		int selectedRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0;i<selectedRows.length;i++) {
			IRow row = this.tblMain.getRow(selectedRows[i]);
			String custIdStr = (String)row.getCell("id").getValue();
			if(!CustomerShareUI.hasSharePermission(custIdStr))  {
				MsgBox.showInfo("您对第"+(row.getRowIndex()+1) + "行客户没有共享权限，不能共享!");
				return;
			}
		}
		
		List idList = this.getSelectedIdValues();
		CustomerShareUI.showUI(this, idList);

		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * 取消共享
	 * */
	public void actionCustomerCancelShare_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		
		int selectedRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0;i<selectedRows.length;i++) {
			IRow row = this.tblMain.getRow(selectedRows[i]);
			String custIdStr = (String)row.getCell("id").getValue();
			if(!CustomerShareUI.hasSharePermission(custIdStr))  {
				MsgBox.showInfo("您对第"+(row.getRowIndex()+1) + "行客户没有共享权限，不能取消共享!");
				return;
			}
		}
		
		String customerID = this.getSelectedKeyValue();
		CustomerCancelShareUI.showEditUI(this, customerID);

		this.actionRefresh_actionPerformed(e);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof UserInfo) {
			UserInfo userInfo = (UserInfo) node.getUserObject();
			uiContext.put("userInfo", userInfo);
			
			if(((DefaultKingdeeTreeNode) node.getParent()).getUserObject() instanceof MarketingUnitInfo)
			{
			MarketingUnitInfo marketingUnit = (MarketingUnitInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			uiContext.put("marketingUnit", marketingUnit);
			}
		}
		if (node.getUserObject() instanceof MarketingUnitInfo) {
			MarketingUnitInfo marketingUnit = (MarketingUnitInfo) node.getUserObject();
			uiContext.put("marketingUnit", marketingUnit);
		}
//		marketNode = (TreeNode) this.treeMain.getLastSelectedPathComponent();
		marketNode=(TreeNode)this.treeMain.getModel().getRoot();    
     	uiContext.put("MarketingNode",marketNode);
	}

	public void actionAddTrackRecord_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddTrackRecord_actionPerformed(e);
		String selectId = this.getSelectedKeyValue();
		if (selectId == null) {
			MsgBox.showInfo("请先选择要跟进的用户!");
			return;
		}

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		String idStr = (String) this.tblMain.getRow(selectRows[0]).getCell("id").getValue();
		if (idStr == null)
			return;

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("project.*"));
		FDCCustomerInfo customer = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(idStr)), selector);
		if (customer != null) {
			// 打开新增跟踪记录界面
			UIContext uiContext = new UIContext(this);
			uiContext.put("FdcCustomer", customer);
			uiContext.put("SellProject", customer.getProject());

			if (customer.isIsForSHE())
				uiContext.put("CommerceSysTypeEnum", MoneySysTypeEnum.SalehouseSys);
			else if (customer.isIsForTen())
				uiContext.put("CommerceSysTypeEnum", MoneySysTypeEnum.TenancySys);

			try {
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TrackRecordEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
				uiWindow.show();
			} catch (UIException ee) {
				ee.printStackTrace();
			}
		}

	}

	/**
	 * 客户删除： 1.只有作废的客户才能删除 2.关联认购单的不能删除 3.关联诚意认购的不能删除 4.关联了认租合同不能删除
	 * 5.已同步系统客户的将系统客户同时删除
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List selectedIds = this.getSelectedIdValues();

		Set cusIds = FDCHelper.list2Set(selectedIds);

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", cusIds, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		if (FDCCustomerFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo(this, "只有作废的客户才能删除！");
			return;
		}

		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("customer.id", cusIds, CompareType.INCLUDE));
		if (SincerityPurchaseFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo(this, "有客户参与了诚意认购，不能删除！");
			return;
		}

		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("customer.id", cusIds, CompareType.INCLUDE));
		if (PurchaseCustomerInfoFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo(this, "有客户参与了认购，不能删除！");
			return;
		}

		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id", cusIds, CompareType.INCLUDE));
		if (TenancyCustomerEntryFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo(this, "有客户参与了租赁，不能删除！");
			return;
		}

		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id", cusIds, CompareType.INCLUDE));
		if (TrackRecordFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo(this, "有客户已有跟进记录，不能删除！");
			return;
		}

		if (confirmRemove()) {
			Remove();
			actionRefresh_actionPerformed(e);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		
	}
	
	/**
	 * 初始化认购状态 
	 * @author liang_ren969
	 * @date 2010-6-13 
	 */
	private void fetchInitData(List idList){
		
		//得到query中的用户id列表
		//List idList = super.getQueryPkList();
		
		if(idList!=null && idList.size()>0){
			try {
				IFDCCustomer customer = FDCCustomerFactory.getRemoteInstance();
				customer.setStatus(idList);
			} catch (BOSException e) {
				logger.equals(e.getMessage());
			}finally{
				this.tblMain.removeRows();
				this.execQuery();
			}
			
		}

	}
	
	/**
	 * 从写了父类中的refresh方法
	 * @author liang_ren969
	 * @date 2010-6-21
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		//fetchInitData(idList);	极影响吸能，暂时去掉，日后改进
		super.actionRefresh_actionPerformed(e);
	}

	/**
	 * 重新计算售楼交易次数<br>
	 * 以客户关联的认购单中单据状态为“认购申请、认购审批中、认购审批、认购变更”<br>
	 * 且认购单关联的房间为“认购”OR“签约状态”的认购单的个数<br>
	 * 亿达需求 add by jiyu_guan
	 */
	private void resetTradeTimes() {
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" update T_SHE_FDCCustomer as cs set FTradeTime = ( ");
		sql.appendSql(" select count(pur.FID) from T_SHE_Purchase as pur ");
		sql.appendSql(" left join T_SHE_Room as rm on rm.FID = pur.FRoomID ");
		sql
				.appendSql(" left join T_SHE_PurchaseCustomerInfo as pc on pc.FHeadID = pur.FID ");
		sql
				.appendSql(" where pur.FPurchaseState in ('PurchaseApply','PurchaseAuditing','PurchaseAudit','PurchaseChange') ");
		sql.appendSql(" and rm.FSellState in ('Purchase','Sign') ");
		sql.appendSql(" and cs.FID = pc.FCustomerID ) ");
		try {
			sql.execute();
		} catch (BOSException e) {
			handUIException(e);
		}
	}
	
	public void actionQuestionPrint_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		UIContext uiContext = new UIContext(this);
		EntityViewInfo view=new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",getSelectedKeyValue()));
		view.setSelector(sic);
		view.setFilter(filter);
		FDCCustomerInfo info=FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view).get(0);
		uiContext.put("fdcCustomer", info);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow curDialog = uiFactory.create(QuestionPaperAnswerEditUI.class
				.getName(), uiContext, null, OprtState.ADDNEW);
		curDialog.show();
	}
}