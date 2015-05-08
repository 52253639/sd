/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Action;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.AchievementProfessionCollection;
import com.kingdee.eas.fdc.schedule.AchievementProfessionFactory;
import com.kingdee.eas.fdc.schedule.AchievementProfessionInfo;
import com.kingdee.eas.fdc.schedule.AchievementStageCollection;
import com.kingdee.eas.fdc.schedule.AchievementStageFactory;
import com.kingdee.eas.fdc.schedule.AchievementStageInfo;
import com.kingdee.eas.fdc.schedule.AchievementTypeCollection;
import com.kingdee.eas.fdc.schedule.AchievementTypeFactory;
import com.kingdee.eas.fdc.schedule.AchievementTypeInfo;
import com.kingdee.eas.fdc.schedule.LbFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 阶段性成果类别
 * 
 * @author 李兵
 * @version EAS7.0
 * @createDate 2011-8-15
 * @see
 */
public class AchievementTypeListUI extends AbstractAchievementTypeListUI {
	private static final Logger logger = CoreUIObject.getLogger(AchievementTypeListUI.class);

	/**
	 * output class constructor
	 */
	public AchievementTypeListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionPopupCopy_actionPerformed
	 */
	public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupCopy_actionPerformed(e);
	}

	/**
	 * output actionHTMLForMail_actionPerformed
	 */
	public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception {
		super.actionHTMLForMail_actionPerformed(e);
	}

	/**
	 * output actionExcelForMail_actionPerformed
	 */
	public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelForMail_actionPerformed(e);
	}

	/**
	 * output actionHTMLForRpt_actionPerformed
	 */
	public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception {
		super.actionHTMLForRpt_actionPerformed(e);
	}

	/**
	 * output actionExcelForRpt_actionPerformed
	 */
	public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelForRpt_actionPerformed(e);
	}

	/**
	 * output actionLinkForRpt_actionPerformed
	 */
	public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception {
		super.actionLinkForRpt_actionPerformed(e);
	}

	/**
	 * output actionPopupPaste_actionPerformed
	 */
	public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupPaste_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node1 = (DefaultKingdeeTreeNode) kDTree1.getLastSelectedPathComponent();
		DefaultKingdeeTreeNode node2 = (DefaultKingdeeTreeNode) kDTree2.getLastSelectedPathComponent();
		if (node1 != null && node1.isLeaf()) {
			getUIContext().put("stage", node1.getUserObject());
		}
		if (node2 != null && node2.isLeaf()) {
			getUIContext().put("profession", node2.getUserObject());
		}
		super.actionAddNew_actionPerformed(e);
		tblMain.refresh();
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
		// tblMain.getSelectManager().getActiveRowIndex();
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// super.actionEdit_actionPerformed(e);貌似存在bug，选中第一行居然出来的数字会各种不同。。
		// 改成KDTABLEUTIL工具类
		// int m = tblMain.getSelectManager().getActiveColumnIndex();
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row == null) {
			FDCMsgBox.showInfo("请先选择记录行");
			SysUtil.abort();
		}
		IUIFactory uifactory = null;
		uifactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		UIContext uicontext = new UIContext(this);
		String id = row.getCell("id").getValue().toString();
		uicontext.put(UIContext.ID, id);
		IUIWindow uiwindow = null;
		uiwindow = uifactory.create(getEditUIName(), uicontext, null, OprtState.EDIT);
		uiwindow.show();
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// super.actionRemove_actionPerformed(e);
		if (KDTableUtil.getSelectedRows(tblMain).length <= 0) {
			FDCMsgBox.showInfo("请先选择记录行！");
			abort();
		}
		// 询问是否删除
		if (confirmRemove()) {
			String strID[] = getSelectedListId();
			IObjectPK[] pk = new ObjectUuidPK[strID.length];
			for (int x = 0; x < pk.length; x++) {
				pk[x] = new ObjectUuidPK(strID[x]);
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			Set ids = new HashSet();
			for (int i = 0; i < strID.length; i++) {
				ids.add(strID[i]);
			}
			filter.getFilterItems().add(new FilterItemInfo("id", ids, CompareType.INCLUDE));
			view.setFilter(filter);
			AchievementTypeCollection col = AchievementTypeFactory.getRemoteInstance().getAchievementTypeCollection(view);
			for (int m = 0; m < col.size(); m++) {
				AchievementTypeInfo info = col.get(m);
				if (info.isIsEnabled()) {
					FDCMsgBox.showInfo("存在成果类别被启用，不能删除！");
					SysUtil.abort();
				}

			}
			getBizInterface().delete(pk);
			refresh(e);
		} else {
			return;
		}

		//		
		//		
		// for (int i = 0; i < pk.length; i++) {
		// pk[i] = new ObjectUuidPK(strID[i]);
		// AchievementTypeInfo info = (AchievementTypeInfo)
		// getBizInterface().getValue(pk[i]);
		// // 判断该记录是否被阶段成果类别启用
		// if (info.isIsEnabled()) {
		//				
		// }
		// // 如果选中专项存在已编制的“专项计划”，则不允许删除。
		// // IAchievementType type = (IAchievementType) getBizInterface();
		// boolean b =
		// LbFacadeFactory.getRemoteInstance().isQuoteScheduleTask(strID[i]);
		// if (b) {
		// FDCMsgBox.showInfo("名称为" + info.getName() +
		// "的成果类别被进度任务引用，不能删除，请重新选择！");
		// SysUtil.abort();
		// }
		//			
		// }
		// getBizInterface().delete(pk);

	}

	public String[] getSelectedListId() {
		checkSelected();

		// SelectManager 是kdtable中行管理类
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		ArrayList idList = new ArrayList();
		Iterator iter = blocks.iterator();
		while (iter.hasNext()) {
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++) {
				ICell cell = tblMain.getRow(rowIndex).getCell(getKeyFieldName());
				if (!idList.contains(cell.getValue())) {
					idList.add(cell.getValue());
				}
			}
		}
		String[] listId = null;
		if (idList != null && idList.size() > 0) {
			Iterator iterat = idList.iterator();
			listId = new String[idList.size()];
			int index = 0;
			while (iterat.hasNext()) {
				listId[index] = (String) iterat.next();
				index++;
			}
		}
		return listId;
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		// this.tblMain.getSelectManager().select(0, 0);
		int m[] = KDTableUtil.getSelectedRows(tblMain);
		if (m.length > 0) {
			tblMain.getSelectManager().select(m[0], 0);
		}

		super.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * output actionQueryScheme_actionPerformed
	 */
	public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception {
		super.actionQueryScheme_actionPerformed(e);
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		// TODO Auto-generated method stub
		return new AchievementTypeInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return AchievementTypeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return AchievementTypeEditUI.class.getName();
	}

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		// 检查操作组织是否是成本中心
		if (!SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()) {
			FDCMsgBox.showInfo("当前组织不是成本中心，不能进入！");
			SysUtil.abort();
		}
		super.onLoad();
		this.windowTitle = "阶段性成果类别设置";
		// kDSplitPane1.setAutoscrolls(true);

		// 初始化图标
		btnAddNew.setIcon(EASResource.getIcon("imgTbtn_new"));
		btnView.setIcon(EASResource.getIcon("imgTbtn_view"));
		btnEdit.setIcon(EASResource.getIcon("imgTbtn_edit"));
		btnRemove.setIcon(EASResource.getIcon("imgTbtn_delete"));
		btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		btnQuery.setIcon(EASResource.getIcon("imgTbtn_find"));
		btnLocate.setIcon(EASResource.getIcon("imgTbtn_speedgoto"));
		kDSplitPane2.setEnabled(true);
		kDSplitPane1.setEnabled(true);
		kDTree1.setAutoscrolls(true);
		kDTree2.setAutoscrolls(true);
		// 构建成果阶段树
		buildAchTypeUpTree();
		// 构建成果专业树
		buildAchTypeDownTree();
		// 增加阶段树上的导航事件
		// kDTree1.addTreeSelectionListener(new TreeSelectionListener() {
		//
		// public void valueChanged(TreeSelectionEvent e) {
		// DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
		// kDTree1.getLastSelectedPathComponent();
		// // if (node.isRoot()) {
		// // return;
		// // }
		// FilterInfo info = navigationQuery();
		// mainQuery.setFilter(info);
		// tblMain.removeRows();
		// }
		//
		// });
		// 增加专业树上的导航事件
		// kDTree2.addTreeSelectionListener(new TreeSelectionListener() {
		//
		// public void valueChanged(TreeSelectionEvent e) {
		//				
		// DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
		// kDTree2.getLastSelectedPathComponent();
		// // if (node.isRoot()) {
		// // return;
		// // }
		// FilterInfo info = navigationQuery();
		// mainQuery.setFilter(info);
		// tblMain.removeRows();
		//				
		//				
		// }
		//
		// });

	}

	// 阶段导航
	protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception {
		// TODO Auto-generated method stub
		// super.kDTree1_valueChanged(e);
		// DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
		// kDTree1.getLastSelectedPathComponent();
		// if (node.isRoot()) {
		// return;
		// }
		FilterInfo info = navigationQuery();
		mainQuery.setFilter(info);
		tblMain.removeRows();

	}

	// 专业导航
	protected void kDTree2_valueChanged(TreeSelectionEvent e) throws Exception {
		// TODO Auto-generated method stub
		// DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
		// kDTree2.getLastSelectedPathComponent();
		// if (node.isRoot()) {
		// return;
		// }
		FilterInfo info = navigationQuery();
		mainQuery.setFilter(info);
		tblMain.removeRows();// super.kDTree2_valueChanged(e);

	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		// TODO Auto-generated method stub
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	// 导航查询
	private FilterInfo navigationQuery() {
		// DefaultKingdeeTreeNode node1 = (DefaultKingdeeTreeNode)
		// kDTree1.getLastSelectedPathComponent();
		DefaultKingdeeTreeNode node1 = null;
		AchievementStageInfo info1 = null;
		String stageId = null;
		String professionId = null;

		DefaultKingdeeTreeNode node2 = null;
		AchievementProfessionInfo info2 = null;

		FilterInfo info = new FilterInfo();
		if (!(kDTree1.getLastSelectedPathComponent() == null)) {
			node1 = (DefaultKingdeeTreeNode) kDTree1.getLastSelectedPathComponent();
			info1 = (AchievementStageInfo) node1.getUserObject();
			if (!node1.isRoot()) {
				stageId = info1.getId().toString();
				info.getFilterItems().add(new FilterItemInfo("stage.id", stageId, CompareType.EQUALS));
			}
		}
		if (!(kDTree2.getLastSelectedPathComponent() == null)) {
			node2 = (DefaultKingdeeTreeNode) kDTree2.getLastSelectedPathComponent();
			info2 = (AchievementProfessionInfo) node2.getUserObject();
			if (!node2.isRoot()) {
				professionId = info2.getId().toString();
				info.getFilterItems().add(new FilterItemInfo("profession.id", professionId, CompareType.EQUALS));
			}
		}
		// info.setMaskString("#0 or #1");

		// viewInfo.setFilter(info);
		// viewInfo.getSelector().add(new SelectorItemInfo("id"));
		// viewInfo.getSelector().add(new SelectorItemInfo("name"));
		// viewInfo.getSelector().add(new SelectorItemInfo("number"));
		// viewInfo.getSelector().add(new SelectorItemInfo("stage.name"));
		// viewInfo.getSelector().add(new SelectorItemInfo("profession.name"));
		// viewInfo.getSelector().add(new SelectorItemInfo("docDirectory"));

		return info;

	}

	// 初始化左边的上树
	private void buildAchTypeUpTree() throws Exception {
		DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode();
		root.setText("所有阶段");
		KingdeeTreeModel model = new KingdeeTreeModel(root);
		EntityViewInfo view = new EntityViewInfo();
		SorterItemCollection sic = new SorterItemCollection();
		sic.add(new SorterItemInfo("number"));
		view.setSorter(sic);
		AchievementStageCollection stageCol = AchievementStageFactory.getRemoteInstance().getAchievementStageCollection(view);
		for (int i = 0; i < stageCol.size(); i++) {
			DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode();
			AchievementStageInfo info = stageCol.get(i);
			node.setUserObject(info);
			node.setText(info.getName());
			model.insertNodeInto(node, root, root.getChildCount());
		}
		kDTree1.setModel(model);
		kDTree1.setSelectionRow(0);
		kDTree1.setShowsRootHandles(true);
	}

	// 初始化左边的下树
	private void buildAchTypeDownTree() throws Exception {
		DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode();
		root.setText("所有专业");
		KingdeeTreeModel model = new KingdeeTreeModel(root);
		EntityViewInfo view = new EntityViewInfo();
		SorterItemCollection sic = new SorterItemCollection();
		sic.add(new SorterItemInfo("number"));
		view.setSorter(sic);
		AchievementProfessionCollection proCol = AchievementProfessionFactory.getRemoteInstance().getAchievementProfessionCollection(view);
		for (int i = 0; i < proCol.size(); i++) {
			DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode();
			AchievementProfessionInfo info = proCol.get(i);
			node.setUserObject(info);
			node.setText(info.getName());
			model.insertNodeInto(node, root, root.getChildCount());
		}
		kDTree2.setModel(model);
		kDTree2.setSelectionRow(0);
		kDTree2.setShowsRootHandles(true);
	}

	protected void initWorkButton() {
		// TODO Auto-generated method stub
		// super.initWorkButton();

		actionAdd1.putValue(Action.SHORT_DESCRIPTION, "新增");
		actionAdd1.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_new"));
		actionView1.putValue(Action.SHORT_DESCRIPTION, "查看");
		actionView1.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_view"));
		actionEdit1.putValue(Action.SHORT_DESCRIPTION, "修改");
		actionEdit1.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_edit"));
		actionDel1.putValue(Action.SHORT_DESCRIPTION, "删除");
		actionDel1.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_delete"));

		KDWorkButton btnAdd = new KDWorkButton(actionAdd1);
		btnAdd.setEnabled(true);
		KDWorkButton btnView = new KDWorkButton(actionView1);
		btnView.setEnabled(true);
		KDWorkButton btnEdit = new KDWorkButton(actionEdit1);
		btnEdit.setEnabled(true);
		KDWorkButton btnDel = new KDWorkButton(actionDel1);
		btnDel.setEnabled(true);

		kDTreeView1.getControlPane().add(btnAdd);
		kDTreeView1.getControlPane().add(btnView);
		kDTreeView1.getControlPane().add(btnEdit);
		kDTreeView1.getControlPane().add(btnDel);
		kDTreeView1.setShowControlPanel(true);
		// kDTreeView1.setShowButton(false);
		// 查找按钮
		kDTreeView1.setShowFind(false);
		initWorkButton2();
		initButton();
	}

	// 菜单栏和工具栏的按钮控制
	private void initButton() {
		// 打印
		this.btnPrint.setVisible(false);
		this.btnPrint.setEnabled(false);
		this.menuItemPrint.setVisible(false);
		this.menuItemPrint.setEnabled(false);

		// 打印预览
		this.btnPrintPreview.setVisible(false);
		this.btnPrintPreview.setEnabled(false);
		this.menuItemPrintPreview.setVisible(false);
		this.menuItemPrintPreview.setEnabled(false);

		// 启用
		this.btnCancelCancel.setVisible(false);
		this.btnCancelCancel.setEnabled(false);
		this.menuItemCancelCancel.setVisible(false);
		this.menuItemCancelCancel.setEnabled(false);
		// 禁用
		this.btnCancel.setVisible(false);
		this.btnCancel.setEnabled(false);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancel.setEnabled(false);
		// 查询方案
		this.btnQueryScheme.setEnabled(false);
		this.btnQueryScheme.setVisible(false);

	}

	// initWorkButton()2
	private void initWorkButton2() {
		actionAdd2.putValue(Action.SHORT_DESCRIPTION, "新增");
		actionAdd2.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_new"));
		actionView2.putValue(Action.SHORT_DESCRIPTION, "查看");
		actionView2.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_view"));
		actionEdit2.putValue(Action.SHORT_DESCRIPTION, "修改");
		actionEdit2.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_edit"));
		actionDel2.putValue(Action.SHORT_DESCRIPTION, "删除");
		actionDel2.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_delete"));
		// 四个button 初始化和四个时间绑定在一起，点按钮则会执行该事件方法
		KDWorkButton btnAdd = new KDWorkButton(actionAdd2);
		btnAdd.setEnabled(true);
		KDWorkButton btnView = new KDWorkButton(actionView2);
		btnView.setEnabled(true);
		KDWorkButton btnEdit = new KDWorkButton(actionEdit2);
		btnEdit.setEnabled(true);
		KDWorkButton btnDel = new KDWorkButton(actionDel2);
		btnDel.setEnabled(true);
		// KDTreeView2为装KDTREE控件的KDTREEVIEW
		kDTreeView2.getControlPane().add(btnAdd);
		kDTreeView2.getControlPane().add(btnView);
		kDTreeView2.getControlPane().add(btnEdit);
		kDTreeView2.getControlPane().add(btnDel);
		kDTreeView2.setShowControlPanel(true);
		// kDTreeView2.setShowButton(false);
		// 查找按钮
		kDTreeView2.setShowFind(false);
	}

	// 成果阶段树增加
	public void actionAdd1_actionPerformed(ActionEvent e) throws Exception {
		// 配置的权限项控制不到，因此手动书写
		if (!PermissionFactory.getRemoteInstance().hasFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()), new MetaDataPK("com.kingdee.eas.fdc.schedule.client.AchievementTypeListUI"),
				new MetaDataPK("ActionAdd1"))) {
			// throw new
			// Permission//AccountBooksException(AccountBooksException.
			// NOSWITCH,new Object[]{scheme.getName()});
			FDCMsgBox.showInfo(this, "没有成果阶段信息增加权限");
			abort();
		}

		if (!((DefaultKingdeeTreeNode) kDTree1.getLastSelectedPathComponent()).isRoot()) {
			FDCMsgBox.showInfo("不允许增加下级成果阶段");
			SysUtil.abort();
		}
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		UIContext uicontext = new UIContext(this);
		IUIWindow uiWindow = uiFactory.create(AchievementStageEditUI.class.getName(), uicontext, null, OprtState.ADDNEW);
		uiWindow.show();
		buildAchTypeUpTree();
	}

	// 成果阶段树查看
	public void actionView1_actionPerformed(ActionEvent e) throws Exception {

		// 配置的权限项控制不到，因此手动书写
		if (!PermissionFactory.getRemoteInstance().hasFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()), new MetaDataPK("com.kingdee.eas.fdc.schedule.client.AchievementTypeListUI"),
				new MetaDataPK("ActionView1"))) {
			// throw new
			// Permission//AccountBooksException(AccountBooksException.
			// NOSWITCH,new Object[]{scheme.getName()});
			FDCMsgBox.showInfo(this, "没有成果阶段信息查看权限");
			abort();
		}

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree1.getLastSelectedPathComponent();
		if (node != null && !node.isRoot()) {
			AchievementStageInfo info = (AchievementStageInfo) node.getUserObject();
			String id = info.getId().toString();

			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			UIContext uicontext = new UIContext(this);
			uicontext.put(UIContext.ID, id);
			IUIWindow uiwindow = uiFactory.create(AchievementStageEditUI.class.getName(), uicontext, null, OprtState.VIEW);
			uiwindow.show();
		} else {
			FDCMsgBox.showInfo("请选择明细成果阶段");
			SysUtil.abort();
		}
	}

	// 成果阶段树编辑
	public void actionEdit1_actionPerformed(ActionEvent e) throws Exception {

		// 配置的权限项控制不到，因此手动书写
		if (!PermissionFactory.getRemoteInstance().hasFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()), new MetaDataPK("com.kingdee.eas.fdc.schedule.client.AchievementTypeListUI"),
				new MetaDataPK("ActionEdit1"))) {
			// throw new
			// Permission//AccountBooksException(AccountBooksException.
			// NOSWITCH,new Object[]{scheme.getName()});
			FDCMsgBox.showInfo(this, "没有成果阶段信息修改权限");
			abort();
		}

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree1.getLastSelectedPathComponent();
		if (node != null && !node.isRoot()) {
			AchievementStageInfo info = (AchievementStageInfo) node.getUserObject();
			String id = info.getId().toString();
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			UIContext uicontext = new UIContext(this);
			uicontext.put(UIContext.ID, id);
			// 判断是否被引用
			if (LbFacadeFactory.getRemoteInstance().isQuoteStage(id)) {
				uicontext.put("quote", "istrue");
			} else {
				uicontext.put("quote", "isfalse");
			}
			IUIWindow uiwindow = null;
			uiwindow = uiFactory.create(AchievementStageEditUI.class.getName(), uicontext, null, OprtState.EDIT);
			uiwindow.show();
			buildAchTypeUpTree();
		} else {
			FDCMsgBox.showInfo("请选择明细成果阶段");
			SysUtil.abort();
		}
	}

	// 成果阶段树删除
	public void actionDel1_actionPerformed(ActionEvent e) throws Exception {
		// super.actionDel1_actionPerformed(e);

		// 配置的权限项控制不到，因此手动书写
		if (!PermissionFactory.getRemoteInstance().hasFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()), new MetaDataPK("com.kingdee.eas.fdc.schedule.client.AchievementTypeListUI"),
				new MetaDataPK("ActionDel1"))) {
			// throw new
			// Permission//AccountBooksException(AccountBooksException.
			// NOSWITCH,new Object[]{scheme.getName()});
			FDCMsgBox.showInfo(this, "没有成果阶段信息删除权限");
			abort();
		}

		if (!confirmRemove()) {
			SysUtil.abort();
		}
		// kDTree1.getSelectionPaths()[1].getLastPathComponent()
		TreePath[] sp = kDTree1.getSelectionPaths();
		if (sp.length > 0) {
			Set idSet = new HashSet();
			IObjectPK[] pk = new IObjectPK[sp.length];
			for (int i = 0; i < sp.length; i++) {
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) sp[i].getLastPathComponent();
				if (node.isRoot()) {
					FDCMsgBox.showInfo("不能选择根节点");
					SysUtil.abort();
				}
				AchievementStageInfo info = (AchievementStageInfo) node.getUserObject();
				String id = info.getId().toString();
				idSet.add(id);
				pk[i] = new ObjectUuidPK(info.getId().toString());
			}
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("stage.name"));
			view.setSelector(sic);
			FilterInfo info = new FilterInfo();
			info.getFilterItems().add(new FilterItemInfo("stage.id", idSet, CompareType.INCLUDE));
			view.setFilter(info);
			AchievementTypeCollection col = AchievementTypeFactory.getRemoteInstance().getAchievementTypeCollection(view);
			if (col.size() > 0) {
				FDCMsgBox.showInfo("选择的成果阶段被引用，不允许删除");
				SysUtil.abort();
			}

			AchievementStageFactory.getRemoteInstance().delete(pk);
			// 删除后刷新树
			buildAchTypeUpTree();
		} else {
			FDCMsgBox.showInfo("请具体选中想要删除的的成果阶段");
			SysUtil.abort();
		}

		// DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
		// kDTree1.getLastSelectedPathComponent();
		// if (node != null && !node.isRoot()) {
		// AchievementStageInfo info = (AchievementStageInfo)
		// node.getUserObject();
		// String strid = info.getId().toString();
		// // 在AcheievementType表中判断是存在该外间 引用
		// if (LbFacadeFactory.getRemoteInstance().isQuoteStage(strid)) {
		// FDCMsgBox.showInfo("该成果阶段已经被引用，不允许删除！");
		// SysUtil.abort();
		// } else {
		// AchievementStageFactory.getRemoteInstance().delete(new
		// ObjectUuidPK(strid));
		// // 删除后刷新树
		// buildAchTypeUpTree();
		// }
		// } else {
		// FDCMsgBox.showInfo("请具体选中想要删除的的成果阶段");
		// SysUtil.abort();
		// }

	}

	// 成果专业树增加
	public void actionAdd2_actionPerformed(ActionEvent e) throws Exception {

		// 配置的权限项控制不到，因此手动书写
		if (!PermissionFactory.getRemoteInstance().hasFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()), new MetaDataPK("com.kingdee.eas.fdc.schedule.client.AchievementTypeListUI"),
				new MetaDataPK("ActionAdd2"))) {
			// throw new
			// Permission//AccountBooksException(AccountBooksException.
			// NOSWITCH,new Object[]{scheme.getName()});
			FDCMsgBox.showInfo(this, "没有成果阶段信息新增权限");
			abort();
		}

		if (!((DefaultKingdeeTreeNode) kDTree2.getLastSelectedPathComponent()).isRoot()) {
			FDCMsgBox.showInfo("不允许增加下级成果专业");
			SysUtil.abort();
		}
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		UIContext uicontext = new UIContext(this);
		IUIWindow uiwindow = uiFactory.create(AchievementProfessionEditUI.class.getName(), uicontext, null, OprtState.ADDNEW);
		uiwindow.show();
		buildAchTypeDownTree();
	}

	// 成果专业树查看
	public void actionView2_actionPerformed(ActionEvent e) throws Exception {

		// 配置的权限项控制不到，因此手动书写
		if (!PermissionFactory.getRemoteInstance().hasFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()), new MetaDataPK("com.kingdee.eas.fdc.schedule.client.AchievementTypeListUI"),
				new MetaDataPK("ActionView2"))) {
			// throw new
			// Permission//AccountBooksException(AccountBooksException.
			// NOSWITCH,new Object[]{scheme.getName()});
			FDCMsgBox.showInfo(this, "没有成果阶段信息查看权限");
			abort();
		}

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree2.getLastSelectedPathComponent();
		if (node != null && !node.isRoot()) {
			AchievementProfessionInfo info = (AchievementProfessionInfo) node.getUserObject();
			String id = info.getId().toString();
			UIContext uicontext = new UIContext(this);
			uicontext.put(UIContext.ID, id);
			IUIFactory uifactory = null;
			uifactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			IUIWindow uiwindow = uifactory.create(AchievementProfessionEditUI.class.getName(), uicontext, null, OprtState.VIEW);
			uiwindow.show();
		} else {
			FDCMsgBox.showInfo("请选择明细成果专业");
			SysUtil.abort();
		}
	}

	// 成果专业树编辑
	public void actionEdit2_actionPerformed(ActionEvent e) throws Exception {

		// 配置的权限项控制不到，因此手动书写
		if (!PermissionFactory.getRemoteInstance().hasFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()), new MetaDataPK("com.kingdee.eas.fdc.schedule.client.AchievementTypeListUI"),
				new MetaDataPK("ActionEdit2"))) {
			// throw new
			// Permission//AccountBooksException(AccountBooksException.
			// NOSWITCH,new Object[]{scheme.getName()});
			FDCMsgBox.showInfo(this, "没有成果阶段信息修改权限");
			abort();
		}

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree2.getLastSelectedPathComponent();
		if (node != null && !node.isRoot()) {
			AchievementProfessionInfo info = (AchievementProfessionInfo) node.getUserObject();
			String id = info.getId().toString();
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			UIContext uicontext = new UIContext(this);
			uicontext.put(UIContext.ID, id);
			// 判断是否被引用
			if (LbFacadeFactory.getRemoteInstance().isQuoteProfeseeion(id)) {
				uicontext.put("quote", "istrue");
			} else {
				uicontext.put("quote", "isfalse");
			}
			IUIWindow uiwindow = null;
			uiwindow = uiFactory.create(AchievementProfessionEditUI.class.getName(), uicontext, null, OprtState.EDIT);
			uiwindow.show();
			buildAchTypeDownTree();
		} else {
			FDCMsgBox.showInfo("请选择明细成果专业");
			SysUtil.abort();
		}
	}

	// 成果专业树删除
	public void actionDel2_actionPerformed(ActionEvent e) throws Exception {

		// 配置的权限项控制不到，因此手动书写
		if (!PermissionFactory.getRemoteInstance().hasFunctionPermission(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()), new MetaDataPK("com.kingdee.eas.fdc.schedule.client.AchievementTypeListUI"),
				new MetaDataPK("ActionDel2"))) {
			// throw new
			// Permission//AccountBooksException(AccountBooksException.
			// NOSWITCH,new Object[]{scheme.getName()});
			FDCMsgBox.showInfo(this, "没有成果阶段信息删除权限");
			abort();
		}

		if (!confirmRemove()) {
			SysUtil.abort();
		}
		TreePath[] sp = kDTree2.getSelectionPaths();
		if (sp.length > 0) {
			Set idSet = new HashSet();
			IObjectPK[] pk = new IObjectPK[sp.length];
			for (int i = 0; i < sp.length; i++) {
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) sp[i].getLastPathComponent();
				if (node.isRoot()) {
					FDCMsgBox.showInfo("不能选择根节点");
					SysUtil.abort();
				}
				AchievementProfessionInfo info = (AchievementProfessionInfo) node.getUserObject();
				String id = info.getId().toString();
				idSet.add(id);
				pk[i] = new ObjectUuidPK(info.getId().toString());
			}
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("profession.name"));
			view.setSelector(sic);
			FilterInfo info = new FilterInfo();
			info.getFilterItems().add(new FilterItemInfo("profession.id", idSet, CompareType.INCLUDE));
			view.setFilter(info);
			AchievementTypeCollection col = AchievementTypeFactory.getRemoteInstance().getAchievementTypeCollection(view);
			if (col.size() > 0) {
				FDCMsgBox.showInfo("选择的成果专业被引用，不允许删除");
				SysUtil.abort();
			}

			AchievementProfessionFactory.getRemoteInstance().delete(pk);
			// 删除后刷新树
			buildAchTypeDownTree();
		} else {
			FDCMsgBox.showInfo("请具体选中想要删除的的成果专业");
			SysUtil.abort();
		}

	}

	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();
		// 启用
		this.btnCancelCancel.setVisible(false);
		this.btnCancelCancel.setEnabled(false);
		this.menuItemCancelCancel.setVisible(false);
		this.menuItemCancelCancel.setEnabled(false);
		// 禁用
		this.btnCancel.setVisible(false);
		this.btnCancel.setEnabled(false);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancel.setEnabled(false);
	}

	public void refreshTable() {
		// TODO Auto-generated method stub
		// tblMain.refresh();
	}

	protected String[] getLocateNames() {
		String locateNames[] = new String[3];
		locateNames[0] = "name";
		locateNames[1] = "number";
		locateNames[2] = "stage.name";
		return locateNames;
	}

	public SelectorItemCollection getSelectors() {
		// TODO Auto-generated method stub
		return super.getSelectors();
	}

}