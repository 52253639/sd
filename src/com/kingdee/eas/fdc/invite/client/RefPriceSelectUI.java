/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ObjectValueRenderImpl;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.HeadColumnCollection;
import com.kingdee.eas.fdc.invite.HeadColumnFactory;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.RefPriceCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryInfo;
import com.kingdee.eas.fdc.invite.RefPriceFactory;
import com.kingdee.eas.fdc.invite.RefPriceInfo;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class RefPriceSelectUI extends AbstractRefPriceSelectUI {
	private static final Color LOCK_COLOR = new Color(0xF0AAD9);
	// the day's million seconds 24*60*60*1000
	private static final long DAYMILLIS = 86400000;

	private HeadTypeInfo headType;

	/**
	 * output class constructor
	 */
	public RefPriceSelectUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		this.tblMain.getStyleAttributes().setWrapText(true);
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
		super.onLoad();
		initQueryTable();
		initMainTable();
		fillMainTable();
	}

	static class myFormatter implements IFormatter{
		public String valueToString(Object o){
			String temp = o.toString();
				int start = temp.indexOf('[');
				int end = temp.indexOf(']');
				return start == 0 && end > 1 ? temp.substring(start + 1, end)
						: temp;
			};
			public void applyPattern(String pattern){};
	}
	static class myDataFormatter implements IDataFormat{
		public String format(Object o) {
			String temp = o.toString();
			int start = temp.indexOf('[');
			int end = temp.indexOf(']');
			return start == 0 && end > 1 ? temp.substring(start + 1, end)
					: temp;
		};
	}
	private myFormatter myFormatter = new myFormatter();
	public void initQueryTable() {
		this.tblQuery.checkParsed();
		tblQuery.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblQuery.getColumn("queryItem").getStyleAttributes().setLocked(
				true);
		this.tblQuery.getColumn("compareSymbol").getStyleAttributes()
				.setLocked(true);
		Map searchMap = (Map) this.getUIContext().get("seachMap");
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

		IRow row = this.tblQuery.addRow();
		row.getCell("queryItem").setValue("时间");
		row.getCell("compareSymbol").setValue(">=");
		row.getCell("value").setValue(null);
		KDDatePicker datePicker = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(datePicker);
		row.getCell("value").setEditor(dateEditor);
		String formatString = "yyyy-MM-dd";
		row.getCell("value").getStyleAttributes().setNumberFormat(formatString);
		row = this.tblQuery.addRow();
		row.getCell("queryItem").setValue("时间");
		row.getCell("compareSymbol").setValue("<=");
		row.getCell("value").setValue(null);
		datePicker = new KDDatePicker();
		dateEditor = new KDTDefaultCellEditor(datePicker);
		row.getCell("value").setEditor(dateEditor);
		row.getCell("value").getStyleAttributes().setNumberFormat(formatString);
		row = this.tblQuery.addRow();
		row.getCell("queryItem").setValue("所属组织");
		row.getCell("compareSymbol").setValue("等于");
		KDBizPromptBox f7 = new KDBizPromptBox();
		f7.setDisplayFormat("$name$");
		f7.setCommitFormat("$name$");
		f7.setEditFormat("$name$");
		f7.setEnabledMultiSelection(true);
		
		f7.setSelector(new CompanyTreeSelectF7(this));
		//设置格式，去掉多选择后，留下的中括号
		f7.setDisplayFormatter(myFormatter);
		f7.setEditFormatter(myFormatter);
		
		//f7.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");
		row.getCell("value").setEditor(new KDTDefaultCellEditor(f7));
		ObjectValueRenderImpl avr = new ObjectValueRenderImpl();
		//avr.setFormat(new BizDataFormat("$name$"));
		avr.setFormat(new myDataFormatter());
		row.getCell("value").setRenderer(avr);
		FullOrgUnitInfo currentOrg = SysContext.getSysContext()
				.getCurrentFIUnit().castToFullOrgUnitInfo();
		Vector v = new Vector();
    	FullOrgUnitInfo compInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		if (compInfo != null) {
			v.add(compInfo);
			f7.setValue(v);
		}
		row.getCell("value").setValue(v);
		
		
	}

	public void initMainTable() {
		tblMain.checkParsed();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		tblMain.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		tblMain.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		tblMain.getHeadMergeManager().mergeBlock(0, 3, 1, 3);
		tblMain.getHeadMergeManager().mergeBlock(0, 4, 1, 4);
		tblMain.getHeadMergeManager().mergeBlock(0, 5, 1, 5);
		tblMain.getHeadMergeManager().mergeBlock(0, 6, 1, 6);
		tblMain.getHeadMergeManager().mergeBlock(0, 7, 1, 7);
		//tblMain.getHeadMergeManager().mergeBlock(0, 8, 1, 8);
		HeadColumnCollection cols = headType.getEntries();
		HeadColumnCollection refCols = new HeadColumnCollection();
		for (int i = 0; i < cols.size(); i++) {
			HeadColumnInfo info = cols.get(i);
			if (info.getName()!=null&&info.getName().equals("单位")) {
				IColumn column = this.tblMain.addColumn();
				column.setKey(info.getId().toString());
			}
			if (info.isIsLeaf() && info.isIsRefPrice()) {
				IColumn column = this.tblMain.addColumn();
				column.setKey(info.getId().toString());
				if (info.getColumnType().equals(ColumnTypeEnum.Amount)) {
					column.getStyleAttributes().setBackground(LOCK_COLOR);
					column.getStyleAttributes().setHorizontalAlign(
							HorizontalAlignment.RIGHT);
					column.getStyleAttributes().setNumberFormat(
							FDCHelper.getNumberFtm(2));
				} else if (info.getColumnType().equals(ColumnTypeEnum.Date)) {
					column.getStyleAttributes().setNumberFormat(
							"yyyy-MM-dd");
				}
				refCols.add(info);
			}
		}
		tblMain.getHeadRow(1).getCell(8).setValue("单位");
		tblMain.getHeadMergeManager().mergeBlock(0, 8, 1, 8);
		int count = 0;
		int baseCount = 9;
		IRow headRow0 = tblMain.getHeadRow(0);
		IRow headRow1 = tblMain.getHeadRow(1);
		for (int j = 0; j < refCols.size(); j++) {
			HeadColumnInfo infoColumn = refCols.get(j);
			headRow1.getCell(j + baseCount).setValue(infoColumn.getName());
			if ((infoColumn.getParent() != null)) {
				headRow0.getCell(j + baseCount).setValue(
						infoColumn.getParent().getName());
				if (j < refCols.size() - 1) {
					HeadColumnInfo infoNext = refCols.get(j + 1);
					if (infoNext.getParent() != null
							&& infoColumn.getParent().equals(
									infoNext.getParent())) {
						count++;
					} else {
						tblMain.getHeadMergeManager().mergeBlock(0,
								j - count + baseCount, 0, j + baseCount);
						count = 0;
					}
				} else {
					tblMain.getHeadMergeManager().mergeBlock(0,
							j - count + baseCount, 0, j + baseCount);
					count = 0;
				}
			} else {
				tblMain.getHeadMergeManager().mergeBlock(0, j + baseCount, 1,
						j + baseCount);
			}
		}
	}

	public void fillMainTable() throws BOSException {
		this.tblMain.removeRows();
		RefPriceCollection refPrices = getFilteredPrices();
		if (refPrices == null || refPrices.size() == 0)
			return;
		Set supplierIds = getSupplierIds(refPrices);
		Map supplierContactInfos = getSupplierContactInfos(supplierIds);
		//Map supplierContractNumInfos = getSupplierContractNumInfos(supplierIds);
		for (int i = 0; i < refPrices.size(); i++) {
			RefPriceInfo refPrice = refPrices.get(i);
			if (!isAccordingFilter(refPrice))
				continue;

			IRow row = this.tblMain.addRow();
			row.setUserObject(refPrice);
			row.getCell("org").setValue(refPrice.getItem().getOrgUnit());
			row.getCell("itemName").setValue(refPrice.getItem().getName());
			row.getCell("date").setValue(refPrice.getDate());
			if (refPrice.getQuotingContent() != null) {
				row.getCell("quotingName").setValue(
						refPrice.getQuotingContent().getSupplier().getName());
				String tempAdd = "";//联系地址
				tempAdd += refPrice.getQuotingContent().getSupplier()
						.getProvince() == null ? "" : refPrice
						.getQuotingContent().getSupplier().getProvince()
						.getName();
				tempAdd += refPrice.getQuotingContent().getSupplier().getCity() == null ? ""
						: refPrice.getQuotingContent().getSupplier().getCity()
								.getName();
				tempAdd += refPrice.getQuotingContent().getSupplier().getAddress() == null ? ""
						: refPrice.getQuotingContent().getSupplier().getAddress();
				row.getCell("address").setValue(tempAdd);
				SupplierCompanyInfoInfo supplierContactInfo = (SupplierCompanyInfoInfo) supplierContactInfos
						.get(refPrice.getQuotingContent().getSupplier().getId()
								.toString());
				if(supplierContactInfo!=null){
					//联系人
					row.getCell("person").setValue(
							supplierContactInfo.getContactPerson() == null ? ""
									: supplierContactInfo.getContactPerson());
					//联系电话
					String phone = "";
					phone += (supplierContactInfo.getPhone() == null ? ""
							: supplierContactInfo.getPhone());
					phone += phone.equals("")?"":"," + (supplierContactInfo.getMobile() == null ? ""
							: supplierContactInfo.getMobile());
					row.getCell("phone").setValue(phone);
				}
//				//合同号
//				row.getCell("contractNumber")
//						.setValue(
//								supplierContractNumInfos.containsKey(refPrice
//										.getQuotingContent().getSupplier()
//										.getId()) ? supplierContractNumInfos
//										.get(refPrice.getQuotingContent()
//												.getSupplier().getId()) : "");
			} else {
				row.getCell("quotingName").setValue("外界导入");
			}
			if (refPrice.getListing() != null) {
				row.getCell("listingName").setValue(
						refPrice.getListing().getName());
			}
			for (int j = 0; j < refPrice.getEntrys().size(); j++) {
				RefPriceEntryInfo entry = refPrice.getEntrys().get(j);
				HeadColumnInfo column = entry.getColumn();
				ColumnTypeEnum colType = column.getColumnType();
				ICell cell = row.getCell(column.getId().toString());
				InviteHelper.loadRefPriceEntry(cell, entry, colType);
			}
		}
		InviteHelper.setAotuHeight(this.tblMain);
	}
	/***
	 * 获取参考价的供应商ids
	 * @param refPrices
	 * @return
	 */
	public static Set getSupplierIds(RefPriceCollection refPrices){
		Set supplierIds = new HashSet();
		for (int i = 0; i < refPrices.size(); i++) {
			RefPriceInfo refPrice = (RefPriceInfo) refPrices.get(i);
			if (refPrice.getQuotingContent() != null)
				supplierIds.add(refPrice.getQuotingContent().getSupplier().getId().toString());
		}
		return supplierIds;
	}
	/***
	 * 获取参考价的供应商ids
	 * @param refPrices
	 * @return
	 */
	public static Set getSupplierIds(RefPriceEntryCollection refPrices){
		Set supplierIds = new HashSet();
		for (int i = 0; i < refPrices.size(); i++) {
			RefPriceInfo refPrice = (RefPriceInfo) refPrices.get(i).getHead();
			if (refPrice.getQuotingContent() != null)
				supplierIds.add(refPrice.getQuotingContent().getSupplier().getId().toString());
		}
		return supplierIds;
	}
	/***
	 * 获取供应商的联系信息，以供应商id作key
	 * @param supplierIds
	 * @return
	 * @throws BOSException
	 */
	public static Map getSupplierContactInfos(Set supplierIds) throws BOSException{
		Map supplierContactInfos = new HashMap();
		if(supplierIds.size()==0)
			return supplierContactInfos;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("contactperson");
		view.getSelector().add("phone");
		view.getSelector().add("mobile");
		view.getSelector().add("supplier.id");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("supplier.id", supplierIds,
						CompareType.INCLUDE));
		SupplierCompanyInfoCollection scics = SupplierCompanyInfoFactory
				.getRemoteInstance().getSupplierCompanyInfoCollection(view);
		for (int i = 0; i < scics.size(); i++) {
			SupplierCompanyInfoInfo scii = scics
					.get(i);
			supplierContactInfos.put(scii.getSupplier().getId().toString(),
					scii);
		}
		return supplierContactInfos;
	}
	/***
	 * 取供应商的合同号信息
	 * @param supplierIds
	 * @return
	 * @throws BOSException
	 */
	public static Map getSupplierContractNumInfos(Set supplierIds) throws BOSException{
		Map supplierContractNumInfos = new HashMap();
		if(supplierIds.size()==0)
			return supplierContractNumInfos;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("number");
		view.getSelector().add("partb.id");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("partb.id", supplierIds,
						CompareType.INCLUDE));
		ContractBillCollection cbc = ContractBillFactory.getRemoteInstance()
				.getContractBillCollection(view);
		for(int i=0;i<cbc.size();i++){
			ContractBillInfo cbInfo = cbc.get(i);
			if(supplierContractNumInfos.containsKey(cbInfo.getPartB().getId())){
				StringBuffer a = new StringBuffer((String)supplierContractNumInfos.get(cbInfo.getPartB().getId()));
				a.append("，");
				a.append(cbInfo.getNumber());
				supplierContractNumInfos.put(cbInfo.getPartB().getId(),a.toString());
			}else{
				supplierContractNumInfos.put(cbInfo.getPartB().getId(),cbInfo.getNumber());
			}
		}
		return supplierContractNumInfos;
	}

	private RefPriceCollection getFilteredPrices() throws BOSException {
		String itemName = null;
		for (int i = 0, n = tblQuery.getRowCount(); i < n; i++) {
			if ("子目名称".equals(tblQuery.getCell(i, 0).getValue())) {
				itemName = (String) this.tblQuery.getRow(i).getCell("value")
						.getValue();
				break;
			}
		}

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("item.*");
		view.getSelector().add("item.orgUnit.*");
		view.getSelector().add("quotingContent.supplier.id");
		view.getSelector().add("quotingContent.supplier.name");
		view.getSelector().add("quotingContent.supplier.province.name");
		view.getSelector().add("quotingContent.supplier.city.name");
		view.getSelector().add("quotingContent.supplier.address");
		view.getSelector().add("listing.name");
		view.getSelector().add("entrys.*");
		view.getSelector().add("entrys.column.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("item.name"));
		filter.getFilterItems().add(
				new FilterItemInfo("item.headType.id", headType.getId()
						.toString()));
		if (!StringUtils.isEmpty(itemName)) {
			filter.getFilterItems().add(
					new FilterItemInfo("item.name", "%" + itemName + "%",
							CompareType.LIKE));
		}

		Date beginDate = (Date) tblQuery.getRow(tblQuery.getRowCount() - 3)
				.getCell("value").getValue();
		if (beginDate != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("date", beginDate,
							CompareType.GREATER_EQUALS));
		}
		Date endDate = (Date) tblQuery.getRow(tblQuery.getRowCount() - 2)
				.getCell("value").getValue();
		if (endDate != null) {
			endDate.setTime(endDate.getTime() + DAYMILLIS);
			filter.getFilterItems().add(
					new FilterItemInfo("date", endDate, CompareType.LESS));
		}

		Object object = tblQuery.getRow(tblQuery.getRowCount() - 1).getCell(
				"value").getValue();
		if (object != null && object instanceof Vector) {
			Vector orgs = (Vector) object;
			Set idSet = new HashSet();
			for (int i = 0; i < orgs.size(); i++) {
				FullOrgUnitInfo company = (FullOrgUnitInfo) orgs.get(i);
				idSet.add(company.getId().toString());
			}
			filter.getFilterItems().add(
					new FilterItemInfo("item.orgUnit.id", idSet,
							CompareType.INCLUDE));
		}
		RefPriceCollection refPrices = RefPriceFactory.getRemoteInstance()
				.getRefPriceCollection(view);
		return refPrices;
	}

	public boolean isAccordingFilter(RefPriceInfo refPrice) {
		Map map = new HashMap();
		for (int i = 0; i < refPrice.getEntrys().size(); i++) {
			RefPriceEntryInfo entry = refPrice.getEntrys().get(i);
			String colId = entry.getColumn().getId().toString();
			map.put(colId, entry);
		}
		for (int i = 1; i < this.tblQuery.getRowCount(); i++) {
			IRow row = this.tblQuery.getRow(i);
			if (row.getUserObject() != null) {
				HeadColumnInfo col = (HeadColumnInfo) row.getUserObject();
				if (row.getCell("value").getValue() != null) {
					if (map.get(col.getId().toString()) == null) {
						return false;
					} else {
						RefPriceEntryInfo entry = (RefPriceEntryInfo) map
								.get(col.getId().toString());
						if (col.getColumnType().equals(ColumnTypeEnum.String)) {
							String value = (String) row.getCell("value")
									.getValue();
							if (entry.getText() == null) {
								return false;
							}
							int j = entry.getText().indexOf(value);
							if (j == -1) {
								return false;
							}
						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 根据id显示窗体
	 */
	public static RefPriceInfo showWindows(IUIObject ui, String headTypeId,
			Map seachMap) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("headTypeId", headTypeId);
		uiContext.put("seachMap", seachMap);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RefPriceSelectUI.class.getName(), uiContext, null,
						"VIEW");
		uiWindow.show();
		RefPriceInfo refPrice = (RefPriceInfo) uiWindow.getUIObject()
				.getUIContext().get("refPrice");
		return refPrice;
	}

	protected void btnQuery_actionPerformed(ActionEvent e) throws Exception {
		super.btnQuery_actionPerformed(e);
		this.fillMainTable();
	}

	protected void btnQuit_actionPerformed(ActionEvent e) throws Exception {
		super.btnQuit_actionPerformed(e);
		this.destroyWindow();
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			if (rowIndex == -1) {
				return;
			}
			IRow row = this.tblMain.getRow(rowIndex);
			RefPriceInfo refPrice = (RefPriceInfo) row.getUserObject();
			this.getUIContext().put("refPrice", refPrice);
			this.destroyWindow();
		}
	}
}