package eon.hg.fap.common.tools.tree;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据list转换为树结构
 * @param <N>
 * @author aeon
 */
public abstract class List2Tree<N> {

    /**
     * 获取主键ID
     * @param node
     * @return
     */
    protected abstract Object getId(N node);

    /**
     * 获取父亲ID
     * @param node
     * @return
     */
    protected abstract Object getPid(N node);

    /**
     * 设置树节点显示名称
     * @param node
     */
    protected abstract void setText(N node);

    /**
     * 获取子级对象
     * @param node
     * @return
     */
    protected abstract List<N> getChildren(N node);

    /**
     * 设置子级对象
     * @param nodes
     * @param node
     */
    protected abstract void setChildren(List<N> nodes, N node);

    protected N getTopParent(Map<Object,N> map, N node) {
        return node;
    }

    public List<N> buildTree(List<N> nodes) {
        return buildTree(nodes,"");
    }

    /**
     * 生成树
     * @param nodes
     * @return
     */
    public List<N> buildTree(List<N> nodes, String sortField) {
        Map<Object,N> map = new HashMap<>();
        for (N node : nodes) {
            map.put(getId(node),node);
            setText(node);
        }
        List<N> newList = new ArrayList<>();
        for (N node : nodes) {
            N parent = map.get(getPid(node));
            if (parent!=null) {
                if (getChildren(parent)==null) {
                    List<N> ch = new ArrayList<>();
                    ch.add(node);
                    setChildren(ch,parent);
                } else {
                    List<N> ch = getChildren(parent);
                    ch.add(node);
                }
            } else {
                newList.add(getTopParent(map,node));
            }
        }
        if (StrUtil.isNotBlank(sortField)) {
            newList = sortTree(newList, sortField);
        }
        return newList;
    }

    public List<N> sortTree(List<N> nodes, String sortField) {
        List<N> sortNodes = CollectionUtil.sortByProperty(nodes, sortField);
        for (N node : nodes) {
            if (getChildren(node)!=null) {
                sortTree(getChildren(node), sortField);
            }
        }
        return sortNodes;
    }
}
