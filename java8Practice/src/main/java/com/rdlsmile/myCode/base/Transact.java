package com.rdlsmile.myCode.base;

import java.util.Arrays;
import java.util.List;

public class Transact {
    private Currency currency;
    private double value;
    public enum Currency {
        EUR, USD, JPY, GBP, CHF
    }

    public Transact(Currency currency, double value) {
        this.currency = currency;
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transact{" +
                "currency=" + currency +
                ", value=" + value +
                '}';
    }


    public static List<Transact> transacts = Arrays.asList( new Transact(Currency.EUR, 1500.0),
            new Transact(Currency.USD, 2300.0),
            new Transact(Currency.GBP, 9900.0),
            new Transact(Currency.EUR, 1100.0),
            new Transact(Currency.JPY, 7800.0),
            new Transact(Currency.CHF, 6700.0),
            new Transact(Currency.EUR, 5600.0),
            new Transact(Currency.USD, 4500.0),
            new Transact(Currency.CHF, 3400.0),
            new Transact(Currency.GBP, 3200.0),
            new Transact(Currency.USD, 4600.0),
            new Transact(Currency.JPY, 5700.0),
            new Transact(Currency.EUR, 6800.0) );
}
