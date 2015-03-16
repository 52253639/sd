/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.finance.FIProSttBillEntryFactory;
import com.kingdee.eas.fdc.finance.IFIProSttBillEntry;
import com.kingdee.eas.fdc.finance.ProductSettleBillCollection;
import com.kingdee.eas.fdc.finance.ProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ProductSettleBillInfo;
import com.kingdee.eas.fdc.invite.client.SupplierInviteRecordEditUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IIDList;
import com.kingdee.eas.framework.client.RealModeIDList;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * 产品结算单列表界面
 */
public class ProductSettleBillListUI extends AbstractProductSettleBillListUI {
	public static final String PRO_ENTRIES_ID = "proEntriesID";

	private static final Logger logger = CoreUIObject
			.getLogger(ProductSettleBillListUI.class);

	/**
	 * output class constructor
	 */
	public ProductSettleBillListUI() throws Exception {
		super();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected void checkBeforeOnLoad() {
		FDCClientUtils.checkCurrentBizCostCenterOrg(this);
	}

	public void onLoad() throws Exception {
		checkBeforeOnLoad();
		super.onLoad();

		getMainTable().getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		getMainTable().getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);

		getBillTable().getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		getBillTable().getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		getBillTable().getStyleAttributes().setLocked(true);

		FDCClientHelper.setActionEnable(new ItemAction[] { actionLocate,
				actionAttachment, actionPrint, actionPrintPreview,
				actionWorkFlowG, actionAudit, actionUnAudit }, false);
		FDCClientHelper.setActionEnable(new ItemAction[] { actionVoucher,
				actionDelVoucher }, true);

		menuBiz.setVisible(false);
//		menuTool.setVisible(false);
		menuWorkFlow.setVisible(false);
		FDCClientHelper.setActionEnable(new ItemAction[] { actionDelVoucher,
				actionVoucher }, false);
		//设置可以保存当前样式
		tHelper = new TablePreferencesHelper(this);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionRemove_actionPerformed(e);

		String id = getSelectedKeyValue(getMainTable());

		displayBill(id);
	}

	protected void checkTableParsed() {
		super.checkTableParsed();
		getBillTable().checkParsed();
	}

	protected void checkBeforeRemove() throws Exception {
		checkSelected();
		int[] selectRows = KDTableUtil.getSelectedRows(getBillTable());
		IFIProSttBillEntry iEntry = FIProSttBillEntryFactory.getRemoteInstance();
		for (int i = 0; i < selectRows.length; i++) {
			String id = (String) getBillTable().getCell(selectRows[i],
					getKeyFieldName()).getValue();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("fiVouchered"));
			ProductSettleBillInfo info = (ProductSettleBillInfo) getBizInterface()
					.getValue(new ObjectUuidPK(BOSUuid.read(id)), sic);
			if (info.isFiVouchered()) {
				MsgBox.showError("包含已经生成凭证的记录，不能执行本操作！");
				SysUtil.abort();
			}else{
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("productBill.id",id));
				if(iEntry.exists(filter)){
					MsgBox.showError("已经被竣工结算帐务处理引用，不能执行本操作！");
					SysUtil.abort();
				}
			}
		}
	}

	/**
	 * 批量删除
	 * 
	 * @throws Exception
	 * @throws EASBizException
	 */
	protected void removeBill() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(getBillTable());
		IObjectPK[] arrayPK = new ObjectUuidPK[selectRows.length];

		for (int i = 0; i < selectRows.length; i++) {
			String id = (String) getBillTable().getCell(selectRows[i],
					getKeyFieldName()).getValue();
			checkRef(id);
			arrayPK[i] = new ObjectUuidPK(id);
		}
		getBizInterface().delete(arrayPK);

		showOprtOKMsgAndRefresh();
	}

	protected void checkBeforeAddNew(ActionEvent e) {
		checkSelected(getMainTable());

		IRow selectedRow = KDTableUtil.getSelectedRow(getMainTable());
		String id = getSelectedKeyValue(getMainTable());
		try {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("curProject.projectStatus.id"));
			sic.add(new SelectorItemInfo("isCompSettle"));
			CurProjProductEntriesInfo info = CurProjProductEntriesFactory
					.getRemoteInstance().getCurProjProductEntriesInfo(
							new ObjectUuidPK(BOSUuid.read(id)), sic);
			if (!(info.getCurProject().getProjectStatus().getId().toString()
					.equals(ProjectStatusInfo.proceedingID))) {
				MsgBox.showWarning(this, "工程项目状态不适合本操作！");
				SysUtil.abort();
			} else if (info.isIsCompSettle()) {
				MsgBox.showWarning(this, "本产品全部已经竣工结算!");
				SysUtil.abort();
			}
		} catch (EASBizException e1) {
			super.handUIException(e1);
		} catch (BOSException e1) {
			super.handUIException(e1);
		} catch (UuidException e1) {
			super.handUIException(e1);
		}

		BigDecimal saleArea = (BigDecimal) selectedRow.getCell("saleArea")
				.getValue();
		if (saleArea == null || saleArea.compareTo(FDCConstants.B0) != 1) {
			MsgBox.showWarning(this, "可售面积为空或0的产品不能进行结算");
			SysUtil.abort();
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		super.actionAddNew_actionPerformed(e);

		String id = getSelectedKeyValue(getMainTable());

		displayBill(id);
	}
	
	//竣工结算账务处理且已生成凭证不允许修改
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(getBillTable());

		String id = getSelectedKeyValue(getBillTable());
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select * from T_FNC_FIProductSettleBill fip ");
		builder.appendSql("inner join T_FNC_ProductSettleBill ps on ps.FCurProjProductEntriesID=fip.FCurProjProductEntriesID ");
		builder.appendSql("where ps.fid = ? and ps.FFiVouchered=1");
		builder.addParam(id);
		IRowSet rs = builder.executeQuery();
		if(rs!=null&&rs.next()){
			MsgBox.showWarning(this, "已经竣工结算账务处理,不允许修改!");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}
	
	protected void execQuery() {

	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		String id = getSelectedKeyValue(getMainTable());

		displayBill(id);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);

		String id = getSelectedKeyValue(getMainTable());
		displayBill(id);
	}

	protected void treeSelectChange() throws Exception {

		if (getProjSelectedTreeNode() != null
				&& getProjSelectedTreeNode().getUserObject() instanceof CurProjectInfo) {

			CurProjectInfo projTreeNodeInfo = (CurProjectInfo) getProjSelectedTreeNode()
					.getUserObject();

			String projId = projTreeNodeInfo.getId().toString();

			fillProductEntries(projId);

			FDCClientHelper.selectTableFirstRow(getMainTable());
		} else {
			getMainTable().removeRows(false);
			getBillListTable().removeRows(false);
		}
	}

	protected KDTable getBillTable() {
		return tblBill;
	}

	protected void displayBill(String id) throws Exception {
		getBillTable().removeRows(false);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("curProjProductEntries.id", id));
		view.setFilter(filter);
		ProductSettleBillCollection productSettleBillCollection = ProductSettleBillFactory
				.getRemoteInstance().getProductSettleBillCollection(view);

		IRow row = null;
		for (Iterator iter = productSettleBillCollection.iterator(); iter
				.hasNext();) {

			ProductSettleBillInfo element = (ProductSettleBillInfo) iter.next();

			row = getBillTable().addRow();

			row.getCell("id").setValue(element.getId().toString());
			row.getCell("isCompSettle").setValue(
					Boolean.valueOf(element.isIsCompSettle()));
			row.getCell("compArea").setValue(element.getCompArea());
			row.getCell("compDate").setValue(element.getCompDate());
			row.getCell("fiVouchered").setValue(
					Boolean.valueOf(element.isFiVouchered()));
		}

		FDCClientHelper.selectTableFirstRow(getBillTable());

	}

	private void fillProductEntries(String projId) throws Exception {

		getMainTable().removeRows(false);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("curProject.id", projId));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("productType.id");
		view.getSelector().add("productType.name");

		Map indexValueByProjProdMap = ProjectHelper.getIndexValueByProjProd(
				null, projId, ProjectStageEnum.DYNCOST);

		CurProjProductEntriesCollection curProjProductEntriesCollection = CurProjProductEntriesFactory
				.getRemoteInstance().getCurProjProductEntriesCollection(view);

		String key = null;
		IRow row = null;

		for (Iterator iter = curProjProductEntriesCollection.iterator(); iter
				.hasNext();) {
			CurProjProductEntriesInfo element = (CurProjProductEntriesInfo) iter
					.next();

			row = getMainTable().addRow();

			row.getCell("id").setValue(element.getId().toString());
			String productTypeId = element.getProductType().getId().toString();
			row.getCell("productType.id").setValue(productTypeId);
			row.getCell("productType.name").setValue(
					element.getProductType().getName());

			key = projId + " " + productTypeId + " "
					+ FDCConstants.SALE_AREA_ID;

			BigDecimal saleArea = (BigDecimal) indexValueByProjProdMap.get(key);

			row.getCell("saleArea").setValue(saleArea);

		}
	}

	protected void audit(List ids) throws Exception {
		// TODO Auto-generated method stub

	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		// TODO Auto-generated method stub
		
		return ProductSettleBillFactory.getRemoteInstance();
	}

	protected void unAudit(List ids) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return ProductSettleBillFactory.getRemoteInstance();
	}

	/**
	 * 关联的编辑UI对象名
	 */
	protected String getEditUIName() {
		return ProductSettleBillEditUI.class.getName();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);

		String id = getSelectedKeyValue(getMainTable());

		uiContext.put(PRO_ENTRIES_ID, id);
	}

	/**
	 * 获取当前选择行的主键
	 * 
	 * @return 返回当前选择行的主键，若当前选择行为空，或者当前选中行的主键列为空，则返回null
	 */
	protected String getSelectedKeyValue(KDTable table) {
		KDTSelectBlock selectBlock = table.getSelectManager().get();

		if (selectBlock != null) {
			int rowIndex = selectBlock.getTop();
			IRow row = table.getRow(rowIndex);
			if (row == null) {
				return null;
			}

			ICell cell = row.getCell(getKeyFieldName());

			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}

			Object keyValue = cell.getValue();

			if (keyValue != null) {
				return keyValue.toString();
			}
		}

		return null;
	}

	protected String getSelectedKeyValue() {
		return getSelectedKeyValue(getBillTable());
	}

	/**
	 * 
	 * 描述：检查当前单据的Table是否选中行
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#checkSelected()
	 */
	public void checkSelected() {
		checkSelected(getBillTable());
	}

	/**
	 * 
	 * 描述：检查当前单据的Table是否选中行
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#checkSelected()
	 */
	public void checkSelected(KDTable table) {
		if (table.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	/**
	 * 获取当前选择所有行的主键集合，已处理单据的id重复情况，它被getSelectedKeyValues()调用
	 * 注：对于房地产系统来存在双分录的情况，而框架把得到主键集合的写死了， 只能从tblMain得到，造成很多不明确的问题
	 * 
	 * @author sxhong Date 2006-10-28
	 * @return IIDList 返回当前选择行的主键，若当前选择行为空，或者当前选中行的主键列为空，则返回null
	 * @see com.kingdee.eas.framework.client.ListUI#getSelectedKeyValuesForHasQueryPK()
	 */
	protected IIDList getSelectedKeyValuesForHasQueryPK() {
		// if(isHasBillTable()){
		return getBillIDList();
		// }else{
		// return super.getSelectedKeyValuesForHasQueryPK();
		// }
	}

	private IIDList getBillIDList() {
		if (getBillListTable() == null) {
			return RealModeIDList.getEmptyIDList();// 返回一个空集
		}
		KDTable billTable = getBillListTable();
		// 多选情况下，获取所有的选择块信息
		int[] selectRows = KDTableUtil.getSelectedRows(billTable);

		// selectList = new ArrayList();
		int size = selectRows.length; // 获取选择块的总个数
		int maxReturnRowCount = 10000;// 来自基类
		if (size == 0) {
			return null;
		}

		if ((size == 1)
				&& (billTable.getSelectManager().get().getTop() == billTable
						.getSelectManager().get().getBottom())) {
			// 仅选择一行时，选择数据范围在100以内。
			// 因为考虑到所有数据都获取并不好，新地虚模式也不能支持。用户很少这么使用的。 by psu_s 2005-8-24
			RealModeIDList idList = new RealModeIDList();
			int rowNum = billTable.getSelectManager().get().getTop();
			ICell cell = billTable.getRow(rowNum).getCell(getKeyFieldName());

			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}

			String curId = cell.getValue().toString();
			// allIdList
			Object[] currentIds = null;
			// TODO 要改进
			List allIdList = getAllIdListForBill();
			if (rowNum < allIdList.size()) {
				currentIds = (Object[]) allIdList.get(rowNum);
			} else {
				for (int i = 0; i < allIdList.size(); i++) {
					Object[] objs = (Object[]) allIdList.get(i);
					if (curId != null && curId.equals(objs[0].toString())) {
						rowNum = i;
						currentIds = (Object[]) allIdList.get(rowNum);
						break;
					}
				}

			}
			String currentId = null;
			if (currentIds == null) {
				// 取当前选择行的主键返回
				RealModeIDList idList2 = new RealModeIDList();
				if (cell != null && cell.getValue() != null) {
					idList2.add(cell.getValue().toString());
				}
				return idList2;
			}
			currentId = currentIds[0].toString();

			Object[] ids = null;

			if (currentIds.length > 1) // 多主键，bill
			{
				String theId = null;
				int j = 0;

				for (int i = 0; i < allIdList.size(); i++) {
					ids = (Object[]) allIdList.get(i);

					if (theId == null || !theId.equals(ids[0].toString())) {
						j++;
						theId = ids[0].toString();

						if (currentId.equals(theId)) {
							rowNum = j - 1;
						}

						idList.add(theId);
						// selectList.add(new Integer(i));
					}
				}
			} else
			// 单主键
			{
				for (int i = 0; i < allIdList.size(); i++) {// 在这里将所有的id放入到idList中，然后再指定当前的Id就确定了所选择的id
					ids = (Object[]) allIdList.get(i);
					idList.add(ids[0].toString());
					// selectList.add(new Integer(i));
				}
			}

			idList.setCurrentIndex(rowNum);
			// idList.setQuery(this.mainQueryPK, this.mainQuery);
			// idList.setCount(allIdList.size()); //可能有问题
			idList.setMaxRowCount(10000);
			// getSelectid
			// selectList.add(new Integer(rowNum));

			return idList;
		} else {
			RealModeIDList idList = new RealModeIDList();

			for (int i = 0; i < size; i++) {
				if (selectRows[i] < 0) {
					return idList;
				}
				ICell cell = billTable.getRow(selectRows[i]).getCell(
						getKeyFieldName());

				if (cell == null) {
					MsgBox.showError(EASResource
							.getString(FrameWorkClientUtils.strResource
									+ "Error_KeyField_Fail"));
					SysUtil.abort();
				}

				if (cell.getValue() == null) {
					return idList;
				}
				String id = cell.getValue().toString();
				idList.add(id);
				// selectList.add(new Integer(selectRows[i]));

			}
			idList.setCurrentIndex(0);
			// idList.setQuery(this.mainQueryPK, this.mainQuery);
			// idList.setCount(this.rowCount);
			// idList.setMaxRowCount(this.maxReturnRowCount);
			idList.setMaxRowCount(maxReturnRowCount);
			return idList;
		}
	}

	/**
	 * 
	 * 没有特别的意义，只是重载超类的方法，指明如果是双分录的时候返回的是子分录的ID集合
	 * 
	 * @author sxhong 获取当前选择所有行的主键集合
	 * 
	 * @return 返回当前选择行的主键，若当前选择行为空，或者当前选中行的主键列为空，则返回null
	 */
	protected IIDList getSelectedKeyValues() {
		return super.getSelectedKeyValues();
	}

	/**
	 * 
	 * 描述：为实现编辑界面的上一条、下一条功能，需要将ListUI的allIdList设置为Bill的IdList
	 * 注意：没有考虑联合主键的情况，比如单据头id和单据体id，一般叙事簿界面只显示单据头的信息
	 * 如果要支持联合主键的情况，请参考ListUI的processAllIdList方法修改 在getBillIDList()中使用
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-9-29
	 *               <p>
	 */
	protected List getAllIdListForBill() {
		List idList = new ArrayList();
		int count = getBillListTable().getRowCount();

		if (count == 0) {
			return null;
		}
		String id = null;
		Object[] keyValue = null;
		for (int i = 0; i < count; i++) {
			id = (String) getBillListTable().getCell(i, getKeyFieldName())
					.getValue();
			keyValue = new Object[1];
			keyValue[0] = id;
			idList.add(keyValue);
		}

		return idList;
	}

	/**
	 * 
	 * 描述：修改前检查
	 * 
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 * @throws Exception
	 */
	protected void checkBeforeEdit() throws Exception {
		checkSelected(getBillTable());
		checkBeforeRemove();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = getSelectors();
		sic.add("*");
		sic.add("curProjProductEntries.*");
		sic.add("curProjProductEntries.curProject.*");
		sic.add("curProjProductEntries.productType.*");
		return sic;
	}

	public void refreshList() throws Exception {
		super.refreshList();
		actionVoucher.setVisible(false);
		actionDelVoucher.setVisible(false);
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if (e.getSource() == btnAddNew || e.getSource() == menuItemAddNew
				|| e.getSource() == btnEdit || e.getSource() == menuItemEdit) {
			actionVoucher.setVisible(false);
			actionDelVoucher.setVisible(false);
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		return;
	}

	protected void tblBill_tableClicked(KDTMouseEvent e) throws Exception {
		//处理双击打开时，报记录不存在的问题  by zhiyuan_tang 
		int rowIndex = tblBill.getSelectManager().getActiveRowIndex();
		if(rowIndex < 0)
		{
			return;
		}

		if(tblBill.getRow(rowIndex).getCell(getKeyFieldName()).getValue() != null)
		{
			if (e.getClickCount() == 2)
			{
				String id = tblBill.getRow(rowIndex).getCell(getKeyFieldName()).getValue().toString();
				BOSUuid recordId = BOSUuid.read(id);
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, recordId);
				
				String proentryid = getSelectedKeyValue(getMainTable());

				uiContext.put(PRO_ENTRIES_ID, proentryid);

				IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL); 
				IUIWindow uiWindow = uiFactory.create(ProductSettleBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);

				uiWindow.show();
			}
		}
//		/*
//		 * 表头暂不排序，不然会引起对tblMain的排序，与逻辑不符合
//		 */
//		if (e.getType() == KDTStyleConstants.HEAD_ROW) {
//			return;
//		}
//		KDTable table=tblMain;
//		try{
//			tblMain=getBillTable();
//			super.tblMain_tableClicked(e);
//		}finally{
//			tblMain=table;
//		}
	}

}