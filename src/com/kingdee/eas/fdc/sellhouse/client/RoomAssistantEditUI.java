/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.IRoomAssistant;
import com.kingdee.eas.fdc.sellhouse.IRoomAssistantType;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomAssistantEditUI extends AbstractRoomAssistantEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomAssistantEditUI.class);
    Map orgMap = FDCSysContext.getInstance().getOrgMap();
    /**
     * output class constructor
     */
    public RoomAssistantEditUI() throws Exception
    {
        super();
		// 初始化设置按钮隐藏，显示
		actionSave.setVisible(false);
		actionCopy.setVisible(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		menuBiz.setVisible(false);
		txtNumber.setRequired(true);
		txtName.setRequired(true);
		txtNumber.setMaxLength(50);
		txtName.setMaxLength(50);
		txtDescription.setMaxLength(255);
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		this.setSize(290, 177);
		

	
    }

    public void onLoad() throws Exception {
		super.onLoad();
		
		txtNumber.setMaxLength(50);
		txtName.setMaxLength(50);
		txtDescription.setMaxLength(255);
//		txtNumber.setEnabled(false);
		btnCancelCancel.setVisible(false);
		actionCancel.setVisible(false);
		btnCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
//		this.kDLabel1.setEnabled(false);
		
		this.menuTool.setVisible(false);
		this.menuBar.setVisible(false);
		this.menuBar.setEnabled(false);
		this.menuFile.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.btnCancel.setEnabled(false);
			this.btnCancelCancel.setEnabled(false);

			
		}else{
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.btnCancel.setEnabled(true);
			this.btnCancelCancel.setEnabled(true);

			
		}
		if(OprtState.ADDNEW.equals(this.getOprtState())){
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			
		}
		
		if(OprtState.EDIT.equals(this.getOprtState())){
			this.txtName.setEnabled(false);
			this.txtNumber.setEnabled(false);
			
		}
		if(null!=this.editData.getType()){
			if(RoomAssistantTypeEnum.Mutiple.equals(this.editData.getType().getSelType())){
				this.chkIsDefault.setEnabled(false);
				this.chkIsDefault.setSelected(false);
				this.kDLabel1.setEnabled(false);
			}
		}
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (txtNumber.getText() == null || txtNumber.getText().equals("")) {
			MsgBox.showInfo(this, "辅助资料编码不能为空!");
			SysUtil.abort();
		}
		if (txtName.getText() == null || txtName.getText().equals("")) {
			MsgBox.showInfo(this, "辅助资料名称不能为空!");
			SysUtil.abort();
		}
		
	}

	protected IObjectValue createNewData() {
		RoomAssistantTypeInfo groupInfo = (RoomAssistantTypeInfo) this.getUIContext().get("typeInfo");
		RoomAssistantInfo info = new RoomAssistantInfo();
		if(groupInfo!=null){
			info.setType(groupInfo);
		}
		
		SellProjectInfo pro = (SellProjectInfo) this.getUIContext().get("sellProjectInfo");
		if(pro != null){
			info.setSellProject(pro);
		}
		if(null!=	this.getUIContext().get("isDefault")){
			Boolean isDefault = (Boolean)this.getUIContext().get("isDefault");
			info.setIsDefault(isDefault.booleanValue());
		}else{
			info.setIsDefault(false);
		}
		info.setIsEnabled(true);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomAssistantFactory.getRemoteInstance();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getId() != null) {
			if(editData.isIsEnabled()){
				MsgBox.showWarning(this, "已启用，不允许删除!");
				this.abort();
			}
		}
		super.actionEdit_actionPerformed(e);
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getId() != null) {
			if(editData.isIsEnabled()){
				MsgBox.showWarning(this, "已启用，不允许删除!");
				this.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}
	
	public void beforeActionPerformed(ActionEvent e) {
//		if (actionAddNew.getClass().getName().equals(e.getActionCommand()) || actionEdit.getClass().getName().equals(e.getActionCommand())
//				|| actionRemove.getClass().getName().equals(e.getActionCommand())) {
//			AdminOrgUnitInfo orgInfo = SysContext.getSysContext().getCurrentAdminUnit();
//
//			if (orgInfo == null || orgInfo.getUnitLayerType() == null || !("00000000-0000-0000-0000-00000000000162824988".equals(orgInfo.getUnitLayerType().getString("id")))) {
//				MsgBox.showInfo("当前系统组织不是集团,不能操作!");
//				SysUtil.abort();
//			}
//		}
		super.beforeActionPerformed(e);
	}
	
	protected String getViewPermItemName() {
//		return "RetailAssistantData_view";
		return null;
	}
	/**
	 * 
	 * 描述：新增权限
	 */
	protected String getAddNewPermItemName() {
//		return "RetailAssistantData_addnew";
		return null;
	}
	
	protected final String getOnloadPermItemName() {
		String state = getOprtState();
		if (state.equals(OprtState.ADDNEW)) {
			return getAddNewPermItemName();
		}
		return getViewPermItemName();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection s = super.getSelectors();
		s.add("CU");
		s.add("orgUnit");
		s.add("isEnabled");
		s.add("type.*");
		s.add("sellProject.*");
		return s;
	}
	
	public void loadFields()
    {
        dataBinder.loadFields();
//    	if(OprtState.ADDNEW.equals(this.getOprtState())){
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new  FilterInfo();
//			if(null!= this.getUIContext().get("typeInfo")){
//				RoomAssistantTypeInfo roomAssType = (RoomAssistantTypeInfo)this.getUIContext().get("typeInfo");
//				 filter.getFilterItems().add(new FilterItemInfo("type.id",roomAssType.getId().toString()));
//			}
//		
//			SorterItemCollection sorterCol = new SorterItemCollection();
//			SorterItemInfo sorterInfo = new SorterItemInfo("number");
//			
//			sorterInfo.setSortType(SortType.DESCEND);
//			sorterCol.add(sorterInfo);
//			
//				
//				
//			view.setSorter(sorterCol);
//			view.setFilter(filter);
//			IRoomAssistant iRoomAss=null;
//			RoomAssistantCollection roomAssCol =null;
//			try {
//				iRoomAss = ((IRoomAssistant)this.getBizInterface());
//				roomAssCol = iRoomAss.getRoomAssistantCollection(view);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			if(null!=roomAssCol&&roomAssCol.size()>0){
//				RoomAssistantInfo roomAss = roomAssCol.get(0);
//					NumberFormat nf = NumberFormat.getInstance();//设定2为前补零
//					if(roomAss.getNumber().length()<=2){
//						
//					
//					//设置是否使用分组
//					nf.setGroupingUsed(false);
//					//设置最大整数位数
//		       
//		        	 nf.setMaximumIntegerDigits(2);
//			        //设置最小整数位数    
//			        nf.setMinimumIntegerDigits(2);
//			        this.txtNumber.setText(nf.format(new Integer(roomAss.getNumber()).intValue()+1));
//					}else{
//						  this.txtNumber.setText(roomAss.getNumber()+1+"");
//					}
//		       
//			}else{
//				 this.txtNumber.setText("01");
//			}
//		
//			
//		}
    }
    
}