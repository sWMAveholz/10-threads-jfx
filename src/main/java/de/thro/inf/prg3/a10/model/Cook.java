package de.thro.inf.prg3.a10.model;

import de.thro.inf.prg3.a10.internals.displaying.ProgressReporter;
import de.thro.inf.prg3.a10.kitchen.KitchenHatch;

public class Cook implements Runnable {

	private String name;
	private ProgressReporter progressReporter;
	private KitchenHatch kitchenHatch;

	public Cook(String name, KitchenHatch kitchenHatch, ProgressReporter progressReporter){
		this.name = name;
		this.kitchenHatch = kitchenHatch;
		this.progressReporter = progressReporter;
	}

	@Override
	public void run() {
		Order order = kitchenHatch.dequeueOrder();

		while(order != null){
			Dish dish = new Dish(order.getMealName());
			try {
				Thread.sleep(dish.getCookingTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			kitchenHatch.enqueueDish(dish);
			progressReporter.updateProgress();
			order = kitchenHatch.dequeueOrder();
		}
		progressReporter.notifyCookLeaving();



	}
}
