/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFontChooser;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.TenancyDisPlaySetting;
import com.kingdee.eas.util.client.MsgBox;


/**
 * output class name
 */
public class TenancyDisplaySettingUI extends AbstractTenancyDisplaySettingUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenancyDisplaySettingUI.class);
    
    TenancyDisPlaySetting setting = new TenancyDisPlaySetting();

	Map proNameMap = new HashMap();

	Map proAliasMap = new HashMap();
	
	Map moneyMap = new HashMap();
	
	//款项类型和对应的套打路径
	private static final String KEY_MONEYDEFINE = "moneyDefine";
	private static final String KEY_PRINTPATH = "printPath";
	
    public TenancyDisplaySettingUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    private KDFontChooser contFont = new KDFontChooser();
    
    public void onLoad() throws Exception {
		super.onLoad();
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID))
		{
			MsgBox.showInfo("非集团组织不能修改显示设置!");
			this.abort();
		}
		initTable();
		Rectangle rectangle = this.labelFont.getBounds();
		rectangle.x += 88;
		rectangle.x -= 3;
		rectangle.width = 600;
		contFont.setBounds(new Rectangle(rectangle));
		this.pnlRoom.add(contFont, new KDLayout.Constraints(rectangle.x, rectangle.y,
				rectangle.width, rectangle.height, 0));
		EntityObjectInfo entity = MetaDataLoaderFactory
				.getRemoteMetaDataLoader().getEntity(
						new RoomInfo().getBOSType());
		PropertyCollection pros = entity.getInheritedProperties();
		
		int tempDebug = 0;
		
		for (int i = 0; i < pros.size(); i++) 
		{
			PropertyInfo pro = pros.get(i);
			comboRoomField.addItem(pro.getAlias());
			this.proNameMap.put(pro.getName(), pro.getAlias());
			this.proAliasMap.put(pro.getAlias(), pro.getName());
			
			if(pro.getAlias().equalsIgnoreCase("编码"))
				tempDebug = i;
			
		}
		this.comboAttachDis.addItem("显示绑定房间个数");
		this.comboAttachDis.addItem("显示具体房间");
		this.comboAttachDis.addItem("不显示绑定房间");
		this.comboUnTenColor.setColor(setting.getUnTenancyColor());
		this.comboWaitTenColor.setColor(setting.getWaitTenancyColor());
		this.comboNewTenColor.setColor(setting.getNewTenancyColor());
		this.comboContinueTenColor.setColor(setting.getContinueTenancyColor());
		this.comboEnlargeTenColor.setColor(setting.getEnlargeTenancyColor());
		this.comboNoTenancyColor.setColor(setting.getNoTenancyColor());
		this.comboKeepDownColor.setColor(setting.getKeepTenancyColor());
		this.comboNoTenancyColor.setColor(setting.getNoTenancyColor());
		this.comboSincerObliColor.setColor(setting.getSincerObliColor());
		this.txtRoomHeight.setValue(new Integer(setting.getRoomHeight()));
		this.txtRoomWidth.setValue(new Integer(setting.getRoomWidth()));
		this.comboRoomField.setSelectedItem(proNameMap.get(setting
				.getDisplayField()));
		this.comboAttachDis.setSelectedIndex(setting.getAttachDisType());
		this.contFont.setSelectionFont(setting.getFont());
		this.comboFrontColor.setColor(setting.getFrontColor());
		
		this.isMoneyDefine.setSelected(setting.getIsMoneyDeifine());
		this.isReceiving.setSelected(setting.getIsReceiving());
		
		this.chkIsAuditRef.setSelected(setting.getIsAuditRef());
		
		moneyMap = setting.getMoneyMap();
		if(moneyMap!=null)
		{
			Set set = moneyMap.keySet();
			Iterator iter = set.iterator();
			while(iter.hasNext())
			{
				IRow row = this.tblMoneyModel.addRow();
				MoneyDefineInfo money = (MoneyDefineInfo)iter.next();
				row.getCell(KEY_MONEYDEFINE).setValue(money);
				row.getCell(KEY_PRINTPATH).setValue(moneyMap.get(money));
			}
			
			this.comboRoomField.setSelectedIndex(tempDebug);
			this.comboRoomField.setEnabled(false);
		}
		
	}
    
    private void initTable()
    {
    	this.tblMoneyModel.checkParsed();    	
		this.tblMoneyModel.getColumn(KEY_MONEYDEFINE).getStyleAttributes().setLocked(true);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblMoneyModel.getColumn("printPath").setEditor(txtEditor);
		this.tblMoneyModel.getColumn("printPath").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_SUBTOTAL_BG_COLOR);
    }
    
    protected void btnAddMoney_actionPerformed(ActionEvent e) throws Exception {
    	super.btnAddMoney_actionPerformed(e);
    	KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery")));
    	EntityViewInfo viewInfo = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();	
    	filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.TENANCYSYS_VALUE));
    	viewInfo.setFilter(filter);
    	dlg.setEntityViewInfo(viewInfo);
		//设置多选
		dlg.setEnabledMultiSelection(true);
		dlg.show();
		Object[] object = (Object[])dlg.getData();
		if(object!=null && object.length>0)
		{
			IRow row = null;
			List list = new ArrayList();
			for(int i=0;i<tblMoneyModel.getRowCount();i++)
			{
				IRow row2 = tblMoneyModel.getRow(i);
				MoneyDefineInfo money = (MoneyDefineInfo)row2.getCell(KEY_MONEYDEFINE).getValue();
				if(money!=null)
				{
					list.add(money.getId().toString());
				}
			}
			if(tblMoneyModel.getRowCount()>0)
			{
				int a = tblMoneyModel.getRowCount();
				for(int j=0;j<object.length;j++)
				{
					MoneyDefineInfo money = (MoneyDefineInfo)object[j];
	                if(TenancyClientHelper.isInclude(money.getId().toString(),list))
					{
						MsgBox.showInfo(money.getName()+"已经存在不要重复添加！");
						return;
					}else
					{
						row = this.tblMoneyModel.addRow(a);
						row.getCell(KEY_MONEYDEFINE).setValue(money);
						a++;
					}
				}
			}else
			{
				for(int j=0;j<object.length;j++)
				{
					MoneyDefineInfo money = (MoneyDefineInfo)object[j];
					row = this.tblMoneyModel.addRow(j);
					row.getCell(KEY_MONEYDEFINE).setValue(money);
				}
			}
			
		}
    }
    
    protected void btnDelMoney_actionPerformed(ActionEvent e) throws Exception {
    	super.btnDelMoney_actionPerformed(e);
    	int activeRowIndex = tblMoneyModel.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = tblMoneyModel.getRowCount();
		}
		tblMoneyModel.removeRow(activeRowIndex);
    }
    
    protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
		super.btnNo_actionPerformed(e);
		this.destroyWindow();
	}
    
	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		super.btnYes_actionPerformed(e);
		setting.setUnTenancyColor(this.comboUnTenColor.getColor());
		setting.setWaitTenancyColor(this.comboWaitTenColor.getColor());
		setting.setNewTenancyColor(this.comboNewTenColor.getColor());
		setting.setContinueTenancyColor(this.comboContinueTenColor.getColor());
		setting.setEnlargeTenancyColor(this.comboEnlargeTenColor.getColor());
		setting.setKeepTenancyColor(this.comboKeepDownColor.getColor());
		setting.setNoTenancyColor(this.comboNoTenancyColor.getColor());
		setting.setSincerObliColor(this.comboSincerObliColor.getColor());
		setting.setRoomHeight(this.txtRoomHeight.getBigDecimalValue()
				.intValue());
		setting.setRoomWidth(this.txtRoomWidth.getBigDecimalValue().intValue());
		setting.setDisplayField((String) proAliasMap.get(this.comboRoomField
				.getSelectedItem()));
		setting.setAttachDisType(this.comboAttachDis.getSelectedIndex());
		setting.setFont(this.contFont.getSelectionFont());
		setting.setFrontColor(this.comboFrontColor.getColor());
		
		setting.setIsMoneyDefine(this.isMoneyDefine.isSelected());
		setting.setIsReceiving(this.isReceiving.isSelected());
		
		setting.setIsAuditRef(this.chkIsAuditRef.isSelected());
		moneyMap = new HashMap();
		for(int i=0;i<tblMoneyModel.getRowCount();i++)
		{
			IRow row = tblMoneyModel.getRow(i);
			MoneyDefineInfo money = (MoneyDefineInfo)row.getCell(KEY_MONEYDEFINE).getValue();
			String printPath = (String)row.getCell(KEY_PRINTPATH).getValue();
			if(printPath==null)
			{
				MsgBox.showInfo("请输入"+money.getName()+"款项对应套打路径");
				return;
			}
			moneyMap.put(money,printPath);
		}
		setting.setMoneyMap(moneyMap);
		setting.save();
		this.destroyWindow();
	}
}