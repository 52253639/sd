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
public abstract class AbstractCompensateRoomSelectUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCompensateRoomSelectUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kdpRoom;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdpCompensate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer17;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRoom;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCustomer;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPuchaseDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkSignDate;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtBuildArea;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPurchaseNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConNumber;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtActBuildArea;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtActRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSaleMan;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayType;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtSellTotal;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtConBuildArea;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtConRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer18;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer19;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer20;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer21;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer22;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer23;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer24;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer25;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer26;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer27;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer28;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompensateNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtScheme;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtReferAmount;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtLastAmount;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDesc;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCompensateDate;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtMainAmount;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtActAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHandler;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtAttAmount;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField txtRate;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractCompensateRoomSelectUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractCompensateRoomSelectUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kdpRoom = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdpCompensate = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer17 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRoom = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCustomer = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkPuchaseDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkSignDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtBuildArea = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtRoomArea = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPurchaseNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtConNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtActBuildArea = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtActRoomArea = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtSaleMan = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtPayType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtSellTotal = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtConBuildArea = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtConRoomArea = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.kDLabelContainer18 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer19 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer20 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer21 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer22 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer23 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer24 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer25 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer26 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer27 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer28 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCompensateNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtScheme = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtReferAmount = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtLastAmount = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtDesc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkCompensateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtMainAmount = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtActAmount = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.prmtHandler = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtAttAmount = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.txtRate = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.kdpRoom.setName("kdpRoom");
        this.kdpCompensate.setName("kdpCompensate");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.kDLabelContainer17.setName("kDLabelContainer17");
        this.prmtSellProject.setName("prmtSellProject");
        this.txtRoom.setName("txtRoom");
        this.txtCustomer.setName("txtCustomer");
        this.pkPuchaseDate.setName("pkPuchaseDate");
        this.pkSignDate.setName("pkSignDate");
        this.txtBuildArea.setName("txtBuildArea");
        this.txtRoomArea.setName("txtRoomArea");
        this.txtPhone.setName("txtPhone");
        this.txtPurchaseNumber.setName("txtPurchaseNumber");
        this.txtConNumber.setName("txtConNumber");
        this.txtActBuildArea.setName("txtActBuildArea");
        this.txtActRoomArea.setName("txtActRoomArea");
        this.txtSaleMan.setName("txtSaleMan");
        this.prmtPayType.setName("prmtPayType");
        this.txtSellTotal.setName("txtSellTotal");
        this.txtConBuildArea.setName("txtConBuildArea");
        this.txtConRoomArea.setName("txtConRoomArea");
        this.kDLabelContainer18.setName("kDLabelContainer18");
        this.kDLabelContainer19.setName("kDLabelContainer19");
        this.kDLabelContainer20.setName("kDLabelContainer20");
        this.kDLabelContainer21.setName("kDLabelContainer21");
        this.kDLabelContainer22.setName("kDLabelContainer22");
        this.kDLabelContainer23.setName("kDLabelContainer23");
        this.kDLabelContainer24.setName("kDLabelContainer24");
        this.kDLabelContainer25.setName("kDLabelContainer25");
        this.kDLabelContainer26.setName("kDLabelContainer26");
        this.kDLabelContainer27.setName("kDLabelContainer27");
        this.kDLabelContainer28.setName("kDLabelContainer28");
        this.txtCompensateNumber.setName("txtCompensateNumber");
        this.txtScheme.setName("txtScheme");
        this.txtReferAmount.setName("txtReferAmount");
        this.txtLastAmount.setName("txtLastAmount");
        this.txtDesc.setName("txtDesc");
        this.pkCompensateDate.setName("pkCompensateDate");
        this.txtMainAmount.setName("txtMainAmount");
        this.txtActAmount.setName("txtActAmount");
        this.prmtHandler.setName("prmtHandler");
        this.txtAttAmount.setName("txtAttAmount");
        this.txtRate.setName("txtRate");
        // CoreUI
        // kdpRoom		
        this.kdpRoom.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kdpRoom.border.title")));
        // kdpCompensate		
        this.kdpCompensate.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kdpCompensate.border.title")));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelLength(100);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
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
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(100);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(100);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(100);		
        this.kDLabelContainer17.setBoundLabelUnderline(true);
        // prmtSellProject
        // txtRoom
        // txtCustomer
        // pkPuchaseDate
        // pkSignDate
        // txtBuildArea		
        this.txtBuildArea.setPrecision(2);		
        this.txtBuildArea.setDataType(6);
        // txtRoomArea		
        this.txtRoomArea.setDataType(6);		
        this.txtRoomArea.setPrecision(2);
        // txtPhone
        // txtPurchaseNumber
        // txtConNumber
        // txtActBuildArea		
        this.txtActBuildArea.setPrecision(2);		
        this.txtActBuildArea.setDataType(6);
        // txtActRoomArea		
        this.txtActRoomArea.setDataType(6);		
        this.txtActRoomArea.setPrecision(2);
        // txtSaleMan
        // prmtPayType
        // txtSellTotal		
        this.txtSellTotal.setDataType(6);		
        this.txtSellTotal.setPrecision(2);
        // txtConBuildArea		
        this.txtConBuildArea.setPrecision(2);		
        this.txtConBuildArea.setDataType(6);
        // txtConRoomArea		
        this.txtConRoomArea.setDataType(6);		
        this.txtConRoomArea.setPrecision(2);
        // kDLabelContainer18		
        this.kDLabelContainer18.setBoundLabelText(resHelper.getString("kDLabelContainer18.boundLabelText"));		
        this.kDLabelContainer18.setBoundLabelLength(100);		
        this.kDLabelContainer18.setBoundLabelUnderline(true);
        // kDLabelContainer19		
        this.kDLabelContainer19.setBoundLabelText(resHelper.getString("kDLabelContainer19.boundLabelText"));		
        this.kDLabelContainer19.setBoundLabelLength(100);		
        this.kDLabelContainer19.setBoundLabelUnderline(true);
        // kDLabelContainer20		
        this.kDLabelContainer20.setBoundLabelText(resHelper.getString("kDLabelContainer20.boundLabelText"));		
        this.kDLabelContainer20.setBoundLabelLength(100);		
        this.kDLabelContainer20.setBoundLabelUnderline(true);
        // kDLabelContainer21		
        this.kDLabelContainer21.setBoundLabelText(resHelper.getString("kDLabelContainer21.boundLabelText"));		
        this.kDLabelContainer21.setBoundLabelLength(100);		
        this.kDLabelContainer21.setBoundLabelUnderline(true);
        // kDLabelContainer22		
        this.kDLabelContainer22.setBoundLabelText(resHelper.getString("kDLabelContainer22.boundLabelText"));		
        this.kDLabelContainer22.setBoundLabelLength(100);		
        this.kDLabelContainer22.setBoundLabelUnderline(true);
        // kDLabelContainer23		
        this.kDLabelContainer23.setBoundLabelText(resHelper.getString("kDLabelContainer23.boundLabelText"));		
        this.kDLabelContainer23.setBoundLabelLength(100);		
        this.kDLabelContainer23.setBoundLabelUnderline(true);
        // kDLabelContainer24		
        this.kDLabelContainer24.setBoundLabelText(resHelper.getString("kDLabelContainer24.boundLabelText"));		
        this.kDLabelContainer24.setBoundLabelLength(100);		
        this.kDLabelContainer24.setBoundLabelUnderline(true);
        // kDLabelContainer25		
        this.kDLabelContainer25.setBoundLabelText(resHelper.getString("kDLabelContainer25.boundLabelText"));		
        this.kDLabelContainer25.setBoundLabelLength(100);		
        this.kDLabelContainer25.setBoundLabelUnderline(true);
        // kDLabelContainer26		
        this.kDLabelContainer26.setBoundLabelText(resHelper.getString("kDLabelContainer26.boundLabelText"));		
        this.kDLabelContainer26.setBoundLabelLength(100);		
        this.kDLabelContainer26.setBoundLabelUnderline(true);
        // kDLabelContainer27		
        this.kDLabelContainer27.setBoundLabelText(resHelper.getString("kDLabelContainer27.boundLabelText"));		
        this.kDLabelContainer27.setBoundLabelLength(100);		
        this.kDLabelContainer27.setBoundLabelUnderline(true);
        // kDLabelContainer28		
        this.kDLabelContainer28.setBoundLabelText(resHelper.getString("kDLabelContainer28.boundLabelText"));		
        this.kDLabelContainer28.setBoundLabelLength(100);		
        this.kDLabelContainer28.setBoundLabelUnderline(true);
        // txtCompensateNumber
        // txtScheme
        // txtReferAmount		
        this.txtReferAmount.setDataType(6);		
        this.txtReferAmount.setPrecision(2);
        // txtLastAmount		
        this.txtLastAmount.setPrecision(2);		
        this.txtLastAmount.setDataType(6);
        // txtDesc
        // pkCompensateDate
        // txtMainAmount		
        this.txtMainAmount.setDataType(6);		
        this.txtMainAmount.setPrecision(2);
        // txtActAmount		
        this.txtActAmount.setPrecision(2);		
        this.txtActAmount.setDataType(6);
        // prmtHandler
        // txtAttAmount		
        this.txtAttAmount.setDataType(6);		
        this.txtAttAmount.setPrecision(2);
        // txtRate		
        this.txtRate.setPrecision(2);		
        this.txtRate.setDataType(6);
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
        this.setBounds(new Rectangle(10, 10, 940, 570));
        this.setLayout(null);
        kdpRoom.setBounds(new Rectangle(17, 29, 910, 279));
        this.add(kdpRoom, null);
        kdpCompensate.setBounds(new Rectangle(17, 318, 910, 226));
        this.add(kdpCompensate, null);
        //kdpRoom
        kdpRoom.setLayout(new KDLayout());
        kdpRoom.putClientProperty("OriginalBounds", new Rectangle(17, 29, 910, 279));        kDLabelContainer1.setBounds(new Rectangle(21, 24, 270, 19));
        kdpRoom.add(kDLabelContainer1, new KDLayout.Constraints(21, 24, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(319, 24, 270, 19));
        kdpRoom.add(kDLabelContainer2, new KDLayout.Constraints(319, 24, 270, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(21, 68, 270, 19));
        kdpRoom.add(kDLabelContainer3, new KDLayout.Constraints(21, 68, 270, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(21, 113, 270, 19));
        kdpRoom.add(kDLabelContainer4, new KDLayout.Constraints(21, 113, 270, 19, 0));
        kDLabelContainer5.setBounds(new Rectangle(21, 159, 270, 19));
        kdpRoom.add(kDLabelContainer5, new KDLayout.Constraints(21, 159, 270, 19, 0));
        kDLabelContainer6.setBounds(new Rectangle(21, 202, 270, 19));
        kdpRoom.add(kDLabelContainer6, new KDLayout.Constraints(21, 202, 270, 19, 0));
        kDLabelContainer7.setBounds(new Rectangle(21, 238, 270, 19));
        kdpRoom.add(kDLabelContainer7, new KDLayout.Constraints(21, 238, 270, 19, 0));
        kDLabelContainer8.setBounds(new Rectangle(319, 68, 270, 19));
        kdpRoom.add(kDLabelContainer8, new KDLayout.Constraints(319, 68, 270, 19, 0));
        kDLabelContainer9.setBounds(new Rectangle(319, 113, 270, 19));
        kdpRoom.add(kDLabelContainer9, new KDLayout.Constraints(319, 113, 270, 19, 0));
        kDLabelContainer10.setBounds(new Rectangle(319, 159, 270, 19));
        kdpRoom.add(kDLabelContainer10, new KDLayout.Constraints(319, 159, 270, 19, 0));
        kDLabelContainer11.setBounds(new Rectangle(319, 202, 270, 19));
        kdpRoom.add(kDLabelContainer11, new KDLayout.Constraints(319, 202, 270, 19, 0));
        kDLabelContainer12.setBounds(new Rectangle(319, 238, 270, 19));
        kdpRoom.add(kDLabelContainer12, new KDLayout.Constraints(319, 238, 270, 19, 0));
        kDLabelContainer13.setBounds(new Rectangle(619, 68, 270, 19));
        kdpRoom.add(kDLabelContainer13, new KDLayout.Constraints(619, 68, 270, 19, 0));
        kDLabelContainer14.setBounds(new Rectangle(619, 113, 270, 19));
        kdpRoom.add(kDLabelContainer14, new KDLayout.Constraints(619, 113, 270, 19, 0));
        kDLabelContainer15.setBounds(new Rectangle(619, 159, 270, 19));
        kdpRoom.add(kDLabelContainer15, new KDLayout.Constraints(619, 159, 270, 19, 0));
        kDLabelContainer16.setBounds(new Rectangle(619, 202, 270, 19));
        kdpRoom.add(kDLabelContainer16, new KDLayout.Constraints(619, 202, 270, 19, 0));
        kDLabelContainer17.setBounds(new Rectangle(619, 238, 270, 19));
        kdpRoom.add(kDLabelContainer17, new KDLayout.Constraints(619, 238, 270, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtSellProject);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtRoom);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtCustomer);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(pkPuchaseDate);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(pkSignDate);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtBuildArea);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(txtRoomArea);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(txtPhone);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(txtPurchaseNumber);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(txtConNumber);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtActBuildArea);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtActRoomArea);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(txtSaleMan);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(prmtPayType);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(txtSellTotal);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(txtConBuildArea);
        //kDLabelContainer17
        kDLabelContainer17.setBoundEditor(txtConRoomArea);
        //kdpCompensate
        kdpCompensate.setLayout(new KDLayout());
        kdpCompensate.putClientProperty("OriginalBounds", new Rectangle(17, 318, 910, 226));        kDLabelContainer18.setBounds(new Rectangle(24, 25, 270, 19));
        kdpCompensate.add(kDLabelContainer18, new KDLayout.Constraints(24, 25, 270, 19, 0));
        kDLabelContainer19.setBounds(new Rectangle(24, 61, 270, 19));
        kdpCompensate.add(kDLabelContainer19, new KDLayout.Constraints(24, 61, 270, 19, 0));
        kDLabelContainer20.setBounds(new Rectangle(24, 94, 270, 19));
        kdpCompensate.add(kDLabelContainer20, new KDLayout.Constraints(24, 94, 270, 19, 0));
        kDLabelContainer21.setBounds(new Rectangle(24, 131, 270, 19));
        kdpCompensate.add(kDLabelContainer21, new KDLayout.Constraints(24, 131, 270, 19, 0));
        kDLabelContainer22.setBounds(new Rectangle(24, 163, 866, 38));
        kdpCompensate.add(kDLabelContainer22, new KDLayout.Constraints(24, 163, 866, 38, 0));
        kDLabelContainer23.setBounds(new Rectangle(322, 25, 270, 19));
        kdpCompensate.add(kDLabelContainer23, new KDLayout.Constraints(322, 25, 270, 19, 0));
        kDLabelContainer24.setBounds(new Rectangle(322, 61, 270, 19));
        kdpCompensate.add(kDLabelContainer24, new KDLayout.Constraints(322, 61, 270, 19, 0));
        kDLabelContainer25.setBounds(new Rectangle(322, 94, 270, 19));
        kdpCompensate.add(kDLabelContainer25, new KDLayout.Constraints(322, 94, 270, 19, 0));
        kDLabelContainer26.setBounds(new Rectangle(622, 25, 270, 19));
        kdpCompensate.add(kDLabelContainer26, new KDLayout.Constraints(622, 25, 270, 19, 0));
        kDLabelContainer27.setBounds(new Rectangle(622, 61, 270, 19));
        kdpCompensate.add(kDLabelContainer27, new KDLayout.Constraints(622, 61, 270, 19, 0));
        kDLabelContainer28.setBounds(new Rectangle(622, 94, 270, 19));
        kdpCompensate.add(kDLabelContainer28, new KDLayout.Constraints(622, 94, 270, 19, 0));
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(txtCompensateNumber);
        //kDLabelContainer19
        kDLabelContainer19.setBoundEditor(txtScheme);
        //kDLabelContainer20
        kDLabelContainer20.setBoundEditor(txtReferAmount);
        //kDLabelContainer21
        kDLabelContainer21.setBoundEditor(txtLastAmount);
        //kDLabelContainer22
        kDLabelContainer22.setBoundEditor(txtDesc);
        //kDLabelContainer23
        kDLabelContainer23.setBoundEditor(pkCompensateDate);
        //kDLabelContainer24
        kDLabelContainer24.setBoundEditor(txtMainAmount);
        //kDLabelContainer25
        kDLabelContainer25.setBoundEditor(txtActAmount);
        //kDLabelContainer26
        kDLabelContainer26.setBoundEditor(prmtHandler);
        //kDLabelContainer27
        kDLabelContainer27.setBoundEditor(txtAttAmount);
        //kDLabelContainer28
        kDLabelContainer28.setBoundEditor(txtRate);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CompensateRoomSelectUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBaseInfo)ov;
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
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CompensateRoomSelectUI");
    }




}