/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
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
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.MarketUnitSellProFactory;
import com.kingdee.eas.fdc.basecrm.client.SHEOrgUnitListUI;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitCollection;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ShareRoomModelsCollection;
import com.kingdee.eas.fdc.sellhouse.ShareRoomModelsFactory;
import com.kingdee.eas.fdc.sellhouse.ShareRoomModelsInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SHESellProjectEditUI extends AbstractSHESellProjectEditUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8300012322818954860L;
	private static final Logger logger = CoreUIObject
			.getLogger(SHESellProjectEditUI.class);

	private CompanyOrgUnitInfo companyInfo = SysContext.getSysContext()
			.getCurrentFIUnit();
	private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	private FullOrgUnitInfo org = SysContext.getSysContext()
			.getCurrentOrgUnit().castToFullOrgUnitInfo();
	private String id = "";
	private static final String CHECK_NAME = "name";
	private static final String CHECK_NUMBER = "number";
	private boolean isOld = false;
	private boolean isParentRoomModel = false;
	private SellProjectInfo sellProject = null;
	private List roomModelList  = null;

	/**
	 * output class constructor
	 */
	public SHESellProjectEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		if (this.prmtCompanyOrgUnit.getValue() != null) {
			CompanyOrgUnitInfo info = (CompanyOrgUnitInfo) this.prmtCompanyOrgUnit
					.getValue();
			this.editData.setCompanyOrgUnit(info);
		}

		if (this.editData.getOrgUnit() == null) {
			FullOrgUnitInfo info = SysContext.getSysContext()
					.getCurrentOrgUnit().castToFullOrgUnitInfo();
			this.editData.setOrgUnit(info);
		}

		if (this.txtDescription.getText() != null
				&& !"".equals(this.txtDescription.getText())) {
			this.editData.setDescription(this.txtDescription.getText());
		}
		/**
		 * 共享组织的保存
		 */
		this.editData.getOrgUnitShareList().clear();
		for (int i = 0; i < kDTable1.getRowCount(); i++) {
			IRow row = kDTable1.getRow(i);
			ShareOrgUnitInfo orgUnitInfo = (ShareOrgUnitInfo) row
					.getUserObject();
			orgUnitInfo.setOrgUnit((FullOrgUnitInfo) row
					.getCell("shareOrgUnit").getUserObject());
			orgUnitInfo.setOperationPerson((UserInfo) row.getCell(
					"shareOperationer").getUserObject());
			orgUnitInfo
					.setShareDate((Date) row.getCell("shareDate").getValue());
			orgUnitInfo.setCreateOrgUnit((FullOrgUnitInfo) row.getCell(
					"shareOpOrgUnit").getUserObject());
			this.editData.getOrgUnitShareList().add(orgUnitInfo);
		}
		
		roomModelList  = new ArrayList();
		this.editData.getShareRoomModels().clear();
		for (int i = 0; i < kDTable2.getRowCount(); i++) {
			IRow row = kDTable2.getRow(i);
			ShareRoomModelsInfo roomModeInfo = new ShareRoomModelsInfo();
			RoomModelInfo room = new RoomModelInfo();
			if (row.getCell("roomNumber").getUserObject() != null) {
				room.setNumber((String) row.getCell("roomNumber")
						.getUserObject());
			}
			if (row.getCell("roomName").getUserObject() != null) {
				room.setName((String) row.getCell("roomName").getUserObject());
			}

			if (row.getCell("roomType").getUserObject() != null) {
				room.setRoomModelType((RoomModelTypeInfo) row.getCell(
						"roomType").getUserObject());
			}
			if (row.getCell("roomDesc").getValue() != null) {
				room
						.setDescription((String) row.getCell("roomDesc")
								.getValue());
			}
			if (row.getCell("roomId").getUserObject() != null) {
				room.setId((BOSUuid) row.getCell("roomId").getUserObject());
			}
			roomModelList.add(room.getId().toString());
			roomModeInfo.setRoomModel(room);
			this.editData.getShareRoomModels().add(roomModeInfo);
		}
		

	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add(new SelectorItemInfo("name"));
		selectors.add(new SelectorItemInfo("number"));
		selectors.add(new SelectorItemInfo("longNumber"));
		selectors.add(new SelectorItemInfo("companyOrgUnit.id"));
		selectors.add(new SelectorItemInfo("companyOrgUnit.number"));
		selectors.add(new SelectorItemInfo("companyOrgUnit.name"));
		selectors.add(new SelectorItemInfo("project.id"));
		selectors.add(new SelectorItemInfo("project.number"));
		selectors.add(new SelectorItemInfo("project.name"));
		selectors.add(new SelectorItemInfo("orgUnit.id"));
		selectors.add(new SelectorItemInfo("orgUnit.name"));
		selectors.add(new SelectorItemInfo("orgUnit.number"));
		selectors.add(new SelectorItemInfo("orgUnitShareList.orgUnit.id"));
		selectors.add(new SelectorItemInfo("orgUnitShareList.orgUnit.name"));
		selectors.add(new SelectorItemInfo("orgUnitShareList.orgUnit.number"));
		selectors.add(new SelectorItemInfo("level"));
		selectors.add(new SelectorItemInfo("parent.id"));
		selectors.add(new SelectorItemInfo("parent.name"));
		selectors.add(new SelectorItemInfo("parent.number"));
		selectors.add(new SelectorItemInfo("parent.longNumber"));
		selectors.add(new SelectorItemInfo(
				"orgUnitShareList.operationPerson.id"));
		selectors.add(new SelectorItemInfo(
				"orgUnitShareList.operationPerson.name"));
		selectors.add(new SelectorItemInfo(
				"orgUnitShareList.operationPerson.number"));
		selectors
				.add(new SelectorItemInfo("orgUnitShareList.createOrgUnit.id"));
		selectors.add(new SelectorItemInfo(
				"orgUnitShareList.createOrgUnit.name"));
		selectors.add(new SelectorItemInfo(
				"orgUnitShareList.createOrgUnit.number"));
		selectors.add(new SelectorItemInfo("orgUnitShareList.shareDate"));
		selectors.add(new SelectorItemInfo("shareRoomModels.roomModel.id"));
		selectors.add(new SelectorItemInfo("shareRoomModels.roomModel.name"));
		selectors.add(new SelectorItemInfo("shareRoomModels.roomModel.number"));
		selectors.add(new SelectorItemInfo(
				"shareRoomModels.roomModel.description"));
		selectors.add(new SelectorItemInfo(
				"shareRoomModels.roomModel.roomModelType.id"));
		selectors.add(new SelectorItemInfo(
				"shareRoomModels.roomModel.roomModelType.name"));
		selectors.add(new SelectorItemInfo(
				"shareRoomModels.roomModel.roomModelType.number"));

		selectors.add(new SelectorItemInfo("description"));
		selectors.add(new SelectorItemInfo("longNumber"));

		return selectors;
	}

	public void actionBtnAdd_actionPerformed(ActionEvent e) throws Exception {
		super.actionBtnAdd_actionPerformed(e);

		if (this.editData.getParent() != null) {
			FDCMsgBox.showWarning(this, "只有一级项目才能共享!");
			this.abort();
		}

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
					SellProjectInfo sellInfo = (SellProjectInfo) this.editData;
					if (sellInfo.getOrgUnit().getId().toString().equals(
							info.getId().toString())) {
						MsgBox.showInfo(info.getName() + "组是该项目的创建组织，不用共享！");
						return;
					}

					this.kDTable1.checkParsed();
					row = this.kDTable1.addRow(j);
					ShareOrgUnitInfo orgUnitInfo = new ShareOrgUnitInfo();
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

	/**
	 * 初始化工程项目
	 */
	private void initCurrProject() {
		String query = "com.kingdee.eas.fdc.basedata.app.F7ProjectQuery";
		FilterInfo filterInfo = new FilterInfo();
		if (org != null && org.getId() != null) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id",
							org.getId().toString(), CompareType.EQUALS));
		}
		SHEHelper.initF7(this.prmtProject, query, filterInfo);
	}

	private void showShareOrgUnit(IRow row, ShareOrgUnitInfo orgUnitInfo) {
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

	private void showLoadOrgUnit(IRow row, ShareOrgUnitInfo orgUnitInfo) {
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

	public void actionBtnDel_actionPerformed(ActionEvent e) throws Exception {
		super.actionBtnDel_actionPerformed(e);
		int activeRowIndex = this.kDTable1.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex == -1) {
			MsgBox.showInfo("请选择行");
			return;
		}
		if (this.editData.getId() != null) {
			this.kDTable1.removeRow(activeRowIndex);
		} else {
			this.kDTable1.removeRow(activeRowIndex);
		}
	}

	public void actionBtnAddRoom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionBtnAddRoom_actionPerformed(e);

		UIContext uiContext = new UIContext(this);
		uiContext.put(RoomModelEditUI.KEY_DESTORY_WINDOW, Boolean.TRUE);
		uiContext.put("SheSellProjectID", this.editData.getId().toString());
		// 创建UI对象并显示

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomModelEditUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();

		RoomModelEditUI cusEditUI = (RoomModelEditUI) uiWindow.getUIObject();
		RoomModelInfo cus = (RoomModelInfo) cusEditUI.getUIContext().get(
				RoomModelEditUI.KEY_SUBMITTED_DATA);
		if (cus != null) {
			addRoomRow(cus);
			addNewRoomModelInShareList(cus);
		}

	}

	private void addNewRoomModelInShareList(RoomModelInfo cus) {
		if (!OprtState.ADDNEW.equals(this.getOprtState())) {
			try {
				ShareRoomModelsInfo model = new ShareRoomModelsInfo();
				model.setHead(this.editData);
				String temp = FDCSQLFacadeFactory.getRemoteInstance()
						.getServerTime().toString();
				model.setNumber(this.editData.getNumber() + temp);
				model.setName(this.editData.getName() + temp);
				model.setRoomModel(cus);
				ShareRoomModelsFactory.getRemoteInstance().addnew(model);
			} catch (Exception ex) {
				logger.error(ex.getMessage() + "新增共享户型失败!");
			}
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

		showRoomModel(row, room);
	}

	private void showRoomModel(IRow row, RoomModelInfo room) {
		if (room != null) {
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
				row.getCell("roomType").setUserObject(room.getRoomModelType());
			}
			row.getCell("roomType").getStyleAttributes().setLocked(true);
			row.getCell("roomDesc").setValue(room.getDescription());
			row.getCell("roomDesc").getStyleAttributes().setLocked(true);
			row.getCell("roomId").setValue(room.getId());
			row.getCell("roomId").setUserObject(room.getId());
			row.getCell("roomId").getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * 将户型填充到table中
	 * 
	 * @param row
	 * @param room
	 */
	private void showLoadRoomModel(IRow row, ShareRoomModelsInfo shareRoom) {
		if (shareRoom.getRoomModel() != null) {
			row.setUserObject(shareRoom);
			if (shareRoom.getRoomModel().getNumber() != null) {
				row.getCell("roomNumber").setValue(
						shareRoom.getRoomModel().getNumber());
				row.getCell("roomNumber").setUserObject(
						shareRoom.getRoomModel().getNumber());
			}
			row.getCell("roomNumber").getStyleAttributes().setLocked(true);
			if (shareRoom.getRoomModel().getName() != null) {
				row.getCell("roomName").setValue(
						shareRoom.getRoomModel().getName());
				row.getCell("roomName").setUserObject(
						shareRoom.getRoomModel().getName());
			}
			row.getCell("roomName").getStyleAttributes().setLocked(true);

			if (shareRoom.getRoomModel().getRoomModelType() != null) {
				row.getCell("roomType").setValue(
						shareRoom.getRoomModel().getRoomModelType().getName());
				row.getCell("roomType").setUserObject(
						shareRoom.getRoomModel().getRoomModelType());
			}
			row.getCell("roomType").getStyleAttributes().setLocked(true);
			row.getCell("roomDesc").setValue(
					shareRoom.getRoomModel().getDescription());
			row.getCell("roomDesc").getStyleAttributes().setLocked(true);
			if (shareRoom.getRoomModel().getId() != null) {
				row.getCell("roomId")
						.setValue(shareRoom.getRoomModel().getId());
				row.getCell("roomId").setUserObject(
						shareRoom.getRoomModel().getId());

			}
			row.getCell("roomId").getStyleAttributes().setLocked(true);
		}
	}

	public void actionBtnDelRoom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionBtnDelRoom_actionPerformed(e);
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
				checkRoomModelIsDelete(id);
				list.add(id);
				this.kDTable2.removeRow(select);
			}
		}
		if (list.size() > 0) {
			RoomModelInfo cusDelete = new RoomModelInfo();
			checkRoomModelIsDelete(list.get(0).toString());
			cusDelete.setId(BOSUuid.read(list.get(0).toString()));
			this.deleteRoomModelInDB(cusDelete, false);
		}

	}

	/**
	 * 校验已经被房间使用的户型,是不能被删除的
	 * @param idString
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void checkRoomModelIsDelete(String idString) throws EASBizException, BOSException {
		FilterInfo filter  = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("roomModel.id",idString,CompareType.EQUALS));
		if(RoomFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this, "户型已经被使用,不能删除!");
			SysUtil.abort();
		}
		
	}

	protected IObjectValue createNewData() {
		SellProjectInfo info = new SellProjectInfo();
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		SellProjectInfo sheProjectInfo = (SellProjectInfo) this.getUIContext()
				.get("sheProject");
		if (sheProjectInfo != null) {
			this.sellProject = sheProjectInfo;
			info.setParent(sheProjectInfo);
		}else{
			if(this.sellProject!=null){
				info.setParent(sellProject);
			}
		}
		if (companyInfo != null && companyInfo.isIsBizUnit()) {
			info.setCompanyOrgUnit(companyInfo);
		}

		FullOrgUnitInfo fullOrginfo = SysContext.getSysContext()
				.getCurrentOrgUnit().castToFullOrgUnitInfo();
		if (fullOrginfo != null) {
			info.setOrgUnit(fullOrginfo);
		}
		info.setIsForSHE(true);
		return info;
	}
	
	private void fillRoomModelFromParent(SellProjectInfo sellProject){
			RoomModelCollection coll = createNewRoomModel(sellProject);
		
			if(coll==null){
				return;
			}
			
			for (int i = 0; i < coll.size(); i++) {
				RoomModelInfo roomModel = coll.get(i);
				ShareRoomModelsInfo item = new ShareRoomModelsInfo();
				item.setRoomModel(roomModel);
				this.editData.getShareRoomModels().add(item);
			}
			if(this.editData.getShareRoomModels().size()>0){
				this.isParentRoomModel = true;
			}
			kDTable2.removeRows();
			for (int i = 0; i < this.editData.getShareRoomModels().size(); i++) {
				ShareRoomModelsInfo roomModel = this.editData.getShareRoomModels().get(i);
				kDTable2.checkParsed();
				IRow row = kDTable2.addRow(i);
				showLoadRoomModel(row, roomModel);
			}
		
	}

	private RoomModelCollection createNewRoomModel(
			SellProjectInfo sellProject) {
		if(sellProject==null){
			return null;
		}
		RoomModelCollection roomModel = new RoomModelCollection();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("name"));
		coll.add(new SelectorItemInfo("number"));
		coll.add(new SelectorItemInfo("roomModelType.id"));
		coll.add(new SelectorItemInfo("roomModelType.name"));
		coll.add(new SelectorItemInfo("roomModelType.number"));
		coll.add(new SelectorItemInfo("sellProject.id"));
		coll.add(new SelectorItemInfo("sellProject.name"));
		coll.add(new SelectorItemInfo("sellProject.number"));
		coll.add(new SelectorItemInfo("pic.id"));
		coll.add(new SelectorItemInfo("pic.imgData"));
		coll.add(new SelectorItemInfo("description"));
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProject.getId().toString(),CompareType.EQUALS));
		view.setFilter(filter);
		view.setSelector(coll);
		
		try {
		RoomModelCollection roomModelColl = RoomModelFactory.getRemoteInstance().getRoomModelCollection(view);
		for (int i = 0; i < roomModelColl.size(); i++) {
			RoomModelInfo info = roomModelColl.get(i);
			info.setId(null);
			info.setSellProject(null);
			IObjectPK pk = RoomModelFactory.getRemoteInstance().addnew(info);
			info.setId(BOSUuid.read(pk.toString()));
			roomModel.add(info);
		}
		} catch (BOSException e) {
			logger.error(e.getMessage());
		} catch(EASBizException ex){
			logger.error(ex.getMessage());
		}
		return roomModel;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SellProjectFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(true);
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

		this.kDTable2.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		this.prmtProject.setEditable(false);

		if (isSellOrg()) {
			this.actionEdit.setEnabled(true);
			this.actionAddNew.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionSave.setEnabled(true);
		}
		this.prmtCompanyOrgUnit.setEditable(false);
		this.actionBtnAddRoom.setEnabled(true);
		this.actionBtnDelRoom.setEnabled(true);
		initCurrProject();
		if(OprtState.ADDNEW.equals(this.getOprtState())){
			this.actionAddNew.setEnabled(false);
		}
		if(editData.getParent()!=null){
			this.prmtProjectBase.setEnabled(false);
		}else{
			Set id=new HashSet();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("projectBase.id",null,CompareType.NOTEQUALS));
			
			if(editData.getId()!=null){
				filter.getFilterItems().add(new FilterItemInfo("id",editData.getId(),CompareType.NOTEQUALS));
			}
			view.setFilter(filter);
			SellProjectCollection col=SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
			for(int i=0;i<col.size();i++){
				id.add(col.get(i).getProjectBase().getId().toString());
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isNewProject",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("engineeProject.id",org.getId()));
			
			if(id.size()>0){
				filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.NOTINCLUDE));
			}
			view.setFilter(filter);
			this.prmtProjectBase.setEntityViewInfo(view);
			this.prmtProjectBase.setRequired(true);
		}
	}

	private boolean isSellOrg() {
		boolean res = false;
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if(saleOrg.isIsBizUnit()){
			res = true;
		}
//		try {
//			FullOrgUnitInfo fullOrginfo = SysContext.getSysContext()
//					.getCurrentOrgUnit().castToFullOrgUnitInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("sellHouseStrut", Boolean.TRUE,
//							CompareType.EQUALS));
//			if (fullOrginfo != null) {
//				filter.getFilterItems().add(
//						new FilterItemInfo("unit.id", fullOrginfo.getId()
//								.toString(), CompareType.EQUALS));
//			}
//			filter.getFilterItems().add(
//					new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID,
//							CompareType.EQUALS));
//			res = FDCOrgStructureFactory.getRemoteInstance().exists(filter);
//		} catch (Exception ex) {
//			logger.error(ex.getMessage() + "获得售楼组织失败!");
//		}

		return res;
	}

	private void setCompanyOrgUnit() {
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			if (companyInfo != null && companyInfo.isIsBizUnit()) {
				this.prmtCompanyOrgUnit.setValue(companyInfo);
			} else {
				this.prmtCompanyOrgUnit.setValue(null);
			}
		}

		if (OprtState.EDIT.equals(this.getOprtState())) {
			this.txtNumber.setEnabled(false);
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		if (OprtState.VIEW.equals(this.getOprtState())) {
			setBtnState(false);
		}
		if (OprtState.EDIT.equals(this.getOprtState())) {

			setBtnState(true);
			this.actionEdit.setEnabled(false);
		}
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			setBtnState(true);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			handleCodingRule();
		}
		if (!this.isSellOrg()) {
			this.actionBtnAddRoom.setEnabled(false);
			this.actionBtnDelRoom.setEnabled(false);
			this.kDTable2.getStyleAttributes().setLocked(true);
		} else {
			if (this.editData.getId() == null) {
				this.actionBtnAddRoom.setEnabled(false);
				this.actionBtnDelRoom.setEnabled(false);
			} else {
				this.actionBtnAddRoom.setEnabled(true);
				this.actionBtnDelRoom.setEnabled(true);
			}
		}

		prmtCompanyOrgUnit.setEnabled(false);
		this.prmtProject.setEditable(false);
		
		if(OprtState.VIEW.equals(this.getOprtState())){
			FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			if(this.editData.getOrgUnit()!=null){
				String orgUnitId = this.editData.getOrgUnit().getId().toString();
				if(!orgUnitId.equals(orgUnit.getId().toString())){
					this.actionBtnAddRoom.setEnabled(false);
					this.actionBtnDelRoom.setEnabled(false);
				}else{
					this.actionBtnAddRoom.setEnabled(true);
					this.actionBtnDelRoom.setEnabled(true);
				}
			}
		}
		if(OprtState.ADDNEW.equals(this.getOprtState())){
			if(this.sellProject!=null){
				this.fillRoomModelFromParent(sellProject);
			}
		}
	}

	private void setBtnState(boolean result) {
		this.btnAdd.setEnabled(result);
		this.btnDel.setEnabled(result);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		isShareProject("共享项目不能修改!");
		super.actionEdit_actionPerformed(e);
		if (OprtState.EDIT.equals(this.getOprtState())) {
			// this.txtName.setEnabled(false);
			this.txtNumber.setEnabled(false);
		}
		setBtnState(true);
		this.prmtCompanyOrgUnit.setEnabled(false);
		this.prmtProject.setEditable(false);
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
						updateRoomModel(cus);
					}
					if (cusDelete != null) {
						deleteRoomModel(cusDelete);
						deleteRoomModelInDB(cusDelete, true);
					}
					if (cusNew != null && cusNew.getName() != null
							&& cusNew.getNumber() != null) {
						addRoomRow(cusNew);
						addNewRoomModelInShareList(cusNew);
					}
				} catch (UIException e1) {
					logger.error(e1.getMessage() + "打开户型界面失败！");
				}

			}

		}

	}

	private void deleteRoomModelInDB(RoomModelInfo cusDelete, boolean isDelete) {
		try {
			if (!isDelete) {
				RoomModelFactory.getRemoteInstance().delete(
						new ObjectUuidPK(cusDelete.getId()));
			}
			FDCSQLBuilder sql = new FDCSQLBuilder();
			String sqlStr = "delete from T_SHE_ShareRoomModels where froommodelid=?";
			sql.appendSql(sqlStr);
			sql.addParam(cusDelete.getId().toString());
			sql.execute();
		} catch (EASBizException e1) {
			logger.error(e1.getMessage() + "删除户型失败!");
		} catch (BOSException e1) {
			logger.error(e1.getMessage() + "删除户型失败!");
		}
	}

	private void updateRoomModel(RoomModelInfo cus) {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("id"));
		selector.add(new SelectorItemInfo("name"));
		selector.add(new SelectorItemInfo("number"));
		selector.add(new SelectorItemInfo("description"));
		selector.add(new SelectorItemInfo("roomModelType.name"));
		selector.add(new SelectorItemInfo("roomModelType.number"));
		try {
			RoomModelFactory.getRemoteInstance().updatePartial(cus, selector);
		} catch (EASBizException e1) {
			logger.error(e1.getMessage() + "更新户型失败!");
		} catch (BOSException e1) {
			logger.error(e1.getMessage() + "更新户型失败!");
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
			int select = selectRows[i];
			IRow row = this.kDTable2.getRow(select);
			if (row == null) {
				continue;
			}
			if (row.getCell("roomId").getValue() != null) {
				id = row.getCell("roomId").getValue().toString();
				if (id.equals(cusDelete.getId().toString())) {
					this.kDTable2.removeRow(select);
				}

			}
		}
	}

	protected void initOldData(IObjectValue dataObject) {
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			// super.initOldData(dataObject);
		} else {
			if (!this.isOld) {
				super.initOldData(dataObject);
			}
		}
	}

	public void loadFields() {
		super.loadFields();
		SellProjectInfo info = this.editData;
		if (info != null && info.getCompanyOrgUnit() != null) {
			this.prmtCompanyOrgUnit.setValue(info.getCompanyOrgUnit());
		} else {
			this.prmtCompanyOrgUnit.setValue(null);
		}

		if (info.getDescription() != null) {
			this.txtDescription.setText(info.getDescription());
		}
		/**
		 * 共享组织的加载
		 */
		ShareOrgUnitCollection orgColl = info.getOrgUnitShareList();
		kDTable1.removeRows();
		for (int i = 0; i < orgColl.size(); i++) {
			ShareOrgUnitInfo orgUnitInfo = orgColl.get(i);
			kDTable1.checkParsed();
			IRow row = kDTable1.addRow(i);

			showLoadOrgUnit(row, orgUnitInfo);
		}

		ShareRoomModelsCollection roomColl = info.getShareRoomModels();
		kDTable2.removeRows();
		for (int i = 0; i < roomColl.size(); i++) {
			ShareRoomModelsInfo orgUnitInfo = roomColl.get(i);
			kDTable2.checkParsed();
			IRow row = kDTable2.addRow(i);
			showLoadRoomModel(row, orgUnitInfo);
		}
		
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		checkDumpName();
		checkCompanyOrgUnit();
		checkProject();
//		if(editData.getParent()==null){
//			FDCClientVerifyHelper.verifyEmpty(this, this.prmtProjectBase);
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("projectBase.id",((ProjectBaseInfo)this.prmtProjectBase.getValue()).getId()));
//			if(editData.getId()!=null){
//				filter.getFilterItems().add(new FilterItemInfo("id",editData.getId(),CompareType.NOTEQUALS));
//			}
//			if(SellProjectFactory.getRemoteInstance().exists(filter)){
//				MsgBox.showInfo("主数据项目信息重复选择！");
//				SysUtil.abort();
//			}
//		}
		this.isOld = true;
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		super.actionSubmit_actionPerformed(e);
		this.actionAddNew.setEnabled(true);
		if (this.editData.getId() != null) {
			this.actionBtnAddRoom.setEnabled(true);
			this.actionBtnDelRoom.setEnabled(true);

			if(this.isParentRoomModel && roomModelList!=null && roomModelList.size()>0){
				SellProjectFactory.getRemoteInstance().updateRoomModelForChild(this.editData.getId(),roomModelList);
			}
		}

	}

	private void checkCompanyOrgUnit() {
		if (this.prmtCompanyOrgUnit.getValue() == null) {
			FDCMsgBox.showWarning(this, "财务组织不能为空，请重新设置!");
			this.abort();
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if(OprtState.VIEW.equals(this.getOprtState()) || OprtState.EDIT.equals(this.getOprtState())){
			if(this.editData!=null && this.editData.getParent()!=null){
				this.sellProject = this.editData.getParent();
			}
		}
		handleCodingRule();
		super.actionAddNew_actionPerformed(e);
		this.txtDescription.setText(null);
		this.prmtCompanyOrgUnit.setEnabled(false);
		this.actionBtnAddRoom.setEnabled(false);
		this.actionBtnDelRoom.setEnabled(false);
		this.actionAddNew.setEnabled(false);
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

		Object object = this.prmtProject.getValue();
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
							FDCMsgBox.showWarning(this, "工程项目在上级已存在,不能重复!");
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

	private void checkDumpName() throws Exception {
		SellProjectInfo info = (SellProjectInfo) this.editData;
		if (info == null) {
			return;
		}
		SellProjectInfo parentInfo = info.getParent();
		String number = this.txtNumber.getText();
		if (number == null || number == "") {
			MsgBox.showInfo("项目编码不能为空！");
			SysUtil.abort();
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			//filter.getFilterItems().add(
					//new FilterItemInfo("isForSHE", Boolean.TRUE,
							//CompareType.EQUALS));
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
				//filter.getFilterItems().add(
						//new FilterItemInfo("isForSHE", Boolean.TRUE,
								//CompareType.EQUALS));
				if (parentInfo != null && parentInfo.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("parent.id", parentInfo.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("parent.id", null,
									CompareType.EQUALS));
				}
				filter.getFilterItems().add(
						new FilterItemInfo("level", String
								.valueOf(this.editData.getLevel())));
				if (SellProjectFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下项目编码不能重复！");
					SysUtil.abort();
				}
			}
			// }

		}
		String name = this.txtName.getText();
		if (name == null || "".equals(name)) {
			MsgBox.showInfo("项目名称不能为空！");
			SysUtil.abort();
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));
			//filter.getFilterItems().add(
					//new FilterItemInfo("isForSHE", Boolean.TRUE,
							//CompareType.EQUALS));
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
				//filter.getFilterItems().add(
						//new FilterItemInfo("isForSHE", Boolean.TRUE,
								//CompareType.EQUALS));
				if (parentInfo != null && parentInfo.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("parent.id", parentInfo.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("parent.id", null,
									CompareType.EQUALS));
				}
				filter.getFilterItems().add(
						new FilterItemInfo("level", String
								.valueOf(this.editData.getLevel())));
				if (SellProjectFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下项目名称不能重复！");
					SysUtil.abort();
				}
			}

		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		isShareProject("共享项目不能删除!");
		
		isExistProjectInBuilding(this.editData.getLongNumber());
		checkRemoveForMarkinget(this.editData.getId().toString());

		if (confirmRemove()) {
			
			FilterInfo newFilter = new FilterInfo();
			newFilter.getFilterItems().add(
					new FilterItemInfo("id",this.editData.getId().toString(),
							CompareType.EQUALS));

			SellProjectInfo project= SellProjectFactory.getRemoteInstance().getSellProjectInfo("select id,importID where id='"+this.editData.getId().toString()+"'");
			if(project!=null && project.getImportID()!=null){
				boolean isUse = false;
				
				isUse = isUseForOtherSystem(this.editData.getId().toString());
				//判断项目资料使用是否被其他系统使用
				if(isUse){
					SellProjectFactory.getRemoteInstance().updateDeleteStatus(BOSUuid.read(this.editData.getId().toString()));
				}else{
					//没有被使用,则删除
					SellProjectFactory.getRemoteInstance().deleteProjectInSystem(BOSUuid.read(this.editData.getId().toString()));
				}
			}else{
				//是自己建立的项目,则直接删除
				SellProjectFactory.getRemoteInstance().deleteSellProject(BOSUuid.read(this.editData.getId().toString()));
			}
			
			this.actionAddNew_actionPerformed(e);
			prmtCompanyOrgUnit.setEnabled(false);
		}

	}
	
	private boolean isUseForOtherSystem(String id){
		boolean result =true;
		
		
		
		return result;
	}
	
	private void isExistProjectInBuilding(String longNumber) throws BOSException,
			EASBizException {
		
		if (longNumber != null && !"".equals(longNumber)) {

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();

			filter.getFilterItems().add(
					new FilterItemInfo("longNumber", longNumber,
							CompareType.EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("longNumber", longNumber + "!%",
							CompareType.LIKE));

			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add(new SelectorItemInfo("id"));
			coll.add(new SelectorItemInfo("name"));
			filter.setMaskString("#0 or #1");
			view.setFilter(filter);
			view.setSelector(coll);
			SellProjectCollection projectColl = SellProjectFactory
					.getRemoteInstance().getSellProjectCollection(view);

			StringBuffer idStr = new StringBuffer();
			for (int i = 0; i < projectColl.size(); i++) {
				SellProjectInfo info = projectColl.get(i);
				if (info != null) {
					idStr.append("'");
					idStr.append(info.getId().toString());
					idStr.append("'");
					if (i != projectColl.size() - 1) {
						idStr.append(",");
					}
				}
			}

			FilterInfo newFilter = new FilterInfo();
			if (idStr.length() == 0) {
				newFilter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null,
								CompareType.INNER));

			} else {
				newFilter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", idStr.toString(),
								CompareType.INNER));
			}
			if (BuildingFactory.getRemoteInstance().exists(newFilter)) {
				FDCMsgBox.showInfo("已经建立楼栋信息,不能删除!");
				this.abort();
			}
		} else {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			this.abort();
		}

	}

	private void checkRemoveForMarkinget(String id) throws EASBizException,
			BOSException {
		
		String billId = getRootBillId(id);
		if(billId!=null && !"".equals(billId)){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", billId, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", SysContext.getSysContext().getCurrentSaleUnit().getId(),CompareType.EQUALS));
			if (MarketUnitSellProFactory.getRemoteInstance().exists(filter)) {
				FDCMsgBox.showInfo("该项目已经被营销单元引用,不能删除!");
				this.abort();
			}	
		}
	}

	private String getRootBillId(String id) throws BOSException, EASBizException {
		String idStr = "";
		SellProjectInfo info = SellProjectFactory.getRemoteInstance().getSellProjectInfo("select id,longnumber where id='"+id+"'");
		if(info!=null && info.getLongNumber()!=null){
			String [] temp = info.getLongNumber().split("!");
			String idString = temp[0].toString();
			SellProjectInfo infoStr = SellProjectFactory.getRemoteInstance().getSellProjectInfo("select id where number='"+idString+"'");
			if(infoStr!=null && infoStr.getId()!=null){
				idStr = infoStr.getId().toString();
			}
		}
		
		return idStr;
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

	protected void setNumberTextEnabled() {
		if (getNumberCtrl() != null) {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if (org != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						new SellProjectInfo(), org.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		getNumberCtrl().setText(number);
	}
	
	private void isShareProject(String msg){
		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		if(this.editData.getOrgUnit()!=null){
			String orgUnitId = this.editData.getOrgUnit().getId().toString();
			if(!orgUnitId.equals(orgUnit.getId().toString())){
				FDCMsgBox.showWarning(this,msg);
				this.abort();
			}
		}
	}
}