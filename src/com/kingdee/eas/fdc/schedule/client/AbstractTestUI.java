/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

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
public abstract class AbstractTestUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTestUI.class);
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDContainer ctTable;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUpload;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewAtt;
    protected com.kingdee.bos.ctrl.swing.KDTextField kDTextField1;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlIMG;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblMain;
    protected ActionUpload actionUpload = null;
    protected ActionViewAtt actionViewAtt = null;
    /**
     * output class constructor
     */
    public AbstractTestUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTestUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionUpload
        this.actionUpload = new ActionUpload(this);
        getActionManager().registerAction("actionUpload", actionUpload);
         this.actionUpload.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewAtt
        this.actionViewAtt = new ActionViewAtt(this);
        getActionManager().registerAction("actionViewAtt", actionViewAtt);
         this.actionViewAtt.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.ctTable = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.btnUpload = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewAtt = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTextField1 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pnlIMG = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblMain = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDScrollPane1.setName("kDScrollPane1");
        this.ctTable.setName("ctTable");
        this.btnUpload.setName("btnUpload");
        this.btnViewAtt.setName("btnViewAtt");
        this.kDTextField1.setName("kDTextField1");
        this.pnlIMG.setName("pnlIMG");
        this.tblMain.setName("tblMain");
        // CoreUI
        // kDScrollPane1
        // ctTable		
        this.ctTable.setTitleLength(200);		
        this.ctTable.setTitle(resHelper.getString("ctTable.title"));
        // btnUpload
        this.btnUpload.setAction((IItemAction)ActionProxyFactory.getProxy(actionUpload, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUpload.setText(resHelper.getString("btnUpload.text"));
        // btnViewAtt
        this.btnViewAtt.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewAtt, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewAtt.setText(resHelper.getString("btnViewAtt.text"));
        // kDTextField1
        this.kDTextField1.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent e) {
                try {
                    kDTextField1_vetoableChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kDTextField1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    kDTextField1_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kDTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kDTextField1_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.kDTextField1.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent e) {
                try {
                    kDTextField1_hierarchyChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kDTextField1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent e) {
                try {
                    kDTextField1_caretUpdate(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kDTextField1.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent e) {
                try {
                    kDTextField1_componentRemoved(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.kDTextField1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent e) {
                try {
                    kDTextField1_componentHidden(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.kDTextField1.addInputMethodListener(new com.kingdee.bos.ctrl.swing.event.InputMethodAdapter() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent e) {
                try {
                    kDTextField1_caretPositionChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent e) {
                try {
                    kDTextField1_inputMethodTextChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.kDTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    kDTextField1_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // pnlIMG		
        this.pnlIMG.setPreferredSize(new Dimension(500,350));
        // tblMain
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"attID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"attName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{attID}</t:Cell><t:Cell>$Resource{attName}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));

        

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
        kDScrollPane1.setBounds(new Rectangle(341, 20, 500, 350));
        this.add(kDScrollPane1, new KDLayout.Constraints(341, 20, 500, 350, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        ctTable.setBounds(new Rectangle(21, 19, 300, 350));
        this.add(ctTable, new KDLayout.Constraints(21, 19, 300, 350, 0));
        btnUpload.setBounds(new Rectangle(191, 401, 80, 19));
        this.add(btnUpload, new KDLayout.Constraints(191, 401, 80, 19, 0));
        btnViewAtt.setBounds(new Rectangle(293, 402, 80, 19));
        this.add(btnViewAtt, new KDLayout.Constraints(293, 402, 80, 19, 0));
        kDTextField1.setBounds(new Rectangle(200, 378, 170, 19));
        this.add(kDTextField1, new KDLayout.Constraints(200, 378, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        //kDScrollPane1
        kDScrollPane1.getViewport().add(pnlIMG, null);
        pnlIMG.setLayout(null);        //ctTable
ctTable.getContentPane().setLayout(new BorderLayout(0, 0));        ctTable.getContentPane().add(tblMain, BorderLayout.CENTER);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
	    return "com.kingdee.eas.fdc.schedule.app.TestUIHandler";
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
     * output kDTextField1_vetoableChange method
     */
    protected void kDTextField1_vetoableChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output kDTextField1_propertyChange method
     */
    protected void kDTextField1_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output kDTextField1_caretPositionChanged method
     */
    protected void kDTextField1_caretPositionChanged(java.awt.event.InputMethodEvent e) throws Exception
    {
    }

    /**
     * output kDTextField1_focusLost method
     */
    protected void kDTextField1_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output kDTextField1_actionPerformed method
     */
    protected void kDTextField1_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kDTextField1_hierarchyChanged method
     */
    protected void kDTextField1_hierarchyChanged(java.awt.event.HierarchyEvent e) throws Exception
    {
    }

    /**
     * output kDTextField1_inputMethodTextChanged method
     */
    protected void kDTextField1_inputMethodTextChanged(java.awt.event.InputMethodEvent e) throws Exception
    {
    }

    /**
     * output kDTextField1_caretUpdate method
     */
    protected void kDTextField1_caretUpdate(javax.swing.event.CaretEvent e) throws Exception
    {
    }

    /**
     * output kDTextField1_componentHidden method
     */
    protected void kDTextField1_componentHidden(java.awt.event.ComponentEvent e) throws Exception
    {
    }

    /**
     * output kDTextField1_componentRemoved method
     */
    protected void kDTextField1_componentRemoved(java.awt.event.ContainerEvent e) throws Exception
    {
    }

    	

    /**
     * output actionUpload_actionPerformed method
     */
    public void actionUpload_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewAtt_actionPerformed method
     */
    public void actionViewAtt_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionUpload(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUpload() {
    	return false;
    }
	public RequestContext prepareActionViewAtt(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewAtt() {
    	return false;
    }

    /**
     * output ActionUpload class
     */     
    protected class ActionUpload extends ItemAction {     
    
        public ActionUpload()
        {
            this(null);
        }

        public ActionUpload(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionUpload.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpload.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUpload.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTestUI.this, "ActionUpload", "actionUpload_actionPerformed", e);
        }
    }

    /**
     * output ActionViewAtt class
     */     
    protected class ActionViewAtt extends ItemAction {     
    
        public ActionViewAtt()
        {
            this(null);
        }

        public ActionViewAtt(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionViewAtt.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAtt.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewAtt.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractTestUI.this, "ActionViewAtt", "actionViewAtt_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "TestUI");
    }




}