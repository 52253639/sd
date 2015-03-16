/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class CRMSellProjectEditUI extends AbstractCRMSellProjectEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CRMSellProjectEditUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public CRMSellProjectEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		} else {
			if (STATUS_ADDNEW.equals(getOprtState())) {
				actionEdit.setEnabled(false);
				actionRemove.setEnabled(false);
			} else if (STATUS_EDIT.equals(getOprtState())) {
				actionEdit.setEnabled(false);
				this.txtName.setEnabled(false);
				this.txtNumber.setEnabled(false);
			} else if (STATUS_VIEW.equals(getOprtState())) {
				actionSubmit.setEnabled(false);
			} else {
				actionAddNew.setEnabled(true);
				actionEdit.setEnabled(true);
				actionRemove.setEnabled(true);
			}
		}
	}

	protected IObjectValue createNewData() {
		SellProjectInfo sellProjectInfo = new SellProjectInfo();
		sellProjectInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		CompanyOrgUnitInfo companyInfo = SysContext.getSysContext()
				.getCurrentFIUnit();
		sellProjectInfo.setCompanyOrgUnit(companyInfo);
		sellProjectInfo.setIsUse(false);
		SellProjectInfo parentInfo = (SellProjectInfo) this.getUIContext().get(
				"sellProject");
		sellProjectInfo.setParent(parentInfo);
		sellProjectInfo.setIsForSHE(false);
		return sellProjectInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SellProjectFactory.getRemoteInstance();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		checkDumpName();
		checkProject();
	}

	/**
	 * 判断项目名称不能重复
	 * 
	 * @throws BOSException
	 */
	public void checkDumpName() throws Exception {
		SellProjectInfo info = (SellProjectInfo) this.editData;
		SellProjectInfo parentInfo = info.getParent();
		String number = this.txtNumber.getText();
		if (StringUtils.isEmpty(number)) {
			MsgBox.showInfo("项目编码不能为空！");
			SysUtil.abort();
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (parentInfo != null && parentInfo.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("parent.id", parentInfo.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("parent.id", null,
								CompareType.EQUALS));
			}
			if (SellProjectFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下项目编码不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("number", number));
				if (parentInfo != null && parentInfo.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("parent.id", parentInfo.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("parent.id", null,
									CompareType.EQUALS));
				}
				if (SellProjectFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下项目编码不能重复！");
					SysUtil.abort();
				}
			}
		}
		String name = this.txtName.getText();
		if (StringUtils.isEmpty(name)) {
			MsgBox.showInfo("项目名称不能为空！");
			SysUtil.abort();
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));
			if (parentInfo != null && parentInfo.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("parent.id", parentInfo.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("parent.id", null,
								CompareType.EQUALS));
			}
			if (SellProjectFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下项目名称不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("name", name));
				if (parentInfo != null && parentInfo.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("parent.id", parentInfo.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("parent.id", null,
									CompareType.EQUALS));
				}
				if (SellProjectFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下项目名称不能重复！");
					SysUtil.abort();
				}
			}
		}
	}

	/**
	 * 判断上下级是否已存在工程项目
	 * 
	 * @throws Exception
	 */
	public void checkProject() throws Exception {
		SellProjectInfo info = (SellProjectInfo) this.editData;
		if (info == null) {
			SysUtil.abort();
		}

		Object object = this.prmtProject.getText();
		if (object != null) {
			CurProjectInfo projectInfo = (CurProjectInfo) this.prmtProject
					.getValue();
			if (projectInfo != null) {
				if (OprtState.ADDNEW.equals(this.getOprtState())) {
					if (this.getUIContext().get("IDSTR") != null) {
						String idStr = this.getUIContext().get("IDSTR")
								.toString();
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems()
								.add(
										new FilterItemInfo("project.id",
												projectInfo.getId().toString(),
												CompareType.EQUALS));
						filter.getFilterItems().add(
								new FilterItemInfo("id", idStr,
										CompareType.INNER));
						if (SellProjectFactory.getRemoteInstance().exists(
								filter)) {
							MsgBox.showWarning(this, "工程项目在上级已存在,不能重复!");
							this.abort();
						}
					}
				} else if (OprtState.EDIT.equals(this.getOprtState())) {
					if (info != null && info.getId() != null) {
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(
								new FilterItemInfo("id", info.getId()
										.toString(), CompareType.NOTEQUALS));
						filter.getFilterItems().add(
								new FilterItemInfo("project.id", projectInfo
										.getId().toString()));
						String longNumber = info.getLongNumber();// 当前项目的长编码
						filter.getFilterItems().add(
								new FilterItemInfo("longNumber", longNumber
										+ "%", CompareType.LIKE));
						if (SellProjectFactory.getRemoteInstance().exists(
								filter)) {
							MsgBox.showInfo("工程项目在下级已存在,不能重复!");
							SysUtil.abort();
						} else {
							FilterInfo tempFilter = new FilterInfo();
							tempFilter.getFilterItems()
									.add(
											new FilterItemInfo("id", info
													.getId().toString(),
													CompareType.NOTEQUALS));
							tempFilter.getFilterItems().add(
									new FilterItemInfo("project.id",
											projectInfo.getId().toString()));
							SellProjectInfo parentInfo = info.getParent();
							if (parentInfo != null
									&& parentInfo.getId() != null) {
								String parentLongNumber = parentInfo
										.getLongNumber();// 上级的长编码
								String numbers[] = parentLongNumber.split("!");// 对上级长编码拆分
								FilterInfo numFilter = new FilterInfo();
								numFilter.getFilterItems().add(
										new FilterItemInfo("number", info
												.getNumber().toString(),
												CompareType.NOTEQUALS));

								for (int i = 0; i < numbers.length; i++) {// 所有上级的编码
									FilterInfo parentFilter = new FilterInfo();
									parentFilter.getFilterItems().add(
											new FilterItemInfo("number",
													numbers[i]));
									parentFilter
											.mergeFilter(parentFilter, "OR");
									numFilter.mergeFilter(parentFilter, "OR");
								}
								tempFilter.mergeFilter(numFilter, "AND");
								if (SellProjectFactory.getRemoteInstance()
										.exists(tempFilter)) {
									MsgBox.showInfo("工程项目在上级已存在,不能重复!");
									SysUtil.abort();
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 设置父节点
	 */
	public void setDataObject(IObjectValue dataObject) {
		if (STATUS_ADDNEW.equals(getOprtState())) {
			dataObject.put("parent", getUIContext().get(UIContext.PARENTNODE));
		}
		super.setDataObject(dataObject);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if (OprtState.EDIT.equals(this.getOprtState())) {
			this.txtName.setEnabled(false);
			this.txtNumber.setEnabled(false);
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() != null && !this.editData.getId().equals("")) {
			SellProjectInfo info = SellProjectFactory.getRemoteInstance()
					.getSellProjectInfo(
							"select id,isForSHE,isLeaf where id='"
									+ this.editData.getId().toString() + "'");
			if (info.isIsForSHE()) {
				FDCMsgBox.showWarning(this, "本记录已被项目建立引用，不能删除!");
				this.abort();
			} else if (!info.isIsLeaf()) {
				FDCMsgBox.showWarning(this, "本记录具有子节点，不能删除!");
				this.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("longNumber");
		selectors.add("simpleName");
		selectors.add("termBegin");
		selectors.add("termBegin");
		selectors.add("termEnd");
		selectors.add("orgUnit.id");
		selectors.add("project.id");
		selectors.add("project.name");
		selectors.add("project.number");
		selectors.add("parent.id");
		selectors.add("parent.name");
		selectors.add("parent.number");
		selectors.add("parent.longNumber");
		return selectors;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
}