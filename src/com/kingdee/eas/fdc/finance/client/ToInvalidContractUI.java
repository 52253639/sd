/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ClearSplitFacade;
import com.kingdee.eas.fdc.contract.ClearSplitFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ToInvalidContractUI extends AbstractToInvalidContractUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ToInvalidContractUI.class);

	private boolean isOk = false;

	/**
	 * output class constructor
	 */
	public ToInvalidContractUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIName() {
		return ContractBillEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractBillFactory.getRemoteInstance();
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return ContractBillFactory.getRemoteInstance();
	}

	protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
		List idList = new ArrayList();
		for (int i = 0, size = tblMain.getRowCount(); i < size; i++) {
			if (tblMain.getCell(i, "selected").getValue().equals(Boolean.TRUE)) {
				idList.add(tblMain.getCell(i, "id").getValue().toString());
			}
		}
		if (idList.size() == 0) {
			MsgBox.showError(this, "未选择任何记录！");
			SysUtil.abort();
		} else {
			if (MsgBox.showConfirm2(this,
					"选择的记录相关的付款拆分将作废，若对应的合同有已经生成凭证的付款拆分，则此合同进入待处理流程，请确认是否操作？") == MsgBox.OK) {
				ClearSplitFacadeFactory.getRemoteInstance().clearPaymentSplit(
						idList);
			}
		}
		setConfirm(true);
		disposeUIWindow();
	}

	protected void btnCancel2_actionPerformed(ActionEvent e) throws Exception {
		setConfirm(false);
		disposeUIWindow();
	}

	private void setConfirm(boolean isOk) {
		this.isOk = isOk;
		disposeUIWindow();
	}

	public void initUIToolBarLayout() {

	}

	public void onLoad() throws Exception {
		super.onLoad();
		final KDCheckBox cb = new KDCheckBox();
		tblMain.getColumn("selected").setEditor(new KDTDefaultCellEditor(cb));
		for (int i = 0, size = tblMain.getRowCount(); i < size; i++) {
			tblMain.getCell(i, "selected").setValue(Boolean.FALSE);
		}
		tblMain.getColumn("selected").getStyleAttributes().setLocked(false);
	}

	protected void audit(List ids) throws Exception {

	}

	protected void unAudit(List ids) throws Exception {

	}

	protected void treeSelectChange() throws Exception {
		super.treeSelectChange();
		final KDCheckBox cb = new KDCheckBox();
		tblMain.getColumn("selected").setEditor(new KDTDefaultCellEditor(cb));
		for (int i = 0, size = tblMain.getRowCount(); i < size; i++) {
			tblMain.getCell(i, "selected").setValue(Boolean.FALSE);
		}
	}

	protected void btnInti_actionPerformed(ActionEvent e) throws Exception {
		super.btnInti_actionPerformed(e);
		List idList = new ArrayList();
		if (getProjSelectedTreeNode() != null
				&& getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode()
					.getUserObject();
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo) projTreeNodeInfo).getUnit()
						.getId();

				Set idSet = FDCClientUtils.genOrgUnitIdSet(id);	
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",
						idSet, CompareType.INCLUDE));
				view.setFilter(filter);
				view.getSelector().add("id");
				CurProjectCollection curProjectCollection = CurProjectFactory
						.getRemoteInstance().getCurProjectCollection(view);

				for (Iterator iter = curProjectCollection.iterator(); iter
						.hasNext();) {
					CurProjectInfo element = (CurProjectInfo) iter.next();
					idList.add(element.getId().toString());
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				CurProjectInfo curProjectInfo = CurProjectFactory
						.getRemoteInstance().getCurProjectInfo(
								new ObjectUuidPK(id));
				String longNumber = curProjectInfo.getLongNumber();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("longNumber", longNumber + "!%",
								CompareType.LIKE));
				filter.getFilterItems().add(
						new FilterItemInfo("longNumber", longNumber));
				filter.getFilterItems().add(
						new FilterItemInfo("fullOrgUnit.id", curProjectInfo
								.getFullOrgUnit().getId().toString()));
				filter.setMaskString("(#0 or #1) and #2");
				view.setFilter(filter);
				view.getSelector().add("id");
				CurProjectCollection curProjectCollection = CurProjectFactory
						.getRemoteInstance().getCurProjectCollection(view);

				for (Iterator iter = curProjectCollection.iterator(); iter
						.hasNext();) {
					CurProjectInfo element = (CurProjectInfo) iter.next();
					idList.add(element.getId().toString());
				}
			}
			
			List getList = ClearSplitFacadeFactory.getRemoteInstance().getToInvalidContract(idList);
			for (int i = 0, size = tblMain.getRowCount(); i < size; i++) {
				if(getList.contains(tblMain.getCell(i, "id").getValue().toString()))
						tblMain.getCell(i, "selected").setValue(Boolean.TRUE);
			}
		}
	}
}