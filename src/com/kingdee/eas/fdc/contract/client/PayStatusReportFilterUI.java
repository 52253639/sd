/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.ma.budget.BgItemEntryCollection;
import com.kingdee.eas.ma.budget.BgItemEntryFactory;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;

/**
 * output class name
 */
public class PayStatusReportFilterUI extends AbstractPayStatusReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(PayStatusReportFilterUI.class);
    
    public PayStatusReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.prmtBgItem.setValue(null);
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
		this.isAuditted.setSelected(true);
		this.isAuditting.setSelected(true);
		this.isPayed.setSelected(true);
		this.isSaved.setSelected(true);
		this.isSubmitted.setSelected(true);
		
		this.prmtBgItem.setDisplayFormat("$name$");
		this.prmtBgItem.setEditFormat("$number$");
		this.prmtBgItem.setCommitFormat("$number$");
		NewBgItemDialog bgItemDialog=new NewBgItemDialog(this);
		bgItemDialog.setMulSelect(true);
		bgItemDialog.setSelectCombinItem(false);
		this.prmtBgItem.setSelector(bgItemDialog);
		this.prmtBgItem.setEnabledMultiSelection(true);
		this.prmtBgItem.setQueryInfo("com.kingdee.eas.ma.budget.BgItemQuery");
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
         pp.setObject("isSaved", this.isSaved.isSelected());
         pp.setObject("isSubmitted", this.isSubmitted.isSelected());
         pp.setObject("isAuditted", this.isAuditted.isSelected());
         pp.setObject("isAuditting", this.isAuditting.isSelected());
         pp.setObject("isPayed", this.isPayed.isSelected());
         Object[] bgItem = (Object[])this.prmtBgItem.getValue();
         if(bgItem!=null ){
			 pp.setObject("bgItem",bgItem);
		 }else{
			 pp.setObject("bgItem",null);
		 }
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
		if(params.getObject("bgItem")!=null){
			this.prmtBgItem.setValue(params.getObject("bgItem"));
		}else{
			this.prmtBgItem.setValue(null);
		}
		this.isSaved.setSelected(params.getBoolean("isSaved"));
		this.isSubmitted.setSelected(params.getBoolean("isSubmitted"));
		this.isAuditted.setSelected(params.getBoolean("isAuditted"));
		this.isAuditting.setSelected(params.getBoolean("isAuditting"));
		this.isPayed.setSelected(params.getBoolean("isPayed"));
	}
	protected void prmtBgItem_dataChanged(DataChangeEvent e) throws Exception {
		if(this.prmtBgItem.getValue()!=null&&this.prmtBgItem.getValue() instanceof BgItemObject){
			BgItemObject bgItemObject=(BgItemObject) prmtBgItem.getValue();
			Set bgItem=new HashSet();
			Set bgItemId=new HashSet();
			for(int i=0;i<bgItemObject.getResult().size();i++){
				if(bgItemObject.getResult().get(i).isIsLeaf()){
					if(!bgItemId.contains(bgItemObject.getResult().get(i).getId())){
						bgItem.add(bgItemObject.getResult().get(i));
						bgItemId.add(bgItemObject.getResult().get(i).getId());
					}
				}else{
					BgItemEntryCollection col=BgItemEntryFactory.getRemoteInstance().getBgItemEntryCollection("select *,bgItem.* from where isLeaf=1 and isEffective=1 and longNumber like '"+bgItemObject.getResult().get(i).getLongNumber()+"%'");
					for(int k=0;k<col.size();k++){
						if(!bgItemId.contains(bgItemObject.getResult().get(i).getId())){
							bgItem.add(col.get(k).getBgItem());
							bgItemId.add(col.get(k).getBgItem().getId());
						}
					}
				}
			}
			this.prmtBgItem.setValue(bgItem.toArray());
		}
	}
}