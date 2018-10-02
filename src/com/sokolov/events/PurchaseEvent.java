package com.sokolov.events;

import java.util.Random;

/**
 * Created by admin on 26.09.2018.
 */
public class PurchaseEvent extends UserEvent {
    public int productId;
    public float price;

    public PurchaseEvent() {
        super();

        Random randomProductID = new Random();
        productId = randomProductID.nextInt(100000);
        price = randomProductID.nextInt(10000000) + randomProductID.nextFloat();
    }

}
