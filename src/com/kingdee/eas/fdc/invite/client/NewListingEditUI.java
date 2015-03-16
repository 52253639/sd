/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
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
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.AppraiseResultFactory;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnCollection;
import com.kingdee.eas.fdc.invite.HeadColumnFactory;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.InviteProjectEntryCollection;
import com.kingdee.eas.fdc.invite.InviteProjectEntryFactory;
import com.kingdee.eas.fdc.invite.InviteProjectEntryInfo;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteProjectStateEnum;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.fdc.invite.NewListTempletCollection;
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
import com.kingdee.eas.fdc.invite.NewListTempletValueInfo;
import com.kingdee.eas.fdc.invite.NewListingCollection;
import com.kingdee.eas.fdc.invite.NewListingColumnCollection;
import com.kingdee.eas.fdc.invite.NewListingColumnInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryCollection;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewListingPageCollection;
import com.kingdee.eas.fdc.invite.NewListingPageFactory;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.PageHeadInfo;
import com.kingdee.eas.fdc.invite.client.offline.util.AttachmentPermissionUtil;
import com.kingdee.eas.fi.newrpt.client.designer.io.WizzardIO;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.EnumUtils;

/**
 * 招标清单编制界面
 */
public class NewListingEditUI extends AbstractNewListingEditUI {

	private static final String ItemName = "子目名称";

	private static final Color CAN_ZERO_COLOR = new Color(233, 134, 54);

	private static final Color LOCKCOLOR = new Color(0xF0EDD9);

	private static final Color REQUIREDCOLOR = new Color(0, true);

	private static final Color ERR_COLOR = new Color(0xFF, 0xEA, 0x67);

	private static final Logger logger = CoreUIObject
			.getLogger(NewListingEditUI.class);

	private CompanyOrgUnitInfo currentCompany = ContextHelperFactory.getRemoteInstance().getCurrentCompany();

	CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();

	Map columnObjMap = new HashMap();

	Map refPriceBakMap = new HashMap();

	int copyLine = 0;

	private transient ICellEditor amtEditor = null;

	private transient ICellEditor dateEditor = null;

	private transient ICellEditor txtEditor = null;

	private transient ICellEditor chkEditor = null;

	private int userConfigLoadTimes = 0;

	private boolean statusEditUserConfigFlag = false;
	private Map isSaveUserConfigMap = new HashMap();

	HashMap listingColumnObjectMap = new HashMap();

	private boolean isReversion = false;
	
	private boolean isRate = false;
	
	public NewListingEditUI() throws Exception {
		super();
		KDFormattedTextField txtAmtFld = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		if(isRate){
			txtAmtFld.setPrecision(2);
		}else{
			txtAmtFld.setPrecision(2);
		}		
		txtAmtFld.setRemoveingZeroInDispaly(false);
		txtAmtFld.setRemoveingZeroInEdit(false);
		txtAmtFld.setNegatived(false);
		txtAmtFld.setMaximumValue(FDCNumberConstants.MAX_VALUE);
		txtAmtFld.setMinimumValue(FDCNumberConstants.MIN_VALUE);
		txtAmtFld.setHorizontalAlignment(SwingConstants.RIGHT);
		txtAmtFld.setSupportedEmpty(true);
		
		amtEditor = new KDTDefaultCellEditor(txtAmtFld);

		KDDatePicker pkDate = new KDDatePicker();
		dateEditor = new KDTDefaultCellEditor(pkDate);

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);

		KDCheckBox chkField = new KDCheckBox();
		chkEditor = new KDTDefaultCellEditor(chkField);
	}

	public KDTabbedPane getTabbedPane() {
		return tabbedPnlTable;
	}

	public void storeFields() {
		KDTable totalTable = (KDTable) this.tabbedPnlTable.getComponentAt(1);
		int tabCount = this.tabbedPnlTable.getTabCount();
		editData.setTax((BigDecimal) totalTable.getCell(tabCount - 1, "amount")
				.getValue());
		editData.setInviteAuditState(FDCBillStateEnum.SAVED);
		super.storeFields();
		this.initOldData(this.editData);
	}

	public void submitHeadColumn() throws EASBizException, BOSException {
		NewListingPageCollection pages = this.editData.getPages();
		for (int i = 0; i < pages.size(); i++) {
			NewListingPageInfo page = pages.get(i);
			NewListingColumnCollection columns = page.getColumns();
			for (int j = 0; j < columns.size(); j++) {
				HeadColumnInfo column = columns.get(j).getHeadColumn();
				if (column.getDescription() != null
						&& column.getDescription().equals("submit")) {
					column.setDescription(null);
					HeadColumnFactory.getRemoteInstance().submit(column);
				}
			}
		}
	}

	/**
	 * 描述：附件管理
	 * add by liuguangyue 2010-04-27
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)	throws Exception {
		String boID = getSelectBOID();
		if (boID == null) {
			return;
		}
		boolean isEdit = false;
		if (STATUS_ADDNEW.equals(getOprtState())
				|| STATUS_EDIT.equals(getOprtState())) {
			isEdit = true;
		}

		 //add liuguangyue 2010-4-28
		isEdit = AttachmentPermissionUtil.checkAuditingAttachmentEdit(editData.getState(), boID,isEdit);
		
		// modify liuguangyue 2010-4-27
		AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		info.setBoID(boID);
		MultiApproveUtil.showAttachmentManager(info, this, editData, String
				.valueOf("1"), isEdit);
		// super.actionAttachment_actionPerformed(e);
	    }

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		setTableConfig();
		setColumnObject();
		isSaveAction = true;
		this.beforeStoreFields(e);
		submitHeadColumn();
		storeEntrys();
		super.actionSave_actionPerformed(e);
		CacheServiceFactory.getInstance().discardType(new NewListingPageInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListingColumnInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListingEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListingValueInfo().getBOSType());

		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);

		this.prmtInviteProject.setValue(editData.getInviteProject());
	}

	public void setTableConfig() {
		for (int i = 0; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) this.tabbedPnlTable.getComponentAt(i);
			if (isSaveUserConfigMap.containsKey(table.getName())) {
				if (((Boolean) isSaveUserConfigMap.get(table.getName())).booleanValue()) {
					tHelper.saveConfig(table);
				}
			}
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		setTableConfig();
		setColumnObject();
		isSaveAction = false;
		this.setOprtState("EDIT");
		this.beforeStoreFields(e);
		submitHeadColumn();
		storeEntrys();
		super.actionSubmit_actionPerformed(e);
		// 清除缓存
		CacheServiceFactory.getInstance().discardType(
				new NewListingPageInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(
				new NewListingColumnInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(
				new NewListingEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(
				new NewListingValueInfo().getBOSType());

		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionSave.setEnabled(false);
	}

	public IObjectPK runSubmit() throws Exception {
		IObjectPK pk = NewListingFactory.getRemoteInstance().submit(
				this.editData);
		for (int i = 0, pageSize = editData.getPages().size(); i < pageSize; i++) {
			NewListingPageFactory.getRemoteInstance().submit(
					editData.getPages().get(i));
		}
		return pk;
	}

	public IObjectPK runSave() throws Exception {
		IObjectPK pk = NewListingFactory.getRemoteInstance()
				.save(this.editData);
		for (int i = 0, pageSize = editData.getPages().size(); i < pageSize; i++) {
			NewListingPageFactory.getRemoteInstance().save(
					editData.getPages().get(i));
		}
		return pk;
	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		// String path = null;
		File tempFile = File.createTempFile("eastemp", ".xls");
		// path = tempFile.getCanonicalPath();
		KDTable table = getTableForCommon();
		if (table != null) {
			table.getIOManager().setTempFileDirection(tempFile.getPath());
			table.getIOManager().exportExcelToTempFile(false);
		}
		tempFile.deleteOnExit();
	}

	/**
	 * 返回需要处理的表格。
	 */
	protected KDTable getTableForCommon() {
		if (this.tabbedPnlTable.getSelectedComponent() == null)
			return null;
		else
			return (KDTable) this.tabbedPnlTable.getSelectedComponent();
	}

	private void storeEntrys() {
		KDTable desTable = (KDTable) this.tabbedPnlTable.getComponentAt(0);
		for (int i = 0; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) tabbedPnlTable.getComponentAt(i);
			NewListingPageInfo page = (NewListingPageInfo) table
					.getUserObject();
			if (page != null) {
				page.setDescription((String) desTable.getCell(i - 2, "des")
						.getValue());
				page.getEntrys().clear();
				for (int j = 1; j < table.getRowCount(); j++) {
					IRow row = table.getRow(j);
					if (row.getUserObject() instanceof NewListingEntryInfo) {
						NewListingEntryInfo entryInfo = new NewListingEntryInfo();
						NewListingEntryInfo tempInfo = (NewListingEntryInfo) row
								.getUserObject();
						entryInfo.getValues().clear();
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
							if (row.getCell(ItemName).getValue() != null) {
								entryInfo.setItemName(row.getCell(ItemName)
										.getValue().toString());
								entryInfo.setItem(null);
							}
						}
						entryInfo.setSeq(j);
						entryInfo.setIsLeaf(tempInfo.isIsLeaf());
						entryInfo.setIsCanZeroProAmount(tempInfo
								.isIsCanZeroProAmount());
						if (row.getCell("isKey").getValue() != null)
							entryInfo.setIsKey(((Boolean) row.getCell("isKey")
									.getValue()).booleanValue());
						else
							entryInfo.setIsKey(false);
						if (row.getCell("isCompose").getValue() != null)
							entryInfo.setIsCompose(((Boolean) row.getCell(
									"isCompose").getValue()).booleanValue());
						else
							entryInfo.setIsCompose(false);
						NewListingValueCollection valueColl = getRowValues(row,
								table, entryInfo, page);
						entryInfo.getValues().addCollection(valueColl);
						entryInfo.setIsLeaf(this.isLeafRow(table, j));
						page.getEntrys().add(entryInfo);
					}
				}
			}
		}
	}

	private NewListingValueCollection getRowValues(IRow row, KDTable table,
			NewListingEntryInfo entryInfo, NewListingPageInfo page) {
		int colCount = table.getColumnCount();
		NewListingValueCollection valueColl = new NewListingValueCollection();
		NewListingValueInfo valueInfo = null;
		NewListingColumnInfo columnInfo = null;
		for (int i = 3; i < colCount; i++) {
			valueInfo = new NewListingValueInfo();
			IColumn column = table.getColumn(i);
			// if (column.getUserObject() instanceof NewListingColumnInfo) {
			// columnInfo = (NewListingColumnInfo) column.getUserObject();
			// valueInfo.setColumn(columnInfo);
			// }

			if (column.getUserObject() instanceof NewListingColumnInfo) {
				columnInfo = (NewListingColumnInfo) column.getUserObject();
			} else {
				columnInfo = (NewListingColumnInfo) listingColumnObjectMap
						.get(table.getName() + column.getKey());
			}
			valueInfo.setColumn(columnInfo);

			valueInfo.setEntry(entryInfo);
			ICell cell = row.getCell(i);
			if (cell.getValue() != null) {
				if (column.getKey() == ItemName) {
					if (cell.getValue() instanceof ListingItemInfo) {
						ListingItemInfo item = (ListingItemInfo) cell
								.getValue();
						valueInfo.setText(item.getName());
					} else {
						valueInfo.setText(cell.getValue().toString());
					}
				} else {
					ColumnTypeEnum colType = columnInfo.getHeadColumn()
							.getColumnType();
					InviteHelper.storeListingValue(valueInfo, cell.getValue(),
							colType);
				}
			}
			if (columnInfo.isIsQuoting()) {
				NewListingValueInfo oldValue = null;
				if (page.getHeadType().getId() != null
						&& row.getCell(ItemName).getValue() != null
						&& columnInfo.getHeadColumn() != null)
					oldValue = (NewListingValueInfo) refPriceBakMap.get(page
							.getHeadType().getId().toString()
							+ row.getCell(ItemName).getValue().toString()
							+ columnInfo.getHeadColumn().getName());
				if (oldValue != null) {
					valueInfo.setAmount(oldValue.getAmount());
					valueInfo.setText(oldValue.getText());
					valueInfo.setDateValue(oldValue.getDateValue());
				}
			}
			valueColl.add(valueInfo);
		}
		return valueColl;
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
		if (this.tabbedPnlTable.getTabCount() == 1) {
			MsgBox.showInfo("没有导入模板!");
			abort();
		}
		FDCClientVerifyHelper.verifyRequire(this);
		if (!isSaveAction) {
			verifyData();
		}
	}

	protected NewListingCollection getNewListingCols(String paramInvPrjId,
			String paramId) throws BOSException {
		NewListingCollection cols = new NewListingCollection();

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("inviteProject"));

		FilterInfo filter = new FilterInfo();
		if (paramInvPrjId != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("inviteProject", paramInvPrjId));
		}
		if (paramId != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", paramId));
		}

		view.setFilter(filter);
		cols = NewListingFactory.getRemoteInstance().getNewListingCollection(
				view);

		return cols;
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
						name = ((ListingItemInfo) nameCell.getValue())
								.getName();
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
					// tableMap.put(table.getName(), table);
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
			// 校验工程量
			for (int j = 1; j < table.getRowCount(); j++) {
				NewListingEntryInfo entry = (NewListingEntryInfo) table.getRow(
						j).getUserObject();
				if (table.getCell(j, "isCompose").getValue() != null
						&& !entry.isIsCanZeroProAmount()) {
					boolean isCompose = ((Boolean) table
							.getCell(j, "isCompose").getValue()).booleanValue();
					boolean isZero = false;
					int projectAmtCount = 0;
					for (int k = 3, colCount = table.getColumnCount(); k < colCount; k++) {
						IColumn col = table.getColumn(k);
						if (col.getUserObject() != null) {
							NewListingColumnInfo column = null;

							if (col.getUserObject() instanceof NewListingColumnInfo) {
								column = (NewListingColumnInfo) col
										.getUserObject();
							} else {
								column = (NewListingColumnInfo) listingColumnObjectMap
										.get(table.getName() + col.getKey());
							}

							ICell cell = table.getCell(j, k);
							if (column.getHeadColumn().getProperty().equals(
									DescriptionEnum.ProjectAmtSum)) {
								cell.getStyleAttributes().setBackground(
										REQUIREDCOLOR);
								Object value = table.getCell(j, k).getValue();
								if (value == null) {
									isZero = true;
								} else {
									BigDecimal val = FDCNumberHelper
											.toBigDecimal(value);
									if (val.compareTo(FDCNumberConstants.ZERO) == 0) {
										isZero = true;
									}
								}
								break;
							}
							if (column.getHeadColumn().getProperty().equals(
									DescriptionEnum.ProjectAmt)) {
								cell.getStyleAttributes().setBackground(
										REQUIREDCOLOR);
								projectAmtCount++;
							}
						}
					}
					if (isZero) {
						for (int k = 3, colCount = table.getColumnCount(); k < colCount; k++) {
							IColumn col = table.getColumn(k);
							if (col.getUserObject() != null) {
								NewListingColumnInfo column = null;

								if (col.getUserObject() instanceof NewListingColumnInfo) {
									column = (NewListingColumnInfo) col
											.getUserObject();
								} else {
									column = (NewListingColumnInfo) listingColumnObjectMap
											.get(table.getName() + col.getKey());
								}

								DescriptionEnum property = column
										.getHeadColumn().getProperty();
								ICell cell = table.getCell(j, k);
								if (property.equals(DescriptionEnum.ProjectAmt)) {
									if (isCompose) {
										quantityNullList.add(cell);
										tableMap.put(table.getName(), table);
									}
								} else if (property
										.equals(DescriptionEnum.ProjectAmtSum)) {
									if (!isCompose || projectAmtCount == 0) {
										quantityNullList.add(cell);
										tableMap.put(table.getName(), table);
									}
								}
							}
						}
					}
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

	public void actionColumnSetting_actionPerformed(ActionEvent e)
			throws Exception {
		KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			return;
		}
		int confirm = MsgBox.showConfirm2(this, "此操作会丢失未保存的数据,请确认是否继续?");
		if (confirm == MsgBox.OK) {
			NewListingColumnCollection collRemove = new NewListingColumnCollection();
			NewListingColumnCollection collAmtRemove = new NewListingColumnCollection();
			HeadColumnCollection coll = new HeadColumnCollection();
			HeadColumnCollection collAmount = new HeadColumnCollection();
			int projectAmtIndex = 0;
			int amountIndex = 0;
			for (int i = 3, colCount = table.getColumnCount(); i < colCount; i++) {
				IColumn col = table.getColumn(i);
				if (col.getUserObject() != null) {
					NewListingColumnInfo column = null;

					if (col.getUserObject() instanceof NewListingColumnInfo) {
						column = (NewListingColumnInfo) col.getUserObject();
					} else {
						column = (NewListingColumnInfo) listingColumnObjectMap
								.get(table.getName() + col.getKey());
					}

					if (column != null) {
						DescriptionEnum property = column.getHeadColumn()
								.getProperty();
						if (property.equals(DescriptionEnum.ProjectAmtSum)
								&& column.getHeadColumn().getName().equals(
										"工程量")) {
							projectAmtIndex = i;
							coll.add(column.getHeadColumn());
							collRemove.add(column);
						} else if (property.equals(DescriptionEnum.ProjectAmt)) {
							coll.add(column.getHeadColumn());
							collRemove.add(column);
							NewListingColumnInfo columnPre = null;

							if (table.getColumn(i - 1).getUserObject() instanceof NewListingColumnInfo) {
								columnPre = (NewListingColumnInfo) table
										.getColumn(i - 1).getUserObject();
							} else {
								columnPre = (NewListingColumnInfo) listingColumnObjectMap
										.get(table.getName()
												+ table.getColumn(i - 1)
														.getKey());
							}

							if (!(columnPre.getHeadColumn().getProperty()
									.equals(DescriptionEnum.ProjectAmt))) {
								projectAmtIndex = i;
							}
						} else if (property
								.equals(DescriptionEnum.ProjectAmtSum)) {
							coll.add(column.getHeadColumn());
							collRemove.add(column);
						} else if (property.equals(DescriptionEnum.AmountSum)
								&& column.getHeadColumn().getName()
										.equals("金额")) {
							amountIndex = i;
							collAmount.add(column.getHeadColumn());
							collAmtRemove.add(column);
						} else if (property.equals(DescriptionEnum.Amount)) {
							collAmount.add(column.getHeadColumn());
							collAmtRemove.add(column);
							NewListingColumnInfo columnPre = null;

							if (table.getColumn(i - 1).getUserObject() instanceof NewListingColumnInfo) {
								columnPre = (NewListingColumnInfo) table
										.getColumn(i - 1).getUserObject();
							} else {
								columnPre = (NewListingColumnInfo) listingColumnObjectMap
										.get(table.getName()
												+ table.getColumn(i - 1)
														.getKey());
							}
							if (!(columnPre.getHeadColumn().getProperty()
									.equals(DescriptionEnum.Amount))) {
								amountIndex = i;
							}
						} else if (property.equals(DescriptionEnum.AmountSum)) {
							collAmount.add(column.getHeadColumn());
							collAmtRemove.add(column);
						}
					}

				}
			}
			UIContext uiContext = new UIContext(this);
			if (coll.size() > 0) {
				uiContext.put("columnColletion", coll);
			}
			if (collAmount.size() > 0) {
				uiContext.put("amountColletion", collAmount);
			}
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(NewListColumnSettingUI.class.getName(), uiContext,
							null, "View");
			uiWindow.show();
			NewListingPageInfo info = (NewListingPageInfo) table
					.getUserObject();
			if (((NewListColumnSettingUI) uiWindow.getUIObject()).isOk()) {
				HeadColumnCollection collNew = ((NewListColumnSettingUI) uiWindow
						.getUIObject()).getHeadColumnCollection(true);
				HeadColumnCollection collAmountNew = ((NewListColumnSettingUI) uiWindow
						.getUIObject()).getAmountHeadColumnCollection();
				if (collNew.size() > 0) {
					int size = collNew.size();
					NewListingColumnInfo oldInfo = null;

					if (table.getColumn(projectAmtIndex).getUserObject() instanceof NewListingColumnInfo) {
						oldInfo = (NewListingColumnInfo) table.getColumn(
								projectAmtIndex).getUserObject();
					} else {
						oldInfo = (NewListingColumnInfo) listingColumnObjectMap
								.get(table.getName()
										+ table.getColumn(projectAmtIndex)
												.getKey());
					}

					int oldSeq = oldInfo.getSeq();
					int columnSize = info.getColumns().size();
					for (int col = 0; col < columnSize; col++) {
						NewListingColumnInfo changeInfo = info.getColumns()
								.get(col);
						if (changeInfo.getSeq() > oldSeq) {
							changeInfo.setSeq(changeInfo.getSeq() + size - 1);
						}
					}
					for (int j = 0, oldsize = collRemove.size(); j < oldsize; j++) {
						info.getColumns().remove(collRemove.get(j));
					}
					for (int i = 0; i < size; i++) {
						HeadColumnInfo infoColumn = collNew.get(i);
						if (infoColumn != null) {
							NewListingColumnInfo columnInfo = new NewListingColumnInfo();
							columnInfo.setId(BOSUuid.create(columnInfo
									.getBOSType()));
							columnInfo.setIsQuoting(false);
							columnInfo.setHeadColumn(infoColumn);
							columnInfo.setPage(info);
							columnInfo.setSeq(oldSeq + i);
							columnInfo.setId(BOSUuid.create(columnInfo
									.getBOSType()));
							info.getColumns().addObject(oldSeq + i, columnInfo);
						}
					}
					columnSize = info.getColumns().size();
					NewListingColumnInfo oldAmountInfo = null;

					if (table.getColumn(amountIndex).getUserObject() instanceof NewListingColumnInfo) {
						oldAmountInfo = (NewListingColumnInfo) table.getColumn(
								amountIndex).getUserObject();
					} else {
						oldAmountInfo = (NewListingColumnInfo) listingColumnObjectMap
								.get(table.getName()
										+ table.getColumn(amountIndex).getKey());
					}

					int oldAmountSeq = info.getColumns().indexOf(oldAmountInfo);

					for (int col = 0; col < columnSize; col++) {
						NewListingColumnInfo changeInfo = info.getColumns()
								.get(col);
						if (changeInfo.getSeq() > oldAmountSeq) {
							changeInfo.setSeq(changeInfo.getSeq() + size - 1);
						}
					}
					for (int j = 0, oldsize = collAmtRemove.size(); j < oldsize; j++) {
						info.getColumns().remove(collAmtRemove.get(j));
					}
					for (int i = 0; i < size; i++) {
						HeadColumnInfo infoColumn = collAmountNew.get(i);
						if (infoColumn != null) {
							NewListingColumnInfo columnInfo = new NewListingColumnInfo();
							columnInfo.setIsQuoting(false);
							columnInfo.setHeadColumn(infoColumn);
							columnInfo.setPage(info);
							columnInfo.setSeq(oldAmountSeq + i);
							columnInfo.setId(BOSUuid.create(columnInfo
									.getBOSType()));
							info.getColumns().addObject(oldAmountSeq + i,
									columnInfo);
						}
					}
				} else {
					NewListingColumnCollection columns = info.getColumns();
					int size = columns.size();
					NewListingColumnCollection removeNode = new NewListingColumnCollection();
					for (int i = 0; i < size; i++) {
						NewListingColumnInfo col = columns.get(i);
						if (col.getHeadColumn().getProperty().equals(
								DescriptionEnum.Amount)
								|| col.getHeadColumn().getProperty().equals(
										DescriptionEnum.ProjectAmt)) {
							removeNode.add(col);
						} else if (col.getHeadColumn().getProperty().equals(
								DescriptionEnum.AmountSum)
								|| col.getHeadColumn().getProperty().equals(
										DescriptionEnum.ProjectAmtSum)) {
							// BOSUuid parentId =
							// col.getHeadColumn().getParent()
							// .getId();
							// HeadColumnInfo parentInfo = HeadColumnFactory
							// .getRemoteInstance().getHeadColumnInfo(
							// new ObjectUuidPK(parentId));
							if (col.getHeadColumn().getParent() != null) {
								col.setHeadColumn(col.getHeadColumn()
										.getParent());
							}
						}
					}
					for (int i = 0, oldsize = removeNode.size(); i < oldsize; i++) {
						info.getColumns().remove(removeNode.get(i));
					}
					for (int col = 0; col < info.getColumns().size(); col++) {
						NewListingColumnInfo changeInfo = info.getColumns()
								.get(col);
						changeInfo.setSeq(col);
					}
				}
				loadPages();
			}
		}
	}

	/**
	 * output actionImportExcel_actionPerformed
	 */
	public void actionImportExcel_actionPerformed(ActionEvent e)
			throws Exception {
		setColumnObject();
		if (editData.getPages().size() <= 0) {
			MsgBox.showError("必须引入模板！");
			return;
		}
		if (tabbedPnlTable.getSelectedIndex() <= 1) {
			MsgBox.showError("当前页签不允许导入Excel！");
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
		if (ret != JFileChooser.APPROVE_OPTION)
			return;

		File file = chsFile.getSelectedFile();
		importExcel(file);
	}

	public void importExcel(File file) throws Exception {
		if (file == null || editData.getPages().size() <= 0
				|| tabbedPnlTable.getSelectedIndex() == 0) {
			logger.warn("importFile null:" + (file == null)
					+ ", editDataPages:" + editData.getPages().size()
					+ ", tabPanl selInd:" + tabbedPnlTable.getSelectedIndex());
			return;
		}
		String fileName = file.getAbsolutePath();
		KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
		KDSBook kdsbook = InviteHelper.excelReaderParse(this, fileName);
		if (kdsbook != null) {
			Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(0);
			List errList = InviteHelper.validateImportExcelHeadRow(table,
					excelSheet);
			if (InviteHelper.validateErrorInfoList(this, errList)) {
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
		NewListingPageInfo page = (NewListingPageInfo) table.getUserObject();
		String headTypeId = page.getHeadType().getId().toString();
		KDBizPromptBox f7 = this.getF7Box(headTypeId);
		int maxRow = excelSheet.getMaxRowIndex();
		int maxColumn = excelSheet.getMaxColIndex();
		for (int row = 3; row <= maxRow; row++) { // Excel的数据是从第4行开始读取
			int tableRowIndex = row - 2; // eas客户端中的数据是从第2行开始填充
			if (table.getRow(tableRowIndex) == null) {
				NewListingEntryInfo infoEntry = new NewListingEntryInfo();
				IRow newRow = table.addRow();
				CellTreeNode node = new CellTreeNode();
				newRow.setTreeLevel(0);
				newRow.setUserObject(infoEntry);
				CellTextRenderImpl avr = new CellTextRenderImpl();
				avr.getText(new BizDataFormat("$name$"));
				// ObjectValueRenderImpl avr = new ObjectValueRenderImpl();
				// avr.setFormat(new BizDataFormat("$name$"));
				newRow.getCell(ItemName).setEditor(new KDTDefaultCellEditor(f7));
				newRow.getCell(ItemName).setRenderer(avr);
				newRow.getCell("level").setValue(new Integer(0));
				newRow.getCell("isKey").setValue(Boolean.FALSE);
				newRow.getCell("isCompose").setValue(Boolean.FALSE);
				node.setValue(new Integer(0));
				node.setTreeLevel(0);
				infoEntry.setLevel(0);
				infoEntry.setIsLeaf(true);
			}
			for (int col = 0; col <= maxColumn; col++) {
				ICell tblCell = table.getCell(tableRowIndex, col);
				Variant cellRawVal = excelSheet.getCell(row, col, true).getValue();
				if (Variant.isNull(cellRawVal)) {
					continue;
				}

				String colValue = cellRawVal.toString();
				if (col == 0) { // 级次列
					table.getRow(tableRowIndex).getCell("level").setValue(new Integer(colValue));
					table.getRow(tableRowIndex).setTreeLevel(Integer.parseInt(colValue));
				} else if (col == 1 || col == 2) { // 关键子目，单价构成 列
					if (colValue.equals("是")) {
						tblCell.setValue(Boolean.valueOf(true));
					} else if (colValue.equals("否")) {
						tblCell.setValue(Boolean.valueOf(false));
					}
				} else { // 其它列
					if (table.getColumn(col).getKey().equals(ItemName)) {
						if (colValue.length() > 500) {
							colValue = colValue.substring(499);
						}
					} else {
						if (colValue.length() > 80) {
							colValue = colValue.substring(79);
						}
					}
					tblCell.setValue(colValue);
				}
				
			}
		}
		setUnionData(table);
	}

	/**
	 * output actionImportTemplet_actionPerformed
	 */
	public void actionImportTemplet_actionPerformed(ActionEvent e)
			throws Exception {
		String templetId = NewTempletChooseUI.showChooseUI(this,
				(InviteTypeInfo) this.prmtInviteType.getValue());
		loadChoosenTemplet(templetId);
		InviteProjectInfo projectInfo = (InviteProjectInfo) this.prmtInviteProject
				.getValue();
		if (projectInfo != null)
			this.actionImportMaterial.setEnabled(projectInfo.isIsMaterial());
	}

	/**
	 * 
	 * @param templetId
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public void loadChoosenTemplet(String templetId) throws BOSException,
			EASBizException {
		if (templetId == null || templetId.trim().length() == 0) {
			return;
		}
		editData.getPages().clear();
		NewListTempletInfo templet = getTempletInfo(templetId);
		prmtInviteType.setValue(templet.getInviteType());
		prmtInviteType.setEnabled(false);
		NewListTempletPageCollection pageColl = templet.getPages();
		int pageSize = pageColl.size();

		for (int i = 0; i < pageSize; i++) {
			NewListTempletPageInfo pageInfo = pageColl.get(i);
			NewListingPageInfo pageListingInfo = new NewListingPageInfo();
			pageListingInfo.setId(BOSUuid.create(pageListingInfo.getBOSType()));
			pageListingInfo.setHead(editData);
			pageListingInfo.setHeadType(pageInfo.getHeadType());
			pageListingInfo.setPageHead(pageInfo.getPageHead());
			pageListingInfo.setSeq(pageInfo.getSeq());
			pageListingInfo.setDescription(pageInfo.getDescription());
			NewListTempletEntryCollection entryColl = pageInfo.getEntrys();
			NewListTempletColumnCollection columnColl = pageInfo
					.getTempletColumns();
			int entrySize = entryColl.size();
			int columnSize = columnColl.size();
			pageListingInfo.getColumns().clear();
			Map columnMap = new HashMap();
			for (int k = 0; k < columnSize; k++) {
				NewListingColumnInfo columnListingInfo = new NewListingColumnInfo();
				NewListTempletColumnInfo columnInfo = columnColl.get(k);
				columnListingInfo.setId(BOSUuid.create(columnListingInfo
						.getBOSType()));
				columnListingInfo.setPage(pageListingInfo);
				columnListingInfo.setId(BOSUuid.create(columnListingInfo
						.getBOSType()));
				columnListingInfo.setIsQuoting(columnInfo.isIsQuoting());
				columnListingInfo.setHeadColumn(columnInfo.getHeadColumn());
				columnListingInfo.setSeq(columnInfo.getSeq());
				pageListingInfo.getColumns().add(columnListingInfo);
				columnMap.put(columnInfo.getId().toString(), columnListingInfo);
			}
			pageListingInfo.getEntrys().clear();
			for (int j = 0; j < entrySize; j++) {
				NewListingEntryInfo entryListingInfo = new NewListingEntryInfo();
				NewListTempletEntryInfo entryInfo = entryColl.get(j);
				entryListingInfo.setHead(pageListingInfo);
				entryListingInfo.setSeq(entryInfo.getSeq());
				entryListingInfo.setLevel(entryInfo.getLevel());
				entryListingInfo.setIsCompose(entryInfo.isIsCompose());
				entryListingInfo.setIsKey(entryInfo.isIsKey());
				entryListingInfo.setIsLeaf(entryInfo.isIsLeaf());
				entryListingInfo.setItem(entryInfo.getItem());
				entryListingInfo.setItemName(entryInfo.getItemName());
				entryListingInfo.setItemNumber(entryInfo.getItemNumber());
				entryListingInfo.setId(BOSUuid.create(entryListingInfo
						.getBOSType()));
				int valueSize = entryInfo.getValues().size();
				entryListingInfo.getValues().clear();
				for (int m = 0; m < valueSize; m++) {
					NewListingValueInfo valueListingInfo = new NewListingValueInfo();
					NewListTempletValueInfo valueInfo = entryInfo.getValues()
							.get(m);
					valueListingInfo.setAmount(valueInfo.getAmount());
					valueListingInfo.setDateValue(valueInfo.getDateValue());
					valueListingInfo.setText(valueInfo.getText());
					valueListingInfo.setEntry(entryListingInfo);
					valueListingInfo.setSeq(valueInfo.getSeq());
					NewListingColumnInfo newListingColumnInfo = (NewListingColumnInfo) columnMap
							.get(valueInfo.getColumn().getId().toString());
					if (newListingColumnInfo == null) {
						continue;
					}
					valueListingInfo.setColumn(newListingColumnInfo);
					entryListingInfo.getValues().add(valueListingInfo);
				}
				pageListingInfo.getEntrys().add(entryListingInfo);
			}
			editData.getPages().add(pageListingInfo);
		}
		loadPages();
	}

	private NewListTempletInfo getTempletInfo(String templetId)
			throws BOSException, EASBizException {
		// NewListTempletInfo templet = new NewListTempletInfo();
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("inviteType.*"));
		sic.add(new SelectorItemInfo("oriOrg.*"));
		sic.add(new SelectorItemInfo("pages.*"));
		sic.add(new SelectorItemInfo("pages.pageHead.*"));
		sic.add(new SelectorItemInfo("pages.headType.*"));
		sic.add(new SelectorItemInfo("pages.templetColumns.*"));
		sic.add(new SelectorItemInfo("pages.entrys.*"));
		sic.add(new SelectorItemInfo("pages.templetColumns.headColumn.*"));
		sic.add(new SelectorItemInfo("pages.templetColumns.headColumn.parent.*"));
		sic.add(new SelectorItemInfo("pages.entrys.item.*"));
		sic.add(new SelectorItemInfo("pages.entrys.values.*"));
		NewListTempletInfo templet = NewListTempletFactory.getRemoteInstance().getNewListTempletInfo(new ObjectUuidPK(BOSUuid.read(templetId)), sic);
		// 如果是分配的模版，是没有page的，需要找到原模版
		if (templet.getPages().size() == 0) {
			BOSUuid oriOrgId = templet.getOriOrg().getId();
			String templetNumber = templet.getNumber();
			EntityViewInfo view = new EntityViewInfo();
			for (int i = 0; i < sic.size(); i++)
				view.getSelector().add(sic.get(i));
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("orgunit.id", oriOrgId));
			filterInfo.getFilterItems().add(new FilterItemInfo("oriorg.id", oriOrgId));
			filterInfo.getFilterItems().add(new FilterItemInfo("number", templetNumber));
			view.setFilter(filterInfo);
			NewListTempletCollection tempColl = NewListTempletFactory.getRemoteInstance().getNewListTempletCollection(view);
			if (tempColl.size() == 0) {
				// 没有原模版信息
				MsgBox.showInfo(this, "此模版为分配的模版，没有找到原模版信息！");
				SysUtil.abort();
			} else {
				templet = tempColl.get(0);
				templetId = templet.getId().toString();
			}
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("templetHead.id", templetId));
		sic = view.getSelector();
		sic.add("*");
		sic.add("pageHead.*");
		sic.add("headType.*");
		sic.add("templetColumns.*");
		sic.add("templetColumns.headColumn.*");
		sic.add("templetColumns.headColumn.parent.*");
		sic.add("entrys.*");
		sic.add("pages.entrys.item.*");
		sic.add("pages.entrys.values.*");

		// view.getSelector().add("*");
		view.getSorter().add(new SorterItemInfo("seq"));
		NewListTempletPageCollection sheets = NewListTempletPageFactory
				.getRemoteInstance().getNewListTempletPageCollection(view);
		templet.getPages().clear();
		templet.getPages().addCollection(sheets);
		for (int i = 0; i < templet.getPages().size(); i++) {
			NewListTempletPageInfo sheet = templet.getPages().get(i);
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
		return templet;
	}

	public List setCopyLines(KDTable table) {
		if (table == null) {
			return null;
		}
		List indexList = new ArrayList();
		if ((table.getSelectManager().size() == 0)
				|| isTableColumnSelected(table)) {
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));

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
				MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));
				return null;
			}
			for (int i = top; i <= bottom; i++) {
				indexList.add(new Integer(i));
			}
		}
		this.copyLine = indexList.size();
		return indexList;
	}

	/**
	 * output actionCopyLine_actionPerformed
	 */
	public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			return;
		}
		super.actionCopyLine_actionPerformed(e);
		if (checkSelectTableRow(table)) {
			setCopyLines(table);
			table.getEditHelper().copy();
		}
	}

	public boolean checkSelectTableRow(KDTable table) {
		if (table.getSelectManager().size() == 0) {
			return false;
		}
		int index = table.getSelectManager().get(0).getTop();
		if (index < 1)
			return false;
		return true;
	}

	/**
	 * output actionCutLine_actionPerformed
	 */
	public void actionCutLine_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			return;
		}
		super.actionCutLine_actionPerformed(e);
		if (checkSelectTableRow(table)) {
			List indexList = setCopyLines(table);
			Map levelMap = new HashMap();
			// before cut ,should bak the "level"
			for (int i = 0; i < indexList.size(); i++) {
				levelMap.put((Integer) indexList.get(i), table.getCell(
						((Integer) indexList.get(i)).intValue(), "level")
						.getValue().toString());
			}
			// cut
			table.getEditHelper().cut();
			// after cut, should restore the "level"
			for (int i = 0; i < indexList.size(); i++) {
				table.getCell(((Integer) indexList.get(i)).intValue(), "level")
						.setValue(levelMap.get((Integer) indexList.get(i)));
			}
			setUnionData(table);
		}
	}

	/**
	 * output actionPasteLine_actionPerformed
	 */
	public void actionPasteLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionPasteLine_actionPerformed(e);
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			return;
		}
		if (checkSelectTableRow(table)) {
			table.getEditHelper().paste();
			KDTSelectBlock sb = table.getSelectManager().get();
			int top = sb.getTop(); // 选择块最上边行索引
			if (top == 0) {
				return;
			}
			for (int i = top; i < top + this.copyLine + 1
					&& i < table.getRowCount(); i++) {
				int level = Integer.parseInt(table.getCell(i, "level")
						.getValue().toString());
				table.getRow(i).setTreeLevel(level);
			}
			setUnionData(table);
		}
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			MsgBox.showInfo(this, "不能在此页签上进行分录的操作！");
			return;
		}
		NewListingPageInfo info = (NewListingPageInfo) table.getUserObject();
		NewListingEntryInfo infoEntry = new NewListingEntryInfo();
		IRow row = table.addRow();
		CellTreeNode node = new CellTreeNode();
		row.setTreeLevel(0);
		row.setUserObject(infoEntry);
		String headTypeId = info.getHeadType().getId().toString();
		KDBizPromptBox f7 = this.getF7Box(headTypeId);
		CellTextRenderImpl avr = new CellTextRenderImpl();
		avr.getText(new BizDataFormat("$name$"));
		// ObjectValueRenderImpl avr = new ObjectValueRenderImpl();
		// avr.setFormat(new BizDataFormat("$name$"));
		row.getCell(ItemName).setEditor(new KDTDefaultCellEditor(f7));
		row.getCell(ItemName).setRenderer(avr);
		row.getCell("level").setValue(new Integer(0));
		row.getCell("isKey").setValue(Boolean.FALSE);
		row.getCell("isCompose").setValue(Boolean.FALSE);
		node.setValue(new Integer(0));
		node.setTreeLevel(0);
		infoEntry.setLevel(0);
		infoEntry.setIsLeaf(true);
		setUnionData(table);
	}

	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			MsgBox.showInfo(this, "不能在此页签上进行分录的操作！");
			return;
		}
		NewListingPageInfo info = (NewListingPageInfo) table.getUserObject();
		int index = table.getSelectManager().getActiveRowIndex();
		Integer level = null;
		if (index == -1) {
			table.getSelectManager().set(table.getRowCount() - 1, 0);
			index = table.getRowCount() - 1;
		} else {
			level = (Integer) table.getCell(index, 0).getValue();
		}
		if (level == null) {
			level = new Integer(0);
		}

		NewListingEntryInfo infoEntry = new NewListingEntryInfo();
		IRow row = table.addRow(index + 1);
		CellTreeNode node = new CellTreeNode();
		row.setTreeLevel(0);
		row.setUserObject(infoEntry);
		String headTypeId = info.getHeadType().getId().toString();
		KDBizPromptBox f7 = this.getF7Box(headTypeId);
		CellTextRenderImpl avr = new CellTextRenderImpl();
		avr.getText(new BizDataFormat("$name$"));
		// ObjectValueRenderImpl avr = new ObjectValueRenderImpl();
		// avr.setFormat(new BizDataFormat("$name$"));
		row.getCell(ItemName).setEditor(new KDTDefaultCellEditor(f7));
		row.getCell(ItemName).setRenderer(avr);
		row.getCell("level").setValue(level);
		row.getCell("isKey").setValue(Boolean.FALSE);
		row.getCell("isCompose").setValue(Boolean.FALSE);
		row.setTreeLevel(level.intValue());
		node.setValue(new Integer(0));
		node.setTreeLevel(0);
		infoEntry.setIsCanZeroProAmount(false);
		infoEntry.setLevel(0);
		infoEntry.setIsLeaf(true);
		setUnionData(table);
	}

	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			MsgBox.showInfo(this, "不能在此页签上进行分录的操作！");
			return;
		}
		if ((table.getSelectManager().size() == 0)
				|| isTableColumnSelected(table)) {
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_NoneEntry"));
			return;
		}
		if (table.getRowCount() <= 1) {
			return;
		}
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

	/**
	 * output actionUpLine_actionPerformed
	 */
	public void actionUpLine_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			MsgBox.showInfo(this, "不能在此页签上进行分录的操作！");
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
				NewListingEntryInfo info = (NewListingEntryInfo) table
						.getRow(i).getUserObject();
				info.setIsLeaf(false);
				break;
			}
		}
		setUnionData(table);
	}

	/**
	 * output actionDownLine_actionPerformed
	 */
	public void actionDownLine_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			MsgBox.showInfo(this, "不能在此页签上进行分录的操作！");
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
				NewListingEntryInfo info = (NewListingEntryInfo) table
						.getRow(i).getUserObject();
				info.setIsLeaf(false);
				break;
			}
		}
		setUnionData(table);
	}

	/**
	 * output actionExportPrice_actionPerformed
	 */
	public void actionExportPrice_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportPrice_actionPerformed(e);
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return NewListingFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		NewListingInfo objectValue = new NewListingInfo();
		Map context = this.getUIContext();
		
		if (context.get("type") instanceof InviteTypeInfo) {
			InviteTypeInfo inviteTypeInfo = (InviteTypeInfo) context.get("type");
			if (inviteTypeInfo.isIsLeaf()) {
				objectValue.setInviteType(inviteTypeInfo);
			}
		}
		objectValue.setOrgUnit(currentOrg.castToFullOrgUnitInfo());
		objectValue.setState(FDCBillStateEnum.SAVED);
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		if (currentCompany.getBaseCurrency() != null) {
			try {
				CurrencyInfo currencyInfo = CurrencyFactory.getRemoteInstance().getCurrencyInfo(new ObjectUuidPK(currentCompany.getBaseCurrency().getId()));
				objectValue.setCurrency(currencyInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		if(this.getUIContext().get("isReversion") != null){
			isReversion = Boolean.valueOf(getUIContext().get("isReversion").toString()).booleanValue();
		}
		
		if(isReversion){			
			NewListingInfo info = null;
			if(getUIContext().get("initData") != null){
				info = (NewListingInfo) getUIContext().get("initData");
				objectValue = info;
			}
		}
		
		return objectValue;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		//20101103 zhougang
		//当选择的表头为供应商自定义时，甲方将不能对此表头进行任何操作。
		NewListingPageInfo info = editData.getPages().get(tabbedPnlTable.getSelectedIndex() - 2);
		contorlPageFunction(info);
		
		/**
		 * 页签改变时，功能控制。
		 */
		tabbedPnlTable.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				NewListingPageInfo info = editData.getPages().get(tabbedPnlTable.getSelectedIndex() - 2);
				//20101012 zhougang
				//当选择的表头为供应商自定义时，甲方将不能对此表头进行任何操作。
				contorlPageFunction(info);
			}
		});
		
        //修订
		if(editData.getVersion() > 1.0){
			isReversion = true;
		}
		
		if(isReversion){
			this.txtName.setEnabled(false);
			this.txtNumber.setEnabled(false);
			this.prmtCurProject.setEnabled(false);
			this.prmtInviteProject.setEnabled(false);
			this.prmtInviteType.setEnabled(false);
			this.txtNumber.setText(editData.getNumber());
		}
		
		prmtInviteMode.setEnabled(false);
		this.inivteOrg.setEnabled(false);
		this.actionImportMaterial.setEnabled(false);
		this.actionAttachment.setVisible(true);
		this.actionAttachment.setEnabled(true);
		this.menuSubmitOption.setVisible(false);
		this.actionExportPrice.setVisible(false);
		this.actionCopy.setVisible(false);
		this.menuView.setVisible(false);
		if (editData.getState() != null) {
			if (editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
				this.actionEdit.setEnabled(false);
				this.actionAudit.setEnabled(false);
				this.actionUnAudit.setEnabled(true);
			} else {
				this.actionEdit.setEnabled(true);
				this.actionAudit.setEnabled(true);
				this.actionUnAudit.setEnabled(false);
			}
		}
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");
		selectors.add(new SelectorItemInfo("inviteType.id"));
		selectors.add(new SelectorItemInfo("inviteType.longnumber"));
		selectors.add(new SelectorItemInfo("inviteType.number"));
		selectors.add(new SelectorItemInfo("inviteType.name"));
		selectors.add(new SelectorItemInfo("project.id"));
		selectors.add(new SelectorItemInfo("project.number"));
		selectors.add(new SelectorItemInfo("project.longnumber"));
		selectors.add(new SelectorItemInfo("project.name"));
		selectors.add(new SelectorItemInfo("inviteProjectState"));
		selectors.add(new SelectorItemInfo("orgUnit.*"));
		selectors.add(new SelectorItemInfo("inviteMode.*"));

		prmtInviteProject.setSelectorCollection(selectors);

		//
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				InviteHelper.setAotuHeight(tabbedPnlTable);
			}
		});

		// 如果先选择招标立项，则自动带出该立项对应的招标类型和所属项目
		prmtInviteProject.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				setInviteProjectRelationValues(eventObj);
			}

		});
		prmtInviteProject.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				try {
					setPrmtInviteProjectFilter(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		prmtInviteProject.setRequired(true);
		prmtInviteProject.setDisplayFormat("$name$");
		prmtInviteProject.setEditFormat("$name$");
		prmtInviteProject.setCommitFormat("$name$");

		this.actionWorkFlowG.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);

		Component[] components = this.menuWorkflow.getPopupMenu()
				.getComponents();

		for (int i = components.length - 1; i >= 0; --i) {
			this.menuWorkflow.remove(i);
		}

		if (getOprtState().equals(OprtState.VIEW)) {
			prmtInviteMode.setEnabled(false);
			this.inivteOrg.setEnabled(false);
			this.actionAddNew.setEnabled(true);
			if (!checkCanOperate()) {
				this.actionAddNew.setEnabled(false);
				this.actionEdit.setEnabled(false);
				this.actionRemove.setEnabled(false);
				this.actionAttachment.setEnabled(false);
			} 
		} 

		if (editData != null && editData.getInviteProject() != null)
			actionImportMaterial.setEnabled(editData.getInviteProject().isIsMaterial());
		else
			actionImportMaterial.setEnabled(false);
		
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);

		prmtCurProject.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				try {
					setCurProjectFilter(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		prmtCurProject.setDisplayFormat("$name$");
		prmtCurProject.setEditFormat("$name$");
		prmtCurProject.setCommitFormat("$name$");

		prmtCurProject.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				if (eventObj.getNewValue() == null && eventObj.getOldValue() != null) {
					prmtInviteProject.setValue(null);
				} else if (eventObj.getNewValue() != null && eventObj.getOldValue() == null) {

				} else if (eventObj.getNewValue() != null && eventObj.getOldValue() != null) {
					CurProjectInfo newValue = (CurProjectInfo) eventObj.getNewValue();
					CurProjectInfo oldValue = (CurProjectInfo) eventObj.getOldValue();

					if (!newValue.getId().equals(oldValue.getId())) {
						prmtInviteProject.setValue(null);
					}
				} else if (eventObj.getNewValue() == null
						&& eventObj.getOldValue() != null) {
					prmtInviteProject.setValue(null);
				}
			}
		});

		prmtInviteType.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {

				if (eventObj.getNewValue() == null&& eventObj.getOldValue() != null) {
					prmtInviteProject.setValue(null);
				} else if (eventObj.getNewValue() != null && eventObj.getOldValue() == null) {

				} else if (eventObj.getNewValue() != null && eventObj.getOldValue() != null) {
					InviteTypeInfo newValue = (InviteTypeInfo) eventObj.getNewValue();
					InviteTypeInfo oldValue = (InviteTypeInfo) eventObj.getOldValue();

					if (!newValue.getId().equals(oldValue.getId())) {
						prmtInviteProject.setValue(null);
					}
				} else if (eventObj.getNewValue() == null
						&& eventObj.getOldValue() != null) {
					prmtInviteProject.setValue(null);
				}
			}
		});

		actionPrintPreview.setVisible(true);
		actionPrint.setVisible(true);
		actionPrintPreview.setEnabled(true);
		actionPrint.setEnabled(true);
	}
	
	/**
	 * 当选择的表头为供应商自定义时，甲方将不能对此表头进行任何操作。
	 * @param info 页签信息。
	 */
	private void contorlPageFunction(NewListingPageInfo info){
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		//20101012 zhougang
		//当选择的表头为供应商自定义时，甲方将不能对此表头进行任何操作。
		if(info != null && info.getHeadType() != null){
			HeadTypeInfo hdInfo;
			try {
				hdInfo = HeadTypeFactory.getRemoteInstance().getHeadTypeInfo("where id = '" + info.getHeadType().getString("id") + "'");
				if(hdInfo.isIsDefined()){
//					if(info.getHeadType().isIsDefined()){
					actionAddLine.setEnabled(false);
					actionInsertLine.setEnabled(false);
					actionRemoveLine.setEnabled(false);
					actionUpLine.setEnabled(false);
					actionDownLine.setEnabled(false);
					actionAllSelect.setEnabled(false);
					actionNoneSelect.setEnabled(false);
					actionImportExcel.setEnabled(false);
					actionImportMaterial.setEnabled(false);
					table.getColumn(1).getStyleAttributes().setHided(true);
					table.getColumn(2).getStyleAttributes().setHided(true);
					for(int i = 1; i < table.getRowCount(); i++){
						table.getRow(i).getStyleAttributes().setHided(true);
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
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	private boolean checkCanOperate() {
		boolean flag = true;
		String orgId = editData.getOrgUnit().getId().toString();
		if (currentOrg.getId().toString().equals(orgId)) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}

	private SelectorItemCollection getInviteProjectNewSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));

		sic.add(new SelectorItemInfo("project.id"));
		sic.add(new SelectorItemInfo("project.number"));
		sic.add(new SelectorItemInfo("project.name"));

		sic.add(new SelectorItemInfo("inviteType.id"));
		sic.add(new SelectorItemInfo("inviteType.number"));
		sic.add(new SelectorItemInfo("inviteType.name"));
		sic.add(new SelectorItemInfo("inviteType.isEnabled"));

		return sic;
	}

	private void setInviteProjectRelationValues(DataChangeEvent e) {
		if (e.getNewValue() == null) {
			prmtInviteMode.setDataNoNotify(null);
			inivteOrg.setText(null,false);
			return;
		}

		InviteProjectInfo oldValue = (InviteProjectInfo) e.getOldValue();
		InviteProjectInfo newValue = (InviteProjectInfo) e.getNewValue();

		if (oldValue == null) {
//			prmtCurProject.setValue(newValue.getProject());
			prmtInviteType.setValue(newValue.getInviteType());
//			prmtInviteMode.setValue(newValue.getInviteMode());
			this.inivteOrg.setText(newValue.getOrgUnit().getName());

		} else {
			if (!newValue.getId().equals(oldValue.getId())) {
//				prmtCurProject.setValue(newValue.getProject());
				prmtInviteType.setValue(newValue.getInviteType());
//				prmtInviteMode.setValue(newValue.getInviteMode());
				this.inivteOrg.setText(newValue.getOrgUnit().getName());
			}
		}
	}

	private void setPrmtInviteProjectFilter(SelectorEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("number"));
		view.getSelector().add(new SelectorItemInfo("name"));
		view.getSelector().add(new SelectorItemInfo("state"));
		view.getSelector().add(new SelectorItemInfo("isRate"));
		view.getSelector().add(new SelectorItemInfo("isMaterial"));

		view.getSelector().add(new SelectorItemInfo("project.id"));
		view.getSelector().add(new SelectorItemInfo("project.number"));
		view.getSelector().add(new SelectorItemInfo("project.name"));

		view.getSelector().add(new SelectorItemInfo("inviteForm"));
		view.getSelector().add(new SelectorItemInfo("respPerson.number"));
		view.getSelector().add(new SelectorItemInfo("prjDate"));

		view.getSelector().add(new SelectorItemInfo("inviteType.longNumber"));
		view.getSelector().add(new SelectorItemInfo("inviteType.id"));
		view.getSelector().add(new SelectorItemInfo("inviteType.number"));
		view.getSelector().add(new SelectorItemInfo("inviteType.name"));

		view.getSelector().add(new SelectorItemInfo("state"));
		view.getSelector().add(new SelectorItemInfo("inviteProjectState"));
		view.getSelector().add(new SelectorItemInfo("orgUnit.id"));

		//prmtInviteProject.setSelectorCollection(getInviteProjectNewSelector())
		// ;

		FilterInfo filter = new FilterInfo();

		// 状态过滤:已通过审批，非已审批
		// update by msb 2010/04/29
		Set states = getPassAuditStateForInvProject();
		if(!states.isEmpty()){
			filter.getFilterItems().add(
					new FilterItemInfo("inviteProjectState",states,CompareType.INCLUDE));
		}
		// filter.getFilterItems().add(
		// new FilterItemInfo("inviteProjectState",
		//		InviteProjectStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));

		// 选择当前组织下的招标立项
		if (currentOrg != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", currentOrg.getId()
							.toString()));
		}

		// 招标立项自身的过滤条件

		// 招标类型过滤条件
		if (prmtInviteType.getValue() != null
				&& (prmtInviteType.getValue() instanceof com.kingdee.eas.fdc.invite.InviteTypeInfo)) {
			FilterInfo typeFilter = new FilterInfo();
			InviteTypeInfo typeInfo = (InviteTypeInfo) prmtInviteType
					.getValue();
			BOSUuid id = typeInfo.getId();

			Set idSet = getInviteTypeIdSet(id);
			filter.getFilterItems().add(
					new FilterItemInfo("inviteType.id", idSet,
							CompareType.INCLUDE));

		}

		// 所属项目过滤条件实现
		if (prmtCurProject.getValue() != null && (prmtCurProject.getValue() instanceof CurProjectInfo)) {
			CurProjectInfo projectInfo = (CurProjectInfo) prmtCurProject.getValue();

			String projectId = projectInfo.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("project.id", projectId));
		}

		// 已定标过滤    add by msb 2010/04/26
        Set appraisedPrjSet =getAppraisedInvireProjectId();
        if((appraisedPrjSet != null) && (!appraisedPrjSet.isEmpty())){
                filter.getFilterItems().add(new FilterItemInfo("id",appraisedPrjSet,CompareType.NOTINCLUDE));
        }
        
		view.setFilter(filter);
		prmtInviteProject.setEntityViewInfo(view);

		if (prmtInviteProject.getSelector() != null
				&& prmtInviteProject.getSelector() instanceof com.kingdee.eas.framework.client.ListUI) {
			((com.kingdee.eas.framework.client.ListUI) prmtInviteProject
					.getSelector()).setFilterForQuery(filter);
			((com.kingdee.eas.framework.client.ListUI) prmtInviteProject
					.getSelector()).onLoad();

		} else {
			if (filter.getFilterItems().size() <= 0) {
				return;
			}
			prmtInviteProject.getEntityViewInfo().setFilter(filter);
			prmtInviteProject.getQueryAgent().resetRuntimeEntityView();
			prmtInviteProject.setRefresh(true);
		}
	}

	/**
     * 获取已定标的招标立项 （已审核的投标结果）
     * @author msb 2010/4/26
     * @return
     * @throws Exception
     */
    private Set getAppraisedInvireProjectId()throws Exception{
            Set idSet = AppraiseResultFactory.getRemoteInstance().getInviteProjectId();
            return idSet;
    }
    
    /**
	 * 获取通过审批且定标之前的招标立项的状态
	 * @author msb 2010/04/29
	 * 1SAVED|2SUBMITED|3AUDITTING|4AUDITTED|5FILEMAKING|
	 * 6ANSWERING|7APPRAISING|8FIXED|9SIGNEDCONTRACT
	 * @return
	 */
	private Set getPassAuditStateForInvProject(){
		Set prjStateSet = new HashSet();
		List lstStatus = EnumUtils.getEnumList(InviteProjectStateEnum.class);
		for(Iterator iterator = lstStatus.iterator();iterator.hasNext();){
			InviteProjectStateEnum state = (InviteProjectStateEnum)iterator.next();
			String stateStr = state.getValue();
			if(stateStr.length()>=1){
				int seq = Integer.parseInt(stateStr.substring(0, 1));
				if(seq >=4 && seq <=7){
					prjStateSet.add(stateStr);
				}
			}
		}
		return prjStateSet;
	}
    
	private Set getInviteTypeIdSet(BOSUuid id) throws EASBizException,
			BOSException {
		Set idSet = new HashSet();
		IObjectPK pk = new ObjectUuidPK(id);
		InviteTypeInfo parentTypeInfo = InviteTypeFactory.getRemoteInstance()
				.getInviteTypeInfo(pk);

		String longNumber = parentTypeInfo.getLongNumber();
		EntityViewInfo view = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber));

		filter.setMaskString("#0 or #1");
		view.setFilter(filter);

		com.kingdee.eas.fdc.invite.InviteTypeCollection typeCols = InviteTypeFactory
				.getRemoteInstance().getInviteTypeCollection(view);

		Iterator iter = typeCols.iterator();
		while (iter.hasNext()) {
			InviteTypeInfo tmp = (InviteTypeInfo) iter.next();
			idSet.add(tmp.getId().toString());
		}

		return idSet;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		setColumnObject();
		super.actionEdit_actionPerformed(e);

		this.actionAttachment.setEnabled(true);
		this.btnAttachment.setEnabled(true);
		this.MenuItemAttachment.setEnabled(true);

		if (!InviteHelper.validateNewListingHasRefPrice(this, this.editData
				.getId().toString())) {
			SysUtil.abort();
		}
		this.actionColumnSetting.setEnabled(true);
		this.actionImportTemplet.setEnabled(true);
		this.actionImportExcel.setEnabled(true);
		this.actionUpLine.setEnabled(true);
		this.actionDownLine.setEnabled(true);
		this.actionAddLine.setEnabled(true);
		this.actionInsertLine.setEnabled(true);
		this.actionRemoveLine.setEnabled(true);
		this.actionTableSet.setEnabled(true);
		this.actionAllSelect.setEnabled(true);
		this.actionNoneSelect.setEnabled(true);
		this.actionSetZeroProAmount.setEnabled(true);

		this.actionCutLine.setEnabled(true);
		this.actionCopyLine.setEnabled(true);
		this.actionPasteLine.setEnabled(true);
		
		//20101103 zhougang
		//当选择的表头为供应商自定义时，甲方将不能对此表头进行任何操作。
		NewListingPageInfo info = editData.getPages().get(tabbedPnlTable.getSelectedIndex() - 2);
		contorlPageFunction(info);

		// if(editData.getInviteProject() != null)
		// {
		// this.actionImportMaterial.setEnabled(editData.getInviteProject().
		// isInviteTypeIsMaterail());
		// }

		if (editData.getInviteProject() != null) {
			this.actionImportMaterial.setEnabled(editData.getInviteProject()
					.isIsMaterial());
		} else {
			this.actionImportMaterial.setEnabled(false);
		}

		int tabCount = tabbedPnlTable.getTabCount();
		KDTable tblDesc = (KDTable) tabbedPnlTable.getComponentAt(0);
		tblDesc.getColumn(1).getStyleAttributes().setLocked(false);
		KDTable tblSum = (KDTable) tabbedPnlTable.getComponentAt(1);
		// -2 + 1: 2前面两个页签是汇总，后面的是添加的，1小计行
		tblSum.getCell(tabCount - 1, "amount").getStyleAttributes().setLocked(
				false);

		if (tabCount <= 2)
			return;
		for (int i = 2, n = tabbedPnlTable.getTabCount() - 1; i <= n; i++) {
			KDTable tbl = (KDTable) tabbedPnlTable.getComponentAt(i);
			tbl.getStyleAttributes().setLocked(false);
			setTableLock(tbl);
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);

		this.actionAttachment.setEnabled(true);
	}

	public void loadFields() {

		if (this.getOprtState().equals("EDIT")) {
			statusEditUserConfigFlag = true;
			initUserConfig();
			statusEditUserConfigFlag = false;
		}
		super.loadFields();
		if(this.editData.getInviteProject() != null){
		BOSUuid inviteProjectId= this.editData.getInviteProject().getId();
		try {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("inviteMode.*"));
			sic.add(new SelectorItemInfo("OrgUnit.*"));
			InviteProjectInfo project = InviteProjectFactory.getRemoteInstance().getInviteProjectInfo((new ObjectUuidPK(inviteProjectId)),sic);
//			this.prmtInviteMode.setData(project.getInviteMode());
			this.inivteOrg.setText(project.getOrgUnit().getName());
		} catch (EASBizException e) {
			handleException(e);
			e.printStackTrace();
		} catch (BOSException e) {
			handleException(e);
			e.printStackTrace();
		}}

		if (this.getOprtState().equals("VIEW")) {
			this.actionColumnSetting.setEnabled(false);
			this.actionImportTemplet.setEnabled(false);
			this.actionImportExcel.setEnabled(false);
			this.actionUpLine.setEnabled(false);
			this.actionDownLine.setEnabled(false);
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);

			this.actionCutLine.setEnabled(false);
			this.actionCopyLine.setEnabled(false);
			this.actionPasteLine.setEnabled(false);

			this.actionTableSet.setEnabled(false);
			this.actionAllSelect.setEnabled(false);
			this.actionNoneSelect.setEnabled(false);
			this.actionSetZeroProAmount.setEnabled(false);
			if (!editData.getOrgUnit().getId().toString().equals(
					this.editData.getId().toString())) {
				this.actionEdit.setEnabled(false);
				this.actionAddNew.setEnabled(false);
			}
			initUserConfig();
		} else {
			this.actionColumnSetting.setEnabled(true);
			this.actionImportTemplet.setEnabled(true);
			this.actionImportExcel.setEnabled(true);
			this.actionUpLine.setEnabled(true);
			this.actionDownLine.setEnabled(true);
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);

			this.actionCutLine.setEnabled(true);
			this.actionCopyLine.setEnabled(true);
			this.actionPasteLine.setEnabled(true);

			this.actionTableSet.setEnabled(true);
			this.actionAllSelect.setEnabled(true);
			this.actionNoneSelect.setEnabled(true);
			this.actionSetZeroProAmount.setEnabled(true);

		}
		if (editData.getState() != null
				&& editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			this.actionSave.setEnabled(false);
		}
		if (editData.getState() != null) {
			if (editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
				this.actionEdit.setEnabled(false);
				this.actionAudit.setEnabled(false);
				this.actionUnAudit.setEnabled(true);
			} else {
				this.actionAudit.setEnabled(true);
				this.actionUnAudit.setEnabled(false);
			}
		}
	
		loadPages();
		// 设置UserObject
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setColumnObject();
			}
		});
		/***
		 * 为了初始化oldData
		 */
		this.storeFields();
	}

	public void setColumnObject() {
		Set set = columnObjMap.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			IColumn column = (IColumn) iter.next();
			column.setUserObject(columnObjMap.get(column));
		}
	}

	private void loadPages() {

		int selectedIndex = this.tabbedPnlTable.getSelectedIndex();
		tabbedPnlTable.removeAll();
		NewListingPageCollection coll = editData.getPages();
		int size = coll.size();
		boolean fView = getOprtState().equals("VIEW");
		for (int i = 0; i < size; i++) {
			NewListingPageInfo page = (NewListingPageInfo) coll.get(i);
			PageHeadInfo pageHead = page.getPageHead();
			KDTable table = new KDTable();
			table.setName(pageHead.getName());
			initTable(table, page);
			loadTableData(table, page);
			setUnionData(table);
			tabbedPnlTable.add(table, pageHead.getName());
		}
		initTotalPageTable();
		initPageDesTable();
		if (selectedIndex != -1
				&& this.tabbedPnlTable.getTabCount() > selectedIndex) {
			this.tabbedPnlTable.setSelectedIndex(selectedIndex);
		}
		if (fView) {
			for (int i = 0; i < tabbedPnlTable.getTabCount(); i++) {
				KDTable tbl = (KDTable) tabbedPnlTable.getComponentAt(i);
				tbl.getStyleAttributes().setLocked(true);
			}
		}
		initUserConfig();
		InviteHelper.setAotuHeight(this.tabbedPnlTable);
		tHelper.setCanMoveColumn(false);
	}

	/**
	 * 描述：招标说明
	 * @Author：owen_wen
	 * @CreateTime：2011-10-24
	 */
	private void initPageDesTable() {
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
		// if (this.getOprtState().equals("VIEW")) {
		// table.getColumn(1).getStyleAttributes().setLocked(true);
		// }
		KDTextField txtField = new KDTextField();
		txtField.setMaxLength(900);
		ICellEditor editor = new KDTDefaultCellEditor(txtField);
		table.getColumn("des").setEditor(editor);
		row.getCell(0).setValue("页签名称");
		row.getCell(1).setValue("描述");

		this.tabbedPnlTable.add(table, 0);
		setTableMenu(table, false);
		initPageDesTableRows();
	}

	private void initPageDesTableRows() {
		KDTable table = (KDTable) this.tabbedPnlTable.getComponentAt(0);
		if (!table.getName().equals("招标说明")) {
			return;
		}
		int pageCount = tabbedPnlTable.getTabCount();
		table.removeRows();
		table.addRows(pageCount - 2);

		for (int i = 2; i < pageCount; i++) {
			KDTable aTable = (KDTable) tabbedPnlTable.getComponentAt(i);
			NewListingPageInfo page = (NewListingPageInfo) aTable
					.getUserObject();
			if (page != null) {
				table.getCell(i - 2, "name").setValue(
						page.getPageHead().getName());
				ICell cell = null;
				cell = table.getCell(i - 2, "des");
				final KDBizMultiLangArea textField = new KDBizMultiLangArea();

				textField.setMaxLength(1000);
				textField.setAutoscrolls(true);
				textField.setEditable(true);
				textField.setToolTipText("Alt+Enter换行");
				KDTDefaultCellEditor editor = new KDTDefaultCellEditor(
						textField);
				cell.setEditor(editor);
				cell.getStyleAttributes().setVerticalAlign(
						VerticalAlignment.TOP);
				cell.getStyleAttributes().setWrapText(true);
				table.getCell(i - 2, "des").setValue(page.getDescription());
			}
		}
	}

	/***
	 * 覆盖此函数，支持用户保存多个样式，每个模版保留不同的样式
	 */
	public Object getTablePreferenceSchemaKey() {
		// 记录加载的次数
		// 这里首先自动加载两次,此两次加载,此editUI的默认设置
		// 然后,onload之后,再次显示调用iniUserConfig
		// 这样,恢复默认设置就可以使用了,相当于,设置第一次加载的是默认设置
		if (userConfigLoadTimes < 2) {
			userConfigLoadTimes++;
			if (this.getOprtState().equals("VIEW"))
				return "";
		}
		if (statusEditUserConfigFlag)
			return "";
		if (this.txtNumber == null || this.txtNumber.getText().equals("")) {
			{
				KDTable table = (KDTable) this.tabbedPnlTable
						.getSelectedComponent();
				isSaveUserConfigMap.put(table.getName(), Boolean.TRUE);
				SysUtil.abort();
				return null;
			}
		} else {
			return this.txtNumber.getText();
		}
	}

	protected void showSubAttacheMent(AttachmentUIContextInfo info) {
		return;
	}

	private void initTotalPageTable() {
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
		table.getCell(pageCount + 1, "amount").setValue(this.editData.getTax());
		table.getCell(pageCount + 2, "name").setValue("税金");
		table.getCell(pageCount + 2, "amount").getStyleAttributes().setLocked(
				true);
		table.getCell(pageCount + 3, "name").setValue("总造价");
		table.getCell(pageCount + 3, "amount").getStyleAttributes().setLocked(
				true);
		// KDFormattedTextField formattedTextField = new KDFormattedTextField(
		// KDFormattedTextField.BIGDECIMAL_TYPE);
		// formattedTextField.setPrecision(2);
		// formattedTextField.setRemoveingZeroInDispaly(false);
		// formattedTextField.setRemoveingZeroInEdit(false);
		// formattedTextField.setNegatived(false);
		// formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE);
		// formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
		// ICellEditor numberEditor = new
		// KDTDefaultCellEditor(formattedTextField);
		// table.getColumn(2).setEditor(numberEditor);
		table.getColumn(2).setEditor(amtEditor);
		FDCHelper.formatTableNumber(table, "amount");
		this.tabbedPnlTable.add(table, 0);
		tHelper.setCanMoveColumn(false);
		setTableMenu(table, false);
		this.updateTotalPageData();
	}

	private void initTable(final KDTable table, NewListingPageInfo page) {
		table.getStyleAttributes().setWrapText(true);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		table.setUserObject(page);
		tHelper.setCanSetTable(true);
		tHelper.setCanMoveColumn(false);
		setTableEvent(table);
		setTableMenu(table, true);
		setTableColumn(table, page);
		setTableRow(table, page);
	}

	private void setTableLock(final KDTable table) {
		if (table.getColumnCount() == 0) {
			return;
		}
		int projectAmtCount = 0;
		table.getColumn("isKey").getStyleAttributes().setLocked(true);
		table.getColumn("isCompose").getStyleAttributes().setLocked(true);
		for (int i = 3, colCount = table.getColumnCount(); i < colCount; i++) {
			IColumn col = table.getColumn(i);
			NewListingColumnInfo colInfo = null;

			if (col.getUserObject() instanceof NewListingColumnInfo) {
				colInfo = (NewListingColumnInfo) col.getUserObject();
			} else {
				colInfo = (NewListingColumnInfo) listingColumnObjectMap
						.get(table.getName() + col.getKey());
			}

			DescriptionEnum property = colInfo.getHeadColumn().getProperty();
			if (property.equals(DescriptionEnum.ProjectAmt)) {
				projectAmtCount++;
			}
		}
		for (int i = 3, colCount = table.getColumnCount(); i < colCount; i++) {
			IColumn col = table.getColumn(i);
			if (col.getUserObject() != null) {
				NewListingColumnInfo colInfo = null;

				if (col.getUserObject() instanceof NewListingColumnInfo) {
					colInfo = (NewListingColumnInfo) col.getUserObject();
				} else {
					colInfo = (NewListingColumnInfo) listingColumnObjectMap
							.get(table.getName() + col.getKey());
				}

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

		// 锁定合计行
		table.getRow(0).getStyleAttributes().setLocked(true);
		// 锁定非叶子节点
		for (int i = 1; i < table.getRowCount(); i++) {
			boolean isLeafRow = isLeafRow(table, i);
			boolean isCompose = false;
			IRow row = table.getRow(i);
			if (row.getCell("isCompose").getValue() != null) {
				isCompose = ((Boolean) row.getCell("isCompose").getValue())
						.booleanValue();
			}
			if (isLeafRow) {
				row.getStyleAttributes().setBackground(Color.WHITE);
				if (row.getCell(2).getValue() == null) {
					row.getCell(2).setValue(Boolean.FALSE);
				}
				row.getCell("单位").getStyleAttributes().setLocked(false);
			} else {
				row.getCell(2).setValue(null);
				row.getStyleAttributes().setBackground(LOCKCOLOR);
				row.getCell("单位").setValue(null);
				row.getStyleAttributes().setLocked(true);
			}
			NewListingEntryInfo entry = (NewListingEntryInfo) row
					.getUserObject();
			for (int j = 3, colCount = table.getColumnCount(); j < colCount; j++) {
				IColumn col = table.getColumn(j);
				if (col.getUserObject() != null) {
					NewListingColumnInfo colInfo = null;

					if (col.getUserObject() instanceof NewListingColumnInfo) {
						colInfo = (NewListingColumnInfo) col.getUserObject();
					} else {
						colInfo = (NewListingColumnInfo) listingColumnObjectMap
								.get(table.getName() + col.getKey());
					}

					DescriptionEnum property = colInfo.getHeadColumn()
							.getProperty();
					ICell cell = table.getCell(i, j);
					if (property.equals(DescriptionEnum.ProjectAmt)) {
						if (isLeafRow) {
							cell.getStyleAttributes().setLocked(false);
						} else {
							cell.getStyleAttributes().setLocked(true);
							cell.setValue(null);
						}
						if (entry.isIsCanZeroProAmount()) {
							cell.getStyleAttributes().setBackground(
									CAN_ZERO_COLOR);
							cell.setValue(null);
							cell.getStyleAttributes().setLocked(true);
						} else {
							if (isLeafRow) {
								cell.getStyleAttributes().setBackground(
										Color.WHITE);
							} else {
								cell.getStyleAttributes().setBackground(
										LOCKCOLOR);
							}
						}
					}
					if (property.equals(DescriptionEnum.ProjectAmtSum)) {
						if (projectAmtCount == 0 && isLeafRow) {
							cell.getStyleAttributes().setLocked(false);
						} else {
							cell.getStyleAttributes().setLocked(true);
							if (!isLeafRow) {
								cell.setValue(null);
							}
						}
						if (entry.isIsCanZeroProAmount()) {
							cell.getStyleAttributes().setBackground(
									CAN_ZERO_COLOR);
							cell.setValue(null);
							cell.getStyleAttributes().setLocked(true);
						} else {
							if (isLeafRow) {
								cell.getStyleAttributes().setBackground(
										Color.WHITE);
							} else {
								cell.getStyleAttributes().setBackground(
										LOCKCOLOR);
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
		// if (getOprtState().equals("VIEW")) {
		// table.getStyleAttributes().setLocked(true);
		// }
	}

	private void setTableTotal(KDTable table) {
		int amountSumCol = -1;
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			NewListingColumnInfo col = null;

			if (column.getUserObject() instanceof NewListingColumnInfo) {
				col = (NewListingColumnInfo) column.getUserObject();
			} else {
				col = (NewListingColumnInfo) listingColumnObjectMap.get(table
						.getName()
						+ column.getKey());
			}

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
			if (isLeafRow(table, j))
				aimRowList.add(rowAfter);
		}
		if (aimRowList.size() > 0) {
			BigDecimal sum = null;
			for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
				IRow rowAdd = (IRow) aimRowList.get(rowIndex);
				Object value = rowAdd.getCell(amountSumCol).getValue();
				if (value != null) {
					if (sum == null) {
						sum = FMConstants.ZERO;
					}
					sum = sum.add(FDCNumberHelper.toBigDecimal(value));
				}
			}
			row.getCell(amountSumCol).setValue(sum);
		}
	}

	KDTEditAdapter editAdapter = null;

	KDTMouseListener mouseAdapter = null;

	private void setTableEvent(final KDTable table) {
		table.removeKDTEditListener(editAdapter);
		editAdapter = new KDTEditAdapter() {
			// 编辑结束后
			public void editStopped(KDTEditEvent e) {
				IRow row = table.getRow(e.getRowIndex());
				IColumn col = table.getColumn(e.getColIndex());
				if (col.getUserObject() != null) {
					NewListingColumnInfo colInfo = null;

					if (col.getUserObject() instanceof NewListingColumnInfo) {
						colInfo = (NewListingColumnInfo) col.getUserObject();
					} else {
						colInfo = (NewListingColumnInfo) listingColumnObjectMap
								.get(table.getName() + col.getKey());
					}

					DescriptionEnum property = colInfo.getHeadColumn()
							.getProperty();
					if (property.equals(DescriptionEnum.ProjectAmt)
							|| property.equals(DescriptionEnum.ProjectAmtSum)
							|| property.equals(DescriptionEnum.TotalPrice)
							|| property.equals(DescriptionEnum.TotalPriceSum)) {
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
				KDTableHelper.autoFitRowHeight(table, e.getRowIndex(), 5);
			}
		};
		table.addKDTEditListener(editAdapter);
		table.removeKDTMouseListener(mouseAdapter);
		mouseAdapter = new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				// 单击
				if (e.getClickCount() == 1) {
					if (getOprtState().equals("VIEW")) {
						return;
					}
					if (e.getRowIndex() <= 0) {
						return;
					}
					if (isLeafRow(table, e.getRowIndex())
							&& e.getColIndex() == table
									.getColumnIndex("isCompose")) {
						IRow row = table.getRow(e.getRowIndex());
						boolean isCompose = false;
						if (row.getCell("isCompose").getValue() != null) {
							isCompose = ((Boolean) row.getCell("isCompose")
									.getValue()).booleanValue();
						}
						row.getCell("isCompose").setValue(
								Boolean.valueOf(!isCompose));
						setUnionData(table);
					}
					if (e.getColIndex() == table.getColumnIndex("isKey")) {
						IRow row = table.getRow(e.getRowIndex());
						boolean isCompose = ((Boolean) row.getCell("isKey")
								.getValue()).booleanValue();
						row.getCell("isKey").setValue(
								Boolean.valueOf(!isCompose));
					}
				} else if (e.getClickCount() == 2) {
					IColumn sumCol = null;
					for (int i = 3; i < table.getColumnCount(); i++) {
						IColumn column = table.getColumn(i);
						if (column.getUserObject() != null) {
							NewListingColumnInfo col = null;

							if (column.getUserObject() instanceof NewListingColumnInfo) {
								col = (NewListingColumnInfo) column
										.getUserObject();
							} else {
								col = (NewListingColumnInfo) listingColumnObjectMap
										.get(table.getName() + column.getKey());
							}

							if (col.getHeadColumn().getProperty().equals(
									DescriptionEnum.ProjectAmtSum)) {
								sumCol = column;
							}
						}
					}
					if (e.getRowIndex() != 0
							|| e.getColIndex() != sumCol.getColumnIndex()) {
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
							NewListingColumnInfo col = null;

							if (column.getUserObject() instanceof NewListingColumnInfo) {
								col = (NewListingColumnInfo) column
										.getUserObject();
							} else {
								col = (NewListingColumnInfo) listingColumnObjectMap
										.get(table.getName() + column.getKey());
							}

							if (col.getHeadColumn().getProperty().equals(
									DescriptionEnum.ProjectAmt)) {
								column.getStyleAttributes().setHided(isHide);
							}
						}
					}

				}
			}
		};
		table.addKDTMouseListener(mouseAdapter);

	}

	private void setTableMenu(final KDTable table, boolean hasSelfEditMenu) {
		try {
			tableMenu = new NewListingTableMenuUI();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		enableTableCommonMenus(table);
		// tHelper.addMenuToTable(table);
		// enableExportExcel(table);
		if (hasSelfEditMenu) {
			KDTMenuManager tm = this.getMenuManager(table);
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
			tm.getMenu().insert(itemCopy, 0);

			KDMenuItem itemPaste = new KDMenuItem();
			itemPaste.setName("粘贴");
			itemPaste.setAction((IItemAction) ActionProxyFactory.getProxy(
					actionPasteLine, new Class[] { IItemAction.class },
					getServiceContext()));
			// itemPaste.setAction(new ActionPasteLine());
			tm.getMenu().insert(itemPaste, 1);

			KDMenuItem itemCut = new KDMenuItem();
			itemCut.setName("剪切");
			itemCut.setAction((IItemAction) ActionProxyFactory.getProxy(
					actionCutLine, new Class[] { IItemAction.class },
					getServiceContext()));
			// itemCut.setAction(new ActionCutLine());
			tm.getMenu().insert(itemCut, 2);

			KDMenuItem itemUp = new KDMenuItem();
			itemUp.setName("升级");
			itemUp.setAction((IItemAction) ActionProxyFactory.getProxy(
					actionUpLine, new Class[] { IItemAction.class },
					getServiceContext()));
			// itemUp.setAction(new ActionUpLine());
			tm.getMenu().insert(itemUp, 3);

			KDMenuItem itemDown = new KDMenuItem();
			itemDown.setName("降级");
			itemDown.setAction((IItemAction) ActionProxyFactory.getProxy(
					actionDownLine, new Class[] { IItemAction.class },
					getServiceContext()));
			// itemDown.setAction(new ActionDownLine());
			tm.getMenu().insert(itemDown, 4);
		}
	}

	private void setTableColumn(final KDTable table, NewListingPageInfo page) {
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
			colIsKey.setEditor(chkEditor);
			IColumn colIsCompose = table.addColumn(2);
			colIsCompose.setKey("isCompose");
			colIsCompose.setWidth(60);
			row.getCell("isCompose").setValue("单价构成");
			colIsCompose.setEditor(chkEditor);
			int count = 0;
			KDTMergeManager mm = table.getHeadMergeManager();
			mm.mergeBlock(0, 0, 1, 0);
			mm.mergeBlock(0, 1, 1, 1);
			mm.mergeBlock(0, 2, 1, 2);
			int baseCount = 3;
			for (int j = 0; j < sizeColumn; j++) {
				HeadColumnInfo infoColumn = collColumn.get(j).getHeadColumn();
				if (infoColumn != null) {
					IColumn column = table.addColumn();
					column.setKey(infoColumn.getName());
					column.setUserObject(collColumn.get(j));
					this.listingColumnObjectMap.put(table.getName()
							+ column.getKey(), collColumn.get(j));
					this.columnObjMap.put(column, collColumn.get(j));
					row.getCell(j + baseCount).setValue(infoColumn.getName());
					if (infoColumn.getColumnType().equals(ColumnTypeEnum.Amount)) {
						// KDFormattedTextField formattedTextField = new
						// KDFormattedTextField(
						// KDFormattedTextField.BIGDECIMAL_TYPE);
						// formattedTextField.setPrecision(2);
						// formattedTextField.setRemoveingZeroInDispaly(false);
						// formattedTextField.setRemoveingZeroInEdit(false);
						// formattedTextField.setNegatived(false);
						// formattedTextField.setSupportedEmpty(true);
						//formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE
						// );
						//formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE
						// );
						// formattedTextField
						// .setHorizontalAlignment(KDFormattedTextField.RIGHT);
						// ICellEditor numberEditor = new KDTDefaultCellEditor(
						// formattedTextField);
						// column.setEditor(numberEditor);
						if(isRate && infoColumn.isIsQuoting()){
							((KDFormattedTextField)amtEditor.getComponent()).setPrecision(4);
							column.setEditor(amtEditor);
							column.getStyleAttributes().setNumberFormat("#,##0.0000;-#,##0.0000");
							column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						}else{
							column.setEditor(amtEditor);
							column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						}
						
					} else if (infoColumn.getColumnType().equals(
							ColumnTypeEnum.Date)) {
						// KDDatePicker pkDate = new KDDatePicker();
						// ICellEditor dateEditor = new KDTDefaultCellEditor(
						// pkDate);
						column.setEditor(dateEditor);
						column.getStyleAttributes().setNumberFormat(
								"yyyy-MM-dd");
					} else if (infoColumn.getColumnType().equals(
							ColumnTypeEnum.String)) {
						// KDTextField textField = new KDTextField();
						// textField.setMaxLength(80);
						// ICellEditor txtEditor = new KDTDefaultCellEditor(
						// textField);
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
									mm.mergeBlock(0, j + baseCount, 1, j
											+ baseCount,
											KDTMergeManager.SPECIFY_MERGE);
								} else {
									mm.mergeBlock(0, j - count + baseCount, 0,
											j + baseCount,
											KDTMergeManager.DATA_UNIFY);
									count = 0;
									// setNode(table, j - count + baseCount,
									// count, infoColumn.getParent()
									// .getName());
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
		}
		// table.getColumn("子目名称").setWidth(400);
	}

	private void setTableRow(final KDTable table, NewListingPageInfo page) {
		if (page.getEntrys() == null || page.getEntrys().size() <= 0) {
			IRow totalRow = table.addRow();
			totalRow.getCell(3).setValue("合计");
			totalRow.getStyleAttributes().setBackground(Color.lightGray);
			totalRow.getStyleAttributes().setLocked(true);
			return;
		}
		NewListingEntryCollection collEntry = page.getEntrys();
		NewListingEntryInfo infoEntry = null;
		int sizeEntry = collEntry.size();
		IRow totalRow = table.addRow();
		totalRow.getCell(3).setValue("合计");
		totalRow.getStyleAttributes().setBackground(Color.lightGray);
		totalRow.getStyleAttributes().setLocked(true);
		String headTypeId = page.getHeadType().getId().toString();
		KDBizPromptBox f7 = getF7Box(headTypeId);
		CellTextRenderImpl avr = new CellTextRenderImpl();
		avr.getText(new BizDataFormat("$name$"));
		// ObjectValueRenderImpl avr = new ObjectValueRenderImpl();
		// avr.setFormat(new BizDataFormat("$name$"));
		for (int k = 0; k < sizeEntry; k++) {
			IRow dataRow = table.addRow();
			infoEntry = collEntry.get(k);
			dataRow.setUserObject(infoEntry);
			if (infoEntry.getItem() != null) {
				dataRow.getCell(ItemName).setValue(infoEntry.getItem());
			} else {
				dataRow.getCell(ItemName).setValue(infoEntry.getItemName());
			}
			dataRow.getCell(ItemName).setEditor(new KDTDefaultCellEditor(f7));
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

	private KDBizPromptBox getF7Box(String headTypeId) {
		KDBizPromptBox f7 = new KDBizPromptBox() {
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
		};
		f7.setDisplayFormat("$name$");
		f7.setCommitFormat("$name$");
		f7.setEditFormat("$name$");
		f7.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7ListingItemQuery");
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("headType.id", headTypeId,
				CompareType.EQUALS));
		Set orgIdSet = getAllParentIds();
		if (orgIdSet != null) {
			filterItems.add(new FilterItemInfo("orgUnit.id", orgIdSet,
					CompareType.INCLUDE));
		}
		evInfo.setFilter(filter);
		f7.setEntityViewInfo(evInfo);
		f7.setEditable(true);
		f7.setEditorLength(500);
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
		if (orgTreeModel == null)
			return null;

		DefaultKingdeeTreeNode orgRoot = (DefaultKingdeeTreeNode) orgTreeModel
				.getRoot();
		DefaultKingdeeTreeNode node = this.findNode(orgRoot, this.currentOrg
				.getId().toString());
		Set idSet = new HashSet();
		idSet.add(currentOrg.getId().toString());
		while (node != null && node.getParent() != null) {
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

	// =CELL(2,1).getValue()*CELL(2,1).getValue()
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

		// 降级。
		if (item.getName().equals("表格设置")) {
			item.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						actionTableSet_actionPerformed(e);
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
				NewListingColumnInfo column = null;

				if (col.getUserObject() instanceof NewListingColumnInfo) {
					column = (NewListingColumnInfo) col.getUserObject();
				} else {
					column = (NewListingColumnInfo) listingColumnObjectMap
							.get(table.getName() + col.getKey());
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
		return NewListingFactory.getRemoteInstance().getNewListingAllValue(
				pk.toString());
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("inviteType.*");
		sic.add("inviteProject.*");
		sic.add("inviteProject.inviteMode.*");
		sic.add("inviteProject.orgUnit.*");
		sic.add("inviteProject.orgUnit.id");
		sic.add("inviteProject.orgUnit.name221");
		sic.add("inviteProject.inviteType.*");
		sic.add("inviteProject.project.*");
		sic.add("orgUnit.*");
		sic.add("oriOrg.*");
		sic.add("curProject.*");
		sic.add("currency.*");
		return sic;
	}

	/**
	 * 功能描述：加载页面时,从数据库读取数据填充相应单元格-获取数据
	 */
	public void loadTableData(KDTable table, NewListingPageInfo page) {
		Map values = new HashMap();
		for (int i = 0; i < page.getEntrys().size(); i++) {
			NewListingEntryInfo entry = page.getEntrys().get(i);
			for (int j = 0; j < entry.getValues().size(); j++) {
				NewListingValueInfo valueInfo = entry.getValues().get(j);
				if(valueInfo.getEntry().getId() != null){
					String entryId = valueInfo.getEntry().getId().toString();
					String colId = valueInfo.getColumn().getId().toString();
					String key = entryId + colId;
					values.put(key, valueInfo);
				}
				
			}
		}
		for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
			IColumn column = table.getColumn(colIndex);
			NewListingColumnInfo colInfo = null;
			if (column.getUserObject() instanceof NewListingColumnInfo) {
				colInfo = (NewListingColumnInfo) column.getUserObject();	
			} else {
				colInfo = (NewListingColumnInfo) listingColumnObjectMap
						.get(table.getName() + column.getKey());
			}

			if (colInfo == null) {
				continue;
			}
			if (colInfo.getHeadColumn().getName().equals(ItemName)) {
				continue;
			}
			ColumnTypeEnum colType = colInfo.getHeadColumn().getColumnType();
			for (int rowIndex = 1; rowIndex < table.getRowCount(); rowIndex++) {
				IRow row = table.getRow(rowIndex);
				NewListingEntryInfo entry = (NewListingEntryInfo) row.getUserObject();
				if(entry.getId() != null){
					NewListingValueInfo value = (NewListingValueInfo) values.get(entry.getId().toString()+ colInfo.getId().toString());
					if (colInfo.isIsQuoting()) {
						if (page.getHeadType().getId() != null
								&& row.getCell(ItemName).getValue() != null
								&& colInfo.getHeadColumn() != null)
							refPriceBakMap.put(page.getHeadType().getId()
									.toString()
									+ row.getCell(ItemName).getValue().toString()
									+ colInfo.getHeadColumn().getName(), value);
					} else {
						InviteHelper.loadListingValue(row.getCell(colIndex), value,
								colType);
					}
				}
				
				// 已子目名称+列名做key，备份参考价
				
			}
		}

		table.invalidate();
	}

	/**
	 * 设置汇总项
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData(KDTable table) {
		setTableScript(table);
		List colList = new ArrayList();
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			if (column.getUserObject() != null) {
				NewListingColumnInfo col = null;

				if (column.getUserObject() instanceof NewListingColumnInfo) {
					col = (NewListingColumnInfo) column.getUserObject();
				} else {
					col = (NewListingColumnInfo) listingColumnObjectMap
							.get(table.getName() + column.getKey());
				}

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
							sum = sum.add(FDCNumberHelper.toBigDecimal(value));
						}
					}
					row.getCell(colIndex).setValue(sum);
				}
			}
		}
		setTableTotal(table);
		table.getScriptManager().runAll();
		updateTotalPageData();
		setTableLock(table);
		table.getTreeColumn().setDepth(getMaxLevel(table) + 1);
		table.revalidate();
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
		BigDecimal sum = FDCNumberConstants.ZERO;
		for (int i = 0; i < pageCount; i++) {
			KDTable tbl = (KDTable) tabbedPnlTable.getComponentAt(i + 2);
			if (tbl.getRowCount() <= 0)
				continue;

			for (int j = 3; j < tbl.getColumnCount(); j++) {
				IColumn col = tbl.getColumn(j);
				NewListingColumnInfo colInfo = null;

				if (col.getUserObject() instanceof NewListingColumnInfo) {
					colInfo = (NewListingColumnInfo) col.getUserObject();
				} else {
					colInfo = (NewListingColumnInfo) listingColumnObjectMap
							.get(table.getName() + col.getKey());
				}

				if (colInfo != null) {
					DescriptionEnum property = colInfo.getHeadColumn()
							.getProperty();
					if (property.equals(DescriptionEnum.AmountSum)) {
						BigDecimal value = (BigDecimal) tbl.getCell(0, j)
								.getValue();
						table.getCell(i, 2).setValue(value);
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
		if (table.getUserObject() == null) {
			MsgBox.showInfo(this, "不能在此页签上进行分录的操作！");
			return;
		}
		int colIndex = table.getSelectManager().getActiveColumnIndex();
		if (colIndex == table.getColumnIndex("isCompose")) {
			for (int i = 1; i < table.getRowCount(); i++) {
				if (isLeafRow(table, i)) {
					table.getCell(i, colIndex).setValue(Boolean.valueOf(true));
				}
			}
			this.setUnionData(table);
		} else if (colIndex == table.getColumnIndex("isKey")) {
			for (int i = 1; i < table.getRowCount(); i++) {
				table.getCell(i, colIndex).setValue(Boolean.valueOf(true));
			}
		} else {
			MsgBox.showInfo(this, "请先选中支持是否选择的列");
		}
	}

	public void actionNoneSelect_actionPerformed(ActionEvent e)
			throws Exception {
		setColumnObject();
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			MsgBox.showInfo(this, "不能在此页签上进行分录的操作！");
			return;
		}
		int colIndex = table.getSelectManager().getActiveColumnIndex();
		if (colIndex == table.getColumnIndex("isCompose")) {
			for (int i = 1; i < table.getRowCount(); i++) {
				if (isLeafRow(table, i)) {
					table.getCell(i, colIndex).setValue(Boolean.valueOf(false));
				}
			}
			this.setUnionData(table);
		} else if (colIndex == table.getColumnIndex("isKey")) {
			for (int i = 1; i < table.getRowCount(); i++) {
				table.getCell(i, colIndex).setValue(Boolean.valueOf(false));
			}
		} else {
			MsgBox.showInfo(this, "请先选中支持是否选择的列");
		}
	}

	public void actionTableSet_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
			return;
		}
		// tHelper.addMenuToTable(table);
		// UIContext ctx = new UIContext(ui);
		// ctx.put(TablePreferencesHelper.CURRENT_TABLE, table);
		// ctx.put(TablePreferencesHelper.HELPER, tHelper);
		// IUIWindow testuiWindow = null;
		// try {
		// testuiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		// .create(TablePreferences.class.getName(), ctx, null,
		// OprtState.VIEW);
		// testuiWindow.show();
		// } catch (UIException e1) {
		// ExceptionHandler.handle(e1);
		// }
	}

	public void onShow() throws Exception {
		super.onShow();
		this.txtNumber.requestFocus();
	}

	public void actionSetZeroProAmount_actionPerformed(ActionEvent e)
			throws Exception {
		setColumnObject();
		super.actionSetZeroProAmount_actionPerformed(e);
		KDTable table = (KDTable) tabbedPnlTable.getSelectedComponent();
		if (table.getUserObject() == null) {
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
		for (int i = top; i <= bottom; i++) {
			if (!this.isLeafRow(table, i)) {
				continue;
			}
			IRow row = table.getRow(i);
			NewListingEntryInfo entry = (NewListingEntryInfo) row
					.getUserObject();
			entry.setIsCanZeroProAmount(true);
		}
		this.setTableLock(table);
	}

	/**
	 * 反审核、删除前检查是否被引用
	 */
	protected void checkRef(String id) throws Exception {
		String sql = " where listing.id = '" + id + "'";
		if (NewQuotingPriceFactory.getRemoteInstance().exists(sql)) {
			// 删除前先检查状态，所以删除时有报价的清单不会执行到该处，可以用该msg
			MsgBox.showWarning(this, "已经有投标报价,不能反审批!");
			abort();
		}
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

	public void actionImportMaterial_actionPerformed(ActionEvent e)
			throws Exception {

		if (this.prmtInviteProject.getValue() == null) {
			FDCMsgBox.showWarning(this, "招标立项为空，不允许引入物料");
			abort();
		}

		InviteProjectInfo projectInfo = (InviteProjectInfo) this.prmtInviteProject
				.getValue();
		InviteProjectEntryCollection projectEntrys = InviteProjectEntryFactory
				.getRemoteInstance().getInviteProjectEntryCollection(
						"select *,material.*,measureUnit.* where parent.id='"
								+ projectInfo.getId().toString() + "'");

		if (projectEntrys != null && projectEntrys.size() > 0) {
//			projectInfo.getEntry().clear();
//			projectInfo.getEntry().addCollection(projectEntrys);

			if (editData.getPages().size() > 1) {
				FDCMsgBox.showWarning(this, "清单中存在多个页签，不允许引入物料");

			} else if (editData.getPages().size() < 1) {
				FDCMsgBox.showWarning(this, "不存在可导入物料的页签");
			} else {
				/***
				 * 同步招标立项中的物料清单,到招标清单中
				 */
				KDTable table = (KDTable) tabbedPnlTable.getComponentAt(2);
				if (table.getUserObject() == null)
					return;
				NewListingPageInfo info = (NewListingPageInfo) table
						.getUserObject();
				while (table.getRowCount() > 1) {
					table.removeRow(table.getRowCount() - 1);
				}
				for (Iterator it = projectEntrys.iterator(); it.hasNext();) {
					InviteProjectEntryInfo entryInfo = (InviteProjectEntryInfo) it
							.next();
					if (entryInfo.getMaterial() != null) {
						NewListingEntryInfo infoEntry = new NewListingEntryInfo();
						IRow row = table.addRow();
						CellTreeNode node = new CellTreeNode();
						row.setTreeLevel(0);
						row.setUserObject(infoEntry);
						String headTypeId = info.getHeadType().getId()
								.toString();
						KDBizPromptBox f7 = this.getF7Box(headTypeId);
						CellTextRenderImpl avr = new CellTextRenderImpl();
						avr.getText(new BizDataFormat("$name$"));
						row.getCell(ItemName).setEditor(new KDTDefaultCellEditor(f7));
						row.getCell(ItemName).setRenderer(avr);
						row.getCell(ItemName).setValue(entryInfo.getMaterial().getNumber() + entryInfo.getMaterial().getName());
						if (row.getCell("单位") != null)
							row.getCell("单位").setValue(entryInfo.getMeasureUnit().getName());
						if (row.getCell("规格") != null)
							row.getCell("规格").setValue(entryInfo.getSize());
						if (row.getCell("规格型号") != null)
							row.getCell("规格型号").setValue(entryInfo.getSize());
						if (row.getCell("型号") != null)
							row.getCell("型号").setValue(entryInfo.getSize());
						if (row.getCell("工程量") != null)
							row.getCell("工程量").setValue(entryInfo.getAmount());
						row.getCell("level").setValue(new Integer(0));
						row.getCell("isKey").setValue(Boolean.FALSE);
						row.getCell("isCompose").setValue(Boolean.FALSE);
						row.setTreeLevel(0);
						node.setValue(new Integer(0));
						node.setTreeLevel(0);
						infoEntry.setIsCanZeroProAmount(false);
						infoEntry.setLevel(0);
						infoEntry.setIsLeaf(true);
					}
				}
				setUnionData(table);
			}
		} else {
			FDCMsgBox.showWarning(this, "招标立项里没有可引入的物料");
		}
	}

	protected void prmtInviteProject_dataChanged(DataChangeEvent e)
			throws Exception {
		Object newValue = e.getNewValue();
		
		if (newValue instanceof InviteProjectInfo) {
			InviteProjectInfo projectInfo = (InviteProjectInfo) newValue;
			isRate = projectInfo.isIsRate();
			// 带出编码显示
			this.txtInviteProjectNumber.setText(projectInfo.getNumber());
			if (!this.isFirstOnload()) {
				// 取消关联单据的过滤，修改为已定标过滤  modify by msb 2010/04/26
				// 恢复此过滤 modify by msb 2010/04/30  需求变动
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("inviteProject.id", projectInfo
								.getId().toString()));
				if (editData.getId() != null)
					filter.getFilterItems().add(
							new FilterItemInfo("id", editData.getId()
									.toString(), CompareType.NOTEQUALS));
				if (!isReversion) {
					if (this.getBizInterface().exists(filter)) {
						FDCMsgBox.showWarning(this, "此招标立项已经关联了招标清单，请重新选择");
						this.prmtInviteProject.setValue(e.getOldValue());

						if (e.getOldValue() == null) {
							this.prmtCurProject.setValue(null);
							this.prmtInviteType.setValue(null);
						}
						return;
					}
				}
				
				
				this.actionImportMaterial
						.setEnabled(projectInfo.isIsMaterial());
				/*if (projectInfo.getProject() != null) {
					editData.setCurProject(projectInfo.getProject());

					this.prmtCurProject.setValue(projectInfo.getProject());
				}*/
				if (projectInfo.getInviteType() != null) {
					editData.setInviteType(projectInfo.getInviteType());
					this.prmtInviteType.setValue(projectInfo.getInviteType());
				}
			}
		}
	}

	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
		verifyDuplicationItem();
	}

	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
		verifyDuplicationItem();
	}

	/**
	 * modify by msb
	 *@deprecated 
	 */
	protected void checkIsReference() throws BOSException {
		if (editData.getInviteProject() == null) {
			MsgBox.showWarning("请选择招标立项");
			abort();
		}
		if (getOprtState().equals(OprtState.ADDNEW)) {
			if (editData.getInviteProject() != null) {
				String inviteProjectId = editData.getInviteProject().getId()
						.toString();
				NewListingCollection cols = getNewListingCols(inviteProjectId,
						null);

				if (cols.size() > 0) {
					MsgBox.showWarning("该招标立项已被其他招标清单引用，不能执行此操作");
					abort();
				}
			}
		} else if (getOprtState().equals(OprtState.EDIT)) {
			if (editData.getInviteProject() != null) {
				if (editData.getId() != null) {
					String inviteProjectId = editData.getInviteProject()
							.getId().toString();
					NewListingCollection cols = getNewListingCols(
							inviteProjectId, editData.getId().toString());

					if (cols.size() > 1) {
						MsgBox.showWarning("该招标立项已被其他招标清单引用，不能执行此操作");
						abort();
					}
				} else {
					String inviteProjectId = editData.getInviteProject()
							.getId().toString();
					NewListingCollection cols = getNewListingCols(
							inviteProjectId, null);

					if (cols.size() > 0) {
						MsgBox.showWarning("该招标立项已被其他招标清单引用，不能执行此操作");
						abort();
					}
				}
			}
		}
	}

	private void setCurProjectFilter(SelectorEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();

		view.setSelector(getCurProjectSelectorCols());

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.valueOf(true)));

		// 明细工程项目
		filter.getFilterItems().add(
				new FilterItemInfo("isLeaf", Boolean.valueOf(true)));

		/**
		 * 组织架构树:财务组织，带需求确认好再修改
		 */
		FullOrgUnitInfo orgUnit = (FullOrgUnitInfo) SysContext.getSysContext()
				.getCurrentFIUnit().castToFullOrgUnitInfo();
		FilterInfo filterOrgUnit = new FilterInfo();
		filterOrgUnit.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.longNumber", orgUnit
						.getLongNumber()
						+ "!%", CompareType.LIKE));
		filterOrgUnit.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.longNumber", orgUnit
						.getLongNumber()));

		filterOrgUnit.setMaskString("#0 or #1");

		filter.mergeFilter(filterOrgUnit, "and");

		view.setFilter(filter);
		prmtCurProject.setEntityViewInfo(view);
		if (prmtCurProject.getSelector() != null
				&& prmtCurProject.getSelector() instanceof com.kingdee.eas.framework.client.ListUI) {
			((com.kingdee.eas.framework.client.ListUI) prmtCurProject
					.getSelector()).setFilterForQuery(filter);
			((com.kingdee.eas.framework.client.ListUI) prmtCurProject
					.getSelector()).onLoad();

		} else {
			prmtCurProject.getEntityViewInfo().setFilter(filter);
			prmtCurProject.getQueryAgent().resetRuntimeEntityView();
			prmtCurProject.setRefresh(true);
		}
	}

	private SelectorItemCollection getCurProjectSelectorCols() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("name"));

		sic.add(new SelectorItemInfo("landDeveloper.name"));
		sic.add(new SelectorItemInfo("startDate"));
		sic.add(new SelectorItemInfo("sortNo"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));

		sic.add(new SelectorItemInfo("parent.id"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("projectStatus.name"));
		sic.add(new SelectorItemInfo("projectPeriod"));
		sic.add(new SelectorItemInfo("projectType.name"));

		sic.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sic.add(new SelectorItemInfo("projectStatus.name"));
		sic.add(new SelectorItemInfo("projectType.name"));

		sic.add(new SelectorItemInfo("isLeaf"));

		return sic;
	}

	private boolean isSelectMaterialType(InviteTypeInfo info) {
		return info.getLongNumber().equals(
				InviteProjectInfo.INVITETYPE_MATERIALLONGNUMBER)
				|| info.getLongNumber().startsWith(
						InviteProjectInfo.INVITETYPE_MATERIALLONGNUMBER + "!");
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * 打印
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		InvitePrintDataProvider data = new InvitePrintDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * 打印预览
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		logger.info("打印预览");
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		InvitePrintDataProvider data = new InvitePrintDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	// 获得无文本合同套打对应的目录
	protected String getTDFileName() {
		return "/bim/fdc/invite/NewListingForPrint";
	}

	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.invite.app.NewListingForPrintQuery");
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
