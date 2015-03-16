/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractRoomPriceSchemeSetUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomPriceSchemeSetUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceScheme;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7PriceScheme;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblFactors;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblFactorValue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBasePointAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSumPriceAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtBasePointAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSumPriceAmount;
    /**
     * output class constructor
     */
    public AbstractRoomPriceSchemeSetUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomPriceSchemeSetUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contPriceScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7PriceScheme = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblFactors = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblFactorValue = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contBasePointAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSumPriceAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBasePointAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtSumPriceAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contPriceScheme.setName("contPriceScheme");
        this.f7PriceScheme.setName("f7PriceScheme");
        this.tblFactors.setName("tblFactors");
        this.tblFactorValue.setName("tblFactorValue");
        this.contBasePointAmount.setName("contBasePointAmount");
        this.contSumPriceAmount.setName("contSumPriceAmount");
        this.txtBasePointAmount.setName("txtBasePointAmount");
        this.txtSumPriceAmount.setName("txtSumPriceAmount");
        // CoreUI		
        this.setPreferredSize(new Dimension(610,460));
        // contPriceScheme		
        this.contPriceScheme.setBoundLabelText(resHelper.getString("contPriceScheme.boundLabelText"));		
        this.contPriceScheme.setBoundLabelLength(80);		
        this.contPriceScheme.setBoundLabelUnderline(true);
        // f7PriceScheme		
        this.f7PriceScheme.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PriceSetSchemeQuery");		
        this.f7PriceScheme.setDisplayFormat("$name$");		
        this.f7PriceScheme.setEditFormat("$number$");		
        this.f7PriceScheme.setCommitFormat("$number$");		
        this.f7PriceScheme.setRequired(true);
        this.f7PriceScheme.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7PriceScheme_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblFactors
		String tblFactorsStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"factor\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"calMethod\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{factor}</t:Cell><t:Cell>$Resource{calMethod}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblFactors.setFormatXml(resHelper.translateString("tblFactors",tblFactorsStrXML));
        this.tblFactors.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
            public void tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
                try {
                    tblFactors_tableSelectChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // tblFactorValue
		String tblFactorValueStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"factorContent\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"factorValue\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"isBasePoint\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{factorContent}</t:Cell><t:Cell>$Resource{factorValue}</t:Cell><t:Cell>$Resource{isBasePoint}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblFactorValue.setFormatXml(resHelper.translateString("tblFactorValue",tblFactorValueStrXML));
        this.tblFactorValue.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblFactorValue_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblFactorValue.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblFactorValue_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // contBasePointAmount		
        this.contBasePointAmount.setBoundLabelText(resHelper.getString("contBasePointAmount.boundLabelText"));		
        this.contBasePointAmount.setBoundLabelUnderline(true);		
        this.contBasePointAmount.setBoundLabelLength(80);
        // contSumPriceAmount		
        this.contSumPriceAmount.setBoundLabelText(resHelper.getString("contSumPriceAmount.boundLabelText"));		
        this.contSumPriceAmount.setBoundLabelLength(80);		
        this.contSumPriceAmount.setBoundLabelUnderline(true);
        // txtBasePointAmount
        // txtSumPriceAmount
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
        this.setBounds(new Rectangle(10, 10, 610, 430));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 610, 430));
        contPriceScheme.setBounds(new Rectangle(19, 15, 239, 19));
        this.add(contPriceScheme, new KDLayout.Constraints(19, 15, 239, 19, 0));
        tblFactors.setBounds(new Rectangle(15, 87, 249, 325));
        this.add(tblFactors, new KDLayout.Constraints(15, 87, 249, 325, 0));
        tblFactorValue.setBounds(new Rectangle(288, 87, 297, 328));
        this.add(tblFactorValue, new KDLayout.Constraints(288, 87, 297, 328, 0));
        contBasePointAmount.setBounds(new Rectangle(19, 47, 239, 19));
        this.add(contBasePointAmount, new KDLayout.Constraints(19, 47, 239, 19, 0));
        contSumPriceAmount.setBounds(new Rectangle(333, 47, 239, 19));
        this.add(contSumPriceAmount, new KDLayout.Constraints(333, 47, 239, 19, 0));
        //contPriceScheme
        contPriceScheme.setBoundEditor(f7PriceScheme);
        //contBasePointAmount
        contBasePointAmount.setBoundEditor(txtBasePointAmount);
        //contSumPriceAmount
        contSumPriceAmount.setBoundEditor(txtSumPriceAmount);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomPriceSchemeSetUIHandler";
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
     * output f7PriceScheme_dataChanged method
     */
    protected void f7PriceScheme_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblFactors_tableSelectChanged method
     */
    protected void tblFactors_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblFactorValue_editStopped method
     */
    protected void tblFactorValue_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblFactorValue_tableClicked method
     */
    protected void tblFactorValue_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomPriceSchemeSetUI");
    }




}