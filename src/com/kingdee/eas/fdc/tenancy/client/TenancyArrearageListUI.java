/**
 * 租赁欠款查询
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.TenancyProvider;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class TenancyArrearageListUI extends AbstractTenancyArrearageListUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5095672692401361495L;
	private static final Logger logger = CoreUIObject
			.getLogger(TenancyArrearageListUI.class);

	private ItemAction[] hiddenAction = new ItemAction[] { this.actionAddNew,
			this.actionEdit, this.actionRemove, this.actionView };

	private TenancyArrearageFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	/**
	 * output class constructor
	 */
	public TenancyArrearageListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		this.tblMain.checkParsed();
		this.initTable();
		this.filterUI = this.getFilterUI();

		FDCClientHelper.setActionVisible(hiddenAction, false);

		this.tdprint.setEnabled(true);
		this.btnTDPrint.setEnabled(true);
		this.btnTDView.setEnabled(true);
		this.btnTDView.setVisible(true);
		this.btnTDPrint.setVisible(true);
		this.tdprintview.setEnabled(true);
	}

	public static final String BankDraftReq_FILENAME = "bim/fdc/tenancy/AnalyseReport";

	public void actionTd_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		checkSelected();
		ArrayList idList = super.getSelectedIdValues();
		
		if (idList == null || idList.size() == 0)
			return;
		BOSQueryDelegate data = new TenancyProvider(idList);
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(BankDraftReq_FILENAME, data, SwingUtilities
				.getWindowAncestor(this));

	}

	public void actionTdView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList idList = super.getSelectedIdValues();
		// for(int i=0;i<idList.size();i++){
		// ArrayList ids=new ArrayList();
		// ids.add(idList.get(i));

		if (idList == null || idList.size() == 0)
			return;
		BOSQueryDelegate data = new TenancyProvider(idList);
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(BankDraftReq_FILENAME, data, SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,
				MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {

		viewInfo = (EntityViewInfo) mainQuery.clone();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			super.getQueryExecutor(queryPK, viewInfo);
		}
		FilterInfo filter = new FilterInfo();
		if (node != null && node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
					.getUserObject();
			String sellProjectId = sellProject.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", sellProjectId));

		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		try {
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (BOSException e) {
			super.handUIException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void execQuery() {
		super.execQuery();

		this.handleDataAfterQueryData();

	}

	protected void refresh(ActionEvent e) throws Exception {
		this.execQuery();
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.execQuery();
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {

	}

	/**
	 * 当系统根据Query的过滤查询出了数据之后，再根据这些数据来进行其他列的处理。这样做，就可以没有脱离框架对取数性能的控制。
	 * 不像只是用SQL语句把所有的结果都查询出来。仍然可以利用系统的优化控制
	 * 
	 */
	private void handleDataAfterQueryData() {
		long begin = System.currentTimeMillis();

		this.fillMoneyNameData();
		this.fillLeaseData();
		this.fillRoomData();

		long end = System.currentTimeMillis();
		logger.info("填充一次数据用时" + (end - begin));
	}

	/**
	 *填充款项名称列
	 * 
	 */
	private void fillMoneyNameData() {
		int count = this.tblMain.getRowCount();
		if (count < 1) {
			return;
		}

		MoneyTypeEnum[] money = this.getMoneyType();
		if (money == null) {
			money = new MoneyTypeEnum[] { MoneyTypeEnum.DepositAmount,
					MoneyTypeEnum.RentAmount,MoneyTypeEnum.PeriodicityAmount };
		}

		for (int i = 0; i < count; i++) {
			IRow row = this.tblMain.getRow(i);
			String id = (String) row.getCell("id").getValue();
			int nums = 0;
			
			if(money[0] !=null){
				IRow depositRow = this.tblMain.getRow(i+nums);
				depositRow.getCell("moneyName").setValue(money[0]);
				depositRow.getCell("id").setValue(id);
				nums++;
			}
			if(money[1] != null){
				
			}
			
			if (money[0] != null && money[1] == null) {
				IRow depositRow = this.tblMain.getRow(i);
				depositRow.getCell("moneyName").setValue(money[0]);
				depositRow.getCell("id").setValue(id);
			} else if (money[0] == null && money[1] != null) {
				IRow depositRow = this.tblMain.getRow(i);
				depositRow.getCell("moneyName").setValue(money[1]);
				depositRow.getCell("id").setValue(id);
			} else if (money[0] != null && money[1] != null) {
				IRow depositRow = this.tblMain.getRow(i);
				depositRow.getCell("moneyName").setValue(money[0]);
				depositRow.getCell("id").setValue(id);

				IRow rentRow = this.tblMain.addRow(i + 1);
				rentRow.getCell("moneyName").setValue(MoneyTypeEnum.RentAmount);
				rentRow.getCell("id").setValue(id);
				count++;
				i++;
			}
		}

	}

	private void fillLeaseData() {
		int count = this.tblMain.getRowCount();
		if (count < 1) {
			return;
		}

		for (int i = 0; i < count; i++) {
			IRow row = this.tblMain.getRow(i);
			String id = (String) row.getCell("id").getValue();
			Object o = row.getCell("moneyName").getValue();

			if (o instanceof MoneyTypeEnum && id != null) {
				TenancyRoomPayListEntryCollection payListColl = this
						.getRoomPayListColl(id, (MoneyTypeEnum) o);

				Map tempMap = new HashMap();
				for (int k = 0; k < payListColl.size(); k++) {
					TenancyRoomPayListEntryInfo info = payListColl.get(k);

					BigDecimal appAmount = info.getAppAmount();
					BigDecimal actAmount = info.getActAmount();
					if (appAmount == null)
						appAmount = FDCHelper.ZERO;
					if (actAmount == null)
						actAmount = FDCHelper.ZERO;
					BigDecimal unPayAmount = appAmount.subtract(actAmount);
					if (unPayAmount == null)
						unPayAmount = FDCHelper.ZERO;

					Date appDate = info.getAppPayDate();
					Date actDate = info.getActPayDate();

					// 如果当前还有欠款，则欠款天数截止到当前计算
					int arrearageDay = 0;

					// arrearageDay = this.getArrearageDay(appDate,actDate);
					if (unPayAmount.compareTo(FDCHelper.ZERO) > 0) {
						arrearageDay = this.getArrearageDay(appDate, null);
					} else {
						arrearageDay = this.getArrearageDay(appDate, actDate);
					}

					// 欠款天数过滤
					if (arrearageDay != this.getArrearageDays()
							&& this.getArrearageDays() != 0)
						return;

					StringBuffer sb = new StringBuffer();
					sb.append(
							info.getTenRoom().getRoom().getBuilding()
									.getSellProject().getName()).append("-")
							.append(
									info.getTenRoom().getRoom().getBuilding()
											.getName()).append("-").append(
									info.getTenRoom().getRoom().getName());

					int temp = info.getLeaseSeq();
					if (tempMap.containsKey(new Integer(temp))) {
						Integer key = (Integer) tempMap.get(new Integer(temp));
						IRow tempRow = this.tblMain.getRow(key.intValue());
						// 累计房间
						String tempRoom = tempRow.getCell("room").getValue() == null ? ""
								: tempRow.getCell("room").getValue().toString();
						tempRow.getCell("room").setValue(
								sb.append(" , ").append(tempRoom));
						// 累计应收
						BigDecimal tempApp = (BigDecimal) tempRow.getCell(
								"appAmount").getValue();
						if (tempApp == null)
							tempApp = FDCHelper.ZERO;
						appAmount = appAmount.add(tempApp);
						tempRow.getCell("appAmount").setValue(appAmount);
						// 累计实收
						BigDecimal tempAct = (BigDecimal) tempRow.getCell(
								"actAmount").getValue();
						if (tempAct == null)
							tempAct = FDCHelper.ZERO;
						actAmount = actAmount.add(tempAct);
						tempRow.getCell("actAmount").setValue(actAmount);

						i--;
						count--;
						continue;
					} else {
						if (k == 0) {
							tempMap.put(new Integer(temp), new Integer(i));
						} else {
							tempMap.put(new Integer(temp), new Integer(i + k
									+ 1));
						}
					}
					IRow leaseRow = null;
					if (k == 0) {
						leaseRow = this.tblMain.getRow(i);
						i--;
						count--;
					} else {
						leaseRow = this.tblMain.addRow(i + k + 1);
					}
					leaseRow.getCell("moneyName").setValue((MoneyTypeEnum) o);

					// leaseRow.getCell("lease").setValue("第 " +new
					// Integer(info.getLeaseSeq())+" 期");
					leaseRow.getCell("lease").setValue(
							new Integer(info.getLeaseSeq()));
					leaseRow.getCell("room").setValue(sb);
					leaseRow.getCell("id").setValue(id);

					if (arrearageDay < 0)
						arrearageDay = 0;

					if (arrearageDay > 0) {
						if (unPayAmount.compareTo(FDCHelper.ZERO) > 0)
							leaseRow.getStyleAttributes().setFontColor(
									Color.RED);
						else
							leaseRow.getStyleAttributes().setFontColor(
									Color.BLUE);
					}

					leaseRow.getCell("arrearageDay").setValue(
							new Integer(arrearageDay));
					leaseRow.getCell("unPayAmount").setValue(unPayAmount);
					leaseRow.getCell("appDate").setValue(info.getAppPayDate());
					leaseRow.getCell("appAmount").setValue(appAmount);
					leaseRow.getCell("actDate").setValue(info.getActPayDate());
					leaseRow.getCell("actAmount").setValue(actAmount);
				}
				i = i + payListColl.size();
				count = count + payListColl.size();
			}
		}
	}

	/**
	 * 欠款天数
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private int getArrearageDay(Date begin, Date end) {
		int day = 0;
		if (begin == null)
			return 0;
		if (end == null || end.toString().trim().length() < 1) {
			try {
				end = FDCCommonServerHelper.getServerTime();
			} catch (BOSException e) {
				end = new Date();
			}
		}
		day = FDCHelper.getTwoDay(begin, end);
		return day;
	}

	/**
	 * 填充房间数据
	 * 
	 */
	private void fillRoomData() {

		int count = this.tblMain.getRowCount();
		if (count < 1) {
			return;
		}

		for (int i = 0; i < count; i++) {
			IRow row = this.tblMain.getRow(i);
			String roomDes = row.getCell("room").getValue() == null ? "" : row
					.getCell("room").getValue().toString();
			if (roomDes != null && this.splitRoom(roomDes) != null
					&& this.splitRoom(roomDes).length > 1) {
				String id = row.getCell("id").getValue().toString();
				MoneyTypeEnum money = (MoneyTypeEnum) row.getCell("moneyName")
						.getValue();
				Integer lease = (Integer) row.getCell("lease").getValue();

				TenancyRoomEntryCollection roomEntryColl = this
						.getRoomEntryColl(id);
				for (int j = 0; j < roomEntryColl.size(); j++) {
					TenancyRoomEntryInfo roomEntry = roomEntryColl.get(j);

					CellTreeNode treeNode = new CellTreeNode();
					treeNode.setValue(roomEntry.getRoom().getBuilding()
							.getSellProject().getName()
							+ "-"
							+ roomEntry.getRoom().getBuilding().getName()
							+ "-" + roomEntry.getRoom().getName());
					treeNode.setTreeLevel(1);
					treeNode.setHasChildren(false);

					TenancyRoomPayListEntryInfo payListInfo = this
							.getInfoValueByLeaseAndMoney(money, lease,
									roomEntry.getId().toString());

					BigDecimal appAmount = payListInfo.getAppAmount();
					BigDecimal actAmount = payListInfo.getActAmount();
					if (appAmount == null)
						appAmount = FDCHelper.ZERO;
					if (actAmount == null)
						actAmount = FDCHelper.ZERO;
					BigDecimal unPayAmount = appAmount.subtract(actAmount);
					if (unPayAmount == null)
						unPayAmount = FDCHelper.ZERO;

					Date appDate = payListInfo.getAppPayDate();
					Date actDate = payListInfo.getActPayDate();

					int arrearageDay = 0;
					if (unPayAmount.compareTo(FDCHelper.ZERO) > 0) {
						arrearageDay = this.getArrearageDay(appDate, null);
					} else {
						arrearageDay = this.getArrearageDay(appDate, actDate);
					}

					if (arrearageDay < 0)
						arrearageDay = 0;

					// 判断那些逾期类型
					if (TenancyArrearageFilterUI.KEY_ALL.equalsIgnoreCase(this
							.getQueryType())) {

					} else if (TenancyArrearageFilterUI.KEY_DelayAndNo
							.equalsIgnoreCase(this.getQueryType())) {
						if (arrearageDay > 0
								&& unPayAmount.compareTo(FDCHelper.ZERO) > 0) {

						} else {
							continue;
						}
					} else if (TenancyArrearageFilterUI.KEY_DelayAndYes
							.equalsIgnoreCase(this.getQueryType())) {
						if (arrearageDay > 0
								&& unPayAmount.compareTo(FDCHelper.ZERO) <= 0) {

						} else {
							continue;
						}
					} else if (TenancyArrearageFilterUI.KEY_NoDelay
							.equalsIgnoreCase(this.getQueryType())) {
						if (arrearageDay > 0) {
							continue;
						}
					}

					IRow roomRow = this.tblMain.addRow(i + 1);
					roomRow.setUserObject(roomEntry.getRoom());
					roomRow.getCell("room").setValue(treeNode);
					roomRow.getCell("id").setValue(id);

					if (arrearageDay > 0) {
						if (unPayAmount.compareTo(FDCHelper.ZERO) > 0)
							roomRow.getStyleAttributes()
									.setFontColor(Color.RED);
						else
							roomRow.getStyleAttributes().setFontColor(
									Color.BLUE);
					}

					roomRow.getCell("arrearageDay").setValue(
							new Integer(arrearageDay));
					roomRow.getCell("unPayAmount").setValue(unPayAmount);

					roomRow.getCell("appDate").setValue(
							payListInfo.getAppPayDate());
					roomRow.getCell("actDate").setValue(
							payListInfo.getActPayDate());
					roomRow.getCell("appAmount").setValue(
							payListInfo.getAppAmount());
					roomRow.getCell("actAmount").setValue(
							payListInfo.getActAmount());

					roomRow.getStyleAttributes().setHided(true);

					++count;
					++i;
				}

				CellTreeNode treeNode = new CellTreeNode();

				treeNode.setValue("多个房间");
				treeNode.setTreeLevel(0);
				treeNode.setHasChildren(true);
				treeNode.setCollapse(true);

				row.getCell("room").setValue(treeNode);
			}
		}
	}

	/**
	 * 
	 * @param money
	 * @param lease
	 * @param id
	 * @return
	 */
	private TenancyRoomPayListEntryInfo getInfoValueByLeaseAndMoney(
			MoneyTypeEnum money, Integer lease, String id) {
		TenancyRoomPayListEntryInfo returnInfo = new TenancyRoomPayListEntryInfo();

		if (money == null || lease == null || id == null)
			return null;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(new FilterItemInfo("tenRoom", id));
		filter.getFilterItems().add(new FilterItemInfo("leaseSeq", lease));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyDefine.moneyType", money));

		TenancyRoomPayListEntryCollection payColl = null;
		try {
			payColl = TenancyRoomPayListEntryFactory.getRemoteInstance()
					.getTenancyRoomPayListEntryCollection(view);
		} catch (BOSException e) {
			super.handUIExceptionAndAbort(e);
		}
		if (payColl.size() > 1) {
			logger.warn("房间ID为" + id + ",的租赁付款明细存在多个第" + lease + "期的，款项"
					+ money);
		}
		BigDecimal actAmount = FDCHelper.ZERO;
		BigDecimal appAmount = FDCHelper.ZERO;

		for (int i = 0; i < payColl.size(); i++) {
			TenancyRoomPayListEntryInfo info = payColl.get(i);
			BigDecimal tempAct = info.getActAmount();
			BigDecimal tempApp = info.getAppAmount();

			if (tempAct == null)
				tempAct = FDCHelper.ZERO;
			if (tempApp == null)
				tempApp = FDCHelper.ZERO;

			actAmount = actAmount.add(tempAct);
			appAmount = appAmount.add(tempApp);
		}
		Date actDate = null;
		Date appDate = null;
		if (payColl.get(0) != null) {
			actDate = payColl.get(0).getActPayDate();
			appDate = payColl.get(0).getAppPayDate();
		}

		returnInfo.setActRevAmount(actAmount);
		returnInfo.setAppAmount(appAmount);
		returnInfo.setActRevDate(actDate);
		returnInfo.setAppPayDate(appDate);

		return returnInfo;
	}

	/**
	 * 获得该合同下的某个款项的所有付款明细
	 * 
	 * @param id
	 * @param money
	 * @return
	 */
	private TenancyRoomPayListEntryCollection getRoomPayListColl(String id,
			MoneyTypeEnum money) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		if (this.getAppBeginDate() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("appDate", this.getAppBeginDate(),
							CompareType.GREATER_EQUALS));
		}
		if (this.getAppEndDate() != null) {
			filter.getFilterItems()
					.add(
							new FilterItemInfo("appDate", FDCDateHelper
									.getNextDay(this.getAppEndDate()),
									CompareType.LESS));
		}

		view.getSelector().add("tenRoom.room.name");
		view.getSelector().add("tenRoom.room.building.sellProject.name");
		view.getSelector().add("tenRoom.room.building.name");
		view.getSelector().add("*");

		filter.getFilterItems().add(
				new FilterItemInfo("tenRoom.tenancy.id", id));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyDefine.moneyType", money));

		TenancyRoomPayListEntryCollection roomPayListColl = null;
		try {
			roomPayListColl = TenancyRoomPayListEntryFactory
					.getRemoteInstance().getTenancyRoomPayListEntryCollection(
							view);
		} catch (BOSException e) {
			super.handUIExceptionAndAbort(e);
		}
		return roomPayListColl;
	}

	private TenancyRoomEntryCollection getRoomEntryColl(String id) {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("room.*");
		view.getSelector().add("room.building.name");
		view.getSelector().add("room.building.sellProject.name");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(new FilterItemInfo("tenancy", id));

		TenancyRoomEntryCollection roomEntryColl = null;
		try {
			roomEntryColl = TenancyRoomEntryFactory.getRemoteInstance()
					.getTenancyRoomEntryCollection(view);
		} catch (BOSException e) {
			super.handUIExceptionAndAbort(e);
		}
		return roomEntryColl;
	}

	private String[] splitRoom(String rooms) {
		if (rooms == null || rooms.trim().length() < 1)
			return null;

		String[] room = rooms.split(",");
		return room;
	}

	/**
	 * 初始表格
	 * 
	 */
	private void initTable() {
		this.tblMain.getColumn("room").getStyleAttributes().setLocked(false);

		tblMain.getColumn("arrearageDay").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("appAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn("actAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn("unPayAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));

		tblMain.getColumn("arrearageDay").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn("unPayAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(620);
		try {
			commonQueryDialog.addUserPanel(this.getFilterUI());
		} catch (Exception e) {
			super.handUIException(e);
		}
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private TenancyArrearageFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new TenancyArrearageFilterUI();
			} catch (Exception e) {
				super.handleException(e);
			}
		}
		return this.filterUI;
	}

	private String getQueryType() {
		FDCCustomerParams para = new FDCCustomerParams(this.filterUI
				.getCustomerParams());

		if (this.filterUI.isOnLoad) {
			return this.filterUI.getQueryType(para);
		} else {
			return null;
		}
	}

	private Date getAppBeginDate() {
		FDCCustomerParams para = new FDCCustomerParams(this.filterUI
				.getCustomerParams());
		if (this.filterUI.isOnLoad) {
			return this.filterUI.getAppBeginDate(para);
		} else {
			return null;
		}
	}

	private Date getAppEndDate() {
		FDCCustomerParams para = new FDCCustomerParams(this.filterUI
				.getCustomerParams());
		if (this.filterUI.isOnLoad) {
			return this.filterUI.getAppEndDate(para);
		} else {
			return null;
		}
	}

	private MoneyTypeEnum[] getMoneyType() {
		FDCCustomerParams para = new FDCCustomerParams(this.filterUI
				.getCustomerParams());
		if (this.filterUI.isOnLoad) {
			return this.filterUI.getMoneyTypeEnum(para);
		} else {
			return null;
		}
	}

	private int getArrearageDays() {
		FDCCustomerParams para = new FDCCustomerParams(this.filterUI
				.getCustomerParams());
		if (this.filterUI.isOnLoad) {
			return this.filterUI.getArrearageDay(para);
		} else {
			return 0;
		}
	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected boolean initDefaultFilter() {
		return false;
	}

	protected boolean isAllowDefaultSolutionNull() {
		return true;
	}

	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);
	}
	
	protected String[] getLocateNames()
	 {
	        String[] locateNames = new String[2];
	        locateNames[0] = "tenancyName";
	        locateNames[1] = IFWEntityStruct.dataBase_Name;
	        return locateNames;
	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionPublishReport);
		super.actionPublishReport_actionPerformed(e);
	}
}