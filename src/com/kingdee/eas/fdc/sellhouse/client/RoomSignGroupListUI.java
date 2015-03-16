/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class RoomSignGroupListUI extends AbstractRoomSignGroupListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomSignGroupListUI.class);
    
    private Map tableListenersMap = new HashMap();
    /**
     * output class constructor
     */
    public RoomSignGroupListUI() throws Exception
    {
        super();
    }

	protected ICoreBase getBizInterface() throws Exception {
		return RoomSignContractFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return RoomSignContractEditUI.class.getName();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
	}
    
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			FilterInfo roomFilter = (FilterInfo)this.getUIContext().get("RoomFilter");

			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(roomFilter, "and");
			} else {
				viewInfo.setFilter(roomFilter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	
	
	protected void execQuery() {
		super.execQuery();
	}
	
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		
		
		//isPayOff  unPayAmount 
		removeTableFillListener(this.tblMain);
		for(int i=0;i<this.tblMain.getRowCount();i++) {
			IRow row = this.tblMain.getRow(i);
			Object cellObject = row.getCell("unPayAmount").getValue();
			if(cellObject!=null && cellObject instanceof BigDecimal) {
				BigDecimal unPayAmount = (BigDecimal)row.getCell("unPayAmount").getValue();
				if(unPayAmount==null || unPayAmount.compareTo(new BigDecimal(0))>=0) {
					row.getCell("isPayOff").setValue(new Boolean(true));
				}else{
					row.getCell("isPayOff").setValue(new Boolean(false));
				}
			}
		}		
		addTableFillListener(this.tblMain);
	}
	
	
    private void removeTableFillListener(JComponent com) {
		EventListener[] listeners = null;	
 		if(com instanceof KDSpinner){
			listeners = com.getListeners(ChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDTable)com).removeKDTDataFillListener((KDTDataFillListener)listeners[i]);
    		}
    	}
		
		if(listeners!=null && listeners.length>0){
			tableListenersMap.put(com,listeners );
		}
    }
	private void addTableFillListener(JComponent com) {
    	EventListener[] listeners = (EventListener[] )tableListenersMap.get(com);    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDSpinner){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDTable)com).addKDTDataFillListener((KDTDataFillListener)listeners[i]);
	    		}
	    	}
    	}
	}    	
	
	
	
	
    
    
    
}