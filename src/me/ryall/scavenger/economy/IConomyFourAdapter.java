package me.ryall.scavenger.economy;

// iConomy
import org.bukkit.plugin.Plugin;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.coelho.iConomy.system.Account;

public class IConomyFourAdapter extends EconomyInterface
{
    public IConomyFourAdapter(Plugin _plugin)
    {
    }

    public String getName()
    {
        return "iConomy 4";
    }

    public double getBalance(String _playerName)
    {
        Account acc = iConomy.getBank().getAccount(_playerName);

        return (acc == null) ? 0 : acc.getBalance();
    }

    public boolean canAfford(String _playerName, double _amount)
    {
        if (_amount == 0)
            return true;

        Account acc = iConomy.getBank().getAccount(_playerName);

        return (acc == null) ? false : acc.hasEnough(_amount);
    }

    public boolean add(String _playerName, double _amount)
    {
        Account acc = iConomy.getBank().getAccount(_playerName);

        if (acc != null)
        {
            acc.add(_amount);
            return true;
        }

        return false;
    }

    public boolean subtract(String _playerName, double _amount)
    {
        Account acc = iConomy.getBank().getAccount(_playerName);

        if (acc != null)
        {
            acc.subtract(_amount);
            return true;
        }

        return false;
    }

    public boolean transfer(String _playerFrom, String _playerTo, double _amount)
    {
        Account accFrom = iConomy.getBank().getAccount(_playerFrom);
        Account accTo = iConomy.getBank().getAccount(_playerTo);

        if (accFrom != null && accTo != null)
        {
            accFrom.subtract(_amount);
            accTo.add(_amount);

            return true;
        }

        return false;
    }

    public String formatCurrency(double _amount)
    {
        return getCurrencyString(_amount) + " " + iConomy.getBank().getCurrency();
    }
}
