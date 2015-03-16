/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class SellProjectReportFilterUI extends AbstractSellProjectReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(SellProjectReportFilterUI.class);
    private TreeSelectDialog dialog=new TreeSelectDialog(this,TimeAccountReportFilterUI.getSellProjectForSHESellProject(null, MoneySysTypeEnum.SalehouseSys,true));
    
    /**
     * output class constructor
     */
    public SellProjectReportFilterUI() throws Exception
    {
        super();
    }
	public void onLoad() throws Exception {
		super.onLoad();
		this.pkBeginDate.setValue(null);
		this.pkEndDate.setValue(null);
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
        if(this.pkBeginDate.getValue()!=null){
   		 pp.setObject("beginDate", this.pkBeginDate.getSqlDate());
        }else{
       	 pp.setObject("beginDate", null);
        }
        if(this.pkEndDate.getValue()!=null){
   		 pp.setObject("endDate", this.pkEndDate.getSqlDate());
        }else{
       	 pp.setObject("endDate", null);
        }
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
        
		return pp;
	}

	public void onInit(RptParams arg0) throws Exception {
		
	}

	public void setCustomCondition(RptParams params) {
		this.pkBeginDate.setValue(params.getObject("beginDate"));
		this.pkEndDate.setValue(params.getObject("endDate"));
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