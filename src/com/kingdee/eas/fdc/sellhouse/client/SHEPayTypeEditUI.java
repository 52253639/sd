/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo;
import com.kingdee.eas.fdc.sellhouse.AfPreEnum;
import com.kingdee.eas.fdc.sellhouse.BizFlowEnum;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizNewFlowEnum;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.CollectionFactory;
import com.kingdee.eas.fdc.sellhouse.CollectionInfo;
import com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum;
import com.kingdee.eas.fdc.sellhouse.LoanPreEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeBizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class SHEPayTypeEditUI extends AbstractSHEPayTypeEditUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3321510345248355991L;
	private static final Logger logger = CoreUIObject
			.getLogger(SHEPayTypeEditUI.class);

	private boolean isFirstLoad = false;
	private int height = 500;
	private boolean isLoan = false;
	private KDComboBox bizCombo = new KDComboBox();
	private KDBizPromptBox f7Customer=null;
	private MoneyDefineInfo moneyType = null;
	private int rowIndex = 0;

	// private AgioParam agioParam = new AgioParam();

	/**
	 * output class constructor
	 */
	public SHEPayTypeEditUI() throws Exception {
		super();

	}

	public void onLoad() throws Exception {
		initControl();
		initPayTable();
		initBizTable();
		initSellProject();
		initLoanPreAndAFPre();
		super.onLoad();
	
		if (!isSellOrg()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
		}

		this.tblBizList.addKDTEditListener(new KDTEditListener() {

			public void editCanceled(KDTEditEvent e) {
			}

			public void editStarted(KDTEditEvent e) {
			}

			public void editStarting(KDTEditEvent e) {
				 if (e.getColIndex() == tblBizList.getColumn("bizTime")
							.getColumnIndex()) {
						try {
							int index = e.getRowIndex();
							setBizTime(index);
						} catch (Exception e1) {
							logger.error(e1.getMessage());
						}
					}
			}

			public void editStopped(KDTEditEvent e) {
			}

			public void editStopping(KDTEditEvent e) {
			}

			public void editValueChanged(KDTEditEvent e) {
			}

		});
		
		this.tblPayList.addKDTEditListener(new KDTEditListener() {

			public void editCanceled(KDTEditEvent e) {
			}

			public void editStarted(KDTEditEvent e) {
			}

			public void editStarting(KDTEditEvent e) {
				 if (e.getColIndex() == tblPayList.getColumn("collection")
							.getColumnIndex()) {
						try {
							int index = e.getRowIndex();
							initCollection(index);
						} catch (Exception e1) {
							logger.error(e1.getMessage());
						}
					}
			}
			public void editStopped(KDTEditEvent e) {
				 if (e.getColIndex() == tblPayList.getColumn("moneyName")
						.getColumnIndex()) {
					try {
						int index = e.getRowIndex();
						initCollection(index);
					} catch (Exception e1) {
						logger.error(e1.getMessage());
					}
				}
				
			}

			public void editStopping(KDTEditEvent e) {
			}

			public void editValueChanged(KDTEditEvent e) {
			}

		});
		
		txtAgio.setVisible(false);
		lblAgio.setVisible(false);
		
		this.btnAddLine.setVisible(false);
		this.btnDeleteLine.setVisible(false);
	}
	
	private void initLoanPreAndAFPre() {
		
		/**
		 * 初始化按揭精度
		 */
		Iterator iteratorLoanPre = LoanPreEnum.iterator();
		while (iteratorLoanPre.hasNext()) {
				this.cbcLoanPre.addItem(iteratorLoanPre.next());
		}
		/**
		 * 初始化公积金精度
		 */
		Iterator iteratorAFPre = AfPreEnum.iterator();
		while (iteratorAFPre.hasNext()) {
				this.cbcAFPre.addItem(iteratorAFPre.next());
		}
		
	}

	private void initCollection(int rowIndex) {
		IRow row = tblPayList.getRow(rowIndex);
		
		if(row==null){
			return;
		}
	
		f7Customer = new KDBizPromptBox();
		moneyType = (MoneyDefineInfo) row.getCell("moneyName").getValue();
		
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		if(moneyType!=null){
			filterInfo.getFilterItems().add(
					new FilterItemInfo("moneyName.id", moneyType.getId().toString(),
							CompareType.EQUALS));
		}
		if(prtSellProject.getValue()!=null){
		   SellProjectInfo sell=(SellProjectInfo)prtSellProject.getValue();
		   filterInfo.getFilterItems().add(
					new FilterItemInfo("project.id", sell.getId().toString(),
							CompareType.EQUALS));
		}
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		evi.setFilter(filterInfo);
		
		f7Customer
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CollectionQuery");
		f7Customer.setEntityViewInfo(evi);
		f7Customer.setEditable(true);
		f7Customer.setDisplayFormat("$name$");
		f7Customer.setEditFormat("$number$");
		f7Customer.setCommitFormat("$number$");
	
		
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Customer);
		if (rowIndex == -2) {
			this.tblPayList.getColumn("collection").setEditor(f7Editor);
		} else {
			this.tblPayList.getRow(rowIndex).getCell("collection").setEditor(
					f7Editor);
		}
		
		f7Customer.getQueryAgent().resetRuntimeEntityView();
	}

	private void setBizTime(int index) {
		bizCombo.removeAllItems();
		HashMap map = new HashMap();
		IRow row = tblBizList.getRow(index);
		int rowCount = 0;
		Object[] objArray = null;
		if (row.getRowIndex() < 0) {
			return;
		} else {
			rowCount = row.getRowIndex();
			objArray = new Object[rowCount + 3];
			objArray[0] = "指定日期";
			if(rowCount==0){
				if (tblBizList.getRow(0).getCell("bizFlow").getValue() != null) {
					BizNewFlowEnum bizFlow = (BizNewFlowEnum) tblBizList
							.getRow(0).getCell("bizFlow").getValue();
					map.put(bizFlow.getAlias(), bizFlow.getAlias());
				
					if (bizFlow.equals(BizNewFlowEnum.PREREGISTER)
							|| bizFlow.equals(BizNewFlowEnum.PURCHASE)
							|| bizFlow.equals(BizNewFlowEnum.SIGN)) {
						objArray[1] = "签署日期";
					}
				}
			}
			for (int i = 0; i < rowCount; i++) {
				if (tblBizList.getRow(i).getCell("bizFlow").getValue() != null) {
					BizNewFlowEnum bizFlow = (BizNewFlowEnum) tblBizList
							.getRow(i).getCell("bizFlow").getValue();

					if (map.containsKey(bizFlow.getAlias())) {
						continue;
					}
					map.put(bizFlow.getAlias(), bizFlow.getAlias());
					objArray[i + 1] = bizFlow.getAlias();
				}
			}
			if(rowCount>0){
				IRow selectRow = KDTableUtil.getSelectedRow(tblBizList);
				if(selectRow!=null){
					if(selectRow.getCell("bizFlow").getValue()!=null){
						BizNewFlowEnum bizFlow = (BizNewFlowEnum) selectRow.getCell("bizFlow").getValue();
						map.put(bizFlow.getAlias(), bizFlow.getAlias());
						if (bizFlow.equals(BizNewFlowEnum.PREREGISTER)
								|| bizFlow.equals(BizNewFlowEnum.PURCHASE)
								|| bizFlow.equals(BizNewFlowEnum.SIGN)) {
							objArray[rowCount+1] = "签署日期";
						}
					}
				}
			}
		}
		bizCombo.addItems(objArray);
	}

	private void initBizTable() {
		this.tblBizList.checkParsed();
		this.tblBizList.getStyleAttributes().setLocked(false);
		this.tblBizList.getSelectManager().setSelectMode(
				KDTSelectManager.CELL_SELECT);
		this.tblBizList.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		KDComboBox combo = new KDComboBox();
		Iterator iterator = BizNewFlowEnum.iterator();
		while (iterator.hasNext()) {
			combo.addItem(iterator.next());
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.tblBizList.getColumn("bizFlow").setEditor(comboEditor);

		comboEditor = new KDTDefaultCellEditor(bizCombo);
		this.tblBizList.getColumn("bizTime").setEditor(comboEditor);

		this.tblBizList.getColumn("month").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblBizList.getColumn("month").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(0));
		this.tblBizList.getColumn("day").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblBizList.getColumn("day").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.INTEGER);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblBizList.getColumn("day").setEditor(numberEditor);
		this.tblBizList.getColumn("month").setEditor(numberEditor);
		String formatString = "yyyy-MM-dd";
		this.tblBizList.getColumn("appDate").getStyleAttributes()
				.setNumberFormat(formatString);
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		this.tblBizList.getColumn("appDate").setEditor(dateEditor);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblBizList.getColumn("des").setEditor(txtEditor);
	}

	private void initPayTable() {
		this.tblPayList.checkParsed();
		this.tblPayList.getStyleAttributes().setLocked(false);
		this.tblPayList.removeColumn(this.tblPayList.getColumnIndex("isMoney"));
		this.tblPayList.getSelectManager().setSelectMode(
				KDTSelectManager.CELL_SELECT);
		this.tblPayList.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		this.tblPayList.getColumn("month").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("month").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(0));
		this.tblPayList.getColumn("day").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("day").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		String formatString = "yyyy-MM-dd";
		this.tblPayList.getColumn("appDate").getStyleAttributes()
				.setNumberFormat(formatString);
		this.tblPayList.getColumn("signcontracttime").getStyleAttributes().setNumberFormat(formatString);//add bu shilei
		this.tblPayList.getColumn("amount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("amount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblPayList.getColumn("pro").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("pro").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		this.tblPayList.getColumn("term").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("term").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		this.tblPayList.getColumn("jiange").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblPayList.getColumn("jiange").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(0));
		
		this.tblPayList.getColumn("term").getStyleAttributes().setHided(true);
		this.tblPayList.getColumn("jiange").getStyleAttributes().setHided(true);
		
		KDComboBox combo = new KDComboBox();
		/*
		 * Iterator iterator = BizTimeEnum.iterator(); while
		 * (iterator.hasNext()) { combo.addItem(iterator.next()); }
		 */
		Iterator iterator = SHEPayTypeBizTimeEnum.iterator();
		while (iterator.hasNext()) {
				combo.addItem(iterator.next());
		}
		combo.removeItem(SHEPayTypeBizTimeEnum.SIGNDATE);
		
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.tblPayList.getColumn("bizTime").setEditor(comboEditor);
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		this.tblPayList.getColumn("appDate").setEditor(dateEditor);
		
		KDDatePicker contractpk = new KDDatePicker(); //add by shilei
		KDTDefaultCellEditor contractEditor = new KDTDefaultCellEditor(contractpk);//add by shilei
		this.tblPayList.getColumn("signcontracttime").setEditor(contractEditor);//add by shilei
		
		// 初始化款项名称
		this.tblPayList.getColumn("moneyName").setEditor(SHEManageHelper.getMoneyTypeCellEditorForSHE());

		// 初始化代收费用公式定义 add by renliang at 2011-6-2
		KDBizPromptBox f7collectionBox = new KDBizPromptBox();
		f7collectionBox.setDisplayFormat("$number$");
		f7collectionBox.setCommitFormat("$number$");
		f7collectionBox.setEditFormat("$number$");
		f7collectionBox.setEditable(false);
		f7collectionBox
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CollectionQuery");

		EntityViewInfo viewcollection = new EntityViewInfo();
		FilterInfo filtercollection = new FilterInfo();
		viewcollection.setFilter(filtercollection);
		if (this.getUIContext().get("sellProject") != null) {
			SellProjectInfo info = (SellProjectInfo) this.getUIContext().get(
					"sellProject");
			filtercollection.getFilterItems().add(
					new FilterItemInfo("project.id", info.getId().toString()));
		}
		filtercollection.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		f7collectionBox.setEntityViewInfo(viewcollection);
		KDTDefaultCellEditor f7collectionEditor = new KDTDefaultCellEditor(
				f7collectionBox);
		this.tblPayList.getColumn("collection").setEditor(f7collectionEditor);
		//以下方法是为了解决不能显示的问题 by renliang at 2011-6-17
		this.tblPayList.getColumn("collection").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof CollectionInfo ){
					return ((CollectionInfo) obj).getNumber();
				}
				return super.getText(obj);
			}
		});

		this.tblPayList.getColumn("collection").getStyleAttributes().setLocked(true);
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setDisplayFormat("$name$");
		f7Box.setCommitFormat("$number$");
		f7Box.setEditFormat("$number$");
		f7Box
				.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery");
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		this.tblPayList.getColumn("currency").setEditor(f7Editor);
		this.tblPayList.getColumn("currency").getStyleAttributes().setLocked(
				true);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.INTEGER);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblPayList.getColumn("day").setEditor(numberEditor);
		this.tblPayList.getColumn("month").setEditor(numberEditor);
		this.tblPayList.getColumn("term").setEditor(numberEditor);
		this.tblPayList.getColumn("jiange").setEditor(numberEditor);
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblPayList.getColumn("amount").setEditor(numberEditor);
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblPayList.getColumn("pro").setEditor(numberEditor);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblPayList.getColumn("des").setEditor(txtEditor);
	}
	
	private void setControl(){
		if(OprtState.EDIT.equals(this.getOprtState())){
			for (int i = 0; i < tblPayList.getRowCount(); i++) {
				IRow row = this.tblPayList.getRow(i);
				if(row==null){
					continue;
				}
				row.getCell("bizTime").getStyleAttributes().setLocked(false);
				SHEPayTypeBizTimeEnum bizTime = (SHEPayTypeBizTimeEnum) row
						.getCell("bizTime").getValue();
 				if (bizTime == null || "".equals(bizTime)) {
					row.getCell("month").getStyleAttributes().setLocked(true);
					row.getCell("day").getStyleAttributes().setLocked(true);
					row.getCell("appDate").getStyleAttributes().setLocked(true);
				}else{
					if (bizTime.equals(SHEPayTypeBizTimeEnum.APPTIME)) {
						row.getCell("month").getStyleAttributes().setLocked(true);
						row.getCell("day").getStyleAttributes().setLocked(true);
						row.getCell("appDate").getStyleAttributes()
								.setLocked(false);
					} else {
						row.getCell("month").getStyleAttributes().setLocked(false);
						row.getCell("day").getStyleAttributes().setLocked(false);
						row.getCell("appDate").getStyleAttributes().setLocked(true);
					}
				}
				
				if(row.getCell("moneyName").getValue() != null){
					MoneyDefineInfo moneyType = (MoneyDefineInfo) row.getCell(
					"moneyName").getValue();
					if (moneyType.getMoneyType() == MoneyTypeEnum.ReplaceFee) {
						row.getCell("collection").getStyleAttributes().setLocked(false);
					}else{
						row.getCell("collection").getStyleAttributes().setLocked(true);
					}
				}
				row.getCell("amount").getStyleAttributes().setLocked(false);
				row.getCell("pro").getStyleAttributes().setLocked(false);
				row.getCell("des").getStyleAttributes().setLocked(false);
			}
		}
	}
	
	private void initMoneyDefine(boolean isLoan){
		// 初始化款项名称
//		KDBizPromptBox f7Box = new KDBizPromptBox();
//		f7Box.setDisplayFormat("$name$");
//		f7Box.setCommitFormat("$number$");
//		f7Box.setEditFormat("$number$");
//		f7Box
//				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
//
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//		filter.getFilterItems().add(
//				new FilterItemInfo("sysType",
//						MoneySysTypeEnum.SALEHOUSESYS_VALUE,
//						CompareType.EQUALS));
//	
//		filter.getFilterItems().add(
//				new FilterItemInfo("moneyType",
//						MoneyTypeEnum.PREMONEY_VALUE,
//						CompareType.NOTEQUALS));
//		if(!isLoan){
//			filter.getFilterItems().add(
//					new FilterItemInfo("moneyType",
//							MoneyTypeEnum.LOANAMOUNT_VALUE,
//							CompareType.NOTEQUALS));
//			filter.getFilterItems().add(
//					new FilterItemInfo("moneyType",
//							MoneyTypeEnum.ACCFUNDAMOUNT_VALUE,
//							CompareType.NOTEQUALS));
//		}
//		
//		filter.getFilterItems().add(
//				new FilterItemInfo("moneyType",
//						MoneyTypeEnum.PRECONCERTMONEY_VALUE,
//						CompareType.NOTEQUALS));
//		
//		filter.getFilterItems().add(
//				new FilterItemInfo("moneyType",
//						MoneyTypeEnum.COMPENSATEAMOUNT_VALUE,
//						CompareType.NOTEQUALS));
//		
//		filter.getFilterItems().add(
//				new FilterItemInfo("moneyType",
//						MoneyTypeEnum.FITMENTAMOUNT_VALUE,
//						CompareType.NOTEQUALS));
//		
//
//		f7Box.setEntityViewInfo(view);
//		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		this.tblPayList.getColumn("moneyName").setEditor(SHEManageHelper.getMoneyTypeCellEditorForSHE());
	}

	private void initControl() {
		// this.contAgio.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		// this.spiLoanPre.setEnabled(false);
		// this.spiAFPre.setEnabled(false);
		this.txtAgio.setRemoveingZeroInDispaly(false);
		this.txtAgio.setRemoveingZeroInEdit(false);
		this.txtAgio.setPrecision(2);
		this.txtAgio.setHorizontalAlignment(JTextField.RIGHT);
		this.txtAgio.setSupportedEmpty(true);
		this.actionInsertLine.setEnabled(false);
		this.actionInsertLine.setVisible(false);

		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);

		this.actionCopy.setVisible(true);
	
		if (!isSellOrg()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAddLine.setEnabled(false);
			this.actionDeleteLine.setEnabled(false);
		}

		if (OprtState.VIEW.equals(this.getOprtState())) {
			this.actionAddLine.setEnabled(false);
			this.actionDeleteLine.setEnabled(false);
		}

		initLoanScheme();
		initAFMScheme();
		// intitAgioSchme();
		// this.txtAgioDesc.setEditable(false);

		if (OprtState.VIEW.equals(this.getOprtState())) {
			this.rbYes.setEnabled(false);
			this.rbNo.setEnabled(false);
			this.rbRoomYes.setEnabled(false);
			this.rbRoomNo.setEnabled(false);
		}
		
		this.kDDatePicker2.setRequired(false);
		
		this.prtSellProject.setEnabled(false);
	}
	
	private boolean isSellOrg() {
		boolean res = false;
		try {
			FullOrgUnitInfo fullOrginfo = SysContext.getSysContext()
					.getCurrentOrgUnit().castToFullOrgUnitInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sellHouseStrut", Boolean.TRUE,
							CompareType.EQUALS));
			if (fullOrginfo != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("unit.id", fullOrginfo.getId()
								.toString(), CompareType.EQUALS));
			}
			filter.getFilterItems().add(
					new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID,
							CompareType.EQUALS));
			res = FDCOrgStructureFactory.getRemoteInstance().exists(filter);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "获得售楼组织失败!");
		}

		return res;
	}

	public void onShow() throws Exception {
		super.onShow();
		this.prtSellProject.setEnabled(false);
		this.prtSellProject.setEditable(false);
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			this.rbRoomYes.setSelected(true);
			this.rbYes.setSelected(true);
			this.rbNo.setSelected(false);
			this.rbRoomNo.setSelected(false);
			this.dpValid.setValue(new Timestamp(System.currentTimeMillis()));
			this.tblBizList.removeRows();
			txtAgio.setValue(FDCHelper.ONE_HUNDRED);
			this.cbcLoanPre.setSelectedItem(LoanPreEnum.HUNDRED);
			this.cbcAFPre.setSelectedItem(AfPreEnum.HUNDRED);
		}

		if (!OprtState.ADDNEW.equals(this.getOprtState())) {
			if (!this.isLoan) {
//				this.tabEntry.setBounds(24, 294, 618, 192);
//				this.kDContainer1.setBounds(28, 600, 617, 114);
//				SwingUtilities.getWindowAncestor(this).setSize(665,
//						height + 65);
				this.kDContainer1.setVisible(false);
				isFirstLoad = false;
			}
		}
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			this.rbYes.setSelected(true);
			this.txtAgio.setValue(FDCHelper.ONE_HUNDRED);
			this.rbRoomYes.setSelected(true);
//			this.tabEntry.setBounds(24, 404, 617, 192);
//			this.kDContainer1.setBounds(23, 278, 618, 114);
//			if (SwingUtilities.getWindowAncestor(this) != null) {
//				SwingUtilities.getWindowAncestor(this)
//						.setSize(665, height + 180);
//			}
			this.kDContainer1.setVisible(true);
			isFirstLoad = true;
			this.dpValid.setValue(new Timestamp(System.currentTimeMillis()));
			this.actionAddLine.setEnabled(true);
			this.actionDeleteLine.setEnabled(true);
			this.rbYes.setEnabled(true);
			this.rbNo.setEnabled(true);
			this.rbRoomYes.setEnabled(true);
			this.rbRoomNo.setEnabled(true);
			this.cbcLoanPre.setSelectedItem(LoanPreEnum.HUNDRED);
			this.cbcAFPre.setSelectedItem(AfPreEnum.HUNDRED);
		}
	}
	
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
		this.actionAddLine.setEnabled(true);
		this.actionDeleteLine.setEnabled(true);
		this.rbYes.setEnabled(true);
		this.rbNo.setEnabled(true);
		this.rbRoomYes.setEnabled(true);
		this.rbRoomNo.setEnabled(true);
		this.prtSellProject.setEnabled(true);
		for (int i = 0; i < tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			if(row==null){
				continue;
			}
			if(row.getCell("moneyName").getValue() != null){
				MoneyDefineInfo moneyType = (MoneyDefineInfo) row.getCell(
				"moneyName").getValue();
				if (moneyType.getMoneyType() != null
						&& !"".equals(moneyType.getMoneyType())
						&& moneyType.getMoneyType().equals(
								MoneyTypeEnum.ReplaceFee)) {
					row.getCell("bizTime").getStyleAttributes()
							.setLocked(false);
					row.getCell("collection").getStyleAttributes().setLocked(
							false);
				}
				
			}
			
			row.getCell("amount").getStyleAttributes().setLocked(false);
			row.getCell("pro").getStyleAttributes().setLocked(false);
			row.getCell("des").getStyleAttributes().setLocked(false);
		}
		
		this.isFirstLoad = false;
	}


	// 校验折扣最大值
	private void vitfyAgio(KDFormattedTextField txtDiscount) {
		if (txtDiscount.getBigDecimalValue() == null) {
			return;
		}
		BigDecimal agioValue = txtDiscount.getBigDecimalValue();
		if (agioValue.compareTo(new BigDecimal(100)) == 1) {
			MsgBox.showInfo("最大折扣不能超过100");
			txtDiscount.setValue(null);
			abort();
		}
		if (agioValue.compareTo(new BigDecimal(0)) == -1
				|| agioValue.compareTo(new BigDecimal(0)) == 0) {
			MsgBox.showInfo("折扣必须大于0");
			txtDiscount.setValue(null);
			abort();
		}
	}
	
	private void initSellProject(){
		OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("orgUnit.id", org.getId().toString(),
						CompareType.EQUALS));
		
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isForSHE", Boolean.TRUE,
						CompareType.EQUALS));
		if(this.getUIContext().get("allSpIdStr")!=null){
			String idStr = this.getUIContext().get("allSpIdStr").toString();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("id", idStr,
							CompareType.INNER));
		}
		String queryInfo = "com.kingdee.eas.fdc.sellhouse.app.SellProjectForSHESellProjectQuery";
		SHEHelper.initF7(this.prtSellProject, queryInfo, filterInfo);
	}

	public void loadFields() {
		SHEHelper.setNumberEnabled(editData, getOprtState(), txtNumber);
		SHEPayTypeInfo type = (SHEPayTypeInfo) this.editData;
		this.txtNumber.setText(type.getNumber());
		this.txtName.setText(type.getName());
		this.txtAgio.setValue(type.getAgio());
		this.txtDes.setText(type.getDescription());
		/**
		 * 以下为售楼优化添加代码 by renliang at 2011-6-1
		 */
		this.prtSellProject.setValue(type.getProject());
		this.prtAfmBank.setValue(type.getAcfBank());
		this.prtLoanBank.setValue(type.getLoanBank());
		this.prtLoanScheme.setValue(type.getLoanScheme());
		this.dpValid.setValue(type.getValidDate());
		this.kDDatePicker2.setValue(type.getInvalidDate());
		this.prtAfmScheme.setValue(type.getAfScheme());
		
		if (type.isIsLoan()) {
			this.rbYes.setSelected(true);
			initMoneyDefine(true);
		} else {
			this.rbNo.setSelected(true);
			initMoneyDefine(false);
		}
		if(type.isIsTotal()){
			this.rbRoomYes.setSelected(true);
		}else{
			this.rbRoomNo.setSelected(true);
		}
		
		if(type.getNewLoanPre()!=null){
			this.cbcLoanPre.setSelectedItem(type.getNewLoanPre());
		}else{
			this.cbcLoanPre.setSelectedItem(LoanPreEnum.HUNDRED);
		}
		if(type.getNewAfPre()!=null){
			this.cbcAFPre.setSelectedItem(type.getNewAfPre());
		}else{
			this.cbcAFPre.setSelectedItem(AfPreEnum.HUNDRED);
		}
		

		PayListEntryCollection payLists = type.getPayLists();
		tblPayList.removeRows();
		for (int i = 0; i < payLists.size(); i++) {
			PayListEntryInfo entry = payLists.get(i);
			addPayEntryRow(entry);
			initCollection(i);
		}
		BizListEntryCollection bizLists = type.getBizLists();
		tblBizList.removeRows();
		for (int i = 0; i < bizLists.size(); i++) {
			BizListEntryInfo entry = bizLists.get(i);
			addBizEntryRow(entry);
		}
		super.loadFields();
		isFirstLoad = true;
		this.isLoan = type.isIsLoan();
		setControl();
	}

	private void addBizEntryRow(BizListEntryInfo entry) {
		IRow row = tblBizList.addRow();
		row.setUserObject(entry);
		// row.getCell("bizFlow").setValue(entry.getBizFlow());
		row.getCell("bizFlow").setValue(entry.getPayTypeBizFlow());
		row.getCell("bizTime").setValue(entry.getPayTypeBizTime());
		row.getCell("month").setValue(new Integer(entry.getMonthLimit()));
		row.getCell("day").setValue(new Integer(entry.getDayLimit()));
		row.getCell("appDate").setValue(entry.getAppDate());
		row.getCell("des").setValue(entry.getDescription());
		setRowAppTimeEnableForBiz(row);
	}

	private void addPayEntryRow(PayListEntryInfo entry) {
		IRow row = tblPayList.addRow();
		row.setUserObject(entry);
		row.getCell("moneyName").setValue(entry.getMoneyDefine());
		row.getCell("bizTime").setValue(entry.getPayTypeBizTime());
		row.getCell("month").setValue(new Integer(entry.getMonthLimit()));
		row.getCell("day").setValue(new Integer(entry.getDayLimit()));
		row.getCell("appDate").setValue(entry.getAppDate());
		row.getCell("signcontracttime").setValue(entry.getSigncontracttime()); //add by shilei
		row.getCell("des").setValue(entry.getDescription());
		row.getCell("currency").setValue(entry.getCurrency());
		row.getCell("amount").setValue(entry.getValue());
		row.getCell("pro").setValue(entry.getProportion());
		row.getCell("term").setValue(new Integer(entry.getTerm()));
		row.getCell("jiange").setValue(new Integer(entry.getMonthInterval()));
		CollectionInfo info  = null;
		if(entry.getCollection()!=null && entry.getCollection().getId()!=null){
			info = loadCollectionById(entry.getCollection().getId().toString());
		}
		if(info!=null){
			row.getCell("collection").setValue(info);
		}
		
		setRowAppTimeEnable(row);
		if (row.getCell("moneyName").getValue() != null) {
			MoneyDefineInfo moneyType = (MoneyDefineInfo) row.getCell(
					"moneyName").getValue();
			if (moneyType.getMoneyType() == MoneyTypeEnum.ReplaceFee) {
				row.getCell("bizTime").getStyleAttributes().setLocked(true);
				row.getCell("month").getStyleAttributes().setLocked(true);
				row.getCell("day").getStyleAttributes().setLocked(true);
				row.getCell("appDate").getStyleAttributes().setLocked(true);
				row.getCell("currency").getStyleAttributes().setLocked(true);
				row.getCell("amount").getStyleAttributes().setLocked(true);
				row.getCell("pro").getStyleAttributes().setLocked(true);
				row.getCell("term").getStyleAttributes().setLocked(true);
				row.getCell("jiange").getStyleAttributes().setLocked(true);
				row.getCell("des").getStyleAttributes().setLocked(true);
			}
		}
	}
	
	private static CollectionInfo loadCollectionById(String number){
		CollectionInfo info  = null;
		
		try {
			info = CollectionFactory.getRemoteInstance().getCollectionInfo("select id,number where id='"+number+"'");
		} catch (EASBizException e) {
			logger.error(e.getMessage());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		
		return info;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		SHEPayTypeInfo type = (SHEPayTypeInfo) this.editData;
		type.setNumber(this.txtNumber.getText());
		type.setName(this.txtName.getText());
		type.setAgio(this.txtAgio.getBigDecimalValue());
		type.setDescription(this.txtDes.getText());

		/**
		 * 售楼优化添加代码 add by renliang at 2011-6-3
		 */
		type.setProject((SellProjectInfo) this.prtSellProject.getValue());
		if (this.prtAfmBank.getValue() != null) {
			type.setAcfBank((BankInfo) this.prtAfmBank.getValue());
		}
		if (this.prtLoanBank.getValue() != null) {
			type.setLoanBank((BankInfo) this.prtLoanBank.getValue());
		}
		if (this.prtLoanScheme.getValue() != null) {
			type.setLoanScheme((AFMortgagedInfo) this.prtLoanScheme.getValue());
		}
		if (this.dpValid.getValue() != null) {
			type.setValidDate((Date) this.dpValid.getValue());
		}
		if (this.kDDatePicker2.getValue() != null) {
			type.setInvalidDate((Date) this.kDDatePicker2.getValue());
		}
		if (this.prtAfmScheme.getValue() != null) {
			type.setAfScheme((AFMortgagedInfo) this.prtAfmScheme.getValue());
		}

		if (this.rbRoomYes.isSelected()) {
			type.setIsTotal(true);
		} else {
			type.setIsTotal(false);
		}

		if (this.rbYes.isSelected()) {
			type.setIsLoan(true);
		} else {
			type.setIsLoan(false);
		}
	
		type.setIsDelete(false);
		
		type.setNewLoanPre((LoanPreEnum)this.cbcLoanPre.getSelectedItem());
		type.setNewAfPre((AfPreEnum)this.cbcAFPre.getSelectedItem());
		
		PayListEntryCollection payLists = type.getPayLists();
		payLists.clear();
		for (int i = 0; i < tblPayList.getRowCount(); i++) {
			IRow row = tblPayList.getRow(i);
			PayListEntryInfo entry = (PayListEntryInfo) row.getUserObject();
			entry.setSeq(i);
			entry.setMoneyDefine((MoneyDefineInfo) row.getCell("moneyName")
					.getValue());
			// entry.setBizTime((SHEPayTypeBizTimeEnum)
			// row.getCell("bizTime").getValue());
			entry.setPayTypeBizTime((SHEPayTypeBizTimeEnum) row.getCell(
					"bizTime").getValue());
			if (row.getCell("month").getValue() != null) {
				entry.setMonthLimit(((Integer) row.getCell("month").getValue())
						.intValue());
			}
			if (row.getCell("day").getValue() != null) {
				entry.setDayLimit(((Integer) row.getCell("day").getValue())
						.intValue());
			}
			entry.setAppDate((Date) row.getCell("appDate").getValue());
			entry.setSigncontracttime((Date) row.getCell("signcontracttime").getValue()); //add bu shilei
			entry
					.setCurrency((CurrencyInfo) row.getCell("currency")
							.getValue());
			entry.setValue((BigDecimal) row.getCell("amount").getValue());
			entry.setProportion((BigDecimal) row.getCell("pro").getValue());
			if (row.getCell("term").getValue() != null) {
				entry.setTerm(((Integer) row.getCell("term").getValue())
						.intValue());
			}
			if (row.getCell("jiange").getValue() != null) {
				entry.setMonthInterval(((Integer) row.getCell("jiange")
						.getValue()).intValue());
			}
			// 添加代收费用公式定义 add by renliang at 2011-6-2
			if (row.getCell("collection").getValue() != null) {
				entry.setCollection((CollectionInfo) row.getCell("collection")
						.getValue());
			}else{
				entry.setCollection(null);
			}
			entry.setDescription((String) row.getCell("des").getValue());
			payLists.add(entry);

		}

		BizListEntryCollection bizLists = type.getBizLists();
		bizLists.clear();
		for (int i = 0; i < tblBizList.getRowCount(); i++) {
			IRow row = tblBizList.getRow(i);
			BizListEntryInfo entry = (BizListEntryInfo) row.getUserObject();
			entry.setSeq(i);
			// entry.setBizTime((BizTimeEnum)
			// row.getCell("bizTime").getValue());
			entry.setPayTypeBizTime((String) row.getCell("bizTime").getValue());
			// entry.setBizFlow((BizFlowEnum)
			// row.getCell("bizFlow").getValue());
			entry.setPayTypeBizFlow((BizNewFlowEnum) row.getCell("bizFlow")
					.getValue());
			if (row.getCell("month").getValue() != null) {
				entry.setMonthLimit(((Integer) row.getCell("month").getValue())
						.intValue());
			}
			if (row.getCell("day").getValue() != null) {
				entry.setDayLimit(((Integer) row.getCell("day").getValue())
						.intValue());
			}
			entry.setAppDate((Date) row.getCell("appDate").getValue());
			entry.setDescription((String) row.getCell("des").getValue());
			bizLists.add(entry);
		}
		super.storeFields();
	}

	protected IObjectValue createNewData() {
		SHEPayTypeInfo type = new SHEPayTypeInfo();
		type.setIsEnabled(true);
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		type.setProject(sellProject);
		type.setLoanCalculateType(LoanCalculateTypeEnum.DescendingAmount);
		type
				.setAccumulationCalculateType(LoanCalculateTypeEnum.DescendingAmount);
		type.setLoanPre(2);
		type.setAfPre(2);
		BizListEntryInfo bizEntry = new BizListEntryInfo();
		bizEntry.setSeq(0);
		bizEntry.setBizFlow(BizFlowEnum.Purchase);
		bizEntry.setBizTime(BizTimeEnum.Purchase);
		type.getBizLists().add(bizEntry);
		type.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return type;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SHEPayTypeFactory.getRemoteInstance();
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
		if (this.tabEntry.getSelectedIndex() == 0) {
			PayListEntryInfo entry = new PayListEntryInfo();
			entry.setTerm(1);
			CompanyOrgUnitInfo currentCompany = SysContext.getSysContext()
					.getCurrentFIUnit();
			CurrencyInfo baseCurrency = CurrencyFactory.getRemoteInstance()
					.getCurrencyInfo(
							new ObjectUuidPK(BOSUuid.read(currentCompany
									.getBaseCurrency().getId().toString())));
			entry.setBizTime(BizTimeEnum.Purchase);
			entry.setCurrency(baseCurrency);
			entry.setMonthLimit(0);
			entry.setDayLimit(0);
			entry.setMonthInterval(1);
			this.addPayEntryRow(entry);
		} else {
			BizListEntryInfo entry = new BizListEntryInfo();
			entry.setMonthLimit(0);
			entry.setDayLimit(0);
			this.addBizEntryRow(entry);
		}
		if(rbYes.isSelected()){
			initMoneyDefine(true);
		}else{
			initMoneyDefine(false);
		}
	}

	public void actionDeleteLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDeleteLine_actionPerformed(e);
		KDTable table = null;
		if(this.tabEntry.getSelectedComponent().getName().equals("tblPayList")){
			table = this.tblPayList;
		}else{
			table = this.tblBizList;
		}
		KDTSelectManager selectManager = table.getSelectManager();
		if (selectManager == null || selectManager.size() == 0) {
			return;
		}
		for (int i = 0; i < selectManager.size(); i++) {
			KDTSelectBlock selectBlock = selectManager.get(i);
			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow selectRow = table.getRow(j);
				selectRow.getCell(0).setUserObject("delete");
			}
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = (IRow) table.getRow(i);
			if (row.getCell(0).getUserObject() != null) {
				table.removeRow(row.getRowIndex());
				i--;
				table.setUserObject("delteRow");
			}
		}

	}

	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionInsertLine_actionPerformed(e);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("loanLoanData.*");
		sels.add("afLoanData.*");
		sels.add("project.id");
		sels.add("project.name");
		sels.add("project.number");
		sels.add("loanBank.id");
		sels.add("loanBank.name");
		sels.add("loanBank.number");
		sels.add("acfBank.id");
		sels.add("acfBank.name");
		sels.add("acfBank.number");
		sels.add("loanScheme.id");
		sels.add("loanScheme.name");
		sels.add("loanScheme.number");
		sels.add("agioScheme.id");
		sels.add("agioScheme.name");
		sels.add("agioScheme.number");
		sels.add("afScheme.id");
		sels.add("afScheme.name");
		sels.add("afScheme.number");
		sels.add("payLists.collection.id");
		sels.add("payLists.collection.number");
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

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		SHEPayTypeInfo type = (SHEPayTypeInfo) super.getValue(pk);
		EntityViewInfo view = new EntityViewInfo();
		view.getSorter().add(new SorterItemInfo("seq"));
		view.getSelector().add("*");
		view.getSelector().add("currency.*");
		view.getSelector().add("moneyDefine.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", type.getId().toString()));
		PayListEntryCollection payLists = PayListEntryFactory
				.getRemoteInstance().getPayListEntryCollection(view);
		type.getPayLists().clear();
		type.getPayLists().addCollection(payLists);

		view.getSelector().clear();
		view.getSelector().add("*");
		BizListEntryCollection bizLists = BizListEntryFactory
				.getRemoteInstance().getBizListEntryCollection(view);
		type.getBizLists().clear();
		type.getBizLists().addCollection(bizLists);
		return type;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.setOprtState("EDIT");
		this.prtSellProject.setEditable(false);
		this.prtSellProject.setEnabled(false);
		this.actionAddLine.setEnabled(true);
		this.actionDeleteLine.setEnabled(true);
		if (this.editData.getId() != null) {
			if (SHEPayTypeFactory.getRemoteInstance().exists(
					"where isEnabled =1 and id='"
							+ this.editData.getId().toString() + "'")) {
				MsgBox.showInfo("该条记录已启用，不允许修改！");
				this.actionAddLine.setEnabled(false);
				this.actionDeleteLine.setEnabled(false);
				return;
			}
		}
		super.actionEdit_actionPerformed(e);
		String payTypeId = this.editData.getId().toString();
		List list = new ArrayList();
		list.add(payTypeId);
		if (hasRelatedByFDCCustomer("payType.id", list)) {
			this.txtNumber.setEditable(false);
		}
		this.rbYes.setEnabled(true);
		this.rbNo.setEnabled(true);
		this.rbRoomYes.setEnabled(true);
		this.rbRoomNo.setEnabled(true);
		setControl();
	}

	private static boolean hasRelatedByFDCCustomer(String key, List selectedIds)
			throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo(key, FDCHelper.getSetByList(selectedIds),
						CompareType.INCLUDE));
		return PurchaseFactory.getRemoteInstance().exists(filter);
	}

	protected void tblPayList_editStopped(KDTEditEvent e) throws Exception {
		super.tblPayList_editStopped(e);
		IRow row = this.tblPayList.getRow(e.getRowIndex());
		if (e.getColIndex() == this.tblPayList.getColumnIndex("bizTime")) {
			setRowAppTimeEnable(row);
		} else if (e.getColIndex() == this.tblPayList.getColumnIndex("amount")) {
			if (row.getCell("amount").getValue() != null) {
				row.getCell("pro").setValue(null);
			}
		} else if (e.getColIndex() == this.tblPayList.getColumnIndex("pro")) {
			if (row.getCell("pro").getValue() != null) {
				row.getCell("amount").setValue(null);
			}
		} else if (e.getColIndex() == this.tblPayList
				.getColumnIndex("moneyName")) {
			if (row.getCell("moneyName").getValue() != null) {
				MoneyDefineInfo moneyType = (MoneyDefineInfo) row.getCell(
						"moneyName").getValue();
				if (moneyType.getMoneyType() == MoneyTypeEnum.ReplaceFee) {
					row.getCell("collection").getStyleAttributes().setLocked(false);
				}else{
					row.getCell("collection").setValue(null);
					row.getCell("collection").getStyleAttributes().setLocked(true);
				}

			}
		}
	}

	private void setRowAppTimeEnable(IRow row) {
		SHEPayTypeBizTimeEnum bizTime = (SHEPayTypeBizTimeEnum) row.getCell(
				"bizTime").getValue();
		if (bizTime == null || "".equals(bizTime)) {
			row.getCell("month").getStyleAttributes().setLocked(true);
			row.getCell("day").getStyleAttributes().setLocked(true);
			row.getCell("day").getStyleAttributes().setLocked(true);
			return;
		}
		if (bizTime.equals(SHEPayTypeBizTimeEnum.APPTIME)) {
			row.getCell("month").setValue(null);
			row.getCell("day").setValue(null);
			row.getCell("month").getStyleAttributes().setLocked(true);
			row.getCell("day").getStyleAttributes().setLocked(true);
			row.getCell("appDate").getStyleAttributes().setLocked(false);
		} else {
			row.getCell("appDate").setValue(null);
			row.getCell("month").getStyleAttributes().setLocked(false);
			row.getCell("day").getStyleAttributes().setLocked(false);
			row.getCell("appDate").getStyleAttributes().setLocked(true);
		}
	}

	private void setRowAppTimeEnableForBiz(IRow row) {
		String bizTime = null;
		bizTime = (String) row.getCell("bizTime").getValue();
		if (bizTime == null || "".equals(bizTime)) {
			row.getCell("month").getStyleAttributes().setLocked(true);
			row.getCell("day").getStyleAttributes().setLocked(true);
			row.getCell("day").getStyleAttributes().setLocked(true);
			return;
		}
		if (bizTime.equals("指定日期")) {
			row.getCell("month").setValue(null);
			row.getCell("day").setValue(null);
			row.getCell("month").getStyleAttributes().setLocked(true);
			row.getCell("day").getStyleAttributes().setLocked(true);
			row.getCell("appDate").getStyleAttributes().setLocked(false);
		} else {
			row.getCell("appDate").setValue(null);
			row.getCell("month").getStyleAttributes().setLocked(false);
			row.getCell("day").getStyleAttributes().setLocked(false);
			row.getCell("appDate").getStyleAttributes().setLocked(true);
		}
	}

	protected void tblPayList_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblPayList_tableClicked(e);
		if (e.getButton() == 1 && e.getClickCount() == 1) {
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable()
				&& StringUtil.isEmptyString(this.txtNumber.getText())) {
			MsgBox.showInfo("编码必须录入!");
			this.abort();
		}
		if (StringUtil.isEmptyString(this.txtName.getText())) {
			MsgBox.showInfo("名称必须录入!");
			this.abort();
		}
		vitfyAgio(this.txtAgio);
		FDCClientVerifyHelper.verifyEmpty(this, dpValid);
		
		if(this.kDDatePicker2
				.getValue()!=null){
			Date valid = DateTimeUtils.truncateDate((Date) this.dpValid.getValue());
			Date inValid = DateTimeUtils.truncateDate((Date) this.kDDatePicker2
					.getValue());
			if (valid.compareTo(inValid) > 0) {
				FDCMsgBox.showWarning(this, "失效日期必须大于等于生效日期!");
				SysUtil.abort();
			}
		}
	
		int rowCount = this.tblPayList.getRowCount();
		if (rowCount == 0) {
			MsgBox.showInfo("付款明细页签不能为空!");
			this.abort();
		}
		verifyPayLists();
		verifyBizLists();
		this.setOprtState("EDIT");
		super.actionSubmit_actionPerformed(e);
		
		if(!OprtState.EDIT.equals(this.getOprtState())){
			this.rbYes.setSelected(true);
			this.rbRoomYes.setSelected(true);
			this.txtAgio.setValue(FDCHelper.ONE_HUNDRED);
			this.kDContainer1.setVisible(true);
//			this.tabEntry.setBounds(24, 404, 617, 192);
//			this.kDContainer1.setBounds(23, 278, 618, 114);
//			if (SwingUtilities.getWindowAncestor(this) != null) {
//				SwingUtilities.getWindowAncestor(this)
//						.setSize(665, height + 180);
//			}
			isFirstLoad = true;
			this.dpValid.setValue(new Timestamp(System.currentTimeMillis()));
			this.cbcLoanPre.setSelectedItem(LoanPreEnum.HUNDRED);
			this.cbcAFPre.setSelectedItem(AfPreEnum.HUNDRED);
		}
		
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			this.dpValid.setValue(new Timestamp(System.currentTimeMillis()));
		}
	}
	

	private void verifyBizLists() {
		if (this.tblBizList.getRowCount() == 0) {
			//MsgBox.showInfo("业务明细必须录入!");
			//this.abort();
		}
		for (int i = 0; i < this.tblBizList.getRowCount(); i++) {
			IRow row = this.tblBizList.getRow(i);
			String des = "业务明细第" + (i + 1) + "行";
				BizNewFlowEnum bizFlow = (BizNewFlowEnum) row.getCell("bizFlow")
					.getValue();
			if (bizFlow == null) {
				MsgBox.showInfo(des + "业务名称必须录入!");
				this.abort();
			} 
			if (row.getCell("bizTime").getValue() == null) {
				MsgBox.showInfo(des + "参照时间必须录入!");
				this.abort();
			} else {
				
				String bizTime = (String) row.getCell("bizTime").getValue();
				if (bizTime.equals("指定日期")) {
					if (row.getCell("appDate").getValue() == null) {
						MsgBox.showInfo(des + "选择了指定日期,指定日期就必须录入!");
						this.abort();
					}
				} else {
					if (row.getCell("month").getValue() == null
							&& row.getCell("day").getValue() == null) {
						MsgBox.showInfo(des + "时间必须录入!");
						this.abort();
					}
					if(row.getCell("month").getValue()!=null){
						int month = Integer.parseInt(row.getCell("month").getValue().toString());
						if(month<0){
							FDCMsgBox.showWarning(this,"业务明细中间隔月大于等于0!");
							this.abort();
						}
					}
					if(row.getCell("day").getValue()!=null){
						int day = Integer.parseInt(row.getCell("day").getValue().toString());
						if(day<0){
							FDCMsgBox.showWarning(this,"业务明细中间隔日大于等于0!");
							this.abort();
						}
					}
				}
			}
			
			
		}
	}

	private void clearLoan(){
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			if (row.getCell("moneyName").getValue() != null) {
				MoneyDefineInfo moneyType = (MoneyDefineInfo) row.getCell(
						"moneyName").getValue();
				if (moneyType.getMoneyType() == MoneyTypeEnum.LoanAmount
						|| moneyType.getMoneyType() == MoneyTypeEnum.AccFundAmount) {
					row.getCell("moneyName").setValue(null);
				}
			}
		}
	}
	private void verifyPayLists() {
		Map dayMap = new HashMap();
		// dayMap =null;
		BigDecimal percentAmount = null;
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			if (row.getCell("moneyName").getValue() != null) {
				//MoneyDefineInfo moneyType = (MoneyDefineInfo) row.getCell(
						//"moneyName").getValue();
				//if (moneyType.getMoneyType() == MoneyTypeEnum.ReplaceFee) {
					//continue;
				//}
			}
			String des = "付款明细,第" + (i + 1) + "行";
			MoneyDefineInfo moneyDefine = (MoneyDefineInfo) row.getCell(
					"moneyName").getValue();
			if (moneyDefine == null) {
				MsgBox.showInfo(des + "款项名称必须录入!");
				this.abort();
			}
			if (row.getCell("bizTime").getValue() == null) {
				MsgBox.showInfo(des + "参照时间必须录入!");
				this.abort();
			} else {
				// BizTimeEnum bizTime = (BizTimeEnum) row.getCell("bizTime")
				// .getValue();
				SHEPayTypeBizTimeEnum bizTime = (SHEPayTypeBizTimeEnum) row
						.getCell("bizTime").getValue();
				
				if (bizTime.equals(SHEPayTypeBizTimeEnum.APPTIME)) {
					Date appDate = (Date) row.getCell("appDate").getValue();
					if (appDate == null) {
						MsgBox.showInfo(des + "选择了指定时间,指定时间就必须录入!");
						this.abort();
					}
					Date date = (Date) dayMap.get(bizTime);
					if (date == null) {
						dayMap.put(bizTime, appDate);
					} else {
						if (appDate.before(date)) {
							MsgBox.showInfo(des + "指定时间必须不小于前面行的指定日期!");
							this.abort();
						} else {
							dayMap.put(bizTime, appDate);
						}
					}
				} else if (bizTime.equals(SHEPayTypeBizTimeEnum.PURCHASE)) {
					int totalDay = 0;
					Object month = row.getCell("month").getValue();
					Object day = row.getCell("day").getValue();
					if (month == null && day == null) {
						MsgBox.showInfo(des + "时间必须录入!");
						this.abort();
					}
					if (month == null) {
						if (day != null) {
							totalDay += ((Integer) day).intValue();
						}
					} else {
						totalDay += ((Integer) month).intValue() * 30;
						if (day != null) {
							totalDay += ((Integer) day).intValue();
						}
					}
					Integer dayCount = (Integer) dayMap.get(bizTime);
					if (dayCount == null) {
						dayMap.put(bizTime, new Integer(totalDay));
					} else {
						if (totalDay < dayCount.intValue()) {
							MsgBox.showInfo(des + "通过认购日期设置期限,不能小于前面行的相同设置!");
							this.abort();
						} else {
							dayMap.put(bizTime, new Integer(totalDay));
						}
					}
				}
			}
			if(row.getCell("month").getValue()!=null){
				int month = Integer.parseInt(row.getCell("month").getValue().toString());
				if(month<0){
					FDCMsgBox.showWarning(this,"付款明细中间隔月必须大于等于0!");
					this.abort();
				}
			}
			if(row.getCell("day").getValue()!=null){
				int day = Integer.parseInt(row.getCell("day").getValue().toString());
				if(day<0){
					FDCMsgBox.showWarning(this,"付款明细中间隔日大于等于0!");
					this.abort();
				}
			}
			if (row.getCell("currency").getValue() == null) {
				MsgBox.showInfo(des + "币别必须录入!");
				this.abort();
			}
			if (moneyDefine.getMoneyType() != MoneyTypeEnum.ReplaceFee) {
				if (row.getCell("pro").getValue() == null) {
					if (row.getCell("amount").getValue() == null) {
						MsgBox.showInfo(des + "金额或比例必须录入!");
						this.abort();
					}
				}
			}
			if (row.getCell("pro").getValue() != null) {
				
				MoneyDefineInfo moneyType = (MoneyDefineInfo) row.getCell(
						"moneyName").getValue();
				if (moneyType.getMoneyType().equals(MoneyTypeEnum.EarnestMoney)
						|| moneyType.getMoneyType().equals(
								MoneyTypeEnum.FisrtAmount)
						|| moneyType.getMoneyType().equals(
								MoneyTypeEnum.HouseAmount)
						|| moneyType.getMoneyType().equals(
								MoneyTypeEnum.LoanAmount)
						|| moneyType.getMoneyType().equals(
								MoneyTypeEnum.AccFundAmount)) {
					BigDecimal proAmount = (BigDecimal) row.getCell("pro")
							.getValue();
					if (percentAmount == null) {
						percentAmount = proAmount;
					} else {
						percentAmount = percentAmount.add(proAmount);
					}

				}
			}

			if (row.getCell("term").getValue() == null) {
				MsgBox.showInfo(des + "分期必须录入,不分期请录入!");
				this.abort();
			} else {
				Integer term = (Integer) row.getCell("term").getValue();
				if (term.intValue() > 1) {
					if (row.getCell("jiange").getValue() == null) {
						MsgBox.showInfo(des + "分多期,间隔名称必须录入!");
						this.abort();
					}
				}
			}
		}

		if (percentAmount != null
				&& percentAmount.compareTo(new BigDecimal("100")) == 1) {
			MsgBox.showInfo("房款类款项比例和不允许大于100%，请检查！");
			this.abort();
		}
	}

	protected void tblBizList_editStopped(KDTEditEvent e) throws Exception {
		super.tblBizList_editStopped(e);
		IRow row = this.tblBizList.getRow(e.getRowIndex());
		if (e.getColIndex() == this.tblBizList.getColumnIndex("bizTime")) {
			setRowAppTimeEnableForBiz(row);
		}
	}

	// 屏蔽掉退出时的提示 xiaoao_liu
	protected void initOldData(IObjectValue dataObject) {
		// super.initOldData(dataObject);
	}

	/**
	 * 初始化按揭方案
	 */
	private void initLoanScheme() {
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		if(this.prtSellProject.getValue()!=null){
			SellProjectInfo sellProject = (SellProjectInfo)this.prtSellProject.getValue();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("project.id", sellProject.getId().toString(),
							CompareType.EQUALS));
		}else{
			SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
			.get("sellProject");
			filterInfo.getFilterItems().add(
					new FilterItemInfo("project.id", sellProject.getId().toString(),
							CompareType.EQUALS));
		}
		String queryInfo = "com.kingdee.eas.fdc.sellhouse.app.LoanForF7Query";
		SHEHelper.initF7(this.prtLoanScheme, queryInfo, filterInfo);
	}

	/**
	 * 初始化公积金方案
	 */
	private void initAFMScheme() {
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		if(this.prtSellProject.getValue()!=null){
			SellProjectInfo sellProject = (SellProjectInfo)this.prtSellProject.getValue();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("project.id", sellProject.getId().toString(),
							CompareType.EQUALS));
		}else{
			SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
			.get("sellProject");
			filterInfo.getFilterItems().add(
					new FilterItemInfo("project.id", sellProject.getId().toString(),
							CompareType.EQUALS));
		}
		String queryInfo = "com.kingdee.eas.fdc.sellhouse.app.AFMForF7Query";
		SHEHelper.initF7(this.prtAfmScheme, queryInfo, filterInfo);
	}

	protected void rbNo_itemStateChanged(ItemEvent e) throws Exception {
		super.rbNo_itemStateChanged(e);
		if (this.isFirstLoad && e.getStateChange() == 1) {
//			this.kDContainer1.setBounds(25, 600, 618, 114);
//			this.tabEntry.setBounds(24, 294, 618, 192);
//			SwingUtilities.getWindowAncestor(this).setSize(665, height+60);
			this.kDContainer1.setVisible(false);
			isFirstLoad = false;
			initMoneyDefine(false);
			clearLoan();
		}
	}

	public void rbYes_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {
		super.rbYes_itemStateChanged(e);
		if (!this.isFirstLoad && e.getStateChange() == 1) {
//			this.tabEntry.setBounds(24, 404, 617, 192);
//			this.kDContainer1.setBounds(23, 278, 618, 114);
//			if (SwingUtilities.getWindowAncestor(this) != null) {
//				SwingUtilities.getWindowAncestor(this)
//						.setSize(665, height + 180);
//			}
			this.kDContainer1.setVisible(true);
			isFirstLoad = true;
			initMoneyDefine(true);
		}
	}
}