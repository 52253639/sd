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
public abstract class AbstractQuestionAnswerGuideUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractQuestionAnswerGuideUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizScene;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSheCustomer;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuestionDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnNext;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancle;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combBizScene;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSheCustomer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtQuestionDefine;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    /**
     * output class constructor
     */
    public AbstractQuestionAnswerGuideUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractQuestionAnswerGuideUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contBizScene = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSheCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contQuestionDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnNext = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancle = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.combBizScene = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtSheCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtQuestionDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contBizScene.setName("contBizScene");
        this.contSheCustomer.setName("contSheCustomer");
        this.kDContainer1.setName("kDContainer1");
        this.contQuestionDefine.setName("contQuestionDefine");
        this.contNumber.setName("contNumber");
        this.btnNext.setName("btnNext");
        this.btnCancle.setName("btnCancle");
        this.combBizScene.setName("combBizScene");
        this.prmtSheCustomer.setName("prmtSheCustomer");
        this.kDTable1.setName("kDTable1");
        this.prmtQuestionDefine.setName("prmtQuestionDefine");
        this.txtNumber.setName("txtNumber");
        // CoreUI
        // contBizScene		
        this.contBizScene.setBoundLabelText(resHelper.getString("contBizScene.boundLabelText"));		
        this.contBizScene.setBoundLabelLength(100);		
        this.contBizScene.setBoundLabelUnderline(true);
        // contSheCustomer		
        this.contSheCustomer.setBoundLabelLength(100);		
        this.contSheCustomer.setBoundLabelText(resHelper.getString("contSheCustomer.boundLabelText"));		
        this.contSheCustomer.setBoundLabelUnderline(true);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // contQuestionDefine		
        this.contQuestionDefine.setBoundLabelText(resHelper.getString("contQuestionDefine.boundLabelText"));		
        this.contQuestionDefine.setBoundLabelLength(100);		
        this.contQuestionDefine.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelUnderline(true);
        // btnNext		
        this.btnNext.setText(resHelper.getString("btnNext.text"));
        this.btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNext_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnCancle		
        this.btnCancle.setText(resHelper.getString("btnCancle.text"));
        this.btnCancle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCancle_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // combBizScene		
        this.combBizScene.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.QuestPaperBizSceneEnum").toArray());
        this.combBizScene.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    combBizScene_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtSheCustomer		
        this.prmtSheCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECustomerQuery");		
        this.prmtSheCustomer.setDisplayFormat("$name$");		
        this.prmtSheCustomer.setEditFormat("$number$");		
        this.prmtSheCustomer.setCommitFormat("$number$");
        this.prmtSheCustomer.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtSheCustomer_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"topic\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /><t:Column t:key=\"bizScene\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:configured=\"false\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:configured=\"false\"><t:Cell t:configured=\"false\">ID</t:Cell><t:Cell t:configured=\"false\">单据编码</t:Cell><t:Cell t:configured=\"false\">问卷主题</t:Cell><t:Cell t:configured=\"false\">业务场景</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));

        

        // prmtQuestionDefine		
        this.prmtQuestionDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.QuestionPaperDefineQuery");		
        this.prmtQuestionDefine.setDisplayFormat("$topric$");		
        this.prmtQuestionDefine.setEditFormat("$number$");		
        this.prmtQuestionDefine.setCommitFormat("$number$");
        // txtNumber
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
        this.setBounds(new Rectangle(10, 10, 556, 359));
        this.setLayout(null);
        contBizScene.setBounds(new Rectangle(40, 17, 270, 19));
        this.add(contBizScene, null);
        contSheCustomer.setBounds(new Rectangle(40, 47, 270, 19));
        this.add(contSheCustomer, null);
        kDContainer1.setBounds(new Rectangle(24, 136, 497, 163));
        this.add(kDContainer1, null);
        contQuestionDefine.setBounds(new Rectangle(40, 107, 270, 19));
        this.add(contQuestionDefine, null);
        contNumber.setBounds(new Rectangle(40, 77, 270, 19));
        this.add(contNumber, null);
        btnNext.setBounds(new Rectangle(322, 315, 73, 19));
        this.add(btnNext, null);
        btnCancle.setBounds(new Rectangle(414, 315, 73, 19));
        this.add(btnCancle, null);
        //contBizScene
        contBizScene.setBoundEditor(combBizScene);
        //contSheCustomer
        contSheCustomer.setBoundEditor(prmtSheCustomer);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(null);        kDTable1.setBounds(new Rectangle(12, 4, 470, 132));
        kDContainer1.getContentPane().add(kDTable1, null);
        //contQuestionDefine
        contQuestionDefine.setBoundEditor(prmtQuestionDefine);
        //contNumber
        contNumber.setBoundEditor(txtNumber);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.QuestionAnswerGuideUIHandler";
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
	 * ????????У??
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
     * output btnNext_actionPerformed method
     */
    protected void btnNext_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnCancle_actionPerformed method
     */
    protected void btnCancle_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output combBizScene_itemStateChanged method
     */
    protected void combBizScene_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtSheCustomer_dataChanged method
     */
    protected void prmtSheCustomer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "QuestionAnswerGuideUI");
    }




}