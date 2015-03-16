/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
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
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnCollection;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.fdc.invite.NewListTempletColumnCollection;
import com.kingdee.eas.fdc.invite.NewListTempletColumnFactory;
import com.kingdee.eas.fdc.invite.NewListTempletColumnInfo;
import com.kingdee.eas.fdc.invite.NewListTempletEntryCollection;
import com.kingdee.eas.fdc.invite.NewListTempletEntryFactory;
import com.kingdee.eas.fdc.invite.NewListTempletEntryInfo;
import com.kingdee.eas.fdc.invite.NewListTempletFactory;
import com.kingdee.eas.fdc.invite.NewListTempletInfo;
import com.kingdee.eas.fdc.invite.NewListTempletPageCollection;
import com.kingdee.eas.fdc.invite.NewListTempletPageFactory;
import com.kingdee.eas.fdc.invite.NewListTempletPageInfo;
import com.kingdee.eas.fdc.invite.NewListTempletValueCollection;
import com.kingdee.eas.fdc.invite.NewListTempletValueFactory;
import com.kingdee.eas.fdc.invite.NewListTempletValueInfo;
import com.kingdee.eas.fdc.invite.PageHeadCollection;
import com.kingdee.eas.fdc.invite.PageHeadInfo;
import com.kingdee.eas.fi.newrpt.client.designer.io.WizzardIO;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 招标清单模板 编辑界面
 */
public class NewListTempletEditUI extends AbstractNewListTempletEditUI {

	private static final String ItemName = "子目名称";

	private static final Logger logger = CoreUIObject
			.getLogger(NewListTempletEditUI.class);

	private static final Color LOCKCOLOR = new Color(0xF0EDD9);

	private static final Color REQUIREDCOLOR = new Color(0, true);

	private static final Color ERR_COLOR = new Color(0xFF, 0xEA, 0x67);

	private CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();

	private transient ICellEditor amtEditor = null;

	private transient ICellEditor dateEditor = null;

	private transient ICellEditor txtEditor = null;

	private transient ICellEditor chkEditor = null;
	
	Map columnObjMap = new HashMap();
	int copyLine = 0;
	HashMap templateColumnObjectMap = new HashMap();
	
	private int userConfigLoadTimes = 0;
	
	private Map isSaveUserConfigMap = new HashMap();

	/**
	 * output class constructor
	 */
	public NewListTempletEditUI() throws Exception {
		super();

		KDFormattedTextField txtAmtFld = new KDFormattedTextField(
				KDFormattedTextField.BIGDECIMAL_TYPE);
		txtAmtFld.setPrecision(2);
		txtAmtFld.setRemoveingZeroInDispaly(false);
		txtAmtFld.setRemoveingZeroInEdit(false);
		txtAmtFld.setNegatived(false);
		txtAmtFld.setMaximumValue(FDCNumberConstants.MAX_VALUE);
		txtAmtFld.setMinimumValue(FDCNumberConstants.MIN_VALUE);
		txtAmtFld.setHorizontalAlignment(SwingConstants.RIGHT);
		txtAmtFld.setSupportedEmpty(true);
		amtEditor = new KDTDefaultCellEditor(txtAmtFld);

		KDDatePicker pkDate = new KDDatePicker();
		dateEditor = new KDTDefaultCellEditor(
				pkDate);

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);

		KDCheckBox chkField = new KDCheckBox();
		chkEditor = new KDTDefaultCellEditor(chkField);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		setColumnObject();
		KDTable desTable = (KDTable) this.tabbedPnlTable.getComponentAt(0);
		for (int i = 0; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) tabbedPnlTable.getComponentAt(i);
			NewListTempletPageInfo page = (NewListTempletPageInfo) table
					.getUserObject();
			if (page != null) {
				page.setDescription((String) desTable.getCell(i - 2, "des")
						.getValue());
				page.getEntrys().clear();
				for (int j = 0; j < table.getRowCount(); j++) {
					IRow row = table.getRow(j);
					if (row.getUserObject() instanceof NewListTempletEntryInfo) {
						NewListTempletEntryInfo entryInfo = new NewListTempletEntryInfo();
						NewListTempletEntryInfo tempInfo = (NewListTempletEntryInfo) row
								.getUserObject();
						
						entryInfo.setHead(page);
						entryInfo.setLevel(Integer.parseInt(row
								.getCell("level").getValue().toString()));
						if (row.getCell(ItemName).getValue() instanceof ListingItemInfo) {
							ListingItemInfo item = (ListingItemInfo) row
									.getCell(ItemName).getValue();
							entryInfo.setItem(item);
							entryInfo.setItemName(item.getName());
							entryInfo.setItemNumber(item.getNumber());
						} else {
							if (row.getCell(ItemName).getValue() != null)
								entryInfo.setItemName(row.getCell(ItemName)
										.getValue().toString());
							// if (row.getCell("子目编码").getValue() != null)
							// entryInfo.setItemNumber(row.getCell("子目编码")
							// .getValue().toString());
						}
						entryInfo.setSeq(j);
						entryInfo.setIsLeaf(tempInfo.isIsLeaf());
						if (row.getCell("isKey").getValue() != null)
							entryInfo.setIsKey(((Boolean) row.getCell("isKey")
									.getValue()).booleanValue());
						else
							entryInfo.setIsKey(false);
						if (row.getCell("isCompose").getValue() != null){
							entryInfo.setIsCompose(((Boolean) row.getCell(
							"isCompose").getValue()).booleanValue());
						}
							
						else{
							entryInfo.setIsCompose(false);
						}
							
						//设置是否是零星项目行
						if(tempInfo.isIsCanZeroProAmount()){
							entryInfo.setIsCanZeroProAmount(tempInfo.isIsCanZeroProAmount());
						}
						else{
							entryInfo.setIsCanZeroProAmount(false);
						}
						
						NewListTempletValueCollection valueColl = getRowValues(
								row, table, entryInfo);
						entryInfo.getValues().addCollection(valueColl);
						entryInfo.setIsLeaf(this.isLeafRow(table, j));
						page.getEntrys().add(entryInfo);
					}
				}
			}
		}
		// 如果不调用以下语句，修改保存时，备注信息保存不上。Added by Owen_wen 2011-10-24
		super.storeFields(); 
	}

	private NewListTempletValueCollection getRowValues(IRow row, KDTable table,
			NewListTempletEntryInfo entryInfo) {
		int colCount = table.getColumnCount();
		NewListTempletValueCollection valueColl = new NewListTempletValueCollection();
		NewListTempletValueInfo valueInfo = null;
		NewListTempletColumnInfo columnInfo = null;
		for (int i = 3; i < colCount; i++) {
			valueInfo = new NewListTempletValueInfo();
			IColumn column = table.getColumn(i);
			if (column.getUserObject() instanceof NewListTempletColumnInfo) {
				columnInfo = (NewListTempletColumnInfo) column.getUserObject();
			}
			else{
				columnInfo = (NewListTempletColumnInfo) this.templateColumnObjectMap.get(table.getName()+column.getKey());
			}
			if(columnInfo==null){
				MsgBox.showError("保存失败!");
				this.abort();
			}
			valueInfo.setColumn(columnInfo);
			valueInfo.setSeq(i - 3);
			// }
			valueInfo.setEntry(entryInfo);
			Object cellVal = row.getCell(i).getValue();
			if (cellVal == null)
				continue;

			if (ItemName.equals(column.getKey())) {
				if (cellVal instanceof ListingItemInfo) {
					ListingItemInfo item = (ListingItemInfo) cellVal;
					valueInfo.setText(item.getName());
				} else {
					valueInfo.setText(cellVal.toString());
				}
			} else {
				ColumnTypeEnum colType = columnInfo.getHeadColumn()
						.getColumnType();
				if (colType.equals(ColumnTypeEnum.Amount)) {
					BigDecimal value = FDCNumberHelper
							.toBigDecimal(cellVal);
					valueInfo.setAmount(value);
				} else if (colType.equals(ColumnTypeEnum.Date)) {
					valueInfo.setDateValue((Date) cellVal);
				} else {
					valueInfo.setText(cellVal.toString());
				}
			}
			valueColl.add(valueInfo);
			
		}
		return valueColl;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		NewListTempletInfo templet = new NewListTempletInfo();
		Map context = this.getUIContext();
		if (context.get("type") instanceof InviteTypeInfo) {
			InviteTypeInfo inviteTypeInfo = (InviteTypeInfo) context
					.get("type");
			if (inviteTypeInfo.isIsLeaf()) {
				templet.setInviteType(inviteTypeInfo);
			}
		}
		CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
				.getCurrentCostUnit();
		templet.setOriOrg(currentOrg.castToFullOrgUnitInfo());
		templet.setState(FDCBillStateEnum.SAVED);
		templet.setId(BOSUuid.create(templet.getBOSType()));
		// HeadColumnCollection columns = (TempletColumnCollection) handler
		// .getInitColumn();
		// templet.getColumns().addCollection(columns);
		return templet;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return NewListTempletFactory.getRemoteInstance();
	}

	public void actionChoosePage_actionPerformed(ActionEvent e)
			throws Exception {
		setColumnObject();
		if (this.tabbedPnlTable.getTabCount() > 2) {
			if (MsgBox.showConfirm2(this, "已经设置了页签,重新设定将清除以前页签和数据,是否继续?") == MsgBox.CANCEL) {
				return;
			}
		}
		UIContext uiContext = new UIContext(this);
		// uiContext.put("tree", tree);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(PageChooseUI.class.getName(), uiContext, null, "View");
		uiWindow.show();

		if (((PageChooseUI) uiWindow.getUIObject()).isOk()) {
			editData.getPages().clear();
			for(int i=this.tabbedPnlTable.getTabCount()-1;i>1;i--)
				tabbedPnlTable.remove(i);
			PageHeadCollection coll = ((PageChooseUI) uiWindow.getUIObject())
					.getSelected();
			int size = coll.size();
			for (int i = 0; i < size; i++) {
				NewListTempletPageInfo pageInfo = new NewListTempletPageInfo();
				PageHeadInfo pageHead = (PageHeadInfo) coll.get(i);
				pageInfo.setPageHead(pageHead);
				pageInfo.setId(BOSUuid.create(pageInfo.getBOSType()));
				pageInfo.setTempletHead(editData);
				final KDTable table = new KDTable();
				table.setUserObject(pageInfo);
				enableAutoAddLine(table);
				//tHelper.
//				tHelper.tables.put(pageHead.getName(),table);
//				tHelper.uiTables.put(table,pageHead.getName());
				table.setName(pageHead.getName());
				this.tabbedPnlTable.add(table, pageHead.getName());
				//tHelper.init();
				tHelper.setCanSetTable(true);
				tHelper.getUiTables().put(table,pageHead.getName());
				//tHelper.findAllTables(new Component[]{tabbedPnlTable});
				setTableMenu(table);
				editData.getPages().add(pageInfo);
			}
			this.updateTotalPageData();
			initPageDesTableRows();
			//initUserConfig();
//			tHelper.init();
//			initTotalPageTable();
//			initPageDesTable();
//			setTablesMenu();
			
			if (tabbedPnlTable.getTabCount() > 2) {
				// select the first added page
				tabbedPnlTable.setSelectedIndex(2);
			}
		} else {
			return;
		}
	}

	private void initTotalPageTable() {
		KDTable table = new KDTable();
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
		row.getCell(2).setValue("金额（元）");
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
		table.getCell(pageCount + 1, "amount").getStyleAttributes().setLocked(
				true);
		table.getCell(pageCount + 2, "name").setValue("税金（元）");
		table.getCell(pageCount + 2, "amount").getStyleAttributes().setLocked(
				true);
		table.getCell(pageCount + 3, "name").setValue("总造价（元）");
		table.getCell(pageCount + 3, "amount").getStyleAttributes().setLocked(
				true);
		table.getColumn(2).setEditor(amtEditor);
		FDCHelper.formatTableNumber(table, "amount");
		this.tabbedPnlTable.add(table, 0);
		this.updateTotalPageData();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		//20101103 zhougang
		//当选择的表头为供应商自定义时，甲方将不能对此表头进行任何操作。
		NewListTempletPageInfo info = editData.getPages().get(tabbedPnlTable.getSelectedIndex() - 2);
		contorlPageFunction(info);
		
		/**
		 * 页签改变时，功能控制。
		 */
		tabbedPnlTable.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				//20101012 zhougang
				//当选择的表头为供应商自定义时，甲方将不能对此表头进行任何操作。
				NewListTempletPageInfo info = editData.getPages().get(tabbedPnlTable.getSelectedIndex() - 2);
				contorlPageFunction(info);
			}
		});	
		
		this.actionSubmit.setVisible(false);
		this.actionAttachment.setVisible(true);
		this.actionAttachment.setEnabled(true);
		this.menuSubmitOption.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.menuView.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		
		this.actionMultiapprove.setEnabled(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionNextPerson.setEnabled(false);
		this.actionWorkFlowG.setEnabled(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionWorkflowList.setVisible(false);
		this.actionWorkflowList.setEnabled(false);
		this.actionAuditResult.setVisible(false);
		this.actionAuditResult.setEnabled(false);
		initUserConfig();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				InviteHelper.setAotuHeight(tabbedPnlTable);
			}
		});
	}

	public void actionChooseColumn_actionPerformed(ActionEvent e)
			throws Exception {
		setColumnObject();
		if (editData.getPages().size() <= 0) {
			MsgBox.showError("请先选择页签！");
			return;
		}
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			return;
		}
		if (table.getColumnCount() > 0) {
			if (MsgBox.showConfirm2(this, "页签已经设置了表头,重新设置将清除原来的列和数据,是否继续?") == MsgBox.CANCEL) {
				return;
			}
		}
		UIContext uiContext = new UIContext(this);
		// uiContext.put("tree", tree);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(HeadTypeChooseUI.class.getName(), uiContext, null,
						"View");
		uiWindow.show();
		if (((HeadTypeChooseUI) uiWindow.getUIObject()).isOk()) {
			NewListTempletPageInfo info = editData.getPages().get(
					tabbedPnlTable.getSelectedIndex() - 2);
			HeadColumnCollection coll = ((HeadTypeChooseUI) uiWindow
					.getUIObject()).getSortHeadColumns();
			int size = coll.size();
			if (size > 0) {
				table.removeColumns();
				info.getTempletColumns().clear();
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
				colIsKey.setEditor(chkEditor);
				row.getCell("isKey").setValue("关键子目");
				IColumn colIsCompose = table.addColumn(2);
				colIsCompose.setKey("isCompose");
				colIsCompose.setWidth(60);
				colIsCompose.setEditor(chkEditor);
				row.getCell("isCompose").setValue("单价构成");
				int count = 0;
				KDTMergeManager mm = table.getHeadMergeManager();
				mm.mergeBlock(0, 0, 1, 0);
				mm.mergeBlock(0, 1, 1, 1);
				mm.mergeBlock(0, 2, 1, 2);
				int baseCount = 3;
				for (int i = 0; i < size; i++) {
					HeadColumnInfo infoColumn = coll.get(i);
					NewListTempletColumnInfo columnInfo = new NewListTempletColumnInfo();
					columnInfo.setId(BOSUuid.create(columnInfo.getBOSType()));
					columnInfo.setIsQuoting(infoColumn.isIsQuoting());
					columnInfo.setHeadColumn(infoColumn);
					columnInfo.setPage(info);
					columnInfo.setSeq(i);
					info.setHeadType(infoColumn.getHeadType());
					info.getTempletColumns().add(columnInfo);
					IColumn column = table.addColumn();
					column.setKey(infoColumn.getName());
					column.setUserObject(columnInfo);
					this.templateColumnObjectMap.put(table.getName()+infoColumn.getName(),columnInfo);
					if (infoColumn.getColumnType()
							.equals(ColumnTypeEnum.Amount)) {
						column.setEditor(amtEditor);
						column.getStyleAttributes().setNumberFormat(
								"#,##0.00;-#,##0.00");
						column.getStyleAttributes().setHorizontalAlign(
								HorizontalAlignment.RIGHT);
					} else if (infoColumn.getColumnType().equals(
							ColumnTypeEnum.Date)) {
						column.setEditor(dateEditor);
						column.getStyleAttributes().setNumberFormat(
								"yyyy-m-d");
					} else if (infoColumn.getColumnType().equals(
							ColumnTypeEnum.String)) {
						column.setEditor(txtEditor);
						column.getStyleAttributes().setWrapText(true);
					}
					if (infoColumn.isIsQuoting()) {
						column.getStyleAttributes().setLocked(true);
					}
					row.getCell(i + baseCount).setValue(infoColumn.getName());
					if ((infoColumn.getParent() != null)) {
						parentRow.getCell(i + baseCount).setValue(
								infoColumn.getParent().getName());
						if (i < size - 1) {
							HeadColumnInfo infoNext = coll.get(i + 1);
							if (infoNext.getParent() != null
									&& infoColumn.getParent().equals(
											infoNext.getParent())) {
								count++;
							} else {
								if (count == 0) {
									mm.mergeBlock(0, i + baseCount, 1, i
											+ baseCount,
											KDTMergeManager.SPECIFY_MERGE);
								} else {
									mm.mergeBlock(0, i - count + baseCount, 0,
											i + baseCount,
											KDTMergeManager.DATA_UNIFY);
									count = 0;
								}
							}
						} else {
							mm.mergeBlock(0, i - count + baseCount, 0, i
									+ baseCount, KDTMergeManager.DATA_UNIFY);
							count = 0;
						}
					} else {
						mm.mergeBlock(0, i + baseCount, 1, i + baseCount);
					}
				}
				
				if (table.getColumnCount() != 0) {
					IRow totalRow = table.addRow();
					totalRow.getCell(3).setValue("合计");
					totalRow.getStyleAttributes().setBackground(Color.lightGray);
					totalRow.getStyleAttributes().setLocked(true);
				}
				disableAutoAddLine(table);
				setTableEvent(table);
				setUnionData(table);
			}

			//20101012 zhougang
			//当选择的表头为供应商自定义时，甲方将不能对此表头进行任何操作。
			this.contorlPageFunction(info);
		}
		initUserConfig();
	}
	
	/**
	 * 当选择的表头为供应商自定义时，甲方将不能对此表头进行任何操作。
	 * @param info 页签信息。
	 */
	private void contorlPageFunction(NewListTempletPageInfo info){
		KDTable table = (KDTable)this.tabbedPnlTable.getSelectedComponent();
		if(info != null && info.getHeadType() != null){
			if(info.getHeadType().isIsDefined()){
				actionAddLine.setEnabled(false);
				actionInsertLine.setEnabled(false);
				actionRemoveLine.setEnabled(false);
				actionUpLine.setEnabled(false);
				actionDownLine.setEnabled(false);
				actionAllSelect.setEnabled(false);
				actionNoneSelect.setEnabled(false);
				actionImportExcel.setEnabled(false);
				table.getColumn(1).getStyleAttributes().setHided(true);
				table.getColumn(2).getStyleAttributes().setHided(true);
			}else{
				actionAddLine.setEnabled(true);
				actionInsertLine.setEnabled(true);
				actionRemoveLine.setEnabled(true);
				actionUpLine.setEnabled(true);
				actionDownLine.setEnabled(true);
				actionAllSelect.setEnabled(true);
				actionNoneSelect.setEnabled(true);
				actionImportExcel.setEnabled(true);
			}
		}else{
			actionAddLine.setEnabled(true);
			actionInsertLine.setEnabled(true);
			actionRemoveLine.setEnabled(true);
			actionUpLine.setEnabled(true);
			actionDownLine.setEnabled(true);
			actionAllSelect.setEnabled(true);
			actionNoneSelect.setEnabled(true);
			actionImportExcel.setEnabled(true);
		}
	}
	
	public void setTableConfig(){
		for(int i=0;i<this.tabbedPnlTable.getTabCount();i++){
			KDTable table = (KDTable)this.tabbedPnlTable.getComponentAt(i);
			if(isSaveUserConfigMap.containsKey(table.getName())){
				if(((Boolean)isSaveUserConfigMap.get(table.getName())).booleanValue()){
					tHelper.saveConfig(table);
				}
			}
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		setTableConfig();
		setColumnObject();
		int selectedIndex = this.tabbedPnlTable.getSelectedIndex();
		//this.setOprtState("EDIT");
		super.actionSubmit_actionPerformed(e);
		this.tabbedPnlTable.setSelectedIndex(selectedIndex);
		//清楚缓存
		CacheServiceFactory.getInstance().discardType(new NewListTempletPageInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListTempletColumnInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListTempletEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListTempletValueInfo().getBOSType());
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		setTableConfig();
		this.editData.setName(this.txtName.getText());
		setColumnObject();
		int selectedIndex = this.tabbedPnlTable.getSelectedIndex();
		
		super.actionSave_actionPerformed(e);
		this.tabbedPnlTable.setSelectedIndex(selectedIndex);
		
		tHelper.getActionSave((KDTable)this.tabbedPnlTable.getComponentAt(selectedIndex)).actionPerformed(e);
		//		清楚缓存
		CacheServiceFactory.getInstance().discardType(new NewListTempletPageInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListTempletColumnInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListTempletEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListTempletValueInfo().getBOSType());
		
	}
	protected void beforeStoreFields(ActionEvent e) throws Exception {		
		
	}

	public void loadFields() {
		super.loadFields();
		loadPages();
		actionPre.setVisible(false);
		actionNext.setVisible(false);
		actionFirst.setVisible(false);
		actionLast.setVisible(false);
		if (this.getOprtState().equals("VIEW")) {
			this.actionChoosePage.setEnabled(false);
			this.actionChooseColumn.setEnabled(false);
			this.actionUpLine.setEnabled(false);
			this.actionDownLine.setEnabled(false);
			this.actionAllSelect.setEnabled(false);
			this.actionNoneSelect.setEnabled(false);
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			
			this.actionCutLine.setEnabled(false);
			this.actionCopyLine.setEnabled(false);
			this.actionPasteLine.setEnabled(false);
			
			this.actionImportExcel.setEnabled(false);
			if (!editData.getOrgUnit().getId().toString().equals(
					this.currentOrg.getId().toString())) {
				this.actionEdit.setEnabled(false);
				this.actionAddNew.setEnabled(false);
			}

			for (int i = 0; i < tabbedPnlTable.getTabCount(); i++) {
				KDTable tbl = (KDTable) tabbedPnlTable.getComponentAt(i);
				tbl.getStyleAttributes().setLocked(true);
			}
		} else {
			this.actionChoosePage.setEnabled(true);
			this.actionChooseColumn.setEnabled(true);
			this.actionUpLine.setEnabled(true);
			this.actionDownLine.setEnabled(true);
			this.actionAllSelect.setEnabled(true);
			this.actionNoneSelect.setEnabled(true);
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
			
			this.actionCutLine.setEnabled(true);
			this.actionCopyLine.setEnabled(true);
			this.actionPasteLine.setEnabled(true);
			
			this.actionImportExcel.setEnabled(true);
		}
		// 设置UserObject
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setColumnObject();
			}
		});
//		Set set = columnObjMap.keySet();
//		for (Iterator iter = set.iterator(); iter.hasNext();) {
//			IColumn column = (IColumn) iter.next();
//			column.setUserObject(columnObjMap.get(column));
//		}
		this.storeFields();
		
	}
	public void setColumnObject(){
		
		Set set = columnObjMap.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			IColumn column = (IColumn) iter.next();
			column.setUserObject(columnObjMap.get(column));
		}
		
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		super.actionEdit_actionPerformed(e);
		this.actionChoosePage.setEnabled(true);
		this.actionChooseColumn.setEnabled(true);
		this.actionUpLine.setEnabled(true);
		this.actionDownLine.setEnabled(true);
		this.actionAllSelect.setEnabled(true);
		this.actionNoneSelect.setEnabled(true);
		this.actionAddLine.setEnabled(true);
		this.actionInsertLine.setEnabled(true);
		this.actionRemoveLine.setEnabled(true);
		this.actionCutLine.setEnabled(true);
		this.actionCopyLine.setEnabled(true);
		this.actionPasteLine.setEnabled(true);
		this.actionImportExcel.setEnabled(true);
		actionSave.setEnabled(true);

		int tabCount = tabbedPnlTable.getTabCount();
		KDTable tblDesc = (KDTable) tabbedPnlTable.getComponentAt(0);
		tblDesc.getColumn(1).getStyleAttributes().setLocked(false);
		KDTable tblSum = (KDTable) tabbedPnlTable.getComponentAt(1);
		// -2 + 1: 2前面两个页签是汇总，后面的是添加的，1小计行
		tblSum.getCell(tabCount - 1, "amount").getStyleAttributes()
				.setLocked(false);

		if (tabCount <= 2)
			return;
		for (int i = 2, n = tabbedPnlTable.getTabCount() - 1; i <= n; i++) {
			KDTable tbl = (KDTable) tabbedPnlTable.getComponentAt(i);
			tbl.getStyleAttributes().setLocked(false);
			setTableLock(tbl);
		}
		
		//20101103 zhougang
		//当选择的表头为供应商自定义时，甲方将不能对此表头进行任何操作。
		NewListTempletPageInfo info = editData.getPages().get(tabbedPnlTable.getSelectedIndex() - 2);
		contorlPageFunction(info);
	}

	private void loadPages() {
		tabbedPnlTable.removeAll();
		NewListTempletPageCollection coll = editData.getPages();
		int size = coll.size();
		for (int i = 0; i < size; i++) {
			NewListTempletPageInfo page = (NewListTempletPageInfo) coll.get(i);
			PageHeadInfo pageHead = page.getPageHead();
			KDTable table = new KDTable();
			table.setName(pageHead.getName());
			initTable(table, page);
			loadTableData(table, page);
			this.setUnionData(table);
			this.tabbedPnlTable.add(table, pageHead.getName());
		}
		initTotalPageTable();
		initPageDesTable();
		initUserConfig();
		if(!this.getOprtState().equals(STATUS_VIEW))
			InviteHelper.setAotuHeight(this.tabbedPnlTable);
	}

	private void initPageDesTable() {
		KDTable table = new KDTable();
		table.removeRows();
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
		// if (this.getOprtState().equals("VIEW")) {
//		table.getColumn(1).getStyleAttributes().setLocked(true);
		// }
		KDTextField txtField = new KDTextField();
		txtField.setMaxLength(900);
		ICellEditor editor = new KDTDefaultCellEditor(txtField);
		table.getColumn("des").setEditor(editor);
		row.getCell(0).setValue("页签名称");
		row.getCell(1).setValue("描述");
		
		
		
		this.tabbedPnlTable.add(table, 0);
		initPageDesTableRows();
	}
	private void initPageDesTableRows(){
		KDTable table = (KDTable)this.tabbedPnlTable.getComponentAt(0);
		if(!table.getName().equals("招标说明")){
			return;
		}
		int pageCount = tabbedPnlTable.getTabCount();
		table.removeRows();
		table.addRows(pageCount - 2);
		for (int i = 2; i < pageCount; i++) {
			KDTable aTable = (KDTable) tabbedPnlTable.getComponentAt(i);
			NewListTempletPageInfo page = (NewListTempletPageInfo) aTable
					.getUserObject();
			if (page != null) {
				table.getCell(i-2, "name").setValue(page.getPageHead().getName());
				ICell cell = null;
				cell = table.getCell(i-2, "des");;
				final KDBizMultiLangArea textField = new KDBizMultiLangArea();
				
				textField.setMaxLength(1000);
				textField.setAutoscrolls(true);
				textField.setEditable(true);
				textField.setToolTipText("Alt+Enter换行");
				KDTDefaultCellEditor editor = new KDTDefaultCellEditor(textField);
				cell.setEditor(editor);
				cell.getStyleAttributes().setVerticalAlign(VerticalAlignment.TOP);
				cell.getStyleAttributes().setWrapText(true);
				table.getCell(i-2, "des").setValue(page.getDescription());
			}
		}
	}

	public void loadTableData(KDTable table, NewListTempletPageInfo page) {
		NewListTempletValueCollection valueCollection = null;
		if (page.getId() != null) {
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.appendFilterItem("entry.head.id", page.getId()
					.toString());
			viewInfo.setFilter(filterInfo);
			viewInfo.getSelector().add(new SelectorItemInfo("*"));
			viewInfo.getSelector().add(new SelectorItemInfo("entry.*"));
			viewInfo.getSelector().add(new SelectorItemInfo("column.*"));
			try {
				valueCollection = NewListTempletValueFactory
						.getRemoteInstance().getNewListTempletValueCollection(
								viewInfo);

			} catch (BOSException e) {
				e.printStackTrace();
			}
		} else {
			valueCollection = new NewListTempletValueCollection();
			NewListTempletEntryCollection entries = page.getEntrys();
			if (entries != null && entries.size() > 0) {
				for (int i = 0, n = entries.size(); i < n; i++) {
					NewListTempletEntryInfo entryInfo = entries.get(i);
					valueCollection.addCollection(entryInfo.getValues());
				}
			}
		}

		Map values = new HashMap();
		if(valueCollection==null)
			return;
		for (int i = 0; i < valueCollection.size(); i++) {
			NewListTempletValueInfo valueInfo = valueCollection.get(i);
			if (valueInfo.getEntry() != null
					&& valueInfo.getEntry().getId() != null
					&& valueInfo.getColumn() != null
					&& valueInfo.getColumn().getId() != null) {
				String entryId = valueInfo.getEntry().getId().toString();
				String colId = valueInfo.getColumn().getId().toString();
				String key = entryId + colId;
				values.put(key, valueInfo);
			}
		}
		for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
			IColumn column = table.getColumn(colIndex);
			NewListTempletColumnInfo colInfo = null;
			
			if (column.getUserObject() instanceof NewListTempletColumnInfo) {
				colInfo = (NewListTempletColumnInfo) column.getUserObject();
			}
			else{
				colInfo = (NewListTempletColumnInfo) this.templateColumnObjectMap.get(table.getName()+column.getKey());
			}
			
			if (colInfo != null
					&& !colInfo.getHeadColumn().getName().equals(ItemName)) {
				ColumnTypeEnum colType = colInfo.getHeadColumn()
						.getColumnType();
				for (int rowIndex = 1; rowIndex < table.getRowCount(); rowIndex++) {
					IRow row = table.getRow(rowIndex);
					NewListTempletEntryInfo entry = (NewListTempletEntryInfo) row
							.getUserObject();
					NewListTempletValueInfo value = null;
					if (entry.getId() != null && colInfo.getId() != null) {
						value = (NewListTempletValueInfo) values.get(entry
								.getId().toString()
								+ colInfo.getId().toString());
					} else {
						NewListTempletValueCollection vals = entry.getValues();
						if (vals != null) {
							for (int ic = 0, in = vals.size(); ic < in; ic++) {
								NewListTempletValueInfo valInfo = vals.get(ic);
								if (valInfo.getColumn().equals(colInfo))
									value = valInfo;
							}
						}
					}
					InviteHelper.loadTempletValue(row.getCell(colIndex), value,
							colType);
//					if (value != null) {
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
	protected void showSubAttacheMent(AttachmentUIContextInfo info){
		return;
	}
	
	
	

	private void initTable(final KDTable table, NewListTempletPageInfo page) {
		table.setUserObject(page);
		tHelper.setCanSetTable(true);
		tHelper.setCanMoveColumn(false);
		
		setTableEvent(table);
		//if (!this.getOprtState().equals("VIEW")) {
			setTableMenu(table);
		//}
		setTableColumn(table, page);
		setTableRow(table, page);
		
	}
	/***
	 * 覆盖此函数，支持用户保存多个样式，每个模版保留不同的样式
	 */
	public Object getTablePreferenceSchemaKey(){
		/****
		 * 记录加载的次数
		 * 这里首先自动加载两次,此两次加载,此editUI的默认设置
		 * 然后,onload之后,再次显示调用iniUserConfig
		 * 这样,恢复默认设置就可以使用了,相当于,设置第一次加载的是默认设置
		 */
		
		if(userConfigLoadTimes < 2 )
		{
			userConfigLoadTimes ++;
			return "";
		}
		String returnString = "";
		if(this.getOprtState().equals("ADDNEW")&&this.txtNumber.getText().equals("")){
			KDTable table = (KDTable)this.tabbedPnlTable.getSelectedComponent();
			isSaveUserConfigMap.put(table.getName(),Boolean.TRUE);
			SysUtil.abort();			
		}
		if(this.txtNumber == null ){
			returnString = "";
		}
		else{
			returnString = this.txtNumber.getText();
		}
		
		return returnString;
    }

	private void setTableLock(KDTable table) {
		if (table.getColumnCount() == 0) {
			return;
		}
		boolean fView = getOprtState().equals(STATUS_VIEW);

		int projectAmtCount = 0;
		table.getColumn("isKey").getStyleAttributes().setLocked(true);
		table.getColumn("isCompose").getStyleAttributes().setLocked(true);
		for (int i = 3, colCount = table.getColumnCount(); i < colCount; i++) {
			IColumn col = table.getColumn(i);			
			NewListTempletColumnInfo colInfo = null;
			
			if (col.getUserObject() instanceof NewListTempletColumnInfo) {
				colInfo = (NewListTempletColumnInfo) col.getUserObject();
			}
			else{
				colInfo = (NewListTempletColumnInfo) this.templateColumnObjectMap.get(table.getName()+col.getKey());
			}
			
			if(colInfo!=null){
				DescriptionEnum property = colInfo.getHeadColumn().getProperty();
				if (property.equals(DescriptionEnum.ProjectAmt)) {
					projectAmtCount++;
				}
			}
		}
		// 锁定列
		for (int i = 3, colCount = table.getColumnCount(); i < colCount; i++) {
			IColumn col = table.getColumn(i);
			if (col.getUserObject() != null) {
				
				NewListTempletColumnInfo colInfo = null;
				if (col.getUserObject() instanceof NewListTempletColumnInfo) {
					colInfo = (NewListTempletColumnInfo) col.getUserObject();
				}
				else{
					colInfo = (NewListTempletColumnInfo) this.templateColumnObjectMap.get(table.getName()+col.getKey());
				}
				
				if(colInfo!=null){
					if (colInfo.isIsQuoting()) {
						col.getStyleAttributes().setLocked(true);
					} else {
						DescriptionEnum property = colInfo.getHeadColumn()
								.getProperty();
						if (property.equals(DescriptionEnum.Amount)
								|| property.equals(DescriptionEnum.AmountSum)) {
							col.getStyleAttributes().setLocked(true);
						}
						if (colInfo.isIsQuoting()) {
							col.getStyleAttributes().setLocked(true);
						}
						if (property.equals(DescriptionEnum.ProjectAmtSum)
								&& projectAmtCount > 0) {
							col.getStyleAttributes().setLocked(true);
						}
					}
				}
			}
		}
		// 锁定非叶子节点
		for (int i = 1; i < table.getRowCount(); i++) {
			boolean isLeafRow = isLeafRow(table, i);
			boolean isCompose = false;
			IRow row = table.getRow(i);
			if (row.getCell("isCompose").getValue() != null) {
				isCompose = ((Boolean) row.getCell("isCompose")
						.getValue()).booleanValue();
			}
			if (isLeafRow) {
				if (fView) {
					row.getCell("isKey").getStyleAttributes().setLocked(true);
				} else {
					row.getCell("isKey").getStyleAttributes().setLocked(false);
				}
				row.getStyleAttributes().setBackground(Color.WHITE);
				if (row.getCell(2).getValue() == null) {
					row.getCell(2).setValue(Boolean.FALSE);
				}
				row.getCell("单位").getStyleAttributes().setLocked(
						false);
			} else {
				row.getCell("isKey").setValue(Boolean.FALSE);
				row.getCell("isKey").getStyleAttributes()
						.setLocked(true);
				row.getCell(2).setValue(null);
				row.getStyleAttributes().setBackground(LOCKCOLOR);
				row.getCell("单位").setValue(null);
				row.getCell("单位").getStyleAttributes().setLocked(
						true);
			}
			for (int j = 3, colCount = table.getColumnCount(); j < colCount; j++) {
				IColumn col = table.getColumn(j);
				if (col.getUserObject() != null) {
					
					NewListTempletColumnInfo colInfo = null;
					
					if (col.getUserObject() instanceof NewListTempletColumnInfo) {
						colInfo = (NewListTempletColumnInfo) col.getUserObject();
					}
					else{
						colInfo = (NewListTempletColumnInfo) this.templateColumnObjectMap.get(table.getName()+col.getKey());
					}
					
					if(colInfo != null){
						DescriptionEnum property = colInfo.getHeadColumn()
								.getProperty();
						ICell cell = table.getCell(i, j);
						if (property.equals(DescriptionEnum.ProjectAmt)) {
							if (isLeafRow) {
								if (fView) {
									cell.getStyleAttributes().setLocked(true);
								} else {
									cell.getStyleAttributes().setLocked(false);
								}
							} else {
								cell.getStyleAttributes().setLocked(true);
								cell.setValue(null);
							}
						}
						if (property.equals(DescriptionEnum.ProjectAmtSum)) {
							if (projectAmtCount == 0 && isLeafRow) {
								if (fView) {
									cell.getStyleAttributes().setLocked(true);
								} else {
									cell.getStyleAttributes().setLocked(false);
								}
							} else {
								cell.getStyleAttributes().setLocked(true);
								if (!isLeafRow) {
									cell.setValue(null);
								}
							}
						}
						if (property.equals(DescriptionEnum.TotalPrice)) {
							if (isLeafRow) {
								if (isCompose) {
									cell.getStyleAttributes().setBackground(
											Color.WHITE);
								} else {
									cell.getStyleAttributes().setBackground(
											Color.lightGray);
								}
							}
						}
					}
				}
			}
		}

//		table.getStyleAttributes().setLocked(fView);
	}

	transient KDTEditAdapter editAdapter = null;

	transient KDTMouseListener mouseAdapter = null;

	private void setTableEvent(final KDTable table) {
		table.removeKDTEditListener(editAdapter);
		editAdapter = new KDTEditAdapter() {
			// 编辑结束后
			public void editStopped(KDTEditEvent e) {
				IRow row = table.getRow(e.getRowIndex());
				IColumn col = table.getColumn(e.getColIndex());
				if (col.getUserObject() != null) {
					NewListTempletColumnInfo colInfo = null;
					if (col.getUserObject() instanceof NewListTempletColumnInfo) {
						colInfo = (NewListTempletColumnInfo) col.getUserObject();
					}
					else{
						colInfo = (NewListTempletColumnInfo) templateColumnObjectMap.get(table.getName()+col.getKey());
					}
					
					if(colInfo!=null){
						DescriptionEnum property = colInfo.getHeadColumn()
								.getProperty();
						if (property.equals(DescriptionEnum.ProjectAmt)
								|| property
										.equals(DescriptionEnum.ProjectAmtSum)
								|| property.equals(DescriptionEnum.TotalPrice)
								|| property
										.equals(DescriptionEnum.TotalPriceSum)) {
							setUnionData(table);
						}
						if (colInfo.getHeadColumn().getName().equals(ItemName)) {
							if (row.getCell(ItemName).getValue() instanceof ListingItemInfo) {
								ListingItemInfo item = (ListingItemInfo) row
										.getCell(ItemName).getValue();
								row.getCell("单位").setValue(item.getUnit());
							}
						}
					}
				}
				KDTableHelper.autoFitRowHeight(table,e.getRowIndex(),5);
			}
		};
		table.removeKDTMouseListener(mouseAdapter);
		table.addKDTEditListener(editAdapter);
		mouseAdapter = new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				// 单击
				int evtCol = e.getColIndex();
				if (e.getClickCount() == 1) {
					if (getOprtState().equals("VIEW") || e.getRowIndex() <= 0) {
						return;
					}
					boolean isLeaf = isLeafRow(table, e.getRowIndex());
					if (!isLeaf) {
						return;
					}

					boolean fComposeCol = "isCompose".equals(table
							.getColumnKey(evtCol));
					if (fComposeCol
							|| "isKey".equals(table.getColumnKey(evtCol))) {
						IRow row = table.getRow(e.getRowIndex());
						boolean fVal = false;
						if (row.getCell(evtCol).getValue() != null) {
							fVal = ((Boolean) row.getCell(evtCol).getValue())
									.booleanValue();
						}
						row.getCell(evtCol).setValue(Boolean.valueOf(!fVal));
						if (fComposeCol) {
							setUnionData(table);
						}
					}
				} else {
					IColumn sumCol = null;
					for (int i = 3; i < table.getColumnCount(); i++) {
						IColumn column = table.getColumn(i);
						if (column.getUserObject() != null) {
							NewListTempletColumnInfo col = null;
							
							if (column.getUserObject() instanceof NewListTempletColumnInfo) {
								col = (NewListTempletColumnInfo) column.getUserObject();
							}
							else{
								col = (NewListTempletColumnInfo) templateColumnObjectMap.get(table.getName()+column.getKey());
							}
							if(col!=null){
								if (col.getHeadColumn().getProperty().equals(
										DescriptionEnum.ProjectAmtSum)) {
									sumCol = column;
								}
							}
						}
					}
					if (e.getRowIndex() != 0
							|| evtCol != sumCol.getColumnIndex()) {
						return;
					}
					String colName = (String) table.getHeadRow(1).getCell(
							sumCol.getColumnIndex()).getValue();
					boolean isHide = false;
					if (colName.indexOf("...") == -1) {
						isHide = true;
						table.getHeadRow(1).getCell(sumCol.getColumnIndex())
								.setValue(colName + "...");
					} else {
						isHide = false;
						int sub = colName.indexOf("...");
						colName = colName.substring(0, colName.length() - sub
								- 1);
						table.getHeadRow(1).getCell(sumCol.getColumnIndex())
								.setValue(colName);
					}

					for (int i = 3; i < table.getColumnCount(); i++) {
						IColumn column = table.getColumn(i);
						if (column.getUserObject() != null) {
							NewListTempletColumnInfo col = null;
							
							if (column.getUserObject() instanceof NewListTempletColumnInfo) {
								col = (NewListTempletColumnInfo) column.getUserObject();
							}
							else{
								col = (NewListTempletColumnInfo) templateColumnObjectMap.get(table.getName()+column.getKey());
							}
							if(col!=null){
								if (col.getHeadColumn().getProperty().equals(
										DescriptionEnum.ProjectAmt)) {
									column.getStyleAttributes().setHided(isHide);
								}
							}
						}
					}
				}
			}
		};
		table.addKDTMouseListener(mouseAdapter);
	}
	static class myF7Box extends KDBizPromptBox{
		/**
		 * 
		 */
		private static final long serialVersionUID = 821085289475565286L;

		protected Object stringToValue(String t) {
			Object obj = super.stringToValue(t);
			if (obj instanceof IObjectValue) {
				return obj;
			} else {
				if (t != null && t.length() > 499) {
					t = t.substring(0, 499);
				}
				return t;
			}

		}
	}
	private KDBizPromptBox getF7Box(String headTypeId) {
		KDBizPromptBox f7 = new myF7Box();
		f7.setDisplayFormat("$name$");
		f7.setCommitFormat("$name$");
		f7.setEditFormat("$name$");
		f7.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7ListingItemQuery");
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("headType.id", headTypeId,
				CompareType.EQUALS));
		filterItems.add(new FilterItemInfo("orgUnit.id", getAllParentIds(),
				CompareType.INCLUDE));
		evInfo.setFilter(filter);
		f7.setEntityViewInfo(evInfo);
		f7.setEditable(true);
		return f7;
	}

	public Set getAllParentIds() {
		TreeModel orgTreeModel = null;
		try {
			orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY, "",
					null, null, null);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		Set idSet = new HashSet();
		if(orgTreeModel==null||orgTreeModel.getRoot()==null)
			return idSet;
		DefaultKingdeeTreeNode orgRoot = (DefaultKingdeeTreeNode) orgTreeModel
				.getRoot();
		DefaultKingdeeTreeNode node = this.findNode(orgRoot, this.currentOrg
				.getId().toString());
		
		idSet.add(currentOrg.getId().toString());
		while (node.getParent() != null) {
			DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node
					.getParent();
			OrgStructureInfo oui = (OrgStructureInfo) parent.getUserObject();
			FullOrgUnitInfo info = oui.getUnit();
			idSet.add(info.getId().toString());
			node = parent;
		}
		return idSet;
	}

	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node,
			String id) {
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if (projectInfo.getId().toString().equals(id)) {
				return node;
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			FullOrgUnitInfo info = oui.getUnit();
			if (info.getId().toString().equals(id)) {
				return node;
			}
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findNode(
					(DefaultKingdeeTreeNode) node.getChildAt(i), id);
			if (findNode != null) {
				return findNode;
			}

		}
		return null;
	}

	private void setTableRow(KDTable table, NewListTempletPageInfo page) {
		NewListTempletEntryCollection collEntry = page.getEntrys();
		NewListTempletEntryInfo infoEntry = null;
		int sizeEntry = collEntry.size();
		if (table.getColumnCount() != 0) {
			IRow totalRow = table.addRow();
			totalRow.getCell(3).setValue("合计");
			totalRow.getStyleAttributes().setBackground(Color.lightGray);
			totalRow.getStyleAttributes().setLocked(true);
		}
		String headTypeId = page.getHeadType().getId().toString();
		KDBizPromptBox f7 = this.getF7Box(headTypeId);
		KDTDefaultCellEditor itemF7Editor = new KDTDefaultCellEditor(f7);
		CellTextRenderImpl avr = new CellTextRenderImpl();
		avr.getText(new BizDataFormat("$name$"));
		for (int k = 0; k < sizeEntry; k++) {
			IRow dataRow = table.addRow();
			infoEntry = collEntry.get(k);
			dataRow.setUserObject(infoEntry);
			if (infoEntry.getItem() != null) {
				dataRow.getCell(ItemName).setValue(infoEntry.getItem());
			} else {
				dataRow.getCell(ItemName).setValue(infoEntry.getItemName());
			}
			dataRow.getCell(ItemName).setEditor(itemF7Editor);
			dataRow.getCell(ItemName).setRenderer(avr);
			dataRow.getCell("level")
					.setValue(new Integer(infoEntry.getLevel()));
			dataRow.getCell("isKey").setValue(
					Boolean.valueOf(infoEntry.isIsKey()));
			dataRow.getCell("isCompose").setValue(
					Boolean.valueOf(infoEntry.isIsCompose()));
			dataRow.setTreeLevel(infoEntry.getLevel());
		}
	}

	private void setTableColumn(KDTable table, NewListTempletPageInfo page) {
		NewListTempletColumnCollection collColumn = page.getTempletColumns();
		if (collColumn == null || collColumn.size() == 0)
			return ;

		int sizeColumn = collColumn.size();
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
		colIsKey.setEditor(chkEditor);
		row.getCell("isKey").setValue("关键子目");
		IColumn colIsCompose = table.addColumn(2);
		colIsCompose.setKey("isCompose");
		
		colIsCompose.setWidth(60);
		colIsCompose.setEditor(chkEditor);
		row.getCell("isCompose").setValue("单价构成");
		int count = 0;
		KDTMergeManager mm = table.getHeadMergeManager();
		mm.mergeBlock(0, 0, 1, 0);
		mm.mergeBlock(0, 1, 1, 1);
		mm.mergeBlock(0, 2, 1, 2);
		int baseCount = 3;
		for (int j = 0; j < sizeColumn; j++) {
			NewListTempletColumnInfo templColumn = collColumn.get(j);
			HeadColumnInfo infoColumn = templColumn.getHeadColumn();
			IColumn column = table.addColumn();
			column.setKey(infoColumn.getName());
			column.setUserObject(templColumn);
			this.columnObjMap.put(column, templColumn);
			row.getCell(j + baseCount).setValue(infoColumn.getName());
			ColumnTypeEnum colType = infoColumn.getColumnType();
			if (colType.equals(ColumnTypeEnum.Amount)) {
				column.setEditor(amtEditor);
				column.getStyleAttributes().setNumberFormat(
						"#,##0.00;-#,##0.00");
				column.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
			} else if (colType.equals(ColumnTypeEnum.Date)) {
				column.setEditor(dateEditor);
				column.getStyleAttributes().setNumberFormat("yyyy-m-d");
			} else if (colType.equals(ColumnTypeEnum.String)) {
				column.setEditor(txtEditor);
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
							mm.mergeBlock(0, j + baseCount, 1, j + baseCount,
									KDTMergeManager.SPECIFY_MERGE);
						} else {
							mm.mergeBlock(0, j - count + baseCount, 0, j
									+ baseCount, KDTMergeManager.DATA_UNIFY);
							count = 0;
						}
					}
				} else {
					mm.mergeBlock(0, j - count + baseCount, 0, j + baseCount,
							KDTMergeManager.DATA_UNIFY);
					count = 0;
				}
			} else {
				mm.mergeBlock(0, j + baseCount, 1, j + baseCount);
			}
		}
		
		table.getColumn(ItemName).setWidth(400);
	}	

	private void setTableMenu(final KDTable table) {
		try {
			tableMenu = new NewListingTableMenuUI();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		enableTableCommonMenus(table);
		//tHelper.addMenuToTable(table);
		table.setColumnMoveable(false);
		enableExportExcel(table);
		KDTMenuManager tm = getMenuManager(table);
		if (tm == null) {
			tm = new KDTMenuManager(table);
		}

		KDMenuItem itemCopy = new KDMenuItem();
		itemCopy.setName("复制");
		itemCopy.setAction((IItemAction) ActionProxyFactory.getProxy(
				actionCopyLine, new Class[] { IItemAction.class },
				getServiceContext()));
		// itemCopy.setAction(new ActionCopyLine());
		tm.getMenu().addSeparator();
		addCommonMenuAction(itemCopy);
		tm.getMenu().insert(itemCopy,0);

		KDMenuItem itemPaste = new KDMenuItem();
		itemPaste.setName("粘贴");
		itemPaste.setAction((IItemAction) ActionProxyFactory.getProxy(
				actionPasteLine, new Class[] { IItemAction.class },
				getServiceContext()));
		// itemPaste.setAction(new ActionPasteLine());
		tm.getMenu().insert(itemPaste,1);

		KDMenuItem itemCut = new KDMenuItem();
		itemCut.setName("剪切");
		itemCut.setAction((IItemAction) ActionProxyFactory.getProxy(
				actionCutLine, new Class[] { IItemAction.class },
				getServiceContext()));
		// itemCut.setAction(new ActionCutLine());
		tm.getMenu().insert(itemCut,2);
		
		KDMenuItem itemUp = new KDMenuItem();
		itemUp.setName("升级");
		itemUp.setAction((IItemAction) ActionProxyFactory.getProxy(
				actionUpLine, new Class[] { IItemAction.class },
				getServiceContext()));
		// itemUp.setAction(new ActionUpLine());
		tm.getMenu().insert(itemUp,3);

		KDMenuItem itemDown = new KDMenuItem();
		itemDown.setName("降级");
		itemDown.setAction((IItemAction) ActionProxyFactory.getProxy(
				actionDownLine, new Class[] { IItemAction.class },
				getServiceContext()));
		// itemDown.setAction(new ActionDownLine());
		tm.getMenu().insert(itemDown,4);
	}

	private void updateTotalPageData() {
		if (tabbedPnlTable.getTabCount() <= 1) {
			return;
		}
		KDTable table = (KDTable) tabbedPnlTable.getComponentAt(1);
		if (!table.getName().equals("报价汇总表")) {
			return;
		}
		int pageCount = tabbedPnlTable.getTabCount() - 2;
		if(table.getRowCount()+4 != pageCount)
		{
			table.removeRows();
			table.addRows(pageCount + 4);
			for (int i = 0; i < pageCount; i++) {
				table.getCell(i, "number").setValue(new Integer(i + 1));
				table.getCell(i, "name").setValue(tabbedPnlTable.getTitleAt(i+2));
				table.getCell(i, "amount").getStyleAttributes().setLocked(true);
			}
			table.getCell(pageCount, "name").setValue("小计");
			table.getCell(pageCount, "amount").getStyleAttributes().setLocked(true);
			table.getCell(pageCount + 1, "name").setValue("税率(%)");
			table.getCell(pageCount + 1, "amount").getStyleAttributes().setLocked(
					true);
			table.getCell(pageCount + 2, "name").setValue("税金（元）");
			table.getCell(pageCount + 2, "amount").getStyleAttributes().setLocked(
					true);
			table.getCell(pageCount + 3, "name").setValue("总造价（元）");
			table.getCell(pageCount + 3, "amount").getStyleAttributes().setLocked(
					true);
			table.getColumn(2).setEditor(amtEditor);
			FDCHelper.formatTableNumber(table, "amount");
		}
		
		BigDecimal sum = FDCNumberConstants.ZERO;
		for (int i = 0; i < pageCount; i++) {
			KDTable page = (KDTable) tabbedPnlTable.getComponentAt(i+2);
			for (int j = 3; j < page.getColumnCount(); j++) {
				IColumn col = page.getColumn(j);
				NewListTempletColumnInfo colInfo = null;
				if (col.getUserObject() instanceof NewListTempletColumnInfo) {
					colInfo = (NewListTempletColumnInfo) col.getUserObject();
				}
				else{
					colInfo = (NewListTempletColumnInfo) this.templateColumnObjectMap.get(table.getName()+col.getKey());
				}
				if (colInfo != null) {
					DescriptionEnum property = colInfo.getHeadColumn()
							.getProperty();
					if (property.equals(DescriptionEnum.AmountSum)) {
						BigDecimal value = FDCNumberHelper.toBigDecimal(page.getCell(
								0, j).getValue());
						table.getCell(i , 2).setValue(value);
						if (value != null) {
							sum = sum.add(value);
						}
						break;
					}
				}
			}
		}
		table.getCell(pageCount, "amount").setValue(sum);
		BigDecimal scale = FDCNumberConstants.ZERO;
		if (table.getCell(pageCount + 1, "amount").getValue() != null) {
			scale = (BigDecimal) table.getCell(pageCount + 1, "amount")
					.getValue();
		}
		sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal amount = sum.multiply(scale).divide(FDCHelper.ONE_HUNDRED,
				BigDecimal.ROUND_UP);
		table.getCell(pageCount + 2, "amount").setValue(amount);
		table.getCell(pageCount + 3, "amount").setValue(sum.add(amount));
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("inviteType.*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		sic.add(new SelectorItemInfo("oriOrg.*"));
		return sic;
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
		if (!checkSelectTable(table)) {
			showInfo();
			return;
		}
//		if (table.getUserObject() == null) {
//			return;
//		}
//		if (table.getColumnCount() == 0) {
//			return;
//		}
		NewListTempletPageInfo page = (NewListTempletPageInfo) table
				.getUserObject();
//		int index = table.getSelectManager().getActiveRowIndex();
//		if (index == -1) {
//			table.getSelectManager().set(table.getRowCount() - 1, 0);
//			index = table.getRowCount() - 1;
//		}
		NewListTempletEntryInfo infoEntry = new NewListTempletEntryInfo();
		IRow row = table.addRow();
		CellTreeNode node = new CellTreeNode();
		row.setTreeLevel(0);
		row.setUserObject(infoEntry);
		String headTypeId = page.getHeadType().getId().toString();
		KDBizPromptBox f7 = this.getF7Box(headTypeId);
		CellTextRenderImpl avr = new CellTextRenderImpl();
		avr.getText(new BizDataFormat("$name$"));
		//ObjectValueRenderImpl avr = new ObjectValueRenderImpl();
		//avr.setFormat(new BizDataFormat("$name$"));
		row.getCell(ItemName).setEditor(new KDTDefaultCellEditor(f7));
		row.getCell(ItemName).setRenderer(avr);
		row.getCell("level").setValue(new Integer(0));
		row.getCell("isKey").setValue(Boolean.FALSE);
		row.getCell("isCompose").setValue(Boolean.FALSE);
		node.setValue(new Integer(0));
		node.setTreeLevel(0);
		infoEntry.setLevel(0);
		infoEntry.setIsLeaf(true);
		infoEntry.setIsCanZeroProAmount(false);
		setUnionData(table);
	}

	public IObjectPK runSubmit() throws Exception {
		IObjectPK pk = NewListTempletFactory.getRemoteInstance().submit(
				this.editData);
		for (int i = 0, pageSize = editData.getPages().size(); i < pageSize; i++) {
			NewListTempletPageFactory.getRemoteInstance().submit(
					editData.getPages().get(i));
		}
		return pk;
	}

	public IObjectPK runSave() throws Exception {
		IObjectPK pk = NewListTempletFactory.getRemoteInstance().save(
				this.editData);
		for (int i = 0, pageSize = editData.getPages().size(); i < pageSize; i++) {
			NewListTempletPageFactory.getRemoteInstance().save(
					editData.getPages().get(i));
		}
		return pk;
	}

	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		setColumnObject();
		KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
		if (!checkSelectTable(table)) {
			showInfo();
			return;
		}
//		if (table.getUserObject() == null) {
//			return;
//		}
//		if (table.getColumnCount() == 0) {
//			return;
//		}
		NewListTempletPageInfo info = (NewListTempletPageInfo) table
				.getUserObject();
		int index = table.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			table.getSelectManager().set(table.getRowCount() - 1, 0);
			index = table.getRowCount() - 1;
		}
		Integer level = (Integer) table.getCell(index, 0).getValue();
		if (level == null) {
			level = new Integer(0);
		}
		NewListTempletEntryInfo infoEntry = new NewListTempletEntryInfo();
		IRow row = table.addRow(index + 1);
		CellTreeNode node = new CellTreeNode();
		row.setTreeLevel(0);
		row.setUserObject(infoEntry);
		String headTypeId = info.getHeadType().getId().toString();
		KDBizPromptBox f7 = this.getF7Box(headTypeId);
		CellTextRenderImpl avr = new CellTextRenderImpl();
		avr.getText(new BizDataFormat("$name$"));
//		ObjectValueRenderImpl avr = new ObjectValueRenderImpl();
//		avr.setFormat(new BizDataFormat("$name$"));
		row.getCell(ItemName).setEditor(new KDTDefaultCellEditor(f7));
		row.getCell(ItemName).setRenderer(avr);
		row.getCell("level").setValue(level);
		row.getCell("isKey").setValue(Boolean.FALSE);
		row.getCell("isCompose").setValue(Boolean.FALSE);
		row.setTreeLevel(level.intValue());
		node.setValue(new Integer(0));
		node.setTreeLevel(0);
		infoEntry.setLevel(0);
		infoEntry.setIsLeaf(true);
		infoEntry.setIsCanZeroProAmount(false);
		setUnionData(table);
	}

	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		setColumnObject();
		KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
		if (!checkSelectTable(table)) {
			showInfo();
			return;
		}
//		if (table.getUserObject() == null) {
//			return;
//		}
//		if (table.getColumnCount() == 0) {
//			return;
//		}
		if ((table.getSelectManager().size() == 0)
				|| isTableColumnSelected(table)) {
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_NoneEntry"));
			return;
		}
		if (table.getSelectManager().get().getBeginRow() == 0) {
			return;
		}
		// [begin]进行删除分录的提示处理。
		if (confirmRemove()) {
			int top = table.getSelectManager().get().getBeginRow();
			int bottom = table.getSelectManager().get().getEndRow();
			for (int i = bottom; i >= top; i--) {
				if (i != 0 && !isLeafRow(table, i)) {
					MsgBox.showInfo("选择了非明细行,不能删除!");
					return;
				}
			}
			for (int i = bottom; i >= top; i--) {
				if (i != 0) {
					table.removeRow(i);
				}
			}
		}
		setUnionData(table);
	}
	public List setCopyLines(KDTable table){
		if (table == null) {
			return null;
		}
		List indexList = new ArrayList();
		if ((table.getSelectManager().size() == 0)
				|| isTableColumnSelected(table)) {
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_NoneEntry"));

			return null;
		}

		// 获取选择块的总个数
		KDTSelectManager selectManager = table.getSelectManager();
		int size = selectManager.size();
		KDTSelectBlock selectBlock = null;
		// 因为先择块的顺序可能并非是表中行的顺序，所以先要排序使选择块的顺序正好是由小到大
		

		for (int blockIndex = 0; blockIndex < size; blockIndex++) {
			selectBlock = selectManager.get(blockIndex);
			int top = selectBlock.getBeginRow();
			int bottom = selectBlock.getEndRow();
			if (table.getRow(top) == null) {
				MsgBox.showInfo(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Msg_NoneEntry"));
				return null;
			}
			for (int i = top; i <= bottom; i++) {
				indexList.add(new Integer(i));
			}
		}
		this.copyLine = indexList.size();
		return indexList;
	}

	public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyLine_actionPerformed(e);
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (checkSelectTableRow(table)) {
			setCopyLines(table);
			table.getEditHelper().copy();
			
		}
	}
	
	public boolean checkSelectTableRow(KDTable table){
		if(table.getSelectManager().size() == 0){
			return false;
		}
		int index = table.getSelectManager().get(0).getTop();
		if(index<1)
			return false;
		return true;
	}

	public void actionCutLine_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if(checkSelectTableRow(table))
		{
			List indexList = setCopyLines(table);
			Map levelMap = new HashMap();
			//before cut ,should bak the "level"
			for (int i = 0; i < indexList.size(); i++) {
				levelMap.put((Integer) indexList.get(i), table.getCell(
						((Integer) indexList.get(i)).intValue(), "level")
						.getValue().toString());
			}
			//cut
			table.getEditHelper().cut();
			//after cut, should restore the "level"
			for (int i = 0; i < indexList.size(); i++) {
				table.getCell(((Integer) indexList.get(i)).intValue(), "level")
						.setValue(levelMap.get((Integer) indexList.get(i)));
			}
			setUnionData(table);
		}
	}

	public void actionDownLine_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
		if (!checkSelectTable(table)) {
			showInfo();
			return;
		}
//		if (table.getUserObject() == null || table.getColumnCount() == 0) {
//			return;
//		}
		KDTSelectBlock sb = table.getSelectManager().get();
		if (sb == null || sb.size() <= 0) {
			MsgBox.showInfo("请先选择行!");
			return;
		}
		int top = sb.getTop(); // 选择块最上边行索引
		if (top == 0) {
			return;
		}
		int bottom = sb.getBottom(); // 选择块最下边行索引
		int levelOrig = Integer.parseInt(table.getCell(top, "level").getValue()
				.toString());
		if (top - 1 >= 1) {
			int uplevel = Integer.parseInt(table.getCell(top - 1, "level")
					.getValue().toString());
			if (uplevel < levelOrig) {
				MsgBox.showInfo("最末级别不能降级!");
				return;
			}
		} else {
			MsgBox.showInfo("最末级别不能降级!");
			return;
		}

		for (int i = top + 1; i <= bottom; i++) {
			int level = Integer.parseInt(table.getCell(i, "level").getValue()
					.toString());
			if (level != levelOrig) {
				MsgBox.showError(this, "必须对同一级次执行本操作！");
				return;
			}
		}

		for (int i = top; i <= bottom; i++) {
			table.getCell(i, "level").setValue(new Integer(levelOrig + 1));
			table.getRow(i).setTreeLevel(levelOrig + 1);
		}
		for (int i = bottom + 1; i < table.getRowCount(); i++) {
			int levelnext = Integer.parseInt(table.getCell(i, "level")
					.getValue().toString());
			if (levelnext <= levelOrig) {
				break;
			}
			table.getCell(i, "level").setValue(new Integer(levelnext + 1));
			table.getRow(i).setTreeLevel(levelnext + 1);
		}
		table.getTreeColumn().setDepth(getMaxLevel(table));
		for (int i = top - 1; i >= 0; i--) {
			int levelOld = Integer.parseInt(table.getCell(i, "level")
					.getValue().toString());
			if (levelOld == levelOrig) {
				NewListTempletEntryInfo info = (NewListTempletEntryInfo) table
						.getRow(i).getUserObject();
				info.setIsLeaf(false);
				break;
			}
		}
		setUnionData(table);
	}

	public void actionPasteLine_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
		if(checkSelectTableRow(table)){
			table.getEditHelper().paste();
			KDTSelectBlock sb = table.getSelectManager().get();
			int top = sb.getTop(); // 选择块最上边行索引
			if (top == 0) {
				return;
			}
			for(int i=top;i<top+this.copyLine+1&&i<table.getRowCount();i++)
			{
				int level = Integer.parseInt(table.getCell(i, "level")
						.getValue().toString());
				table.getRow(i).setTreeLevel(level);
			}
			setUnionData(table);
		}
		
	}
	
	public boolean checkSelectTable(KDTable table){
		if(table.getUserObject() == null || table.getColumnCount() == 0){
			return false;
		}
		else
			return true;
	}

	public void actionUpLine_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
		if (!checkSelectTable(table)) {
			showInfo();
			return;
		}
		KDTSelectBlock sb = table.getSelectManager().get();
		if (sb == null || sb.size() <= 0) {
			MsgBox.showInfo("请先选择行!");
			return;
		}
		int top = sb.getTop(); // 选择块最上边行索引
		if (top == 0) {
			return;
		}
		int bottom = sb.getBottom(); // 选择块最下边行索引
		int levelOrig = Integer.parseInt(table.getCell(top, "level").getValue()
				.toString());
		if (levelOrig == 0) {
			MsgBox.showInfo("最上级别不能升级!");
			return;
		}
		for (int i = top + 1; i <= bottom; i++) {
			int level = Integer.parseInt(table.getCell(i, "level").getValue()
					.toString());
			if (level != levelOrig) {
				MsgBox.showError(this, "必须对同一级次执行本操作！");
				return;
			}
		}
		for (int i = top; i <= bottom; i++) {
			table.getCell(i, "level").setValue(new Integer(levelOrig - 1));
			table.getRow(i).setTreeLevel(levelOrig - 1);
		}
		for (int j = bottom + 1; j < table.getRowCount(); j++) {
			int levelnext = Integer.parseInt(table.getCell(j, "level")
					.getValue().toString());
			if (levelnext <= levelOrig) {
				break;
			}
			table.getCell(j, "level").setValue(new Integer(levelnext - 1));
			table.getRow(j).setTreeLevel(levelnext - 1);
		}
		table.getTreeColumn().setDepth(getMaxLevel(table));

		for (int i = top - 1; i >= 1; i--) {
			int levelOld = Integer.parseInt(table.getCell(i, "level")
					.getValue().toString());
			if (levelOld == levelOrig) {
				NewListTempletEntryInfo info = (NewListTempletEntryInfo) table
						.getRow(i).getUserObject();
				info.setIsLeaf(false);
				break;
			}
		}
		setUnionData(table);
	}

	private void showInfo() {
		if(this.tabbedPnlTable.getSelectedIndex()<2)
			MsgBox.showInfo(this,"不能在此页签上进行分录的操作！");
		else
			MsgBox.showInfo(this,"请先选择表头!");
	}

	private int getMaxLevel(KDTable table) {
		int count = table.getRowCount();
		int levelthis;
		int levelmax = 0;
		for (int i = 1; i < count; i++) {
			levelthis = Integer.parseInt(table.getCell(i, "level").getValue()
					.toString());
			if (levelthis > levelmax) {
				levelmax = levelthis;
			}
		}
		return levelmax;
	}

	transient KDTEditHelper kdtEditHelper;

	private void addCommonMenuAction(JMenuItem item) {
		// 复制。
		if (item.getName().equals("复制")) {
			item.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						actionCopyLine_actionPerformed(e);
					} catch (Exception exc) {
						handUIException(exc);
					} finally {
					}
				};
			});
		}

		// 粘贴。
		if (item.getName().equals("粘贴")) {
			item.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						actionPasteLine_actionPerformed(e);
					} catch (Exception exc) {
						handUIException(exc);
					} finally {
					}
				};
			});
		}

		// 剪切。
		if (item.getName().equals("剪切")) {
			item.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						actionCutLine_actionPerformed(e);
					} catch (Exception exc) {
						handUIException(exc);
					} finally {
					}
				};
			});
		}

		// 升级。
		if (item.getName().equals("升级")) {
			item.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						actionUpLine_actionPerformed(e);
					} catch (Exception exc) {
						handUIException(exc);
					} finally {
					}
				};
			});
		}

		// 降级。
		if (item.getName().equals("降级")) {
			item.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						actionDownLine_actionPerformed(e);
					} catch (Exception exc) {
						handUIException(exc);
					} finally {
					}
				};
			});
		}
	}

	private void setTableScript(KDTable table) {
		int rowCount = table.getRowCount();
		int amountIndex = 0;
		int amountSum = 0;
		int projectAmtIndex = 0;
		int projectAmtSum = 0;
		int totalPriceIndex = 0;
		int totalPriceSum = 0;
		for (int i = 3, colCount = table.getColumnCount(); i < colCount; i++) {
			IColumn col = table.getColumn(i);
			if (col.getUserObject() != null) {
				
				NewListTempletColumnInfo column = null;
				
				if (col.getUserObject() instanceof NewListTempletColumnInfo) {
					column = (NewListTempletColumnInfo) col.getUserObject();
				}
				else{
					column = (NewListTempletColumnInfo) this.templateColumnObjectMap.get(table.getName()+col.getKey());
				}
				
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
						} else {
							table.getCell(i, totalPriceSum)
									.setExpressions(null);
							table.getCell(i, totalPriceSum).setValue(null);
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

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		NewListTempletInfo listing = (NewListTempletInfo) super.getValue(pk);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems()
				.add(
						new FilterItemInfo("templetHead.id", listing.getId()
								.toString()));
		SelectorItemCollection sic = view.getSelector();
		sic.add("*");
		sic.add("pageHead.*");
		sic.add("headType.*");
		view.getSorter().add(new SorterItemInfo("seq"));
		NewListTempletPageCollection sheets = NewListTempletPageFactory
				.getRemoteInstance().getNewListTempletPageCollection(view);
		listing.getPages().clear();
		listing.getPages().addCollection(sheets);
		for (int i = 0; i < listing.getPages().size(); i++) {
			NewListTempletPageInfo sheet = listing.getPages().get(i);
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
			NewListTempletColumnCollection columns = null;
			columns = NewListTempletColumnFactory.getRemoteInstance()
					.getNewListTempletColumnCollection(view);
			sheet.getTempletColumns().clear();
			sheet.getTempletColumns().addCollection(columns);

			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("item.*");
			view.getSelector().add("values.*");
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", sheet.getId().toString()));
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListTempletEntryCollection entrys = null;
			entrys = NewListTempletEntryFactory.getRemoteInstance()
					.getNewListTempletEntryCollection(view);
			sheet.getEntrys().clear();
			sheet.getEntrys().addCollection(entrys);
		}
		return listing;
	}

	public void setUnionData(KDTable table) {
		setTableScript(table);
		List colList = new ArrayList();
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			NewListTempletColumnInfo col = null;
			if (column.getUserObject() instanceof NewListTempletColumnInfo) {
				col = (NewListTempletColumnInfo) column.getUserObject();
			}
			else{
				col = (NewListTempletColumnInfo) this.templateColumnObjectMap.get(table.getName()+column.getKey());
			}
			if (col != null
					&& (col.getHeadColumn().getProperty().equals(
							DescriptionEnum.Amount) || col.getHeadColumn()
							.getProperty().equals(DescriptionEnum.AmountSum))) {
				colList.add(col.getHeadColumn().getName());
			}
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			// 设置汇总行
			int level = row.getTreeLevel();
			int maxLevel = this.getMaxLevel(table);
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
					String colName = (String) colList.get(j);
					BigDecimal sum = null;
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (sum == null) {
								sum = FMConstants.ZERO;
							}
							sum = sum.add(FDCNumberHelper.toBigDecimal(value));
						}
					}
					row.getCell(colName).setValue(sum);
				}
			}
		}
		setTableTotal(table);
		table.getScriptManager().runAll();
		this.updateTotalPageData();
		setTableLock(table);
		table.getTreeColumn().setDepth(getMaxLevel(table) + 1);
		table.revalidate();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		setColumnObject();
		super.verifyInput(e);
		FDCClientVerifyHelper.verifyRequire(this);
		if (this.prmtInviteType.getValue()==null){
			MsgBox.showInfo("没有选择招标类型!");
			abort();
		}else{
			this.editData.setInviteType((InviteTypeInfo)this.prmtInviteType.getValue());
		}
		if (this.tabbedPnlTable.getTabCount() == 1) {
			MsgBox.showInfo("没有选择页签!");
			abort();
		}
		for (int i = 2; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) this.tabbedPnlTable.getComponentAt(i);
			if (table.getColumnCount() == 0) {
				MsgBox.showInfo("页签:" + table.getName() + " 没有设置表头!");
				abort();
			}
		}
		verifyData();
		
		verifyDuplicationItem();
	}

	public void verifyData() {
		
		List nameNullList = new ArrayList();
		List nameDupList = new ArrayList();
		List unitNullList = new ArrayList();
		List quantityNullList = new ArrayList();
		List tooLongList = new ArrayList();
		Map tableMap = new HashMap();
		for (int i = 2; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) this.tabbedPnlTable.getComponentAt(i);
			// 校验名称不重复
			Map nameMap = new HashMap();
			for (int j = 1; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				ICell nameCell = row.getCell(ItemName);
				nameCell.getStyleAttributes().setBackground(REQUIREDCOLOR);
				String name = null;
				if (nameCell.getValue() instanceof String) {
					name = (String) nameCell.getValue();
				} else {
					if (nameCell.getValue() != null) {
						name = ((ListingItemInfo) nameCell.getValue()).getName();
					}
				}
				if (name == null || name.equals("")) {
					nameNullList.add(nameCell);
					tableMap.put(table.getName(), table);
					continue;
				}
				name = name.trim();
				if (nameMap.containsKey(name)) {
					nameDupList.add(nameCell);
					nameDupList.add(nameMap.get(name));
					tableMap.put(table.getName(), table);
					continue;
				}
				nameMap.put(name, nameCell);
			}
			// 校验单位
			for (int j = 1; j < table.getRowCount(); j++) {
				if (!isLeafRow(table, j)) {
					continue;
				}
				IRow row = table.getRow(j);
				ICell unitCell = row.getCell("单位");
				unitCell.getStyleAttributes().setBackground(REQUIREDCOLOR);
				String name = (String) unitCell.getValue();
				if (name == null || name.equals("")) {
					unitNullList.add(unitCell);
					tableMap.put(table.getName(), table);
					continue;
				}
			}
		}
		StringBuffer msg = new StringBuffer("页签:");
		Set set = tableMap.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			msg.append(name + " ");
		}
		msg.append("保存校验不通过:");
		if (nameNullList.size() > 0) {
			msg.append("\n存在名称为空的行");
		}
		nameDupList.clear();
		if (nameDupList.size() > 0) {
			msg.append("\n存在名称重复的行");
		}
		if (unitNullList.size() > 0) {
			msg.append("\n存在单位为空的行");
		}
		if (quantityNullList.size() > 0) {
			msg.append("\n清单中存在工程量不合法的行");
		}
		if (tooLongList.size() > 0) {
			msg.append("\n存在超长数据行");
		}
		List errCell = new ArrayList();
		errCell.addAll(nameNullList);
		errCell.addAll(nameDupList);
		errCell.addAll(tooLongList);
		errCell.addAll(unitNullList);
		errCell.addAll(quantityNullList);
		if (errCell.size() > 0) {
			MsgBox.showError(msg.toString());
			for (int i = 0; i < errCell.size(); i++) {
				ICell cell = (ICell) errCell.get(i);
				cell.getStyleAttributes().setBackground(ERR_COLOR);
			}
			SysUtil.abort();
		}
	}

	public boolean isLeafRow(KDTable table, int rowIndex) {
		if (rowIndex + 1 >= table.getRowCount()) {
			return true;
		}
		int rowLevel = table.getRow(rowIndex).getTreeLevel();
		int nextLevel = table.getRow(rowIndex + 1).getTreeLevel();
		if (nextLevel <= rowLevel) {
			return true;
		}
		return false;
	}

	public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (!checkSelectTable(table)) {
			showInfo();
			return;
		}
//		if (table.getUserObject() == null) {
//			return;
//		}
//		if (table.getColumnCount() == 0) {
//			return;
//		}
		int colIndex = table.getSelectManager().getActiveColumnIndex();
		boolean fComposeCol = "isCompose".equals(table.getColumnKey(colIndex));
		if (fComposeCol || "isKey".equals(table.getColumnKey(colIndex))) {
			for (int i = 1; i < table.getRowCount(); i++) {
				if (isLeafRow(table, i))
					table.getCell(i, colIndex).setValue(Boolean.TRUE);
			}
			if (fComposeCol) {
				setUnionData(table);
			}
		}else{
			MsgBox.showInfo(this,"请先选中支持是否选择的列");
		}
	}

	public void actionNoneSelect_actionPerformed(ActionEvent e)
			throws Exception {
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (!checkSelectTable(table)) {
			showInfo();
			return;
		}
//		if (table.getUserObject() == null) {
//			return;
//		}
//		if (table.getColumnCount() == 0) {
//			return;
//		}
		int colIndex = table.getSelectManager().getActiveColumnIndex();
		boolean fComposeCol = "isCompose".equals(table.getColumnKey(colIndex));
		if (fComposeCol || "isKey".equals(table.getColumnKey(colIndex))) {
			for (int i = 1; i < table.getRowCount(); i++) {
				if (isLeafRow(table, i))
					table.getCell(i, colIndex).setValue(Boolean.FALSE);
			}
			if (fComposeCol) {
				setUnionData(table);
			}
		}else{
			MsgBox.showInfo(this,"请先选中支持是否选择的列");
		}
	}

	private void setTableTotal(KDTable table) {
		NewListTempletColumnInfo amountSumCol = null;
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			NewListTempletColumnInfo col = null;
			
			if (column.getUserObject() instanceof NewListTempletColumnInfo) {
				col = (NewListTempletColumnInfo) column.getUserObject();
			}
			else{
				col = (NewListTempletColumnInfo) this.templateColumnObjectMap.get(table.getName()+column.getKey());
			}
			
			if (col != null
					&& (col.getHeadColumn().getProperty()
							.equals(DescriptionEnum.AmountSum))) {
				amountSumCol = col;
				break;
			}
		}
		IRow row = (IRow) table.getRow(0);
		List aimRowList = new ArrayList();
		for (int j = 1; j < table.getRowCount(); j++) {
			IRow rowAfter = table.getRow(j);
			if (isLeafRow(table, j))
				aimRowList.add(rowAfter);
		}
		if (aimRowList.size() > 0) {
			BigDecimal sum = null;
			for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
				IRow rowAdd = (IRow) aimRowList.get(rowIndex);
				Object value = rowAdd.getCell(
						amountSumCol.getHeadColumn().getName()).getValue();
				if (value != null) {
					if (sum == null) {
						sum = FMConstants.ZERO;
					}
					sum = sum.add(FDCNumberHelper.toBigDecimal(value));
				}
			}
			row.getCell(amountSumCol.getHeadColumn().getName()).setValue(sum);
		}
	}
	
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		
		setColumnObject();
//		String path = null;
		File tempFile = File.createTempFile("eastemp", ".xls");
		// path = tempFile.getCanonicalPath();
		KDTable  table = getTableForCommon();
		if(table != null){
			table.getIOManager().setTempFileDirection(
					tempFile.getPath());
			table.getIOManager().exportExcelToTempFile(false);
			
		}
		tempFile.deleteOnExit();
	}
	/**
     * 返回需要处理的表格。
     */
    protected KDTable getTableForCommon()
    {
    	if(this.tabbedPnlTable.getSelectedComponent() == null)
			return null;
		else
			return (KDTable)this.tabbedPnlTable.getSelectedComponent();
    }

	/**
	 * output actionImportExcel_actionPerformed
	 */
	public void actionImportExcel_actionPerformed(ActionEvent e)
			throws Exception {
		setColumnObject();
		KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			return;
		}
		if (table.getColumnCount() == 0) {
			MsgBox.showInfo("没有设置表头!");
			return;
		}
		if (MsgBox.OK != MsgBox.showConfirm2("此操作将覆盖以前的数据!")) {
			return;
		}
		KDFileChooser chsFile = new KDFileChooser();
		String XLS = "xls";
		String Key_File = "Key_File";
		SimpleFileFilter Filter_Excel = new SimpleFileFilter(XLS, "MS Excel"
				+ LanguageManager.getLangMessage(Key_File, WizzardIO.class,
						"操作失败"));
		chsFile.addChoosableFileFilter(Filter_Excel);
		int ret = chsFile.showOpenDialog(this);
		File file = null;
		if (ret == JFileChooser.APPROVE_OPTION) {
			file = chsFile.getSelectedFile();
		}

		loadImportXls(file);
	}

	public void loadImportXls(File file) throws Exception {
		if (file == null)
			return;

		String fileName = file.getAbsolutePath();
		KDSBook kdsbook = InviteHelper.excelReaderParse(this,fileName);
		if(kdsbook != null){
			Book importBook = KDSBookToBook.traslate(kdsbook);
			Sheet excelSheet = importBook.getSheet(0);
			KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
			List errList = InviteHelper.validateImportExcelHeadRow(table,excelSheet);
			if(InviteHelper.validateErrorInfoList(this,errList)){
				while (table.getRowCount() > 1) {
					table.removeRow(table.getRowCount() - 1);
				}
				KDSSheet kdsSheet = kdsbook.getSheet(new Integer(0));
				importSheetToTable(table, excelSheet, kdsSheet);
				InviteHelper.setAotuHeight(table);
			}
		}
	}

	private void importSheetToTable(KDTable table, Sheet excelSheet,
			KDSSheet kdsSheet) throws Exception {
		NewListTempletPageInfo page= (NewListTempletPageInfo) table.getUserObject();
		String headTypeId = page.getHeadType().getId().toString();
		KDBizPromptBox f7 = this.getF7Box(headTypeId);
		int maxRow = excelSheet.getMaxRowIndex();
		int maxColumn = excelSheet.getMaxColIndex();
		for (int row = 3; row <= maxRow; row++) {
			if (table.getRow(row - 2) == null) {
				NewListTempletEntryInfo infoEntry = new NewListTempletEntryInfo();
				IRow newRow = table.addRow();
				CellTreeNode node = new CellTreeNode();
				newRow.setTreeLevel(0);
				newRow.setUserObject(infoEntry);
				CellTextRenderImpl avr = new CellTextRenderImpl();
				avr.getText(new BizDataFormat("$name$"));
//				ObjectValueRenderImpl avr = new ObjectValueRenderImpl();
//				avr.setFormat(new BizDataFormat("$name$"));
				newRow.getCell(ItemName).setEditor(new KDTDefaultCellEditor(f7));
				newRow.getCell(ItemName).setRenderer(avr);
				newRow.getCell("level").setValue(new Integer(0));
				newRow.getCell("isKey").setValue(Boolean.FALSE);
				newRow.getCell("isCompose").setValue(Boolean.FALSE);
				node.setValue(new Integer(0));
				node.setTreeLevel(0);
				infoEntry.setLevel(0);
				infoEntry.setIsLeaf(true);
				infoEntry.setIsCanZeroProAmount(false);
			}
			for (int col = 1; col <= maxColumn; col++) {
				ICell tblCell = table.getCell(row - 2, col);
				if (tblCell == null || tblCell.getStyleAttributes().isLocked())
					continue;

				Cell excelCell = excelSheet.getCell(row, col, true);
				Variant cellRawVal = excelCell.getValue();
				if (Variant.isNull(cellRawVal))
					continue;

				String colValue = cellRawVal.toString();
				logger.debug("excelData :" + colValue);
				table.getRow(row - 2).getCell("level").setValue(new Integer(0));
				table.getRow(row - 2).setTreeLevel(0);
				if (colValue.equals("是")) {
					tblCell.setValue(Boolean.valueOf(true));
				} else if (colValue.equals("否")) {
					tblCell.setValue(Boolean.valueOf(false));
				} else {
					NewListTempletColumnInfo colInfo = null;
					
					if (table.getColumn(col).getUserObject() instanceof NewListTempletColumnInfo) {
						colInfo = (NewListTempletColumnInfo) table.getColumn(col).getUserObject();
					}
					else{
						colInfo = (NewListTempletColumnInfo) this.templateColumnObjectMap.get(table.getName()+table.getColumn(col).getKey());
					}
					if(colInfo !=null){
						ColumnTypeEnum colType = colInfo.getHeadColumn()
								.getColumnType();
						Object kdsVal = kdsSheet.getCell(row, col, true)
								.getValue();
						InviteHelper.loadExcelVal(tblCell, cellRawVal, kdsVal,
								colType);
						// 如果金额列中输入了普通字符，直接用new BigDecimal会导致错误
						// if (ColumnTypeEnum.Amount.equals(colType)) {
						// cell.setValue(FDCHelper.toBigDecimal(colValue));
						// } else {
						// if (cellRawVal.getVt() == Variant.VT_CALENDAR
						// || cellRawVal.getVt() == Variant.VT_DATE) {
						// cell.setValue(excelCell.getFormula());
						//					} else
						//						cell.setValue(colValue);
						// }
					}
				}
			}
		}
		setUnionData(table);
	}

	public void onShow() throws Exception {
		super.onShow();
		this.txtNumber.requestFocus();
	}

	protected void attachListeners() {
		// TODO 自动生成方法存根

	}

	protected void detachListeners() {
		// TODO 自动生成方法存根

	}

	protected KDTable getDetailTable() {
		// TODO 自动生成方法存根
		return null;
	}
	
	protected void getAttachMentItem(KDTable table) {
		return;
	}

	public KDMenuItem getAttachMenuItem(KDTable table) {
		return null;
	}
	
	/**
	 * 
	 * 描述：从第三个页签起，校验每行数据，确保同父级下子目不能重复
	 * @Author：owen_wen
	 * @CreateTime：2011-10-24
	 */
	private void verifyDuplicationItem() {
		for (int i = 2; i < tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) tabbedPnlTable.getComponentAt(i);
			InviteClientHelper.verifyDuplicationItem(table, ItemName);
		}
	}
}