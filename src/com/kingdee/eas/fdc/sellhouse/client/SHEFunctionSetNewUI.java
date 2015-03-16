/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BizDateToEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum;
import com.kingdee.eas.fdc.sellhouse.SHEFunProductTypeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SHEFunProductTypeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEFunctionChooseRoomSetCollection;
import com.kingdee.eas.fdc.sellhouse.SHEFunctionChooseRoomSetInfo;
import com.kingdee.eas.fdc.sellhouse.SHEFunctionSetCollection;
import com.kingdee.eas.fdc.sellhouse.SHEFunctionSetEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SHEFunctionSetEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEFunctionSetFactory;
import com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SHEProjectSetCollection;
import com.kingdee.eas.fdc.sellhouse.SHEProjectSetInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * SHEFunctionSetNewUI
 * 
 * @author liang_ren969
 * @date 2011-9-22
 */
public class SHEFunctionSetNewUI extends AbstractSHEFunctionSetNewUI implements
		SHEParamConstant {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8116376204355696762L;
	private static final Logger logger = CoreUIObject
			.getLogger(SHEFunctionSetNewUI.class);

	private Map projectMap = new HashMap();
	private Map chooseRoomMap = new HashMap();
	private Map baseProjectMap = new HashMap();
	private Map isEnableMap = new HashMap();

	/**
	 * output class constructor
	 */
	public SHEFunctionSetNewUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		saveBaseProjectSet();
		saveRootProjectSet();
		saveChooseRoomSet();
	}

	/**
	 * 保存选房设置
	 * 
	 * @param editData
	 */
	private void saveChooseRoomSet() {

		this.editData.getChooseRoomSet().clear();

		for (int i = 0; i < this.tblChooseRoom.getRowCount(); i++) {
			IRow row = this.tblChooseRoom.getRow(i);
			if (row == null) {
				continue;
			}

			SHEFunctionChooseRoomSetInfo roomSetInfo = new SHEFunctionChooseRoomSetInfo();

			if (row.getCell(T3_PROJECT).getValue() != null) {
				roomSetInfo.setSellProject((SellProjectInfo) row.getCell(
						T3_PROJECT).getValue());
			}

			if (row.getCell(T3_BUILDING).getValue() != null) {
				roomSetInfo.setBuilding((BuildingInfo) row.getCell(T3_BUILDING)
						.getValue());
			}

			if (row.getCell(T3_BEGIN_DATE).getValue() != null) {
				roomSetInfo.setStartDate((Date) row.getCell(T3_BEGIN_DATE)
						.getValue());
			}
			if (row.getCell(T3_END_DATE).getValue() != null) {
				roomSetInfo.setEndDate((Date) row.getCell(T3_END_DATE)
						.getValue());
			}
			if (row.getCell(T3_IS_ENABLE).getValue() != null) {
				roomSetInfo.setIsEnabled(((Boolean) row.getCell(T3_IS_ENABLE)
						.getValue()).booleanValue());
			} else {
				roomSetInfo.setIsEnabled(false);
			}
			if (row.getCell(T3_EXPIRY_DATE).getValue() != null) {
				roomSetInfo.setDefaultLimitDate(((Integer) row.getCell(
						T3_EXPIRY_DATE).getValue()).intValue());
			}

			this.editData.getChooseRoomSet().add(roomSetInfo);
		}
	}

	/**
	 * 保存根项目设置
	 * 
	 * @param editData
	 */
	private void saveRootProjectSet() {

		this.editData.getProjectSet().clear();

		for (int i = 0; i < this.tblProSetWithoutProductType.getRowCount(); i++) {
			IRow row = this.tblProSetWithoutProductType.getRow(i);

			if (row == null) {
				continue;
			}

			SHEProjectSetInfo projectSetInfo = new SHEProjectSetInfo();

			if (row.getCell(T2_PROJECT).getValue() != null) {
				projectSetInfo.setSellProject((SellProjectInfo) row.getCell(
						T2_PROJECT).getValue());
			}
			if (row.getCell(T2_IS_DEAL_AMOUNT_EDIT).getValue() != null) {
				projectSetInfo.setIsUpdateForAmount(((Boolean) row.getCell(
						T2_IS_DEAL_AMOUNT_EDIT).getValue()).booleanValue());
			} else {
				projectSetInfo.setIsUpdateForAmount(false);
			}
			if (row.getCell(T2_IS_ENABLE_AGIO).getValue() != null) {
				projectSetInfo.setIsUseAgioScheme(((Boolean) row.getCell(
						T2_IS_ENABLE_AGIO).getValue()).booleanValue());
			} else {
				projectSetInfo.setIsUseAgioScheme(false);
			}
			if (row.getCell(T2_IS_ENABLE_ADJUST_AGIO).getValue() != null) {
				projectSetInfo.setIsUpdateForAgioDetail(((Boolean) row.getCell(
						T2_IS_ENABLE_ADJUST_AGIO).getValue()).booleanValue());
			} else {
				projectSetInfo.setIsUpdateForAgioDetail(false);
			}
			if (row.getCell(T2_IS_FORCE_LIMIT_PRICE).getValue() != null) {
				projectSetInfo.setIsBasePriceControl(((Boolean) row.getCell(
						T2_IS_FORCE_LIMIT_PRICE).getValue()).booleanValue());
			} else {
				projectSetInfo.setIsBasePriceControl(false);
			}
			if (row.getCell(T2_IS_MUST_BY_BANK).getValue() != null) {
				projectSetInfo.setIsBank(((Boolean) row.getCell(
						T2_IS_MUST_BY_BANK).getValue()).booleanValue());
			} else {
				projectSetInfo.setIsBank(false);
			}

			if (row.getCell(T2_IS_DEAL_AMT_BY_STAND_AMT).getValue() != null) {
				projectSetInfo
						.setIsStandardTotalAmount(((Boolean) row.getCell(
								T2_IS_DEAL_AMT_BY_STAND_AMT).getValue())
								.booleanValue());
			} else {
				projectSetInfo.setIsStandardTotalAmount(false);
			}
			if (row.getCell(T2_IS_RE_PRICE_OF_QUIT_ROOM).getValue() != null) {
				projectSetInfo.setIsPriceAuditedForQuitRoom(((Boolean) row
						.getCell(T2_IS_RE_PRICE_OF_QUIT_ROOM).getValue())
						.booleanValue());
			} else {
				projectSetInfo.setIsPriceAuditedForQuitRoom(false);
			}
			if (row.getCell(T2_IS_RE_PRICE_OF_CHANGE_ROOM).getValue() != null) {
				projectSetInfo.setIsPriceAuditedForChanceRoom(((Boolean) row
						.getCell(T2_IS_RE_PRICE_OF_CHANGE_ROOM).getValue())
						.booleanValue());
			} else {
				projectSetInfo.setIsPriceAuditedForChanceRoom(false);
			}
			if (row.getCell(T2_IS_AREA_CHANGE_NEED_AUDIT).getValue() != null) {
				projectSetInfo.setIsPriceAuditedForAreaChance(((Boolean) row
						.getCell(T2_IS_AREA_CHANGE_NEED_AUDIT).getValue())
						.booleanValue());
			} else {
				projectSetInfo.setIsPriceAuditedForAreaChance(false);
			}
			if (row.getCell(T2_IS_BIZ_DATE_EDITABLE).getValue() != null) {
				projectSetInfo.setIsUpdateForbelongDate(((Boolean) row.getCell(
						T2_IS_BIZ_DATE_EDITABLE).getValue()).booleanValue());
			} else {
				projectSetInfo.setIsUpdateForbelongDate(false);
			}
			if (row.getCell(T2_CHANGE_ROOM_DEF_BIZ_DATE).getValue() != null) {
				projectSetInfo
						.setChangeRoomDefaultBelongDate((BizDateToEnum) row
								.getCell(T2_CHANGE_ROOM_DEF_BIZ_DATE)
								.getValue());
			}
			if (row.getCell(T2_PRICE_ADJUST_DEF_BIZ_DATE).getValue() != null) {
				projectSetInfo
						.setPriceChangeDefaultBelongDate((BizDateToEnum) row
								.getCell(T2_PRICE_ADJUST_DEF_BIZ_DATE)
								.getValue());
			}
			if (row.getCell(T2_CHANGE_CUS_DEF_BIZ_DATE).getValue() != null) {
				projectSetInfo
						.setChangeNameDefaultBelongDate((BizDateToEnum) row
								.getCell(T2_CHANGE_CUS_DEF_BIZ_DATE).getValue());
			}
			if (row.getCell(T2_IS_NO_SELL_CAN_SIN).getValue() != null) {
				projectSetInfo.setIsSincerForNotSell(((Boolean) row.getCell(
						T2_IS_NO_SELL_CAN_SIN).getValue()).booleanValue());
			} else {
				projectSetInfo.setIsSincerForNotSell(false);
			}
			if (row.getCell(T2_IS_ENABLE_TRADE).getValue() != null) {
				projectSetInfo.setIsUseTrackLimeDate(((Boolean) row.getCell(
						T2_IS_ENABLE_TRADE).getValue()).booleanValue());
			} else {
				projectSetInfo.setIsUseTrackLimeDate(false);
			}
			if (row.getCell(T2_CUS_TRADE_DAYS).getValue() != null) {
				projectSetInfo.setCustomerTrackLimitDate(((Integer) row
						.getCell(T2_CUS_TRADE_DAYS).getValue()).intValue());
			}
			if (row.getCell(T2_CHANCE_DAYS).getValue() != null) {
				projectSetInfo.setCommerTrackLimitDate(((Integer) row.getCell(
						T2_CHANCE_DAYS).getValue()).intValue());
			}
			if (row.getCell(T2_COMMERCECHANCE_DAYS).getValue() != null) {
				projectSetInfo.setCommerLimitDate(((Integer) row.getCell(
						T2_COMMERCECHANCE_DAYS).getValue()).intValue());
			}
			
			if(row.getCell(T2_NAME_REPEAT_RULE).getValue() != null){
				projectSetInfo.setNameRepeatRule((CusRepeatRuleEnum) row.getCell(T2_NAME_REPEAT_RULE).getValue());
			}
			if(row.getCell(T2_PHONE_REPEAT_RULE).getValue() != null){
				projectSetInfo.setPhoneRepeatRule((CusRepeatRuleEnum) row.getCell(T2_PHONE_REPEAT_RULE).getValue());
			}
			if(row.getCell(T2_CER_REPEAT_RULE).getValue() != null){
				projectSetInfo.setCerRepeatRule((CusRepeatRuleEnum) row.getCell(T2_CER_REPEAT_RULE).getValue());
			}
			
			
			this.editData.getProjectSet().add(projectSetInfo);
		}

	}

	/**
	 * 保存项目基础设置
	 * 
	 * @param editData
	 */
	private void saveBaseProjectSet() {

		this.editData.getEntrys().clear();

		for (int i = 0; i < this.tblProSetWithProductType.getRowCount(); i++) {
			IRow row = this.tblProSetWithProductType.getRow(i);

			if (row == null) {
				continue;
			}
			SHEFunctionSetEntryInfo entryInfo = new SHEFunctionSetEntryInfo();

			if (row.getCell(T1_COL_PROEJCT_NAME).getValue() != null) {
				entryInfo.setSellProject((SellProjectInfo) row.getCell(
						T1_COL_PROEJCT_NAME).getValue());
			}

			if (row.getCell(T1_COL_PRODUCT_TYPE).getValue() != null) {
				entryInfo.getProductTypeEntry().clear();
				Object[] proTypes = (Object[]) row.getCell(T1_COL_PRODUCT_TYPE)
						.getValue();
				for (int j = 0; j < proTypes.length; j++) {
					ProductTypeInfo productType = null;
					SHEFunProductTypeEntryInfo typeEntryInfo = null;
					if (proTypes[j] instanceof SHEFunProductTypeEntryInfo) {
						typeEntryInfo = (SHEFunProductTypeEntryInfo) proTypes[j];
						productType = typeEntryInfo.getProductType();
					} else if (proTypes[j] instanceof ProductTypeInfo) {
						productType = (ProductTypeInfo) proTypes[j];
					}
					SHEFunProductTypeEntryInfo typeInfo = new SHEFunProductTypeEntryInfo();
					typeInfo.setProductType(productType);
					entryInfo.getProductTypeEntry().add(typeInfo);
				}

			}

			if (row.getCell(T1_KEEP_DOWN_LIMIT_TIME).getValue() != null) {
				entryInfo.setAppointInvalidLimit(((Integer) row.getCell(
						T1_KEEP_DOWN_LIMIT_TIME).getValue()).intValue());
			}
			if (row.getCell(T1_PRE_PURCHASE_LIMIT_TIME).getValue() != null) {
				entryInfo.setBookingInvalidLimit(((Integer) row.getCell(
						T1_PRE_PURCHASE_LIMIT_TIME).getValue()).intValue());
			}
			if (row.getCell(T1_PURCHASE_LIMIT_TIME).getValue() != null) {
				entryInfo.setPurchaseInvalidLimit(((Integer) row.getCell(
						T1_PURCHASE_LIMIT_TIME).getValue()).intValue());
			}
			if (row.getCell(T1_TO_SIGN_LIMIT_TIME).getValue() != null) {
				entryInfo.setPurchaseSignLimit(((Integer) row.getCell(
						T1_TO_SIGN_LIMIT_TIME).getValue()).intValue());
			}
			if (row.getCell(T1_SIGN_LIMIT_TIME).getValue() != null) {
				entryInfo.setSignInvalidLimit(((Integer) row.getCell(
						T1_SIGN_LIMIT_TIME).getValue()).intValue());
			}

			if (row.getCell(T1_SIN_PURCHASE_STANDARD).getValue() != null) {
				entryInfo.setSincerAmount((BigDecimal) row.getCell(
						T1_SIN_PURCHASE_STANDARD).getValue());
			}
			if (row.getCell(T1_PRE_PURCHASE_STANDARD).getValue() != null) {
				entryInfo.setPurBookingAmount((BigDecimal) row.getCell(
						T1_PRE_PURCHASE_STANDARD).getValue());
			}

			if (row.getCell(T1_PRE_STANDARD).getValue() != null) {
				entryInfo.setBookingAmount((BigDecimal) row.getCell(
						T1_PRE_STANDARD).getValue());
			}

			this.editData.getEntrys().add(entryInfo);

		}
	}

	/**
	 * output tblProSetWithProductType_editStopped method
	 */
	protected void tblProSetWithProductType_editStopped(
			com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e)
			throws Exception {
		
		if (e.getColIndex() == tblProSetWithProductType.getColumn(T1_COL_PROEJCT_NAME).getColumnIndex()) {
			int rowIndex = e.getRowIndex();
			SellProjectInfo pro = (SellProjectInfo) tblProSetWithProductType.getRow(e.getRowIndex()).getCell(T1_COL_PROEJCT_NAME).getValue();
			if(pro != null){
				addRowForProject(pro);
				addRowForChooseRoom(pro);
			}
		}
		
		if (tblProSetWithProductType.getRow(e.getRowIndex()).getCell(
				T1_COL_PRODUCT_TYPE).getValue() == null
				|| tblProSetWithProductType.getRow(e.getRowIndex()).getCell(
						T1_COL_PROEJCT_NAME).getValue() == null)
			return;

		String projectName = tblProSetWithProductType.getRow(e.getRowIndex())
				.getCell(T1_COL_PROEJCT_NAME).getValue().toString();
		Object[] obj = (Object[]) tblProSetWithProductType.getRow(
				e.getRowIndex()).getCell(T1_COL_PRODUCT_TYPE).getValue();
		boolean isExist = false;
		for (int i = 0; i < tblProSetWithProductType.getRowCount(); i++) {
			IRow row = tblProSetWithProductType.getRow(i);
			if (row == null) {
				continue;
			}
			if (e.getRowIndex() == row.getRowIndex()) {
				continue;
			}
			isExist = isExistInProductInfo(projectName, obj, isExist, row);
			if (isExist) {
				break;
			}
		}

		if (isExist) {
			FDCMsgBox.showWarning(this, "该项目存在相同产品类型售楼功能设置！");
			tblProSetWithProductType.getRow(e.getRowIndex()).getCell(
					T1_COL_PRODUCT_TYPE).setValue(null);
		}
	}

	private boolean isExistInProductInfo(String projectName, Object[] obj,
			boolean isExist, IRow row) {
		if (row.getCell(T1_COL_PROEJCT_NAME).getValue() != null) {
			if (projectName.equals(row.getCell(T1_COL_PROEJCT_NAME).getValue()
					.toString())) {
				Object[] temp = (Object[]) row.getCell(T1_COL_PRODUCT_TYPE)
						.getValue();
				if (temp == null) {
					isExist = false;
					return isExist;
				}
				for (int j = 0; j < obj.length; j++) {
					if (isExist) {
						break;
					}
					ProductTypeInfo type = (ProductTypeInfo) obj[j];
					for (int j2 = 0; j2 < temp.length; j2++) {
						ProductTypeInfo info = (ProductTypeInfo) temp[j2];
						if (type != null
								&& info != null
								&& type.getId().toString().equals(
										info.getId().toString())) {
							isExist = true;
							break;
						}
					}
				}
			}
		}
		return isExist;
	}

	/**
	 * output btnAddLine_actionPerformed method
	 */
	protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		IRow row = this.tblProSetWithProductType.addRow();
		row.getCell(T1_COL_PROEJCT_NAME).getStyleAttributes().setBackground(
				FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
		setDefaultValue(row);
	}

	protected void btnInsertLine_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		int i = this.tblProSetWithProductType.getSelectManager()
				.getActiveRowIndex();

		IRow row = null;
		if (i == -1) {
			row = this.tblProSetWithProductType.addRow();
		} else {
			row = this.tblProSetWithProductType.addRow(i);
		}

		row.getCell(T1_COL_PROEJCT_NAME).getStyleAttributes().setBackground(
				FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);

		setDefaultValue(row);
	}

	/**
	 * output btnRmLine_actionPerformed method
	 */
	protected void btnRmLine_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		SellProjectCollection coll = null;
		SellProjectInfo rootProject = null;
		SellProjectInfo currentProject = null;
		int i = this.tblProSetWithProductType.getSelectManager()
				.getActiveRowIndex();

		IRow row = this.tblProSetWithProductType.getRow(i);
		if (row != null) {
			if (row.getCell(T1_COL_PROEJCT_NAME).getValue() != null) {
				currentProject = (SellProjectInfo) row.getCell(
						T1_COL_PROEJCT_NAME).getValue();
				
				boolean existInOtherRows = false;
				for(int j=0;j<this.tblProSetWithProductType.getRowCount(); j++){
					if(j == i){
						continue;
					}
					IRow tr = this.tblProSetWithProductType.getRow(j);
					SellProjectInfo ts = (SellProjectInfo) tr.getCell(T1_COL_PROEJCT_NAME).getValue();
					if(ts != null  &&  ts.getId().toString().equals(currentProject.getId().toString())){
						existInOtherRows = true;
						break;
					}
				}
				
				if(!existInOtherRows){
					if (this.baseProjectMap.containsKey(currentProject.getId()
							.toString())) {
						this.baseProjectMap.remove(currentProject.getId()
								.toString());
					}
					this.deleteProjectFromChooseRoomSet(currentProject);
					this.deleteProjectFromProjectSet(currentProject);
				}
				this.tblProSetWithProductType.removeRow(i);
				
				
//				rootProject = this.getRootProject(currentProject);
//				if (rootProject == null) {
//					rootProject = currentProject;
//				}
//				// 得到当前项目的所有子项目
//				coll = getChildProject(rootProject);
			}else{
				this.tblProSetWithProductType.removeRow(i);
			}
		}

//		if (!isExistInProjectMap(coll)) {
//			deleteProjectFromProjectSet(rootProject);
//		}

//		deleteProjectFromChooseRoomSet(currentProject);
	}

	private void deleteProjectFromChooseRoomSet(SellProjectInfo currentProject) {
		if (currentProject == null) {
			return;
		}

		// 要删除的行
		List deleteList = new ArrayList();

		for (int i = 0; i < this.tblChooseRoom.getRowCount(); i++) {
			IRow row = this.tblChooseRoom.getRow(i);
			if (row == null) {
				continue;
			}
			SellProjectInfo info = (SellProjectInfo) row.getCell(T3_PROJECT)
					.getValue();
			if (info != null
					&& info.getId().toString().equals(
							currentProject.getId().toString())) {
				deleteList.add(info.getId().toString());
			}

		}

		if (deleteList.size() > 0) {
			String projectId = "";
			for (int i = 0; i < deleteList.size(); i++) {
				projectId = "";
				projectId = deleteList.get(i).toString();
				for (int j = 0; j < this.tblChooseRoom.getRowCount(); j++) {
					IRow row = this.tblChooseRoom.getRow(j);
					if (row == null) {
						continue;
					}
					SellProjectInfo info = (SellProjectInfo) row.getCell(
							T3_PROJECT).getValue();
					if (info != null
							&& info.getId().toString().equals(projectId)) {
						this.tblChooseRoom.removeRow(j);
						this.chooseRoomMap.remove(projectId);
					}

				}
			}
		}
	}

	private void deleteProjectFromProjectSet(SellProjectInfo rootProject) {
		if (rootProject == null) {
			return;
		}

		// 要删除的行
		List deleteList = new ArrayList();

		for (int i = 0; i < this.tblProSetWithoutProductType.getRowCount(); i++) {
			IRow row = this.tblProSetWithoutProductType.getRow(i);
			if (row == null) {
				continue;
			}
			SellProjectInfo info = (SellProjectInfo) row.getCell(T2_PROJECT)
					.getValue();
			if (info != null
					&& info.getId().toString().equals(
							rootProject.getId().toString())) {
				deleteList.add(info.getId().toString());
			}

		}

		if (deleteList.size() > 0) {
			String projectId = "";
			for (int i = 0; i < deleteList.size(); i++) {
				projectId = "";
				projectId = deleteList.get(i).toString();
				for (int j = 0; j < this.tblProSetWithoutProductType
						.getRowCount(); j++) {
					IRow row = this.tblProSetWithoutProductType.getRow(j);
					if (row == null) {
						continue;
					}
					SellProjectInfo info = (SellProjectInfo) row.getCell(
							T2_PROJECT).getValue();
					if (info != null
							&& info.getId().toString().equals(projectId)) {
						this.tblProSetWithoutProductType.removeRow(j);
						this.projectMap.remove(projectId);
					}

				}
			}
		}

	}

	private boolean isExistInProjectMap(SellProjectCollection coll) {

		boolean result = false;
		if (coll == null || coll.size() == 0) {
			return result;
		}
		for (int i = 0; i < coll.size(); i++) {
			SellProjectInfo info = coll.get(i);
			if (info != null) {
				if (this.baseProjectMap.containsKey(info.getId().toString())) {
					result = true;
				}
			}
		}
		return result;
	}

	private SellProjectCollection getChildProject(SellProjectInfo rootProject)
			throws BOSException {
		SellProjectCollection coll = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", rootProject.getId().toString(),
						CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", rootProject.getLongNumber()
						+ "!%", CompareType.LIKE));
		filter.setMaskString("#0 or #1");
		view.setFilter(filter);
		SelectorItemCollection select = new SelectorItemCollection();
		select.add("id");
		select.add("name");
		select.add("number");
		select.add("longNumber");
		view.setSelector(select);
		coll = SellProjectFactory.getRemoteInstance().getSellProjectCollection(
				view);

		return coll;
	}

	protected IObjectValue createNewData() {

		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SHEFunctionSetFactory.getRemoteInstance();
	}

	/**
	 * 给末级项目增加一行数据,并给初始值
	 * 
	 * @param row
	 */
	private void setDefaultValue(IRow row) {
		for (int i = 0; i < t1.length; i++) {
			ICell cell = row.getCell((String) t1[i][0]);
			if (cell != null) {
				cell.setValue(t1[i][1]);
			}
		}
	}

	public void onLoad() throws Exception {
		FDCSysContext.getInstance().checkIsSHEOrg(this);
		super.onLoad();
		initTables();
		this.projectMap.clear();
		this.chooseRoomMap.clear();
		this.baseProjectMap.clear();
		this.isEnableMap.clear();

		this.tblProSetWithProductType.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);

		if (!isExitsRecoard()) {
			addRecoard();
		}
		
		loadFields();

	}

	/**
	 * 在售楼设置表中增加一条记录
	 * 
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void addRecoard() throws EASBizException, BOSException {
		SHEFunctionSetInfo model = new SHEFunctionSetInfo();
		model.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		String time = getStrDate();
		model.setName("系统自动创建" + time);
		model.setNumber("系统自动创建01" + time);
		SHEFunctionSetFactory.getRemoteInstance().addnew(model);

	}

	/**
	 * 获得一个日期的字符串
	 * 
	 * @return
	 */
	private static String getStrDate() {
		String temp = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd-hh:mm:ssSSS");
			Date date = new Date();
			temp = sdf.format(date);
		} catch (Exception e) {
			logger.error(e.getMessage() + "得到当前时间失败");
		}
		return temp;
	}

	/**
	 * 判断售楼设置表中是否有数据
	 * 
	 * @return
	 * @throws BOSException
	 */
	private boolean isExitsRecoard() throws BOSException {
		boolean result = false;
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selectColl = new SelectorItemCollection();
		selectColl.add("id");
		selectColl.add("name");
		selectColl.add("number");
		view.setSelector(selectColl);
		SHEFunctionSetCollection coll = SHEFunctionSetFactory
				.getRemoteInstance().getSHEFunctionSetCollection(view);

		if (coll != null && coll.size() > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * 增加一行跟级项目设置
	 * 
	 * @param pro
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void addRowForProject(SellProjectInfo info) throws EASBizException,
			BOSException {

			SellProjectInfo rootInfo = getRootProject(info);
			if (rootInfo == null) {
				rootInfo = info;
			}

			if (this.projectMap.containsKey(rootInfo.getId().toString())) {
				return;
			} else {
				this.projectMap.put(rootInfo.getId().toString(), rootInfo);
			}

			IRow rootRow = this.tblProSetWithoutProductType.addRow();
			if (rootInfo != null) {
				rootRow.getCell(T2_PROJECT).setValue(rootInfo);
			}

			for (int i = 0; i < t2.length; i++) {
				ICell cell = rootRow.getCell((String) t2[i][0]);
				if (cell != null) {
					cell.setValue(t2[i][1]);
				}
			}
	}

	/**
	 * 获得该项目的终极根项目
	 * 
	 * @param currProject
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private SellProjectInfo getRootProject(SellProjectInfo currProject)
			throws EASBizException, BOSException {
		if (currProject == null) {
			return null;
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("parent.id");
		sic.add("parent.name");
		sic.add("parent.number");
		sic.add("parent.longNumber");
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("longNumber");
		currProject = SellProjectFactory.getRemoteInstance()
				.getSellProjectInfo(new ObjectUuidPK(currProject.getId()), sic);

		if (currProject.getParent() != null) {
			return getRootProject(currProject.getParent());
		}
		return currProject;
	}

	/**
	 * 增加一行选房设置
	 * 
	 * @param pro
	 */
	private void addRowForChooseRoom(SellProjectInfo info) {
			if (this.chooseRoomMap.containsKey(info.getId().toString())) {
				return;
			} else {
				this.chooseRoomMap.put(info.getId().toString(), info.getId());
			}

			BuildingCollection permitBuildings = new BuildingCollection();

			permitBuildings = getBuildingByProject(info);

			for (int i = 0; i < permitBuildings.size(); i++) {
				BuildingInfo buildinfo = permitBuildings.get(i);
				IRow ChooseRow = this.tblChooseRoom.addRow();
				ChooseRow.getCell(T2_PROJECT).setValue(info);
				if (buildinfo != null) {
					ChooseRow.getCell(T3_BUILDING).setValue(buildinfo);
				}
				for (int j = 0; j < t3.length; j++) {
					ICell cell = ChooseRow.getCell((String) t3[j][0]);
					if (cell != null) {
						cell.setValue(t3[j][1]);
					}
				}
			}

			this.tblChooseRoom.getMergeManager().mergeBlock(0, 0,
					this.tblChooseRoom.getRowCount() - 1, 1,
					KDTMergeManager.FREE_MERGE);

	}

	/**
	 * 根据项目来查找出该项目下的所有的楼栋
	 */
	private BuildingCollection getBuildingByProject(SellProjectInfo info) {
		BuildingCollection permitBuildings = null;
		if (info != null) {
			try {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", info.getId()
								.toString(), CompareType.EQUALS));
				view.setFilter(filter);

				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("*");
				sels.add("sellProject.*");
				view.setSelector(sels);

				SorterItemCollection sors = new SorterItemCollection();
				sors.add(new SorterItemInfo("number"));
				view.setSorter(sors);

				permitBuildings = BuildingFactory.getRemoteInstance()
						.getBuildingCollection(view);
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "获得楼栋失败!");
			}

		}

		return permitBuildings;
	}

	/**
	 * 初始化table
	 */
	private void initTables() {
		this.tblProSetWithProductType.checkParsed();
		this.tblProSetWithProductType.getColumn(T1_KEEP_DOWN_LIMIT_TIME)
				.setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithProductType.getColumn(T1_PRE_PURCHASE_LIMIT_TIME)
				.setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithProductType.getColumn(T1_PURCHASE_LIMIT_TIME)
				.setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithProductType.getColumn(T1_TO_SIGN_LIMIT_TIME)
				.setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithProductType.getColumn(T1_SIGN_LIMIT_TIME).setEditor(
				CRMClientHelper.getIntegerCellEditor());

		this.tblProSetWithProductType.getColumn(T1_PRE_PURCHASE_STANDARD)
				.setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblProSetWithProductType.getColumn(T1_SIN_PURCHASE_STANDARD)
				.setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblProSetWithProductType.getColumn(T1_PRE_STANDARD).setEditor(
				CRMClientHelper.getKDTDefaultCellEditor());

		this.tblProSetWithoutProductType.checkParsed();
		this.tblProSetWithoutProductType.getColumn(T2_PROJECT).setEditor(
				CommerceHelper.getKDBizPromptBoxEditor(
						"com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery",
						null));
		this.tblProSetWithoutProductType.getColumn(T2_CUS_TRADE_DAYS)
				.setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithoutProductType.getColumn(T2_CHANCE_DAYS).setEditor(
				CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithoutProductType.getColumn(T2_COMMERCECHANCE_DAYS)
				.setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithoutProductType.getColumn(T2_PROJECT)
				.getStyleAttributes().setLocked(true);
		this.tblProSetWithoutProductType.getColumn(T2_CHANGE_CUS_DEF_BIZ_DATE)
				.setEditor(
						CommerceHelper.getKDComboBoxEditor(BizDateToEnum
								.getEnumList()));
		this.tblProSetWithoutProductType.getColumn(T2_CHANGE_ROOM_DEF_BIZ_DATE)
				.setEditor(
						CommerceHelper.getKDComboBoxEditor(BizDateToEnum
								.getEnumList()));
		this.tblProSetWithoutProductType
				.getColumn(T2_PRICE_ADJUST_DEF_BIZ_DATE).setEditor(
						CommerceHelper.getKDComboBoxEditor(BizDateToEnum
								.getEnumList()));

		this.tblProSetWithoutProductType.getColumn(T2_NAME_REPEAT_RULE).setEditor(CommerceHelper.getKDComboBoxEditor(CusRepeatRuleEnum.getEnumList()));
		this.tblProSetWithoutProductType.getColumn(T2_PHONE_REPEAT_RULE).setEditor(CommerceHelper.getKDComboBoxEditor(CusRepeatRuleEnum.getEnumList()));
		this.tblProSetWithoutProductType.getColumn(T2_CER_REPEAT_RULE).setEditor(CommerceHelper.getKDComboBoxEditor(CusRepeatRuleEnum.getEnumList()));
		
		this.tblChooseRoom.checkParsed();
		this.tblChooseRoom.getColumn(T3_PROJECT).setEditor(
				CommerceHelper.getKDBizPromptBoxEditor(
						"com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery",
						null));
		this.tblChooseRoom
				.getColumn(T3_BUILDING)
				.setEditor(
						CommerceHelper
								.getKDBizPromptBoxEditor(
										"com.kingdee.eas.fdc.sellhouse.app.BuildingQuery",
										null));
		this.tblChooseRoom.getColumn(T3_BEGIN_DATE).setEditor(
				CommerceHelper.getKDDatePickerEditor());
		this.tblChooseRoom.getColumn(T3_END_DATE).setEditor(
				CommerceHelper.getKDDatePickerEditor());
		this.tblChooseRoom.getColumn(T3_EXPIRY_DATE).setEditor(
				CRMClientHelper.getIntegerCellEditor());

		this.tblChooseRoom.getColumn(T3_PROJECT).getStyleAttributes()
				.setLocked(true);
		this.tblChooseRoom.getColumn(T3_BUILDING).getStyleAttributes()
				.setLocked(true);

		FullOrgUnitInfo orgUnit = SysContext.getSysContext()
				.getCurrentOrgUnit().castToFullOrgUnitInfo();
		EntityViewInfo viewcollection = new EntityViewInfo();
		FilterInfo filtercollection = new FilterInfo();
		filtercollection.getFilterItems().add(
				new FilterItemInfo("isLeaf", new Boolean(true),
						CompareType.EQUALS));

		if (orgUnit != null
				&& !orgUnit.getId().toString().equals(
						OrgConstants.DEF_CU_ID.toString())) {
			filtercollection.getFilterItems().add(
					new FilterItemInfo("orgUnit.id",
							orgUnit.getId().toString(), CompareType.EQUALS));

		}
		viewcollection.setFilter(filtercollection);
		this.tblProSetWithProductType.getColumn(T1_COL_PROEJCT_NAME).setEditor(
				CommerceHelper.getKDBizPromptBoxEditor(
						"com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery",
						viewcollection));

		this.tblProSetWithProductType.getColumn(T1_COL_PROEJCT_NAME)
				.getStyleAttributes().setBackground(
						FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);

		EntityViewInfo typeviewcollection = new EntityViewInfo();
		FilterInfo typefiltercollection = new FilterInfo();
		typefiltercollection.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Boolean(true)));
		typeviewcollection.setFilter(typefiltercollection);
		KDBizPromptBox f7Prompt = new KDBizPromptBox();
		f7Prompt
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");
		f7Prompt.setEditable(true);
		f7Prompt.setEnabledMultiSelection(true);
		f7Prompt.setDisplayFormat("$name$");
		f7Prompt.setEditFormat("$number$");
		f7Prompt.setCommitFormat("$number$");
		f7Prompt.setEntityViewInfo(typeviewcollection);
		this.tblProSetWithProductType.getColumn(T1_COL_PRODUCT_TYPE).setEditor(
				new KDTDefaultCellEditor(f7Prompt));

		this.tblProSetWithProductType.getColumn(T1_COL_PRODUCT_TYPE)
				.setRenderer(new ObjectValueRender() {
					public String getText(Object obj) {
						if (obj instanceof Object[]) {
							Object[] objs = (Object[]) obj;
							StringBuffer proDes = new StringBuffer();
							for (int j = 0; j < objs.length; j++) {

								SHEFunProductTypeEntryInfo entryInfo = null;
								ProductTypeInfo proType = null;

								if (objs[j] instanceof SHEFunProductTypeEntryInfo) {
									entryInfo = (SHEFunProductTypeEntryInfo) objs[j];
									if (entryInfo == null) {
										continue;
									}
									proType = entryInfo.getProductType();
								} else if (objs[j] instanceof ProductTypeInfo) {
									proType = ((ProductTypeInfo) objs[j]);
								}

								if (proType == null)
									continue;
								if (j != 0) {
									proDes.append(SPIT_STR);
								}
								proDes.append(proType.getName());
							}
							return proDes.toString();
						}
						return super.getText(obj);
					}
				});
	}

	/**
	 * 保存数据
	 */
	protected void btnOK_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
//		if (this.tblProSetWithProductType.getRowCount() == 0) {
//			this.destroyWindow();
//			this.abort();
//		}
		checkSave();
		checkChooseSave();
		this.storeFields();
		// 更新楼栋下的选房单
		updateChooseRoomSetInBuilding();
		SHEFunctionSetFactory.getRemoteInstance().save(this.editData);
		FDCMsgBox.showWarning("保存成功!");
		this.destroyWindow();
	}

	private void updateChooseRoomSetInBuilding() throws BOSException {
		Set toUpBs = new HashSet();
		if (!toUpBs.isEmpty()) {
			toUpBs.clear();
		}
		for (int i = 0; i < this.tblChooseRoom.getRowCount(); i++) {
			IRow row = this.tblChooseRoom.getRow(i);
			BuildingInfo building = (BuildingInfo) row.getCell(T3_BUILDING)
					.getValue();
			String buildingId = building.getId().toString();
			// 如果取消启用选房，则需要将该楼栋下的选房单置为选房作废
			if (row.getCell(T3_IS_ENABLE) != null
					&& !((Boolean) row.getCell(T3_IS_ENABLE).getValue())
							.booleanValue()) {
				Object tmp = this.isEnableMap.get(buildingId);
				if (tmp != null) {
					if (((Boolean) tmp).booleanValue()) {
						toUpBs.add(building);
					}
				}
			}
		}

		if (!toUpBs.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			StringBuffer bdes = new StringBuffer();
			int i = 0;
			for (Iterator itor = toUpBs.iterator(); itor.hasNext();) {
				BuildingInfo build = (BuildingInfo) itor.next();
				if (i != 0) {
					sb.append(",");
					bdes.append(",");
				}
				sb.append("'");
				sb.append(build.getId().toString());
				sb.append("'");

				bdes.append(build.getName());
				i++;
			}

			if (!(MsgBox.YES == MsgBox
					.showConfirm2(
							this,
							"楼栋【"
									+ bdes.toString()
									+ "】的选房功能被人工关闭，所有未转销售的选房单都将被取消，继续请点击【确定】按钮，返回重新修改请单击【取消】按钮。"))) {
				this.abort();
			}

			String sql = "update T_SHE_ChooseRoom set FChooseRoomStateEnum='7CANCELCHOOSE' where FChooseRoomStateEnum='2AFFIRM' and froomid in (select fid from t_she_room where fbuildingid in ("
					+ sb.toString() + "))";
			FDCSQLBuilder sqlb = new FDCSQLBuilder(sql);
			sqlb.execute();
		}
	}

	private void checkChooseSave() {
		for (int i = 0; i < this.tblChooseRoom.getRowCount(); i++) {
			IRow row = this.tblChooseRoom.getRow(i);
			// 增加判断，如果针对某楼栋是否启用勾选了则该条记录的开始、结束日期和选房时效为必录项
			if (row.getCell(T3_IS_ENABLE) != null
					&& ((Boolean) row.getCell(T3_IS_ENABLE).getValue())
							.booleanValue()) {
				if (row.getCell(T3_BEGIN_DATE).getValue() == null
						|| row.getCell(T3_END_DATE).getValue() == null
						|| row.getCell(T3_EXPIRY_DATE).getValue() == null) {
					MsgBox.showInfo(this, "选房设置第" + (i + 1)
							+ "行，开始日期，结束日期，选房时效不能为空！");
					this.abort();
				}
			}
		}
	}

	/**
	 * 校验是否可以保存
	 */
	private void checkSave() {

		boolean result = true;

		for (int i = 0; i < this.tblProSetWithProductType.getRowCount(); i++) {
			IRow row = this.tblProSetWithProductType.getRow(i);
			if (row == null) {
				continue;
			}

			if (row.getCell(T1_COL_PROEJCT_NAME).getValue() == null) {
				result = false;
			}
		}

		if (!result) {
			FDCMsgBox.showWarning(this, "请选择项目!");
			this.abort();
		}
		
		boolean existRepeat = false;
		Set pros = new HashSet();
		//如果存在2个相同项目，且产品类型为空的，需要提示不能保存
		for (int i = 0; i < this.tblProSetWithProductType.getRowCount(); i++) {
			IRow row = this.tblProSetWithProductType.getRow(i);
			if (row == null) {
				continue;
			}

			if (row.getCell(T1_COL_PROEJCT_NAME).getValue() != null  &&  row.getCell(T1_COL_PRODUCT_TYPE).getValue() == null) {
				String proId = ((SellProjectInfo)row.getCell(T1_COL_PROEJCT_NAME).getValue()).getId().toString();
				if(pros.contains(proId)){
					existRepeat = true;
					break;
				}else{
					pros.add(proId);
				}
			}
		}
		
		if(existRepeat){
			FDCMsgBox.showWarning(this, "同一项目存在重复设置!");
			this.abort();
		}

	}

	protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		this.destroyWindow();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("id");
		sic.add("entrys.id");
		sic.add("entrys.sellProject.id");
		sic.add("entrys.sellProject.name");
		sic.add("entrys.sellProject.number");
		sic.add("entrys.sellProject.longNumber");
		sic.add("entrys.appointInvalidLimit");
		sic.add("entrys.bookingInvalidLimit");
		sic.add("entrys.purchaseInvalidLimit");
		sic.add("entrys.purchaseSignLimit");
		sic.add("entrys.signInvalidLimit");
		sic.add("entrys.sincerAmount");
		sic.add("entrys.purBookingAmount");
		sic.add("entrys.bookingAmount");
		sic.add("entrys.productTypeEntry.id");
		sic.add("entrys.productTypeEntry.productType.id");
		sic.add("entrys.productTypeEntry.productType.name");
		sic.add("entrys.productTypeEntry.productType.number");
		
		sic.add("projectSet.*");
		sic.add("projectSet.sellProject.id");
		sic.add("projectSet.sellProject.name");
		sic.add("projectSet.sellProject.number");
		sic.add("projectSet.sellProject.longNumber");
		/*
		sic.add("projectSet.isUpdateForAmount");
		sic.add("projectSet.isUseAgioScheme");
		sic.add("projectSet.isUpdateForAgioDetail");
		sic.add("projectSet.isBasePriceControl");
		sic.add("projectSet.isBank");
		sic.add("projectSet.isStandardTotalAmount");
		sic.add("projectSet.isPriceAuditedForQuitRoom");
		sic.add("projectSet.isPriceAuditedForChanceRoom");
		sic.add("projectSet.isPriceAuditedForAreaChance");
		sic.add("projectSet.isUpdateForbelongDate");
		sic.add("projectSet.changeRoomDefaultBelongDate");
		sic.add("projectSet.priceChangeDefaultBelongDate");
		sic.add("projectSet.changeNameDefaultBelongDate");
		sic.add("projectSet.isSincerForNotSell");
		sic.add("projectSet.isUseTrackLimeDate");
		sic.add("projectSet.customerTrackLimitDate");
		sic.add("projectSet.commerTrackLimitDate");
		sic.add("projectSet.commerLimitDate");
		*/

		sic.add("chooseRoomSet.id");
		sic.add("chooseRoomSet.sellProject.id");
		sic.add("chooseRoomSet.sellProject.name");
		sic.add("chooseRoomSet.sellProject.number");
		sic.add("chooseRoomSet.sellProject.longNumber");
		sic.add("chooseRoomSet.building.id");
		sic.add("chooseRoomSet.building.name");
		sic.add("chooseRoomSet.building.number");
		sic.add("chooseRoomSet.startDate");
		sic.add("chooseRoomSet.endDate");
		sic.add("chooseRoomSet.isEnabled");
		sic.add("chooseRoomSet.defaultLimitDate");
		return sic;
	}

	public void loadFields() {
		super.loadFields();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selectColl = this.getSelectors();
		view.setSelector(selectColl);
		SorterItemCollection sors = new SorterItemCollection();
		sors.add(new SorterItemInfo("projectSet.sellProject.id"));
		sors.add(new SorterItemInfo("entrys.sellProject.id"));
		sors.add(new SorterItemInfo("chooseRoomSet.sellProject.id"));
		view.setSorter(sors);
		try {
			this.editData = null;
			SHEFunctionSetCollection coll = SHEFunctionSetFactory
					.getRemoteInstance().getSHEFunctionSetCollection(view);
			for (int i = 0; i < coll.size(); i++) {
				this.editData = coll.get(i);
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}

		if (this.editData.getId() != null) {
			loadBaseProjectSet();
			loadRootProjectSet();
			loadChooseRoomSet();
		}

	}

	/**
	 * 加载选房设置
	 */
	private void loadChooseRoomSet() {
		this.tblChooseRoom.removeRows();
		SHEFunctionChooseRoomSetCollection chooseRoomSet = this.editData
				.getChooseRoomSet();
		
		CRMHelper.sortCollection(chooseRoomSet, "sellProject.name", true);
		
		for (int i = 0; i < chooseRoomSet.size(); i++) {
			SHEFunctionChooseRoomSetInfo projectSetInfo = chooseRoomSet.get(i);
			IRow row = this.tblChooseRoom.addRow(i);
			if (projectSetInfo.getSellProject() != null
					&& !this.chooseRoomMap.containsKey(projectSetInfo
							.getSellProject().getId().toString())) {
				this.chooseRoomMap.put(projectSetInfo.getSellProject().getId()
						.toString(), projectSetInfo.getSellProject());
			}
			setChooseRoomRowData(row, projectSetInfo);

		}

		this.tblChooseRoom.getMergeManager().mergeBlock(0, 0,
				this.tblChooseRoom.getRowCount() - 1, 1,
				KDTMergeManager.FREE_MERGE);
	}

	/**
	 * 加载根项目的设置
	 */
	private void loadRootProjectSet() {
		this.tblProSetWithoutProductType.removeRows();
		SHEProjectSetCollection projectSet = this.editData.getProjectSet();
		CRMHelper.sortCollection(projectSet, "sellProject.name", true);
		for (int i = 0; i < projectSet.size(); i++) {
			SHEProjectSetInfo projectSetInfo = projectSet.get(i);
			IRow row = this.tblProSetWithoutProductType.addRow(i);
			if (projectSetInfo.getSellProject() != null
					&& !this.projectMap.containsKey(projectSetInfo
							.getSellProject().getId().toString())) {
				this.projectMap.put(projectSetInfo.getSellProject().getId()
						.toString(), projectSetInfo.getSellProject());
			}
			setRootProjectSetRowData(row, projectSetInfo);

		}

	}

	/**
	 * 加载末级项目的设置
	 */
	private void loadBaseProjectSet() {
		this.tblProSetWithProductType.removeRows();
		SHEFunctionSetEntryCollection setEntry = this.editData.getEntrys();
		CRMHelper.sortCollection(setEntry, "sellProject.name", true);
		for (int j = 0; j < setEntry.size(); j++) {
			SHEFunctionSetEntryInfo setEntryInfo = setEntry.get(j);
			if (setEntryInfo != null) {
				IRow row = this.tblProSetWithProductType.addRow(j);
				if (setEntryInfo.getSellProject() != null
						&& !this.baseProjectMap.containsKey(setEntryInfo
								.getSellProject().getId().toString())) {
					this.baseProjectMap.put(setEntryInfo.getSellProject()
							.getId().toString(), setEntryInfo.getSellProject());
				}
				setBaseProjectRowData(row, setEntryInfo);
			}
		}

	}

	/**
	 * 加载一行的选房设置
	 * 
	 * @param row
	 * @param chooseSetInfo
	 */
	private void setChooseRoomRowData(IRow row,
			SHEFunctionChooseRoomSetInfo chooseSetInfo) {

		if (row == null) {
			return;
		}

		row.getCell(T3_PROJECT).setValue(chooseSetInfo.getSellProject());
		row.getCell(T3_BUILDING).setValue(chooseSetInfo.getBuilding());
		row.getCell(T3_BEGIN_DATE).setValue(chooseSetInfo.getStartDate());
		row.getCell(T3_END_DATE).setValue(chooseSetInfo.getEndDate());
		row.getCell(T3_IS_ENABLE).setValue(
				new Boolean(chooseSetInfo.isIsEnabled()));
		row.getCell(T3_EXPIRY_DATE).setValue(
				new Integer(chooseSetInfo.getDefaultLimitDate()));
		if (!this.isEnableMap.containsKey(chooseSetInfo.getBuilding().getId()
				.toString())) {
			this.isEnableMap.put(
					chooseSetInfo.getBuilding().getId().toString(),
					new Boolean(chooseSetInfo.isIsEnabled()));
		}
	}

	/**
	 * 加载一行的根项目设置
	 * 
	 * @param row
	 * @param projectSetInfo
	 */
	private void setRootProjectSetRowData(IRow row,
			SHEProjectSetInfo projectSetInfo) {

		if (row == null) {
			return;
		}
		row.getCell(T2_PROJECT).setValue(projectSetInfo.getSellProject());
		row.getCell(T2_IS_DEAL_AMOUNT_EDIT).setValue(
				new Boolean(projectSetInfo.isIsUpdateForAmount()));
		row.getCell(T2_IS_ENABLE_AGIO).setValue(
				new Boolean(projectSetInfo.isIsUseAgioScheme()));
		row.getCell(T2_IS_ENABLE_ADJUST_AGIO).setValue(
				new Boolean(projectSetInfo.isIsUpdateForAgioDetail()));
		row.getCell(T2_IS_FORCE_LIMIT_PRICE).setValue(
				new Boolean(projectSetInfo.isIsBasePriceControl()));
		row.getCell(T2_IS_MUST_BY_BANK).setValue(
				new Boolean(projectSetInfo.isIsBank()));
		row.getCell(T2_IS_DEAL_AMT_BY_STAND_AMT).setValue(
				new Boolean(projectSetInfo.isIsStandardTotalAmount()));
		row.getCell(T2_IS_RE_PRICE_OF_QUIT_ROOM).setValue(
				new Boolean(projectSetInfo.isIsPriceAuditedForQuitRoom()));
		row.getCell(T2_IS_RE_PRICE_OF_CHANGE_ROOM).setValue(
				new Boolean(projectSetInfo.isIsPriceAuditedForChanceRoom()));
		row.getCell(T2_IS_AREA_CHANGE_NEED_AUDIT).setValue(
				new Boolean(projectSetInfo.isIsPriceAuditedForAreaChance()));
		row.getCell(T2_IS_BIZ_DATE_EDITABLE).setValue(
				new Boolean(projectSetInfo.isIsUpdateForbelongDate()));
		row.getCell(T2_CHANGE_ROOM_DEF_BIZ_DATE).setValue(
				projectSetInfo.getChangeRoomDefaultBelongDate());
		row.getCell(T2_PRICE_ADJUST_DEF_BIZ_DATE).setValue(
				projectSetInfo.getPriceChangeDefaultBelongDate());
		row.getCell(T2_CHANGE_CUS_DEF_BIZ_DATE).setValue(
				projectSetInfo.getChangeNameDefaultBelongDate());
		row.getCell(T2_IS_NO_SELL_CAN_SIN).setValue(
				new Boolean(projectSetInfo.isIsSincerForNotSell()));
		row.getCell(T2_IS_ENABLE_TRADE).setValue(
				new Boolean(projectSetInfo.isIsUseTrackLimeDate()));
		row.getCell(T2_CUS_TRADE_DAYS).setValue(
				new Integer(projectSetInfo.getCustomerTrackLimitDate()));
		row.getCell(T2_CHANCE_DAYS).setValue(
				new Integer(projectSetInfo.getCommerTrackLimitDate()));
		row.getCell(T2_COMMERCECHANCE_DAYS).setValue(
				new Integer(projectSetInfo.getCommerLimitDate()));
		
		row.getCell(T2_NAME_REPEAT_RULE).setValue(projectSetInfo.getNameRepeatRule());
		row.getCell(T2_PHONE_REPEAT_RULE).setValue(projectSetInfo.getPhoneRepeatRule());
		row.getCell(T2_CER_REPEAT_RULE).setValue(projectSetInfo.getCerRepeatRule());
		
	}

	/**
	 * 加载一行的末级项目设置
	 * 
	 * @param row
	 * @param setEntryInfo
	 */
	private void setBaseProjectRowData(IRow row,
			SHEFunctionSetEntryInfo setEntryInfo) {
		if (row == null) {
			return;
		}

		if (setEntryInfo.getSellProject() != null) {
			row.getCell(T1_COL_PROEJCT_NAME).setValue(
					setEntryInfo.getSellProject());
		}

		if (setEntryInfo.getProductTypeEntry() != null) {
			SHEFunProductTypeEntryCollection coll = setEntryInfo
					.getProductTypeEntry();
			Object[] proTypes = new Object[coll.size()];
			for (int j = 0; j < coll.size(); j++) {
				proTypes[j] = coll.get(j).getProductType();
			}
			if (proTypes.length > 0) {
				row.getCell(T1_COL_PRODUCT_TYPE).setValue(proTypes);
			}
		}
		row.getCell(T1_KEEP_DOWN_LIMIT_TIME).setValue(
				new Integer(setEntryInfo.getAppointInvalidLimit()));
		row.getCell(T1_PRE_PURCHASE_LIMIT_TIME).setValue(
				new Integer(setEntryInfo.getBookingInvalidLimit()));
		row.getCell(T1_PURCHASE_LIMIT_TIME).setValue(
				new Integer(setEntryInfo.getPurchaseInvalidLimit()));
		row.getCell(T1_TO_SIGN_LIMIT_TIME).setValue(
				new Integer(setEntryInfo.getPurchaseSignLimit()));
		row.getCell(T1_SIGN_LIMIT_TIME).setValue(
				new Integer(setEntryInfo.getSignInvalidLimit()));
		row.getCell(T1_SIN_PURCHASE_STANDARD).setValue(
				(BigDecimal) setEntryInfo.getSincerAmount());
		row.getCell(T1_PRE_PURCHASE_STANDARD).setValue(
				(BigDecimal) setEntryInfo.getPurBookingAmount());
		row.getCell(T1_PRE_STANDARD).setValue(
				(BigDecimal) setEntryInfo.getBookingAmount());
	}

}