package inc.bizties.fifferz.data.provider.tables;

public abstract class BaseTable<T extends BaseContract> {

    private final T contract;

    public BaseTable(T contract) {
        this.contract = contract;
    }

    public T getContract() {
        return contract;
    }
}
