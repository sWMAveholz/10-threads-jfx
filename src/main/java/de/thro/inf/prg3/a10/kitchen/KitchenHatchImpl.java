package de.thro.inf.prg3.a10.kitchen;

import de.thro.inf.prg3.a10.KitchenHatchConstants;
import de.thro.inf.prg3.a10.model.Dish;
import de.thro.inf.prg3.a10.model.Order;

import java.util.ArrayDeque;
import java.util.Deque;

public class KitchenHatchImpl implements KitchenHatch{

	private final int maxMeals;
	private Deque<Order> orders;
	private Deque<Dish> dishes;

	public KitchenHatchImpl(int maxMeals, Deque<Order> orders){

		this.maxMeals = maxMeals;
		this.orders = orders;
		this.dishes = new ArrayDeque<Dish>();
	}


	@Override
	public int getMaxDishes() {
		return this.maxMeals;
	}

	@Override
	public Order dequeueOrder(long timeout) {
		return orders.pollLast();
	}

	@Override
	public int getOrderCount() {
		return orders.size();
	}

	@Override
	public Dish dequeueDish(long timeout) {
		return dishes.pollLast();
	}

	@Override
	public void enqueueDish(Dish m) {
		dishes.addFirst(m);
	}

	@Override
	public int getDishesCount() {
		return dishes.size();
	}
}
