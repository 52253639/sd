/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.AchievementManagerFactory;
import com.kingdee.eas.fdc.schedule.AchievementManagerInfo;
import com.kingdee.eas.fdc.schedule.LbFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AchievementManagerListUI extends AbstractAchievementManagerListUI {
	private static final Logger logger = CoreUIObject.getLogger(AchievementManagerListUI.class);

	private String selectedID = null;
	
	private Map rowIndexMap = new HashMap();
	// 全局变量.工程项目的id
	private String projectID = "";

	/**
	 * output class constructor
	 */
	public AchievementManagerListUI() throws Exception {
		super();
	}

	/**
	 * ac output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output menuItemDel_actionPerformed method
	 */
	protected void menuItemDel_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemDel_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionAdd_actionPerformed
	 */
	public void actionAdd_actionPerformed(ActionEvent e) throws Exception {
		super.actionAdd_actionPerformed(e);
		IUIFactory uifactory = null;
		uifactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		UIContext uicontext = new UIContext(this);
		// update by libing at 2011-10-13
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree1.getLastSelectedPathComponent();

		if (node != null && node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
			uicontext.put("curInfo", (CurProjectInfo) node.getUserObject());
		}
		IUIWindow uiwindow = uifactory.create(getEditUIName(), uicontext, null, OprtState.ADDNEW);
		uiwindow.show();
		kDTree1.setSelectionNode(node);
		fillTable();

	}

	/**
	 * output actionDel_actionPerformed
	 */
	public void actionDel_actionPerformed(ActionEvent e) throws Exception {

		if (selectedID != null) {
			if (confirmRemove()) {
				AchievementManagerInfo info = AchievementManagerFactory.getRemoteInstance().getAchievementManagerInfo(new ObjectUuidPK(selectedID));
				if (info != null && info.getState() != null && info.getState().equals(FDCBillStateEnum.AUDITTED)) {
					FDCMsgBox.showInfo("此成果类别已审批，不能删除");
					abort();
				} else if (info != null && info.getState() != null) {
					AchievementManagerFactory.getRemoteInstance().delete(new ObjectUuidPK(selectedID));
					// 刷新
					fillTable();
				}
			}
		} else {
			FDCMsgBox.showInfo("请选择需要删除的阶段性成果");
			abort();
		}
	}

	protected boolean confirmRemove() {
		return MsgBox.isYes(MsgBox.showConfirm2(this, "是否删除选择的阶段性成果？"));
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		showAchUIBySltID(OprtState.VIEW);
	}

	private void showAchUIBySltID(String status) throws BOSException {
		if (selectedID != null) {
			IUIFactory uiFactory = null;
			try {
				uiFactory = UIFactory
						.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
				UIContext uicontext = new UIContext(this);
				uicontext.put(UIContext.ID, selectedID);
				IUIWindow uiwindow = uiFactory.create(
						AchievementManagerEditUI.class.getName(), uicontext,
						null, status);
				uiwindow.show();
			} catch (UIException e1) {
				e1.printStackTrace();
			}
		} else {
			FDCMsgBox.showInfo("请先选中一条阶段性成果");
			abort();
		}
		fillTable();
	}

	/**
	 * output actionUpdate_actionPerformed
	 */
	public void actionUpdate_actionPerformed(ActionEvent e) throws Exception {
		super.actionUpdate_actionPerformed(e);
		// Map selectedItemValue = table.getSelectedItemValue();
		// String id =(String) selectedItemValue.get("id");
		// String id = "qoKq2WUKQUCZhjtWP6IhSGzwl9Q=";
		if (selectedID != null) {
			AchievementManagerInfo info = AchievementManagerFactory.getRemoteInstance().getAchievementManagerInfo(new ObjectUuidPK(selectedID));
			if (info != null && info.getState() != null && info.getState().equals(FDCBillStateEnum.AUDITTED)) {
				FDCMsgBox.showInfo("此成果类别已审批，不允许修改");
				abort();
			} else if (info != null && info.getState() != null
					&& info.getState().equals(FDCBillStateEnum.AUDITTING)) {
				FDCMsgBox.showInfo("此成果类别正在审批中，不允许修改");
				abort();
			} else if (info != null && info.getState() != null && (info.getState().equals(FDCBillStateEnum.SAVED) || info.getState().equals(FDCBillStateEnum.SUBMITTED))) {
				IUIFactory uiFactory = null;
				try {
					uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
					UIContext uicontext = new UIContext(this);
					uicontext.put(UIContext.ID, selectedID);
					IUIWindow uiwindow = uiFactory.create(AchievementManagerEditUI.class.getName(), uicontext, null, OprtState.EDIT);
					uiwindow.show();
					// 刷新显示

					fillTable();
				} catch (UIException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			FDCMsgBox.showInfo("请选择需要修改的任务");
			abort();
		}
	}

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		initTree();
		initTable();
		// 初始化几个按钮图标
		btnAddNew.setIcon(EASResource.getIcon("imgTbtn_new"));
		btnDel.setIcon(EASResource.getIcon("imgTbtn_delete"));
		btnPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		btnPrintPreview.setIcon(EASResource.getIcon("imgTbtn_printpreview"));
		btnView.setIcon(EASResource.getIcon("imgTbtn_view"));
		btnUpdate.setIcon(EASResource.getIcon("imgTbtn_edit"));
		tblMain.getStyleAttributes().setLocked(false);
		this.windowTitle = "阶段性成果管理";
	}

	protected void initWorkButton() {
		super.initWorkButton();
	}

	private String getEditUIName() {
		return AchievementManagerEditUI.class.getName();
	}

	private ICoreBase getBizInterface() {
		try {
			return AchievementManagerFactory.getRemoteInstance();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void initTree() {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		try {
			projectTreeBuilder.build(this, kDTree1, actionOnLoad);
			this.kDTree1.expandAllNodes(true, (TreeNode) this.kDTree1.getModel().getRoot());
			// 过滤有权限，有工程项目的组织
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	
	public void fillTable() throws BOSException{
		if (kDTree1.getLastSelectedPathComponent() == null) {
			return;
		}
		initTable();
		List data = new ArrayList();
		data = LbFacadeFactory.getRemoteInstance().getAchievementManagerData(
				projectID);
		
		Map map = (Map) data.get(0);
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Entry) it.next();
			String key = (String) entry.getKey();
			String rowID = key.substring(0, key.indexOf("@"));
			String colID = key.substring(key.indexOf("@") + 1);
			int rowIndex = ((Integer) rowIndexMap.get(rowID)).intValue();
			ICell cell = tblMain.getCell(rowIndex, colID);
			List lst = (List) entry.getValue();
			cell.setValue(lst);
			if (lst.size() > 1) {
				int rowHeight = tblMain.getRow(rowIndex).getHeight();
				int curHeight = lst.size() * 19 + 2;
				if (rowHeight < curHeight) {
					tblMain.getRow(rowIndex).setHeight(curHeight);
					tblMain.getRow(rowIndex).setUserObject(new Integer(lst.size()));
				}
			}
		}
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			AchievementManagerCellEditor editor = new AchievementManagerCellEditor(this);
			Object obj = tblMain.getRow(i).getUserObject();
			int maxSize = 1;
			if (obj != null) {
				maxSize = ((Integer) obj).intValue();
			}
			editor.setMaxSize(maxSize);
			for (int j = 2; j < tblMain.getColumnCount(); j++) {
				tblMain.getCell(i, j).setEditor(new KDTDefaultCellEditor(editor));
			}
		}
	}

	private void initTable() {
		tblMain.checkParsed();
		tblMain.removeRows();
		// 前两列其实为成果阶段id和名称，不能删除
		for (int i = tblMain.getColumnCount(); i > 1; i--) {
			tblMain.removeColumn(i);
		}
		List header1 = new ArrayList();
		List header2 = new ArrayList();
		try {
			header1 = LbFacadeFactory.getRemoteInstance()
					.getAchievementManagerHeader1();
			header2 = LbFacadeFactory.getRemoteInstance()
					.getAchievementManagerHeader2();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		// 横向为专业，纵向为阶段
		IRow head = tblMain.getHeadRow(0);
		for (int i = 0; i < header2.size(); i++) {
			Map apInfo = (Map) header2.get(i);
			IColumn col = tblMain.addColumn();
			String key = (String) apInfo.get("id");
			col.setRenderer(new AchievementManagerRenderer());
			col.setKey(key);
			col.setWidth(130);
			head.getCell(key).setValue(apInfo.get("name"));
		}
		for (int i = 0; i < header1.size(); i++) {
			Map as = (Map) header1.get(i);
			IRow row = tblMain.addRow();
			Object asID = as.get("id");
			row.getCell("asID").setValue(asID);
			row.getCell("asName").setValue(as.get("name"));
			row.getCell("asName").setStyleAttributes(
					head.getCell(1).getStyleAttributes());
			row.getCell("asName").setRenderer(head.getCell(1).getRenderer());
			row.setResizeable(false);
			row.setHeight(21);
			rowIndexMap.put(asID, new Integer(row.getRowIndex()));
		}
	}

	// 树去过滤右边表数据
	protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree1
				.getLastSelectedPathComponent();
		if (node != null) {
			Object obj = node.getUserObject();
			if (obj != null && obj instanceof CurProjectInfo) {
				CurProjectInfo prjInfo = (CurProjectInfo) obj;
				// 拿到了工程项目信息的id,
				String curProId = prjInfo.getId().toString();
				projectID = curProId;
				// 调用加载表格
				fillTable();
			} else {
				tblMain.removeRows();
			}
		}
	}

	public void setSelectedID(String selectedID) {
		this.selectedID = selectedID;
		tblMain.setUserObject(selectedID);
	}

	public String getSelectedID() {
		return selectedID;
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
        if (selectedID == null) {
			FDCMsgBox.showError("请选择要打印的单据！");
			abort();
		}
		AchievementMangerDataProvider dataPvd = new AchievementMangerDataProvider(selectedID, getTDQueryPK());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		 if (selectedID == null) {
			FDCMsgBox.showError("请选择要预览的单据！");
			abort();
		}
		AchievementMangerDataProvider dataPvd = new AchievementMangerDataProvider(selectedID, getTDQueryPK());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.schedule.app.AchievementManagerTDQuery");
	}

	protected String getTDFileName() {
		return "/bim/fdc/process/achievementmanager";
	}
	
}