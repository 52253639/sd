/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.finance.ISubjectLevel;
import com.kingdee.eas.fdc.finance.SubjectLevelFactory;
import com.kingdee.eas.fdc.finance.SubjectLevelInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SubjectLevelListUI extends AbstractSubjectLevelListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SubjectLevelListUI.class);

	/**
	 * output class constructor
	 */
	public SubjectLevelListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionLocate.setVisible(false);
		this.actionQuery.setVisible(false);
		String orgID = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
		if (!orgID.equals("00000000-0000-0000-0000-000000000000CCE7AED4")) {
			this.actionCancel.setEnabled(false);
			this.actionCancel.setVisible(false);

			this.actionCancelCancel.setVisible(false);
			this.actionCancelCancel.setEnabled(false);

			this.actionAddNew.setVisible(false);
			this.actionEdit.setVisible(false);
			this.actionRemove.setVisible(false);

		} else {
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionCancel.setEnabled(true);
			this.actionCancelCancel.setEnabled(true);
		}

	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		String orgID = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
		if (orgID.equals("00000000-0000-0000-0000-000000000000CCE7AED4")) {
			Boolean isSystem = (Boolean) KDTableUtil.getSelectedRow(tblMain)
					.getCell("isEnabled").getValue();
			if (isSystem.booleanValue() == true) {
				this.actionCancelCancel.setEnabled(false);
				this.actionCancel.setEnabled(true);
			} else {
				this.actionCancel.setEnabled(false);
				this.actionCancelCancel.setEnabled(true);
			}
		}
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkCanEdit();
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {

		super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkCanEdit();
		checkSelected();
		Boolean isSystem = (Boolean) KDTableUtil.getSelectedRow(tblMain)
				.getCell("isEnabled").getValue();
		if (isSystem.booleanValue() == true) {
			MsgBox.showWarning(this, "单据已启用，不允许修改！");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkCanEdit();
		checkSelected();
		Boolean isSystem = (Boolean) KDTableUtil.getSelectedRow(tblMain)
				.getCell("isEnabled").getValue();
		if (isSystem.booleanValue() == true) {
			MsgBox.showWarning(this, "记录已启用，不允许删除！");
			SysUtil.abort();
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row == null) {
			MsgBox.showWarning(this, "请先选中行");
			SysUtil.abort();
		}
		String cancelMsg = EASResource
				.getString(FrameWorkClientUtils.strResource + "Confirm_Cancel");
		if (!confirmDialog(cancelMsg)) {
			return;
		}
		String id = (String) row.getCell("id").getValue();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("isEnabled");
		ObjectUuidPK pk = new ObjectUuidPK(id);
		SubjectLevelInfo info = ((ISubjectLevel) getBizInterface())
				.getSubjectLevelInfo(pk, sic);
		if (!info.isIsEnabled()) {
			MsgBox.showWarning(this, "已经是禁用用状态");
			SysUtil.abort();
		}
		getBizInterface().cancel(pk, info);
		refresh(null);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		// IRow row = KDTableUtil.getSelectedRow(tblMain);
		// if (row == null) {
		// MsgBox.showWarning(this, "请先选中行");
		// SysUtil.abort();
		// }

		// int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		// if(actRowIdx < 0){
		// FDCMsgBox.showWarning("请选中要操作的行！");
		// SysUtil.abort();
		// }
		// String id = (String) row.getCell("id").getValue();
		// SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add("id");
		// sic.add("isEnabled");
		// ObjectUuidPK pk = new ObjectUuidPK(id);
		// SubjectLevelInfo info = ((ISubjectLevel) getBizInterface())
		// .getSubjectLevelInfo(pk, sic);
		// if (info.isIsEnabled()) {
		// MsgBox.showWarning(this, "已经是启用状态");
		// SysUtil.abort();
		// }
		// getBizInterface().cancelCancel(pk, info);
		// refresh(null);

		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if (actRowIdx < 0) {
			FDCMsgBox.showWarning("请选中要操作的行！");
			SysUtil.abort();
		}
		Object o = tblMain.getCell(actRowIdx, "id").getValue();
		if (o == null) {
			return;
		}
		String id = o.toString();
		// 一个实体财务组织下只能有一个被启用的付款计划周期
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select count(fid) as cishu from T_FNC_SubjectLevel where  FIsEnabled = 1  ");
		// builder.addParam(id);
		IRowSet rowSet = builder.executeQuery();
		if (rowSet != null && rowSet.size() >= 1) {
			rowSet.next();
			int count = rowSet.getInt("cishu");
			if (count > 0) {
				MsgBox.showWarning("只能有一套启用状态的科目级次！");
				SysUtil.abort();
			}
		}

		builder.clear();
		builder
				.appendSql("update T_FNC_SubjectLevel set  FIsEnabled=1 where fid =?  ");
		builder.addParam(id);
		builder.execute();

		this.refresh(null);

	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return SubjectLevelFactory.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		SubjectLevelInfo objectValue = new SubjectLevelInfo();
		return objectValue;
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	/**
	 * 是否可新增修改删除
	 */
	private void checkCanEdit() {
		String orgID = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
		if (!orgID.equals("00000000-0000-0000-0000-000000000000CCE7AED4")) {
			MsgBox.showWarning(this, "请先切换到集团组织!");
			SysUtil.abort();
		}
	}

	protected String getEditUIName() {
		return SubjectLevelEditUI.class.getName();
	}

}