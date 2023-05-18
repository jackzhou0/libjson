package org.chinagcd.libjson.vo;

import lombok.Getter;

import java.util.function.Supplier;

public class Node {
    @Getter
    private Object data;
    @Getter
    private Relation relation;
    private Node next;

    public Node(Object data) {
        this.data = data;
    }

    public Node(Node data) {
        this.data = data;
    }

    public Node and(String val) {
        return addNode(val, Relation.AND);
    }

    public Node and(Supplier<Node> supplier) {
        return addNode(supplier.get(), Relation.AND);
    }

    public Node and(Node node) {
        return addNode(node, Relation.AND);
    }

    public Node or(String val) {
        return addNode(val, Relation.OR);
    }

    public Node or(Node node) {
        return addNode(node, Relation.OR);
    }

    public Node or(Supplier<Node> supplier) {
        return addNode(supplier.get(), Relation.OR);
    }

    private Node addNode(Object val, Relation relation) {
        Node node = new Node(val);
        this.relation = relation;
        this.next = node;
        return node;
    }

    public String sql() {
        StringBuffer sb = new StringBuffer();
        parse(sb, this);
        return sb.toString();
    }

    private void parse(StringBuffer sb, Node node) {
        if (node != null) {
            if (node.data instanceof Node) {
                // 追加上一个relation
                sb.append(" (");
                Node dataNode = (Node) node.data;
                parse(sb, dataNode);
                sb.append(") ");
            } else {
                sb.append(" " + node.data);
            }
            // 处理下一个node的时候需要带上and or 运算符
            if (node.next != null) {
                sb.append(" " + node.relation.getBooleanOperator());
                parse(sb, node.next);
            }
        }
    }
}
