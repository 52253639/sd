/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class ProgrammingTemplateListUI extends AbstractProgrammingTemplateListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingTemplateListUI.class);
    protected static final String CONTROLTYPE_S1="S1";
    /**
     * output class constructor
     */
    public ProgrammingTemplateListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	setBtnCopyState(true);
    	tableSelectChange();
    	
    	//设置只可选择单行记录
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    }
    
    private void setBtnCopyState(boolean isFalg){
    	btnCopy.setVisible(isFalg);
    	btnCopy.setEnabled(isFalg);
    	actionCopy.setVisible(isFalg);
    	actionCopy.setEnabled(isFalg);
    	menuItemCopy.setVisible(isFalg);
    	menuItemCopy.setEnabled(isFalg);
    }
    
    private void tableSelectChange(){
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
    	if(rowIndex >= 0){
    		boolean isEnable = ((Boolean)tblMain.getCell(rowIndex, "isEnable").getValue()).booleanValue();
    		if(isEnable){
    			btnCancelCancel.setEnabled(false);
    			menuItemCancelCancel.setEnabled(false);
    			btnCancel.setEnabled(true);
    			menuItemCancel.setEnabled(true);
    		}else{
    			btnCancelCancel.setEnabled(true);
    			menuItemCancelCancel.setEnabled(true);
    			btnCancel.setEnabled(false);
    			menuItemCancel.setEnabled(false);
    		}
    		btnView.setEnabled(true);
    		menuItemView.setEnabled(true);
    		btnEdit.setEnabled(true);
    		menuItemEdit.setEnabled(true);
    		btnRemove.setEnabled(true);
    		menuItemRemove.setEnabled(true);
    		btnCopy.setEnabled(true);
    		menuItemCopy.setEnabled(true);
    	}else{
    		btnView.setEnabled(false);
    		menuItemView.setEnabled(false);
    		btnEdit.setEnabled(false);
    		menuItemEdit.setEnabled(false);
    		btnRemove.setEnabled(false);
    		menuItemRemove.setEnabled(false);
    		btnCopy.setEnabled(false);
    		menuItemCopy.setEnabled(false);
    		btnCancel.setEnabled(false);
    		menuItemCancel.setEnabled(false);
    		btnCancelCancel.setEnabled(false);
    		menuItemCancelCancel.setEnabled(false);
    	}
    	
    	String cuID = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
    	if(getControlType().equalsIgnoreCase(CONTROLTYPE_S1)) {
            if(!cuID.equals(OrgConstants.DEF_CU_ID)) {
            	btnAddNew.setEnabled(false);
            	menuItemAddNew.setEnabled(false);
        		btnEdit.setEnabled(false);
        		menuItemEdit.setEnabled(false);
        		btnRemove.setEnabled(false);
        		menuItemRemove.setEnabled(false);
        		btnCopy.setEnabled(false);
        		menuItemCopy.setEnabled(false);
        		btnCancel.setEnabled(false);
        		menuItemCancel.setEnabled(false);
        		btnCancelCancel.setEnabled(false);
        		menuItemCancelCancel.setEnabled(false);
            }
        }
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected String getEditUIModal(){
        return UIFactoryName.NEWTAB;
    }
    
    protected void tblMain_tableSelectChanged(KDTSelectEvent e)
    		throws Exception {
    	tableSelectChange();
    }
    
    protected void tblMain_activeCellChanged(KDTActiveCellEvent e) throws Exception {
    	tableSelectChange();
    }
    
    /**
     * 复制
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
    	String selectKeyValue = getSelectedKeyValue();
    	if(StringUtils.isEmpty(selectKeyValue)){
    		MsgBox.showInfo("请选择要操作的数据！");
    		return;
    	}
    	ProgrammingTemplateFactory.getRemoteInstance().copy();
    	IUIWindow uiWindow = showEditUI(e);
        uiWindow.show();

        if (isDoRefresh(uiWindow))
        {
        	if(UtilRequest.isPrepare("ActionRefresh",this)) {
        		prepareRefresh(null).callHandler();
        	}
            setLocatePre(false);
            refresh(e);
            setPreSelecteRow();
            setLocatePre(true);
        }
    }
    
    private IUIWindow showEditUI(ActionEvent e) throws Exception
    {
        checkSelected();

        UIContext uiContext = new UIContext(this);
        uiContext.put(UIContext.ID, getSelectedKeyValue());
        uiContext.put("Copy",Boolean.TRUE);

        // 供子类定义要传递到EditUI中扩展的属性
        prepareUIContext(uiContext, e);

        IUIWindow uiWindow = null;
        if (SwingUtilities.getWindowAncestor(this) != null && SwingUtilities.getWindowAncestor(this) instanceof JDialog)
        {
            uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(getEditUIName(), uiContext, null,
                    OprtState.EDIT);
        }
        else
        {
            // 创建UI对象并显示
            uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,
                    OprtState.EDIT);
        }

        return uiWindow;
    }
    
    /**
     * 禁用
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        String id = getSelectedKeyValue();
        if(!StringUtils.isEmpty(id)){
        	ProgrammingTemplateInfo info = ProgrammingTemplateFactory.getRemoteInstance().getProgrammingTemplateInfo(new ObjectUuidPK(id));
        	ProgrammingTemplateFactory.getRemoteInstance().disEnabled(new ObjectUuidPK(id), info);
        }else{
        	MsgBox.showInfo("请选择要操作的数据！");
        	return;
        }
        refresh(null);
        MsgBox.showInfo("合约框架禁用成功！");
    }

    /**
     * 启用
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	 String id = getSelectedKeyValue();
    	 if(!StringUtils.isEmpty(id)){
    		 ProgrammingTemplateInfo info = ProgrammingTemplateFactory.getRemoteInstance().getProgrammingTemplateInfo(new ObjectUuidPK(id));
    		 ProgrammingTemplateFactory.getRemoteInstance().enabled(new ObjectUuidPK(id), info);
    	 }else{
        	MsgBox.showInfo("请选择要操作的数据！");
        	return;
        }
         refresh(null);
         MsgBox.showInfo("合约框架启用成功！");
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return ProgrammingTemplateFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        ProgrammingTemplateInfo objectValue = new ProgrammingTemplateInfo();
		
        return objectValue;
    }

}