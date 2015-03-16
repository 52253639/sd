package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;

public class NoPerSupplierStockEditUI extends NewSupplierStockEditUI{
	public NoPerSupplierStockEditUI() throws Exception {
		super();
	}
	protected void handlePermissionForItemAction(ItemAction action)
    {
		 String actionName = action.getClass().getName();
		 if(actionName.indexOf("$") >= 0)
	            actionName = actionName.substring(actionName.indexOf("$") + 1);
		 if("ActionOnLoad".equals(actionName)){
			 return;
		 }
		 super.handlePermissionForItemAction(action);
    }
	protected void loadReviewGatherUI() {
		try {
			Map map=new HashMap();
			if(editData.getId()!=null){
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("supplier.id" , editData.getId()));
				view.setFilter(filter);
				view.getSelector().add(new SelectorItemInfo("id"));
				view.getSelector().add(new SelectorItemInfo("evaluationType.id"));
				
				SupplierReviewGatherCollection col= SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherCollection(view);
				for(int i=0;i<col.size();i++){
					if(col.get(i).getEvaluationType()==null) continue;
					if(map.get(col.get(i).getEvaluationType().getId().toString())!=null){
						((Set)map.get(col.get(i).getEvaluationType().getId().toString())).add(col.get(i).getId().toString());
					}else{
						Set id=new HashSet();
						id.add(col.get(i).getId().toString());
						map.put(col.get(i).getEvaluationType().getId().toString(), id);
					}
				}
			}
			Iterator it=map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry entry = (Map.Entry) it.next();
				String typeId=entry.getKey().toString();
				Set idSet=(HashSet)entry.getValue();
				
				KDScrollPane panel=new KDScrollPane();
				panel.setName(typeId);
				panel.setMinimumSize(new Dimension(1013,600));		
				panel.setPreferredSize(new Dimension(1013,600));
		        this.kDTabbedPane2.add(panel,SupplierEvaluationTypeFactory.getRemoteInstance().getSupplierEvaluationTypeInfo(new ObjectUuidPK(typeId)).getName());
		        
		        UIContext uiContext = new UIContext(this);
		        uiContext.put("IDSET", idSet);
				NoPerSupplierReviewGatherListUI ui = (NoPerSupplierReviewGatherListUI) UIFactoryHelper.initUIObject(NoPerSupplierReviewGatherListUI.class.getName(), uiContext, null,OprtState.VIEW);
				panel.setViewportView(ui);
				panel.setKeyBoardControl(true);
				panel.setEnabled(false);
			}
		}catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}

}
