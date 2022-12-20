package com.example.javafx;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


@Component
public class StageListener implements ApplicationListener<StageReadyEvent>{
	private static final Logger log= LoggerFactory.getLogger(StageListener.class);

	private final String appTitle;	
	private Resource fxml;
	private final ApplicationContext applicationContext;
	
	@Autowired
	public StageListener(
						@Value("${ui.title}") String appTitle,
						@Value("classpath:ui.fxml") Resource resource, 
						ApplicationContext ac) {
		super();
		this.appTitle = appTitle;
		this.fxml=resource;
		this.applicationContext=ac;
	}
	
	@Override
	public void onApplicationEvent(StageReadyEvent event) {
		
		try {
			Stage stage=event.getStage();
			URL url= this.fxml.getURL();
			FXMLLoader fxmlLoader= new FXMLLoader(url);
			fxmlLoader.setControllerFactory(applicationContext::getBean);
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root,600,600);
			stage.setScene(scene);
			stage.setTitle(this.appTitle);
			stage.show();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
	}

}
