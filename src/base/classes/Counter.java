// ID 318646072
package base.classes;

/**
 * A Counter class.
 *
 * @author Asaf Mesilaty
 */
public class Counter {

    private int val;

    /**
     * Construct a Counter given a number.
     * @param number the value stored in the counter.
     * */
    public Counter(int number) {
        this.val = number;
    }

    /**
     * default constructor with default value 0.
     * */
    public Counter() {
        this(0);
    }

    /**
     * This method adds a number to current count.
     * @param number the added number.
     * */
    public void increase(int number) {
        if (number < 0) {
           System.out.println("In order to increase by a negative number - "
                    + "you should call decrease with the absolute value of the number");
            return;
        }
        this.val += number;
    }

    /**
     * This method adds 1 to current count.
     * */
    public void increase() {
        this.increase(1);
    }

    /**
     * This method subtract a number from current count.
     * @param number the added number.
     * */
    public void decrease(int number) {
        this.val -= number;
        if (this.val < 0) {
            System.out.println("You can't subtract more then you've got! Counter has been set to zero.");
            this.val = 0;
        }
    }

    /**
     * This method subtract 1 from current count.
     * */
    public void decrease() {
        this.decrease(1);
    }

    /**
     * @return current count.
     * */
    public int getValue() {
        return this.val;
    }

    @Override
    public String toString() {
        return (Integer.toString(this.val));
    }
}