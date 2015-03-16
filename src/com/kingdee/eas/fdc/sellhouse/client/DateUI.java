/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.MsgBox;
/**
 * output class name
 */
public class DateUI extends AbstractDateUI
{
    private static final Logger logger = CoreUIObject.getLogger(DateUI.class);
    private ArrayList selectList = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    private String IS_onRecord = "您是否确认要备案吗?";
    private String IS_pullDown = "您是否确认要领取吗?";
    private String IS_stamp = "您是否确认要签章吗?";
    protected ResourceBundleHelper reHelper;
    /**
     * output class constructor
     */
    public DateUI() throws Exception
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
    public void onLoad()
    {
    	
    }
    public void onShow()
    {
    	String lable = (String) this.getUIContext().get("lable");
    	this.kDLabel1.setText(lable);
    	this.kDLabel1.setOpaque(true);
    	String title = (String) this.getUIContext().get("setTitle");
    	if(IS_pullDown.equals(lable) || IS_stamp.equals(lable))
    	{
    		this.kDLabelContainer1.setVisible(false);
    		this.kdtxtContractNumber.setVisible(false);
    	}
    	setUITitle(title);
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
    protected void kDButton1_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
//    	ArrayList ids = (ArrayList) this.getUIContext().get("selectedIDValues");   	
    	String id=  this.getUIContext().get("selectedIDValues").toString();
    	String lable = (String) this.getUIContext().get("lable");
    	String contractNumber = this.kdtxtContractNumber.getText();
    	if(lable.equals(IS_onRecord)){
    		Date date =(Date) this.kDDatePicker1.getValue();
    		if(contractNumber.length()>30){
    			MsgBox.showInfo(this, "合同号长度太长请重新输入");
    			
    			this.kdtxtContractNumber.setText("");
    			return;
    		}
    		SignManageFactory.getRemoteInstance().onRecord(BOSUuid.read(id),date,contractNumber);    		
//    		RoomSignContractFactory.getRemoteInstance().onRecord(FDCHelper.idListToPKArray(ids),date,contractNumber);
    	}
    	if(lable.equals(IS_pullDown)){
//    		Date date =(Date) this.kDDatePicker1.getValue();
//    		RoomSignContractFactory.getRemoteInstance().pullDown(FDCHelper.idListToPKArray(ids),date);
    	}
    	if(lable.equals(IS_stamp)){
//    		Date date =(Date) this.kDDatePicker1.getValue();
//    		RoomSignContractFactory.getRemoteInstance().stamp(FDCHelper.idListToPKArray(ids),date);
    	}
       ListUI parentUI = (ListUI)this.getUIContext().get("parentUI");
        parentUI.refreshList();
           this.destroyWindow();
    }

    
    /**
     * output kDButton2_actionPerformed method
     */
    protected void kDButton2_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.destroyWindow();
    }
}