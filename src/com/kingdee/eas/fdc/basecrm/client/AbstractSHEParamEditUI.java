/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractSHEParamEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSHEParamEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel6;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isQuitUpdate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isChangeUpdate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox idChangeRoomUpdate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isChangeObjectUpdate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox isSincerUpdate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7QuitMoneyType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ChangeMoneyType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7ChangeRoomMoney;
    protected com.kingdee.bos.ctrl.swing.KDComboBox f7ChangeBalanceObject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SincerMoneyType;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable2;
    /**
     * output class constructor
     */
    public AbstractSHEParamEditUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractSHEParamEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel6 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.isQuitUpdate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isChangeUpdate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.idChangeRoomUpdate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isChangeObjectUpdate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.isSincerUpdate = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.f7QuitMoneyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7ChangeMoneyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7ChangeRoomMoney = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7ChangeBalanceObject = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7SincerMoneyType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTable2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel4.setName("kDPanel4");
        this.kDPanel5.setName("kDPanel5");
        this.kDPanel6.setName("kDPanel6");
        this.kDTable1.setName("kDTable1");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.isQuitUpdate.setName("isQuitUpdate");
        this.isChangeUpdate.setName("isChangeUpdate");
        this.idChangeRoomUpdate.setName("idChangeRoomUpdate");
        this.isChangeObjectUpdate.setName("isChangeObjectUpdate");
        this.isSincerUpdate.setName("isSincerUpdate");
        this.f7QuitMoneyType.setName("f7QuitMoneyType");
        this.f7ChangeMoneyType.setName("f7ChangeMoneyType");
        this.f7ChangeRoomMoney.setName("f7ChangeRoomMoney");
        this.f7ChangeBalanceObject.setName("f7ChangeBalanceObject");
        this.f7SincerMoneyType.setName("f7SincerMoneyType");
        this.kDTable2.setName("kDTable2");
        // CoreUI
        // kDTabbedPane1
        // kDPanel3
        // kDPanel4
        // kDPanel5
        // kDPanel6
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"basePrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"signGathering\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"mortagage\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"actGathering\" t:width=\"170\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isSincerSellOrder\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isSincerPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isHouseMoney\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isEditPurAndSignDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isAdjustPrices\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{basePrice}</t:Cell><t:Cell>$Resource{signGathering}</t:Cell><t:Cell>$Resource{mortagage}</t:Cell><t:Cell>$Resource{actGathering}</t:Cell><t:Cell>$Resource{isSincerSellOrder}</t:Cell><t:Cell>$Resource{isSincerPrice}</t:Cell><t:Cell>$Resource{isHouseMoney}</t:Cell><t:Cell>$Resource{isEditPurAndSignDate}</t:Cell><t:Cell>$Resource{isAdjustPrices}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));

        

        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelLength(100);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // isQuitUpdate		
        this.isQuitUpdate.setText(resHelper.getString("isQuitUpdate.text"));
        this.isQuitUpdate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    isQuitUpdate_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // isChangeUpdate		
        this.isChangeUpdate.setText(resHelper.getString("isChangeUpdate.text"));
        this.isChangeUpdate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    isChangeUpdate_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // idChangeRoomUpdate		
        this.idChangeRoomUpdate.setText(resHelper.getString("idChangeRoomUpdate.text"));
        this.idChangeRoomUpdate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    idChangeRoomUpdate_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // isChangeObjectUpdate		
        this.isChangeObjectUpdate.setText(resHelper.getString("isChangeObjectUpdate.text"));
        this.isChangeObjectUpdate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    isChangeObjectUpdate_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // isSincerUpdate		
        this.isSincerUpdate.setText(resHelper.getString("isSincerUpdate.text"));
        this.isSincerUpdate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    isSincerUpdate_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7QuitMoneyType		
        this.f7QuitMoneyType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7QuitMoneyType.setDisplayFormat("$name$");		
        this.f7QuitMoneyType.setEditFormat("$number$");		
        this.f7QuitMoneyType.setCommitFormat("$number$");
        // f7ChangeMoneyType		
        this.f7ChangeMoneyType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7ChangeMoneyType.setEditFormat("$number$");		
        this.f7ChangeMoneyType.setDisplayFormat("$name$");		
        this.f7ChangeMoneyType.setCommitFormat("$number$");
        // f7ChangeRoomMoney		
        this.f7ChangeRoomMoney.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7ChangeRoomMoney.setCommitFormat("$number$");		
        this.f7ChangeRoomMoney.setEditFormat("$number$");		
        this.f7ChangeRoomMoney.setDisplayFormat("$name$");
        // f7ChangeBalanceObject		
        this.f7ChangeBalanceObject.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum").toArray());
        // f7SincerMoneyType		
        this.f7SincerMoneyType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");		
        this.f7SincerMoneyType.setDisplayFormat("$name$");		
        this.f7SincerMoneyType.setEditFormat("$number$");		
        this.f7SincerMoneyType.setCommitFormat("$number$");
        // kDTable2
		String kDTable2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"preLevelAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"isLevelModify\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"preStandAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"isStandModify\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{preLevelAmount}</t:Cell><t:Cell>$Resource{isLevelModify}</t:Cell><t:Cell>$Resource{preStandAmount}</t:Cell><t:Cell>$Resource{isStandModify}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable2.setFormatXml(resHelper.translateString("kDTable2",kDTable2StrXML));

        

		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        kDTabbedPane1.setBounds(new Rectangle(15, 17, 985, 599));
        this.add(kDTabbedPane1, new KDLayout.Constraints(15, 17, 985, 599, 0));
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
        kDTabbedPane1.add(kDPanel4, resHelper.getString("kDPanel4.constraints"));
        kDTabbedPane1.add(kDPanel5, resHelper.getString("kDPanel5.constraints"));
        kDTabbedPane1.add(kDPanel6, resHelper.getString("kDPanel6.constraints"));
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(0, 0, 984, 566));        kDTable1.setBounds(new Rectangle(3, 4, 628, 314));
        kDPanel3.add(kDTable1, new KDLayout.Constraints(3, 4, 628, 314, 0));
        //kDPanel4
        kDPanel4.setLayout(null);        kDLabelContainer5.setBounds(new Rectangle(19, 40, 270, 19));
        kDPanel4.add(kDLabelContainer5, null);
        kDLabelContainer6.setBounds(new Rectangle(19, 88, 270, 19));
        kDPanel4.add(kDLabelContainer6, null);
        kDLabelContainer7.setBounds(new Rectangle(19, 141, 270, 19));
        kDPanel4.add(kDLabelContainer7, null);
        kDLabelContainer8.setBounds(new Rectangle(18, 193, 270, 19));
        kDPanel4.add(kDLabelContainer8, null);
        kDLabelContainer9.setBounds(new Rectangle(19, 245, 270, 19));
        kDPanel4.add(kDLabelContainer9, null);
        isQuitUpdate.setBounds(new Rectangle(378, 40, 110, 19));
        kDPanel4.add(isQuitUpdate, null);
        isChangeUpdate.setBounds(new Rectangle(378, 87, 110, 19));
        kDPanel4.add(isChangeUpdate, null);
        idChangeRoomUpdate.setBounds(new Rectangle(378, 140, 110, 19));
        kDPanel4.add(idChangeRoomUpdate, null);
        isChangeObjectUpdate.setBounds(new Rectangle(378, 192, 110, 19));
        kDPanel4.add(isChangeObjectUpdate, null);
        isSincerUpdate.setBounds(new Rectangle(378, 245, 110, 19));
        kDPanel4.add(isSincerUpdate, null);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(f7QuitMoneyType);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(f7ChangeMoneyType);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(f7ChangeRoomMoney);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(f7ChangeBalanceObject);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(f7SincerMoneyType);
        //kDPanel5
        kDPanel5.setLayout(new KDLayout());
        kDPanel5.putClientProperty("OriginalBounds", new Rectangle(0, 0, 984, 566));        kDTable2.setBounds(new Rectangle(3, 4, 629, 315));
        kDPanel5.add(kDTable2, new KDLayout.Constraints(3, 4, 629, 315, 0));
        kDPanel6.setLayout(new KDLayout());
        kDPanel6.putClientProperty("OriginalBounds", new Rectangle(0, 0, 984, 566));
    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basecrm.app.SHEParamEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output isQuitUpdate_stateChanged method
     */
    protected void isQuitUpdate_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output isChangeUpdate_stateChanged method
     */
    protected void isChangeUpdate_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output idChangeRoomUpdate_stateChanged method
     */
    protected void idChangeRoomUpdate_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output isChangeObjectUpdate_stateChanged method
     */
    protected void isChangeObjectUpdate_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output isSincerUpdate_stateChanged method
     */
    protected void isSincerUpdate_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basecrm.client", "SHEParamEditUI");
    }




}