/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RoomSignContractFilterUI extends AbstractRoomSignContractFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomSignContractFilterUI.class);

    private static final String IS_RECEIVE_HOUSEAMOUNT = "isReceiveHouseAmount";
	/**
     * output class constructor
     */
    public RoomSignContractFilterUI() throws Exception
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
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	chkAll.setSelected(true);
    	chbIsReceiveHouseAmount.setVisible(false);
    }
    
    public void clear() {
    	super.clear();
    }

    public CustomerParams getCustomerParams() {
    	FDCCustomerParams param = new FDCCustomerParams();
    	param.add(IS_RECEIVE_HOUSEAMOUNT, this.chbIsReceiveHouseAmount.isSelected());
    	return param.getCustomerParams();
    }
    
    
    public Set findByIsSelected(){
    	Set set = new HashSet();
    	
    	FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
    	if(para.getBoolean(IS_RECEIVE_HOUSEAMOUNT)){
    		FDCSQLBuilder builder = new FDCSQLBuilder();
    		String sql = "select contract.fid as id, sum(pay.FActPayAmount) as amount from t_she_purchase pur "+ 
						 "left outer join t_she_purchasepaylistentry pay on  pur.FID = pay.FHeadID "+
						 "left outer join t_she_moneydefine money on pay.FMoneyDefineID = money.FID "+
						 "left outer join t_she_roomsigncontract contract on  pur.FID = contract.FPurchaseID "+ 
						 "where  (money.FMoneyType='HouseAmount' or money.FMoneyType='FisrtAmount') "+
						 "group by contract.fid";
    		builder.appendSql(sql);
    		IRowSet rowSet = null;
    		try {
				rowSet = builder.executeQuery();
				while(rowSet.next()){
					if(rowSet.getString("id")!=null && !"".equals(rowSet.getString("id")) && rowSet.getBigDecimal("amount")!=null && rowSet.getBigDecimal("amount").compareTo(FDCHelper.ZERO)==1){
						set.add(rowSet.getString("id"));
					}
				}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return set;
    }
    
    protected void chbIsReceiveHouseAmount_itemStateChanged(ItemEvent e)
    		throws Exception {
    	super.chbIsReceiveHouseAmount_itemStateChanged(e);
    	if(chbIsReceiveHouseAmount.isSelected()){
    		chkAll.setSelected(false);
    	}else{
    		chkAll.setSelected(true);
    	}
    }
    
    protected void chkAll_itemStateChanged(ItemEvent e) throws Exception {
    	super.chkAll_itemStateChanged(e);
    	if(chkAll.isSelected()){
    		chbIsReceiveHouseAmount.setSelected(false);
    	}
    	if(!chbIsReceiveHouseAmount.isSelected()){
    		chkAll.setSelected(true);
    	}
    }
    
    public FilterInfo getFilterInfo() {
    	FilterInfo filter = new FilterInfo();
    	FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
    	if(para.getBoolean(IS_RECEIVE_HOUSEAMOUNT)){
	    	Set set = findByIsSelected();
	    	if(set != null && set.size() > 0){
	    		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INNER));
	    	}else{
	    		filter.getFilterItems().add(new FilterItemInfo("id", "111"));
	    	}
    	}
    	return filter;
    }
}