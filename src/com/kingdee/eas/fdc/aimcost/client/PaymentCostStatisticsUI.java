/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;

/**
 * output class name
 */
public class PaymentCostStatisticsUI extends AbstractPaymentCostStatisticsUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5662999650568717508L;
	private static final Logger logger = CoreUIObject.getLogger(PaymentCostStatisticsUI.class);
	//工程项目的成本科目Map
	private List costAccountList = null;
	//工程项目下的成本科目与会计科目的对应关系Map
    private List accountList = null;
    //工程项目下的成本科目与会计科目的对应关系Map，已经完善了子科目与父科目的对应关系
    private Map costAccountWithAccountMapAll = null;
    //目标成本
    private Map aimCostMap = null;
    //节点计价
    private Map nodeMeasureMap = null;
    //历次付款记录的List，用来动态生成列
    private List payList = null;
    //所有付款单的Map
    private Map paySplitAmtMap = null;
    //非明细节点，会计科目的金额MAPs
    private Map accountAmtMap = null;
    
    
    /**
     * output class constructor
     */
    public PaymentCostStatisticsUI() throws Exception
    {
        super();
        //fetchData();
    }	
    protected String getEditUIName() {
		// TODO 自动生成方法存根
		return null;
	}
    protected void initTable(){
    	
    	tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	
    	tblMain.removeRows();
    	tblMain.getHeadMergeManager().removeAllMergeBlock();
    	
		tblMain.getHeadMergeManager().mergeBlock(0,0,1,0);
		tblMain.getHeadMergeManager().mergeBlock(0,1,1,1);
		tblMain.getHeadMergeManager().mergeBlock(0,2,1,2);
		tblMain.getHeadMergeManager().mergeBlock(0,3,1,3);
		
		boolean isNodeMeasure = false;
		try {
			isNodeMeasure = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_MEASURESPLIT);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			logger.warn(e);
		}
				
		tblMain.getColumn("aimCost").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getHeadRow(0).getCell("aimCost").setValue("目标成本");
		tblMain.getColumn("aimCost").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("sumNMAmount").getStyleAttributes().setHided(!isNodeMeasure);
		tblMain.getColumn("sumNMAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("sumNMAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("paymentDetails").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getHeadRow(0).getCell("paymentDetails").setValue("付款情况");
		tblMain.getColumn("paymentDetails").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("NMPayAmtDiff").getStyleAttributes().setHided(!isNodeMeasure);
		int oldColumnStart = tblMain.getColumnIndex("sumNMAmount");
		int oldColumnEnd   = tblMain.getColumnIndex("paymentDetails")-1;
		for(int i=oldColumnEnd;i>oldColumnStart;i--){
			tblMain.removeColumn(i);
		}
		
		int addColumnCount = 0;
		if(payList!=null&&payList.size()>0){
			addColumnCount = payList.size();
			
			for(int i=0;i<addColumnCount;i++){
				Map pay = (Map)payList.get(i);
				String payid = (String)pay.get("payid");
				Date paydate = (Date)pay.get("paydate");
				
				IColumn column = tblMain.addColumn(4+i);
				column.setKey(payid);
				column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				if(paydate!=null){
					//TODO 未付款 by hpw
					tblMain.getHeadRow(1).getCell(payid).setValue("第"+(i+1)+"次付款("+FDCHelper.FORMAT_DAY.format(paydate)+")");
				}
			}
			
		}
		tblMain.getHeadMergeManager().mergeBlock(0,4,0,4+addColumnCount);
		tblMain.getHeadMergeManager().mergeBlock(0,5+addColumnCount,1,5+addColumnCount);
		tblMain.getHeadMergeManager().mergeBlock(0,6+addColumnCount,1,6+addColumnCount);
		tblMain.getColumn(5+addColumnCount).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(5+addColumnCount).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(6+addColumnCount).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(6+addColumnCount).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);
		if(payList == null /* || payList.size() <=0*/){
			tblMain.getHeadRow(0).getCell("aimCost").setValue("目标成本(核备概算)");
			tblMain.getHeadMergeManager().mergeBlock(0,4,1,4);
			tblMain.getHeadRow(0).getCell("paymentDetails").setValue("累计付款金额");
		}
		
	}
    protected void fillTable()  throws Exception {
    	String prjId = getSelectObjId();
		if(prjId==null)
			return;
		Map unionCellMap = new HashMap();
		Map accountAmountsMap = new HashMap();//会计科目相关
		Set leafPrjIds = this.getSelectObjLeafIds();
		//填充成本科目栏相关值
    	if(costAccountList!=null){
    		for(Iterator it=costAccountList.iterator();it.hasNext();){
    			CostAccountInfo costAccountInfo = (CostAccountInfo)it.next();
    			
    			if(costAccountInfo!=null){
    				IRow row = tblMain.addRow();
    				String costAccountId = costAccountInfo.getId().toString();
    				
    				
    				//by ling_peng 
    				String longNumber=costAccountInfo.getLongNumber();
    				
    				
    				row.setUserObject(costAccountInfo);
    				row.setTreeLevel(costAccountInfo.getLevel()-1);
    				if(tblMain.getTreeColumn().getDepth()<costAccountInfo.getLevel()-1)
    					tblMain.getTreeColumn().setDepth(costAccountInfo.getLevel());
    				row.getCell("accountNumber").setValue(costAccountInfo.getLongNumber().replaceAll("!","."));
    				row.getCell("accountName").setValue(costAccountInfo.getName());
    				if(!costAccountInfo.isIsLeaf())
    					unionCellMap.put(costAccountId,row);
    				CostAccountWithAccountInfo costAccountWithAccountInfo = (CostAccountWithAccountInfo)costAccountWithAccountMapAll.get(prjId+"_"+costAccountId);
    				String accountId = "nothing";
    				if(costAccountWithAccountInfo!=null){
    					accountId = costAccountWithAccountInfo.getAccount().getId().toString();
    				}
    				Map accountAmountMap = null;
    				if(accountAmountsMap.containsKey(accountId))
    					accountAmountMap = (Map)accountAmountsMap.get(accountId);
    				else
    				{	
    					accountAmountMap = new HashMap();
    					accountAmountsMap.put(accountId,accountAmountMap);
    				}
    				
    				//目标成本    by ling_peng
    				BigDecimal aimCostAmount = FDCHelper.toBigDecimal(aimCostMap.get(longNumber));
    				//累计节点计价金额   by ling_peng
    				BigDecimal nodeMeasureAmount = FDCHelper.toBigDecimal(nodeMeasureMap.get(longNumber));
    				
    				//付款情况：累计付款金额
    				BigDecimal sumPaySplitAmount = FDCHelper.toBigDecimal(paySplitAmtMap.get(longNumber));
//    				row.getCell("paymentDetails").setValue(getValue(sumPaySplitAmount));
    				row.getCell("paymentDetails").setValue(getValue(sumPaySplitAmount));
    				
    				if(payList!=null){
    					for(int i=0;i<payList.size();i++){
    						Map pay = (Map)payList.get(i);
    						String payid = (String)pay.get("payid");
    						//历次付款金额
    						BigDecimal   paySplitAmount = FDCHelper.toBigDecimal(paySplitAmtMap.get(payid+"_"+costAccountId));
    						//历次付款的合计金额
    						sumPaySplitAmount = sumPaySplitAmount.add(paySplitAmount);
    						row.getCell(payid).setValue(getValue(paySplitAmount));
    						accountAmountMap.put(payid,paySplitAmount.add(FDCHelper.toBigDecimal(accountAmountMap.get(payid))));
    					}
    					//目标成本   by ling_peng
    					aimCostAmount = FDCHelper.toBigDecimal(aimCostMap.get(costAccountId));
    					//节点计价    by ling_peng
    					nodeMeasureAmount = FDCHelper.toBigDecimal(nodeMeasureMap.get(costAccountId));
    					//历次付款的合计金额    by ling_peng
    					row.getCell("paymentDetails").setValue(getValue(sumPaySplitAmount));
    				}
    				//目标成本
    				row.getCell("aimCost").setValue(getValue(aimCostAmount));
    				//将此金额加入会计科目对应的map中
    				accountAmountMap.put("aimCost",aimCostAmount.add(FDCHelper.toBigDecimal(accountAmountMap.get("aimCost"))));
    				
    				//累计节点计价金额
    				row.getCell("sumNMAmount").setValue(getValue(nodeMeasureAmount));
    				//将此金额加入会计科目对应的map中
    				accountAmountMap.put("sumNMAmount",nodeMeasureAmount.add(FDCHelper.toBigDecimal(accountAmountMap.get("sumNMAmount"))));
    				
    				//目标控制金额等于目标成本-累计付款金额
    				row.getCell("aimCtrlAmount").setValue(getValue(aimCostAmount.subtract(sumPaySplitAmount)));
    				//等于累计计价金额-累计付款金额
    				row.getCell("NMPayAmtDiff").setValue(getValue(nodeMeasureAmount.subtract(sumPaySplitAmount)));
    				
    				//写父的值
    				setParentRow(unionCellMap, costAccountInfo, row);
    				
    			}
    		}
    	}
    	//填充会计科目栏相关值
    	if(accountList!=null){
    		Map accountCostMap = null;
    		Map accountNodeMeasureMap = null;
    		Map accountPayAmtMap = null;
    		
    		if(accountAmtMap!=null)
    		{
    			accountCostMap = (Map)accountAmtMap.get("accountCostMap");
    			accountNodeMeasureMap = (Map)accountAmtMap.get("accountNodeMeasureMap");
    			accountPayAmtMap = (Map)accountAmtMap.get("accountPayAmtMap");
    			for(Iterator it=accountList.iterator();it.hasNext();){
        			AccountViewInfo accountViewInfo = (AccountViewInfo)it.next();
        			if(accountViewInfo!=null
        					&&accountViewInfo.getId()!=null){
        				String accountId = accountViewInfo.getId().toString();
        				String longnumber = accountViewInfo.getLongNumber();
        				IRow row = tblMain.addRow();
        				row.setUserObject(accountViewInfo);
        				row.setTreeLevel(0);
        				row.getCell("accountNumber").setValue(accountViewInfo.getNumber());
        				row.getCell("accountName").setValue(accountViewInfo.getName());
        				if((accountCostMap!=null&&accountCostMap.containsKey(longnumber))||
        						(accountNodeMeasureMap!=null&&accountNodeMeasureMap.containsKey(longnumber))||
        						(accountPayAmtMap!=null&&accountPayAmtMap.containsKey(longnumber))){
        					// 目标成本
        					BigDecimal aimCostAmount = FDCHelper.toBigDecimal(accountCostMap.get(longnumber));
        					BigDecimal nodeMeasureAmount = FDCHelper.toBigDecimal(accountNodeMeasureMap.get(longnumber));
        					
            				row.getCell("aimCost").setValue(getValue(aimCostAmount));
            				//累计节点计价金额
            				row.getCell("sumNMAmount").setValue(getValue(nodeMeasureAmount));
        					//历次付款金额
            				//历次付款的合计金额
        					BigDecimal sumPaySplitAmount = FDCHelper.toBigDecimal(accountPayAmtMap.get(longnumber));
            				//历次付款的合计金额
            				row.getCell("paymentDetails").setValue(getValue(sumPaySplitAmount));
            				//目标控制金额等于目标成本-累计付款金额
            				row.getCell("aimCtrlAmount").setValue(getValue(aimCostAmount.subtract(sumPaySplitAmount)));
            				//等于累计计价金额-累计付款金额
            				row.getCell("NMPayAmtDiff").setValue(getValue(nodeMeasureAmount.subtract(sumPaySplitAmount)));
            				
        				}
        			}
        		}
    		}else{
    			for(Iterator it=accountList.iterator();it.hasNext();){
        			AccountViewInfo accountViewInfo = (AccountViewInfo)it.next();
        			if(accountViewInfo!=null
        					&&accountViewInfo.getId()!=null){
        				String accountId = accountViewInfo.getId().toString();
        				String longnumber = accountViewInfo.getLongNumber();
        				IRow row = tblMain.addRow();
        				row.setUserObject(accountViewInfo);
        				row.setTreeLevel(0);
        				row.getCell("accountNumber").setValue(accountViewInfo.getNumber());
        				row.getCell("accountName").setValue(accountViewInfo.getName());
        				if(accountAmountsMap.containsKey(accountViewInfo.getId().toString())||(accountPayAmtMap!=null&&accountPayAmtMap.containsKey(longnumber))){
        					Map accountAmountMap = (Map)accountAmountsMap.get(accountId);
        					// 目标成本
        					BigDecimal aimCostAmount = FDCHelper.ZERO;
        					BigDecimal nodeMeasureAmount = FDCHelper.ZERO;
        					if(accountAmountMap!=null){
        						aimCostAmount = FDCHelper.toBigDecimal(accountAmountMap.get("aimCost"));
                				row.getCell("aimCost").setValue(getValue(aimCostAmount));
                				//累计节点计价金额
                				nodeMeasureAmount = FDCHelper.toBigDecimal(accountAmountMap.get("sumNMAmount"));
                				row.getCell("sumNMAmount").setValue(getValue(nodeMeasureAmount));
                				
                				
        					}
        					//历次付款金额
            				//历次付款的合计金额
        					BigDecimal sumPaySplitAmount = FDCHelper.ZERO;
            				if(accountPayAmtMap!=null&&accountPayAmtMap.containsKey(longnumber))
            					sumPaySplitAmount = FDCHelper.toBigDecimal(accountPayAmtMap.get(longnumber));
            				else if(payList!=null){
            					for(int i=0;i<payList.size();i++){
            						Map pay = (Map)payList.get(i);
            						String payid = (String)pay.get("payid");
            						BigDecimal paySplitAmount = FDCHelper.toBigDecimal(accountAmountMap.get(payid));
            						sumPaySplitAmount = sumPaySplitAmount.add(paySplitAmount);
            						row.getCell(payid).setValue(getValue(paySplitAmount));
            					}
            				}
            				//历次付款的合计金额
            				row.getCell("paymentDetails").setValue(getValue(sumPaySplitAmount));
            				//目标控制金额等于目标成本-累计付款金额
            				row.getCell("aimCtrlAmount").setValue(getValue(aimCostAmount.subtract(sumPaySplitAmount)));
            				//等于累计计价金额-累计付款金额
            				row.getCell("NMPayAmtDiff").setValue(getValue(nodeMeasureAmount.subtract(sumPaySplitAmount)));
            				
        				}
        			}
        		}
    		}
    		
    	}
	}    
    private void setParentRow(Map unionCellMap, CostAccountInfo costAccountInfo, IRow row) {
		if(costAccountInfo.getParent()!=null&&unionCellMap.containsKey(costAccountInfo.getParent().getId().toString())){
			IRow parentRow = (IRow)unionCellMap.get(costAccountInfo.getParent().getId().toString());
			BigDecimal parentAimCost = FDCHelper.toBigDecimal(row.getCell("aimCost").getValue()).add(FDCHelper.toBigDecimal(parentRow.getCell("aimCost").getValue()));
			parentRow.getCell("aimCost").setValue(getValue(parentAimCost));
			BigDecimal parentNodeMeasureAmount = FDCHelper.toBigDecimal(row.getCell("sumNMAmount").getValue()).add(FDCHelper.toBigDecimal(parentRow.getCell("sumNMAmount").getValue()));
			parentRow.getCell("sumNMAmount").setValue(getValue(parentNodeMeasureAmount));
			BigDecimal parentPaymentDetails = FDCHelper.ZERO;
			//累计付款金额
			if(payList==null){
				parentPaymentDetails=FDCHelper.toBigDecimal(row.getCell("paymentDetails").getValue()).add(FDCHelper.toBigDecimal(parentRow.getCell("paymentDetails").getValue()));
				parentRow.getCell("paymentDetails").setValue(getValue(parentPaymentDetails));
			}
			
			if(payList!=null){
				for(int i=0;i<payList.size();i++){
					Map pay = (Map)payList.get(i);
					String payid = (String)pay.get("payid");
					BigDecimal parentPaySplitAmount = FDCHelper.toBigDecimal(row.getCell(payid).getValue()).add(FDCHelper.toBigDecimal(parentRow.getCell(payid).getValue()));
					parentPaymentDetails = parentPaymentDetails.add(parentPaySplitAmount);
					parentRow.getCell(payid).setValue(getValue(parentPaySplitAmount));
				}
				parentRow.getCell("paymentDetails").setValue(getValue(parentPaymentDetails));
			}
			//目标控制金额等于目标成本-累计付款金额
			parentRow.getCell("aimCtrlAmount").setValue(getValue(parentAimCost.subtract(parentPaymentDetails)));
			//等于累计计价金额-累计付款金额
			parentRow.getCell("NMPayAmtDiff").setValue(getValue(parentNodeMeasureAmount.subtract(parentPaymentDetails)));
			if(parentRow.getUserObject() instanceof CostAccountInfo){
				costAccountInfo = (CostAccountInfo) parentRow.getUserObject();
				setParentRow(unionCellMap,costAccountInfo,row);
			}
		}else{
			return;
		}
	}
    private Object getValue(BigDecimal value){
    	//return value;
    	return value.compareTo(FDCHelper.ZERO)!=0?value:null;
    	
    }
    /**
     * 明细节点取数
     */
	protected void fetchLeafData() throws Exception{
		String prjId = getSelectObjId();
		if(prjId==null)
			return;
		Map param = new HashMap();
		
		param.put("prjId",prjId);
		Map resultMap = FDCCostRptFacadeFactory.getRemoteInstance().getPaymentCostStatistics(param);
		//会计科目
		accountList = (List)resultMap.get("accountList");
		//成本科目
		costAccountList = (List) resultMap.get("costAccountList");
		//会计科目与成本科目的对应关系
		costAccountWithAccountMapAll = (Map)resultMap.get("costAccountWithAccountMapAll");
		//目标成本
		aimCostMap = (Map)resultMap.get("aimCostMap");
		
		//节点计价
		nodeMeasureMap = (Map)resultMap.get("nodeMeasureMap");
		
		//付款记录
		payList = (List) resultMap.get("payList");
		
		//归属付款金额
		paySplitAmtMap = (Map) resultMap.get("paySplitAmtMap");
		accountAmtMap = null;
		
		if(costAccountWithAccountMapAll.isEmpty()){
			FDCMsgBox.showInfo(this,"没有找到成本科目与会计科目的对应关系！");
		}
	}
	/**
	 * 非明细节点取数
	 * @author ling_peng
	 */
	protected void fetchNonLeafData() throws Exception{
		//selectedId :可能是组织ID，也有可能是工程项目的ID。
		String selectedId=getSelectObjId();
		Object obj = getSelectObj();
		if(selectedId==null){
			return;
		}
		Set leafPrjIds=getSelectObjLeafIds();		
		Map paramMap = new HashMap();
		paramMap.put("leafPrjIds",leafPrjIds);
		paramMap.put("selectedObj", obj);
		
		Map resultMap = FDCCostRptFacadeFactory.getRemoteInstance().getNonLeafPaymentCostStatistics(paramMap);
		
		accountList = (List)resultMap.get("accountList");//会计科目
		costAccountList = (List) resultMap.get("costAccountList");//成本科目
		costAccountWithAccountMapAll = (Map)resultMap.get("costAccountWithAccountMapAll");//会计科目与成本科目的对应关系
		aimCostMap = (Map)resultMap.get("aimCostMap");//目标成本
		nodeMeasureMap = (Map)resultMap.get("nodeMeasureMap");//节点计价
		payList = (List) resultMap.get("payList");//付款记录
		paySplitAmtMap = (Map) resultMap.get("paySplitAmtMap");//归属付款金额
		accountAmtMap = (Map) resultMap.get("accountAmtMap");//会计科目金额
		//   by  sxhong 
		if(costAccountWithAccountMapAll.isEmpty()){
			FDCMsgBox.showInfo(this,"没有找到成本科目与会计科目的对应关系！");
		}
	}
	//标识是否是第一次加载该UI
	private boolean isFirstLoad=true;
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		
		//第一次加载UI的时候如果选中的节点是非明细工程项目或者是组织()，只初始化一个表头，其他啥都 不显示。
		if(isFirstLoad&&(treeMain==null||treeMain.getRowCount()>1||getSelectObj() instanceof FullOrgUnitInfo||getSelectObj() instanceof OrgStructureInfo)) {
			isFirstLoad=false;
			initTable();
			return;
		}
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		
		//如果是非明细项目
		if(!isSelectLeafPrj()){
			fetchNonLeafData();
			initTable();
			fillTable();
		}else{
			fetchLeafData();
			initTable();
			fillTable();
		}
	}

	
	/**
	 * 汇总
	 */
	protected void setUnionData(){
		
	}
	/**
	 * 双击显示全项目动态成本信息
	 * @author ling_peng
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
		//只有明细节点"双击显示全项目动态成本信息"才有效
		if(isSelectLeafPrj()){
			if (e.getClickCount() == 2) {

				int rowIndex = e.getRowIndex();

				if(getMainTable().getRow(rowIndex)==null)
					return;
				
				Object value = getMainTable().getRow(rowIndex).getUserObject();
				if (value == null)
					return;
				if(value instanceof CostAccountInfo){			//如果是成本科目
					CostAccountInfo acctInfo = (CostAccountInfo) value;
					
					boolean b = acctInfo.isIsLeaf();
					if (!b)
						return;
					this.setCursorOfWair();
					Map map = new HashMap();
					//此处必须还要传递一些值，动态成本信息才能正确显示出来。
					map.put("acctId", acctInfo.getId().toString());
					map.put("FullDyDetailInfoTitle","全项目动态成本详细信息");
					FullDyDetailInfoUI.showDialogWindows((IUIObject)this,acctInfo,FDCHelper.ZERO);
					this.setCursorOfDefault();
				}else if(value instanceof AccountViewInfo){//如果是会计科目
					//......  do nothing ....
				}
			}

		}
    }
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }
	protected String getKeyFieldName()
    {
        return "accountNumber";
    }
	private void setMenuToolBarState(){
		this.btnAddNew.setEnabled(false);
		this.btnAddNew.setVisible(false);
		
		this.btnView.setEnabled(false);
		this.btnView.setVisible(false);
		
		this.btnRemove.setEnabled(false);
		this.btnRemove.setVisible(false);
		
		this.btnEdit.setEnabled(false);
		this.btnEdit.setVisible(false);
		
		this.btnLocate.setEnabled(false);
		this.btnLocate.setVisible(false);
		
//		this.btnPrint.setEnabled(false);
//		this.btnPrint.setVisible(false);
		
//		this.btnPrintPreview.setEnabled(false);
//		this.btnPrintPreview.setVisible(false);
		
		this.btnQuery.setEnabled(false);
		this.btnQuery.setVisible(false);
		
		this.menuEdit.setEnabled(false);
		this.menuEdit.setVisible(false);
		
		this.menuView.setEnabled(false);
		this.menuView.setVisible(false);
		
		this.menuItemAddNew.setEnabled(false);
		this.menuItemAddNew.setVisible(false);
		
		this.menuItemPrint.setEnabled(false);
		this.menuItemPrint.setVisible(false);
		
		this.menuItemPrintPreview.setEnabled(false);
		this.menuItemPrintPreview.setVisible(false);
		
		
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		setMenuToolBarState();
		
	}
	public int getRowCountFromDB() {
//		super.getRowCountFromDB();
		return -1;
	}
}