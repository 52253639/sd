/**
 * @author jiyu_guan
 * @updatetime 2010-05-31
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillFacadeFactory;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class TenancyBillNewListUI extends AbstractTenancyBillNewListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(TenancyBillNewListUI.class);

	private TenancyArrearageFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	private IRow footRow = null;
	

	/**
	 * output class constructor
	 */
	public TenancyBillNewListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		KDTFootManager footRowManager= tblMain.getFootManager();
		footRowManager = new KDTFootManager(tblMain);
		footRowManager.addFootView();
		tblMain.setFootManager(footRowManager);
		footRow= footRowManager.addFootRow(0);
		footRow.setUserObject("FDC_PARAM_TOTALCOST");
		footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
		footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
		tblMain.getIndexColumn().setWidth(60);
		footRowManager.addIndexText(0, "合计");  
		super.onLoad();
		filterUI = getFilterUI();
		tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		treeMain.setSelectionRow(0);
		tblMain.getColumn("room").getStyleAttributes().setLocked(false);
//		tblMain.getColumn("moneyType").getStyleAttributes().setHided(true);
		initActionStatus();
		this.actionView.setVisible(false);
		
	}

	private void initActionStatus() {
		actionAddNew.setVisible(false);
		actionEdit.setVisible(false);
		actionRemove.setVisible(false);
		actionAttachment.setVisible(false);
		actionLocate.setVisible(false);
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.execQuery();
	}

	protected void execQuery() {
		Map param = getFilterParam();
		addTreeSelectParam(param);
		FillTable(param);
		setTableTree();
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

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected boolean initDefaultFilter() {
		return true;
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,
				MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		execQuery();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	private TenancyArrearageFilterUI getFilterUI() {
		if (filterUI == null) {
			try {
				filterUI = new TenancyArrearageFilterUI();
			} catch (Exception e) {
				handleException(e);
			}
		}
		return filterUI;
	}
	
	public void handUIException(Throwable exc) {
		// TODO Auto-generated method stub
		
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}

	private Map getFilterParam() {
		if (filterUI == null) {
			filterUI = getFilterUI();
		}
		Map param = new HashMap();
		if (filterUI != null) {
			param = filterUI.getParam();
		}
		return param;
	}

	private void addTreeSelectParam(Map param) {
		if (param == null) {
			param = new HashMap();
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			Object obj = node.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				tblMain.getColumn("projectName").getStyleAttributes().setHided(
						false);

				Set set = getSelectSellProjects(node);
				param.put("orgUnit", set);
			} else if (obj instanceof SellProjectInfo) {
				tblMain.getColumn("projectName").getStyleAttributes().setHided(
						true);
				param
						.put("project", ((SellProjectInfo) obj).getId()
								.toString());
			}
		}
	}

	/**
	 * 通过过滤条件查询结果集，并显示到表格
	 * 
	 * @param param
	 */
	private void FillTable(Map param) {
		tblMain.removeRows();
		try {
			Map result = TenancyBillFacadeFactory.getRemoteInstance()
					.getTenancyBill(param);
			List tenancys = (List) result.get("tenancy");
			Map customers = (Map) result.get("customers");
			
			BigDecimal allTotalAppamout =FDCHelper.ZERO;
			BigDecimal allTotalactAmout = FDCHelper.ZERO;
			BigDecimal allHasTransferredAmount = FDCHelper.ZERO; //已转押金  add by wanping
			BigDecimal allTotalUnPayAmt = FDCHelper.ZERO;
			if (tenancys != null && tenancys.size() > 0) {
				// 当前已加汇总行数
				int nums = 0;
				// 当前明细行数
				int line = 0;
				String oldId = null;
				String oldProjectName = null;
				String oldContractName = null;
				String oldContractNumber = null;
				String oldCustomer = null;
//				MoneyTypeEnum oldMoneyType = null;
				String oldMoneyName = null;
				Integer oldLease = null;
				// String oldAdvisor = null;
				BigDecimal totalAppAmout = FDCHelper.ZERO;
				BigDecimal totalactAmout = FDCHelper.ZERO;
				BigDecimal totaloutAmout = FDCHelper.ZERO;
				BigDecimal totalUnPayAmt = FDCHelper.ZERO;
				for (int i = 0; i < tenancys.size(); i++) {
					Map tenancy = (Map) tenancys.get(i);
					if (tenancy != null) {
						String id = (String) tenancy.get("id");
						String projectName = (String) tenancy
								.get("projectName");
						String contractNumber = (String) tenancy
						.get("contractNumber");
						String contractName = (String) tenancy
								.get("contractName");
						String customer = null;
						if (customers != null) {
							customer = (String) customers.get(id);
						}
//						String moneyType = (String) tenancy.get("moneyType");
//						MoneyTypeEnum moneyTpyeEnum = MoneyTypeEnum
//								.getEnum(moneyType);
						String moneyName = (String) tenancy.get("moneyName");
						Integer lease = (Integer) tenancy.get("lease");
						String room = (String) tenancy.get("room");
						Date appDate = (Date) tenancy.get("appDate");
						
						Date startDate = (Date) tenancy.get("startDate");
						Date endDate = (Date) tenancy.get("endDate");
						
						BigDecimal appAmount = (BigDecimal) tenancy
						.get("appAmount")==null?FDCHelper.ZERO:(BigDecimal) tenancy
								.get("appAmount");
						Date actDate = (Date) tenancy.get("actDate");
						BigDecimal actAmount = (BigDecimal) tenancy.get("actAmount")==null?FDCHelper.ZERO:(BigDecimal) tenancy.get("actAmount");
						BigDecimal hasTransferredAmount = (BigDecimal) tenancy.get("hasTransferredAmount")==null?FDCHelper.ZERO:(BigDecimal) tenancy.get("hasTransferredAmount");
						BigDecimal unPayAmount = (BigDecimal) tenancy
						.get("unPayAmount")==null?FDCHelper.ZERO:(BigDecimal) tenancy
								.get("unPayAmount");
						Integer arrearageDay = (Integer) tenancy
								.get("arrearageDay");
						String advisor = (String) tenancy.get("advisor");

						// 未付金额大于0，红色，小于0，蓝色
						IRow row = tblMain.addRow();
						if (unPayAmount != null) {
							if (unPayAmount.compareTo(FDCHelper.ZERO) > 0) {
								row.getStyleAttributes()
										.setFontColor(Color.red);
							} else if (unPayAmount.compareTo(FDCHelper.ZERO) < 0) {
								row.getStyleAttributes().setFontColor(
										Color.blue);
							}
						}
						allTotalAppamout=allTotalAppamout.add(appAmount);
						allTotalactAmout=allTotalactAmout.add(actAmount);
						allHasTransferredAmount=allHasTransferredAmount.add(hasTransferredAmount);
						allTotalUnPayAmt=allTotalUnPayAmt.add(unPayAmount);
						// 界面设置值
						/**
						 * 此分支是为了处理一个合同下的多个房间的欠款明细
						 *if 判断是一个合同
						 */
						if (line != 0 && id.equals(oldId)
								&& moneyName.equals(oldMoneyName)
								&& lease.equals(oldLease)) {
							line++;
							appAmount = appAmount == null ? FDCHelper.ZERO
									: appAmount;
							actAmount = actAmount == null ? FDCHelper.ZERO
									: actAmount;
							unPayAmount = unPayAmount == null ? FDCHelper.ZERO
									: unPayAmount;
							totalAppAmout = totalAppAmout.add(appAmount);
							totalactAmout = totalactAmout.add(actAmount);
							totaloutAmout = totaloutAmout.add(hasTransferredAmount);
							totalUnPayAmt = totalUnPayAmt.add(unPayAmount);

							row.getCell("id").setValue(id);
							row.getCell("projectName").setValue(projectName);
							row.getCell("contractNumber").setValue(contractNumber);
							row.getCell("contractName").setValue(contractName);
							row.getCell("customer").setValue(customer);
//							row.getCell("moneyType").setValue(moneyTpyeEnum);
							row.getCell("moneyName").setValue(moneyName);
							row.getCell("lease").setValue(lease);

							CellTreeNode treeNode = new CellTreeNode();
							treeNode.setValue(room);
							treeNode.setTreeLevel(0);
							treeNode.setHasChildren(false);
							row.getCell("room").setValue(treeNode);
							
							row.getCell("startDate").setValue(startDate);
							row.getCell("endDate").setValue(endDate);
							
							row.getCell("appDate").setValue(appDate);
							row.getCell("appAmount").setValue(appAmount);
							row.getCell("actDate").setValue(actDate);
							row.getCell("actAmount").setValue(actAmount);
							row.getCell("outAmount").setValue(hasTransferredAmount);
							row.getCell("unPayAmount").setValue(unPayAmount);
							row.getCell("arrearageDay").setValue(arrearageDay);
							row.getCell("advisor").setValue(advisor);
						} else {
							if (line > 1) {
								IRow rowTotal = tblMain.addRow(i - line + nums);
								CellTreeNode treeNode = new CellTreeNode();
								treeNode.setValue("多个房间");
								treeNode.setTreeLevel(0);
								treeNode.setHasChildren(true);
								treeNode.setCollapse(false);
								rowTotal.getCell("id").setValue(oldId);
								rowTotal.getCell("projectName").setValue(
										oldProjectName);
								rowTotal.getCell("contractNumber").setValue(
										oldContractNumber);
								rowTotal.getCell("contractName").setValue(
										oldContractName);
								rowTotal.getCell("customer").setValue(
										oldCustomer);
//								rowTotal.getCell("moneyType").setValue(
//										oldMoneyType);
								rowTotal.getCell("moneyName").setValue(
										oldMoneyName);
								rowTotal.getCell("lease").setValue(oldLease);
								rowTotal.getCell("room").setValue(treeNode);
								rowTotal.getCell("room").setUserObject(
										new Integer(line));
								rowTotal.getCell("appAmount").setValue(
										totalAppAmout);
								rowTotal.getCell("actAmount").setValue(
										totalactAmout);
								rowTotal.getCell("outAmount").setValue(
										hasTransferredAmount);
								rowTotal.getCell("unPayAmount").setValue(
										totalUnPayAmt);
								if (totalUnPayAmt != null) {
									if (totalUnPayAmt.compareTo(FDCHelper.ZERO) > 0) {
										rowTotal.getStyleAttributes()
												.setFontColor(Color.red);
									} else if (totalUnPayAmt
											.compareTo(FDCHelper.ZERO) < 0) {
										rowTotal.getStyleAttributes()
												.setFontColor(Color.blue);
									}
								}
								nums++;
							}
							row.getCell("id").setValue(id);
							row.getCell("projectName").setValue(projectName);
							row.getCell("contractNumber").setValue(contractNumber);
							row.getCell("contractName").setValue(contractName);
							row.getCell("customer").setValue(customer);
//							row.getCell("moneyType").setValue(moneyTpyeEnum);
							row.getCell("moneyName").setValue(moneyName);
							row.getCell("lease").setValue(lease);

							CellTreeNode treeNode = new CellTreeNode();
							treeNode.setValue(room);
							treeNode.setTreeLevel(0);
							treeNode.setHasChildren(false);
							row.getCell("room").setValue(treeNode);
							
							row.getCell("startDate").setValue(startDate);
							row.getCell("endDate").setValue(endDate);
							
							row.getCell("appDate").setValue(appDate);
							row.getCell("appAmount").setValue(appAmount);
							row.getCell("actDate").setValue(actDate);
							row.getCell("actAmount").setValue(actAmount);
							row.getCell("outAmount").setValue(hasTransferredAmount);
							row.getCell("unPayAmount").setValue(unPayAmount);
							row.getCell("arrearageDay").setValue(arrearageDay);
							row.getCell("advisor").setValue(advisor);

							line = 1;
							totalAppAmout = appAmount == null ? FDCHelper.ZERO
									: appAmount;
							totalactAmout = actAmount == null ? FDCHelper.ZERO
									: actAmount;
							totalUnPayAmt = unPayAmount == null ? FDCHelper.ZERO
									: unPayAmount;
						}
						oldId = id;
						oldProjectName = projectName;
						oldContractNumber = contractNumber;
						oldContractName = contractName;
						oldCustomer = customer;
//						oldMoneyType = moneyTpyeEnum;
						oldMoneyName = moneyName;
						oldLease = lease;
					}
				}
				if (line > 1) {
					IRow rowTotal = tblMain.addRow(tenancys.size() - line
							+ nums);
					CellTreeNode treeNode = new CellTreeNode();
					treeNode.setValue("多个房间");
					treeNode.setTreeLevel(0);
					treeNode.setHasChildren(true);
					treeNode.setCollapse(false);
					rowTotal.getCell("id").setValue(oldId);
					rowTotal.getCell("projectName").setValue(oldProjectName);
					rowTotal.getCell("contractNumber").setValue(oldContractNumber);
					rowTotal.getCell("contractName").setValue(oldContractName);
					rowTotal.getCell("customer").setValue(oldCustomer);
//					rowTotal.getCell("moneyType").setValue(oldMoneyType);
					rowTotal.getCell("moneyName").setValue(oldMoneyName);
					rowTotal.getCell("lease").setValue(oldLease);
					rowTotal.getCell("room").setValue(treeNode);
					rowTotal.getCell("room").setUserObject(new Integer(line));
					rowTotal.getCell("appAmount").setValue(totalAppAmout);
					rowTotal.getCell("actAmount").setValue(totalactAmout);
					rowTotal.getCell("outAmount").setValue(totaloutAmout);
					rowTotal.getCell("unPayAmount").setValue(totalUnPayAmt);
					if (totalUnPayAmt != null) {
						if (totalUnPayAmt.compareTo(FDCHelper.ZERO) > 0) {
							rowTotal.getStyleAttributes().setFontColor(
									Color.red);
						} else if (totalUnPayAmt.compareTo(FDCHelper.ZERO) < 0) {
							rowTotal.getStyleAttributes().setFontColor(
									Color.blue);
						}
					}
					nums++;
				}
				
			}
			//增加合计行
			footRow.getCell("appAmount").setValue(allTotalAppamout );
			footRow.getCell("actAmount").setValue(allTotalactAmout );
			footRow.getCell("outAmount").setValue(allHasTransferredAmount );
			footRow.getCell("unPayAmount").setValue(allTotalUnPayAmt );
		} catch (BOSException e) {
			handUIException(e);
		}
	}

	private void setTableTree() {
		tblMain.setRowCount(tblMain.getBody().getRows().size());
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			CellTreeNode treeNode = (CellTreeNode) row.getCell("room")
					.getValue();
			if ("多个房间".equals(treeNode.getValue())) {
				Integer nums = (Integer) row.getCell("room").getUserObject();
				for (int j = i + 1; j < i + nums.intValue() + 1; j++) {
					IRow child = tblMain.getRow(j);
					child.getCell("projectName").setValue(null);
					child.getCell("contractNumber").setValue(null);
					child.getCell("contractName").setValue(null);
					child.getCell("customer").setValue(null);
//					child.getCell("moneyType").setValue(null);
					child.getCell("moneyName").setValue(null);
					child.getCell("lease").setValue(null);
					// child.getCell("advisor").setValue(null);
					CellTreeNode childTreeNode = (CellTreeNode) child.getCell(
							"room").getValue();
					childTreeNode.setTreeLevel(1);
				}
				// treeNode.setCollapse(true);
			}
		}
	}

	private Set getSelectSellProjects(DefaultKingdeeTreeNode node) {
		Set projects = new HashSet();
		Enumeration en = node.breadthFirstEnumeration();
		while (en.hasMoreElements()) {
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) en
					.nextElement();
			if (child.getUserObject() instanceof SellProjectInfo) {
				projects.add(((SellProjectInfo) child.getUserObject()).getId()
						.toString());
			}
		}
		return projects;
	}

}