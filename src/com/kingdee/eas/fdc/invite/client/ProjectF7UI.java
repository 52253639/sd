/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectF7UI extends AbstractProjectF7UI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectF7UI.class);
    
    /**
     * output class constructor
     */
    public ProjectF7UI() throws Exception
    {
        super();
    }
    
    private boolean isCancel = false;


    protected void btnCancel2_actionPerformed(ActionEvent e) throws Exception {
    	disposeUIWindow();
    	setCancel(true);
    }
    
    protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
    	confirm();
    }

	private void confirm() throws Exception {
		checkSelected();
    	getData();
    	setCancel(false);
	}

    public CurProjectInfo getData() throws Exception {
		String id = getSelectedKeyValue();
		CurProjectInfo prjInfo=null;
		prjInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(id));
    	if(!prjInfo.isIsLeaf()) {
    		MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
    		SysUtil.abort();
    	}
		disposeUIWindow();
		return prjInfo;
	}

    public boolean isCancel() {
    	return isCancel;
    }

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}
    
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if (e.getClickCount() == 2) {
            // modify to view when doubleClick row by Jacky 2005-1-7
            if (e.getType() == 0) {
                return;
            }
            confirm();
    	}
    }

    public boolean destroyWindow() {
    	setCancel(true);
    	return super.destroyWindow();
    }
    // 业务系统可重载，执行Query前对EntityViewInfo进行处理。
    protected void beforeExcutQuery(EntityViewInfo ev) {
    	
    	FilterInfo filter = getEnableFilter();
		
		this.getUIContext().put("F7Filter",filter);
		try {
			ev.getFilter().mergeFilter(filter, "and");
		} catch (BOSException e) {
			handUIException(e);
		}
    	
    }

	private FilterInfo getEnableFilter() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));	
		return filter;
	}
    
    protected FilterInfo getDefaultFilterForQuery() {
    	FilterInfo filter = getEnableFilter();
    	return filter;
    }
    
    
    protected FilterInfo getDefaultFilterForTree()
    {
    	FilterInfo filter = super.getDefaultFilterForTree();
    	FilterInfo enableFilter = getEnableFilter();
    	if(filter == null || filter.getFilterItems().isEmpty()) return enableFilter;
    	try {
    		filter.mergeFilter(enableFilter, "and");
		} catch (BOSException e) {
			handUIException(e);
		}
        return filter;
    }
    
    protected boolean isIgnoreTreeCUFilter() {
    	return true;
    }
    protected void refresh(ActionEvent e) throws Exception {
		super.refresh(e);
		if(this.getUIContext().get("noOrgIsolation")!=null){
			buildProjectTree(((Boolean)this.getUIContext().get("noOrgIsolation")).booleanValue());
			this.treeMain.setSelectionRow(0);
		}
	}
    public void onLoad() throws Exception {
    	super.onLoad();
    	getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	if(this.getUIContext().get("noOrgIsolation")!=null){
			buildProjectTree(((Boolean)this.getUIContext().get("noOrgIsolation")).booleanValue());
			this.treeMain.setSelectionRow(0);
		}
    }
    private void buildProjectTree(boolean noOrgIsolation) throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(true,noOrgIsolation);
		projectTreeBuilder.setDevPrjFilter(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
	}
}