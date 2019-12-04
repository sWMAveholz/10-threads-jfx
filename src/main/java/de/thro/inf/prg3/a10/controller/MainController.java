package de.thro.inf.prg3.a10.controller;

import de.thro.inf.prg3.a10.KitchenHatchConstants;
import de.thro.inf.prg3.a10.internals.displaying.ProgressReporter;
import de.thro.inf.prg3.a10.kitchen.KitchenHatch;
import de.thro.inf.prg3.a10.kitchen.KitchenHatchImpl;
import de.thro.inf.prg3.a10.model.Cook;
import de.thro.inf.prg3.a10.model.Order;
import de.thro.inf.prg3.a10.model.Waiter;
import de.thro.inf.prg3.a10.util.NameGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.util.ArrayDeque;
import java.util.ResourceBundle;

import static de.thro.inf.prg3.a10.KitchenHatchConstants.*;

public class MainController implements Initializable {

	private final ProgressReporter progressReporter;
	private final KitchenHatch kitchenHatch;
	private final NameGenerator nameGenerator;

	@FXML
	private ProgressIndicator waitersBusyIndicator;

	@FXML
	private ProgressIndicator cooksBusyIndicator;

	@FXML
	private ProgressBar kitchenHatchProgress;

	@FXML
	private ProgressBar orderQueueProgress;

	public MainController() {
		nameGenerator = new NameGenerator();


		//TODO assign an instance of your implementation of the KitchenHatch interface
		ArrayDeque<Order> orders = new ArrayDeque<>();

		for (int i = 0; i< ORDER_COUNT; i++){
			orders.add(new Order(nameGenerator.generateName()));
		}

		this.kitchenHatch = new KitchenHatchImpl(KITCHEN_HATCH_SIZE,orders);
		//this.kitchenHatch = null;
		this.progressReporter = new ProgressReporter(kitchenHatch, COOKS_COUNT, WAITERS_COUNT, ORDER_COUNT, KITCHEN_HATCH_SIZE);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderQueueProgress.progressProperty().bindBidirectional(this.progressReporter.orderQueueProgressProperty());
		kitchenHatchProgress.progressProperty().bindBidirectional(this.progressReporter.kitchenHatchProgressProperty());
		waitersBusyIndicator.progressProperty().bindBidirectional(this.progressReporter.waitersBusyProperty());
		cooksBusyIndicator.progressProperty().bind(this.progressReporter.cooksBusyProperty());

		/* TODO create the cooks and waiters, pass the kitchen hatch and the reporter instance and start them */
		for (int i = 0; i<COOKS_COUNT; i++){
			new Thread(new Cook(nameGenerator.generateName(),this.kitchenHatch,this.progressReporter)).start();

		}

		for (int i = 0; i< WAITERS_COUNT; i++){
			new Thread(new Waiter(nameGenerator.generateName(),this.kitchenHatch,this.progressReporter)).start();
		}
	}
}
