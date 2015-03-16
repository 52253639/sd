/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryCollection;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryFactory;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryInfo;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelFactory;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;

/**
 * output class name
 */
public class ContractPayNewTashListUI extends AbstractContractPayNewTashListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractPayNewTashListUI.class);
    
    /**
     * output class constructor
     */
    public ContractPayNewTashListUI() throws Exception
    {
        super();
    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 600, 300));
        this.setLayout(null);
        tblMain.setBounds(new Rectangle(37, 29, 550, 260));
        this.add(tblMain, null);

    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
//        super.tblMain_tableClicked(e);
    	if(e.getClickCount()>1){
    		IPayLayoutNew pl = (IPayLayoutNew)this.getUIContext().get("UI");
    		String taskid = tblMain.getCell(e.getRowIndex(),"column5").getValue().toString();
    		ContractAndTaskRelEntryInfo entryInfo = ContractAndTaskRelEntryFactory.getRemoteInstance().getContractAndTaskRelEntryInfo(new ObjectUuidPK(taskid));
    		pl.handleResult(entryInfo);
    	}
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
		super.onLoad();
	}

	public void onShow() throws Exception {
		super.onShow();
		this.setUITitle("合同任务列表");
		if(UIRuleUtil.isNotNull(this.getUIContext().get("ContractCd"))){
			String contractId = this.getUIContext().get("ContractCd").toString();
			Map taskListMap = ContractAndTaskRelFactory.getRemoteInstance().getTaskData(contractId);
	        ContractAndTaskRelInfo info;
	        if(taskListMap.get("conTask") != null && ((Map)taskListMap.get("conTask")).get("conAndTaskRel") != null){
	        	info = (ContractAndTaskRelInfo) ((Map)taskListMap.get("conTask")).get("conAndTaskRel");
	        	info = ContractAndTaskRelFactory.getRemoteInstance().getContractAndTaskRelInfo(new ObjectUuidPK(info.getId()));
	        	ContractAndTaskRelEntryCollection infoEntrys = info.getEntrys();
	        	if(infoEntrys.size() >0){
	        		for(int i=0;i<infoEntrys.size();i++){
	        			ContractAndTaskRelEntryInfo entryInfo = infoEntrys.get(i);
	        			String taskId = entryInfo.getTask().getId().toString(); 
	        			FDCScheduleTaskInfo stInfo = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(new ObjectUuidPK(taskId));
	        			FDCWBSInfo siInfo = FDCWBSFactory.getRemoteInstance().getFDCWBSInfo(new ObjectUuidPK(entryInfo.getWbs().getId()));
	        			IRow row = tblMain.addRow();
	        			row.getCell("column1").setValue(siInfo.getName());
	        			row.getCell("column2").setValue(stInfo.getName());
	        			row.getCell("column3").setValue(stInfo.getStart());
	        			row.getCell("column3").setValue(stInfo.getEnd());
	        			row.getCell("column5").setValue(taskId);
	        			logger.info("taskID-------------:"+taskId);
	        		}
	        	}
	        }
		}
	}

}