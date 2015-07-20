import greenfoot.*;  // (getWorld(), Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Calendar;
import java.awt.Color;
import java.lang.String;
import java.lang.reflect.*;
import javax.swing.JOptionPane;

/**
 * Write a description of class Scratch here.
 * 
 * @author Victor Norman 
 * @version 0.1
 */
public class Scratch extends Actor
{
    // These are the 200 numbered colors used in Scratch.
    private static String numberedColors[] = {
            "#FF0000", "#FF0700", "#FF0F00", "#FF1600", "#FF1E00",        // indices 0 - 4
            "#FF2600", "#FF2D00", "#FF3500", "#FF3D00", "#FF4400",        // indices 5 - 9
            "#FF4C00", "#FF5400", "#FF5B00", "#FF6300", "#FF6B00",        // indices 10 - 14   
            "#FF7200", "#FF7A00", "#FF8200", "#FF8900", "#FF9100",        // indices 15 - 19
            "#FF9900", "#FFA000", "#FFA800", "#FFAF00", "#FFB700",        // indices 20 - 24
            "#FFBF00", "#FFC600", "#FFCE00", "#FFD600", "#FFDD00",        // indices 25 - 29
            "#FFE500", "#FFED00", "#FFF400", "#FFFC00", "#F9FF00",        // indices 30 - 34
            "#F2FF00", "#EAFF00", "#E2FF00", "#DBFF00", "#D3FF00",        // indices 35 - 39
            "#CCFF00", "#C4FF00", "#BCFF00", "#B5FF00", "#ADFF00",        // indices 40 - 44
            "#A5FF00", "#9EFF00", "#96FF00", "#8EFF00", "#87FF00",        // indices 45 - 49
            "#7FFF00", "#77FF00", "#70FF00", "#68FF00", "#60FF00",        // indices 50 - 54
            "#59FF00", "#51FF00", "#49FF00", "#42FF00", "#3AFF00",        // indices 55 - 59
            "#32FF00", "#2BFF00", "#23FF00", "#1CFF00", "#14FF00",        // indices 60 - 64
            "#0CFF00", "#05FF00", "#00FF02", "#00FF0A", "#00FF11",        // indices 65 - 69
            "#00FF19", "#00FF21", "#00FF28", "#00FF30", "#00FF38",        // indices 70 - 74
            "#00FF3F", "#00FF47", "#00FF4F", "#00FF56", "#00FF5E",        // indices 75 - 79
            "#00FF65", "#00FF6D", "#00FF75", "#00FF7C", "#00FF84",        // indices 80 - 84
            "#00FF8C", "#00FF93", "#00FF9B", "#00FFA3", "#00FFAA",        // indices 15 - 19
            "#00FFB2", "#00FFBA", "#00FFC1", "#00FFC9", "#00FFD1",        // indices 90 - 94
            "#00FFD8", "#00FFE0", "#00FFE8", "#00FFEF", "#00FFF7",        // indices 95 - 99
            "#00FFFF", "#00F7FF", "#00EFFF", "#00E8FF", "#00E0FF",        // indices 100 - 104
            "#00D8FF", "#00D1FF", "#00C9FF", "#00C1FF", "#00BAFF",        // indices 15 - 19
            "#00B2FF", "#00AAFF", "#00A3FF", "#0098FF", "#0093FF",        // indices 110 - 114
            "#008CFF", "#0084FF", "#007CFF", "#0075FF", "#006DFF",        // indices 115 - 119
            "#0065FF", "#005EFF", "#0056FF", "#004FFF", "#0047FF",        // indices 120 - 124
            "#003FFF", "#0038FF", "#0030FF", "#0028FF", "#0021FF",        // indices 125 - 129
            "#0019FF", "#0011FF", "#000AFF", "#0002FF", "#0500FF",        // indices 130 - 134
            "#0C00FF", "#1400FF", "#1C00FF", "#2300FF", "#2B00FF",        // indices 135 - 139
            "#3300FF", "#3A00FF", "#4200FF", "#4900FF", "#5100FF",        // indices 140 - 144
            "#5900FF", "#6000FF", "#6800FF", "#7000FF", "#7700FF",        // indices 145 - 149
            "#7F00FF", "#8700FF", "#8E00FF", "#9600FF", "#9E00FF",        // indices 150 - 154
            "#A500FF", "#AD00FF", "#B500FF", "#BC00FF", "#C400FF",        // indices 155 - 159
            "#CB00FF", "#D300FF", "#D800FF", "#E200FF", "#EA00FF",        // indices 160 - 164
            "#F200FF", "#F900FF", "#FF00FC", "#FF00F4", "#FF00ED",        // indices 165 - 169
            "#FF00E5", "#FF00DD", "#FF00D6", "#FF00CE", "#FF00C6",        // indices 170 - 174
            "#FF00BF", "#FF00B7", "#FF00AF", "#FF00A8", "#FF00A0",        // indices 175 - 179
            "#FF0098", "#FF0091", "#FF0089", "#FF0082", "#FF007A",        // indices 180 - 184
            "#FF0072", "#FF006B", "#FF0063", "#FF005B", "#FF0054",        // indices 185 - 189
            "#FF004C", "#FF0044", "#FF003D", "#FF0035", "#FF002D",        // indices 190 - 194
            "#FF0026", "#FF001E", "#FF0016", "#FF000F", "#FF0007",        // indices 195 - 199
        };

    private boolean isPenDown = false;
    private Color penColor = Color.RED;
    private int penColorNumber = 0;         // an integer that is mod 200 -- 0 to 199.
    private int penSize = 1;
    private int currCostume = 0;
    private ArrayList<GreenfootImage> costumes = new ArrayList<GreenfootImage>();
    // costumesCopy holds the original unaltered costume images.  So, if
    // the code alters the costume by, e.g., scaling it, the copy stays
    // unchanged.
    private ArrayList<GreenfootImage> costumesCopy = new ArrayList<GreenfootImage>();   
    private boolean isShowing = true;  // do we show the image or not?
    private int ghostEffect;           // image transparency.

    // The layer this object (actually all objects of this class) is painted in.
    // Layer 0 is on top.  ScratchWorld object keeps a list of the overall paint
    // order.
    private int currentLayer;

    // Note that the scale of a sprite in Scratch is a property of the sprite,
    // not a property of the image.  In Greenfoot it is just a property of
    // the image, so if you scale one image and then change to another image
    // the scaling is not applied.  So, here we have to store the current
    // scaling factor to be applied to all costumes/images.
    private int costumeSize = 100;   // percentage of original size

    // The direction the sprite is set to move in.  Due to the 
    // rotationStyle that is set, the image may not be pointing in that
    // direction.  This value is in Scratch orientation (which is GF 
    // orientation + 90).
    private int currDirection = 90; 

    private int lastMouseX;
    private int lastMouseY;

    // Remember if this object is a clone or not.  It is not, by default.
    private boolean isClone = false;

    // Note if the code being run is from an actCb (i.e., simulating the forever
    // loop of Scratch).  This needs to be noted so that if stopThisScript() is 
    // called it can check if the code is in the foreverloop.
    private boolean inActCb = false;

    // Actor that is showing what is being said OR thought by this sprite.
    SayActor sayActor = null;

    /*
     *  Turn the sprite to face the direction depending on the rotation style:
     *   o if ALL_AROUND: rotate to face the direction.
     *   o if LEFT_RIGHT: face left if direction is -1 to -179 or 181 - 359
     *   o if DONT_ROTATE: don't change the sprite.
     */
    public enum RotationStyle {
        LEFT_RIGHT, ALL_AROUND, DONT_ROTATE
    }
    private RotationStyle rotationStyle = RotationStyle.ALL_AROUND;

    private class StopScriptException extends RuntimeException {
        public StopScriptException() {
            super();
        }
    }

    private class KeypressCb {
        public String key;
        public Object obj;
        public String method;

        public KeypressCb(String key, Object obj, String method)
        {
            this.key = key;
            this.obj = obj;
            this.method = method;
        }

        public void invoke() 
        {
            try {
                Method m = obj.getClass().getMethod(method);
                m.invoke(obj);
            } catch (Exception e) {
                System.err.println("Scratch.act: exception when invoking keypress callback method '" + 
                    method + "' for key '" + key + "': " + e);
                e.printStackTrace(System.err);
            }
        }
    }
    private ArrayList<KeypressCb> keyCbs = new ArrayList<KeypressCb>();

    private class ActCb {
        public Object obj;
        public String method;
        // active records if the registered callback should continue to be run in the future.
        // It is set to false when stopThisScript() or stopOtherScriptsForSprite() has been called.
        public boolean active;
        // isRunning records if this actCb is being run now.  It is used only in stopOtherScriptsForSprite.
        // This is similar to the variable above called inActCb, which is set to true if any actCb is
        // being run at the time.
        public boolean isRunning;

        public ActCb(Object obj, String method)
        {
            this.obj = obj;
            this.method = method;
            this.active = true;
        }

        public void invoke() 
        {
            if (! active) {
                return;
            }
            try {
                Method m = obj.getClass().getMethod(method);
                // System.out.println("ActCb.invoke(): setting inActCb to true");
                inActCb = true;
                isRunning = true;
                m.invoke(obj);
            } catch (InvocationTargetException i) {
                if (i.getCause() instanceof StopScriptException) {
                    // System.out.println("ActCb.invoke: got StopScriptException: making script inactive");
                    active = false;
                } else {
                    // We had a problem with invoke(), but it wasn't the StopScript exception, so
                    // just print out the info.
                    i.printStackTrace();
                }
            } catch (Exception e) {
                System.err.println("Scratch.act: exception when invoking callback method '" + 
                    method + "': " + e);
            }
            // System.out.println("ActCb.invoke(): setting inActCb to false");
            isRunning = false;
            inActCb = false;
        }
    }
    private ArrayList<ActCb> actCbs = new ArrayList<ActCb>();
    private ArrayList<ActCb> actorClickedCbs = new ArrayList<ActCb>();

    private class CloneStartCb {
        public String className;
        public String method;

        // Store the method to call, and also the class in which the method exists.
        // When we invoke this (here in Scratch), we don't know the actual subclass, so
        // we have to reference it by the stored name.
        public CloneStartCb(String className, String method) 
        {
            this.className = className;
            this.method = method;
        }
        // obj is the (new) clone object to call the method on.
        public void invoke(Object obj)
        {
            try {
                Class clazz = Class.forName(className);
                Method m = clazz.getMethod(method);
                m.invoke(obj);
            } catch (Exception e) {
                System.err.println("Scratch.cloneCb: exception when invoking callback method '" + 
                    method + "': " + e);
                e.printStackTrace();
            }
        }
    }
    private ArrayList<CloneStartCb> cloneStartCbs = new ArrayList<CloneStartCb>();

    private class MessageCb {
        public String mesg;
        public Object obj;
        public String method;

        public MessageCb(String mesg, Object obj, String method)
        {
            this.mesg = mesg;
            this.obj = obj;
            this.method = method;
        }

        public void invoke()
        {
            try {
                Method m = obj.getClass().getMethod(method);
                m.invoke(obj);
            } catch (Exception e) {
                System.err.println("Scratch.act: exception when invoking broadcast callback method '" + 
                    method + "' for message '" + mesg + "': " + e);
            }
        }
    }
    private LinkedList<MessageCb> mesgCbs = new LinkedList<MessageCb>();

    /*
     * A Sequence is an executable thread of code that will be repeated run -- i.e., code in a Scratch
     * forever loop.  It can be paused via a wait() call, like in Scratch, etc.
     */
    public class Sequence extends Thread
    {
        private Object sequenceLock;
        private boolean doneSequence;
        private boolean terminated;
        private Object objToCall;
        private String methodToCall;

        /**
         * Constructor for objects of class Sequence
         */
        public Sequence(Object obj, String method)
        {
            this.sequenceLock = this;
            doneSequence = true;
            this.objToCall = obj;
            this.methodToCall = method;
            // System.out.println("Sequence ctor: obj " + obj + " method " + method);
        }

        public String toString() {
            return "Sequence: obj " + objToCall + " method " + methodToCall + " doneSeq " + doneSequence;
        }

        public void run()
        {
            try {
                synchronized (sequenceLock) {
                    while (true) {
                        while (doneSequence) {
                            // System.out.println(methodToCall + ": run(): Calling seqLock.wait");
                            sequenceLock.wait();
                            // System.out.println(methodToCall + ": run(): done with seqLock.wait");
                        }

                        java.lang.reflect.Method m = objToCall.getClass().getMethod(methodToCall, 
                                this.getClass());
                        // System.out.println(methodToCall + ": run(): invoking callback");
                        m.invoke(objToCall, this);
                        // System.out.println(methodToCall + ": run(): done invoking callback");
                        waitForNextSequence();
                    }
                }
            }
            catch (InterruptedException ie) {}
            catch (Throwable t) {
                t.printStackTrace();
            }
            // System.out.println(methodToCall + ": run(): done");

            terminated = true;
            doneSequence = true;
        }

        /**
         * Call this to relinquish control and wait for the next sequence.
         */
        public void waitForNextSequence() throws InterruptedException
        {
            doneSequence = true;
            sequenceLock.notify();

            while (doneSequence) {
                // System.out.println(methodToCall + ": waitForNextSequence(): calling seqLock.wait()");
                sequenceLock.wait();
                // System.out.println(methodToCall + ": waitForNextSequence(): done with seqLock.wait()");
            }
        }

        /**
         * The controller calls this when a sequence should be executed. The sequence thread
         * will be notified, and allowed to run until it relinquishes control, at which point
         * this method will return.
         */
        public void performSequence()
        {
            try {
                synchronized (sequenceLock) {
                    if (terminated) {
                        return;
                    }

                    doneSequence = false;
                    // System.out.println(methodToCall + ": perfSeq: calling notify()");
                    sequenceLock.notify();  // Thread now continues

                    while (! doneSequence) {
                        // System.out.println(methodToCall + ": perfSeq: calling wait()");
                        sequenceLock.wait(); // Wait for thread to notify us it's done
                        // System.out.println(methodToCall + ": perfSeq: done with wait()");
                    }
                }
            }
            catch (InterruptedException ie) {
            }
            // System.out.println(methodToCall + ": perfSeq: done");
        }

    }

    private ArrayList<Sequence> sequences = new ArrayList<Sequence>();
    /**
     * Add a sequence object to the list of sequences.
     */
    public void addSequence(Object obj, String method)
    {
        Sequence s = new Sequence(obj, method);
        sequences.add(s);
        s.start();
    }

    /*
     * Start of code!
     */

    public Scratch()
    {
        super();

        // put the first costume in our array of costumes.
        costumes.add(getImage());

        // System.out.println("item from getImage is " + System.identityHashCode(getImage()));
        // System.out.println("item in costumes array is " + System.identityHashCode(costumes.get(0)));

        // make sure the copy is a new copy of the image.
        costumesCopy.add(new GreenfootImage(getImage()));  
        // System.out.println("item in costumesCopy array is " + System.identityHashCode(costumesCopy.get(0)));
        // System.out.println("Scratch(): constructor finished for object " + System.identityHashCode(this));
    }

    /**
     * Copy constructor used only for cloning.
     * This is automatically called when a clone is created.
     */
    public Scratch(Scratch other, int x, int y)
    {
        // copy fields from other to this.
        isPenDown = other.isPenDown;
        penColor = other.penColor;
        penColorNumber = other.penColorNumber;
        penSize = other.penSize;
        currCostume = other.currCostume;
        costumes = new ArrayList<GreenfootImage>(other.costumes);     
        costumesCopy = new ArrayList<GreenfootImage>(other.costumesCopy);
        isShowing = other.isShowing;
        ghostEffect = other.ghostEffect;
        currentLayer = other.currentLayer;
        costumeSize = other.costumeSize;
        currDirection = other.currDirection;
        lastMouseX = other.lastMouseX;
        lastMouseY = other.lastMouseY;

        rotationStyle = other.rotationStyle;

        keyCbs = new ArrayList<KeypressCb>(other.keyCbs);
        actCbs = new ArrayList<ActCb>(other.actCbs);
        actorClickedCbs = new ArrayList<ActCb>(other.actorClickedCbs);
        cloneStartCbs = new ArrayList<CloneStartCb>(other.cloneStartCbs);
        mesgCbs = new LinkedList<MessageCb>(other.mesgCbs);

        // Initialize everything for this new Actor in Greenfoot.
        super.setLocation(x, y);
        setSizeTo(costumeSize);
        pointInDirection(currDirection);

        // NOTE: we are assuming that when this copy constructor is called, it is to make a clone.
        isClone = true;
        // Record that this new clone is not operating inside a forever loop at this time.
        inActCb = false;
        // a cloned Scratch actor does not say or think anything even if its clonee was saying something.
        sayActor = null;
        System.out.println("Scratch: copy constructor finished for object " + System.identityHashCode(this));
    }

    // This method is called by Greenfoot each time an actor is added to the world.
    // In this method, we register this actor's Class in the world, so that paint order
    // can be manipulated.
    // Any subclass of Scratch Actor has to implement addedToWorld() and call this method 
    // if the program needs to manipulate paint order.
    public void addedToWorld(World w)
    {
        ((ScratchWorld) w).addToPaintOrder(this.getClass());
    }

    /**
     * act - first look for keypresses and call any registered methods on them.  Then, call each 
     * method registered as an 'act' callback -- i.e., forever loop methods.
     */
    public final void act()
    {
        // Call all the methods registered to get notified when a key has been pressed.
        for (KeypressCb keyCb : keyCbs) {
            if (Greenfoot.isKeyDown(keyCb.key)) {
                keyCb.invoke();
            }
        }

        // Call all the methods registered to get notified when the sprite is clicked.
        for (ActCb aCb : actorClickedCbs) {
            if (Greenfoot.mouseClicked(this)) {
                aCb.invoke();
            }
        }

        // Call the registered methods to get notified when a message has been broadcast.
        ScratchWorld sw = (ScratchWorld) getWorld();
        for (MessageCb mCb : mesgCbs) {
            if (sw.bcastPending(mCb.mesg)) {
                mCb.invoke();
            }
        }

        // Call all the registered act()-like methods.
        for (Sequence seq : sequences) {
            seq.performSequence();
        }

        if (sayActor != null) {
            sayActorUpdateLocation();
        }
    }

    /**
     * register a method to be called each time a key press is noticed.
     * Note that Greenfoot runs very quickly so a key press is often noticed multiple 
     * times in a row.
     */
    public void registerKeyPressMethod(String keyName, String methodName)
    {
        KeypressCb kcb = new KeypressCb(keyName, this, methodName);
        keyCbs.add(kcb);
    }

    /**
     * register a method to be called in a Scratch forever loop.
     */
    public void registerForeverLoopMethod(String methodName)
    {
        addSequence(this, methodName);
    }

    public void registerSpriteClickedMethod(String methodName)
    {
        // use ActCb as it holds the same info we need for actor clicked callbacks.
        ActCb acb = new ActCb(this, methodName);
        actorClickedCbs.add(acb);
    }

    public void registerRecvMessageMethod(String messageName, String methodName)
    {
        MessageCb mcb = new MessageCb(messageName, this, methodName);
        mesgCbs.add(mcb);
    }

    public void broadcast(String message)
    {
        ((ScratchWorld) getWorld()).registerBcast(message);
    }

    /*
     * ---------------------------------------------------------------------
     * Control commands (most are not necessary to implement in Greenfoot)
     * ---------------------------------------------------------------------
     */

    /**
     * Create a new clone of this Sprite.  If this sprite has registered a 
     * startAsClone callback, then that method will be called.  If not, the 
     * new clone is positioned on the screen at the same location as the original.
     * TODO: if the original is hidden, make the clone hidden as well.
     */
    public void createCloneOfMyself()
    {
        // Create a new Object, which is a subclass of Scratch (the same class as "this").
        Object clone = callConstructor();

        // System.out.println("createCloneOfMyself: called copy constructor to get object of type " + 
        //    clone.getClass().getName() + ". Now, calling addObject()");
        ((ScratchWorld) getWorld()).addObject((Scratch)clone, super.getX(), super.getY());

        // If there are methods registered to be called when a clone is created,
        // call the methods now.   TODO: should this be done in the next frame?
        // But, have the new object run the callbacks.
        ((Scratch)clone).runCloneCallbacks();

        // System.out.println("Clone added");
    }

    /**
     * createCloneOf: create a clone of the given Scratch actor.
     */
    public void createCloneOf(Scratch actor)
    {
        // Create a new Object, which is a subclass of Scratch (the same class as "this").
        Object clone = callConstructor(actor);

        // System.out.println("createCloneOfMyself: called copy constructor to get object of type " + 
        //    clone.getClass().getName() + ". Now, calling addObject()");
        ((ScratchWorld) getWorld()).addObject((Scratch)clone, translateToGreenfootX(actor.getX()), 
            translateToGreenfootY(actor.getY()));

        // NOTE: Scratch does NOT run the "when added as clone" block when a clone of another
        // Sprite is created, so we won't either.

        System.out.println("Clone added");        
    }

    // Run registered "WhenIStartAsAClone" callbacks.
    private void runCloneCallbacks()
    {
        for (CloneStartCb cb : cloneStartCbs) {
            cb.invoke(this);
        }
    }

    private Object callConstructor(Scratch obj)
    {
        try {
            Constructor ctor = obj.getClass().getDeclaredConstructor(obj.getClass(), int.class, int.class);

            ctor.setAccessible(true);
            return ctor.newInstance(obj, super.getX(), super.getY());
        } catch (InstantiationException x) {
            x.printStackTrace();
        } catch (InvocationTargetException x) {
            x.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        } catch (java.lang.NoSuchMethodException x) {
            x.printStackTrace();
        }
        return null;
    }

    private Object callConstructor()
    {
        return callConstructor(this);
    }

    /**
     * register a method to be called when a new clone starts up.  The method
     * has no parameters.
     */
    public void registerWhenStartAsCloneMethod(String methodName)
    {
        // Save the method name, and also the name of the class of the new clone after it
        // is created.
        CloneStartCb cb = new CloneStartCb(getClass().getName(), methodName);
        cloneStartCbs.add(cb);
    }

    /**
     * remove this clone from the world.
     */
    public void deleteThisClone()
    {
        if (isClone) {        
            getWorld().removeObject(this);
        }
    }

    /**
     * Stop all scripts. In Scratch, event blocks like "when <keypress> pressed" still work 
     * even after all scripts have been stopped.  So, what it really means is "stop all forever loops 
     * for all objects."  However, I am going to implement this by stopping Greenfoot.
     */
    public void stopAll()
    {
        Greenfoot.stop();
    }

    /**
     * If this function is called from within a foreverLoop, unregister it (so that this isn't called again).
     * 
     */
    public void stopThisScript() throws StopScriptException
    {
        if (! inActCb) {
            // System.out.println("stopThisScript: returning because not in ActCb.");
            return;
        }
        // System.out.println("stopThisScript: throwing StopScriptException");
        throw new StopScriptException();
    }

    /**
     * Stop all other foreverLoop methods from running anymore.  If this is called from NOT in a foreverLoop,
     * then stop all foreverLoops.
     */
    public void stopOtherScriptsInSprite()
    {

        if (! inActCb) {
            // Just make all foreverLoop methods inactive.
            for (ActCb actCb : actCbs) {
                actCb.active = false;
            }
        } else {
            for (ActCb actCb : actCbs) {
                if (! actCb.isRunning) {
                    actCb.active = false;
                }
            }
        }
    }

    /*
     * ---------------------------------------------------------------------
     * Pen commands.
     * ---------------------------------------------------------------------
     */
    /**
     * put the pen down so that a line is drawn for all subsequent movements 
     * of the actor.
     */
    public void penDown() 
    {
        isPenDown = true;
    }

    /**
     * put the pen up so that nothing is drawn for all subsequent movements 
     * of the actor.
     */
    public void penUp()
    {
        isPenDown = false;
    }

    /**
     * set the color to be drawn if the pen is down.
     */
    public void setPenColor(Color c)
    {
        penColor = c;
    }

    /**
     * set the color to a color number, between 0 and 200.
     */
    public void setPenColor(int c)
    {
        // the colors are numbered between 0 and 199, and then wraparound: 200 is 0, etc.
        penColorNumber = c % 200;
        penColor = Color.decode(numberedColors[penColorNumber]);
    }

    /**
     * change the pen color number by the given amount.
     */
    public void changePenColorBy(int n)
    {
        penColorNumber = (penColorNumber + n) % 200;
        penColor = Color.decode(numberedColors[penColorNumber]);
    }

    /**
     * set the pen size to the given value.  If pen size is set to 0 or negative,
     * a size of 1 is used. 
     */
    public void setPenSize(int size)
    {
        penSize = size;
        // getWorld().getBackground().setPenWidth(size);

    }

    /**
     * change pen size by the given amount.  If pen size is set to 0 or negative,
     * a size of 1 is used.
     */
    public void changePenSizeBy(int size)
    {
        penSize += size;
        if (penSize <= 0) {
            penSize = 1;
        }
        setPenSize(penSize);
    }

    /**
     * clear the screen.
     */
    public void clear()
    {
        // This call actually rewrites the backdrop onto the background, without
        // all the pen drawing, stamping, etc.
        ((ScratchWorld) getWorld()).clearBackdrop();
    }

    /**
     * copy the actor's image onto the screen.
     */
    public void stamp()
    {
        // The image that we get will not be rotated the same as the current image, so 
        // we have to do that ourselves.  Also, getX() and getY() will get the center of
        // the image, but drawImage() wants the upper left corner of where to draw it.
        // So, have to do some some manipulation here before drawing the image onto the
        // background.

        // NOTE: we have to get the max of the width and height of the current image. 
        // Then, make a new empty image with both dimensions equal to that max value.
        // Then, draw the current image onto the new image.  Then, rotate it.

        // But, this is even more complicated: when you make the new big square image, now
        // the offset from the upperleft corner to the middle will have changed...
        GreenfootImage oldImg = getImage();
        int w = oldImg.getWidth(), h = oldImg.getHeight();
        // System.out.println("image width: " + w + " height " + h);
        int newDim = w > h ? w : h;
        GreenfootImage image = new GreenfootImage(newDim, newDim);  
        image.drawImage(oldImg, (newDim - w) / 2, (newDim - h) / 2);
        int rot = getRotation();
        image.rotate(rot);

        // System.out.println("stamp: x " + super.getX() + " y " + super.getY());
        getWorld().getBackground().drawImage(image, super.getX() - newDim / 2, 
            super.getY() - newDim / 2);
        // System.out.println("stamp: drawing at x " + (super.getX() - newDim / 2) +
        //     " y " + (super.getY() - newDim / 2));
    }

    /* 
     * TODO:
     * 2. change pen shade by <n>, or set pen shade to <n>.
     */

    /*
     * ---------------------------------------------------------------------
     * Motion commands.
     * ---------------------------------------------------------------------
     */

    /**
     * move the given distance in the direction the sprite is facing.
     */
    public void move(int distance) 
    {
        int oldX = super.getX();
        int oldY = super.getY();

        if (rotationStyle == RotationStyle.ALL_AROUND) {
            super.move(distance);
        } else {      // rotationStyle is LEFT_RIGHT || DONT_ROTATE

            // We don't use the move() function provided by greenfoot because
            // it moves in the direction the image is facing.  If we use it,
            // then we cannot do the setRotationStyle(LEFT_RIGHT) correctly.
            // So, instead we move the actor using setLocation().

            int GFdir = (currDirection - 90) % 360;

            // This code copied from Greenfoot source.
            double radians = Math.toRadians(GFdir);
            // We round to the nearest integer, to allow moving one unit at an angle
            // to actually move.
            int dx = (int) Math.round(Math.cos(radians) * distance);
            int dy = (int) Math.round(Math.sin(radians) * distance);
            setLocation(oldX + dx, oldY + dy);
        }

        /* pen is down, so we need to draw a line from the current point to the new point */
        if (isPenDown) {
            getWorld().getBackground().setColor(penColor);
            getWorld().getBackground().drawLine(oldX, oldY, super.getX(), super.getY());
        }
    }

    /**
     * glide the sprite to the given x, y coordinates over the given time period.
     */
    public void glideTo(Sequence s, double duration, int x, int y)
    {
        duration *= 1000.0;   // convert to milliseconds.
        int begX = super.getX();  // get original X, Y in Greenfoot coordinates
        int begY = super.getY();
        int endX = translateToGreenfootX(x);   // get end destination in GF coordinates.
        int endY = translateToGreenfootY(y);
        System.out.println("glideTo: beg " + begX + ", " + begY + " end " + endX + ", " + endY);
        double begTime = System.currentTimeMillis();
        double endTime = begTime + duration;
        double currTime;
        while ((currTime = System.currentTimeMillis()) < endTime) {
            try {
                s.waitForNextSequence();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            // Compute how far along we are in the duration time.
            double diff = (currTime - begTime) / duration;
            int newX = begX + (int) ((endX - begX) * diff);
            int newY = begY + (int) ((endY - begY) * diff);
            goToGF(newX, newY);
        }
    }

    /**
     * move the sprite to the location on the screen, where (0, 0) is the center and x increases
     * to the right and y increases up.
     */
    public void goTo(int x, int y) 
    {
        int newX = translateToGreenfootX(x);
        int newY = translateToGreenfootY(y);
        // Call goToGF() which assumes greenfoot coordinates.
        // System.out.println("goTo: got x, y = " + x + ", " + y + " which are " + newX + ", " + newY);
        goToGF(newX, newY);
    }

    /**
     * move the sprite to where the mouse is.
     */
    public void goToMouse() 
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if (mi == null) {
            return;
        }
        goToGF(mi.getX(), mi.getY());
    }

    /**
     * move to the location of another sprite
     */
    public void goTo(Scratch other)
    {
        goTo(other.getX(), other.getY());
    }

    /**
     * set the sprite's x position.  (left or right)
     */
    public void setXTo(int x) { 
        goTo(x, getY()); 
    }

    /**
     * set the sprite's y position.  (up or down)
     */
    public void setYTo(int y) 
    { 
        goTo(getX(), y); 
    }

    /**
     * change the x position of the sprite by the given value.
     */
    public void changeXBy(int val) 
    { 
        goTo(getX() + val, getY()); 
    }

    /**
     * change the y position of the sprite by the given value.
     */
    // subtract val from y since y increases upward in Scratch
    public void changeYBy(int val) 
    { 
        goTo(getX(), getY() + val); 
    }

    /**
     * turn the sprite clockwise by the given degrees.
     */
    public void turnRightDegrees(int degrees) {
        int oldDir = currDirection;
        currDirection = (currDirection + degrees) % 360;
        if (rotationStyle == RotationStyle.ALL_AROUND) {
            turn(degrees);
        } else if (rotationStyle == RotationStyle.LEFT_RIGHT) {
            if (isFacingLeft(oldDir) != isFacingLeft(currDirection)) {
                getImage().mirrorHorizontally();
            }
        } // else for DONT_ROTATE: nothing to do.
    }

    /**
     * turn the sprite counter-clockwise by the given degrees.
     */
    public void turnLeftDegrees(int degrees) { 
        int oldDir = currDirection;
        currDirection = (currDirection - degrees) % 360;
        if (rotationStyle == RotationStyle.ALL_AROUND) {
            turn(-degrees);
        } else if (rotationStyle == RotationStyle.LEFT_RIGHT) {
            if (isFacingLeft(oldDir) != isFacingLeft(currDirection)) {
                getImage().mirrorHorizontally();
            }
        } // else for DONT_ROTATE: nothing to do.turn(-degrees); 
    }

    /**
     * point the sprite in the given direction.  0 is up, 
     * 90 is to the right, -90 to the left, 180 is down.
     */
    public void pointInDirection(int dir) 
    {
        dir %= 360;   // get rid of extra rotations.

        // store the current direction (in Scratch orientation)
        int oldDir = currDirection;
        currDirection = dir;   

        if (rotationStyle == RotationStyle.ALL_AROUND) {
            // subtract 90 because 0 is up for Scratch, but to the east for Greenfoot.
            setRotation(dir - 90);
        } else if (rotationStyle == RotationStyle.LEFT_RIGHT) {
            // Only face directly left or right.

            if (isFacingLeft(oldDir) != isFacingLeft(currDirection)) {
                // direction has changed, so flip the image.
                getImage().mirrorHorizontally();  
            }

            // Note: no need to store changed image in currCostumes as they refer
            // to the same image in memory anyway.

            displayCostume();
        } // DONT_ROTATE: do nothing.
    }

    /**
     * return the direction the sprite is pointed in.  Note that the sprite/actor
     * may not look like it is facing this way, due to the rotation style that is set.
     */
    public int getDirection()
    {
        return currDirection;
    }

    /**
     * turn the sprite to point toward the direction of the given sprite
     */
    public void pointToward(Scratch other)
    {
        // deltaX and deltaY are in Greenfoot coordinates.
        double deltaX = translateToGreenfootX(other.getX()) - super.getX();
        double deltaY = translateToGreenfootY(other.getY()) - super.getY();
        double degrees = java.lang.Math.toDegrees(java.lang.Math.atan2(deltaY, deltaX));
        int oldDir = currDirection;
        currDirection = (int) degrees + 90;   // convert to Scratch orientation

        if (rotationStyle == RotationStyle.ALL_AROUND) {
            setRotation((int) degrees);
        } else if (rotationStyle == RotationStyle.LEFT_RIGHT) {
            if (isFacingLeft(oldDir) != isFacingLeft(currDirection)) {
                // direction has changed, so flip the image.
                getImage().mirrorHorizontally();  
            }
        }  // else DONT_ROTATE: do nothing

        // TODO: need displayCostume() call?
    }

    /**
     * turn the sprite to point toward the mouse.
     */
    public void pointTowardMouse()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if (mi == null) {
            return;
        }
        double deltaX = mi.getX() - super.getX();
        double deltaY = mi.getY() - super.getY();
        double degrees = java.lang.Math.toDegrees(java.lang.Math.atan2(deltaY, deltaX));
        // NOTE: this code identical to code above, and very similar to code above that... refactor!
        int oldDir = currDirection;
        currDirection = (int) degrees + 90;   // convert to Scratch orientation

        if (rotationStyle == RotationStyle.ALL_AROUND) {
            setRotation((int) degrees);
        } else if (rotationStyle == RotationStyle.LEFT_RIGHT) {
            if (isFacingLeft(oldDir) != isFacingLeft(currDirection)) {
                // direction has changed, so flip the image.
                getImage().mirrorHorizontally();  
            }
        }  // else DONT_ROTATE: do nothing
    }

    /**
     * if the sprite is on the edge of the Scenario, then changes its direction to point
     * as if it bounced against the edge.
     */
    public void ifOnEdgeBounce()
    {
        int worldW = getWorld().getWidth();
        int worldH = getWorld().getHeight();
        if (super.getX() >= worldW - 1 || super.getX() <= 0) {
            // hitting right edge or left edge
            setRotation(180 - getRotation());
        }
        if (super.getY() >= worldH - 1 || super.getY() <= 0) {
            // hitting bottom or top
            setRotation(360 - getRotation());
        }
    }

    /**
     * Set the rotation style to one of ALL_AROUND (default), LEFT_RIGHT, 
     * or DONT_ROTATE.
     */
    public void setRotationStyle(RotationStyle rs)
    {
        if (rs == rotationStyle) {
            return;    // no change
        }
        rotationStyle = rs;

        // Take the original image and make a new copy of it.
        GreenfootImage img = new GreenfootImage(costumesCopy.get(currCostume));
        // Now scale it.
        if (costumeSize != 100) {
            img.scale((int) (img.getWidth() *  (costumeSize / 100.0F)),
                (int) (img.getHeight() * (costumeSize / 100.0F)));
        }

        if (rs == RotationStyle.ALL_AROUND) {
            setRotation(currDirection);
        } else if (rs == RotationStyle.LEFT_RIGHT) {
            if (isFacingLeft(currDirection)) {
                img.mirrorHorizontally();
            }
            setRotation(0);    // TODO: necessary?  This is facing right.
        } else {    // DONT_ROTATE: always face right.
            setRotation(0);
        }

        // No need to rotate the image.  Rotation is a property of the Actor, not the image,
        // so when you switch images they are rotated automatically (just like Scratch as
        // it turns out).
        costumes.set(currCostume, img);
        displayCostume();
    }

    /**
     * return x coordinate of this sprite.
     */
    public int getX() 
    {
        // System.out.println("x in GF is " + super.getX(); + " but in scratch is " + translateGFtoScratchX(super.getX()));
        return translateGFtoScratchX(super.getX());
    }

    /**
     * return the y coordinate of this sprite.
     */
    public int getY() 
    {
        // System.out.println("y in GF is " + super.getY() + " but in scratch is " + translateGFtoScratchY(super.getY()));
        return translateGFtoScratchY(super.getY());
    }

    // private helper function
    private void goToGF(int x, int y)
    {
        if (! isPenDown) {
            super.setLocation(x, y);
            return;
        }
        /* pen is down, so we need to draw a line from the current point to the new point */
        int oldX = super.getX();
        int oldY = super.getY();
        super.setLocation(x, y);
        getWorld().getBackground().setColor(penColor);
        getWorld().getBackground().drawLine(oldX, oldY, super.getX(), super.getY());
    }

    // private helper function
    private boolean isFacingLeft(int dir)
    {
        return ((dir < 0 && dir > -180) || (dir > 180 && dir < 360));
    }

    /*
     * ---------------------------------------------------------------------
     * Commands from the Looks tab in Scratch.
     * ---------------------------------------------------------------------
     */

    /**
     * display the given string next to the sprite.
     */
    public void say(String str)
    {
        if (sayActor != null) {
            if (str == null) {
                // saying nothing means remove the sayActor
                getWorld().removeObject(sayActor);
                sayActor = null;
            } else {
                sayActor.setString(str);
            }
            return;
        }

        GreenfootImage mySprite = getImage();
        int width = mySprite.getWidth();
        int height = mySprite.getHeight();

        sayActor = new SayActor(str);
        getWorld().addObject(sayActor, super.getX() + width + 10, super.getY() - height - 5);
    }

    /**
     * display the given string for <n> seconds next to the sprite.
     */
    public void sayForNSeconds(Sequence s, String str, double duration)
    {
        GreenfootImage mySprite = getImage();
        int width = mySprite.getWidth();
        int height = mySprite.getHeight();

        sayActor = new SayActor(str);
        getWorld().addObject(sayActor, super.getX() + width + 10, super.getY() - height - 5);
        
        wait(s, duration);
        
        getWorld().removeObject(sayActor);
        sayActor = null;
    }

    // called from act() above to update the location of the say/think actor.
    private void sayActorUpdateLocation()
    {
        GreenfootImage mySprite = getImage();
        int width = mySprite.getWidth();
        int height = mySprite.getHeight();
        sayActor.updateLocation(super.getX() + width + 10, super.getY() - height - 5);
    }

    /**
     * add new costumes to the list of costumes for a sprite.  
     * Not available in Scratch.
     */
    public void addCostume(String costumeFile) 
    {
        costumes.add(new GreenfootImage(costumeFile));
        costumesCopy.add(new GreenfootImage(costumeFile));
    }

    /**
     * switch to the next costume
     */
    public void nextCostume() 
    {
        // System.out.println("nextCostume!");
        currCostume = (currCostume + 1) % costumes.size();
        displayCostume();
    }

    /**
     * switch to the previous costume
     */
    public void prevCostume()
    {
        // Note: this function is not offered in Scratch
        currCostume--;
        if (currCostume == -1) {
            currCostume = costumes.size() - 1;
        }
        displayCostume();
    }

    /**
     * switch to the costume with the given number.
     */
    public void switchToCostume(int costumeNum)
    {
        if (costumeNum < 0 || costumeNum > costumes.size() - 1) {
            return;     // can't switch to the desired costume, so do nothing.
        }
        currCostume = costumeNum;
        displayCostume();
    }

    /**
     * return the number of the current costume.
     */
    public int getCostumeNumber()
    {
        return currCostume;
    }

    /**
     * hide this sprite -- don't show it.
     */
    public void hide()
    {
        isShowing = false;
        displayCostume();
    }

    /**
     * show this sprite in the getWorld().
     */
    public void show()
    {
        isShowing = true;
        displayCostume();
    }

    /**
     * set the ghost effect (transparency) to a value from 0 to 100.  
     * 0 is fully visible; 100 is completely invisible.
     */
    public void setGhostEffectTo(int amount)
    {
        if (amount < 0) {
            amount = 0;
        } else if (amount > 100) {
            amount = 100;
        }
        ghostEffect = amount;
        displayCostume();

    }

    /**
     * change the ghost effect (transparency) by the given amount.
     * 0 is full visible; 100 is fully invisible.
     */
    public void changeGhostEffectBy(int amount)
    {
        setGhostEffectTo(ghostEffect + amount);
    }

    /**
     * change the size of this sprite by the given percent.
     */
    public void changeSizeBy(int percent)
    {
        setSizeTo(costumeSize + percent);
    }

    /**
     * Move the sprite to the front in the paint order.
     */
    public void goToFront()
    {
        // move this object's class in the paint order.
        ((ScratchWorld) getWorld()).moveClassToFront(this.getClass());
    }

    /**
     * Move the sprite forward <n> layers in the paint order.
     */
    public void goForwardNLayers(int n)
    {
        // move this object's class in the paint order.
        ((ScratchWorld) getWorld()).moveClassForwardNLayers(this.getClass(), n);
    }

    /**
     * Move the sprite back <n> layers in the paint order.
     */
    public void goBackNLayers(int n)
    {
        // move this object's class in the paint order.
        ((ScratchWorld) getWorld()).moveClassBackNLayers(this.getClass(), n);
    }

    /**
     * Paint the sprite at layer n.
     */
    public void setLayer(int n)
    {
        // move this object's class in the paint order.
        ((ScratchWorld) getWorld()).moveClassToLayerN(this.getClass(), n);
    }

    /**
     * Return the current layer of this sprite in the paint order.
     */
    public int getLayer()
    {
        return ((ScratchWorld) getWorld()).getLayer(this.getClass());
    }

    /**
     * return the size (in percent) of the sprite.  (100% is the 
     * original size.)
     */
    public int size() 
    {
        return costumeSize;
    }

    /**
     * Set the sprite size to a percentage of the original size.
     */
    public void setSizeTo(int percent)
    {
        float perc = percent / 100.0F;
        // Take the original image and make a new copy of it.
        GreenfootImage img = new GreenfootImage(costumesCopy.get(currCostume));
        // System.out.println("sst: Making copy of the costumesCopy and scaling it.");
        // Now scale it, store it and display it.
        img.scale((int) (img.getWidth() * perc), (int) (img.getHeight() * perc));
        // No need to rotate the image.  Rotation is a property of the Actor, not the image, 
        // so when you switch images they are rotated automatically (just like Scratch as
        // it turns out).
        costumes.set(currCostume, img);
        displayCostume();
        costumeSize = percent;

        /*System.out.println("sst: item from getImage is " + System.identityHashCode(getImage()));
        System.out.println("sst: item in costumes array is " + System.identityHashCode(costumes.get(currCostume)));
        System.out.println("sst: item in costumesCopy array is " + System.identityHashCode(costumesCopy.get(currCostume)));
         */
    }

    // private helper function
    private void displayCostume()
    {
        if (isShowing) {
            GreenfootImage img = costumes.get(currCostume);
            // Greenfoot transparency is from 0 to 255, with 0 being fully visible and 255 being
            // fully transparent.  So, we need to do a transformation: (0, 100) -> (255, 0)
            int transparency = (int) (((-1 * ghostEffect)   // now from -100 to 0
                        + 100)            // now from 0 to 100
                    * 2.55);         // now from 0 to 255.
            img.setTransparency(transparency);
            setImage(img);
        } else {
            System.out.println("displayCostume: changing image to null");
            setImage((GreenfootImage) null);
        }
    }

    /**
     * return the current backdrop name being shown in the world.
     */
    public String backdropName()
    {
        return ((ScratchWorld) getWorld()).getBackdropName();
    }

    /*
     * TODO: blocks to implement
     * 3. graphic effects blocks
     */

    /*
     * ---------------------------------------------------------------------
     * Sensing blocks.
     * ---------------------------------------------------------------------
     */

    /**
     * return true if this sprite is touching the other given sprite,
     * false otherwise.
     */
    public boolean isTouching(Scratch other)
    {
        return intersects((Actor) other);
    }

    /**
     * return true if this sprite is touching the mouse, 
     * false otherwise.
     */
    public boolean isTouchingMouse()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if (mi == null) {
            return false;
        }
        if (mi.getActor() == null) {
            return false;
        }
        return (mi.getActor() == this);
    }

    /**
     * return true if this sprite is touching the edge of the getWorld(),
     * false otherwise.
     */
    public boolean isTouchingEdge()
    {
        return (super.getX() >= getWorld().getWidth() - 1 || super.getX() <= 0 || 
            super.getY() >= getWorld().getHeight() - 1 || super.getY() <= 0);
    }

    /**
     * not implemented yet.
     */
    public boolean isTouchingColor(Color color)
    {
        /*
         * Not sure how to implement this.  I could just ask if the one pixel under 
         * (x, y) center of this sprite is the given color.  Or I could get the width
         * and height of the sprite image and check if any pixel under it is that color.
         * And/or I could check if the pixels that just border the image (or the one pixel)
         * are the given color...
         * 
         * I do know that I've seen kids use this function for detecting when a sprite 
         * touches a wall or reaches the goal, etc., so this is a function that does seem to be 
         * needed. 
         */
        GreenfootImage im = getImage();
        int height = im.getHeight();
        int width = im.getWidth();
        int x = getX();
        int y = getY();
        java.awt.image.BufferedImage bIm = im.getAwtImage();
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                int pixel = bIm.getRGB(w, h);
                if ((pixel >> 24) == 0x00) {
                    continue;   // transparent pixel: skip it.
                }
                // See if the pixel at this location in the background is of the given color.
                if (getWorld().getColorAt(x + (w / 2), y + (h / 2)).equals(color)) {
                    // Not sure this is correct, as it checks the transparency value as well...
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * return the x position of the mouse
     */
    public int getMouseX()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if (mi == null) {
            return translateGFtoScratchX(lastMouseX);
        }
        // squirrel away the x value so that every call to this function returns a x value, 
        // even if the mouse hasn't moved.
        lastMouseX = mi.getX();
        return translateGFtoScratchX(lastMouseX);
    }

    /**
     * return the y position of the mouse
     */
    public int getMouseY()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if (mi == null) {
            return translateGFtoScratchY(lastMouseY);
        }
        // squirrel away the y value so that every call to this function returns a y value, 
        // even if the mouse hasn't moved.
        lastMouseY = mi.getY();
        return translateGFtoScratchY(lastMouseY);
    }

    /**
     * return true if the mouse is pressed, else false.
     */
    public boolean isMouseDown()
    {
        return Greenfoot.mousePressed(null);
    }

    /**
     * return true if the given key is currently pressed, else false.
     */
    public boolean isKeyPressed(java.lang.String keyName)
    {
        return Greenfoot.isKeyDown(keyName);
    }

    /**
     * return the distance in pixels to the other sprite.
     */
    public int distanceTo(Scratch other)
    {
        int deltaX = super.getX() - other.getX();
        int deltaY = super.getY() - other.getY();
        return (int) java.lang.Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * return the distance in pixels to the mouse pointer.
     */
    public int distanceToMouse()
    {
        int x, y;
        MouseInfo mi = Greenfoot.getMouseInfo();
        if (mi == null) {
            x = lastMouseX;
            y = lastMouseY;
        } else {
            x = mi.getX();
            y = mi.getY();
        }
        int deltaX = super.getX() - x;
        int deltaY = super.getY() - y;
        return (int) java.lang.Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * return the time, in seconds and tenths of seconds, since the scenario started.
     */
    public double getTimer()
    {
        return ((ScratchWorld) getWorld()).getTimer();
    }

    /**
     * reset the built-in timer to 0.0.
     */
    public void resetTimer()
    {
        ((ScratchWorld) getWorld()).resetTimer();
    }

    /**
     * return the x position of the given sprite.
     */
    public int xPositionOf(Scratch other)
    {
        return translateGFtoScratchX(other.getX());
    }

    /**
     * return the y position of the given sprite.
     */
    public int yPositionOf(Scratch other)
    {
        return translateGFtoScratchY(other.getY());
    }

    /**
     * return the direction the given sprite is pointing to.
     */
    public int directionOf(Scratch other)
    {
        return other.getDirection();
    }

    /**
     * return the costume number of the given sprite
     */
    public int costumeNumberOf(Scratch other)
    {
        return other.getCostumeNumber();
    }

    /**
     * return the size (in percentage of the original) of the given sprite
     */
    public int sizeOf(Scratch other)
    {
        return other.size();
    }

    /**
     * return the current year.
     */
    public int getCurrentYear()
    {
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        return now.get(Calendar.YEAR);
    }

    /**
     * return the current month.
     */
    public int getCurrentMonth()
    {
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * return the current date.
     */
    public int getCurrentDate()
    {
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        return now.get(Calendar.DATE);
    }

    /**
     * return the current day of the week.
     */
    public int getCurrentDayOfWeek()
    {
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        return now.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * return the current hour.
     */
    public int getCurrentHour()
    {
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        return now.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * return the current minute.
     */
    public int getCurrentMinute()
    {
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        return now.get(Calendar.MINUTE);
    }

    /**
     * return the current second.
     */
    public int getCurrentSecond()
    {
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        return now.get(Calendar.SECOND);
    }

    /**
     * askStringAndWait
     */
    public String askStringAndWait(String message)
    {
        return JOptionPane.showInputDialog(message);
    }

    /*
     * TODO: implement these blocks yet:
     * 1. touching color <color>
     * 2. color <col> is touching <col> ?   This one is really hard, I think...
     *    but, I've never seen any person use it.
     * 3. xxx
     * 4. xxx
     * 5. xxx
     * 6. loudness
     * 7. is loud ? 
     * 8. sensor stuff -- won't implement.
     * 9. getVolumeOf()
     */

    /*
     * Miscellaneous stuff.
     */

    /**
     * delay execution for "duration" seconds.
     * 
     * Note: this does not work if the duration is very short -- shorter than
     * the current frameRate that Greenfoot is running at.  
     * Also, note that this implementation is NOT dependent on the speed the simulation 
     * is running at: it tries to wait exactly duration seconds, regardless of how fast
     * the speed is set.
     */
    public void wait(Sequence s, double duration) 
    {
        double endTime = System.currentTimeMillis() + duration * 1000.0;
        while (System.currentTimeMillis() < endTime) {
            try {
                s.waitForNextSequence();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void wait(Sequence s, int duration) {  wait(s, (double) duration); }

    public void wait(Sequence s, float duration) {  wait(s, (double) duration); }

    /*
     * --------------------------------------------------------------
     * Operator Blocks
     * --------------------------------------------------------------
     */

    public String join(String a, String b) { return a + b; }

    public String join(String a, int b) { return a + b; }

    public String join(String a, double b) { return a + b; }

    public String join(String a, float b) { return a + b; }

    public String join(int a, String b) { return Integer.toString(a) + b; }

    public String join(double a, String b) { return Double.toString(a) + b; }

    public String join(float a, String b) { return Float.toString(a) + b; }

    public String join(int a, int b) { return Integer.toString(a) + Integer.toString(b); }

    public String join(double a, double b) { return Double.toString(a) + Double.toString(b); }

    public String join(float a, float b) { return Float.toString(a) + Float.toString(b); }

    public String join(int a, double b) { return Integer.toString(a) + Double.toString(b); }

    public String join(int a, float b) { return Integer.toString(a) + Float.toString(b); }

    public String join(float a, int b) { return Float.toString(a) + Integer.toString(b); }

    public String join(double a, int b) { return Double.toString(a) + Integer.toString(b); }

    public String join(float a, double b) { return Float.toString(a) + Double.toString(b); }

    public String join(double a, float b) { return Double.toString(a) + Float.toString(b); }

    public String letterNOf(String s, int n) 
    {
        if (n < 0) {
            return "";
        }
        if (n >= s.length()) {
            return "";
        }
        return "" + s.charAt(n);
    }

    public String letterNOf(int i, int n) { return letterNOf(Integer.toString(i), n); }

    public String letterNOf(double d, int n) { return letterNOf(Double.toString(d), n); }

    public String letterNOf(float f, int n) { return letterNOf(Float.toString(f), n); }

    public int lengthOf(String s) 
    {
        return s.length();
    }

    public int lengthOf(int i) { return lengthOf(Integer.toString(i)); }

    public int lengthOf(double d) { return lengthOf(Double.toString(d)); }

    public int lengthOf(float f) { return lengthOf(Float.toString(f)); }

    /**
     * return a random number between low and high, inclusive (for both).
     */
    public int pickRandom(int low, int high)
    {
        // getRandomNumber gets a number between 0 (inclusive) and high (exclusive).
        // so we have add low to the value.
        return Greenfoot.getRandomNumber(high - low + 1) + low;
    }

    public int getWorldMinX()
    {
        return translateGFtoScratchX(0);
    }

    public int getWorldMaxX()
    {
        return translateGFtoScratchX(getWorld().getWidth() - 1);
    }

    public int getWorldMinY()
    {
        // subtract 1 because the getWorld() goes from 0 to Height - 1.
        return translateGFtoScratchY(getWorld().getHeight() - 1);
    }

    public int getWorldMaxY()
    {
        return translateGFtoScratchY(0);
    }

    /*
     * Scratch's (0, 0) is in the middle, with increase x to the right.  So, to translate
     * from scratch to greenfoot, add half the width of the getWorld().
     */
    public int translateToGreenfootX(int x) 
    {
        return x + getWorld().getWidth() / 2;
    }

    /*
     * Scratch's (0, 0) is in the middle, with y increasing y up, while greenfoot's 0, 0 is in 
     * the upper-left corner with y increasing downward.
     */
    public int translateToGreenfootY(int y) 
    {
        return getWorld().getHeight() / 2 - y;
    }

    /*
     * translateGFToScratchX - translate greenfoot x coordinate to a Scratch coord.
     */
    public int translateGFtoScratchX(int x)
    {
        return x - getWorld().getWidth() / 2;
    }

    /*
     * translateGFToScratchY - translate greenfoot y coordinate to a Scratch coord.
     */
    public int translateGFtoScratchY(int y)
    {
        return getWorld().getHeight() / 2 - y;
    }

}