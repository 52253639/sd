/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.sellhouse.CluesManageCollection;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CluesSourceEnum;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataCollection;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataFactory;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CluesManageImportUI extends AbstractCluesManageImportUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CluesManageImportUI.class);
	private SellProjectInfo sellProject = null;
	private Map userMap;
	private Map cusAssistantDataMap;

	/**
	 * output class constructor
	 */
	public CluesManageImportUI() throws Exception {
		super();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {

	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.btnSave.setEnabled(true);
		sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		initImportTable();
		tblMain.getStyleAttributes().setLocked(false);
		this.tblMain.removeRows(false);
	}

	private void initImportTable() throws BOSException, EASBizException {
		this.tblMain.getColumn("name").getStyleAttributes().setBackground(
				CommerceHelper.BK_COLOR_MUST);
		this.tblMain.getColumn("phone").getStyleAttributes().setBackground(
				CommerceHelper.BK_COLOR_MUST);

		List cluesSourceList = CluesSourceEnum.getEnumList();
		List list = new ArrayList();
		for (int i = 0; i < CluesSourceEnum.getEnumList().size(); i++) {
			if (!cluesSourceList.get(i).equals(CluesSourceEnum.CHOOCEROOM)
					&& !cluesSourceList.get(i).equals(
							CluesSourceEnum.SINCERITYPRUCHASE)) {
				list.add(cluesSourceList.get(i));
			}
		}
		this.tblMain.getColumn("source").setEditor(
				CommerceHelper.getKDComboBoxEditor(list));

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("group.id", CRMConstants.KNOW_TYPE_GROUP_ID));
		view.setFilter(filterInfo);
		KDBizPromptBox f7CognizePath = new KDBizPromptBox();
		f7CognizePath.setEditable(false);
		f7CognizePath.setEntityViewInfo(view);
		f7CognizePath.setDisplayFormat("$name$");
		f7CognizePath.setEditFormat("$number$");
		f7CognizePath.setCommitFormat("$number$");
		f7CognizePath
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SHECusAssistantDataQuery");
		KDTDefaultCellEditor cognizePathEditor = new KDTDefaultCellEditor(
				f7CognizePath);
		this.tblMain.getColumn("cognizePath.name").setEditor(cognizePathEditor);

		view = new EntityViewInfo();
		KDBizPromptBox f7CustomManager = new KDBizPromptBox();
		f7CustomManager.setEditable(false);
		view = NewCommerceHelper.getPermitSalemanView(sellProject,null);
		f7CustomManager.setEntityViewInfo(view);
		f7CustomManager.setDisplayFormat("$name$");
		f7CustomManager.setEditFormat("$name$");
		f7CustomManager.setCommitFormat("$name$");
		f7CustomManager
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7PropertyConsultantQuery");
		KDTDefaultCellEditor customerEditor = new KDTDefaultCellEditor(
				f7CustomManager);
		this.tblMain.getColumn("propertyConsultant.name").setEditor(
				customerEditor);
	}

	protected void btnSelectFile_actionPerformed(ActionEvent e)
			throws Exception {
		this.tblMain.removeRows(false);
		String fileName = SHEHelper.showExcelSelectDlg(this);
		this.txtFilePath.setText(fileName);
		int excelNum = CommerceHelper
				.importExcelToTable(fileName, this.tblMain); // 对隐藏的列不写入
		this.tblMain.setRowCount(excelNum);
		this.txtExcelCount.setText(String.valueOf(excelNum));
	}

	private CluesManageCollection cols = new CluesManageCollection();

	private void vertifyARowImportDate() throws BOSException, EASBizException {
		cols.clear();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
		if (orgUnit == null)
			orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		boolean existCodingRule = iCodingRuleManager.isExist(
				new CluesManageInfo(), orgUnit.getId().toString());

		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			CluesManageInfo clues = new CluesManageInfo();
			clues.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
			IRow row = this.tblMain.getRow(i);
			int rowIndex = i + 1;
			clues.setName((String) row.getCell("name").getValue());
			if (isNullStr((String) row.getCell("name").getValue())) {
				MsgBox.showInfo(this, "第" + rowIndex + "行线索客户必须录入！");
				this.abort();
			} else if (row.getCell("name").getValue().toString().length() > 80) {
				MsgBox.showInfo(this, "第" + rowIndex + "行线索客户长度不能大于80！");
				this.abort();
			}

			clues.setPhone((String) row.getCell("phone").getValue());
			if (isNullStr((String) row.getCell("phone").getValue())) {
				MsgBox.showInfo(this, "第" + rowIndex + "行联系电话必须录入！");
				this.abort();
			} else {
				for(int j=rowIndex;j<this.tblMain.getRowCount();j++){
					IRow tempRow = this.tblMain.getRow(j);
					if (!isNullStr((String) tempRow.getCell("phone").getValue())) {
						String tempPhone = tempRow.getCell("phone").getValue().toString();
						if (row.getCell("phone").getValue().toString().equals(tempPhone)) {
							MsgBox.showInfo(this, "第" + rowIndex + "行与"+"第" +(j+1)+ "行联系电话相同！");
							this.abort();
						}
					}
				}
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("phone", row.getCell("phone").getValue().toString()));
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellProject.getId()));
				if (CluesManageFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo(this, "第" + rowIndex + "行在线索客户中存在相同的联系电话！");
					this.abort();
				}
				if (SHECustomerFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo(this, "第" + rowIndex + "行在客户台账中存在相同的联系电话！");
					this.abort();
				}
				if (row.getCell("phone").getValue().toString().length() < 7) {
					MsgBox.showInfo(this, "第" + rowIndex + "行联系电话至少需要输入7位以上！");
					this.abort();
				} else if (row.getCell("phone").getValue().toString().length() > 80) {
					MsgBox.showInfo(this, "第" + rowIndex + "行联系电话长度不能大于80！");
					this.abort();
				}
			}
			clues.setDescription((String) row.getCell("description")
							.getValue());
			if (row.getCell("description").getValue() != null
					&& !row.getCell("description").getValue().equals("")
					&& row.getCell("description").getValue().toString()
							.length() > 80) {
				MsgBox.showInfo(this, "第" + rowIndex + "行线索说明长度不能大于255！");
				this.abort();
			}

			Object source = row.getCell("source").getValue(); // 来源
			if (source != null) {
				if (source instanceof String) {
					CluesSourceEnum thisInfo = getCluesSourceByName((String) source);
					if (thisInfo != null) {
						row.getCell("source").setValue(thisInfo);
						clues.setSource((CluesSourceEnum) thisInfo);
					} else {
						MsgBox.showInfo(this, "第" + rowIndex + "行来源无法识别！");
						this.abort();
					}
				} else if (source instanceof CluesSourceEnum) {
					clues.setSource((CluesSourceEnum) source);
				}
			} else {
				MsgBox.showInfo(this, "第" + rowIndex + "行来源必须录入！");
				this.abort();
			}

			Object cognizePathName = row.getCell("cognizePath.name").getValue(); // 认知途径
			if (cognizePathName != null) {
				if (cognizePathName instanceof String) {
					SHECusAssistantDataInfo thisInfo = getSHECusAssistantDataByName((String) cognizePathName);
					if (thisInfo != null) {
						row.getCell("cognizePath.name").setValue(thisInfo);
						clues
								.setCognizePath((SHECusAssistantDataInfo) thisInfo);
					} else {
						MsgBox.showInfo(this, "第" + rowIndex + "行认知途径无法识别！");
						this.abort();
					}
				} else if (cognizePathName instanceof SHECusAssistantDataInfo) {
					clues
							.setCognizePath((SHECusAssistantDataInfo) cognizePathName);
				}
			}

			Object propertyConsultantName = row.getCell(
					"propertyConsultant.name").getValue(); // 置业顾问
			if (propertyConsultantName != null) {
				if (propertyConsultantName instanceof String) {
					UserInfo thisInfo = getUserByName((String) propertyConsultantName);
					if (thisInfo != null) {
						row.getCell("propertyConsultant.name").setValue(
								thisInfo);
						clues.setPropertyConsultant((UserInfo) thisInfo);
					} else {
						MsgBox.showInfo(this, "第" + rowIndex + "行置业顾问无法识别！");
						this.abort();
					}
				} else if (propertyConsultantName instanceof UserInfo) {
					clues
							.setPropertyConsultant((UserInfo) propertyConsultantName);
				}
			} else {
				MsgBox.showInfo(this, "第" + rowIndex + "行置业顾问必须录入！");
				this.abort();
			}
			clues.setSellProject(sellProject);
			Timestamp time = new Timestamp(new Date().getTime());
			if (existCodingRule) {
				String retNumber = iCodingRuleManager.getNumber(clues, orgUnit
						.getId().toString());
				clues.setNumber(retNumber);
			} else {

				String milliSecond = String.valueOf(time).substring(20, 23);
				String timeStr = String.valueOf(time).replaceAll("-", "")
						.replaceAll(" ", "").replaceAll(":", "").substring(0,
								14)
						+ milliSecond;
				String number = String.valueOf(timeStr) + "001";
				clues.setNumber(number);
			}

			clues.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
			clues.setCreator(SysContext.getSysContext().getCurrentUserInfo());
			clues.setCreateTime(time);

			row.setUserObject(clues);
			cols.add(clues);
		}
	}

	private boolean isNullStr(String s) {
		return s == null || s.trim().equals("");
	}

	// 判断来源是否可识别
	private CluesSourceEnum getCluesSourceByName(String name) {
		if (name == null || name.trim().equals(""))
			return null;
		if ("购买".equals(name)) {
			return (CluesSourceEnum) CluesSourceEnum.CALL;
		} else if ("巡展".equals(name)) {
			return (CluesSourceEnum) CluesSourceEnum.VISIT;
		} else if ("其他".equals(name)) {
			return (CluesSourceEnum) CluesSourceEnum.OTHER;
		} else if ("选房单".equals(name)) {
			return (CluesSourceEnum) CluesSourceEnum.CHOOCEROOM;
		} else if ("预约单".equals(name)) {
			return (CluesSourceEnum) CluesSourceEnum.SINCERITYPRUCHASE;
		} else {
			return null;
		}
	}

	// 判断认知途径是否可识别
	private SHECusAssistantDataInfo getSHECusAssistantDataByName(String name)
			throws BOSException {
		if (name == null || name.trim().equals(""))
			return null;

		if (cusAssistantDataMap == null) {
			SHECusAssistantDataCollection coll = SHECusAssistantDataFactory
					.getRemoteInstance().getSHECusAssistantDataCollection();
			cusAssistantDataMap = new HashMap();
			for (int i = 0; i < coll.size(); i++) {
				SHECusAssistantDataInfo thisInfo = coll.get(i);
				if (thisInfo.getName() == null) {
					continue;
				}
				cusAssistantDataMap.put(thisInfo.getName().trim(), thisInfo);
			}
		}
		return (SHECusAssistantDataInfo) cusAssistantDataMap.get(name.trim());
	}

	// 判断置业顾问是否可识别
	private UserInfo getUserByName(String name) throws BOSException {
		if (name == null || name.trim().equals(""))
			return null;

		if (userMap == null) {
			UserCollection coll = UserFactory.getRemoteInstance()
					.getUserCollection();
			userMap = new HashMap();
			for (int i = 0; i < coll.size(); i++) {
				UserInfo thisInfo = coll.get(i);
				if (thisInfo.getNumber() == null) {
					continue;
				}
				userMap.put(thisInfo.getNumber().trim(), thisInfo);
			}
		}
		return (UserInfo) userMap.get(name.trim());
	}

	public void actionSaveImport_actionPerformed(ActionEvent e)
			throws Exception {
		this.vertifyARowImportDate();
		if (cols == null || cols.isEmpty()) {
			MsgBox.showInfo(this, "没有要导入的数据！");
			this.abort();
		}
		CluesManageFactory.getRemoteInstance().importClues(cols);
		MsgBox.showInfo(this, "导入成功！");
	}

	protected void btnCheck_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			int rowIndex = i + 1;
			if (isNullStr((String) row.getCell("phone").getValue())) {
				MsgBox.showInfo(this, "第" + rowIndex + "行联系电话必须录入！");
				row.getStyleAttributes().setBackground(Color.RED);
				this.abort();
			} else {
				if (row.getCell("phone").getValue().toString().length() < 7) {
					MsgBox.showInfo(this, "第" + rowIndex + "行联系电话至少需要输入7位以上！");
					row.getStyleAttributes().setBackground(Color.RED);
					this.abort();
				} else if (row.getCell("phone").getValue().toString().length() > 80) {
					MsgBox.showInfo(this, "第" + rowIndex + "行联系电话长度不能大于80！");
					row.getStyleAttributes().setBackground(Color.RED);
					this.abort();
				}
				for(int j=rowIndex;j<this.tblMain.getRowCount();j++){
					IRow tempRow = this.tblMain.getRow(j);
					if (!isNullStr((String) tempRow.getCell("phone").getValue())) {
						String tempPhone = tempRow.getCell("phone").getValue().toString();
						if (row.getCell("phone").getValue().toString().equals(tempPhone)) {
							MsgBox.showInfo(this, "第" + rowIndex + "行与"+"第" +(j+1)+ "行联系电话相同！");
							row.getStyleAttributes().setBackground(Color.RED);
							tempRow.getStyleAttributes().setBackground(Color.RED);
							this.abort();
						}
					}
				}
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("phone", row.getCell("phone").getValue().toString()));
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellProject.getId()));
				if (CluesManageFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo(this, "第" + rowIndex + "行在线索客户中存在相同的联系电话！");
					row.getStyleAttributes().setBackground(Color.RED);
					this.abort();
				}
				if (SHECustomerFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo(this, "第" + rowIndex + "行在客户台账中存在相同的联系电话！");
					row.getStyleAttributes().setBackground(Color.RED);
					this.abort();
				}
			}
			row.getStyleAttributes().setBackground(Color.WHITE);
		}
	}
	
}