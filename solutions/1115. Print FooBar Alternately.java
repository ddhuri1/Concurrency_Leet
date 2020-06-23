class FooBar
{
    private int n;
    private final static ReentrantLock re = new ReentrantLock();
    private final static Condition cond= re.newCondition();
    private boolean foo;
    
    public FooBar(int n) 
    {
        this.n = n;
        this.foo = true;
    }

    // printFoo.run() outputs "foo". Do not change or remove this line.
    public void foo(Runnable printFoo) throws InterruptedException 
    {
        for (int i = 0; i < n; i++) 
        {
            re.lock();
            while (foo != true) 
                cond.await();
        	printFoo.run();
            foo = false;
            cond.signal();
            re.unlock();
        }
    }

    // printBar.run() outputs "bar". Do not change or remove this line.
    public void bar(Runnable printBar) throws InterruptedException 
    {
        
        for (int i = 0; i < n; i++) 
        {
            re.lock();
            while (foo == true) 
                cond.await();
        	printBar.run();
            foo = true;
            cond.signal();
            re.unlock();
        }
    }
}