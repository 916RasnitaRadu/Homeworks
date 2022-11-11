package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADTs.IDictionary;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class DeclarationStatement implements IStatement{
    private Type type;
    private String var_name;

    public DeclarationStatement(Type type, String var_name) {
        this.type = type;
        this.var_name = var_name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<String, Value> symTable = state.getSymbolTable();
        if (symTable.contains_key(var_name))
        {
            throw new InterpreterException("ERROR: The variable " + var_name + " was already declared.");
        }
        symTable.put(var_name,type.default_value());
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new DeclarationStatement(type.clone(), var_name);
    }

    @Override
    public String toString() {
        return String.format("DeclarationStatement{%s : %s}", var_name, type.toString());
    }

    public Type getType() {
        return type;
    }

    public String getVar_name() {
        return var_name;
    }
}
