/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class CustomerChangeReportFilterUI extends AbstractCustomerChangeReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerChangeReportFilterUI.class);
    private TreeSelectDialog dialog=new TreeSelectDialog(this,TimeAccountReportFilterUI.getSellProjectForSHESellProject(null, MoneySysTypeEnum.SalehouseSys,true));
    
    public CustomerChangeReportFilterUI() throws Exception
    {
        super();
    }
    public boolean verify()
    {
        if(this.txtSellProjectId.getText()==null||"".equals(this.txtSellProjectId.getText()))
        {
            FDCMsgBox.showWarning("项目不能为空！");
            return false;
        }
        return true;
    }
	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         if(this.txtSellProject.getUserObject()!=null){
        	 pp.setObject("tree", this.txtSellProject.getUserObject());
         }else{
        	 pp.setObject("tree", null);
         }
         if(this.txtSellProjectId.getText()!=null&&!"".equals(this.txtSellProjectId.getText())){
        	 pp.setObject("sellProject", this.txtSellProjectId.getText());
         }else{
        	 pp.setObject("sellProject", null);
         }
         if(this.txtSellMan.getText()!=null&&!"".equals(this.txtSellMan.getText())){
        	 pp.setObject("sellMan", this.txtSellMan.getText());
         }else{
        	 pp.setObject("sellMan", null);
         }
         if(this.txtName.getText()!=null&&!"".equals(this.txtName.getText())){
        	 pp.setObject("name", this.txtName.getText());
         }else{
        	 pp.setObject("name", null);
         }
         pp.setObject("isAll", this.cbIsAll.isSelected());
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		if(params.getObject("tree")!=null){
			try {
				List sellProject=new ArrayList();
				getSellProject(sellProject,((DefaultKingdeeTreeNode) ((TreeModel)params.getObject("tree")).getRoot()));
				String spText="";
				String spId="";
				for(int i=0;i<sellProject.size();i++){
					spText=spText+((SellProjectInfo)sellProject.get(i)).getName().toString()+";";
					if(i==0)
						spId+="'"+((SellProjectInfo)sellProject.get(i)).getId().toString()+"'";
		    		else
		    			spId+=",'"+((SellProjectInfo)sellProject.get(i)).getId().toString()+"'";
				}
				this.txtSellProject.setText(spText);
				this.txtSellProject.setUserObject(params.getObject("tree"));
				this.txtSellProjectId.setText(spId);
				
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(params.getObject("sellMan")!=null){
			this.txtSellMan.setText(params.getObject("sellMan").toString());
		}
		if(params.getObject("name")!=null){
			this.txtName.setText(params.getObject("name").toString());
		}
		this.cbIsAll.setSelected(params.getBoolean("isAll"));
	}
	public void getSellProject(List sellProject,DefaultKingdeeTreeNode node) throws BOSException{
		if(node.getUserObject() instanceof SellProjectInfo) {
			sellProject.add(((SellProjectInfo)node.getUserObject()));
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			getSellProject(sellProject,(DefaultKingdeeTreeNode) node.getChildAt(i));
		}
	}
	protected void btnSelect_actionPerformed(ActionEvent e) throws Exception {
		dialog.show();
		if(dialog.getSelectTree()!=null){
			List sellProject=new ArrayList();
			getSellProject(sellProject,(DefaultKingdeeTreeNode) dialog.getSelectTree().getRoot());
			String spText="";
			String spId="";
			for(int i=0;i<sellProject.size();i++){
				spText=spText+((SellProjectInfo)sellProject.get(i)).getName().toString()+";";
				if(i==0)
					spId+="'"+((SellProjectInfo)sellProject.get(i)).getId().toString()+"'";
	    		else
	    			spId+=",'"+((SellProjectInfo)sellProject.get(i)).getId().toString()+"'";
			}
			this.txtSellProject.setText(spText);
			this.txtSellProject.setUserObject(dialog.getSelectTree());
			this.txtSellProjectId.setText(spId);
		}
	}

}