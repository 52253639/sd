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
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class CustomerSignReportFilterUI extends AbstractCustomerSignReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerSignReportFilterUI.class);
    private TreeSelectDialog dialog=new TreeSelectDialog(this,TimeAccountReportFilterUI.getSellProjectForSHESellProject(null, MoneySysTypeEnum.SalehouseSys,true));
    
    /**
     * output class constructor
     */
    public CustomerSignReportFilterUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
		super.onLoad();
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
		this.txtFromAmount.setValue(null);
		this.txtToAmount.setValue(null);
		this.pkFromBizDate.setValue(null);
		this.pkToBizDate.setValue(null);
		this.txtFromDealTotalAmount.setValue(null);
		this.txtToDealTotalAmount.setValue(null);
		this.prmtProductType.setValue(null);
		this.txtFromAmount.setNegatived(false);
		this.txtToAmount.setNegatived(false);
		this.txtFromDealTotalAmount.setNegatived(false);
		this.txtToDealTotalAmount.setNegatived(false);
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
        if(this.pkFromDate.getValue()!=null){
    		pp.setObject("fromDate", this.pkFromDate.getValue());
        }else{
        	pp.setObject("fromDate", null);
        }
        if(this.pkToDate.getValue()!=null){
    		pp.setObject("toDate", this.pkToDate.getValue());
        }else{
        	pp.setObject("toDate", null);
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
        if(this.txtFromAmount.getIntegerValue()!=null){
        	pp.setObject("fromAmount", this.txtFromAmount.getIntegerValue());
        }else{
        	pp.setObject("fromAmount", null);
        }
        if(this.txtToAmount.getIntegerValue()!=null){
        	pp.setObject("toAmount", this.txtToAmount.getIntegerValue());
        }else{
        	pp.setObject("toAmount", null);
        }
        if(this.txtFromDealTotalAmount.getBigDecimalValue()!=null){
        	pp.setObject("fromDealTotalAmount", this.txtFromDealTotalAmount.getBigDecimalValue());
        }else{
        	pp.setObject("fromDealTotalAmount", null);
        }
        if(this.txtToDealTotalAmount.getIntegerValue()!=null){
        	pp.setObject("toDealTotalAmount", this.txtToDealTotalAmount.getBigDecimalValue());
        }else{
        	pp.setObject("toDealTotalAmount", null);
        }
        if(this.pkFromBizDate.getValue()!=null){
    		pp.setObject("fromBizDate", this.pkFromBizDate.getValue());
        }else{
        	pp.setObject("fromBizDate", null);
        }
        if(this.pkToBizDate.getValue()!=null){
    		pp.setObject("toBizDate", this.pkToBizDate.getValue());
        }else{
        	pp.setObject("toBizDate", null);
        }
        Object[] productType = (Object[])this.prmtProductType.getValue();
        if(productType!=null){
    		pp.setObject("productType", productType);
        }else{
        	pp.setObject("productType", null);
        }
		return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
		this.pkFromBizDate.setValue(params.getObject("fromBizDate"));
		this.pkToBizDate.setValue(params.getObject("toBizDate"));
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
		this.txtFromAmount.setValue(params.getObject("fromAmount"));
		this.txtToAmount.setValue(params.getObject("toAmount"));
		this.txtFromDealTotalAmount.setValue(params.getObject("fromDealTotalAmount"));
		this.txtToDealTotalAmount.setValue(params.getObject("toDealTotalAmount"));
		this.prmtProductType.setValue(params.getObject("productType"));
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