/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.invite.TemplateTypeCollection;
import com.kingdee.eas.fdc.invite.TemplateTypeFactory;
import com.kingdee.eas.fdc.invite.TemplateTypeInfo;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class TemplateTypeListUI extends AbstractTemplateTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TemplateTypeListUI.class);
    
    private String LONGNUMBER_COLUMN = "longNumber";
    private String ID_COLUMN = "id"; 
    
    private TemplateTypeInfo selectTypeInfo = null ;
    /**
     * output class constructor
     */
    public TemplateTypeListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception 
    {
    	super.onLoad();
    	
    	this.tblMain.getColumn(this.ID_COLUMN).getStyleAttributes().setHided(true);
    	this.tblMain.getColumn(this.LONGNUMBER_COLUMN).getStyleAttributes().setHided(true);
    	
    }
    protected String getRootName() 
    {
    	String rootName = "评标模板类型";
    	return rootName ;
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
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
        selectTypeInfo = getParentType();
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {

    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	
		if (node == null) {
			this.selectTypeInfo = null ;
			return;
		}
		
		if(node.getUserObject() instanceof TemplateTypeInfo)
		{
			TemplateTypeInfo selectInfo = (TemplateTypeInfo)node.getUserObject();
			
			if(selectInfo.getParent() == null)
			{
				this.selectTypeInfo = null ;
			}
			else
			{
				this.selectTypeInfo = selectInfo ;
			}
			
			HashSet lnUps = new HashSet();
			try
			{
				String oql = "select id where longNumber like '" + selectInfo.getLongNumber() + "!%'" ;
				TemplateTypeCollection cols = TemplateTypeFactory.getRemoteInstance().getTemplateTypeCollection(oql);
				lnUps.add(selectInfo.getId().toString());
				
				for(int i = 0; i < cols.size(); ++i)
				{
					lnUps.add(cols.get(i).getId().toString());
				}
			}catch (BOSException e1)
			{
				handUIException(e1);
			}
			FilterInfo filterInfo = new FilterInfo();
			if(lnUps.size() != 0)
			{
				filterInfo.getFilterItems().add(new FilterItemInfo("id", lnUps, CompareType.INCLUDE));
				this.mainQuery.setFilter(filterInfo);
			}
		}
		else
		{
			this.mainQuery.setFilter(new FilterInfo());
		}
		
		tblMain.removeRows();
    }

	protected ITreeBase getTreeInterface() throws Exception 
	{
		return TemplateTypeFactory.getRemoteInstance();
	}
    protected String getEditUIModal() {
    	return UIFactoryName.MODEL;
    }
    
    protected String getEditUIName() 
    {
    	return TemplateTypeEditUI.class.getName();
    }
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) 
    {
    	super.prepareUIContext(uiContext, e);
    	ItemAction action = getActionFromActionEvent(e);
    	if(action.equals(actionAddNew) || action.equals(actionImportData))
    	{
    		uiContext.put(UIContext.PARENTNODE, selectTypeInfo);
    	}
    }
    private TemplateTypeInfo getParentType()
    {
    	String selectID = getSelectedKeyValue();
    	if(selectID == null)
    		return null ;
    	IObjectPK typePK = new ObjectUuidPK(selectID);
    	
    	TemplateTypeInfo info = null ;
    	try {
			info = TemplateTypeFactory.getRemoteInstance().getTemplateTypeInfo(typePK);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
    	return info ;
    }  
}