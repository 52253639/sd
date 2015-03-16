/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetFactory;
import com.kingdee.eas.fdc.sellhouse.CalculateMethodEnum;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemenTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class PriceSetSchemeEditUI extends AbstractPriceSetSchemeEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PriceSetSchemeEditUI.class);

	Map proNameMap = new HashMap();

	Map proAliasMap = new HashMap();

	/**
	 * output class constructor
	 */
	public PriceSetSchemeEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		EntityObjectInfo entity = MetaDataLoaderFactory.getRemoteMetaDataLoader()
					.getEntity(new RoomInfo().getBOSType());
		PropertyCollection pros = entity.getProperties();
		this.tblFactors.checkParsed();
		this.tblFactors.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblFactors.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		Map map = new HashMap();
		map.put("floor", new Object());    				//楼层
		map.put("floorHeight", new Object());		 	//层高
		map.put("unit", new Object());					//单元
		map.put("direction", new Object());				//朝向
		map.put("newSight", new Object());			    //景观
		map.put("roomModel", new Object());				//户型
		map.put("sellOrder", new Object());				//盘次		
		map.put("newDecorastd", new Object());		//装修标准
		map.put("newPossStd", new Object());	//交房标准
//		map.put("decoraStd", new Object());		        //装修标准
//		map.put("posseStd", new Object());	            //交房标准
		map.put("houseProperty", new Object());			//房产性质
		map.put("buildingProperty", new Object());		//建筑性质		
		map.put("newNoise", new Object());		        //噪音
		map.put("newEyeSignht", new Object());		    //视野
		map.put("newProduceRemark", new Object());		//产品描述		
		
		KDComboBox facBox = new KDComboBox();
		for (int i = 0; i < pros.size(); i++) {
			PropertyInfo pro = pros.get(i);
			if (map.containsKey(pro.getName())) {
				facBox.addItem(pro.getAlias());
				this.proNameMap.put(pro.getName(), pro);
				this.proAliasMap.put(pro.getAlias(), pro);
			}
		}
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(facBox);
		this.tblFactors.getColumn("factor").setEditor(editor);
		KDComboBox calBox = new KDComboBox();
		for (int i = 0; i < CalculateMethodEnum.getEnumList().size(); i++) {
			calBox.addItem(CalculateMethodEnum.getEnumList().get(i));
		}
		editor = new KDTDefaultCellEditor(calBox);
		this.tblFactors.getColumn("calMethod").setEditor(editor);
		this.actionRemove.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.chkIsImortDeal.setVisible(false);
		super.onLoad();
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);

		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		} else {
			if (this.getOprtState().equals("VIEW")) {
				this.btnEdit.setEnabled(true);
				this.btnAddNew.setEnabled(true);
			}
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		this.btnAddLine.setEnabled(true);
		this.btnDeleteLine.setEnabled(true);
		this.tblFactors.getStyleAttributes().setLocked(false);
		this.chkIsCalByRoomArea.setEnabled(true);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String id = this.editData.getId().toString();
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("priceScheme.id", id));
			if (BuildingPriceSetFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经参与定价,不能修改!");
				return;
			}
		}
		super.actionEdit_actionPerformed(e);
		this.btnAddLine.setEnabled(true);
		this.btnDeleteLine.setEnabled(true);
		this.tblFactors.getStyleAttributes().setLocked(false);
		this.chkIsCalByRoomArea.setEnabled(true);
	}

	public void loadFields() {
		super.loadFields();
		SHEHelper.setNumberEnabled(editData,getOprtState(),txtNumber);
		PriceSetSchemeInfo scheme = (PriceSetSchemeInfo) this.editData;
		this.tblFactors.removeRows();
		PriceSetSchemeEntryCollection entrys = scheme.getEntrys();
		for (int i = 0; i < entrys.size(); i++) {
			PriceSetSchemeEntryInfo entry = entrys.get(i);
			IRow row = this.tblFactors.addRow();
			row.setUserObject(entry);
			PropertyInfo pro = (PropertyInfo) this.proNameMap.get(entry
					.getFactorField());
			if (pro != null) {
				row.getCell("factor").setValue(pro.getAlias());
			}
			row.getCell("calMethod").setValue(entry.getCalculateMethod());
		}
		if (this.getOprtState().equals("VIEW")) {
			this.btnAddLine.setEnabled(false);
			this.btnDeleteLine.setEnabled(false);
			this.tblFactors.getStyleAttributes().setLocked(true);
			this.chkIsCalByRoomArea.setEnabled(false);
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		PriceSetSchemeInfo scheme = this.editData;
		scheme.getEntrys().clear();
		PriceSetSchemeEntryCollection entrys = scheme.getEntrys();
		for (int i = 0; i < this.tblFactors.getRowCount(); i++) {
			IRow row = tblFactors.getRow(i);
			PriceSetSchemeEntryInfo entry = (PriceSetSchemeEntryInfo) row
					.getUserObject();
			PropertyInfo pro = (PropertyInfo) this.proAliasMap.get(row.getCell(
					"factor").getValue());
			if (pro != null) {
				entry.setFactorField(pro.getName());
				entry.setFactorName(pro.getAlias());
			}
			if (row.getCell("calMethod").getValue() != null) {
				entry.setCalculateMethod((CalculateMethodEnum) row.getCell(
						"calMethod").getValue());
			}
			entrys.add(entry);
		}

	}

	protected IObjectValue createNewData() {
		PriceSetSchemeInfo scheme = new PriceSetSchemeInfo();
		scheme.setIsEnabled(true);
		scheme.setType(PriceSetSchemenTypeEnum.Ding);
		scheme.setSchemeDate(new Date());
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		scheme.setProject(sellProject);
		scheme.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return scheme;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PriceSetSchemeFactory.getRemoteInstance();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("entrys.*");
		sels.add("project.id");
		sels.add("project.name");
		sels.add("project.number");
		sels.add("project.level");
		sels.add("project.parent.id");
		sels.add("project.parent.name");
		sels.add("project.parent.number");
		sels.add("project.parent.level");
		return sels;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("编码不能为空!");
			this.abort();
		}
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(
				this.txtName, this.editData, "name");
		if (flag) {
			MsgBox.showInfo("名称不能为空!");
			this.abort();
		}
		Map factorsMap = new HashMap();
		for (int i = 0; i < this.tblFactors.getRowCount(); i++) {
			IRow row = this.tblFactors.getRow(i);
			if (row.getCell("factor").getValue() == null) {
				MsgBox.showInfo("第" + (i + 1) + "行因素没有录入!");
				this.abort();
			}
			if (row.getCell("calMethod").getValue() == null) {
				MsgBox.showInfo("第" + (i + 1) + "行计算方法没有录入!");
				this.abort();
			}
			String factor = (String) row.getCell("factor").getValue();
			if (factorsMap.containsKey(factor)) {
				MsgBox.showInfo("第" + (i + 1) + "行因素与前面行重复!");
				this.abort();
			} else {
				factorsMap.put(factor, factor);
			}
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkDumpName();
		super.actionSubmit_actionPerformed(e);
	}

	protected void btnAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddLine_actionPerformed(e);
		IRow row = this.tblFactors.addRow();
		PriceSetSchemeEntryInfo entry = new PriceSetSchemeEntryInfo();
		row.setUserObject(entry);
	}

	protected void btnDeleteLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnDeleteLine_actionPerformed(e);
		int activeRowIndex = this.tblFactors.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex < 0) {
			return;
		}
		this.tblFactors.removeRow(activeRowIndex);
	}

	protected FDCDataBaseInfo getEditData() {
		FDCDataBaseInfo info = (FDCDataBaseInfo) this.editData;
		return info;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return this.txtName;
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
		PriceSetSchemeInfo pur = new PriceSetSchemeInfo();
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
						new PriceSetSchemeInfo(), org.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
				getNumberCtrl().setRequired(isAllowModify);
			}
		}
	}
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		getNumberCtrl().setText(number);
	}
	
	private void checkDumpName() throws Exception {
		SellProjectInfo info = (SellProjectInfo) this.editData.getProject();
		if (info == null) {
			return;
		}
		
		String number = this.txtNumber.getText();
	
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (info != null && info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", info.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null,
								CompareType.EQUALS));
			}
//			filter.getFilterItems().add(
//					new FilterItemInfo("level", String
//							.valueOf(info.getLevel())));
			if (PriceSetSchemeFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下编码不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", this.editData.getId().toString(),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("number", number));
		
				if (info != null && info.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", info.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", null,
									CompareType.EQUALS));
				}
//				filter.getFilterItems().add(
//						new FilterItemInfo("level", String
//								.valueOf(info.getLevel())));
				if (PriceSetSchemeFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下编码不能重复！");
					SysUtil.abort();
				}
			}
	
		}
		String name = this.txtName.getSelectedItem().toString();
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));
			if (info != null && info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", info.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null,
								CompareType.EQUALS));
			}
//			filter.getFilterItems().add(
//					new FilterItemInfo("level", String
//							.valueOf(info.getLevel())));
			if (PriceSetSchemeFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下名称不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", this.editData.getId().toString(),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("name", name));
				if (info != null && info.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", info.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", null,
									CompareType.EQUALS));
				}
//				filter.getFilterItems().add(
//						new FilterItemInfo("level", String
//								.valueOf(info.getLevel())));
				if (PriceSetSchemeFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下名称不能重复！");
					SysUtil.abort();
				}
			}

		}
	}
	private void checkDumpNameForLevel() throws Exception {
		SellProjectInfo info = (SellProjectInfo) this.editData.getProject();
		if (info == null) {
			return;
		}
		
		String number = this.txtNumber.getText();
	
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (info != null && info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", info.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null,
								CompareType.EQUALS));
			}
			if (PriceSetSchemeFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下编码不能重复！");
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
		
				if (info != null && info.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", info.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", null,
									CompareType.EQUALS));
				}
			
				if (PriceSetSchemeFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下编码不能重复！");
					SysUtil.abort();
				}
			}
	
		}
		String name = this.txtName.getSelectedItem().toString();
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));
			if (info != null && info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", info.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null,
								CompareType.EQUALS));
			}
			if (PriceSetSchemeFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下名称不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("name", name));
				if (info != null && info.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", info.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", null,
									CompareType.EQUALS));
				}
				
				if (PriceSetSchemeFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下名称不能重复！");
					SysUtil.abort();
				}
			}

		}
	}
}