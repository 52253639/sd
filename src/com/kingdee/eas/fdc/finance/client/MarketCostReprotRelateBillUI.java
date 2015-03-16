/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.client.ContractBillListUI;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextListUI;
import com.kingdee.eas.fdc.contract.client.PayRequestFullListUI;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SignManageListUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;

/**
 * output class name
 */
public class MarketCostReprotRelateBillUI extends AbstractMarketCostReprotRelateBillUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketCostReprotRelateBillUI.class);
    public MarketCostReprotRelateBillUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		Window win = SwingUtilities.getWindowAncestor(this);
        LongTimeDialog dialog = null;
        if(win instanceof Frame){
        	dialog = new LongTimeDialog((Frame)win);
        }else if(win instanceof Dialog){
        	dialog = new LongTimeDialog((Dialog)win);
        }
        if(dialog==null){
        	dialog = new LongTimeDialog(new Frame());
        }
        dialog.setLongTimeTask(new ILongTimeTask() {
            public Object exec()throws Exception{
        		String number=((String)getUIContext().get("number"));
        		String orgUnit=(String)getUIContext().get("orgUnit");
        		boolean isAll=((Boolean)getUIContext().get("isAll")).booleanValue();
        		String year=(String)getUIContext().get("year");
        		String month=(String)getUIContext().get("month");
        		String type=(String)getUIContext().get("type");
            	
        		if(type.equals("contract")){
        			StringBuilder sb = new StringBuilder();
        			sb.append(" select contract.fid id from T_CON_ContractCostSplitEntry contractSplitEntry left join T_FDC_CostAccount costAccount on costAccount.fid=contractSplitEntry.fcostAccountId");
        			sb.append(" left join T_CON_ContractCostSplit contractSplit on contractSplit.fid=contractSplitEntry.fparentid");
        			sb.append(" left join t_con_contractBill contract on contract.fid=contractSplit.fcontractBillId left join T_FDC_CurProject curProject on contract.fcurProjectId=curProject.fid left join T_ORG_BaseUnit orgUnit on orgUnit.fid=curProject.ffullOrgUnit where contract.fstate='4AUDITTED' and contract.fauditTime is not null and costAccount.fname_l2 is not null");
        	    	sb.append(" and REPLACE(costAccount.flongNumber, '!', '.') like '"+number+"%' and orgUnit.flongNumber like '"+orgUnit+"%' and year(contract.fauditTime)="+year);
        	    	if(!isAll){
        	    		sb.append(" and month(contract.fauditTime)="+month);
        	    	}
        	    	
        	    	FilterInfo filter=new FilterInfo();
        	    	filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
        	    	
        			KDScrollPane panel=new KDScrollPane();
        			panel.setMinimumSize(new Dimension(1013,629));		
        			panel.setPreferredSize(new Dimension(1013,629));
        	        kDTabbedPane1.add(panel,"合同");
        	        
        	        UIContext uiContext = new UIContext(this);
        			uiContext.put("filter", filter);
        	        
        			ContractBillListUI ui = (ContractBillListUI) UIFactoryHelper.initUIObject(ContractBillListUI.class.getName(), uiContext, null,OprtState.VIEW);
        			panel.setViewportView(ui);
        			panel.setKeyBoardControl(true);
        			panel.setEnabled(false);
        			
        			sb = new StringBuilder();
        			sb.append(" select distinct contract.fid id from T_CON_CWTextBgEntry contractSplitEntry left join T_BC_ExpenseType expenseType on expenseType.fid=contractSplitEntry.fexpenseTypeId left join T_FDC_CostAccount costAccount on costAccount.fname_l2=expenseType.fname_l2");
        			sb.append(" left join t_con_contractWithoutText contract on contract.fid=contractSplitEntry.fheadId left join T_ORG_BaseUnit orgUnit on orgUnit.fid=contract.FCostedCompanyId where contract.fstate='4AUDITTED' and contract.fauditTime is not null and costAccount.fname_l2 is not null and costAccount.FFullOrgUnit='00000000-0000-0000-0000-000000000000CCE7AED4' and costAccount.fcurproject is null");
        	    	sb.append(" and REPLACE(costAccount.flongNumber, '!', '.') like '"+number+"%' and orgUnit.flongNumber like '"+orgUnit+"%' and year(contract.fauditTime)="+year);
        	    	if(!isAll){
        	    		sb.append(" and month(contract.fauditTime)="+month);
        	    	}
        	    	
        	    	filter=new FilterInfo();
        	    	filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
        	    	
        			KDScrollPane noPanel=new KDScrollPane();
        			noPanel.setMinimumSize(new Dimension(1013,629));		
        			noPanel.setPreferredSize(new Dimension(1013,629));
        	        kDTabbedPane1.add(noPanel,"无文本合同");
        		        
        	        uiContext = new UIContext(this);
        			uiContext.put("filter", filter);
        	        
        			ContractWithoutTextListUI noUi = (ContractWithoutTextListUI) UIFactoryHelper.initUIObject(ContractWithoutTextListUI.class.getName(), uiContext, null,OprtState.VIEW);
        			noPanel.setViewportView(noUi);
        			noPanel.setKeyBoardControl(true);
        			noPanel.setEnabled(false);
        		}else if(type.equals("pay")){
        			StringBuilder sb = new StringBuilder();
        			sb.append(" select distinct pay.FFdcPayReqID id from T_CON_PayRequestBillBgEntry contractSplitEntry left join T_BC_ExpenseType expenseType on expenseType.fid=contractSplitEntry.fexpenseTypeId  left join T_FDC_CostAccount costAccount on costAccount.fname_l2=expenseType.fname_l2");
        			sb.append(" left join T_CON_PayRequestBill payRequest on payRequest.fid=contractSplitEntry.fheadId left join t_con_contractBill contract on contract.fid =payRequest.fcontractId left join T_CAS_PaymentBill pay on pay.FFdcPayReqID=payRequest.fid left join T_ORG_BaseUnit orgUnit on orgUnit.fid=payRequest.FCostedCompanyId where pay.fbillstatus=15 and pay.FPayDate is not null and costAccount.fname_l2 is not null and costAccount.FFullOrgUnit='00000000-0000-0000-0000-000000000000CCE7AED4' and costAccount.fcurproject is null and contract.fid is not null");
        			sb.append(" and REPLACE(costAccount.flongNumber, '!', '.') like '"+number+"%' and orgUnit.flongNumber like '"+orgUnit+"%' and year(pay.FPayDate)="+year);
        	    	if(!isAll){
        	    		sb.append(" and month(pay.FPayDate)="+month);
        	    	}
        			sb.append(" union all select distinct pay.FFdcPayReqID id from T_CON_CWTextBgEntry contractSplitEntry left join T_BC_ExpenseType expenseType on expenseType.fid=contractSplitEntry.fexpenseTypeId  left join T_FDC_CostAccount costAccount on costAccount.fname_l2=expenseType.fname_l2");
        			sb.append(" left join t_con_contractWithoutText contract on contract.fid=contractSplitEntry.fheadId left join T_CAS_PaymentBill pay on pay.fcontractBillid=contract.fid left join T_ORG_BaseUnit orgUnit on orgUnit.fid=contract.FCostedCompanyId where pay.fbillstatus=15 and pay.FPayDate is not null and costAccount.fname_l2 is not null and costAccount.FFullOrgUnit='00000000-0000-0000-0000-000000000000CCE7AED4' and costAccount.fcurproject is null");
        			sb.append(" and REPLACE(costAccount.flongNumber, '!', '.') like '"+number+"%' and orgUnit.flongNumber like '"+orgUnit+"%' and year(pay.FPayDate)="+year);
        	    	if(!isAll){
        	    		sb.append(" and month(pay.FPayDate)="+month);
        	    	}
        	    	
        	    	FilterInfo filter=new FilterInfo();
        	    	filter.getFilterItems().add(new FilterItemInfo("id",sb.toString(),CompareType.INNER));
        	    	
        			KDScrollPane panel=new KDScrollPane();
        			panel.setMinimumSize(new Dimension(1013,629));		
        			panel.setPreferredSize(new Dimension(1013,629));
        	        kDTabbedPane1.add(panel,"付款申请单");
        	        
        	        UIContext uiContext = new UIContext(this);
        			uiContext.put("filter", filter);
        	        
        			PayRequestFullListUI ui = (PayRequestFullListUI) UIFactoryHelper.initUIObject(PayRequestFullListUI.class.getName(), uiContext, null,OprtState.VIEW);
        			panel.setViewportView(ui);
        			panel.setKeyBoardControl(true);
        			panel.setEnabled(false);
        		}
            	return null;
            }
            public void afterExec(Object result)throws Exception{
            }
        }
        );
        dialog.show();
	}

}