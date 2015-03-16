/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardFactory;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo;
import com.kingdee.eas.fdc.sellhouse.EyeSignhtFactory;
import com.kingdee.eas.fdc.sellhouse.EyeSignhtInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class DecorationStandardEditUI extends AbstractDecorationStandardEditUI {
	private static final Logger logger = CoreUIObject.getLogger(DecorationStandardEditUI.class);

	/**
	 * output class constructor
	 */
	public DecorationStandardEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		checkNullAndDump();
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkNullAndDump();
		super.actionSubmit_actionPerformed(e);
	}

	private void checkNullAndDump() throws Exception {
		DecorationStandardInfo info = (DecorationStandardInfo) this.editData;
		if (info == null) {
			SysUtil.abort();
		}
		String number = this.txtNumber.getText().trim();
		if (number == null || "".equals(number)) {
			MsgBox.showInfo("编码不能为空！");
			SysUtil.abort();
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (DecorationStandardFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("编码不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("number", number));
				if (DecorationStandardFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("编码不能重复！");
					SysUtil.abort();
				}
			}

		}
		String name = this.txtName.getText().trim();
		if (name == null || "".equals(name)) {
			MsgBox.showInfo("名称不能为空！");
			SysUtil.abort();
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));

			if (DecorationStandardFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("名称不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("name", name));
				if (DecorationStandardFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("名称不能重复！");
					SysUtil.abort();
				}
			}

		}

	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		DecorationStandardInfo info = (DecorationStandardInfo) this.editData;
		if (info == null) {
			return;
		}
		String id = info.getId().toString();
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("noise.id", id));
			if (RoomDesFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已被房间定义引用，不能删除！");
				SysUtil.abort();
			}
			if (RoomFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已被房间引用，不能删除！");
				SysUtil.abort();
			}
			super.actionRemove_actionPerformed(e);
		}
	}

	protected IObjectValue createNewData() {
		DecorationStandardInfo info = new DecorationStandardInfo();
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return DecorationStandardFactory.getRemoteInstance();
	}

}