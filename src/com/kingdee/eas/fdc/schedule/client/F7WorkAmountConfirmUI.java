/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryInfo;
import com.kingdee.eas.framework.CoreBaseCollection;

/**
 * output class name
 */
public class F7WorkAmountConfirmUI extends AbstractF7WorkAmountConfirmUI
{
    private static final Logger logger = CoreUIObject.getLogger(F7WorkAmountConfirmUI.class);
    
    /**
     * output class constructor
     */
    
    private Map alreadySelected;
    private List selectedList;
    private Map completeAmtMap;
    
   public void onLoad() throws Exception {
	// TODO Auto-generated method stub
	super.onLoad();
	 initTableStyle();
     initButtonStyle();
     String contractId = null;
     if(getUIContext().get("contractId") != null)
         contractId = (String)getUIContext().get("contractId");
     if(getUIContext().get("alreadySelected") != null)
         alreadySelected = (Map)getUIContext().get("alreadySelected");
     Map retValue = new HashMap();
     retValue.put("contractId", contractId);
     retValue = WorkLoadConfirmBillFactory.getRemoteInstance().getRefWorkAmount(retValue);
     if(retValue.get("completeMap") != null)
         completeAmtMap = (Map)retValue.get("completeMap");
     if(retValue.get("entryCols") != null)
     {
         CoreBaseCollection cols = (CoreBaseCollection)retValue.get("entryCols");
         WorkAmountEntryInfo info = null;
         if(!cols.isEmpty())
         {
             for(Iterator it = cols.iterator(); it.hasNext(); fillTable(info))
                 info = (WorkAmountEntryInfo)it.next();

         }
     }
}
    
    protected void initTableStyle()
    {
        KDTable t = getDetailTable();
        t.checkParsed();
        String frm = "#,###.00";
        t.getColumn("percent").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
        t.getColumn("totalPercent").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
        t.getColumn("amont").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
        t.getColumn("totalAmt").getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
        t.getColumn("percent").getStyleAttributes().setNumberFormat(frm);
        t.getColumn("totalPercent").getStyleAttributes().setNumberFormat(frm);
        t.getColumn("amont").getStyleAttributes().setNumberFormat(frm);
        t.getColumn("totalAmt").getStyleAttributes().setNumberFormat(frm);
        t.getColumn("percent").getStyleAttributes().setLocked(true);
        t.getColumn("totalPercent").getStyleAttributes().setLocked(true);
        t.getColumn("amont").getStyleAttributes().setLocked(true);
        t.getColumn("totalAmt").getStyleAttributes().setLocked(true);
        t.getColumn("isRef").getStyleAttributes().setLocked(true);
        t.getColumn("taskName").getStyleAttributes().setLocked(true);
        t.getColumn("taskNumber").getStyleAttributes().setLocked(true);
        t.getColumn("isRef").getStyleAttributes().setHided(true);
        KDCheckBox chkSelected = new KDCheckBox();
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(chkSelected);
        t.getColumn("isSelect").setEditor(editor);
    }
    
    protected KDTable getDetailTable()
    {
        return workamountTable;
    }
    
    public F7WorkAmountConfirmUI() throws Exception
    {
        super();
        alreadySelected = new HashMap();
        selectedList = null;
        completeAmtMap = new HashMap();
    }
    
    
    public void fillTable(WorkAmountEntryInfo info)
    {
        java.math.BigDecimal completeArray[] = (java.math.BigDecimal[])null;
        java.math.BigDecimal percent = FDCHelper.ZERO;
        java.math.BigDecimal amount = FDCHelper.ZERO;
        KDTable t = getDetailTable();
        t.checkParsed();
        IRow row = t.addRow();
        row.getCell("isSelect").setValue(Boolean.valueOf(alreadySelected.containsKey(info.getId().toString())));
        row.getCell("taskName").setValue(info.getTask().getName());
        row.getCell("taskNumber").setValue(info.getTask().getNumber());
        row.getCell("amont").setValue(info.getConfirmAmount());
        row.getCell("percent").setValue(info.getConfirmPercent());
        if(completeAmtMap != null && completeAmtMap.containsKey(info.getTask().getId().toString()))
        {
            completeArray = (java.math.BigDecimal[])completeAmtMap.get(info.getTask().getId().toString());
            amount = completeArray[0];
            percent = completeArray[1];
            row.getCell("totalAmt").setValue(amount);
            row.getCell("totalPercent").setValue(percent);
        }
        row.getCell("id").setValue(info.getId());
        row.setUserObject(info);
        if(alreadySelected.containsKey(info.getId().toString()))
            row.getCell("isSelect").setValue(Boolean.TRUE);
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

    /**
     * output actionChoose_actionPerformed
     */
    public void actionChoose_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionChoose_actionPerformed(e);
        selectedList = new ArrayList();
        KDTable t = getDetailTable();
        for(int i = 0; i < t.getRowCount(); i++)
            if(((Boolean)t.getCell(i, "isSelect").getValue()).booleanValue() && t.getRow(i).getUserObject() != null)
                selectedList.add(t.getRow(i).getUserObject());

        getUIContext().put("entry", selectedList);
        getUIContext().put("completeMap", completeAmtMap);
        getUIContext().put("isCancled", "false");
        disposeUIWindow();
    }

    /**
     * output actionCancleChoose_actionPerformed
     */
    public void actionCancleChoose_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancleChoose_actionPerformed(e);
        getUIContext().put("isCancled", "true");
        disposeUIWindow();
    }
    
    protected void initButtonStyle()
    {
        actionChoose.setEnabled(true);
        actionCancleChoose.setEnabled(true);
    }

}