package com.lovely3x.jsonparser.model;

/**
 * Created by lovely3x on 15-6-29.
 * 栈结构
 */
public class Stack<T> {

    /**
     * 默认的数组增加增量因子
     */
    public static final float DEFAULT_FACTOR = 0.7f;

    private static final String TAG = "Stack";

    /**
     * 增量因子
     */
    private float factor = DEFAULT_FACTOR;

    /**
     * 锁对象
     */
    private Object lock = new Object();

    /**
     * 栈结构
     */
    private Object[] stack = new Object[1];

    /**
     * 制定数组的初始容量
     *
     * @param capacity
     */
    public Stack(int capacity) {
        stack = new Object[capacity];
    }

    /**
     * 使用指定的增量因子
     *
     * @param factor
     */
    public Stack(float factor) {
        this.factor = factor;
    }

    /**
     * 使用指定的增量因子和默认容量创建栈
     *
     * @param factor
     * @param capacity
     */
    public Stack(float factor, int capacity) {
        this.factor = factor;
        stack = new Object[capacity];
        size = stack.length;
    }

    /**
     * 当前栈指向的下标
     */
    private int index;

    /**
     * 栈的带大小
     */
    private int size = stack.length;

    /**
     * 数组使用默认的的容量
     */
    public Stack() {

    }

    /**
     * 获取指定下标的元素
     *
     * @param index
     * @return
     */
    public T get(int index) {
        synchronized (lock) {
            if (index < 0 || index > this.index) return null; //out of bounds
            return (T) stack[index];
        }
    }

    public int size() {
        return index;
    }

    /**
     * 压栈
     *
     * @param t
     */
    public void push(T t) {
        synchronized (lock) {
            //如果容量不够
            if (index + 1 >= size) {
                addCapacity();
            }
            stack[index++] = t;
        }
    }

    /**
     * 弹栈
     *
     * @return
     */
    public T pop() {
        synchronized (lock) {
            return (T) stack[--index];
        }
    }

    /**
     * 增加容量
     */
    private void addCapacity() {
        int targetLength = (int) (size + (size * factor));
        if (targetLength <= stack.length) targetLength = stack.length + 1;
        Object[] destArray = new Object[targetLength];
        System.arraycopy(stack, 0, destArray, 0, index);
        this.stack = destArray;
        size = destArray.length;
    }

    public void clear() {
        stack = new Object[1];
        index = 0;
        size = stack.length;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append('[');
        final int count = index;
        for (int i = 0; i < count; i++) {
            sb.append(stack[i]);
            if (i + 1 != count) {
                sb.append(' ');
            }

        }
        sb.append(']');

        return "Stack ".concat(sb.toString());
    }
}
