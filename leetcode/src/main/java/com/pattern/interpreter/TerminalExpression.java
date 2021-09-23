package com.pattern.interpreter;

public class TerminalExpression implements Expression {
    private String data;

    public TerminalExpression(String data) {
        this.data = data;
    }

    @Override
    public boolean interpret(String contxt) {
        if (contxt.contains(data)) {
            return true;
        }
        return false;
    }
}
