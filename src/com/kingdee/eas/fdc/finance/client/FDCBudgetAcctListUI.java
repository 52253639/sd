/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.List;

import javax.swing.Action;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.IFDCBudgetAcct;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class FDCBudgetAcctListUI extends AbstractFDCBudgetAcctListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCBudgetAcctListUI.class);
    /**
     * output class constructor
     */
    public FDCBudgetAcctListUI() throws Exception
    {
        super();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

	protected void audit(List ids) throws Exception {
		((IFDCBudgetAcct)getRemoteInterface()).audit(ids);
	}


	protected void unAudit(List ids) throws Exception {
		((IFDCBudgetAcct)getRemoteInterface()).unAudit(ids);
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		actionRecension.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_emend"));
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);
		actionRecension.setEnabled(true);
		btnRecension.setToolTipText("修订");
	}
	
	public void onLoad() throws Exception {
		OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		try{
			SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentFIUnit());
			Calendar cal=Calendar.getInstance();
			SpinnerNumberModel model=new SpinnerNumberModel();
			model.setMinimum(new Integer(1900));
			model.setMaximum(new Integer(3000));
			model.setStepSize(new Integer(1));
			model.setValue(new Integer(cal.get(Calendar.YEAR)));
			this.spYear.setModel(model);
			model=new SpinnerNumberModel();
			model.setMinimum(new Integer(1));
			model.setMaximum(new Integer(12));
			model.setStepSize(new Integer(1));
			model.setValue(new Integer(cal.get(Calendar.MONTH)+1));
			this.spMonth.setModel(model);
			this.kDPanel2.setPreferredSize(new Dimension(100,40));
			chkAll.setSelected(true);
			spYear.setEnabled(false);
			spMonth.setEnabled(false);
			super.onLoad();
			initCtrlListener();
	    	//元数据改不了,郁闷
	    	this.menuBar.add(menuHelp);
		}finally{
			SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
		}
	}
	public void initCtrlListener(){
		ChangeListener listener=new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				date_dateChanged(e);
			}
		};
		spYear.addChangeListener(listener);
		spMonth.addChangeListener(listener);
		chkAll.addChangeListener(listener);
	}
	
	public void date_dateChanged(ChangeEvent e){
		spYear.setEnabled(!chkAll.isSelected());
		spMonth.setEnabled(!chkAll.isSelected());
		getMainTable().removeRows();
		selectFirstRow();
	}
	protected void execQuery() {
		super.execQuery();
	}

	protected void filterByBillState(EntityViewInfo view) {
		super.filterByBillState(view);
		if(!chkAll.isSelected()){
			//日期过滤
			FilterInfo filter=new FilterInfo();
			if(this.spMonth.isVisible()){
				filter.getFilterItems().add(new FilterItemInfo("fdcPeriod.year",new Integer(getPeriod().getYear())));
				filter.getFilterItems().add(new FilterItemInfo("fdcPeriod.month",new Integer(getPeriod().getMonth())));
			}else{
				filter.getFilterItems().add(new FilterItemInfo("period",new Integer(getPeriod().getYear())));
			}
			if(view.getFilter()!=null){
				try {
					view.getFilter().mergeFilter(filter, "and");
				} catch (BOSException e) {
					handUIException(e);
				}
			}else{
				view.setFilter(filter);
			}
		}
		
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if(act.equals(this.actionAddNew)){
			Object userObject = getProjSelectedTreeNode().getUserObject();
			if (userObject instanceof CurProjectInfo&&((CurProjectInfo)userObject).isIsLeaf()) {
				uiContext.put("project", userObject);
				uiContext.put("fdcPeriod", FDCBudgetPeriodInfo.getCurrentPeriod(!spMonth.isVisible()));
			}
		}else if(act.equals(this.actionRecension)){
			uiContext.put("isRecension", Boolean.TRUE);
		}
	}
	/* 
	 * 修订
	 * @see com.kingdee.eas.fdc.finance.client.AbstractFDCBudgetAcctListUI#actionRecension_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionRecension_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}
	
	protected void checkBeforeEdit(ActionEvent e) throws Exception {
		ItemAction act = getActionFromActionEvent(e);
		if(act.equals(this.actionRecension)){
			checkSelected();
			CoreBaseInfo billInfo = getRemoteInterface().getValue(
					new ObjectUuidPK(getSelectedKeyValue()));
			String billState = billInfo.getString(getBillStatePropertyName());
			String[] states = getBillStateForRecension();
			boolean pass = false;
			for (int i = 0; i < states.length; i++) {
				if (billState.equals(states[i])) {
					pass = true;
				}
			}
			if (!pass) {
				MsgBox.showWarning(this, "当前状态不允许修订");
				SysUtil.abort();
			}
		}else{
			super.checkBeforeEdit(e);
		}

	}
	
	/**
	 * 可修订的状态
	 * @return
	 */
	protected String[] getBillStateForRecension() {
		return new String[] { FDCBillStateEnum.AUDITTED_VALUE};
	}
	
    protected final FDCBudgetPeriodInfo getPeriod(){
    	return FDCBudgetPeriodInfo.getPeriod(getSpValue(spYear), getSpValue(spMonth), !spMonth.isVisible());
    }
    protected int getSpValue(KDSpinner sp){
    	if(sp.getValue()!=null){
    		return ((Integer)sp.getValue()).intValue();
    	}
    	return 0;
    }
    
    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
    	this.menuBar.add(menuFile);
    	this.menuBar.add(menuTool);
    	this.menuBar.add(menuEdit);
    	this.menuBar.add(menuView);
    	this.menuBar.add(menuTools);
    	this.menuBar.add(menuBiz);
    	this.menuBar.add(menuWorkFlow);
    	this.menuBar.add(menuHelp);
    	//元数据改不了,郁闷
    	super.initUIMenuBarLayout();
    }
}