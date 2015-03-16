/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;
import org.jgroups.demos.wb.UserInfoDialog;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SaleManPromptDialog;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class SaleManReportFilterUI extends AbstractSaleManReportFilterUI
{
    public SaleManReportFilterUI() throws Exception {
		super();
	}
	private static final Logger logger = CoreUIObject.getLogger(SaleManReportFilterUI.class);
	private TreeSelectDialog dialog=new TreeSelectDialog(this,TimeAccountReportFilterUI.getSellProjectForSHESellProject(null, MoneySysTypeEnum.SalehouseSys,false));
	   
    public void onLoad() throws Exception {
		super.onLoad();
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
		this.prmtMarketUnit.setValue(null);
		this.prmtUser.setValue(null);
		this.txtFromAmount.setValue(null);
		this.txtToAmount.setValue(null);
		this.txtFromAmount.setNegatived(false);
		this.txtToAmount.setNegatived(false);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.longnumber", SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber()+"%",CompareType.LIKE));
		view.setFilter(filter);		
		this.prmtMarketUnit.setEntityViewInfo(view);
		this.prmtMarketUnit.setEditable(false);
		
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("head.orgUnit.longnumber", SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber()+"%",CompareType.LIKE));
		view.setFilter(filter);	
		MarketingUnitMemberCollection user=MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(view);
		Set userId=new HashSet();
		for(int i=0;i<user.size();i++){
			userId.add(user.get(i).getMember().getId().toString());
		}
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(UserType.PERSON_VALUE),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type",new Integer(UserType.OTHER_VALUE),CompareType.EQUALS) );
		filter.getFilterItems().add(new FilterItemInfo("isDelete", Boolean.TRUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isLocked", Boolean.TRUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isForbidden", Boolean.TRUE,CompareType.NOTEQUALS));
		if(user.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("id", userId,CompareType.INCLUDE));
			filter.setMaskString("(#0 or #1) and #2 and #3 and #4 and #5");
		}else{
			filter.setMaskString("(#0 or #1) and #2 and #3 and #4");
		}
		view.setFilter(filter);		
		this.prmtUser.setEntityViewInfo(view);
		this.prmtUser.setEditable(false);
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
         Object[] user = (Object[])this.prmtUser.getValue();
         if(user!=null ){
			 pp.setObject("user",user);
		 }else{
			 pp.setObject("user",null);
		 }
         Object[] marketUnit = (Object[])this.prmtMarketUnit.getValue();
         if(marketUnit!=null ){
			 pp.setObject("marketUnit",marketUnit);
		 }else{
			 pp.setObject("marketUnit",null);
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
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
		this.prmtUser.setValue(params.getObject("user"));
		this.prmtMarketUnit.setValue(params.getObject("marketUnit"));
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