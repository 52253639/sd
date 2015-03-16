/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommerceChanceDataTypeEditUI extends
		AbstractCommerceChanceDataTypeEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CommerceChanceDataTypeEditUI.class);
	private Map map = FDCSysContext.getInstance().getOrgMap();
	/**
	 * output class constructor
	 */
	public CommerceChanceDataTypeEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
		} else {
			CommerceChanceDataTypeInfo typeInfo = (CommerceChanceDataTypeInfo) this.editData;
			checkDefaultValue(typeInfo);
		}
		this.chkIsDefault.setEnabled(false);
	}

	private void checkDefaultValue(CommerceChanceDataTypeInfo typeInfo) {
		if (typeInfo != null) {
			if (typeInfo.isIsDefault()) {
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
			} else {
				this.btnEdit.setEnabled(true);
				this.btnRemove.setEnabled(true);
			}
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		CommerceChanceDataTypeInfo info = (CommerceChanceDataTypeInfo)this.editData;
		if(info.getOrgUnit() != null){
			if(!orgUnit.getId().equals(info.getOrgUnit().getId())){
				MsgBox.showInfo("非当前组织数据不允许操作!");
				SysUtil.abort();
			}
		}
		super.actionEdit_actionPerformed(e);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		CommerceChanceDataTypeInfo info = (CommerceChanceDataTypeInfo)this.editData;
		if(info.getOrgUnit() != null){
			if(!orgUnit.getId().equals(info.getOrgUnit().getId())){
				MsgBox.showInfo("非当前组织数据不允许操作!");
				SysUtil.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}
	
	protected IObjectValue createNewData() {
		CommerceChanceDataTypeInfo typeInfo = new CommerceChanceDataTypeInfo();
		typeInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit()
				.castToFullOrgUnitInfo());

		CommerceChanceDataTypeInfo parentInfo = (CommerceChanceDataTypeInfo) getUIContext()
				.get(UIContext.PARENTNODE);
		typeInfo.setParent(parentInfo);
		return typeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceChanceDataTypeFactory.getRemoteInstance();
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (txtNumber.getText() == null || txtNumber.getText().equals("")) {
			MsgBox.showInfo(this, "编码不能为空!");
			SysUtil.abort();
		}
		if (txtName.getText() == null || txtName.getText().equals("")) {
			MsgBox.showInfo(this, "名称不能为空!");
			SysUtil.abort();
		}
		checkNumber();
		checkName();
	}

	public void checkNumber() throws Exception {
		String number = this.txtNumber.getText().trim();
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));

			if (CommerceChanceDataTypeFactory.getRemoteInstance()
					.exists(filter)) {
				MsgBox.showInfo("编码已存在,不能重复");
				SysUtil.abort();
			}
		}
	}

	public void checkName() throws Exception {
		String name = this.txtName.getText().trim();
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));

			if (CommerceChanceDataTypeFactory.getRemoteInstance()
					.exists(filter)) {
				MsgBox.showInfo("名称已存在,不能重复");
				SysUtil.abort();
			}
		}
	}

	public void storeFields() {
		super.storeFields();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("parent");
		return sic;
	}
}