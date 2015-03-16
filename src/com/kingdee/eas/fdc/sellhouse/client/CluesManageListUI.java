/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageCollection;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CluesStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SharePropertyCollection;
import com.kingdee.eas.fdc.sellhouse.SharePropertyFactory;
import com.kingdee.eas.fdc.sellhouse.SharePropertyInfo;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * output class name
 */
public class CluesManageListUI extends AbstractCluesManageListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CluesManageListUI.class);

	// 是否有编码规则
	boolean existNumberRule = CommerceHelper
			.isExistNumberRule(new CluesManageInfo());
	// 用来判断是否为售楼组织
	private Map map = FDCSysContext.getInstance().getOrgMap();
	protected Map permit=new HashMap();
	protected boolean isControl=SHEManageHelper.isControl(null, SysContext.getSysContext().getCurrentUserInfo());

	/**
	 * output class constructor
	 */
	public CluesManageListUI() throws Exception {
		super();
	}

	public void onShow() throws Exception {
		super.onShow();
		getMainTable().setColumnMoveable(true);
		// 启用禁用按钮不显示
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionImport.setEnabled(false);
			this.actionFollow.setEnabled(false);
		}
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionShare.setEnabled(true);
		this.actionDeliver.setEnabled(true);
		this.actionQuestionPrint.setEnabled(true);
		this.actionCustomer.setEnabled(true);
		this.actionCommerceChance.setEnabled(true);
		this.actionFollow.setEnabled(true);
		this.btnFollow.setIcon(EASResource.getIcon("imgTbtn_follow"));
		
		this.btnCommerceChance.setVisible(false);
		this.btnTrade.setVisible(false);
		this.btnQuestionPrint.setVisible(false);
		this.btnShare.setVisible(false);
		
		this.menuItemShare.setVisible(false);
		this.menuQuestionPrint.setVisible(false);
		this.menuTrade.setVisible(false);
		this.menuItemCommerceChance.setVisible(false);

		KDMenuItem menuItem1 = new KDMenuItem();
		menuItem1.setAction(this.actionSincerity);
		menuItem1.setText("预约排号");
		menuItem1.setIcon(EASResource.getIcon("imgTree_businessgroup"));
		this.btnTrade.addAssistMenuItem(menuItem1);

		KDMenuItem menuItem2 = new KDMenuItem();
		menuItem2.setAction(this.actionPrePurchase);
		menuItem2.setText("预定");
		menuItem2.setIcon(EASResource.getIcon("imgTbtn_balancecheck"));
		this.btnTrade.addAssistMenuItem(menuItem2);

		KDMenuItem menuItem3 = new KDMenuItem();
		menuItem3.setAction(this.actionPurchase);
		menuItem3.setText("认购");
		menuItem3.setIcon(EASResource.getIcon("imgTbtn_scatterpurview"));
		this.btnTrade.addAssistMenuItem(menuItem3);

		KDMenuItem menuItem4 = new KDMenuItem();
		menuItem4.setAction(this.actionSign);
		menuItem4.setText("签约");
		menuItem4.setIcon(EASResource.getIcon("imgTbtn_signup"));
		this.btnTrade.addAssistMenuItem(menuItem4);

		this.btnCustomer.setIcon(EASResource.getIcon("imgTbtn_addgroup"));

		this.menuItemShare.setIcon(EASResource.getIcon("imgTbtn_sealup"));
		this.menuItemDeliver.setIcon(EASResource.getIcon("imgTbtn_deliverto"));
		this.menuQuestionPrint.setIcon(EASResource
				.getIcon("imgTbtn_addcredence"));
		this.menuItemFollow.setIcon(EASResource.getIcon("imgTbtn_follow"));
		this.menuItemCustomer.setIcon(EASResource.getIcon("imgTbtn_addgroup"));
		menuItemCommerceChance.setIcon(EASResource
				.getIcon("imgTbtn_synchronization"));
		menuTrade.setIcon(EASResource.getIcon("imgTbtn_conversionsave"));
		menuItemSincerity.setIcon(EASResource.getIcon("imgTree_businessgroup"));
		menuItemPrePurchase
				.setIcon(EASResource.getIcon("imgTbtn_balancecheck"));
		menuItemPurchase.setIcon(EASResource.getIcon("imgTbtn_scatterpurview"));
		menuItemSign.setIcon(EASResource.getIcon("imgTbtn_signup"));
		menuItemImport.setIcon(EASResource.getIcon("imgTbtn_importexcel"));

		actionSincerity.setEnabled(true);
		actionPrePurchase.setEnabled(true);
		actionPurchase.setEnabled(true);
		actionSign.setEnabled(true);
        
		initTree();
        //设置日期格式
		if (tblMain.getColumn("createTime") != null) {
			tblMain.getColumn("createTime").getStyleAttributes()
					.setNumberFormat("yyyy-MM-dd");
		}
	}

	/**
	 * 初始化树
	 * @throws Exception
	 */
	protected void initTree() throws Exception {
		this.treeMain.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad,
				false));
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		treeMain.setSelectionRow(0); // 默认选择根节点
	}

	/**
	 * 定位
	 */
	protected String[] getLocateNames() {
		return new String[] { "number", "name", "phone" };
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (orgNode != null && orgNode.getUserObject() != null) {
			if (orgNode.getUserObject() instanceof OrgStructureInfo) {
				this.tblMain.removeRows(false);
				this.actionAddNew.setEnabled(false);
				return;
			} else {
				this.execQuery();
			}
		}
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();
			if (orgNode != null) {
				if (orgNode.getUserObject() != null
						&& orgNode.getUserObject() instanceof OrgStructureInfo) {
					// 取得选定组织下的所有id
//					String allSpIdStr = FDCTreeHelper
//							.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(
//									orgNode, "SellProject").keySet());
//					if (allSpIdStr.trim().length() == 0) {
//						allSpIdStr = "'nullnull'";
//					}
//					filter.getFilterItems().add(
//							new FilterItemInfo("sellProject.id", allSpIdStr,
//									CompareType.INNER));
//					filter.getFilterItems().add(
//							new FilterItemInfo("propertyConsultant.id",
//									getUserIdSet(), CompareType.INCLUDE));
					
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'nullnull'",CompareType.INNER));
					this.actionAddNew.setEnabled(false);
					this.actionEdit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				} else if (orgNode.getUserObject() != null
						&& orgNode.getUserObject() instanceof SellProjectInfo) {
					SellProjectInfo sellProject = getSellProject();
//					Set idRow = new HashSet();
//					idRow = getCluesId(sellProject);
//					if (idRow != null && idRow.size() > 0) {
//						filter.getFilterItems().add(
//								new FilterItemInfo("id", idRow,
//										CompareType.INNER));
//					} else {
//						filter.getFilterItems().add(
//								new FilterItemInfo("id", null,
//										CompareType.EQUALS));
//					}
					if(!isControl){
						Set saleMan=SHEManageHelper.getPermitSaleManSet(sellProject,permit);
						filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", saleMan,CompareType.INNER));
					}
					if (!map.containsKey(SysContext.getSysContext()
							.getCurrentOrgUnit().getId().toString())) {
						this.actionAddNew.setEnabled(false);
						this.actionEdit.setEnabled(false);
						this.actionRemove.setEnabled(false);
						this.actionImport.setEnabled(false);
					} else {
						// 只能在项目的根节点下新增
						if (sellProject.getParent() != null
								&& sellProject.getParent().getId() != null) {
							this.actionAddNew.setEnabled(false);
						} else {
							this.actionAddNew.setEnabled(true);
						}
						this.actionEdit.setEnabled(true);
						this.actionRemove.setEnabled(true);
						this.actionImport.setEnabled(true);
					}
				}
			}else{
				filter.getFilterItems().add(new FilterItemInfo("id", "null"));
			}
			viewInfo = (EntityViewInfo) mainQuery.clone();
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	/**
	 * 满足条件的置业顾问集合
	 * 
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private Set getUserIdSet(SellProjectInfo sellProject) throws BOSException, EASBizException {
		Set idSet = new HashSet();
		EntityViewInfo view = NewCommerceHelper.getPermitSalemanView(sellProject,false);
		UserCollection userColl = UserFactory.getRemoteInstance()
				.getUserCollection(view);
		if (userColl != null && userColl.size() > 0) {
			for (int i = 0; i < userColl.size(); i++) {
				idSet.add(userColl.get(i).getId());
			}
		}
		// 添加自己
		UserInfo currentInfo = SysContext.getSysContext().getCurrentUserInfo();
		idSet.add(currentInfo.getId());
		return idSet;
	}

	/**
	 * 得到项目根节点
	 * @return
	 */
	private SellProjectInfo getSellProject() {
		SellProjectInfo sellInfo = null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			DefaultKingdeeTreeNode nodeTemp = getParentNode(node);
			if (nodeTemp != null) {
				sellInfo = (SellProjectInfo) nodeTemp.getUserObject();
			}
		}
		return sellInfo;
	}

	/**
	 * 得到根节点
	 * @param node
	 * @return
	 */
	private DefaultKingdeeTreeNode getParentNode(DefaultKingdeeTreeNode node) {
		if (node == null) {
			return null;
		}
		if (node.getParent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) node
					.getParent();
			if (treeNode.getUserObject() instanceof SellProjectInfo) {
				return getParentNode(treeNode);
			} else {
				return node;
			}
		}
		return null;
	}

	/**
	 * 查询满足条件的记录 当前置业顾问、所选项目、共享到当前置业顾问、共享到当前项目
	 * 
	 * @param sellProjectInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private Set getCluesId(SellProjectInfo sellProjectInfo)
			throws BOSException, EASBizException {
		Set rs = new HashSet();
		Set propertyConsultantSet = this.getUserIdSet(sellProjectInfo);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("user.id", propertyConsultantSet,
						CompareType.INCLUDE));
		view.setFilter(filter);
		SharePropertyCollection shareProColl = SharePropertyFactory
				.getRemoteInstance().getSharePropertyCollection(view);
		Set propertyCluesSet = new HashSet();
		if (shareProColl != null && shareProColl.size() > 0) {
			for (int i = 0; i < shareProColl.size(); i++) {
				SharePropertyInfo info = (SharePropertyInfo) shareProColl
						.get(i);
				if (info.getProperty() != null) {
					propertyCluesSet.add(info.getProperty().getId());
				}
			}
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("id", propertyCluesSet,
								CompareType.INCLUDE));
		filter.getFilterItems().add(
				new FilterItemInfo("sellProject.id", sellProjectInfo.getId(),
						CompareType.EQUALS));
		view.setFilter(filter);
		CluesManageCollection colls = CluesManageFactory.getRemoteInstance()
				.getCluesManageCollection(view);
		if (colls != null && colls.size() > 0) {
			for (int i = 0; i < colls.size(); i++) {
				rs.add(colls.get(i).getId());
			}
		}

		// 共享项目的
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sellProject.id", sellProjectInfo.getId(),
						CompareType.EQUALS));
		view.setFilter(filter);
		ShareSellProjectCollection projectColl = ShareSellProjectFactory
				.getRemoteInstance().getShareSellProjectCollection(view);
		Set projectCluesSet = new HashSet();
		if (projectColl != null && projectColl.size() > 0) {
			for (int i = 0; i < projectColl.size(); i++) {
				ShareSellProjectInfo info = (ShareSellProjectInfo) projectColl
						.get(i);
				if (info.getShareProject() != null) {
					projectCluesSet.add(info.getShareProject().getId());
				}
			}
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", projectCluesSet, CompareType.INCLUDE));
		filter.getFilterItems().add(
				new FilterItemInfo("propertyConsultant.id",
						propertyConsultantSet, CompareType.INCLUDE));
		view.setFilter(filter);
		CluesManageCollection coll = CluesManageFactory.getRemoteInstance()
				.getCluesManageCollection(view);
		if (projectColl != null && coll.size() > 0) {
			for (int i = 0; i < coll.size(); i++) {
				rs.add(coll.get(i).getId());
			}
		}

		// 当前置业顾问，当前项目的
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("propertyConsultant.id",
						propertyConsultantSet, CompareType.INCLUDE));
		filter.getFilterItems().add(
				new FilterItemInfo("sellProject.id", sellProjectInfo.getId(),
						CompareType.EQUALS));
		view.setFilter(filter);
		CluesManageCollection cluesColl = CluesManageFactory
				.getRemoteInstance().getCluesManageCollection(view);
		if (cluesColl != null && cluesColl.size() > 0) {
			for (int i = 0; i < cluesColl.size(); i++) {
				rs.add(cluesColl.get(i).getId());
			}
		}
		return rs;
	}

	/**
	 * 返回选中的行
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private CluesManageInfo getSelectInfo() throws EASBizException,
			BOSException {
		List idList = this.getSelectedIdValues();
		if (idList == null) {
			FDCMsgBox.showWarning(this, "请先选择行记录!");
		}
		if (idList.size() > 0 && idList.get(0) != null) {
			CluesManageInfo info = CluesManageFactory.getRemoteInstance()
					.getCluesManageInfo(
							new ObjectUuidPK(idList.get(0).toString()));
			return info;
		} else {
			return null;
		}
	}

	/**
	 * 新增
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			Object object = node.getUserObject();
			if (object != null && object instanceof SellProjectInfo) {
				SellProjectInfo sellProject = getSellProject();
				getUIContext().put("info", sellProject);
			}
		} else {
			MsgBox.showInfo("请选择节点!");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * 修改
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		super.actionEdit_actionPerformed(e);
		this.refresh(e);
	}

	/**
	 * 查看
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		super.actionView_actionPerformed(e);
		this.refresh(e);
	}

	/**
	 * 删除
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			CluesManageInfo info = CluesManageFactory.getRemoteInstance().getCluesManageInfo("select id, cluesStatus where id='" + id.get(i).toString() + "'");
			if (info.getCluesStatus() != null&& !info.getCluesStatus().equals("")) {
				FDCMsgBox.showWarning(this, "本记录已转商机或交易，不能删除!");
				this.abort();
			}

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("clues.id", id.get(i).toString()));

			if (CommerceChanceTrackFactory.getRemoteInstance().exists(filter)) {
				FDCMsgBox.showWarning(this, "已有跟进，不能删除!");
				this.abort();
			}
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("cluesManage", id.get(i).toString()));
			if (QuestionPaperAnswerFactory.getRemoteInstance().exists(filter)) {
				FDCMsgBox.showWarning(this, "已有问卷，不能删除!");
				this.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * 共享
	 */
	public void actionShare_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = this.getSelectedIdValues();

		CluesManageInfo info = this.getSelectInfo();
		if (info != null) {
			SellProjectInfo sellProject = info.getSellProject();
			CluesCustomerShareEditUI.showUI(this, idList, sellProject);
		}
		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * 转交
	 * 
	 * @throws Exception
	 * */
	public void actionDeliver_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		List idList = this.getSelectedIdValues();
		CluesManageInfo info = this.getSelectInfo();
		if (info != null) {
			SellProjectInfo sellProject = info.getSellProject();
			CluesCustomerDeliverEditUI.showUI(this, idList, sellProject);
		}
		this.refreshList();
	}

	/**
	 * 填写问卷
	 */
	public void actionQuestionPrint_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		showEditUI(null, QuestionPaperAnswerEditUI.class.getName());
	}

	/**
	 * 跟进
	 * 
	 * @throws Exception
	 * */
	public void actionFollow_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		showEditUI(null, CluesManageTrakcEditUI.class.getName());

	}

	/**
	 * 转客户
	 */
	public void actionCustomer_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		CluesManageInfo cluesInfo = getSelectInfo();
		if (cluesInfo != null && cluesInfo.getCluesStatus() != null) {
			if (cluesInfo.getCluesStatus().equals(CluesStatusEnum.CUSTOMER)) {
				MsgBox.showInfo("已转客户不允许重复操作！");
				return;
			} else if (cluesInfo.getCluesStatus().equals(
					CluesStatusEnum.COMMERCECHANCE)) {
				MsgBox.showInfo("已转商机不允许重复操作！");
				return;
			} else {
				MsgBox.showInfo("已转交易不允许重复操作！");
				return;
			}
		} else {
			showEditUI(CluesStatusEnum.CUSTOMER, CluesCustomerUI.class
					.getName());
		}
		this.refreshList();
	}

	/**
	 * 转商机,同时生成客户资料 当转商机后状态标记为 '转商机' 先转客户，若保存，再转商机
	 */
	public void actionCommerceChance_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		CluesManageInfo cluesInfo = getSelectInfo();
		if (cluesInfo != null && cluesInfo.getCluesStatus() != null) {
			if (cluesInfo.getCluesStatus().equals(
					CluesStatusEnum.COMMERCECHANCE)) {
				MsgBox.showInfo("已转商机不允许重复操作！");
				return;
			} else if (cluesInfo.getCluesStatus().equals(
					CluesStatusEnum.CUSTOMER)) {
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("id");
				sic.add("name");
				sic.add("number");
				sic.add("status");
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("cluesCustomer.id",
								getSelectedKeyValue()));
				view.setSelector(sic);
				view.setFilter(filter);
				CommerceChanceCollection coll = CommerceChanceFactory
						.getRemoteInstance().getCommerceChanceCollection(view);
				if (coll != null && coll.size() > 0) {
					for (int i = 0; i < coll.size(); i++) {
						CommerceChanceInfo commerceChanceInfo = coll.get(i);
						if (CommerceChangeNewStatusEnum.ACTIVE
								.equals(commerceChanceInfo.getStatus()))
							;
						MsgBox.showInfo("商机已经存在，不允许重复建立！");
						return;
					}
				}
				showEditUI(CluesStatusEnum.COMMERCECHANCE,
						CommerceChangeNewEditUI.class.getName());
			} else {
				MsgBox.showInfo("已转交易不允许重复操作！");
				return;
			}
		} else {
			showEditUI(CluesStatusEnum.COMMERCECHANCE, CluesCustomerUI.class
					.getName());
		}
		this.refreshList();
	}

	/**
	 * 预约排号
	 */
	public void actionSincerity_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		showEditUI(null, SincerityPurchaseChangeNameUI.class.getName());
		this.refreshList();
	}

	/**
	 * 预定
	 */
	public void actionPrePurchase_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		actionTrade(CluesStatusEnum.PREPURCHASE, PrePurchaseManageEditUI.class
				.getName());
		this.refreshList();
	}

	/**
	 * 认购
	 */
	public void actionPurchase_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		actionTrade(CluesStatusEnum.PURCHASE, PurchaseManageEditUI.class
				.getName());
		this.refreshList();
	}

	/**
	 * 签约
	 */
	public void actionSign_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		actionTrade(CluesStatusEnum.SIGN, SignManageEditUI.class.getName());
		this.refreshList();
	}

	/**
	 * 转交易，各功能调用
	 * 预定,认购,签约
	 * @param status
	 * @param name
	 * @throws Exception
	 */
	private void actionTrade(CluesStatusEnum status, String name)
			throws Exception {
		CluesManageInfo cluesInfo = getSelectInfo();
		SellProjectInfo sellProject = cluesInfo.getSellProject();
		if (sellProject != null) {
			sellProject = SellProjectFactory.getRemoteInstance()
					.getSellProjectInfo(new ObjectUuidPK(sellProject.getId()));
		}
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		FilterInfo filter = new FilterInfo();
		if (cluesInfo != null && cluesInfo.getCluesStatus() != null) {
			if (cluesInfo.getCluesStatus().equals(
					CluesStatusEnum.COMMERCECHANCE)) {
				view = new EntityViewInfo();
				sic = new SelectorItemCollection();
				sic.add("id");
				sic.add("number");
				sic.add("phone");
				sic.add("*");
				filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("clues.id", cluesInfo.getId()));
				view.setSelector(sic);
				view.setFilter(filter);

				SHECustomerInfo customerInfo = (SHECustomerInfo) SHECustomerFactory
						.getRemoteInstance().getDataBaseCollection(view).get(0);

				List customerList = new ArrayList();
				customerList.add(customerInfo);
				SHEManageHelper.toTransactionBill(cluesInfo.getId(),
						sellProject, this, name, customerList);

			} else if (cluesInfo.getCluesStatus().equals(
					CluesStatusEnum.CUSTOMER)) {
				view = new EntityViewInfo();
				sic = new SelectorItemCollection();
				sic.add("id");
				sic.add("name");
				sic.add("number");
				sic.add("commerceStatus");
				filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("cluesCustomer.id",
								getSelectedKeyValue()));
				view.setSelector(sic);
				view.setFilter(filter);
				CommerceChanceCollection coll = CommerceChanceFactory
						.getRemoteInstance().getCommerceChanceCollection(view);
				if (coll != null && coll.size() > 0) {
					for (int i = 0; i < coll.size(); i++) {
						CommerceChanceInfo commerceChanceInfo = coll.get(i);
						if (CommerceChangeNewStatusEnum.ACTIVE
								.equals(commerceChanceInfo.getStatus()))
							;
						MsgBox.showInfo("商机已经存在，不允许重复建立！");
						return;
					}
				}

				view = new EntityViewInfo();
				sic = new SelectorItemCollection();
				sic.add("id");
				sic.add("number");
				sic.add("phone");
				sic.add("*");
				filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("clues.id", cluesInfo.getId()));
				view.setSelector(sic);
				view.setFilter(filter);
				SHECustomerInfo customerInfo = (SHECustomerInfo) SHECustomerFactory
						.getRemoteInstance().getDataBaseCollection(view).get(0);
				List customerList = new ArrayList();
				customerList.add(customerInfo);
				SHEManageHelper.toTransactionBill(cluesInfo.getId(),
						sellProject, this, name, customerList);
			} else {
				MsgBox.showInfo("已转交易不允许重复操作！");
				return;
			}
		} else {
			showEditUI(status, CluesCustomerUI.class.getName());
		}
	}

	/**
	 * 打开各功能新增界面,供各功能调用
	 * 
	 * @param status
	 * @param name
	 * @param isAdd
	 * @throws Exception
	 */
	public void showEditUI(CluesStatusEnum status, String name)
			throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("phone");
		sic.add("cluesStatus");
		sic.add("sellProject.*");
		sic.add("propertyConsultant.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", getSelectedKeyValue()));
		view.setSelector(sic);
		view.setFilter(filter);

		CluesManageInfo info = CluesManageFactory.getRemoteInstance()
				.getCluesManageCollection(view).get(0);
		SHECustomerInfo customerInfo = new SHECustomerInfo();
		if (info.getCluesStatus() != null
				&& info.getCluesStatus().equals(CluesStatusEnum.CUSTOMER)) {
			view = new EntityViewInfo();
			sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("number");
			sic.add("phone");
			sic.add("*");
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("clues.id", info.getId()));
			view.setSelector(sic);
			view.setFilter(filter);
			customerInfo = (SHECustomerInfo) SHECustomerFactory
					.getRemoteInstance().getDataBaseCollection(view).get(0);
		}

		UIContext uiContext = new UIContext(this);
		uiContext.put("id", info.getId());
		uiContext.put("number", info.getNumber());
		uiContext.put("cluesCustomer", info);
		uiContext.put("sheCustomer", customerInfo);
		uiContext.put("sellProject", info.getSellProject());
		uiContext.put("status", status);
		uiContext.put("propertyConsultant", info.getPropertyConsultant());
		//与直接新增客户一样
		uiContext.put("addnewDerict", "derict");

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(name, uiContext, null, "ADDNEW");
		uiWindow.show();
	}
	
	/**
	 * 导入
	 */
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		super.actionImport_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		SellProjectInfo sellProjectInfo = null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			Object object = node.getUserObject();
			if (object != null && object instanceof SellProjectInfo) {
				sellProjectInfo = getSellProject();
			}
		}
		uiContext.put("sellProject", sellProjectInfo);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CluesManageImportUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();
		this.refreshList();
	}

	/**
	 * 监听每一笔记录是否可用,从而设置按钮是否可用
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		CluesManageInfo info = getSelectInfo();
		if (info != null) {
			if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
					.getId().toString())) {
				selectChange(false);
			} else {
				if (info.getCluesStatus() != null
						&& !info.getCluesStatus().equals("")) {
					this.actionEdit.setEnabled(false);
					this.actionRemove.setEnabled(false);
					this.actionDeliver.setEnabled(false);
					this.actionQuestionPrint.setEnabled(false);
					this.actionFollow.setEnabled(false);
					this.actionShare.setEnabled(false);
				} else {
					selectChange(true);
				}
			}
		}
	}

	/**
	 * 判断各功能按钮是否可用
	 * @param isEnabled
	 */
	private void selectChange(boolean isEnabled) {
		this.actionShare.setEnabled(isEnabled);
		this.actionDeliver.setEnabled(isEnabled);
		this.actionQuestionPrint.setEnabled(isEnabled);
		this.actionCommerceChance.setEnabled(isEnabled);
		this.actionEdit.setEnabled(isEnabled);
		this.actionRemove.setEnabled(isEnabled);
		this.actionFollow.setEnabled(isEnabled);
		actionSincerity.setEnabled(isEnabled);
		actionPrePurchase.setEnabled(isEnabled);
		actionPurchase.setEnabled(isEnabled);
		actionSign.setEnabled(isEnabled);
		btnTrade.setEnabled(isEnabled);
		actionCustomer.setEnabled(isEnabled);
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new CluesManageInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CluesManageFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return CluesManageEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected boolean isIncludeAllChildren() {
		return true;
	}
}