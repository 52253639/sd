package com.kingdee.eas.fdc.invite.client;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.tree.TreeModel;

import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.client.ComponentUtil;


public class CompanyTreeSelectF7 implements KDPromptSelector {
    
    //父窗口
    private Window owner=null;    
    private IUIObject uiOwner=null;
    ListingItemPriceQueryFilterUI queryFilterUI = null;
    CompanyTreeSelectUI companySelectUI;
    //公司IDs
    private String[] companyIds = null;
    private Vector orgUnitVec;
    private KDDialog companySelectDlg = null;
    
    protected CompanyTreeSelectF7()
    {
        this((Frame)null);
    }
    protected CompanyTreeSelectF7(IUIObject _uiObject)
    {
        uiOwner=_uiObject;
    }
    protected CompanyTreeSelectF7(Frame _owner)
    {
        owner=_owner;
    }
    protected CompanyTreeSelectF7(Dialog _owner)
    {
        owner=_owner;
    }
    protected CompanyTreeSelectF7(ListingItemPriceQueryFilterUI _uiObject)
    {
        uiOwner=_uiObject;
        queryFilterUI = _uiObject;
    }
    
    /**
     * 
     * 描述：TODO 
     * @author jxd
     * 创建时间：2006-1-4
     */
    public void show() {
        Window owner=null;
        if(uiOwner!=null) {
            owner=ComponentUtil.getOwnerWindow((Component)uiOwner);
        }
//        
//        //创建UI对象并显示
//        UIContext context = new UIContext(owner);
//        IUIWindow uiWindow = null;
//        try {
//            uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
//                    "com.kingdee.eas.ma.budget.client.CompanyTreeSelectUI", context);
//        } catch (UIException e) {
//            e.printStackTrace();
//        }
//        
//        //f7UI=(BgItemGroupInF7UI)uiWindow.getUIObject();
//        uiWindow.show();
        if(companySelectDlg == null)
        	companySelectDlg = new KDDialog(FDCClientHelper.getFrameAncestor(owner),
                true);        
        try {
            if( companySelectUI==null )
            	companySelectUI = new CompanyTreeSelectUI(); 
            if( queryFilterUI !=null ) {
            	if(queryFilterUI.f7Org.getValue() != null){
            		KDTreeNode root = new KDTreeNode("");
            		KingdeeTreeModel model = new KingdeeTreeModel(root);
            		Vector orgVector = (Vector)queryFilterUI.f7Org.getValue();
                	Object[] orgs = orgVector.toArray();
                	for (int i = 0; i < orgs.length; i++) {
            			FullOrgUnitInfo company = (FullOrgUnitInfo) orgs[i];
            			DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode(company,
            	                null, false, false);
            			model.insertNodeInto(
            					node, root, model.getChildCount(root));
            		}
                	companySelectUI.setValue(model);
            	}
            	
            	
            }
            companySelectUI.setDialog(companySelectDlg);
            companySelectUI.setPreferredSize(companySelectUI.getBounds().getSize());
            companySelectDlg.getContentPane().setLayout(new BorderLayout());
            companySelectDlg.getContentPane().add(companySelectUI,
                    BorderLayout.CENTER);
            companySelectDlg.setSize(640, 480);
            CtrlSwingUtilities.centerWindow(companySelectDlg);
            companySelectDlg.setResizable(false);
            companySelectDlg.setTitle(companySelectUI.getUITitle());
            //        companySelectUI.setValue(this.companyTree);
            companySelectUI.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) {
                    if (e.getPropertyName().equals("companyChanged")) {
                        try {
                            companyValueChange(e.getNewValue());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            //handUIException(e1);
                        }
                    }
                }
            });
        
        } catch (Exception e2) {
            // TODO 自动生成 catch 块
            e2.printStackTrace();
        }        
        companySelectDlg.show();

    }
    
    /**
     * 公司改变时调用
     * 如果只查询实体公司，请覆盖此方法，过滤掉虚体公司
     * 参考CapitalStockFilterUI.companyValueChange();
     * 
     * @param object
     */
    protected void companyValueChange(Object object) throws Exception {     
    	
        this.setCompanyIds(FDCHelper.getIds(object));
        Object[] data ;
        if (object instanceof Object[]){
             data = (Object[]) object;
            
        }
        else{
            data = new Object[]{object};
        }
        if (FDCHelper.isEmpty(data)) {
            //TODO：只有当前公司
            this.setOrgUnitVec(null);
        } else{
            Vector vec = new Vector(data.length);
            for (int i = 0; i < data.length; i++) {
                OrgUnitInfo info = (OrgUnitInfo) data[i];
                vec.add(info);
                //result[i] = info.getId().toString();                
            }
            this.setOrgUnitVec(vec);            
        }
    }
    
    /**
     * 得到公司ids
     * 
     * @param companyIds
     */
    public void setCompanyIds(String[] companyIds) {
        this.companyIds = (String[])companyIds.clone();        
    }
    
    public String[] getCompanyIds() {
    	if(companyIds==null)
    		return new String[0];
    	else
    		return (String[])this.companyIds.clone();
    }   
    
    
    /**
     * 
     * 描述：TODO 
     * @author jxd
     * 创建时间：2006-1-4
     */
    public boolean isCanceled() {        
        return false;
    }
    
    /**
     * 
     * 描述：得到数据 
     * @author jxd
     * 创建时间：2006-1-4
     */
    public Object getData() {       
        //return this.companyIds;
    	return this.orgUnitVec;
        
    }
    /**
     * @return 返回 orgUnitVec。
     */
    public Vector getOrgUnitVec() {
        return orgUnitVec;
    }
    /**
     * @param orgUnitVec 要设置的 orgUnitVec。
     */
    public void setOrgUnitVec(Vector orgUnitVec) {
        this.orgUnitVec = orgUnitVec;
    }

}
