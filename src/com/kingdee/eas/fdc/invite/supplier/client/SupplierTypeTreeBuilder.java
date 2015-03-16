/**
 * 
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import com.enterprisedt.util.debug.Logger;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.IPermissionServiceProvider;
import com.kingdee.eas.base.permission.PermissionServiceProviderFactory;
import com.kingdee.eas.basedata.framework.util.EntityControlTypeUtil;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.DeletedStatusEnum;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.client.EASResource;

/**
 * @(#)			供应商树分类构建				
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @author		杜余
 * @version		EAS7.0		
 * @see						
 */
public class SupplierTypeTreeBuilder {
	private static Logger logger = Logger.getLogger(SupplierTypeTreeBuilder.class);
	public static void builderTree(KDTree tree) throws Exception{
		TreeSelectionListener treeSelectionListener = null;
		ITreeBuilder treeBuilder = null;
		KDTree treeMain = getTypeTree(tree);
		TreeSelectionListener[] listeners = treeMain.getTreeSelectionListeners();
		if (listeners.length > 0) {
			treeSelectionListener = listeners[0];
			treeMain.removeTreeSelectionListener(treeSelectionListener);
		}
	
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getLNTreeNodeCtrl(),	getTreeInitialLevel(), getTreeExpandLevel(), getDefaultFilterForTree());
	
		if (null != getRootName() && !"".equals(getRootName())) {
			KDTreeNode rootNode = new KDTreeNode(getRootObject());
			((DefaultTreeModel) treeMain.getModel()).setRoot(rootNode);
			
		} else {
			((DefaultTreeModel) treeMain.getModel()).setRoot(null);
		}
		
		treeBuilder.buildTree(treeMain);
		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);
	}
	private static String getRootName(){
    	return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", "supType");
    }
    
    public static Object getRootObject(){
    	return getRootName();
    }
    
    
    private static KDTree getTypeTree(KDTree tree){
    	return tree;
    }
    
    private static FilterInfo getDefaultFilterForTree(){
    	FilterInfo filterInfo = new FilterInfo();
        filterInfo.getFilterItems().add(new FilterItemInfo("groupStandard.isBasic", Boolean.TRUE, CompareType.EQUALS));
        //因为是否基准分类有两个,一个是isBasic,一个是id,跟主数据树构建一致,这两个都加上
        filterInfo.getFilterItems().add(new FilterItemInfo("groupStandard.id", "00000000-0000-0000-0000-000000000001BC122A7F", CompareType.EQUALS));
        filterInfo.getFilterItems().add(new FilterItemInfo("deletedStatus", String.valueOf(DeletedStatusEnum.NORMAL.getValue()), CompareType.EQUALS));
        return filterInfo;
    }
    private static ITreeBase getTreeInterface() throws Exception {

		ITreeBase treeBase = null;
			treeBase = CSSPGroupFactory.getRemoteInstance();

		return treeBase;
	}
    
    protected static ILNTreeNodeCtrl getLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getTreeInterface());
	}
    
    protected static int getTreeInitialLevel() {
		return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}

	protected static int getTreeExpandLevel() {
		return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
	}
}
