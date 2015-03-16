/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTCell;
import com.kingdee.bos.ctrl.kdf.table.KDTRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.HeadColumnCollection;
import com.kingdee.eas.fdc.invite.HeadColumnFactory;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.ListingItemFactory;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.fdc.invite.NewListTempletEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingColumnInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.fdc.invite.RefPriceCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryFactory;
import com.kingdee.eas.fdc.invite.RefPriceEntryInfo;
import com.kingdee.eas.fdc.invite.RefPriceFactory;
import com.kingdee.eas.fdc.invite.RefPriceInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 *
 * @author 肖飙彪_金蝶深圳分公司
 *
 */
/**
 * output class name
 */
public class ListingItemEditUI extends AbstractListingItemEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ListingItemEditUI.class);

	public CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();
	
	private Set deleteRefPriceIDs = new HashSet();
	private Map refPriceIDMap = new HashMap();

	/**
	 * output class constructor
	 */
	public ListingItemEditUI() throws Exception {
		super();
	}
    
    /**
     * output actionDeleteEntry_actionPerformed method
     */
    public void actionDeleteEntry_actionPerformed(ActionEvent e) throws Exception
    {
    	removeLineOver(this.kdPrice);
    }

	protected void removeLineOver(KDTable table) {
		if (table == null) {
			return;
		}

		if ((table.getSelectManager().size() == 0)
				|| isTableColumnSelected(table)) {
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_NoneEntry"));

			return;
		}

		// [begin]进行删除分录的提示处理。

		// 获取选择块的总个数
		KDTSelectManager selectManager = table.getSelectManager();
		int size = selectManager.size();
		KDTSelectBlock selectBlock = null;
		// 因为先择块的顺序可能并非是表中行的顺序，所以先要排序使选择块的顺序正好是由小到大
		List indexList = new ArrayList();

		for (int blockIndex = 0; blockIndex < size; blockIndex++) {
			selectBlock = selectManager.get(blockIndex);
			int top = selectBlock.getBeginRow();
			int bottom = selectBlock.getEndRow();
			if (table.getRow(top) == null) {
				MsgBox.showInfo(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Msg_NoneEntry"));
				return;
			}
			for (int i = top; i <= bottom; i++) {
				indexList.add(new Integer(i));
			}
		}
		Integer[] indexArr = new Integer[indexList.size()];
		Object[] indexObj = indexList.toArray();
		System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
		Arrays.sort(indexArr);
//		if (indexArr == null)
//			return;
		
		//this.deleteRefPriceIDs.clear();
		HashMap deleteIds = new HashMap();
		boolean isHavCannotDelete = false;
		for (int i = indexArr.length - 1; i >= 0; i--) {
			int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
			if(!table.getCell(rowIndex,1).getValue().equals("外界导入"))
			{	
				isHavCannotDelete = true;
				continue;
			}
			if((table.getRow(rowIndex).getUserObject()) != null
					&&table.getRow(rowIndex).getUserObject() instanceof RefPriceInfo){
				
				this.deleteRefPriceIDs.add(((RefPriceInfo)table.getRow(rowIndex).getUserObject()).getId().toString());
				table.removeRow(rowIndex);
			}
			else{
				MsgBox.showInfo(this,"价格不存在,删除子目有错!");
				return;
			}
		}
//		if(deleteIds.size()>0){
//			this.deleteRefPriceIDs.clear();
//			this.deleteRefPriceIDs = deleteIds.keySet();
//		}

		// 如果现在有记录定位到第一行
		if (table.getRow(0) != null)
			table.getSelectManager().select(0, 0);
		
		if (isHavCannotDelete){
			String showString = "";
			if (indexArr.length>1){
				showString = "您选择的行中有中标反写的价格，这些价格不能删除";
			}
			else{
				showString = "您选择的行是中标反写的价格，这些价格不能删除";
			}
			MsgBox.showInfo(this,showString);
		}

	}
	/**
	 * 
	 * @param collection
	 * @param obj
	 * @return
	 * @deprecated
	 */
	private int getCollectionIndex(IObjectCollection collection,
			IObjectValue obj) {
		int index = -1;
		if (collection == null) {
			return index;
		}
		for (int i = collection.size() - 1; i >= 0; i--) {
			if (obj == collection.getObject(i)) {
				index = i;
				return index;
			}
		}
		return index;
	}
	
	//	显示分录附件
    protected void showSubAttacheMent(AttachmentUIContextInfo info){
    	return;
    }

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(!deleteEntries()){
			MsgBox.showInfo(this,"删除子目价格出错，保存失败！");
			SysUtil.abort();
		}
		this.storeFields();
		super.actionSubmit_actionPerformed(e);
		this.initOldData(this.editData);
		this.deleteRefPriceIDs.clear();
		initUserConfig();
		CacheServiceFactory.getInstance().discardType(new ListingItemInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new RefPriceInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new RefPriceEntryInfo().getBOSType());
	}
	
	public boolean deleteEntries(){
		try{
			if(this.deleteRefPriceIDs.size()>0){
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(new FilterItemInfo("id",this.deleteRefPriceIDs,CompareType.INCLUDE));
				RefPriceFactory.getRemoteInstance().delete(filterInfo);
				
				filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(new FilterItemInfo("head.id",this.deleteRefPriceIDs,CompareType.INCLUDE));
				RefPriceEntryFactory.getRemoteInstance().delete(filterInfo);
				
			}
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	protected IObjectValue createNewData() {
		ListingItemInfo info = new ListingItemInfo();
		Map context = this.getUIContext();
		Object oType = context.get("type");
		if (oType instanceof HeadTypeInfo) {
			if (((HeadTypeInfo) oType).isIsLeaf())
				info.setHeadType((HeadTypeInfo) oType);
		}
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		info.setCreateTime(new Timestamp(FDCHelper.getCurrentDate().getTime()));
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ListingItemFactory.getRemoteInstance();
	}

	public void loadFields() {
		// 查看或修改时，F7HeadType可能是enable=false的，所以要将其先还原
		if (!F7HeadType.isEnabled()) {
			F7HeadType.setEnabled(true);
		}
		if (getOprtState().equals(STATUS_ADDNEW)) {
			if (((ListingItemInfo) getEditData()).getHeadType() != null) {
				F7HeadType.setValue(((ListingItemInfo) getEditData())
						.getHeadType());
			}
		}
		super.loadFields();
		
		
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext()
				.getCurrentCtrlUnit().getId().toString())) {
			actionAddNew.setEnabled(true);
			if (this.getOprtState().equals("VIEW")) {
				actionEdit.setEnabled(true);
			}
			actionRemove.setEnabled(true);
		} else {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}		
//		
//		if (this.editData.getOrgUnit() != null
//				&& !currentOrg.getId().toString().equals(
//						this.editData.getOrgUnit().getId().toString())) {
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//		}
		try {
			this.fillHead();
			this.fillData();
			setIsUsedEnable();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		
	}

	private void fillData() throws BOSException {
		this.kdPrice.removeRows();
		HeadTypeInfo headType = (HeadTypeInfo) this.F7HeadType.getValue();
		if (headType == null || this.editData.getId() == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("*");
		view.getSelector().add("item.*");
		view.getSelector().add("item.creator.*");
		view.getSelector().add("quotingContent.supplier.*");
		view.getSelector().add("listing.name");
		view.getSelector().add("entrys.*");
		view.getSelector().add("entrys.column.*");
		view.getSorter().add(new SorterItemInfo("item"));
		view.getSorter().add(new SorterItemInfo("date"));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("item.id", this.editData.getId()
								.toString()));
		view.setFilter(filter);
		RefPriceCollection refPrices = RefPriceFactory.getRemoteInstance()
				.getRefPriceCollection(view);
		this.refPriceIDMap.clear();
		for (int i = 0; i < refPrices.size(); i++) {
			IRow row = kdPrice.addRow();
			RefPriceInfo price = refPrices.get(i);
			row.setUserObject(price);
			this.refPriceIDMap.put(new Integer(i),price.getId().toString());
			row.getCell("日期").setValue(price.getDate());
			if (price.getQuotingContent() != null) {
				row.getCell("报价单位").setValue(
						price.getQuotingContent().getSupplier().getName());
			} else {
				row.getCell("报价单位").setValue("外界导入");
			}
			if (price.getListing() != null) {
				row.getCell("清单名称").setValue(price.getListing().getName());
			}
			for (int k = 0; k < price.getEntrys().size(); k++) {
				RefPriceEntryInfo entry = price.getEntrys().get(k);
				HeadColumnInfo headColumn = entry.getColumn();
				String colName = headColumn.getName();
				String colKey  = headColumn.getId().toString();
				
				if (//colName.equals("子目编码") ||
						colName.equals("子目名称")
						|| colName.equals("单位")) {
					continue;
				}
				if (!(headColumn.getProperty().equals(
						DescriptionEnum.TotalPrice)
						|| headColumn.getProperty().equals(
								DescriptionEnum.TotalPriceSum) || headColumn
						.getProperty().equals(DescriptionEnum.Personal))) {
					continue;
				}
				IColumn column = kdPrice.getColumn(colKey);
				if (column != null) {
					HeadColumnInfo colInfo = (HeadColumnInfo) column
							.getUserObject();
					if(colInfo != null){
						ColumnTypeEnum colType = colInfo.getColumnType();
						InviteHelper.loadRefPriceEntry(row.getCell(colKey), entry, colType);
					}
//					if (colType.equals(ColumnTypeEnum.Amount)) {
//						row.getCell(colName).setValue(entry.getValue());
//					} else if (colType.equals(ColumnTypeEnum.Date)) {
//						row.getCell(colName).setValue(entry.getDateValue());
//					} else {
//						row.getCell(colName).setValue(entry.getText());
//					}
				}
			}
		}
	}

	private void fillHead() throws BOSException {
		this.kdPrice.removeColumns();
		HeadTypeInfo headType = (HeadTypeInfo) this.F7HeadType.getValue();
		if (headType == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("parent.*");
		view.getSorter().add(new SorterItemInfo("longNumber"));
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("headType.id", headType.getId().toString()));
		HeadColumnCollection entrys = HeadColumnFactory.getRemoteInstance()
				.getHeadColumnCollection(view);
		headType.getEntries().clear();
		headType.getEntries().addCollection(entrys);
		//目前对于删除只允许单行删除
		//如果需要允许多行删除，直接将此行改为多行选择即可
		kdPrice.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		kdPrice.getStyleAttributes().setLocked(true);
		IRow headRow0 = kdPrice.addHeadRow();
		IRow headRow1 = kdPrice.addHeadRow();
		IColumn column = kdPrice.addColumn();
		
		column.setKey("日期");
		headRow0.getCell("日期").setValue("日期");
		column = kdPrice.addColumn();
		column.setKey("报价单位");
		headRow0.getCell("报价单位").setValue("报价单位");
		column = kdPrice.addColumn();
		column.setKey("清单名称");
		headRow0.getCell("清单名称").setValue("清单名称");
		kdPrice.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		kdPrice.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		kdPrice.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		kdPrice.getHeadMergeManager().mergeBlock(0, 3, 1, 3);
		HeadColumnCollection cols = headType.getEntries();
		HeadColumnCollection refCols = new HeadColumnCollection();
		for (int i = 0; i < cols.size(); i++) {
			HeadColumnInfo info = cols.get(i);
			if (info.isIsLeaf()
					&& !info.getProperty().equals(DescriptionEnum.System)
					&& !info.getProperty().equals(DescriptionEnum.ProjectAmt)
					&& !info.getProperty()
							.equals(DescriptionEnum.ProjectAmtSum)
					&& !info.getProperty().equals(DescriptionEnum.Amount)
					&& !info.getProperty().equals(DescriptionEnum.AmountSum)) {
				column = this.kdPrice.addColumn();
				column.setUserObject(info);
				column.setKey(info.getId().toString());
				ColumnTypeEnum colType = info.getColumnType();
				if (colType.equals(ColumnTypeEnum.Amount)) {
					column.getStyleAttributes().setHorizontalAlign(
							HorizontalAlignment.RIGHT);
					column.getStyleAttributes().setNumberFormat(
							FDCHelper.getNumberFtm(2));
				} else if (colType.equals(ColumnTypeEnum.Date)) {
					column.getStyleAttributes().setNumberFormat(
							"yyyy-MM-dd");
				}
				refCols.add(info);
			}
		}
		int count = 0;
		int baseCount = 3;
		for (int j = 0; j < refCols.size(); j++) {
			HeadColumnInfo infoColumn = refCols.get(j);
			headRow1.getCell(j + baseCount).setValue(infoColumn.getName());
			if (infoColumn.getParent() != null) {
				headRow0.getCell(j + baseCount).setValue(
						infoColumn.getParent().getName());
				if (j < refCols.size() - 1) {
					HeadColumnInfo infoNext = refCols.get(j + 1);
					if (infoNext.getParent() != null
							&& infoColumn.getParent().equals(
									infoNext.getParent())) {
						count++;
					} else {
						kdPrice.getHeadMergeManager().mergeBlock(0,
								j - count + baseCount, 0, j + baseCount);
						count = 0;
					}
				} else {
					kdPrice.getHeadMergeManager().mergeBlock(0,
							j - count + baseCount, 0, j + baseCount);
					count = 0;
				}
			} else {
				kdPrice.getHeadMergeManager().mergeBlock(0, j + baseCount, 1,
						j + baseCount);
			}
		}
		tHelper.setCanMoveColumn(false);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("creator.*");
		return sels;
	}

	protected void initOldData(IObjectValue dataObject) {
		super.initOldData(dataObject);
		this.txtNumber.requestFocus();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setBtnStatus();

		// 初始化招标类型F7
		this.F7HeadType.setSelector(new HeadTypePromptSelector(this));
		this.btnSubmit.setText(btnSave.getText());//"保存");
		this.btnSubmit.setToolTipText(btnSave.getToolTipText());//"保存");
		this.txtNumber.setMaxLength(300);
		this.txtName.setMaxLength(300);
		this.txtDescription.setMaxLength(255);
		this.actionViewDoProccess.setVisible(false);
		setIsUsedEnable();
	}
	private void setIsUsedEnable() throws EASBizException, BOSException{
		if (this.editData.getId() != null && InviteHelper.validateItemHasUsed(this,this.editData.getId().toString()))
		{
			this.txtName.setEnabled(false);
			this.txtDescription.setEnabled(false);
			this.textUnit.setEnabled(false);			
			this.F7HeadType.setEnabled(false);
		}
	}

	/**
	 *
	 * 描述：表头类型F7的过滤条件
	 *
	 * @return
	 * @author:肖飙彪 创建时间：2007-5-10
	 *             <p>
	 */
	protected EntityViewInfo getViewForHeadTypeF7() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		return view;
	}

	private void setBtnStatus() {
		this.btnAuditResult.setVisible(false);
		this.btnMultiapprove.setVisible(false);
		this.btnNextPerson.setVisible(false);
		actionAudit.setVisible(false);
		actionUnAudit.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnTraceDown.setVisible(false);
		this.btnTraceUp.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.btnCreateFrom.setVisible(false);
		this.btnSave.setVisible(false);
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.menuItemSave.setVisible(false);
		this.menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
	}
		
	public boolean checkBeforeWindowClosing(){
		if(this.deleteRefPriceIDs.size()>0)
		{
			if(MsgBox.CANCEL == MsgBox.showConfirm2(this,"您已经删除了子目价格，直接退出将不保存此次的删除操作，确定退出吗？"))
			{
				return false;
			}
		}
		return super.checkBeforeWindowClosing();
	}

	protected void verifyInput(ActionEvent e) throws Exception {

//		// 编号是否为空
//		if (this.txtNumber.getText() == null
//				|| this.txtNumber.getText().trim().equals("")) {
//
//			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
//		}

		// 名称是否为空
		if (StringUtils.isEmpty(txtName.getText())) {
			txtName.requestFocus();
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
		// 单位是否为空
		if (StringUtils.isEmpty(textUnit.getText())) {
			textUnit.requestFocus();
			throw new FDCInviteException(FDCInviteException.LISTITEM_UNIT_NULL);
		}

		// 表头类型是否为空
		if (StringUtils.isEmpty(F7HeadType.getText())) {
			F7HeadType.requestFocus();
			throw new FDCInviteException(FDCInviteException.HEADTYPE_NULL);
		}
	}
	/**
	 * 提交成功的显示接口
	 */
	// 不知道什么原因老是提示提交成功("本来是显示保存成功"),重载一下,什么代码也没改,就好了,郁闷
	protected void showSubmitSuccess() {
		// 2004-09-21 by Jerry
		// showSuccessMessage(FrameWorkClientUtils.strResource + "Msg_Save_OK");
		setMessageText(getClassAlise()
				+ " "
				+ EASResource.getString(FrameWorkClientUtils.strResource
						+ "Msg_Save_OK"));
		if (this.chkMenuItemSubmitAndAddNew.isSelected()) {
			setNextMessageText(getClassAlise()
					+ " "
					+ EASResource.getString(FrameWorkClientUtils.strResource
							+ "Msg_AddNew"));
		} else if (!this.chkMenuItemSubmitAndPrint.isSelected()
				&& this.chkMenuItemSubmitAndAddNew.isSelected()) {
			setNextMessageText(getClassAlise()
					+ " "
					+ EASResource.getString(FrameWorkClientUtils.strResource
							+ "Msg_AddNew"));
		} else {
			setNextMessageText(getClassAlise()
					+ " "
					+ EASResource.getString(FrameWorkClientUtils.strResource
							+ "Msg_Edit"));
		}
		setIsShowTextOnly(false);
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		showMessage();
	}

	static class TableCellComparator implements Comparator, Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1443884270536100248L;

		int colIndex = 0;

		int sortType;

		public TableCellComparator(int i, int sortType) {
			colIndex = i;
			this.sortType = sortType;
		}

		public int compare(Object kdtRow1, Object kdtRow2) {
			int result = doCompare(kdtRow1, kdtRow2);
			if (sortType == ISortManager.SORT_ASCEND) {
				return result;
			} else {
				if (result < 0) {
					return 1;
				} else if (result > 0) {
					return -1;
				} else {
					return result;
				}
			}
		}

		public int doCompare(Object kdtRow1, Object kdtRow2) {
			Object o1 = null, o2 = null;
			KDTCell cell = ((KDTRow) kdtRow1).getCell(colIndex);
			if (cell != null) {
				o1 = cell.getValue();
			}
			cell = ((KDTRow) kdtRow2).getCell(colIndex);
			if (cell != null) {
				o2 = cell.getValue();
			}
			if (o1 == o2 || (o1 == null && o2 == null)) {
				return 0;
			} else if (o1 == null && o2 != null) {
				return -1;
			} else if (o1 != null && o2 == null) {
				return 1;
			} else {
				if (o1 instanceof Number) {
					if (o2 instanceof Number) {
						double d1 = ((Number) o1).doubleValue();
						double d2 = ((Number) o2).doubleValue();
						if (d1 == d2) {
							return 0;
						} else if (d1 < d2) {
							return -1;
						} else {
							return 1;
						}
					} else {
						return o1.toString().compareTo(o2.toString());
					}
				} else if (o1 instanceof String) {
					if (o2 instanceof String) {
						return ((String) o1).compareTo((String) o2);
					} else {
						return ((String) o1).compareTo(o2.toString());
					}
				} else if (o1 instanceof Date) {
					if (o2 instanceof Date) {
						return ((Date) o1).compareTo((Date) o2);
					} else {
						return o1.toString().compareTo(o2.toString());
					}
				} else if (o1 instanceof Calendar) {
					if (o2 instanceof Calendar) {
						long d1 = ((Calendar) o1).getTimeInMillis();
						long d2 = ((Calendar) o2).getTimeInMillis();
						if (d1 == d2) {
							return 0;
						} else if (d1 < d2) {
							return -1;
						} else {
							return 1;
						}
					} else {
						return o1.toString().compareTo(o2.toString());
					}
				} else {
					return o1.toString().compareTo(o2.toString());
				}
			}
		}

	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected void F7HeadType_dataChanged(DataChangeEvent e) throws Exception {
		super.F7HeadType_dataChanged(e);
		if (this.editData.getHeadType() != null) {
			HeadTypeInfo value = (HeadTypeInfo) this.F7HeadType.getValue();
			if (value != null
					&& value.getId().toString().equals(
							this.editData.getHeadType().getId().toString())) {
				return;
			}
			if (this.kdPrice.getRowCount() > 0) {
				MsgBox.showError("已经有价格导入不能修改表头类型!");
				this.F7HeadType.setValue(this.editData.getHeadType());
				return;
			}
		}
		this.fillHead();
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		setIsUsedEnable();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		super.actionAddNew_actionPerformed(e);
		this.txtName.setEnabled(true);
		this.txtDescription.setEnabled(true);
		this.textUnit.setEnabled(true);			
		this.F7HeadType.setEnabled(true);
	}
	
	protected void attachListeners() {
		// TODO 自动生成方法存根

	}

	protected void detachListeners() {
		// TODO 自动生成方法存根

	}
}