/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryFactory;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */

public class ChangeReasonSumUI extends AbstractChangeReasonSumUI {
	private Map adjustMap = new HashMap();
	private Map dyAppMap = null;
	
	//是否使用成本月结,当前财务组织的期间
	private Date[] pkdate ;
	/**
	 * output class constructor
	 */
	public ChangeReasonSumUI() throws Exception {
		super();
		pkdate = FDCClientHelper.getCompanyCurrentDate(false);
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		initTable();
		
		dyAppMap = ProjectHelper.getIndexValueByProjProd(null, (String) null,
				ProjectStageEnum.DYNCOST);
		fillTable();
	}
	
	private void initControl() {
		this.btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
//		int year = Calendar.getInstance().get(Calendar.YEAR);
//		int month = Calendar.getInstance().get(Calendar.MONTH);
		Date curDate = pkdate[0];
		int year = curDate.getYear();
		int month = curDate.getMonth();
		
		SpinnerNumberModel yearMo = new SpinnerNumberModel(year+1900, 1990, 2099, 1);
		this.spiYear.setModel(yearMo);
		SpinnerNumberModel monthMo = new SpinnerNumberModel(month + 1, 1, 12, 1);
		spiMonth.setModel(monthMo);
	}
	
	public void initTable() throws BOSException {
		this.tblMain.checkParsed();
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getHeadRow(1).getStyleAttributes().setHided(true);
		IColumn column = this.tblMain.addColumn();
		column.setKey("acct");
		this.tblMain.getHeadRow(0).getCell("acct").setValue("科目");
		column = tblMain.addColumn();
		column.setKey("adjustAmount");
		column.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getHeadRow(0).getCell("adjustAmount").setValue("调整金额");
		
		//添加动态可售面积，并设置主键为"dyArea"
		column = tblMain.addColumn();
		column.setKey("dyArea");
		column.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getHeadRow(0).getCell("dyArea").setValue("动态可售面积");
		
		//添加调整单方,并设置主键为"adjustBill"
		column = tblMain.addColumn();
		column.setKey("adjustBill");
		column.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getHeadRow(0).getCell("adjustBill").setValue("调整单方");
		
		column = tblMain.addColumn();
		column.setKey("adjustReason");
		this.tblMain.getHeadRow(0).getCell("adjustReason").setValue("调整原因");
		column = tblMain.addColumn();
		column.setKey("description");
		tblMain.getHeadRow(0).getCell("description").setValue("描述");
	}
	
	public void fillTable() throws Exception {
		adjustMap.clear();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		Calendar beginCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		beginCal.set(Calendar.YEAR, ((Integer) this.spiYear.getValue())
				.intValue());
		beginCal.set(Calendar.MONTH, ((Integer) this.spiMonth.getValue())
				.intValue() - 1);
		beginCal.set(Calendar.DATE, 1);
		endCal.set(Calendar.YEAR, ((Integer) this.spiYear.getValue())
				.intValue());
		endCal.set(Calendar.MONTH, ((Integer) this.spiMonth.getValue())
				.intValue());
		endCal.set(Calendar.DATE, 1);
		filter.getFilterItems().add(
				new FilterItemInfo("adjustDate", DateTimeUtils
						.truncateDate(beginCal.getTime()),
						CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("adjustDate", DateTimeUtils
						.truncateDate(endCal.getTime()), CompareType.LESS));
		view.getSelector().add("*");
		view.getSelector().add("parent.account.*");
		view.getSelector().add("adjustReason.*");
		AdjustRecordEntryCollection adjusts = AdjustRecordEntryFactory
		.getRemoteInstance().getAdjustRecordEntryCollection(view);
		
		for (int i = 0; i < adjusts.size(); i++) {
			AdjustRecordEntryInfo adjust = adjusts.get(i);
			CurProjectInfo pro = adjust.getParent().getAccount()
			.getCurProject();
			if (pro == null) {
				continue;
			}
			String proId = pro.getId().toString();
			if (adjustMap.containsKey(proId)) {
				AdjustRecordEntryCollection adjs = (AdjustRecordEntryCollection) adjustMap
				.get(proId);
				adjs.add(adjust);
			} else {
				AdjustRecordEntryCollection adjs = new AdjustRecordEntryCollection();
				adjustMap.put(proId, adjs);
				adjs.add(adjust);
			}
		}
		tblMain.removeRows();
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		KDTree tree = new KDTree();
		treeBuilder.build(this, tree, this.actionOnLoad);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) tree.getModel()
		.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
			.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		if (root.isRoot()) {
			maxLevel++;
		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		fillNode(tblMain, root);
		setUnionData();
		
		//计算调整单方列，调整单方 = 调整金额/动态可售面积
		for(int i = 0; i < tblMain.getRowCount(); ++i)
		{
			BigDecimal flag = FMConstants.ZERO;
			
			BigDecimal adjustAmount = (BigDecimal) tblMain.getCell(i,"adjustAmount").getValue();
			BigDecimal dyArea = (BigDecimal) tblMain.getCell(i,"dyArea").getValue();
			if(dyArea != flag)
			{
//				tblMain.getCell(i,"adjustBill").setValue(adjustAmount.divide(dyArea, 2, BigDecimal.ROUND_HALF_UP));
				//避免除0异常 使用FDCHelper.divide方法
				tblMain.getCell(i,"adjustBill").setValue(FDCHelper.divide(adjustAmount,dyArea));
			}
			else
			{
				//如果调整金额或者动态可售面积有一个为0，调整单方为0
				tblMain.getCell(i,"adjustBill").setValue(flag);
			}
		}
		
		/*
		 * 由于明细行的动态调整面积为汇总行的动态调整面积，所以不显示明细行的动态调整面积。
		 * 在计算明细行的调整单方的时候是用明细行的调整金额除以对应的汇总行的动态调整面积。
		 * 由于明细行相对于汇总行来说会往里面缩一列，且左边列没有值，通过该特点可以判断出
		 * 从哪里开始有了明细行
		 */
		BigDecimal temp = FMConstants.ZERO;
		for(int i = 0; i < tblMain.getRowCount(); ++i)
		{
			if(tblMain.getCell(i, "orgOrProject").getValue() == null)
			{
				tblMain.getCell(i, "dyArea").setValue(null);
				
				if(tblMain.getCell(i-1, "orgOrProject").getValue() != null)
				{
					temp = (BigDecimal) tblMain.getCell(i-1,"dyArea").getValue();
				}
				
				BigDecimal adjustAmount = (BigDecimal) tblMain.getCell(i,"adjustAmount").getValue();
				if(temp != FMConstants.ZERO)
				{
//					tblMain.getCell(i,"adjustBill").setValue(adjustAmount.divide(temp, 2, BigDecimal.ROUND_HALF_UP));
					//避免除0异常 使用FDCHelper.divide方法
					tblMain.getCell(i,"adjustBill").setValue(FDCHelper.divide(adjustAmount, temp, 2, BigDecimal.ROUND_HALF_UP));
				}
				else
				{
					tblMain.getCell(i,"adjustBill").setValue(null);
				}
			}
		}
	}	
	/*
	 * 用于汇总带有树状结构表格中的值，从下往上逐一汇总。
	 * 并且将动态可售面积汇总和调整金额汇总融合在一起，增强其可扩展性。
	 * 如果增加其他可汇总列：如下所示
	 * String[] cols = new String[3];
	 * int index = 0;
	 * cols[index++] = "adjustAmount";
	 * cols[index++] = "dyArea";
	 * colos[index++] = "test";
	 */
	private void setUnionData() {
		
		String[] cols = new String[2];
		int index = 0;
		cols[index++] = "adjustAmount";
		cols[index++] = "dyArea";
		KDTable table = this.tblMain;
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getCell("orgOrProject").getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				List adjAmount = new ArrayList() ; 
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					//由于调整金额和动态可售面积的汇总标准不一样，所以需要分别处理
					if (rowAfter.getUserObject() == null) {
						aimRowList.add(rowAfter);
					}
					else 
					{
						adjAmount.add(rowAfter) ;
					}
				}
				for (int j = 0; j < cols.length; j++) {
					BigDecimal aimCost = FMConstants.ZERO;
					//调整金额列汇总
					if(cols[j] == "adjustAmount")
					{
						for (int rowIndex = 0; rowIndex < adjAmount.size(); rowIndex++) {
							IRow rowAdd = (IRow) adjAmount.get(rowIndex);
							Object value = rowAdd.getCell(cols[j]).getValue();
							if (value != null) {
								if (value instanceof BigDecimal) {
									aimCost = aimCost.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									aimCost = aimCost.add(new BigDecimal(
											((Integer) value).toString()));
								}
							}
						}
					}
					//动态可售面积汇总
					else if(cols[j] == "dyArea")
					{
						for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) aimRowList.get(rowIndex);
							Object value = rowAdd.getCell(cols[j]).getValue();
							if (value != null) {
								if (value instanceof BigDecimal) {
									aimCost = aimCost.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									aimCost = aimCost.add(new BigDecimal(
											((Integer) value).toString()));
								}
							}
						}
					}
					if(row.getCell(cols[j]).getValue() == null)
					{
						row.getCell(cols[j]).setValue(aimCost);
					}
				}
			}
		}
	}
	
	public void fillNode(KDTable table, DefaultKingdeeTreeNode node)
	throws BOSException, SQLException {
		IRow row = table.addRow();
		if (((DefaultKingdeeTreeNode) node.getRoot()).getUserObject() == null) {
			row.setTreeLevel(node.getLevel() - 1);
		} else {
			row.setTreeLevel(node.getLevel());
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			row.getCell("orgOrProject").setValue(projectInfo.getName());
			
		} else {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {
				
			}
			FullOrgUnitInfo info = oui.getUnit();
			row.getCell("orgOrProject").setValue(info.getName());
		}
		
		if (node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) { 
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			AdjustRecordEntryCollection adjs = (AdjustRecordEntryCollection) this.adjustMap
			.get(projectInfo.getId().toString());
		
			//获取明细目录下的动态可售面积
			String key = projectInfo.getId().toString() + " "
			+ ApportionTypeInfo.sellAreaType;
			BigDecimal dyArea = (BigDecimal) this.dyAppMap.get(key);
			row.getCell("dyArea").setValue(dyArea);
			
			if (adjs == null) {
				return;
			}
			
			HashMap acctMap = new HashMap();
			for (int i = 0; i < adjs.size(); i++) {
				AdjustRecordEntryInfo adj = adjs.get(i);
				CostAccountInfo acct = adj.getParent().getAccount();
				
				if (acctMap.containsKey(acct)) {
					AdjustRecordEntryCollection acctAdjs = (AdjustRecordEntryCollection) acctMap
					.get(acct);
					acctAdjs.add(adj);
				} else {
					AdjustRecordEntryCollection acctAdjs = new AdjustRecordEntryCollection();
					acctMap.put(acct, acctAdjs);
					acctAdjs.add(adj);
				}
			}
			Set set = acctMap.keySet();
			for (Iterator iter = set.iterator(); iter.hasNext();) {
				CostAccountInfo acct = (CostAccountInfo) iter.next();
				AdjustRecordEntryCollection acctAdjs = (AdjustRecordEntryCollection) acctMap
				.get(acct);
				row = table.addRow();
				if (((DefaultKingdeeTreeNode) node.getRoot()).getUserObject() == null) {
					row.setTreeLevel(node.getLevel());
				} else {
					row.setTreeLevel(node.getLevel() + 1);
				}
				row.getCell("acct").setValue(acct.getName());
				
				for (int i = 0; i < acctAdjs.size(); i++) {
					AdjustRecordEntryInfo adj = acctAdjs.get(i);
					
					row = table.addRow();
					row.setUserObject(adj);
					if (((DefaultKingdeeTreeNode) node.getRoot())
							.getUserObject() == null) {
						row.setTreeLevel(node.getLevel() + 1);
					} else {
						row.setTreeLevel(node.getLevel() + 2);
					}
					row.getCell("acct").setValue(adj.getAdjustAcctName());
					row.getCell("adjustAmount").setValue(adj.getCostAmount());
					row.getCell("adjustReason").setValue(adj.getAdjustReason());
					row.getCell("description").setValue(adj.getDescription());
				    dyArea = (BigDecimal) this.dyAppMap.get(key);
					row.getCell("adjustBill").setValue(dyArea);
					
				}
			}
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultKingdeeTreeNode) node
						.getChildAt(i));
			}
		}
	}
	
	protected void spiMonth_stateChanged(ChangeEvent e) throws Exception {
		super.spiMonth_stateChanged(e);
		this.fillTable();
	}
	
	protected void spiYear_stateChanged(ChangeEvent e) throws Exception {
		super.spiYear_stateChanged(e);
		this.fillTable();
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
		this.fillTable();
	}
}