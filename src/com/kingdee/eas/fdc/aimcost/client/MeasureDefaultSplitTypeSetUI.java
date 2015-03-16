/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.fdc.aimcost.MeasureDefaultSplitTypeSetCollection;
import com.kingdee.eas.fdc.aimcost.MeasureDefaultSplitTypeSetFactory;
import com.kingdee.eas.fdc.aimcost.MeasureDefaultSplitTypeSetInfo;
import com.kingdee.eas.fdc.aimcost.client.AimMeasureCostEditUI.Item;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MeasureDefaultSplitTypeSetUI extends AbstractMeasureDefaultSplitTypeSetUI
{
    private static final Logger logger = CoreUIObject.getLogger(MeasureDefaultSplitTypeSetUI.class);
//	private AimMeasureCostEditUI measureCostEditUI=null;
	private TreeModel costAcctTree = null;
	private Map defaultSetMap=new HashMap();
    /**
     * output class constructor
     */
    public MeasureDefaultSplitTypeSetUI() throws Exception
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
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnOK_actionPerformed(e);
        //save
        FDCSQLBuilder builder=new FDCSQLBuilder();
        builder.appendSql("delete from T_AIM_MeasureDefSplitSet");
        builder.executeUpdate();
        for(int i=0;i<tblMain.getRowCount();i++){
        	IRow row=tblMain.getRow(i);
        	Object obj=row.getUserObject();
        	if(obj instanceof CostAccountInfo){
        		CostAccountInfo acct=(CostAccountInfo)obj;
        		if(acct.isIsLeaf()){
        			Object itemObj=row.getCell("splitType").getValue();
        			if(itemObj instanceof Item){
        				MeasureDefaultSplitTypeSetInfo info=new MeasureDefaultSplitTypeSetInfo();
        				info.setCostAccount(acct);
        				info.setSplitType(((Item)itemObj).key);
        				MeasureDefaultSplitTypeSetFactory.getRemoteInstance().addnew(info);
        			}
        		}
        	}
        }
        this.destroyWindow();
    }

    /**
     * output btnCancel_actionPerformed method
     */
    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnCancel_actionPerformed(e);
        this.destroyWindow();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
		FilterInfo acctFilter = new FilterInfo();
		acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",OrgConstants.DEF_CU_ID));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory
				.getRemoteInstance(), acctFilter);
		defaultSetMap.clear();
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("costAccount.id");
		view.getSelector().add("splitType");
		MeasureDefaultSplitTypeSetCollection c = MeasureDefaultSplitTypeSetFactory.getRemoteInstance().getMeasureDefaultSplitTypeSetCollection(view);
		for(Iterator iter=c.iterator();iter.hasNext();){
			MeasureDefaultSplitTypeSetInfo info =(MeasureDefaultSplitTypeSetInfo)iter.next();
			defaultSetMap.put(info.getCostAccount().getId().toString(), info.getSplitType());	
			
		}
//		this.measureCostEditUI=(AimMeasureCostEditUI)getUIContext().get(UIContext.OWNER);
		fillTable(tblMain);
    }
    
    private void fillTable(KDTable table){
		table.removeRows();
		table.checkParsed();
		int w=table.getWidth();
		table.getColumn("number").setWidth(w/4);
		table.getColumn("name").setWidth(w/4);
		table.getColumn("splitType").setWidth(w-w/4-w/4);
		table.getStyleAttributes().setLocked(true);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
				.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
					.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		table.getTreeColumn().setDepth(maxLevel + 1);
		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) root
					.getChildAt(i);
			fillNode(table, child);
		}
    }
    
    private void fillNode(KDTable  table,DefaultMutableTreeNode node){
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		
		if (costAcct == null) {
			MsgBox.showError("too many costAccount level!");
			return;
		}
		
		if (costAcct.getType() != null&&costAcct.getType().equals(CostAccountTypeEnum.MAIN)) {
			return;
		}

		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
//		row.getStyleAttributes().setLocked(true);
//		row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		row.setUserObject(costAcct);
		String longNumber = costAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("id").setValue(costAcct.getId().toString());
		row.getCell("number").setValue(longNumber);
		row.getCell("name").setValue(costAcct.getName());
		if (node.isLeaf()) {
			loadRow(row);
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
		}
    }
    
    private void loadRow(IRow row){
    	row.getCell("splitType").setEditor(getSplitTypeEditor());
    	String key=(String)defaultSetMap.get(row.getCell("id").getValue());
    	if(key==null){
    		row.getCell("splitType").setValue(null);
    	}else{
    		row.getCell("splitType").setValue(getSelectItem(key));
    	}
    	row.getCell("splitType").getStyleAttributes().setLocked(false);
    }
    
	private KDTDefaultCellEditor editor=null;
	KDTDefaultCellEditor getSplitTypeEditor(){
		if(editor!=null) return editor;
		Object []items=Item.PRODUCTITEMS;
		KDComboBox box=new KDComboBox(items);

		editor=new KDTDefaultCellEditor(box);
		return 	editor;
	}
	
	private Item getSelectItem(String key){
		if(editor==null) return null;
		KDComboBox box = (KDComboBox)editor.getComponent();
		for(int i=0;i<box.getItemCount();i++){
			Item item = (Item)box.getItemAt(i);
			 
			if(key.equals(item.key)){
				return item;
			}
		}
		return null;
	}
}