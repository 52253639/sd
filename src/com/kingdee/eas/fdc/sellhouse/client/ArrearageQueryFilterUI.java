/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ArrearageQueryFilterUI extends AbstractArrearageQueryFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ArrearageQueryFilterUI.class);
    
	private static final String PUR_FROM = "purBeginDate";
	private static final String PUR_TO = "purEndDate";
	private static final String APP_FROM = "dpAppBeginDate";
	private static final String APP_TO = "dpAppEndDate";
	private static final String CUSTOMER = "prmtCustomer";		//存客户的id
	private static final String Check_YuQiArage = "checkYuQiArage";
	private static final String Check_UnYuQiArage = "checkUnYuQiArage";
	private static final String Check_YuQiPay = "checkYuQiPay";
	private static final String Check_PayOnTime = "checkPayOnTime";
	private static final String Txt_YuQiDayNum = "textYuQiDayNum";
	private static final String Tbl_MoneyType = "tblMoneyType";   //款项类型的id，字符串数组
	private static final String MoenyType_Num = "moneyTypeNum";   //款项类型种类 ，存数字  0全部，1应收，2其它部分
	private static final String Radio_ShowDetail = "radioShowDetail";
	private static final String Radio_ShowGroup = "radioShowGroup";
	private static final String Tbl_SellStatus = "tblSellStatus";   //销售状态的value，字符串数组
	
	
    public ArrearageQueryFilterUI() throws Exception
    {
        super();
    }
        
	public void clear() {
		this.purBeginDate.setValue(null);
		this.purEndDate.setValue(null);
		this.dpAppBeginDate.setValue(null);
		this.dpAppEndDate.setValue(null);
		this.prmtCustomer.setValue(null);
		this.checkYuQiArage.setSelected(true);
		this.checkUnYuQiArage.setSelected(true);
		
		this.checkYuQiPay.setSelected(false);
		this.checkPayOnTime.setSelected(false);
		this.textYuQiDayNum.setText(null);
		this.tblMoneyType.removeRows();
		this.radioShowDetail.setSelected(true);
		this.radioShowGroup.setSelected(false);
		this.chkPrePur.setText("预定");
		
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		try {
			this.prmtCustomer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(null,currentUserInfo));
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		try {
			//(应收回款)默认选中		:   预收款(改成了预订金  by zhicheng_jin)、定金、楼款、首期、公积金
			String resvMTypeStr = MoneyTypeEnum.EARNESTMONEY_VALUE +","+ MoneyTypeEnum.HOUSEAMOUNT_VALUE +","
								   + MoneyTypeEnum.FISRTAMOUNT_VALUE +"," + MoneyTypeEnum.LOANAMOUNT_VALUE +","+ MoneyTypeEnum.ACCFUNDAMOUNT_VALUE;		

			this.tblMoneyType.checkParsed();
			this.tblMoneyType.getStyleAttributes().setLocked(true);
			this.tblMoneyType.getColumn("selectA").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			this.tblMoneyType.getColumn("selectB").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			this.tblMoneyType.getColumn("selectC").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			MoneyDefineCollection moneyDefineColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection("select id ,name ,moneyType where sysType = '"+MoneySysTypeEnum.SALEHOUSESYS_VALUE+"' " 
					+ " and moneyType <>'"+MoneyTypeEnum.COMPENSATEAMOUNT_VALUE+"' and moneyType <>'"+MoneyTypeEnum.PREMONEY_VALUE+"' and moneyType <>'"+MoneyTypeEnum.PRECONCERTMONEY_VALUE+"' and moneyType <>'"+MoneyTypeEnum.REFUNDMENT_VALUE+"' order by moneyType ");
			int countNum = moneyDefineColl.size();
			if(countNum>0) {
				int rowNums = countNum / 3 + (countNum % 3 ==0?0:1);
				for(int i=0;i<rowNums;i++) 
					this.tblMoneyType.addRow();
				
				for(int i=0;i<countNum;i++) {
					ICell cellName = this.tblMoneyType.getCell(i/3, 2*(i%3));
					ICell cellSelect = this.tblMoneyType.getCell(i/3, 2*(i%3)+1);
					cellName.setValue(moneyDefineColl.get(i).getName());
					cellName.setUserObject(moneyDefineColl.get(i));
					cellSelect.setUserObject(moneyDefineColl.get(i).getId().toString());
					if(resvMTypeStr.indexOf(moneyDefineColl.get(i).getMoneyType().getValue())>=0)
						cellSelect.setValue(new Boolean(true));
					else
						cellSelect.setValue(new Boolean(false));
					
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		this.chkPrePur.setSelected(true);
		this.chkPurchase.setSelected(true);
		this.chkSign.setSelected(true);
	}

	
	public FilterInfo getFilterInfo() {
		FilterInfo filter = new FilterInfo();
		FDCCustomerParams param = new FDCCustomerParams(getCustomerParams());

		Date purBeginDate = param.getDate(PUR_FROM);
		Date purEndDate = param.getDate(PUR_TO);		
		Date dpAppBeginDate = param.getDate(APP_FROM);
		Date dpAppEndDate = param.getDate(APP_TO);
		
		String prmtCustomerId = param.getString(CUSTOMER);
		
		boolean checkYuQiArage = param.getBoolean(Check_YuQiArage);
		boolean checkUnYuQiArage = param.getBoolean(Check_UnYuQiArage);
		boolean checkYuQiPay = param.getBoolean(Check_YuQiPay);
		boolean checkPayOnTime = param.getBoolean(Check_PayOnTime);
		
		String textYuQiDayNum = param.get(Txt_YuQiDayNum);
		
		boolean radioShowDetail = param.getBoolean(Radio_ShowDetail);
		boolean radioShowGroup = param.getBoolean(Radio_ShowGroup);
		
		//只做传递参数用
		if(purBeginDate!=null)
			filter.getFilterItems().add(new FilterItemInfo(PUR_FROM,purBeginDate==null?null:new Timestamp(purBeginDate.getTime())));
		if(purEndDate!=null) {
			purEndDate = FDCDateHelper.getNextDay(purEndDate);
			filter.getFilterItems().add(new FilterItemInfo(PUR_TO,purEndDate==null?null:new Timestamp(purEndDate.getTime())));
		}
		if(dpAppBeginDate!=null)
			filter.getFilterItems().add(new FilterItemInfo(APP_FROM,dpAppBeginDate==null?null:new Timestamp(dpAppBeginDate.getTime())));
		if(dpAppEndDate!=null) {
			dpAppEndDate = FDCDateHelper.getNextDay(dpAppEndDate);
			filter.getFilterItems().add(new FilterItemInfo(APP_TO,dpAppEndDate==null?null:new Timestamp(dpAppEndDate.getTime())));
		}
		if(prmtCustomerId!=null) { //查找该客户对应的认购单的id集合
			try {
				//客户
				PurchaseCustomerInfoCollection purCustColl = PurchaseCustomerInfoFactory.getRemoteInstance()
							.getPurchaseCustomerInfoCollection("select head.id where customer.id = '"+prmtCustomerId+"'");
				if(purCustColl.size()>0) {
					String purIdStr = "";
					for(int i=0;i<purCustColl.size();i++) {
						purIdStr += (i==0?"":",") + "'" + purCustColl.get(i).getHead().getId().toString() + "'";
					}
					filter.getFilterItems().add(new FilterItemInfo("PurIdStr",purIdStr));  //如果用CompareType.INCLUDE，取值时会变成Set类型的，所以没有这样用
				}else{
					filter.getFilterItems().add(new FilterItemInfo("PurIdStr","-1" ));
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(checkYuQiArage) filter.getFilterItems().add(new FilterItemInfo(Check_YuQiArage, new Integer(checkYuQiArage?1:0)));
		if(checkUnYuQiArage) filter.getFilterItems().add(new FilterItemInfo(Check_UnYuQiArage, new Integer(checkUnYuQiArage?1:0)));
		if(checkYuQiPay) filter.getFilterItems().add(new FilterItemInfo(Check_YuQiPay, new Integer(checkYuQiPay?1:0)));
		if(checkPayOnTime) filter.getFilterItems().add(new FilterItemInfo(Check_PayOnTime, new Integer(checkPayOnTime?1:0)));
		
		if(textYuQiDayNum!=null && !textYuQiDayNum.equals("0")) filter.getFilterItems().add(new FilterItemInfo(Txt_YuQiDayNum, new Integer(textYuQiDayNum)));
			
		//款项类别
		String[] tblMoneyTypeIds =param.getStringArray(Tbl_MoneyType);
		if(tblMoneyTypeIds!=null && tblMoneyTypeIds.length>0) {
			String motypeIdStr = "";
			for(int i=0;i<tblMoneyTypeIds.length;i++) {
				motypeIdStr += (i==0?"":"<>") + tblMoneyTypeIds[i];
			}
			filter.getFilterItems().add(new FilterItemInfo("MoneyTypeIdStr",motypeIdStr));
			
			//MoenyType_Num = "moneyTypeNum";   //款项类型种类 ，存数字  0全部，1应收，2其它部分
			int moneyTypeNum = param.getInt(MoenyType_Num);
			filter.getFilterItems().add(new FilterItemInfo(MoenyType_Num,new Integer(moneyTypeNum)));
		}
		
		//房间状态
		String[] sellStatus =param.getStringArray(Tbl_SellStatus);
		if(sellStatus!=null && sellStatus.length>0) {
			String sellStatuStr = "";
			for(int i=0;i<sellStatus.length;i++) {
				sellStatuStr += (i==0?"":"<>") + sellStatus[i];
			}
			filter.getFilterItems().add(new FilterItemInfo("RoomSellStatuStr",sellStatuStr));
		}
		
		
		if(radioShowDetail) filter.getFilterItems().add(new FilterItemInfo(Radio_ShowDetail, new Integer(radioShowDetail?1:0)));
		if(radioShowGroup) filter.getFilterItems().add(new FilterItemInfo(Radio_ShowGroup, new Integer(radioShowGroup?1:0)));
		
		return filter;
	}


	
	protected void tblMoneyType_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1) {
			if(e.getType()!=1) return;	
			
				if(e.getColIndex()==1 || e.getColIndex()==3 || e.getColIndex()==5) {
					ICell cell = this.tblMoneyType.getCell(e.getRowIndex(), e.getColIndex());
					if(cell!=null && cell.getValue() instanceof Boolean) {
						Boolean cellValue = (Boolean)cell.getValue();
						cell.setValue(new Boolean(!cellValue.booleanValue()));
					}
				}
		}
	}
	
	
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		param.add(PUR_FROM, (Date)this.purBeginDate.getValue());
		param.add(PUR_TO, (Date)this.purEndDate.getValue());
		param.add(APP_FROM, (Date)this.dpAppBeginDate.getValue());
		param.add(APP_TO, (Date)this.dpAppEndDate.getValue());
		
		FDCCustomerInfo custInfo = (FDCCustomerInfo)this.prmtCustomer.getValue(); 
		param.add(CUSTOMER, custInfo==null?null:custInfo.getId().toString());
		
		param.add(Check_YuQiArage, this.checkYuQiArage.isSelected());
		param.add(Check_UnYuQiArage, this.checkUnYuQiArage.isSelected());
		param.add(Check_YuQiPay, this.checkYuQiPay.isSelected());
		param.add(Check_PayOnTime, this.checkPayOnTime.isSelected());
		
		if(this.textYuQiDayNum.getNumberValue()!=null)
			param.add(Txt_YuQiDayNum, (Integer)this.textYuQiDayNum.getNumberValue());
		else{
			param.add(Txt_YuQiDayNum, new Integer(0));
		}
		

		//(应收回款)		:   定金、楼款、首期、按揭款、公积金
		String resvMTypeStr = MoneyTypeEnum.EARNESTMONEY_VALUE +","+ MoneyTypeEnum.HOUSEAMOUNT_VALUE +","
							   + MoneyTypeEnum.FISRTAMOUNT_VALUE +"," + MoneyTypeEnum.LOANAMOUNT_VALUE +","+ MoneyTypeEnum.ACCFUNDAMOUNT_VALUE;		
		//MoenyType_Num = "moneyTypeNum";   //款项类型种类 ，存数字  0全部，1应收，2其它部分
		boolean existShouldPay = false;
		boolean existOtherPay = false;
		Set moneyTypeIdSet = new HashSet(); 
		//款项类型
		for(int i=0;i<tblMoneyType.getRowCount();i++) {
			IRow row = this.tblMoneyType.getRow(i);
			for(int j=0;j<3;j++) {
				ICell cell = row.getCell(2*j+1);
				if(cell!=null && cell.getValue() instanceof Boolean) {
					if(((Boolean)cell.getValue()).booleanValue()) {
						moneyTypeIdSet.add((String)cell.getUserObject());
						
						if(!existShouldPay || !existOtherPay) {
							ICell cellMT= row.getCell(2*j);
							MoneyDefineInfo moDeInfo = (MoneyDefineInfo)cellMT.getUserObject();
							if(moDeInfo!=null && moDeInfo.getMoneyType()!=null) {
								if(resvMTypeStr.indexOf(moDeInfo.getMoneyType().getValue())>=0)  
									existShouldPay = true;
								else
									existOtherPay = true;
							}
						}
					}
				}
			}
		}
		
		if(moneyTypeIdSet.size()>0) {
			String[] tempStrs = new String[moneyTypeIdSet.size()];
			moneyTypeIdSet.toArray(tempStrs);
			param.add(Tbl_MoneyType, tempStrs);
			
			if(existShouldPay && existOtherPay)  param.add(MoenyType_Num, 0);
			else if(existShouldPay)  param.add(MoenyType_Num, 1);
			else if(existOtherPay)  param.add(MoenyType_Num, 2);
		}	
		
		//增加销售状态过滤
		int tempCount = 0;
		if(this.chkPrePur.isSelected()) tempCount++;
		if(this.chkPurchase.isSelected()) tempCount++;
		if(this.chkSign.isSelected()) tempCount++;
		if(tempCount>0) {
			String[] sellStateStrs = new String[tempCount];
			if(this.chkPrePur.isSelected()) {
				sellStateStrs[--tempCount] = RoomSellStateEnum.PREPURCHASE_VALUE;
			}
			if(this.chkPurchase.isSelected()) {
				sellStateStrs[--tempCount] = RoomSellStateEnum.PURCHASE_VALUE;
			}
			if(this.chkSign.isSelected()) {
				sellStateStrs[--tempCount] = RoomSellStateEnum.SIGN_VALUE;
			}			
			param.add(Tbl_SellStatus, sellStateStrs);
		}
		
		param.add(Radio_ShowDetail, this.radioShowDetail.isSelected());
		param.add(Radio_ShowGroup, this.radioShowGroup.isSelected());		
	
		return param.getCustomerParams();
	}



	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;
		clear();
		FDCCustomerParams para = new FDCCustomerParams(cp);

		this.purBeginDate.setValue(para.getDate(PUR_FROM));
		this.purEndDate.setValue(para.getDate(PUR_TO));
		this.dpAppBeginDate.setValue(para.getDate(APP_FROM));
		this.dpAppEndDate.setValue(para.getDate(APP_TO));
		
		String custInfoId = para.getString(CUSTOMER);
		if(custInfoId!=null) {
			try {
				FDCCustomerInfo custInfo = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(custInfoId)));
				if(custInfo!=null) this.prmtCustomer.setValue(custInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			}			
		}		

		if(para.getBoolean(Check_YuQiArage))
			this.checkYuQiArage.setSelected(true);
		else
			this.checkYuQiArage.setSelected(false);
		if(para.getBoolean(Check_UnYuQiArage))
			this.checkUnYuQiArage.setSelected(true);
		else
			this.checkUnYuQiArage.setSelected(false);
		
		this.checkYuQiPay.setSelected(para.getBoolean(Check_YuQiPay));
		this.checkPayOnTime.setSelected(para.getBoolean(Check_PayOnTime));
		
		this.textYuQiDayNum.setNumberValue(para.getInteger(Txt_YuQiDayNum));
		
		String[] moneyTypeIds = para.getStringArray(Tbl_MoneyType);
		if(moneyTypeIds!=null && moneyTypeIds.length>0) {
			String moTypeIdStr = "";
			for(int i=0;i<moneyTypeIds.length;i++) moTypeIdStr += moneyTypeIds[i]+",";
			
			for(int i=0;i<this.tblMoneyType.getRowCount();i++) {
				for(int j=0;j<3;j++) {
					ICell cell = this.tblMoneyType.getCell(i, 2*j+1);
					if(cell!=null && cell.getValue() instanceof Boolean) {
						String mtId = (String)cell.getUserObject();
						if(moTypeIdStr.indexOf(mtId)>=0) cell.setValue(new Boolean(true));
						else cell.setValue(new Boolean(false));
					}
				}
			}
		}
		
		String[] sellStatus = para.getStringArray(Tbl_SellStatus);
		if(sellStatus!=null && sellStatus.length>0) {
			String sellStatusStr = "";
			this.chkPrePur.setSelected(false);
			this.chkPurchase.setSelected(false);
			this.chkSign.setSelected(false);
			for(int i=0;i<sellStatus.length;i++){
				 sellStatusStr =sellStatus[i];
				if(sellStatusStr.equals(RoomSellStateEnum.PREPURCHASE_VALUE.toString())){
					this.chkPrePur.setSelected(true);
				}
				if(sellStatusStr.equals(RoomSellStateEnum.PURCHASE_VALUE.toString())){
					this.chkPurchase.setSelected(true);
				}
				if(sellStatusStr.equals(RoomSellStateEnum.SIGN_VALUE.toString())){
					this.chkSign.setSelected(true);
				}
			}
		}
		
		this.radioShowDetail.setSelected(para.getBoolean(Radio_ShowDetail));
		this.radioShowGroup.setSelected(para.getBoolean(Radio_ShowGroup));
		
		super.setCustomerParams(para.getCustomerParams());
	}
	
	public boolean verify() {
		FDCCustomerParams param = new FDCCustomerParams(getCustomerParams());

		Date purBeginDate = param.getDate(PUR_FROM);
		Date purEndDate = param.getDate(PUR_TO);		
		if(purBeginDate!=null && purEndDate!=null) {
			if(purBeginDate.after(purEndDate)) {
				MsgBox.showWarning("业务申请开始日期大于结束日期！");
				return false;
			}
		}
		Date dpAppBeginDate = param.getDate(APP_FROM);
		Date dpAppEndDate = param.getDate(APP_TO);
		if(dpAppBeginDate!=null && dpAppEndDate!=null) {
			if(dpAppBeginDate.after(dpAppEndDate)) {
				MsgBox.showWarning("款项应付开始日期大于结束日期！");
				return false;
			}
		}
		
		boolean checkYuQiArage = param.getBoolean(Check_YuQiArage);
		boolean checkUnYuQiArage = param.getBoolean(Check_UnYuQiArage);
		boolean checkYuQiPay = param.getBoolean(Check_YuQiPay);
		boolean checkPayOnTime = param.getBoolean(Check_PayOnTime);
		if(!checkYuQiArage && !checkUnYuQiArage && !checkYuQiPay && !checkPayOnTime) {
			MsgBox.showWarning("欠款类型必须选择至少一项！");
			return false;
		}
		
		//String textYuQiDayNum = param.get(Txt_YuQiDayNum);
		String[] tblMoneyTypeIds =param.getStringArray(Tbl_MoneyType);
		if(tblMoneyTypeIds==null || tblMoneyTypeIds.length==0){
			MsgBox.showWarning("款项类型必须选择至少一项！");
			return false;
		}
		
		String[] tblSellStatus =param.getStringArray(Tbl_SellStatus);
		if(tblSellStatus==null || tblSellStatus.length==0){
			MsgBox.showWarning("房间状态必须选择至少一项！");
			return false;
		}
		
		boolean radioShowDetail = param.getBoolean(Radio_ShowDetail);
		boolean radioShowGroup = param.getBoolean(Radio_ShowGroup);
		if(!radioShowDetail && !radioShowGroup){
			MsgBox.showWarning("展示方式必须选择至少一项！");
			return false;
		}
		
		return true;
	}


    /**
     * 点击全选
     */
    protected void chackAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	for(int i = 0;i <= tblMoneyType.getRowCount();i++){
    		for(int j = 1;j <= tblMoneyType.getColumnCount();j+=2){
    			if(tblMoneyType.getCell(i, j-1) != null && tblMoneyType.getCell(i, j-1).getValue()!=null){
        			tblMoneyType.getCell(i, j).setValue(new Boolean(true));
    			}
    		}
    	}
    }

    /**
     * 点击全部取消
     */
    protected void canotAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	for(int i = 0;i <= tblMoneyType.getRowCount();i++){
    		for(int j = 1;j <= tblMoneyType.getColumnCount();j+=2){
    			if(tblMoneyType.getCell(i, j) != null && tblMoneyType.getCell(i, j-1).getValue()!=null){
    				tblMoneyType.getCell(i, j).setValue(new Boolean(false));
    			}
    		}
    	}
    }
	
	protected void checkPayOnTime_itemStateChanged(ItemEvent e)
			throws Exception {
		checkIfContentYuQi();
	}
	
	protected void checkUnYuQiArage_itemStateChanged(ItemEvent e)
			throws Exception {
		checkIfContentYuQi();
	}
	
	protected void checkYuQiArage_itemStateChanged(ItemEvent e)
			throws Exception {
		checkIfContentYuQi();
	}
	protected void checkYuQiPay_itemStateChanged(ItemEvent e) throws Exception {
		checkIfContentYuQi();
	}
	
	private void checkIfContentYuQi() {
		boolean checkYuArage = this.checkYuQiArage.isSelected();
		boolean checkYuPay = this.checkYuQiPay.isSelected();
		if(checkYuArage || checkYuPay) {
			this.textYuQiDayNum.setEnabled(true);
		}else{
			this.textYuQiDayNum.setNumberValue(null);
			this.textYuQiDayNum.setEnabled(false);
		}
		
	}
	
	
}