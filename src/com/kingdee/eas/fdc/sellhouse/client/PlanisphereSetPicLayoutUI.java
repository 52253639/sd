/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.EditUI;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PlanisphereSetPicLayoutUI extends AbstractPlanisphereSetPicLayoutUI
{
    private static final Logger logger = CoreUIObject.getLogger(PlanisphereSetPicLayoutUI.class);
    
    /**
     * output class constructor
     */
    public PlanisphereSetPicLayoutUI() throws Exception
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
    	
  	    	
    	SpinnerNumberModel spModel = new SpinnerNumberModel(1, 1, 50, 1);    	
    	this.kDSpinCellHeight.setModel(spModel);
    	spModel = new SpinnerNumberModel(1, 1, 50, 1);
    	this.kDSpinCellWidth.setModel(spModel);
    	spModel = new SpinnerNumberModel(100, 1, 1000, 1);
    	this.kDSpinCellHorizCount.setModel(spModel);
    	spModel = new SpinnerNumberModel(100, 1, 1000, 1);
    	this.kDSpinCellVertiCount.setModel(spModel);
    	
    	Integer cellHeigth = (Integer)this.getUIContext().get("CellHeigth");
    	Integer cellWidth = (Integer)this.getUIContext().get("CellWidth");
    	Integer cellHorizCount = (Integer)this.getUIContext().get("CellHorizCount");
    	Integer cellVertiCount = (Integer)this.getUIContext().get("CellVertiCount");
    	this.kDSpinCellHeight.setValue(cellHeigth);
    	this.kDSpinCellWidth.setValue(cellWidth);
    	this.kDSpinCellHorizCount.setValue(cellHorizCount);
    	this.kDSpinCellVertiCount.setValue(cellVertiCount);
    }
    
    
    protected void btnSubmit_actionPerformed(ActionEvent e) throws Exception {
    	super.btnSubmit_actionPerformed(e);
    	
    	if(MsgBox.showConfirm2("确认要修改设置?")==MsgBox.OK) {
        		this.getUIContext().put("CellHeigth",this.kDSpinCellHeight.getValue());
        		this.getUIContext().put("CellWidth",this.kDSpinCellWidth.getValue());
        		this.getUIContext().put("CellHorizCount",this.kDSpinCellHorizCount.getValue());
        		this.getUIContext().put("CellVertiCount",this.kDSpinCellVertiCount.getValue());
    		this.destroyWindow();
    	}
    }
    
    
    protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
    	super.btnCancel_actionPerformed(e);
    	
    	this.destroyWindow();
    }
    
    
    

}