/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CRMChequeFactory;
import com.kingdee.eas.fdc.sellhouse.CRMChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CodeGenerater;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ChequeVCEditUI extends AbstractChequeVCEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChequeVCEditUI.class);
    private CRMChequeInfo editdata = null;
    /**
     * output class constructor
     */
    public ChequeVCEditUI() throws Exception
    {
        super();
    }

    
    public void onLoad() throws Exception {
    	//查取一下数据
    	String id = (String)this.getUIContext().get("chequeid");
    	editdata = CRMChequeFactory.getRemoteInstance().getCRMChequeInfo(new ObjectUuidPK(id));
    	this.contStart.setEnabled(false);
		this.contEnd.setEnabled(false);
    	super.onLoad();
    	loadFields();
    }
  
    public void loadFields() {
    	this.F7Cheque.setValue(this.editdata);
    	this.spinnerStart.setValue(new Integer(this.editdata.getSerialNumber()));
    	this.spinnerEnd.setValue(new Integer(this.editdata.getSerialNumber()+this.editdata.getNumberOfCheque()-1));
    }
    protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
    	verifyInput();
    	if(this.radPart.isSelected()){
    		verifyIsCanVC();
    	}
    	CRMChequeFactory.getRemoteInstance().vc((prepareData()));
    	this.destroyWindow();
    }

    /**
     * 部分核销时，所选流水号是否都能核销
     */
    String numberString = null;
	private void verifyIsCanVC() throws EASBizException, BOSException {
		// 设置编码规则、张数、起始流水号
		CRMChequeInfo cheque = (CRMChequeInfo)this.F7Cheque.getValue();
		CodeGenerater codeGen = new CodeGenerater();
		int beginIndex = Integer.parseInt(this.spinnerStart.getValue().toString());
		int endIndex = Integer.parseInt(this.spinnerEnd.getValue().toString());
		int count = endIndex - beginIndex +1;
		codeGen.setBeginIndex(beginIndex);
		codeGen.setCount(count);
		codeGen.setCodeRule(cheque.getCodeRule());
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i< count ; i++){
			sb.append(",");
			sb.append(codeGen.nextNumber(cheque.getNumberOfCheque(),cheque.getSerialNumber()));
		}
		numberString = sb.toString().replaceFirst(",","");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("status",new Integer(ChequeStatusEnum.VC_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("cheque.id",cheque.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("number",numberString,CompareType.INCLUDE));
		if(ChequeDetailEntryFactory.getRemoteInstance().exists(filter)){
			if(2==FDCMsgBox.showConfirm2(this,"部分票据已核销，已核销的不会重复核销，是否继续！")){
				this.abort();
			}
			
		}
	}

	private Map prepareData() {
		Map dataMap = new HashMap();
		dataMap.put("cheque", this.F7Cheque.getValue());
		dataMap.put("start",this.spinnerStart.getValue());
		dataMap.put("end", this.spinnerEnd.getValue());
		dataMap.put("F7VCer",this.F7VCer.getValue());
		dataMap.put("VCDate", this.VCDate.getValue());
		dataMap.put("radAll", new Boolean(this.radAll.isSelected()));
		dataMap.put("radpart", new Boolean(this.radPart.isSelected()));
		dataMap.put("numberString", numberString);
		return dataMap;
	}

	private void verifyInput() {
		if(this.F7Cheque.getValue()==null){
			FDCMsgBox.showWarning(this,"请选择票据批次号！");
			this.abort();
		}
		//效验流水号是否越界了
		if(!this.radAll.isSelected()){
			Integer start = new Integer(((CRMChequeInfo)this.F7Cheque.getValue()).getSerialNumber());
			Integer numberOfCheque =  new Integer(((CRMChequeInfo)this.F7Cheque.getValue()).getNumberOfCheque());
			if(((Integer)this.spinnerStart.getValue()).compareTo(((Integer)this.spinnerEnd.getValue()))>0){
				FDCMsgBox.showWarning(this,"起始流水号不能小于结束流水号  ");
				this.abort();
			}
			if(((Integer)this.spinnerStart.getValue()).compareTo(start)<0){
				FDCMsgBox.showWarning(this,"起始流水号不能小于票据登记上的流水号  "+start);
				this.abort();
			}
			
			if(((Integer)this.spinnerEnd.getValue()).compareTo(new Integer(start.intValue()+numberOfCheque.intValue()-1))>0){
				FDCMsgBox.showWarning(this,"起始流水号不能大于票据登记上的流水号" + (start.intValue()+numberOfCheque.intValue()-1));
				this.abort();
			}
		}
		//效验领用人
		
		if(this.F7VCer.getValue()==null){
			FDCMsgBox.showWarning(this,"请选择核销人！");
			this.abort();
		}
		
		if(this.VCDate.getValue()==null){
			FDCMsgBox.showWarning(this,"请填写核销日期！");
			this.abort();
		}
	}
	
	
	/**
	 * 全选灰流水号不可编辑
	 */
	protected void radAll_actionPerformed(ActionEvent e) throws Exception {
		if(this.radAll.isSelected()){
			this.contStart.setEnabled(false);
			this.contEnd.setEnabled(false);
		}
	}
	
	
	/**
	 * 部分显流水号可编辑
	 */
	protected void radPart_actionPerformed(ActionEvent e) throws Exception {
		if(this.radPart.isSelected()){
			this.contStart.setEnabled(true);
			this.contEnd.setEnabled(true);
		}
	}
	protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
		this.destroyWindow();
	}
    
   

}