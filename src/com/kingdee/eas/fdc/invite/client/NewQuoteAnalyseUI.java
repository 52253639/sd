/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.biz.agent.DefaultMultiBillWorkAgent;
import com.kingdee.bos.workflow.biz.agent.IMultiBillWorkAgent;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.AssessSetting;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.INewListingColumn;
import com.kingdee.eas.fdc.invite.INewListingEntry;
import com.kingdee.eas.fdc.invite.ListingPageSumEntryCollection;
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
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.PageHeadInfo;
import com.kingdee.eas.fdc.invite.QuotePeopleDisplayEnum;
import com.kingdee.eas.fdc.invite.QuoteStandEnum;
import com.kingdee.eas.fdc.invite.QuotingPriceAppraiseCollection;
import com.kingdee.eas.fdc.invite.QuotingPriceAppraiseFactory;
import com.kingdee.eas.fdc.invite.QuotingPriceAppraiseInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 报价评审 编辑界面
 */
public class NewQuoteAnalyseUI extends AbstractNewQuoteAnalyseUI {
	private static final Color SUMCOLOR = new Color(123, 155, 255);

	private static final Logger logger = CoreUIObject
			.getLogger(NewQuoteAnalyseUI.class);

	private static final Color NON_LEAF_COLOR = new Color(0xF0EDD9);

	private List tables = null;
	
	private boolean isRate = false;

	NewQuotingPriceCollection quotingPrices = null;

	AssessSetting setting = null;

	NewListingInfo listing = null;

	Map pageItemFilter = new HashMap();

	Map columnMap = new HashMap();
	
	QuotingPriceAppraiseInfo editData = null;

	public NewQuoteAnalyseUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		this.quotingPrices = getQuotingPrice();
		if (this.quotingPrices.size() == 0) {
			MsgBox.showInfo("没有选择报价!");
			this.abort();
		}
		listing = getlisting();
		if(listing.getInviteProject() != null){
			isRate = listing.getInviteProject().isIsRate();
		}
		setting = new AssessSetting(listing);
		if(editData==null)
			editData = getQuotingPriceAppraiseInfo();
		super.onLoad();
		this.chkIsShowCompositor.setSelected(true);
		this.chkIsShowDetail.setSelected(true);
		this.chkIsShowPercent.setSelected(true);
		this.actionItemFilter.setEnabled(true);
		this.actionWorkFlowG.setEnabled(true);
		this.menuBar.remove(this.menuHelp);
		this.menuBar.add(this.menuHelp);
		loadPages();
		InviteHelper.setAotuHeight(this.tabbedPnlTable);
		if(!this.getOprtState().equals("FINDVIEW"))
			this.actionSubmit.setVisible(true);
		else
			this.actionSubmit.setVisible(false);
	}
	private QuotingPriceAppraiseInfo getQuotingPriceAppraiseInfo() throws BOSException{
		if(listing==null){
			return null;
		}
		else{
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("inviteListing",listing.getId().toString()));
			view.getSelector().add("id");
			view.getSelector().add("inviteListing");
			view.getSelector().add("state");
			QuotingPriceAppraiseCollection qc = QuotingPriceAppraiseFactory.getRemoteInstance().getQuotingPriceAppraiseCollection(view);
			if(qc!=null&&qc.size()>0){
				return qc.get(0);
			}else{
				QuotingPriceAppraiseInfo info = new QuotingPriceAppraiseInfo();
				info.setInviteListing(listing.getId());
				info.setId(BOSUuid.create(info.getBOSType()));
				info.setNumber(listing.getNumber());
				return info;
			}
		}
	}

	private void setPageItemFilter() throws UIException {
		KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
		NewListingPageInfo page = (NewListingPageInfo) table.getUserObject();
		if(page == null || page.getId() == null){
			MsgBox.showInfo("不能在此页签上进行子目筛选操作！");
			return;
		}
		Map searchMap = (Map) this.pageItemFilter.get(page.getId().toString());
		Map newSearchMap = QuoteAnalyseItemFilterUI.showFilterUI(this, page
				.getHeadType().getId().toString(), searchMap);
		if (newSearchMap != null) {
			this.pageItemFilter.put(page.getId().toString(), newSearchMap);
			this.refresh();
		}

	}

	private NewQuotingPriceCollection getQuotingPrice() throws BOSException {
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("supplier.*");
		String inviteListingId = getInviteListingId();
		filter.getFilterItems().add(new FilterItemInfo("listing", inviteListingId));
		filter.getFilterItems().add(new FilterItemInfo("hasEffected", Boolean.TRUE));
		NewQuotingPriceCollection quotingPrices = NewQuotingPriceFactory.getRemoteInstance()
				.getNewQuotingPriceCollection(view);
		return quotingPrices;
	}

	private String getInviteListingId() {
		String inviteListingId = "";
		if(this.getUIContext().containsKey("isFromWorkflow")){
			if(this.getUIContext().get("ID")!=null)
			{
				String objectID = String.valueOf(getUIContext().get("ID"));
				if(BOSUuid.read(objectID).getType().equals(new QuotingPriceAppraiseInfo().getBOSType())){
					try {
						if(editData==null)
							this.editData = QuotingPriceAppraiseFactory.getRemoteInstance().getQuotingPriceAppraiseInfo(new ObjectUuidPK(objectID));
						inviteListingId = editData.getInviteListing().toString();
					} catch (Exception e) {
						this.handUIException(e);
					}
				}
				else
					inviteListingId = this.getUIContext().get("ID").toString();
			}
			else
				inviteListingId = String.valueOf(this.getUIContext().get("ID"));
			
		}
		else if(this.getUIContext().get("inviteListingId")!=null){
			if(this.getUIContext().get("inviteListingId") instanceof BOSUuid)
				inviteListingId = this.getUIContext().get("inviteListingId").toString();
			else
				inviteListingId = (String)this.getUIContext().get("inviteListingId");
		}
		return inviteListingId;
	}

	/**
	 * 添加提示：当前单据已经审批，不能再提交！
	 * @author owen_wen 2010-12-17
	 */
	protected void doBeforeSubmitForWF(CoreBaseInfo editData) throws Exception
    {
        //2005-8-21
        //在单据提交的时候，发现是AddNew（或者值对象的ID为空），在提交前调用如下方法：
        //由于许多单据没有继承CoreBillEditUI，所以加入这里。 modify 2005-9-8
        
        IMultiBillWorkAgent agent = new DefaultMultiBillWorkAgent();
        agent.markNewBill(this.editData, this.getUIContext());
        
        if (((QuotingPriceAppraiseInfo)editData).getState() != null // 由于没有考虑到是新增提交，单据状态有可能是空的，现已经加上单据状态是否非空。
        		&& FDCBillStateEnum.AUDITTED_VALUE.equals(((QuotingPriceAppraiseInfo)editData).getState().getValue())){
        	FDCMsgBox.showInfo("当前单据已经审批，不能再提交！");
        	SysUtil.abort();
        }
     }
	
	public IObjectPK runSubmit() throws Exception
    {
        return new ObjectUuidPK(this.editData.getId());
    }
	
	protected IObjectValue getValue(IObjectPK pk) throws Exception {
        return this.editData;
    }
	
	private NewListingInfo getlisting() throws Exception {
		String inviteListingId = this.getInviteListingId();

		NewListingInfo listing = null;
		listing = NewListingFactory.getRemoteInstance().getNewListingInfo("select *,inviteProject.* where id='"+inviteListingId+"'");
		
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
		sic.add("columns.headColumn.*");
		sic.add("entrys.*");
		sic.add("sumEntry.*");
		view.getSorter().add(new SorterItemInfo("seq"));
		NewListingPageCollection sheets = NewListingPageFactory
				.getRemoteInstance().getNewListingPageCollection(view);
		listing.getPages().clear();
		listing.getPages().addCollection(sheets);

		INewListingColumn iListingCol = NewListingColumnFactory
				.getRemoteInstance();
		INewListingEntry iListingEntry = NewListingEntryFactory
				.getRemoteInstance();
		for (int i = 0; i < sheets.size(); i++) {
			NewListingPageInfo sheet = sheets.get(i);
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
			NewListingColumnCollection columns = iListingCol
					.getNewListingColumnCollection(view);
			sheet.getColumns().clear();
			sheet.getColumns().addCollection(columns);

			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("item.*");
			view.getSelector().add("values.*");
			view.getSelector().add("values.column.*");
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", sheet.getId().toString()));
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingEntryCollection entrys = iListingEntry
					.getNewListingEntryCollection(view);
			sheet.getEntrys().clear();
			sheet.getEntrys().addCollection(entrys);
		}
		return listing;
	}

	private void loadPages() {
		tabbedPnlTable.removeAll();
		tables = new ArrayList();
		NewListingPageCollection coll = listing.getPages();
		
		// 用来记录供应商自定义在tabbedPnlTable中的位置，用完之后要删除（因为不能隐藏掉，很杯具）
		List indexShouldRemove = new ArrayList(); 
		for (int i = 0; i < coll.size(); i++) {
			NewListingPageInfo page = (NewListingPageInfo) coll.get(i);
			Map searchMap = new HashMap();
			searchMap.put("showType", "showAll");
			pageItemFilter.put(page.getId().toString(), searchMap);
			PageHeadInfo pageHead = page.getPageHead();
			KDTable table = new KDTable();
			tables.add(table);
			table.setName(pageHead.getNumber());
			table.getStyleAttributes().setLocked(true);
			table.setUserObject(page);
			initTable(table);
			tabbedPnlTable.add(table, pageHead.getName());
			if (page.getHeadType().isIsDefined()) {
				indexShouldRemove.add(new Integer(i + 1));
			}
		}
		
		initTotalPageTable();

		// 用完之后删除，因为每个供应商添加的子目不一样，对比没有意义
		for (int i = indexShouldRemove.size() - 1; i >= 0; i--) { 
			tabbedPnlTable.removeTabAt(((Integer) indexShouldRemove.get(i)).intValue());
		}
	}
	
	private void initTotalPageTable() {
		Set quotingId = this.getQuotingId();
		Set supplierId = new HashSet();
		ArrayList name = new ArrayList();
		ArrayList quoting = new ArrayList();
		
		KDTable table = new KDTable();
		table.getStyleAttributes().setWrapText(true);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		table.setName("报价汇总表");
		table.addHeadRows(2);
		IRow row = table.getHeadRow(1);
		table.addColumns(quotingId.size() + 3);
		table.getColumn(0).setKey("number");
		table.getColumn(1).setKey("name");
		table.getColumn(2).setKey("quoting");
		row.getCell(0).setValue("序号");
		row.getCell(1).setValue("项目");
		row.getCell(2).setValue("报价列");
		
		if(quotingId.size() > 0){
			EntityViewInfo view1 = new EntityViewInfo();
			FilterInfo filter1 = new FilterInfo();
			for(Iterator it = quotingId.iterator(); it.hasNext(); ) {
				quoting.add(it.next());
			}
			
			for(int quotingIdNo = 0 ; quotingIdNo < quoting.size();quotingIdNo++){
				NewQuotingPriceCollection newQuotingPriceCollection = null;
				try {
					view1 = new EntityViewInfo();
					filter1 = new FilterInfo();
					filter1.getFilterItems().add(new FilterItemInfo("id", quoting.get(quotingIdNo).toString(), CompareType.EQUALS));
					view1.setFilter(filter1);
					view1.getSelector().add(new SelectorItemInfo("supplier.id"));
					newQuotingPriceCollection = NewQuotingPriceFactory
							.getRemoteInstance().getNewQuotingPriceCollection(view1);
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
				
				if (newQuotingPriceCollection != null && newQuotingPriceCollection.size() > 0) {
					for (int i = 0; i < newQuotingPriceCollection.size(); i++) {
						if (newQuotingPriceCollection.get(i).getSupplier() != null) {
							supplierId.add(newQuotingPriceCollection.get(i).getSupplier().getId().toString());
						}
					}
				}
				
				if(supplierId.size() > 0){
					SupplierCollection supplierCollection = null;
					try {
						view1 = new EntityViewInfo();
						filter1 = new FilterInfo();
						filter1.getFilterItems().add(new FilterItemInfo("id", supplierId, CompareType.INCLUDE));
						view1.setFilter(filter1);
						view1.getSelector().add(new SelectorItemInfo("name"));
						supplierCollection = SupplierFactory.getRemoteInstance().getSupplierCollection(view1);
					} catch (BOSException e) {
						e.printStackTrace();
					}
					
					if (supplierCollection != null && supplierCollection.size() > 0) {
						for (int i = 0; i < supplierCollection.size(); i++) {
							if (supplierCollection.get(i).getName() != null) {
								name.add(supplierCollection.get(i).getName().toString());
							}
						}
					}
				}
				
				table.getColumn(quotingIdNo+3).setKey(quoting.get(quotingIdNo).toString());
				row.getCell(quotingIdNo+3).setValue(name.get(0));
				FDCHelper.formatTableNumber(table, quoting.get(quotingIdNo).toString());
				
				supplierId.clear();
				name.clear();
			}
		}

		table.getHeadMergeManager().mergeBlock(0, 0, 0, 2);
		if(table.getColumnCount() > 3){
			table.getHeadMergeManager().mergeBlock(0, 3, 0, quoting.size() + 2);
			table.getHeadRow(0).getCell(3).setValue("报价人");
		}
		table.getHeadRow(0).getCell(0).setValue("报价汇总表");
		int pageCount = tabbedPnlTable.getTabCount();
		table.addRows(pageCount + 1);
		for (int i = 0; i < pageCount; i++) {
			table.getCell(i, "number").setValue(new Integer(i + 1));
			table.getCell(i, "name").setValue(tabbedPnlTable.getTitleAt(i));
			table.getCell(i, "quoting").setValue("金额（元）");
		}
		table.getCell(pageCount, "name").setValue("小计");
		
		table.getStyleAttributes().setLocked(true);
		this.tabbedPnlTable.add(table, 0);
		tHelper.setCanMoveColumn(false);
		this.updateTotalPageData(tabbedPnlTable, quoting);
	}
	
	/**
	 * 获取报价人集合。
	 * @return 报价人ID。
	 */
	private Set getQuotingId(){
		Set quotingIdSet = new HashSet();
		for (int i = 0; i < this.quotingPrices.size(); i++) {
			NewQuotingPriceInfo quoting = this.quotingPrices.get(i);
			String quotingId = quoting.getId().toString();
			if(!quotingIdSet.contains(quotingId)){
				quotingIdSet.add(quotingId);
			}
		}
		return quotingIdSet;
	}
	
	/**
	 * 合计报价汇总表。
	 * @param tabbedPnlTable 报价汇总表。
	 */
	private void updateTotalPageData(KDTabbedPane tabbedPnlTable, ArrayList quoting) {
		String quotingId = "";
		BigDecimal sum = FDCHelper.ZERO;
		if (tabbedPnlTable.getTabCount() <= 1) {
			return;
		}
		KDTable table = (KDTable) tabbedPnlTable.getComponentAt(0);
		if (!table.getName().equals("报价汇总表")) {
			return;
		}
		
		int pageCount = tabbedPnlTable.getTabCount() - 1;
		for (int i = 1; i < pageCount + 1; i++) {
			KDTable page = (KDTable) tabbedPnlTable.getComponentAt(i);
			for (int j = 0; j < quoting.size(); j++) {
				quotingId = quoting.get(j).toString();
				table.getCell(i-1 , quotingId).setValue(page.getCell(page.getRowCount()-1, quotingId).getValue());
			}
		}

		for (int k = 0; k < quoting.size(); k++) {
			sum = FDCHelper.ZERO;;
			for (int m = 1; m < pageCount + 1; m++) {
				sum = sum.add((BigDecimal)table.getCell(m-1, quoting.get(k).toString()).getValue());
			}
			table.getCell(table.getRowCount()-1 , quoting.get(k).toString()).setValue(sum);
		}
	}

	/***
	 * 
	 * @param table
	 * @return
	 * @deprecated
	 */
	private int getMinEntryLevel(KDTable table) {
		if (table.getRowCount() == -1)
			return -1;

		int minLevel = table.getRow(0).getTreeLevel();
		for (int i = 0, n = table.getRowCount() - 1; i < n; i++) {
			int levelthis = table.getRow(i).getTreeLevel();
			if (levelthis < minLevel) {
				minLevel = levelthis;
			}
		}
		return minLevel;
	}

	private int getMaxEntryLevel(NewListingPageInfo page) {
		NewListingEntryCollection entrys = page.getEntrys();
		int levelmax = 0;
		for (int i = 1; i < entrys.size(); i++) {
			int levelthis = entrys.get(i).getLevel();
			if (levelthis > levelmax) {
				levelmax = levelthis;
			}
		}
		return levelmax;
	}

	private void initTable(KDTable table) {
		tHelper.setCanSetTable(true);
		setTableColumn(table);
		setTableRow(table);
	}

	private void setTableColumn(KDTable table) {
		table.removeColumns();
		NewListingPageInfo page = (NewListingPageInfo) table.getUserObject();
		NewListingColumnCollection columns = page.getColumns();
		if (columns == null || columns.size() == 0)
			return;

		int sizeColumn = columns.size();
		IRow parentRow = table.addHeadRow();
		table.addHeadRow();
		KDTMergeManager mm = table.getHeadMergeManager();
		for (int j = 0; j < sizeColumn; j++) {
			NewListingColumnInfo listColInfo = columns.get(j);
			HeadColumnInfo infoColumn = listColInfo.getHeadColumn();
			if (listColInfo.isIsQuoting()) {
				continue;
			}
			if (infoColumn.getProperty().equals(DescriptionEnum.Amount)	|| infoColumn.getProperty().equals(DescriptionEnum.AmountSum)
					|| infoColumn.getProperty().equals(
							DescriptionEnum.ProjectAmt)) {
				continue;
			}
			IColumn column = table.addColumn();
			column.setKey(infoColumn.getName());
			column.setUserObject(listColInfo);
			logger.debug("index: " + j + ", Name: " + infoColumn.getName());
			logger.debug("tblColindex: " + column.getColumnIndex());
			if (infoColumn.getColumnType() != null&& infoColumn.getColumnType().equals(ColumnTypeEnum.Amount)) {
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			} else if (infoColumn.getColumnType().equals(ColumnTypeEnum.Date)) {
				column.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
			} else if (infoColumn.getColumnType().equals(ColumnTypeEnum.String)) {
				column.getStyleAttributes().setWrapText(true);
				column.getStyleAttributes().setNumberFormat("@");
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			}

			if (infoColumn.getProperty().equals(DescriptionEnum.ProjectAmtSum)&& infoColumn.getName().equals("小计")) {
				parentRow.getCell(infoColumn.getName()).setValue("工程量");
				table.getColumn("小计").setKey("工程量");
				logger.debug("change 小计 to 工程量");
			} else {
				parentRow.getCell(infoColumn.getName()).setValue(infoColumn.getName());
			}
			columnMap.put(table.getName() + column.getKey(), listColInfo);
			mm.mergeBlock(0, column.getColumnIndex(), 1, column.getColumnIndex());
		}

		// 如果有子目编码列，将其移动到最前面
		IColumn column = table.getColumn("子目编码");
		if (column != null) {
			table.setColumnMoveable(true);
			boolean flag = table.moveColumn(column.getColumnIndex(), 0);
			logger.debug("moveSucc:" + flag);
			table.setColumnMoveable(false);
		}

		setQuotingCols(table, mm);

		table.getTreeColumn().setDepth(getMaxEntryLevel(page));
	}

	private void setQuotingCols(KDTable table, KDTMergeManager mm) {
		IRow parentRow = table.getHeadRow(0);
		IRow row = table.getHeadRow(1);

		boolean fHasNumCol = (table.getColumn("子目编码") != null);
		int quotingBaseInd = fHasNumCol ? 4 : 3;
		int quotingColIndex = quotingBaseInd;
		IColumn column = table.addColumn(quotingColIndex);
		column.setWidth(120);
		column.setKey("column");
		parentRow.getCell("column").setValue("报价列");
		mm.mergeBlock(0, column.getColumnIndex(), 1, column.getColumnIndex());
		quotingColIndex++;
		column = table.addColumn(quotingColIndex);
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column.setKey("refPrice");
		NewQuotingPriceInfo nullPriceInfo = new NewQuotingPriceInfo();
		column.setUserObject(nullPriceInfo);
		this.columnMap.put(table.getName() + column.getKey(), nullPriceInfo);
		parentRow.getCell("refPrice").setValue("报价人");
		row.getCell("refPrice").setValue("参考价（元）");

		quotingColIndex++;
		boolean fShowBName = setting.getBidderDisplay().equals(	QuotePeopleDisplayEnum.BidderFullName);
		for (int i = 0; i < this.quotingPrices.size(); i++) {
			column = table.addColumn(quotingColIndex + i);
			NewQuotingPriceInfo priceInfo = quotingPrices.get(i);
			String key = priceInfo.getId().toString();
			column.getStyleAttributes().setHorizontalAlign(	HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			column.setKey(key);
			column.setUserObject(priceInfo);
			this.columnMap.put(table.getName() + column.getKey(), priceInfo);
			String title = fShowBName ? priceInfo.getSupplier().getName()
					: priceInfo.getSupplier().getNumber();
			row.getCell(key).setValue(title);
		}
		mm.mergeBlock(0, ++quotingBaseInd, 0, quotingBaseInd+ quotingPrices.size());
	}

	private boolean isAccordingFilter(NewListingEntryInfo entry,
			NewListingColumnInfo priceSumCol,
			NewListingColumnInfo totalAmountCol, int maxLevel) {
		NewListingColumnInfo aimCol = null;
		if (entry.isIsLeaf()) {
			aimCol = priceSumCol;
		} else {
			aimCol = totalAmountCol;
		}
		Map searchMap = (Map) this.pageItemFilter.get(entry.getHead().getId()
				.toString());
		Map map = new HashMap();
		for (int i = 0; i < entry.getValues().size(); i++) {
			NewListingValueInfo value = entry.getValues().get(i);
			if (value.getType() != null
					&& !value.getType().equals(QuotingPriceTypeEnum.modify)) {
				continue;
			}
			StringBuffer keyId = new StringBuffer(value.getColumn().getHeadColumn().getId().toString());
			if (value.getQuotingPrice() != null) {
				String qutoingPriceId = value.getQuotingPrice().getId()
						.toString();
				keyId.append(qutoingPriceId);
			}
			map.put(keyId.toString(), value);
		}
		String showType = (String) searchMap.get("showType");
		BigDecimal overPro = (BigDecimal) searchMap.get("overPro");
		if (showType.equals("showImportent")) {
			if (!entry.isIsKey()) {
				return false;
			}

		} else if (showType.equals("showOver")) {
			if (!entry.isIsLeaf()) {
				return false;
			}
			// 只显示超出范围的
			boolean hasOverBase = false;
			BigDecimal basePrice = this.getBasePrice(entry.getValues(), aimCol
					.getId().toString(), this.setting.getStand());
			if (basePrice != null && basePrice.compareTo(FDCHelper.ZERO) != 0) {
				for (int j = 0; j < this.quotingPrices.size(); j++) {
					BigDecimal value = FDCHelper.ZERO;
					NewListingValueInfo valueInfo = (NewListingValueInfo) map
							.get(aimCol.getHeadColumn().getId().toString()
									+ this.quotingPrices.get(j).getId()
											.toString());
					if (valueInfo != null) {
						value = valueInfo.getAmount();
					}
					BigDecimal percent = value.multiply(FDCHelper.ONE_HUNDRED)
							.divide(basePrice, 2, BigDecimal.ROUND_HALF_UP);
					if (percent.compareTo(this.setting.getAllowHigh()) > 0
							|| percent.compareTo(this.setting.getAllowLow()) < 0) {
						hasOverBase = true;
						break;
					}
				}
			} else {
				hasOverBase = true;
			}
			if (!hasOverBase) {
				return false;
			}
		} else if (showType.equals("showDetail")) {
			if (!entry.isIsCompose()) {
				return false;
			}
		} else if (showType.equals("showOverAll")) {
			if (!entry.isIsLeaf()) {
				return false;
			}
			boolean hasOverBase = false;
			for (int j = 0; j < this.quotingPrices.size(); j++) {
				BigDecimal allAmount = this.getTotolAmount(null, this.quotingPrices.get(j).getId().toString());
				if (allAmount.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal value = FDCHelper.ZERO;
					NewListingValueInfo valueInfo = (NewListingValueInfo) map.get(totalAmountCol.getHeadColumn()
							.getId().toString()
							+ this.quotingPrices.get(j).getId().toString());
					if (valueInfo != null) {
						value = valueInfo.getAmount();
					}
					BigDecimal percent = value.multiply(FDCHelper.ONE_HUNDRED).divide(allAmount, 2,
							BigDecimal.ROUND_HALF_UP);
					if (percent.compareTo(overPro) > 0) {
						hasOverBase = true;
						break;
					}
				} else {
					hasOverBase = true;
				}
			}
			if (!hasOverBase) {
				return false;
			}

		} else if (showType.equals("showOverSheet")) {
			if (!entry.isIsLeaf()) {
				return false;
			}
			boolean hasOverBase = false;
			for (int j = 0; j < this.quotingPrices.size(); j++) {
				BigDecimal sheetAmount = this.getTotolAmount(entry.getHead().getId().toString(),
						this.quotingPrices.get(j).getId().toString());
				if (sheetAmount.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal value = FDCHelper.ZERO;
					NewListingValueInfo valueInfo = (NewListingValueInfo) map.get(totalAmountCol.getHeadColumn()
							.getId().toString()
							+ this.quotingPrices.get(j).getId().toString());
					if (valueInfo != null) {
						value = valueInfo.getAmount();
					}
					BigDecimal percent = value.multiply(FDCHelper.ONE_HUNDRED).divide(sheetAmount, 2,
							BigDecimal.ROUND_HALF_UP);
					if (percent.compareTo(overPro) > 0) {
						hasOverBase = true;
						break;
					}
				} else {
					hasOverBase = true;
				}
			}
			if (!hasOverBase) {
				return false;
			}
		}

		// 过滤条件
		Set set = searchMap.keySet();
		if (set.size() > 1) {
			for (Iterator iter = set.iterator(); iter.hasNext();) {
				String colId = (String) iter.next();
				if (colId.equals("showType") || colId.equals("overPro")) {
					continue;
				}
				String value = (String) searchMap.get(colId);
				boolean isHave = false;
				if (map.get(colId) != null) {
					NewListingValueInfo valueInfo = (NewListingValueInfo) map
							.get(colId);
					String text = valueInfo.getText();
					if (!StringUtil.isEmptyString(text)) {
						int j = text.indexOf(value);
						if (j != -1) {
							isHave = true;
						}
					}
				}
				for (int i = 0; i < this.quotingPrices.size(); i++) {
					NewQuotingPriceInfo quoting = this.quotingPrices.get(i);
					if (map.get(colId + quoting.getId().toString()) != null) {
						NewListingValueInfo valueInfo = (NewListingValueInfo) map
								.get(colId + quoting.getId().toString());
						String text = valueInfo.getText();
						if (!StringUtil.isEmptyString(text)) {
							int j = text.indexOf(value);
							if (j != -1) {
								isHave = true;
							}
						}
					}
				}
				if (!isHave) {
					return false;
				}
			}
		}
		return true;
	}

	private BigDecimal getTotolAmount(String pageId, String quotingId) {
		// BigDecimal sum = FDCHelper.ZERO;
		// if (pageId == null) {
		// Set set = sheetTotalAmount.keySet();
		// for (Iterator iter = set.iterator(); iter.hasNext();) {
		// Map sheetMap = (Map) iter.next();
		// Set set2 = sheetMap.keySet();
		// for (Iterator iterator = set2.iterator(); iterator.hasNext();) {
		// BigDecimal value = (BigDecimal) iterator.next();
		// if (value != null) {
		// sum = sum.add(value);
		// }
		// }
		// }
		// } else {
		// Map sheetMap = (Map) sheetTotalAmount.get(pageId);
		// Set set2 = sheetMap.keySet();
		// for (Iterator iterator = set2.iterator(); iterator.hasNext();) {
		// BigDecimal value = (BigDecimal) iterator.next();
		// if (value != null) {
		// sum = sum.add(value);
		// }
		// }
		// }
		// return sum;
		NewListingPageCollection pages = this.listing.getPages();
		BigDecimal sum = FDCHelper.ZERO;
		for (int i = 0; i < pages.size(); i++) {
			NewListingPageInfo page = pages.get(i);
			ListingPageSumEntryCollection sumEntry = page.getSumEntry();
			if (pageId != null) {
				if (page.getId().toString().equals(pageId)) {
					for (int j = 0; j < sumEntry.size(); j++) {
						ListingPageSumEntryInfo entry = sumEntry.get(j);
						if (entry.getType().equals(
								QuotingPriceTypeEnum.original)) {
							continue;
						}
						BigDecimal amount = entry.getAmount();
						if (amount == null) {
							amount = FDCHelper.ZERO;
						}
						if (entry.getQuotingPrice() == null) {
							if (quotingId == null) {
								return amount;
							}
						} else {
							if (entry.getQuotingPrice().getId().toString()
									.equals(quotingId)) {
								return amount;
							}
						}
					}
				}
			} else {
				for (int j = 0; j < sumEntry.size(); j++) {
					ListingPageSumEntryInfo entry = sumEntry.get(j);
					if (entry.getType().equals(QuotingPriceTypeEnum.original)) {
						continue;
					}
					BigDecimal amount = entry.getAmount();
					if (amount == null) {
						amount = FDCHelper.ZERO;
					}
					if (entry.getQuotingPrice() == null) {
						if (quotingId == null) {
							sum = sum.add(amount);
						}
					} else {
						if (entry.getQuotingPrice().getId().toString().equals(
								quotingId)) {
							sum = sum.add(amount);
						}
					}
				}
			}
		}
		return sum;
	}

	private void setTableRow(final KDTable table) {
		NewListingPageInfo page = (NewListingPageInfo) table.getUserObject();
		table.removeRows();
		NewListingValueCollection values1 = getListPageValues(page);
		Map values = new HashMap();
		for (int i = 0; i < values1.size(); i++) {
			NewListingValueInfo valueInfo = values1.get(i);
			String entryId = valueInfo.getEntry().getId().toString();
			String colId = valueInfo.getColumn().getId().toString();
			String quotingId = "";
			if (valueInfo.getQuotingPrice() != null) {
				quotingId = valueInfo.getQuotingPrice().getId().toString();
			}
			String key = entryId + colId + quotingId;
			values.put(key, valueInfo);
		}

		int maxLevel = getMaxEntryLevel(page);
		NewListingEntryCollection collEntry = page.getEntrys();
		NewListingColumnCollection quotingCol = new NewListingColumnCollection();
		NewListingColumnInfo totalCol = null;
//		NewListingColumnInfo projectAmtCol = null;
		NewListingColumnInfo priceSumCol = null;
		for (int i = 0; i < page.getColumns().size(); i++) {
			NewListingColumnInfo colInfo = page.getColumns().get(i);
			if (colInfo.isIsQuoting()) {
				quotingCol.add(colInfo);
			}
			if (colInfo.getHeadColumn().getProperty().equals(DescriptionEnum.AmountSum)) {
				totalCol = colInfo;
			}
			if (colInfo.getHeadColumn().getProperty().equals(DescriptionEnum.TotalPriceSum)) {
				priceSumCol = colInfo;
			}
//			if (colInfo.getHeadColumn().getProperty().equals(
//					DescriptionEnum.ProjectAmtSum)) {
//				projectAmtCol = colInfo;
//			}
		}

		int sizeEntry = collEntry.size();
		for (int i = 0; i < sizeEntry; i++) {
			NewListingEntryInfo entry = collEntry.get(i);
			NewListingColumnInfo aimCol = null;
			if (entry.isIsLeaf()) {
				aimCol = priceSumCol;
			} else {
				aimCol = totalCol;
			}
			if (!isAccordingFilter(entry, priceSumCol, totalCol, maxLevel)) {
				continue;
			}
			IRow row = table.addRow();
			row.setUserObject(entry);
			row.setTreeLevel(entry.getLevel());
			if (!entry.isIsLeaf()) {
				row.getStyleAttributes().setBackground(NON_LEAF_COLOR);
			}
			setRowQuoteValues(table, values, totalCol, entry, row);

			if (entry.isIsLeaf()) {
				for (int j = 0; j < quotingCol.size(); j++) {
					NewListingColumnInfo info = quotingCol.get(j);
					if (!this.chkIsShowDetail.isSelected()) {
						if (info.getHeadColumn().getProperty().equals(DescriptionEnum.TotalPrice)
								|| info.getHeadColumn().getProperty().equals(DescriptionEnum.Personal)) {
							continue;
						}
					}
					row = table.addRow();
					row.setUserObject(info);
					row.setTreeLevel(entry.getLevel() + 1);
					for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
						IColumn column = table.getColumn(colIndex);
						if (columnMap.get(table.getName() + column.getKey()) instanceof NewListingColumnInfo) {
							continue;
						} else if (columnMap.get(table.getName()+ column.getKey()) instanceof NewQuotingPriceInfo) {
							NewQuotingPriceInfo quoting = (NewQuotingPriceInfo) columnMap.get(table.getName() + column.getKey());
							String key = entry.getId().toString() + info.getId().toString();
							if (quoting.getId() != null) {
								key += quoting.getId().toString();
							}
							NewListingValueInfo value = (NewListingValueInfo) values.get(key);
							if(isRate){
								row.getCell(colIndex).getStyleAttributes().setNumberFormat("####.0000");
							}
							
							if (value != null) {
								ColumnTypeEnum colType = info.getHeadColumn().getColumnType();
								InviteHelper.loadListingValue(row.getCell(colIndex), value,colType);
							}
						} else {
							if (info.getHeadColumn().getProperty().equals(DescriptionEnum.TotalPriceSum)) {
								row.getCell(colIndex).setValue("综合单价小计（元）");
							} else {
								row.getCell(colIndex).setValue(info.getHeadColumn().getName());
							}
						}
					}
				}
			}

			BigDecimal basePrice = getBasePrice(entry.getValues(), aimCol.getId().toString(), this.setting.getStand());
			boolean isShowCompositor = this.chkIsShowCompositor.isSelected();
			boolean isShowPercent = this.chkIsShowPercent.isSelected();
			// 加入百分比行
			if (isShowPercent) {
				setPercentRow(table, values, entry, aimCol, basePrice);
			}
			// 加入排名行
			// if (entry.getLevel() == maxLevel&&isShowCompositor) {
			if (isShowCompositor) {
				setRankingsRow(table, values, entry, aimCol);

			}

			Map searchMap = (Map) this.pageItemFilter.get(page.getId()
					.toString());
			String showType = (String) searchMap.get("showType");
			if (showType.equals("showOverAll")) {
				row = table.addRow();
				if (!entry.isIsLeaf()) {
					row.getStyleAttributes().setBackground(NON_LEAF_COLOR);
				}
				row.setTreeLevel(entry.getLevel() + 1);
				for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
					IColumn column = table.getColumn(colIndex);
					if (columnMap.get(table.getName() + column.getKey()) instanceof NewListingColumnInfo) {
						continue;
					} else if (columnMap.get(table.getName() + column.getKey()) instanceof NewQuotingPriceInfo) {
						NewQuotingPriceInfo quoting = (NewQuotingPriceInfo) columnMap
								.get(table.getName() + column.getKey());
						if (quoting.getId() != null) {
							BigDecimal allAmount = this.getTotolAmount(null,
									quoting.getId().toString());
							if (allAmount == null
									|| allAmount.compareTo(FDCHelper.ZERO) == 0) {
								row.getCell(colIndex).setValue("总金额为0");
								break;
							}
							NewListingValueInfo valueInfo = (NewListingValueInfo) values
									.get(entry.getId().toString()
											+ totalCol.getId().toString()
											+ quoting.getId().toString());
							BigDecimal value = FDCHelper.ZERO;
							if (valueInfo != null) {
								value = valueInfo.getAmount();
							}
							BigDecimal percent = value.multiply(
									FDCHelper.ONE_HUNDRED).divide(allAmount, 2,
									BigDecimal.ROUND_HALF_UP);
							row.getCell(colIndex).setValue(
									percent.toString() + "%");
						}
					} else {
						row.getCell(colIndex).setValue("占总金额份额");
					}
				}
			}

			if (showType.equals("showOverSheet")) {

				row = table.addRow();
				if (!entry.isIsLeaf()) {
					row.getStyleAttributes().setBackground(NON_LEAF_COLOR);
				}
				row.setTreeLevel(entry.getLevel() + 1);
				for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
					IColumn column = table.getColumn(colIndex);
					if (columnMap.get(table.getName() + column.getKey()) instanceof NewListingColumnInfo) {
						continue;
					} else if (columnMap.get(table.getName() + column.getKey()) instanceof NewQuotingPriceInfo) {
						NewQuotingPriceInfo quoting = (NewQuotingPriceInfo) columnMap
								.get(table.getName() + column.getKey());
						if (quoting.getId() != null) {
							BigDecimal allAmount = this.getTotolAmount(page
									.getId().toString(), quoting.getId()
									.toString());
							if (allAmount == null
									|| allAmount.compareTo(FDCHelper.ZERO) == 0) {
								row.getCell(colIndex).setValue("总金额为0");
								break;
							}
							NewListingValueInfo valueInfo = (NewListingValueInfo) values
									.get(entry.getId().toString()
											+ totalCol.getId().toString()
											+ quoting.getId().toString());
							BigDecimal value = FDCHelper.ZERO;
							if (valueInfo != null) {
								value = valueInfo.getAmount();
							}
							BigDecimal percent = value.multiply(
									FDCHelper.ONE_HUNDRED).divide(allAmount, 2,
									BigDecimal.ROUND_HALF_UP);
							row.getCell(colIndex).setValue(
									percent.toString() + "%");
						}
					} else {
						row.getCell(colIndex).setValue("占页签金额份额");
					}
				}

			}
		}

		setSumRow(table);
		table.getTreeColumn().setDepth(getMaxEntryLevel(page) + 2);
	}

	private void setRowQuoteValues(final KDTable table, Map values, NewListingColumnInfo totalCol, NewListingEntryInfo entry, IRow row) {
		for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
			IColumn column = table.getColumn(colIndex);
			String colMapKey = table.getName() + column.getKey();
			if (columnMap.get(colMapKey) instanceof NewListingColumnInfo) {
				NewListingColumnInfo colInfo = (NewListingColumnInfo) columnMap.get(colMapKey);
				ColumnTypeEnum colType = colInfo.getHeadColumn().getColumnType();
				NewListingValueInfo value = (NewListingValueInfo) values.get(entry.getId().toString()+ colInfo.getId().toString());
				InviteHelper.loadListingValue(row.getCell(colIndex), value,	colType);
			} else if (columnMap.get(colMapKey) instanceof NewQuotingPriceInfo) {
				NewQuotingPriceInfo quoting = (NewQuotingPriceInfo) columnMap.get(colMapKey);
				String key = entry.getId().toString() + totalCol.getId().toString();
				if (quoting.getId() != null) {
					key += quoting.getId().toString();
				}
				NewListingValueInfo value = (NewListingValueInfo) values.get(key);
				if (value != null) {
					BigDecimal amount = value.getAmount();
					if (amount == null) {
						amount = FDCHelper.ZERO;
					}
					row.getCell(colIndex).setValue(amount);
//					row.getCell(colIndex).getStyleAttributes().setNumberFormat("####.0000");
					if (quoting.getId() != null) {
						BigDecimal lowestPrice = getBasePrice(entry.getValues(), totalCol.getId().toString(),QuoteStandEnum.BidderMinPrice);
						BigDecimal highestPrice = getBasePrice(entry.getValues(), totalCol.getId().toString(),null);
						if (amount.compareTo(lowestPrice) == 0) {
							// lowestQuoting = quoting.getId().toString();
							row.getCell(colIndex).getStyleAttributes().setFontColor(setting.getLowColor());
							if (setting.getLowFont() != null) {
								row.getCell(colIndex).getStyleAttributes().setFont(setting.getLowFont());
							}
						}
						if (amount.compareTo(highestPrice) == 0) {
							// highestQuoting = quoting.getId().toString();
							row.getCell(colIndex).getStyleAttributes().setFontColor(setting.getHighColor());
							if (setting.getHighFont() != null) {
								row.getCell(colIndex).getStyleAttributes().setFont(setting.getHighFont());
							}
						}
					}
				}

			} else {
				row.getCell(colIndex).setValue("金额（元）");
			}
		}
	}

	private void setRankingsRow(final KDTable table, Map values, NewListingEntryInfo entry, NewListingColumnInfo aimCol) {
		IRow row;
		row = table.addRow();
		if (!entry.isIsLeaf()) {
			row.getStyleAttributes().setBackground(NON_LEAF_COLOR);
		}
		row.setTreeLevel(entry.getLevel() + 1);
		Map map = new TreeMap();
		for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
			IColumn column = table.getColumn(colIndex);
			if (columnMap.get(table.getName() + column.getKey()) instanceof NewListingColumnInfo) {
				continue;
			} else if (columnMap.get(table.getName() + column.getKey()) instanceof NewQuotingPriceInfo) {
				NewQuotingPriceInfo quoting = (NewQuotingPriceInfo) columnMap
						.get(table.getName() + column.getKey());
				if (quoting.getId() != null) {
					NewListingValueInfo valueInfo = (NewListingValueInfo) values
							.get(entry.getId().toString()
									+ aimCol.getId().toString()
									+ quoting.getId().toString());
					BigDecimal value = FDCHelper.ZERO;
					if (valueInfo != null
							&& valueInfo.getAmount() != null) {
						value = valueInfo.getAmount();
					}
					List sameList = null;
					if (map.containsKey(value)) {
						sameList = (List) map.get(value);
					} else {
						sameList = new ArrayList();
						map.put(value, sameList);
					}
					sameList.add(new Integer(colIndex));
				}
			} else {
				row.getCell(colIndex).setValue("排名");
			}
		}
		int compositor = 1;
		Collection valCol = map.values();
		for (Iterator iter = valCol.iterator(); iter.hasNext();) {
			List sameList = (List) iter.next();
			for (int j = 0; j < sameList.size(); j++) {
				Integer index = (Integer) sameList.get(j);
				row.getCell(index.intValue()).setValue(
						new Integer(compositor));
				row.getCell(index.intValue()).getStyleAttributes()
						.setNumberFormat(FDCHelper.getNumberFtm(0));
			}
			compositor += sameList.size();
		}
	}

	private void setPercentRow(final KDTable table, Map values, NewListingEntryInfo entry, NewListingColumnInfo aimCol, BigDecimal basePrice) {
		IRow row;
		row = table.addRow();
		if (!entry.isIsLeaf()) {
			row.getStyleAttributes().setBackground(NON_LEAF_COLOR);
		}
		row.setTreeLevel(entry.getLevel() + 1);
		for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
			IColumn column = table.getColumn(colIndex);
			if (columnMap.get(table.getName() + column.getKey()) instanceof NewListingColumnInfo) {
				continue;
			} else if (columnMap.get(table.getName() + column.getKey()) instanceof NewQuotingPriceInfo) {
				if (basePrice == null
						|| basePrice.compareTo(FDCHelper.ZERO) == 0) {
					if (setting.getStand().equals(
							QuoteStandEnum.ConsultPrice)) {
						row.getCell(colIndex).setValue("没有基准价");
					} else if (setting.getStand().equals(
							QuoteStandEnum.QuotingPriceAvg)) {
						row.getCell(colIndex).setValue("平均价为0");
					} else {
						row.getCell(colIndex).setValue("最低报价为0");
					}
					break;
				}
				NewQuotingPriceInfo quoting = (NewQuotingPriceInfo) columnMap
						.get(table.getName() + column.getKey());
				if (quoting.getId() != null) {
					NewListingValueInfo valueInfo = (NewListingValueInfo) values
							.get(entry.getId().toString()
									+ aimCol.getId().toString()
									+ quoting.getId().toString());
					BigDecimal value = FDCHelper.ZERO;
					if (valueInfo != null&&valueInfo.getAmount()!=null) {
						value = valueInfo.getAmount();
					}
					BigDecimal percent = value.multiply(
							FDCHelper.ONE_HUNDRED).divide(basePrice, 2,
							BigDecimal.ROUND_HALF_UP);
					row.getCell(colIndex).setValue(
							percent.toString() + "%");
				}
			} else {
				row.getCell(colIndex).setValue("百分比");
			}
		}
	}

	private NewListingValueCollection getListPageValues(NewListingPageInfo page) {
		NewListingValueCollection values1 = null;
		EntityViewInfo view1 = new EntityViewInfo();
		FilterInfo filter1 = new FilterInfo();
		filter1.getFilterItems().add(
				new FilterItemInfo("entry.head", page.getId().toString()));
		filter1.getFilterItems().add(
				new FilterItemInfo("quotingPrice.id", null));
		view1.setFilter(filter1);
		view1.getSelector().add(new SelectorItemInfo("*"));
		view1.getSelector().add(new SelectorItemInfo("entry.*"));
		view1.getSelector().add(new SelectorItemInfo("column.*"));

		NewListingValueCollection values2 = null;
		EntityViewInfo view2 = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(
				new FilterItemInfo("entry.head", page.getId().toString()));
		filter2.getFilterItems().add(
				new FilterItemInfo("type", QuotingPriceTypeEnum.modify));
		Set quotingSet=new HashSet();
		for (int i = 0; i < this.quotingPrices.size(); i++) {
			quotingSet.add(this.quotingPrices.get(i).getId().toString());
		}
		filter2.getFilterItems().add(
				new FilterItemInfo("quotingPrice.id", quotingSet,CompareType.INCLUDE));
		view2.setFilter(filter2);
		view2.getSelector().add(new SelectorItemInfo("*"));
		view2.getSelector().add(new SelectorItemInfo("entry.*"));
		view2.getSelector().add(new SelectorItemInfo("column.*"));

		try {
			values1 = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view1);
			values2 = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view2);
			values1.addCollection(values2);

		} catch (BOSException e) {
			e.printStackTrace();
		}
		return values1;
	}

	private void setSumRow(final KDTable table) {
		IRow sumRow = table.addRow();
		sumRow.setTreeLevel(0);
		sumRow.getCell("子目名称").setValue("合计（元）");
		String unit = "";
		boolean isFirstSet = true;
		BigDecimal prjSum = FDCHelper.ZERO;
		BigDecimal refPrice = FDCHelper.ZERO;

		logger.debug("table " + table.getName());
		for (int i = 0; i < table.getRowCount() - 1; i++) {
			IRow aRow = table.getRow(i);
			if (aRow.getUserObject() instanceof NewListingEntryInfo
					&& ((NewListingEntryInfo) aRow.getUserObject()).isIsLeaf()) {
				String unit2 = (String) aRow.getCell("单位").getValue();
				if (unit2 == null) {
					unit2 = "";
				}
				if (isFirstSet) {
					unit = unit2;
					isFirstSet = false;
				}
				if (unit != null) {
					if (unit2.equals(unit)) {
						if (aRow.getCell("工程量").getValue() != null) {
							prjSum = prjSum.add((BigDecimal) aRow.getCell(
									"工程量").getValue());
						}
					} else {
						unit = null;
					}
				}
			}
			logger.debug("row " + i + ", treeLevel:" + aRow.getTreeLevel());
			Object rowObj = aRow.getUserObject();
			if (rowObj instanceof NewListingEntryInfo
					&& ((NewListingEntryInfo) rowObj).isIsLeaf()) {
				if (aRow.getCell("refPrice").getValue() != null) {
					refPrice = refPrice.add((BigDecimal) aRow.getCell(
							"refPrice").getValue());
					logger.debug("refPrice:"
							+ aRow.getCell("refPrice").getValue() + ", sum: "
							+ refPrice);
				}
			}
		}
		if (unit != null) {
			sumRow.getCell("单位").setValue(unit);
			sumRow.getCell("工程量").setValue(prjSum);
		} else {
			sumRow.getCell("单位").setValue("单位不同,不统计工程量");
		}
		sumRow.getCell("column").setValue("金额（元）");
		sumRow.getCell("refPrice").setValue(refPrice);
//		int minLevel = getMinEntryLevel(table);
// 		logger.debug(" minLevel:" + minLevel);
		for (int i = 0; i < this.quotingPrices.size(); i++) {
			NewQuotingPriceInfo quoting = this.quotingPrices.get(i);
			String quotingId = quoting.getId().toString();
			BigDecimal sum = FDCHelper.ZERO;
			for (int j = 0; j < table.getRowCount() - 1; j++) {
				IRow aRow = table.getRow(j);
				Object rowObj = aRow.getUserObject();
				if (rowObj instanceof NewListingEntryInfo
						&& ((NewListingEntryInfo) rowObj).isIsLeaf()
						&& aRow.getCell(quotingId).getValue() instanceof BigDecimal) {
					BigDecimal value = (BigDecimal) aRow.getCell(quotingId)
							.getValue();
					logger.debug("row," + j + ", value," + value);
					if (value != null) {
						sum = sum.add(value);
					}
				}
			}
			logger.debug("sum," + sum);
			sumRow.getCell(quotingId).setValue(sum);
		}
		sumRow.getStyleAttributes().setBackground(SUMCOLOR);
	}

	private BigDecimal getBasePrice(NewListingValueCollection values,
			String columnId, QuoteStandEnum stand) {

		NewQuotingPriceInfo loweastPriceBidder = null;
		for (int i = 0; i < this.quotingPrices.size(); i++) {
			NewQuotingPriceInfo bidder = this.quotingPrices.get(i);
			if (loweastPriceBidder == null) {
				loweastPriceBidder = bidder;
			} else {
				if (loweastPriceBidder.getTotoalPrice().compareTo(
						bidder.getTotoalPrice()) > 0) {
					loweastPriceBidder = bidder;
				}
			}
		}

		BigDecimal basePrice = null;
		BigDecimal lowestPrice = FDCHelper.MAX_DECIMAL;
		BigDecimal highestPrice = FDCHelper.ZERO;
		BigDecimal avgPrice = FDCHelper.ZERO;
		BigDecimal referencePrice = FDCHelper.ZERO;
		BigDecimal totalLowestPrice = FDCHelper.ZERO;
		int priceCount = 0;
		Map quotingPriceMap = new HashMap();
		for (int k = 0; k < this.quotingPrices.size(); k++) {
			quotingPriceMap.put(this.quotingPrices.get(k).getId().toString(),this.quotingPrices.get(k));
		}
		for (int j = 0; j < values.size(); j++) {
			NewListingValueInfo value = values.get(j);
			if (value.getColumn().getId().toString().equals(columnId)) {
				if (value.getQuotingPrice() != null) {
					if (value.getType() != null	&& !value.getType().equals(QuotingPriceTypeEnum.modify)) {
						continue;
					}
					String quotingId = value.getQuotingPrice().getId().toString();
					if (quotingPriceMap.containsKey(quotingId)) {
						BigDecimal amount = value.getAmount();
						if (amount == null) {
							amount = FDCHelper.ZERO;
						}
						if (amount.compareTo(lowestPrice) < 0) {
							lowestPrice = amount;
						}
						if (amount.compareTo(highestPrice) > 0) {
							highestPrice = amount;
						}
						avgPrice = avgPrice.add(amount);
						if (quotingId.equals(loweastPriceBidder.getId()
								.toString())) {
							totalLowestPrice = amount;
						}
						priceCount++;
					}
				} else {
					referencePrice = value.getAmount();
				}
			}
		}
		if (priceCount < this.quotingPrices.size()) {
			lowestPrice = FDCHelper.ZERO;
		}
		if (quotingPrices.size() != 0) {
			avgPrice = avgPrice.divide(new BigDecimal(String.valueOf(quotingPrices.size())), 2,	BigDecimal.ROUND_HALF_UP);
		}
		if (stand == null) {
			basePrice = highestPrice;
		} else if (QuoteStandEnum.BidderMinPrice.equals(stand)) {
			basePrice = lowestPrice;
		} else if (QuoteStandEnum.QuotingPriceAvg.equals(stand)) {
			basePrice = avgPrice;
		} else if (QuoteStandEnum.LowestTotalPrice.equals(stand)) {
			basePrice = totalLowestPrice;
		} else if (QuoteStandEnum.ConsultPrice.equals(stand)){
			basePrice = referencePrice;
		}
		return basePrice;
	}

	public void loadTableData(KDTable table, NewListingPageInfo page,boolean isIncludePrice, String quotingId) {
		NewListingValueCollection valueCollection = null;
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("entry.head", page.getId().toString());
		// filterInfo.appendFilterItem("type", QuotingPriceTypeEnum.original);
		if (!isIncludePrice) {
			filterInfo.appendFilterItem("column.headColumn.isRefPrice",	Boolean.FALSE);
		} else {
			filterInfo.appendFilterItem("quotingPrice", quotingId);
		}
		viewInfo.setFilter(filterInfo);
		viewInfo.getSelector().add(new SelectorItemInfo("*"));
		viewInfo.getSelector().add(new SelectorItemInfo("entry.*"));
		viewInfo.getSelector().add(new SelectorItemInfo("column.*"));
		try {
			valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(viewInfo);

		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(valueCollection==null)
			return;
		Map values = new HashMap();
		for (int i = 0; i < valueCollection.size(); i++) {
			NewListingValueInfo valueInfo = valueCollection.get(i);
			String entryId = valueInfo.getEntry().getId().toString();
			String colId = valueInfo.getColumn().getId().toString();
			String key = entryId + colId;
			values.put(key, valueInfo);
		}
		for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
			IColumn column = table.getColumn(colIndex);
			NewListingColumnInfo colInfo = (NewListingColumnInfo) columnMap
					.get(table.getName() + column.getKey());
			if (colInfo != null) {
				ColumnTypeEnum colType = colInfo.getHeadColumn().getColumnType();
				for (int rowIndex = 0; rowIndex < table.getRowCount(); rowIndex++) {
					IRow row = table.getRow(rowIndex);
					NewListingEntryInfo entry = (NewListingEntryInfo) row.getUserObject();
					NewListingValueInfo value = (NewListingValueInfo) values.get(entry.getId().toString()+ colInfo.getId().toString());
					if (!colInfo.getHeadColumn().getName().equals("子目名称")) {
						InviteHelper.loadListingValue(row.getCell(colIndex),value, colType);
					}
//					if (value != null
//							&& !colInfo.getHeadColumn().getName()
//									.equals("子目名称")) {
//						if (colType.equals(ColumnTypeEnum.Amount)) {
//							row.getCell(colIndex).setValue(value.getAmount());
//						} else if (colType.equals(ColumnTypeEnum.Date)) {
//							row.getCell(colIndex)
//									.setValue(value.getDateValue());
//						} else {
//							row.getCell(colIndex).setValue(value.getText());
//						}
//					}
				}
			}
		}
	}

	protected void chkIsShowCompositor_actionPerformed(ActionEvent e)
			throws Exception {
		refresh();
	}

	private void refresh() {
		boolean fShowBName = setting.getBidderDisplay().equals(QuotePeopleDisplayEnum.BidderFullName);
		for (int i = 0; i < this.tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			for (int j = 0; j < this.quotingPrices.size(); j++) {
				NewQuotingPriceInfo priceInfo = quotingPrices.get(j);
				String key = priceInfo.getId().toString();
				String title = fShowBName ? priceInfo.getSupplier().getName(): priceInfo.getSupplier().getNumber();
				table.getHeadRow(1).getCell(key).setValue(title);
			}
			this.setTableRow(table);
		}
		InviteHelper.setAotuHeight(this.tabbedPnlTable);
	}

	protected void chkIsShowDetail_actionPerformed(ActionEvent e)
			throws Exception {
		refresh();
	}

	protected void chkIsShowPercent_actionPerformed(ActionEvent e)
			throws Exception {
		refresh();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		this.menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		this.btnItemFilter.setIcon(EASResource.getIcon("imgTbtn_pzjlq"));
		this.menuItemItemFilter.setIcon(EASResource.getIcon("imgTbtn_pzjlq"));
		btnSubmit.setVisible(true);
		this.menuItemWorkFlowG.setIcon(EASResource.getIcon("imgTbtn_flowchart"));
	}

	public void actionQuoteSet_actionPerformed(ActionEvent e) throws Exception {
		IObjectPK orgPK = getOrgPK(this.actionQuoteSet);
		PermissionFactory.getRemoteInstance().checkFunctionPermission(
				new ObjectUuidPK(SysContext.getSysContext()
						.getCurrentUserInfo().getId().toString()), orgPK,
				"inv_quotingPriceAnalyse_appSet");
		
		AssessSetting newSetting = QuoteSetUI.showSetting(this, this.setting);
		if (newSetting != null) {
			this.setting = newSetting;
			refresh();
		}
	}

	public void actionItemFilter_actionPerformed(ActionEvent e)
			throws Exception {
		
		IObjectPK orgPK = getOrgPK(this.actionItemFilter);
		PermissionFactory.getRemoteInstance().checkFunctionPermission(
				new ObjectUuidPK(SysContext.getSysContext()
						.getCurrentUserInfo().getId().toString()), orgPK,
				"inv_quotingPriceAnalyse_itemFilter");
		
		setPageItemFilter();
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		doBeforeSubmitForWF(this.editData);
//		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId().toString());
		getUIContext().put(UIContext.ID,this.listing.getId().toString());
		if(FDCBillStateEnum.AUDITTED.equals(this.editData.getState()))
		{
			if(MsgBox.CANCEL==MsgBox.showConfirm2(this,"请再次确认，进入工作流审批环节或点击取消返回")){
				this.abort();
			}
		}
		QuotingPriceAppraiseFactory.getRemoteInstance().submit(this.editData);
        this.showWorkflowSuccess();
        
	}

	protected KDTextField getNumberCtrl() {
		return null;
	}
	
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		FDCClientHelper.viewDoProccess(this, editData.getId().toString());
	}
}