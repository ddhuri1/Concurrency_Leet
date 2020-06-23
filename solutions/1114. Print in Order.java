class Foo 
{
    boolean hasFirst = false;
    boolean hasSecond = false;
    public Foo(){}

    public void first(Runnable printFirst) throws InterruptedException 
    {
        // printFirst.run() outputs "first". Do not change or remove this line.
       synchronized (printFirst)
       {
            printFirst.run();
            hasFirst = true;
       }
    }

    public void second(Runnable printSecond) throws InterruptedException 
    {
        // printSecond.run() outputs "second". Do not change or remove this line.
            while (!hasFirst) {}
            printSecond.run();
            hasSecond = true;
    }

    public void third(Runnable printThird) throws InterruptedException 
    {
        // printThird.run() outputs "third". Do not change or remove this line.
            while (!hasSecond) {}
            printThird.run();
    }
}