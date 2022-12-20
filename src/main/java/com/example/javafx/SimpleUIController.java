package com.example.javafx;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import javafx.application.HostServices;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBContextFactory;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import oecd.ties.fatca.v2.FATCAOECD;

@Controller
public class SimpleUIController {
	
	private static final Logger log= LoggerFactory.getLogger(SimpleUIController.class);
	
	private final HostServices hostServices;

	@FXML
	public Label label1;
	@FXML
	public Label label2;
	@FXML
	public Label label3;
	@FXML
	public Button button;
	
	SimpleUIController(HostServices hostServices){
		this.hostServices=hostServices;
	}
	
//	@FXML
//	public void initialize() {
//		this.button.setOnAction(actionEvent -> this.label1.setText(this.hostServices.getDocumentBase()));
//	}
	
	@FXML
    public void buttonClicked(Event e){
		JAXBContext jaxbContext = null;
		Unmarshaller jaxbUnmarshaller;
		try {
			jaxbContext = JAXBContext.newInstance(FATCAOECD.class);
			File file = new File( System.getProperty("user.dir") + File.separator + "data" + File.separator+"FATCADemo.xml");
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FATCAOECD o = (FATCAOECD) jaxbUnmarshaller.unmarshal(file);
			label1.setText("FATCA MessageSpec :: " + o.getMessageSpec().getMessageRefId());
			label2.setText("FATCA getReportingPeriod :: " + o.getMessageSpec().getReportingPeriod());
			label3.setText("FATCA size :: " + o.getFATCA().size());
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
       //label1.setText(System.getProperty("user.dir") + File.separator + "data" + File.separator+"FATCADemo.xml");
    }
}
