/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 描述：未月结明细
 */
public class CostCloseDetailUI extends AbstractCostCloseDetailUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostCloseDetailUI.class);
    
    private Map unCloseDetail = new HashMap();
    private String periodNumber = "";
    private String projectId = "";
    private String item = "";
    private boolean isCost = true;
    /**
     * output class constructor
     */
    public CostCloseDetailUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }
    
    public void onLoad() throws Exception {
    	getMainTable().checkParsed();
    	getMainTable().setEnabled(false);
    	super.onLoad();
//    	getMainTable().getColumn("number").setWidth(150);
//    	getMainTable().getColumn("name").setWidth(150);
//    	getMainTable().getColumn("conNumber").setWidth(150);
//    	getMainTable().getColumn("conName").setWidth(150);
    	Object obj = getUIContext().get("item");
    	if(obj!=null){
    		item = obj.toString();
    	}
    	obj = getUIContext().get("projectId");
    	if(obj!=null){
    		projectId=obj.toString();
    	}
    	obj = getUIContext().get("periodNumber");
    	if(obj!=null){
    		periodNumber=obj.toString();
    	}
    	obj = getUIContext().get("isCost");
    	if(obj!=null){
    		isCost=Boolean.valueOf(obj.toString()).booleanValue();
    	}
    	fetchData();
    	fillTable();
    	this.btnAddNew.setVisible(false);
    	this.btnView.setVisible(false);
    	this.btnRemove.setVisible(false);
    	this.btnPrint.setVisible(false);
    	this.btnPrintPreview.setVisible(false);
    	this.btnAttachment.setVisible(false);
    	this.btnEdit.setVisible(false);
    	this.btnLocate.setVisible(false);
    	this.btnQuery.setVisible(false);
//    	this.btnRefresh.setVisible(false);
    	
    }
    
    private void fillTable() {
    	IRow row = null;
    	Object[] detailInfo = null;
    	for(Iterator iter = unCloseDetail.keySet().iterator();iter.hasNext();){
    		Object key = iter.next();
    		detailInfo = (Object[])unCloseDetail.get(key);
    		row = getMainTable().addRow();
    		row.getCell("number").setValue(detailInfo[0]);
    		row.getCell("name").setValue(detailInfo[1]);
    		row.getCell("state").setValue(detailInfo[2]);
    		row.getCell("splitState").setValue(detailInfo[3]);
    		row.getCell("conNumber").setValue(detailInfo[4]);
    		row.getCell("conName").setValue(detailInfo[5]);
    		row.getCell("id").setValue(detailInfo[6]);
    		row.getCell("contract.id").setValue(detailInfo[7]);
    		
    	}
    	
	}

	/**
     * 后续优化并放在服务端一次性取数 
     * 后续再支持query，支持绑定，支持排序
	 * @throws BOSException 
	 * @throws SQLException 
     */
    private void fetchData() throws BOSException, SQLException{
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	boolean isCon = false;
    	if(isCost){
	    	if("con".equals(item)){
	    		isCon = true;
	    		builder.appendSql(" select bill.fid as id,bill.fnumber as number ,bill.fname as name,bill.fstate as state,split.fsplitstate splitState,bill.fid conId ,bill.fnumber conNumber,bill.fname conName from t_con_contractbill bill \n");
	    		builder.appendSql(" inner join t_bd_period period on period.fid=bill.fperiodid \n");
	    		builder.appendSql(" left outer join t_con_contractcostsplit split on split.fcontractbillid=bill.fid \n");
	    		builder.appendSql(" where (bill.fstate = '1SAVED' or bill.fstate='2SUBMITTED' or bill.fstate ='4AUDITTED' or split.fsplitstate is null) and period.fnumber<=? \n");
				builder.addParam(periodNumber);
				builder.appendSql(" and \n");
				builder.appendParam("bill.FCurProjectID",projectId);
				builder.appendSql(" and \n");
				builder.appendParam("bill.fiscostsplit",Boolean.TRUE);
				builder.appendSql(" and (split.fstate <> '9INVALID' or split.fstate ='4AUDITTED' or split.fstate='1SAVED' or split.fstate is null) \n");
				builder.appendSql(" and split.fid not in ( \n");
				builder.appendSql("    select closee.fobjectid from t_fnc_settledmonthly closee where closee.fcurprojectid=split.FCurProjectID) \n");
				
	    	}else if("change".equals(item)){
				builder.appendSql(" select bill.fid as id,bill.fnumber as number ,bill.fname as name,bill.fstate as state,split.fsplitstate splitState,con.fid conId ,con.fnumber conNumber,con.fname conName from t_con_contractchangebill bill \n");
				builder.appendSql(" inner join t_bd_period period on period.fid=bill.fperiodid \n");
				builder.appendSql(" inner join t_con_contractbill con on con.fid=bill.fcontractbillid \n");
				builder.appendSql(" left outer join  t_con_conchangesplit split on bill.fid=split.FContractChangeID \n");
				builder.appendSql(" where (bill.fstate = '1SAVED' or bill.fstate='2SUBMITTED' or bill.fstate ='4AUDITTED' or bill.fstate ='7ANNOUNCE' or bill.fstate ='8VISA' or split.fsplitstate is null) and period.fnumber<=? \n");
				builder.addParam(periodNumber);
				builder.appendSql(" and \n");
				builder.appendParam("bill.FCurProjectID",projectId);
				builder.appendSql(" and \n");
				builder.appendParam("con.fiscostsplit",Boolean.TRUE);
				builder.appendSql(" and (split.fstate <> '9INVALID' or split.fstate ='4AUDITTED' or split.fstate='1SAVED' or split.fstate is null) \n");
				builder.appendSql(" and split.fid not in ( \n");
				builder.appendSql("    select closee.fobjectid from t_fnc_settledmonthly closee where closee.fcurprojectid=split.FCurProjectID) \n");
	    	}else if("settle".equals(item)){
				builder.appendSql(" select bill.fid as id,bill.fnumber as number ,bill.fname as name,bill.fstate as state,split.fsplitstate splitState,con.fid conId ,con.fnumber conNumber,con.fname conName from t_con_contractsettlementbill bill \n");
				builder.appendSql(" inner join t_bd_period period on period.fid=bill.fperiodid \n");
				builder.appendSql(" inner join t_con_contractbill con on con.fid=bill.fcontractbillid \n");
				builder.appendSql(" left outer join T_CON_SettlementCostSplit split on bill.fid=split.FSettlementBillID \n");
				builder.appendSql(" where (bill.fstate = '1SAVED' or bill.fstate='2SUBMITTED' or bill.fstate ='4AUDITTED' or split.fsplitstate is null) and period.fnumber<=? \n");
				builder.addParam(periodNumber);
				builder.appendSql(" and \n");
				builder.appendParam("bill.FCurProjectID",projectId);
				builder.appendSql(" and \n");
				builder.appendParam("con.fiscostsplit",Boolean.TRUE);
				builder.appendSql(" and (split.fstate <> '9INVALID' or split.fstate ='4AUDITTED' or split.fstate='1SAVED' or split.fstate is null) \n");
				builder.appendSql(" and split.fid not in ( \n");
				builder.appendSql("    select closee.fobjectid from t_fnc_settledmonthly closee where closee.fcurprojectid=split.FCurProjectID) \n");
	    	}else if("workload".equals(item)){
				builder.appendSql(" select bill.fid as id,bill.fnumber as number ,'' as name,bill.fstate as state,split.fsplitstate splitState,con.fid conId ,con.fnumber conNumber,con.fname conName from t_fnc_workloadconfirmbill bill \n");
				builder.appendSql(" inner join t_bd_period period on period.fid=bill.fperiodid \n");
				builder.appendSql(" inner join t_con_contractbill con on con.fid=bill.fcontractbillid \n");
				builder.appendSql(" left outer join t_fnc_paymentsplit split on bill.fid=split.fworkloadbillid \n");
				builder.appendSql(" where (bill.fstate = '1SAVED' or bill.fstate='2SUBMITTED' or bill.fstate ='4AUDITTED' or split.fsplitstate is null) and period.fnumber<=? \n");
				builder.addParam(periodNumber);
				builder.appendSql(" and \n");
				builder.appendParam("bill.FCurProjectID",projectId);
				builder.appendSql(" and \n");
				builder.appendParam("con.fiscostsplit",Boolean.TRUE);
				builder.appendSql(" and (split.fstate <> '9INVALID' or split.fstate ='4AUDITTED' or split.fstate='1SAVED' or split.fstate is null) \n");
				builder.appendSql(" and split.fid not in ( \n");
				builder.appendSql("    select closee.fobjectid from t_fnc_settledmonthly closee where closee.fcurprojectid=split.FCurProjectID) \n");
	    	}else if("conWithout".equals(item)){
	    		isCon = true;
				builder.appendSql(" select bill.fid as id,bill.fnumber as number ,bill.fname as name,bill.fstate as state,split.fsplitstate splitState,bill.fid conId ,bill.fnumber conNumber,bill.fname conName  from t_con_contractwithouttext bill \n");
				builder.appendSql(" inner join t_bd_period period on period.fid=bill.fperiodid \n");
				builder.appendSql(" left outer join t_fnc_paymentsplit split on split.FConWithoutTextID=bill.fid \n");
				builder.appendSql(" where (bill.fstate = '1SAVED' or bill.fstate='2SUBMITTED' or bill.fstate ='4AUDITTED' or bill.fstate ='7ANNOUNCE' or bill.fstate ='8VISA' or split.fsplitstate is null) and period.fnumber<=? \n");
				builder.addParam(periodNumber);
				builder.appendSql(" and \n");
				builder.appendParam("bill.FCurProjectID",projectId);
				builder.appendSql(" and \n");
				builder.appendParam("bill.fiscostsplit",Boolean.TRUE);
				builder.appendSql(" and (split.fstate <> '9INVALID' or split.fstate ='4AUDITTED' or split.fstate='1SAVED' or split.fstate is null) \n");
				builder.appendSql(" and split.fid not in ( \n");
				builder.appendSql("    select closee.fobjectid from t_fnc_settledmonthly closee where closee.fcurprojectid=split.FCurProjectID) \n");
	    	}
    	}else{
    		if("paySplit".equals(item)){
    			builder.appendSql(" select bill.fid as id,bill.fnumber as number ,'' as name,bill.fbillstatus as state,split.fsplitstate splitState,con.fid conId ,con.fnumber conNumber,con.fname conName from t_cas_paymentbill bill \n"); 
    			builder.appendSql(" inner join t_con_contractbill con on con.fid=bill.fcontractbillid \n");
    			builder.appendSql(" inner join t_con_payrequestbill req on req.fid=bill.ffdcpayreqid \n");
    			builder.appendSql(" inner join t_bd_period period on period.fid=req.fperiodid \n");
    			builder.appendSql(" left outer join t_fnc_paymentsplit split on split.fpaymentbillid=bill.fid \n");
    			builder.appendSql(" where (bill.fbillstatus =10 or bill.fbillstatus=11 or bill.fbillstatus =12 or bill.fbillstatus =15 or split.fsplitstate is null) \n");
    			builder.appendSql("	and  period.fnumber<=? \n");
    			builder.addParam(periodNumber);
    			builder.appendSql("	 and \n");
    			builder.appendParam("bill.FCurProjectID",projectId);
    			builder.appendSql("	 and \n");
    			builder.appendParam("con.fiscostsplit",Boolean.TRUE);
    			builder.appendSql(" and (split.fstate <> '9INVALID' or split.fstate ='4AUDITTED' or split.fstate='1SAVED' or split.fstate is null) \n");
    			builder.appendSql("  and split.fid not in (  \n");
    			builder.appendSql("  select closee.fobjectid from t_fnc_settledmonthly closee where closee.fcurprojectid=split.FCurProjectID) \n"); 
    		}else if("payNoTextSplit".equals(item)){
    			builder.appendSql(" select bill.fid as id,bill.fnumber as number ,'' as name,bill.fbillstatus as state,split.fsplitstate splitState,con.fid conId ,con.fnumber conNumber,con.fname conName from t_cas_paymentbill bill \n"); 
    			builder.appendSql("  inner join t_con_contractwithouttext con on con.fid=bill.fcontractbillid \n");
    			builder.appendSql("  inner join t_con_payrequestbill req on req.fid=bill.ffdcpayreqid  \n");
    			builder.appendSql("  inner join t_bd_period period on period.fid=req.fperiodid \n");
    			builder.appendSql("  left outer join t_fnc_paymentsplit split on split.fpaymentbillid=bill.fid  \n");
    			builder.appendSql("  where (bill.fbillstatus =10 or bill.fbillstatus=11 or bill.fbillstatus =12 or bill.fbillstatus =15 or split.fsplitstate is null) \n");
    			builder.appendSql("	and  period.fnumber<=? \n");
    			builder.addParam(periodNumber);
    			builder.appendSql("	 and \n");
    			builder.appendParam("bill.FCurProjectID",projectId);
    			builder.appendSql("	 and \n");
    			builder.appendParam("con.fiscostsplit",Boolean.TRUE);
    			builder.appendSql(" and (split.fstate <> '9INVALID' or split.fstate ='4AUDITTED' or split.fstate='1SAVED' or split.fstate is null) \n");
    			builder.appendSql("  and split.fid not in (  \n");
    			builder.appendSql("     select closee.fobjectid from t_fnc_settledmonthly closee where closee.fcurprojectid=split.FCurProjectID) \n"); 
    		}else if("invalidCon".equals(item)){
    			isCon = true;
    			builder.appendSql("  select bill.fid as id,bill.fnumber as number ,bill.fname as name,bill.fstate as state,split.fsplitstate splitState,bill.fid conId ,bill.fnumber conNumber,bill.fname conName from t_con_contractbill bill \n");
    			builder.appendSql(" inner join t_con_contractcostsplit split on split.fcontractbillid=bill.fid \n");
    			builder.appendSql("  inner join t_bd_period period on period.fid=bill.fperiodid \n");
    			builder.appendSql("	and  period.fnumber<=? \n");
    			builder.addParam(periodNumber);
    			builder.appendSql("  and bill.FConSplitExecState='INVALID' \n");
    			builder.appendSql("  and \n");
    			builder.appendParam("bill.FCurProjectID",projectId);
    			builder.appendSql("	 and \n");
    			builder.appendParam("bill.fiscostsplit",Boolean.TRUE);
    		}else if("invalidConWithout".equals(item)){
    			isCon = true;
    			builder.appendSql("  select bill.fid as id,bill.fnumber as number ,bill.fname as name,bill.fstate as state,split.fsplitstate splitState,bill.fid conId ,bill.fnumber conNumber,bill.fname conName  from t_con_contractwithouttext bill \n");
    			builder.appendSql(" inner join t_fnc_paymentsplit split on split.FConWithoutTextID=bill.fid \n");
    			builder.appendSql("  inner join t_bd_period period on period.fid=bill.fperiodid \n");
    			builder.appendSql("  where period.fnumber<=? \n");
    			builder.addParam(periodNumber);
    			builder.appendSql("  and bill.FConSplitExecState='INVALID'\n");
    			builder.appendSql("  and \n");
    			builder.appendParam("bill.FCurProjectID",projectId);
    			builder.appendSql("	 and \n");
    			builder.appendParam("bill.fiscostsplit",Boolean.TRUE);
    		}
    	}
    	IRowSet rs = builder.executeQuery();
    	Object[] detailInfo = null;
    	
    	while(rs.next()){
    		detailInfo = new Object[8];
    		detailInfo[0]= rs.getString("number");
    		detailInfo[1]= rs.getString("name");
    		String state = rs.getString("state");
    		detailInfo[2] = getStateName(state);
    		if(!item.startsWith("invalid")){
    			detailInfo[3] = getSplitStateName(state,rs.getString("splitState"));
    		}
    		if(!isCon){
    			detailInfo[4] = rs.getString("conNumber");
    			detailInfo[5] = rs.getString("conName");
    		}
    		
    		detailInfo[6]= rs.getString("id");
    		detailInfo[7] = rs.getString("conId");
    		unCloseDetail.put(rs.getString("id"), detailInfo);
    	}
    	
    	
    	
    }
    //有没有更好的办法?
    private Object getStateName(String state) {
    	
		if (FDCBillStateEnum.SAVED_VALUE.equals(state) || "10".equals(state)) {
			return "保存";
		} else if (FDCBillStateEnum.SUBMITTED_VALUE.equals(state)
				|| "11".equals(state)
				|| FDCBillStateEnum.AUDITTING_VALUE.equals(state)) {
			return "已提交";
		} else if (FDCBillStateEnum.AUDITTED_VALUE.equals(state)
				|| "12".equals(state)) {
			return "已审批";
		} else if ("15".equals(state)) {
			return "已付款";
		} else if (FDCBillStateEnum.ANNOUNCE_VALUE.equals(state)) {
			return "已下发";
		} else if (FDCBillStateEnum.VISA_VALUE.equals(state)) {
			return "已签证";
		}
		return null;
	}
    private Object getSplitStateName(String state,String splitState){
    	if(CostSplitStateEnum.ALLSPLIT_VALUE.equals(splitState)){
			return "全部拆分";
		}else if(CostSplitStateEnum.PARTSPLIT_VALUE.equals(splitState)){
			return "部分拆分";
		}else if(splitState==null && (state.equals(FDCBillStateEnum.AUDITTED_VALUE) || "12".equals(state))){
			return "未拆分";
		}
    	return "";
    }
	public boolean destroyWindow() {
    	unCloseDetail.clear();
    	unCloseDetail=null;
    	item=null;
    	return super.destroyWindow();
    }
    
	public KDTable getMainTable() {
		return super.getMainTable();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if(e==null||e.getClickCount()==2){
			return;
		}
		super.tblMain_tableClicked(e);
	}
	protected void refresh(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
//		super.refresh(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}
}