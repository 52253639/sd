/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
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
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.DyProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.app.PeriodDataGetter;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjProEntrApporDataCollection;
import com.kingdee.eas.fdc.basedata.CurProjProEntrApporDataInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PeriodProductSplitSettingUI extends AbstractPeriodProductSplitSettingUI
{
    private static final Logger logger = CoreUIObject.getLogger(PeriodProductSplitSettingUI.class);
    
    
	private ApportionTypeCollection apportionColl = new ApportionTypeCollection();

	private Map splitMap = new HashMap();

	private String projectId = null;

	private boolean isFullAppor;
	
	//是否多选
	private boolean isMutiSelected;
	
	private Map aimApportionMap;
	
    /**
     * output class constructor
     */
    public PeriodProductSplitSettingUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
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
		aimApportionMap = (Map)getUIContext().get("index");
		for (int i = 0; i < splits.size(); i++) {
			DynamicCostProductSplitEntryInfo info = splits.get(i);
			splitMap.put(info.getProduct().getId().toString(), info);
		}
		initTable();
		this.actionSelectAll.setEnabled(true);
		this.actionSelectNone.setEnabled(true);
		this.actionRemove.setVisible(false);
	}
	
    /**
     * 全选
     */
    public void actionSelectAll_actionPerformed(ActionEvent e) throws Exception
    {
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			kdtEntrys.getCell(i, "isSelected").setValue(Boolean.TRUE);
		}
    }

    /**
     * 全清
     */
    public void actionSelectNone_actionPerformed(ActionEvent e) throws Exception
    {
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			kdtEntrys.getCell(i, "isSelected").setValue(Boolean.FALSE);
		}
    }

    /**
     * output actionSplit_actionPerformed
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
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
		//去除校验 不需要值完全相等
//		checkAmt();
		this.getUIContext().put("isOK", Boolean.TRUE);
		getUIContext().put("splits", this.getSelectSetting());
		disposeUIWindow();
    }

	protected IObjectValue createNewData() {
		// TODO 自动生成方法存根
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return AimCostProductSplitEntryFactory.getRemoteInstance();
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
				entry.setApportionType(apportion);
				entry.setDescription("isChoose");
				entrys.add(entry);
			}
		}
		return entrys;
	}
	
	private ApportionTypeInfo getSelectApportion() {
		String apptId = null;
		for (int i = 3; i < kdtEntrys.getColumnCount(); i++) {
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
		sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
//		sa.setLocked(true);
//		sa.setBackground(FDCTableHelper.cantEditColor);
		col.setKey("appointType");
		kdtEntrys.getHeadRow(0).getCell(col.getColumnIndex()).setValue("指定分摊");
		// 选择行
		ICell appointcell = selectRow.getCell("appointType");
		appointcell.setValue(Boolean.FALSE);
//		appointcell.getStyleAttributes().setLocked(true);
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
				sa.setLocked(true);
				sa.setBackground(FDCTableHelper.cantEditColor);
				row.getCell("appointType").getStyleAttributes().setLocked(true);
			}
/*			CurProjProEntrApporDataCollection appts = getter.getAppors(product
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
			}*/
			//应该取月结指标数据
			for(int i = 0 ; i < kdtEntrys.getRowCount();i++){
				IRow iRow = kdtEntrys.getRow(i);
				ProductTypeInfo type = (ProductTypeInfo)iRow.getUserObject();
				if(type == null)
					continue;
				for(int j = 0 ; j < apportionColl.size();j++){
					ApportionTypeInfo apportion = apportionColl.get(j);
					String appId = apportion.getId().toString();
					ICell cell = iRow.getCell(appId);
					if(cell == null)
						continue;
					String typeId = type.getId().toString();
					if(aimApportionMap.containsKey(projectId+" "+typeId+" "+appId)){
						cell.setValue(aimApportionMap.get(projectId+" "+typeId+" "+appId));
					}else{
						cell.setValue(FDCHelper.ZERO);
					}
					cell.getStyleAttributes().setNumberFormat(
							FDCHelper.getNumberFtm(2));
					cell.getStyleAttributes().setHorizontalAlign(
							HorizontalAlignment.RIGHT);
				}
			}
			ICell cell = row.getCell("aimCostAmount");
			cell.getStyleAttributes()
					.setNumberFormat(FDCHelper.getNumberFtm(2));
			cell.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
		}
	}
	//指定分摊时校验
	private void checkAmt(){
		//如果分摊时选择了多个
		if(isMutiSelected){
			return;
		}
		//如果选择的不是指定分摊
		IRow row = kdtEntrys.getRow(0);
		int colIndex = kdtEntrys.getColumnCount() -1 ;
		Boolean b = (Boolean)row.getCell(colIndex).getValue();
		if(!b.booleanValue()){
			return;
		}
		BigDecimal hasPaidAmount = (BigDecimal)getUIContext().get("hasPaidAmount");
		BigDecimal appointAmount = FDCHelper.ZERO;
		for(int i = 1; i < kdtEntrys.getRowCount();i++ ){
			IRow rowEntry = kdtEntrys.getRow(i);
			BigDecimal amount = (BigDecimal)rowEntry.getCell("appointType").getValue();
			appointAmount = FDCHelper.add(appointAmount,amount);
		}
		if(FDCHelper.toBigDecimal(hasPaidAmount).compareTo(
				FDCHelper.toBigDecimal(appointAmount)) != 0){
			MsgBox.showWarning(this,"指定分摊金额之和必须等于该科目的已实现金额！\r\n" +
					"该科目已实现金额为："+hasPaidAmount+"\r\n" +
					"指定分摊金额之和为："+appointAmount);
			SysUtil.abort();
		}
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSplit.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.btnSelectNone.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
		this.btnSelectAll.setIcon(EASResource.getIcon("imgTbtn_selectall"));
	}
	
	protected void kdtEntrys_tableClicked(KDTMouseEvent e) throws Exception {
		super.kdtEntrys_tableClicked(e);
		//选择互斥
		if (e.getRowIndex() == 0) {
			int idx = e.getColIndex();
			int count = kdtEntrys.getColumnCount();
			if(isMutiSelected){
				count = kdtEntrys.getColumnCount() - 1;
			}
//			if (idx >= 4 && idx < kdtEntrys.getColumnCount()) {
			if (idx >= 3 && idx < count) {
				IRow row = kdtEntrys.getRow(0);
				if (idx == 3 && row.getCell(3).getUserObject() != null) {
					return;
				}
				for (int i = 3; i < kdtEntrys.getColumnCount(); i++) {
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

}