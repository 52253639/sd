/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI;
import com.kingdee.eas.fdc.finance.PaymentSplitFacadeFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PrjActualInputDetailQueryUI extends AbstractPrjActualInputDetailQueryUI {

	private static final Logger logger = CoreUIObject.getLogger(PrjActualInputDetailQueryUI.class);

	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	protected EntityViewInfo view;

	private static final String FILTER_BY_PRJ = "filterByPrj";
	
	private static final String FILTER_BY_PERIOD = "filterByPeriod";
	
	private static final String MONTH_TO = "monthTo";

	private static final String MONTH_FROM = "monthFrom";

	private static final String YEAR_TO = "yearTo";

	private static final String YEAR_FROM = "yearFrom";

	private static final String PROJECT_IDS = "projectIds";
	
	private static final String COST_ACCOUNT_IDS = "costAccounts";
	private static final String COST_ACCOUNT_LGNUMS = "costAccountLgNums";

	private CurProjectInfo curProject = null;
	private String projectName = "";

	RetValue retValue = null;

	private String year_from;
	private String year_to;
	private String month_from;
	private String month_to;

//	protected FDCCustomerParams objParam = new FDCCustomerParams();
	
	private Set costAccountIds = new HashSet();
	private Set costAccountLgNums = new HashSet();
	
	private HashMap objParam = new HashMap();

	private static boolean filterByPeriod = true;
	
	private static boolean filterByPrj = true;
	
	String [] tempCostAccountIds = null;
	String [] tempCostAccountLgNums = null;
	/**
	 * output class constructor
	 */
	public PrjActualInputDetailQueryUI() throws Exception {
		super();
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setUiObject(null);
		commonQueryDialog.setShowFieldCompare(false);
		commonQueryDialog.setShowFilter(false);
		commonQueryDialog.setShowSorter(false);
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new PrjActualInputDetailFilterUI();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	protected void execQuery() {
		try {
			objParam.clear();
			objParam.putAll(((PrjActualInputDetailFilterUI) getFilterUI()).getResult());

			year_from = (String) objParam.get(YEAR_FROM);
			month_from = (String) objParam.get(MONTH_FROM);
			year_to = (String) objParam.get(YEAR_TO);
			month_to = (String) objParam.get(MONTH_TO);
			filterByPeriod = ((Boolean)objParam.get(FILTER_BY_PERIOD)).booleanValue();
			filterByPrj = ((Boolean)objParam.get(FILTER_BY_PRJ)).booleanValue();
			tempCostAccountIds = (String[])objParam.get(COST_ACCOUNT_IDS); 
			tempCostAccountLgNums = (String[])objParam.get(COST_ACCOUNT_LGNUMS);
			costAccountIds = FDCHelper.getSetByArray(tempCostAccountIds);
			costAccountLgNums = FDCHelper.getSetByArray(tempCostAccountLgNums);
			
			//当以默认方案直接进入 时，获取默认方案
			if(objParam.get(PROJECT_IDS)==null&&mainQuery!=null){
				//取保存的分配方案
				if(mainQuery.getFilter()!=null){
					FilterInfo filter=mainQuery.getFilter();
					for(Iterator iter=filter.getFilterItems().iterator();iter.hasNext();){
						FilterItemInfo item=(FilterItemInfo)iter.next();
						 //工程ID
						if("curProject.id".equalsIgnoreCase(item.getPropertyName())){
							objParam.put(PROJECT_IDS,item.getCompareValue().toString().replaceAll("[\\[\\]]", ""));
						}
						//科目ID
						if ("costAccount.id".equalsIgnoreCase(item.getPropertyName())) {
							if (item.getCompareValue() != null) {
								objParam.put(COST_ACCOUNT_IDS,item.getCompareValue().toString().replaceAll("[\\[\\]]", ""));
								costAccountIds = (Set)item.getCompareValue(); 
							}
						}
						//科目number
						if ("costAccount.number".equalsIgnoreCase(item.getPropertyName())) {
							if (item.getCompareValue() != null) {
								objParam.put(COST_ACCOUNT_LGNUMS,item.getCompareValue().toString().replaceAll("[\\[\\]]", ""));
								costAccountLgNums = (Set)item.getCompareValue(); 
							}
						}
						//会计期间
						if ("beginQueryDate".equalsIgnoreCase(item.getPropertyName())) {
							if (item.getCompareValue() != null) {
								if(item.getCompareValue() instanceof Timestamp){
									Date begindate = new Date(((Timestamp)item.getCompareValue()).getTime());
									Calendar c = Calendar.getInstance();
									c.setTime(begindate);
									year_from = "" + c.get(Calendar.YEAR);
									month_from = "" + (c.get(Calendar.MONTH)+1);
								}
							}
						}
						
						if ("endQueryDate".equalsIgnoreCase(item.getPropertyName())) {
							if (item.getCompareValue() != null) {
								if(item.getCompareValue() instanceof Timestamp){
									Date endDate = new Date(((Timestamp)item.getCompareValue()).getTime());
									Calendar c = Calendar.getInstance();
									c.setTime(endDate);
									year_to = "" + c.get(Calendar.YEAR);
									month_to = "" + (c.get(Calendar.MONTH)+1);
								}
							}
						}
						
						//不按期间查询
						if ("filterByPeriod".equalsIgnoreCase(item.getPropertyName())) {
							if (item.getCompareValue() != null) {
								if ("1".equals(item.getCompareValue())) {
									filterByPeriod = true;
								} else {
									filterByPeriod = false;
								}
							}
						}
						
						//按工程查询
						if ("filterByPrj".equalsIgnoreCase(item.getPropertyName())) {
							if (item.getCompareValue() != null) {
								if ("1".equals(item.getCompareValue())) {
									filterByPrj = true;
								} else {
									filterByPrj = false;
								}
							}
						}
					}
				}
			}
			
			if (objParam.get(PROJECT_IDS) != null) {
				curProject = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(objParam.get(PROJECT_IDS).toString()));
				refreshTable();
			}
		} catch (Exception e) {
			handUIException(e);
		}
 	}

	private void formatTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
		table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		table.getViewManager().setFreezeView(-1, 2);
//		table.setRefresh(false);
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		table.getColumn("costAccount.number").getStyleAttributes().setLocked(true);
		table.getColumn("costAccount.name").getStyleAttributes().setLocked(true);
		if (table.getHeadRowCount() == 1) {
			table.addHeadRow(1, (IRow) table.getHeadRow(0).clone());
		}
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
		table.setColumnMoveable(true);
		FDCHelper.formatTableNumber(tblMain, "workAmount.amount");
		FDCHelper.formatTableNumber(tblMain, "contract.amount");
		FDCHelper.formatTableNumber(tblMain, "contract.lastPrice");
	}

	private void refreshTable() throws Exception {
		formatTable();
		fetchData();

		setHeadInfos();
		this.tblMain.removeRows(false);
		if (!curProject.isIsLeaf()) {
			return;
		}
		fillTable();
	}

	private void setHeadInfos() {
		projectName = this.curProject.getDisplayName(SysContext.getSysContext().getLocale());
		projectName = projectName.replaceAll("_", " \\\\ ");
		txtProject.setText(projectName);
		txtProject.setEnabled(false);
		
		StringBuffer periodDesc = new StringBuffer();
		if(filterByPeriod){
			periodDesc.append(year_from).append("年").append("第").append(getMonth(month_from)).append("期至");
			periodDesc.append(year_to).append("年").append("第").append(getMonth(month_to)).append("期");
		}else{
			periodDesc.append("所有期间");
		}
		kDLabel3.setText(periodDesc.toString());
	}

	protected void initAcct(FilterInfo acctFilter) throws BOSException {
		acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		acctFilter.getFilterItems().add(new FilterItemInfo("isEnterDB", new Integer(1)));
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("number"));
		sel.add(new SelectorItemInfo("name"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
	}
	//找到所有的父级长编码
	private void getAllParenIds(CostAccountInfo costAccount,Set set) {
		CostAccountInfo info = costAccount;
		if(info.getParent()!= null){
			String id = info.getParent().getId().toString();
			set.add(id);
			CostAccountInfo _info = info.getParent(); 
			getAllParenIds(_info, set);
		}
	}
	protected void fillTable() throws Exception {
		tblMain.removeRows(false);
		FilterInfo acctFilter = new FilterInfo();
		acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
		Set tempSet = null;
		if (!filterByPrj) {
			if (costAccountIds.size() > 0) {
				tempSet = new HashSet();
				// acctFilter.getFilterItems().add(new FilterItemInfo("id",
				// costAccountIds ,CompareType.INCLUDE));
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("select parent.flongnumber lgnum from t_fdc_costaccount parent \n ");
				builder.appendSql("inner join t_fdc_costaccount child on charindex(parent.flongnumber ||'!',child.flongnumber||'!' )=1 \n  ");
				builder.appendParam("where child.fid \n  ",costAccountIds.toArray());
				builder.appendSql("and child.fcurproject=? \n ");
				builder.appendSql("and parent.fcurproject=? \n ");
				builder.addParam(curProject.getId().toString());
				builder.addParam(curProject.getId().toString());
				IRowSet rowSet = builder.executeQuery();
				while (rowSet.next()) {
					tempSet.add(rowSet.getString("lgnum"));
				}
				acctFilter.getFilterItems().add(new FilterItemInfo("longNumber", tempSet, CompareType.INCLUDE));
			}
		}
			initAcct(acctFilter);
			TreeModel costAcctTree = null;
			try {
				// 根据当前指定项目条件构造科目树
				costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);

				DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree.getRoot();
				Enumeration childrens = root.depthFirstEnumeration();
				int maxLevel = 0;
				while (childrens.hasMoreElements()) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens.nextElement();
					if (node.getLevel() > maxLevel) {
						maxLevel = node.getLevel();
					}
				}
				tblMain.getTreeColumn().setDepth(maxLevel);
				for (int i = 0; i < root.getChildCount(); i++) {
					fillNode((DefaultMutableTreeNode) root.getChildAt(i), tblMain);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			gatherData(tblMain);
	}

	protected void fillNode(DefaultMutableTreeNode node, KDTable table) throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		String acctId = costAcct.getId().toString();
		List confirmPeriodList = (List) retValue.get("confirmPeriodList");
		Map confirmSplits = (Map) retValue.get("confirmSplits");
		Map confirms = (Map) retValue.get("confirms");
		Map cons = (Map) retValue.get("cons");
		Map conSplitMap = (Map)retValue.get("conSplitMap");
		Map changeSplitMap = (Map)retValue.get("changeSplitMap");
		Map settleSplitMap = (Map)retValue.get("settleSplitMap");
		Map noTextSplitMap = (Map)retValue.get("noTextSplitMap");
		
		IRow row = table.addRow();
		int level = node.getLevel() - 1;
		row.setTreeLevel(level);
		row.getCell("costAccount.number").setValue(costAcct.getLongNumber().replace('!', '.'));
		row.getCell("costAccount.name").setValue(costAcct.getName());
		if (node.isLeaf()) {
			row.setUserObject(costAcct);
			BigDecimal acctAmt = FDCHelper.ZERO;
			for (int i = 0; i < confirmPeriodList.size(); i++) {
				String period = (String) confirmPeriodList.get(i);
				// key:科目+期间
				Map confirmSplitDatas = (Map) confirmSplits.get(acctId + period);
				if (confirmSplitDatas != null) {
					Set keySet = confirmSplitDatas.keySet();
					// 拆分数据
					for (Iterator iter = keySet.iterator(); iter.hasNext();) {
						String key = (String) iter.next();
						List splitInfos = (List) confirmSplitDatas.get(key);
						for(int w=0;w<splitInfos.size();w++){
							PaymentSplitInfo splitInfo = (PaymentSplitInfo)splitInfos.get(w);
							IRow splitRow = table.addRow();
							splitRow.getCell("workAmount.amount").setValue(splitInfo.getAmount());
							if(!splitInfo.isIsInvalid()){
								acctAmt = FDCHelper.add(acctAmt, splitInfo.getAmount());
							}
							
							// 单据信息
							if (confirms!=null&&confirms.containsKey(key)) {
								WorkLoadConfirmBillInfo confirm = (WorkLoadConfirmBillInfo) confirms.get(key);
								if (confirm != null) {
									splitRow.setTreeLevel(level + 1);
									splitRow.getCell("workAmount.id").setValue(confirm.getId().toString());
									splitRow.getCell("workAmount.number").setValue(confirm.getNumber());
									splitRow.getCell("workAmount.date").setValue(confirm.getConfirmDate());
									splitRow.getCell("workAmount.period").setValue(period);
									if (cons != null && confirm.getContractBill() != null && confirm.getContractBill().getId() != null && cons.containsKey(confirm.getContractBill().getId().toString())) {
										ContractBillInfo con = (ContractBillInfo) cons.get(confirm.getContractBill().getId().toString());
										if (con != null) {
											String key2 = acctId+con.getId().toString();
											splitRow.getCell("contract.id").setValue(con.getId());
											splitRow.getCell("contract.number").setValue(con.getNumber());
											splitRow.getCell("contract.name").setValue(con.getName());
											splitRow.getCell("contract.partB").setValue(con.getPartB().getName());
											splitRow.getCell("contract.amount").setValue(conSplitMap.get(key2));
											BigDecimal lastPrice = FDCHelper.ZERO;
											if(!con.isHasSettled()){
												BigDecimal conSplitAmt = (BigDecimal)conSplitMap.get(key2);
												BigDecimal changeSplitAmt = (BigDecimal)changeSplitMap.get(key2);
												lastPrice = FDCHelper.add(conSplitAmt, changeSplitAmt);
											}else{
												lastPrice = (BigDecimal)settleSplitMap.get(key2);
											}
											splitRow.getCell("contract.lastPrice").setValue(lastPrice);
										}
									}
								}
							}
//							if(splitInfo.containsKey("invalidPeriod")){
//								splitRow = table.addRow();
//								splitRow.getCell("workAmount.amount").setValue(FDCHelper.toBigDecimal(splitInfo.getAmount()).multiply(FDCHelper._ONE));
//								if (confirms!=null&&confirms.containsKey(key)) {
//									WorkLoadConfirmBillInfo confirm = (WorkLoadConfirmBillInfo) confirms.get(key);
//									if (confirm != null) {
//										splitRow.setTreeLevel(level + 1);
//										splitRow.getCell("workAmount.id").setValue(confirm.getId().toString());
//										splitRow.getCell("workAmount.number").setValue(confirm.getNumber());
//										splitRow.getCell("workAmount.date").setValue(confirm.getConfirmDate());
//										splitRow.getCell("workAmount.period").setValue(splitInfo.get("invalidPeriod"));
//										if (cons != null && confirm.getContractBill() != null && confirm.getContractBill().getId() != null && cons.containsKey(confirm.getContractBill().getId().toString())) {
//											ContractBillInfo con = (ContractBillInfo) cons.get(confirm.getContractBill().getId().toString());
//											if (con != null) {
//												splitRow.getCell("contract.id").setValue(con.getId());
//												splitRow.getCell("contract.number").setValue(con.getNumber());
//												splitRow.getCell("contract.name").setValue(con.getName());
//												splitRow.getCell("contract.partB").setValue(con.getPartB().getName());
//											}
//										}
//									}
//								}
//							}
						}
						
					}
//					if(periodAmt.compareTo(FDCHelper.ZERO) != 0){
					//取消现有的分期合计功能
						/*IRow periodRow = table.addRow();
						periodRow.setTreeLevel(level);
						periodRow.getCell("workAmount.period").setValue(period + "期");
						periodRow.getCell("workAmount.amount").setValue(periodAmt);*/
//					}
				}
				
			}
			row.getCell("workAmount.amount").setValue(acctAmt);
			
			//填充无文本合同的付款拆分数据和付款单相关数据
			Map paymentSplits = (Map)retValue.get("paymentSplits");
			List paymentPeriodList = (List) retValue.get("paymentPeriodList");
			Map parments = (Map) retValue.get("pays");
			Map txts = (Map) retValue.get("txts");
			
			if (paymentPeriodList != null) {
				for (int i = 0; i < paymentPeriodList.size(); i++) {
					String period = (String) paymentPeriodList.get(i);
					// key:科目+期间
					Map confirmSplitDatas = (Map) paymentSplits.get(acctId + period);
					if (confirmSplitDatas != null) {
						Set keySet = confirmSplitDatas.keySet();

						// 拆分数据
						for (Iterator iter = keySet.iterator(); iter.hasNext();) {
							String key = (String) iter.next();
							BigDecimal splitAmt = (BigDecimal) confirmSplitDatas.get(key);
							IRow _Row = table.addRow();
							_Row.getCell("workAmount.amount").setValue(splitAmt);
							acctAmt = FDCHelper.add(acctAmt, splitAmt);
							// 单据信息
							if (parments!=null&&parments.containsKey(key)) {
								PaymentBillInfo payment = (PaymentBillInfo) parments.get(key);
								if (payment != null) {
									_Row.setTreeLevel(level + 1);
									if (txts != null && payment.getContractBillId() != null && txts.containsKey(payment.getContractBillId())) {
										ContractWithoutTextInfo txt = (ContractWithoutTextInfo) txts.get(payment.getContractBillId());
										if (txt != null) {
											String key2 = acctId+txt.getId().toString();
											_Row.getCell("contract.id").setValue(txt.getId());
											_Row.getCell("contract.number").setValue(txt.getNumber());
											_Row.getCell("contract.name").setValue(txt.getName());
											_Row.getCell("contract.partB").setValue(payment.getActFdcPayeeName().getName());
											_Row.getCell("contract.amount").setValue(noTextSplitMap.get(key2));
											_Row.getCell("contract.lastPrice").setValue(noTextSplitMap.get(key2));
										}
									}
								}
							}
						}
					}
				}
			}
			row.getCell("workAmount.amount").setValue(acctAmt);
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode((DefaultMutableTreeNode) node.getChildAt(i), table);
			}
		}
	}
	protected List getUnionColumns(){
		List columns = new ArrayList();
		columns.add("workAmount.amount");
		return columns;
	}
	protected void gatherData(KDTable table) {
		List columns = getUnionColumns();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					//过滤小计行
					if(rowAfter.getCell("costAccount.number").getValue()==null){
						continue;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				BigDecimal amount = FDCHelper.ZERO;
				for (int k = 0; k < columns.size(); k++) {
					String colName = (String) columns.get(k);
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(
										((Integer) value).toString()));
							}
						}
					}
				}
				if(row.getCell("costAccount.number").getValue()!=null){
					row.getCell("workAmount.amount").setValue(amount);
				}
			}
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	/**
	 * 
	 * 初始化默认过滤条件
	 * 
	 * @return 如果重载了（即做了初始化动作），请返回true;默认返回false;
	 */
	protected boolean initDefaultFilter() {
		return true;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			int rowIndex = e.getRowIndex();
			if(getMainTable().getRow(rowIndex)==null)
				return;
			if(getMainTable().getRow(rowIndex)==null||getMainTable().getRow(rowIndex).getCell("workAmount.id")==null||getMainTable().getRow(rowIndex).getCell("contract.id").getValue()==null){
				return;
			}
			Object obj = getMainTable().getRow(rowIndex).getCell("workAmount.id").getValue();
			//双击打开无文本
			boolean isNoText = false;
			if(obj==null){
				obj=getMainTable().getRow(rowIndex).getCell("contract.id").getValue();
				isNoText= true;
			}
			if (obj == null)
				return;
			
			UIContext uiContext = new UIContext(ui);
			uiContext.put(UIContext.ID, obj.toString());
			//Add by zhiyuan_tang 2010/07/24     增加参数ParentUI，以便在工程量确认单界面能进行判断，如果是从工程实际投入表过去的，将新增按钮屏蔽掉
			uiContext.put("ParentUI", "PrjActualInputDetailQueryUI");
			// 创建UI对象并显示
			IUIWindow uiWindow= null;
			if (!isNoText) {
				uiWindow= UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(WorkLoadConfirmBillEditUI.class.getName(), uiContext, null, "VIEW");
			} else {
				uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractWithoutTextEditUI.class.getName(), uiContext, null, "VIEW");
			}
			
			uiWindow.show();
		}
}

	private String getMonth(String month) {
		if (Integer.parseInt(month) < 10) {
			month = "0" + month;
		}
		return month;
	}

	private String getBeginPeriodNumber() {
		return year_from + getMonth(month_from);
	}

	private String getLastPeriodNumber() {
		return year_to + getMonth(month_to);
	}

	protected void fetchData() throws Exception {
		HashMap paramValue = new HashMap();
		paramValue.put("prjId", curProject.getId().toString());
		paramValue.put("beginPeriod", getBeginPeriodNumber());
		paramValue.put("lastPeriod", getLastPeriodNumber());
		paramValue.put("filterByPeriod", new Boolean(filterByPeriod));
		paramValue.put("filterByPrj", new Boolean(filterByPrj));
		paramValue.put("costAccounts", costAccountIds);
		retValue = PaymentSplitFacadeFactory.getRemoteInstance().getWorkLoadConfirmSplitDatas(paramValue);
	}

	protected String getKeyFieldName() {
		return "workAmount.id";
	}
	 
	public void onLoad() throws Exception {
		//只要是财务组织，不论是实体还是虚体都能进入  2010-07-21
		if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit() /*|| !SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()*/)   	
    	{
    		FDCMsgBox.showWarning(this, "非财务组织不能进入!");
    		SysUtil.abort();
    	}
		String companyId=SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		if(!FDCUtils.getDefaultFDCParamByKey(null, companyId, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)){
			FDCMsgBox.showWarning(this, "未启用“工程量确认流程与付款流程分离”参数，不能使用本功能！");
			SysUtil.abort();
		}
		super.onLoad();
		setDisplayButton();
		//设置保存当前样式
		tHelper = new TablePreferencesHelper(this);
	}

	protected void setDisplayButton() {
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnView.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnLocate.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemEdit.setVisible(false);
		this.menuItemView.setVisible(false);
		this.menuItemRemove.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.menuItemLocate.setVisible(false);
		this.menuEdit.setVisible(false);
	}
}