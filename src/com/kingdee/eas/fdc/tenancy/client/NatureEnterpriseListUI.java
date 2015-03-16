/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;

/**
 * output class name
 */
public class NatureEnterpriseListUI extends AbstractNatureEnterpriseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(NatureEnterpriseListUI.class);
    
    /**
     * output class constructor
     */
    public NatureEnterpriseListUI() throws Exception
    {
        super();
    }
	protected String getEditUIModal() {	
		return UIFactoryName.MODEL;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,	EntityViewInfo viewInfo) {
		FilterInfo filter =  viewInfo.getFilter();
		if(filter!=null) {
			FilterItemCollection filterColl = filter.getFilterItems();
			for(int i=0;i<filterColl.size();i++) {
				FilterItemInfo filterItem = filterColl.get(i);
				if(filterItem.getPropertyName().equals("longNumber")){
					filterItem.setCompareValue(((String)filterItem.getCompareValue()).replaceAll("!", "."));
				}
			}
		}		

		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.tenancy.NatureEnterpriseFactory.getRemoteInstance();
    }

    protected ITreeBase getTreeInterface() throws Exception
    {
        return com.kingdee.eas.fdc.tenancy.NatureEnterpriseFactory.getRemoteInstance();
    }

    protected String getLongNumberFieldName()
    {
        return "longNumber";
    }
    
	//过滤条件
	protected void buildTreeFilter()
    {
        KDTreeNode treeNode = getSelectedTreeNode();

        if (treeNode == null)
        {
            return;
        }
        
        if (mainQuery.getFilter() == null)
        {
            mainQuery.setFilter(new FilterInfo());
        }

        //移除上一次的过滤字段项
        FilterItemCollection col=removeQueryFilterAndSorter(mainQuery);

        mainQuery.getFilter().remove("maskString");

        if (treeNode.getUserObject() instanceof TreeBaseInfo)
        {
            col.add(new FilterItemInfo(getLongNumberFieldName() ,
                                       ( (TreeBaseInfo) treeNode.getUserObject()).
                                       getLongNumber()));
            col.add(new FilterItemInfo(getLongNumberFieldName() ,
                                       ( (TreeBaseInfo) treeNode.getUserObject()).
                                       getLongNumber()
                                       + ".%" , CompareType.LIKE));

//            if (!isIncludeAllChildren())
//            {
//                col.add(new FilterItemInfo("level" ,
//                                           new Integer( ( (TreeBaseInfo)
//                    treeNode.getUserObject())
//                    .getLevel() - +1)));
//                mainQuery.getFilter().setMaskString("#0 or (#1 and #2)");
//            }
//            else
//            {
                mainQuery.getFilter().setMaskString("#0 or #1");
//            }
        }
//        else
//        {
//            if (!isIncludeAllChildren())
//            {
//                col.add(new FilterItemInfo("level" , new Integer(1)));
//            }
//        }

        //进行CU过滤。
        try
        {
            if(getDefaultFilterForQuery() != null && getDefaultFilterForQuery().size()>0)
            {
                if(this.mainQuery.getFilter() != null && this.mainQuery.getFilter().getFilterItems().size()>0)
                {

                this.mainQuery.getFilter().mergeFilter(getDefaultFilterForQuery(),
                    "AND");
                }
                else
                {
                    this.mainQuery.setFilter(getDefaultFilterForQuery());
                }
            }
            this.getEntityViewInfo(this.mainQuery);
            if (this.getDefaultEntityViewInfo()!=null)
                MergeCommonQuery(this.getDefaultEntityViewInfo());
        }
        catch (Exception ex)
        {
            this.handleException(ex);
            this.abort();
        }


        if(getCurrentOrder() != null)
        {
            mainQuery.getSorter().add(getCurrentOrder());
        }
        else
        {
            mainQuery.getSorter().add(new SorterItemInfo(getLongNumberFieldName()));
        }
    }
	
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	//检查是否已被客户资料引用
		String idStr = this.getSelectedKeyValue();
		if(idStr!=null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("businessNature.id",idStr));
			if(FDCCustomerFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被客户资料引用，禁止新增!");
				return;
			}
		}
    	super.actionAddNew_actionPerformed(e);
	}
    
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	//检查是否已被客户资料引用
		String idStr = this.getSelectedKeyValue();
		if(idStr!=null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("businessNature.id",idStr));
			if(FDCCustomerFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被客户资料引用，禁止删除!");
				return;
			}
		}
        super.actionRemove_actionPerformed(e);
    }
   
	//重载父类方法，进行CU过滤
	 protected FilterInfo getDefaultFilterForQuery()
	    {
	        return getDefaultCUFilter(isIgnoreCUFilter());
	    }

    protected String getRootName()
    {
        return "企业性质";
    }

    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo objectValue = new com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo();
		
        return objectValue;
    }

}