/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PaymentNoCostFullListUI extends AbstractPaymentNoCostFullListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PaymentNoCostFullListUI.class);
    
    /**
     * output class constructor
     */
    public PaymentNoCostFullListUI() throws Exception
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
     *  成本科目合同付款拆分
     */
    private static final int PAYMENTSPLIT=1;
    /**
     * 成本科目无文本合同付款拆分
     */
    private static final int PAYMENTSPLIT_WITHOUTTEXT=2;
    /**
     * 财务科目（非成本科目）合同付款拆分
     */
    private static final int PAYMENTNOCOSTSPLIT=3;
    /**
     * 财务科目（非成本科目）无文本合同付款拆分
     */
    private static final int PAYMENTNOCOSTSPLIT_WITHOUTTEXT=4;
    private static final BOSObjectType  withoutTxtConBosType=new ContractWithoutTextInfo().getBOSType();
    
	protected String getEditUIName() {		
		switch (getSplitType())
		{
		case PAYMENTSPLIT :
			return com.kingdee.eas.fdc.finance.client.PaymentSplitEditUI.class.getName();
		case PAYMENTSPLIT_WITHOUTTEXT :
			return com.kingdee.eas.fdc.finance.client.PaymentSplitWithoutTxtConEditUI.class.getName();
		case PAYMENTNOCOSTSPLIT :
			return com.kingdee.eas.fdc.finance.client.PaymentNoCostSplitEditUI.class.getName();
		case PAYMENTNOCOSTSPLIT_WITHOUTTEXT :
			return com.kingdee.eas.fdc.finance.client.PaymentNoCostSplitWithoutTxtConEditUI.class.getName();
		default:
			return null;
		}				
	}
	
	private int getSplitType(){
		KDTable table=getMainTable();
		boolean isConWithoutTxt=false;
		String contractId=null;
		int selectRows[]=KDTableUtil.getSelectedRows(table);
		if(selectRows.length==1){
			Object obj=table.getCell(selectRows[0], "contractId").getValue();
			if(obj!=null){
				contractId=obj.toString();
				isConWithoutTxt=BOSUuid.read(contractId).getType().equals(withoutTxtConBosType);
			}
		}
		
		if(contractId==null) return 0;
		if(isConWithoutTxt){
//			if(true) return PAYMENTSPLIT_WITHOUTTEXT;
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",contractId));
			filter.getFilterItems().add(new FilterItemInfo("isCostSplit",1+""));
			try
			{
				if(ContractWithoutTextFactory.getRemoteInstance().exists(filter)){
					return PAYMENTSPLIT_WITHOUTTEXT;
				}else{
					return PAYMENTNOCOSTSPLIT_WITHOUTTEXT;
				}
			}catch (Exception e){
				handUIException(e);
			}
		}else{
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",contractId));
			filter.getFilterItems().add(new FilterItemInfo("isCoseSplit",1+""));
			try
			{
				if(ContractBillFactory.getRemoteInstance().exists(filter)){
					return PAYMENTSPLIT;
				}else{
					return PAYMENTNOCOSTSPLIT;
				}
			} catch (Exception e)
			{
				handUIException(e);
			}
		}
		
		
		return 0;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return getRemoteInterface();
	}
	
	protected ICoreBase getRemoteInterface() throws BOSException {
		switch (getSplitType()){
//		switch (getSplit()){
			case PAYMENTSPLIT :
			case PAYMENTSPLIT_WITHOUTTEXT:
				return com.kingdee.eas.fdc.finance.PaymentSplitFactory.getRemoteInstance();
			
			case PAYMENTNOCOSTSPLIT :
			case PAYMENTNOCOSTSPLIT_WITHOUTTEXT:
				return com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory.getRemoteInstance();
			default :
				return com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory.getRemoteInstance();
		}
	}

	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		super.getRowSetBeforeFillTable(rowSet);
		try{
			rowSet.beforeFirst();
			while(rowSet.next()){
				String contractId=rowSet.getString("contractId");
				if(contractId!=null&&BOSUuid.read(contractId).getType().equals(withoutTxtConBosType)){
					rowSet.updateObject("isContractWithoutText", Boolean.TRUE);
				}
			}
			rowSet.beforeFirst();
		}catch (Exception e){
			handUIException(e);
		}
		
	}
		
	/**
	 * 为了实现BOTP调用，根据参数返回keyFieldName
	 */
	protected String getKeyFieldName() {
 
		if(isVoucherUse) return "id";
		
		else	return "PaymentNoCostSplit.id";
	}
	
	//是否凭证使用，在gainBillSet（）方法中用
	boolean isVoucherUse = false;
	
	public void gainBillSet(List list) throws Exception
    {
		//因为super.gainBillSet()方法要使用keyfieldname，所以这里设置参数
		isVoucherUse = true;
		super.gainBillSet(list, actionCreateTo);
        
		isVoucherUse = false; //还原
    }
	
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
}