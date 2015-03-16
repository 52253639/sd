/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CanRefundmentAmountSetUI extends AbstractCanRefundmentAmountSetUI
{
    private static final Logger logger = CoreUIObject.getLogger(CanRefundmentAmountSetUI.class);
    
    public CanRefundmentAmountSetUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	initTbl();
    	
    	this.btnOK.setEnabled(false);
    	this.tblSet.getStyleAttributes().setLocked(true);
    	if("EDIT".equals(this.oprtState)){
    		this.tblSet.getColumn("canRefundmentAmount").getStyleAttributes().setLocked(false);
    		this.btnOK.setEnabled(true);
    	}
    	
    	QuitPayListEntryCollection quitEntrys = (QuitPayListEntryCollection) this.getUIContext().get("quitEntrys");
    	
    	for(int i=0; i<quitEntrys.size(); i++){
    		QuitPayListEntryInfo quitEntry = quitEntrys.get(i);
    		IRow row = this.tblSet.addRow();
    		row.setUserObject(quitEntry);
    		row.getCell("moneyDefine").setValue(quitEntry.getMoneyDefine().getName());
    		row.getCell("appAmount").setValue(quitEntry.getApAmount());
    		row.getCell("actAmount").setValue(quitEntry.getActPayAmount());
    		row.getCell("canRefundmentAmount").setValue(quitEntry.getCanRefundmentAmount());
    	}
    }
    
    private void initTbl() {
    	this.tblSet.checkParsed();
    	KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setMinimumValue(FDCHelper.MIN_DECIMAL);
		formattedTextField.setMaximumValue(new BigDecimal(Integer.MAX_VALUE));
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		
		this.tblSet.getColumn("canRefundmentAmount").setEditor(numberEditor);
		
		this.tblSet.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblSet.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblSet.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);		
		this.tblSet.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblSet.getColumn("canRefundmentAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblSet.getColumn("canRefundmentAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    }

	protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
    	for(int i=0; i<this.tblSet.getRowCount(); i++){
    		IRow row = this.tblSet.getRow(i);
    		
    		QuitPayListEntryInfo quitEntry = (QuitPayListEntryInfo) row.getUserObject();
    		BigDecimal canRefundmentAmount = (BigDecimal) row.getCell("canRefundmentAmount").getValue();
    		BigDecimal maxCanRefundAmount = quitEntry.getMaxCanRefundAmount();
    		if(canRefundmentAmount != null  &&  maxCanRefundAmount != null){
    			if(canRefundmentAmount.compareTo(maxCanRefundAmount) > 0){
    				maxCanRefundAmount = maxCanRefundAmount.setScale(2);
    				MsgBox.showInfo(this, "第" + (i+1) + "行可退金额超出最大可退金额 " + maxCanRefundAmount + "！");
    				this.abort();
    			}
    		}
    		
    		quitEntry.setCanRefundmentAmount(canRefundmentAmount);
    	}
    	this.destroyWindow();
	}
    
    protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
    	this.destroyWindow();
    }
    
}