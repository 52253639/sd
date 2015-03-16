/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.fdc.invite.ListingPageSumEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingColumnCollection;
import com.kingdee.eas.fdc.invite.NewListingColumnFactory;
import com.kingdee.eas.fdc.invite.NewListingColumnInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryCollection;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewListingPageCollection;
import com.kingdee.eas.fdc.invite.NewListingPageFactory;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewListingValueFactory;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.PageHeadInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceStatusEnum;
import com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryInfo;
import com.kingdee.eas.fdc.migrate.StringUtils;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.AbstractEditUI;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * 报价导入 编辑界面
 */
public class NewQuotingPriceEditUI extends AbstractNewQuotingPriceEditUI {
	private static final Color LEAF_COLOR = new Color(0xFF, 0xEA, 0x67);

	private static final Color NON_LEAF_COLOR = new Color(0xF0EDD9);

	private static final Color REQUIREDCOLOR = new Color(0, true);

	private static final Logger logger = CoreUIObject
			.getLogger(NewQuotingPriceEditUI.class);


	// begin xuisan 
	Map refPriceBakMap = new HashMap();
	HashMap listingColumnObjectMap = new HashMap();
	// end xuisan 
	
	
	NewListingInfo listing = null;

	/**
	 * 报价类型，可以为：原始报价、修正报价、历史报价
	 */
	private QuotingPriceTypeEnum priceTypeEnum = null;

	boolean isModify = false;
	
	private static boolean isRate = false;

	Map rowMap = new HashMap();
	Map columnMap = new HashMap();
	public boolean isModify() {
		return isModify;
	}

	/**
	 * output class constructor
	 */
	public NewQuotingPriceEditUI() throws Exception {
		super();
	}

	public void onShow() throws Exception {
		super.onShow();
		this.f7Bidder.requestFocus();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		if(this.f7Bidder.getValue()!=null&&this.f7Bidder.getValue() instanceof SupplierQualifyEntryInfo)
		{
			SupplierQualifyEntryInfo entryInfo = (SupplierQualifyEntryInfo)this.f7Bidder.getValue();
//			this.editData.setSupplier(entryInfo.getSupplier());
		}
		if(this.f7Bidder.getValue()!=null&&this.f7Bidder.getValue() instanceof SupplierInfo)
		{
			SupplierInfo sInfo = (SupplierInfo)this.f7Bidder.getValue();
			this.editData.setSupplier(sInfo);
		}
		QuotingPriceStatusEnum status = null;
		if (priceTypeEnum != null) {
			NewQuotingPriceInfo dbQuoting = null;
			try {
				dbQuoting = NewQuotingPriceFactory.getRemoteInstance()
						.getNewQuotingPriceInfo(
								new ObjectUuidPK(this.editData.getId()));
				status = dbQuoting.getStatus();
			} catch (EASBizException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}
		status = (status == null) ? QuotingPriceStatusEnum.NoImportPrice
				: status;

		if (status.equals(QuotingPriceStatusEnum.NoImportPrice)
				|| priceTypeEnum.equals(QuotingPriceTypeEnum.modify)) {
			if (tabbedPnlTable.getTabCount() > 2) {
				KDTable tbl = (KDTable) tabbedPnlTable.getComponentAt(1);
				BigDecimal value = (BigDecimal) tbl.getCell(
						tbl.getRowCount() - 1, "amount").getValue();
				this.editData.setTotoalPrice(value);
			}
		}
		this.initOldData(this.editData);
	}

	protected IObjectValue createNewData() {
		NewQuotingPriceInfo quoting = new NewQuotingPriceInfo();
		quoting.setId(BOSUuid.create(quoting.getBOSType()));
		String inviteListingId = (String) getUIContext().get("inviteListingId");
		try {
			quoting.setListing(NewListingFactory.getRemoteInstance()
					.getNewListingInfo(new ObjectUuidPK(inviteListingId)));
		} catch (EASBizException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		quoting.setStatus(QuotingPriceStatusEnum.NoImportPrice);
		quoting.setTotoalPrice(new BigDecimal("0"));
		return quoting;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return NewQuotingPriceFactory.getRemoteInstance();
	}

	public boolean destroyWindow() {
		boolean b = super.destroyWindow();
		// 退出时添加刷新 ListUI的函数调用．
		if (this.isSaved() && getUIContext().get(UIContext.OWNER) != null) {
			QuotingPriceListUI listUI = (QuotingPriceListUI) getUIContext()
					.get(UIContext.OWNER);
			listUI.refreshQuotingPriceListByInviteListingRow();
		}
		return b;
	}

	public void loadFields() {
		super.loadFields();
		this.f7Bidder.setValue(this.editData.getSupplier());
		if (this.editData.getSupplier() != null) {
			this.txtBidderNumber.setText(this.editData.getSupplier()
					.getNumber());
		}
		// 修改时不能改投标人
		if (AbstractEditUI.STATUS_EDIT.equals(getOprtState())) {
			f7Bidder.setEnabled(false);
		}
		this.storeFields();
	}

	public void onLoad() throws Exception {
		if (this.getOprtState().equals("EDIT1")) {
			this.setOprtState("EDIT");
		}
		if (this.getOprtState().equals("VIEW1")) {
			this.setOprtState("VIEW");
		}
		super.onLoad();
		String inviteListingId = (String) getUIContext().get("inviteListingId");
		listing = getInviteListing(inviteListingId);
		if(listing.getInviteProject()!=null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("supplier.name");
			sic.add("supplier.number");
			sic.add("supplier.id");
			sic.add("parent.*");
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.inviteProject.id",listing.getInviteProject().getId().toString()));
			view.setFilter(filter);
			this.f7Bidder.setEntityViewInfo(view);
			this.f7Bidder.setSelectorCollection(sic);
		}
		else{
			//FDCMsgBox.showError(this,"改清单没有指定招标立项");
			FDCClientUtils.initSupplierF7(this, this.f7Bidder);
		}
		this.f7Bidder.addChangeListener(new SupplierF7CommitListener(
				this.txtBidderNumber));
		
		if (getUIContext().get("quotingPriceType") != null) {
			priceTypeEnum = (QuotingPriceTypeEnum) getUIContext().get(
					"quotingPriceType");
		}
		this.actionExportErrorInfo.setEnabled(false);
		this.menuImportPrice.setIcon(this.btnImportQuotingExcel.getIcon());
		this.exportMenuItem.setIcon(this.exportButton.getIcon());
		if (priceTypeEnum != null) {
			this.windowTitle = this.windowTitle + "["
					+ priceTypeEnum.getAlias() + "]";
			this.btnImportQuotingExcel.setText("导入" + priceTypeEnum.getAlias());
			this.menuImportPrice.setText("导入" + priceTypeEnum.getAlias());
		}else{
			this.actionImportQuotingExcel.setVisible(false);
			this.actionExportQuotingExcel.setVisible(false);
			this.menuBiz.setVisible(false);
		}
		this.actionExportErrorInfo.setVisible(false);
		this.actionSave.setVisible(false);
		// enable=false才能禁用快捷键
		actionSave.setEnabled(false);
		actionAddNew.setEnabled(false);
		actionRemove.setEnabled(false);

		this.actionSubmit.setVisible(true);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				loadPages(listing);
			}
		});
		this.btnImportQuotingExcel.getAction().setEnabled(
				OprtState.EDIT.equals(oprtState)
						|| OprtState.ADDNEW.equals(oprtState));
		this.exportMenuItem.getAction().setEnabled(
				OprtState.EDIT.equals(oprtState)
						|| OprtState.ADDNEW.equals(oprtState));
		
		// 隐藏导出报价
		this.actionExportQuotingExcel.setVisible(false);
		
		// 非叶子节点合计重新计算
		for (int i = 0; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) this.tabbedPnlTable.getComponentAt(i);
			//父节点行
			ArrayList isLeafListNo = new ArrayList();
			for (int k = 1; k < table.getRowCount(); k++) {
				BigDecimal sum = FMConstants.ZERO;
				IRow row = table.getRow(k);
				BigDecimal rowLevel = FDCHelper.toBigDecimal(row.getCell(0).getValue());
				NewListingEntryInfo info = new NewListingEntryInfo();
//				if (info != null && !info.isIsLeaf()) {
				if(!checkLeafRow(table, k)){
					row.getCell(4).setValue(null);
					row.getCell(5).setValue(null);
//					row.getCell(6).setValue(null);
					info.setIsLeaf(false);
					
					isLeafListNo.add(String.valueOf(k));
					
					for (int m = k + 1; m < table.getRowCount(); m++) {
						IRow rowNext = table.getRow(m);
						NewListingEntryInfo nextInfo = (NewListingEntryInfo) rowNext.getUserObject();
//						if (nextInfo != null && nextInfo.isIsLeaf()) {
						if(checkLeafRow(table, m)){
							BigDecimal rowLevelNext = FDCHelper.toBigDecimal(rowNext.getCell(0).getValue());
							BigDecimal value = FDCHelper.toBigDecimal(rowNext.getCell(7).getValue());
							if(rowLevelNext.floatValue() > rowLevel.floatValue()){
								sum = sum.add(FDCHelper.toBigDecimal(value));
							}
						}
					}
//					row.getCell(7).setValue(sum);
				}else{
					info.setIsLeaf(true);
				}
				row.setUserObject(info);
			}
		}
	}
	
	public static boolean checkLeafRow(KDTable table, int rowIndex) {
		if (rowIndex + 1 == table.getRowCount()) {
			return true;
		}
		BigDecimal rowLevel = FDCHelper.toBigDecimal(table.getRow(rowIndex).getCell(0).getValue());
		BigDecimal nextLevel = FDCHelper.toBigDecimal(table.getRow(rowIndex + 1).getCell(0).getValue());
		if (nextLevel.floatValue() <= rowLevel.floatValue()) {
			return true;
		}
		return false;
	}

	/**
	 * 获取招标清单
	 * @param inviteListingId 招标清单ID，从QuotingPriceListUI.java通过UIContent传过来
	 * @return 招标清单
	 * @throws BOSException
	 */
	public static NewListingInfo getInviteListing(String inviteListingId) throws BOSException {
		
		NewListingInfo listing = new NewListingInfo();
		SelectorItemCollection sicInviteProject = new SelectorItemCollection();
		sicInviteProject.add(new SelectorItemInfo("*"));
		sicInviteProject.add(new SelectorItemInfo("inviteProject.isRate"));
		try {
			listing = NewListingFactory.getRemoteInstance().getNewListingInfo(new ObjectUuidPK(BOSUuid.read(inviteListingId)),sicInviteProject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(listing.getInviteProject() != null){
			isRate = listing.getInviteProject().isIsRate();
		}
				
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", listing.getId().toString()));
		SelectorItemCollection sic = view.getSelector();
		sic.add("*");
		sic.add("pageHead.*");
		sic.add("headType.*");
		sic.add("columns.*");
		sic.add("entrys.*");
		view.getSorter().add(new SorterItemInfo("seq"));
		NewListingPageCollection sheets = NewListingPageFactory
				.getRemoteInstance().getNewListingPageCollection(view);
		listing.getPages().clear();
		listing.getPages().addCollection(sheets);
		for (int i = 0; i < listing.getPages().size(); i++) {
			NewListingPageInfo sheet = listing.getPages().get(i);
			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("page.id", sheet.getId().toString()));
			sic = view.getSelector();
			sic.add("*");
			sic.add("headColumn.*");
			sic.add("headColumn.parent.*");
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingColumnCollection columns = null;
			columns = NewListingColumnFactory.getRemoteInstance()
					.getNewListingColumnCollection(view);
			sheet.getColumns().clear();
			sheet.getColumns().addCollection(columns);

			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("item.*");
			view.getSelector().add("values.*");
			//页签表头类型定义isDefined一定要select，
			//否则即便sheet中有值，但newListingEntry.getHead().getHeadType().isIsDefined()还是取不到值
			view.getSelector().add("head.headType.isDefined"); 
			filter.getFilterItems().add(new FilterItemInfo("head.id", sheet.getId().toString()));
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingEntryCollection entrys = null;
			entrys = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(view);
			sheet.getEntrys().clear();
			sheet.getEntrys().addCollection(entrys);
		}
		return listing;
	}

	/**
	 * 描述：载入页签及页签数据
	 * @param newListingInfo 招标清单
	 */
	private void loadPages(NewListingInfo newListingInfo) {
		tabbedPnlTable.removeAll();
		for (int page_i = 0; page_i < newListingInfo.getPages().size(); page_i++) {
			NewListingPageInfo page = (NewListingPageInfo) newListingInfo.getPages().get(page_i);
			PageHeadInfo pageHead = page.getPageHead();
			KDTable table = new KDTable();
			table.setName(pageHead.getName());
			initTable(table, page);
			String id = null;
			if (this.editData.getId() != null) {
				id = this.editData.getId().toString();
			}
			loadTableData(table, page, id,priceTypeEnum);
			setUnionData(table,tabbedPnlTable);
			
			hideSupplierDefinedRow(page, table);
			
			setTableLock(table, true);
			this.tabbedPnlTable.add(table, pageHead.getName());
		}
		initTotalPageTable(tabbedPnlTable,listing);
		initPageDesTable(tabbedPnlTable);
		InviteHelper.setAotuHeight(this.tabbedPnlTable);
		tHelper.setCanMoveColumn(false);
	}

	/**
	 * 
	 * 描述：隐藏非本次供应商自定义的某些行，对于非本次供应商的数据必须要隐藏，
	 * 否则本供应商能看到其它供应商的报价信息是非常严重的问题
	 * @Author：owen_wen
	 * @param page 页签
	 * @param table 表格
	 * @CreateTime：2011-11-1
	 */
	private void hideSupplierDefinedRow(NewListingPageInfo page, KDTable table) {
		if (page != null) {
			int projectAmtIndex = 0;
			int projectAmtSum = 0;	
			int totalPriceIndex = 0;
			int totalPriceSum = 0;
			int amountIndex = 0;
			
			if("工程量".equals(table.getColumn(5).getKey())){
				projectAmtIndex = 5;
				projectAmtSum = 5;
			}else{
				projectAmtIndex = 5;
				for(int j = projectAmtIndex; j < table.getColumnCount(); j++){
					if("小计".equals(table.getColumn(j).getKey())){
						projectAmtSum = j;
						break;
					}
				}
			}
			
			if("综合单价".equals(table.getColumn(projectAmtSum + 1).getKey())){
				totalPriceIndex = projectAmtSum + 1;
				totalPriceSum = projectAmtSum + 1;
			}else{
				totalPriceIndex = projectAmtSum + 1;
				for(int j = totalPriceIndex; j < table.getColumnCount(); j++){
					if("小计".equals(table.getColumn(j).getKey())){
						totalPriceSum = j;
						break;
					}
				}
			}
			
			if("金额".equals(table.getColumn(totalPriceSum + 1).getKey())){
				amountIndex = totalPriceSum + 1;
			}else{
				amountIndex = totalPriceSum + 1;
				for(int j = amountIndex; j < table.getColumnCount(); j++){
					if("小计".equals(table.getColumn(j).getKey())){
						break;
					}
				}
			}
			
			for (int i = 0; i < page.getEntrys().size(); i++) {
				NewListingEntryInfo pageEntryInfo = page.getEntrys().get(i);
				// 非供应商自定义，则不需要隐藏子目行
				if(pageEntryInfo.getHead() != null && pageEntryInfo.getHead().getHeadType() != null){
					if(!pageEntryInfo.getHead().getHeadType().isIsDefined()){
						continue;
					}
				}
				NewListingValueInfo amountSumNewListingValueInfo = pageEntryInfo.getValues().get(totalPriceSum - 3);
				String columnnIdProjectAmt = null;
				if(amountSumNewListingValueInfo != null && amountSumNewListingValueInfo.getColumn() != null && amountSumNewListingValueInfo.getColumn().getId() != null){
					columnnIdProjectAmt = amountSumNewListingValueInfo.getColumn().getId().toString();
				}
				try {
					if(columnnIdProjectAmt != null){
						if (getUITitle().toString().contains("修正")) {
							// 不是本次的修正报价 ， 则需要隐藏
							if (!NewListingValueFactory.getRemoteInstance().exists(
									"where entry.id = '" + pageEntryInfo.getString("id") + "'" + " and quotingPrice.id = '" + editData.getString("id") + "'" + "and type = 1")) {
								if (table.getRow(i + 1) != null) {
									 table.getRow(i + 1).getStyleAttributes().setHided(true);
								}
							}
						} else { // 如果不是本次的原始报价，则隐藏
							if (!NewListingValueFactory.getRemoteInstance().exists(
									"where entry.id = '" + pageEntryInfo.getString("id") + "'" + " and quotingPrice.id = '" + editData.getString("id") + "'" + "and type = 0")) {
								if (table.getRow(i + 1) != null) {
									table.getRow(i + 1).getStyleAttributes().setHided(true);
								}
							}
						}
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void initPageDesTable(final KDTabbedPane tabbedPnlTable) {
		KDTable table = new KDTable();
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		table.setName("招标说明");
		IRow row = table.addHeadRow();
		table.addColumns(2);
		table.getColumn(0).setKey("name");
		table.getColumn(1).setWidth(300);
		table.getColumn(0).getStyleAttributes().setLocked(true);
		table.getColumn(1).setKey("des");
		table.getColumn(1).setWidth(400);
		table.getColumn(1).getStyleAttributes().setWrapText(true);
		table.getColumn(1).getStyleAttributes().setLocked(true);
		KDTextField txtField = new KDTextField();
		txtField.setMaxLength(200);
		ICellEditor editor = new KDTDefaultCellEditor(txtField);
		table.getColumn("des").setEditor(editor);
		row.getCell(0).setValue("页签名称");
		row.getCell(1).setValue("描述");

		int pageCount = tabbedPnlTable.getTabCount();
		table.addRows(pageCount - 1);
		for (int i = 0; i < pageCount - 1; i++) {
			KDTable aTable = (KDTable) tabbedPnlTable.getComponentAt(i + 1);
			NewListingPageInfo page = (NewListingPageInfo) aTable
					.getUserObject();
			table.getCell(i, "name").setValue(page.getPageHead().getName());
			table.getCell(i, "des").setValue(page.getDescription());
		}
		tabbedPnlTable.add(table, 0);
		updateTotalPageData(tabbedPnlTable);
	}

	public static void initTotalPageTable(final KDTabbedPane tabbedPnlTable,NewListingInfo listing) {
		KDTable table = new KDTable();
		table.getStyleAttributes().setWrapText(true);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		table.setName("报价汇总表");
		table.addHeadRows(2);
		IRow row = table.getHeadRow(1);
		table.addColumns(3);
		table.getColumn(0).setKey("number");
		table.getColumn(0).getStyleAttributes().setLocked(true);
		table.getColumn(1).setKey("name");
		table.getColumn(1).getStyleAttributes().setLocked(true);
		table.getColumn(2).setKey("amount");
		row.getCell(0).setValue("序号");
		row.getCell(1).setValue("项目");
		row.getCell(2).setValue("金额");
		table.getHeadRow(0).getCell(0).setValue("报价汇总表");
		table.getHeadMergeManager().mergeBlock(0, 0, 0, 2);
		int pageCount = tabbedPnlTable.getTabCount();
		table.addRows(pageCount + 4);
		for (int i = 0; i < pageCount; i++) {
			table.getCell(i, "number").setValue(new Integer(i + 1));
			table.getCell(i, "name").setValue(tabbedPnlTable.getTitleAt(i));
			table.getCell(i, "amount").getStyleAttributes().setLocked(true);
		}
		table.getCell(pageCount, "name").setValue("小计");
		table.getCell(pageCount, "amount").getStyleAttributes().setLocked(true);
		table.getCell(pageCount + 1, "name").setValue("税率(%)");
		table.getCell(pageCount + 1, "amount").setValue(listing.getTax());
		table.getCell(pageCount + 1, "amount").getStyleAttributes().setLocked(
				true);
		table.getCell(pageCount + 2, "name").setValue("税金");
		table.getCell(pageCount + 2, "amount").getStyleAttributes().setLocked(
				true);
		table.getCell(pageCount + 3, "name").setValue("总造价");
		table.getCell(pageCount + 3, "amount").getStyleAttributes().setLocked(
				true);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setPrecision(2);
		formattedTextField.setRemoveingZeroInDispaly(false);
		formattedTextField.setRemoveingZeroInEdit(false);
		formattedTextField.setNegatived(false);
		formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE);
		formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn(2).setEditor(numberEditor);
		FDCHelper.formatTableNumber(table, "amount");
		tabbedPnlTable.add(table, 0);
		updateTotalPageData(tabbedPnlTable);
	}

	/**
	 * 初始化表格，包括表列和表数据
	 */
	private void initTable(final KDTable table, NewListingPageInfo page) {
		table.getStyleAttributes().setWrapText(true);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		tHelper.setCanSetTable(true);
		table.setUserObject(page);
		setTableEvent(table);
		setTableColumn(table, page);
		setTableRow(table, page, false);
		table.getTreeColumn().setDepth(getMaxLevel(table));
	}

	private static void updateTotalPageData(final KDTabbedPane tabbedPnlTable) {
		if (tabbedPnlTable.getTabCount() <= 1) {
			return;
		}
		KDTable table = (KDTable) tabbedPnlTable.getComponentAt(1);
		if (!table.getName().equals("报价汇总表")) {
			return;
		}
		int pageCount = tabbedPnlTable.getTabCount() - 2;
		BigDecimal sum = FDCHelper.ZERO;
		for (int i = 2; i < pageCount + 2; i++) {
			KDTable page = (KDTable) tabbedPnlTable.getComponentAt(i);
			for (int j = 3; j < page.getColumnCount(); j++) {
				IColumn col = page.getColumn(j);
				NewListingColumnInfo colInfo = (NewListingColumnInfo) col.getUserObject();
				if (colInfo != null) {
					DescriptionEnum property = colInfo.getHeadColumn().getProperty();
					if (property.equals(DescriptionEnum.AmountSum)) {
						BigDecimal value = (BigDecimal) page.getCell(0, j).getValue();
						table.getCell(i - 2, 2).setValue(value);
						if (value != null) {
							sum = sum.add(value);
						}
						break;
					}
				}
			}
		}
		table.getCell(pageCount, "amount").setValue(sum);
		BigDecimal scale = FDCHelper.ZERO;
		if (table.getCell(pageCount + 1, "amount").getValue() != null) {
			scale = (BigDecimal) table.getCell(pageCount + 1, "amount")
					.getValue();
		}
		sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal amount = sum.multiply(scale).divide(FDCHelper.ONE_HUNDRED,
				BigDecimal.ROUND_HALF_UP);
		table.getCell(pageCount + 2, "amount").setValue(amount);
		table.getCell(pageCount + 3, "amount").setValue(sum.add(amount));
	}

	private static void setTableScript(KDTable table) {
		int rowCount = table.getRowCount();
		int amountIndex = 0;
		int amountSum = 0;
		int projectAmtIndex = 0;
		int projectAmtSum = 0;
		int totalPriceIndex = 0;
		int totalPriceSum = 0;
		for (int i = 3, colCount = table.getColumnCount(); i < colCount; i++) {
			IColumn col = table.getColumn(i);
			if (col.getUserObject() != null
					&& col.getUserObject() instanceof NewListingColumnInfo) {
				NewListingColumnInfo column = (NewListingColumnInfo) col
						.getUserObject();
				DescriptionEnum property = column.getHeadColumn().getProperty();
				if (property.equals(DescriptionEnum.ProjectAmt)) {
					if (projectAmtIndex == 0) {
						projectAmtIndex = i;
					}
				} else if (property.equals(DescriptionEnum.ProjectAmtSum)) {
					projectAmtSum = i;
					if (projectAmtIndex == 0) {
						projectAmtIndex = i;
					}
				} else if (property.equals(DescriptionEnum.TotalPrice)) {
					if (totalPriceIndex == 0) {
						totalPriceIndex = i;
					}
				} else if (property.equals(DescriptionEnum.TotalPriceSum)) {
					totalPriceSum = i;
					if (totalPriceIndex == 0) {
						totalPriceIndex = i;
					}
				} else if (property.equals(DescriptionEnum.Amount)) {
					if (amountIndex == 0) {
						amountIndex = i;
					}
				} else if (property.equals(DescriptionEnum.AmountSum)) {
					amountSum = i;
					if (amountIndex == 0) {
						amountIndex = i;
					}
				}
			}
		}
		for (int i = 1; i < rowCount; i++) {
			if (isLeafRow(table, i)) {
				if (projectAmtIndex < projectAmtSum) {
					TableScriptHelper.setTableRowSumFormalu(table.getCell(i,
							projectAmtSum), projectAmtSum - projectAmtIndex);
				}
				if (totalPriceIndex < totalPriceSum) {
					if (table.getCell(i, "isCompose").getValue() != null) {
						boolean isCompose = ((Boolean) table.getCell(i,
								"isCompose").getValue()).booleanValue();
						if (isCompose) {
							TableScriptHelper.setTableRowSumFormalu(table
									.getCell(i, totalPriceSum), totalPriceSum
									- totalPriceIndex);
						}
					}
				}
				for (int j = 0; j <= projectAmtSum - projectAmtIndex; j++) {
					TableScriptHelper.setTableMultiplyFormalu(table.getCell(i,
							amountIndex + j), table.getCell(i, projectAmtIndex
							+ j), table.getCell(i, totalPriceSum));
				}
			}
		}
	}

	public static void setTableLock(final KDTable table, boolean isLock) {
		// if(true)return;
		int priceCount = 0;
		for (int i = 3, colCount = table.getColumnCount(); i < colCount; i++) {
			IColumn col = table.getColumn(i);
			NewListingColumnInfo colInfo = (NewListingColumnInfo) col
					.getUserObject();
			DescriptionEnum property = colInfo.getHeadColumn().getProperty();
			if (property.equals(DescriptionEnum.TotalPrice)) {
				priceCount++;
			}
		}

		for (int i = 1; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			NewListingEntryInfo entry = (NewListingEntryInfo) row
					.getUserObject();
			for (int j = 0; j < table.getColumnCount(); j++) {
				IColumn column = table.getColumn(j);
				ICell cell = row.getCell(j);
				cell.getStyleAttributes().setLocked(true);
				NewListingColumnInfo colInfo = (NewListingColumnInfo) column
						.getUserObject();
				if (colInfo == null) {
					continue;
				}
				DescriptionEnum property = colInfo.getHeadColumn()
						.getProperty();
				if (property.equals(DescriptionEnum.TotalPrice)) {
					if (entry.isIsCompose()) {
						cell.getStyleAttributes().setLocked(isLock);
					} else {
						cell.getStyleAttributes()
								.setBackground(Color.lightGray);
					}
				} else if (property.equals(DescriptionEnum.TotalPriceSum)) {
					if (!entry.isIsCompose() || priceCount == 0) {
						cell.getStyleAttributes().setLocked(isLock);
					}
				} else if (colInfo.isIsQuoting()) {
					cell.getStyleAttributes().setLocked(isLock);
				}
			}
		}
		for (int i = 1; i < table.getRowCount(); i++) {
			if (!isLeafRow(table, i)) {
				table.getRow(i).getCell(2).setValue(null);
				table.getRow(i).getStyleAttributes().setBackground(
						NON_LEAF_COLOR);
			}
		}
	}

	private void setTableEvent(final KDTable table) {
		table.addKDTEditListener(new KDTEditAdapter() {
			// 编辑结束后
			public void editStopped(KDTEditEvent e) {
				// IRow row = table.getRow(e.getRowIndex());
				IColumn col = table.getColumn(e.getColIndex());
				if (col.getUserObject() != null
						&& col.getUserObject() instanceof NewListingColumnInfo) {
					NewListingColumnInfo colInfo = (NewListingColumnInfo) col
							.getUserObject();
					DescriptionEnum property = colInfo.getHeadColumn()
							.getProperty();
					if (property.equals(DescriptionEnum.TotalPrice)
							|| property.equals(DescriptionEnum.TotalPriceSum)) {
						setUnionData(table,tabbedPnlTable);
					}
				}
			}
		});
	}

	public static void setTableColumn(final KDTable table, NewListingPageInfo page) {
		NewListingColumnCollection collColumn = page.getColumns();
		int sizeColumn = collColumn.size();
		if (sizeColumn > 0) {
			table.addHeadRows(2);
			IRow row = table.getHeadRow(1);
			IRow parentRow = table.getHeadRow(0);
			IColumn col = table.addColumn(0);
			col.setKey("level");
			col.setWidth(30);
			row.getCell("level").setValue("级次");
			col.getStyleAttributes().setLocked(true);
			IColumn colIsKey = table.addColumn(1);
			colIsKey.setKey("isKey");
			colIsKey.setWidth(60);
			row.getCell("isKey").setValue("关键子目");
			colIsKey.getStyleAttributes().setLocked(true);
			IColumn colIsCompose = table.addColumn(2);
			colIsCompose.setKey("isCompose");
			colIsCompose.setWidth(60);
			row.getCell("isCompose").setValue("单价构成");
			colIsCompose.getStyleAttributes().setLocked(true);
			if(page != null && page.getHeadType() != null){
				if(page.getHeadType().isIsDefined()){
					table.getColumn(1).getStyleAttributes().setHided(true);
					table.getColumn(2).getStyleAttributes().setHided(true);
				}
			}
			int count = 0;
			KDTMergeManager mm = table.getHeadMergeManager();
			mm.mergeBlock(0, 0, 1, 0);
			mm.mergeBlock(0, 1, 1, 1);
			mm.mergeBlock(0, 2, 1, 2);
			int baseCount = 3;
			for (int j = 0; j < sizeColumn; j++) {
				NewListingColumnInfo listColInfo = collColumn.get(j);
				HeadColumnInfo infoColumn = listColInfo.getHeadColumn();
				IColumn column = table.addColumn();
				column.setKey(infoColumn.getName());
				column.setUserObject(listColInfo);
				row.getCell(j + baseCount).setValue(infoColumn.getName());
				if (infoColumn.getColumnType().equals(ColumnTypeEnum.Amount)) {
					KDFormattedTextField formattedTextField = new KDFormattedTextField(
							KDFormattedTextField.BIGDECIMAL_TYPE);
					
					formattedTextField.setRemoveingZeroInDispaly(false);
					formattedTextField.setRemoveingZeroInEdit(false);
					formattedTextField.setNegatived(false);
					formattedTextField.setSupportedEmpty(true);
					formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE);
					formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
					formattedTextField
							.setHorizontalAlignment(KDFormattedTextField.RIGHT);
					ICellEditor numberEditor = new KDTDefaultCellEditor(
							formattedTextField);
					if(isRate && infoColumn.isIsQuoting()){
						formattedTextField.setPrecision(4);
						column.getStyleAttributes().setNumberFormat(
								"#,##0.0000;-#,##0.0000");
					}else{
						formattedTextField.setPrecision(2);
						column.getStyleAttributes().setNumberFormat(
								"#,##0.00;-#,##0.00");
					}
					column.setEditor(numberEditor);
					
					column.getStyleAttributes().setHorizontalAlign(
							HorizontalAlignment.RIGHT);
					// FDCHelper.formatTableNumber(table, infoColumn.getName());
				} else if (infoColumn.getColumnType().equals(
						ColumnTypeEnum.Date)) {
					//加入一个日期控件 2008-05-28 周勇
					com.kingdee.bos.ctrl.swing.KDDatePicker pkauditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
					ICellEditor dateEditor = new KDTDefaultCellEditor(pkauditDate);
					column.setEditor(dateEditor);
					column.getStyleAttributes().setNumberFormat(
							"yyyy-m-d");
				} else if (infoColumn.getColumnType().equals(
						ColumnTypeEnum.String)) {
					column.getStyleAttributes().setWrapText(true);
				}
				if ((infoColumn.getParent() != null)) {
					parentRow.getCell(j + baseCount).setValue(
							infoColumn.getParent().getName());
					if (j < sizeColumn - 1) {
						HeadColumnInfo infoNext = collColumn.get(j + 1)
								.getHeadColumn();
						if (infoNext.getParent() != null
								&& infoColumn.getParent().equals(
										infoNext.getParent())) {
							count++;
						} else {
							if (count == 0) {
								mm.mergeBlock(0, j + baseCount, 1, j
										+ baseCount,
										KDTMergeManager.SPECIFY_MERGE);
							} else {
								mm
										.mergeBlock(0, j - count + baseCount,
												0, j + baseCount,
												KDTMergeManager.DATA_UNIFY);
								count = 0;
							}
						}
					} else {
						mm.mergeBlock(0, j - count + baseCount, 0, j
								+ baseCount, KDTMergeManager.DATA_UNIFY);
						count = 0;
					}
				} else {
					mm.mergeBlock(0, j + baseCount, 1, j + baseCount);
				}
			}
		}
		table.getColumn("子目名称").setWidth(400);
	}
	static class myF7 extends KDBizPromptBox{
		protected Object stringToValue(String t) {
			Object obj = super.stringToValue(t);
			if (obj instanceof IObjectValue) {
				return obj;
			} else {
				return t;
			}
		}
	}

	/**
	 * 
	 * 描述：设置表格行，只包含表格行的子目名称，像综合单价，工程量等未导入数据。
	 * @param isExporting 是否往外导出数据
	 * @Author：owen_wen
	 * @CreateTime：2011-10-27
	 */
	public static void setTableRow(final KDTable table, NewListingPageInfo page, boolean isExporting) {
		NewListingEntryCollection collEntry = page.getEntrys();
		NewListingEntryInfo infoEntry = null;
		int sizeEntry = collEntry.size();
		IRow totalRow = table.addRow();
		totalRow.getCell(3).setValue("合计");
		totalRow.getStyleAttributes().setBackground(Color.lightGray);
		totalRow.getStyleAttributes().setLocked(true);
		// 如果是供应商自定义页签，往外导出时，不需要导出数据
		if (page.getHeadType().isIsDefined() && isExporting) {
			return;
		}
		for (int k = 0; k < sizeEntry; k++) {
			IRow dataRow = table.addRow();
			infoEntry = collEntry.get(k);
			dataRow.setUserObject(infoEntry);
			if (infoEntry.getItem() != null) {
				dataRow.getCell("子目名称").setValue(infoEntry.getItem());
			} else {
				dataRow.getCell("子目名称").setValue(infoEntry.getItemName());
			}
			KDBizPromptBox f7 = new myF7();
			f7.setDisplayFormat("$name$");
			f7.setCommitFormat("$name$");
			f7.setEditFormat("$name$");
			f7.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7ListingItemQuery");
			EntityViewInfo evInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection filterItems = filter.getFilterItems();
			filterItems.add(new FilterItemInfo("headType.id", page.getHeadType().getId().toString(), CompareType.EQUALS));
			evInfo.setFilter(filter);
			f7.setEntityViewInfo(evInfo);
			f7.setEditable(true);
			CellTextRenderImpl avr = new CellTextRenderImpl();
			avr.getText(new BizDataFormat("$name$"));
			dataRow.getCell("子目名称").setEditor(new KDTDefaultCellEditor(f7));
			dataRow.getCell("子目名称").setRenderer(avr);
			dataRow.getCell("level").setValue(new Integer(infoEntry.getLevel()));
			dataRow.getCell("isKey").setValue(Boolean.valueOf(infoEntry.isIsKey()));
			dataRow.getCell("isCompose").setValue(Boolean.valueOf(infoEntry.isIsCompose()));
			dataRow.setTreeLevel(infoEntry.getLevel());
		}
	}

	public static int getMaxLevel(KDTable table) {
		int levelmax = 0;
		for (int i = 1; i < table.getRowCount(); i++) {
			int thisLevel = Integer.parseInt(table.getCell(i, "level").getValue().toString());
			if (thisLevel > levelmax) {
				levelmax = thisLevel;
			}
		}
		return levelmax;
	}

	class SupplierF7CommitListener implements ChangeListener {
		KDTextField kDTextNumber;

		public SupplierF7CommitListener(KDTextField kDTextNumber) {
			this.kDTextNumber = kDTextNumber;
		}

		public void stateChanged(ChangeEvent arg0) {
			KDBizPromptBox box = (KDBizPromptBox) arg0.getSource();
			if (box.getData() != null) {
				if(box.getData() instanceof SupplierQualifyEntryInfo){
					SupplierQualifyEntryInfo info = (SupplierQualifyEntryInfo) box.getData();
					kDTextNumber.setText(info.getSupplier().getNumber());
				}else if(box.getData() instanceof SupplierInfo){
					SupplierInfo info = (SupplierInfo) box.getData();
					kDTextNumber.setText(info.getName());
				}
				
			} else {
				kDTextNumber.setText(null);
			}
		}

	}

	/**
	 * 描述：往表中导入数据，不包含子目名称，但包含综合单价，工程量等列
	 */
	public static void loadTableData(KDTable table, NewListingPageInfo page,
			String quotingId,QuotingPriceTypeEnum priceTypeEnum) {
		Map values = new HashMap();
		NewListingValueCollection values1 = null;
		EntityViewInfo view1 = new EntityViewInfo();
		FilterInfo filter1 = new FilterInfo();
		filter1.appendFilterItem("entry.head", page.getId().toString()); // 该页签page下的NewListingValue
		filter1.appendFilterItem("column.isQuoting", Boolean.FALSE); // 非报价列
		view1.setFilter(filter1);
		view1.getSelector().add(new SelectorItemInfo("*"));
		view1.getSelector().add(new SelectorItemInfo("entry.*"));
		view1.getSelector().add(new SelectorItemInfo("column.*"));

		NewListingValueCollection values2 = null;
		EntityViewInfo view2 = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		filter2.appendFilterItem("entry.head", page.getId().toString());
		filter2.appendFilterItem("quotingPrice.id", quotingId); // 按报价id过滤的NewListingValue
		if (QuotingPriceTypeEnum.modify.equals(priceTypeEnum)) {// 按修改或原始报价过滤
			filter2.appendFilterItem("type", QuotingPriceTypeEnum.modify);
		} else if (QuotingPriceTypeEnum.original.equals(priceTypeEnum)) {
			filter2.appendFilterItem("type", QuotingPriceTypeEnum.original);
		}
		view2.setFilter(filter2);
		view2.getSelector().add(new SelectorItemInfo("*"));
		view2.getSelector().add(new SelectorItemInfo("entry.*"));
		view2.getSelector().add(new SelectorItemInfo("column.*"));
		try {
			values1 = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view1);
			values2 = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view2);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(values1==null||values2==null)
			return;
		for (int i = 0; i < values1.size(); i++) {
			NewListingValueInfo valueInfo = values1.get(i);
			String entryId = valueInfo.getEntry().getId().toString();
			String colId = valueInfo.getColumn().getId().toString();
			String key = entryId + colId;
			values.put(key, valueInfo);
		}
		for (int i = 0; i < values2.size(); i++) {
			NewListingValueInfo valueInfo = values2.get(i);
			String entryId = valueInfo.getEntry().getId().toString();
			String colId = valueInfo.getColumn().getId().toString();
			String key = entryId + colId;
			values.put(key, valueInfo);
		}
		for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
			IColumn column = table.getColumn(colIndex);
			NewListingColumnInfo colInfo = (NewListingColumnInfo) column.getUserObject();
			if (colInfo != null) {
				ColumnTypeEnum colType = colInfo.getHeadColumn().getColumnType();
				for (int rowIndex = 1; rowIndex < table.getRowCount(); rowIndex++) {
					IRow row = table.getRow(rowIndex);
					NewListingEntryInfo entry = (NewListingEntryInfo) row.getUserObject();
					NewListingValueInfo value = (NewListingValueInfo) values.get(entry.getId().toString() + colInfo.getId().toString());
					if (!colInfo.getHeadColumn().getName().equals("子目名称")) {
						InviteHelper.loadListingValue(row.getCell(colIndex), value, colType);
					}
				}
			}
		}
	}

	/**
	 * 功能描述:在状态栏显示信息
	 */
	protected void showBarInfo(CoreUI ui,String info) {
		ui.setMessageText(info);
		setNextMessageText(info);
		ui.setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		setIsShowTextOnly(false);
		ui.showMessage();
	}

	/**
	 * 功能描述：将合法数据填充到页面
	 */
	public void importSheetToNewSheet(Sheet sheet, KDTable table) {
		int maxRow = sheet.getMaxRowIndex();
		int maxColumn = sheet.getMaxColIndex();
		for (int col = 2; col <= maxColumn; col++) {
			IColumn column = table.getColumn(col);
			Object colUserObj = column.getUserObject();
			if (colUserObj != null
					&& colUserObj instanceof NewListingColumnInfo) {
				NewListingColumnInfo colInfo = (NewListingColumnInfo) colUserObj;
				if (!colInfo.isIsQuoting()) {
					continue;
				}
				ColumnTypeEnum colType = colInfo.getHeadColumn()
						.getColumnType();
				for (int row = 2; row <= maxRow; row++) {
					Cell excelCell = sheet.getCell(row, col, true);
					Variant rawVal = excelCell.getValue();
					if (Variant.isNull(rawVal)) {
						continue;
					}
					InviteHelper.loadExcelVal(table.getCell(row - 2, col), rawVal, colType);
				}
			}
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		FDCClientVerifyHelper.verifyRequire(this);
		if(editData.getSupplier()==null)
			return;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("supplier", editData.getSupplier().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("listing", editData.getListing().getId().toString()));
		if (editData.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
		}
		if (NewQuotingPriceFactory.getRemoteInstance().exists(filter)) {
			throw new FDCInviteException(FDCInviteException.SUPPLIER_IS_OVER);
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("*");
		sic.add("supplier.*");
		return sic;
	}

	/**
	 * 设置父科目汇总数
	 *
	 * @param table
	 * @param amountColumns
	 */
	public static void setUnionData(KDTable table,final KDTabbedPane tabbedPnlTable) {
		setTableScript(table);
		List colList = new ArrayList();
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			if (column.getUserObject() != null
					&& column.getUserObject() instanceof NewListingColumnInfo) {
				NewListingColumnInfo col = (NewListingColumnInfo) column
						.getUserObject();
				if (col != null
						&& (col.getHeadColumn().getProperty()
								.equals(DescriptionEnum.AmountSum))) {
					colList.add(new Integer(i));
				}
			}
		}
		for (int i = 1; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			// 设置汇总行
			int level = row.getTreeLevel();
			int maxLevel = getMaxLevel(table);
			if (level == maxLevel) {
				continue;
			}
			List aimRowList = new ArrayList();
			for (int j = i + 1; j < table.getRowCount(); j++) {
				IRow rowAfter = table.getRow(j);
				if (rowAfter.getTreeLevel() <= level) {
					break;
				}
				if (isLeafRow(table, j))
					aimRowList.add(rowAfter);

			}
			if (aimRowList.size() > 0) {
				for (int j = 0; j < colList.size(); j++) {
					Integer colNum = (Integer) colList.get(j);
					int colIndex = colNum.intValue();
					BigDecimal sum = null;
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell(colIndex).getValue();
						if (value != null) {
							if (sum == null) {
								sum = FMConstants.ZERO;
							}
							sum = sum.add(FDCHelper.toBigDecimal(value));
						}
					}
					row.getCell(colIndex).setValue(sum);
				}
			}

		}
		setTableTotal(table);
		table.getScriptManager().runAll();
		updateTotalPageData(tabbedPnlTable);
		// setTableLock(table, true);

	}

	private static void setTableTotal(KDTable table) {
		int amountSumCol = -1;
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			NewListingColumnInfo col = (NewListingColumnInfo) column
					.getUserObject();
			if (col != null
					&& (col.getHeadColumn().getProperty()
							.equals(DescriptionEnum.AmountSum))) {
				amountSumCol = i;
				break;
			}
		}
		if (amountSumCol == -1) {
			return;
		}
		IRow row = (IRow) table.getRow(0);
		List aimRowList = new ArrayList();
		for (int j = 1; j < table.getRowCount(); j++) {
			IRow rowAfter = table.getRow(j);
//			NewListingEntryInfo info = (NewListingEntryInfo) table.getRow(j).getUserObject();
			if (isLeafRow(table, j))
//			if(info.isIsLeaf())
				aimRowList.add(rowAfter);
		}
		if (aimRowList.size() > 0) {
			BigDecimal sum = FMConstants.ZERO;
			for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
				IRow rowAdd = (IRow) aimRowList.get(rowIndex);
				Object value = rowAdd.getCell(amountSumCol).getValue();
				sum = sum.add(FDCHelper.toBigDecimal(value));
			}
			row.getCell(amountSumCol).setValue(sum);
		}
		
		
	}

	/**
	 * 
	 * 描述：判断是否是叶子行。<br>
	 * 2011-10-27 修改：之前用row.getTreeLevel()有时获取不到值，改用table.getCell(rowIndex, "level").getValue()
	 */
	public static boolean isLeafRow(KDTable table, int rowIndex) {
		if (rowIndex + 1 == table.getRowCount()) {
			return true;
		}
		int rowLevel = 0;
		if (table.getCell(rowIndex, "level").getValue() != null) {
			rowLevel = ((Integer) table.getCell(rowIndex, "level").getValue()).intValue();
		}
		int nextLevel = ((Integer) table.getCell(rowIndex + 1, "level").getValue()).intValue();
		if (nextLevel <= rowLevel) {
			return true;
		}
		return false;
	}

	public void verifyData() {
		boolean isErr = false;
		for (int i = 2; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) this.tabbedPnlTable.getComponentAt(i);
			// 校验同名称价格是否相同
			Map nameMap = new HashMap();
			for (int j = 1; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				ICell nameCell = row.getCell("子目名称");
				String name = null;
				if (nameCell.getValue() instanceof String) {
					name = (String) nameCell.getValue();
				} else {
					if (nameCell.getValue() != null) {
						name = ((ListingItemInfo) nameCell.getValue())
								.getName();
					}
				}
				Color errColor = LEAF_COLOR;
				int colCount = table.getColumnCount();
				if (nameMap.containsKey(name)) {
					IRow fRow = (IRow) nameMap.get(name);
					for (int k = 0; k < colCount; k++) {
						NewListingColumnInfo colInfo = (NewListingColumnInfo) table.getColumn(k).getUserObject();
						ICell cell = row.getCell(k);
						if (!cell.getStyleAttributes().isLocked()) {
							cell.getStyleAttributes().setBackground(REQUIREDCOLOR);
							if (colInfo.getHeadColumn().getColumnType().equals(ColumnTypeEnum.Amount)) {
								BigDecimal fValue = (BigDecimal) fRow.getCell(k).getValue();
								if (fValue == null) {
									fValue = FDCHelper.ZERO;
								}
								BigDecimal value = (BigDecimal) row.getCell(k).getValue();
								if (value == null) {
									value = FDCHelper.ZERO;
								}
								if (value.compareTo(fValue) != 0) {
									cell.getStyleAttributes().setBackground(errColor);
									isErr = true;
								}
							}
						}
					}

				} else {
					nameMap.put(name, row);
				}
			}
		}
		if (isErr) {
			MsgBox.showInfo("存在子目名称相同的行报价不同!");
			this.abort();
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.verifyData();
		super.actionSubmit_actionPerformed(e);
		
//		storeNewListingValues();
		storeNewListingValuesByMySelf();
		isModify = false;
		CacheServiceFactory.getInstance().discardType(new NewListingPageInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListingColumnInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListingEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListingValueInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new ListingPageSumEntryInfo().getBOSType());
	}

	/**
	 * 
	 * 描述：保存导入的清单数据。具体实现做法是先清空历史数据，再保存。
	 * @throws Exception
	 * @Author：owen_wen
	 * @CreateTime：2011-11-3
	 */
	private void storeNewListingValuesByMySelf() throws Exception {
		if (this.editData.getStatus().equals(QuotingPriceStatusEnum.NoImportPrice)) {
			isModify = false;
			return;
		}		
		
		// 保存newListingValues，分两种情况：
		// 1) 对供应商而言，还需要保存newListingEntry；
		// 2) 对于甲方，不需要保存newListingEntry，只需要保存newListingValues
		for (int i = 2; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) tabbedPnlTable.getComponentAt(i);
			NewListingPageInfo page = (NewListingPageInfo) table.getUserObject();
			// 分情况处理，先处理供应商自定义页签
			if (page.getHeadType().isIsDefined()) {
				clearSupplierNewsListingEntries();
				// 因为clearSupplierNewsListingEntries可能删除了部分Entry，所以重新取数一次
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("headType.isDefined"));
				page = NewListingPageFactory.getRemoteInstance().getNewListingPageInfo(new ObjectUuidPK(page.getId()), sic);
				page.setDescription((String) ((KDTable) tabbedPnlTable.getComponentAt(0)).getCell(i - 2, "des").getValue());

				for (int rowIndex = 1; rowIndex < table.getRowCount(); rowIndex++) {
					IRow row = table.getRow(rowIndex);
					if (!(row.getUserObject() instanceof NewListingEntryInfo))
						return;

					NewListingEntryInfo rowEntryInfo = (NewListingEntryInfo) row.getUserObject();
					NewListingEntryInfo newEntryInfo = new NewListingEntryInfo();
					newEntryInfo.setHead(page);
					newEntryInfo.setId(rowEntryInfo.getId());
					newEntryInfo.setLevel(Integer.parseInt(row.getCell("level").getValue().toString()));
					if (row.getCell("子目名称").getValue() instanceof ListingItemInfo) {
						ListingItemInfo item = (ListingItemInfo) row.getCell("子目名称").getValue();
						newEntryInfo.setItem(item);
						newEntryInfo.setItemName(item.getName());
						newEntryInfo.setItemNumber(item.getNumber());
					} else {
						if (row.getCell("子目名称").getValue() != null) {
							newEntryInfo.setItemName(row.getCell("子目名称").getValue().toString());
							newEntryInfo.setItem(null);
						}
					}

					newEntryInfo.setSeq(rowIndex); // 如果其它供应商，这么设置seq好像不对 了？
					newEntryInfo.setIsLeaf(rowEntryInfo.isIsLeaf());
					newEntryInfo.setIsCanZeroProAmount(rowEntryInfo.isIsCanZeroProAmount());

					if (row.getCell("isKey").getValue() != null) { // 关键字目
						newEntryInfo.setIsKey(((Boolean) row.getCell("isKey").getValue()).booleanValue());
					} else {
						newEntryInfo.setIsKey(false);
					}

					if (row.getCell("isCompose").getValue() != null) { // 单价构成
						newEntryInfo.setIsCompose(((Boolean) row.getCell("isCompose").getValue()).booleanValue());
					} else {
						newEntryInfo.setIsCompose(false);
					}
					
					NewListingEntryInfo cloneNewListingEntryInfo = (NewListingEntryInfo) newEntryInfo.clone();//这一句不能放到下面去，克隆之前必须确保它的分录是空的
					NewListingValueCollection valueColl = getRowValues(row, table, newEntryInfo, page);
					newEntryInfo.getValues().addCollection(valueColl);
					page.getEntrys().add(newEntryInfo);
					
					// 导入原始报价时，要克隆一份作为修正报价
					if (QuotingPriceTypeEnum.original.equals(priceTypeEnum)) { 
						cloneNewListingEntryInfo.setBoolean("isCloned", true); //加个克隆标志
						NewListingValueCollection valueColl2 = getRowValues(row, table, cloneNewListingEntryInfo, page);
						cloneNewListingEntryInfo.getValues().addCollection(valueColl2);
						page.getEntrys().add(cloneNewListingEntryInfo);
					}
				}
				
				/* print all data, use for debug 
				System.out.println("page.getName()" + page.getDescription() + "; page.getId()=" + page.getId());
				for (int k = 0; k < page.getEntrys().size(); k++) {
					System.out.println("子目名称：" + page.getEntrys().get(k).getItemName());
					for (int m = 0; m < page.getEntrys().get(k).getValues().size(); m++) {
						System.out.print("\t 清单数据：金额=" + page.getEntrys().get(k).getValues().get(m).getAmount() + "\t文本信息＝" + page.getEntrys().get(k).getValues().get(m).getText() + "\t日期＝"
								+ page.getEntrys().get(k).getValues().get(m).getDateValue());
						System.out.print(" page.id = " + page.getEntrys().get(k).getValues().get(m).getEntry().getHead().getId());
						System.out.println("\t type = " + page.getEntrys().get(k).getValues().get(m).getType());
					}
				}
				*/		
				
				NewListingPageFactory.getRemoteInstance().save(page);
			} else {// 供应商页签处理完毕，现处理甲方定义的页签
				clearPartANewsListingValues(page);
				// 因为clearPartANewsListingValues可能删除了部分newListingValue，所以重新取数一次
				page = NewListingPageFactory.getRemoteInstance().getNewListingPageInfo(new ObjectUuidPK(page.getId()));

				for (int rowIndex = 1; rowIndex < table.getRowCount(); rowIndex++) {
					IRow row = table.getRow(rowIndex);
					if (!isLeafRow(table, rowIndex)) // 如果不是末级节点，是没有values的
						continue;

					for (int j = 0; j < page.getEntrys().size(); j++) {
						NewListingEntryInfo entryInfo = page.getEntrys().get(j);
						// 如果子目名称、单位、工程量相同，则认为是相等的Entry
						if (row.getCell("子目名称").getValue().toString().equals(entryInfo.getItemName())) {
							if (entryInfo.getValues().get(1).getText() != null // 单位相同
									&& entryInfo.getValues().get(1).getText().equals(row.getCell("单位").getValue().toString())) {
								if (entryInfo.getValues().get(2).getAmount() != null&&row.getCell("工程量").getValue()!=null // 综合单价相同
										&&entryInfo.getValues().get(2).getAmount().compareTo((BigDecimal)row.getCell("工程量").getValue())== 0) {
									entryInfo.getValues().addCollection(getRowValues(row, table, entryInfo, page));
									break; // 本次entryInfo与表格中的比较结束，开始下一个
								}
							}
						}
					}
				}
				NewListingPageFactory.getRemoteInstance().save(page);
			}
		}
	}

	/**
	 * 描述：清除甲方页签某供应商的清单数据，条件是：报价ID、报价的供应商、报价类型、报价页签相同
	 * @Author：owen_wen
	 * @CreateTime：2011-11-6
	 */
	private void clearPartANewsListingValues(NewListingPageInfo page) throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("quotingPrice.id", editData.getString("id")));
		filter.getFilterItems().add(new FilterItemInfo("quotingPrice.supplier.id", editData.getSupplier().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(priceTypeEnum.equals(QuotingPriceTypeEnum.modify) ? 1 : 0)));
		filter.getFilterItems().add(new FilterItemInfo("entry.head.id", page.getId().toString()));
		NewListingValueFactory.getRemoteInstance().delete(filter);
	}

	/** 
	 * 根据报价ID，供应商ID，先清除库中该报价下的清单数据（NewListingValue）。甲方对应的NewListingEntry不需要清除。
	 * @Author：owen_wen
	 * @CreateTime：2011-11-6
	 */
	private void clearSupplierNewsListingEntries() throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("quotingPrice.id", editData.getString("id")));
		filter.getFilterItems().add(new FilterItemInfo("quotingPrice.supplier.id", editData.getSupplier().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(priceTypeEnum.equals(QuotingPriceTypeEnum.modify) ? 1 : 0)));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		NewListingValueCollection newListingValues = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
		Set newListingEntryIdSet = new HashSet();
		for (int i = 0; i < newListingValues.size(); i++) {
			newListingEntryIdSet.add(newListingValues.get(i).getEntry().getId().toString());
		}

		if (!newListingEntryIdSet.isEmpty()) {
			filter.getFilterItems().clear();
			filter.getFilterItems().add(new FilterItemInfo("id", newListingEntryIdSet, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("head.headType.isDefined", new Integer(1))); // 非供应商定义页签不需要清除
			NewListingEntryFactory.getRemoteInstance().delete(filter); // 删除当前供应商对应的清单分录，进而也删除了清单数据
		}
	}
	
	/**
	 * 这是外包写的代码，逻辑过于复杂，被重写了，将来不需要可删除 owen_wen 2011-11-6
	 * @deprecated 
	 * @see {@link NewQuotingPriceEditUI} 中的 storeNewListingValuesByMySelf()
	 */
	private void storeNewListingValues() throws Exception {
		List pageId = new ArrayList();
		// begin ---- xuisan
		KDTable desTable = (KDTable) this.tabbedPnlTable.getComponentAt(0);
		
		if (this.editData.getStatus().equals(QuotingPriceStatusEnum.NoImportPrice)) {
			isModify = false;
			return;
		}
		
		boolean isChange = false;
		if(this.getUITitle().toString().contains("修正")){
			 isChange = true;
		}
		
		CoreBaseCollection baseCollection = new CoreBaseCollection();
		for (int i = 0; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) tabbedPnlTable.getComponentAt(i);			
			NewListingPageInfo page = (NewListingPageInfo) table.getUserObject();
			if (page != null) {
				
				int projectAmtIndex = 0; // 工程量
				int projectAmtSum = 0; // 工程量小计
				int totalPriceIndex = 0; // 综合单价
				int totalPriceSum = 0; 
				int amountIndex = 0;
				int amountSum = 0;
				
				if("工程量".equals(table.getColumn(5).getKey())){
					projectAmtIndex = 5;
					projectAmtSum = 5;
				}else{
					projectAmtIndex = 5;
					for(int j = projectAmtIndex; j < table.getColumnCount(); j++){
						if("小计".equals(table.getColumn(j).getKey())){
							projectAmtSum = j;
							break;
						}
					}
				}

				if("综合单价".equals(table.getColumn(projectAmtSum + 1).getKey())){
					totalPriceIndex = projectAmtSum + 1;
					totalPriceSum = projectAmtSum + 1;
				}else{
					totalPriceIndex = projectAmtSum + 1;
					for(int j = totalPriceIndex; j < table.getColumnCount(); j++){
						if("小计".equals(table.getColumn(j).getKey())){
							totalPriceSum = j;
							break;
						}
					}
				}
				
				if("金额".equals(table.getColumn(totalPriceSum + 1).getKey())){
					amountIndex = totalPriceSum + 1;
					amountSum = totalPriceSum + 1;
				}else{
					amountIndex = totalPriceSum + 1;
					for(int j = amountIndex; j < table.getColumnCount(); j++){
						if("小计".equals(table.getColumn(j).getKey())){
							amountSum = j;
							break;
						}
					}
				}
				
				page.setDescription((String) desTable.getCell(i - 2, "des").getValue());
				page.getEntrys().clear();
				for (int j = 1; j < table.getRowCount(); j++) {
					IRow row = table.getRow(j);
					if (row.getUserObject() instanceof NewListingEntryInfo) {
						NewListingEntryInfo entryInfo = new NewListingEntryInfo();
						NewListingEntryInfo tempInfo = (NewListingEntryInfo) row.getUserObject();
						entryInfo.getValues().clear();
						entryInfo.setHead(page);
						entryInfo.setId(tempInfo.getId());
						entryInfo.setLevel(Integer.parseInt(row.getCell("level").getValue().toString()));
						if (row.getCell("子目名称").getValue() instanceof ListingItemInfo) {
							ListingItemInfo item = (ListingItemInfo) row.getCell("子目名称").getValue();
							entryInfo.setItem(item);
							entryInfo.setItemName(item.getName());
							entryInfo.setItemNumber(item.getNumber());
						} else {
							if (row.getCell("子目名称").getValue() != null) {
								entryInfo.setItemName(row.getCell("子目名称").getValue().toString());
								entryInfo.setItem(null);
							}
						}
						
						entryInfo.setSeq(j);
						entryInfo.setIsLeaf(tempInfo.isIsLeaf());
						entryInfo.setIsCanZeroProAmount(tempInfo.isIsCanZeroProAmount());
						if (row.getCell("isKey").getValue() != null)
							entryInfo.setIsKey(((Boolean) row.getCell("isKey").getValue()).booleanValue());
						else
							entryInfo.setIsKey(false);
						if (row.getCell("isCompose").getValue() != null)
							entryInfo.setIsCompose(((Boolean) row.getCell("isCompose").getValue()).booleanValue());
						else
							entryInfo.setIsCompose(false);
						NewListingValueCollection valueColl = getRowValues(row, table, entryInfo, page);
						entryInfo.getValues().addCollection(valueColl);
//						entryInfo.setIsLeaf(this.isLeafRow(table, j));
						page.getEntrys().add(entryInfo);
					}
				}
				NewListingPageInfo oldPage = NewListingPageFactory.getRemoteInstance().getNewListingPageInfo(
						"where id = '" + page.getId() + "'");
				
				for(int s = 0 ; s < page.getEntrys().size() ; s ++ )
				{					
					boolean isEqual = false;
					NewListingEntryInfo newEntryInfo = page.getEntrys().get(s);
					if(newEntryInfo.getHead() != null && newEntryInfo.getHead().getHeadType() != null){
						// 如果是甲方页签（即招标方），它的page.getEntrys()是不需要改变的，在招标清单编制里定义多少行就是多少行，
						if(!newEntryInfo.getHead().getHeadType().isIsDefined()){
							continue;
						}
					}
					
					pageId.add(page.getString("id"));

					NewListingValueInfo newListingValueUnit = newEntryInfo.getValues().get(1);
					NewListingValueInfo newListingValueProjectAmt = newEntryInfo.getValues().get(projectAmtSum - 3);
					String columnnIdUnit = newListingValueUnit.getColumn().getId().toString();
					String unit = StringUtils.cnulls(newListingValueUnit.get("text"));
//					String unitId = newListingValueUnit.getEntry().getId().toString();
					String columnnIdProjectAmt = newListingValueProjectAmt.getColumn().getId().toString();
					BigDecimal projectAmt = FDCNumberHelper.toBigDecimal(newListingValueProjectAmt.get("amount"));
//					String projectAmtId = newListingValueProjectAmt.getEntry().getId().toString();
					
					for(int k = 0 ; k < oldPage.getEntrys().size() ; k ++ )
					{
						NewListingEntryInfo oldEntryInfo = oldPage.getEntrys().get(k);
						
						if(newEntryInfo.getItemName() != null && oldEntryInfo.getItemName() != null 
								&& newEntryInfo.getItemName().equals(oldEntryInfo.getItemName()))
						{							
							EntityViewInfo view = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("entry.id", oldEntryInfo.getString("id") ,CompareType.EQUALS));
							filter.getFilterItems().add(new FilterItemInfo("column.id", columnnIdUnit ,CompareType.EQUALS));
							view.setFilter(filter);
							NewListingValueCollection oldNewListingValue = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);

							if(oldNewListingValue != null && oldNewListingValue.size() > 0){
								for(int unitIndex = 0 ; unitIndex < oldNewListingValue.size(); unitIndex++){
									NewListingValueInfo oldNewListingValueInfo = oldNewListingValue.get(unitIndex);
									if(unit.equals(oldNewListingValueInfo.getText())){
										view = new EntityViewInfo();
										filter = new FilterInfo();
										filter.getFilterItems().add(new FilterItemInfo("entry.id", oldEntryInfo.getString("id") ,CompareType.EQUALS));
										filter.getFilterItems().add(new FilterItemInfo("column.id", columnnIdProjectAmt ,CompareType.EQUALS));
										view.setFilter(filter);
										NewListingValueCollection oldNewListingValueProjectAmt = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
										if(oldNewListingValueProjectAmt != null && oldNewListingValueProjectAmt.size() > 0){
											for(int projectIndex = 0 ; projectIndex < oldNewListingValue.size(); projectIndex++){
												NewListingValueInfo oldProjectAmt = oldNewListingValueProjectAmt.get(projectIndex);
												if(projectAmt.floatValue() == FDCNumberHelper.toBigDecimal(oldProjectAmt.getAmount()).floatValue()){
													isEqual = true;
												}
											}
										}
									}
								}
							}
						}
					}
					if(!isEqual){
//						if(isChange){
						for(int index = amountSum ; index >= totalPriceIndex ; index--){
							newEntryInfo.getValues().get(index - 3).setAmount(null);//Constants.zero
						}
						oldPage.getEntrys().add(newEntryInfo);
					}
//					else{
//						oldPage.getEntrys().set(s, newEntryInfo);
//					}
				}
				
				// 如果是甲方页签（即招标方），它的page.getEntrys()是不需要改变的，因此不需要保存
				if (page.getHeadType().isIsDefined()) { 
					NewListingPageFactory.getRemoteInstance().submit(oldPage);
				}
				
				if(NewListingEntryFactory.getRemoteInstance().exists("where head.id = '" + oldPage.getString("id") + "'"))
				{
					NewListingEntryCollection newListingEntryCollection = 
						NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection("where head.id = '" + oldPage.getString("id") + "'");

					for(int s = 0 ; s < newListingEntryCollection.size() ; s ++ )
					{
						if(isChange)
						{
							NewListingValueFactory.getRemoteInstance().delete(
									"where entry.id = '" + newListingEntryCollection.get(s).getString("id") + "' " +
									" and quotingPrice.id = '" + editData.getString("id") + "' and type = 1");
						}
						else
						{
							NewListingValueFactory.getRemoteInstance().delete(
								"where entry.id = '" + newListingEntryCollection.get(s).getString("id") + "' " +
								" and quotingPrice.id = '" + editData.getString("id") + "' and type = 0");
						}
					}
					
					for(int j = 0; j < table.getRowCount(); j++){
						IRow row = table.getRow(j);
						if (row.getUserObject() instanceof NewListingEntryInfo) {
							NewListingEntryInfo entryInfo = (NewListingEntryInfo) row.getUserObject();
							if (row.getCell("子目名称").getValue() != null) {
								for (int a = 0; a < newListingEntryCollection.size(); a++) {
									if (newListingEntryCollection.get(a).getItemName().equals(row.getCell("子目名称").getValue())) {
										if ((newListingEntryCollection.get(a).getValues().get(1).get("text") == null && row.getCell("单位").getValue() == null)
												|| (newListingEntryCollection.get(a).getValues().get(1).get("text") != null && newListingEntryCollection.get(a).getValues().get(1).get("text")
														.toString().equals(row.getCell("单位").getValue()))) {
											Object amount = newListingEntryCollection.get(a).getValues().get(projectAmtSum - 3).get("amount");
											float famount = FDCNumberHelper.toBigDecimal(newListingEntryCollection.get(a).getValues().get(projectAmtSum - 3).get("amount")).floatValue();
											float famount2 = FDCNumberHelper.toBigDecimal(row.getCell(projectAmtSum).getValue()).floatValue();
											if (amount != null && famount == famount2) {
												entryInfo = newListingEntryCollection.get(a);
											}
										}
									}
								}
							}
							int colCount = table.getColumnCount();
							for (int k = 0; k < colCount; k++) {
								IColumn column = table.getColumn(k);
								if (column.getUserObject() instanceof NewListingColumnInfo) {
									NewListingColumnInfo columnInfo = (NewListingColumnInfo) column.getUserObject();
									DescriptionEnum property = columnInfo.getHeadColumn().getProperty();
									if (property.equals(DescriptionEnum.TotalPrice) || property.equals(DescriptionEnum.TotalPriceSum) || property.equals(DescriptionEnum.Personal)
											|| property.equals(DescriptionEnum.AmountSum)) {
										Object temp = row.getCell(k).getValue();
										if (temp == null) {
											continue;
										}
										
										NewListingValueInfo valueInfo = new NewListingValueInfo();
										valueInfo.setColumn(columnInfo);
										valueInfo.setEntry(entryInfo);
										valueInfo.setQuotingPrice(editData);
										ColumnTypeEnum colType = columnInfo.getHeadColumn().getColumnType();
										InviteHelper.storeListingValue(valueInfo, temp, colType);
										if (colType.equals(ColumnTypeEnum.Amount)) {
											valueInfo.setAmount(FDCHelper.toBigDecimal(temp));
										} else if (colType.equals(ColumnTypeEnum.Date)) {
											valueInfo.setDateValue((Date) temp);
										} else {
											valueInfo.setText(temp.toString());
										}
										
										if (isChange) {
											valueInfo.setType(QuotingPriceTypeEnum.modify);
											baseCollection.add(valueInfo);
										} else {
											valueInfo.setType(QuotingPriceTypeEnum.original);
											baseCollection.add(valueInfo);
											NewListingValueInfo newInfo = (NewListingValueInfo) valueInfo.clone();
											newInfo.setType(QuotingPriceTypeEnum.modify);
											baseCollection.add(newInfo);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		NewListingValueFactory.getRemoteInstance().submit(baseCollection); 		
		//  end ---- xuisan 
	}
	
	//begin ----xuisan

	/**
	 * 获得每一行的清单数据，供保存时使用。
	 */
	private NewListingValueCollection getRowValues(IRow row, KDTable table, NewListingEntryInfo entryInfo, NewListingPageInfo page) {
		NewListingValueCollection valueColl = new NewListingValueCollection();
		NewListingValueInfo valueInfo = null;
		NewListingColumnInfo columnInfo = null;
		for (int i = 3; i < table.getColumnCount(); i++) {
			valueInfo = new NewListingValueInfo();
			IColumn column = table.getColumn(i);
			if (column.getUserObject() instanceof NewListingColumnInfo) {
				columnInfo = (NewListingColumnInfo) column.getUserObject();
			} else {
				columnInfo = (NewListingColumnInfo) listingColumnObjectMap.get(table.getName() + column.getKey());
			}
			valueInfo.setColumn(columnInfo);
			valueInfo.setEntry(entryInfo);
			ICell cell = row.getCell(i);
			if (cell.getValue() != null) {
				if (column.getKey() == "子目名称") {
					if (cell.getValue() instanceof ListingItemInfo) {
						ListingItemInfo item = (ListingItemInfo) cell.getValue();
						valueInfo.setText(item.getName());
					} else {
						valueInfo.setText(cell.getValue().toString());
					}
				} else {
					ColumnTypeEnum colType = columnInfo.getHeadColumn().getColumnType();
					InviteHelper.storeListingValue(valueInfo, cell.getValue(), colType);
				}
			}
			if (columnInfo.isIsQuoting()) {
				NewListingValueInfo oldValue = null;
				if (page.getHeadType().getId() != null && row.getCell("子目名称").getValue() != null && columnInfo.getHeadColumn() != null)
					oldValue = (NewListingValueInfo) refPriceBakMap.get(page.getHeadType().getId().toString() + row.getCell("子目名称").getValue().toString() + columnInfo.getHeadColumn().getName());
				if (oldValue != null) {
					valueInfo.setAmount(oldValue.getAmount());
					valueInfo.setText(oldValue.getText());
					valueInfo.setDateValue(oldValue.getDateValue());
				}
			}
			valueInfo.setType(priceTypeEnum);
			valueInfo.setQuotingPrice(editData);//与报价关联起来
			if (page.getHeadType().isIsDefined()) {//对供应商页签的分录行进行复制
				if (entryInfo.getBoolean("isCloned")) { // 需要clone原始报价，这里的entryInfo是克隆后的entry
					valueInfo.setType(QuotingPriceTypeEnum.modify);
				}
			}			
			// 如果是甲方页签，克隆非常简单，直接复制一份valueInfo即可
			else if (QuotingPriceTypeEnum.original.equals(priceTypeEnum)) {
				NewListingValueInfo valueInfoClone = (NewListingValueInfo) valueInfo.clone();
				valueInfoClone.setType(QuotingPriceTypeEnum.modify);
				valueColl.add(valueInfoClone);
			}
			valueColl.add(valueInfo);
		}
		return valueColl;
	}
	//end ----- xuisan 
	
	
	// 导入与导出

	private String importID;

	private static final char separator = '`';

	// 需要清除后再导出的列，用于还原
	private Map priceCols = new HashMap(5);

	/**
	 * 打开和保存文件对话框
	 *
	 * @param title
	 * @param mode
	 * @return
	 */
	public static File fileDialog(String title,String listingName, int mode,CoreUI coreui) {
		JFileChooser fileChooser = new KDFileChooser() {
			public void approveSelection() {
				File file = this.getSelectedFile();
				if (this.getDialogType() == this.SAVE_DIALOG) {
					if (file != null && file.exists()) {
						if (MsgBox.showConfirm2(this, "文件已存在，确定要覆盖吗？") == MsgBox.OK) {
							super.approveSelection();
						}
					} else {
						super.approveSelection();
					}
				} else if (this.getDialogType() == this.OPEN_DIALOG) {
					if (file == null || !file.exists()) {
						MsgBox.showError(this, "文件不存在，请重新选择文件");
					} else {
						super.approveSelection();
					}
	
				}
			}
		};
		fileChooser.setSelectedFile(new File("c:\\"+listingName+".fdc"));
		fileChooser.setDialogTitle(title);
		fileChooser.setDialogType(mode);
		fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().endsWith(".fdc");
			}

			public String getDescription() {
				return "房地产报价文件(*.fdc)";
			}
		});
		if (fileChooser.showDialog(coreui, null) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}

	/*
	 * 导入
	 */
	public void actionImportQuotingExcel_actionPerformed(ActionEvent e)
			throws Exception {
		
		//权限检测
		IObjectPK orgPK = getOrgPK(this.actionImportQuotingExcel);
		PermissionFactory.getRemoteInstance().checkFunctionPermission(
				new ObjectUuidPK(SysContext.getSysContext()
						.getCurrentUserInfo().getId().toString()), orgPK,
		"inv_quotingPrice_ImportQuotingExcel");
		String importName = (String) getUIContext().get("inviteListingName");
		File file = fileDialog("导入报价",importName, JFileChooser.OPEN_DIALOG,this);
		importQuoting(file);
	}

	/**
	 * set it public in order to be called in perf test codes
	 *
	 * @param file
	 * @throws ZipException
	 * @throws IOException
	 */
	public void importQuoting(File file) throws ZipException, IOException {
		if (file == null)
			return;
		ZipFile zipFile = null;
		try{
			zipFile = new ZipFile(file);
		}
		catch(Exception e){
			MsgBox.showError("文件格式错误!");
			return;
		}
		if (!verifyImport(zipFile)) {
			MsgBox.showError("导入文件与当前报价表不一致!");
			return;
		}
		ZipEntry zipEntry = null;
		Enumeration enums = zipFile.entries();
		while (enums.hasMoreElements()) {
			zipEntry = (ZipEntry) enums.nextElement();
			if (zipEntry.getName().endsWith(".kdf")) {
				String comment = zipEntry.getComment();
				Object[] strCols = null;
				if (comment != null && comment.length() > 1) {		
					
					//加入字符串列，日期列的支持，所以原有的转换代码需要更新
					String[] tempCols = comment.split(""+separator);
					ArrayList tempList = new ArrayList();
					for (int i = 0; i < tempCols.length - 1; i++) {
						String[] cols = tempCols[i].split(";");
						for (int j = 0; j < cols.length; j++) {
							if (!cols[j].equals(""))
								tempList.add(cols[j]);
						}
					}
					strCols = tempList.toArray();
					if (strCols == null || strCols.length < 1)
						continue;
					KDTable table = new KDTable();
					table.getIOManager().load(zipFile.getInputStream(zipEntry));
					int[] cols = new int[strCols.length];
					for (int i = 0; i < cols.length; i++) {
						cols[i] = Integer.parseInt((String)strCols[i]);
					}
					// 导入填充
					if (table.getRowCount() > 0)
						fill(table, cols);
				}
			}
		}
		isModify = true;
		this.editData.setStatus(QuotingPriceStatusEnum.ImportedPrice);
		
		showBarInfo(this,"成功导入报价");
	}

	private boolean verifyImport(ZipFile zipFile) {
		importID = (String) getUIContext().get("inviteListingId");// (String)this.getUIContext().get(UIContext.ID);
		Enumeration enums = zipFile.entries();
		while (enums.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) enums.nextElement();
			if (zipEntry.getName().endsWith(".fdc")) {
				String comment = zipEntry.getComment();
				if (comment.indexOf('`') < 0)
					return false;
				return importID.equals(comment.substring(0, comment
						.indexOf(separator)));
			}
		}
		return false;
	}

	/**
	 * 导入相应列
	 *
	 * @param table
	 * @param columnList
	 *            需要填充的列
	 */
	private void fill(KDTable table, int[] columnList) {
		for (int i = 0; i < this.tabbedPnlTable.getTabCount(); i++) {

			KDTable myTable = (KDTable) this.tabbedPnlTable.getComponentAt(i);
			if (myTable.getUserObject() == null) {
				continue;
			}
			// 标题验证
			if (!table.getName().equals(myTable.getName())) {
				continue;
			}

			logger.debug("table :" + table.getName());
			//begin ---- xuisan 
			myTable.removeRows(); 
			//end   ---- xuisan 
			for (int j = 0, count = table.getRowCount(); j < count; j++) {
				for (int colIndex = 0; colIndex < myTable.getColumnCount(); colIndex++) {
					IColumn col = myTable.getColumn(colIndex);
					NewListingColumnInfo colInfo = (NewListingColumnInfo) col
							.getUserObject();
					if (colInfo == null || !colInfo.isIsQuoting()) {
						continue;
					}
					Object cellVal = table.getCell(j, colIndex).getValue();
					// myTable.getCell(j, colIndex).setValue(cellVal);
					// 非明细行以及为0的报价数据不显示
					NewListingEntryInfo entry  = new NewListingEntryInfo();
					if(myTable.getRow(j) == null){
						myTable.addRow(j, table.getRow(j));
						myTable.getRow(j).getStyleAttributes().setLocked(true);
					}else{
						entry = (NewListingEntryInfo) myTable.getRow(j).getUserObject();
					}
					if (entry != null && cellVal != null) {
						if (!entry.isIsLeaf()) {
//							myTable.getCell(j, colIndex).setValue(null);
						} else {
							//日期  字符串  金额 的处理 2008-05-28 周勇
							if (cellVal instanceof String) {
								//用户可能输入的一串数字，例如：1234
								//但用户输入的此列为日期列，需要得到getFormattedValue()
								//2008-05-30 周勇
								if(table.getCell(j, colIndex).getStyleAttributes().getNumberFormat().equals("yyyy-m-d")){
									
										try {
											myTable.getCell(j, colIndex).setValue(DateTimeUtils.parseDate(table.getCell(j, colIndex).getFormattedValue().toString()));
										} catch (ParseException e) {
											// TODO 自动生成 catch 块
											e.printStackTrace();
											myTable.getCell(j, colIndex).setValue(null);
										}
									
								}
								else{
									myTable.getCell(j, colIndex).setValue(cellVal);
								}								
							} else if (cellVal instanceof Date) {
								myTable.getCell(j, colIndex).setValue(cellVal);
							}
							else{
								BigDecimal val = FDCHelper.toBigDecimal(cellVal);
								logger.debug("quotingVal:" + cellVal);
								if (val.signum() == 0)
									myTable.getCell(j, colIndex).setValue(null);
								else
									myTable.getCell(j, colIndex).setValue(val);
							}
							
						}
					}
					else if(entry != null && cellVal == null){
						myTable.getCell(j, colIndex).setValue(null);
					}else if(entry == null && cellVal != null){
//						myTable.setRow(j).setUserObject(table.getRow(j));
					}
				}
			}
			setTreeLevel(myTable);
			// 完成导入,主动触发计算
			setUnionData(myTable,tabbedPnlTable);
			
			// 非叶子节点合计重新计算
			//父节点行
			ArrayList isLeafListNo = new ArrayList();
			for (int k = 1; k < table.getRowCount(); k++) {
				BigDecimal sum = FMConstants.ZERO;
				IRow row = table.getRow(k);
				BigDecimal rowLevel = FDCHelper.toBigDecimal(row.getCell(0).getValue());
				NewListingEntryInfo info = new NewListingEntryInfo();
//				if (info != null && !info.isIsLeaf()) {
				if(!checkLeafRow(table, k)){
					row.getCell(4).setValue(null);
					row.getCell(5).setValue(null);
//					row.getCell(6).setValue(null);
					info.setIsLeaf(false);
					
					isLeafListNo.add(String.valueOf(k));
					
					for (int m = k + 1; m < table.getRowCount(); m++) {
						IRow rowNext = table.getRow(m);
						NewListingEntryInfo nextInfo = (NewListingEntryInfo) rowNext.getUserObject();
//						if (nextInfo != null && nextInfo.isIsLeaf()) {
						if(checkLeafRow(table, m)){
							BigDecimal rowLevelNext = FDCHelper.toBigDecimal(rowNext.getCell(0).getValue());
							BigDecimal value = FDCHelper.toBigDecimal(rowNext.getCell(7).getValue());
							if(rowLevelNext.floatValue() > rowLevel.floatValue()){
								sum = sum.add(FDCHelper.toBigDecimal(value));
							}
						}
					}
//					row.getCell(7).setValue(sum);
				}else{
					info.setIsLeaf(true);
				}
				row.setUserObject(info);
			}
		}
	}

	/*
	 * 导出
	 */
	public void actionExportQuotingExcel_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.tabbedPnlTable.getTabCount() <= 2) {
			MsgBox.showWarning("没有可导出的数据");
			return;
		}
		importID = (String) getUIContext().get("inviteListingId");
		String importName = (String) getUIContext().get("inviteListingName");
		File zipFile = null;
		if ((zipFile = fileDialog("导出",importName, JFileChooser.SAVE_DIALOG,this)) == null)
			return;
		if (!zipFile.getName().endsWith(".fdc"))
			zipFile = new File(zipFile.getAbsolutePath() + ".fdc");

		
		exportQuoting(zipFile,tabbedPnlTable,userObjectList,priceCols,importID);
		showBarInfo(this,"成功导出报价");
	}

	/**
	 * set it public in order to be called in perf test codes
	 *
	 * @param zipFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void exportQuoting(File zipFile,final KDTabbedPane tabbedPnlTable,List userObjectList,Map priceCols,
			String importID) throws FileNotFoundException,
			IOException {
		if (zipFile == null || tabbedPnlTable.getTabCount() <= 2) {
			logger.error("expFile: " + zipFile + ", tabCount:"
					+ tabbedPnlTable.getTabCount());
			return;
		}

		// 导出KDTable为kdf文件
		File files[] = new File[tabbedPnlTable.getTabCount()];
		List comments = new ArrayList(10);
		
		
		comments.add(0, "");
		comments.add(1, "");
		// 各表汇总金额列
		String totalColumn = "";
		// 各表不需要打印的列
		String withoutPrintCols = "";
		
		
		for (int i = 2; i < tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) tabbedPnlTable.getComponentAt(i);
			// if(table.getUserObject()==null){
			// continue;
			// }
			// 解锁列
			setTableLock(table, false);
			// 锁定非叶子节点行
			lockNotLeafRow(table);
			// '`'之前字符包含需要填写报价的列，之后是要父科目汇总的列
			// 需要填写报价的列
			// 金额类型报价列
			String str = "";
			// 字符类型报价列
			String str2 = "";
			// 日期类型报价列
			String str3 = "";
			for (int k = 3; k < table.getColumnCount(); k++) {
				IColumn column = table.getColumn(k);
				if (!(column.getUserObject() instanceof NewListingColumnInfo))
					continue;
				NewListingColumnInfo columnInfo = (NewListingColumnInfo) column
						.getUserObject();
				if (columnInfo.isIsQuoting()
						&& columnInfo.getHeadColumn().getColumnType().equals(
								ColumnTypeEnum.Amount)) {
					str += k + ";";
				} else if (columnInfo.isIsQuoting()
						&& columnInfo.getHeadColumn().getColumnType().equals(
								ColumnTypeEnum.String)) {
					str2 += k + ";";
				} else if (columnInfo.isIsQuoting()
						&& columnInfo.getHeadColumn().getColumnType().equals(
								ColumnTypeEnum.Date)) {
					str3 += k + ";";
				}
			}
			//    金额列 ` 字符串列 ` 日期列 `
			str = str + separator + str2 + separator + str3 + separator;
			// 父科目汇总的列
			// 各表汇总金额列
			// 不需要打印的列
			List noPrintColumn = new ArrayList(5);
			boolean found = false;

			// 综合单价
			int totalPrice = 0;

			for (int i0 = 3; i0 < table.getColumnCount(); i0++) {
				IColumn column = table.getColumn(i0);
				if (column.getUserObject() instanceof NewListingColumnInfo) {
					NewListingColumnInfo col = (NewListingColumnInfo) column
							.getUserObject();
					if (col.getHeadColumn() == null)
						continue;
					DescriptionEnum property = col.getHeadColumn()
							.getProperty();
					if (property.equals(DescriptionEnum.AmountSum)) {
						found = true;
						str += i0;
						totalColumn += i0 + ";";
					} else if (property.equals(DescriptionEnum.ProjectAmt)) {
						noPrintColumn.add(i0 + ";");
					} else if (property.equals(DescriptionEnum.TotalPrice)) {
						totalPrice = i0;
					} else if (property.equals(DescriptionEnum.TotalPriceSum)) {
						totalPrice = i0;
					}
				}
			}

			table.setID(table.getID() + separator + totalPrice);

			if (!found) {
				totalColumn += "0;";
			}
			// 金额列 ` 字符串列 ` 日期列 ` 综合单价列
			comments.add(i, str);
			if (noPrintColumn.size() > 0) {
				for (int j = 0; j < noPrintColumn.size(); j++) {
					withoutPrintCols = withoutPrintCols
							+ (String) noPrintColumn.get(j);
				}
				withoutPrintCols += "!";
			} else {
				withoutPrintCols += "0!";
			}
			files[i] = new File("table_" + i + ".kdf");
			filter(table,userObjectList,priceCols).getIOManager().save("table_" + i + ".kdf");
			// 还原
			unfilter(table,userObjectList,priceCols);
			// 锁定
			setTableLock(table, true);

		}

		// 首先加入招标说明Table
		final String kd0 = "kd0.fdc";
		files[0] = new File(kd0);
		KDTable table = (KDTable) tabbedPnlTable.getComponentAt(0);
		table.getIOManager().save(kd0);

		// 注释
		comments.set(0, importID + separator + totalColumn + separator
				+ withoutPrintCols);
		
		// 供应商自定义页签
		String userDefined = "-1"+separator; // userDefined默认用-1填充，便于离线客户端导入MainFrame.importFile()
		for (int i = 2; i < tabbedPnlTable.getTabCount(); i++) {
			table = (KDTable) tabbedPnlTable.getComponentAt(i);
			NewListingPageInfo pageInfo=(NewListingPageInfo)table.getUserObject();
			if(pageInfo != null && pageInfo.getHeadType() != null && pageInfo.getHeadType().isIsDefined()){
				userDefined = userDefined + i + separator;
			}
		}
		comments.set(1, userDefined);

		// 报价汇总表
		final String kd1 = "kd1.fdc";
		files[1] = new File(kd1);
		table = (KDTable) tabbedPnlTable.getComponentAt(1);

		final int rowCount = table.getRowCount();
		Object[] objs = new Object[rowCount];
		for (int i = 0; i < rowCount; i++) {
			if (i != rowCount - 3) {
				ICell cell = table.getCell(i, 2);
				objs[i] = cell.getValue();
				cell.setValue(FDCHelper.ZERO);
			}
		}
		table.getIOManager().save(kd1);
		for (int i = 0; i < rowCount; i++) {
			if (i != rowCount - 3) {
				ICell cell = table.getCell(i, 2);
				cell.setValue(objs[i]);
			}
		}

		// 压缩文件
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
		for (int i = 0; i < files.length; i++) {
			ZipEntry zipEntry = new ZipEntry(files[i].getAbsolutePath());
			out.putNextEntry(zipEntry);
			zipEntry.setComment((String) comments.get(i));
			if(files[i].getAbsolutePath()==null)
				continue;
			FileInputStream in = new FileInputStream(files[i]);
			int b = 0;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
			files[i].deleteOnExit();
		}
		out.close();
	}

	private List userObjectList = new ArrayList(25);

	/**
	 * KDTable(过滤)处理后再导出
	 *
	 * @param table
	 * @return
	 */
	public static KDTable filter(KDTable table,List userObjectList,Map priceCols) {
		for (int j = 3; j < table.getColumnCount(); j++) {
			List list = null;
			// 清空各列UserObject(cell里的UserObject未清除),减小导出文件的长度
			IColumn column = table.getColumn(j);
			Object userObj = column.getUserObject();
			userObjectList.add(userObj);
			// 工程量
			boolean isProjectAmt = false;
			// 综合单价
			boolean isTotalPriceAmt = false;
			// 综合单价小计
			boolean isTotalPriceSum = false;
			// 仅仅是报价列
			boolean isQuoting = false;
			// 如果是金额小计
			boolean isAmountSum = false;
			//
			if (userObj instanceof NewListingColumnInfo) {
				NewListingColumnInfo columnInfo = (NewListingColumnInfo) userObj;
				if (DescriptionEnum.ProjectAmt.equals(columnInfo
						.getHeadColumn().getProperty())
						|| DescriptionEnum.ProjectAmtSum.equals(columnInfo
								.getHeadColumn().getProperty())) {
					isProjectAmt = true;
				} else if (columnInfo.isIsQuoting()
						&& DescriptionEnum.TotalPrice.equals(columnInfo
								.getHeadColumn().getProperty())) {
					isTotalPriceAmt = true;
				} else if (columnInfo.isIsQuoting()
						&& DescriptionEnum.TotalPriceSum.equals(columnInfo
								.getHeadColumn().getProperty())) {
					isTotalPriceSum = true;
				} else if (columnInfo.isIsQuoting()) {
					isQuoting = true;
				} else if (DescriptionEnum.AmountSum.equals(columnInfo
						.getHeadColumn().getProperty())) {
					isAmountSum = true;
				}
				if (isProjectAmt || isTotalPriceAmt || isQuoting
						|| isTotalPriceSum || isAmountSum)
					list = new ArrayList(45);
			}
			// 子目名称对象变字符串
			for (int i = 1; i < table.getRowCount(); i++) {
				ICell cell = table.getRow(i).getCell("子目名称");
				if (cell != null) {
					Object value = cell.getValue();
					if (value instanceof ListingItemInfo) {
						cell.setValue(((ListingItemInfo) value).getName());
					}
				}

				// 顺便修改背景颜色
				if (!isLeafRow(table, i)) {
					table.getRow(i).getStyleAttributes().setBackground(
							NON_LEAF_COLOR);
					// continue;
				}
				cell = table.getCell(i, j);
				// 填零
				if (isProjectAmt) {
					if (cell.getValue() == null) {
						cell.setValue("0");
						cell.getStyleAttributes().setFontColor(
								cell.getStyleAttributes().getBackground());
					}
				} else if (isTotalPriceAmt) {
					list.add(cell.getValue());
					if (cell.getStyleAttributes().isLocked())
						cell.getStyleAttributes().setFontColor(
								cell.getStyleAttributes().getBackground());
					cell.setValue("0");
				} else if (isTotalPriceSum) {
					list.add(cell.getValue());
					if (cell.getExpressions() == null)
						cell.setValue("0");
					if (isLeafRow(table, cell.getRowIndex()))
						cell.getStyleAttributes().setBackground(LEAF_COLOR);
				} else if (isQuoting) {
					list.add(cell.getValue());
					cell.setValue(null);
				} else if (isAmountSum) {
					if (i == 1 && table.getCell(0, j).getValue() != null)
						table.getCell(0, j).setValue(FDCHelper.ZERO);
					list.add(cell.getValue());
					if (cell.getExpressions() == null)
						cell.setValue("0");
				}

			}
			column.setUserObject(null);
			if (isQuoting || isTotalPriceAmt || isTotalPriceSum || isAmountSum)
				priceCols.put(new Integer(j), list);
		}

		return table;
	}

	/**
	 * 把过滤后的KDTable还原,注意：但未还原"子目名称"对象
	 *
	 * @param table
	 * @return
	 */
	private static void unfilter(KDTable table,List userObjectList,Map priceCols) {
		for (int j = 3; j < table.getColumnCount(); j++) {
			table.getColumn(j).setUserObject(userObjectList.get(j - 3));
			if (priceCols.containsKey(new Integer(j))) {
				List list = (List) priceCols.get(new Integer(j));
				for (int i = 1; i < table.getRowCount(); i++) {
					ICell cell = table.getCell(i, j);
					if (cell.getExpressions() != null)
						continue;
					cell.setValue(list.get(i - 1));
				}
			}
		}
		userObjectList.clear();
		priceCols.clear();

		int col = 0;
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			Object userObj = column.getUserObject();
			if (userObj instanceof NewListingColumnInfo) {
				NewListingColumnInfo columnInfo = (NewListingColumnInfo) userObj;
				if (columnInfo.isIsQuoting()
						&& DescriptionEnum.TotalPriceSum.equals(columnInfo
								.getHeadColumn().getProperty())) {
					col = i;
					break;
				}
			}

		}
		// 颜色还原
		if (col > 0) {
			for (int i = 1; i < table.getRowCount(); i++) {
				if (isLeafRow(table, i))
					table.getCell(i, col).getStyleAttributes().setBackground(
							Color.WHITE);

			}
		}

	}

	/**
	 * 锁定非子科目行
	 *
	 * @param table
	 */
	public static void lockNotLeafRow(KDTable table) {
		for (int i = 0; i < table.getRowCount(); i++) {
			if (!isLeafRow(table, i))
				table.getRow(i).getStyleAttributes().setLocked(true);
		}
	}

	protected void afterSubmitAddNew() {
	}

	/**
	 * 描述：设置表格的树模型：级次及深度
	 * @param table
	 * @Author：owen_wen
	 * @CreateTime：2011-10-29
	 */
	private void setTreeLevel(KDTable table) {
		int maxLevel = 0;
		for (int i = 1; i < table.getRowCount(); i++) { // 第一行为合计行，不需要设置
			int level = ((Integer) table.getRow(i).getCell("level").getValue()).intValue();
			table.getRow(i).setTreeLevel(level);
			 if (level > maxLevel) {
				maxLevel = level;
			}
		}
		table.getTreeColumn().setDepth(maxLevel);
	}
}
