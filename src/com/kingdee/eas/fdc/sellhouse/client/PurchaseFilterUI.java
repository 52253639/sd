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
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.PurchaseFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PurchaseFilterUI extends AbstractPurchaseFilterUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(PurchaseFilterUI.class);

	protected ItemAction actionListOnLoad;

	protected ListUI listUI;

	Context ctx = null;

	/**
	 * output class constructor
	 */
	public PurchaseFilterUI() throws Exception
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

	public void onLoad() throws Exception
	{
		super.onLoad();
		this.kDCheckBox5.setSelected(true);
	}

	public void boxItemStateChanged(int i)
	{
		switch (i)
		{
		case 1:
			this.kDCheckBox2.setSelected(false);
			this.kDCheckBox3.setSelected(false);
			this.kDCheckBox4.setSelected(false);
			this.kDCheckBox5.setSelected(false);
			findByChanged(2, true);
			break;
		case 2:
			this.kDCheckBox1.setSelected(false);
			this.kDCheckBox3.setSelected(false);
			this.kDCheckBox4.setSelected(false);
			this.kDCheckBox5.setSelected(false);
			findByChanged(2, false);
			break;
		case 3:
			this.kDCheckBox2.setSelected(false);
			this.kDCheckBox1.setSelected(false);
			this.kDCheckBox4.setSelected(false);
			this.kDCheckBox5.setSelected(false);
			findByChanged(1, true);
			break;
		case 4:
			this.kDCheckBox2.setSelected(false);
			this.kDCheckBox3.setSelected(false);
			this.kDCheckBox1.setSelected(false);
			this.kDCheckBox5.setSelected(false);
			findByChanged(1, false);
			break;
		case 5:
			this.kDCheckBox2.setSelected(false);
			this.kDCheckBox3.setSelected(false);
			this.kDCheckBox4.setSelected(false);
			this.kDCheckBox1.setSelected(false);
			findByChanged(3, false);
			break;

		default:
			break;
		}
	}

	Set set =new HashSet();

	/** 很失败的过滤，汗。。。*/
	public void findByChanged(int state, boolean i)
	{/*
		set.clear();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		String sql = "select a.fid as id,a.FPurchaseState as state,sum(c.FAmount) as sumAmount from T_SHE_Purchase as a left join T_SHE_FDCReceiveBill as b on a.fid=b.FPurchaseID left join T_CAS_ReceivingBill as c on b.Fid=c.FFdcReceiveBillID group by a.fid,a.FPurchaseState";
		builder.appendSql(sql);
		IRowSet rs = null;
		try
		{
			rs = builder.executeQuery();
		} catch (BOSException e)
		{
			MsgBox.showConfirm2("查询报错");
		}
		if (rs != null && rs.size() > 0)
		{
			try
			{
				while (rs.next())
				{
					String FPurchaseState = rs.getString("state");
					if (state == 1)
					{
						if (PurchaseStateEnum.PREPURCHASECHECK_VALUE.equals(FPurchaseState))
						{
							addSet(i, rs);
						}
					} else if (state == 2)
					{
						if (PurchaseStateEnum.PURCHASEAUDIT_VALUE.equals(FPurchaseState))
						{
							addSet(i, rs);
						}
					} else
					{
						set.add(rs.getString("id"));
					}
				}
			} catch (SQLException e)
			{
				MsgBox.showConfirm2("报错");
			}
		}
	*/}

	private void addSet(boolean i, IRowSet rs) throws SQLException
	{
		double FSumAmount = 0;
		FSumAmount = rs.getDouble("sumAmount");
		if (i)
		{
			if (FSumAmount > 0)
			{
				set.add(rs.getString("id"));
			}
		} else
		{
			if (FSumAmount == 0)
			{
				set.add(rs.getString("id"));
			}
		}
	}

	/**
	 * 认购已收款
	 */
	protected void kDCheckBox1_itemStateChanged(ItemEvent e) throws Exception
	{
		if (kDCheckBox1.isSelected())
		{
			boxItemStateChanged(1);
		}else{
			initKDCheckBox5();
		}
	}

	/**
	 * 认购未收款
	 */
	protected void kDCheckBox2_itemStateChanged(ItemEvent e) throws Exception
	{
		if (kDCheckBox2.isSelected())
		{
			boxItemStateChanged(2);
		}else{
			initKDCheckBox5();
		}
	}

	/**
	 * 预定已收款
	 */
	protected void kDCheckBox3_itemStateChanged(ItemEvent e) throws Exception
	{
		if (kDCheckBox3.isSelected())
		{
			boxItemStateChanged(3);
		}else{
			initKDCheckBox5();
		}
	}

	/**
	 * 预定未收款
	 */
	protected void kDCheckBox4_itemStateChanged(ItemEvent e) throws Exception
	{
		if (kDCheckBox4.isSelected())
		{
			boxItemStateChanged(4);
		}else{
			initKDCheckBox5();
		}
	}

	/**
	 * 全部
	 */
	protected void kDCheckBox5_itemStateChanged(ItemEvent e) throws Exception
	{
		if (kDCheckBox5.isSelected())
		{
			boxItemStateChanged(5);
		}else{
			initKDCheckBox5();
		}
	}

	/**
	 * 如果未选择任何一个，则默认选择全部
	 */
	public void initKDCheckBox5(){
		if ((!kDCheckBox1.isSelected()) && (!kDCheckBox2.isSelected())
				&& (!kDCheckBox3.isSelected()) && (!kDCheckBox4.isSelected()))
		{
			kDCheckBox5.setSelected(true);
		}
	}
	
	
	public void clear()
	{
		super.clear();
		this.kDCheckBox5.setSelected(true);
	}

	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);

		this.kDCheckBox1.setSelected(para.getBoolean("PurHasRev"));	//认购已收款
		this.kDCheckBox2.setSelected(para.getBoolean("PurUnRev"));	//认购未收款
		this.kDCheckBox3.setSelected(para.getBoolean("PreHasRev"));	//预订已收款
		this.kDCheckBox4.setSelected(para.getBoolean("PreUnRev"));	//未定未收款
		this.kDCheckBox5.setSelected(para.getBoolean("All"));	//全部
		
		super.setCustomerParams(para.getCustomerParams());
	}
	
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		param.add("PurHasRev", this.kDCheckBox1.isSelected());
		param.add("PurUnRev", this.kDCheckBox2.isSelected());
		param.add("PreHasRev", this.kDCheckBox3.isSelected());
		param.add("PreUnRev", this.kDCheckBox4.isSelected());
		param.add("All", this.kDCheckBox5.isSelected());
		
		return param.getCustomerParams();
	}
	
	
	public FilterInfo getFilterInfo()
	{
		FilterInfo filter = new FilterInfo();;
		
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		if(para.getBoolean("All"))  filter.getFilterItems().add(new FilterItemInfo("id",null,CompareType.NOTEQUALS));
		
		if(para.getBoolean("PurHasRev") || para.getBoolean("PurUnRev")) {
			String stateSql = " '"+PurchaseStateEnum.PURCHASEAPPLY_VALUE+"','"+PurchaseStateEnum.PURCHASEAUDIT_VALUE+"','"+PurchaseStateEnum.PURCHASEAUDITING_VALUE+"','"+PurchaseStateEnum.PURCHASECHANGE_VALUE+"' ";
			
			filter.getFilterItems().add(new FilterItemInfo("purchaseState",stateSql,CompareType.INNER));

			String purSqlStr = " select Pur.fid from t_she_purchase Pur ";
			purSqlStr += " inner join T_BDC_FDCReceivingBill FdcBill on Pur.fid = FdcBill.FPurchaseObjID ";
			purSqlStr += " where (Pur.fpurchaseState ='"+PurchaseStateEnum.PURCHASEAPPLY_VALUE+"' or Pur.fpurchaseState ='"+PurchaseStateEnum.PURCHASEAUDIT_VALUE+"' " +
					"or Pur.fpurchaseState ='"+PurchaseStateEnum.PURCHASEAUDITING_VALUE+"' or Pur.fpurchaseState ='"+PurchaseStateEnum.PURCHASECHANGE_VALUE+"') ";
			filter.getFilterItems().add(new FilterItemInfo("id",purSqlStr,para.getBoolean("PurHasRev")?CompareType.INNER:CompareType.NOTINNER));
		}else if(para.getBoolean("PreHasRev") || para.getBoolean("PreUnRev")) {
			String stateSql = " '"+PurchaseStateEnum.PREPURCHASEAPPLY_VALUE+"','"+PurchaseStateEnum.PREPURCHASECHECK_VALUE+"' ";
			filter.getFilterItems().add(new FilterItemInfo("purchaseState",stateSql,CompareType.INNER));
			
			String purSqlStr = " select Pur.fid from t_she_purchase Pur ";
			purSqlStr += " inner join T_BDC_FDCReceivingBill FdcBill on Pur.fid = FdcBill.FPurchaseObjID ";
			purSqlStr += " where (Pur.fpurchaseState ='"+PurchaseStateEnum.PREPURCHASEAPPLY_VALUE+"' or Pur.fpurchaseState ='"+PurchaseStateEnum.PREPURCHASECHECK_VALUE+"')";
			filter.getFilterItems().add(new FilterItemInfo("id",purSqlStr,para.getBoolean("PreHasRev")?CompareType.INNER:CompareType.NOTINNER));			
		}
		return filter;
	}

}