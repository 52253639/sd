package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.CommonFilterPanel;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.commonquery.client.IPromptBoxFactory;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.custom.fdcprojectcommonquery.ProjectCommonQueryUI;
import com.kingdee.eas.fdc.invite.InviteChangeFormCollection;
import com.kingdee.eas.fdc.invite.InviteChangeFormEntryCollection;
import com.kingdee.eas.fdc.invite.InviteChangeFormEntryFactory;
import com.kingdee.eas.fdc.invite.InviteChangeFormEntryInfo;
import com.kingdee.eas.fdc.invite.InviteChangeFormFactory;
import com.kingdee.eas.framework.FrameWorkUtils;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.client.EASResource;

public class InviteChangeFormListUICTEx extends InviteChangeFormListUI
{
	
	private CommonQueryDialog commonQueryDialog;
    private CustomerQueryPanel filterUI;
   
 
    //采用适配模式，覆写父类方法
    private  class MyQueryDialog extends CommonQueryDialog
    {
	
	
    	@Override
    	public FilterInfo getCommonFilter()
    	{
    	    	FilterInfo commFilter = filterUI.getFilterInfo();
    	    	return commFilter;
    	}
    	

    }
    
    
    protected CommonQueryDialog initCommonQueryDialogs()
    {
        CommonQueryDialog dialog = new MyQueryDialog();
        if(getUIWindow() == null)
            dialog.setOwner((Component)getUIContext().get("OwnerWindow"));
        else
            dialog.setOwner(this);
        dialog.setUiObject(this);
        dialog.setParentUIClassName(getMetaDataPK().getFullName());
        dialog.setQueryObjectPK(mainQueryPK);
        dialog.setTitle(getUITitle() + " - " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Query_Filter"));
        if(orgContextManager != null)
        {
            if(orgContextManager.getOwner() != null && (orgContextManager.getOwner() instanceof CustomerQueryPanel))
                dialog.addUserPanel((CustomerQueryPanel)orgContextManager.getOwner());
            orgContextManager.init(getUIContext());
        }
        IPromptBoxFactory promptBoxFactory = getPromptBoxFactory();
        if(promptBoxFactory != null)
            dialog.setPromptBoxFactory(promptBoxFactory);
        dialog.setMaxReturnCountVisible(true);
        dialog.setVisibleTableCols(getQueryShowField());
        return dialog;
    }
    
    
	   protected CommonQueryDialog initCommonQueryDialog()
	    {
	        if(commonQueryDialog != null)
	        {
	            return commonQueryDialog;
	        } else
	        {
	            commonQueryDialog = initCommonQueryDialogs();
	            commonQueryDialog.setWidth(400);
	            commonQueryDialog.addUserPanel(getFilterUI());
	            
	            return commonQueryDialog;
	        }
	    }
	
	    private CustomerQueryPanel getFilterUI()
	    {
	        if(filterUI == null)
	            try
	            {
	                filterUI = new ProjectCommonQueryUI(this, actionOnLoad);
	            }
	            catch(Exception e)
	            {
	                e.printStackTrace();
	                abort(e);
	            }
	        return filterUI;
	    }
	
	
	

	public InviteChangeFormListUICTEx() throws Exception
	{
		super();
		// TODO Auto-generated constructor stub
	}
	//修改中标审批少页签
	 public static com.kingdee.eas.fdc.invite.InviteChangeFormCollection getBillList(String paramId)
      throws BOSException
  {
      EntityViewInfo view = new EntityViewInfo();
      view.getSelector().add(new SelectorItemInfo("*"));
      //view.getSelector().add(new SelectorItemInfo("respDept.name"));
      view.getSelector().add(new SelectorItemInfo("creator.name"));
      view.getSelector().add(new SelectorItemInfo("auditor.name"));
      FilterInfo filter = new FilterInfo();
      Set set = new HashSet();
      
      InviteChangeFormEntryCollection inviteFormEntryCol = InviteChangeFormEntryFactory.getRemoteInstance().getInviteChangeFormEntryCollection("select parent.id where inviteProject.id='"+paramId+"'");
     
      //InviteChangeFormEntryCollection inviteFormEntryCol = InviteChangeFormEntryFactory.getRemoteInstance().getInviteChangeFormEntryCollection("select parent.id ");

      for(int i=0;i<inviteFormEntryCol.size();i++)
      {
    	  InviteChangeFormEntryInfo  obj= inviteFormEntryCol.get(i);
    	  if(obj==null)
    	  {
    		  continue;
    	  }
    	 String parentId = obj.getParent().getId().toString();
    	  set.add(parentId);
      }
      filter.getFilterItems().add(new FilterItemInfo("id",set ,CompareType.INCLUDE));
      view.setFilter(filter);
      
      
      InviteChangeFormCollection inviteFormCol = InviteChangeFormFactory.getRemoteInstance().getInviteChangeFormCollection(view);
      return inviteFormCol;
  }
	

}
