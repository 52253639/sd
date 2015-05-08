/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.*;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.DataObject;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerCollection;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerFactory;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SelectVersionCompareUI extends AbstractSelectVersionCompareUI
{
    private static final Logger logger = CoreUIObject.getLogger(SelectVersionCompareUI.class);
    private ScheduleVerManagerCollection verManagers = null;
    
    /**
     * output class constructor
     */
    public SelectVersionCompareUI() throws Exception
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
		if(this.getUIContext().containsKey("curProject")){
			CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("curProject");
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("project.id",project.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("version",FDCHelper.ZERO,CompareType.GREATER));
			view.getSelector().add("id");
			view.getSelector().add("version");
			SorterItemInfo sorter = new SorterItemInfo();
			sorter.setPropertyName("version");
			sorter.setSortType(SortType.DESCEND);
			view.getSorter().add(sorter);
			verManagers = ScheduleVerManagerFactory.getRemoteInstance().getScheduleVerManagerCollection(view);
			if(verManagers!=null){
				if(verManagers.size()<=1){
					FDCMsgBox.showInfo(this,"该计划只存在一个版本，无需进行比较");
					abort();
				}
				int i=0;
				ActionListener[] baseActions =null;
				ActionListener[] compActions =null;
				if(this.baseBox.getActionListeners()!=null&&this.baseBox.getActionListeners().length>0){
					baseActions = this.baseBox.getActionListeners();
					for(int l=0;l<baseActions.length;l++)
						this.baseBox.removeActionListener(baseActions[l]);
				}
				if(this.compBox.getActionListeners()!=null&&this.compBox.getActionListeners().length>0){
					compActions = this.compBox.getActionListeners();
					for(int l=0;l<compActions.length;l++)
						this.compBox.removeActionListener(compActions[l]);
				}
				for(Iterator it = verManagers.iterator(); it.hasNext();){
					ScheduleVerManagerInfo info = (ScheduleVerManagerInfo)it.next();
					DataObject obj = new DataObject();
					obj.setName(String.valueOf(info.getVersion()));
					obj.setValue(info.getId().toString());
					this.baseBox.addItem(obj);
					if(i>0)
						this.compBox.addItem(obj);
					i++;
				}
				if(baseActions!=null){
					for(int l=0;l<baseActions.length;l++)
						this.baseBox.addActionListener(baseActions[l]);
				}
				if(compActions!=null){
					for(int l=0;l<compActions.length;l++)
						this.compBox.addActionListener(compActions[l]);
				}
				
			}
		}
	}

	protected void baseBox_actionPerformed(ActionEvent e) throws Exception {
		DataObject baseObj = (DataObject)this.baseBox.getSelectedItem();
		
		ActionListener[] compActions =null;
		
		if(this.compBox.getActionListeners()!=null&&this.compBox.getActionListeners().length>0){
			compActions = this.compBox.getActionListeners();
			for(int l=0;l<compActions.length;l++)
				this.compBox.removeActionListener(compActions[l]);
		}
		String selectId = ((DataObject)this.compBox.getSelectedItem()).getValue();
		this.compBox.removeAllItems();
		for(Iterator it = verManagers.iterator(); it.hasNext();){
			ScheduleVerManagerInfo info = (ScheduleVerManagerInfo)it.next();
			if(baseObj.getValue().equals(info.getId().toString()))
				continue;
			DataObject obj = new DataObject();
			obj.setName(String.valueOf(info.getVersion()));
			obj.setValue(info.getId().toString());
			this.compBox.addItem(obj);
			if(selectId.equals(info.getId().toString())){
				this.compBox.setSelectedItem(obj);
			}
		}
		
		if(compActions!=null){
			for(int l=0;l<compActions.length;l++)
				this.compBox.addActionListener(compActions[l]);
		}
	}

	protected void compBox_actionPerformed(ActionEvent e) throws Exception {
		DataObject compObj = (DataObject)this.compBox.getSelectedItem();
		ActionListener[] baseActions =null;
		if(this.baseBox.getActionListeners()!=null&&this.baseBox.getActionListeners().length>0){
			baseActions = this.baseBox.getActionListeners();
			for(int l=0;l<baseActions.length;l++)
				this.baseBox.removeActionListener(baseActions[l]);
		}
		String selectId = ((DataObject)this.baseBox.getSelectedItem()).getValue();
		this.baseBox.removeAllItems();
		for(Iterator it = verManagers.iterator(); it.hasNext();){
			ScheduleVerManagerInfo info = (ScheduleVerManagerInfo)it.next();
			if(compObj.getValue().equals(info.getId().toString()))
				continue;
			DataObject obj = new DataObject();
			obj.setName(String.valueOf(info.getVersion()));
			obj.setValue(info.getId().toString());
			this.baseBox.addItem(obj);
			if(selectId.equals(info.getId().toString())){
				this.baseBox.setSelectedItem(obj);
			}
		}
		if(baseActions!=null){
			for(int l=0;l<baseActions.length;l++)
				this.baseBox.addActionListener(baseActions[l]);
		}
	}

	protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
		String uiName = ScheduleVersionCompareUI.class.getName();
		UIContext uiContext = new UIContext();
		uiContext.put("ID", ((DataObject)(this.baseBox.getSelectedItem())).getValue());
		uiContext.put("baseVerID", ((DataObject)(this.baseBox.getSelectedItem())).getValue());
		uiContext.put("compVerID", ((DataObject)(this.compBox.getSelectedItem())).getValue());
		
		this.disposeUIWindow();
		String type = UIFactoryName.NEWWIN;
		IUIWindow CompareUiWindow = UIFactory.createUIFactory(type).create(uiName, uiContext, null, "VIEW");
		
		if (CompareUiWindow != null) {
			CompareUiWindow.show();
		}

	}
}