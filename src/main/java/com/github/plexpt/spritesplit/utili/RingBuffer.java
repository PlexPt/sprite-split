package com.github.plexpt.spritesplit.utili;

/**
 * @author pengtao
 * @email plexpt@gmail.com
 * @date 2022-03-16 14:59
 */
public class RingBuffer<T> {
    private  Object[] elements;
    private int first;
    private int last;
    private int numElems;
    private int maxSize;

    private static final int DEFAULT_SIZE = 1024;

    public RingBuffer() {
        this(DEFAULT_SIZE);
    }

    public RingBuffer(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException("The maxSize argument ("+maxSize+
                    ") is not a positive integer.");
        }

        this.maxSize = maxSize;
        elements = new Object[maxSize];
        this.first = 0;
        this.last = 0;
        numElems = 0;
    }

    /**
     * 添加元素
     * @param element
     */
    public void add(T element) {
        elements[last] = element;
        if (++last == maxSize) {
            last = 0;
        }
        if (numElems < maxSize) {
            numElems++;
        }
        else if (++first == maxSize) {
            first = 0;
        }
    }

    /**
     * 获取元素
     * @param i
     * @return
     */
    public T get(int i) {
        if (i < 0 || i >= numElems) {
            return null;
        }
        return (T)elements[(first + i) % maxSize];
    }

    /**
     * 获取最大的长度
     * @return
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * 获取当前元素
     * @return
     */
    public T get() {
        Object ele = null;
        if (numElems > 0) {
            numElems--;
            ele = elements[first];
            if (++first == maxSize) {
                first = 0;
            }
        }
        return (T)ele;
    }

    /**
     * 长度
     * @return
     */
    public int length() {
        return numElems;
    }

    /**
     * 扩容
     * @param newSize
     */
    public void resize(int newSize) {
        if (newSize < 0) {
            throw new IllegalArgumentException("Negative array size ["+newSize+
                    "] not allowed.");
        }

        if (newSize == numElems) {
            return;
        }

        Object[] temp = new Object[newSize];
        int loopLen = newSize < numElems ? newSize : numElems;
        for (int i = 0; i < loopLen; i++) {
            temp[i] = elements[first];
            elements[first] = null;
            if (++first == numElems) {
                first = 0;
            }
        }
        elements = temp;
        first = 0;
        numElems = loopLen;
        maxSize = newSize;
        if (loopLen == newSize) {
            last = 0;
        } else {
            last = loopLen;
        }
    }

}
