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
public abstract class AbstractRoomTimeFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomTimeFilterUI.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton kDWorkButton2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkPurchase;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkSign;
    protected ActionAddNew actionAddNew = null;
    protected ActionDeleteLine actionDeleteLine = null;
    /**
     * output class constructor
     */
    public AbstractRoomTimeFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomTimeFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddNew
        this.actionAddNew = new ActionAddNew(this);
        getActionManager().registerAction("actionAddNew", actionAddNew);
         this.actionAddNew.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDeleteLine
        this.actionDeleteLine = new ActionDeleteLine(this);
        getActionManager().registerAction("actionDeleteLine", actionDeleteLine);
         this.actionDeleteLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDWorkButton1 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDWorkButton2 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.checkPrePurchase = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.checkPurchase = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.checkSign = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDTable1.setName("kDTable1");
        this.kDWorkButton1.setName("kDWorkButton1");
        this.kDWorkButton2.setName("kDWorkButton2");
        this.kDPanel1.setName("kDPanel1");
        this.checkPrePurchase.setName("checkPrePurchase");
        this.checkPurchase.setName("checkPurchase");
        this.checkSign.setName("checkSign");
        // CustomerQueryPanel
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"start\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"end\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /><t:Column t:key=\"columnName\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{start}</t:Cell><t:Cell>$Resource{end}</t:Cell><t:Cell>$Resource{columnName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));
        this.kDTable1.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kDTable1_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kDWorkButton1
        this.kDWorkButton1.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddNew, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDWorkButton1.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_addline"));		
        this.kDWorkButton1.setToolTipText(resHelper.getString("kDWorkButton1.toolTipText"));
        // kDWorkButton2
        this.kDWorkButton2.setAction((IItemAction)ActionProxyFactory.getProxy(actionDeleteLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDWorkButton2.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_deleteline"));		
        this.kDWorkButton2.setToolTipText(resHelper.getString("kDWorkButton2.toolTipText"));
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // checkPrePurchase		
        this.checkPrePurchase.setText(resHelper.getString("checkPrePurchase.text"));
        // checkPurchase		
        this.checkPurchase.setText(resHelper.getString("checkPurchase.text"));
        // checkSign		
        this.checkSign.setText(resHelper.getString("checkSign.text"));
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
        this.setBounds(new Rectangle(10, 10, 560, 360));
        this.setLayout(null);
        kDTable1.setBounds(new Rectangle(4, 23, 540, 254));
        this.add(kDTable1, null);
        kDWorkButton1.setBounds(new Rectangle(501, 3, 20, 19));
        this.add(kDWorkButton1, null);
        kDWorkButton2.setBounds(new Rectangle(524, 3, 20, 19));
        this.add(kDWorkButton2, null);
        kDPanel1.setBounds(new Rectangle(3, 290, 543, 63));
        this.add(kDPanel1, null);
        //kDPanel1
        kDPanel1.setLayout(null);        checkPrePurchase.setBounds(new Rectangle(35, 23, 140, 19));
        kDPanel1.add(checkPrePurchase, null);
        checkPurchase.setBounds(new Rectangle(212, 23, 140, 19));
        kDPanel1.add(checkPurchase, null);
        checkSign.setBounds(new Rectangle(384, 23, 140, 19));
        kDPanel1.add(checkSign, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomTimeFilterUIHandler";
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
     * output kDTable1_editStopped method
     */
    protected void kDTable1_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    	

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDeleteLine_actionPerformed method
     */
    public void actionDeleteLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAddNew(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddNew() {
    	return false;
    }
	public RequestContext prepareActionDeleteLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDeleteLine() {
    	return false;
    }

    /**
     * output ActionAddNew class
     */     
    protected class ActionAddNew extends ItemAction {     
    
        public ActionAddNew()
        {
            this(null);
        }

        public ActionAddNew(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddNew.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddNew.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddNew.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomTimeFilterUI.this, "ActionAddNew", "actionAddNew_actionPerformed", e);
        }
    }

    /**
     * output ActionDeleteLine class
     */     
    protected class ActionDeleteLine extends ItemAction {     
    
        public ActionDeleteLine()
        {
            this(null);
        }

        public ActionDeleteLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDeleteLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDeleteLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomTimeFilterUI.this, "ActionDeleteLine", "actionDeleteLine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomTimeFilterUI");
    }




}