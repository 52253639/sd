/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.DyProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjProEntrApporDataCollection;
import com.kingdee.eas.fdc.basedata.CurProjProEntrApporDataInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class DynamicCostProductSplitSettingUI extends
		AbstractDynamicCostProductSplitSettingUI {
	private ApportionTypeCollection apportionColl = new ApportionTypeCollection();

	private Map splitMap = new HashMap();

	private String projectId = null;

	private boolean isFullAppor;

	private boolean isMutiSelected;

	/**
	 * output class constructor
	 */
	public DynamicCostProductSplitSettingUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionSplit.setEnabled(true);
		projectId = (String) getUIContext().get("projectId");
		isFullAppor = ((Boolean) getUIContext().get("isFullApportion"))
				.booleanValue();
		isMutiSelected = ((Boolean) getUIContext().get("isMutiSelected"))
				.booleanValue();

		DynamicCostProductSplitEntryCollection splits = (DynamicCostProductSplitEntryCollection) getUIContext()
				.get("splits");
		for (int i = 0; i < splits.size(); i++) {
			DynamicCostProductSplitEntryInfo info = splits.get(i);
			splitMap.put(info.getProduct().getId().toString(), info);
		}
		initTable();
		this.actionSelectAll.setEnabled(true);
		this.actionSelectNone.setEnabled(true);
		this.actionRemove.setVisible(false);
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		boolean isSelected = false;
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			if (kdtEntrys.getCell(i, "isSelected").getValue().equals(
					Boolean.TRUE)) {
				isSelected = true;
				break;
			}
		}
		if (!isSelected) {
			MsgBox.showWarning(this, AimCostHandler
					.getResource("NoSelectObject"));
			return;
		}
		if (this.getSelectApportion() == null) {
			MsgBox.showWarning(this, AimCostHandler
					.getResource("NoSelectApportion"));
			return;
		}
		this.getUIContext().put("isOK", Boolean.TRUE);
		getUIContext().put("splits", this.getSelectSetting());
		disposeUIWindow();
	}

	public void initTable() throws BOSException {
		kdtEntrys.checkParsed();
		kdtEntrys.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		kdtEntrys.getColumn("isSelected").getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn("hasHappenAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntrys.getColumn("hasHappenAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtEntrys.getColumn("hasHappenAmount").getStyleAttributes().setLocked(
				true);
		kdtEntrys.getColumn("intendingAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntrys.getColumn("intendingAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtEntrys.getColumn("intendingAmount").getStyleAttributes().setLocked(
				true);
		kdtEntrys.getColumn("aimCostAmount").getStyleAttributes().setLocked(
				true);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("forCostApportion", Boolean.TRUE));
		filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("name");
		view.getSorter().add(new SorterItemInfo("number"));
		apportionColl = ApportionTypeFactory.getRemoteInstance()
				.getApportionTypeCollection(view);
		IRow selectRow = kdtEntrys.addRow();
		selectRow.getStyleAttributes().setLocked(true);
		selectRow.getCell("aimCostAmount").setValue(Boolean.FALSE);
		for (Iterator iter = apportionColl.iterator(); iter.hasNext();) {
			ApportionTypeInfo apportion = (ApportionTypeInfo) iter.next();

			String key = apportion.getId().toString();
			if (!key.equals(ApportionTypeInfo.aimCostType)
					&& !key.equals(ApportionTypeInfo.appointType)) {
				IColumn col = kdtEntrys.addColumn(kdtEntrys.getColumnCount());
				col.setKey(key);
				col.getStyleAttributes().setLocked(true);
				kdtEntrys.getHeadRow(0).getCell(col.getColumnIndex()).setValue(
						apportion.getName());
				// 选择行
				ICell cell = selectRow.getCell(key);
				cell.setValue(Boolean.FALSE);
				// cell.getStyleAttributes().setBackground(new Color(0xFFFFFF));
			}
		}
		// 加指定分摊
		IColumn col = kdtEntrys.addColumn();
		final StyleAttributes sa = col.getStyleAttributes();
		sa.setLocked(true);
		sa.setBackground(FDCTableHelper.cantEditColor);
		col.setKey("appointType");
		kdtEntrys.getHeadRow(0).getCell(col.getColumnIndex()).setValue("指定分摊");
		// 选择行
		ICell appointcell = selectRow.getCell("appointType");
		appointcell.setValue(Boolean.FALSE);
		appointcell.getStyleAttributes().setLocked(true);
		if (!isMutiSelected && !isFullAppor) {
			selectRow.getCell("aimCostAmount").getStyleAttributes()
					.setBackground(FDCHelper.KDTABLE_SUBTOTAL_BG_COLOR);
			selectRow.getCell("aimCostAmount").setUserObject("noSelected");
		} else {
			selectRow.getCell("aimCostAmount").setValue(Boolean.TRUE);
		}
		Collection collection = splitMap.values();
		Iterator iterator = collection.iterator();
		if (iterator.hasNext()) {
			DynamicCostProductSplitEntryInfo info = (DynamicCostProductSplitEntryInfo) iterator
					.next();
			if (info.getApportionType() != null) {
				kdtEntrys.getRow(0).getCell("aimCostAmount").setValue(
						Boolean.FALSE);
				if (info.getApportionType().getId().toString().equals(
						ApportionTypeInfo.aimCostType)) {
					if (isMutiSelected || isFullAppor) {
						kdtEntrys.getRow(0).getCell("aimCostAmount").setValue(
								Boolean.TRUE);
					}
				} else if (info.getApportionType().getId().toString().equals(
						ApportionTypeInfo.appointType)) {
					kdtEntrys.getRow(0).getCell("appointType").setValue(
							Boolean.TRUE);
				} else {
					kdtEntrys.getRow(0).getCell(
							info.getApportionType().getId().toString())
							.setValue(Boolean.TRUE);
				}
			} else {
				if (isMutiSelected || isFullAppor) {
					kdtEntrys.getRow(0).getCell("aimCostAmount").setValue(
							Boolean.TRUE);
				}
			}
		}
		DyProductTypeGetter getter = new DyProductTypeGetter(projectId);
		Map prodcutMap = getter.getSortedProductMap();
		Set set = prodcutMap.keySet();
		for (Iterator pIter = set.iterator(); pIter.hasNext();) {
			ProductTypeInfo product = (ProductTypeInfo) prodcutMap.get(pIter
					.next());
			IRow row = kdtEntrys.addRow();

			KDFormattedTextField formattedTextField = new KDFormattedTextField(
					KDFormattedTextField.DECIMAL);
			formattedTextField.setPrecision(2);
			formattedTextField.setSupportedEmpty(true);
			formattedTextField.setNegatived(false);
			ICellEditor numberEditor = new KDTDefaultCellEditor(
					formattedTextField);
			row.getCell("appointType").setEditor(numberEditor);
			// row.getCell("appointType").getStyleAttributes().setLocked(true);
			row.getCell("appointType").getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));

			String productId = product.getId().toString();
			row.setUserObject(product);
			// row.getCell(0).setValue(productId);
			row.getCell("isSelected").setValue(Boolean.FALSE);
			row.getCell("apportionObj.name").setValue(product.getName());
			DynamicCostProductSplitEntryInfo info = (DynamicCostProductSplitEntryInfo) splitMap
					.get(productId);
			if (info != null) {
				if (info.getDescription() != null) {
					row.getCell("isSelected").setValue(Boolean.TRUE);
				}
				if (info.getHanppenDirectAmount() != null
						&& info.getHanppenDirectAmount().compareTo(
								FDCHelper.ZERO) != 0) {
					row.getCell("hasHappenAmount").setValue(
							info.getHanppenDirectAmount());
				}
				if (info.getIntendingDirectAmount() != null
						&& info.getIntendingDirectAmount().compareTo(
								FDCHelper.ZERO) != 0) {
					row.getCell("intendingAmount").setValue(
							info.getIntendingDirectAmount());
				}
				if (info.getAimCostAmount() != null
						&& info.getAimCostAmount().compareTo(FDCHelper.ZERO) != 0) {
					row.getCell("aimCostAmount").setValue(
							info.getAimCostAmount());
				}
				if (info.getAppointAmount() != null
						&& info.getAppointAmount().compareTo(FDCHelper.ZERO) != 0) {
					row.getCell("appointType")
							.setValue(info.getAppointAmount());
				}
			}
			if (this.isMutiSelected) {
				row.getCell("aimCostAmount").setValue("*       ");
				row.getCell("appointType").setValue(null);
			}
			CurProjProEntrApporDataCollection appts = getter.getAppors(product
					.getId().toString());
			for (Iterator aIter = appts.iterator(); aIter.hasNext();) {
				CurProjProEntrApporDataInfo apptData = (CurProjProEntrApporDataInfo) aIter
						.next();
				ICell cell = row.getCell(apptData.getApportionType().getId()
						.toString());
				if (cell == null) {
					continue;
				}
				BigDecimal amount = apptData.getValue();
				if (amount == null) {
					amount = FDCHelper.ZERO;
				}
				cell.setValue(amount);
				cell.getStyleAttributes().setNumberFormat(
						FDCHelper.getNumberFtm(2));
				cell.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
			}
			ICell cell = row.getCell("aimCostAmount");
			cell.getStyleAttributes()
					.setNumberFormat(FDCHelper.getNumberFtm(2));
			cell.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
		}
	}

	private DynamicCostProductSplitEntryCollection getSelectSetting() {
		DynamicCostProductSplitEntryCollection entrys = new DynamicCostProductSplitEntryCollection();
		ApportionTypeInfo apportion = getSelectApportion();
		// 拆分对象
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			if (row.getCell("isSelected").getValue().equals(Boolean.TRUE)) {
				DynamicCostProductSplitEntryInfo entry = new DynamicCostProductSplitEntryInfo();
				entry.setProduct((ProductTypeInfo) row.getUserObject());
				BigDecimal happenAmount = (BigDecimal) row.getCell(
						"hasHappenAmount").getValue();
				if (happenAmount == null) {
					happenAmount = FDCHelper.ZERO;
				}
				BigDecimal intendingAmount = (BigDecimal) row.getCell(
						"intendingAmount").getValue();
				if (intendingAmount == null) {
					intendingAmount = FDCHelper.ZERO;
				}
				if (apportion.getId().toString().equals(
						ApportionTypeInfo.appointType)) {
					BigDecimal appointAmount = (BigDecimal) row.getCell(
							"appointType").getValue();
					if (appointAmount == null) {
						appointAmount = FDCHelper.ZERO;
					}
					entry.setAppointAmount(appointAmount);
				} else {
					entry.setAppointAmount(null);
				}
				entry.setHanppenDirectAmount(happenAmount);
				entry.setIntendingDirectAmount(intendingAmount);
				entry.setApportionType(apportion);
				entry.setDescription("isChoose");
				entrys.add(entry);
			}
		}
		return entrys;

	}

	private ApportionTypeInfo getSelectApportion() {
		String apptId = null;
		for (int i = 4; i < kdtEntrys.getColumnCount(); i++) {
			if (kdtEntrys.getRow(0).getCell(i).getValue().equals(Boolean.TRUE)) {
				apptId = kdtEntrys.getColumnKey(i);
				break;
			}
		}
		if (apptId != null && apptId.equals("aimCostAmount")) {
			apptId = ApportionTypeInfo.aimCostType;
		}
		if (apptId != null && apptId.equals("appointType")) {
			apptId = ApportionTypeInfo.appointType;
		}
		for (Iterator iter = apportionColl.iterator(); iter.hasNext();) {
			ApportionTypeInfo apptType = (ApportionTypeInfo) iter.next();
			if (apptType.getId().toString().equals(apptId)) {
				return apptType;
			}
		}
		return null;
	}

	protected IObjectValue createNewData() {
		// TODO 自动生成方法存根
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return AimCostProductSplitEntryFactory.getRemoteInstance();
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractCostSplitApportionUI#kdtEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent)
	 */
	protected void kdtEntrys_tableClicked(KDTMouseEvent e) throws Exception {
		super.kdtEntrys_tableClicked(e);

		if (e.getRowIndex() == 0) {
			int idx = e.getColIndex();
			if (idx >= 4 && idx < kdtEntrys.getColumnCount()-1) {
				IRow row = kdtEntrys.getRow(0);
				if (idx == 4 && row.getCell(4).getUserObject() != null) {
					return;
				}
				for (int i = 4; i < kdtEntrys.getColumnCount(); i++) {
					row.getCell(i).setValue(Boolean.FALSE);
				}
				row.getCell(idx).setValue(Boolean.TRUE);
			}
		}
		if (e.getColIndex() == 0 && e.getRowIndex() != 0) {
			Boolean b = (Boolean) kdtEntrys.getRow(e.getRowIndex()).getCell(
					"isSelected").getValue();
			kdtEntrys.getRow(e.getRowIndex()).getCell("isSelected").setValue(
					Boolean.valueOf(!b.booleanValue()));
		}
	}

	public void actionSelectAll_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			kdtEntrys.getCell(i, "isSelected").setValue(Boolean.TRUE);
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractCostSplitApportionUI#actionSelNone_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSelectNone_actionPerformed(ActionEvent e)
			throws Exception {
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			kdtEntrys.getCell(i, "isSelected").setValue(Boolean.FALSE);
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSplit.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.btnSelectNone.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
		this.btnSelectAll.setIcon(EASResource.getIcon("imgTbtn_selectall"));
	}
}