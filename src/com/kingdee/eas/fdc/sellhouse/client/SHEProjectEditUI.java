/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.SHEOrgUnitListUI;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.ISHEProject;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SHEProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SHEProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SHEShareOrgUnitCollection;
import com.kingdee.eas.fdc.sellhouse.SHEShareOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SHEProjectEditUI extends AbstractSHEProjectEditUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4378398391485913282L;
	private static final Logger logger = CoreUIObject
			.getLogger(SHEProjectEditUI.class);

	private CompanyOrgUnitInfo companyInfo = SysContext.getSysContext()
			.getCurrentFIUnit();
	private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	private FullOrgUnitInfo org = SysContext.getSysContext()
			.getCurrentOrgUnit().castToFullOrgUnitInfo();
	private String id = "";
	private ArrayList roomIdList = new ArrayList();
	private ArrayList roomTwoList = new ArrayList();
	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	private CurProjectInfo currProject = null;

	/**
	 * output class constructor
	 */
	public SHEProjectEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {

		super.storeFields();
		if (this.prtOrgUnit.getValue() != null) {
			CompanyOrgUnitInfo info = (CompanyOrgUnitInfo) this.prtOrgUnit
					.getValue();
			this.editData.setCompanyOrgUnit(info);
		}

		if (this.editData.getOrgUnit() == null) {
			FullOrgUnitInfo info = SysContext.getSysContext()
					.getCurrentOrgUnit().castToFullOrgUnitInfo();
			this.editData.setOrgUnit(info);
		}
		/**
		 * 共享组织的保存
		 */
		this.editData.getShareOrgUnitList().clear();
		for (int i = 0; i < kDTable1.getRowCount(); i++) {
			IRow row = kDTable1.getRow(i);
			SHEShareOrgUnitInfo orgUnitInfo = (SHEShareOrgUnitInfo) row
					.getUserObject();
			orgUnitInfo.setOrgUnit((FullOrgUnitInfo) row
					.getCell("shareOrgUnit").getUserObject());
			orgUnitInfo.setOperationPerson((UserInfo) row.getCell(
					"shareOperationer").getUserObject());
			orgUnitInfo
					.setShareDate((Date) row.getCell("shareDate").getValue());
			orgUnitInfo.setCreateOrgUnit((FullOrgUnitInfo) row.getCell(
					"shareOpOrgUnit").getUserObject());
			this.editData.getShareOrgUnitList().add(orgUnitInfo);
		}

	}

	/**
	 * 初始化数据
	 */
	protected IObjectValue createNewData() {
		SHEProjectInfo objectValue = new SHEProjectInfo();
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		SHEProjectInfo sheProjectInfo = (SHEProjectInfo) this.getUIContext()
				.get("sheProject");
		if (sheProjectInfo != null) {
			objectValue.setParent(sheProjectInfo);
		}
		if (this.editData == null) {
			BOSUuid uuid = BOSUuid.create("8BD71CA5");
			if (uuid != null) {
				this.id = uuid.toString();
				objectValue.setId(uuid);
			}
		}
		FullOrgUnitInfo info = SysContext.getSysContext().getCurrentOrgUnit()
				.castToFullOrgUnitInfo();
		if (info != null) {
			objectValue.setOrgUnit(info);
		}
		objectValue.setIsUse(false);
		objectValue.setIsEnabled(false);
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SHEProjectFactory.getRemoteInstance();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add(new SelectorItemInfo("longNumber"));
		selectors.add(new SelectorItemInfo("parent.*"));
		selectors.add(new SelectorItemInfo("companyOrgUnit.id"));
		selectors.add(new SelectorItemInfo("companyOrgUnit.number"));
		selectors.add(new SelectorItemInfo("companyOrgUnit.name"));
		selectors.add(new SelectorItemInfo("project.*"));
		selectors.add(new SelectorItemInfo("orgUnit.*"));
		selectors.add(new SelectorItemInfo("shareOrgUnitList.*"));
		selectors.add(new SelectorItemInfo("shareOrgUnitList.orgUnit.*"));
		selectors
				.add(new SelectorItemInfo("shareOrgUnitList.operationPerson.*"));
		selectors.add(new SelectorItemInfo("shareOrgUnitList.createOrgUnit.*"));
		selectors.add(new SelectorItemInfo("roomModel.id"));
		selectors.add(new SelectorItemInfo("roomModel.name"));
		selectors.add(new SelectorItemInfo("roomModel.number"));
		selectors.add(new SelectorItemInfo("roomModel.description"));
		selectors.add(new SelectorItemInfo("roomModel.roomModelType.id"));
		selectors.add(new SelectorItemInfo("roomModel.roomModelType.name"));
		selectors.add(new SelectorItemInfo("roomModel.roomModelType.number"));
		selectors.add(new SelectorItemInfo(
				"roomModel.roomModelType.description"));
		return selectors;
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

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		checkDumpName();
		super.actionSave_actionPerformed(e);
		setCompanyOrgUnit();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkDumpName();
		BOSUuid id = this.editData.getId();
		this.id = id.toString();
		if (this.currProject != null && this.prtProject.getValue() != null) {
			CurProjectInfo newProject = (CurProjectInfo) this.prtProject
					.getValue();
			if (!newProject.getId().toString().equals(
					currProject.getId().toString())) {
				checkProject();
			}

		}
		super.actionSubmit_actionPerformed(e);
		setCompanyOrgUnit();
		updateRoomModel(true);
		this.editData = null;
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			this.kDTable2.removeRows();
			this.roomTwoList.clear();
			// handleCodingRule();
		}
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			if (id != null) {
				SHEProjectFactory.getRemoteInstance()
						.updateSHEProjectToSellProject(id);
			}
		}
		actionAddNew_actionPerformed(e);
	}

	/**
	 * 判断上下级是否已存在工程项目
	 * 
	 * @throws Exception
	 */
	public void checkProject() throws Exception {
		SHEProjectInfo info = (SHEProjectInfo) this.editData;
		if (info == null) {
			SysUtil.abort();
		}

		Object object = this.prtProject.getValue();
		if (object != null) {
			CurProjectInfo projectInfo = (CurProjectInfo) this.prtProject
					.getValue();
			if (projectInfo != null) {
				if (info != null && info.getId() != null) {
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("id", info.getId().toString(),
									CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", projectInfo
									.getId().toString()));

					String longNumber = info.getLongNumber();// 当前项目的长编码
					filter.getFilterItems().add(
							new FilterItemInfo("longNumber", longNumber + "%",
									CompareType.LIKE));

					if (SHEProjectFactory.getRemoteInstance().exists(filter)) {
						MsgBox.showInfo("工程项目在下级已存在,不能重复!");
						SysUtil.abort();
					} else {
						FilterInfo tempFilter = new FilterInfo();
						tempFilter.getFilterItems().add(
								new FilterItemInfo("id", info.getId()
										.toString(), CompareType.NOTEQUALS));
						tempFilter.getFilterItems().add(
								new FilterItemInfo("project.id", projectInfo
										.getId().toString()));
						SHEProjectInfo parentInfo = info.getParent();
						if (parentInfo != null && parentInfo.getId() != null) {
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
								parentFilter.getFilterItems()
										.add(
												new FilterItemInfo("number",
														numbers[i]));
								parentFilter.mergeFilter(parentFilter, "OR");
								numFilter.mergeFilter(parentFilter, "OR");
							}
							tempFilter.mergeFilter(numFilter, "AND");
							if (SHEProjectFactory.getRemoteInstance().exists(
									tempFilter)) {
								MsgBox.showInfo("工程项目在上级已存在,不能重复!");
								SysUtil.abort();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 更新户型
	 */
	private void updateRoomModel(boolean isNewAdd) {

		if (isNewAdd) {
			if (!"".equals(this.id)) {
				try {
					SHEProjectFactory.getRemoteInstance().updateRoomModel(
							roomIdList, id);

				} catch (EASBizException e) {
					logger.error(e.getMessage() + "更新户型id失败");
				} catch (BOSException e) {
					logger.error(e.getMessage() + "更新户型id失败");
				} finally {
					// roomTwoList = (ArrayList) roomIdList.clone();
					this.roomIdList.clear();
				}
			} else {
				try {
					if (roomIdList.size() > 0) {
						SHEProjectFactory.getRemoteInstance().updateRoomModel(
								roomIdList, this.editData.getId().toString());
					}
				} catch (EASBizException e) {
					logger.error(e.getMessage() + "更新户型id失败");
				} catch (BOSException e) {
					logger.error(e.getMessage() + "更新户型id失败");
				} finally {
					// roomTwoList = (ArrayList) roomIdList.clone();
					this.roomIdList.clear();
				}
			}
		} else {
			try {
				SHEProjectFactory.getRemoteInstance().updateRoomModel(
						roomIdList, this.editData.getId().toString());
			} catch (EASBizException e) {
				logger.error(e.getMessage() + "更新户型id失败");
			} catch (BOSException e) {
				logger.error(e.getMessage() + "更新户型id失败");
			} finally {
				roomTwoList = (ArrayList) roomIdList.clone();
				this.roomIdList.clear();
			}
		}

	}

	/**
	 * 编码的校验
	 * 
	 * @throws Exception
	 */
	private void checkDumpName() throws Exception {
		SHEProjectInfo info = (SHEProjectInfo) this.editData;
		if (info == null) {
			return;
		}
		SHEProjectInfo parentInfo = info.getParent();
		String number = this.txtNumber.getText();
		if (number == null || number == "") {
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
			}
			if (SHEProjectFactory.getRemoteInstance().exists(filter)) {
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
				}
				if (SHEProjectFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下项目编码不能重复！");
					SysUtil.abort();
				}
			}

		}
		String name = this.txtName.getText();
		if (name == null || "".equals(name)) {
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
			}
			if (SHEProjectFactory.getRemoteInstance().exists(filter)) {
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
				}
				if (SHEProjectFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下项目名称不能重复！");
					SysUtil.abort();
				}
			}

		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionEdit.setVisible(true);
		this.actionRemove.setVisible(true);
		this.actionEdit.setEnabled(false);
		this.actionAddNew.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionSave.setVisible(false);
		this.actionSave.setEnabled(false);
		this.actionSubmit.setVisible(true);
		this.actionCopy.setVisible(false);
		this.actionAbout.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);

		setCompanyOrgUnit();
		this.prtProject.setEditable(false);
		this.kDTable2.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		// this.txtNumber.setEnabled(false);
		// handleCodingRule();
		this.prtProject.setEditable(false);

		this.id = "";

		if (this.saleOrg.isIsBizUnit()) {
			this.actionEdit.setEnabled(true);
			this.actionAddNew.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionSave.setEnabled(true);
		}

	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		// handleCodingRule();

	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		FDCClientVerifyHelper.verifyEmpty(this, txtNumber);
	}

	public void loadFields() {
		super.loadFields();
		SHEProjectInfo info = this.editData;
		if (info != null && info.getCompanyOrgUnit() != null) {
			this.prtOrgUnit.setValue(info.getCompanyOrgUnit());
		} else {
			this.prtOrgUnit.setValue(null);
		}

		if (this.editData.getProject() != null) {
			this.currProject = this.editData.getProject();
		}

		/**
		 * 共享组织的加载
		 */
		SHEShareOrgUnitCollection orgColl = info.getShareOrgUnitList();
		kDTable1.removeRows();
		for (int i = 0; i < orgColl.size(); i++) {
			SHEShareOrgUnitInfo orgUnitInfo = orgColl.get(i);
			kDTable1.checkParsed();
			IRow row = kDTable1.addRow(i);

			showLoadOrgUnit(row, orgUnitInfo);
		}

		/**
		 * 户型的加载
		 */
		if (this.roomTwoList.size() > 0) {
			this.kDTable2.removeRows();
			for (int i = 0; i < roomTwoList.size(); i++) {
				RoomModelInfo room = (RoomModelInfo) roomTwoList.get(i);
				this.kDTable2.checkParsed();
				IRow row = this.kDTable2.addRow(i);
				showLoadRoomModel(row, room);
			}
		} else {
			try {
				loadRoomModel();
			} catch (BOSException e) {
				logger.error(e.getMessage() + "加载户型出错！");
			}
		}

	}

	/**
	 * 将户型填充到table中
	 * 
	 * @param row
	 * @param room
	 */
	private void showLoadRoomModel(IRow row, RoomModelInfo room) {
		row.setUserObject(room);
		row.getCell("roomNumber").setValue(room.getNumber());
		row.getCell("roomNumber").setUserObject(room.getNumber());
		row.getCell("roomNumber").getStyleAttributes().setLocked(true);
		row.getCell("roomName").setValue(room.getName());
		row.getCell("roomName").setUserObject(room.getName());
		row.getCell("roomName").getStyleAttributes().setLocked(true);
		if (room.getRoomModelType() != null) {
			row.getCell("roomType").setValue(room.getRoomModelType().getName());
			row.getCell("roomType").setUserObject(room.getRoomModelType());
		}
		row.getCell("roomType").getStyleAttributes().setLocked(true);
		row.getCell("roomDesc").setValue(room.getDescription());
		row.getCell("roomDesc").getStyleAttributes().setLocked(true);
		row.getCell("roomId").setValue(room.getId());
		row.getCell("roomId").setUserObject(room.getId());
		row.getCell("roomId").getStyleAttributes().setLocked(true);

	}

	public void onShow() throws Exception {
		super.onShow();
		if (OprtState.VIEW.equals(this.getOprtState())) {
			setBtnState(false);
		}
		if (OprtState.EDIT.equals(this.getOprtState())) {
			if (this.editData.getCompanyOrgUnit() != null) {
				this.prtOrgUnit.setValue(null);
			}
		}
	}

	private void setBtnState(boolean result) {
		this.btnAdd.setEnabled(result);
		this.btnDel.setEnabled(result);
		// this.btnAddRoom.setEnabled(result);
		// /this.btnDelRoom.setEnabled(result);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if (OprtState.EDIT.equals(this.getOprtState())) {
			this.txtName.setEnabled(false);
			this.txtNumber.setEnabled(false);
		}
		setBtnState(true);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() != null) {
			SHEProjectInfo info = SHEProjectFactory.getRemoteInstance()
					.getSHEProjectInfo(
							"select id,isUse where id='"
									+ this.editData.getId().toString() + "'");
			if (info.isIsUse()) {
				FDCMsgBox.showWarning(this, "项目已经被引用，不能删除!");
				this.abort();
			}
		}

		super.actionRemove_actionPerformed(e);
		setCompanyOrgUnit();
	}

	private void setCompanyOrgUnit() {
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			if (companyInfo != null) {
				this.prtOrgUnit.setValue(companyInfo);
			}
		}

		if (OprtState.EDIT.equals(this.getOprtState())) {
			this.txtName.setEnabled(false);

		}
	}

	/**
	 * 共享组织的添加
	 */
	public void actionBtnAdd_actionPerformed(ActionEvent e) throws Exception {
		super.actionBtnAdd_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		uiContext.put("isMultiSelect", Boolean.TRUE);

		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory
				.createUIFactory(UIFactoryName.MODEL)
				.create(SHEOrgUnitListUI.class.getName(), uiContext, null, null);
		uiWindow.show();
		Object[] object = null;
		SHEOrgUnitListUI cusEditUI = (SHEOrgUnitListUI) uiWindow.getUIObject();
		if (cusEditUI.getUIContext().get(SHEOrgUnitListUI.FULLORGUNIT) != null) {
			object = (Object[]) cusEditUI.getUIContext().get(
					SHEOrgUnitListUI.FULLORGUNIT);
		}

		if (object != null && object.length > 0) {
			IRow row = null;
			List list = new ArrayList();
			for (int i = 0; i < kDTable1.getRowCount(); i++) {
				IRow row2 = kDTable1.getRow(i);
				FullOrgUnitInfo orgInfo = (FullOrgUnitInfo) row2.getCell(
						"shareOrgUnit").getUserObject();
				if (orgInfo != null) {
					list.add(orgInfo.getId().toString());
				}
			}
			for (int j = 0; j < object.length; j++) {
				FullOrgUnitInfo info = (FullOrgUnitInfo) object[j];
				if (org.getId().toString().equals(info.getId().toString())) {
					MsgBox.showInfo(info.getName() + "是当前登陆组织!");
					return;
				} else if (SHEHelper.isInclude(info.getId().toString(), list)) {
					MsgBox.showInfo(info.getName() + "已经存在不要重复添加！");
					return;
				} else {
					SHEProjectInfo sellInfo = (SHEProjectInfo) this.editData;
					if (sellInfo.getOrgUnit().getId().toString().equals(
							info.getId().toString())) {
						MsgBox.showInfo(info.getName() + "组是该项目的创建组织，不用共享！");
						return;
					}

					this.kDTable1.checkParsed();
					row = this.kDTable1.addRow(j);
					SHEShareOrgUnitInfo orgUnitInfo = new SHEShareOrgUnitInfo();
					orgUnitInfo.setOrgUnit(info);
					orgUnitInfo.setOperationPerson(userInfo);
					if (STATUS_ADDNEW.equals(getOprtState())) {
						orgUnitInfo.setCreateOrgUnit(org);
					} else {
						orgUnitInfo.setCreateOrgUnit(sellInfo.getOrgUnit());
					}

					orgUnitInfo.setShareDate(FDCSQLFacadeFactory
							.getRemoteInstance().getServerTime());
					// 显示共享组织信息
					showShareOrgUnit(row, orgUnitInfo);
				}
			}
		}
	}

	public void actionBtnDelete_actionPerformed(ActionEvent e) throws Exception {
		super.actionBtnDelete_actionPerformed(e);
		int activeRowIndex = this.kDTable1.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex == -1) {
			MsgBox.showInfo("请选择行");
			return;
		}
		/*SHEShareOrgUnitInfo orgUnitInfo = (SHEShareOrgUnitInfo) this.kDTable1
				.getRow(activeRowIndex).getUserObject();*/
		if (this.editData.getId() != null) {
			/*
			 * String projectID = this.editData.getId().toString(); boolean
			 * isUsed =
			 * MarketingUnitSellProjectFactory.getRemoteInstance().exists(
			 * "where head.orgUnit.id='"
			 * +orgUnitInfo.getOrgUnit().getId().toString
			 * ()+"' and sellProject='"+projectID+"'"); if(isUsed) {
			 * MsgBox.showInfo("该共享组织已引用项目，不能取消组织共享"); return; }
			 */
			this.kDTable1.removeRow(activeRowIndex);
		} else {
			this.kDTable1.removeRow(activeRowIndex);
		}
	}

	private void showShareOrgUnit(IRow row, SHEShareOrgUnitInfo orgUnitInfo) {
		row.setUserObject(orgUnitInfo);
		row.getCell("shareOrgUnit")
				.setValue(orgUnitInfo.getOrgUnit().getName());
		row.getCell("shareOrgUnit").setUserObject(orgUnitInfo.getOrgUnit());
		row.getCell("shareOrgUnit").getStyleAttributes().setLocked(true);
		row.getCell("shareOperationer").setValue(
				orgUnitInfo.getOperationPerson().getName());
		row.getCell("shareOperationer").setUserObject(
				orgUnitInfo.getOperationPerson());
		row.getCell("shareOperationer").getStyleAttributes().setLocked(true);
		row.getCell("shareDate").setValue(orgUnitInfo.getShareDate());
		row.getCell("shareDate").getStyleAttributes().setLocked(true);
		row.getCell("shareOpOrgUnit").setValue(org.getName());
		row.getCell("shareOpOrgUnit").setUserObject(org);
		row.getCell("shareOpOrgUnit").getStyleAttributes().setLocked(true);
	}

	private void showLoadOrgUnit(IRow row, SHEShareOrgUnitInfo orgUnitInfo) {
		row.setUserObject(orgUnitInfo);
		row.getCell("shareOrgUnit")
				.setValue(orgUnitInfo.getOrgUnit().getName());
		row.getCell("shareOrgUnit").setUserObject(orgUnitInfo.getOrgUnit());
		row.getCell("shareOrgUnit").getStyleAttributes().setLocked(true);
		row.getCell("shareOperationer").setValue(
				orgUnitInfo.getOperationPerson().getName());
		row.getCell("shareOperationer").setUserObject(
				orgUnitInfo.getOperationPerson());
		row.getCell("shareOperationer").getStyleAttributes().setLocked(true);
		row.getCell("shareDate").setValue(orgUnitInfo.getShareDate());
		row.getCell("shareDate").getStyleAttributes().setLocked(true);
		row.getCell("shareOpOrgUnit").setValue(
				orgUnitInfo.getCreateOrgUnit().getName());
		row.getCell("shareOpOrgUnit").setUserObject(
				orgUnitInfo.getCreateOrgUnit());
		row.getCell("shareOpOrgUnit").getStyleAttributes().setLocked(true);
	}

	public void actionAddRoom_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddRoom_actionPerformed(e);
		this.roomIdList.clear();
		this.roomTwoList.clear();
		for (int i = 0; i < kDTable2.getRowCount(); i++) {
			IRow row2 = kDTable2.getRow(i);
			String id = row2.getCell("roomId").getValue().toString();
			if (id != null) {
				this.roomIdList.add(id);
			}
			RoomModelInfo room = new RoomModelInfo();
			room.setId((BOSUuid) row2.getCell("roomId").getValue());
			room.setName((String) row2.getCell("roomName").getValue());
			room.setNumber((String) row2.getCell("roomNumber").getValue());
			if (row2.getCell("roomType").getUserObject() != null) {
				room.setRoomModelType((RoomModelTypeInfo) row2.getCell(
						"roomType").getUserObject());
			}
			room.setDescription((String) row2.getCell("roomDesc").getValue());
			this.roomTwoList.add(room);
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put(RoomModelEditUI.KEY_DESTORY_WINDOW, Boolean.TRUE);
		// 创建UI对象并显示

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomModelEditUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();

		RoomModelEditUI cusEditUI = (RoomModelEditUI) uiWindow.getUIObject();
		RoomModelInfo cus = (RoomModelInfo) cusEditUI.getUIContext().get(
				RoomModelEditUI.KEY_SUBMITTED_DATA);
		if (cus != null) {
			this.roomIdList.add(cus.getId().toString());
			this.roomTwoList.add(cus);
		}
		addRoomRow(cus);
		if (!OprtState.ADDNEW.equals(this.getOprtState())) {
			this.updateRoomModel(false);
		}
	}

	protected void addRoomRow(RoomModelInfo room) {
		if (room == null) {
			return;
		}

		int activeRowIndex = this.kDTable2.getSelectManager()
				.getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.kDTable2.addRow();
		} else {
			row = this.kDTable2.addRow(activeRowIndex + 1);
		}

		showLoadRoomModel(row, room);
	}

	public void actionDeleteRoom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDeleteRoom_actionPerformed(e);
		int[] selectRows = KDTableUtil.getSelectedRows(this.kDTable2);
		if (selectRows == null || selectRows.length <= 0) {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			SysUtil.abort();
		}
		List list = new ArrayList();
		String id = "";
		for (int i = 0; i < selectRows.length; i++) {
			id = "";
			int select = selectRows[i];
			IRow row = this.kDTable2.getRow(select);
			if (row == null) {
				continue;
			}
			if (row.getCell("roomId").getValue() != null) {
				id = row.getCell("roomId").getValue().toString();
				list.add(id);
				if (this.roomIdList.contains(id)) {
					this.roomIdList.remove(id);
				}
				for (int j = 0; j < roomTwoList.size(); j++) {
					RoomModelInfo roomInfo = (RoomModelInfo) this.roomTwoList
							.get(j);
					if (roomInfo != null) {
						if (id.equals(roomInfo.getId().toString())) {
							roomTwoList.remove(roomInfo);
						}
					}
				}
				this.kDTable2.removeRow(select);
			}
		}
		try {
			SHEProjectFactory.getRemoteInstance().deleteRoomModel(list);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "删除户型失败");
		}

	}

	private void loadRoomModel() throws BOSException {
		if (this.editData != null && this.editData.getId() != null) {
			RoomModelCollection coll = RoomModelFactory
					.getRemoteInstance()
					.getRoomModelCollection(
							"select id,name,number,roomModelType.id,roomModelType.name,roomModelType.number,description where head='"
									+ this.editData.getId().toString() + "'");
			this.kDTable2.removeRows();
			for (int i = 0; i < coll.size(); i++) {
				RoomModelInfo roomInfo = (RoomModelInfo) coll.get(i);
				this.kDTable2.checkParsed();
				IRow row = this.kDTable2.addRow(i);
				showLoadRoomModel(row, roomInfo);
			}
		}
	}

	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return;
		}
		boolean isExist = true;
		SellProjectInfo pur = new SellProjectInfo();
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(pur, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(pur, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(pur,
					currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(pur, currentOrgId);
			} else {
				String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(pur, currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(pur, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								pur, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(pur, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}

	protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
					.getRemoteInstance();
			if (orgId == null || orgId.trim().length() == 0) {
				orgId = OrgConstants.DEF_CU_ID;
			}
			if (iCodingRuleManager.isExist(caller, orgId)) {
				String number = "";
				if (iCodingRuleManager.isUseIntermitNumber(caller, orgId)) { // 此处的orgId与步骤1
					// 判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(caller, orgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								caller, orgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(caller, orgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						} else {
							number = iCodingRuleManager
									.getNumber(caller, orgId);
						}
					} else {
						// 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
						number = iCodingRuleManager.readNumber(caller, orgId);
					}
				} else {
					// 没有启用断号支持功能，此时获取了编码规则产生的编码
					String costCenterId = null;
					if (editData instanceof SHEProjectInfo
							&& ((SHEProjectInfo) editData).getOrgUnit() != null) {
						costCenterId = ((SHEProjectInfo) editData).getOrgUnit()
								.getId().toString();
					} else {
						costCenterId = SysContext.getSysContext()
								.getCurrentCostUnit().getId().toString();
					}

					number = prepareNumberForAddnew(iCodingRuleManager,
							(SHEProjectInfo) editData, orgId, costCenterId,
							null);
				}

				// 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
				prepareNumber(caller, number);
				if (iCodingRuleManager.isModifiable(caller, orgId)) {
					// 如果启用了用户可修改
					setNumberTextEnabled();
				}
				return;
			}
		} catch (Exception err) {
			handleCodingRuleError(err);

			setNumberTextEnabled();
		}

		// 把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
		setNumberTextEnabled();
	}

	public void kDTable2_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
		if (e.getType() == KDTStyleConstants.BODY_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			IRow row = this.kDTable2.getRow(e.getRowIndex());
			Object idObj = row.getCell("roomId").getValue();
			if (idObj == null) {
				return;
			}
			String idStr = "";
			if (idObj instanceof String) {
				idStr = (String) idObj;
			} else if (idObj instanceof BOSUuid) {
				idStr = ((BOSUuid) idObj).toString();
			}
			if (!idStr.equals("")) {
				String uiClassName = "com.kingdee.eas.fdc.sellhouse.client.RoomModelEditUI";
				UIContext uiContext = new UIContext(this);
				uiContext.put(RoomModelEditUI.KEY_DESTORY_WINDOW, Boolean.TRUE);
				uiContext.put(UIContext.ID, idStr);

				try {
					IUIWindow uiWindow = UIFactory.createUIFactory(
							UIFactoryName.MODEL).create(uiClassName, uiContext,
							null, OprtState.VIEW);
					uiWindow.show();
					RoomModelEditUI cusEditUI = (RoomModelEditUI) uiWindow
							.getUIObject();
					RoomModelInfo cus = (RoomModelInfo) cusEditUI
							.getUIContext().get(
									RoomModelEditUI.KEY_SUBMITTED_DATA);
					RoomModelInfo cusDelete = (RoomModelInfo) cusEditUI
							.getUIContext()
							.get(RoomModelEditUI.KEY_DELETE_DATA);
					RoomModelInfo cusNew = (RoomModelInfo) cusEditUI
							.getUIContext().get(RoomModelEditUI.KEY_NEW_DATA);
					if (cus != null) {
						updateLoadRoomModel(e.getRowIndex(), cus);
					}
					if (cusDelete != null) {
						deleteRoomModel(cusDelete);
					}
					if (cusNew != null) {
						addRoomRow(cusNew);
					}

					this.roomIdList.clear();
					this.roomTwoList.clear();
					for (int i = 0; i < kDTable2.getRowCount(); i++) {
						IRow row2 = kDTable2.getRow(i);
						String id = row2.getCell("roomId").getValue()
								.toString();
						if (id != null) {
							this.roomIdList.add(id);
						}
						RoomModelInfo room = new RoomModelInfo();
						room.setId((BOSUuid) row2.getCell("roomId").getValue());
						room.setName((String) row2.getCell("roomName")
								.getValue());
						room.setNumber((String) row2.getCell("roomNumber")
								.getValue());
						if (row2.getCell("roomType").getUserObject() != null) {
							room.setRoomModelType((RoomModelTypeInfo) row2
									.getCell("roomType").getUserObject());
						}
						room.setDescription((String) row2.getCell("roomDesc")
								.getValue());
						this.roomTwoList.add(room);
					}

					this.updateRoomModel(false);

				} catch (UIException e1) {
					logger.error(e1.getMessage() + "打开户型界面失败！");
				}

			}

		}

	}

	private void updateLoadRoomModel(int select, RoomModelInfo room) {

		IRow row = this.kDTable2.getRow(select);
		if (row == null) {
			return;
		}
		if (row.getCell("roomId").getValue() != null) {
			id = row.getCell("roomId").getValue().toString();
			if (id.equals(room.getId().toString())) {
				row.setUserObject(room);
				row.getCell("roomNumber").setValue(room.getNumber());
				row.getCell("roomNumber").setUserObject(room.getNumber());
				row.getCell("roomNumber").getStyleAttributes().setLocked(true);
				row.getCell("roomName").setValue(room.getName());
				row.getCell("roomName").setUserObject(room.getName());
				row.getCell("roomName").getStyleAttributes().setLocked(true);
				if (room.getRoomModelType() != null) {
					row.getCell("roomType").setValue(
							room.getRoomModelType().getName());
					row.getCell("roomType").setUserObject(
							room.getRoomModelType());
				}
				row.getCell("roomType").getStyleAttributes().setLocked(true);
				row.getCell("roomDesc").setValue(room.getDescription());
				row.getCell("roomDesc").getStyleAttributes().setLocked(true);
				row.getCell("roomId").setValue(room.getId());
				row.getCell("roomId").setUserObject(room.getId());
				row.getCell("roomId").getStyleAttributes().setLocked(true);
			}

		}

	}

	private void deleteRoomModel(RoomModelInfo cusDelete) {

		int[] selectRows = KDTableUtil.getSelectedRows(this.kDTable2);
		if (selectRows == null || selectRows.length <= 0) {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			SysUtil.abort();
		}
		for (int i = 0; i < selectRows.length; i++) {
			id = "";
			int select = selectRows[i];
			IRow row = this.kDTable2.getRow(select);
			if (row == null) {
				continue;
			}
			if (row.getCell("roomId").getValue() != null) {
				id = row.getCell("roomId").getValue().toString();
				if (id.equals(cusDelete.getId().toString())) {

					if (this.roomIdList.contains(id)) {
						this.roomIdList.remove(id);
					}
					for (int j = 0; j < roomTwoList.size(); j++) {
						RoomModelInfo roomInfo = (RoomModelInfo) this.roomTwoList
								.get(j);
						if (roomInfo != null) {
							if (id.equals(roomInfo.getId().toString())) {
								roomTwoList.remove(roomInfo);
							}
						}
					}
					this.kDTable2.removeRow(select);
				}

			}
		}
	}

	// 编码规则中启用了“新增显示”,必须检验是否已经重复
	private String prepareNumberForAddnew(
			ICodingRuleManager iCodingRuleManager, SHEProjectInfo info,
			String orgId, String costerId, String bindingProperty)
			throws Exception {

		String number = null;
		FilterInfo filter = null;
		int i = 0;
		do {
			// 如果编码重复重新取编码
			if (bindingProperty != null) {
				number = iCodingRuleManager.getNumber(editData, orgId,
						bindingProperty, "");
			} else {
				number = iCodingRuleManager.getNumber(editData, orgId);
			}

			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			// filter.getFilterItems().add(
			// new FilterItemInfo("state", FDCBillStateEnum.INVALID
			// .getValue(), CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		} while (((ISHEProject) getBizInterface()).exists(filter) && i < 1000);

		return number;
	}

	protected void setNumberTextEnabled() {
		if (getNumberCtrl() != null) {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentSaleUnit();
			if (org != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						new PurchaseInfo(), org.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void initOldData(IObjectValue dataObject) {
		if (OprtState.EDIT.equals(this.getOprtState())) {
			super.initOldData(dataObject);
		}
	}
}