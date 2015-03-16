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
public abstract class AbstractShowRoomDetailInfoUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractShowRoomDetailInfoUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabel labRoomType;
    protected com.kingdee.bos.ctrl.swing.KDLabel labBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabel labRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabel labTotalAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabel labPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabel labRoomPrice;
    /**
     * output class constructor
     */
    public AbstractShowRoomDetailInfoUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractShowRoomDetailInfoUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.labRoomType = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labRoomArea = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labTotalAmount = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labPrice = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.labRoomType.setName("labRoomType");
        this.labBuildingArea.setName("labBuildingArea");
        this.labRoomArea.setName("labRoomArea");
        this.labTotalAmount.setName("labTotalAmount");
        this.labPrice.setName("labPrice");
        this.labRoomPrice.setName("labRoomPrice");
        // CoreUI		
        this.setPreferredSize(new Dimension(150,145));		
        this.setBackground(new java.awt.Color(254,253,224));		
        this.setBorder(new EtchedBorder(EtchedBorder.RAISED,new Color(255,255,255),new Color(148,145,140)));
        // labRoomType		
        this.labRoomType.setText(resHelper.getString("labRoomType.text"));
        // labBuildingArea		
        this.labBuildingArea.setText(resHelper.getString("labBuildingArea.text"));
        // labRoomArea		
        this.labRoomArea.setText(resHelper.getString("labRoomArea.text"));
        // labTotalAmount		
        this.labTotalAmount.setText(resHelper.getString("labTotalAmount.text"));
        // labPrice		
        this.labPrice.setText(resHelper.getString("labPrice.text"));		
        this.labPrice.setBackground(new java.awt.Color(254,252,216));
        // labRoomPrice		
        this.labRoomPrice.setText(resHelper.getString("labRoomPrice.text"));
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
        this.setBounds(new Rectangle(10, 10, 150, 145));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 150, 145));
        labRoomType.setBounds(new Rectangle(11, 7, 128, 19));
        this.add(labRoomType, new KDLayout.Constraints(11, 7, 128, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        labBuildingArea.setBounds(new Rectangle(11, 120, 128, 19));
        this.add(labBuildingArea, new KDLayout.Constraints(11, 120, 128, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        labRoomArea.setBounds(new Rectangle(11, 95, 128, 19));
        this.add(labRoomArea, new KDLayout.Constraints(11, 95, 128, 19, 0));
        labTotalAmount.setBounds(new Rectangle(11, 29, 128, 19));
        this.add(labTotalAmount, new KDLayout.Constraints(11, 29, 128, 19, 0));
        labPrice.setBounds(new Rectangle(11, 51, 128, 19));
        this.add(labPrice, new KDLayout.Constraints(11, 51, 128, 19, 0));
        labRoomPrice.setBounds(new Rectangle(11, 73, 128, 19));
        this.add(labRoomPrice, new KDLayout.Constraints(11, 73, 128, 19, 0));

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
	    return "com.kingdee.eas.fdc.sellhouse.app.ShowRoomDetailInfoUIHandler";
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ShowRoomDetailInfoUI");
    }




}