import grammar.Grammar;
import parser.Parser;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.*;
public class Test {
    private Parser parser;
    private Grammar grammar;

    public void testAll() throws Exception {
        test1();
        test2();
    }

    public void test1() throws Exception {
        grammar = Grammar.fromFile("g1.txt");
        parser = new Parser(grammar);

        List<Set<String>> expectedResultFirst = List.of(Set.of("(","int"), Set.of("E","+"), Set.of("(", "int"), Set.of("E","*"));
        List<Set<String>> expectedResultFollow = List.of(Set.of("$","E",")","+"), Set.of("$", ")"), Set.of("$", ")"), Set.of("$","E",")","+"));


        Map<String, Set<String>> firstSet = parser.getFirstSet();
        Map<String, Set<String>> followSet = parser.getFollowSet();

        assert firstSet.size() == 4;
        assert followSet.size() == 4;

        for (Map.Entry<String, Set<String>> entry : firstSet.entrySet()) {
            assert grammar.isNonTerminal(entry.getKey());
            for (String value : entry.getValue()) {
                assert (grammar.isTerminal(value) || value.equals("E"));
            }
        }

        for (Map.Entry<String, Set<String>> entry : followSet.entrySet()) {
            assert grammar.isNonTerminal(entry.getKey());
            for (String value : entry.getValue()) {
                assert (grammar.isTerminal(value) || value.equals("E") || value.equals("$"));
            }
        }

        for (int i = 0; i < firstSet.size(); i++)
        {
            assert firstSet.containsValue(expectedResultFirst.get(i));
            assert followSet.containsValue(expectedResultFollow.get(i));
        }
    }

    public void test2() throws Exception {
        grammar = Grammar.fromFile("g2.txt");
        parser = new Parser(grammar);

        Map<String, Set<String>> firstSet = parser.getFirstSet();
        Map<String, Set<String>> followSet = parser.getFollowSet();

        assert firstSet.size() == 22;
        assert followSet.size() == 22;

        for (Map.Entry<String, Set<String>> entry : firstSet.entrySet()) {
            assert grammar.isNonTerminal(entry.getKey());
            for (String value : entry.getValue()) {
                assert (grammar.isTerminal(value) || value.equals("E"));
            }
        }
    }
}
