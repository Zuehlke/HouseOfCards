package com.zuehlke.houseofcards;

import java.util.Iterator;
import java.util.List;

public class RoundRobin<T> implements Iterable<T> {
    private final int startIndex;
    private List<T> coll;

    public RoundRobin(List<T> coll, int startIndex) {
        this.coll = coll;
        this.startIndex = startIndex;
    }

    public static int getNextStartPosition(List coll, int startIndex){
        return (startIndex + 1) % coll.size();
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = startIndex;
            private boolean first = true;

            @Override
            public boolean hasNext() {
                return first || index != startIndex;
            }

            @Override
            public T next() {
                if(!hasNext()){
                    throw new RuntimeException("No more elements in the one round robin itertor!");
                }
                T res = coll.get(index);
                first = false;
                index = (index + 1) % coll.size();
                return res;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }
}