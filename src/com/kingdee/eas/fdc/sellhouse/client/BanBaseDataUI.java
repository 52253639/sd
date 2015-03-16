/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.BanBasedataCollection;
import com.kingdee.eas.fdc.basedata.BanBasedataEntryInfo;
import com.kingdee.eas.fdc.basedata.BanBasedataFactory;
import com.kingdee.eas.fdc.basedata.BanBasedataInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.IndexVersionInfo;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectCollection;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectFactory;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListFactory;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class BanBaseDataUI extends AbstractBanBaseDataUI
{
    private static final Logger logger = CoreUIObject.getLogger(BanBaseDataUI.class);
    public BanBaseDataUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.prmtBanBasedataEntry.setEnabledMultiSelection(true);
    	List id=(List) this.getUIContext().get("list");
    	if(id!=null){
    		SelectorItemCollection selCol=new SelectorItemCollection();
    		selCol.add("banBasedataEntryList.banBasedataEntry.*");
    		selCol.add("sellProject.*");
    		for(int i=0;i<id.size();i++){
    			BuildingInfo info=BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(id.get(i).toString()),selCol);
    			if(info.getBanBasedataEntryList().size()>0){
    				Object entry[]=new Object[info.getBanBasedataEntryList().size()];
        			for(int j=0;j<info.getBanBasedataEntryList().size();j++){
        				entry[j]=info.getBanBasedataEntryList().get(j).getBanBasedataEntry();
        			}
        			this.prmtBanBasedataEntry.setValue(entry);
    			}
    			
    			SellProjectInfo psp=SHEManageHelper.getParentSellProject(null, info.getSellProject());
    			if(psp!=null){
    				SelectorItemCollection sel=new SelectorItemCollection();
    				sel.add("projectBase.id");
    				psp=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(psp.getId()), sel);
    				if(psp.getProjectBase()!=null){
    					BanBasedataCollection col=BanBasedataFactory.getRemoteInstance().getBanBasedataCollection("select project.*,indexVersion.*,* from where billState='4AUDITTED' and project.projectBase.id='"+psp.getProjectBase().getId().toString()+"' order by IndexVersion.number desc,description desc ");
    					
    					Map map=new HashMap();
    					for(int j=0;j<col.size();j++){
    						if(map.get(col.get(j).getProject().getLongNumber())==null){
    							map.put(col.get(j).getProject().getLongNumber(), col.get(j));
    						}else{
    							BanBasedataInfo pd=(BanBasedataInfo) map.get(col.get(j).getProject().getLongNumber());
    							int number=Integer.parseInt(pd.getIndexVersion().getNumber().replaceFirst("^0*", ""));
    							int nowNumber=Integer.parseInt(col.get(j).getIndexVersion().getNumber().replaceFirst("^0*", ""));
    							if(nowNumber>number){
    								map.put(col.get(j).getProject().getLongNumber(), col.get(j));
    							}
    						}
    					}
    					Set idSet=new HashSet();
    					Object[] key = map.keySet().toArray();
    					for (int k = 0; k < key.length; k++) {
    						idSet.add(((BanBasedataInfo)map.get(key[k])).getId().toString());
    					}
    						
    					EntityViewInfo view=new EntityViewInfo();
    					FilterInfo filter=new FilterInfo();
    					if(idSet.size()>0){
    						filter.getFilterItems().add(new FilterItemInfo("parent.id",idSet,CompareType.INCLUDE));
    					}else{
    						filter.getFilterItems().add(new FilterItemInfo("parent.id","'null'"));
    					}
    					view.setFilter(filter);
    					this.prmtBanBasedataEntry.setEntityViewInfo(view);
    				}
    			}
    		}
    	}
    }
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	List id=(List) this.getUIContext().get("list");
    	if(id!=null){
    		SelectorItemCollection selectors = new SelectorItemCollection();
    		selectors.add("*");
    		selectors.add("floorHeight");
    		selectors.add("sellProject.*");
    		selectors.add("productType.*");
    		selectors.add("buildingProperty.*");
    		selectors.add("constructPart.*");
    		selectors.add("subarea.*");
    		
    		selectors.add("buildingStructure.*");
    		selectors.add("roomModels.roomModelType.*");
    		selectors.add("units.*");
    		selectors.add("floorEntry.*");
    		
    		selectors.add("banBasedataEntry.productType.*");
    		selectors.add("banBasedataEntry.*");
    		selectors.add("banBasedataEntryList.banBasedataEntry.*");
    		
    		Object entry[]=(Object[])this.prmtBanBasedataEntry.getValue();
    		for(int i=0;i<id.size();i++){
    			BuildingInfo info=BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(id.get(i).toString()),selectors);
    			String entryStr="";
    			info.getBanBasedataEntryList().clear();
    			if(entry!=null){
    				for(int j=0;j<entry.length;j++){
    					if(entry[j]==null) continue;
    					BanBasedataEntryListInfo entryInfo=new BanBasedataEntryListInfo();
    					entryInfo.setHead(info);
    					entryInfo.setBanBasedataEntry((BanBasedataEntryInfo) entry[j]);
    					if(j==entry.length-1){
    						entryStr=entryStr+((BanBasedataEntryInfo) entry[j]).getBanNumber();
    					}else{
    						entryStr=entryStr+((BanBasedataEntryInfo) entry[j]).getBanNumber()+";";
    					}
    					info.getBanBasedataEntryList().add(entryInfo);
        			}
    			}
    			info.setDescription(entryStr);
    			BuildingFactory.getRemoteInstance().submit(info);
    		}
    		FDCMsgBox.showInfo(this,"²Ù×÷³É¹¦£¡");
    		this.getUIWindow().close();
    	}
    }
    protected void btnCancell_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.getUIWindow().close();
    }

}