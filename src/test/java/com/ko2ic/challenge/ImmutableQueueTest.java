package com.ko2ic.challenge;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ImmutableQueueTest {
    @Test
    public void shouldBeImmutable_evenAfterCall_enQueue_and_deQueue() {

        Queue<MutableItem> emptyQueue = new ImmutableQueue<>();

        assertTrue(emptyQueue.isEmpty());

        MutableItem mutableItem = new MutableItem(0);
        Queue<MutableItem> afterEnQueue = emptyQueue.enQueue(mutableItem);

        assertThat("emptyQueue should not be changed even after enQueue().", emptyQueue.head(), is(nullValue()));
        assertThat(afterEnQueue.head().key, is(mutableItem.key));

        Queue afterDeQueue = afterEnQueue.deQueue();

        assertTrue(afterDeQueue.isEmpty());
        assertThat("afterEnQueue should not be changed even after deQueue().", afterEnQueue.head().key, is(mutableItem.key));
    }

    @Test
    public void shouldBeImmutable_evenAfterChangingItem() {

        Queue<MutableItem> emptyQueue = new ImmutableQueue<>();

        MutableItem mutableItem1 = new MutableItem(1);
        MutableItem mutableItem2 = new MutableItem(2);
        Queue<MutableItem> afterEnQueue = emptyQueue.enQueue(mutableItem1).enQueue(mutableItem2);

        assertThat(afterEnQueue.head().key, is(mutableItem1.key));

        mutableItem1.setKey(10000);

        assertThat("afterEnQueue should not be changed even after changing the item.", afterEnQueue.head().key, is(1));

        Queue<MutableItem> afterdeQueue = afterEnQueue.deQueue();

        mutableItem2.setKey(10000);

        assertThat("afterdeQueue should not be changed even after changing the item.", afterdeQueue.head().key, is(2));
    }

    @Test
    public void shouldBeFIFO() {

        MutableItem item1 = new MutableItem(1);
        MutableItem item2 = new MutableItem(2);
        MutableItem item3 = new MutableItem(3);
        MutableItem item4 = new MutableItem(4);

        Queue<MutableItem> queue = new ImmutableQueue<>(item1).enQueue(item2).enQueue(item3).enQueue(item4);

        MutableItem actual1 = queue.head();

        assertThat("should be able to get the item you put in firstly", actual1, is(item1));

        Queue<MutableItem> afterDeQueue = queue.deQueue();

        assertThat("should be the first item is deleted and be able to get the item you put in secondary because of after calling deQueue()", afterDeQueue.head().key, is(item2.key));

        MutableItem item5 = new MutableItem(5);

        Queue<MutableItem> queue5 = afterDeQueue.enQueue(item5);
        assertThat("should be able to get the same head by adding new items", queue5.head().key, is(item2.key));


    }

    @Test
    public void shouldNotCrashEvenIfHandleEmptyQueue() {
        Queue<MutableItem> emptyQueue = new ImmutableQueue<>();

        Queue<MutableItem> lastQueue = emptyQueue.deQueue().deQueue().deQueue().deQueue();

        assertThat(lastQueue.head(), is(nullValue()));

        assertThat(lastQueue.deQueue().head(), is(nullValue()));

        assertTrue(lastQueue.isEmpty());
    }

    static class MutableItem implements MyCloneable<MutableItem> {
        private int key = 0;

        MutableItem(int key) {
            this.key = key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        @Override
        public MutableItem clone() {
            MutableItem mutableItem;
            try {
                mutableItem = (MutableItem) super.clone();
            } catch (CloneNotSupportedException e) {
                // TODO
                throw new RuntimeException(e);
            }
            mutableItem.key = this.key;
            return mutableItem;
        }


        @Override
        public String toString() {
            return String.format("%s, key=%s", super.toString(), this.key);
        }
    }


}
