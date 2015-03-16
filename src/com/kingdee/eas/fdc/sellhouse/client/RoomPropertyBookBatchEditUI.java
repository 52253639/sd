/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class RoomPropertyBookBatchEditUI extends AbstractRoomPropertyBookBatchEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomPropertyBookBatchEditUI.class);
    
    /**
     * output class constructor
     */
    public RoomPropertyBookBatchEditUI() throws Exception
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
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionAddRoom_actionPerformed
     */
    public void actionAddRoom_actionPerformed(ActionEvent e) throws Exception
    {
        
    }

    /**
     * output actionRemoveRoom_actionPerformed
     */
    public void actionRemoveRoom_actionPerformed(ActionEvent e) throws Exception
    {
        
    }

	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomPropertyBatchFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtBook;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		initHeadStyle();
		initTblStyle();
	}
	
	protected void initHeadStyle()
	{
		
	}
	
	protected void initTblStyle()
	{
		
	}

}