/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;

/**
 * output class name
 */
public class FDCBudgetAcctDeptFilterUI extends AbstractFDCBudgetAcctDeptFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCBudgetAcctDeptFilterUI.class);
    
    /**
     * output class constructor
     */
    public FDCBudgetAcctDeptFilterUI() throws Exception
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
     * output prmtDept_dataChanged method
     */
    protected void prmtDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        super.prmtDept_dataChanged(e);
    }

    /**
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	getUIContext().put("adminOrgUnitInfo", prmtDept.getValue());
    	destroyWindow();
        super.btnOK_actionPerformed(e);
    }

    /**
     * output btnNO_actionPerformed method
     */
    protected void btnNO_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
//    	AdminOrgUnitInfo info = new AdminOrgUnitInfo();
    	getUIContext().put("adminOrgUnitInfo", null);
    	destroyWindow();
        super.btnNO_actionPerformed(e);
    }

    public void onLoad() throws Exception {
    	// TODO 自动生成方法存根
    	
		String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
    	FDCClientUtils.setRespDeptF7(prmtDept, this, cuId);
    	super.onLoad();
    	prmtDept.setEditable(false);
    }
    
    /**
     * @return AdminOrgUnitInfo 行政组织
     * @throws BOSException
     */
    public AdminOrgUnitInfo showUI() throws BOSException{
    	getWindow().show();
//    	if(getUIContext().get("adminOrgUnitInfo") == null){
//    		return null;
//    	}
    	return (AdminOrgUnitInfo) getWindow().getUIObject().getUIContext().get("adminOrgUnitInfo");
    }
    
    private  IUIWindow window = null;
    private  IUIWindow getWindow() throws BOSException{
    	Map context = new HashMap();
    	if(window==null){
    		window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(FDCBudgetAcctDeptFilterUI.class.getName(),context,null,null);
    	}
    	return window;
    }
}