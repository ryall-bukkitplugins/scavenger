package me.ryall.scavenger.economy;

// Java
import java.text.DecimalFormat;

public abstract class EconomyInterface
{
    public abstract String getName();

    public abstract double getBalance(String _playerName);

    public abstract boolean canAfford(String _playerName, double _amount);

    public abstract boolean add(String _playerName, double _amount);

    public abstract boolean subtract(String _playerName, double _amount);

    public abstract boolean transfer(String _playerFrom, String _playerTo, double _amount);

    public abstract String formatCurrency(double _amount);

    protected String getCurrencyString(double _amount)
    {
        if ((double) ((int)_amount) != _amount)
        {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(_amount);
        }

        DecimalFormat df = new DecimalFormat("#");
        return df.format(_amount);
    }
}