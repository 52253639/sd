/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.AssessSetting;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.HeadColumnCollection;
import com.kingdee.eas.fdc.invite.HeadColumnFactory;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.RefPriceEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.CoreUI;

/**
 * output class name
 */
public class QuoteAnalyseItemFilterUI extends AbstractQuoteAnalyseItemFilterUI {

	boolean isClickYes = false;

	private HeadTypeInfo headType;

	private Map searchMap;

	/**
	 * output class constructor
	 */
	public QuoteAnalyseItemFilterUI() throws Exception {
		super();
	}

	public boolean destroyWindow() {
		if (!isClickYes) {
			this.getUIContext().put("searchMap", null);
		}
		return super.destroyWindow();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		String headTypeId = (String) this.getUIContext().get("headTypeId");
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		headType = HeadTypeFactory.getRemoteInstance().getHeadTypeInfo(
				new ObjectUuidPK(BOSUuid.read(headTypeId)));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("parent.*");
		view.getSorter().add(new SorterItemInfo("longNumber"));
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("headType.id", headTypeId));
		HeadColumnCollection entrys = HeadColumnFactory.getRemoteInstance()
				.getHeadColumnCollection(view);
		headType.getEntries().clear();
		headType.getEntries().addCollection(entrys);
		searchMap = (Map) this.getUIContext().get("searchMap");
		this.comboFilterType.addItem("显示全部子目");
		this.comboFilterType.addItem("显示关键子目");
		this.comboFilterType.addItem("显示明细报价子目");
		this.comboFilterType.addItem("显示超出基准百分比范围子目");
		this.comboFilterType.addItem("显示超出总金额指定份额子目");
		this.comboFilterType.addItem("显示超出页签金额指定份额子目");
		super.onLoad();
		this.initQueryTable();
		if (searchMap.get("showType").equals("showAll")) {
			this.comboFilterType.setSelectedIndex(0);
		} else if (searchMap.get("showType").equals("showImportent")) {
			this.comboFilterType.setSelectedIndex(1);
		} else if (searchMap.get("showType").equals("showDetail")) {
			this.comboFilterType.setSelectedIndex(2);
		} else if (searchMap.get("showType").equals("showOver")) {
			this.comboFilterType.setSelectedIndex(3);
		} else if (searchMap.get("showType").equals("showOverAll")) {
			this.comboFilterType.setSelectedIndex(4);
			this.txtOverPro.setValue(searchMap.get("overPro"));
		} else if (searchMap.get("showType").equals("showOverSheet")) {
			this.comboFilterType.setSelectedIndex(5);
			this.txtOverPro.setValue(searchMap.get("overPro"));
		}
		this.txtOverPro.setNegatived(false);
		this.txtOverPro.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtOverPro.setPrecision(2);
		this.txtOverPro.setSupportedEmpty(true);
	}

	/**
	 * output btnYes_actionPerformed method
	 */
	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnYes_actionPerformed(e);
		this.searchMap.clear();
		BigDecimal pro = this.txtOverPro.getBigDecimalValue();
		if(pro==null){
			pro=FDCHelper.ZERO;
		}
		if (comboFilterType.getSelectedIndex() == 0) {
			searchMap.put("showType", "showAll");
		} else if (comboFilterType.getSelectedIndex() == 1) {
			searchMap.put("showType", "showImportent");
		} else if (comboFilterType.getSelectedIndex() == 2) {
			searchMap.put("showType", "showDetail");
		} else if (comboFilterType.getSelectedIndex() == 3) {
			searchMap.put("showType", "showOver");
		} else if (comboFilterType.getSelectedIndex() == 4) {
			searchMap.put("showType", "showOverAll");
			searchMap.put("overPro",pro);
		} else if (comboFilterType.getSelectedIndex() == 5) {
			searchMap.put("showType", "showOverSheet");
			searchMap.put("overPro",pro);
		}
		for (int i = 0; i < this.tblQuery.getRowCount(); i++) {
			IRow row = this.tblQuery.getRow(i);
			if (row.getUserObject() != null) {
				HeadColumnInfo col = (HeadColumnInfo) row.getUserObject();
				if (row.getCell("value").getValue() != null) {
					String value = (String) row.getCell("value").getValue();
					this.searchMap.put(col.getId().toString(), value);
				}
			}
		}
		this.getUIContext().put("searchMap", searchMap);
		this.isClickYes = true;
		this.destroyWindow();
	}

	public void initQueryTable() {
		this.tblQuery.checkParsed();
		tblQuery.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		IColumn column = this.tblQuery.addColumn();
		column.setKey("queryItem");
		column.setWidth(70);
		column = this.tblQuery.addColumn();
		column.setKey("compareSymbol");
		column.setWidth(50);
		column = this.tblQuery.addColumn();
		column.setKey("value");
		column.setWidth(200);
		IRow headRow = tblQuery.addHeadRow();
		headRow.getCell("queryItem").setValue("比较项");
		headRow.getCell("compareSymbol").setValue("比较符");
		headRow.getCell("value").setValue("比较值");
		this.tblQuery.getColumn("queryItem").getStyleAttributes().setLocked(
				true);
		this.tblQuery.getColumn("compareSymbol").getStyleAttributes()
				.setLocked(true);
		HeadColumnCollection cols = headType.getEntries();
		for (int i = 0; i < cols.size(); i++) {
			HeadColumnInfo info = cols.get(i);
			if (info.getColumnType().equals(ColumnTypeEnum.String)) {
				IRow row = this.tblQuery.addRow();
				row.setUserObject(info);
				String value = (String) searchMap.get(info.getId().toString());
				row.getCell("queryItem").setValue(info.getName());
				row.getCell("compareSymbol").setValue("Like");
				row.getCell("value").setValue(value);
			}
		}
	}

	/**
	 * output btnNo_actionPerformed method
	 */
	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnNo_actionPerformed(e);
		this.getUIContext().put("searchMap", null);
		this.destroyWindow();
	}

	public static Map showFilterUI(CoreUI ui, String headTypeId, Map searchMap)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("headTypeId", headTypeId);
		uiContext.put("searchMap", searchMap);
		IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(QuoteAnalyseItemFilterUI.class.getName(), uiContext,
						null, "Edit");
		window.show();
		Map ctx = window.getUIObject().getUIContext();
		return (Map) ctx.get("searchMap");
	}

	protected void comboFilterType_actionPerformed(ActionEvent e)
			throws Exception {
		super.comboFilterType_actionPerformed(e);
		if (comboFilterType.getSelectedIndex() == 0) {
			this.contOverPro.setVisible(false);
		} else if (comboFilterType.getSelectedIndex() == 1) {
			this.contOverPro.setVisible(false);
		} else if (comboFilterType.getSelectedIndex() == 2) {
			this.contOverPro.setVisible(false);
		} else if (comboFilterType.getSelectedIndex() == 3) {
			this.contOverPro.setVisible(false);
		} else if (comboFilterType.getSelectedIndex() == 4) {
			this.contOverPro.setVisible(true);
		} else if (comboFilterType.getSelectedIndex() == 5) {
			this.contOverPro.setVisible(true);
		}
	}
}