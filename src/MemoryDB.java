import java.util.HashMap;

public class MemoryDB implements InMemoryDB{
    private HashMap<String, Integer> database;
    private HashMap<String, Integer> changes;
    private boolean inTransaction;

    public MemoryDB(){
        database = new HashMap<>();
        changes = new HashMap<>();
    }

    @Override
    public int get(String key) {
        if(database.containsKey(key)) {
            return database.get(key);
        }else{
            return database.getOrDefault(key,0);
        }
    }

    @Override
    public void put(String key, int val) {
        if(!inTransaction){
            throw new IllegalStateException("Transaction Not in Progress");
        }else{
            changes.put(key,val);
        }
    }

    @Override
    public void begin_transaction() {
        if(inTransaction){
            throw new IllegalStateException("Transaction already in Progress");
        }
        inTransaction = true;
        changes.clear();
        changes.putAll(database);
    }

    @Override
    public void commit() {
        if(!inTransaction){
            throw new IllegalStateException("Transaction not in Progress");
        }
        inTransaction = false;
        database.putAll(changes);
    }

    @Override
    public void rollback() {
        if (!inTransaction) {
            throw new IllegalStateException("No ongoing transaction");
        }
        inTransaction = false;
    }
}
