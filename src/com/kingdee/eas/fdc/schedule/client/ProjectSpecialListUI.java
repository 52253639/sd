/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.ProjectSpecialFactory;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)	ProjectSpecialEditUI.java 版权： 金蝶国际软件集团有限公司版权所有 描述： 项目专项列表界面
 * @author 王维强
 * @version EAS7.0
 * @createDate 2011-8-1
 * @see
 */
public class ProjectSpecialListUI extends AbstractProjectSpecialListUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectSpecialListUI.class);
	
	private OrgStructureInfo orgUnitInfo = null;

	private ProjectSpecialListUI listui;

	public CurProjectInfo projectInfo;

	private String treeSel = "no";

	public ProjectSpecialListUI() throws Exception {
		super();
	}

	protected void initWorkButton() {
		super.initWorkButton();
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new ProjectSpecialInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProjectSpecialFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ProjectSpecialEditUI.class.getName();
	}

	/**
	 * 设置工程项目
	 * 
	 * @throws Exception
	 */
	private void initTree() throws Exception {
		OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		try {
			SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentFIUnit());
			treeMain.setShowsRootHandles(true);
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
			projectTreeBuilder.build(this, treeMain, actionOnLoad);
			treeMain.setShowsRootHandles(true);
			treeMain.expandOnLevel(10);
		} finally {
			SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
		}

	}

	public void onLoad() throws Exception {
		super.onLoad();
		initSeting();
	}

	private void initSeting() throws Exception {
		this.windowTitle = "项目专项设置";
		initTree();
		if (!SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()) {
			FDCMsgBox.showInfo("当前组织不是成本中心，不能进入！");
			SysUtil.abort();
		}
		this.btnCancelCancel.setVisible(false);
		this.btnCancel.setVisible(false);
		// 禁用业务菜单下启用和禁用功能
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);

		treeSel = "no";
	}

	public void onShow() throws Exception {
		super.onShow();

		this.btnCancelCancel.setVisible(false);
		this.btnCancel.setVisible(false);
	}

	/**
	 * 树节点改变时的按钮状态处理
	 * 
	 * @param e
	 * @throws Exception
	 * @throws Exception
	 */
	protected ITreeBase getTreeInterface() throws BOSException {
		return CurProjectFactory.getRemoteInstance();
	}

	/**
	 * 树节点选择事件
	 * 
	 * @param e
	 * @throws Exception
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		FilterInfo filterInfo = new FilterInfo();
		if (node.getUserObject() instanceof CurProjectInfo) {
			projectInfo = (CurProjectInfo) node.getUserObject();
			if (projectInfo.isIsLeaf()) {
				filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", projectInfo.getId().toString(), CompareType.EQUALS));
			} else {
				/* modified by zhaoqin for R140310-0247 on 2014/03/12 start */
				//filterInfo.getFilterItems().add(
					//	new FilterItemInfo("curProject.longNumber", projectInfo.getNumber().toString() + "%", CompareType.LIKE));
				filterInfo.getFilterItems().add(
						new FilterItemInfo("curProject.longNumber", projectInfo.getLongNumber().toString() + "!%", CompareType.LIKE));
				/* modified by zhaoqin for R140310-0247 on 2014/03/12 end */
			}

		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			// 由于继承的是基础资料，所以根据当前组织下的工程项目来过滤。
			orgUnitInfo = (OrgStructureInfo) node.getUserObject();
			String sql = "select fid from t_fdc_curproject where ffullorgunit in (select fid from t_org_baseunit where flongnumber like '"
					+ orgUnitInfo.getLongNumber()
					+ "%' and fid in (select forgid from T_PM_OrgRange where fuserid = '"
					+ SysContext.getSysContext().getCurrentUserInfo().getId().toString() + "'))";
			filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", sql, CompareType.INNER));
			logger.info(sql);

		}
		this.mainQuery.setFilter(filterInfo);
		tblMain.removeRows();
		if (projectInfo != null && projectInfo.isIsLeaf()) {
			treeSel = "yes";
		}

	}

	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * 定位内容设置
	 * 
	 * @return
	 */
	protected String[] getLocateNames() {
		String locateNames[] = new String[4];
		locateNames[0] = "name";
		locateNames[1] = "number";
		locateNames[2] = "adminPerson.name";
		locateNames[3] = "adminDept.name";
		return locateNames;
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		projectSelected();
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * @param e
	 * @throws Exception
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelectedRows();
		super.actionView_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelectedRows();
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		UIContext ui = new UIContext(this);
		String id = null;
		int m = tblMain.getSelectManager().getActiveRowIndex();
		id = tblMain.getCell(m, "id").getValue().toString();
		ui.put(UIContext.ID, id);
		listui = this;
		ui.put("listui", listui);
		ui.put("treeSelect", treeSel);
		IUIWindow uiwindow = null;
		uiwindow = uiFactory.create(ProjectSpecialEditUI.class.getName(), ui, null, OprtState.EDIT);
		uiwindow.show();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelectedRows();
		int sRow = tblMain.getSelectManager().getActiveRowIndex();
		String id = tblMain.getCell(sRow, "id").getValue().toString();
		boolean v = verifyCheck(id);
		if (v == false) {
			FDCMsgBox.showWarning(this, "项目被引用不允许删除!");
			return;
		}
		if (!confirmRemove()) {
			return;
		}
		removeCall();
		refresh(e);
	}

	/**
	 * 选择删除调用,删除多行
	 */
	private void removeCall() {
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
				boolean v = verifyCheck(listId[index]);
				if (v == false) {
					FDCMsgBox.showWarning(this, "项目被引用不允许删除!");
					return;
				}
				index++;
			}
		}
		IObjectPK[] pk = new IObjectPK[listId.length];
		for (int i = 0; i < listId.length; i++) {
			pk[i] = new ObjectUuidPK(listId[i]);
		}
		try {
			getBizInterface().delete(pk);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 刷新时选中多行所选行的第一行
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		setLocatePre(false);
		int min = 100;
		int[] rows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < rows.length; i++) {
			if (min > rows[i]) {
				min = rows[i];
			}
		}
		super.actionRefresh_actionPerformed(e);
		tblMain.getSelectManager().select(min, 0);
	}

	/**
	 * 选择的工程项目
	 * 
	 * @return
	 */
	protected CurProjectInfo getSelectProject() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if (projectInfo.isIsLeaf()) {
				return projectInfo;
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		return null;
	}

	/**
	 * 构建查询条件
	 * 
	 * @param queryPK
	 * @param viewInfo
	 * @return
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		if (orgUnitInfo == null) {
			String sql = "select fid from t_fdc_curproject where ffullorgunit in (select fid from t_org_baseunit where flongnumber like '"
					+ SysContext.getSysContext().getCurrentFIUnit().getLongNumber() + "%' and fid in (select forgid from T_PM_OrgRange where fuserid = '"
					+ SysContext.getSysContext().getCurrentUserInfo().getId().toString() + "'))";
			FilterInfo filterInfo = null;
			if (viewInfo.getFilter() != null) {
				filterInfo = viewInfo.getFilter();
			} else {
				filterInfo = new FilterInfo();
			}
			filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", sql, CompareType.INNER));
			viewInfo.setFilter(filterInfo);
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	/**
	 * 
	 */
	public void projectSelected() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo project = (CurProjectInfo) node.getUserObject();
			if (!project.isIsLeaf()) {
				FDCMsgBox.showWarning(this, "请选择明细工程项目!");
				SysUtil.abort();
			}

		} else {
			FDCMsgBox.showWarning(this, "请选择工程项目!");
			SysUtil.abort();
		}
		treeSel = "yes";
	}

	public void checkSelectedRows() {
		if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	/**
	 * 删除验证是否被引用
	 * 
	 * @param id
	 * @return
	 */
	public boolean verifyCheck(String id) {
		Set set = new HashSet();
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select FProjectSpecialID as proID from T_SCH_FDCSchedule ");
		try {
			for (IRowSet rs = sql.executeQuery(); rs.next(); set.add(rs.getString(1)))
				;
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (set.contains(id)) {
			return false;
		}
		return true;
	}

	/**
	 * @param uiContext
	 * @param e
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put("treeSelect", treeSel);
	}
}