package br.com.finance.finance.components.factory;

import br.com.finance.finance.components.interfaces.TransactionComponent;
import br.com.finance.finance.domain.enums.EnumTransactionType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class TransactionComponentFactory {

    private Map<EnumTransactionType, TransactionComponent> strategies = new HashMap<>();

    public TransactionComponentFactory(Set<TransactionComponent> strategySet) {
        createStrategy(strategySet);
    }

    public TransactionComponent getStrategy(EnumTransactionType transactionType) {
        return strategies.get(transactionType);
    }

    private void createStrategy(Set<TransactionComponent> strategySet) {
        strategySet.forEach(strategy -> strategies.put(strategy.getStrategyName(), strategy));
    }

}
