package de.thro.inf.prg3.a10.model;

import de.thro.inf.prg3.a10.internals.displaying.ProgressReporter;
import de.thro.inf.prg3.a10.kitchen.KitchenHatch;

import java.util.Random;

public class Waiter implements Runnable {

	private String name;
	private ProgressReporter progressReporter;
	private KitchenHatch kitchenHatch;
	private Random rand = new Random();

	public Waiter(String name, KitchenHatch kitchenHatch, ProgressReporter progressReporter){
		this.name = name;
		this.kitchenHatch = kitchenHatch;
		this.progressReporter = progressReporter;
	}

	@Override
	public void run() {
		Dish dish = kitchenHatch.dequeueDish();
		while (dish != null || kitchenHatch.getOrderCount()>0){
			try {
				Thread.sleep(rand.nextInt(5000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			progressReporter.updateProgress();
			dish = kitchenHatch.dequeueDish();

		}
		progressReporter.notifyWaiterLeaving();

	}
}
